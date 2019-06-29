package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CoffeAutomata implements Automata {

    private State mState = State.OFF;
    private int mCash = 0;
    private List<Pair<String, Integer>> mGoods = new ArrayList<>();

    //Visible for testing
    void init(){
        //read config file here
        mGoods.add(new Pair<>("latte", 55));
        mGoods.add(new Pair<>("americano", 20));
        mGoods.add(new Pair<>("irish", 70));
        mGoods.add(new Pair<>("espresso", 30));
        mGoods.add(new Pair<>("milk", 50));
        mGoods.add(new Pair<>("water", 10));
    }

    //used for testing
    @Override
    public int getCash(){
        return mCash;
    }

    //used for testing
    void setCash(int cash){
        mCash = cash;
    }

    //used for testing
    void setState(State state){
        mState = state;
    }

    @Override
    public boolean on() {
        if(mState != State.OFF) return false;
        init();
        mState = State.WAIT;
        return true;
    }

    @Override
    public boolean off() {
        if(mState == State.WAIT) {
            mState = State.OFF;
            return true;
        }
        return false;
    }

    @Override
    public boolean coin(int value) {
        if(mState != State.WAIT && mState != State.ACCEPT) return false;
        mCash += value;
        mState = State.ACCEPT;
        return true;
    }

    @Override
    public List<Pair<String, Integer>> getMenu() {
        return mGoods;
    }

    @Override
    public State getState() {
        return mState;
    }

    @Override
    public boolean choice(int goodsNumber) {
        if(mState != State.ACCEPT) return false;
        mState = State.CHECK;
        return check(goodsNumber);

    }

    @Override
    public boolean check(int goodsNumber) {
        if(mState != State.CHECK) return false;
        if(mGoods.get(goodsNumber).getValue() <= mCash){
            return cook(goodsNumber);
        } else {
            mState = State.ACCEPT;
            return false;
        }
    }

    @Override
    public boolean cancel() {
        if(mState != State.ACCEPT && mState != State.CHECK) return false;
        mCash = 0;
        mState = State.WAIT;
        return true;
    }

    @Override
    public boolean cook(int goodsNumber) {
        if(mState != State.CHECK) return false;
        mState = State.COOK;
        finish(mGoods.get(goodsNumber).getValue());
        return true;
    }

    @Override
    public int finish(int goodsPrice) {
        if(mState != State.COOK) return 0;
        int change = mCash - goodsPrice;
        //return change
        mCash = 0;
        mState = State.WAIT;
        return change;
    }
}

