package com.lxj.xpopup.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class BottomListPopupView extends BottomPopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    int checkedPosition;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;
    CharSequence title;
    TextView tv_cancel;
    TextView tv_title;
    View vv_divider;

    public BottomListPopupView(@NonNull Context context, int i2, int i3) {
        super(context);
        this.checkedPosition = -1;
        this.bindLayoutId = i2;
        this.bindItemLayoutId = i3;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void applyDarkTheme() throws Resources.NotFoundException {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.TRUE);
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i2 = R.color._xpopup_white_color;
        textView.setTextColor(resources.getColor(i2));
        TextView textView2 = this.tv_cancel;
        if (textView2 != null) {
            textView2.setTextColor(getResources().getColor(i2));
        }
        findViewById(R.id.xpopup_divider).setBackgroundColor(getResources().getColor(R.color._xpopup_list_dark_divider));
        View view = this.vv_divider;
        if (view != null) {
            view.setBackgroundColor(Color.parseColor("#1B1B1B"));
        }
        View popupImplView = getPopupImplView();
        int color = getResources().getColor(R.color._xpopup_dark_color);
        float f2 = this.popupInfo.borderRadius;
        popupImplView.setBackground(XPopupUtils.createDrawable(color, f2, f2, 0.0f, 0.0f));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void applyLightTheme() throws Resources.NotFoundException {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.FALSE);
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i2 = R.color._xpopup_dark_color;
        textView.setTextColor(resources.getColor(i2));
        TextView textView2 = this.tv_cancel;
        if (textView2 != null) {
            textView2.setTextColor(getResources().getColor(i2));
        }
        findViewById(R.id.xpopup_divider).setBackgroundColor(getResources().getColor(R.color._xpopup_list_divider));
        View view = this.vv_divider;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R.color._xpopup_white_color));
        }
        View popupImplView = getPopupImplView();
        int color = getResources().getColor(R.color._xpopup_light_color);
        float f2 = this.popupInfo.borderRadius;
        popupImplView.setBackground(XPopupUtils.createDrawable(color, f2, f2, 0.0f, 0.0f));
    }

    public void applyTheme() throws Resources.NotFoundException {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        int i2 = this.bindLayoutId;
        return i2 == 0 ? R.layout._xpopup_bottom_impl_list : i2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() throws Resources.NotFoundException {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        this.vv_divider = findViewById(R.id.vv_divider);
        TextView textView = this.tv_cancel;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.impl.BottomListPopupView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BottomListPopupView.this.dismiss();
                }
            });
        }
        if (this.tv_title != null) {
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
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(listAsList, i3) { // from class: com.lxj.xpopup.impl.BottomListPopupView.2
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(@NonNull ViewHolder viewHolder, @NonNull String str, int i4) {
                int i5 = R.id.tv_text;
                viewHolder.setText(i5, str);
                ImageView imageView = (ImageView) viewHolder.getViewOrNull(R.id.iv_image);
                int[] iArr = BottomListPopupView.this.iconIds;
                if (iArr == null || iArr.length <= i4) {
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                } else if (imageView != null) {
                    imageView.setVisibility(0);
                    imageView.setBackgroundResource(BottomListPopupView.this.iconIds[i4]);
                }
                if (BottomListPopupView.this.checkedPosition != -1) {
                    int i6 = R.id.check_view;
                    if (viewHolder.getViewOrNull(i6) != null) {
                        viewHolder.getView(i6).setVisibility(i4 != BottomListPopupView.this.checkedPosition ? 8 : 0);
                        ((CheckView) viewHolder.getView(i6)).setColor(XPopup.getPrimaryColor());
                    }
                    TextView textView2 = (TextView) viewHolder.getView(i5);
                    BottomListPopupView bottomListPopupView = BottomListPopupView.this;
                    textView2.setTextColor(i4 == bottomListPopupView.checkedPosition ? XPopup.getPrimaryColor() : bottomListPopupView.getResources().getColor(R.color._xpopup_title_color));
                } else {
                    int i7 = R.id.check_view;
                    if (viewHolder.getViewOrNull(i7) != null) {
                        viewHolder.getView(i7).setVisibility(8);
                    }
                    ((TextView) viewHolder.getView(i5)).setGravity(17);
                }
                BottomListPopupView bottomListPopupView2 = BottomListPopupView.this;
                if (bottomListPopupView2.bindItemLayoutId == 0) {
                    if (bottomListPopupView2.popupInfo.isDarkTheme) {
                        ((TextView) viewHolder.getView(i5)).setTextColor(BottomListPopupView.this.getResources().getColor(R.color._xpopup_white_color));
                    } else {
                        ((TextView) viewHolder.getView(i5)).setTextColor(BottomListPopupView.this.getResources().getColor(R.color._xpopup_dark_color));
                    }
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.BottomListPopupView.3
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener, com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i4) {
                if (BottomListPopupView.this.selectListener != null) {
                    BottomListPopupView.this.selectListener.onSelect(i4, (String) easyAdapter.getData().get(i4));
                }
                BottomListPopupView bottomListPopupView = BottomListPopupView.this;
                if (bottomListPopupView.checkedPosition != -1) {
                    bottomListPopupView.checkedPosition = i4;
                    easyAdapter.notifyDataSetChanged();
                }
                BottomListPopupView.this.postDelayed(new Runnable() { // from class: com.lxj.xpopup.impl.BottomListPopupView.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BottomListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                            BottomListPopupView.this.dismiss();
                        }
                    }
                }, 100L);
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    public BottomListPopupView setCheckedPosition(int i2) {
        this.checkedPosition = i2;
        return this;
    }

    public BottomListPopupView setOnSelectListener(OnSelectListener onSelectListener) {
        this.selectListener = onSelectListener;
        return this;
    }

    public BottomListPopupView setStringData(CharSequence charSequence, String[] strArr, int[] iArr) {
        this.title = charSequence;
        this.data = strArr;
        this.iconIds = iArr;
        return this;
    }
}
