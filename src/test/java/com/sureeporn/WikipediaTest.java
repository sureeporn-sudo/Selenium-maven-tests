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

public class WikipediaTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.wikipedia.org/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchForSoftwareTesting() {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("Software Testing");
        searchBox.sendKeys(Keys.ENTER);

        assertTrue(driver.getTitle().toLowerCase().contains("software testing"),
                "Page title should contain 'Software Testing'");
    }

    @Test
    public void verifyArticleHeading() {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("Software Testing");
        searchBox.sendKeys(Keys.ENTER);

        WebElement heading = driver.findElement(By.id("firstHeading"));
        assertEquals("Software testing", heading.getText(),
                "Article heading should be 'Software testing'");
    }

    @Test
    public void countNumberOfSections() {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("Software Testing");
        searchBox.sendKeys(Keys.ENTER);

        List<WebElement> sections = driver.findElements(By.cssSelector("h2"));
        assertFalse(sections.isEmpty(), "There should be at least one section");

        System.out.println("Number of sections: " + sections.size());
    }
}