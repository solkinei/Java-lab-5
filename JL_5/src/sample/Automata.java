package sample;

import java.util.ArrayList;
enum State //текущее состояние автомата;
{OFF, WAIT, ACCEPT, CHECK, COOK}

public class Automata {
 private float cash; //текущея сумма
 private State state;//начальное состояние

public Automata(){
    this.cash=0;
    this.state=State.OFF;//начальное состояние
}

    public void  on(){
      if (state == State.OFF ){
          state = State.WAIT;
      }
  }

    public void off(){
      if (state == State.WAIT ){
          state = State.OFF;
      }
  }

    public State getState(){
       return state;
    }

    public ArrayList[] getMenu(){
     ArrayList<String> menuItem = new ArrayList();
     ArrayList<Float> menuItemPrice = new ArrayList();
        menuItem.add("Espresso");
            menuItemPrice.add(40f);
        menuItem.add("Cappuccino");
            menuItemPrice.add(40f);
        menuItem.add("Americano");
            menuItemPrice.add(30f);
        menuItem.add("Iced coffee");
            menuItemPrice.add(50f);
        ArrayList[] fullMenu={menuItem, menuItemPrice};
        return fullMenu;
    }

    public void  coin( float money ){
        if(state == State.WAIT || state == State.ACCEPT){
            state = State.ACCEPT;
            cash+=money;
        }
    }

    public float getCash() {
        return cash;
    }

    private boolean check(float price){
        return (state == State.CHECK)&&(cash >= price);
    }

    public float cancel(){
        float  change=0;
        if (state == State.CHECK || state == State.ACCEPT){
            state = State.WAIT;
            change=cash;
            cash=0;
        }
        return change;
    }
    private void cook(int itemNumber){
        if ( state == State.CHECK){
            state = State.COOK;
            // cooking menu[0].get(itemNumber)
        }
    }

    private void finish() {
        if (state == State.COOK) {
            state = State.WAIT;
            System.out.println("Take your drink");
        }
    }

     public float choice (int itemNumber){
         float  change=0;
         try {
             if (state == State.ACCEPT) {
                 state = State.CHECK;
                 ArrayList[] menu = getMenu();
                 float price = (Float) menu[1].get(itemNumber);
                 if (check(price)) {
                     change=cash-= price;
                     cash=0;
                     cook(itemNumber);
                     finish();
                 }
                 else {
                     change = cancel();
                 }
             }
         }
         catch (IndexOutOfBoundsException e1){
             System.out.println("there is no such item in the menu!! " + e1);
             change = cancel();
             System.out.println("Take the change: " + change);
         }
     return change;
     }
}
