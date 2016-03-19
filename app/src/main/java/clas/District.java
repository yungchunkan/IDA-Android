package clas;

import java.util.ArrayList;
import java.util.List;

public class District {
    String id;
    String name;

    public District(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<String> getAllName(List<District> districtlist) {
        List<String> districtName = new ArrayList<>();
        for (int i = 0; i < districtlist.size(); i++) {
            districtName.add(districtlist.get(i).name);
        }
        return districtName;
    }

    public static int getIdByName(List<District> districtlist, int index) {
        return Integer.parseInt(districtlist.get(index).id);
    }
}
