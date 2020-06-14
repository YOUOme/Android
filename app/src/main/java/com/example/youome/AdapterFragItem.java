package com.example.youome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterFragItem extends BaseAdapter {
    private ArrayList<ItemData> mItems = new ArrayList<>();
    private int backgroundColorID = 0, mode;  // mode=1 : lend , mode=2 : repay
    private Context context;

    public ArrayList<ItemData> getArrayList(){ return mItems; }
    public void setArrayList(ArrayList<ItemData> a){
        mItems.clear();
        mItems.addAll(a);
    }

    public AdapterFragItem(Context context, int mode){
        this.mode = mode;
        this.context = context;
    }

    public AdapterFragItem(Context context,int colorID,int mode) {
        backgroundColorID = colorID;
        this.mode = mode;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ItemData getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_trade,viewGroup,false);
        }

        if(backgroundColorID != 0)
            view.setBackgroundResource(backgroundColorID);

        TextView name = (TextView)view.findViewById(R.id.item_name);
        TextView date = (TextView)view.findViewById(R.id.item_date);
        TextView money = (TextView)view.findViewById(R.id.item_money);
        ImageView bt = (ImageView) view.findViewById(R.id.bt_detail);

        if(backgroundColorID != 0) {
            name.setTextColor(Color.WHITE);
            date.setTextColor(Color.WHITE);
            money.setTextColor(Color.WHITE);
        }

        name.setText(getItem(i).getName());
        date.setText(getItem(i).getDate());
        money.setText(getItem(i).getMoney());

        final int para = i;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActivityIOU.class);
                intent.putExtra("mode",mode);
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void addItem(String pName,String pDate,String pMoney){
        ItemData mItem = new ItemData();
        mItem.setName(pName);
        mItem.setDate(pDate);
        mItem.setMoney(pMoney);
        mItems.add(mItem);
    }

    class ItemData{
        private String name, date, money;
        public String getName(){ return name; }
        public String getDate(){return date;}
        public String getMoney(){return money;}
        public void setName(String name) { this.name = name; }
        public void setDate(String date) { this.date = date; }
        public void setMoney(String money) { this.money = money; }
    }
}
