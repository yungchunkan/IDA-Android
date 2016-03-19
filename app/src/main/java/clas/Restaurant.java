package clas;

import android.util.Log;

public class Restaurant {
    private String restID;
    private String restName;
    private String address;
    private String tel;
    private String restInfo;
    private String typeId;
    private String imagePath;
    private String districtId;

    public Restaurant(String id, String name, String address, String tel, String info, String typeId, String image, String districtId) {
        // TODO Auto-generated constructor stub
        this.restID = id;
        this.restName = name;
        this.address = address;
        this.tel = tel;
        this.imagePath = image;
        this.restInfo = info;
        this.typeId = typeId;
        this.districtId = districtId;
    }

    public String getRestID() {
        return restID;
    }

    public String getRestName() {
        return restName;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public String getRestInfo() {
        return restInfo;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getImageUrl() {
        String image_url = null;
        Log.v("image", imagePath + ":" + restName);
        try {
            if (imagePath.length() == 0) {
                image_url = null;
            } else {
                image_url = App.DOMAIN + "/userdata/restaurant/"
                        + restID + "/" + imagePath;
            }
            Log.v("image", image_url);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return image_url;
    }

    public String getDistrictId() {
        return districtId;
    }
}
