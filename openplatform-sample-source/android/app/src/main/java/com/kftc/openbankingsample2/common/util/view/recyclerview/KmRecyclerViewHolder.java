package com.kftc.openbankingsample2.common.util.view.recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * ListView 대신에 사용이 권장되는 RecyclerView에 대한 ArrayAdapter의 viewholder 이다.
 * @param <T> 어댑터에 저장되는 데이터 오트젝트 타입
 */
@SuppressWarnings({"UnusedReturnValue", "WeakerAccess", "SameParameterValue", "unused", "FieldCanBeLocal"})
public abstract class KmRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public KmRecyclerViewHolder(View v) {
        super(v);
    }

    // KmRecyclerViewArrayAdapter의 onBindViewHolder를 holder에서 일관성있게 처리하기 위해 ViewHolder로 위임하여 구현
    public abstract void onBindViewHolder(final View view, final int position, T item, final boolean isSelected, final boolean isExpanded, final boolean isEtc);
}
