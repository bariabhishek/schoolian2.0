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
    private String senderId = "SCLOTP";
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
    private String mainUrl="http://api.msg91.com/api/sendhttp.php?";

    public SendSms(String mobile, String msg)
    {
        mobiles=mobile;
        message=msg;
    }
    //Prepare parameter string
    /*https://api.msg91.com/api/sendhttp.php?mobiles=Mobile no.&
    authkey=$authentication_key&route=4&sender=TESTIN&message=Hello! This is a test message&country=91*/
    public void send() {
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append( "mobiles=" ).append( mobiles );
        sbPostData.append( "&authkey=" ).append( authkey );
        sbPostData.append( "&route=" ).append( route );
        sbPostData.append( "&sender=" ).append( senderId );
        sbPostData.append( "&message=" ).append( message );
        sbPostData.append("&country=91");


//final string
        mainUrl = sbPostData.toString();
        Log.d("RESPONSE", "" + mainUrl);

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

