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
import java.net.URL;

public class whereIsJonatanActivity extends AppCompatActivity {

    //Attributes.
    private int numberOfPlacesShown=0;
    private BufferedReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_is_jonatan);
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