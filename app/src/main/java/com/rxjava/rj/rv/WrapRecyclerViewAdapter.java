package com.rxjava.rj.rv;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class WrapRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "WrapRecyclerViewAdapter";
    //不包含头部或底部的适配器
    private RecyclerView.Adapter mAdapter;

    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;

    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;

    public WrapRecyclerViewAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //头
        if (mHeaderViews.indexOfKey(viewType)>=0){
            return createHeaderFooterViewHolder(mHeaderViews.get(viewType));

        }else if (mFooterViews.indexOfKey(viewType)>=0){
            return createHeaderFooterViewHolder(mFooterViews.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    /**
     * 创建头部或底部的ViewHolder
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头
        int numHeaders = mHeaderViews.size();
        if (position < numHeaders){
            return;
        }
        // viewType 中间
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }
        if (position >= (numHeaders + mAdapter.getItemCount())){
            return;
        }

    }

    @Override
    public int getItemCount() {

        return  mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = mHeaderViews.size();
        if (position < numHeaders) {
            return mHeaderViews.keyAt(position);
        }
        //position   viewType 中间
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return mFooterViews.keyAt(adjPosition - adapterCount);
    }

    public void addHeaderView(View view){
        if (mHeaderViews.indexOfValue(view) < 0){

            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++,view);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view){
        if (mFooterViews.indexOfValue(view) < 0){
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++,view);
        }
        notifyDataSetChanged();
    }
}
