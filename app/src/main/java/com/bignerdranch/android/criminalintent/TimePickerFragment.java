package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by treetender on 1/11/15.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_HOUR = "com.bignerdranch.android.criminalintent.hour";
    public static final String EXTRA_MINUTE = "com.bignerdranch.android.criminalintent.minute";

    private int mHours, mMinutes;
    private TimePicker mTp;

    public static TimePickerFragment newInstance(int hours, int minutes)
    {
        Bundle args = new Bundle();
        args.putInt(EXTRA_HOUR, hours);
        args.putInt(EXTRA_MINUTE, minutes);
        TimePickerFragment f = new TimePickerFragment();
        f.setArguments(args);

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        mHours = getArguments().getInt(EXTRA_HOUR, c.get(Calendar.HOUR_OF_DAY));
        mMinutes = getArguments().getInt(EXTRA_MINUTE, c.get(Calendar.MINUTE));

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
        mTp = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);

        //Branch Test

        mTp.setCurrentHour(mHours);
        mTp.setCurrentMinute(mMinutes);
        mTp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHours = hourOfDay;
                mMinutes = minute;

                getArguments().putInt(EXTRA_HOUR, mHours);
                getArguments().putInt(EXTRA_MINUTE, mMinutes);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time of crime:")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHours = mTp.getCurrentHour();
                        mMinutes = mTp.getCurrentMinute();
                                    
                        getArguments().putInt(EXTRA_HOUR, mHours);
                        getArguments().putInt(EXTRA_MINUTE, mMinutes);
                        
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode) {
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_HOUR, mHours);
        i.putExtra(EXTRA_MINUTE, mMinutes);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
