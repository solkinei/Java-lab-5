package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class Controller {
    Automata automata = new Automata("menu.txt");

    boolean onFlag = false;
    boolean onCook = false;
    String value;
    int price;
    @FXML
    Label labelState;
    @FXML
    Label lableCash;
    @FXML
    Label labelPrice;
    @FXML
    TextField textCoin;
    @FXML
    ComboBox comboBoxMenu;

    @FXML
    public void initialize() {
        labelState.setText(automata.getState().toString());
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(automata.drinks);
        comboBoxMenu.getItems().addAll(list);
        comboBoxMenu.setValue("Menu");
        labelState.setText(automata.getState().toString());
        lableCash.setText("");
        labelPrice.setText("");
    }

    @FXML
    public void on() {
        if(!onFlag) {
            comboBoxMenu.setDisable(false);
            automata.on();
            labelState.setText(automata.getState().toString());
            textCoin.setText(String.valueOf(automata.getCash()));
            onFlag = true;
        }
    }

    @FXML
    public void drinkSelection() {
        if (onFlag) {
            value = comboBoxMenu.getValue().toString();
            for (int i = 0; i < automata.drinks.size(); i++) {
                if (automata.drinks.get(i).equals(value)) {
                    price = automata.prices.get(i);
                }
            }
            labelPrice.setText("Drink cost is " + price + " rub");
        }
    }

    @FXML
    public void off() {
        if(onFlag) {
            automata.off();
            labelState.setText(automata.getState().toString());
            labelPrice.setText("");
            lableCash.setText("");
            textCoin.clear();
            onFlag=false;
            onCook=false;
        }
    }

    @FXML
    public void takeMoney() {
        if(onFlag) {
            if (!textCoin.getText().isEmpty()) {
                int money = Integer.parseInt(textCoin.getText());
                automata.coin(money);
                labelState.setText(automata.getState().toString());
                lableCash.setText("Your deposit is " + automata.getCash() + " rub");
                textCoin.clear();
                onCook = true;
            }
        }
    }

    @FXML
    public void cancel() {
        if(onFlag) {
            int returnMoney = automata.cancel();
            if (returnMoney > 0) {
                lableCash.setText("Return your deposit " + returnMoney + " rub");
                labelState.setText(automata.getState().toString());
            } else {
                lableCash.setText("You have no deposit!");
                labelState.setText(automata.getState().toString());
            }
        }
    }

    @FXML
    public void cook() {
        if (onFlag) {
            if (onCook) {
                comboBoxMenu.setDisable(true);
                int change = automata.choice(value);
                if (change >= 0) {
                    lableCash.setText("Your change " + change + " rub");
                    labelState.setText(automata.getState().toString());
                } else {
                    lableCash.setText("You have no change!");
                    labelState.setText(automata.getState().toString());
                }
                labelPrice.setText("Cooking your drink!");
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(event -> {
                    onCook = false;
                    labelPrice.setText("Your drink is ready!");
                });
                pause.play();
            }
        }
    }

}
