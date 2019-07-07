package sample;

import java.util.Map;

public class Automata {

    private int cash;
    private States state;
    protected Menu mapOfPriceAndDrink = new Menu();

    public Automata() {
        this.state = States.OFF;
        this.cash = 0;
    }

    public void on() {
        setState(States.WAIT);
    }

    public void off() {
        setState(States.OFF);
    }

    public void finish() {
        //System.out.println("Put your drink please");
        setState(States.WAIT);
    }

    public Map getMenu() {
        return mapOfPriceAndDrink.getMenu();
    }

    public void coin(int money) {
        setCash(money);
        setState(States.ACCEPT);
    }

    public void cook() {
        //System.out.println("Your drink is cooking");
        setState(States.COOK);
    }

    public void check() {
        setState(States.CHECK);
    }

    public int oddMoney(int indexOfDrink) {
        return this.cash - mapOfPriceAndDrink.getPriceofIndex(indexOfDrink);
    }

    public void cancel() {
        setState(States.WAIT);
    }

    public int getCash() {
        return this.cash;
    }

    public States getState() {
        return this.state;
    }


    public void setCash(int cash) {
        if (cash > 0) {
            this.cash += cash;
        } else {
            this.cash = cash;
        }
    }

    public void setState(States state) {
        this.state = state;
    }

    public void choice(int button) {
        if (button > mapOfPriceAndDrink.getMenu().size()) {
            cancel();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 0) {
            cook();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 1) {
            cook();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 2) {
            cook();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 3) {
            cook();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 4) {
            cook();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 5) {
            cook();
        } else if (cash >= mapOfPriceAndDrink.getPriceofIndex(button) && button == 6) {
            cook();
        } else {
            cancel();
            System.out.println("Not enough money");
        }

    }

}
