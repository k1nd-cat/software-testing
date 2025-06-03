package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteArticleTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(15);
    private final String START_URL = "https://habr.com/ru/articles/";

    private JavascriptExecutor js;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        driver.manage().window().maximize();

        js = (JavascriptExecutor) driver;

        wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT);
        driver.get(START_URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка, что статью можно отправить на модерацию")
    public void writeArticleTest() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'tm-header-user-menu__login') and contains(text(), 'Войти')]")
        ));
        loginButton.click();

        String urlBeforeLogin = driver.getCurrentUrl();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ignore) {}

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeLogin)));

        WebElement writeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'tm-header-user-menu__write')]/a")
        ));
        js.executeScript("arguments[0].click();", writeButton);

        By titleInputLocator = By.xpath("//div[@data-placeholder='Заголовок']/h1[@data-node-view-content]");
        WebElement titleInput = wait.until(ExpectedConditions.presenceOfElementLocated(titleInputLocator));
        String articleTitle = "Новая тестовая статья";
        titleInput.click();
        titleInput.sendKeys(articleTitle);

        By editorBodyLocator = By.xpath("//div[contains(@class, 'ProseMirror') and @role='textbox']");
        WebElement editorBody = wait.until(ExpectedConditions.elementToBeClickable(editorBodyLocator));
        String articleText = "Статья будет информативная";

        editorBody.click();
        editorBody.sendKeys(articleText);

        By nextButtonLocator = By.xpath("//button[@data-test-id='publication_ready_for_publish']");
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
        assertTrue(nextButton.isEnabled(), "Кнопка 'Далее к настройкам' должна быть активна.");
    }
}
