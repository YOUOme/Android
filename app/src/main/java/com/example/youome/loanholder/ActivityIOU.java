package com.example.youome.loanholder;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.youome.network.NetworkAPI;
import com.example.youome.network.NetworkRectrofitClient;
import com.example.youome.R;
import com.google.gson.annotations.SerializedName;

public class ActivityIOU extends AppCompatActivity {
    Button bt_sign;

    NetworkAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_o_u);

        service = NetworkRectrofitClient.getClient().create(NetworkAPI.class);

        bt_sign = (Button)findViewById(R.id.bt_sign);
        bt_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(getApplicationContext(), ActivityIOUtransfer.class);
                startActivity(intent);
                finish();
                /*final ProgressDialog progressDialog = new ProgressDialog(ActivityIOU.this);
                progressDialog.setMessage("김오미님의 승인을 기다리는 중...");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        // ToDo - block chain
                        try{
                            startRequest(new ReqData("dkfkalsdfklfdskldfs134"));
                        }catch (Exception e){
                            Log.d("dddd","서버요청 오류");
                        }

                        try{
                                 Thread.sleep(5000);}
                        catch (Exception e){}

                        startActivity(intent);
                        progressDialog.dismiss();
                        finish();
                    }
                };
                thread.start();*/
            }
        });
    }

    // Network call
    /*private void startRequest(ReqData data) {
        service.callData(data).enqueue(new Callback<ResData>() {
            @Override
            public void onResponse(Call<ResData> call, Response<ResData> response) {
                ResData result = response.body();
                Log.d("dddd response",result.message+" , "+result.code);
            }
            @Override
            public void onFailure(Call<ResData> call, Throwable t) { t.printStackTrace(); }
        });
    }*/

    public class ReqData{
        @SerializedName("receive_user_id")
        String receive_user_id;

        public ReqData(String receive_user_id){
            this.receive_user_id = receive_user_id;
        }
    }

    public class ResData{
        @SerializedName("message")
        String message;
        @SerializedName("code")
        int code;

        public ResData(String message, int code) {
            this.message = message;
            this.code = code;
        }
    }
}