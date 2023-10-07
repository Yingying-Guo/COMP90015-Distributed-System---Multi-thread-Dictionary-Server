module com.example.dictionarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens model to javafx.fxml;
    exports model;
    exports model.Client;
    opens model.Client to javafx.fxml;
    exports model.Server;
    opens model.Server to javafx.fxml;
}