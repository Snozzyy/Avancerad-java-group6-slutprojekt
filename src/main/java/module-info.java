module com.example.avanceradjavagroup6slutprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires minimal.json;


    opens com.example.avanceradjavagroup6slutprojekt to javafx.fxml;
    exports com.example.avanceradjavagroup6slutprojekt;
}