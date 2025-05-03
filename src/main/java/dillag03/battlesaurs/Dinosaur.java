package dillag03.battlesaurs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Dinosaur {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}