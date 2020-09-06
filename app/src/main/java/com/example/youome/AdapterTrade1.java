package com.example.youome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterTrade1 extends RecyclerView.Adapter<AdapterTrade1.ViewHolder> {

    private ArrayList<ItemTrade> mData = new ArrayList<>();
    private int backgroundColorID = 0, mode;  // mode=1 : lend , mode=2 : repay  // background 1 : 상단, 0 : 하단
    private Context context;

    public AdapterTrade1(Context context, ArrayList<ItemTrade> list,int colorID,int mode) {
        mData = list;
        backgroundColorID = colorID;
        this.mode = mode;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView type,name,date,money;
        ImageView bt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.tx_type);
            name = itemView.findViewById(R.id.item_name);
            date = itemView.findViewById(R.id.item_date);
            money = itemView.findViewById(R.id.item_money);
            bt = itemView.findViewById(R.id.bt_detail);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_trade,parent,false);

        AdapterTrade1.ViewHolder vh = new AdapterTrade1.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemTrade item = mData.get(position);         // 랜더링하기위한 정보 데이터
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.money.setText(item.getMoney());

        if(backgroundColorID != 0)
            holder.itemView.setBackgroundResource(backgroundColorID);

        if(item.getType() == 1){
            holder.type.setText("빌린돈");
            holder.type.setTextColor(Color.parseColor("#d34947"));
        }
        else{
            holder.type.setText("빌려준돈");
            holder.type.setTextColor(Color.parseColor("#57A7D9"));
            holder.bt.getBackground().setTint(Color.parseColor("#57A7D9"));
        }

        if(backgroundColorID != 0) {
            holder.name.setTextColor(Color.WHITE);
            holder.date.setTextColor(Color.WHITE);
            holder.money.setTextColor(Color.WHITE);
        }
        else {
            holder.bt.setColorFilter(Color.WHITE);
            holder.bt.getBackground().setTint(Color.parseColor("#14213d"));
        }

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActivityIOU.class);
                intent.putExtra("mode",mode);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
