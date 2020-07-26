package com.kftc.openbankingsample2.common.util.view.recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * ListView 대신에 사용이 권장되는 RecyclerView에 대한 ArrayAdapter
 * @param <T> 어댑터에 저장되는 데이터 오트젝트 타입
 */
@SuppressWarnings({"UnusedReturnValue", "WeakerAccess", "SameParameterValue", "unused", "FieldCanBeLocal"})
public abstract class KmRecyclerViewArrayAdapter<T> extends RecyclerView.Adapter<KmRecyclerViewHolder> {

    public ItemClick itemClick;

    public interface ItemClick {
        void onClick(int type, View view, final int position, final boolean isSelected, final boolean isExpanded, final boolean isEtc);
    }

    // 표현하려는 기본 item
    private ArrayList<T> itemList;

    // 선택된 항목 리스트
    private ArrayList<Boolean> isSelectedList;

    // 펼쳐진 항목 리스트(옵션) -> 필요하면 사용
    private ArrayList<Boolean> isExpandedList;

    // 기타 플래그 리스트(옵션) -> 필요하면 사용
    private ArrayList<Boolean> isEtcList;

    // viewHolder가 바닥에 있는 뷰를 읽어올때의 리스너
    private OnBottomReachedListener bottomReachedListener;

    // 리스너를 위한 인터페이스
    public interface OnBottomReachedListener {
        void onBottomReached(int position);
    }

    /**
     * viewHolder가 바닥에 있는 뷰를 읽어올때의 리스너. onScrollStateChanged 대신 사용해보자.
     * 단, recyclerView가 wrap_content로 모두 스크롤된 상태로 표시할 경우에는 이 listener를 사용하면 안된다.
     * 출처 : https://medium.com/@ayhamorfali/android-detect-when-the-recyclerview-reaches-the-bottom-43f810430e1e
     * @param bottomReachedListener 리스너
     */
    public void setBottomReachedListener(OnBottomReachedListener bottomReachedListener) {
        this.bottomReachedListener = bottomReachedListener;
    }

