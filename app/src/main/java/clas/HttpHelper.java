package clas;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by ricky on 6/7/15.
 */
public class HttpHelper {

    public HttpHelper() {
    }

    public static String executeHttpGet(String url) {
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;

        try {
            response = client.execute(request);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            result = "[ERROR] " + e.toString();
        }

        if (App.SHOW_JSON) Log.d("SHOW_JSON", result);

        return result;
    }
}
