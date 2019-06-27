package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {
    Automata CoffeeMashine = new Automata();

/*------Button--------------------*/
@FXML
private Button btnOnOff;
@FXML
private Button btnOkMoney;
@FXML
private Button btnChange;
@FXML
private Button btnMenu1;
@FXML
private Button btnMenu2;
@FXML
private Button btnMenu3;
@FXML
private Button btnMenu4;

/*------Label-----------------*/
@FXML
private Label lbMenu1;
@FXML
private Label lbMenu2;
@FXML
private Label lbMenu3;
@FXML
private Label lbMenu4;

/*------TextField-----------------*/
@FXML
private TextField tfDisplay;
@FXML
private TextField tfMoney;
@FXML
private TextField tfChange;

    private ArrayList[] menu;
    public void onAction_btnOnOff (ActionEvent actionEvent){
        if (CoffeeMashine.getState() == State.OFF ){
            CoffeeMashine.on();
            tfDisplay.setText("Wellcome!");
            tfMoney.editableProperty().setValue(true);
            tfChange.editableProperty().setValue(true);
            btnMenu1.setDisable(false);
            btnMenu2.setDisable(false);
            btnMenu3.setDisable(false);
            btnMenu4.setDisable(false);
            btnChange.setDisable(false);
            btnOkMoney.setDisable(false);
            menu = CoffeeMashine.getMenu();
                  btnMenu1.setText(menu[0].get(0).toString());
                  btnMenu2.setText(menu[0].get(1).toString());
                  btnMenu3.setText(menu[0].get(2).toString());
                  btnMenu4.setText(menu[0].get(3).toString());
                    lbMenu1.setText(menu[1].get(0).toString());
                    lbMenu2.setText(menu[1].get(1).toString());
                    lbMenu3.setText(menu[1].get(2).toString());
                    lbMenu4.setText(menu[1].get(3).toString());
        }
        else if (CoffeeMashine.getState() == State.WAIT ){
            tfDisplay.setText("Goodbay");
            tfMoney.editableProperty().setValue(false);
            tfChange.editableProperty().setValue(false);
            btnMenu1.setDisable(true);
            btnMenu2.setDisable(true);
            btnMenu3.setDisable(true);
            btnMenu4.setDisable(true);
            btnChange.setDisable(true);
            btnOkMoney.setDisable(true);
            btnMenu1.setText("");
            btnMenu2.setText("");
            btnMenu3.setText("");
            btnMenu4.setText("");
             lbMenu1.setText("");
             lbMenu2.setText("");
             lbMenu3.setText("");
             lbMenu4.setText("");
            CoffeeMashine.off();
        }
    }

    public void onAction_btnOkMoney (ActionEvent actionEvent){
        if (CoffeeMashine.getState() != State.OFF ) {
            float temp = Float.parseFloat(tfMoney.getText());
            tfMoney.setText("");
            CoffeeMashine.coin(temp);
            tfDisplay.setText("Credit: " + CoffeeMashine.getCash());
        }
    }

    public void onAction_btnChange (ActionEvent actionEvent){
        Float temp = CoffeeMashine.cancel();
        if (temp != 0) {
            tfDisplay.setText("take your change");
            tfChange.setText(temp.toString() );
        }
        else {
            tfChange.setText("");
            tfDisplay.setText("your credit: 0");
        }
    }

    public void onAction_btnMenu (ActionEvent actionEvent){
        int temp=0;
        if(actionEvent.getSource()==btnMenu1){temp=0;}
        if(actionEvent.getSource()==btnMenu2){temp=1;}
        if(actionEvent.getSource()==btnMenu3){temp=2;}
        if(actionEvent.getSource()==btnMenu4){temp=3;}
        float cash=CoffeeMashine.getCash();
        float change = CoffeeMashine.choice(temp);
        if (change != cash){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tfDisplay.setText("Take your "+menu[0].get(temp));
        }
        else {tfDisplay.setText("take your change and try again" );}
        if (change!=0) {tfChange.setText(""+change);}
        else {tfChange.setText("");}

    }
}
