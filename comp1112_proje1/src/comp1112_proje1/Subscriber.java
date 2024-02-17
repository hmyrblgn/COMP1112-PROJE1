/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.*;
import java.util.Random;

public class Subscriber {

    private int s_id;
    private String name;
    private int age;
    private boolean isActive;
    private ServiceProvider s_provider;
    private Invoice invoice;

    public Subscriber(String name, int age, ServiceProvider s1, double usageLimit){

        Random rand = new Random();
        this.s_id = rand.nextInt(1000000) + 8000000; // Assigning random values between (1000000 - 9000000)
        this.name = name;
        this.age = age;
        this.isActive=true;
        this.s_provider=s1;
        Date date=new Date(); // Creating current time date
        invoice = new Invoice(usageLimit, date); // Sending current time to invoice class as a parameter

    }

    public int getS_id() {
        return s_id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public ServiceProvider getS_provider() {
        return s_provider;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setS_provider(ServiceProvider s_provider) {
        this.s_provider = s_provider;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void updateStatus() {
        Date d1=new Date(); //Current Time
        if (d1.before(invoice.getLastDayToPay()) == false && invoice.getCurrentSpending() != 0) { //
            isActive = false;
        } else {
            isActive = true;
        }

    }

    public void makeVoiceCall(int minute, Subscriber receiver) {
        if (this.isActive == true && receiver.isActive == true) {
            double totalCost = s_provider.calculateVoiceCallCost(minute, this);
            if (invoice.getUsageLimit() - invoice.getCurrentSpending() >= totalCost) {
                invoice.addCost(totalCost); //toplam harcamamı += yaparak arttırıyor mesela
                System.out.println("Voice Call is sucessfully made!");
            } else {
                System.out.println("LIMIT EXCEEDED!");
            }
        }

    }

    public void sendMessage(int quantity, Subscriber receiver) {
        if (this.isActive == true && receiver.isActive == true) {
            double totalCost = s_provider.calculateMessagingCost(quantity, this, receiver); //this=sender bu obje
            if (invoice.getUsageLimit() - invoice.getCurrentSpending() >= totalCost) {
                invoice.addCost(totalCost); //toplam harcamamı += yaparak arttırıyor mesela
                System.out.println("Message(s) is sucessfully sent!");
            } else {
                System.out.println("LIMIT EXCEEDED!");
            }
        }
    }

    public void connectToInternet(double amount) {
        if (this.isActive == true) {
            double intCost = s_provider.calculateInternetCost(amount, this);
            if (invoice.getUsageLimit() - invoice.getCurrentSpending() >= intCost) {
                invoice.addCost(intCost); //toplam harcamamı += yaparak arttırıyor mesela
                System.out.println("You Connected to Internet!");
            } else {
                System.out.println("LIMIT EXCEEDED!");
            }
        }
    }

    public void changeServiceProvider(ServiceProvider s_provider) {
        invoice.pay(invoice.getCurrentSpending()); //şu anki faturayi odeyip kapatiyoruz
        this.s_provider = s_provider;
        System.out.println("Service Provider of this subscriber has been changed!");
    }

    
    public String toString() {
        return "s_id=" + s_id + ", name=" + name + ", age=" + age + ", isActive=" + isActive + ", invoice=" + invoice + "}\n";
    }

}
