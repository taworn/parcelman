package diy.parcelman;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    private static class Subsample {
        public int data;
        public int previousData;
    }

    private String name;
    private int data;
    private List<Subsample> list;

    /**
     * Constructs a new data.
     */
    public Sample() {
        this.name = "";
        this.data = 0;
        this.list = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String value) {
        String s = value.trim();
        if (s.length() <= 0)
            throw new IllegalArgumentException("Item name's length cannot be zero.");
        name = s;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

}
