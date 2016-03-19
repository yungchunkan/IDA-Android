package clas;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

public class Discount {
    private String id;
    private String title;
    private String desc;
    private String image;
    private String expire;
    private String restId;
    private List<String> terms;

    public Discount(String id, String title, String desc, String image, String expire, String restId, List<String> terms) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.expire = expire;
        this.restId = restId;
        this.terms = terms;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        Bitmap bitmap = null;
        String image_url = null;
        try {
            if (image.length() == 0) {
                image_url = null;
            } else {
                image_url = App.DOMAIN + "/userdata/Discount/"
                        + image;
            }
            Log.v("image", image_url);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return image_url;
    }

    public String getExpire() {
        return expire;
    }

    public String getRestId() {
        return restId;
    }

    public List<String> getTerms() {
        return terms;
    }
}
