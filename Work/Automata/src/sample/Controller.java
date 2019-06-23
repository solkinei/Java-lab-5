package sample;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    private Button btCapuccino;

    @FXML
    private Button btTea;

    @FXML
    private Button btAmericana;

    @FXML
    private Label lbTextDisplay;

    @FXML
    private Button btInsertMoney;

    @FXML
    private Button btLatte;

    @FXML
    private Button btOnOff;

    Automata automata = new Automata();
    Timer t;
    TimerTask task;
    Thread tr;
    String fin="";
    String str="Cooking";

    Button btnTemp=new Button();


    private boolean start = false;

    @FXML
    void switchOnOff() {
        if (automata.getState() == STATES.OFF) {
            automata.on();
            lbTextDisplay.setText("Please insert coins!");
        } else if (automata.getState() == STATES.WAIT){
            automata.cancel();
            if (automata.change == 0) {
                automata.off();
                lbTextDisplay.setText(automata.getState().toString());
            } else {
                automata.off();
                lbTextDisplay.setText("Your change " + automata.change + " coins");
                automata.change = 0;

            }
        }

    }

    @FXML
    void insertCoin() {
        if (automata.getState() == STATES.WAIT)
            lbTextDisplay.setText(automata.coin(50) + " coins");
    }

    @FXML
    void initialize() {

        btnTemp.setOnAction(e-> lbTextDisplay.setText(fin));
        btCapuccino.setOnAction(event -> menuButton(1));

        btAmericana.setOnAction(event -> menuButton(2));

        btTea.setOnAction(event -> menuButton(4));

        btLatte.setOnAction(event -> menuButton(3));

    }


    void menuButton(int num) {
        automata.setMenu();

        if (automata.getState() == STATES.WAIT) {
            lbTextDisplay.setText(automata.choice(num));
            lbTextDisplay.setText(automata.getState().toString());
            System.out.println(automata.getState().toString());
            lbTextDisplay.setText(automata.check().toString());
            if (automata.getState() == STATES.ACCEPT) {
                lbTextDisplay.setText(automata.getState().toString());
                System.out.println(automata.getState().toString());
                funcTimer();
                System.out.println("After func");
            }
        }


        }




        void funcTimer() {
                if (start == false) {
                    t = new Timer();
                    task = new TimerTask() {
                        public void run() {
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    str+=".";
                                    lbTextDisplay.setText(str);
                                }
                            });

                        }
                    };

                    tr = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            automata.cook();
                            fin = automata.finish();
                            automata.change = 0;
                            automata.getMenu().clear();
                            automata.getPrices().clear();
                            start = false;
                            t.cancel();
                            t.purge();
                            t = null;
                            Platform.runLater((() -> btnTemp.fire()));
                        }
                    });
                    tr.start();
                    t.schedule(task, 0, 500);
                    start = true;
                } else {
                    start = false;
                    t.cancel();
                    t.purge();
                    t = null;
                }
            str = "Cooking";
        }

}
