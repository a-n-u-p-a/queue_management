package com.example.task4_gui;          //importing necessary packages

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;                  //importing necessary classes and libraries
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;

public class Main extends Application {

    static int burgers = 50;
    static int burgerSaleQ1 = 0;
    static int burgerSaleQ2 = 0;
    static int burgerSaleQ3 = 0;
    static int que1_remains = 0;
    static int que2_remains = 0;            //Declaring variables and arrays needed
    static int que3_remains = 0;

    static char[] cashier_1_status = {'X', 'X'};
    static char[] cashier_2_status = {'X', 'X', 'X'};
    static char[] cashier_3_status = {'X', 'X', 'X', 'X', 'X'};



    static FoodQueue[] cashiers = {new FoodQueue(2),new FoodQueue(3),new FoodQueue(5),new FoodQueue(5)};
    static int serving;                 //Foodqueue object array is initialized here


    public static void main(String[] args) {

        boolean exit = false;                               //Display the menu for the operator
        String menu_op = " ";                               //According to the option selected, each method is executed and if used want to exit, it breaks the loop
        do {
            try {
                System.out.println("""
                                                           
                        100 or VFQ: View all Queues.
                        101 or VEQ: View all Empty Queues.
                        102 or ACQ: Add customer to a Queue.
                        103 or RCQ: Remove a customer from a Queue.
                        104 or PCQ: Remove a served customer.
                        105 or VCS: View Customers Sorted in alphabetical order
                        106 or SPD: Store Program Data into file.
                        107 or LPD: Load Program Data from file.
                        108 or STK: View Remaining burgers Stock.
                        109 or AFS: Add burgers to Stock.
                        110 or IFQ: Print the income of each queue.
                        112 or GUI: Load the Graphical User Interface
                        999 or EXT: Exit the Program.
                        """);

                System.out.print("Enter your option: ");
                Scanner input = new Scanner(System.in);
                menu_op = input.nextLine();
                System.out.println(" ");
                switch (menu_op) {
                    case "100", "VFQ" -> allQueues();
                    case "101", "VEQ" -> {

                        System.out.print("Available queues are: ");
                        emptyQueues(cashiers[0]);
                        emptyQueues(cashiers[1]);
                        emptyQueues(cashiers[2]);

                        if (emptyQueues(cashiers[0]) != 2) {
                            System.out.print(1 + " ");
                        }
                        if (emptyQueues(cashiers[1]) != 3) {
                            System.out.print(2 + " ");
                        }
                        if (emptyQueues(cashiers[2]) != 5) {
                            System.out.print(3);
                        }
                        if (((emptyQueues(cashiers[0])) == 2) && ((emptyQueues(cashiers[1])) == 3) && ((emptyQueues(cashiers[2])) == 5)) {
                            System.out.println("None");
                        }
                        System.out.println("\n");
                    }
                    case "102", "ACQ" -> addCustomer();
                    case "103", "RCQ" -> removeCustomer(menu_op);
                    case "104", "PCQ" -> removeCustomer(menu_op);
                    case "105", "VCS" -> customernames_order();
                    case "106", "SPD" -> storeData();
                    case "107", "LPD" -> displayData();
                    case "108", "STK" -> System.out.println("There are " + burgers + " remaining burgers in the stock!\n");
                    case "109", "AFS" -> addBurgers();
                    case "110", "IFQ" -> burgerSale();
                    case "112", "GUI" -> loadGUI();
                    case "999", "EXT" -> exit = true;
                    default -> System.out.println("Wrong menu input!!\n");
                }
            } catch (Exception error) {
                System.out.println("Error in menu method");
            }

        } while (!exit);        //menu is displayed until user wants to exit(enter 999 or EXT)
    }


    public static void allQueues() {
        System.out.println("    ******************");                   //Prints cashiers and the customer status in each queue in order
        System.out.println("       * Cashiers *");
        System.out.println("    ******************");
        for (int j = 0; j < 2; j++) {
            System.out.println("    "+cashier_1_status[j] + "       " + cashier_2_status[j] + "       " + cashier_3_status[j] + " ");
        }
        for (int j = 2; j < 3; j++) {
            System.out.println("            " + cashier_2_status[j] + "       " + cashier_3_status[j] + " ");
        }
        for (int j = 3; j < 5; j++) {
            System.out.println("                    " + cashier_3_status[j] + " ");
        }
        System.out.println(" ");
        System.out.println("X – Not Occupied  O – Occupied");
    }

