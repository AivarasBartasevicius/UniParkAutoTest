package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.pages;

import com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.testconfig.UniParkConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, UniParkConfig.DEFAULT_WAIT_TIME);
    }

    protected void open(String url){
        driver.get(UniParkConfig.BASE_URL + url);
    }
}
