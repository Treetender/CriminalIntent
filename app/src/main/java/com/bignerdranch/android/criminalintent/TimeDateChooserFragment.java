package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by treetender on 1/18/15.
 */
public class TimeDateChooserFragment extends DialogFragment {

    public static final String EXTRA_DATETIME = "com.bignerdranch.android.criminalintent.datetime";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Date mDate;

    public static TimeDateChooserFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATETIME, date);
        TimeDateChooserFragment f = new TimeDateChooserFragment();
        f.setArguments(args);

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATETIME);
        TextView helpView = new TextView(getActivity().getApplicationContext());
        helpView.setText("Select whether you wish to change the date or the time for this crime.");

        return new AlertDialog.Builder(getActivity())
                .setTitle("Change Time or Date?")
                .setView(helpView)
                .setPositiveButton("Time", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                TimePickerFragment d = TimePickerFragment.newInstance(mDate);
                                d.setTargetFragment(TimeDateChooserFragment.this, REQUEST_TIME);
                                d.show(fm, DIALOG_TIME);
                            }
                        }
                )
                .setNegativeButton("Date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        DatePickerFragment d = DatePickerFragment.newInstance(mDate);
                        d.setTargetFragment(TimeDateChooserFragment.this, REQUEST_DATE);
                        d.show(fm, DIALOG_DATE);
                    }
                })
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        Date date;
        if(requestCode == REQUEST_DATE) {
            date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        }
        else if (requestCode == REQUEST_TIME) {
            date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
        }
        else
            date = null;
        sendResult(Activity.RESULT_OK, date);
    }

    private void sendResult(int resultCode, Date date) {
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATETIME, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

}
