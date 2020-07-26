package com.kftc.openbankingsample2.biz.center_auth.api.transfer_result;

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
import com.kftc.openbankingsample2.common.data.ApiCallTransferResultResponse;
import com.kftc.openbankingsample2.common.data.ResMsg;
import com.kftc.openbankingsample2.common.data.TransferResult;
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
 * 이체결과조회
 */
public class CenterAuthAPITransferResultFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String checkType;

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
        view = inflater.inflate(R.layout.fragment_center_auth_api_transfer_result, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 액세스 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getCenterAuthAccessToken(Scope.OOB));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.OOB));

        // 출금, 입금구분
        List<TwoString> checkTypeMenuList = new ArrayList<>();
        checkTypeMenuList.add(new TwoString("1(출금이체)", "1"));
        checkTypeMenuList.add(new TwoString("2(입금이체)", "2"));
        AppCompatSpinner spCntrAccountType = view.findViewById(R.id.spCheckType);
        ArrayAdapter<TwoString> cntrAccountTypeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, checkTypeMenuList);
        spCntrAccountType.setAdapter(cntrAccountTypeAdapter);
        spCntrAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = cntrAccountTypeAdapter.getItem(position);
                if (twoString != null) {
                    checkType = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 요청일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 요청건수
        EditText etReqCnt = view.findViewById(R.id.etReqCnt);

        ////////// 이하 반복부(요청목록) //////////
        // 거래순번
        EditText etTranNo = view.findViewById(R.id.etTranNo);

        // 원거래 거래고유번호(참가은행)
        String clientUseCode = CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_USE_CODE);
        String unique9String = "123456789";    // 이용기관 부여번호를 시뮬레이터 데이터로 채움
        EditText etOrgBankTranId = view.findViewById(R.id.etOrgBankTranId);
        etOrgBankTranId.setText(String.format("%sU%s", clientUseCode, unique9String));

        // 원거래 거래일자(참가은행)
        EditText etOrgBankTranDate = view.findViewById(R.id.etOrgBankTranDate);
        etOrgBankTranDate.setText(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));

        // 원거래 거래금액
        KmUtilMoneyEditText moneyOrgTranAmt = view.findViewById(R.id.moneyOrgTranAmt);

        // 이체결과조회 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);

            // 요청전문
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("check_type", checkType);
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
            jsonObject.addProperty("org_bank_tran_id", etOrgBankTranId.getText().toString());
            jsonObject.addProperty("org_bank_tran_date", etOrgBankTranDate.getText().toString());
            jsonObject.addProperty("org_tran_amt", moneyOrgTranAmt.getTextString());       // 쉼표(,)를 제외하고 추출
            jsonArray.add(jsonObject);

            // 반복부값을 파라미터에 추가
            paramMap.put("req_list", jsonArray);

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .transferResult("Bearer " + accessToken, paramMap)
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
            ApiCallTransferResultResponse result =
                    new Gson().fromJson(responseJson, ApiCallTransferResultResponse.class);

            // 반복부 파싱
            ArrayList<TransferResult> transferResultList = result.getRes_list();
            if (transferResultList != null) {
                Double tranTotalAmt = 0.0;
                for (TransferResult transferResult : transferResultList) {
                    tranTotalAmt += transferResult.getTranAmtDouble();
                }

                showAlert("정상", "이체확인 성공!! 이체총금액: " + Utils.moneyForm(String.valueOf(tranTotalAmt)) + "원");
            }

        }

        @Override
        public void onFailure(Call<Map> call, Throwable t) {
            hideProgress();
            showAlert("통신실패", "서버 접속에 실패하였습니다.", t.getMessage());
        }
    };
}
