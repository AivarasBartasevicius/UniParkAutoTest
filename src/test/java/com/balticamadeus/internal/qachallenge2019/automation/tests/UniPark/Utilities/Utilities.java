package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Utilities {

    static final DecimalFormat formatter = new DecimalFormat("#.##");

    public static final int dayCorrection(int year, int month, int day){
        if(day < 1){
            if(day <= 0){
                day = 1;
            }
        }else if(day > 28){
            LocalDate date = LocalDate.of(year,month,1);
            if(day > date.getMonth().length(date.isLeapYear())){
                day = date.getMonth().length(date.isLeapYear());
            }
        }
        return day;
    }

    public static final double getFormatedDouble(double doubleForFormating){
        return Double.valueOf(formatter.format(doubleForFormating).replace(",", "."));
    }
}
