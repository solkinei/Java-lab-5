package sample;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.ArrayList;

enum STATES {
    OFF,
    WAIT,
    ACCEPT,
    CHECK,
    COOK
}

public class Automata {
    int cash = 0;
    public ArrayList<String> menu = new ArrayList<String>();
    ArrayList<Integer> price = new ArrayList<Integer>();

    STATES state;

    // Constructor:
    Automata() {
        state = STATES.OFF;

        menu.add("Espresso");
        menu.add("Americano");
        menu.add("Latte");
        menu.add("Mocco");
        menu.add("Chocolate");
        menu.add("Kotletki s pureshkoy");
        price.add(5);
        price.add(8);
        price.add(10);
        price.add(8);
        price.add(7);
        price.add(20);
    }

    /*
    конструктор
on() - включение автомата;
off() - выключение автомата;
coin() - занесение денег на счёт пользователем;
printMenu() - отображение меню с напитками и ценами для пользователя;
printState() - отображение текущего состояния для пользователя;
choice() - выбор напитка пользователем;
check() - проверка наличия необходимой суммы;
cancel() - отмена сеанса обслуживания пользователем;
cook() - имитация процесса приготовления напитка;
finish() - завершение обслуживания пользователя.
*/

    void on() {
        if (state != STATES.OFF) {        // только выключенный автомат можно включить
            return;
        }
        state = STATES.WAIT;

    }

    void off() {
        if (state == STATES.WAIT || state == STATES.ACCEPT) {        // только в режиме ожидания автомат можно выключить
            state = STATES.OFF;
        }

    }

    void coin(int insertedCoin) {
        if (state != STATES.WAIT && state != STATES.ACCEPT) {
            return;
        }
        cash += insertedCoin;
        state = STATES.ACCEPT;
    }

    ArrayList<String> printMenu() {
        return menu;
    }

    STATES getState() {
        return state;
    }

    int getCash(){
        return cash;
    }

    void setCash(int newCash){
        cash=newCash;
    }

    boolean choice(int buttonNumber) {
        if (state==STATES.OFF){
            return false;
        }
        if (check(buttonNumber) == true) {
            cash -= price.get(buttonNumber);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                cook();
            });
            pause.play();

            return true;
        }
        cancel();
        return false;
    }

    boolean check(int buttonNumber) {
        state = STATES.CHECK;

        if (cash < price.get(buttonNumber)) {
            state = STATES.WAIT;
            return false;
        }

        return true;
    }

    void cook() {
        state = STATES.COOK;
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            finish();
        });
        pause.play();

    }

    void finish() {
        state = STATES.WAIT;
        // обслуживание завершен
    }

    void cancel() {
        if (state != STATES.ACCEPT) {
            return;
        }
        state = STATES.WAIT;
        // отмена и переход назад в WAIT
    }



}
