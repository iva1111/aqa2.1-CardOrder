package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendValidForm() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванова Анна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79021111111");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actualText = driver.findElement(By.cssSelector(".paragraph_theme_alfa-on-white")).getText();
        assertEquals(expectedMessage, actualText.trim());
    }

    @Test
    public void shouldGetErrorMessageIfNameNotRussian() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ivanova Anna");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79021111111");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText= driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals(expectedMessage, actualText.trim());
    }

    @Test
    public void shouldGetErrorMessageIfPhoneNumberIsIncorrect() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванова Анна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("1111111");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals(expectedText, actualText.trim());
    }

    @Test
    public void shouldGetErrorMessageIfEmptyFieldOfName() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79021111111");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals(expectedText, actualText.trim());
    }

    @Test
    public void shouldGetErrorMessageIfEmptyFieldOfPhoneNumber() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванова Анна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals(expectedText, actualText.trim());
    }

    @Test
    public void shouldShowErrorWithoutAgreementInCheckBox() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванова Анна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79021111111");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid"));
    }


}
