package com.hyphenate.easeui.widget.emojicon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.view.ViewCompat;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.util.DensityUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseEmojiconScrollTabBar extends RelativeLayout {
    private Context context;
    private EaseScrollTabBarItemClickListener itemClickListener;
    private HorizontalScrollView scrollView;
    private LinearLayout tabContainer;
    private List<ImageView> tabList;

    public interface EaseScrollTabBarItemClickListener {
        void onItemClick(int i2);
    }

    public EaseEmojiconScrollTabBar(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_emojicon_tab_bar, this);
        this.scrollView = (HorizontalScrollView) findViewById(R.id.scroll_view);
        this.tabContainer = (LinearLayout) findViewById(R.id.tab_container);
        if (DarkModeHelper.isDarkMode(context)) {
            this.scrollView.setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addTab$0(int i2, View view) {
        EaseScrollTabBarItemClickListener easeScrollTabBarItemClickListener = this.itemClickListener;
        if (easeScrollTabBarItemClickListener != null) {
            easeScrollTabBarItemClickListener.onItemClick(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scrollTo$1(int i2) {
        int scrollX = this.tabContainer.getScrollX();
        int x2 = (int) ViewCompat.getX(this.tabContainer.getChildAt(i2));
        if (x2 < scrollX) {
            this.scrollView.scrollTo(x2, 0);
            return;
        }
        int width = x2 + this.tabContainer.getChildAt(i2).getWidth();
        int width2 = scrollX + this.scrollView.getWidth();
        if (width > width2) {
            this.scrollView.scrollTo(width - width2, 0);
        }
    }

    private void scrollTo(final int i2) {
        if (i2 < this.tabContainer.getChildCount()) {
            this.scrollView.post(new Runnable() { // from class: com.hyphenate.easeui.widget.emojicon.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8761c.lambda$scrollTo$1(i2);
                }
            });
        }
    }

    public void addTab(int i2) {
        View viewInflate = View.inflate(this.context, R.layout.ease_scroll_tab_item, null);
        if (DarkModeHelper.isDarkMode(this.context)) {
            viewInflate.findViewById(R.id.line).setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
        }
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_icon);
        imageView.setImageResource(i2);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dip2px(this.context, 60), -1));
        this.tabContainer.addView(viewInflate);
        this.tabList.add(imageView);
        final int size = this.tabList.size() - 1;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.widget.emojicon.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f8759c.lambda$addTab$0(size, view);
            }
        });
    }

    public void removeTab(int i2) {
        this.tabContainer.removeViewAt(i2);
        this.tabList.remove(i2);
    }

    public void selectedTo(int i2) {
        scrollTo(i2);
        for (int i3 = 0; i3 < this.tabList.size(); i3++) {
            if (DarkModeHelper.isDarkMode(this.context)) {
                this.tabList.get(i3).setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
            } else if (i2 == i3) {
                this.tabList.get(i3).setBackgroundColor(getResources().getColor(R.color.emojicon_tab_selected));
            } else {
                this.tabList.get(i3).setBackgroundColor(getResources().getColor(R.color.emojicon_tab_nomal));
            }
        }
    }

    public void setTabBarItemClickListener(EaseScrollTabBarItemClickListener easeScrollTabBarItemClickListener) {
        this.itemClickListener = easeScrollTabBarItemClickListener;
    }

    public EaseEmojiconScrollTabBar(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet);
    }

    public EaseEmojiconScrollTabBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.tabList = new ArrayList();
        init(context, attributeSet);
    }
}
