package clas;

import java.util.ArrayList;
import java.util.List;

public class Dish {

    String id;
    String name;

    public Dish(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getAllName(List<Dish> dishslist) {
        List<String> dishsname = new ArrayList<>();
        for (int i = 0; i < dishslist.size(); i++) {
            dishsname.add(dishslist.get(i).name);
        }
        return dishsname;
    }

    public static int getIdByName(ArrayList<Dish> dishslist, int index) {
        return Integer.parseInt(dishslist.get(index).id);
    }
}
