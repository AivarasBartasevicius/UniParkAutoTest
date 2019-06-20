package com.balticamadeus.internal.qachallenge2019.automation.tests.UniPark.Utilities.CustomEnums;

public enum AirPort {
    VILNIAUS("0", "2"),
    KAUNO("1", "6"),
    RYGOS("2", "7");

    private final String idValue;
    private final String dataValue;

    AirPort(String value, String dataValue) {
        this.idValue = value;
        this.dataValue = dataValue;
    }

    public String getIdValue() {
        return idValue;
    }

    public String getDataValue() {
        return dataValue;
    }

    public static AirPort findAiportById(String id){
        for(AirPort temp :AirPort.values()){
            if(temp.getIdValue() == id){
                return temp;
            }
        }
        return VILNIAUS;
    }
}