    public static int emptyQueues(FoodQueue customer_Data) {
        int nullCount = 0;
        for (int i = 0; i < customer_Data.length(); i++) {          //Checks if any of the queue has an empty position and if yes, breaks the loop
            if (customer_Data.isNull(i)) {                          //if no empty option, returns the nullcount which is a count to check how many positions are occupied
                break;
            } else {
                nullCount++;
            }
        }
        return nullCount;
    }


    public static void addCustomer() {
        try {

            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter the firstname of the customer: ");
            String firstName = input.nextLine();                                   //gets firstname, lastname, burger amount from the operator and creates a customer object with that data
            System.out.print("\nEnter the lastname of the customer: ");            //Then the created customer object is added to the queue with the minimum length
            String lastName = input.nextLine();                                    //If all queues are full, that customer will be added to the waiting queue
            System.out.print("\nEnter the number of burgers: ");
            serving = input.nextInt();

            Customer customer = new Customer(firstName, lastName, serving);


            if ((que1_remains < que2_remains && que1_remains < que3_remains || que1_remains == que2_remains && que1_remains < que3_remains || que1_remains == que3_remains && que1_remains < que2_remains || que1_remains == que2_remains && que1_remains == que3_remains) && que1_remains != 2) {        /* avoid asking name once the queue is full */
                cashiers[0].addCustomer(customer, que1_remains);
                System.out.println(firstName + " " + lastName + " added to the queue 1 successfully\n");
                cashier_1_status[que1_remains] = 'O';
                que1_remains++;

            } else if ((que2_remains < que3_remains && que2_remains < que1_remains || que2_remains == que1_remains && que2_remains < que3_remains || que2_remains == que3_remains && que2_remains < que1_remains) && que2_remains != 3) {
                cashiers[1].addCustomer(customer, que2_remains);
                cashier_2_status[que2_remains] = 'O';
                que2_remains++;
                System.out.println(firstName + " " + lastName + " added to the queue 2 successfully\n");

            } else if (que3_remains != 5) {
                cashiers[2].addCustomer(customer, que3_remains);
                System.out.println(firstName + " " + lastName + " added to the queue 3 successfully\n");
                cashier_3_status[que3_remains] = 'O';
                que3_remains++;

            } else {
                System.out.println("All queues are full!!, new customers will be added to the waiting list!");
                cashiers[3].enqueue(customer);

            }
        }catch (Exception e){
            System.out.println("Please enter correct inputs");
        }
    }

    public static void removeCustomer(String menu_op) {
        try {
            Scanner input = new Scanner(System.in);

            if ("103".equals(menu_op) || "RCQ".equals(menu_op)) {                       //checks if a customer needs to be served or just removed
                System.out.print("Enter the queue number (1, 2, or 3): ");              //menu options regarding removing customers are given as a parameter(103 or 104)
                int unserved_queue = input.nextInt();                                   //according to the parameter each method related to the menu option will be called

                switch (unserved_queue) {
                    case 1 -> unservedCustomers(cashiers[0], cashier_1_status, que1_remains, unserved_queue);
                    case 2 -> unservedCustomers(cashiers[1], cashier_2_status, que2_remains, unserved_queue);
                    case 3 -> unservedCustomers(cashiers[2], cashier_3_status, que3_remains, unserved_queue);
                    default -> System.out.println("Wrong queue number!!, Please enter 1,2 or 3.\n");
                }
            } else if (("104".equals(menu_op) || "PCQ".equals(menu_op)) && burgers >= 5) {      //checks the menu option as well as burger stock
                System.out.print("Enter the served queue number (1, 2, or 3): ");               //if stock is low customer will not be served
                int served_queue = input.nextInt();

                switch (served_queue) {
                    case 1 -> servedCustomer(cashiers[0], cashier_1_status, que1_remains, served_queue);
                    case 2 -> servedCustomer(cashiers[1], cashier_2_status, que2_remains, served_queue);
                    case 3 -> servedCustomer(cashiers[2], cashier_3_status, que3_remains, served_queue);
                    default -> System.out.println("Wrong queue number!!, Please enter 1,2 or 3.\n");
                }
            } else {
                System.out.println("Burger stock is running below 5 burgers!!, Please add burgers to the stock in order to serve.");
            }

        } catch (Exception remove_customers1) {
            System.out.println("Wrong input!!, Queue should be a number ranging from 1 to 3.\n");
        }
    }

