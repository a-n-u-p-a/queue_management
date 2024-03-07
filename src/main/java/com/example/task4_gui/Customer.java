package com.example.task4_gui;      //importing necessary packages

public class Customer {
    private String firstName;           //Declaring variables
    private String lastName;
    private int Serving;

   public Customer(String firstName, String lastName, int Serving){
    this.firstName = firstName;                                         //Initialize firstname,lastname and serving for each customer object
    this.lastName = lastName;
    this.Serving = Serving;
   }

   public String getFirstname(){
        return firstName;
   }

   public String getLastname(){
        return lastName;                        //return each customer object attribute regarding the requirement
   }

   public int getServing(){
        return Serving;
   }

}