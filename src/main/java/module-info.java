module com.example.task4_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task4_gui to javafx.fxml;
    exports com.example.task4_gui;
}