module com.example.klient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.klient to javafx.fxml;
    exports com.example.klient;
}