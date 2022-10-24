package com.diego.matavisev3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class whereIsJonatanActivity extends AppCompatActivity {

    //Attributes.
    private int numberOfPlacesShown=0;
    private URL urlPlaces,urlDeaths;
    private BufferedReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_is_jonatan);
    }
    //Method that is called when the button is clicked and chooses one place to show.
    public void onClickPlace(View view)
    {
        //Creates a new thread (It is required by Android Studio)
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Shows a random place.
                int election;
                long maxLines = getMaxLines(urlPlaces); //Gets the number of lines the file has.
                do
                {
                    election=(int)(1+Math.random()*maxLines);
                }
                while(selectorInternet(election).equals(""));
                String place = selectorInternet(election);
                place = place.substring(place.indexOf(" ")+1);
                String result = "Sitio Nº"+election+"\n"+ place;
                TextView resultView = findViewById(R.id.textViewPlace);
                resultView.setText(result);
                numberOfPlacesShown++;
            }
        }).start();

    }

    //Returns the line passed as a parameter.
    public String selector(int numLine)
    {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("places.txt"))))
        {
            String line="";
            for (int i=0; i<numLine; i++)
                line = reader.readLine();
            return line;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "Error";
        }
    }

    public String selectorInternet(int numLine)
    {
        String line="";
        try
        {
            //Create the connection to the URL.
            urlPlaces = new URL("https://raw.githubusercontent.com/kriseren/DatosMataVise/main/places.txt"); //Defines the places.txt URL.
            HttpURLConnection conn = (HttpURLConnection)urlPlaces.openConnection(); //Creates a connection.
            conn.setConnectTimeout(60000); // Timing out in a minute.
            //Read the file from the URL.
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            for (int i=0; i<numLine; i++)
                line = reader.readLine();
            reader.close();
        }
        catch (IOException e) {e.printStackTrace();}
        return line;
    }

    //Shares the place.
    public void onClickSharePlace2(View view)
    {
        if(numberOfPlacesShown>0)
        {
            //Gets the text to send.
            TextView resultView = findViewById(R.id.textViewPlace);
            String shareDeath = "¡Mira donde está Jonatan en MataViseV3!\n"+resultView.getText();
            //Opens the menu to shares it.
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,shareDeath);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        else{
            Snackbar.make(view,"Aún no se ha encontrado a Jonatan.",Snackbar.LENGTH_LONG).show();}

    }

    //Finishes the activity so that switches to the first one.
    public void onClickChangeMode2(View view){finish();}

    //TODO poder calcular el numero de lineas que tiene un archivo.
    //Calculates the maximum number of lines the file has.
    public long getMaxLines(URL url)
    {
        getMaxLineThread getMaxLineThread = new getMaxLineThread();
        Thread thread = new Thread(getMaxLineThread);
        thread.run();
        return getMaxLineThread.getValue();
    }
}