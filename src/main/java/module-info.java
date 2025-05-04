module dillag03.battlesaurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens Battlesaurs to javafx.fxml;
    exports Battlesaurs;
}