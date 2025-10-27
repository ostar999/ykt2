package com.hyphenate.easeui.modules.chat;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.modules.chat.interfaces.EaseEmojiconMenuListener;
import com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconIndicatorView;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconScrollTabBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseEmojiconMenu extends LinearLayout implements IChatEmojiconMenu {
    private static final int defaultBigColumns = 4;
    private static final int defaultColumns = 7;
    private int bigEmojiconColumns;
    private int emojiconColumns;
    private List<EaseEmojiconGroupEntity> emojiconGroupList;
    private EaseEmojiconIndicatorView indicatorView;
    private EaseEmojiconMenuListener listener;
    private EaseEmojiconPagerView pagerView;
    private EaseEmojiconScrollTabBar tabBar;

    public class EmojiconPagerViewListener implements EaseEmojiconPagerView.EaseEmojiconPagerViewListener {
        private EmojiconPagerViewListener() {
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onDeleteImageClicked() {
            if (EaseEmojiconMenu.this.listener != null) {
                EaseEmojiconMenu.this.listener.onDeleteImageClicked();
            }
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onExpressionClicked(EaseEmojicon easeEmojicon) {
            if (EaseEmojiconMenu.this.listener != null) {
                EaseEmojiconMenu.this.listener.onExpressionClicked(easeEmojicon);
            }
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onGroupInnerPagePostionChanged(int i2, int i3) {
            EaseEmojiconMenu.this.indicatorView.selectTo(i2, i3);
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onGroupMaxPageSizeChanged(int i2) {
            EaseEmojiconMenu.this.indicatorView.updateIndicator(i2);
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onGroupPagePostionChangedTo(int i2) {
            EaseEmojiconMenu.this.indicatorView.selectTo(i2);
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onGroupPositionChanged(int i2, int i3) {
            EaseEmojiconMenu.this.indicatorView.updateIndicator(i3);
            EaseEmojiconMenu.this.tabBar.selectedTo(i2);
        }

        @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener
        public void onPagerViewInited(int i2, int i3) {
            EaseEmojiconMenu.this.indicatorView.init(i2);
            EaseEmojiconMenu.this.indicatorView.updateIndicator(i3);
            EaseEmojiconMenu.this.tabBar.selectedTo(0);
        }
    }

    public EaseEmojiconMenu(Context context) {
        this(context, null);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseEmojiconMenu);
        this.emojiconColumns = typedArrayObtainStyledAttributes.getInt(R.styleable.EaseEmojiconMenu_emojiconColumns, 7);
        this.bigEmojiconColumns = typedArrayObtainStyledAttributes.getInt(R.styleable.EaseEmojiconMenu_bigEmojiconRows, 4);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu
    public void addEmojiconGroup(EaseEmojiconGroupEntity easeEmojiconGroupEntity) {
        this.emojiconGroupList.add(easeEmojiconGroupEntity);
        this.pagerView.addEmojiconGroup(easeEmojiconGroupEntity, true);
        this.tabBar.addTab(easeEmojiconGroupEntity.getIcon());
    }

    public void init() throws Resources.NotFoundException {
        init(null);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu
    public void removeEmojiconGroup(int i2) {
        this.emojiconGroupList.remove(i2);
        this.pagerView.removeEmojiconGroup(i2);
        this.tabBar.removeTab(i2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu
    public void setEmojiconMenuListener(EaseEmojiconMenuListener easeEmojiconMenuListener) {
        this.listener = easeEmojiconMenuListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu
    public void setTabBarVisibility(boolean z2) {
        if (z2) {
            this.tabBar.setVisibility(0);
        } else {
            this.tabBar.setVisibility(8);
        }
    }

    public EaseEmojiconMenu(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void init(List<EaseEmojiconGroupEntity> list) throws Resources.NotFoundException {
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(new EaseEmojiconGroupEntity(R.drawable.ee_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
        }
        for (EaseEmojiconGroupEntity easeEmojiconGroupEntity : list) {
            this.emojiconGroupList.add(easeEmojiconGroupEntity);
            this.tabBar.addTab(easeEmojiconGroupEntity.getIcon());
        }
        this.pagerView.setPagerViewListener(new EmojiconPagerViewListener());
        this.pagerView.init(this.emojiconGroupList, this.emojiconColumns, this.bigEmojiconColumns);
        this.tabBar.setTabBarItemClickListener(new EaseEmojiconScrollTabBar.EaseScrollTabBarItemClickListener() { // from class: com.hyphenate.easeui.modules.chat.EaseEmojiconMenu.1
            @Override // com.hyphenate.easeui.widget.emojicon.EaseEmojiconScrollTabBar.EaseScrollTabBarItemClickListener
            public void onItemClick(int i2) throws Resources.NotFoundException {
                EaseEmojiconMenu.this.pagerView.setGroupPostion(i2);
            }
        });
    }

    public EaseEmojiconMenu(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.emojiconGroupList = new ArrayList();
        LayoutInflater.from(context).inflate(R.layout.ease_widget_emojicon, this);
        this.pagerView = (EaseEmojiconPagerView) findViewById(R.id.pager_view);
        this.indicatorView = (EaseEmojiconIndicatorView) findViewById(R.id.indicator_view);
        this.tabBar = (EaseEmojiconScrollTabBar) findViewById(R.id.tab_bar);
        if (DarkModeHelper.isDarkMode(context)) {
            findViewById(R.id.line).setBackground(new ColorDrawable(Color.parseColor("#2E3241")));
        }
        initAttrs(context, attributeSet);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu
    public void addEmojiconGroup(List<EaseEmojiconGroupEntity> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            EaseEmojiconGroupEntity easeEmojiconGroupEntity = list.get(i2);
            this.emojiconGroupList.add(easeEmojiconGroupEntity);
            EaseEmojiconPagerView easeEmojiconPagerView = this.pagerView;
            boolean z2 = true;
            if (i2 != list.size() - 1) {
                z2 = false;
            }
            easeEmojiconPagerView.addEmojiconGroup(easeEmojiconGroupEntity, z2);
            this.tabBar.addTab(easeEmojiconGroupEntity.getIcon());
        }
    }
}
