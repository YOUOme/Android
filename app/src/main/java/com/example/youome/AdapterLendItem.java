package com.example.youome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterLendItem extends BaseAdapter {
    private ArrayList<ItemData> mItems = new ArrayList<>();

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
        Context context = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_trade2,viewGroup,false);
        }

        TextView name = (TextView)view.findViewById(R.id.item_name);
        TextView phone = (TextView)view.findViewById(R.id.phone);
        TextView credit = (TextView)view.findViewById(R.id.good);
        TextView bt_lend = (TextView) view.findViewById(R.id.bt_lend);

        name.setText(getItem(i).getName());
        phone.setText(getItem(i).getPhone());
        credit.setText(getItem(i).getCredit());

        final int para = i;
        bt_lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getItem(para);
            }
        });
        return view;
    }

    public void addItem(String pName,String pPhone,String pCredit){
        ItemData mItem = new ItemData();
        mItem.setName(pName);
        mItem.setPhone(pPhone);
        mItem.setCredit(pCredit);
        mItems.add(mItem);
    }

    class ItemData{
        private String name, phone, credit;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getCredit() { return credit; }
        public void setCredit(String credit) { this.credit = credit; }
    }
}


