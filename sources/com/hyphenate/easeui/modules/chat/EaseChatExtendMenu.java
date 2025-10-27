package com.hyphenate.easeui.modules.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseChatExtendMenuAdapter;
import com.hyphenate.easeui.adapter.EaseChatExtendMenuIndicatorAdapter;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.chat.interfaces.EaseChatExtendMenuItemClickListener;
import com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu;
import com.hyphenate.easeui.widget.chatextend.HorizontalPageLayoutManager;
import com.hyphenate.easeui.widget.chatextend.PagingScrollHelper;
import com.hyphenate.util.DensityUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EaseChatExtendMenu extends FrameLayout implements PagingScrollHelper.onPageChangeListener, IChatExtendMenu, OnItemClickListener {
    private EaseChatExtendMenuAdapter adapter;
    protected Context context;
    private int currentPosition;
    private PagingScrollHelper helper;
    private EaseChatExtendMenuIndicatorAdapter indicatorAdapter;
    private boolean isDarkMode;
    private final int[] itemIds;
    private EaseChatExtendMenuItemClickListener itemListener;
    private Map<Integer, ChatMenuItemModel> itemMap;
    private List<ChatMenuItemModel> itemModels;
    private final int[] itemStrings;
    private final int[] itemdrawables;
    private int numColumns;
    private int numRows;
    private RecyclerView rvExtendMenu;
    private RecyclerView rvIndicator;

    public class ChatMenuItem extends LinearLayout {
        private ImageView imageView;
        private TextView textView;

        public ChatMenuItem(EaseChatExtendMenu easeChatExtendMenu, Context context, AttributeSet attributeSet, int i2) {
            this(context, attributeSet);
        }

        private void init(Context context, AttributeSet attributeSet) {
            LayoutInflater.from(context).inflate(R.layout.ease_chat_menu_item, this);
            this.imageView = (ImageView) findViewById(R.id.image);
            this.textView = (TextView) findViewById(R.id.text);
        }

        public void setImage(int i2) {
            this.imageView.setBackgroundResource(i2);
        }

        public void setText(int i2) {
            this.textView.setText(i2);
        }

        public ChatMenuItem(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            init(context, attributeSet);
        }

        public void setText(String str) {
            this.textView.setText(str);
        }

        public ChatMenuItem(Context context) {
            super(context);
            init(context, null);
        }
    }

    public static class ChatMenuItemModel {
        public EaseChatExtendMenuItemClickListener clickListener;
        public int id;
        public int image;
        public String name;
        public int order;
    }

    public EaseChatExtendMenu(Context context, boolean z2) {
        this(context, null, z2);
    }

    private void addDefaultData() {
        int i2 = 0;
        while (true) {
            int[] iArr = this.itemStrings;
            if (i2 >= iArr.length) {
                return;
            }
            registerMenuItem(iArr[i2], this.itemdrawables[i2], this.itemIds[i2], (EaseChatExtendMenuItemClickListener) null);
            i2++;
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.context = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseChatExtendMenu);
        this.numColumns = typedArrayObtainStyledAttributes.getInt(R.styleable.EaseChatExtendMenu_numColumns, 4);
        this.numRows = typedArrayObtainStyledAttributes.getInt(R.styleable.EaseChatExtendMenu_numRows, 2);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initChatExtendMenu() {
        HorizontalPageLayoutManager horizontalPageLayoutManager = new HorizontalPageLayoutManager(this.numRows, this.numColumns);
        horizontalPageLayoutManager.setItemHeight(DensityUtil.dip2px(this.context, 90.0f));
        this.rvExtendMenu.setLayoutManager(horizontalPageLayoutManager);
        this.rvExtendMenu.setHasFixedSize(true);
        ConcatAdapter concatAdapter = new ConcatAdapter((RecyclerView.Adapter<? extends RecyclerView.ViewHolder>[]) new RecyclerView.Adapter[0]);
        EaseChatExtendMenuAdapter easeChatExtendMenuAdapter = new EaseChatExtendMenuAdapter();
        this.adapter = easeChatExtendMenuAdapter;
        concatAdapter.addAdapter(easeChatExtendMenuAdapter);
        this.rvExtendMenu.setAdapter(concatAdapter);
        this.adapter.setData(this.itemModels);
        PagingScrollHelper pagingScrollHelper = new PagingScrollHelper();
        this.helper = pagingScrollHelper;
        pagingScrollHelper.setUpRecycleView(this.rvExtendMenu);
        this.helper.updateLayoutManger();
        this.helper.scrollToPosition(0);
        setHorizontalFadingEdgeEnabled(true);
        this.helper.setOnPageChangeListener(this);
        this.adapter.setOnItemClickListener(this);
    }

    private void initChatExtendMenuIndicator() {
        EaseChatExtendMenuIndicatorAdapter easeChatExtendMenuIndicatorAdapter = new EaseChatExtendMenuIndicatorAdapter();
        this.indicatorAdapter = easeChatExtendMenuIndicatorAdapter;
        this.rvIndicator.setAdapter(easeChatExtendMenuIndicatorAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.context, 0);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this.context, R.drawable.ease_chat_extend_menu_indicator_divider));
        this.rvIndicator.addItemDecoration(dividerItemDecoration);
        this.indicatorAdapter.setSelectedPosition(this.currentPosition);
    }

    private void initLayout() {
        LayoutInflater.from(this.context).inflate(R.layout.ease_layout_chat_extend_menu, this);
        this.rvExtendMenu = (RecyclerView) findViewById(R.id.rv_extend_menu);
        this.rvIndicator = (RecyclerView) findViewById(R.id.rv_indicator);
    }

    private void sortByOrder(List<ChatMenuItemModel> list) {
        Collections.sort(list, new Comparator<ChatMenuItemModel>() { // from class: com.hyphenate.easeui.modules.chat.EaseChatExtendMenu.1
            @Override // java.util.Comparator
            public int compare(ChatMenuItemModel chatMenuItemModel, ChatMenuItemModel chatMenuItemModel2) {
                int i2 = chatMenuItemModel.order - chatMenuItemModel2.order;
                if (i2 > 0) {
                    return 1;
                }
                return i2 == 0 ? 0 : -1;
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void clear() {
        this.itemModels.clear();
        this.itemMap.clear();
        this.adapter.notifyDataSetChanged();
        this.indicatorAdapter.setPageCount(0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PagingScrollHelper pagingScrollHelper = this.helper;
        if (pagingScrollHelper == null || this.rvExtendMenu == null) {
            return;
        }
        pagingScrollHelper.scrollToPosition(0);
        this.helper.checkCurrentStatus();
    }

    @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
    public void onItemClick(View view, int i2) {
        ChatMenuItemModel chatMenuItemModel = this.itemModels.get(i2);
        EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener = this.itemListener;
        if (easeChatExtendMenuItemClickListener != null) {
            easeChatExtendMenuItemClickListener.onChatExtendMenuItemClick(chatMenuItemModel.id, view);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.onPageChangeListener
    public void onPageChange(int i2) {
        this.currentPosition = i2;
        this.indicatorAdapter.setSelectedPosition(i2);
    }

    public void registerMenuItem(String str, int i2, int i3, EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener) {
        if (this.itemMap.containsKey(Integer.valueOf(i3))) {
            return;
        }
        ChatMenuItemModel chatMenuItemModel = new ChatMenuItemModel();
        chatMenuItemModel.name = str;
        chatMenuItemModel.image = i2;
        chatMenuItemModel.id = i3;
        chatMenuItemModel.clickListener = easeChatExtendMenuItemClickListener;
        this.itemMap.put(Integer.valueOf(i3), chatMenuItemModel);
        this.itemModels.add(chatMenuItemModel);
        this.adapter.notifyItemInserted(this.itemModels.size() - 1);
        this.indicatorAdapter.setPageCount((int) Math.ceil((this.itemModels.size() * 1.0f) / (this.numColumns * this.numRows)));
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void setEaseChatExtendMenuItemClickListener(EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener) {
        this.itemListener = easeChatExtendMenuItemClickListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void setMenuOrder(int i2, int i3) {
        ChatMenuItemModel chatMenuItemModel;
        if (!this.itemMap.containsKey(Integer.valueOf(i2)) || (chatMenuItemModel = this.itemMap.get(Integer.valueOf(i2))) == null) {
            return;
        }
        chatMenuItemModel.order = i3;
        sortByOrder(this.itemModels);
        this.adapter.notifyDataSetChanged();
    }

    public EaseChatExtendMenu(Context context, AttributeSet attributeSet, boolean z2) {
        this(context, attributeSet, 0, z2);
    }

    public EaseChatExtendMenu(Context context, AttributeSet attributeSet, int i2, boolean z2) {
        super(context, attributeSet, i2);
        this.itemModels = new ArrayList();
        this.itemMap = new HashMap();
        this.itemStrings = new int[]{R.string.attach_take_pic, R.string.attach_picture, R.string.attach_video, R.string.attach_file};
        this.itemdrawables = new int[]{R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector, R.drawable.em_chat_video_selector, R.drawable.em_chat_file_selector};
        this.itemIds = new int[]{R.id.extend_item_take_picture, R.id.extend_item_picture, R.id.extend_item_video, R.id.extend_item_file};
        init(context, attributeSet);
        initLayout();
    }

    public void init() {
        initChatExtendMenu();
        initChatExtendMenuIndicator();
        addDefaultData();
    }

    public void registerMenuItem(String str, int i2, int i3, int i4, EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener) {
        if (this.itemMap.containsKey(Integer.valueOf(i3))) {
            return;
        }
        ChatMenuItemModel chatMenuItemModel = new ChatMenuItemModel();
        chatMenuItemModel.name = str;
        chatMenuItemModel.image = i2;
        chatMenuItemModel.id = i3;
        chatMenuItemModel.order = i4;
        chatMenuItemModel.clickListener = easeChatExtendMenuItemClickListener;
        this.itemMap.put(Integer.valueOf(i3), chatMenuItemModel);
        this.itemModels.add(chatMenuItemModel);
        sortByOrder(this.itemModels);
        this.adapter.notifyDataSetChanged();
        this.indicatorAdapter.setPageCount((int) Math.ceil((this.itemModels.size() * 1.0f) / (this.numColumns * this.numRows)));
    }

    public void registerMenuItem(int i2, int i3, int i4, EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener) {
        registerMenuItem(this.context.getString(i2), i3, i4, easeChatExtendMenuItemClickListener);
    }

    public void registerMenuItem(int i2, int i3, int i4, int i5, EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener) {
        registerMenuItem(this.context.getString(i2), i3, i4, i5, easeChatExtendMenuItemClickListener);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void registerMenuItem(String str, int i2, int i3) {
        registerMenuItem(str, i2, i3, (EaseChatExtendMenuItemClickListener) null);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void registerMenuItem(String str, int i2, int i3, int i4) {
        registerMenuItem(str, i2, i3, i4, (EaseChatExtendMenuItemClickListener) null);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void registerMenuItem(int i2, int i3, int i4) {
        registerMenuItem(i2, i3, i4, (EaseChatExtendMenuItemClickListener) null);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu
    public void registerMenuItem(int i2, int i3, int i4, int i5) {
        registerMenuItem(i2, i3, i4, i5, (EaseChatExtendMenuItemClickListener) null);
    }
}
