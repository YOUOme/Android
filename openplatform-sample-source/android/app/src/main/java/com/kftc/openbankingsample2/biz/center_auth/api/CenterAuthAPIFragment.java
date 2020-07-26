package com.kftc.openbankingsample2.biz.center_auth.api;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.account_balance.CenterAuthAPIAccountBalanceFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.account_cancel.CenterAuthAPIAccountCancelFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.account_list.CenterAuthAPIAccountListRequestFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.account_transaction.CenterAuthAPIAccountTransactionRequestFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.inquiry_realname.CenterAuthAPIInquiryRealNameFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.inquiry_receive.CenterAuthAPIInquiryReceiveFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.inquiry_remitlist.CenterAuthAPIInquiryRemitListFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.return_claim.CenterAuthAPIReturnClaimFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.return_result.CenterAuthAPIReturnResultFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_deposit.CenterAuthAPITransferDepositFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_result.CenterAuthAPITransferResultFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_withdraw.CenterAuthAPITransferWithdrawFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.user_me.CenterAuthAPIUserMeRequestFragment;

/**
 * API 호출 메뉴
 */
public class CenterAuthAPIFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center_auth_api, container, false);
        initView();
        return view;
    }

    void initView() {

        // 사용자 정보조회
        view.findViewById(R.id.btnInqrUserInfoPage).setOnClickListener(v -> startFragment(CenterAuthAPIUserMeRequestFragment.class, args, R.string.fragment_id_api_call_userme));

        // 잔액조회
        view.findViewById(R.id.btnInqrBlncPage).setOnClickListener(v -> startFragment(CenterAuthAPIAccountBalanceFragment.class, args, R.string.fragment_id_api_call_balance));

        // 거래내역조회
        view.findViewById(R.id.btnInqrTranRecPage).setOnClickListener(v -> startFragment(CenterAuthAPIAccountTransactionRequestFragment.class, args, R.string.fragment_id_api_call_transaction));

        // 계좌실명조회
        view.findViewById(R.id.btnInqrRealNamePage).setOnClickListener(v -> startFragment(CenterAuthAPIInquiryRealNameFragment.class, args, R.string.fragment_id_api_call_realname));

        // 출금이체
        view.findViewById(R.id.btnTrnsWDPage).setOnClickListener(v -> startFragment(CenterAuthAPITransferWithdrawFragment.class, args, R.string.fragment_id_api_call_withdraw));

        // 입금이체(핀테크이용번호)
        view.findViewById(R.id.btnTrnsDPPage).setOnClickListener(v -> startFragment(CenterAuthAPITransferDepositFragment.class, args, R.string.fragment_id_api_call_deposit));

        // 등록계좌조회
        view.findViewById(R.id.btnAccountList).setOnClickListener(v -> startFragment(CenterAuthAPIAccountListRequestFragment.class, args, R.string.fragment_id_api_call_account));

        // 계좌해지
        view.findViewById(R.id.btnAccountCancel).setOnClickListener(v -> startFragment(CenterAuthAPIAccountCancelFragment.class, args, R.string.fragment_id_api_call_account_cancel));

        // 이체결과조회
        view.findViewById(R.id.btnTransferResult).setOnClickListener(v -> startFragment(CenterAuthAPITransferResultFragment.class, args, R.string.fragment_id_api_call_transfer_result));

        // 송금인정보조회
        view.findViewById(R.id.btnInqrRemitList).setOnClickListener(v -> startFragment(CenterAuthAPIInquiryRemitListFragment.class, args, R.string.fragment_id_api_call_inquiry_remitlist));

        // 수취조회
        view.findViewById(R.id.btnInqrReceive).setOnClickListener(v -> startFragment(CenterAuthAPIInquiryReceiveFragment.class, args, R.string.fragment_id_api_call_inquiry_receive));

        // 자금반환청구
        view.findViewById(R.id.btnReturnClaim).setOnClickListener(v -> startFragment(CenterAuthAPIReturnClaimFragment.class, args, R.string.fragment_id_api_call_return_claim));

        // 자금반환결과조회
        view.findViewById(R.id.btnReturnResult).setOnClickListener(v -> startFragment(CenterAuthAPIReturnResultFragment.class, args, R.string.fragment_id_api_call_return_result));
    }

    @Override
    public void onBackPressed() {
        startFragment(CenterAuthHomeFragment.class, null, R.string.fragment_id_center);
    }

}
