package com.lxj.xpopup.impl;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.R;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class AttachListPopupView extends AttachPopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    protected int contentGravity;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;

    public AttachListPopupView(@NonNull Context context, int i2, int i3) {
        super(context);
        this.contentGravity = 17;
        this.bindLayoutId = i2;
        this.bindItemLayoutId = i3;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.TRUE);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.FALSE);
    }

    public void applyTheme() {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
            this.attachPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(this.popupInfo.isDarkTheme ? R.color._xpopup_dark_color : R.color._xpopup_light_color), this.popupInfo.borderRadius));
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        int i2 = this.bindLayoutId;
        return i2 == 0 ? R.layout._xpopup_attach_impl_list : i2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        List listAsList = Arrays.asList(this.data);
        int i2 = this.bindItemLayoutId;
        if (i2 == 0) {
            i2 = R.layout._xpopup_adapter_text;
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(listAsList, i2) { // from class: com.lxj.xpopup.impl.AttachListPopupView.1
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(@NonNull ViewHolder viewHolder, @NonNull String str, int i3) {
                int i4 = R.id.tv_text;
                viewHolder.setText(i4, str);
                ImageView imageView = (ImageView) viewHolder.getViewOrNull(R.id.iv_image);
                int[] iArr = AttachListPopupView.this.iconIds;
                if (iArr == null || iArr.length <= i3) {
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                } else if (imageView != null) {
                    imageView.setVisibility(0);
                    imageView.setBackgroundResource(AttachListPopupView.this.iconIds[i3]);
                }
                AttachListPopupView attachListPopupView = AttachListPopupView.this;
                if (attachListPopupView.bindItemLayoutId == 0) {
                    if (attachListPopupView.popupInfo.isDarkTheme) {
                        ((TextView) viewHolder.getView(i4)).setTextColor(AttachListPopupView.this.getResources().getColor(R.color._xpopup_white_color));
                    } else {
                        ((TextView) viewHolder.getView(i4)).setTextColor(AttachListPopupView.this.getResources().getColor(R.color._xpopup_dark_color));
                    }
                    ((LinearLayout) viewHolder.getView(R.id._ll_temp)).setGravity(AttachListPopupView.this.contentGravity);
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.AttachListPopupView.2
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener, com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i3) {
                if (AttachListPopupView.this.selectListener != null) {
                    AttachListPopupView.this.selectListener.onSelect(i3, (String) easyAdapter.getData().get(i3));
                }
                if (AttachListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    AttachListPopupView.this.dismiss();
                }
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    public AttachListPopupView setContentGravity(int i2) {
        this.contentGravity = i2;
        return this;
    }

    public AttachListPopupView setOnSelectListener(OnSelectListener onSelectListener) {
        this.selectListener = onSelectListener;
        return this;
    }

    public AttachListPopupView setStringData(String[] strArr, int[] iArr) {
        this.data = strArr;
        this.iconIds = iArr;
        return this;
    }
}
