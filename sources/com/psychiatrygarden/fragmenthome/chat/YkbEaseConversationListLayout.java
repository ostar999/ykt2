package com.psychiatrygarden.fragmenthome.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseAdapterDelegate;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.interfaces.OnItemLongClickListener;
import com.hyphenate.easeui.modules.EaseBaseLayout;
import com.hyphenate.easeui.modules.conversation.adapter.EaseConversationListAdapter;
import com.hyphenate.easeui.modules.conversation.delegate.EaseBaseConversationDelegate;
import com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout;
import com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter;
import com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenterImpl;
import com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView;
import com.hyphenate.easeui.modules.interfaces.IPopupMenu;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.modules.menu.OnPopupMenuDismissListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.easeui.widget.EaseRecyclerView;
import com.psychiatrygarden.utils.NewToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class YkbEaseConversationListLayout extends EaseBaseLayout implements IConversationListLayout, IConversationStyle, IEaseConversationListView, IPopupMenu {
    private ConcatAdapter adapter;
    private OnConversationChangeListener conversationChangeListener;
    private OnPopupMenuDismissListener dismissListener;
    private OnItemClickListener itemListener;
    private OnItemLongClickListener itemLongListener;
    private EaseConversationListAdapter listAdapter;
    private OnConversationLoadListener loadListener;
    private EasePopupMenuHelper menuHelper;
    private OnPopupMenuPreShowListener menuPreShowListener;
    private OnPopupMenuItemClickListener popupMenuItemClickListener;
    private EaseConversationPresenter presenter;
    private EaseRecyclerView rvConversationList;
    private EaseConversationSetStyle setModel;
    private boolean showDefaultMenu;
    private float touchX;
    private float touchY;

    public YkbEaseConversationListLayout(Context context) {
        this(context, null);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.EaseConversationListLayout);
            this.setModel.setTitleTextSize(typedArrayObtainStyledAttributes.getDimension(16, EaseBaseLayout.sp2px(context, 16.0f)));
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(15, -1);
            this.setModel.setTitleTextColor(resourceId != -1 ? ContextCompat.getColor(context, resourceId) : typedArrayObtainStyledAttributes.getColor(15, ContextCompat.getColor(context, com.yikaobang.yixue.R.color.ease_conversation_color_item_name)));
            this.setModel.setContentTextSize(typedArrayObtainStyledAttributes.getDimension(8, EaseBaseLayout.sp2px(context, 14.0f)));
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(7, -1);
            this.setModel.setContentTextColor(resourceId2 != -1 ? ContextCompat.getColor(context, resourceId2) : typedArrayObtainStyledAttributes.getColor(7, ContextCompat.getColor(context, com.yikaobang.yixue.R.color.ease_conversation_color_item_message)));
            this.setModel.setDateTextSize(typedArrayObtainStyledAttributes.getDimension(10, EaseBaseLayout.sp2px(context, 13.0f)));
            int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(9, -1);
            this.setModel.setDateTextColor(resourceId3 != -1 ? ContextCompat.getColor(context, resourceId3) : typedArrayObtainStyledAttributes.getColor(9, ContextCompat.getColor(context, com.yikaobang.yixue.R.color.ease_conversation_color_item_time)));
            this.setModel.setMentionTextSize(typedArrayObtainStyledAttributes.getDimension(13, EaseBaseLayout.sp2px(context, 14.0f)));
            int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(12, -1);
            this.setModel.setMentionTextColor(resourceId4 != -1 ? ContextCompat.getColor(context, resourceId4) : typedArrayObtainStyledAttributes.getColor(12, ContextCompat.getColor(context, com.yikaobang.yixue.R.color.ease_conversation_color_item_mention)));
            float dimension = typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
            int integer = typedArrayObtainStyledAttributes.getInteger(3, -1);
            float dimension2 = typedArrayObtainStyledAttributes.getDimension(2, EaseBaseLayout.dip2px(context, 50.0f));
            float dimension3 = typedArrayObtainStyledAttributes.getDimension(1, 0.0f);
            int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(0, -1);
            int color = resourceId5 != -1 ? ContextCompat.getColor(context, resourceId5) : typedArrayObtainStyledAttributes.getColor(0, 0);
            this.setModel.setAvatarSize(dimension);
            this.setModel.setShapeType(integer);
            this.setModel.setAvatarRadius(dimension2);
            this.setModel.setBorderWidth(dimension3);
            this.setModel.setBorderColor(color);
            float dimension4 = typedArrayObtainStyledAttributes.getDimension(11, EaseBaseLayout.dip2px(context, 75.0f));
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(5);
            this.setModel.setItemHeight(dimension4);
            this.setModel.setBgDrawable(drawable);
            this.setModel.setUnreadDotPosition(typedArrayObtainStyledAttributes.getInteger(17, 0) == 0 ? EaseConversationSetStyle.UnreadDotPosition.LEFT : EaseConversationSetStyle.UnreadDotPosition.RIGHT);
            boolean z2 = typedArrayObtainStyledAttributes.getBoolean(14, true);
            this.setModel.setShowSystemMessage(z2);
            this.presenter.setShowSystemMessage(z2);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void initListener() {
        this.listAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.chat.e
            @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                this.f15519c.lambda$initListener$0(view, i2);
            }
        });
        this.listAdapter.setOnItemLongClickListener(new OnItemLongClickListener() { // from class: com.psychiatrygarden.fragmenthome.chat.f
            @Override // com.hyphenate.easeui.interfaces.OnItemLongClickListener
            public final boolean onItemLongClick(View view, int i2) {
                return this.f15520a.lambda$initListener$1(view, i2);
            }
        });
    }

    private void initViews() {
        this.presenter.attachView(this);
        EaseRecyclerView easeRecyclerView = (EaseRecyclerView) findViewById(com.yikaobang.yixue.R.id.rv_conversation_list);
        this.rvConversationList = easeRecyclerView;
        easeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new ConcatAdapter(new ConcatAdapter.Config.Builder().setStableIdMode(ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS).build(), (RecyclerView.Adapter<? extends RecyclerView.ViewHolder>[]) new RecyclerView.Adapter[0]);
        EaseConversationListAdapter easeConversationListAdapter = new EaseConversationListAdapter();
        this.listAdapter = easeConversationListAdapter;
        easeConversationListAdapter.setHasStableIds(true);
        this.adapter.addAdapter(this.listAdapter);
        this.menuHelper = new EasePopupMenuHelper();
        initListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$0(View view, int i2) {
        OnItemClickListener onItemClickListener = this.itemListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initListener$1(View view, int i2) throws NoSuchFieldException, SecurityException {
        this.listAdapter.getItem(i2).setSelected(true);
        OnItemLongClickListener onItemLongClickListener = this.itemLongListener;
        if (onItemLongClickListener != null) {
            return onItemLongClickListener.onItemLongClick(view, i2);
        }
        if (!this.showDefaultMenu) {
            return false;
        }
        showDefaultMenu(view, i2, this.listAdapter.getItem(i2));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDefaultMenu$2(int i2, EaseConversationInfo easeConversationInfo, MenuItem menuItem, int i3) {
        OnPopupMenuItemClickListener onPopupMenuItemClickListener = this.popupMenuItemClickListener;
        if (onPopupMenuItemClickListener != null && onPopupMenuItemClickListener.onMenuItemClick(menuItem, i2)) {
            return true;
        }
        int itemId = menuItem.getItemId();
        if (itemId == com.yikaobang.yixue.R.id.action_con_make_read) {
            this.presenter.makeConversionRead(i2, easeConversationInfo);
            return true;
        }
        if (itemId == com.yikaobang.yixue.R.id.action_con_make_top) {
            this.presenter.makeConversationTop(i2, easeConversationInfo);
            return true;
        }
        if (itemId == com.yikaobang.yixue.R.id.action_con_cancel_top) {
            this.presenter.cancelConversationTop(i2, easeConversationInfo);
            return true;
        }
        if (itemId != com.yikaobang.yixue.R.id.action_con_delete) {
            return false;
        }
        this.presenter.deleteConversation(i2, easeConversationInfo);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDefaultMenu$3(EaseConversationInfo easeConversationInfo, PopupMenu popupMenu) {
        easeConversationInfo.setSelected(false);
        OnPopupMenuDismissListener onPopupMenuDismissListener = this.dismissListener;
        if (onPopupMenuDismissListener != null) {
            onPopupMenuDismissListener.onDismiss(popupMenu);
        }
    }

    private void showDefaultMenu(View view, final int position, final EaseConversationInfo info) throws NoSuchFieldException, SecurityException {
        this.menuHelper.addItemMenu(0, com.yikaobang.yixue.R.id.action_con_make_read, 0, getContext().getString(com.yikaobang.yixue.R.string.ease_conversation_menu_make_read));
        this.menuHelper.addItemMenu(0, com.yikaobang.yixue.R.id.action_con_make_top, 1, getContext().getString(com.yikaobang.yixue.R.string.ease_conversation_menu_make_top));
        this.menuHelper.addItemMenu(0, com.yikaobang.yixue.R.id.action_con_cancel_top, 2, getContext().getString(com.yikaobang.yixue.R.string.ease_conversation_menu_cancel_top));
        this.menuHelper.addItemMenu(0, com.yikaobang.yixue.R.id.action_con_delete, 3, getContext().getString(com.yikaobang.yixue.R.string.ease_conversation_menu_delete));
        this.menuHelper.initMenu(view, getContext());
        this.menuHelper.findItemVisible(com.yikaobang.yixue.R.id.action_con_make_top, !info.isTop());
        this.menuHelper.findItemVisible(com.yikaobang.yixue.R.id.action_con_cancel_top, info.isTop());
        if (info.getInfo() instanceof EMConversation) {
            this.menuHelper.findItemVisible(com.yikaobang.yixue.R.id.action_con_make_read, ((EMConversation) info.getInfo()).getUnreadMsgCount() > 0);
        }
        this.menuHelper.findItemVisible(com.yikaobang.yixue.R.id.action_con_make_read, false);
        OnPopupMenuPreShowListener onPopupMenuPreShowListener = this.menuPreShowListener;
        if (onPopupMenuPreShowListener != null) {
            onPopupMenuPreShowListener.onMenuPreShow(this.menuHelper, position);
        }
        this.menuHelper.setOnPopupMenuItemClickListener(new OnPopupMenuItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.chat.g
            @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem, int i2) {
                return this.f15521c.lambda$showDefaultMenu$2(position, info, menuItem, i2);
            }
        });
        this.menuHelper.setOnPopupMenuDismissListener(new OnPopupMenuDismissListener() { // from class: com.psychiatrygarden.fragmenthome.chat.h
            @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuDismissListener
            public final void onDismiss(PopupMenu popupMenu) {
                this.f15524a.lambda$showDefaultMenu$3(info, popupMenu);
            }
        });
        this.menuHelper.show(getContext(), (int) getTouchX(), 0);
    }

    public void addData(List<EaseConversationInfo> data) {
        if (data != null) {
            List<EaseConversationInfo> data2 = this.listAdapter.getData();
            data2.addAll(data);
            this.presenter.sortData(data2);
        }
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addFooterAdapter(RecyclerView.Adapter adapter) {
        this.adapter.addAdapter(adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addHeaderAdapter(RecyclerView.Adapter adapter) {
        this.adapter.addAdapter(0, adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void addItemMenu(int groupId, int itemId, int order, String title) {
        this.menuHelper.addItemMenu(groupId, itemId, order, title);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addRVItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        this.rvConversationList.addItemDecoration(decor);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void cancelConversationTop(int position, EaseConversationInfo info) {
        this.presenter.cancelConversationTop(position, info);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void clearMenu() {
        this.menuHelper.clear();
    }

    @Override // com.hyphenate.easeui.modules.ILoadDataView
    public Context context() {
        return getContext();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void deleteConversation(int position, EaseConversationInfo info) {
        this.presenter.deleteConversation(position, info);
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void deleteItem(int position) {
        if (this.listAdapter.getData() == null) {
            return;
        }
        OnConversationChangeListener onConversationChangeListener = this.conversationChangeListener;
        if (onConversationChangeListener != null) {
            onConversationChangeListener.notifyItemRemove(position);
        }
        try {
            this.listAdapter.getData().remove(position);
            this.listAdapter.notifyItemRemoved(position);
            this.listAdapter.notifyItemChanged(position);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void deleteItemFail(int position, String message) {
        NewToast.showShort(getContext(), com.yikaobang.yixue.R.string.ease_conversation_delete_item_fail, 0).show();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.touchX = ev.getX();
        this.touchY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void findItemVisible(int id, boolean visible) {
        this.menuHelper.findItemVisible(id, visible);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public EaseConversationInfo getItem(int position) {
        if (position < this.listAdapter.getData().size()) {
            return this.listAdapter.getItem(position);
        }
        throw new ArrayIndexOutOfBoundsException(position);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public EaseConversationListAdapter getListAdapter() {
        return this.listAdapter;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public EasePopupMenuHelper getMenuHelper() {
        return this.menuHelper;
    }

    public float getTouchX() {
        return this.touchX;
    }

    public float getTouchY() {
        return this.touchY;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void hideUnreadDot(boolean hide) {
        this.setModel.setHideUnreadDot(hide);
        notifyDataSetChanged();
    }

    public void init() {
        this.listAdapter.addDelegate(new YkbEaseSystemMsgDelegate(this.setModel));
        this.listAdapter.addDelegate(new YkbEaseConversationDelegate(this.setModel));
        this.rvConversationList.setAdapter(this.adapter);
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void loadConversationListFail(String message) {
        OnConversationLoadListener onConversationLoadListener = this.loadListener;
        if (onConversationLoadListener != null) {
            onConversationLoadListener.loadDataFail(message);
        }
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void loadConversationListNoData() {
        OnConversationLoadListener onConversationLoadListener = this.loadListener;
        if (onConversationLoadListener != null) {
            onConversationLoadListener.loadDataFinish(new ArrayList());
        }
        this.listAdapter.setData(new ArrayList());
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void loadConversationListSuccess(List<EaseConversationInfo> data) {
        this.presenter.sortData(data);
    }

    public void loadDefaultData() {
        this.presenter.loadData();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void makeConversationTop(int position, EaseConversationInfo info) {
        this.presenter.makeConversationTop(position, info);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void makeConversionRead(int position, EaseConversationInfo info) {
        this.presenter.makeConversionRead(position, info);
    }

    public void notifyDataSetChanged() {
        EaseConversationListAdapter easeConversationListAdapter = this.listAdapter;
        if (easeConversationListAdapter != null) {
            List<EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder>> allDelegate = easeConversationListAdapter.getAllDelegate();
            if (allDelegate != null && !allDelegate.isEmpty()) {
                for (int i2 = 0; i2 < allDelegate.size(); i2++) {
                    ((EaseBaseConversationDelegate) allDelegate.get(i2)).setSetModel(this.setModel);
                }
            }
            this.listAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void refreshList() {
        OnConversationChangeListener onConversationChangeListener = this.conversationChangeListener;
        if (onConversationChangeListener != null) {
            onConversationChangeListener.notifyAllChange();
        }
        this.presenter.sortData(this.listAdapter.getData());
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void removeAdapter(RecyclerView.Adapter adapter) {
        this.adapter.removeAdapter(adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void removeRVItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        this.rvConversationList.removeItemDecoration(decor);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarBorderColor(int borderColor) {
        this.setModel.setBorderColor(borderColor);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarBorderWidth(int borderWidth) {
        this.setModel.setBorderWidth(borderWidth);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public /* synthetic */ void setAvatarDefaultSrc(Drawable drawable) {
        f1.a.a(this, drawable);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarRadius(int radius) {
        this.setModel.setAvatarRadius(radius);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarShapeType(EaseImageView.ShapeType shapeType) {
        this.setModel.setShapeType(shapeType);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarSize(float avatarSize) {
        this.setModel.setAvatarSize(avatarSize);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setContentTextColor(int textColor) {
        this.setModel.setContentTextColor(textColor);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setContentTextSize(int textSize) {
        this.setModel.setContentTextSize(textSize);
        notifyDataSetChanged();
    }

    public void setData(List<EaseConversationInfo> data) {
        this.presenter.sortData(data);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setDateTextColor(int textColor) {
        this.setModel.setDateTextColor(textColor);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setDateTextSize(int textSize) {
        this.setModel.setDateTextSize(textSize);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void setItemBackGround(Drawable backGround) {
        this.setModel.setBgDrawable(backGround);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void setItemHeight(int height) {
        this.setModel.setItemHeight(height);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void setOnConversationChangeListener(OnConversationChangeListener listener) {
        this.conversationChangeListener = listener;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void setOnConversationLoadListener(OnConversationLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemListener = listener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongListener = listener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void setOnPopupMenuDismissListener(OnPopupMenuDismissListener listener) {
        this.dismissListener = listener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void setOnPopupMenuItemClickListener(OnPopupMenuItemClickListener listener) {
        this.popupMenuItemClickListener = listener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void setOnPopupMenuPreShowListener(OnPopupMenuPreShowListener preShowListener) {
        this.menuPreShowListener = preShowListener;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void setPresenter(EaseConversationPresenter presenter) {
        this.presenter = presenter;
        if (getContext() instanceof AppCompatActivity) {
            ((AppCompatActivity) getContext()).getLifecycle().addObserver(presenter);
        }
        this.presenter.setShowSystemMessage(this.setModel.isShowSystemMessage());
        this.presenter.attachView(this);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setTitleTextColor(int textColor) {
        this.setModel.setTitleTextColor(textColor);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setTitleTextSize(int textSize) {
        this.setModel.setTitleTextSize(textSize);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void showItemDefaultMenu(boolean showDefault) {
        this.showDefaultMenu = showDefault;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void showSystemMessage(boolean show) {
        this.setModel.setShowSystemMessage(show);
        this.presenter.setShowSystemMessage(show);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void showUnreadDotPosition(EaseConversationSetStyle.UnreadDotPosition position) {
        this.setModel.setUnreadDotPosition(position);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void sortConversationListSuccess(List<EaseConversationInfo> data) {
        OnConversationLoadListener onConversationLoadListener = this.loadListener;
        if (onConversationLoadListener != null) {
            onConversationLoadListener.loadDataFinish(data);
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (((EMConversation) data.get(i2).getInfo()).getType() != EMConversation.EMConversationType.GroupChat) {
                arrayList.add(data.get(i2));
            }
        }
        this.listAdapter.setData(arrayList);
    }

    public YkbEaseConversationListLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YkbEaseConversationListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.showDefaultMenu = true;
        this.setModel = new EaseConversationSetStyle();
        LayoutInflater.from(context).inflate(com.yikaobang.yixue.R.layout.ease_conversation_list, this);
        this.presenter = new EaseConversationPresenterImpl();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this.presenter);
        }
        initAttrs(context, attrs);
        initViews();
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void refreshList(int position) {
        OnConversationChangeListener onConversationChangeListener = this.conversationChangeListener;
        if (onConversationChangeListener != null) {
            onConversationChangeListener.notifyItemChange(position);
        }
        this.listAdapter.notifyItemChanged(position);
    }
}
