package browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import utilities.ConfigurationReader;

public class BaseUtil {

    private WebDriver driver;

    public WebDriver getDriver() {
        if(driver == null){

            String browser = ConfigurationReader.getProperty("browser");

            switch (browser){

                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                case "opera":
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;

                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver( new ChromeOptions().setHeadless(true));
                    break;

                default:
                    throw new IllegalArgumentException("Supplied browser ["+browser+"] not configured.");
            }
        }
        return driver;
    }

    public void setDriver(WebDriver driver) { this.driver = driver;}

}
