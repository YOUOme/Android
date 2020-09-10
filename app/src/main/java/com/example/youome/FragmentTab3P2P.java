package com.example.youome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentTab3P2P extends Fragment {
    private ImageView addFunding;
    private TextView bt_addFunding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3,null);
        ArrayList<ItemTab3> list = new ArrayList<>();
        list.add(new ItemTab3("16%","5개월","500,000원","현재 250,000원 투자 완료"));
        list.add(new ItemTab3("16%","5개월","300,000원","현재 100,000원 투자 완료"));
        list.add(new ItemTab3("13%","4개월","400,000원","현재 130,000원 투자 완료"));

        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterTab3 adapter = new AdapterTab3(getContext(),list,1);
        recyclerView.setAdapter(adapter);

        addFunding = (ImageView)view.findViewById(R.id.addfunding);
        addFunding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ActivityP2PFunding.class);
                startActivity(intent);
            }
        });

        bt_addFunding = (TextView)view.findViewById(R.id.bt_addfunding);
        bt_addFunding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ActivityP2PFunding.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
