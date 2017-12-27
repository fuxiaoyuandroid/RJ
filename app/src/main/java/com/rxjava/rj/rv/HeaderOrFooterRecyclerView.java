package com.rxjava.rj.rv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class HeaderOrFooterRecyclerView extends RecyclerView {
    private WrapRecyclerViewAdapter mAdapter;

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            mAdapter.notifyItemRangeInserted(positionStart,itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            mAdapter.notifyItemRangeRemoved(positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            mAdapter.notifyItemMoved(fromPosition,toPosition);
        }
    };
    public HeaderOrFooterRecyclerView(Context context) {
        super(context,null);
    }

    public HeaderOrFooterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public HeaderOrFooterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof WrapRecyclerViewAdapter){
            mAdapter = (WrapRecyclerViewAdapter) adapter;
        }else {
            mAdapter = new WrapRecyclerViewAdapter(adapter);
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
        super.setAdapter(mAdapter);
    }

    //方法添加头底

    public void addHeaderView(View view){
        if (mAdapter != null){
            mAdapter.addHeaderView(view);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void addFooterView(View view){
        if (mAdapter != null){
            mAdapter.addFooterView(view);
        }
        mAdapter.notifyDataSetChanged();
    }
}
