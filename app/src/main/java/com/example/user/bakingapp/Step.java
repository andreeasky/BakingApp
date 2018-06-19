package com.example.user.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable{

    /** Property id */
    int id;

    /** Property shortDescription */
    String shortDescription;

    /** Property description */
    String description;

    /** Property videoURL */
    String videoURL;

    /** Property thumbnailURL */
    String thumbnailURL;

    /**
     * Constructor
     */
    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {

        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    /**
     * Constructor
     * @param position
     */
    public Step(int position) {
    }

    public Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    /**
     * Gets the Step id
     *
     */
    public int getStepId() {return this.id;
    }

    /**
     * Sets the Step id
     */
    public void setStepId() {this.id = id;
    }

    /**
     * Gets the Step shortDescription
     */
    public String getStepShortDescription() {
        return this.shortDescription;
    }

    /**
     * Sets the Step shortDescription
     */
    public void setStepShortDescription() {
        this.shortDescription = shortDescription;
    }

    /**
     * Gets the Step description
     */
    public String getStepDescription() {
        return this.description;
    }

    /**
     * Sets the Step description
     */
    public void setStepDescription() {
        this.description = description;
    }

    /**
     * Gets the Step videoURL
     */
    public String getStepsVideoURL() {
        return this.videoURL;
    }

    /**
     * Sets the Step videoURL
     */
    public void setStepVideoURL() {
        this.videoURL  = videoURL;
    }

    /**
     * Gets the Step thumbnailURL
     */
    public String getStepThumbnailURL() {
        return this.thumbnailURL ;
    }

    /**
     * Sets the Step thumbnailURL
     */
    public void setStepThumbnailURL() {
        this.thumbnailURL  = thumbnailURL ;
    }



    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString( description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>()
    {
        public Step createFromParcel(Parcel in)
        {
            return new Step(in);
        }
        public Step[] newArray(int size)
        {
            return new Step[size];
        }
    };
}
