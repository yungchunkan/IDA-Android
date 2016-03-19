package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ida.org.hk.ida.PhotoFragment;

/**
 * Created by ricky on 14/9/15.
 */
public class PhotoSliderAdapter extends FragmentPagerAdapter {
    private int[] resID;

    public PhotoSliderAdapter(FragmentManager fm, int[] resID) {
        super(fm);
        this.resID = resID;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(resID[position]);
    }

    @Override
    public int getCount() {
        return resID.length;
    }
}
