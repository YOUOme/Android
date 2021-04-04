package com.example.youome.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youome.R;

public class FragmentTab4O2O extends Fragment {
    private ImageView addFunding;
    private TextView bt_addFunding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4,null);
        ArrayList<ItemTab3> list = new ArrayList<>();
        list.add(new ItemTab3("13%","5개월","100,000원","현재 0명 신청중"));
        list.add(new ItemTab3("16%","5개월","300,000원","현재 3명 신청중"));
        list.add(new ItemTab3("13%","4개월","400,000원","현재 1명 신청중"));

        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterTab3 adapter = new AdapterTab3(getContext(),list,2);
        recyclerView.setAdapter(adapter);

        addFunding = (ImageView)view.findViewById(R.id.addfunding);
        addFunding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(),ActivityP2PFunding.class);
                //startActivity(intent);
            }
        });

        bt_addFunding = (TextView)view.findViewById(R.id.bt_addfunding);
        bt_addFunding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(),ActivityP2PFunding.class);
                //startActivity(intent);
            }
        });
        return view;
    }
}
