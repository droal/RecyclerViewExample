package com.droal.tipcalc.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aleydario on 1/04/17.
 */

public class TipRecordPOJO {

    private double bill;
    private Date timestamp;
    private float tipPercentage;


    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getTipPercentage() {
        return tipPercentage;
    }

    public void setTipPercentage(float tipPercentage) {
        this.tipPercentage = tipPercentage;
    }


    public double geTip(){
        return bill * (tipPercentage/100d);
    }

    public String getDateFormatted(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd,yyyy HH:mm");
        return simpleDateFormat.format(timestamp);
    }
}
