package clas;

//import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 6/7/15.
 */
public class ServerHandler {
    private String getDistrict = App.DOMAIN + "/ServicesProvider/getdistricts.ashx";
    private String getDishes = App.DOMAIN + "/ServicesProvider/GetDishs.ashx";
    private String getRestByDD = App.DOMAIN + "/ServicesProvider/SearchRestaurant.ashx?";
    private String getRestById = App.DOMAIN + "/ServicesProvider/GetRestaurantInfoByID.ashx?restID=";
    private String getAllRestaurant = App.DOMAIN + "/ServicesProvider/GetRestaurantNames.ashx";
    private String getRestaurantTypeById = App.DOMAIN + "/ServicesProvider/GetRestauranttypebyid.ashx?typeid=";
    private String getTopRestaurant = App.DOMAIN + "/ServicesProvider/GetRanking.ashx?qty=10&os=";
    private String getRecommendRestaurant = App.DOMAIN + "/ServicesProvider/GetRanking.ashx?qty=15";
    private String getDiscount = App.DOMAIN + "/ServicesProvider/GetMobileDiscountList.ashx?qty=20";
    private String getDiscountInfoById = App.DOMAIN + "/ServicesProvider/GetDiscountInfoByID.ashx?discountID=";
    private String getEmployInfoById = App.DOMAIN + "/ServicesProvider/GetEmployInfoByID.ashx?employID=";
    private String getAllEmploy = App.DOMAIN + "/ServicesProvider/GetMobileEmployList.ashx?qty=10";
    private String getLocation = "http://maps.google.com/maps/api/geocode/json?sensor=true&address=";

    /* NEW SERVER FUNCTION */
    private String addUser = App.DOMAIN + "/ServicesProvider/AddUser.ashx?userId=%s&userPw=%s";


    private JsonParser helper;

    public ServerHandler() {
        helper = new JsonParser();
    }

    public List<District> getAllDistrict() {
        String result = HttpHelper.executeHttpGet(getDistrict);
        return helper.extractDistrict(result);
    }

    public List<Dish> getAllDishes() {
        String result = HttpHelper.executeHttpGet(getDishes);
        return helper.extractDishs(result);
    }

    public Restaurant getRestById(String restId) {
        String path = getRestById + restId;
        String result = HttpHelper.executeHttpGet(path);
        return helper.extractRestaurant(result, restId);
    }

    public List<Restaurant> getTopRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        String result = HttpHelper.executeHttpGet(getTopRestaurant);
        List<String> sList = helper.extractTopRestaurant(result);
        for (String str : sList) {
            list.add(getRestById(str));
        }
        return list;
    }

    public String getRestTypebyid(String id) {
        String result = HttpHelper.executeHttpGet(getRestaurantTypeById + id);
        return helper.extractRestaurantType(result);
    }

    public List<Restaurant> getAllRestaurants() {
        return getRestByDD(0, 0, "");
        //String result = HttpHelper.executeHttpGet(getAllRestaurant);
        //return helper.extractAllRestaurant(result);
    }

    public List<Restaurant> getRestByDD(int locationIndex, int dishIndex, String keyword) {
        String path = getRestByDD + "districtID=" + locationIndex + "&dishID=" + dishIndex + "&keyword=" + keyword;
        String result = HttpHelper.executeHttpGet(path);
        return helper.extractRestaurantByDD(result);
    }

    public Discount getDiscountInfoById(String id) {
        String result = HttpHelper.executeHttpGet(getDiscountInfoById + id);
        return helper.extractDiscountInfoById(result);
    }

    public List<Discount> getAllDiscount() {
        String result = HttpHelper.executeHttpGet(getDiscount);
        List<String> idList = helper.extractAllDiscountId(result);
        List<Discount> list = new ArrayList<>();
        for (String str : idList) {
            list.add(getDiscountInfoById(str));
        }
        return list;
    }

    //[{"EmployID":8,"Position":"侍應生(樓面)","RestName":"勝香園"},{"EmployID":6,"Position":"清潔女工","RestName":"金仔米線"}]
    public List<Employ> getAllEmploy() {
        String result = HttpHelper.executeHttpGet(getAllEmploy);
        return helper.extractAllEmploy(result);
    }

    public Employ getEmployInfoById(String id) {
        Employ employ;
        String result = HttpHelper.executeHttpGet(getEmployInfoById + id);
        employ = helper.extractEmploy(result);
        employ.setRestName(getRestById(employ.getRestid()).getRestName());
        return employ;
    }

    public LatLng getLatlngbyaddress(String address) {
        address = address.replace(" ", "");
        String path = getLocation + address;
        String result = HttpHelper.executeHttpGet(path);
        return helper.extractLatLngByAddress(result);
    }

    //[{"Rank":1,"Restaurant":25},{"Rank":2,"Restaurant":44},
    // {"Rank":3,"Restaurant":26},{"Rank":4,"Restaurant":24},
    // {"Rank":5,"Restaurant":48}]
    public List<Restaurant> getRecommendRestaurant() {
        String json = HttpHelper.executeHttpGet(getRecommendRestaurant);
        List<String> restIds = helper.extractTopRestaurant(json);
        List<Restaurant> result = new ArrayList<>();
        if (restIds != null) for (String str : restIds) result.add(getRestById(str));
        return result;

    }


    /*
    public String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + ","
                + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters;

        return url;
    }
    */

    /* NEW SERVER FUNCTION */

    public boolean addUser(String email, String pw) {
        String url = String.format(addUser, email, pw);
        String json = HttpHelper.executeHttpGet(url);
        return true;
    }
}
