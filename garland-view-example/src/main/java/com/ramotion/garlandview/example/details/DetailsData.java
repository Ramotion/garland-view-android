package com.ramotion.garlandview.example.details;


import android.os.Parcel;
import android.os.Parcelable;

public class DetailsData implements Parcelable {
    public final String title;
    public final String text;

    public DetailsData(String title, String text) {
        this.title = title;
        this.text = text;
    }

    private DetailsData(Parcel in) {
        title = in.readString();
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(text);
    }

    public static final Parcelable.Creator<DetailsData> CREATOR = new Parcelable.Creator<DetailsData>() {
        @Override
        public DetailsData createFromParcel(Parcel parcel) {
            return new DetailsData(parcel);
        }

        @Override
        public DetailsData[] newArray(int size) {
            return new DetailsData[size];
        }
    };

}
