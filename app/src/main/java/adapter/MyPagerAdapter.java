package adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by zth on 2018/1/4.
 */

public class MyPagerAdapter extends PagerAdapter{
    int[] myDrable=new int[3];
    ArrayList<ImageView> images;
    public MyPagerAdapter(ArrayList<ImageView> images) {
        this.images = images;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //container.addView(images.get(position%images.size()));
        try{
            ((ViewPager) container).addView(images.get(position%images.size()),0);
        }catch(Exception e) {
        }
            return  images.get(position%images.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // container.removeView((View) object);
    }


}
