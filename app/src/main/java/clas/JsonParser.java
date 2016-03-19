package clas;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ricky on 6/7/15.
 */
public class JsonParser {
    private String TAG = "JsonParser";

    public JsonParser() {
    }

    public JSONArray getRows(String result) {
        try {
            JSONObject json = new JSONObject(result);
            return json.getJSONArray("rows");
        } catch (Exception e) {
            return null;
        }
    }

    public List<District> extractDistrict(String result) {
        List<District> list = new ArrayList<>();
        try {
            JSONArray rows = getRows(result);
            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);
                list.add(new District(obj.getString("DistrictID"), obj.getString("DistrictName")));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        } catch (NullPointerException ne) {
            Log.e(TAG, "Null parsing data " + ne.toString());
        }
        return list;
    }

    public List<Dish> extractDishs(String result) {
        List<Dish> list = new ArrayList<>();
        try {
            JSONArray rows = new JSONArray(result);
            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);
                list.add(new Dish(obj.getString("TypeID"), obj.getString("TypeName")));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }
        return list;
    }

    public Restaurant extractRestaurant(String result, String restId) {
        Restaurant restaurant = null;
        try {
            JSONObject rows = new JSONObject(result);
            restaurant = new Restaurant(restId,
                    rows.getString("RestName"),
                    rows.getString("Address"),
                    rows.getString("Tel"),
                    rows.getString("RestInfo"),
                    rows.getString("TypeID"),
                    rows.getString("Image"),
                    rows.getString("DistrictID"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(e.getClass().getSimpleName(), result);
        }
        return restaurant;
    }

    public List<String> extractTopRestaurant(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray thisarray = new JSONArray(result);
            for (int i = 0; i < thisarray.length(); i++) {
                JSONObject e = thisarray.getJSONObject(i);
                list.add(e.getString("Restaurant"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Json Helper", result);
        }
        return null;
    }

    public String extractRestaurantType(String result) {
        String type = null;
        try {
            JSONObject rows = new JSONObject(result);
            type = rows.getString("TypeName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    public List<Restaurant> extractAllRestaurant(String result) {
        List<Restaurant> list = new ArrayList<>();
        try {
            JSONArray rows = getRows(result);
            for (int i = 0; i < rows.length(); i++) {
                JSONObject e = rows.getJSONObject(i);
                list.add(extractRestaurant(result, e.getString("RestID")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //{"Key":"error","Value":"empty","Content":"無法找到餐廳"}
    public List<Restaurant> extractRestaurantByDD(String result) {
        List<Restaurant> list = new ArrayList<>();
        try {
            JSONArray rows = new JSONArray(result);
            for (int i = 0; i < rows.length(); i++) {
                JSONObject rest = rows.getJSONObject(i);

                list.add(new Restaurant(rest.getString("RestID"),
                        rest.getString("RestName"),
                        rest.getString("Address"),
                        rest.getString("Tel"),
                        rest.getString("RestInfo"),
                        rest.getString("TypeID"),
                        rest.getString("Image"),
                        rest.getString("DistrictID")));
            }
            return list;
        }
        catch (JSONException je) {
            try {
                JSONObject obj = new JSONObject(result);
                if (obj.getString("Value").equals("empty")) {
                    return list;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Discount extractDiscountInfoById(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject rows = new JSONObject(result);
            JSONArray terms = rows.getJSONArray("Terms");
            for (int u = 0; u < terms.length(); u++) {
                JSONObject f = terms.getJSONObject(u);
                list.add(f.getString("Term"));
            }
            return new Discount(rows.getString("DiscountID"),
                    rows.getString("Title"),
                    rows.getString("Desc"),
                    rows.getString("Image"),
                    rows.getString("ExpireDate"),
                    rows.getString("RestID"),
                    list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> extractAllDiscountId(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray rows = new JSONArray(result);
            for (int i = 0; i < rows.length(); i++) {
                JSONObject discount = rows.getJSONObject(i);
                list.add(discount.getString("DiscountID"));
            }
            //return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Employ> extractAllEmploy(String result) {
        List<Employ> list = new ArrayList<>();
        try {
            JSONArray rows = new JSONArray(result);
            for (int i = 0; i < rows.length(); i++) {
                JSONObject jEmploy = rows.getJSONObject(i);
                Employ employ = new Employ(jEmploy.getString("EmployID"),
                        jEmploy.getString("Position"),
                        jEmploy.getString("RestName"));
                list.add(employ);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employ extractEmploy(String result) {
        try {
            JSONObject rows = new JSONObject(result);
            Employ employ = new Employ(rows.getString("EmployID"),
                    rows.getString("Position"),
                    rows.getString("Responsibilities"),
                    rows.getString("Requirement"),
                    rows.getString("Term"),
                    rows.getString("Salary"),
                    rows.getString("Quota"),
                    rows.getString("StartTime"),
                    rows.getString("EndTime"),
                    rows.getString("RestID"),
                    rows.getBoolean("IsPartTime"));
            //rows.getString("RestName"));
            return employ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LatLng extractLatLngByAddress(String result) {
        LatLng latLng = null;
        try {
            JSONObject resultObj = new JSONObject(result);
            JSONArray resultArray = resultObj.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject e = resultArray.getJSONObject(i);
                JSONObject geometryObj = e.getJSONObject("geometry");
                JSONObject location = geometryObj.getJSONObject("location");
                latLng = new LatLng(Double.parseDouble(location.getString("lat")), Double.parseDouble(location.getString("lng")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }
}
