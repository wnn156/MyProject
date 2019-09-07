package com.example.myapplication.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public String id;
    @SerializedName("_id")
    public String _id;
    @SerializedName("pw")
    public String pw;

    public User(){
        _id = null;
        name = null;
        id = null;
        pw = null;
    }

    public User(String _id, String name, String id, String pw) {
        this._id = _id;
        this.name = name;
        this.id = id;
        this.pw = pw;
    }

    protected User(Parcel in) {
        _id = in.readString();
        name = in.readString();
        id = in.readString();
        pw = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(pw);
    }
}
