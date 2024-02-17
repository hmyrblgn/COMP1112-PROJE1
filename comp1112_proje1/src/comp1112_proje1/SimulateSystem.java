/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

/**
 *
 * @author humeyrabilgin
 */
public class SimulateSystem {
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        ArrayList<Subscriber> subscribers = new ArrayList<>();
        ArrayList<ServiceProvider> serviceProviders = new ArrayList<>();

        int total_minute[]=new int[50]; // to keep each subscribers total minute
        int total_message[]=new int[50]; // to keep each subscribers total message
        int total_internet[]=new int[50]; // to keep each subscribers total internet usage
        double total_spending[]=new double[50]; // to keep each subscribers total spending

        for(int i=0;i<50;i++){ // initiliazing with 0 arrays
            total_minute[i]=0;
            total_message[i]=0;
            total_internet[i]=0;
            total_spending[i]=0.0;
        }

        int age, discountRatio, p_id, s_id, caller_id, sender_id, reciever_id, minute, quantity, MB; // variables to get values from user
        double voiceCallCost, messagingCost, internetCost, usageLimit, amount_of_pay; // variables to get values from user
        String name; // variables to get values from user
        int choice; // variables to get values from user

        do { // Do-While LOOP FOR MENU SYSTEM!!
            System.out.println("1- Creating a new Service Provider");
            System.out.println("2- Create a new Subscriber");
            System.out.println("3- Voice Call: A subscriber calls another subscriber");
            System.out.println("4- Messaging: A subscriber sends a message to another subscriber");
            System.out.println("5- Internet: A subscriber connects to the Internet");
            System.out.println("6- Pay Invoice: A subscriber pays his/her invoice");
            System.out.println("7- Change ServiceProvider: A subscriber changes his/her provider");
            System.out.println("8- Change Limit: A subscriber changes his/her usage limit for the Invoice");
            System.out.println("9- List all Subscribers (show s_id, isActive, s_provider, invoice)");
            System.out.println("10- List all Service Providers (show p_id, p_name, all costs, and discount)");
            System.out.println("11- Exit");
            choice = scan.nextInt(); // Getting choice number from user
            
            if (choice == 1) {
                System.out.print("Enter the name of Service Provider: ");
                scan.nextLine();
                name = scan.nextLine();
                System.out.print("Enter voiceCallCost, messagingCost, internetCost, and discountRatio: ");
                voiceCallCost = scan.nextDouble();
                messagingCost = scan.nextDouble();
                internetCost = scan.nextDouble();
                discountRatio = scan.nextInt();
                
                ServiceProvider s1 = new ServiceProvider(name, voiceCallCost, messagingCost, internetCost, discountRatio);
                // Creating new ServiceProvider object with parameter that user entered
                
                if (!serviceProviders.contains(s1)) { // Checking if the ArrayList of ServiceProvider has the object which is created now!
                    serviceProviders.add(s1); // If it has not this object, we will ad to ArrayList
                } else {
                    System.out.println("Service Provider which you want to create has already been created!"); // else printing message
                }
            } 
            else if (choice == 2) {
                System.out.print("Enter the Subscriber name: ");
                scan.nextLine();
                name = scan.nextLine();
                System.out.print("Enter age, p_id, usageLimit: ");
                age = scan.nextInt();
                p_id = scan.nextInt();
                usageLimit = scan.nextDouble();

                boolean check=false; // To understand there is serviceprovider for value of p_id

                for(int i=0;i<serviceProviders.size();i++){ // This for to check there is an ServiceProvider which has p_id(user entered) value 
                    if(serviceProviders.get(i).getP_id()==p_id){ // If there is

                        Subscriber sub1=new Subscriber(name, age, serviceProviders.get(i), usageLimit); // Creating new Subscriber object

                        if(!subscribers.contains(sub1)){ // Checking there is same Subscriber in ArrayList

                            subscribers.add(sub1); // if there is no subscriber, add to ArrayList
                            serviceProviders.get(i).addSubscriber(sub1); // Adding Subscriber to ArrayList which is in ServiceProvider class;
                            // This is important!!

                        } 
                        else{ 
                            // if there is printing message
                            System.out.println("Subscriber which you want to create has already been created!");
                        }
                        
                        check=true; 
                        break;
                    }
                }
                if(check==false){ // There is no serviceProvider for p_id value
                    System.out.println("There is no Service Provider for that p_id number: "+p_id);
                }
                
            } 
            else if (choice == 3) {
                System.out.println("Enter the <caller's s_id>: ");
                caller_id = scan.nextInt();
                System.out.println("Enter the <reciever's s_id>: ");
                reciever_id = scan.nextInt();
                System.out.println("Enter the time in minutes: ");
                minute = scan.nextInt();

                boolean check = false; // To check id numbers are valid or not

                for (int i = 0; i < subscribers.size(); i++) { // To check sender_id is valid or not
                    if (subscribers.get(i).getS_id() == caller_id) { // If there is caller_id in ArrayList<Subscriber>, we need to check reciver_id
                        for (int j = 0; j < subscribers.size(); j++) { // To check there is reciever_id in ArrayList<Subscriber>
                            if (subscribers.get(j).getS_id() == reciever_id) { 
                                
                                double temp_spending=subscribers.get(i).getInvoice().getCurrentSpending(); // for caller to keep old spending value
                                double temp_spending2=subscribers.get(j).getInvoice().getCurrentSpending(); // For reciever to keep old spending value
                                // If there is makeVoiceCall
                                subscribers.get(i).makeVoiceCall(minute, subscribers.get(j)); // Sender is charged
                                subscribers.get(j).makeVoiceCall(minute, subscribers.get(i)); // Reciever is charged

                                

                                total_minute[i]+=minute; // Calculating total minute for sender
                                total_minute[j]+=minute; // Calculating total minute for reciever
                                total_spending[i]+=subscribers.get(i).getInvoice().getCurrentSpending()-temp_spending; // Calculating total spending for caller
                                total_spending[j]+=subscribers.get(j).getInvoice().getCurrentSpending()-temp_spending2; // Calculating total spending for reciever
                                
                                check = true;
                                break;

                            }
                        }
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("Caller or reciever id is wrong!");
                }
            } 
            else if (choice == 4) {
                System.out.println("Enter the <sender's s_id>: ");
                sender_id = scan.nextInt();
                System.out.println("Enter the <reciever's s_id>: ");
                reciever_id = scan.nextInt();
                System.out.println("Enter the quantity of messages: ");
                quantity = scan.nextInt();
                
                boolean check = false; // To check id numbers are valid or not
                
                for (int i = 0; i < subscribers.size(); i++) { // To check sender id is valid or not
                    if (subscribers.get(i).getS_id() == sender_id) {// If there is caller_id in ArrayList<Subscriber>, we need to check reciver_id
                        for (int j = 0; j < subscribers.size(); j++) {  // To check there is reciever_id in ArrayList<Subscriber>
                            if (subscribers.get(j).getS_id() == reciever_id) {
 
                                double temp_spending=subscribers.get(i).getInvoice().getCurrentSpending(); // for sender to keep old current spending value

                                // If there is sendMessages do it
                                subscribers.get(i).sendMessage(quantity, subscribers.get(j));
                                total_message[i]+=quantity;

                                total_spending[i]+=subscribers.get(i).getInvoice().getCurrentSpending()-temp_spending; // Calculating total minute for sender
                                check = true;
                                break;
                                
                            }
                        }
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("Sender or reciever id is wrong!");
                }
            } 
            else if (choice == 5) {
                System.out.println("Enter the <s_id>: ");
                sender_id = scan.nextInt();
                System.out.println("Enter the amount in MBs: ");
                MB = scan.nextInt();
                
                boolean check = false;
                for (int i = 0; i < subscribers.size(); i++) { // Checking if there is s_id or not in ArrayList<Subscriber>
                    if (subscribers.get(i).getS_id() == sender_id) {

                        double temp_spending=subscribers.get(i).getInvoice().getCurrentSpending(); // for sender's currentspending before connect internet

                        subscribers.get(i).connectToInternet(MB); // Connecting Internet

                        total_spending[i]+=subscribers.get(i).getInvoice().getCurrentSpending()-temp_spending; // Calculating total spending for user
                        total_internet[i]+=MB; // Calculating total internet usage for subscriber who want to connect internet
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("s_id is wrong!");
                }
            } 
            else if (choice == 6) {
                System.out.println("Enter the <s_id>: ");
                sender_id = scan.nextInt();
                System.out.println("Enter the amount of the pay: ");
                amount_of_pay = scan.nextInt();
                
                boolean check = false;
                for (int i = 0; i < subscribers.size(); i++) {  // Checking if there is s_id or not in ArrayList<Subscriber>
                    if (subscribers.get(i).getS_id() == sender_id) {
                        subscribers.get(i).getInvoice().pay(amount_of_pay); // Paying the invoice
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("s_id is wrong!");
                }
            } 
            else if (choice == 7) {
                System.out.println("Enter the <s_id>: ");
                sender_id = scan.nextInt();
                System.out.println("Enter the <p_id of new service_provider> ");
                p_id = scan.nextInt();
                
                boolean check = false;
                for (int i = 0; i < subscribers.size(); i++) {  // Checking if there is s_id or not in ArrayList<Subscriber>
                    if (subscribers.get(i).getS_id() == sender_id) {
                        for (int j = 0; j < serviceProviders.size(); j++) {  // Checking if there is p_id or not in ArrayList<ServiceProvider>
                            if (serviceProviders.get(j).getP_id() == p_id) {

                                // Indexes are important, calling changeServiceProvider method.
                                subscribers.get(i).changeServiceProvider(serviceProviders.get(j)); 

                                serviceProviders.get(j).addSubscriber(subscribers.get(i)); 
                                // Adding Subscriber to ArrayList which is in the Service Provider Class
                                // This is important
                                check = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("<s_id or p_id> is wrong!");
                }
            } 
            else if (choice == 8) {
                System.out.println("Enter the <s_id>: ");
                sender_id = scan.nextInt();
                System.out.println("Enter the <new limit> ");
                usageLimit = scan.nextInt();
                boolean check = false;
                for (int i = 0; i < subscribers.size(); i++) { // Checking if there is s_id or not in ArrayList<Subscriber>
                    if (subscribers.get(i).getS_id() == sender_id) {
                        subscribers.get(i).getInvoice().changeUsageLimit(usageLimit);
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("s_id is wrong!");
                }
            }
            else if(choice==9){
                System.out.println(subscribers);
            }
            else if(choice==10){
                System.out.println(serviceProviders);
            }
            else if(choice==11){
                for(int i=0;i<serviceProviders.size();i++){ // this loop to calculate total message, minute and internet for each service providers.
                    int total_voice_call=0;
                    int total_number_of_messages=0;
                    int total_mbs_of_internet=0;
                    for(int j=0;j<subscribers.size();j++){
                        if(serviceProviders.get(i)==subscribers.get(j).getS_provider()){ 
                            // service provider equal to service provider which is in subscriber class
                            total_voice_call+=total_minute[j];
                            total_number_of_messages+=total_message[j];
                            total_mbs_of_internet+=total_internet[j];
                        }
                    }
                    System.out.println(serviceProviders.get(i).getP_id()+" "+total_voice_call+" "+total_number_of_messages+" "+total_mbs_of_internet);
                }
                
                int max_call=0; // to get max minute
                double max_mb=0.0; // to get max internet
                int max_message=0; // to get max message
                for(int i=0;i<subscribers.size();i++){
                    System.out.println(subscribers.get(i).getS_id()+": "+total_spending[i]+", "+subscribers.get(i).getInvoice().getCurrentSpending());
                }
                int j=0;
                int index1=0,index2=0,index3=0;
                while(j<50){ // while loop to find max values 
                    if(total_minute[j]>max_call){
                        max_call=total_minute[j];
                        index1=j; // i need to keep index values because i will print informations from ArrayList
                    }
                    if(total_message[j]>max_message){
                        max_message=total_message[j];
                        index2=j; // i need to keep index values because i will print informations from ArrayList
                    }
                    if(total_internet[j]>max_mb){
                        max_mb=total_internet[j];
                        index3=j; // i need to keep index values because i will print informations from ArrayList
                    }
                    j++;
                }
                System.out.println(subscribers.get(index1).getS_id()+" "+subscribers.get(index1).getName()+" "+max_call);
                System.out.println(subscribers.get(index2).getS_id()+" "+subscribers.get(index2).getName()+" "+max_message);
                System.out.println(subscribers.get(index3).getS_id()+" "+subscribers.get(index3).getName()+" "+max_mb);



            }
            else{
                System.out.println("\nYou typed incorrectly!");
            }
        } while (choice != 11);
    }
}
