package Battlesaurs;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    @FXML
    private TextArea battleLog;

    @FXML
    private Button biteBtn;

    @FXML
    private Button chargeBtn;

    @FXML
    private ImageView compDino;

    @FXML
    private Label compHP;

    @FXML
    private Button kickBtn;

    @FXML
    private ImageView playerDino;

    @FXML
    private ImageView titleIV; // Set in SceneBuilder already.

    @FXML
    private Label playerHP;

    @FXML
    private Button restBtn;

    @FXML
    private Button stompBtn;

    @FXML
    private Button trikeBtn;

    @FXML
    private Button ankyBtn;

    @FXML
    private Button raptorBtn;

    @FXML
    private Label playerName;

    @FXML
    private Label compName;

    @FXML
    private Label promptLbl;

    private Dinosaur player;
    private Dinosaur computer;
    private Random random = new Random();

    @FXML
    public void initialize() {
        titleIV.setVisible(true);
        promptLbl.setVisible(true);
        actionButtons(false); // No actions til a dinosaur is picked...
        dinoButtons(true);

        playerName.setVisible(false);
        compName.setVisible(false);

        playerHP.setVisible(false);
        compHP.setVisible(false);

        ankyBtn.setOnAction(e -> selectDino((new Ankylosaurus())));
        trikeBtn.setOnAction(e -> selectDino((new Triceratops())));
        raptorBtn.setOnAction(e -> selectDino((new Velociraptor())));

        chargeBtn.setOnAction(e -> handleActions("charge"));
        stompBtn.setOnAction(e -> handleActions("stomp"));
        kickBtn.setOnAction(e -> handleActions("kick"));
        biteBtn.setOnAction(e -> handleActions("bite"));
        restBtn.setOnAction(e -> handleActions("rest"));

        // Cue the music...
        playTheme();
    }

    private void selectDino(Dinosaur selected) {
        this.player = selected;
        this.computer = getOpponent();

        // Show labels...
        playerName.setVisible(true);
        playerName.setText(player.getName());
        compName.setVisible(true);
        compName.setText(computer.getName());

        playerHP.setVisible(true);
        compHP.setVisible(true);
        updateHealth(); // Initialize health...

        // Set images...
        setDinosaurImage(playerDino, player.getName());
        setDinosaurImage(compDino, computer.getName());
        compDino.setScaleX(-1); // Flip horizontally!

        // Bring action buttons back...
        actionButtons(true);

        // Kill the prompt...
        titleIV.setVisible(false);
        promptLbl.setVisible(false);
        dinoButtons(false);

        actionLog("You've selected: " + player.getName());
        actionLog("Opponent selected: " + computer.getName());
    }

    private Dinosaur getOpponent() {
        ArrayList<Dinosaur> choices = new ArrayList<>();
        choices.add(new Ankylosaurus());
        choices.add(new Triceratops());
        choices.add(new Velociraptor());
        choices.add(new Tyrannosaurus());
        choices.add(new Compsognathus());
        return choices.get(random.nextInt(choices.size())); // Random pick of 5 choices...
    }

    private String getOpponentAction() {
        String[] moves = {"charge", "stomp", "kick", "bite", "rest"};
        return moves[random.nextInt(moves.length)]; // Randomize computer's actions...
    }

    // Updating health for initialization + ongoing...
    private void updateHealth() {
        playerHP.setText("HP: " + player.getHealth() + " / " + player.maxHealth);
        compHP.setText("HP: " + computer.getHealth() + " / " + computer.maxHealth);
    }

    private void handleActions(String action) {
        // PLAYER'S TURN
        // Check for raptor bleed...
        player.bleedCheck();
        if (player.bleeding) {
            actionLog("Player: " + player.getName() + " is bleeding and lost 2 HP!");
        }

        if (player.isStunned()) {
            actionLog("Player: Stunned! You cannot move this turn!");
            player.recoveredStun();
        } else {
            int playerDmg = 0;
            if (action.equals("rest")) {
                player.recover();
                actionLog("Player: You rested and regained 10 HP. Hope the " + computer.getName() + " doesn't mind.");
            } else {
                playerDmg = player.attack(action, computer);
                computer.takeDamage(playerDmg);
                if (computer.didHeDodge()) {
                    actionLog("Player: " + player.flavorText(action) + " " + computer.getName() + " evaded the attack! Your opponent has lost 0 HP!");
                } else if (player instanceof Velociraptor && action.equals("bite")) {
                    computer.bleed(3);
                    actionLog("Player: " + player.flavorText(action) + " Opponent is now bleeding! Your opponent has lost " + playerDmg + " HP!");
                } else {
                    actionLog("Player: " + player.flavorText(action) + " Your opponent has lost " + playerDmg + " HP!");
                }
            }
            if (!computer.isAlive()) {
                updateHealth(); // Force.
                stopTheme();
                playSound("victory.mp3");
                actionLog("Enemy: " + computer.getName() + " retreats to care for their wounds.\nYou win!");
                actionButtons(false);

                // Visual indicator...
                defeatedImage(compDino);
                return;
            }
        }

        // COMPUTER'S TURN
        // Check for raptor bleed...
        computer.bleedCheck();
        if (computer.bleeding) {
            actionLog("Enemy: " + computer.getName() + " is bleeding and lost 2 HP!");
        }

        if (computer.isStunned()) {
            actionLog("Enemy: Stunned! Your enemy cannot move this turn!");
            computer.recoveredStun();
        } else {
            String compMove = getOpponentAction();
            int compDmg = 0;
            if (compMove.equals("rest")) {
                computer.recover();
                actionLog("Enemy: " + computer.getName() + " rested and regained 10 HP! How dare they?");
            } else {
                compDmg = computer.attack(compMove, player);
                player.takeDamage(compDmg);
                if (player.didHeDodge()) {
                    actionLog("Enemy: " + computer.flavorText(compMove) + " " + player.getName() + " evaded the attack! You've lost 0 HP.");
                } else if (computer instanceof Velociraptor && compMove.equals("bite")) {
                    player.bleed(3);
                    actionLog("Enemy: " + computer.flavorText(compMove) + " You are now bleeding! You've lost " + compDmg + " HP!");
                } else {
                    actionLog("Enemy: " + computer.flavorText(compMove) + " You've lost " + compDmg + " HP!");
                }
            }
            if (!player.isAlive()) {
                updateHealth(); // Force.
                stopTheme();
                playSound("defeat.mp3");
                actionLog("Player: You are forced to retreat in defeat.\nThe " + computer.getName() + " wins!");
                actionButtons(false);

                // Visual indicator...
                defeatedImage(playerDino);
                return;
            }
        }
        updateHealth(); // If all else, it updates here...
    }

    // Battle Log...
    public void actionLog(String message) {
        battleLog.appendText(message + "\n");
    }

    // Pictures!
    private void setDinosaurImage(ImageView user, String dinoName) {
        String file = dinoName.toLowerCase().replace(" ", "-") + ".png";
        Image image = new Image(getClass().getResourceAsStream("/images/" + file));
        user.setImage(image);
    }

    // Dead pictures...
    private void defeatedImage(ImageView user) {
        // Visual indicator...
        ColorAdjust defeated = new ColorAdjust();
        defeated.setSaturation(-1.0); // No color for you!
        user.setEffect(defeated);

        RotateTransition bellyUp = new RotateTransition(Duration.seconds(1), user);
        bellyUp.setByAngle(180);
        bellyUp.play();
    }

    // GROUPING PURPOSES...
    private void actionButtons(boolean enabled) {
        chargeBtn.setDisable(!enabled);
        stompBtn.setDisable(!enabled);
        kickBtn.setDisable(!enabled);
        biteBtn.setDisable(!enabled);
        restBtn.setDisable(!enabled);

        chargeBtn.setVisible(enabled);
        stompBtn.setVisible(enabled);
        kickBtn.setVisible(enabled);
        biteBtn.setVisible(enabled);
        restBtn.setVisible(enabled);
    }

    private void dinoButtons(boolean enabled) {
        ankyBtn.setDisable(!enabled);
        trikeBtn.setDisable(!enabled);
        raptorBtn.setDisable(!enabled);

        ankyBtn.setVisible(enabled);
        trikeBtn.setVisible(enabled);
        raptorBtn.setVisible(enabled);
    }

    // Win/Lose Sound Effects...
    // This hands-down gave me more fits than the rest... :(
    private void playSound(String file) {
        try {
            Media media = new Media(getClass().getResource("/sounds/" + file).toExternalForm());
            MediaPlayer mp = new MediaPlayer(media);
            mp.play();
        } catch (MediaException e) {
            System.err.println("Media Error: " + e.getMessage());
        }
    }

    // Theme!
    private MediaPlayer mpTheme;

    private void playTheme() {
        try {
            Media media = new Media(getClass().getResource("/sounds/theme.mp3").toExternalForm());
            mpTheme = new MediaPlayer(media);
            mpTheme.setVolume(0.5); // Protecting your ears...
            mpTheme.setCycleCount(MediaPlayer.INDEFINITE); // Infinitely play... until stopped.
            mpTheme.play();
        } catch (MediaException e) {
            System.err.println("Media Error: " + e.getMessage());
        }
    }

    private void stopTheme() {
        if (mpTheme != null) {
            mpTheme.stop();
        }
    }
}
