module dillag03.battlesaurs {
    requires javafx.controls;
    requires javafx.fxml;


    opens dillag03.battlesaurs to javafx.fxml;
    exports dillag03.battlesaurs;
}