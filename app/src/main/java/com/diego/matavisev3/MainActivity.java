package com.diego.matavisev3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MainActivity extends AppCompatActivity
{
    //Attributes.
    private int numberOfDeathsShown=0;
    private static URL urlPlaces,urlDeaths;
    private final File fileDeaths = new File(this.getFilesDir(),"deaths.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Updates the source files "deaths.txt" and "places.txt"
        new Thread(MainActivity::updateFiles);
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
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDeaths))))
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

    //Shares the death.
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
        else{Snackbar.make(view,"Aún no se ha generado ninguna muerte.",Snackbar.LENGTH_LONG).show();}

    }

    //Creates a new instance of the whereIsJonatan Activity.
    public void onClickChangeMode(View view)
    {
        Intent intent = new Intent(this, whereIsJonatanActivity.class);
        startActivity(intent);
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

    public static void updateFiles()
    {
        try {
            URL urlPlaces,urlDeaths;
            //Create the connection to the URLs.
            urlPlaces = new URL("https://raw.githubusercontent.com/kriseren/DatosMataVise/main/places.txt"); //Defines the places.txt URL.
            urlDeaths = new URL("https://raw.githubusercontent.com/kriseren/DatosMataVise/main/deaths.txt"); //Defines the deaths.txt URL.

            //Create the necessary objects for copying.
            ReadableByteChannel readableByteChannelPlaces = Channels.newChannel(urlPlaces.openStream());
            ReadableByteChannel readableByteChannelDeaths = Channels.newChannel(urlDeaths.openStream());
            FileOutputStream fileOutputStream1 = MainActivity.openFileOutput("places.txt", Context.MODE_PRIVATE); //TODO arreglar el error
            FileOutputStream fileOutputStream2 = MainActivity.openFileOutput("deaths.txt", Context.MODE_PRIVATE); //TODO arreglar el error.

            //Copies the files.
            fileOutputStream1.getChannel().transferFrom(readableByteChannelPlaces, 0, Long.MAX_VALUE);
            fileOutputStream2.getChannel().transferFrom(readableByteChannelDeaths, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}