package com.aliyun.svideo.common.base;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.aliyun.svideo.common.R;
import com.aliyun.svideo.common.widget.WheelView;

/* loaded from: classes2.dex */
public class AlivcWheelDialogFragment extends BaseDialogFragment implements WheelView.OnValueChangeListener {
    private String mDialogLeft;
    private String mDialogRight;
    private String[] mDialogWheel;
    public FragmentManager mFragmentManager;
    private OnWheelDialogListener mOnWheelDialogListener;
    private TextView mTvLeft;
    private TextView mTvRight;
    private WheelView mWheelView;
    public boolean mIsCancelableOutside = true;
    public int mDialogAnimationRes = 0;

    public static final class Builder {
        private AlivcWheelDialogFragment mDialogFragment;

        public Builder(FragmentManager fragmentManager) {
            AlivcWheelDialogFragment alivcWheelDialogFragment = new AlivcWheelDialogFragment();
            this.mDialogFragment = alivcWheelDialogFragment;
            alivcWheelDialogFragment.mFragmentManager = fragmentManager;
        }

        public Builder cancelString(String str) {
            this.mDialogFragment.mDialogLeft = str;
            return this;
        }

        public AlivcWheelDialogFragment create() {
            return this.mDialogFragment;
        }

        public Builder dialogAnimationRes(int i2) {
            this.mDialogFragment.mDialogAnimationRes = i2;
            return this;
        }

        public Builder isCancelableOutside(boolean z2) {
            this.mDialogFragment.mIsCancelableOutside = z2;
            return this;
        }

        public Builder onWheelDialogListener(OnWheelDialogListener onWheelDialogListener) {
            this.mDialogFragment.mOnWheelDialogListener = onWheelDialogListener;
            return this;
        }

        public Builder setWheelData(String[] strArr) {
            this.mDialogFragment.mDialogWheel = strArr;
            return this;
        }

        public Builder sureString(String str) {
            this.mDialogFragment.mDialogRight = str;
            return this;
        }
    }

    public interface OnWheelDialogListener {
        void onClickLeft(DialogFragment dialogFragment, String str);

        void onClickRight(DialogFragment dialogFragment, String str);

        void onValueChanged(DialogFragment dialogFragment, String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getWheelValue() {
        String[] displayedValues = this.mWheelView.getDisplayedValues();
        if (displayedValues == null) {
            return null;
        }
        return displayedValues[this.mWheelView.getValue() - this.mWheelView.getMinValue()];
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public void bindView(View view) {
        this.mTvLeft = (TextView) view.findViewById(R.id.alivc_tv_cancel);
        this.mTvRight = (TextView) view.findViewById(R.id.alivc_tv_sure);
        this.mWheelView = (WheelView) view.findViewById(R.id.alivc_wheelView_dialog);
        this.mTvLeft.setText(this.mDialogLeft);
        this.mTvRight.setText(this.mDialogRight);
        this.mWheelView.refreshByNewDisplayedValues(this.mDialogWheel);
        this.mWheelView.setWrapSelectorWheel(false);
        this.mWheelView.setDividerColor(ContextCompat.getColor(getContext(), R.color.alivc_common_bg_white_gray));
        this.mWheelView.setSelectedTextColor(ContextCompat.getColor(getContext(), R.color.alivc_common_bg_black));
        this.mWheelView.setNormalTextColor(ContextCompat.getColor(getContext(), R.color.alivc_common_font_gray_333333));
        initEvent();
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getDialogAnimationRes() {
        return this.mDialogAnimationRes;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getLayoutRes() {
        return R.layout.alivc_common_dialogfragment_wheelview;
    }

    public void initEvent() {
        this.mTvLeft.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.base.AlivcWheelDialogFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AlivcWheelDialogFragment.this.mOnWheelDialogListener != null) {
                    OnWheelDialogListener onWheelDialogListener = AlivcWheelDialogFragment.this.mOnWheelDialogListener;
                    AlivcWheelDialogFragment alivcWheelDialogFragment = AlivcWheelDialogFragment.this;
                    onWheelDialogListener.onClickLeft(alivcWheelDialogFragment, alivcWheelDialogFragment.getWheelValue());
                }
            }
        });
        this.mTvRight.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.base.AlivcWheelDialogFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AlivcWheelDialogFragment.this.mOnWheelDialogListener != null) {
                    OnWheelDialogListener onWheelDialogListener = AlivcWheelDialogFragment.this.mOnWheelDialogListener;
                    AlivcWheelDialogFragment alivcWheelDialogFragment = AlivcWheelDialogFragment.this;
                    onWheelDialogListener.onClickRight(alivcWheelDialogFragment, alivcWheelDialogFragment.getWheelValue());
                }
            }
        });
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public boolean isCancelableOutside() {
        return this.mIsCancelableOutside;
    }

    @Override // com.aliyun.svideo.common.widget.WheelView.OnValueChangeListener
    public void onValueChange(WheelView wheelView, int i2, int i3) {
        OnWheelDialogListener onWheelDialogListener;
        String[] displayedValues = this.mWheelView.getDisplayedValues();
        if (displayedValues == null || (onWheelDialogListener = this.mOnWheelDialogListener) == null) {
            return;
        }
        onWheelDialogListener.onValueChanged(this, displayedValues[i3 - this.mWheelView.getMinValue()]);
    }

    public void setWheelDialogListener(OnWheelDialogListener onWheelDialogListener) {
        this.mOnWheelDialogListener = onWheelDialogListener;
    }

    public AlivcWheelDialogFragment show() {
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
