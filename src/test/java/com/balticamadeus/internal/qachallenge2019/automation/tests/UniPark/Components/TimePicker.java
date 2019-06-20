package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Components;

import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.PickerType;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages.HomePage;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages.OrderPageDateAndTime;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class TimePicker {
    private final WebDriver driver;
    private WebDriverWait wait;

    private By datePickerLocation = By.id("ui-datepicker-div");

    private By timePickerTo = By.cssSelector("[id = 'hour_to']");
    private By timePickerFrom = By.cssSelector("[id = 'hour_from']");

    private By timeSelects = By.cssSelector(".ui-timepicker-list li");

    public TimePicker(WebDriver driver, WebDriverWait wait, PickerType type){
        this.driver = driver;
        this.wait = wait;
        switch (type){
            case FROM:
                driver.findElement(timePickerFrom).click();
                break;
            case TO:
                driver.findElement(timePickerTo).click();
                break;
        }
    }

    public HomePage selectTimeAndGoBackToHomePage(int time){
        selectTime(time);
        return new HomePage(driver);
    }

    public OrderPageDateAndTime selectTimeAndGoBackToOrderPage(int time){
        selectTime(time);
        return new OrderPageDateAndTime(driver);
    }

    public void selectTime(int time){
        getTime(getTimeList(), time).click();
    }

    public TimePicker checkIfSelectedTimeMatcherThe(int time){
       Assert.assertThat(getTime(getTimeList(),time).getText(), containsString(time + ":"));
       return this;
    }

    private WebElement getTime(List<WebElement> webElements, int time){
        for(WebElement element : webElements){
            if(element.getText().startsWith(String.valueOf(time))){
                return element;
            }
        }
        throw new NullPointerException("Time was not found");
    }

    private List<WebElement> getTimeList(){
        return driver.findElements(timeSelects);
    }
}
