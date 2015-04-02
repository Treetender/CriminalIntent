package com.bignerdranch.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by treetender on 1/3/15.
 */
public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";

    private UUID mId;
    private String mTitle, mSuspect;
    private Date mDate;
    private boolean mSolved;
    private Photo mPhoto;

    public Crime() {
        //Generate Unique Identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE))
            mTitle = json.getString(JSON_TITLE);
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        if(json.has(JSON_PHOTO))
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        if(json.has(JSON_SUSPECT))
            mSuspect = json.getString(JSON_SUSPECT);
    }

    //Getters
    public UUID getId() {
        return mId;
    }
    public Date getDate() {
        return mDate;
    }
    public String getTitle() {
        return mTitle;
    }
    public Photo getPhoto() {
        return mPhoto;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public String getSuspect() { return mSuspect; }

    //Setters
    public void setTitle(String title) {
        mTitle = title;
    }
    public void setDate(Date date) {
        mDate = date;
    }
    public void setPhoto(Photo p) {
        mPhoto = p;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
    public void setSuspect(String suspect) { mSuspect = suspect; }

    @Override
    public String toString() {
        return mTitle;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        if (mPhoto != null)
            json.put(JSON_PHOTO, mPhoto.toJSON());

        return json;
    }
}
