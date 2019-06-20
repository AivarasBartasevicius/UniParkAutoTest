package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums;

import org.openqa.selenium.By;

public enum Input {
    //input by selector if i decide to finish the third page
    NAME(By.id("firstname1")),
    SURNAME(By.id("lastname1")),
    CAR_NUMBER(By.id("vehicle_number1")),
    PHONE_NUMBER(By.id("phone_number1")),
    MAIL(By.id("email1"));

    By selector;

    Input(By selector){
        this.selector = selector;
    }

    public By getIdValue(){
        return selector;
    }
}
