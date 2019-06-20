package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages;

import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Components.DatePicker;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Components.TimePicker;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.AirPort;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.PickerType;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomConditions.HasChanged;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.Utilities.getFormatedDouble;
import static org.hamcrest.CoreMatchers.is;

public class OrderPageDateAndTime extends BasePage {

    public static final int HOURS_IN_DAY = 24;

    private AirPort airPort = AirPort.VILNIAUS;
    private By airportDropList = By.cssSelector(".zone-select-div .dropas");

    private By carNumber = By.id("nr");

    private By infoContainer = By.cssSelector("#discount-pop");
    private By infoContainerCloseButton = By.cssSelector("#discount-pop button");

    //value may changes inside changeAirport method
    private By parkingZonesWrapper = By.cssSelector("[data-master='" + airPort.getDataValue()+ "'] > table");
    private By parkingZonesWrapperTotalPrice = By.cssSelector("[data-master='" + airPort.getDataValue()+ "'] > table .coll-4");
    //---
    private By parkingZoneTotalPrice = By.cssSelector(".coll-4");
    private By parkingZoneSubmitButton = By.cssSelector(".coll-5 a");
    private By parkingZonesHourAndDayPrices = By.cssSelector(".blue-2");

    private By parkingLengthDays = By.id("step_1_duration_days");
    private By parkingLengthHours = By.id("step_1_duration_hours");


    private int parkingLengthInHours = 0;
    private double hourPrice, dayPrice, totalPrice;
    private String prices[];

    public OrderPageDateAndTime(WebDriver driver){
        super(driver);
    }
    public DatePicker openDatePicker(PickerType type){
        return new DatePicker(driver, wait, type);
    }

    public TimePicker openTimePicker(PickerType type){
        return new TimePicker(driver, wait, type);
    }

    //ToDo: refactor this into component, maybe
    public OrderPageDateAndTime inputCarNumber(String carNumber){
        driver.findElement(this.carNumber).sendKeys(carNumber);
        return this;
    }

    public OrderPageDateAndTime changeAirport(AirPort airport){
        WebElement airportElement = driver.findElement(By.id(airport.getIdValue()));
        if(!airportElement.isDisplayed()){
            driver.findElement(airportDropList).click();
            airPort = airport;
            parkingZonesWrapper = By.cssSelector("[data-master='" + airPort.getDataValue()+ "'] > table");
            parkingZonesWrapperTotalPrice = By.cssSelector("[data-master='" + airPort.getDataValue()+ "'] > table .coll-4");
            return this;
        }
        airPort = airport;
        airportElement.click();

        return this;
    }

    public OrderPageOrderDetails selectParkingZone(){
        List<WebElement> parkingZones = driver.findElements(parkingZonesWrapper);

        for(WebElement element : parkingZones){
            if(driver.findElements(infoContainer).size()<1) {
                WebElement siteTotalPrice = element.findElement(parkingZoneTotalPrice);
                wait.until(HasChanged.byElement(element,"0,00€"));
                element.findElement(parkingZoneSubmitButton).click();
                return new OrderPageOrderDetails(driver, wait, getSiteTotalPriceValue(siteTotalPrice), airPort);
            }
            else {
                driver.findElement(infoContainerCloseButton).click();
            }
        }

        System.out.println("No awailable parking zones");
        return null;
    }


    public OrderPageDateAndTime checkIfDurationIsCorrect(int durationInHours){
        new WebDriverWait(driver,5).until(
                ExpectedConditions.and(
                        ExpectedConditions.textToBe(parkingLengthDays,durationInHours/HOURS_IN_DAY + " d."),
                        ExpectedConditions.textToBe(parkingLengthHours,durationInHours%HOURS_IN_DAY + " val."))
        );
        return this;
    }

    public OrderPageDateAndTime checkParkingZonePrices(){
        wait.until(HasChanged.bySelector(parkingZonesWrapperTotalPrice,"0,00€"));
        List<WebElement> parkingZones = driver.findElements(parkingZonesWrapper);
        parkingZones.forEach(element->
                Assert.assertThat("Checks if parcking prices match",isParkingPriceCorrect(element), is(true))
        );
        return this;
    }

    private boolean isParkingPriceCorrect(WebElement element){
        WebElement price = element.findElement(parkingZonesHourAndDayPrices);
        WebElement siteTotalPrice = element.findElement(parkingZoneTotalPrice);
        getParkingDuration();

        if(!price.getText().equals("")) {
            getDayAndHourPrices(price);
            getSiteTotalPriceValue(siteTotalPrice);
            if(parkingLengthInHours > 1 && parkingLengthInHours % HOURS_IN_DAY == 0){
                return parkingDayPrice();
            }else if(parkingLengthInHours > 1){
                return parkingDayAndHourPrice();
            }else{
                return hourPrice == totalPrice;
            }
        }
        //ToDo: log that price was not found
        System.out.println("Price for one of the items was not found");
        return true;
    }

    private void getDayAndHourPrices(WebElement price){
        prices = StringUtils.substringsBetween(price.getText(), "-", "€");
        hourPrice = Double.valueOf(prices[0].replace(" ", ""));
        dayPrice = Double.valueOf(prices[1].replace(" ", ""));
    }

    private double getSiteTotalPriceValue(WebElement siteTotalPrice){
        return totalPrice = Double.valueOf(siteTotalPrice.getText().replace(",","."));
    }

    private int getParkingDuration(){
        int days = 0;
        int hours = 0;
        WebElement elementDays = driver.findElement(parkingLengthDays);
        WebElement elementHours = driver.findElement(parkingLengthHours);

        if(!elementDays.getText().equals("")) {
           days = Integer.parseInt(elementDays.getText().replace(" " + elementDays.getAttribute("data-prefix"), ""));
        }
        if(!elementHours.getText().equals("")) {
            hours = Integer.parseInt(elementHours.getText().replace(" " + elementHours.getAttribute("data-prefix"), ""));
        }
        return parkingLengthInHours = days * HOURS_IN_DAY + hours;
    }

    private boolean parkingDayPrice(){
        return getFormatedDouble(parkingLengthInHours/HOURS_IN_DAY * dayPrice) == totalPrice ||
                getFormatedDouble(((parkingLengthInHours/HOURS_IN_DAY * dayPrice) * 0.75)) == totalPrice;
    }

    private boolean parkingDayAndHourPrice(){
        return  getFormatedDouble(Double.valueOf((parkingLengthInHours/HOURS_IN_DAY)+1)*dayPrice) == totalPrice
                || getFormatedDouble(Double.valueOf((parkingLengthInHours/HOURS_IN_DAY))*dayPrice) + getFormatedDouble(parkingLengthInHours%HOURS_IN_DAY*hourPrice) == totalPrice
                || getFormatedDouble(Double.valueOf((parkingLengthInHours/HOURS_IN_DAY)+1)*dayPrice) * 0.75 == totalPrice
                || getFormatedDouble(Double.valueOf((parkingLengthInHours/HOURS_IN_DAY))*dayPrice) + getFormatedDouble(parkingLengthInHours%HOURS_IN_DAY*hourPrice) *0.75 == totalPrice;
    }


    private boolean parkingHourPrice(){
        return  getFormatedDouble(hourPrice) == totalPrice;
    }
}
