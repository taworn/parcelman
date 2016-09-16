package diy.parcelman;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SampleTest {

    private static final String TAG = SampleTest.class.getSimpleName();

    private void write(Sample s) {
        Log.d(TAG, " ");
        Log.d(TAG, "Sample: " + s.getName() + "/" + s.getData() + ", has " + s.size() + " subsample(s)");
        for (int i = 0; i < s.size(); i++) {
            Sample.Subsample ss = s.get(i);
            Log.d(TAG, "  Subsample #" + i + ": data=" + ss.getData() + " previous=" + ss.getPreviousData());
        }
        Log.d(TAG, " ");
    }

    @Test
    public void test() {
        Sample s0 = new Sample();
        s0.setName("Parcelman");
        s0.setData(55555);

        Sample.Subsample ss;
        ss = new Sample.Subsample();
        ss.setData(1);
        ss.setData(10);
        ss.setData(100);
        s0.add(ss);
        ss = new Sample.Subsample();
        ss.setData(2);
        ss.setData(22);
        ss.setData(222);
        s0.add(ss);
        ss = new Sample.Subsample();
        ss.setData(3);
        ss.setData(36);
        ss.setData(369);
        s0.add(ss);

        write(s0);

        Bundle bundle = new Bundle();
        bundle.putParcelable("sample", s0);
        Sample s1 = bundle.getParcelable("sample");
        write(s1);
        assertTrue(s0.compare(s1));

        Sample s2 = new Sample();
        s2.setName("Not Equal with Sample s0");
        write(s2);
        assertFalse(s0.compare(s2));
    }

}
