package sample;
import javafx.util.Pair;

import java.util.List;

public interface Automata {
    boolean on();
    boolean off();
    boolean coin(int value);
    List<Pair<String, Integer>> getMenu();
    State getState();
    boolean choice(int goodsNumber);
    boolean check(int goodsNumber);
    boolean cancel();
    boolean cook(int goodsNumber);
    int finish(int goodsPrice);
    int getCash();
}
