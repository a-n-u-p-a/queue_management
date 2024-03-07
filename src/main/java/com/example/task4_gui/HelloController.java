package com.example.task4_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private TextField customerName;

    @FXML
    private Label Fname;
    @FXML
    private Label Lname;
    @FXML
    private Label Burgers;
    @FXML
    private Label queue;
    @FXML
    private Label index;
    @FXML
    private Label Q1_0;
    @FXML
    private Label Q1_1;
    @FXML
    private Label Q2_0;
    @FXML
    private Label Q2_1;
    @FXML
    private Label Q2_2;
    @FXML
    private Label Q3_0;
    @FXML
    private Label Q3_1;
    @FXML
    private Label Q3_2;
    @FXML
    private Label Q3_3;
    @FXML
    private Label Q3_4;

    @FXML
    private Label WQ_0;
    @FXML
    private Label WQ_1;
    @FXML
    private Label WQ_2;
    @FXML
    private Label WQ_3;
    @FXML
    private Label WQ_4;

    String Firstname = "";
    String Lastname = "";
    String Serving = "";
    String array[] = new String[15];
    String Index = "";
    String Queue = "";


    @FXML
    public void getSearchName(){

        if(Main.cashiers[0].searchCustomer(customerName.getText()) != null){

            Firstname = (Main.cashiers[0].searchCustomer(customerName.getText()))[0];
            Lastname = (Main.cashiers[0].searchCustomer(customerName.getText()))[1];
            Serving = (Main.cashiers[0].searchCustomer(customerName.getText()))[2];
            Index = (Main.cashiers[0].searchCustomer(customerName.getText()))[3];
            Queue = (Main.cashiers[0].searchCustomer(customerName.getText()))[4];

        }else if(Main.cashiers[1].searchCustomer(customerName.getText()) != null){

            Firstname = (Main.cashiers[1].searchCustomer(customerName.getText()))[0];
            Lastname = (Main.cashiers[1].searchCustomer(customerName.getText()))[1];
            Serving = (Main.cashiers[1].searchCustomer(customerName.getText()))[2];
            Index = (Main.cashiers[1].searchCustomer(customerName.getText()))[3];
            Queue = (Main.cashiers[1].searchCustomer(customerName.getText()))[4];

        }else if(Main.cashiers[2].searchCustomer(customerName.getText()) != null){

            Firstname = (Main.cashiers[2].searchCustomer(customerName.getText()))[0];
            Lastname = (Main.cashiers[2].searchCustomer(customerName.getText()))[1];
            Serving = (Main.cashiers[2].searchCustomer(customerName.getText()))[2];
            Index = (Main.cashiers[2].searchCustomer(customerName.getText()))[3];
            Queue = (Main.cashiers[2].searchCustomer(customerName.getText()))[4];

        }else if(Main.cashiers[3].searchCQCustomer(customerName.getText()) != null){

            Firstname = (Main.cashiers[3].searchCQCustomer(customerName.getText()))[0];
            Lastname = (Main.cashiers[3].searchCQCustomer(customerName.getText()))[1];
            Serving = (Main.cashiers[3].searchCQCustomer(customerName.getText()))[2];
            Index = (Main.cashiers[3].searchCQCustomer(customerName.getText()))[3];
            Queue = (Main.cashiers[3].searchCQCustomer(customerName.getText()))[4];
        }

        Fname.setText(Firstname);
        Lname.setText(Lastname);
        Burgers.setText(Serving);
        queue.setText(Queue);
        index.setText(Index);
    }

    @FXML
    public void viewDetails(){
        customerRetriever(0,0);
        customerRetriever(2,1);
        customerRetriever(5,2);
        customerRetriever(10,3);

        Q1_0.setText(array[0]);
        Q1_1.setText(array[1]);
        Q2_0.setText(array[2]);
        Q2_1.setText(array[3]);
        Q2_2.setText(array[4]);
        Q3_0.setText(array[5]);
        Q3_1.setText(array[6]);
        Q3_2.setText(array[7]);
        Q3_3.setText(array[8]);
        Q3_4.setText(array[9]);
        WQ_0.setText(array[10]);
        WQ_1.setText(array[11]);
        WQ_2.setText(array[12]);
        WQ_3.setText(array[13]);
        WQ_4.setText(array[14]);


    }

    public void customerRetriever(int length, int array_num) {
        for (int i = 0; i < Main.cashiers[array_num].length(); i++) {
            if (!(Main.cashiers[array_num].getCustomer(i) == null)) {
                array[length] = Main.cashiers[array_num].getCustomer(i).getFirstname();
                length++;
            } else {
                array[length] = "none";
                length++;
            }
        }
    }
}