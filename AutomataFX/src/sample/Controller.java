package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Map;

public class Controller {
    Automata automata = new Automata();

    @FXML
    private Button btn_ON;

    @FXML
    private ComboBox <String> MenuBox;

    @FXML
    private TextField Cash;

    @FXML
    private TextField Coin;

    @FXML
    private Button Cook;

    @FXML
    private Button Cancel;
    @FXML

    Main main;

    public void click_ON(javafx.event.ActionEvent actionEvent) {

        automata.on_off();
        Cash.setEditable(false);
        Cash.setText(Integer.toString(automata.getCash()));
        Coin.setText("");

        Map<String,Integer> Menu =  automata.getMenu();

        ObservableList<String> langs = FXCollections.observableArrayList();
        for(Map.Entry <String, Integer> item : Menu.entrySet()){
            langs.add(item.getKey() +" " + item.getValue());
        }
        MenuBox.setEditable(false);
        MenuBox.setItems( langs );

        btn_ON.setText("OFF");
    }

    public void Coin(ActionEvent actionEvent) {
        automata.coin(Integer.parseInt(Coin.getText()));
        Cash.setText(Integer.toString(automata.getCash()));
        Coin.setText("");
    }

    public void Choice(ActionEvent actionEvent) {
        String[] bufX = MenuBox.getValue().split(" ");
        System.out.println(bufX[0]);
        automata.choice(bufX[0]);
    }

    public void Cook(ActionEvent actionEvent) {
        automata.cook();
        Cash.setText(Integer.toString(automata.getCash()));

    }

    public void Cancel(ActionEvent actionEvent) {
        automata.cancel();
        Cash.setText(Integer.toString(automata.getCash()));
    }
}
