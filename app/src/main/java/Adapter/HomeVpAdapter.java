package Adapter;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class HomeVpAdapter extends PagerAdapter {
    List<ImageView> mImages;
    public HomeVpAdapter(List<ImageView>images){
        this.mImages=images;
    }
    public int getCount(){
        //TODO Auto-generated method stub
        return mImages.size();
    }
    public boolean isViewFromObject(View view, Object object){
        //TODO Auto-generated method stub
        return view == object;
    }
    public void destroyItem(ViewGroup container, int position, Object object){
        //TODO Auto-generated method stub
        container.removeView((View) object);
    }
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container,int position){
        //TODO Auto-generated method stub
        container.addView(mImages.get(position));
        return mImages.get(position);
    }



}