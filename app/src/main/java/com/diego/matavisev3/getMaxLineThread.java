package com.diego.matavisev3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getMaxLineThread implements Runnable
{
    private long maxLines;
    public void run()
    {
        try
        {
            //Create the connection to the URL.
            URL urlPlaces = new URL("https://raw.githubusercontent.com/kriseren/DatosMataVise/main/places.txt"); //Defines the places.txt URL.
            HttpURLConnection conn = (HttpURLConnection)urlPlaces.openConnection(); //Creates a connection.
            conn.setConnectTimeout(60000); // Timing out in a minute.
            //Read the file from the URL.
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            maxLines = reader.lines().count();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public long getValue(){return maxLines;}
}
