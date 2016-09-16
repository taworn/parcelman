package diy.parcelman;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Sample implements Parcelable {

    public static class Subsample implements Parcelable {

        private int data;
        private int previousData;

        public Subsample() {
            data = 0;
            previousData = 0;
        }

        public Subsample(Parcel in) {
            data = in.readInt();
            previousData = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(data);
            out.writeInt(previousData);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void setData(int data) {
            this.previousData = this.data;
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public int getPreviousData() {
            return previousData;
        }

        public static final Parcelable.Creator<Subsample> CREATOR = new Parcelable.Creator<Subsample>() {

            public Subsample createFromParcel(Parcel in) {
                return new Subsample(in);
            }

            public Subsample[] newArray(int size) {
                return new Subsample[size];
            }

        };

    }

    private String name;
    private int data;
    private List<Subsample> list;

    public Sample() {
        name = "";
        data = 0;
        list = new ArrayList<>();
    }

    public Sample(Parcel in) {
        name = in.readString();
        data = in.readInt();
        list = new ArrayList<>();
        int count = in.readInt();
        for (int i = 0; i < count; i++)
            list.add(Subsample.CREATOR.createFromParcel(in));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(data);
        out.writeInt(list.size());
        for (int i = 0; i < list.size(); i++)
            out.writeParcelable(list.get(i), 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String value) {
        name = value;
    }

    public int getData() {
        return data;
    }

    public void setData(int value) {
        data = value;
    }

    public void add(@NonNull Subsample subsample) {
        list.add(subsample);
    }

    public void edit(int index, int data) {
        list.get(index).setData(data);
    }

    public void delete(int index) {
        list.remove(index);
    }

    public void clear() {
        list.clear();
    }

    public Subsample get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean compare(Sample s) {
        if (!this.name.equals(s.name))
            return false;
        if (this.data != s.data)
            return false;
        if (this.list.size() != s.list.size())
            return false;

        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getData() != s.list.get(i).getData())
                return false;
            if (this.list.get(i).getPreviousData() != s.list.get(i).getPreviousData())
                return false;
        }

        return true;
    }

    public static final Parcelable.Creator<Sample> CREATOR = new Parcelable.Creator<Sample>() {

        public Sample createFromParcel(Parcel in) {
            return new Sample(in);
        }

        public Sample[] newArray(int size) {
            return new Sample[size];
        }

    };

}
