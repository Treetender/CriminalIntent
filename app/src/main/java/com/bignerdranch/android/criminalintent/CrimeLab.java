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

}
