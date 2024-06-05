package com.example.rentalmanagementsystem.Entities;

import androidx.lifecycle.ViewModel;

import com.example.rentalmanagementsystem.util.IntervalType;
import com.example.rentalmanagementsystem.util.PaymentConstants;

public class BillingModel extends ViewModel {
    private String stayDuration = "0";
    private int costPerMonth = 0;
    private int totalCost = 0;
    private BillType selectedBillType = null;
    private IntervalType selectedIntervalType = null;

    public String getStayDuration() {
        return stayDuration;
    }

    public int getCostPerMonth() {
        return costPerMonth;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public BillType getSelectedBillType() {
        return selectedBillType;
    }

    public IntervalType getSelectedIntervalType() {
        return selectedIntervalType;
    }

    public void calculateBill() {
        int costPerDay;
        switch (selectedBillType) {
            case Electricity:
                costPerDay = PaymentConstants.ELECTRICITY_COST_PER_DAY;
                break;
            case Water:
                costPerDay = PaymentConstants.WATER_COST_PER_DAY;
                break;
            case Internet:
                costPerDay = PaymentConstants.INTERNET_COST_PER_DAY;
                break;
            default:
                costPerDay = 0;
                break;
        }

        costPerMonth = costPerDay * 30;

        int stayDurationVal;
        try {
            stayDurationVal = Integer.parseInt(stayDuration);
        }
        catch (NumberFormatException e) {
            stayDurationVal = 0;
        }

        int days;
        switch (selectedIntervalType) {
            case Month:
                days = 30 * stayDurationVal;
                break;
            case Year:
                days = 365 * stayDurationVal;
                break;
            case Day:
                days = stayDurationVal;
                break;
            case Week:
                days = 7 * stayDurationVal;
                break;
            default:
                days = 0;
                break;
        }

        totalCost = days * costPerDay;
    }


    public void onBillTypeSelected(BillType billType) {
        selectedBillType = billType;
        resetCosts();
    }

    public void onStayDurationChanged(String duration) {
        stayDuration = duration;
        resetCosts();
    }

    public void onIntervalTypeSelected(IntervalType intervalType) {
        selectedIntervalType = intervalType;
        resetCosts();
    }

    private void resetCosts() {
        costPerMonth = 0;
        totalCost = 0;
    }
}
