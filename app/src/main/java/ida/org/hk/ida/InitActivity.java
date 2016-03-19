package ida.org.hk.ida;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.FacebookSdk;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnectedOrConnecting()) {
            FacebookSdk.sdkInitialize(getApplicationContext());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(InitActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
        else {
            Toast.makeText(getApplicationContext(), "請連接網絡再重新嘗試", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
