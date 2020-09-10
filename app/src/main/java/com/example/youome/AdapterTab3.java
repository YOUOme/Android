package com.example.youome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterTab3 extends RecyclerView.Adapter<AdapterTab3.ViewHolder> {

    private ArrayList<ItemTab3> mData;
    private int mode;   // tab 3 : 1, tab 4 : 2
    private Context context;

    public AdapterTab3(Context context, ArrayList<ItemTab3> list, int mode) {
        mData = list;
        this.mode = mode;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tx_margin, tx_month, tx_money, tx_current;
        TextView bt_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tx_margin = (TextView)itemView.findViewById(R.id.tx_margin);
            tx_month = (TextView)itemView.findViewById(R.id.tx_month);
            tx_money = (TextView)itemView.findViewById(R.id.tx_money);
            tx_current = (TextView)itemView.findViewById(R.id.tx_current);
            bt_detail = (TextView)itemView.findViewById(R.id.bt_detail);
        }
    }

    @NonNull
    @Override
    public AdapterTab3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_fundingp2p,parent,false);

        AdapterTab3.ViewHolder vh = new AdapterTab3.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTab3.ViewHolder holder, int position) {
        ItemTab3 item = mData.get(position);         // 랜더링하기위한 정보 데이터
        holder.tx_margin.setText(item.getMargin());
        holder.tx_month.setText(item.getMonth());
        holder.tx_money.setText(item.getMoney());
        holder.tx_current.setText(item.getCurrent());

        if(mode == 2) {
            holder.bt_detail.getBackground().setTint(Color.parseColor("#d34947"));
            holder.tx_current.setTextColor(Color.parseColor("d34947"));
        }

        holder.bt_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(context,ActivityIOU.class);
                //intent.putExtra("mode",mode);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

