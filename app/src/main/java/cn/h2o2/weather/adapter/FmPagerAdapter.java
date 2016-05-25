package cn.h2o2.weather.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * author      yichen
 * Email       kedongjun@mmclick.com
 * Date        2016/4/3 11:34
 * description: TODO
 */
public class FmPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();


    public FmPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    public void addFragment(String title,Fragment fragment){
        titles.add(title);
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
