package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class MainActivity extends Activity {
    EditText ETusername,ETpassword;
    ImageView loginup;
    String isok;
    String answer;
    ProgressDialog dialog;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    dialog.dismiss();

                    String user=jsonParser(answer);
                    String answer=msg.obj.toString().trim();
//                    Toast.makeText(getApplicationContext(),answer,Toast.LENGTH_SHORT).show();
                    if (isok.equals("ok") && user!=null){
                        save(user);
                        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"用户不存在或密码错误",Toast.LENGTH_LONG).show();
                    }

                    break;
//                case 2:
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(),"网络异常，请重新链接",Toast.LENGTH_SHORT).show();
//                    break;
//                case 3:
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(),"出现不明异常，让我理一理",Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(),"系统繁忙请稍后再试",Toast.LENGTH_SHORT).show();
//                    break;
//            }
//                super.handleMessage(msg);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intView();
    }
    private void intView(){
        //TODO Auto-generated method stub
        ETusername=(EditText) this.findViewById(R.id.Login_editText1);
        ETpassword=(EditText) this.findViewById(R.id.Login_editText2);
        loginup=(ImageView) this.findViewById(R.id.Login_imageView1);
        loginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String username = ETusername.getText().toString();
                String password = ETpassword.getText().toString();
                if (username.length()!=0 && password.length()!=0){
                    dialog= new ProgressDialog(MainActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("正在登录，请稍后....");
                    dialog.setCancelable(false);
                    dialog.show();
                    LoginThread(username,password);
                }else {
                    Toast.makeText(MainActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void LoginThread(final String uaername,final String password){
        Thread login = new Thread(new Runnable() {
            @Override
            public void run() {
                LoginHttp(uaername,password);
            }
        });
        login.start();
    }
    public void LoginHttp(String username,String password){
        HttpURLConnection conn =null;
        InputStream is =null;
        try{
            String path = "http;//172.20.51.99/login.ashx";
            path = path+"?username"+ username +"&password=" + password;
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset","UTF-8");
            if (conn.getResponseCode()==200){
                is = conn.getInputStream();
                byte[] buffer=new byte[1024];
                int len=0;
                StringBuilder sb = new StringBuilder();
                while ((len=is.read(buffer))!=-1) {
                    is.read(buffer);
                    String data = new String(buffer);
                    sb.append(data);
                }
                String response= sb.toString();
                Message msg = new Message();
                msg.what=1;
                msg.obj=response;
                handler.sendMessage(msg);
            }
        }  catch (MalformedURLException e){
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (Exception e){
            handler.sendEmptyMessage(3);
            e.printStackTrace();
        } finally {
            if (conn !=null){
                conn.disconnect();
            }
            if (is !=null){
                try{
                    is.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void save(String username){
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.commit();
    }
    public String jsonParser(String jsonStr){
        /**
         用户信息合法时，服务器返回值：{"msg":"ok":"UserName":"我是神人"}
         用户信息不合法时，服务器返回值：{"msg":"nook"}
         **/
        String user="";
        try {
            JSONObject jsonObject=new JSONObject(jsonStr);
            isok = jsonObject.getString("msg");
            if (isok.equals("ok")){
                user=jsonObject.getString("UserName");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return user;
    }
}


