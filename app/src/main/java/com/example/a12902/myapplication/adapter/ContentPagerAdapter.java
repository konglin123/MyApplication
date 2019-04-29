package com.example.a12902.myapplication.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a12902.myapplication.ui.fragment.ContentFragment;

public class ContentPagerAdapter extends FragmentPagerAdapter {
    private ContentFragment mOneFragment;
    private ContentFragment mTwoFragment;
    private ContentFragment mThreeFragment;

    public ContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (mOneFragment == null) {
                mOneFragment = ContentFragment.newInstance(position);
            }
            return mOneFragment;
        }else if(position==1){
            if (mTwoFragment == null) {
                mTwoFragment = ContentFragment.newInstance(position);
            }
            return mTwoFragment;
        }else{
            if(mThreeFragment==null){
                mThreeFragment=ContentFragment.newInstance(position);
            }
            return mThreeFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "第" + (position + 1) + "页";
    }
}
