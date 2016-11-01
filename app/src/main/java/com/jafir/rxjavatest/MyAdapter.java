package com.jafir.rxjavatest;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import com.chad.library.R.layout;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.animation.SlideInRightAnimation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class MyAdapter<T> extends Adapter<ViewHolder> {
    private boolean mNextLoadEnable;
    private boolean mLoadingMoreEnable;
    private boolean mFirstOnlyEnable;
    private boolean mOpenAnimationEnable;
    private boolean mEmptyEnable;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;
    private Interpolator mInterpolator;
    private int mDuration;
    private int mLastPosition;
    private MyAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private MyAdapter.OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;
    private MyAdapter.RequestLoadMoreListener mRequestLoadMoreListener;
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation;
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private LinearLayout mCopyHeaderLayout;
    private LinearLayout mCopyFooterLayout;
    private int pageSize;
    private View mContentView;
    private View mEmptyView;
    protected static final String TAG = MyAdapter.class.getSimpleName();
    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
    public static final int HEADER_VIEW = 273;
    public static final int LOADING_VIEW = 546;
    public static final int FOOTER_VIEW = 819;
    public static final int EMPTY_VIEW = 1365;
    private View mLoadingView;
    public static final int ALPHAIN = 1;
    public static final int SCALEIN = 2;
    public static final int SLIDEIN_BOTTOM = 3;
    public static final int SLIDEIN_LEFT = 4;
    public static final int SLIDEIN_RIGHT = 5;
    private MyAdapter.OnRecyclerViewItemChildClickListener mChildClickListener;
    private MyAdapter.OnRecyclerViewItemChildLongClickListener mChildLongClickListener;

    /** @deprecated */
    @Deprecated
    public void setOnLoadMoreListener(int pageSize, MyAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        this.setOnLoadMoreListener(requestLoadMoreListener);
    }

    public void setOnLoadMoreListener(MyAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void openLoadMore(int pageSize, boolean enable) {
        this.pageSize = pageSize;
        this.mNextLoadEnable = enable;
    }

    public void openLoadMore(boolean enable) {
        this.mNextLoadEnable = enable;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setOnRecyclerViewItemClickListener(MyAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(MyAdapter.OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    public void setOnRecyclerViewItemChildClickListener(MyAdapter.OnRecyclerViewItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public void setOnRecyclerViewItemChildLongClickListener(MyAdapter.OnRecyclerViewItemChildLongClickListener childLongClickListener) {
        this.mChildLongClickListener = childLongClickListener;
    }

    public MyAdapter(int layoutResId, List<T> data) {
        this.mNextLoadEnable = false;
        this.mLoadingMoreEnable = false;
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mSelectAnimation = new AlphaInAnimation();
        this.mCopyHeaderLayout = null;
        this.mCopyFooterLayout = null;
        this.pageSize = -1;
        this.mData = (List)(data == null?new ArrayList():data);
        if(layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }

    }

    public MyAdapter(List<T> data) {
        this(0, data);
    }

    public MyAdapter(View contentView, List<T> data) {
        this(0, data);
        this.mContentView = contentView;
    }

    public void remove(int position) {
        this.mData.remove(position);
        this.notifyItemRemoved(position + this.getHeaderLayoutCount());
    }

    public void add(int position, T item) {
        this.mData.add(position, item);
        this.notifyItemInserted(position);
    }
    public void addReverse(T item) {
        this.mData.add(item);
        this.notifyDataSetChanged();
    }

    public void setNewData(List<T> data) {
        this.mData = data;
        if(this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
        }

        this.mLastPosition = -1;
        this.notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }
    public void addDataReverse(List<T> data) {
        this.mData.addAll(data);
        Collections.reverse(mData);
        this.notifyDataSetChanged();
    }



    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    public List<T> getData() {
        return this.mData;
    }

    public T getItem(int position) {
        return this.mData.get(position);
    }

    /** @deprecated */
    @Deprecated
    public int getHeaderViewsCount() {
        return this.mHeaderLayout == null?0:1;
    }

    /** @deprecated */
    @Deprecated
    public int getFooterViewsCount() {
        return this.mFooterLayout == null?0:1;
    }

    public int getHeaderLayoutCount() {
        return this.mHeaderLayout == null?0:1;
    }

    public int getFooterLayoutCount() {
        return this.mFooterLayout == null?0:1;
    }

    public int getmEmptyViewCount() {
        return this.mEmptyView == null?0:1;
    }

    @Override
    public int getItemCount() {
        int i = this.isLoadMore()?1:0;
        int count = this.mData.size() + i + this.getHeaderLayoutCount() + this.getFooterLayoutCount();
        if(this.mData.size() == 0 && this.mEmptyView != null) {
            if(count == 0 && (!this.mHeadAndEmptyEnable || !this.mFootAndEmptyEnable)) {
                count += this.getmEmptyViewCount();
            } else if(this.mHeadAndEmptyEnable || this.mFootAndEmptyEnable) {
                count += this.getmEmptyViewCount();
            }

            if(this.mHeadAndEmptyEnable && this.getHeaderLayoutCount() == 1 && count == 1 || count == 0) {
                this.mEmptyEnable = true;
                count += this.getmEmptyViewCount();
            }
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if(this.mHeaderLayout != null && position == 0) {
            return 273;
        } else {
            if(this.mData.size() == 0 && this.mEmptyEnable && this.mEmptyView != null && position <= 2) {
                if((this.mHeadAndEmptyEnable || this.mFootAndEmptyEnable) && position == 1) {
                    if(this.mHeaderLayout == null && this.mEmptyView != null && this.mFooterLayout != null) {
                        return 819;
                    }

                    if(this.mHeaderLayout != null && this.mEmptyView != null) {
                        return 1365;
                    }
                } else if(position == 0) {
                    if(this.mHeaderLayout == null) {
                        return 1365;
                    }

                    if(this.mFooterLayout != null) {
                        return 1365;
                    }
                } else {
                    if(position == 2 && (this.mFootAndEmptyEnable || this.mHeadAndEmptyEnable) && this.mHeaderLayout != null && this.mEmptyView != null) {
                        return 819;
                    }

                    if((!this.mFootAndEmptyEnable || !this.mHeadAndEmptyEnable) && position == 1 && this.mFooterLayout != null) {
                        return 819;
                    }
                }
            } else {
                if(this.mData.size() == 0 && this.mEmptyView != null && this.getItemCount() == (this.mHeadAndEmptyEnable?2:1) && this.mEmptyEnable) {
                    return 1365;
                }

                if(position == this.mData.size() + this.getHeaderLayoutCount()) {
                    if(this.mNextLoadEnable) {
                        return 546;
                    }

                    return 819;
                }

                if(position > this.mData.size() + this.getHeaderLayoutCount()) {
                    return 819;
                }
            }

            return this.getDefItemViewType(position - this.getHeaderLayoutCount());
        }
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        switch(viewType) {
            case 273:
                baseViewHolder = new BaseViewHolder(this.mHeaderLayout);
                break;
            case 546:
                baseViewHolder = this.getLoadingView(parent);
                break;
            case 819:
                baseViewHolder = new BaseViewHolder(this.mFooterLayout);
                break;
            case 1365:
                baseViewHolder = new BaseViewHolder(this.mEmptyView);
                break;
            default:
                baseViewHolder = this.onCreateDefViewHolder(parent, viewType);
                this.initItemClickListener(baseViewHolder);
        }

        return baseViewHolder;
    }

    private BaseViewHolder getLoadingView(ViewGroup parent) {
        return this.mLoadingView == null?this.createBaseViewHolder(parent, layout.def_loading):new BaseViewHolder(this.mLoadingView);
    }

    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if(type != 1365 && type != 273 && type != 819 && type != 546) {
            this.addAnimation(holder);
        } else {
            this.setFullSpan(holder);
        }

    }

    protected void setFullSpan(ViewHolder holder) {
        if(holder.itemView.getLayoutParams() instanceof LayoutParams) {
            LayoutParams params = (LayoutParams)holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }

    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager)manager;
            gridManager.setSpanSizeLookup(new SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = MyAdapter.this.getItemViewType(position);
                    return type != 1365 && type != 273 && type != 819 && type != 546?1:gridManager.getSpanCount();
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int positions) {
        int viewType = holder.getItemViewType();
        switch(viewType) {
            case 0:
                this.convert((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
            case 273:
            case 819:
            case 1365:
                break;
            case 546:
                this.addLoadMore(holder);
                break;
            default:
                this.convert((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
                this.onBindDefViewHolder((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
        }

    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return this.createBaseViewHolder(parent, this.mLayoutResId);
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return this.mContentView == null?new BaseViewHolder(this.getItemView(layoutResId, parent)):new BaseViewHolder(this.mContentView);
    }

    public LinearLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    public LinearLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public void addHeaderView(View header) {
        this.addHeaderView(header, -1);
    }

    public void addHeaderView(View header, int index) {
        if(this.mHeaderLayout == null) {
            if(this.mCopyHeaderLayout == null) {
                this.mHeaderLayout = new LinearLayout(header.getContext());
                this.mHeaderLayout.setOrientation(1);
                this.mHeaderLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyHeaderLayout = this.mHeaderLayout;
            } else {
                this.mHeaderLayout = this.mCopyHeaderLayout;
            }
        }

        index = index >= this.mHeaderLayout.getChildCount()?-1:index;
        this.mHeaderLayout.addView(header, index);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        this.addFooterView(footer, -1);
    }

    public void addFooterView(View footer, int index) {
        this.mNextLoadEnable = false;
        if(this.mFooterLayout == null) {
            if(this.mCopyFooterLayout == null) {
                this.mFooterLayout = new LinearLayout(footer.getContext());
                this.mFooterLayout.setOrientation(1);
                this.mFooterLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyFooterLayout = this.mFooterLayout;
            } else {
                this.mFooterLayout = this.mCopyFooterLayout;
            }
        }

        index = index >= this.mFooterLayout.getChildCount()?-1:index;
        this.mFooterLayout.addView(footer, index);
        this.notifyDataSetChanged();
    }

    public void removeHeaderView(View header) {
        if(this.mHeaderLayout != null) {
            this.mHeaderLayout.removeView(header);
            if(this.mHeaderLayout.getChildCount() == 0) {
                this.mHeaderLayout = null;
            }

            this.notifyDataSetChanged();
        }
    }

    public void removeFooterView(View footer) {
        if(this.mFooterLayout != null) {
            this.mFooterLayout.removeView(footer);
            if(this.mFooterLayout.getChildCount() == 0) {
                this.mFooterLayout = null;
            }

            this.notifyDataSetChanged();
        }
    }

    public void removeAllHeaderView() {
        if(this.mHeaderLayout != null) {
            this.mHeaderLayout.removeAllViews();
            this.mHeaderLayout = null;
        }
    }

    public void removeAllFooterView() {
        if(this.mFooterLayout != null) {
            this.mFooterLayout.removeAllViews();
            this.mFooterLayout = null;
        }
    }

    public void setEmptyView(View emptyView) {
        this.setEmptyView(false, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        this.setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        this.mHeadAndEmptyEnable = isHeadAndEmpty;
        this.mFootAndEmptyEnable = isFootAndEmpty;
        this.mEmptyView = emptyView;
        this.mEmptyEnable = true;
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    /** @deprecated */
    @Deprecated
    public void isNextLoad(boolean isNextLoad) {
        this.mNextLoadEnable = isNextLoad;
        this.mLoadingMoreEnable = false;
        this.notifyDataSetChanged();
    }

    public void notifyDataChangedAfterLoadMore(boolean isNextLoad) {
        this.mNextLoadEnable = isNextLoad;
        this.mLoadingMoreEnable = false;
        this.notifyDataSetChanged();
    }

    public void notifyDataChangedAfterLoadMore(List<T> data, boolean isNextLoad) {
        this.mData.addAll(data);
        this.notifyDataChangedAfterLoadMore(isNextLoad);
    }

    private void addLoadMore(ViewHolder holder) {
        if(this.isLoadMore() && !this.mLoadingMoreEnable) {
            this.mLoadingMoreEnable = true;
            this.mRequestLoadMoreListener.onLoadMoreRequested();
        }

    }

    private void initItemClickListener(final BaseViewHolder baseViewHolder) {
        if(this.onRecyclerViewItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    MyAdapter.this.onRecyclerViewItemClickListener.onItemClick(v, baseViewHolder.getLayoutPosition() - MyAdapter.this.getHeaderLayoutCount());
                }
            });
        }

        if(this.onRecyclerViewItemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    return MyAdapter.this.onRecyclerViewItemLongClickListener.onItemLongClick(v, baseViewHolder.getLayoutPosition() - MyAdapter.this.getHeaderLayoutCount());
                }
            });
        }

    }

    private void addAnimation(ViewHolder holder) {
        if(this.mOpenAnimationEnable && (!this.mFirstOnlyEnable || holder.getLayoutPosition() > this.mLastPosition)) {
            BaseAnimation animation = null;
            if(this.mCustomAnimation != null) {
                animation = this.mCustomAnimation;
            } else {
                animation = this.mSelectAnimation;
            }

            Animator[] var3 = animation.getAnimators(holder.itemView);
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Animator anim = var3[var5];
                this.startAnim(anim, holder.getLayoutPosition());
            }

            this.mLastPosition = holder.getLayoutPosition();
        }

    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration((long)this.mDuration).start();
        anim.setInterpolator(this.mInterpolator);
    }

    private boolean isLoadMore() {
        return this.mNextLoadEnable && this.pageSize != -1 && this.mRequestLoadMoreListener != null && this.mData.size() >= this.pageSize;
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return this.mLayoutInflater.inflate(layoutResId, parent, false);
    }

    /** @deprecated */
    @Deprecated
    protected void onBindDefViewHolder(BaseViewHolder holder, T item) {
    }

    public void openLoadAnimation(int animationType) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = null;
        switch(animationType) {
            case 1:
                this.mSelectAnimation = new AlphaInAnimation();
                break;
            case 2:
                this.mSelectAnimation = new ScaleInAnimation();
                break;
            case 3:
                this.mSelectAnimation = new SlideInBottomAnimation();
                break;
            case 4:
                this.mSelectAnimation = new SlideInLeftAnimation();
                break;
            case 5:
                this.mSelectAnimation = new SlideInRightAnimation();
        }

    }

    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    protected abstract void convert(BaseViewHolder var1, T var2);

    public long getItemId(int position) {
        return (long)position;
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public class OnItemChildLongClickListener implements OnLongClickListener {
        public ViewHolder mViewHolder;

        public OnItemChildLongClickListener() {
        }

        public boolean onLongClick(View v) {
            return MyAdapter.this.mChildLongClickListener != null?MyAdapter.this.mChildLongClickListener.onItemChildLongClick(MyAdapter.this, v, this.mViewHolder.getLayoutPosition() - MyAdapter.this.getHeaderViewsCount()):false;
        }
    }

    public interface OnRecyclerViewItemChildLongClickListener {
        boolean onItemChildLongClick(MyAdapter var1, View var2, int var3);
    }

    public class OnItemChildClickListener implements OnClickListener {
        public ViewHolder mViewHolder;

        public OnItemChildClickListener() {
        }

        public void onClick(View v) {
            if(MyAdapter.this.mChildClickListener != null) {
                MyAdapter.this.mChildClickListener.onItemChildClick(MyAdapter.this, v, this.mViewHolder.getLayoutPosition() - MyAdapter.this.getHeaderLayoutCount());
            }

        }
    }

    public interface OnRecyclerViewItemChildClickListener {
        void onItemChildClick(MyAdapter var1, View var2, int var3);
    }

    public interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View var1, int var2);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View var1, int var2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }
}
