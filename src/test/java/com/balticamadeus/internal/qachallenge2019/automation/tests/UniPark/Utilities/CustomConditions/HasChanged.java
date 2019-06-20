package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;


public class HasChanged{

    public static ExpectedCondition<WebElement> bySelector(final By selector, final String oldText) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return hasElementChanged(driver.findElement(selector),oldText);
            }
        };
    }

    public static ExpectedCondition<WebElement> byElement(final WebElement element, final String oldText) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return hasElementChanged(element,oldText);
            }
        };
    }

    private static WebElement hasElementChanged(WebElement element,String oldText){
        return !element.getText().equals(oldText) ? element : null;
    }

}