    public static void unservedCustomers(FoodQueue customer_data, char[] cashier_status, int queue_remains, int unserved_queue) {
        try {

            if (customer_data.getCustomer(0) == null) {                                             //Method regarding removing customers who aren't served
                System.out.println("No customers to remove, queue is currently empty!!\n");               //queue object,a cashier status array, a count for each queue that adds up when a customer is added,
            } else {                                                                                      //and an integer which have the queue number of that unserved customer
                                                                                                          //are provided as paramaters
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the position of the customer in the queue ranging from 0-" + (customer_data.length() - 1) + ": ");
                int customer_index = input.nextInt();

                if (customer_index < 0 || customer_index >= customer_data.length()) {
                    System.out.println("Invalid customer position!!, Position of the customer should be in range.\n");
                } else if (customer_data.getCustomer(customer_index) == null) {
                    System.out.println("No customer to remove!!, Entered customer position is empty.\n");
                } else {
                    System.out.println("Customer in position " + customer_index + "(" + customer_data.getCustomer(customer_index).getFirstname() + ")" + " has been removed successfully!\n");

                    for (int i = customer_index; i < customer_data.length() - 1; i++) {
                        if (customer_data.getCustomer(i + 1) == null) {
                            break;
                        } else {                                                              //checks and removes the customer at the index that operator enters which need to be removed
                            customer_data.setCustomer(i);                                     //then the customer objects after that are assigned to its before index, like when someone is going out of a queue
                            customer_index++;
                        }
                    }
                    switch (unserved_queue) {
                        case 1 -> que1_remains -= 1;
                        case 2 -> que2_remains -= 1;                                        //According to the queue the count relating to that queue is removed by 1
                        case 3 -> que3_remains -= 1;
                    }
                    if(cashiers[3].chkCQ()) {
                        customer_data.addCustomer(cashiers[3].dequeue(),customer_data.length()-1);
                        switch (unserved_queue) {
                            case 1 -> que1_remains += 1;
                            case 2 -> que2_remains += 1;                        //if the waiting queue is not null, meaning that all queues are full, when the customer has been removed
                            case 3 -> que3_remains += 1;                        //then the very first customer in waiting queue is added to that last spot of that queue referencing the removal of the unserved customer
                        }
                    } else {
                        queue_remains--;
                        customer_data.setnullCustomer(customer_index);
                        customer_data.setnullCustomer(customer_data.length() - 1);         //if waiting queue is empty,100 options are updated and the last of the queue referenced here is set to null
                        cashier_status[queue_remains] = 'X';
                    }
                }
            }
        } catch (Exception err) {
            System.out.println("Invalid input!!, Customer position should be a number in the provided range.");
        }
    }

    public static void customerRemover(FoodQueue customer_data, char[] cashier_status, int queue_remains, int removeCount) {
        do {
            if (removeCount == 0) break;
            queue_remains--;                                                    //queue object,a cashier status array, a count for each queue that adds up when a customer is added,
            removeCount--;                                                      // a count for how many customers to be removed are given as parameters
            for (int i = 1; i < customer_data.length(); i++) {                  //this removes customer according to given queue and the remove count and updates information related to it
                customer_data.setCustomer(i - 1);
            }

            customer_data.setnullCustomer(customer_data.length() - 1);
            cashier_status[queue_remains] = 'X';
        } while (removeCount > 0);
    }

    public static void servedCustomer(FoodQueue customer_data, char[] cashier_status, int queue_remains, int served_queue) {
        int removeCount = 1;
        try {
            Scanner input = new Scanner(System.in);
            if (customer_data.isNull(0)) {
                System.out.println("No customers to serve, queue is empty!!\n");
            } else {
                switch (served_queue) {                                                                         //queue object,a cashier status array, a count for each queue that adds up when a customer is added,
                                                                                                                //an integer referencing the customer queue are added as parameters
                    case 1 -> {                                                                                 //This method is regarding the removal of served customers
                        que1_remains -= 1;                                                                      //updates the burger sales for each queue and removes the customer and the waiting queue functionalities are same as in unserved option
                        burgerSaleQ1 += 650 * cashiers[0].getCustomer(0).getServing();
                    }
                    case 2 -> {
                        que2_remains -= 1;
                        burgerSaleQ2 += 650 * cashiers[1].getCustomer(0).getServing();
                    }
                    case 3 -> {
                        que3_remains -= 1;
                        burgerSaleQ3 += 650 * cashiers[2].getCustomer(0).getServing();
                    }
                }
                if (serving <= burgers) {

                    System.out.println("Customer " + customer_data.getCustomer(queue_remains-1).getFirstname() + " has been removed successfully!\n");
                    burgers -= customer_data.getCustomer(0).getServing();
                    customerRemover(customer_data, cashier_status, queue_remains, removeCount);

                    if (cashiers[3].chkCQ()){
                        customer_data.addCustomer(cashiers[3].dequeue(),customer_data.length()-1);

                        cashier_status[customer_data.length()-1] = 'O';
                        switch (served_queue){
                            case 1 -> que1_remains += 1;
                            case 2 -> que2_remains += 1;
                            case 3 -> que3_remains += 1;
                        }
                    }

                } else {
                    System.out.println("Order for " + serving + " burgers cannot exceed the current burger stock of " + burgers + " burgers.");
                }
            }

            if (burgers == 10) {
                System.out.println("********************************");
                System.out.println("The burger stock has reached down to 10 burgers\n");
                System.out.println("********************************");                         //warnings regarding the burger stock
            } else if (burgers < 15 && burgers > 10) {
                System.out.println("Warning!! burger stock might reach below 10 burgers if a customer is served more than " + (burgers - 10) + " burgers");
                System.out.println("Currently there are " + burgers + " burgers in the stock!");
            }
        } catch (Exception error) {
            error.printStackTrace();
            System.out.println("Please enter a queue number\n");
        }
    }

