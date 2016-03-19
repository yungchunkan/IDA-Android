package clas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by ricky on 3/10/15.
 */
public class BroadcastReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                break;
        }
    }
}
