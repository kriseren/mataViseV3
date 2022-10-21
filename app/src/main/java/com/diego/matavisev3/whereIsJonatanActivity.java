package com.diego.matavisev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class whereIsJonatanActivity extends AppCompatActivity {

    //Attributes.
    private int numberOfPlacesShown=0;
    private URL urlPlaces,urlDeaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_is_jonatan);

        //Set URL variables.
        try
        {
            urlDeaths = new URL("https://github.com/kriseren/DatosMataVise/blob/main/deaths.txt");
            urlPlaces = new URL("https://github.com/kriseren/DatosMataVise/blob/main/places.txt");
        }
        catch(MalformedURLException e){e.printStackTrace();}

    }
    //Method that is called when the button is clicked and chooses one place to show.
    public void onClickPlace(View view)
    {
        //Shows a random place.
        int election;
        do
        {
            election=(int)(1+Math.random()*100);
        }
        while(selector(election).equals(""));
        String place = selector(election);
        place = place.substring(place.indexOf(" ")+1);
        String result = "Sitio Nº"+election+"\n"+ place;
        TextView resultView = findViewById(R.id.textViewPlace);
        resultView.setText(result);
        numberOfPlacesShown++;
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

    //TODO abrir ficheros en la nube sin errores
    public String selectorInternet(int numLine)
    {
        final String[] line = {""};
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlPlaces.openStream())))
                {
                    for (int i=0; i<numLine; i++)
                        line[0] = reader.readLine();
                } catch (IOException e){e.printStackTrace();}
            }
        });
        return line[0];
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
}