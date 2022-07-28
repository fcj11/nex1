package Adapter;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.nex.R;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
public class GourmetBaseAdapter extends BaseAdapter {
    List<HashMap>data;
    Context mContext;
    ViewHolder viewHolder = null;
    int count[];

    public GourmetBaseAdapter(List<HashMap>mydata,Context myContext){
        data =mydata;
        mContext=myContext;
        count = new int[mydata.size()];
    }
    public int getCount(){
        return data.size();
    }
    public Object getItem(int position){
        return null;
    }
    public long getItemId(int position){
        return 0;
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView=mInflater.inflate(R.layout.buju_gourmet_listview,null);
            viewHolder.name=(TextView)convertView
                    .findViewById(R.id.buju_gourment_username);
            viewHolder.date=(TextView)convertView
                    .findViewById(R.id.buju_gourment_time);
            viewHolder.comment=(TextView)convertView
                    .findViewById(R.id.buju_gourment_text);
            viewHolder.image=(ImageView)convertView
                    .findViewById(R.id.buju_gourment_pic);
            viewHolder.praise=(ImageView)convertView
                    .findViewById(R.id.buju_gourment_praise);
            viewHolder.praise_count=(TextView)convertView
                    .findViewById(R.id.buju_gourment_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(data.get(position).get("name").toString());
        viewHolder.date.setText(data.get(position).get("date").toString());
        viewHolder.comment.setText(data.get(position).get("comment").toString());
        viewHolder.image.setBackgroundResource((Integer)data.get(position).get("image"));
        viewHolder.praise_count.setText(count[position]+"");
        viewHolder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[position]++;
                GourmetBaseAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;

    };
}

