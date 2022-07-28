package Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nex.R;

import java.util.HashMap;
import java.util.List;

public class OrderBaseAdapter extends BaseAdapter {
    List<HashMap<String,Object>> data;
    Context mContext;
    ViewHolder viewHolder = null;
    public OrderBaseAdapter(List<HashMap<String,Object>>mydata,Context myContext){
        data =mydata;
        mContext=myContext;
    }
    public int getCount(){
        //TODO Auto-generated menthod stub
        return data.size();
    }
    @Override
    public Object getItem(int positiom){
        //TODO Auto-generated menthod stub
        return null;
    }
    @Override
    public long getItemId(int position){
        //TODO Auto-generated menthod stub
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.buju_order_collectionlist,null);
            viewHolder.name=(TextView) convertView
                    .findViewById(R.id.buju_order_collectionlist_username);
            viewHolder.date=(TextView) convertView
                    .findViewById(R.id.buju_order_collectionlist_time);
            viewHolder.comment=(TextView) convertView
                    .findViewById(R.id.buju_order_collectionlist_text);
            viewHolder.image=(ImageView) convertView
                    .findViewById(R.id.buju_order_collectionlist_pic);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(data.get(position).get("name").toString());
        viewHolder.date.setText(data.get(position).get("date").toString());
        viewHolder.comment.setText(data.get(position).get("comment").toString());
        Drawable bm=(Drawable) data.get(position).get("image");
        viewHolder.image.setBackground(bm);
        return convertView;
    }



}
