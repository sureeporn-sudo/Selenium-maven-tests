package com.sureeporn;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DuckDuckGoTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://duckduckgo.com/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchSeleniumWebDriverAndVerifyResults() {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.sendKeys(Keys.ENTER);

        assertTrue(driver.getTitle().toLowerCase().contains("selenium webdriver"));
        List<WebElement> results = driver.findElements(By.cssSelector("[data-testid='result']"));
        assertFalse(results.isEmpty());
    }

    @Test
    public void searchJUnit5AndVerifyResults() {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("JUnit 5");
        searchBox.sendKeys(Keys.ENTER);

        assertTrue(driver.getTitle().toLowerCase().contains("junit 5"));
        List<WebElement> results = driver.findElements(By.cssSelector("[data-testid='result']"));
        assertFalse(results.isEmpty());
    }

    @Test
    public void clickFirstResultAndVerifyNavigation() {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.sendKeys(Keys.ENTER);

        List<WebElement> resultLinks = driver.findElements(By.cssSelector("[data-testid='result-title-a']"));
        assertFalse(resultLinks.isEmpty());

        resultLinks.get(0).click();

        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertFalse(currentUrl.isEmpty());
        assertTrue(driver.getTitle().length() > 0);
    }
}
