package com.base.library.preview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.base.library.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment> fragmentArrayList;

    public PagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    public void UpdateList() {
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
