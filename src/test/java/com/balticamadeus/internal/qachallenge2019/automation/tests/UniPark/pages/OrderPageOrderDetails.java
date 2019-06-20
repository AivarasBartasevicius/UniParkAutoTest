package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages;

import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums.AirPort;
import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomConditions.HasChanged;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class OrderPageOrderDetails {

    WebDriverWait wait;
    WebDriver driver;

    private final boolean detailsGiven;
    private double price;
    private AirPort airPort;

    private By airposrtName = By.id("place_name");

    private By optionalPrices = By.id("coll_5");

    private By siteTotalPrice = By.id("step_2_price");

    public OrderPageOrderDetails(WebDriver driver, WebDriverWait wait, double price, AirPort airport){
        detailsGiven = true;
        this.price = price;
        this.airPort = airport;
        this.driver = driver;
        this.wait = wait;
    }

    public OrderPageOrderDetails(WebDriver driver, WebDriverWait wait){
        detailsGiven = false;
        this.driver = driver;
        this.wait = wait;
    }

    public OrderPageOrderDetails checkIfAirportNameMatch(){
        if(detailsGiven) {
            ifAirportNameMatch(airPort.toString());
        }else {
            System.out.println("To use this medhod you need to call constructor with Airport value or call method with String criteria value");
        }
        return this;
    }

    public OrderPageOrderDetails checkIfAirportNameMatch(String criteria){
        ifAirportNameMatch(criteria);
        return this;
    }

    private void ifAirportNameMatch(String criteria){
        wait.until(HasChanged.bySelector(airposrtName, ""));
        Assert.assertThat(driver.findElement(airposrtName).getText().toLowerCase(), containsString(criteria.toLowerCase()));

    }

    public OrderPageOrderDetails checkIfPricesMatch(){
        if(detailsGiven) {
            System.out.println("price: " + price);
        Assert.assertThat(getSiteTotalPrice(),is(price+getExtraPrices()));
        }else {
            System.out.println("To use this medhod you need to call constructor with Airport value or call method with String criteria value");
        }
        return this;
    }

    public OrderPageOrderDetails checkIfPricesMatch(double criteria){
        Assert.assertThat(getSiteTotalPrice(), is(criteria));
        return this;
    }

    private double getSiteTotalPrice(){
        wait.until(HasChanged.bySelector(siteTotalPrice, ""));
        return Double.valueOf(driver.findElement(siteTotalPrice).getText().replace("€","").replace(" ", ""));
    }

    private double getExtraPrices(){
        double totalPrice = 0;
        for(WebElement element : driver.findElements(optionalPrices)){
            if (element.getText().length()>2) {
                totalPrice += Double.valueOf(element.getText().substring(0,element.getText().length()-2));
                totalPrice += Double.valueOf(element.findElement(By.cssSelector("sup")).getText())/100;
            }

        }
        return totalPrice;
    }

}
