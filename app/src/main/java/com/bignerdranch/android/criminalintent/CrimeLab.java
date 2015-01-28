package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by treetender on 1/4/15.
 */
public class CrimeLab {

    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private static CrimeLab sCrimeLab;

    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;

    private CrimeLab(Context appContext)
    {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(appContext, FILENAME);

        try {
            mCrimes =  mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static CrimeLab get(Context context)
    {
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes()
    {
        return mCrimes;
    }

    public Crime getCrime(UUID id)
    {
        for(Crime c : mCrimes)
        {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes", e);
            return false;
        }
    }

}
