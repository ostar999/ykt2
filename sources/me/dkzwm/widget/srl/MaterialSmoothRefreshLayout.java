package me.dkzwm.widget.srl;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.internal.view.SupportMenu;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.extra.footer.MaterialFooter;
import me.dkzwm.widget.srl.extra.header.MaterialHeader;
import me.dkzwm.widget.srl.indicator.IIndicator;
import me.dkzwm.widget.srl.utils.PixelUtl;

/* loaded from: classes9.dex */
public class MaterialSmoothRefreshLayout extends SmoothRefreshLayout {
    protected SmoothRefreshLayout.OnUIPositionChangedListener mOnUIPositionChangedListener;

    public MaterialSmoothRefreshLayout(Context context) {
        super(context);
        this.mOnUIPositionChangedListener = new SmoothRefreshLayout.OnUIPositionChangedListener() { // from class: me.dkzwm.widget.srl.MaterialSmoothRefreshLayout.1
            int mLastMovingStatus = 0;

            @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnUIPositionChangedListener
            public void onChanged(byte b3, IIndicator iIndicator) {
                int movingStatus = iIndicator.getMovingStatus();
                if (movingStatus == 2) {
                    if (movingStatus != this.mLastMovingStatus) {
                        MaterialSmoothRefreshLayout.this.setEnablePinContentView(true);
                        MaterialSmoothRefreshLayout.this.setEnablePinRefreshViewWhileLoading(true);
                    }
                } else if (movingStatus != this.mLastMovingStatus) {
                    MaterialSmoothRefreshLayout.this.setEnablePinContentView(false);
                }
                this.mLastMovingStatus = movingStatus;
            }
        };
    }

    @Override // me.dkzwm.widget.srl.SmoothRefreshLayout
    public void init(Context context, AttributeSet attributeSet, int i2, int i3) {
        super.init(context, attributeSet, i2, i3);
        MaterialHeader materialHeader = new MaterialHeader(context);
        materialHeader.setColorSchemeColors(new int[]{SupportMenu.CATEGORY_MASK, -16776961, -16711936, -16777216});
        materialHeader.setPadding(0, PixelUtl.dp2px(context, 25.0f), 0, PixelUtl.dp2px(context, 20.0f));
        setHeaderView(materialHeader);
        setFooterView(new MaterialFooter(context));
    }

    public void materialStyle() {
        setRatioOfFooterToRefresh(0.95f);
        setMaxMoveRatioOfFooter(1.0f);
        setRatioToKeep(1.0f);
        setMaxMoveRatioOfHeader(1.5f);
        setEnablePinContentView(true);
        setEnableKeepRefreshView(true);
        setEnablePinRefreshViewWhileLoading(true);
        setEnableNextPtrAtOnce(true);
        IRefreshView<IIndicator> iRefreshView = this.mHeaderView;
        if (iRefreshView instanceof MaterialHeader) {
            ((MaterialHeader) iRefreshView).doHookUIRefreshComplete(this);
        }
        if (isDisabledLoadMore()) {
            return;
        }
        removeOnUIPositionChangedListener(this.mOnUIPositionChangedListener);
        addOnUIPositionChangedListener(this.mOnUIPositionChangedListener);
    }

    public MaterialSmoothRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnUIPositionChangedListener = new SmoothRefreshLayout.OnUIPositionChangedListener() { // from class: me.dkzwm.widget.srl.MaterialSmoothRefreshLayout.1
            int mLastMovingStatus = 0;

            @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnUIPositionChangedListener
            public void onChanged(byte b3, IIndicator iIndicator) {
                int movingStatus = iIndicator.getMovingStatus();
                if (movingStatus == 2) {
                    if (movingStatus != this.mLastMovingStatus) {
                        MaterialSmoothRefreshLayout.this.setEnablePinContentView(true);
                        MaterialSmoothRefreshLayout.this.setEnablePinRefreshViewWhileLoading(true);
                    }
                } else if (movingStatus != this.mLastMovingStatus) {
                    MaterialSmoothRefreshLayout.this.setEnablePinContentView(false);
                }
                this.mLastMovingStatus = movingStatus;
            }
        };
    }

    public MaterialSmoothRefreshLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mOnUIPositionChangedListener = new SmoothRefreshLayout.OnUIPositionChangedListener() { // from class: me.dkzwm.widget.srl.MaterialSmoothRefreshLayout.1
            int mLastMovingStatus = 0;

            @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnUIPositionChangedListener
            public void onChanged(byte b3, IIndicator iIndicator) {
                int movingStatus = iIndicator.getMovingStatus();
                if (movingStatus == 2) {
                    if (movingStatus != this.mLastMovingStatus) {
                        MaterialSmoothRefreshLayout.this.setEnablePinContentView(true);
                        MaterialSmoothRefreshLayout.this.setEnablePinRefreshViewWhileLoading(true);
                    }
                } else if (movingStatus != this.mLastMovingStatus) {
                    MaterialSmoothRefreshLayout.this.setEnablePinContentView(false);
                }
                this.mLastMovingStatus = movingStatus;
            }
        };
    }
}
