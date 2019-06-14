package com.wikav.schoolian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public  class ConnectivityReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        int [] type={ ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        if(isNetworkAvailable(context,type))
        {
            return;
        }
        else {
            Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isNetworkAvailable(Context context, int[] typeNetworks)
    {
       try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
           for (int ni : typeNetworks) {
                NetworkInfo netInfo = cm.getNetworkInfo(ni);
               if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                   return true;
               }
           }
       }catch (Exception e)
       {
            return  false;
       }
            return false;
    }
}
