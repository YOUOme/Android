package com.example.youome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

public class AdapterAccountItem extends BaseAdapter {
    private ArrayList<ItemData> mItems = new ArrayList<>();

    public ArrayList<ItemData> getArrayList(){ return mItems; }
    public void setArrayList(ArrayList<ItemData> a){ mItems =a; }

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
        TextView account = (TextView)view.findViewById(R.id.phone);
        TextView credit = (TextView)view.findViewById(R.id.good);
        TextView select = (TextView) view.findViewById(R.id.bt_lend);

        name.setText(getItem(i).getName());
        account.setText(getItem(i).getAccount());
        credit.setVisibility(View.GONE);
        select.setText("계좌 선택");

        final int para = i;
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    public void addItem(String pName,String pAccount){
        ItemData mItem = new ItemData();
        mItem.setName(pName);
        mItem.setAccount(pAccount);
        mItems.add(mItem);
    }

    public class ItemData{
        private String name,account;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}



