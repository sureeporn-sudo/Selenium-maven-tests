package com.sureeporn;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactFormTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://ist109.netlify.app/forms");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void submitValidFormData() {
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Anna");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("anna@example.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Password123");

        List<WebElement> radios = driver.findElements(By.cssSelector("input[type='radio']"));
        assertFalse(radios.isEmpty(), "Radio buttons should exist");
        radios.get(0).click();

        WebElement dropdownElement = driver.findElement(By.tagName("select"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Technology");

        WebElement checkbox = driver.findElement(By.cssSelector("input[type='checkbox']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.cssSelector("button[type='submit'], input[type='submit']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Form Submitted Successfully')]")
                )
        );

        assertTrue(successMessage.isDisplayed(), "Success message should be displayed");
    }

    @Test
    public void testFormValidation() {
        driver.findElement(By.cssSelector("button[type='submit'], input[type='submit']")).click();

        WebElement usernameField = driver.findElement(By.cssSelector("input[type='text']"));
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));

        boolean usernameInvalid = Boolean.parseBoolean(usernameField.getAttribute("required")) || !usernameField.getAttribute("validationMessage").isEmpty();
        boolean emailInvalid = Boolean.parseBoolean(emailField.getAttribute("required")) || !emailField.getAttribute("validationMessage").isEmpty();
        boolean passwordInvalid = Boolean.parseBoolean(passwordField.getAttribute("required")) || !passwordField.getAttribute("validationMessage").isEmpty();

        assertTrue(usernameInvalid || emailInvalid || passwordInvalid,
                "At least one required field should trigger validation");
    }

    @Test
    public void testAllFormFields() {
        WebElement usernameField = driver.findElement(By.cssSelector("input[type='text']"));
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));

        usernameField.sendKeys("Anna");
        emailField.sendKeys("anna@example.com");
        passwordField.sendKeys("Password123");

        assertEquals("Anna", usernameField.getAttribute("value"));
        assertEquals("anna@example.com", emailField.getAttribute("value"));
        assertEquals("Password123", passwordField.getAttribute("value"));

        List<WebElement> radioButtons = driver.findElements(By.cssSelector("input[type='radio']"));
        assertEquals(3, radioButtons.size(), "There should be 3 gender radio buttons");
        radioButtons.get(1).click();
        assertTrue(radioButtons.get(1).isSelected(), "Second radio button should be selected");

        WebElement dropdownElement = driver.findElement(By.tagName("select"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Sports");
        assertEquals("Sports", dropdown.getFirstSelectedOption().getText());

        WebElement checkbox = driver.findElement(By.cssSelector("input[type='checkbox']"));
        checkbox.click();
        assertTrue(checkbox.isSelected(), "Checkbox should be selected");

        WebElement fileInput = driver.find