package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import static java.lang.Integer.parseInt;

public class Controller {
    Automata automata = new Automata();
    boolean flag = false;
    boolean comboFlag = true;
    boolean onFlag = true;
    boolean cancelFlag = true;
    @FXML
    Label state;
    @FXML
    Label money;
    @FXML
    Label price;
    @FXML
    private ComboBox comboBox;
    @FXML
    TextField textField;


    @FXML
    public void on() {
        if (onFlag) {
            automata.mapOfPriceAndDrink.menu.put("Tea", 10);
            automata.mapOfPriceAndDrink.menu.put("Coffee", 50);
            automata.mapOfPriceAndDrink.menu.put("Capuchino", 70);
            automata.mapOfPriceAndDrink.menu.put("Latte", 30);
            automata.on();
            state.setText(automata.getState().toString());
            if (comboFlag) {
                ObservableList<String> list = FXCollections.observableArrayList();
                list.addAll(automata.mapOfPriceAndDrink.menu.keySet());
                comboBox.getItems().addAll(list);
                comboBox.setOnAction(event -> price.setText(String.valueOf(automata.mapOfPriceAndDrink.menu.
                        get(comboBox.getValue()))));
            }
            comboBox.setDisable(false);
            flag = true;
            comboFlag = false;
            onFlag = false;
            cancelFlag = true;
        }
    }

    @FXML
    public void off() {
        if (flag) {
            automata.off();
            state.setText(automata.getState().toString());
            textField.clear();
            price.setText("");
            money.setText("");
            comboBox.setDisable(true);
            automata.setCash(0);
            onFlag = true;
        }
    }

    @FXML
    public void setCash() {
        if (flag && !price.getText().isEmpty()) {
            automata.coin(parseInt(textField.getText()));
            state.setText(automata.getState().toString());
            money.setText("CREDIT " + String.valueOf(automata.getCash()) + "$");
        }
    }

    @FXML
    public void cancel() {
        if (cancelFlag && !price.getText().isEmpty()) {
            automata.cancel();
            state.setText(automata.getState().toString());
            if (automata.getCash() > parseInt(price.getText())) {
                money.setText("COIN " + String.valueOf(automata.getCash() - parseInt(price.getText())) + "$");
                automata.setCash(0);
                comboBox.setDisable(true);
                price.setText("");
            } else if (automata.getCash() <= parseInt(price.getText())) {
                money.setText("COIN " + String.valueOf(automata.getCash()));
                automata.setCash(0);
                comboBox.setDisable(true);
                price.setText("");
            }
            automata.setCash(0);
        }
    }

    @FXML
    public void cook() {
        if (flag && !price.getText().isEmpty()) {
            automata.check();
            state.setText(automata.getState().toString());
            if (automata.getCash() >= parseInt(price.getText())) {
                PauseTransition pause1 = new PauseTransition(Duration.seconds(2));
                pause1.setOnFinished(event -> {
                    automata.cook();
                    state.setText(automata.getState().toString());
                });
                pause1.play();
                PauseTransition pause = new PauseTransition(Duration.seconds(4));
                pause.setOnFinished(event -> {
                    automata.finish();
                    state.setText(automata.getState().toString());
                    money.setText("Put your drink");
                    price.setText("Put coin " + String.valueOf(automata.getCash() - parseInt(price.getText())) + "$");
                    automata.setCash(0);
                });
                pause.play();
            } else if (automata.getCash() < parseInt(price.getText())) {
                PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
                pause2.setOnFinished(event -> {
                    money.setText("Add money please");
                });
                pause2.play();
            }
            cancelFlag = false;
        }
    }
}
