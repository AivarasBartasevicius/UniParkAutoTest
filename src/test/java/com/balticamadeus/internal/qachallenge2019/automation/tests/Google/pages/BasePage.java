package com.balticamadeus.internal.qachallenge2019.automation.tests.Google.pages;

import com.balticamadeus.internal.qachallenge2019.automation.tests.Google.testconfig.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected final WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Config.DEFAULT_WAIT_TIME);
    }

    public BasePage(WebDriver driver, int waitTime){
        this.driver = driver;
        wait = new WebDriverWait(driver, waitTime);
    }

    public void open(String url){
        driver.get(Config.BASE_URL + url);
    }


}
