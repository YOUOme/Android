package com.kftc.openbankingsample2.common.http;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.kftc.openbankingsample2.R;

public class KmProgressBar {

    private AlertDialog dialog;

    public KmProgressBar(Context context) {

        // colorTint가 API 21레벨 이상이라서 ContextCompat을 적용하기 위해 코드로 구현. 이것만 아니면 xml에서 설정이 가능.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = null;
        if (inflater != null) {
            dialogView = inflater.inflate(R.layout.km_progress_bar, null);
            ProgressBar progressBar = dialogView.findViewById(R.id.kmProgressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.md_white_1000), PorterDuff.Mode.SRC_IN);
            progressBar.setBackgroundResource(R.drawable.km_progress_bar_background_grey_circle);
        }
        dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void show() {
        dialog.show();
    }
    public void hide() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
