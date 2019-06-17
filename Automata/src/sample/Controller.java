package sample;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;


public class Controller {
    Automata burda = new Automata();
    String drinkName;
    boolean cookingFlag;

    Timeline threeSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            labelShowMessage.setText("");
        }
    }));

    Timeline oneSecondCheckState = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            printState();
            labelShowBalance.setText(Integer.toString(burda.getCash()));
        }
    }));


    @FXML
    private Button btnOnOff;
    @FXML
    private Label showState;
    @FXML
    private Label labelShowBalance;
    @FXML
    private Label labelShowMessage;
    @FXML
    private TextField TFinsertCash;
    @FXML
    private Button BtnInsertCash;

    // Drinks:
    @FXML
    private Label labelEspresso;
    @FXML
    private Label labelEspressoPrice;
    @FXML
    private Button btnEspresso;

    @FXML
    private Label labelAmericano;
    @FXML
    private Label labelAmericanoPrice;
    @FXML
    private Button btnAmericano;

    @FXML
    private Label labelLatte;
    @FXML
    private Label labelLattePrice;
    @FXML
    private Button btnLatte;

    @FXML
    private Label labelMocco;
    @FXML
    private Label labeMoccoPrice;
    @FXML
    private Button btnMocco;

    @FXML
    private Label labelChocolate;
    @FXML
    private Label labelChocolatePrice;
    @FXML
    private Button btnChocolate;

    @FXML
    private Label labelKotletkispureshkoy;
    @FXML
    private Label labelKotletkispureshkoyPrice;
    @FXML
    private Button btnKotletkispureshkoy;


    @FXML
    public void onOff() {
        if (burda.getState() == STATES.OFF) {
            burda.on();
            oneSecondCheckState.setCycleCount(Timeline.INDEFINITE);
            oneSecondCheckState.play();

            labelEspresso.setText(burda.menu.get(0));
            labelEspressoPrice.setText(burda.price.get(0).toString());

            labelAmericano.setText(burda.menu.get(1));
            labelAmericanoPrice.setText(burda.price.get(1).toString());

            labelLatte.setText(burda.menu.get(2));
            labelLattePrice.setText(burda.price.get(2).toString());

            labelMocco.setText(burda.menu.get(3));
            labeMoccoPrice.setText(burda.price.get(3).toString());

            labelChocolate.setText(burda.menu.get(4));
            labelChocolatePrice.setText(burda.price.get(4).toString());

            labelKotletkispureshkoy.setText(burda.menu.get(5));
            labelKotletkispureshkoyPrice.setText(burda.price.get(5).toString());

        } else if (burda.getState() == STATES.WAIT || burda.getState() == STATES.ACCEPT) {
            burda.off();

        }
    }

    @FXML
    public void printState() {
        showState.setText(burda.getState().toString());
    }


    private void showMessage(String message) {
        labelShowMessage.setText(message);
        threeSecondsWonder.play();
    }

    @FXML
    public void getChange() {
        if ((burda.getCash() != 0 && burda.getState() == STATES.WAIT) || burda.getState() == STATES.ACCEPT) {
            String message = "Take your change $ - " + burda.getCash();
            burda.setCash(0);
            showMessage(message);
        }
    }

    @FXML
    public void insertCash() {
        if (burda.getState()!=STATES.OFF) {
            try {
                int insertedCash = Integer.parseInt(TFinsertCash.getText());
                if (insertedCash > 0) {
                    burda.coin(insertedCash);
                }
            } catch (Exception e) {
                showMessage("Incorrect banknote!");
            }
        }

    }

    @FXML
    public void buyEspresso() {
        cookingFlag=burda.choice(0);
        drinkName = burda.menu.get(0);
        if (cookingFlag == true) {
            showMessageAfterTime(drinkName);

        }
    }

    @FXML
    public void buyAmericano() {
        cookingFlag=burda.choice(1);
        drinkName = burda.menu.get(1);
        if (cookingFlag == true) {
            showMessageAfterTime(drinkName);

        }
    }

    @FXML
    public void buyLatte() {
        cookingFlag=burda.choice(2);
        drinkName = burda.menu.get(2);
        if (cookingFlag == true) {
            showMessageAfterTime(drinkName);

        }
    }

    @FXML
    public void buyMocco() {
        cookingFlag=burda.choice(3);
        drinkName = burda.menu.get(3);
        if (cookingFlag == true) {
            showMessageAfterTime(drinkName);

        }
    }

    @FXML
    public void buyChocolate() {
        cookingFlag=burda.choice(4);
        drinkName = burda.menu.get(4);
        if (cookingFlag == true) {
            showMessageAfterTime(drinkName);

        }
    }

    @FXML
    public void buyKotletki() {
        cookingFlag=burda.choice(5);
        drinkName = burda.menu.get(5);
        if (cookingFlag == true) {
            showMessageAfterTime(drinkName);

        }
    }

    private void showMessageAfterTime(String drinkName){
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> {
            showMessage("Your " + drinkName + " is ready!");
        });
        pause.play();
    }
}
