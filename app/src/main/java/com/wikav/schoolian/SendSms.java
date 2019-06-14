package com.wikav.schoolian;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class SendSms{

    private String authkey = "235212AZpQkT0BmNm5b8be6ed";
    //Multiple mobiles numbers separated by comma
    private String mobiles ;
    //Sender ID,Wh
    // ile using route4 sender id should be 6 characters long.
    private String senderId = "MVOULU";
    //Your message to send, Add URL encoding here.
    private String message=null;
    //define route
    private String route="4";

    URLConnection myURLConnection=null;
    URL myURL=null;
    BufferedReader reader=null;

    //encoding message
    //String encoded_message=URLEncoder.encode(message);

    //Send SMS API
    private String mainUrl="http://api.msg91.com/api/sendhttp.php?country=91";

    public SendSms(String mobile, String msg)
    {
        mobiles=mobile;
        message=msg;
    }
    //Prepare parameter string
    public void send() {
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("&sender=" + senderId);
        sbPostData.append("&route=" + route);
        sbPostData.append("&mobiles=" + mobiles);
        sbPostData.append("&authkey=" + authkey);
        sbPostData.append("&message=" + message);


//final string
        mainUrl = sbPostData.toString();

        try {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", "" + response);

            //finally close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

