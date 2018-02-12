package com.example.admin.broadexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 10.02.2018.
 */

public class ObjectName implements Parcelable {

    private int Value1;
    private int Value2;
    private int Value3;

    public ObjectName(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<ObjectName> CREATOR = new Parcelable.Creator<ObjectName>() {
        public ObjectName createFromParcel(Parcel in) {
            return new ObjectName(in);
        }

        public ObjectName[] newArray(int size) {

            return new ObjectName[size];
        }

    };

    public void readFromParcel(Parcel in) {
        Value1 = in.readInt();
        Value2 = in.readInt();
        Value3 = in.readInt();

    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Value1);
        dest.writeInt(Value2);
        dest.writeInt(Value3);
    }
}