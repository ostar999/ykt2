package com.lxj.xpopup.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class CenterListPopupView extends CenterPopupView {
    int checkedPosition;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;
    CharSequence title;
    TextView tv_title;

    public CenterListPopupView(@NonNull Context context, int i2, int i3) {
        super(context);
        this.checkedPosition = -1;
        this.bindLayoutId = i2;
        this.bindItemLayoutId = i3;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.TRUE);
        this.tv_title.setTextColor(getResources().getColor(R.color._xpopup_white_color));
        findViewById(R.id.xpopup_divider).setBackgroundColor(getResources().getColor(R.color._xpopup_list_dark_divider));
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.FALSE);
        this.tv_title.setTextColor(getResources().getColor(R.color._xpopup_dark_color));
        findViewById(R.id.xpopup_divider).setBackgroundColor(getResources().getColor(R.color._xpopup_list_divider));
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        int i2 = this.bindLayoutId;
        return i2 == 0 ? R.layout._xpopup_center_impl_list : i2;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        int i2 = this.popupInfo.maxWidth;
        return i2 == 0 ? (int) (super.getMaxWidth() * 0.8f) : i2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        TextView textView = (TextView) findViewById(R.id.tv_title);
        this.tv_title = textView;
        if (textView != null) {
            if (TextUtils.isEmpty(this.title)) {
                this.tv_title.setVisibility(8);
                int i2 = R.id.xpopup_divider;
                if (findViewById(i2) != null) {
                    findViewById(i2).setVisibility(8);
                }
            } else {
                this.tv_title.setText(this.title);
            }
        }
        List listAsList = Arrays.asList(this.data);
        int i3 = this.bindItemLayoutId;
        if (i3 == 0) {
            i3 = R.layout._xpopup_adapter_text_match;
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(listAsList, i3) { // from class: com.lxj.xpopup.impl.CenterListPopupView.1
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(@NonNull ViewHolder viewHolder, @NonNull String str, int i4) {
                int i5 = R.id.tv_text;
                viewHolder.setText(i5, str);
                ImageView imageView = (ImageView) viewHolder.getViewOrNull(R.id.iv_image);
                int[] iArr = CenterListPopupView.this.iconIds;
                if (iArr == null || iArr.length <= i4) {
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                } else if (imageView != null) {
                    imageView.setVisibility(0);
                    imageView.setBackgroundResource(CenterListPopupView.this.iconIds[i4]);
                }
                if (CenterListPopupView.this.checkedPosition != -1) {
                    int i6 = R.id.check_view;
                    if (viewHolder.getViewOrNull(i6) != null) {
                        viewHolder.getView(i6).setVisibility(i4 != CenterListPopupView.this.checkedPosition ? 8 : 0);
                        ((CheckView) viewHolder.getView(i6)).setColor(XPopup.getPrimaryColor());
                    }
                    TextView textView2 = (TextView) viewHolder.getView(i5);
                    CenterListPopupView centerListPopupView = CenterListPopupView.this;
                    textView2.setTextColor(i4 == centerListPopupView.checkedPosition ? XPopup.getPrimaryColor() : centerListPopupView.getResources().getColor(R.color._xpopup_title_color));
                } else {
                    int i7 = R.id.check_view;
                    if (viewHolder.getViewOrNull(i7) != null) {
                        viewHolder.getView(i7).setVisibility(8);
                    }
                    ((TextView) viewHolder.getView(i5)).setGravity(17);
                }
                if (((CenterPopupView) CenterListPopupView.this).bindItemLayoutId == 0) {
                    if (CenterListPopupView.this.popupInfo.isDarkTheme) {
                        ((TextView) viewHolder.getView(i5)).setTextColor(CenterListPopupView.this.getResources().getColor(R.color._xpopup_white_color));
                    } else {
                        ((TextView) viewHolder.getView(i5)).setTextColor(CenterListPopupView.this.getResources().getColor(R.color._xpopup_dark_color));
                    }
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.CenterListPopupView.2
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener, com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i4) {
                if (CenterListPopupView.this.selectListener != null && i4 >= 0 && i4 < easyAdapter.getData().size()) {
                    CenterListPopupView.this.selectListener.onSelect(i4, (String) easyAdapter.getData().get(i4));
                }
                CenterListPopupView centerListPopupView = CenterListPopupView.this;
                if (centerListPopupView.checkedPosition != -1) {
                    centerListPopupView.checkedPosition = i4;
                    easyAdapter.notifyDataSetChanged();
                }
                if (CenterListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    CenterListPopupView.this.dismiss();
                }
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    public CenterListPopupView setCheckedPosition(int i2) {
        this.checkedPosition = i2;
        return this;
    }

    public CenterListPopupView setOnSelectListener(OnSelectListener onSelectListener) {
        this.selectListener = onSelectListener;
        return this;
    }

    public CenterListPopupView setStringData(CharSequence charSequence, String[] strArr, int[] iArr) {
        this.title = charSequence;
        this.data = strArr;
        this.iconIds = iArr;
        return this;
    }
}
