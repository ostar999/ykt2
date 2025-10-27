package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class ScoreTrendInfoDialog extends CenterPopupView {
    private TextView btnSure;
    private String descTwo;
    private boolean haveNightTheme;
    private boolean isShouldReadAll;
    private ImageView mImgClose;
    private LinearLayout mLyContent;
    private String mTitle;
    private int maxHeight;
    private NestedScrollView scrollView;
    private TextView tvContent;
    private TextView tvTitle;

    public ScoreTrendInfoDialog(@NonNull Context context, String desc, String title) {
        super(context);
        this.haveNightTheme = false;
        this.isShouldReadAll = false;
        this.maxHeight = 0;
        this.descTwo = desc;
        this.mTitle = title;
    }

    private boolean canScroll(NestedScrollView nestedScrollView) {
        return nestedScrollView.getChildCount() > 0 && nestedScrollView.getChildAt(0).getMeasuredHeight() > this.maxHeight;
    }

    private void handleTxt(TextView textView, String value) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        Matcher matcher = Pattern.compile("\\[(.+?)\\]").matcher(value);
        int i2 = 0;
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            if (iStart > i2) {
                spannableStringBuilder.append((CharSequence) value.substring(i2, iStart));
            }
            String strGroup = matcher.group(1);
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append((CharSequence) strGroup);
            spannableStringBuilder.setSpan(new StyleSpan(1), length, strGroup.length() + length, 33);
            i2 = iEnd;
        }
        if (i2 < value.length()) {
            spannableStringBuilder.append((CharSequence) value.substring(i2));
        }
        textView.setText(spannableStringBuilder);
    }

    private void initNightTheme() {
        this.mLyContent.setBackgroundResource(R.drawable.shape_app_bg_corners_16);
        this.tvTitle.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.first_txt_color));
        this.tvContent.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.first_txt_color));
        this.btnSure.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.app_bg));
        this.mImgClose.setImageResource(SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.closeing_night : R.drawable.closeing);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initShouldReadAll() {
        if (!this.isShouldReadAll || !canScroll(this.scrollView)) {
            this.btnSure.setEnabled(true);
            this.btnSure.setBackgroundResource(R.drawable.shape_app_theme_corners_12);
            this.btnSure.setText("我知道了");
            this.btnSure.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.app_bg));
            return;
        }
        this.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.widget.mg
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
                this.f16717a.lambda$initShouldReadAll$2(nestedScrollView, i2, i3, i4, i5);
            }
        });
        this.btnSure.setBackgroundResource(R.drawable.shape_new_bg_two_color_corner12);
        this.btnSure.setText("请完整阅读说明文档后关闭");
        this.btnSure.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color));
        this.btnSure.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initShouldReadAll$2(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
        if (i3 >= nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight()) {
            this.btnSure.setBackgroundResource(R.drawable.shape_app_theme_corners_12);
            this.btnSure.setText("我知道了");
            this.btnSure.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.app_bg));
            this.btnSure.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.view_score_trend_info_dialog;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        return CommonUtil.getScreenWidth(getContext());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        this.mLyContent = (LinearLayout) findViewById(R.id.ly_view);
        this.btnSure = (TextView) findViewById(R.id.btn_sure);
        this.mImgClose = (ImageView) findViewById(R.id.img_close);
        this.tvContent = (TextView) findViewById(R.id.tv_content);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        handleTxt(this.tvContent, this.descTwo);
        this.tvTitle.setText(this.mTitle);
        this.btnSure.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ng
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16741c.lambda$onCreate$0(view);
            }
        });
        this.mImgClose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.og
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16768c.lambda$onCreate$1(view);
            }
        });
        this.maxHeight = UIUtil.dip2px(getContext(), 180.0d);
        if (this.haveNightTheme) {
            initNightTheme();
        }
        if (this.isShouldReadAll) {
            this.mImgClose.setVisibility(8);
        }
        this.tvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.ScoreTrendInfoDialog.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ScoreTrendInfoDialog.this.tvContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (ScoreTrendInfoDialog.this.tvContent.getHeight() > ScoreTrendInfoDialog.this.maxHeight) {
                    ViewGroup.LayoutParams layoutParams = ScoreTrendInfoDialog.this.scrollView.getLayoutParams();
                    layoutParams.height = ScoreTrendInfoDialog.this.maxHeight;
                    ScoreTrendInfoDialog.this.scrollView.setLayoutParams(layoutParams);
                    ScoreTrendInfoDialog.this.tvContent.setVisibility(0);
                }
                ScoreTrendInfoDialog.this.initShouldReadAll();
            }
        });
    }

    public ScoreTrendInfoDialog(@NonNull Context context, String desc, String title, boolean haveNightTheme) {
        super(context);
        this.isShouldReadAll = false;
        this.maxHeight = 0;
        this.descTwo = desc;
        this.mTitle = title;
        this.haveNightTheme = haveNightTheme;
    }

    public ScoreTrendInfoDialog(@NonNull Context context, String desc, String title, boolean haveNightTheme, boolean isShouldReadAll) {
        super(context);
        this.maxHeight = 0;
        this.descTwo = desc;
        this.mTitle = title;
        this.haveNightTheme = haveNightTheme;
        this.isShouldReadAll = isShouldReadAll;
    }
}
