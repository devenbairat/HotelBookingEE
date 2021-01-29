package stepDefinitions;

import browser.BaseUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks extends BaseUtil {

    BaseUtil base;

    @Before
    public void testInitializer(){
        WebDriver driver = base.getDriver();
        setDriver(driver);
    }

    public Hooks(BaseUtil base) {
        this.base = base;
    }

    @After
    public void tearDownTest(){
        base.getDriver().close();
    }
}
