package com.kftc.openbankingsample2.biz.self_auth.api;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.api.inquiry_receive.CenterAuthAPIInquiryReceiveFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.inquiry_remitlist.CenterAuthAPIInquiryRemitListFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.return_claim.CenterAuthAPIReturnClaimFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.return_result.CenterAuthAPIReturnResultFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_result.CenterAuthAPITransferResultFragment;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthHomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.account_balance.SelfAuthAPIAccountBalanceFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.account_cancel.SelfAuthAPIAccountCancelFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.account_list.SelfAuthAPIAccountListRequestFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.account_transaction.SelfAuthAPIAccountTransactionRequestFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.inquiry_fdsresult.SelfAuthAPIInquiryFdsDetectFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.inquiry_realname.SelfAuthAPIInquiryRealNameFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.inquiry_receive.SelfAuthAPIInquiryReceiveFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.inquiry_remitlist.SelfAuthAPIInquiryRemitListFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.return_claim.SelfAuthAPIReturnClaimFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.return_result.SelfAuthAPIReturnResultFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.transfer_deposit.SelfAuthAPITransferDepositFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.transfer_result.SelfAuthAPITransferResultFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.transfer_withdraw.SelfAuthAPITransferWithdrawFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.user_me.SelfAuthAPIUserMeRequestFragment;

/**
 * API 호출 메뉴
 */
public class SelfAuthAPIFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_self_api, container, false);
        initView();
        return view;
    }

    void initView() {

        // 사용자 정보조회
        view.findViewById(R.id.btnInqrUserInfoPage).setOnClickListener(v -> startFragment(SelfAuthAPIUserMeRequestFragment.class, args, R.string.fragment_id_api_call_userme));

        // 잔액조회
        view.findViewById(R.id.btnInqrBlncPage).setOnClickListener(v -> startFragment(SelfAuthAPIAccountBalanceFragment.class, args, R.string.fragment_id_api_call_balance));

        // 거래내역조회
        view.findViewById(R.id.btnInqrTranRecPage).setOnClickListener(v -> startFragment(SelfAuthAPIAccountTransactionRequestFragment.class, args, R.string.fragment_id_api_call_transaction));

        // 계좌실명조회
        view.findViewById(R.id.btnInqrRealNamePage).setOnClickListener(v -> startFragment(SelfAuthAPIInquiryRealNameFragment.class, args, R.string.fragment_id_api_call_realname));

        // 출금이체
        view.findViewById(R.id.btnTrnsWDPage).setOnClickListener(v -> startFragment(SelfAuthAPITransferWithdrawFragment.class, args, R.string.fragment_id_api_call_withdraw));

        // 입금이체
        view.findViewById(R.id.btnTrnsDPPage).setOnClickListener(v -> startFragment(SelfAuthAPITransferDepositFragment.class, args, R.string.fragment_id_api_call_deposit));

        // 등록계좌조회
        view.findViewById(R.id.btnAccountList).setOnClickListener(v -> startFragment(SelfAuthAPIAccountListRequestFragment.class, args, R.string.fragment_id_api_call_account));

        // 계좌해지
        view.findViewById(R.id.btnAccountCancel).setOnClickListener(v -> startFragment(SelfAuthAPIAccountCancelFragment.class, args, R.string.fragment_id_api_call_account_cancel));

        // 이체결과조회
        view.findViewById(R.id.btnTransferResult).setOnClickListener(v -> startFragment(SelfAuthAPITransferResultFragment.class, args, R.string.fragment_id_api_call_transfer_result));

        // 송금인정보조회
        view.findViewById(R.id.btnInqrRemitList).setOnClickListener(v -> startFragment(SelfAuthAPIInquiryRemitListFragment.class, args, R.string.fragment_id_api_call_inquiry_remitlist));

        // 수취조회
        view.findViewById(R.id.btnInqrReceive).setOnClickListener(v -> startFragment(SelfAuthAPIInquiryReceiveFragment.class, args, R.string.fragment_id_api_call_inquiry_receive));

        // 자금반환청구
        view.findViewById(R.id.btnReturnClaim).setOnClickListener(v -> startFragment(SelfAuthAPIReturnClaimFragment.class, args, R.string.fragment_id_api_call_return_claim));

        // 자금반환결과조회
        view.findViewById(R.id.btnReturnResult).setOnClickListener(v -> startFragment(SelfAuthAPIReturnResultFragment.class, args, R.string.fragment_id_api_call_return_result));

        // 이상금융거래 탐지내역 조회
        view.findViewById(R.id.btnInquiryFdsDetect).setOnClickListener(v -> startFragment(SelfAuthAPIInquiryFdsDetectFragment.class, args, R.string.fragment_id_api_call_inquiry_fdsdetect));

    }

    @Override
    public void onBackPressed() {
        startFragment(SelfAuthHomeFragment.class, null, R.string.fragment_id_self);
    }

}
