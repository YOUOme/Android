package com.kftc.openbankingsample2.biz.center_auth.api.transfer_deposit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.ApiCallTransferDepositResponse;
import com.kftc.openbankingsample2.common.data.ResMsg;
import com.kftc.openbankingsample2.common.data.Transfer;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.KmUtilMoneyEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 입금이체
 */
public class CenterAuthAPITransferDepositFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String cntrAccountType;
    private String transferPurpose;

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
        view = inflater.inflate(R.layout.fragment_center_auth_api_transfer_deposit, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 액세스 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getCenterAuthAccessToken(Scope.OOB));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.OOB));

        // 약정 계좌/계정 구분 : 드랍다운 메뉴
        List<TwoString> cntrAccountTypeMenuList = new ArrayList<>();
        cntrAccountTypeMenuList.add(new TwoString("N(계좌)", "N"));
        cntrAccountTypeMenuList.add(new TwoString("C(계정)", "C"));
        AppCompatSpinner spCntrAccountType = view.findViewById(R.id.spCntrAccountType);
        ArrayAdapter<TwoString> cntrAccountTypeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, cntrAccountTypeMenuList);
        spCntrAccountType.setAdapter(cntrAccountTypeAdapter);
        spCntrAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = cntrAccountTypeAdapter.getItem(position);
                if (twoString != null) {
                    cntrAccountType = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 약정 계좌/계정 번호 : 설정값
        EditText etCntrAccountNum = view.findViewById(R.id.etCntrAccountNum);
        etCntrAccountNum.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM));

        // 입금이체용 암호문구
        EditText etWdPassPhrase = view.findViewById(R.id.etWdPassPhrase);

        // 출금계좌인자내역
        EditText etWdPrintContent = view.findViewById(R.id.etWdPrintContent);

        // 수취인성명 검증여부
        EditText etNameCheckOption = view.findViewById(R.id.etNameCheckOption);

        // 하위가맹점명
        EditText etSubFrncName = view.findViewById(R.id.etSubFrncName);

        // 하위가맹점번호
        EditText etSubFrncNum = view.findViewById(R.id.etSubFrncNum);

        // 하위가맹점 사업자등록번호
        EditText etSubFrncBusinessNum = view.findViewById(R.id.etSubFrncBusinessNum);

        // 입금요청건수
        EditText etReqCnt = view.findViewById(R.id.etReqCnt);

        ////////// 이하 반복부(입금요청목록) //////////
        // 거래순번
        EditText etTranNo = view.findViewById(R.id.etTranNo);

        // 은행거래고유번호(20자리)
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        setRandomBankTranId(etBankTranId);
        view.findViewById(R.id.btnGenBankTranId).setOnClickListener(v -> setRandomBankTranId(etBankTranId));

        // 핀테크이용번호 : 최근 계좌로 기본 설정
        EditText etFintechUseNum = view.findViewById(R.id.etFintechUseNum);
        etFintechUseNum.setText(Utils.getSavedValue(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM));

        // 핀테크이용번호 : 기존 계좌에서 선택
        View.OnClickListener onClickListener = v -> showAccountDialog(etFintechUseNum);
        view.findViewById(R.id.btnSelectFintechUseNum).setOnClickListener(onClickListener);

        // 입금계좌인자내역
        EditText etPrintContent = view.findViewById(R.id.etPrintContent);

        // 거래금액
        KmUtilMoneyEditText moneyTranAmt = view.findViewById(R.id.moneyTranAmt);

        // 요청고객성명
        EditText etReqClientName = view.findViewById(R.id.etReqClientName);
        etReqClientName.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_NAME, etReqClientName.getText().toString()));

        // 요청고객계좌 개설기관 표준코드
        EditText etReqClientBankCode = view.findViewById(R.id.etReqClientBankCode);
        etReqClientBankCode.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_BANK_CODE, etReqClientBankCode.getText().toString()));

        // 요청고객 계좌번호
        EditText etReqClientAccountNum = view.findViewById(R.id.etReqClientAccountNum);
        etReqClientAccountNum.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_ACCOUNT_NUM, etReqClientAccountNum.getText().toString()));

        // 요청고객 회원번호
        EditText etReqClientNum = view.findViewById(R.id.etReqClientNum);

        // 이체용도
        List<TwoString> transferPurposeMenuList = new ArrayList<>();
        transferPurposeMenuList.add(new TwoString("TR(송금)", "TR"));
        transferPurposeMenuList.add(new TwoString("ST(결제)", "ST"));
        transferPurposeMenuList.add(new TwoString("AU(인증)", "AU"));
        AppCompatSpinner spTransferPurpose = view.findViewById(R.id.spTransferPurpose);
        ArrayAdapter<TwoString> transferPurposeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, transferPurposeMenuList);
        spTransferPurpose.setAdapter(transferPurposeAdapter);
        spTransferPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = transferPurposeAdapter.getItem(position);
                if (twoString != null) {
                    transferPurpose = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 요청일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 입금이체 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);
            String fintechUseNum = etFintechUseNum.getText().toString();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM, fintechUseNum);
            String reqClientName = etReqClientName.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_NAME, reqClientName);
            String reqClientBankCode = etReqClientBankCode.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_BANK_CODE, reqClientBankCode);
            String reqClientAccountNum = etReqClientAccountNum.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_ACCOUNT_NUM, reqClientAccountNum);

            // 요청전문
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("cntr_account_type", cntrAccountType);
            paramMap.put("cntr_account_num", etCntrAccountNum.getText().toString());
            paramMap.put("wd_pass_phrase", etWdPassPhrase.getText().toString());
            paramMap.put("wd_print_content", etWdPrintContent.getText().toString());
            paramMap.put("name_check_option", etNameCheckOption.getText().toString());
            paramMap.put("sub_frnc_name", etSubFrncName.getText().toString());
            paramMap.put("sub_frnc_num", etSubFrncNum.getText().toString());
            paramMap.put("sub_frnc_bussiness_num", etSubFrncBusinessNum.getText().toString());
            paramMap.put("tran_dtime", etTranDtime.getText().toString());
            paramMap.put("req_cnt", etReqCnt.getText().toString());

            // 이하 반복부(req_list) : 편의상 1개만 입력
            /*
                (참고사항)
                1. JSONObject 또는 JsonObject 를 사용하여 파라미터맵에 추가하게되면, retrofit 에서 map 을 json 으로 변환하는 과정에서
                   문자열로 인식되어, JSONObject는 따옴표(") 앞에 백슬래시(\)를 붙이고, JSONObject.toString인 경우와 JsonObject는 백슬래시 3개(\\\)를 붙인다.
                   -> 문자열로 인식하지않고 object 로 인식하게 하게위해서 retrofit 설정에서 다음과 같은 옵션이 필요하다.
                     .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().enableComplexMapKeySerialization().create()))
                   -> JSONObject 는 nameValuePairs 구조가 추가로 있어 표현하는데 문제가 있으므로 구글의 JsonObject 를 사용한다.
                2. 위 문제를 간단하게 해결하려면 Object 를 사용하지 않고, ArrayList(json array 와 대응) 와 LinkedHashMap(json object 와 대응) 을 사용해도 된다.
             */

            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tran_no", etTranNo.getText().toString());
            jsonObject.addProperty("bank_tran_id", etBankTranId.getText().toString());
            jsonObject.addProperty("fintech_use_num", fintechUseNum);
            jsonObject.addProperty("print_content", etPrintContent.getText().toString());
            jsonObject.addProperty("tran_amt", moneyTranAmt.getTextString());       // 쉼표(,)를 제외하고 추출
            jsonObject.addProperty("req_client_name", reqClientName);
            jsonObject.addProperty("req_client_bank_code", reqClientBankCode);
            jsonObject.addProperty("req_client_account_num", reqClientAccountNum);
            jsonObject.addProperty("req_client_num", etReqClientNum.getText().toString());
            jsonObject.addProperty("transfer_purpose", transferPurpose);
            jsonArray.add(jsonObject);

            // 반복부값을 파라미터에 추가
            paramMap.put("req_list", jsonArray);

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .transferDepositFinNum("Bearer " + accessToken, paramMap)
                    .enqueue(handleResponse);
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }

    // 결과처리
    Callback<Map> handleResponse = new Callback<Map>() {

        @Override
        public void onResponse(Call<Map> call, Response<Map> response) {
            hideProgress();

            // http ok(200) 아닐경우
            if (!response.isSuccessful()) {
                handleHttpFailure(response);
                return;
            }

            String responseJson = new Gson().toJson(response.body());

            // json 안에 있는 응답코드를 확인
            ResMsg resMsg = new Gson().fromJson(responseJson, ResMsg.class);
            if (!resMsg.isSuccess()) {
                handleApiFailure(resMsg, responseJson);
                return;
            }

            // 응답메시지 파싱
            ApiCallTransferDepositResponse result =
                    new Gson().fromJson(responseJson, ApiCallTransferDepositResponse.class);

            // 반복부 파싱
            ArrayList<Transfer> transferList = result.getRes_list();
            if (transferList != null) {
                Double tranTotalAmt = 0.0;
                for (Transfer transfer : transferList) {
                    tranTotalAmt += transfer.getTranAmtDouble();
                }

                showAlert("정상", "이체성공!! 이체총금액: " + Utils.moneyForm(String.valueOf(tranTotalAmt)) + "원");
            }

        }

        @Override
        public void onFailure(Call<Map> call, Throwable t) {
            hideProgress();
            showAlert("통신실패", "서버 접속에 실패하였습니다.", t.getMessage());
        }
    };

}
