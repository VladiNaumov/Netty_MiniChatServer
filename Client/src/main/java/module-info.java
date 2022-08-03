module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.transport;


    opens com.example to javafx.fxml;
    exports com.example;
}