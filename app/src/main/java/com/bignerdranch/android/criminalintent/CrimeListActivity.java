package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by treetender on 1/4/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
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
