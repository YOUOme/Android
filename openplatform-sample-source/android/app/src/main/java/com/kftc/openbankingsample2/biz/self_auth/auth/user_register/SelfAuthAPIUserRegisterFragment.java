package com.kftc.openbankingsample2.biz.self_auth.auth.user_register;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthHomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 사용자계좌등록(자체인증기관 전용)
 */
public class SelfAuthAPIUserRegisterFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String agmtDataType;

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
        view = inflater.inflate(R.layout.fragment_self_auth_api_user_register, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 액세스 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken));

        // 은행거래고유번호(20자리)
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        setRandomBankTranId(etBankTranId);
        view.findViewById(R.id.btnGenBankTranId).setOnClickListener(v -> setRandomBankTranId(etBankTranId));

        // 은행표준코드
        EditText etBankCode = view.findViewById(R.id.etBankCode);
        etBankCode.setText(Utils.getSavedValueOrDefault(SelfAuthConst.SELF_AUTH_BANK_CODE, etBankCode.getText().toString()));

        // 등록계좌번호
        EditText etAccountNum = view.findViewById(R.id.etAccountNum);
        etAccountNum.setText(Utils.getSavedValueOrDefault(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM, etAccountNum.getText().toString()));

        // 생년월일
        EditText etUserInfo = view.findViewById(R.id.etUserInfo);
        String tUserInfo = Utils.getSavedValue(SelfAuthConst.SELF_AUTH_USER_INFO);
        if (!TextUtils.isEmpty(tUserInfo)) {
            etUserInfo.setText(tUserInfo);
        }

        // 사용자명
        EditText etUserName = view.findViewById(R.id.etUserName);
        String tUserName = Utils.getSavedValue(SelfAuthConst.SELF_AUTH_USER_NAME);
        if (!TextUtils.isEmpty(tUserName)) {
            etUserName.setText(tUserName);
        }

        // CI (사용자정보조회에서 저장된 CI 값을 가져올수 있다)
        EditText etUserCi = view.findViewById(R.id.etUserCi);
        String tUserCi = Utils.getSavedValue(SelfAuthConst.SELF_AUTH_USER_CI);
        if (!TextUtils.isEmpty(tUserCi)) {
            etUserCi.setText(tUserCi);
        }

        // 이메일주소
        EditText etUserEmail = view.findViewById(R.id.etUserEmail);
        String tUserEmail = Utils.getSavedValue(SelfAuthConst.SELF_AUTH_USER_EMAIL);
        if (!TextUtils.isEmpty(tUserEmail)) {
            etUserEmail.setText(tUserEmail);
        }

        // 제3자 정보제공 동의여부
        EditText etInfoPrvdAgmtYn = view.findViewById(R.id.etInfoPrvdAgmtYn);

        // 출금동의여부
        EditText etWdAgmtYn = view.findViewById(R.id.etWdAgmtYn);

        // 동의자료구분 : 드랍다운 메뉴
        List<TwoString> agmtDataTypeMenuList = new ArrayList<>();
        agmtDataTypeMenuList.add(new TwoString("(미설정)", ""));
        agmtDataTypeMenuList.add(new TwoString("1(서면)", "1"));
        agmtDataTypeMenuList.add(new TwoString("2(공인인증)", "2"));
        agmtDataTypeMenuList.add(new TwoString("3(일반인증)", "3"));
        agmtDataTypeMenuList.add(new TwoString("4(녹취)", "4"));
        agmtDataTypeMenuList.add(new TwoString("5(ARS)", "5"));
        agmtDataTypeMenuList.add(new TwoString("6(기타)", "6"));
        AppCompatSpinner spAgmtDataType = view.findViewById(R.id.spAgmtDataType);
        ArrayAdapter<TwoString> agmtDataTypeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, agmtDataTypeMenuList);
        spAgmtDataType.setAdapter(agmtDataTypeAdapter);
        spAgmtDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = agmtDataTypeAdapter.getItem(position);
                if (twoString != null) {
                    agmtDataType = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // scope
        EditText etScope = view.findViewById(R.id.etScope);
        view.findViewById(R.id.btnScope).setOnClickListener(v -> showScopeSingleDialog(etScope, AppData.scopeInqTranList, selectedItem -> {

            // scope를 선택했을때 제3자 정보제공 동의여부와 출금동의여부를 같이 설정
            if ("inquiry".equals(selectedItem)) {
                etInfoPrvdAgmtYn.setText("Y");
                etWdAgmtYn.setText("");
                spAgmtDataType.setSelection(0);
            }
            else if("transfer".equals(selectedItem)) {
                etInfoPrvdAgmtYn.setText("");
                etWdAgmtYn.setText("Y");
                spAgmtDataType.setSelection(1);
            }
        }));

        // 거래일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 계좌등록 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);
            String bankCode = etBankCode.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_BANK_CODE, bankCode);
            String accountNum = etAccountNum.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM, accountNum);
            String userInfo = etUserInfo.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_USER_INFO, userInfo);
            String userName = etUserName.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_USER_NAME, userName);
            String userCi = etUserCi.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_USER_CI, userCi);
            String userEmail = etUserEmail.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_USER_EMAIL, userEmail);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("bank_code_std", bankCode);
            paramMap.put("register_account_num", accountNum);
            paramMap.put("user_info", userInfo);
            paramMap.put("user_name", userName);
            paramMap.put("user_ci", userCi);
            paramMap.put("user_email", etUserEmail.getText().toString());
            paramMap.put("scope", etScope.getText().toString());
            paramMap.put("info_prvd_agmt_yn", etInfoPrvdAgmtYn.getText().toString());
            paramMap.put("wd_agmt_yn", etWdAgmtYn.getText().toString());
            paramMap.put("agmt_data_type", agmtDataType);
            paramMap.put("tran_dtime", etTranDtime.getText().toString());

            SelfAuthApiRetrofitAdapter.getInstance()
                    .userRegister("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("user_seq_no", "계좌등록 완료.\n사용자 일련번호",
                            responseJson -> startFragment(SelfAuthHomeFragment.class, null, R.string.fragment_id_self)));
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());

    }
}
