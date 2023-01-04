module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.pages.main;
    opens com.example.demo.pages.main to javafx.fxml;
    opens com.example.demo.pages.login to javafx.fxml;
    exports com.example.demo.pages.login;
    opens com.example.demo.pages.register to javafx.fxml;
    exports com.example.demo.pages.register;
}