    public KmRecyclerViewArrayAdapter(ArrayList<T> itemList) {
        this.itemList = itemList;
        isSelectedList = new ArrayList<>();
        isExpandedList = new ArrayList<>();
        isEtcList = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i++) {
            isSelectedList.add(false);
            isExpandedList.add(false);
            isEtcList.add(false);
        }
    }

    /**
     * ViewHolder 생성하는 부분으로, 자식 클래스에서 다음과 같이 구현<br>
     *
     *     View v = LayoutInflater.from(parent.getContext())<br>
     *          .inflate(R.layout.my_recyclerview_viewholder_layout, parent, false);<br>
     *     return new MyViewHolder(v);<br>
     *
     * my_recyclerview_viewholder_layout : 한개의 행을 표시하는 레이아웃
     * viewType은 getItemViewType를 오버라이딩해서 설정한 값이다.
     */
    @NonNull
    @Override
    abstract public KmRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @SuppressWarnings("unchecked") @Override
    public void onBindViewHolder(@NonNull KmRecyclerViewHolder holder, int position) {
        if(itemList == null || position < 0) {
            return;
        }

        // 2019.6 수정. header/footer 를 사용할 경우에는 postion 이 itemList.size() 보다 크거나 같을수 있다.(getItemCount() 보다는 작다)
        if (position < itemList.size()) {
            holder.onBindViewHolder(holder.itemView, position, itemList.get(position), isSelectedList.get(position), isExpandedList.get(position), isEtcList.get(position));
        } else {
            holder.onBindViewHolder(holder.itemView, position, null, false, false, false);
        }

        // 바닥까지 보여졌을때 리스너를 호출해보자(header/footer를 고려하여 itelList.size() 보다 getItemCount()를 사용.)
        if (position == getItemCount() - 1) {
            if (bottomReachedListener != null) {
                bottomReachedListener.onBottomReached(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(itemList == null) {
            return 0;
        } else {
            return itemList.size();
        }
    }

    /**
     * 특정위치에 item을 추가한다. ArrayList 특성상, 추가되는 열에 데이터가 있으면 기존 데이터는 오른쪽으로 shift 된다.
     */
    public void addItem(int position, T item) {
        if(position > itemList.size()) {
            position = itemList.size();
        }
        itemList.add(position, item);
        isSelectedList.add(position, false);
        isExpandedList.add(position, false);
        isEtcList.add(position, false);
        notifyItemInserted(position);
    }

    /**
     * 마지막위치에 item을 추가한다. 부가정보(isSelectedList..)는 없을 경우에만 기본값으로 추가한다.
     */
    public void addItem(T item) {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        itemList.add(item);
        int size = itemList.size();

        if (isSelectedList.size() < size)
            isSelectedList.add(false);
        if (isExpandedList.size() < size)
            isExpandedList.add(false);
        if (isEtcList.size() < size)
            isEtcList.add(false);
        notifyDataSetChanged();
    }

    /**
     * 특정 위치에 아이템을 삭제한다.
     */
    public void removeAtPostion(int position) {
        if (position < 0 || position >= itemList.size()) {
            return;
        }

        itemList.remove(position);
        isSelectedList.remove(position);
        isExpandedList.remove(position);
        isEtcList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 모든 데이터를 null로 초기화하고 arrayList의 size를 0으로 한다.
     * (주의) 부가정보(isSelectedList...)도 모두 삭제되므로 부가정보를 활용한 view도 초기화 된다는 것에 유의.
     */
    public void clearAll() {
        itemList.clear();
        isSelectedList.clear();
        isExpandedList.clear();
        isEtcList.clear();
        notifyDataSetChanged();
    }

    /**
     * item을 특정 위치로 이동한다.
     */
    public void move(int fromPosition, int toPosition) {
        if (itemList == null || fromPosition < 0 || fromPosition >= itemList.size() ||
                toPosition < 0 || toPosition >= itemList.size()) {
            return;
        }

        final T item = itemList.get(fromPosition);
        itemList.remove(fromPosition);
        itemList.add(toPosition, item);

        final boolean isSelected = isSelectedList.get(fromPosition);
        isSelectedList.remove(fromPosition);
        isSelectedList.add(toPosition, isSelected);

        final boolean isExpanded = isExpandedList.get(fromPosition);
        isExpandedList.remove(fromPosition);
        isExpandedList.add(toPosition, isExpanded);

        final boolean isEtc = isEtcList.get(fromPosition);
        isEtcList.remove(fromPosition);
        isEtcList.add(toPosition, isExpanded);

        notifyItemMoved(fromPosition, toPosition);
    }

    ////////// 기본 item get, set //////////
    public @Nullable
    T getItem(int position) {
        if (itemList == null || position < 0 || position >= itemList.size()) {
            return null;
        }
        return itemList.get(position);
    }
    // item을 설정할때는 반드시 set 함수를 사용해야 나머지 리스트도 같이 설정된다.
    public void setItem(int position, T item) {
        if (itemList == null || position < 0 || position >= itemList.size()) {
            return;
        }
        itemList.set(position, item);
        isSelectedList.set(position, false);
        isExpandedList.set(position, false);
        isEtcList.set(position, false);
        notifyDataSetChanged();
    }
    public @NonNull
    ArrayList<T> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList;
    }
    // item을 설정할때는 반드시 set 함수를 사용해야 나머지 리스트도 같이 설정된다.
    public void setItemList(ArrayList<T> itemList) {
        this.itemList = itemList;

        isSelectedList.clear();
        isExpandedList.clear();
        isEtcList.clear();

        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                isSelectedList.add(false);
                isExpandedList.add(false);
                isEtcList.add(false);
            }
        }

        notifyDataSetChanged();
    }

    /**
     * 다른 상태값들은 그대로 유지한채 itemList만 교체하고 싶을때 사용한다.
     * 반드시 list 개수가 같아야 한다. 다르면 상태값을 초기화 시킨다.
     * @param itemList itemList
     */
    public void setItemListOnly(ArrayList<T> itemList) {
        if (this.itemList == null || itemList == null) {
            return;
        }

        if (this.itemList.size() != itemList.size()) {
            Timber.e( "[KM PUSH] list 크기가 서로 다릅니다. 기존:%s, 신규:%s", this.itemList.size(), itemList.size());
            setItemList(itemList);
            return;
        }

        this.itemList = itemList;
        notifyDataSetChanged();
    }

    ////////// isSelected get, set //////////
    public boolean isSelected(int position) {
        if (isSelectedList == null || position < 0 || position >= isSelectedList.size()) {
            return false;
        }

        return isSelectedList.get(position);
    }
    public boolean areAllSelected() {
        if (isSelectedList == null || itemList == null) {
            return false;
        }

        int cnt = 0;
        for (boolean isSelected : isSelectedList) {
            if (!isSelected) {
                return false;
            }
            cnt++;
        }

        return itemList.size() == cnt;
    }
    public void setSelected(int position, boolean isSelected) {
        if (isSelectedList == null || position < 0 || position >= isSelectedList.size()) {
            return;
        }

        isSelectedList.set(position, isSelected);
        notifyDataSetChanged();
    }
    public void setSelectedToggle(int position) {
        if (isSelectedList == null || position < 0 || position >= isSelectedList.size()) {
            return;
        }

        isSelectedList.set(position, !isSelectedList.get(position));
        notifyDataSetChanged();
    }
    public @NonNull
    ArrayList<Boolean> getSelectedList() {
        if (isSelectedList == null) {
            isSelectedList = new ArrayList<>();
        }

        ArrayList<Boolean> selectedList = new ArrayList<>();
        for (int i = 0; i < isSelectedList.size(); i++) {
            if (isSelectedList.get(i)) {
                selectedList.add(isSelectedList.get(i));
            }
        }

        return selectedList;
    }
    public void setAllSelected(boolean isSelected) {
        if (isSelectedList == null) {
            return;
        }

        for (int i=0; i < isSelectedList.size(); i++) {
            isSelectedList.set(i, isSelected);
        }
        notifyDataSetChanged();
    }

    ////////// isExpanded get, set //////////
    public boolean isExpanded(int position) {
        if (isExpandedList == null || position >= isExpandedList.size()) {
            return false;
        }

        return isExpandedList.get(position);
    }
    public void setExpanded(int position, boolean isExpanded) {
        if (isExpandedList == null || position < 0 || position >= isExpandedList.size()) {
            return;
        }

        isExpandedList.set(position, isExpanded);
        notifyDataSetChanged();
    }
    public void setExpandedToggle(int position) {
        if (isExpandedList == null || position < 0 || position >= isExpandedList.size()) {
            return;
        }

        isExpandedList.set(position, !isExpandedList.get(position));
        notifyDataSetChanged();
    }
    public @NonNull
    ArrayList<Boolean> getExpandedList() {
        if (isExpandedList == null) {
            isExpandedList = new ArrayList<>();
        }

        ArrayList<Boolean> expandedList = new ArrayList<>();
        for (int i = 0; i < isExpandedList.size(); i++) {
            if (isExpandedList.get(i)) {
                expandedList.add(isExpandedList.get(i));
            }
        }

        return expandedList;
    }
    public void setAllExpanded(boolean isSelected) {
        if (isExpandedList == null) {
            return;
        }

        for (int i=0; i < isExpandedList.size(); i++) {
            isExpandedList.set(i, isSelected);
        }
        notifyDataSetChanged();
    }

    ////////// isEtc get, set //////////
    public boolean isEtc(int position) {
        if (isEtcList == null || position < 0 || position >= isEtcList.size()) {
            return false;
        }

        return isEtcList.get(position);
    }
    public void setEtc(int position, boolean isEtc) {
        if (isEtcList == null || position < 0 || position >= isEtcList.size()) {
            return;
        }

        isEtcList.set(position, isEtc);
        notifyDataSetChanged();
    }
    public void setEtcToggle(int position) {
        if (isEtcList == null || position < 0 || position >= isEtcList.size()) {
            return;
        }

        isEtcList.set(position, !isEtcList.get(position));
        notifyDataSetChanged();
    }
    public @Nullable
    ArrayList<Boolean> getEtcList() {
        if (isEtcList == null) {
            return null;
        }

        ArrayList<Boolean> etcList = new ArrayList<>();
        for (int i = 0; i < isEtcList.size(); i++) {
            if (isEtcList.get(i)) {
                etcList.add(isEtcList.get(i));
            }
        }

        return etcList;
    }
    public void setAllEtc(boolean isEtc) {
        if (isEtcList == null) {
            return;
        }

        for (int i=0; i < isEtcList.size(); i++) {
            isEtcList.set(i, isEtc);
        }
        notifyDataSetChanged();
    }

    ////////// isSelected, isExpanded, isEtc 값이 설정된 item을 가져오는 get 함수 //////////
    public @NonNull
    ArrayList<T> getSelectedItemList() {
        if (isSelectedList == null) {
            isSelectedList = new ArrayList<>();
        }

        ArrayList<T> selectedItemList = new ArrayList<>();
        for (int i = 0; i < isSelectedList.size(); i++) {
            if (isSelectedList.get(i)) {
                selectedItemList.add(itemList.get(i));
            }
        }
        return selectedItemList;
    }
    public @NonNull
    ArrayList<T> getExpandedItemList() {
        if (isExpandedList == null) {
            isExpandedList = new ArrayList<>();
        }

        ArrayList<T> expandedItemList = new ArrayList<>();
        for (int i = 0; i < isExpandedList.size(); i++) {
            if (isExpandedList.get(i)) {
                expandedItemList.add(itemList.get(i));
            }
        }
        return expandedItemList;
    }
    public @NonNull
    ArrayList<T> getEtcItemList() {
        if (isEtcList == null) {
            isEtcList = new ArrayList<>();
        }

        ArrayList<T> etcItemList = new ArrayList<>();
        for (int i = 0; i < isEtcList.size(); i++) {
            if (isEtcList.get(i)) {
                etcItemList.add(itemList.get(i));
            }
        }
        return etcItemList;
    }

    ////////// item 클릭 리스너 //////////
    public void setItemClick(ItemClick itemClick) {this.itemClick = itemClick;}
}
