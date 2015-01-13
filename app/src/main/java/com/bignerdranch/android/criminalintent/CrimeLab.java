package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by treetender on 1/4/15.
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext)
    {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        for (int i = 1; i <= 100; i++)
        {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
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

}
