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
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveArticleTest {
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
    @DisplayName("Сохранить / удалить статью из закладок")
    public void openDefaultArticle() throws InterruptedException {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'tm-header-user-menu__login') and contains(text(), 'Войти')]")
        ));
        loginButton.click();

        String urlBeforeLogin = driver.getCurrentUrl();
        Thread.sleep(20000);

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeLogin)));

        List<WebElement> articleLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[@class='tm-title__link' and @data-article-link='true' and @data-test-id='article-snippet-title-link']/span")
        ));

        if (articleLinks.isEmpty()) {
            throw new RuntimeException("Статей не найдено");
        }
        articleLinks.getFirst().click();

        By counterLocator = By.xpath("//span[@class='bookmarks-button__counter']");

        WebElement counterElementForScroll = wait.until(ExpectedConditions.presenceOfElementLocated(counterLocator));
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", counterElementForScroll);
        wait.until(ExpectedConditions.textMatches(counterLocator, Pattern.compile("^\\d+$")));

        String initialCountStr = driver.findElement(counterLocator).getText().trim();
        int initialCountValue = Integer.parseInt(initialCountStr);

        WebElement bookmarkButton;
        try {
            bookmarkButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@title='Добавить в закладки' and contains(@class, 'bookmarks-button')]")
            ));
        } catch (Exception ignore) {
            bookmarkButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@title='Убрать из закладок' and contains(@class, 'bookmarks-button')]")
            ));
        }
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", bookmarkButton);
        js.executeScript("arguments[0].click();", bookmarkButton);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String updatedCountStr = driver.findElement(counterLocator).getText().trim();
        int updatedCountValue = Integer.parseInt(updatedCountStr);

        assertTrue(initialCountValue != updatedCountValue);
    }
}