    public static void customernames_order() {
        int index = 0;
        ArrayList<String> allDataList = new ArrayList<String>();

        allNames(cashiers[0], allDataList);                             //Initializing an array list to get all customer names and calling functions that does the adding functionality
        allNames(cashiers[1], allDataList);
        allNames(cashiers[2], allDataList);

        nameSort(allDataList);
    }

    public static void allNames(FoodQueue allData, ArrayList<String> allDataList) {
        try {                                                                                   //queues and an array list is given as parameters
            for (int i = 0; i < allData.length(); i++) {
                if (allData.getCustomer(i) != null) {
                    allDataList.add(allData.getCustomer(i).getFirstname() + " " + allData.getCustomer(i).getLastname());
                }
            }
            for (int i = 0; i < allDataList.size(); i++) {          //Adding all names to a single array list
                System.out.print(allDataList.get(i) + ", ");
            }
            System.out.println("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void nameSort(ArrayList<String> array) {         //array list with names is given as a parameter
        try {

            int bottom = array.size() - 2;
            boolean exchanged = true;
            while (exchanged) {
                exchanged = false;
                for (int i = 0; i <= bottom; i++) {
                    if (array.get(i).compareTo(array.get(i + 1)) > 0) {
                        String temp = array.get(i + 1);
                        array.set(i + 1, array.get(i));                 //Sort names using compareTo method
                        array.set(i, temp);
                    } else {
                        String temp = array.get(i);
                        array.set(i, array.get(i + 1));
                        array.set(i + 1, temp);
                    }
                }
                bottom--;
            }
            for (int i = 0; i < array.size(); i++) {
                System.out.print(array.get(i) + ", ");
            }
            System.out.println("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void storeData() throws IOException {
        FileWriter saveData = new FileWriter("dataSave.txt");

        storeDataloop(saveData, cashiers[0]);
        storeDataloop(saveData, cashiers[1]);
        storeDataloop(saveData, cashiers[2]);                       //Saves data to the text file(Executes the method that does it)

        saveData.write(burgerSaleQ1 + "\n");
        saveData.write(burgerSaleQ2 + "\n");
        saveData.write(burgerSaleQ3 + "\n");
        saveData.write(burgers + "\n");
        saveData.close();

        System.out.println("Data has been saved successfully!\n");
    }

    public static void storeDataloop(FileWriter fileWriter, FoodQueue customer_data) throws IOException {

        for (int i = 0; i < customer_data.length(); i++) {

            if (customer_data.getCustomer(i) != null) {
                fileWriter.write(customer_data.getCustomer(i).getFirstname() + ",");        //customer data is written to the text file
                fileWriter.write(customer_data.getCustomer(i).getLastname() + ",");
                fileWriter.write(customer_data.getCustomer(i).getServing() + "\n");
            } else fileWriter.write("null" + "," + "null" + "," + "null\n");
        }
    }

    public static void displayData() {

        try {
            File inputFile = new File("dataSave.txt");
            Scanner readFile = new Scanner(inputFile);      // Read and process each line of the file
            int lineCount = 0;

            customerRemover(cashiers[0], cashier_1_status, que1_remains, que1_remains);
            customerRemover(cashiers[1], cashier_2_status, que2_remains, que2_remains);             //Emptying data(executes customer remover method above) in queues before adding old data stored in the text file
            customerRemover(cashiers[2], cashier_3_status, que3_remains, que3_remains);
            que1_remains = 0;
            que2_remains = 0;
            que3_remains = 0;

            while (readFile.hasNextLine()) {
                String text = readFile.nextLine();
                String firstName;
                String lastName;
                int serving;


                if (lineCount >= 0 && lineCount < 2) {
                    if (!("null,null,null".equals(text))) {
                        String[] values = text.split(",");
                        cashier_1_status[que1_remains] = 'O';

                        firstName = values[0];
                        lastName = values[1];
                        serving = Integer.parseInt(values[2]);          //assigns each line data to customer object attributes accordingly

                        Customer customer = new Customer(firstName, lastName, serving);
                        cashiers[0].addCustomer(customer, lineCount);        //data from text file that refers queue 1 is added to queue 1 here
                        que1_remains++;
                    } else {
                        cashiers[0].setnullCustomer(lineCount);
                    }

                } else if (lineCount > 1 && lineCount < 5) {
                    if (!("null,null,null".equals(text))) {
                        String[] values = text.split(",");
                        cashier_2_status[que2_remains] = 'O';

                        firstName = values[0];
                        lastName = values[1];
                        serving = Integer.parseInt(values[2]);

                        Customer customer = new Customer(firstName, lastName, serving);
                        cashiers[1].addCustomer(customer, lineCount - 2);       //data from text file that refers queue 2 is added to queue 2 here
                        que2_remains++;
                    } else {
                        cashiers[1].setnullCustomer(lineCount - 2);
                    }

                } else if (lineCount > 4 && lineCount < 10) {
                    if (!("null,null,null".equals(text))) {
                        String[] values = text.split(",");
                        cashier_3_status[que3_remains] = 'O';

                        firstName = values[0];
                        lastName = values[1];
                        serving = Integer.parseInt(values[2]);

                        Customer customer = new Customer(firstName, lastName, serving);
                        cashiers[2].addCustomer(customer, lineCount - 5);            //data from text file that refers queue 3 is added to queue 3 here
                        que3_remains++;
                    } else {
                        cashiers[2].setnullCustomer(lineCount - 5);
                    }

                } else if (lineCount > 9 && lineCount < 13) {
                    switch (lineCount) {
                        case 10 -> burgerSaleQ1 = Integer.parseInt(text);           //Burger sales data is updated here
                        case 11 -> burgerSaleQ2 = Integer.parseInt(text);
                        case 12 -> burgerSaleQ3 = Integer.parseInt(text);
                    }
                } else {
                    burgers = Integer.parseInt(text);               //Burger stock data is updated here
                }
                lineCount++;
            }
            readFile.close();
            System.out.println("Data has been successfully loaded to the programme!!\n");

        } catch (Exception error) {
            System.out.println("Please try saving data into a file first and then try loading data.");
            error.printStackTrace();
        }
    }

    public static void addBurgers() throws IOException {
        int stock;
        int new_stock;
                                                                        //All options related to adding burgers are here
        while (true) {
            if (burgers == 50) {
                System.out.println("Stock is full, please serve customers and try again..\n");          //burgers are permitted to be added only if the burger stock is not = to 50, here it checks if it's = 50 and if true, breaks
                break;

            } else if (burgers != 50) {
                stock = (50 - burgers);
                System.out.println("The maximum number of burgers you can add is: " + stock + "\n");

                while (true) {
                    try {
                        System.out.print("Enter the numbers of burgers you are going to add: ");
                        Scanner input = new Scanner(System.in);
                        new_stock = input.nextInt();

                        if (new_stock > stock) {
                            System.out.println("Cannot exceed the maximum number of burgers you can add!!\n");
                            new_stock -= new_stock;
                        } else if (new_stock <= stock) {
                            burgers += new_stock;
                            System.out.println("Successfully added " + new_stock + " burgers to the stock!\n");
                            break;
                        }
                    } catch (Exception add_burgers) {
                        System.out.println("Wrong input!!, Amount of burgers can only be a number.\n");
                    }
                }
                break;
            }

        }
    }

    public static void burgerSale() {
        System.out.println("Sales for Each queues are as below: ");
        System.out.println("\nQueue 1 sales --> Rs " + burgerSaleQ1 + "/=");
        System.out.println("Queue 2 sales --> Rs " + burgerSaleQ2 + "/=");          //prints burger sales for each queues
        System.out.println("Queue 3 sales --> Rs " + burgerSaleQ3 + "/=");
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));      //GUI method initializations etc
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("Foodies Fave Queue Management System(GUI)");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadGUI(){           // launches the GUI
        launch();
    }

}