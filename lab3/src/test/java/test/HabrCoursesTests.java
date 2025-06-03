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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HabrCoursesTests {
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
    @DisplayName("Открытие \"Хабр Курсы\" в новой вкладке")
    public void openQAInNewTab() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'career.habr.com/courses')]")
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

        wait.until(ExpectedConditions.urlContains("career.habr.com/courses"));
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("career.habr.com/courses"));
    }

    @Test
    @DisplayName("Открыть курс")
    public void openCourse() {
        WebElement dropdownToggle = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='tm-header__dropdown-toggle']")
                )
        );
        dropdownToggle.click();

        WebElement careerLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='tm-our-projects__item' and contains(@href, 'career.habr.com/courses')]")
                )
        );
        careerLink.click();

        String mainWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!mainWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.urlContains("career.habr.com/courses"));

        List<WebElement> courses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[contains(@class, 'bg-ui-white') and contains(@class, 'rounded-lg') and @target='_blank']")
        ));

        if (courses.isEmpty()) {
            throw new RuntimeException("Не найдено ни одного курса");
        }

        String firstCourseUrl = courses.getFirst().getAttribute("href");

        String coursesWindow = driver.getWindowHandle();
        courses.getFirst().click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(3));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!mainWindow.equals(windowHandle) && !coursesWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("about:blank")));
        String newTabUrl = driver.getCurrentUrl();

        String expectedBaseUrl = firstCourseUrl.split("\\?")[0];
        String actualBaseUrl = newTabUrl.split("\\?")[0];

        driver.close();
        driver.switchTo().window(coursesWindow);

        assertEquals(expectedBaseUrl, actualBaseUrl);
    }
}
