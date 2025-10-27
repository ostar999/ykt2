package com.aliyun.svideo.common.base;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.R;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class AlivcListSelectorDialogFragment extends BaseDialogFragment {
    public static final String TAG = "AlivcListSelectorDialogFragment";
    public FragmentManager mFragmentManager;
    public int mHeight;
    private int mItemSelectedColor;
    private int mItemUnSelectedColor;
    public DialogInterface.OnKeyListener mKeyListener;
    public List<String> mLists;
    private OnListItemSelectedListener mOnListItemSelectedListener;
    public String mPosition;
    private SelectorQuickAdapter mSelectorQuickAdapter;
    public TextView mTvCancel;
    public int mWidth;
    private DialogInterface.OnDismissListener onDismissListener;
    public float mDimAmount = 0.2f;
    public int mGravity = 80;
    public String mTag = TAG;
    public boolean mIsCancelableOutside = true;
    public int mDialogAnimationRes = 0;

    public static class Builder {
        private AlivcListSelectorDialogFragment params;

        public Builder(FragmentManager fragmentManager) {
            AlivcListSelectorDialogFragment alivcListSelectorDialogFragment = new AlivcListSelectorDialogFragment();
            this.params = alivcListSelectorDialogFragment;
            alivcListSelectorDialogFragment.mFragmentManager = fragmentManager;
        }

        public AlivcListSelectorDialogFragment create() {
            return this.params;
        }

        public Builder setCancelableOutside(boolean z2) {
            this.params.mIsCancelableOutside = z2;
            return this;
        }

        public Builder setDialogAnimationRes(int i2) {
            this.params.mDialogAnimationRes = i2;
            return this;
        }

        public Builder setDimAmount(float f2) {
            this.params.mDimAmount = f2;
            return this;
        }

        public Builder setGravity(int i2) {
            this.params.mGravity = i2;
            return this;
        }

        public Builder setHeight(int i2) {
            this.params.mHeight = i2;
            return this;
        }

        public Builder setItemColor(int i2) {
            this.params.mItemSelectedColor = i2;
            return this;
        }

        public Builder setNewData(ArrayList<String> arrayList) {
            this.params.mLists = arrayList;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.params.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.params.mKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnListItemSelectedListener(OnListItemSelectedListener onListItemSelectedListener) {
            this.params.mOnListItemSelectedListener = onListItemSelectedListener;
            return this;
        }

        public Builder setScreenHeightAspect(Context context, float f2) {
            this.params.mHeight = (int) (BaseDialogFragment.getScreenHeight(context) * f2);
            return this;
        }

        public Builder setScreenWidthAspect(Context context, float f2) {
            this.params.mWidth = (int) (BaseDialogFragment.getScreenWidth(context) * f2);
            return this;
        }

        public Builder setTag(String str) {
            this.params.mTag = str;
            return this;
        }

        public Builder setUnItemColor(int i2) {
            this.params.mItemUnSelectedColor = i2;
            return this;
        }

        public Builder setWidth(int i2) {
            this.params.mWidth = i2;
            return this;
        }
    }

    public interface OnListItemSelectedListener {
        void onClick(String str);
    }

    public class SelectorQuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private String mPosition;

        public SelectorQuickAdapter(int i2, @Nullable List<String> list) {
            super(i2, list);
        }

        public void setSelectedPosition(String str) {
            this.mPosition = str;
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, String str) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.alivc_tv_name);
            textView.setText(str);
            String str2 = this.mPosition;
            if (str2 == null || !str2.equals(str)) {
                textView.setTextColor(AlivcListSelectorDialogFragment.this.mItemUnSelectedColor);
            } else {
                textView.setTextColor(AlivcListSelectorDialogFragment.this.mItemSelectedColor);
            }
        }
    }

    private void initData() {
        List<String> list = this.mLists;
        if (list != null) {
            this.mSelectorQuickAdapter.setNewData(list);
            this.mSelectorQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.aliyun.svideo.common.base.AlivcListSelectorDialogFragment.2
                @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    if (AlivcListSelectorDialogFragment.this.mOnListItemSelectedListener != null) {
                        AlivcListSelectorDialogFragment.this.mOnListItemSelectedListener.onClick(baseQuickAdapter.getData().get(i2).toString());
                        AlivcListSelectorDialogFragment.this.dismiss();
                    }
                }
            });
        }
        String str = this.mPosition;
        if (str != null) {
            this.mSelectorQuickAdapter.setSelectedPosition(str);
        }
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public void bindView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.alivc_common_dialog_recyclerview);
        TextView textView = (TextView) view.findViewById(R.id.alivc_tv_cancel);
        this.mTvCancel = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.base.AlivcListSelectorDialogFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AlivcListSelectorDialogFragment.this.dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        SelectorQuickAdapter selectorQuickAdapter = new SelectorQuickAdapter(R.layout.alivc_common_dialog_rv_selector_item, null);
        this.mSelectorQuickAdapter = selectorQuickAdapter;
        recyclerView.setAdapter(selectorQuickAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), 1);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.alivc_common_rv_divider_gray_vertical));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        initData();
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getDialogAnimationRes() {
        return this.mDialogAnimationRes;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getDialogHeight() {
        return this.mHeight;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getDialogWidth() {
        return this.mWidth;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public float getDimAmount() {
        return this.mDimAmount;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public String getFragmentTag() {
        return this.mTag;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getGravity() {
        return this.mGravity;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getLayoutRes() {
        return R.layout.alivc_common_dialogfragment_selector;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public DialogInterface.OnKeyListener getOnKeyListener() {
        return this.mKeyListener;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public boolean isCancelableOutside() {
        return this.mIsCancelableOutside;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.onDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setOnListSelectedListener(OnListItemSelectedListener onListItemSelectedListener) {
        this.mOnListItemSelectedListener = onListItemSelectedListener;
    }

    public void setPosition(String str) {
        this.mPosition = str;
    }

    public AlivcListSelectorDialogFragment show() {
        Log.d("Dialog", "show");
        try {
            FragmentTransaction fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction();
            fragmentTransactionBeginTransaction.remove(this);
            fragmentTransactionBeginTransaction.add(this, this.tag);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        } catch (Exception e2) {
            Log.e("Dialog", e2.toString());
        }
        return this;
    }
}
