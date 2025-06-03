package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenArticleTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(15);
    private final String START_URL = "https://habr.com/ru/articles/";

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
    @DisplayName("Открыть статью на хабре")
    public void openDefaultArticle() {
        List<WebElement> articleLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[@class='tm-title__link' and @data-article-link='true' and @data-test-id='article-snippet-title-link']/span")
        ));

        if (!articleLinks.isEmpty()) {
            WebElement firstArticle = articleLinks.getFirst();
            String articleTitle = firstArticle.getText();
            articleLinks.getFirst().click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertArticleTitle(articleTitle + " / Хабр");
        }
    }

    @Test
    @DisplayName("Открыть статью на хабре после её поиска")
    public void openArticleBySearch() {
        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-header-user-menu__item tm-header-user-menu__search' and @data-test-id='search-button']")
                )
        );
        searchButton.click();

        WebElement searchForm = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//form[@class='tm-search' and @action='/ru/search/']")
                )
        );

        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@class='tm-search__input tm-input-text-decorated__input' and @name='q']")
                )
        );
        searchInput.sendKeys("DHCP-сервер облачной сети MWS. Как мы одинаковые адреса на разные виртуалки раздаём");

        WebElement searchSubmit = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class, 'tm-input-text-decorated__label_after')]//span[contains(@class, 'tm-search__icon')]")
                )
        );
        searchSubmit.click();

        List<WebElement> articleLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[@class='tm-title__link' and @data-article-link='true' and @data-test-id='article-snippet-title-link']/span")
        ));

        if (!articleLinks.isEmpty()) {
            WebElement firstArticle = articleLinks.getFirst();
            articleLinks.getFirst().click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertArticleTitle("DHCP-сервер облачной сети MWS. Как мы одинаковые адреса на разные виртуалки раздаём / Хабр");
        }
    }

    @Test
    @DisplayName("Открыть статью на хабре в категории \"Научпоп\"")
    public void openArticleByCategory() {
        WebElement sciencePopLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(), 'Научпоп')]")
                )
        );
        sciencePopLink.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> articleLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[@class='tm-title__link' and @data-article-link='true' and @data-test-id='article-snippet-title-link']/span")
        ));

        if (!articleLinks.isEmpty()) {
            WebElement firstArticle = articleLinks.getFirst();
            String articleTitle = firstArticle.getText();
            articleLinks.getFirst().click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertArticleTitle(articleTitle + " / Хабр");
        }
    }

    private void assertArticleTitle(String expectedTitle) {
        String articleTitle = driver.getTitle();
        assertEquals(expectedTitle, articleTitle);
    }
}
