package com.example.admin.broadexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 10.02.2018.
 */

class Book implements Parcelable {
    public long id;
    public String title;

    public Book(long id, String title) {
        this.id = id;
        this.title = title;
    }


    protected Book(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book( in );
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);

    }
}