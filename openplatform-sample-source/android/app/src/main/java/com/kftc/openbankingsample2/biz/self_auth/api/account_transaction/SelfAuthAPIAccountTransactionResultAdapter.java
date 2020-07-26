package com.kftc.openbankingsample2.biz.self_auth.api.account_transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.common.data.Transaction;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.recyclerview.KmRecyclerViewArrayAdapter;
import com.kftc.openbankingsample2.common.util.view.recyclerview.KmRecyclerViewHolder;

import java.util.ArrayList;

/**
 * 거래내역조회 어댑터
 */
public class SelfAuthAPIAccountTransactionResultAdapter extends KmRecyclerViewArrayAdapter<Transaction> {
    public SelfAuthAPIAccountTransactionResultAdapter(ArrayList<Transaction> itemList) {
        super(itemList);
    }

    @NonNull
    @Override
    public KmRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // layout은 center와 같이 사용
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_center_auth_api_account_transaction_result, parent, false);
        return new ApiCallAccountTransactionResultViewHolder(v, this);
    }

    public static class ApiCallAccountTransactionResultViewHolder extends KmRecyclerViewHolder<Transaction> {

        private SelfAuthAPIAccountTransactionResultAdapter adapter;

        public ApiCallAccountTransactionResultViewHolder(View v, SelfAuthAPIAccountTransactionResultAdapter adapter) {
            super(v);
            this.adapter = adapter;
        }

        @Override
        public void onBindViewHolder(View view, int position, Transaction item, boolean isSelected, boolean isExpanded, boolean isEtc) {

            ((TextView) view.findViewById(R.id.tvTranDate)).setText(Utils.dateForm(item.getTran_date(), "-"));
            ((TextView) view.findViewById(R.id.tvInoutType)).setText(item.getInout_type());

            // 거래금액
            ((TextView) view.findViewById(R.id.tvTranAmt)).setText(Utils.moneyForm(item.getTran_amt()));

            // 거래후잔액
            ((TextView) view.findViewById(R.id.tvAfterBalanceAmt)).setText(Utils.moneyForm(item.getAfter_balance_amt()));

            // 통장인자내용
            ((TextView) view.findViewById(R.id.tvPrintContent)).setText(item.getPrint_content());
        }
    }
}
