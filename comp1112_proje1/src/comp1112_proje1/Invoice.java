/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.*;

/**
 *
 * @author humeyrabilgin
 */
public class Invoice {

    private double usageLimit;
    private double currentSpending;
    private Date lastDayToPay;

    public Invoice(double usageLimit, Date lastDayToPay) {
        this.usageLimit = usageLimit;
        this.lastDayToPay = lastDayToPay;
        this.lastDayToPay.setDate(lastDayToPay.getDate()+30);
        this.currentSpending = 0.0;
    }

    public Date getLastDayToPay() {
        return lastDayToPay;
    }

    public void setLastDayToPay(Date lastDayToPay) {
        this.lastDayToPay = lastDayToPay;
    }

    public boolean isLimitExceeded(double amount) { //aşım varsa false
        if (amount > usageLimit) {
            return false;
        }
        return true;

    }

    public void addCost(double amount) { //harcadığını faturaya ekliyorsun
        if (isLimitExceeded(currentSpending + amount)) {
            currentSpending += amount;
        }
    }

    public void pay(double amount) { //ödediğin faturadan çıkarıyorsun
        if(currentSpending>amount){
            currentSpending-=amount;
        } 
        else if(amount>currentSpending){
            System.out.println("You cannot pay a value greater than currentSpending");
        } 
        else{
            this.lastDayToPay.setDate(this.lastDayToPay.getDate()+30); // Resetting lastDayToPay
            currentSpending -= amount;
        }

    }

    public void changeUsageLimit(double amount) {
        usageLimit = amount;
    }

    public double getUsageLimit() {
        return usageLimit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public void setUsageLimit(double usageLimit) {
        this.usageLimit = usageLimit;
    }

    public void setCurrentSpending(double currentSpending) {
        this.currentSpending = currentSpending;
    }

    
    public String toString() {
        return "usageLimit=" + usageLimit + ", currentSpending=" + currentSpending + ", lastDayToPay=" + lastDayToPay + "}\n";
    }

}
