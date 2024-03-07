package com.example.task4_gui;  //importing necessary packages


public class FoodQueue {

    private Customer[] customerQueue;       //Declaring variables and object arrays
    private int front;
    private int rear;



    public FoodQueue(int queueSize) {
        this.customerQueue = new Customer[queueSize];       //Initialize rear and front indexes together with queuesize
        this.front = -1;
        this.rear = -1;
    }
    public int length() {
        return customerQueue.length;
    }       //Returns queue length

    public void addCustomer(Customer customer, int index) {
        customerQueue[index] = customer;                      //Customer objects are added
    }


    public String[] searchCustomer(String firstname){
        for(int i = 0; i < customerQueue.length; i++){
            if((customerQueue[i] != null) && firstname.equals(customerQueue[i].getFirstname())){
                String[] qArray = new String[5];
                qArray[0] = customerQueue[i].getFirstname();            //Search for a customer object that matches the firstname string
                qArray[1] = customerQueue[i].getLastname();             //String firstname is given as a parameter
                qArray[2] = customerQueue[i].getServing() + "";         //firstname is checked in each queue by looping
                qArray[3] = (i + "");                                   //if found returns a string array with attributed that belongs to that customer object
                if(customerQueue.length == 2) qArray[4] = "1";          //if not found returns a null value
                if(customerQueue.length == 3) qArray[4] = "2";
                if(customerQueue.length == 5) qArray[4] = "3";

                return qArray;
            }
        }
        return null;
    }

    public String[] searchCQCustomer(String firstname){
        if (!(front == -1)) {
            for (int i = 0; i < customerQueue.length; i++) {
                if (customerQueue[i] != null && firstname.equals(customerQueue[i].getFirstname())) {
                    String[] stringArr = new String[5];
                    stringArr[0] = customerQueue[i].getFirstname();         //Does the same action as above method but for the waiting queue
                    stringArr[1] = customerQueue[i].getLastname();
                    stringArr[2] = customerQueue[i].getServing() + "";
                    stringArr[3] = (i + "");
                    stringArr[4] = ("waiting queue");
                    return stringArr;
                }
            }
        }
        return null;
    }

    public Customer getCustomer(int index) {                        //Used to get a customer object in a certain position
        if (index >= 0 && index < customerQueue.length) {           //An index for an array object is provided as a parameter
            if (customerQueue[index] != null) {                     //If no customer is found, null will be returned
                return customerQueue[index];
            }
        }return null;
    }


    public void setCustomer(int index) {
        customerQueue[index] = customerQueue[index+1] ;     //Used to make a customer object at one position equal to another customer object
    }                                                       //Index is provided as a parameter and the customer object is set to it's (index+1) customer object

    public void setnullCustomer(int index) {
        customerQueue[index] = null ;               //Makes a customer object as a certain position to a null value(like removing that customer)
    }                                               //Index is given as a parameter


    public boolean isNull(int index) {
        boolean status = false;
        if (customerQueue[index] == null) {             //Checks if a customer object at a certain position is null or not null
            status = true;                              //If there's an object(not null), true will be returned, else false will be returned
        }else if(customerQueue[index] != null) {        //index is provided as a parameter for the position(to check customer position)
            status = false;
        }return status;
    }

    public boolean chkCQ(){
        if (front == -1) {                      //Checks if the circular is empty or not
            return false;                       //If empty, returns false, if not returns true
        }else return true;
    }

    public void enqueue(Customer lateCustomer) {
        if ((front == 0 && rear == 5 - 1) || (rear == (front - 1) % (5 - 1))) {
            System.out.println("Waiting queue is full!!");                          //Uses enqueue method to add a customer to the circular queue
        } else {                                                                    //Customer object is provided as a parameter to add it to circular queue
            if (front == -1) {
                front = 0;
            }
            rear = (rear + 1) % customerQueue.length;
            customerQueue[rear] = lateCustomer;
        }
    }

    public Customer dequeue() {
        if (front == -1) {
            System.out.println("Waiting queue is empty.");
        }
        Customer customer = customerQueue[front];          //Uses dequeue method to check if the circular queue is empty and if not customer at front will be removed
        customerQueue[front] = null;                       //Customer object that is going to be removed will be returned

        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % customerQueue.length;
        }
        return customer;
    }
}
