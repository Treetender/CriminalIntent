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
import java.util.Date;

/**
 * Created by treetender on 1/11/15.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";

    private Date mDate;
    private TimePicker mTp;
    private Calendar mCalendar;

    public static TimePickerFragment newInstance(Date time)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, time);
        TimePickerFragment f = new TimePickerFragment();
        f.setArguments(args);

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_TIME);
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mDate);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
        mTp = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);

        mTp.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTp.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        mTp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);

                mDate = mCalendar.getTime();
                getArguments().putSerializable(EXTRA_TIME, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time of crime:")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, mTp.getCurrentHour());
                        mCalendar.set(Calendar.MINUTE, mTp.getCurrentMinute());

                        mDate = mCalendar.getTime();
                                    
                        getArguments().putSerializable(EXTRA_TIME, mDate);
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode) {
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
