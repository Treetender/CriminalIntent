package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;
import android.content.*;
import android.util.*;

/**
 * Created by treetender on 1/4/15.
 */
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID crimeID = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeID);
    }
}
