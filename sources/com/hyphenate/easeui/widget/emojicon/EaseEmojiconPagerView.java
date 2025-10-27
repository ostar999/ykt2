package com.hyphenate.easeui.widget.emojicon;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EmojiconGridAdapter;
import com.hyphenate.easeui.adapter.EmojiconPagerAdapter;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseEmojiconPagerView extends ViewPager {
    private int bigEmojiconColumns;
    private int bigEmojiconRows;
    private Context context;
    private int emojiconColumns;
    private int emojiconRows;
    private int firstGroupPageSize;
    private List<EaseEmojiconGroupEntity> groupEntities;
    private int maxPageCount;
    private PagerAdapter pagerAdapter;
    private EaseEmojiconPagerViewListener pagerViewListener;
    private int previousPagerPosition;
    private List<View> viewpages;

    public interface EaseEmojiconPagerViewListener {
        void onDeleteImageClicked();

        void onExpressionClicked(EaseEmojicon easeEmojicon);

        void onGroupInnerPagePostionChanged(int i2, int i3);

        void onGroupMaxPageSizeChanged(int i2);

        void onGroupPagePostionChangedTo(int i2);

        void onGroupPositionChanged(int i2, int i3);

        void onPagerViewInited(int i2, int i3);
    }

    public class EmojiPagerChangeListener implements ViewPager.OnPageChangeListener {
        private EmojiPagerChangeListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i2, float f2, int i3) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i2) {
            Iterator it = EaseEmojiconPagerView.this.groupEntities.iterator();
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                int pageSize = EaseEmojiconPagerView.this.getPageSize((EaseEmojiconGroupEntity) it.next());
                int i5 = i3 + pageSize;
                if (i5 <= i2) {
                    i4++;
                    i3 = i5;
                } else if (EaseEmojiconPagerView.this.previousPagerPosition - i3 < 0) {
                    if (EaseEmojiconPagerView.this.pagerViewListener != null) {
                        EaseEmojiconPagerView.this.pagerViewListener.onGroupPositionChanged(i4, pageSize);
                        EaseEmojiconPagerView.this.pagerViewListener.onGroupPagePostionChangedTo(0);
                    }
                } else if (EaseEmojiconPagerView.this.previousPagerPosition - i3 >= pageSize) {
                    if (EaseEmojiconPagerView.this.pagerViewListener != null) {
                        EaseEmojiconPagerView.this.pagerViewListener.onGroupPositionChanged(i4, pageSize);
                        EaseEmojiconPagerView.this.pagerViewListener.onGroupPagePostionChangedTo(i2 - i3);
                    }
                } else if (EaseEmojiconPagerView.this.pagerViewListener != null) {
                    EaseEmojiconPagerView.this.pagerViewListener.onGroupInnerPagePostionChanged(EaseEmojiconPagerView.this.previousPagerPosition - i3, i2 - i3);
                }
            }
            EaseEmojiconPagerView.this.previousPagerPosition = i2;
        }
    }

    public EaseEmojiconPagerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.emojiconRows = 3;
        this.emojiconColumns = 7;
        this.bigEmojiconRows = 2;
        this.bigEmojiconColumns = 4;
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPageSize(EaseEmojiconGroupEntity easeEmojiconGroupEntity) {
        List<EaseEmojicon> emojiconList = easeEmojiconGroupEntity.getEmojiconList();
        int i2 = (this.emojiconColumns * this.emojiconRows) - 1;
        int size = emojiconList.size();
        if (easeEmojiconGroupEntity.getType() == EaseEmojicon.Type.BIG_EXPRESSION) {
            i2 = this.bigEmojiconRows * this.bigEmojiconColumns;
        }
        int i3 = size % i2;
        int i4 = size / i2;
        return i3 == 0 ? i4 : i4 + 1;
    }

    public void addEmojiconGroup(EaseEmojiconGroupEntity easeEmojiconGroupEntity, boolean z2) {
        int pageSize = getPageSize(easeEmojiconGroupEntity);
        if (pageSize > this.maxPageCount) {
            this.maxPageCount = pageSize;
            EaseEmojiconPagerViewListener easeEmojiconPagerViewListener = this.pagerViewListener;
            if (easeEmojiconPagerViewListener != null && this.pagerAdapter != null) {
                easeEmojiconPagerViewListener.onGroupMaxPageSizeChanged(pageSize);
            }
        }
        this.viewpages.addAll(getGroupGridViews(easeEmojiconGroupEntity));
        PagerAdapter pagerAdapter = this.pagerAdapter;
        if (pagerAdapter == null || !z2) {
            return;
        }
        pagerAdapter.notifyDataSetChanged();
    }

    public List<View> getGroupGridViews(EaseEmojiconGroupEntity easeEmojiconGroupEntity) {
        List<EaseEmojicon> emojiconList = easeEmojiconGroupEntity.getEmojiconList();
        int i2 = (this.emojiconColumns * this.emojiconRows) - 1;
        int size = emojiconList.size();
        EaseEmojicon.Type type = easeEmojiconGroupEntity.getType();
        if (type == EaseEmojicon.Type.BIG_EXPRESSION) {
            i2 = this.bigEmojiconColumns * this.bigEmojiconRows;
        }
        int i3 = size % i2 == 0 ? size / i2 : (size / i2) + 1;
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < i3; i4++) {
            View viewInflate = View.inflate(this.context, R.layout.ease_expression_gridview, null);
            GridView gridView = (GridView) viewInflate.findViewById(R.id.gridview);
            EaseEmojicon.Type type2 = EaseEmojicon.Type.BIG_EXPRESSION;
            if (type == type2) {
                gridView.setNumColumns(this.bigEmojiconColumns);
            } else {
                gridView.setNumColumns(this.emojiconColumns);
            }
            ArrayList arrayList2 = new ArrayList();
            if (i4 != i3 - 1) {
                arrayList2.addAll(emojiconList.subList(i4 * i2, (i4 + 1) * i2));
            } else {
                arrayList2.addAll(emojiconList.subList(i4 * i2, size));
            }
            if (type != type2) {
                EaseEmojicon easeEmojicon = new EaseEmojicon();
                easeEmojicon.setEmojiText(EaseSmileUtils.DELETE_KEY);
                arrayList2.add(easeEmojicon);
            }
            final EmojiconGridAdapter emojiconGridAdapter = new EmojiconGridAdapter(this.context, 1, arrayList2, type);
            gridView.setAdapter((ListAdapter) emojiconGridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i5, long j2) {
                    EaseEmojicon easeEmojicon2 = (EaseEmojicon) emojiconGridAdapter.getItem(i5);
                    if (EaseEmojiconPagerView.this.pagerViewListener != null) {
                        String emojiText = easeEmojicon2.getEmojiText();
                        if (emojiText == null || !emojiText.equals(EaseSmileUtils.DELETE_KEY)) {
                            EaseEmojiconPagerView.this.pagerViewListener.onExpressionClicked(easeEmojicon2);
                        } else {
                            EaseEmojiconPagerView.this.pagerViewListener.onDeleteImageClicked();
                        }
                    }
                }
            });
            arrayList.add(viewInflate);
        }
        return arrayList;
    }

    public void init(List<EaseEmojiconGroupEntity> list, int i2, int i3) throws Resources.NotFoundException {
        if (list == null) {
            throw new RuntimeException("emojiconGroupList is null");
        }
        this.groupEntities = list;
        this.emojiconColumns = i2;
        this.bigEmojiconColumns = i3;
        this.viewpages = new ArrayList();
        for (int i4 = 0; i4 < this.groupEntities.size(); i4++) {
            EaseEmojiconGroupEntity easeEmojiconGroupEntity = this.groupEntities.get(i4);
            easeEmojiconGroupEntity.getEmojiconList();
            List<View> groupGridViews = getGroupGridViews(easeEmojiconGroupEntity);
            if (i4 == 0) {
                this.firstGroupPageSize = groupGridViews.size();
            }
            this.maxPageCount = Math.max(groupGridViews.size(), this.maxPageCount);
            this.viewpages.addAll(groupGridViews);
        }
        EmojiconPagerAdapter emojiconPagerAdapter = new EmojiconPagerAdapter(this.viewpages);
        this.pagerAdapter = emojiconPagerAdapter;
        setAdapter(emojiconPagerAdapter);
        setOnPageChangeListener(new EmojiPagerChangeListener());
        EaseEmojiconPagerViewListener easeEmojiconPagerViewListener = this.pagerViewListener;
        if (easeEmojiconPagerViewListener != null) {
            easeEmojiconPagerViewListener.onPagerViewInited(this.maxPageCount, this.firstGroupPageSize);
        }
    }

    public void removeEmojiconGroup(int i2) {
        PagerAdapter pagerAdapter;
        if (i2 <= this.groupEntities.size() - 1 && (pagerAdapter = this.pagerAdapter) != null) {
            pagerAdapter.notifyDataSetChanged();
        }
    }

    public void setGroupPostion(int i2) throws Resources.NotFoundException {
        if (getAdapter() == null || i2 < 0 || i2 >= this.groupEntities.size()) {
            return;
        }
        int pageSize = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            pageSize += getPageSize(this.groupEntities.get(i3));
        }
        setCurrentItem(pageSize);
    }

    public void setPagerViewListener(EaseEmojiconPagerViewListener easeEmojiconPagerViewListener) {
        this.pagerViewListener = easeEmojiconPagerViewListener;
    }

    public EaseEmojiconPagerView(Context context) {
        this(context, null);
    }
}
