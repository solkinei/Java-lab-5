package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Menu {
    private String[] arrayListOfDrinkAndPrice;
    protected Map<String, Integer> menu = new TreeMap<String, Integer>();
    private ArrayList<String> drink = new ArrayList<String>();
    private ArrayList<Integer> price = new ArrayList<Integer>();

    public Map<String, Integer> getMenu() {
        return menu;
    }

    public Map readMenu() {


        File file = new File("menu");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                arrayListOfDrinkAndPrice = scanner.nextLine().split(":");
                menu.put(arrayListOfDrinkAndPrice[0], Integer.parseInt(arrayListOfDrinkAndPrice[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");

        }

        return menu;
    }

    public Integer getPriceofIndex(int index) {
        int i = 0;
        for (Map.Entry<String, Integer> entry : menu.entrySet()) {
            price.add(i, entry.getValue());
            i++;
        }
        return price.get(index);
    }

    public String getDrinkOfIndex(int index) {
        int i = 0;
        for (Map.Entry<String, Integer> entry : menu.entrySet()) {
            drink.add(i, entry.getKey());
            i++;
        }
        return drink.get(index);
    }

}
