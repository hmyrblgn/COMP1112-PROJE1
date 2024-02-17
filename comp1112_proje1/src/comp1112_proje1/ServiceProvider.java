/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.Random;

public class ServiceProvider {

    private int p_id;
    private String p_name;
    private double voiceCallCost;
    private double messagingCost;
    private double internetCost;
    private int discountRatio;
    ArrayList<Subscriber> subscribersList;

    public ServiceProvider(String p_name, double voiceCallCost, double messagingCost, double internetCost, int discountRatio) {
        Random rand = new Random();
        this.p_id = rand.nextInt(100) + 500;
        this.p_name = p_name;
        this.voiceCallCost = voiceCallCost;
        this.messagingCost = messagingCost;
        this.internetCost = internetCost;
        this.discountRatio = discountRatio;
        this.subscribersList=new ArrayList<>();
    }


    public int getP_id() {
        return p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public double getVoiceCallCost() {
        return voiceCallCost;
    }

    public double getMessagingCost() {
        return messagingCost;
    }

    public int getDiscountRatio() {
        return discountRatio;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public void setVoiceCallCost(double voiceCallCost) {
        this.voiceCallCost = voiceCallCost;
    }

    public void setMessagingCost(double messagingCost) {
        this.messagingCost = messagingCost;
    }

    public void setDiscountRatio(int discountRatio) {
        this.discountRatio = discountRatio;
    }

    public double calculateVoiceCallCost(int minute, Subscriber caller) {
        double totalCost = minute * voiceCallCost;
        if (minute < 5 && (caller.getAge() < 18 && caller.getAge() > 10)) {
            return 0.0;
        } else if (minute >= 5 && caller.getAge() < 18 && caller.getAge() > 10) {
            totalCost -= (totalCost * discountRatio) / 100.0;

        }

        return totalCost;
    }

    public double calculateMessagingCost(int quantity, Subscriber sender, Subscriber receiver) {//parametre bunlar 

        double totalCost = quantity * messagingCost;
        if (sender.getAge() < 18 && sender.getAge() > 10 && receiver.getAge() < 18 && receiver.getAge() > 10 && quantity < 10) {
            return 0.0; //
        } else if (sender.getS_provider().equals(receiver.getS_provider())) {
            totalCost -= (totalCost * discountRatio) / 100.0; //100 de 5 ise totalde kaçtır ? // indirim orani uygulandi
        }

        return totalCost;
    }

    public double calculateInternetCost(double amount, Subscriber user) {
        double totalCost = amount * internetCost;
        if (user.getAge() < 18 && user.getAge() > 10 && amount < 5) {
            return 0.0;
        }
        return totalCost;
    }

    public boolean addSubscriber(Subscriber s) {
        for (int i = 0; i < subscribersList.size(); i++) {
            Subscriber currentSubscriber = subscribersList.get(i);
            if (currentSubscriber.equals(s)) { //s gelen parametre
                System.out.println("This subscribe is already subscribed...");
                return false;
            }
        }
        subscribersList.add(s);
        System.out.println("The subscriber is sucessfully added.");
        return true;
    }

    public boolean removeSubscriber(Subscriber s) {

        for (int i = 0; i < subscribersList.size(); i++) {
            Subscriber currentSubscriber = subscribersList.get(i);
            if (currentSubscriber.equals(s)) { //s gelen parametre

                subscribersList.remove(s);
                System.out.println("The user has been deleted.");

                return true;
            }
        }
        System.out.println("This user is not in ArrayList.");
        return false;
    }

    
    public String toString() {
        return "p_id=" + p_id + ", p_name=" + p_name + ", voiceCallCost=" + voiceCallCost + ", messagingCost=" + messagingCost + ", internetCost=" + internetCost + ", discountRatio=" + discountRatio + ", subscribersList=" + subscribersList + "}\n";
    }

}
