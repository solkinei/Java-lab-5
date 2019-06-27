package sample;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Automata {
    private int cash;
   // private HashMap<String, Integer> menu;
    private ArrayList<Pair> menu;
    private States state;
    private String beverage; //for chosen beverage
    private Pair result = new Pair(0, null);

    //constructor for creating the new Automata
    public Automata(String nameOfFile) {
        this.cash = 0; //initial amount of cash for the new Automata
        this.state = States.OFF; //initial state for the new Automata
        //this.menu = new HashMap<String, Integer>();
        this.menu = new ArrayList<Pair>();

        //creating menu
        BufferedReader breader = null; //buffered stream for reading strings of menu from the file
        String tmpString; //temporary string for reading from the file in format "beverage=price"
        try {
            breader = new BufferedReader(new FileReader(nameOfFile));
            while ((tmpString = breader.readLine()) != null) {
                String[] dividedStrings = tmpString.split("=", 2);
                //this.menu.put(dividedStrings[0],(Integer.parseInt(dividedStrings[1]))); //put string to the menu
                Pair pairBev = new Pair(Integer.parseInt(dividedStrings[1]), dividedStrings[0]);
                menu.add(pairBev);
            }
            breader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.beverage = null;
    }

    //getters and setters
    public int getCash() {
        return cash;
    }

    //current state for user
    public States getState() {
        return state;
    }

    public Pair getResult() { return result; }

    public ArrayList<Pair> getMenu() {
        return menu;
    }

    public String getBeverage() {
        return beverage;
    }

    public void setResult(Integer sum, String message) {
        this.result.setMessage(message);
        this.result.setSum(sum);
    }

    //switching-on
    public void on() {
        //checking the current state
        if (States.OFF == state) {
            state = States.WAIT;
        }
    }

    //switching off
    public void off() {
        //checking the current state
        if (States.WAIT == state) {
            state = States.OFF;
        }
    }

    //getting coins
    public int coin(int cash) {
        if ((States.WAIT == state) || (States.ACCEPT == state)) {
            state = States.ACCEPT;
            this.cash = this.cash + cash;
        }
        return getCash();
    }

    //cancelling the current session and returning money
    public int returnCash() {
        if (States.ACCEPT == state) {
            int cash = getCash();
            this.cash = 0;
            state = States.WAIT;
            return cash;
        }
        else {
            return 0;
        }
    }

    //user's choice of beverage
    public Pair choice(String beverage) {
        if (States.ACCEPT != state) {
            return null;
        }
        Pair res = new Pair(0, null);
        //checking if menu contains this beverage
        boolean found = false;
        int i = 0;
        for (i = 0; i < menu.size(); i++){
            if (beverage.equals(menu.get(i).getMessage())){
                found = true;
                state = States.CHECK;
                int check = check(i);
                if (check < 0) {
                    lackOfCash(check);
                }
                else {
                    cook(check);
                }
                res.setSum(result.getSum());
                res.setMessage(result.getMessage());
                setResult(0, null);
                break;
            }
        }
        if (!found) {
            res.setSum(0);
            res.setMessage("There is no such beverage in menu! Choose again!");
        }
        return res;
    }

    private void lackOfCash(int check){
        state = States.ACCEPT;
        setResult(check, "Добавь денег. Не хватает ");
    }

    //checking the amount of money
    private int check(int i) {
        int res = cash - menu.get(i).getSum();
        if (res >= 0) {
            this.beverage = menu.get(i).getMessage();
        }
        return res;
    }

    //delay as imitation of cooking process, returning the name of product and excess cash
    private void cook(int check) {
        if (States.CHECK == state) {
            state = States.COOK;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish(check);
        }
    }

    //finishing
    private void finish(int check) {
        if (States.COOK == state) {
            setResult(check, "Возьми " + beverage.toLowerCase() + ". Сдача: ");
            beverage = null;
            cash = 0;
            state = States.WAIT;
        }
    }
}
