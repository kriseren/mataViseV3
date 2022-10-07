package com.diego.matavisev3;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
{
    private int numberOfDeathsShown=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //Gets the number of deaths shown and increases its value.
        numberOfDeathsShown++;
        actionsOnDeathsShown(numberOfDeathsShown,view);

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

    //Reads the text on the resultView and copies it to the clipboard.
    public void onClickCopyClipboard(View view)
    {
        TextView resultView = findViewById(R.id.textViewDeath);
        String shareDeath = "¡Mira esta muerte de MataViseV3!\n"+resultView.getText();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("muerte",shareDeath);
        clipboard.setPrimaryClip(clip);

        //Shows a little message as feedback for the user.
        Snackbar.make(view,"Muerte copiada al portapapeles.",Snackbar.LENGTH_LONG).show();
    }

    public void onClickShareDeath(View view)
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

    @SuppressLint("ResourceAsColor")
    private void actionsOnDeathsShown(int numberOfDeathsShown, View view)
    {
        //Depending on how many deaths have been shown, the app does several actions.
        switch (numberOfDeathsShown)
        {
            case 20:
                Snackbar.make(view,"¡Enhorabuena! Has visto a Vise morir "+numberOfDeathsShown+" veces.",Snackbar.LENGTH_LONG).show();
                break;
            case 50:
                String text = "¡Te aburres mucho! Has visto a Vise morir "+numberOfDeathsShown+" veces.";
                Snackbar.make(view,text,Snackbar.LENGTH_LONG).show();
                break;
            case 75:
                Snackbar.make(view,"¡Eres un adicto a matar a Vise!",Snackbar.LENGTH_LONG).show();
                break;
            case 100:
                MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.police);
                mediaPlayer.start();
                Snackbar.make(view,"¡Hemos verificado que eres un psicópata, la policía va en tu dirección!",Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}