package com.example.nex.fragment;

import android.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.example.nex.MainActivity;
import com.example.nex.R;

@SuppressLint("ValidFragment")
public class PersonanlCenter extends Fragment {
    private TextView TVusername;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_personalcenter,null);
        jumplogin(view);
        return view;
    }
    public void jumplogin(View v){
        TextView login=(TextView) v.findViewById(R.id.textView);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //跳转登录
                Intent it=new Intent(getActivity(), MainActivity.class);
                startActivity(it);
            }
        });
    }
    public void initUser(View v){
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String name=sp.getString("username","notsave");
        TVusername=(TextView) v.findViewById(R.id.cmember_textView1);
        if (name!="notsave"){
            TVusername.setText(name);
        }
    }
}
