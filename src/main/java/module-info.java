module com.stickhero.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;
    requires org.controlsfx.controls;

    opens com.stickhero.stickhero to javafx.fxml;
    exports com.stickhero.stickhero;
}