package com.example.zino.nfcfinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by zino on 2016-06-23.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter{
    Fragment[] fragments=new Fragment[3];

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments[0] = new ReadFragment();
        fragments[1] = new WriteFragment();
        fragments[2] = new EraseFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
