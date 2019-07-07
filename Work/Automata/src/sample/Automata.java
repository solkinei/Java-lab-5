package sample;

import javafx.application.Platform;

import java.util.ArrayList;
import java.lang.*;
import java.util.Timer;
import java.util.TimerTask;

enum STATES {OFF, WAIT, ACCEPT, CHECK, COOK}
public class Automata {
    private int cash;
    private ArrayList<String> menu = new ArrayList<String>();
    private ArrayList<Integer> prices = new ArrayList<Integer>();
    private STATES state;
    int change;

    Automata(){
        this.state=STATES.OFF;
        this.cash = 0;
        this.change = 0;

    }

    public ArrayList getMenu(){
        return menu;
    }
    public ArrayList getPrices(){
        return prices;
    }

    public STATES on(){
        if(state == STATES.OFF)
            state = STATES.WAIT;
        return state;
    }
    public STATES off(){
        if(state == STATES.WAIT)
            state = STATES.OFF;
        return  state;
    }
    public int coin(int c){
        if (state == STATES.WAIT)
                 this.cash+=c;
        return this.cash;
    }
    public ArrayList [] setMenu(){
        ArrayList buf [] = {menu, prices};
       if (state == STATES.WAIT){
            menu.add("Cappuccino");
            menu.add("Americana");
            menu.add("Latte");
            menu.add("Tea");
            prices.add(100);
            prices.add(80);
            prices.add(150);
            prices.add(50);
            }
       return buf;
    }

    public STATES getState(){
        return state;

    }

    public String choice(int num){
        if (state == STATES.WAIT){
            state = STATES.CHECK;
            int sum = prices.get(num-1);
            prices.clear();
            prices.add(sum);
        }
        return menu.get(num-1);
    }
    public STATES check(){
        if (state == STATES.CHECK) {
            if (prices.get(0) <= cash){
                state = STATES.ACCEPT;
            }else{
                cancel();
            }
        }
        return state;
    }
    public int cancel(){
        state = STATES.WAIT;
        this.change = this.cash;
        this.cash=0;
        return this.change;
    }
    public STATES cook(){
       try {
           if (state == STATES.ACCEPT) {
               state = STATES.COOK;
               Thread.sleep(5000);
           }
       }
       catch (InterruptedException ex){
           System.out.println(ex.getMessage());

       }
        return state;
    }
    public String finish(){
        if(state == STATES.COOK) {
            state = STATES.WAIT;
            if (cash>prices.get(0)) {
                this.change = this.cash - prices.get(0);
                this.cash = 0;
            }else {
                this.change = 0;
                this.cash = 0;
            }
        }
        return "Your change: " + change+ " coins.";
    }
}
