package com.diego.matavisev3;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class Utilities
{
    //Updates the files
    public static void updateFiles() {
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
