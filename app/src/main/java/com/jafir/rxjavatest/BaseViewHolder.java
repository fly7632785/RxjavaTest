package com.jafir.rxjavatest;


import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener;

public class BaseViewHolder extends ViewHolder {
    private final SparseArray<View> views = new SparseArray();
    public View convertView;
    Object associatedObject;

    protected BaseViewHolder(View view) {
        super(view);
        this.convertView = view;
    }

    public View getConvertView() {
        return this.convertView;
    }

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = (TextView)this.getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = this.getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = this.getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = (TextView)this.getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setAlpha(int viewId, float value) {
        if(VERSION.SDK_INT >= 11) {
            this.getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0L);
            alpha.setFillAfter(true);
            this.getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = this.getView(viewId);
        view.setVisibility(visible?0:8);
        return this;
    }

    public BaseViewHolder linkify(int viewId) {
        TextView view = (TextView)this.getView(viewId);
        Linkify.addLinks(view, 15);
        return this;
    }

    public BaseViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = (TextView)this.getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | 128);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        int[] var3 = viewIds;
        int var4 = viewIds.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int viewId = var3[var5];
            TextView view = (TextView)this.getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | 128);
        }

        return this;
    }

    public BaseViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = (ProgressBar)this.getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BaseViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = (ProgressBar)this.getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BaseViewHolder setMax(int viewId, int max) {
        ProgressBar view = (ProgressBar)this.getView(viewId);
        view.setMax(max);
        return this;
    }

    public BaseViewHolder setRating(int viewId, float rating) {
        RatingBar view = (RatingBar)this.getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BaseViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = (RatingBar)this.getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, OnClickListener listener) {
        View view = this.getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, OnItemChildClickListener listener) {
        View view = this.getView(viewId);
        listener.mViewHolder = this;
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, OnItemChildLongClickListener listener) {
        View view = this.getView(viewId);
        listener.mViewHolder = this;
        view.setOnLongClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, OnTouchListener listener) {
        View view = this.getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, OnLongClickListener listener) {
        View view = this.getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemClickListener(int viewId, OnItemClickListener listener) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(int viewId, OnItemLongClickListener listener) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemSelectedClickListener(int viewId, OnItemSelectedListener listener) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(int viewId, OnCheckedChangeListener listener) {
        CompoundButton view = (CompoundButton)this.getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = this.getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = this.getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseViewHolder setChecked(int viewId, boolean checked) {
        View view = this.getView(viewId);
        if(view instanceof CompoundButton) {
            ((CompoundButton)view).setChecked(checked);
        } else if(view instanceof CheckedTextView) {
            ((CheckedTextView)view).setChecked(checked);
        }

        return this;
    }

    public BaseViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View)this.views.get(viewId);
        if(view == null) {
            view = this.convertView.findViewById(viewId);
            this.views.put(viewId, view);
        }

        return (T) view;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }
}
