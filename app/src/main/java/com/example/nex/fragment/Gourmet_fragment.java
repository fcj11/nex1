package com.example.nex.fragment;

import android.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.nex.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.DBOpenHelper;
import Adapter.GourmetBaseAdapter;

public class Gourmet_fragment extends Fragment{
    @Nullable
    private ListView mylistview;
    private int images[]={
            R.drawable.p1_gourmet,R.drawable.p2_gourmet,
            R.drawable.p3_gourmet,R.drawable.p4_gourmet,
            R.drawable.p5_gourmet,R.drawable.p6_gourmet,
            R.drawable.p7_gourmet,R.drawable.p8_gourmet,
    };
    private String[] comment = {"减肥干吗 又不是吃不起","这样的馒头，感觉能吃一筐","给你一个爱上烘焙的理由","不是我瘦不下来 是敌人太强","一场咖啡与鲜花的比赛","美食是灵魂的伴侣","吃货的幸福世界","美得舍不得下咽"};
    private String[] date = {"2016年7月","2016年9月","2017年1月","2017年2月","2017年10月","2018年5月","2018年9月","2018年10月"};
    private String[] name = {"叶德焖","刘芸","徐自贤","丁志诚","梁文道","张笛","杨若兮","王丽达"};
    private List<HashMap>dataList;
    GourmetBaseAdapter myBaseAdapter;
    private Toast oast;
    private DBOpenHelper openHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.frag_gourmet,null);
        initdata();
        myBaseAdapter=new GourmetBaseAdapter(dataList,this.getActivity());
        mylistview=(ListView) view.findViewById(R.id.gourment_frag_listView1);
        mylistview.setAdapter(myBaseAdapter);
        this.registerForContextMenu(mylistview);
        return view;
    }
    private void initdata(){
        dataList= new ArrayList();
        for (int i = 0; i < date.length;i++){
            HashMap hm = new HashMap();
            hm.put("image",images[i]);
            hm.put("date",date[i]);
            hm.put("comment",comment[i]);
            hm.put("name",name[i]);
            dataList.add(hm);
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,1, Menu.NONE,"微信分享");
        menu.add(0,2, Menu.NONE,"收藏");
        menu.add(0,3, Menu.NONE,"删除");
        super.onCreateContextMenu(menu,v,menuInfo);
    }
    public void dialog(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        mydialog.setTitle("确认");
        mydialog.setIcon(android.R.drawable.ic_dialog_alert);
        mydialog.setMessage("您确认要在微信分享");
        mydialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @SuppressLint("WrongConstant")
            public void onClick(DialogInterface dialogInterface,int i){
                Toast.makeText(getActivity(),"你不微信分享了",2000).show();
            }
        });
        mydialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @SuppressLint("WrongConstant")
            public void onClick(DialogInterface dialogInterface,int i){
                Toast.makeText(getActivity(),"微信分享成功",2000).show();
            }
        });
        mydialog.show();
    }
    public boolean onContextItemSelected(MenuItem item){
        final AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 1:
                dialog();
                break;
            case 2:
                collection(info.position);
                break;
            case 3:
                dataList.remove(info.position);
                myBaseAdapter.notifyDataSetInvalidated();
                break;
        }
        return super.onContextItemSelected(item);
    }
    private byte[] getPicture(Drawable drawable){
        if (drawable==null){
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
        return os.toByteArray();
    }
    public void collection(int select_index){
        openHelper = new DBOpenHelper(this.getActivity(), "collection.db", null, 1);
        SQLiteDatabase sqLiteDatabase=openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",dataList.get(select_index).get("name").toString());
        values.put("date",dataList.get(select_index).get("date").toString());
        values.put("comment",dataList.get(select_index).get("comment").toString());
        values.put("image",getPicture(this.getResources().getDrawable((Integer)dataList.get(select_index).get("image"))));
        long i = sqLiteDatabase.insert("collection_imf",null,values);
        if (i > -1) {
            oast.makeText(getActivity(),"亲！已收藏",Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabase.close();

    }
}


