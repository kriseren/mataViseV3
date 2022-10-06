package com.diego.matavisev3;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Method that is called when the button is clicked and chooses one death to show.
    public void onClickDeath(View view)
    {
        int election;
        do
        {
            election=1+(int)(Math.random()*1000);
        }
        while(selector(election).equals(""));
        String death = selector(election);
        death = death.substring(death.indexOf(" ")+1);
        String result = "Muerte Nº"+election+"\n"+death;
        TextView resultView = (TextView)findViewById(R.id.textViewDeath);
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
        } catch(IOException e) {
            return "Error.";
        }
    }

    //Reads the text on the resultView and copies it to the clipboard.
    public void onClickShare(View view)
    {
        TextView resultView = (TextView)findViewById(R.id.textViewDeath);
        String shareDeath = "¡Mira esta muerte de MataViseV3!\n"+resultView.getText();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("muerte",shareDeath);
        clipboard.setPrimaryClip(clip);
    }
}