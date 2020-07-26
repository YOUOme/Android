package com.kftc.openbankingsample2.biz.self_auth.api.user_me;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.api.SelfAuthAPIFragment;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.data.ApiCallUserMeResponse;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.recyclerview.KmRecyclerViewDividerHeight;

/**
 * 사용자정보조회 결과
 */
public class SelfAuthAPIUserMeResultFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;
    private RecyclerView recyclerView;
    private SelfAuthAPIUserMeResultAdapter adapter;

    // data
    private Bundle args;
    private ApiCallUserMeResponse result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();

        result = args.getParcelable("result");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_self_auth_api_user_me_result, container, false);
        initView();
        return view;
    }

    void initView() {

        // 상단 고객정보
        ((TextView) view.findViewById(R.id.tvUserNameInfo)).setText(String.format("%s(%s)", result.getUser_name(), result.getUser_seq_no()));
        ((TextView) view.findViewById(R.id.tvUserCi)).setText(result.getUser_ci());
        ((TextView) view.findViewById(R.id.tvResCnt)).setText(String.format("%s개", result.getRes_cnt()));

        // 계좌정보(반복부)
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new KmRecyclerViewDividerHeight(30));

        view.findViewById(R.id.btnNext).setOnClickListener(v -> goNext());

        initData();
    }

    void initData() {

        // 리사이클러뷰에 어댑터 설정
        adapter = new SelfAuthAPIUserMeResultAdapter(result.getRes_list());
        recyclerView.setAdapter(adapter);

        // 다른 메뉴에서 계좌정보를 사용할 수 있도록 저장.
        if (adapter.getItemCount() > 0) {
            SelfAuthUtils.saveSelfAuthBankAccountList(adapter.getItemList());
            Toast.makeText(context, "계좌정보 저장됨", Toast.LENGTH_SHORT).show();
        }

        // CI 정보 저장.
        Utils.saveData(SelfAuthConst.SELF_AUTH_USER_CI, result.getUser_ci());
    }

    void goNext() {
        startFragment(SelfAuthAPIFragment.class, null, R.string.fragment_id_self_api_call);
    }
}
