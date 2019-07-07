package sample;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.Field;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class Controller {
    Automata automata = new Automata("drinks.txt");
    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    //Очень черная магия и антиматерия, которые не дают ввести в спинер что-то кроме цифр
    NumberFormat format = NumberFormat.getIntegerInstance();
    UnaryOperator<TextFormatter.Change> filter = c -> {
        if (c.isContentChange()) {
            ParsePosition parsePosition = new ParsePosition(0);
            // NumberFormat evaluates the beginning of the text
            format.parse(c.getControlNewText(), parsePosition);
            if (parsePosition.getIndex() == 0 ||
                    parsePosition.getIndex() < c.getControlNewText().length()) {
                // reject parsing the complete text failed
                return null;
            }
        }
        return c;
    };
    TextFormatter<Integer> moneyFormated = new TextFormatter<Integer>(
            new IntegerStringConverter(), 0, filter);


    public void initialize(){
        URL url = getClass().getResource("/background.jpg");
        background.setImage(new Image(String.valueOf(url)));
        setMenu(menu);
        setStatus(status);
        setMyBottons(myBottons);
        getOnOff();
        setMoneyCounter();
        setWorking();
        instructionPane.setVisible(false);
        //черное колдунство, которое сохраняет значение, введеное с клавиатуры в спинере
        //без использования клавиши Enter
        for (Field field : getClass().getDeclaredFields()) {
            try {
                Object obj = field.get(this);
                if (obj != null && obj instanceof Spinner)
                    ((Spinner) obj).focusedProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue) {
                            ((Spinner) obj).increment(0);
                        }
                    });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    Pane mainPain;

    @FXML
    Pane instructionPane;

    @FXML
    ImageView background;

    @FXML
    Pane moneySpinner;
    Spinner moneyCounter = new Spinner();

    public
    void setMoneyCounter () {
        moneySpinner.getChildren().add(moneyCounter);
        SpinnerValueFactory factory = new SpinnerValueFactory.
                IntegerSpinnerValueFactory(0, 150, 0, 5);
        moneyCounter.setValueFactory(factory);
        moneyCounter.setEditable(true);
        moneyCounter.getEditor().setTextFormatter(moneyFormated);
    }

    @FXML
    Button deposite;

    public
    void setDeposite (){
        automata.coin(Integer.parseInt(moneyCounter.getValue().toString()));
        moneyCounter.getValueFactory().setValue(0);
        String s = "Your cash " + automata.getCash() + "₽";
        instruction.setText(s);
        setStatus(status);
    }

    @FXML
    Label menu;

    private
    void setMenu ( Label menu ) {
        this.menu.setWrapText(true);
        ArrayList<String>fullMenu = automata.getMenu();
        String s = "";
        for (String item:
                fullMenu) {
            s += item + "\n\n";
        }
        this.menu.setText(s);
    }

    @FXML
    VBox onOff;

    public
    void getOnOff () {
        Button on_Off = new Button("ON");
        on_Off.setMinSize(100, 30);
        onOff.getChildren().add(on_Off);

        on_Off.setOnAction(event -> {
            if(!status.getText().equals("OFF")){
                on_Off.setText("ON");
                instructionPane.setVisible(false);
                automata.off();
                moneyCounter.getValueFactory().setValue(0);
                setStatus(status);
                setInstruction("");
            } else {
                automata.on();
                setStatus(status);
                on_Off.setText("OFF");
                setStatus(status);
                setInstruction("Insert money");
                instructionPane.setVisible(true);
            }
        });
    }

    @FXML
    Label status;

    public
    void setStatus ( Label status ) {
        status.setText(this.automata.getState().toString().toUpperCase());
    }

    @FXML
    Label instruction;


    public
    void setInstruction ( String instruction ) {
        this.instruction.setText(instruction);
    }
    @FXML
    Label working;

    public
    void setWorking () {
        working.setWrapText(true);
        working.setText("I'm busy! \n Dear Leather Basterds!\n" +
                "Don't touch my <censored> interface, please!");
        working.setVisible(false);
    }

    @FXML
    VBox myBottons;
    private
    void setMyBottons ( VBox myBottons ) {
        for(int i = 0; i < automata.getMenu().size(); i++){
            Button btn = new Button(automata.getButtonLabel().get(i).toString().toUpperCase());
            myBottons.getChildren().add(btn);
            btn.setPrefWidth(182);
            int choice = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public
                void handle ( ActionEvent event ) {
                    // защита от дурака (нажатия кнопок во время готовки)
                        myBottons.setVisible(false);
                        working.setVisible(true);
                        automata.choiceByFX(choice);
                        automata.setYourChoice(choice);
                        String s = "Your cash " + automata.getCash() + "₽";
                        setStatus(status);
                        instruction.setText(s);
                        if(automata.getState() != STATES.CHECK){
                            int priceDifferene = automata.getPrices().get(choice) - automata.getCash() ;
                            String s1 = "Please, add " + priceDifferene + "₽" + " to take your " +
                                    automata.getYourChoice().substring(3).toUpperCase() +
                                    " or change your choice";
                            myBottons.setVisible(true);
                            working.setVisible(false);
                            instruction.setWrapText(true);
                            instruction.setText(s1);
                            return;
                        }
                    pause.setOnFinished(event1 -> {
                        if(automata.getState() != STATES.WAIT){
                            automata.cook();
                            setStatus(status);
                        }
                        pause.play();
                        pause.setOnFinished(event2 -> {
                                automata.finish();
                                setStatus(status);
                                String s1 = "Your cash " + automata.getCash() + "₽" +
                                        "\n" + automata.getYourChoice().substring(3).toUpperCase()
                                        + " is READY";
                                instruction.setText(s1);
                                myBottons.setVisible(true);
                                working.setVisible(false);
                        });
                        pause.play();
                    });
                    pause.play();
                }
            });
        }
    }
}
