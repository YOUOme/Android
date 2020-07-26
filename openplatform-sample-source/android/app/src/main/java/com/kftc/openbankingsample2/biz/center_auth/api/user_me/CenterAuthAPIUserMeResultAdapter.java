package com.kftc.openbankingsample2.biz.center_auth.api.user_me;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.common.data.BankAccount;
import com.kftc.openbankingsample2.common.util.view.recyclerview.KmRecyclerViewArrayAdapter;
import com.kftc.openbankingsample2.common.util.view.recyclerview.KmRecyclerViewHolder;

import java.util.ArrayList;

/**
 * 사용자정보조회, 등록계좌조회 어댑터
 */
public class CenterAuthAPIUserMeResultAdapter extends KmRecyclerViewArrayAdapter<BankAccount> {
    public CenterAuthAPIUserMeResultAdapter(ArrayList<BankAccount> itemList) {
        super(itemList);
    }

    @NonNull
    @Override
    public KmRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_center_auth_api_user_me_result, parent, false);
        return new ApiCallUserMeResultViewHolder(v, this);
    }

    public static class ApiCallUserMeResultViewHolder extends KmRecyclerViewHolder<BankAccount> {

        private CenterAuthAPIUserMeResultAdapter adapter;

        public ApiCallUserMeResultViewHolder(View v, CenterAuthAPIUserMeResultAdapter adapter) {
            super(v);
            this.adapter = adapter;
        }

        @Override
        public void onBindViewHolder(View view, int position, BankAccount item, boolean isSelected, boolean isExpanded, boolean isEtc) {

            ((TextView) view.findViewById(R.id.tvFintechUseNum)).setText(item.getFintech_use_num());
            ((TextView) view.findViewById(R.id.tvBankInfo)).setText(String.format("%s(%s)", item.getBank_name(), item.getBank_code_std()));

            // 계좌정보
            ((TextView) view.findViewById(R.id.tvAccountInfo)).setText(String.format("%s  %s", item.getAccountNum(), item.getAccount_holder_name()));

            // 조회, 출금 동의
            ((TextView) view.findViewById(R.id.tvAgree)).setText(String.format("조회: %s, 출금: %s", item.isInquiry_agree_yn() ? "동의" : "미동의", item.isTransfer_agree_yn() ? "동의" : "미동의"));

            // 등록계좌정보
            if (!item.getAccount_state().isEmpty()) {
                view.findViewById(R.id.trPayerNum).setVisibility(View.GONE);
                view.findViewById(R.id.trAccountState).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.tvAccountState)).setText(item.getAccountState());
            }

            // 사용자정보조회 : 납부자번호(추가)
            else {
                view.findViewById(R.id.trPayerNum).setVisibility(View.VISIBLE);
                view.findViewById(R.id.trAccountState).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.tvPayerNum)).setText(item.getPayer_num());
            }
        }
    }
}
