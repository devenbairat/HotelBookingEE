package pages;

import booking.Booking;
import browser.BaseUtil;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        this.wait = new WebDriverWait(driver, 5);
    }

    public HotelBookingPage navigateToBookingPage() {
        driver.navigate().to(BOOKING_URL);
        PageFactory.initElements(driver,this);
        return this;
    }

    public HotelBookingPage captureBookingDetails(Booking booking) throws Exception {
        firstNameInput.sendKeys(booking.getFirstname());
        lastNameInput.sendKeys(booking.getSurname());
        totalPriceInput.sendKeys(booking.getPrice());
        setDepositPaid(depositSelect, booking.getDeposit());
        checkInDateInput.sendKeys(Utilities.getDates(booking.getCheckinDate()));
        checkOutDateInput.sendKeys(Utilities.getDates(booking.getCheckoutDate()));
        return this;
    }

    public HotelBookingPage confirmBooking() {
        saveBooking.click();
        return this;
    }

    public HotelBookingPage validateBooking(Booking booking) throws Exception {

        // Wait for booking to appear on the form
        List<WebElement> bookingRowValues = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(String.format(bookingRowValuesXpath, booking.getUniqueId())))));

        Assert.assertEquals(booking.getFirstname(),bookingRowValues.get(0).getText());
        Assert.assertEquals(booking.getSurname(),bookingRowValues.get(1).getText());
        Assert.assertEquals(booking.getPrice(),bookingRowValues.get(2).getText());
        Assert.assertEquals(booking.getDeposit(),bookingRowValues.get(3).getText());
        Assert.assertEquals(Utilities.getDates(booking.getCheckinDate()),bookingRowValues.get(4).getText());
        Assert.assertEquals(Utilities.getDates(booking.getCheckoutDate()),bookingRowValues.get(5).getText());

        return this;
    }

    public HotelBookingPage validatePriceOnBooking(String price, String uniqueId) {
        // Wait for booking to appear on the form
        List<WebElement> bookingRowValues = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(String.format(bookingRowValuesXpath, uniqueId)))));
        Assert.assertEquals(price,bookingRowValues.get(2).getText());
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

    public HotelBookingPage confirmBookingNotSaved(String uniqueId) {
        boolean bookingFound;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(String.format(bookingRowXpath, uniqueId)))));
            bookingFound = true;
        } catch (Exception e) {
            bookingFound = false;
        }
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
