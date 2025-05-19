module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.Singelton;
    opens com.example.demo.Singelton to javafx.fxml;
}