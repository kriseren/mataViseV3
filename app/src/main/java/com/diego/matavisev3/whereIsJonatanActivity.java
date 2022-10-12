package com.diego.matavisev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class whereIsJonatanActivity extends AppCompatActivity {

    //Attributes.
    private int numberOfDeathsShown=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_is_jonatan);
    }
    //Method that is called when the button is clicked and chooses one death to show.
    public void onClickDeath(View view)
    {
        //Shows a random death.
        int election;
        do
        {
            election=1+(int)(Math.random()*1000);
        }
        while(selector(election).equals(""));
        String death = selector(election);
        death = death.substring(death.indexOf(" ")+1);
        String result = "Muerte Nº"+election+"\n"+death;
        TextView resultView = findViewById(R.id.textViewDeath);
        resultView.setText(result);
    }

    //Returns the line passed as a parameter.
    public String selector(int numLine)
    {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("deaths.txt"))))
        {
            String line="";
            for (int i=0; i<numLine; i++)
                line = reader.readLine();
            return line;
        }
        catch(IOException e)
        {
            return "Error.";
        }
    }

    //TODO Adaptar todo el código a Busca a Jonatan
    public void onClickShareDeath(View view)
    {
        if(numberOfDeathsShown>0)
        {
            //Gets the text to send.
            TextView resultView = findViewById(R.id.textViewDeath);
            String shareDeath = "¡Mira esta muerte de MataViseV3!\n"+resultView.getText();
            //Opens the menu to shares it.
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,shareDeath);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        else{
            Snackbar.make(view,"Aún no se ha generado ninguna muerte.",Snackbar.LENGTH_LONG).show();}

    }

    //TODO Arreglar esto para que vaya a la anterior.
    public void onClickChangeMode(View view)
    {
        Intent intent = new Intent(this, whereIsJonatanActivity.class);
        startActivity(intent);
    }

}