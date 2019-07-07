package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {

    private Timer t;
    private TimerTask task;

    @FXML
    private TextArea taMessage;
    @FXML
    private TextField taCoin;

    @FXML
    private Label lblChoice;
    @FXML
    private Label lblGive;

    @FXML
    private Pane pnBase;

    @FXML
    private Button btnPay;
    @FXML
    private Button btnTake;
    @FXML
    private Button btnCancel;
    @FXML
    private ToggleButton btnOnOff;

    @FXML
    private Button btnOne;
    @FXML
    private Button btnTwo;
    @FXML
    private Button btnThree;
    @FXML
    private Button btnFour;
    @FXML
    private Button btnFive;
    @FXML
    private Button btnSix;
    @FXML
    private Button btnSeven;
    @FXML
    private Button btnEight;

    @FXML
    private Label lblOne;
    @FXML
    private Label lblTwo;
    @FXML
    private Label lblThree;
    @FXML
    private Label lblFour;
    @FXML
    private Label lblFive;
    @FXML
    private Label lblSix;
    @FXML
    private Label lblSeven;
    @FXML
    private Label lblEight;

    //@FXML
   //URL url = getClass().getResource("white.jpg");

    Automata myAutomata = new Automata("menu.txt");

  //  boolean flag = false;//flag for on and off state of automata
   // boolean ableToSwitchOff = true;

    @FXML
    public void btnOnOffClick(){
        if (btnOnOff.isSelected()){//if Automata's state was off
            //flag = true;
            myAutomata.on();
            taMessage.setText ("Дай мне денег");
            //taMessage.setEditable(false);
            taCoin.setEditable(true);
            btnOne.setText(myAutomata.getMenu().get(0).getMessage());
            btnTwo.setText(myAutomata.getMenu().get(1).getMessage());
            btnThree.setText(myAutomata.getMenu().get(2).getMessage());
            btnFour.setText(myAutomata.getMenu().get(3).getMessage());
            btnFive.setText(myAutomata.getMenu().get(4).getMessage());
            btnSix.setText(myAutomata.getMenu().get(5).getMessage());
            btnSeven.setText(myAutomata.getMenu().get(6).getMessage());
            btnEight.setText(myAutomata.getMenu().get(7).getMessage());
            lblOne.setText(myAutomata.getMenu().get(0).getSum().toString());
            lblTwo.setText(myAutomata.getMenu().get(1).getSum().toString());
            lblThree.setText(myAutomata.getMenu().get(2).getSum().toString());
            lblFour.setText(myAutomata.getMenu().get(3).getSum().toString());
            lblFive.setText(myAutomata.getMenu().get(4).getSum().toString());
            lblSix.setText(myAutomata.getMenu().get(5).getSum().toString());
            lblSeven.setText(myAutomata.getMenu().get(6).getSum().toString());
            lblEight.setText(myAutomata.getMenu().get(7).getSum().toString());
            taCoin.setEditable(true);
            setAbleBev();
            btnTake.setDisable(true);
        }
        else if ((!btnOnOff.isSelected()) && (States.WAIT == myAutomata.getState())){
           // flag = false;
            myAutomata.off();
            taMessage.setText ("Включи меня");
            setDisableBev();
            btnTake.setDisable(true);
            taCoin.setText("");
            taCoin.setEditable(false);

        }
        else{
            taMessage.setText ("Не могу отключиться");
            taMessage.setEditable(false);
            taCoin.setEditable(true);
        }
    }

    @FXML
    public void btnPayClick(){
        if(!taCoin.getText().isEmpty() && ((myAutomata.getState() == States.WAIT) || (myAutomata.getState() == States.ACCEPT))){
            try {
                if (Integer.parseInt(taCoin.getText()) < 0){
                    taMessage.setText("Очень смешно. Сумма должна быть больше 0");
                    taCoin.setText("");
                    return;
                }
                myAutomata.coin(Integer.parseInt(taCoin.getText()));
                taMessage.setText ("Внесено " + myAutomata.getCash() + " руб. Выберете напиток");
                taMessage.setWrapText(true);
                taCoin.setText("");
            }
            catch(Exception e){
                if (myAutomata.getCash() <= 0) {
                    taMessage.setText("Положите нормальные деньги");
                    taCoin.setText("");
                }
                else{
                    taMessage.setText("Положите нормальные деньги. Внесено " + myAutomata.getCash() + " руб.");
                    taMessage.setWrapText(true);
                    taCoin.setText("");
                }
            }
        }
    }

    @FXML
    public void btnCancelClick(){
        int resCancel = myAutomata.returnCash();
        taCoin.setText("");
        if (resCancel > 0){
            taMessage.setText ("Заберите ваши " + resCancel + " руб.");
            btnTake.setStyle("-fx-border-color: white; -fx-background-color: blue; -fx-border-width: 2; -fx-border-radius: 3;" );
            btnTake.setOpacity(1.0);
            btnTake.setText("Заберите деньги");
            btnTake.setDisable(false);
            taCoin.setText("");
            taCoin.setEditable(false);
            btnOnOff.setDisable(true);
            setDisableBev();
        }
        else{
            taMessage.setText ("Дай мне денег");
        }
    }

    public void btnBevClick(String bev) {
        if (States.OFF == myAutomata.getState()) {
            taMessage.setText("");
        } else if (States.ACCEPT != myAutomata.getState()) {
            taMessage.setText("Дай мне денег");
            taMessage.setWrapText(true);
        } else {
            Pair res = myAutomata.choice(bev);
            taMessage.setText(res.getMessage() + res.getSum() + " руб.");
            taMessage.setWrapText(true);
            if (res.getSum() >= 0) {
                btnTake.setStyle("-fx-border-color: white; -fx-background-color: blue; -fx-border-width: 2; -fx-border-radius: 3;");
                btnTake.setOpacity(1.0);
                btnTake.setText("Заберите напиток");
                btnTake.setDisable(false);
                //ableToSwitchOff = false;
                taCoin.setEditable(false);
                setDisableBev();
                btnOnOff.setDisable(true);
            }
        }
    }

    @FXML
    public void btnOneClick() {
        btnBevClick(btnOne.getText());
    }

    @FXML
    public void btnTwoClick() {
        btnBevClick(btnTwo.getText());
    }

    @FXML
    public void btnThreeClick() {
        btnBevClick(btnThree.getText());
    }

    @FXML
    public void btnFourClick() {
        btnBevClick(btnFour.getText());
    }

    @FXML
    public void btnFiveClick() {
        btnBevClick(btnFive.getText());
    }

    @FXML
    public void btnSixClick() {
        btnBevClick(btnSix.getText());
    }

    @FXML
    public void btnSevenClick() {
        btnBevClick(btnSeven.getText());
    }

    @FXML
    public void btnEightClick() {
        btnBevClick(btnEight.getText());
    }

    @FXML
    public void btnTakeClick() {
        if (States.WAIT == myAutomata.getState()){
            taMessage.setText("Дай мне денег");
            btnTake.setOpacity(0.0);
            btnTake.setDisable(true);
            btnTake.setStyle("-fx-border-color: white; -fx-background-color: inherit; -fx-border-width: 2; -fx-border-radius: 3;" );
            btnTake.setText("Забрать");
           // ableToSwitchOff = true;
            taCoin.setEditable(true);
            btnOnOff.setDisable(false);
            setAbleBev();
        }
    }
//making all buttons and labels connected with putting money and choosing beverage disable
    public void setDisableBev(){
        btnOne.setDisable(true);
        btnTwo.setDisable(true);
        btnThree.setDisable(true);
        btnFour.setDisable(true);
        btnFive.setDisable(true);
        btnSix.setDisable(true);
        btnSeven.setDisable(true);
        btnEight.setDisable(true);
        lblOne.setOpacity(0.34);
        lblTwo.setOpacity(0.34);
        lblThree.setOpacity(0.34);
        lblFour.setOpacity(0.34);
        lblFive.setOpacity(0.34);
        lblSix.setOpacity(0.34);
        lblSeven.setOpacity(0.34);
        lblEight.setOpacity(0.34);
        btnCancel.setDisable(true);
        btnPay.setDisable(true);
        lblChoice.setOpacity(0.34);
        lblGive.setOpacity(0.34);
        btnPay.setOpacity(0.34);
        btnCancel.setOpacity(0.34);
    }
    //making all buttons and labels connected with putting money and choosing beverage able
    public void setAbleBev(){
        btnOne.setDisable(false);
        btnTwo.setDisable(false);
        btnThree.setDisable(false);
        btnFour.setDisable(false);
        btnFive.setDisable(false);
        btnSix.setDisable(false);
        btnSeven.setDisable(false);
        btnEight.setDisable(false);
        lblOne.setOpacity(1.0);
        lblTwo.setOpacity(1.0);
        lblThree.setOpacity(1.0);
        lblFour.setOpacity(1.0);
        lblFive.setOpacity(1.0);
        lblSix.setOpacity(1.0);
        lblSeven.setOpacity(1.0);
        lblEight.setOpacity(1.0);
        btnCancel.setDisable(false);
        btnPay.setDisable(false);
        lblChoice.setOpacity(1.0);
        lblGive.setOpacity(1.0);
        btnCancel.setOpacity(1.0);
        btnPay.setOpacity(1.0);

    }

}
