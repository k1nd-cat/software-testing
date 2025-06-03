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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HabrCareerTests {

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
    @DisplayName("Открытие \"Хабр Карьера\" в новой вкладке")
    public void openCareerInNewTab() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'career.habr.com')]")
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

        wait.until(ExpectedConditions.urlContains("career.habr.com"));
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("career.habr.com"));
    }

    @Test
    @DisplayName("Открыть вакансию")
    public void openVacancyTest() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'career.habr.com')]")
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

        wait.until(ExpectedConditions.urlContains("career.habr.com"));

        List<WebElement> vacancies = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'l-vacancy-card__wrapper')]")
                )
        );

        assertFalse(vacancies.isEmpty(), "Нет найденных вакансий");

        WebElement firstVacancy = vacancies.getFirst();

        String expectedUrl = firstVacancy.getAttribute("href");
        assertNotNull(expectedUrl, "У вакансии нет ссылки");

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                firstVacancy
        );

        wait.until(ExpectedConditions.elementToBeClickable(firstVacancy)).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
