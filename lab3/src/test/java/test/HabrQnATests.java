package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HabrQnATests {
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
    @DisplayName("Открытие \"Хабр Q&A\" в новой вкладке")
    public void openQAInNewTab() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'qna.habr.com')]")
                )
        );
        careerLink.click();

        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.urlContains("qna.habr.com"));
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("qna.habr.com"));
    }

    @Test
    @DisplayName("Открыть \"Хабр Q&A\" вопрос по заданному тегу")
    public void openByTeg() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'qna.habr.com')]")
                )
        );
        careerLink.click();

        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.urlContains("qna.habr.com"));

        WebElement tagsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='tags' and contains(@class, 'main-menu__link')]")
        ));
        tagsLink.click();

        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.suggest__field")
        ));
        searchField.sendKeys("dart");
        searchField.sendKeys(Keys.ENTER);

        List<WebElement> questionLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("a.question__title-link.question__title-link_list")
        ));

        if (questionLinks.isEmpty()) {
            throw new RuntimeException("Не найдено вопросов по запросу 'dart'");
        }

        String firstTitle = questionLinks.getFirst().getText().trim();

        questionLinks.getFirst().click();

        WebElement questionPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h1.question__title")
        ));
        String pageTitle = questionPageTitle.getText().trim();

        assertEquals(firstTitle, pageTitle);
    }

    @Test
    @DisplayName("Открыть \"Хабр Q&A\" пользователя")
    public void openUser() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'qna.habr.com')]")
                )
        );
        careerLink.click();

        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.urlContains("qna.habr.com"));

        WebElement tagsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='users' and contains(@class, 'main-menu__link')]")
        ));
        tagsLink.click();

        List<WebElement> userLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[contains(@href, '/user/') and @itemprop='url']")
        ));

        if (userLinks.isEmpty()) {
            throw new RuntimeException("Не найдено пользователей по заданному XPath");
        }

        String expectedUserName = "Пользователь " + userLinks.getFirst().getText().trim() + " — Хабр Q&A";

        userLinks.getFirst().click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String pageTitle = driver.getTitle();

        assertEquals(expectedUserName, pageTitle);
    }
}
