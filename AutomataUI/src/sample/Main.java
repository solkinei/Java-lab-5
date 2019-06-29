package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

public class Main extends Application {

    private Button onOffButton;
    private Button cancelButton;
    private Button coin1;
    private Button coin5;
    private Button coin10;
    private Button coffee1;
    private Button coffee2;
    private Button coffee3;
    private Button coffee4;
    private Button coffee5;
    private Button coffee6;
    private Label screen;
    private Rectangle indicator;

    private Automata automata;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("C0ffee");
        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        automata = new CoffeAutomata();

        onOffButton = (Button) scene.lookup("#onOffButton");
        cancelButton = (Button) scene.lookup("#cancelButton");
        coin1 = (Button) scene.lookup("#oneCoinButton");
        coin5 = (Button) scene.lookup("#fiveCoinButton");
        coin10 = (Button) scene.lookup("#tenCoinButton");
        coffee1 = (Button) scene.lookup("#coffee1");
        coffee2 = (Button) scene.lookup("#coffee2");
        coffee3 = (Button) scene.lookup("#coffee3");
        coffee4 = (Button) scene.lookup("#coffee4");
        coffee5 = (Button) scene.lookup("#coffee5");
        coffee6 = (Button) scene.lookup("#coffee6");
        screen = (Label) scene.lookup("#screen");
        indicator = (Rectangle) scene.lookup("#indicator");

        screen.setText("");

        onOffButton.setOnAction(event -> {
            if(automata.getState() == State.OFF){
                boolean result = automata.on();
                if(result){
                    checkIndicator();
                    screen.setText("Waiting...");
                    fillMenu();
                }
            } else {
                boolean resalt = automata.off();
                if(resalt){
                    checkIndicator();
                    screen.setText("");
                }
            }
        });

        cancelButton.setOnAction(event -> {
            boolean result = automata.cancel();
            if(result){
                screen.setText("Waiting...");
            }
        });

        coin1.setOnAction(event -> {
            boolean result = automata.coin(1);
            if (result){
                int cash = automata.getCash();
                screen.setText("Money = " + cash);
            }
        });

        coin5.setOnAction(event -> {
            boolean result = automata.coin(5);
            if (result){
                int cash = automata.getCash();
                screen.setText("Money = " + cash);
            }
        });

        coin10.setOnAction(event -> {
            boolean result = automata.coin(10);
            if (result){
                int cash = automata.getCash();
                screen.setText("Money = " + cash);
            }
        });

        coffee1.setOnAction(event -> {
            boolean result = automata.choice(0);
            if(result){
                screen.setText("Waiting...");
            }
        });

        coffee2.setOnAction(event -> {
            boolean result = automata.choice(1);
            if(result){
                screen.setText("Waiting...");
            }
        });

        coffee3.setOnAction(event -> {
            boolean result = automata.choice(2);
            if(result){
                screen.setText("Waiting...");
            }
        });

        coffee4.setOnAction(event -> {
            boolean result = automata.choice(3);
            if(result){
                screen.setText("Waiting...");
            }
        });

        coffee5.setOnAction(event -> {
            boolean result = automata.choice(4);
            if(result){
                screen.setText("Waiting...");
            }
        });

        coffee6.setOnAction(event -> {
            boolean result = automata.choice(5);
            if(result){
                screen.setText("Waiting...");
            }
        });

        checkIndicator();
    }

    private void fillMenu() {
        List<Pair<String, Integer>> menu = automata.getMenu();
        coffee1.setText(menu.get(0).getKey() + " - " + menu.get(0).getValue());
        coffee2.setText(menu.get(1).getKey() + " - " + menu.get(1).getValue());
        coffee3.setText(menu.get(2).getKey() + " - " + menu.get(2).getValue());
        coffee4.setText(menu.get(3).getKey() + " - " + menu.get(3).getValue());
        coffee5.setText(menu.get(4).getKey() + " - " + menu.get(4).getValue());
        coffee6.setText(menu.get(5).getKey() + " - " + menu.get(5).getValue());
    }

    private void checkIndicator() {
        if(automata.getState() == State.OFF){
            indicator.setFill(Color.GRAY);
        } else {
            indicator.setFill(Color.GREEN);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
