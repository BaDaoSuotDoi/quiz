package com.badao.quiz.base.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.base.view_holder.BaseViewHolder;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<T> mDisplayItems;
    public OnItemClickListener mListener;

    protected abstract int getLayoutId();

    protected abstract VH createViewHolder(View view);

    protected abstract void bindView(VH holder, T item, int position) throws JSONException;


    public interface OnItemClickListener {
        void onItemClick(BaseAdapter adapter, View view, int position);
    }

    public BaseAdapter(Context context, List<T> items) {
        mContext = context;
        mDisplayItems = items;
        if (mDisplayItems == null) {
            mDisplayItems = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(context);

    }

    public final void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public final Context getContext() {
        return mContext;
    }

    public final LayoutInflater getLayoutInflater() {
        return mInflater;
    }

    public final List<T> getDisplayItems() {
        return mDisplayItems;
    }

    public OnItemClickListener getListener() {
        return mListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(getLayoutId(), parent, false);
        return createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItemAtPosition(position);
        try {
            bindView((VH) holder, item, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initOnItemClicked((VH) holder);
    }


    @Override
    public int getItemCount() {
        return mDisplayItems == null ? 0 : mDisplayItems.size();
    }

    public final T getItemAtPosition(int position) {
        return position >= 0 && position < mDisplayItems.size() ? mDisplayItems.get(position) : null;
    }

    protected void onItemClick(VH holder, int position) {
        if (mListener != null) mListener.onItemClick(this, holder.itemView, position);
    }

    protected void onItemClick(View view, int position) {
        if (mListener != null) mListener.onItemClick(this, view, position);
    }

    protected void initOnItemClicked(VH holder) {
        holder.itemView.setOnClickListener(v -> {
            onItemClick((VH) holder, holder.getAdapterPosition());
        });
    }

    public void setData(List<T> items){
        if (items == null) items = new ArrayList<>();
        this.mDisplayItems = items;
        notifyDataSetChanged();
    }

    public final void addData(T item) {
        if (mDisplayItems == null) {
            mDisplayItems = new ArrayList<>();
        }
        mDisplayItems.add(item);
        notifyDataSetChanged();
    }
    public final void addData(List<T> items) {
        if (mDisplayItems == null) {
            mDisplayItems = new ArrayList<>();
        }
        mDisplayItems.addAll(items);
        notifyDataSetChanged();
    }

}
