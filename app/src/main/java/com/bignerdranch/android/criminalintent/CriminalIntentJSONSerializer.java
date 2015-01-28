package com.bignerdranch.android.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by treetender on 1/27/15.
 */
public class CriminalIntentJSONSerializer {

    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializer(Context context, String filename) {
        mContext = context;
        mFilename = filename;
    }

    public ArrayList<Crime> loadCrimes() throws JSONException, IOException {
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;
        try {
            //Open the file and read it
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsonString = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                //Line Breaks are omitted and irrelevant
                jsonString.append(line);
            }

            //JSON has a tokenizer
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of crimes from the JSON Objects
            for(int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            //Ignore this as it happens on fresh loads
        } finally {
            if (reader != null)
                reader.close();
        }
        return crimes;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        //Build an array in JSON
        JSONArray array = new JSONArray();
        for (Crime c : crimes) {
            array.put(c.toJSON());
        }

        //Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null)
                writer.close();
        }
    }
}
