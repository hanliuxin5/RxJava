package com.vvsai.rxjava.retrofit;

/**
 * Created by lychee on 2016/4/28.
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}