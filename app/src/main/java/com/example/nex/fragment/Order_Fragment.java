package com.example.nex.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import com.example.nex.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import Adapter.DBOpenHelper;
import Adapter.OrderBaseAdapter;

public class Order_Fragment extends Fragment {
    private int images[]={R.drawable.po1_hetao,R.drawable.po2_mi,
            R.drawable.po3_jidan,R.drawable.po4_huasheng,R.drawable.po5_huajiao};
    private String[] price={"¥50元/公斤","¥10元/公斤","¥25元/公斤","¥30元/公斤","¥100元/公斤"};
    private String[] address={"云南大理","惠州惠东","惠州农门","河源和平","四川汶川"};
    private String[] name={"核桃","大米","鸡蛋","花生","花椒",};
    ArrayList dataList;
    ListView order;
    private ArrayList CollectionList;
    private AbsListView collection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,    @Nullable ViewGroup container, Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.frag_order,null);
        init(view);
        showOrder();
        showCollection();
        return view;
    }
    public void init(View view){
        order=view.findViewById(R.id.frag_order_orderList);
        collection = (ListView) view.findViewById(R.id.frag_order_collectionlist);
    }
    public void showOrder(){
        initData();
        SimpleAdapter adapter=new SimpleAdapter(this.getActivity(),dataList,
                R.layout.buju_order_orderlist,new String[]{"image","price","address","name"},
                new int[]{R.id.buju_orderList_pic,R.id.buju_orderList_price,
                        R.id.buju_orderList_address,R.id.buju_orderList_name});
        order.setAdapter(adapter);
    }
    public void initData(){
        dataList=new ArrayList();
        for (int i = 0; i < images.length;i++){
            HashMap hm = new HashMap();
            hm.put("image",images[i]);
            hm.put("price",price[i]);
            hm.put("address",address[i]);
            hm.put("name",name[i]);
            dataList.add(hm);
        }
    }
    private void getCollection(){
        CollectionList = new ArrayList();
        SQLiteOpenHelper openHelper = new DBOpenHelper(this.getActivity(), "collection.db", null, 1);
        SQLiteDatabase sqLiteDatabase=openHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("collection_imf",null,null,null,null,null,null);
        if (cursor !=null && cursor.getCount()!=0){
            while (cursor.moveToNext()){
                HashMap hm = new HashMap();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String comment =cursor.getString(cursor.getColumnIndex("comment"));
                byte[] b = cursor.getBlob(cursor.getColumnIndex("image"));
                Bitmap bitmap =BitmapFactory.decodeByteArray(b,0,b.length,null);
                Drawable drawable =new BitmapDrawable(bitmap);
                hm.put("name",name);
                hm.put("date",date);
                hm.put("comment",comment);
                hm.put("image",drawable);
                CollectionList.add(hm);
            }
        }

    }
    public void showCollection(){
        getCollection();
        OrderBaseAdapter myadapter=new OrderBaseAdapter(CollectionList,this.getActivity());
        collection.setAdapter(myadapter);
    }
}

