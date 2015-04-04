package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by treetender on 1/4/15.
 */
public class CrimeListActivity extends SingleFragmentActivity 
                               implements CrimeListFragment.Callbacks,
                                          CrimeFragment.Callbacks
{

    @Override
    public void onCrimeUpdated(Crime crime)
    {
        FragmentManager fm = getSupportFragmentManager();
        CrimeListFragment f = (CrimeListFragment)fm.findFragmentById(R.id.fragmentContainer);
        f.updateUI();
    }


    @Override
    public void onCrimeSelected(Crime crime)
    {
        if(findViewById(R.id.detailsContainer) == null)
        {
            //Single Pane Mode
            Intent i = new Intent(this, CrimePagerActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
            startActivity(i);
        }
        else
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            
            Fragment oldDetail = fm.findFragmentById(R.id.detailsContainer);
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            
            if(oldDetail != null) {
                ft.remove(oldDetail);
            }
            
            ft.add(R.id.detailsContainer, newDetail);
            ft.commit();
        }
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId()
    {
        // TODO: Implement this method
        return R.layout.activity_masterdetail;
    }
}
