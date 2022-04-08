package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.Home;

import java.io.IOException;

public class Ejecucion {
    private String PATHDRIVER = "src/test/resources/drivers/";
    private String baseURL = "https://www.mercadolibre.cl";
    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", PATHDRIVER + "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
    }

    @Test
    public void signUp() throws InterruptedException, IOException {
        driver.get(baseURL);
        Home homePage = new Home(driver);
        homePage.busqueda();

    }

    @After
    public void close() {
        driver.close();
    }
}
