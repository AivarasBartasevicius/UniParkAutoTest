package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages;

import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Components.DatePicker;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Components.TimePicker;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.PickerType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{
    //lt nesikeicia
    private By orderParking = By.cssSelector(".order-button .order-link");
    private By cookie = By.cssSelector(".cookieConsentOK");
    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage openHomePage(){
        open("/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cookie));
        driver.findElement(cookie).click();
        return this;
    }

    public DatePicker openDatePicker(PickerType type){
        return new DatePicker(driver, wait, type);
    }

    public TimePicker openTimePicker(PickerType type){
        return new TimePicker(driver, wait, type);
    }

    public OrderPageDateAndTime submitParkingOrder(){
        driver.findElement(orderParking).click();
        return new OrderPageDateAndTime(driver);
    }

}
