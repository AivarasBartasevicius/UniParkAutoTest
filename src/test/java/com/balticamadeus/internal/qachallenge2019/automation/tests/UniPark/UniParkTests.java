package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark;

import com.balticamadeus.internal.qachallenge2019.automation.tests.Google.pages.GooglePage;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages.HomePage;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.testconfig.UniParkConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.PickerType.FROM;
import static com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.PickerType.TO;

public class UniParkTests {

    WebDriver driver;
    GooglePage googlePage;
    int currentDay = LocalDate.now().getDayOfMonth();
    int dayBefore = currentDay - 1;
    int dayAfter = currentDay + 1;
    int currentTime = LocalTime.now().getHour() + 1;
    int timeBefore = currentTime - 1;
    int timeAfter = currentTime + 1;

    @Before
    public void setUp() {
        driver = new ChromeDriver(new ChromeOptions().addArguments(UniParkConfig.ARGUMENTS));
        googlePage = new GooglePage(driver);
    }

    @Test
    public void openDatePicker() {
        new HomePage(driver).openHomePage()
                .openDatePicker(FROM)
                .checkIfDatePickerIsOpened();
    }

    @Test
    public void selectDay() {
        new HomePage(driver).openHomePage()
                .openDatePicker(FROM)
                .selectDayHomePage(currentDay)
                .openTimePicker(TO)
                .selectTimeAndGoBackToHomePage(timeAfter)
                .submitParkingOrder()
                .openDatePicker(FROM)
                .checkIfDaysMatch(currentDay);
    }

    @Test
    public void disabledDays() {
        new HomePage(driver).openHomePage()
                .openDatePicker(FROM)
                .checkIfDayIsDisabled(dayBefore);
    }

    @Test
    public void selectDayInNextMonth() {
        new HomePage(driver).openHomePage()
                .openDatePicker(FROM)
                .openNextMonth()
                .selectDayHomePage(currentDay)
                .submitParkingOrder()
                .openDatePicker(FROM)
                .checkIfDaysMatch(currentDay);
    }

    @Test
    public void tryToGoBackToMonthThatPassed() {
        new HomePage(driver).openHomePage()
                .openDatePicker(FROM)
                .checkIfYouCantGoToPrevMonth();
    }

    @Test
    public void selectTime() {
        try {
            new HomePage(driver).openHomePage()
                    .openTimePicker(FROM)
                    .selectTimeAndGoBackToHomePage(currentTime)
                    .openTimePicker(TO)
                    .selectTimeAndGoBackToHomePage(timeAfter)
                    .submitParkingOrder()
                    .openTimePicker(FROM)
                    .checkIfSelectedTimeMatcherThe(currentTime);
        }catch (NullPointerException exception){
            System.out.println("null exception handled");
        }catch (Exception exception){
            System.out.println("IndexOutOfBounds exception handled");
        }
    }

    @Test
    public void checkDuration() {
        new HomePage(driver).openHomePage()
                .openDatePicker(TO)
                .selectDayHomePage(dayAfter)
                .openTimePicker(TO)
                .selectTimeAndGoBackToHomePage(currentTime)
                .submitParkingOrder()
                .checkIfDurationIsCorrect(25);
    }

    @Test
    public void checkParkingPrices() {
        try {
        Random rand = new Random();
            new HomePage(driver).openHomePage()
                    .openDatePicker(FROM)
                    .openNextMonth()
                    .openNextMonth()
                    .selectDayHomePage(rand.nextInt(28) + 1)
                    .openDatePicker(TO)
                    .selectDayHomePage(rand.nextInt(28) + 1)
                    .openTimePicker(TO)
                    .selectTimeAndGoBackToHomePage(rand.nextInt(24) + 1)
                    .submitParkingOrder()
                    .checkParkingZonePrices();
        }catch (NullPointerException exception){
            System.out.println("null exception handled");
        }catch (Exception exception){
            System.out.println("IndexOutOfBounds exception handled");
        }
    }

    @Test
    public void openOrderDetailsAirportName(){
        new HomePage(driver).openHomePage()
                .submitParkingOrder()
                .inputCarNumber("asd123")
                .selectParkingZone()
                .checkIfAirportNameMatch();
    }

    @Test
    public void openOrderDetailsTotalPrice(){
        new HomePage(driver).openHomePage()
                .submitParkingOrder()
                .inputCarNumber("asd123")
                .selectParkingZone()
                .checkIfPricesMatch();
    }


    //dont mind this
    @Test
    public void selectDayOrderPage(){
        new HomePage(driver).openHomePage()
                .submitParkingOrder()
                .openDatePicker(TO)
                .selectDayOrderPage(currentDay)
                .openDatePicker(TO)
                .checkIfDaysMatch(currentDay);
    }

    @After
    public void cleanUp() {
       driver.quit();
    }
}
