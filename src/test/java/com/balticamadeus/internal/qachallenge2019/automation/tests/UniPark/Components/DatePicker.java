package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Components;

import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.PickerType;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.Utilities;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages.HomePage;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages.OrderPageDateAndTime;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;

public class DatePicker {

    private WebDriver driver;
    private WebDriverWait wait;

    private By datePickerLocation = By.id("ui-datepicker-div");

    private By datePickerTo = By.id("time_to");
    private By datePickerFrom = By.id("time_from");

    private By selectedDay = By.cssSelector("[data-handler='selectDay'] .ui-state-active");
    private By selectableDay = By.cssSelector("[data-handler='selectDay']");

    private By nextMonth = By.cssSelector(".ui-datepicker-next");
    private By prevMonth = By.cssSelector(".ui-datepicker-prev");

    private By dateAndYear = By.cssSelector("[data-handler='selectDay']");

    private String isDisabledString = "ui-state-disabled";

    public DatePicker(WebDriver driver, WebDriverWait wait, PickerType type) {
        this.driver = driver;
        this.wait = wait;
        switch (type) {
            case FROM:
                driver.findElement(datePickerFrom).click();
                break;
            case TO:
                driver.findElement(datePickerTo).click();
                break;
        }
    }

    public HomePage selectDayHomePage(int day) {
        selectDay(day);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(datePickerLocation));
        return new HomePage(driver);
    }

    public OrderPageDateAndTime selectDayOrderPage(int day) {
        selectDay(day);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(datePickerLocation));
        return new OrderPageDateAndTime(driver);
    }

    private void selectDay(int day) {
        WebElement elementDay = getDayElement(day);
        WebElement Parent = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", elementDay);
            if (isDisabled(Parent, isDisabledString)) {
            System.out.println("Element was disabled, and instead the first selectable day was chosen");
            driver.findElement(selectableDay).click();
        }
        Parent.click();
    }

    public DatePicker openPreviousMonth() {
        WebElement prevMonthButton = driver.findElement(prevMonth);
        if (isDisabled(prevMonthButton, isDisabledString)) {
            //ToDo: log that it was imposible to go to prev month
            System.out.println("The prev month button is disabled");
        } else {
            prevMonthButton.click();
        }
        return this;
    }

    public DatePicker openNextMonth() {
        driver.findElement(nextMonth).click();
        return this;
    }


    public DatePicker checkIfDaysMatch(int day) {
        day = Utilities.dayCorrection(getYear(), getMonth(), day);
        Assert.assertThat(driver.findElement(selectedDay).getText(), is(String.valueOf(day)));
        return this;
    }

    public DatePicker checkIfDatePickerIsOpened() {
        Assert.assertTrue(driver.findElement(datePickerLocation).isDisplayed());
        return this;
    }

    public DatePicker checkIfDayIsDisabled(int day) {
        day = Utilities.dayCorrection(getYear(), getMonth(), day);
        if (LocalDate.now().getDayOfMonth() == 1) {
            System.out.println("All days are enebled becouse it's the first day of the month");
        } else {
            WebElement Parent = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", getDayElement(day));
            Assert.assertTrue(isDisabled(Parent, isDisabledString));
        }
        return this;
    }

    public DatePicker checkIfYouCantGoToPrevMonth() {
        Assert.assertTrue(isDisabled(driver.findElement(prevMonth), isDisabledString));
        return this;
    }

    private boolean isDisabled(WebElement element, String disableString) {
        //ToDo: warning log that element was disabled
        System.out.println("Element was disabled");
        return element.getAttribute("class").contains(disableString);
    }


    private WebElement getDayElement(int day) {
        day = Utilities.dayCorrection(getYear(), getMonth(), day);
        return driver.findElement(By.xpath("//*[@class='ui-datepicker-calendar'] //*[text()='" + day + "']"));
    }

    private int getMonth() {
        WebElement dateYearElement = driver.findElement(dateAndYear);
        return Integer.parseInt(dateYearElement.getAttribute("data-month"));
    }

    private int getYear() {
        WebElement dateYearElement = driver.findElement(dateAndYear);
        return Integer.parseInt(dateYearElement.getAttribute("data-year"));
    }

    public DatePicker openDatePicker(PickerType type) {
        return new DatePicker(driver, wait, type);
    }
}
