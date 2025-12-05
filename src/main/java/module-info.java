module com.example.hxhprofilemanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.hxhprofilemanagement to javafx.fxml;
    exports com.example.hxhprofilemanagement;
    exports com.example.hxhprofilemanagement.Controller;
    opens com.example.hxhprofilemanagement.Controller to javafx.fxml;
}