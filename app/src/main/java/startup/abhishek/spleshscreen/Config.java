package startup.abhishek.spleshscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Config {

        private Context context;
        private Context contet;
///mera code test okkk
        public Config(Context context)
        {
            this.context=context;
        }


        public boolean haveNetworkConnection() {

            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
        }

        public void CheckConnection()
        {
            if (!haveNetworkConnection())
            {
                Intent in=new Intent(context,NoInternetActivity.class);
                context.startActivity(in);
                ((Activity) context).finish();
            }
        }
    }

