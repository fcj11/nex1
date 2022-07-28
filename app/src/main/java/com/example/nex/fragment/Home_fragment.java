package com.example.nex.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nex.R;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import Adapter.HomeVpAdapter;
public class Home_fragment extends Fragment {
    private ViewPager viewpager;//轮播图组件
    private GridView gridView;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private String[] nameListView = {"新品驾到", "食趣", "食品安全", "产品溯源", "健康养生", "产品展示"};
    private int[] imageListView = {R.mipmap.no1, R.mipmap.no2, R.mipmap.no3, R.mipmap.no4, R.mipmap.no5, R.mipmap.no6};
    private List<ImageView> list_VP=new ArrayList<ImageView>();
    private int[] Image_VP={R.drawable.guanggao1,R.drawable.guanggao2,R.drawable.guanggao3};
    TextView tv_radio;
    LinearLayout ll_viewpager_don;
    private List<TextView>tv_list=new ArrayList<TextView>();
    private LinearLayout.LayoutParams layoutParams;
    Thread mytr;
    private Handler handler = new Handler() {
        public void handleMessage( Message msg) {
            // 在当前页面位置加 1
            switch (msg.what) {
                case 1:
                    // 利用取模运算方法（模为3）实现动态切换 ViewPager 图片的功能
                    viewpager.setCurrentItem((viewpager.getCurrentItem() + 1) % 3);
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.frag_home, null);
        initViewPager(view);
        initGridView(view);
        return view;
    }
    private void initViewPager(View view){
        viewpager = (ViewPager)view.findViewById(R.id.home_viewpager);
        ll_viewpager_don=(LinearLayout) view.findViewById(R.id.home_viewpager_dot);
        layoutParams=new LinearLayout.LayoutParams(20,20);
        layoutParams.setMargins(0,0,10,0);
        for (int i=0;i< Image_VP.length;i++){
            ImageView imageV=new ImageView(getActivity());
            imageV.setImageResource(Image_VP[i]);
            imageV.setAdjustViewBounds(true);
            imageV.setScaleType(ImageView.ScaleType.FIT_XY);
            list_VP.add(imageV);

            tv_radio=new TextView(getActivity());
            tv_radio.setBackgroundResource(R.drawable.tv_dot_selector);
            ll_viewpager_don.addView(tv_radio,layoutParams);
            tv_list.add(tv_radio);
        }
        viewpager.setAdapter(new HomeVpAdapter(list_VP));
        viewpager.setCurrentItem(0);
        tv_list.get(0).setSelected(true);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){
            }
            public void onPageSelected(int position){
                for (int i =0; i<tv_list.size();i++){
                    if (i == position% list_VP.size()){
                        tv_list.get(i).setSelected(true);
                    } else {
                        tv_list.get(i).setSelected(false);
                    }
                }
            }
            public void onPageScrollStateChanged(int state){
            }
        });
    }
    private  void myThread(){
        mytr=new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO Auto-generated method stub
                while (!Thread.interrupted()){
                    try{
                        Thread.sleep(5000);
                        handler.sendEmptyMessage(1);
                    } catch(InterruptedException e){
                        //TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        mytr.start();
    }
    public void onResume(){
        myThread();
        super.onResume();
    }
    @Override
    public void onStop(){
        mytr.interrupt();
        super.onStop();
    }
    private void initGridView(View view){
        gridView=(GridView)view.findViewById(R.id.home_gridView);
        list=new ArrayList<HashMap<String,Object>>();
        for (int i = 0;i<6;i++){
            HashMap<String,Object> hmap=new HashMap<String,Object>();
//            hmap.put("name",name_listview[i]);
            hmap.put("name",nameListView[i]);//41
//            hmap.put("icon",image_listview);
            hmap.put("icon",imageListView[i]);//42
            list.add(hmap);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list,R.layout.buju_home_gridview,new
                String[]{"name","icon"},new int[]{R.id.buju_home_gridview_name,R.id.buju_home_gridview_icon});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            public void onItemClick(AdapterView<?>arg0,View v,int i,long id){
                switch (i){
                    case 0:
                        Toast.makeText(getActivity(), "新品驾到", 5000).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "食趣", 5000).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "食品安全", 5000).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "产品溯源", 5000).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "健康养生", 5000).show();
                        break;
                    case 5:
                        Toast.makeText(getActivity(), "产品展示", 5000).show();
                        break;
                }
            }

        });
    }

}

