package com.example.youome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAlarmItem extends BaseAdapter {
    private ArrayList<ItemData> mItems = new ArrayList<>();
    private int backgroundColorID = 0;  // mode=1 : lend , mode=2 : repay
    private Context context;

    public AdapterAlarmItem(Context context){
        this.context = context;
    }

    public AdapterAlarmItem(Context context,int colorID) {
        backgroundColorID = colorID;
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
            view = inflater.inflate(R.layout.item_trade3,viewGroup,false);
        }

        if(backgroundColorID != 0)
            view.setBackgroundResource(backgroundColorID);

        TextView trade = (TextView)view.findViewById(R.id.trade_name);
        TextView date = (TextView)view.findViewById(R.id.al_date);
        TextView money = (TextView)view.findViewById(R.id.al_money);
        TextView name = (TextView)view.findViewById(R.id.al_name);
        ImageView bt = (ImageView) view.findViewById(R.id.bt_al_detail);

        if(backgroundColorID != 0) {
            trade.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            date.setTextColor(Color.WHITE);
            money.setTextColor(Color.WHITE);
        }

        name.setText(getItem(i).getName());
        date.setText(getItem(i).getDate());
        money.setText(getItem(i).getMoney());
        trade.setText(getItem(i).getTrade());

        final int para = i;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(context,ActivityIOU.class);
                //context.startActivity(intent);
            }
        });
        return view;
    }

    public void addItem(String pTrade,String  pDate,String pMoney,String pName){
        ItemData mItem = new ItemData();
        mItem.setName(pName);
        mItem.setDate(pDate);
        mItem.setMoney(pMoney);
        mItem.setTrade(pTrade);
        mItems.add(mItem);
    }

    class ItemData{
        private String trade,date,money,name;

        public String getTrade() { return trade; }
        public void setTrade(String trade) { this.trade = trade; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getMoney() { return money; }
        public void setMoney(String money) { this.money = money; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}

