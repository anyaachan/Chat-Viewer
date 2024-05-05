module com.example.chatviewer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.chatviewer to javafx.fxml;
    exports com.example.chatviewer;
    exports com.example.chatviewer.data;
    opens com.example.chatviewer.data to javafx.fxml;
}