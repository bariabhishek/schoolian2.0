package com.wikav.voulu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public  class ConnectivityReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
       /* if(status.isEmpty()) {
            status="No Internet Connection";
            Log.i("sssp",status);

        }*/
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        Log.i("sssp",status);
    }
}
