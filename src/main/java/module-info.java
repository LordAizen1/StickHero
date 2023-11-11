module com.stickhero.stickhero {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.stickhero.stickhero to javafx.fxml;
    exports com.stickhero.stickhero;
}