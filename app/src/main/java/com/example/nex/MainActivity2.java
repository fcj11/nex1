package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nex.fragment.Gourmet_fragment;
import com.example.nex.fragment.Home_fragment;
import com.example.nex.fragment.Order_Fragment;
import com.example.nex.fragment.PersonanlCenter;

public class MainActivity2 extends AppCompatActivity {

    private FragmentManager fgm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fgm = this.getFragmentManager();
        initview();//调用初始化组件功能
        navigation();//调用设置单机按钮组选项改变监听事件的功能从而实现导航功能
    }

    private RadioGroup myradioGroup; //单选按钮组
    private RadioButton rbutton1,rbutton2,rbutton3,rbutton4;//4个单选按钮
    private Resources res;
    private Drawable icon_home_true,icon_home_false,icon_me_ture,icon_me_false,
            icon_order_true, icon_order_false,icon_community_true,getIcon_community_false;
    private int fontColor_false,fonColor_true;//选中和未选中颜色设置


    private void initview() {
        //通过ID找到ui中的单选按钮
        myradioGroup = (RadioGroup) this.findViewById(R.id.main_radioGroup);
        rbutton1 = (RadioButton) this.findViewById(R.id.main_radio0);
        rbutton2 = (RadioButton) this.findViewById(R.id.main_radio1);
        rbutton3 = (RadioButton) this.findViewById(R.id.main_radio2);
        rbutton4 = (RadioButton) this.findViewById(R.id.main_radio3);
        res = getResources();//得到Resources对象通过它获取存与系统的资源
        //找到图片icon_home_ture,用于设置当"首页"选项未被选中时的图片
        icon_home_true = res.getDrawable(R.mipmap.icon_home_true);
        icon_home_false = res.getDrawable(R.mipmap.icon_home_false);
        icon_community_true = res.getDrawable(R.mipmap.icon_community_true);
        getIcon_community_false = res.getDrawable(R.mipmap.icon_community_false);
        icon_me_ture = res.getDrawable(R.mipmap.icon_me_true);
        icon_me_false = res.getDrawable(R.mipmap.icon_me_false);
        icon_order_true = res.getDrawable(R.mipmap.icon_order_true);
        icon_order_false = res.getDrawable(R.mipmap.icon_order_false);
        fontColor_false = res.getColor(R.color.navigation_false);
        fonColor_true = res.getColor(R.color.public_green);
    }
    private void setAllColor(){
        rbutton1.setTextColor(fontColor_false);
        rbutton2.setTextColor(fontColor_false);
        rbutton3.setTextColor(fontColor_false);
        rbutton4.setTextColor(fontColor_false);
        //设置未选中颜色
    }
    private void setAllImage(){
        rbutton1.setCompoundDrawablesWithIntrinsicBounds(null,icon_home_false,null,null);
        rbutton2.setCompoundDrawablesWithIntrinsicBounds(null,getIcon_community_false,null,null);
        rbutton3.setCompoundDrawablesWithIntrinsicBounds(null,icon_order_false,null,null);
        rbutton4.setCompoundDrawablesWithIntrinsicBounds(null,icon_me_false,null,null);
        //设置未被选中时的图片
    }
    private void navigation(){
        //TODO Auto-generated method stub
        myradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //变量int i 中保存了用户每次选中的选项ID，下面的操作就是用此特点来确定单选按钮被选中的状态，并实现相应的需求
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction transaction=fgm.beginTransaction();
                //调用此方法用于每次切换选项时将所有选项的文字颜色复位为未被选中时的颜色
                setAllColor();
                //复位照片
                setAllImage();
                //当首页选项被选中时，设置选项在选中状态下的文字图片
                switch (i){
                    case R.id.main_radio0:
                        rbutton1.setTextColor(fonColor_true);
                        rbutton1.setCompoundDrawablesWithIntrinsicBounds(null,icon_home_true,null,null);
                        transaction.replace(R.id.main_framelayout,new Home_fragment());
                        Toast.makeText(MainActivity2.this,"首页",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.main_radio1:
                        rbutton2.setTextColor(fonColor_true);
                        rbutton2.setCompoundDrawablesWithIntrinsicBounds(null,icon_community_true,null,null);
                        transaction.replace(R.id.main_framelayout,new Gourmet_fragment());
                        Toast.makeText(MainActivity2.this,"吃货驾到",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.main_radio2:
                        rbutton3.setTextColor(fonColor_true);
                        rbutton3.setCompoundDrawablesWithIntrinsicBounds(null,icon_order_true,null,null);
                        transaction.replace(R.id.main_framelayout,new Order_Fragment());
                        Toast.makeText(MainActivity2.this,"我的订单",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.main_radio3:
                        rbutton4.setTextColor(fonColor_true);
                        rbutton4.setCompoundDrawablesWithIntrinsicBounds(null,icon_me_ture,null,null);
                        transaction.replace(R.id.main_framelayout,new PersonanlCenter());
                        Toast.makeText(MainActivity2.this,"个人中心",Toast.LENGTH_SHORT).show();
                        break;
                }
                transaction.commit();
            }
        });
    }
}