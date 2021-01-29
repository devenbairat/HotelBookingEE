package pages;

import browser.BaseUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import utilities.Utilities;

import java.util.List;
import java.util.Map;

public class HotelBookingPage extends BaseUtil {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final String BOOKING_URL = "http://hotel-test.equalexperts.io/";

    // Page Object Locators
    @FindBy(id = "firstname")
    public WebElement firstNameInput;

    @FindBy(id = "lastname")
    public WebElement lastNameInput;

    @FindBy(id = "totalprice")
    public WebElement totalPriceInput;

    @FindBy(xpath = "//select[(@id='depositpaid')]")
    public WebElement depositSelect;

    @FindBy(id = "checkin")
    public WebElement checkInDateInput;

    @FindBy(id = "checkout")
    public WebElement checkOutDateInput;

    @FindBy(xpath="//input[contains(@value,'Save') and (@type='button')]")
    public WebElement saveBooking;

    // Xpath Locators
    String bookingRowXpath       = "//p[contains(text(),\"%s\")]/ancestor::div[@class='row']";
    String bookingRowValuesXpath = "//p[contains(text(),\"%s\")]/ancestor::div[@class='row']//child::p";
    String bookingRowDeleteXpath = "//p[contains(text(),\"%s\")]/ancestor::div[@class='row']//child::input[@type='button' and @value='Delete']";

    public HotelBookingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }

    public HotelBookingPage navigateToBookingPage() {
        driver.navigate().to(BOOKING_URL);
        PageFactory.initElements(driver,this);
        return this;
    }

    public HotelBookingPage captureBookingDetails(List<Map<String, String>> bookingDetailsMap, String uniqueId) throws Exception {
        for (Map<String, String> columns : bookingDetailsMap) {
            firstNameInput.sendKeys(columns.get("Firstname"));
            lastNameInput.sendKeys(columns.get("Surname") + "_" + uniqueId);
            totalPriceInput.sendKeys(columns.get("Price"));
            setDepositPaid(depositSelect, columns.get("Deposit"));
            checkInDateInput.sendKeys(Utilities.getDates(columns.get("Check-in")));
            checkOutDateInput.sendKeys(Utilities.getDates(columns.get("Check-out")));
        }
        return this;
    }

    public HotelBookingPage confirmBooking() {
        saveBooking.click();
        return this;
    }

    public HotelBookingPage validateBooking(List<Map<String, String>> bookingDetailsMap, String uniqueId) throws Exception {

        // Wait for booking to appear on the form
        List<WebElement> bookingRowValues = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(String.format(bookingRowValuesXpath, uniqueId)))));

        for (Map<String, String> columns : bookingDetailsMap) {
            Assert.assertEquals(columns.get("Firstname"),bookingRowValues.get(0).getText());
            Assert.assertEquals(columns.get("Surname") + "_" + uniqueId,bookingRowValues.get(1).getText());
            Assert.assertEquals(columns.get("Price"),bookingRowValues.get(2).getText());
            Assert.assertEquals(columns.get("Deposit"),bookingRowValues.get(3).getText());
            Assert.assertEquals(Utilities.getDates(columns.get("Check-in")),bookingRowValues.get(4).getText());
            Assert.assertEquals(Utilities.getDates(columns.get("Check-out")),bookingRowValues.get(5).getText());
        }

        return this;
    }

    public HotelBookingPage cancelBooking(String uniqueId) {
        driver.findElement((By.xpath(String.format(bookingRowDeleteXpath, uniqueId)))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath(String.format(bookingRowXpath, uniqueId)))));
        return this;
    }

    public HotelBookingPage confirmBookingDeleted(String uniqueId) {
        int elementCount = driver.findElements((By.xpath(String.format(bookingRowXpath, uniqueId)))).size();
        Assert.assertEquals(elementCount,0);
        return this;
    }

    public HotelBookingPage confirmBookingNotPresent(String uniqueId) {
        Boolean bookingFound = wait.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath(String.format(bookingRowXpath, uniqueId)))));
        Assert.assertFalse("Booking was created with invalid data", bookingFound);
        return this;
    }

    private void setDepositPaid(WebElement depositSelect, String depositPaid) {
        Select dropdown   = new Select(depositSelect);
        WebElement option = dropdown.getFirstSelectedOption();

        if (!option.getText().equals(depositPaid)) {
            dropdown.selectByVisibleText(depositPaid);
        }
    }
}
