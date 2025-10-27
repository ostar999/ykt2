package com.hyphenate.easeui.modules.conversation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
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
import com.hyphenate.easeui.modules.conversation.delegate.EaseConversationDelegate;
import com.hyphenate.easeui.modules.conversation.delegate.EaseSystemMsgDelegate;
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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseConversationListLayout extends EaseBaseLayout implements IConversationListLayout, IConversationStyle, IEaseConversationListView, IPopupMenu {
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

    public EaseConversationListLayout(Context context) {
        this(context, null);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseConversationListLayout);
            this.setModel.setTitleTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_title_text_size, EaseBaseLayout.sp2px(context, 16.0f)));
            int i2 = R.styleable.EaseConversationListLayout_ease_con_item_title_text_color;
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(i2, -1);
            this.setModel.setTitleTextColor(resourceId != -1 ? ContextCompat.getColor(context, resourceId) : typedArrayObtainStyledAttributes.getColor(i2, ContextCompat.getColor(context, R.color.ease_conversation_color_item_name)));
            this.setModel.setContentTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_content_text_size, EaseBaseLayout.sp2px(context, 14.0f)));
            int i3 = R.styleable.EaseConversationListLayout_ease_con_item_content_text_color;
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(i3, -1);
            this.setModel.setContentTextColor(resourceId2 != -1 ? ContextCompat.getColor(context, resourceId2) : typedArrayObtainStyledAttributes.getColor(i3, ContextCompat.getColor(context, R.color.ease_conversation_color_item_message)));
            this.setModel.setDateTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_date_text_size, EaseBaseLayout.sp2px(context, 13.0f)));
            int i4 = R.styleable.EaseConversationListLayout_ease_con_item_date_text_color;
            int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(i4, -1);
            this.setModel.setDateTextColor(resourceId3 != -1 ? ContextCompat.getColor(context, resourceId3) : typedArrayObtainStyledAttributes.getColor(i4, ContextCompat.getColor(context, R.color.ease_conversation_color_item_time)));
            this.setModel.setMentionTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_mention_text_size, EaseBaseLayout.sp2px(context, 14.0f)));
            int i5 = R.styleable.EaseConversationListLayout_ease_con_item_mention_text_color;
            int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(i5, -1);
            this.setModel.setMentionTextColor(resourceId4 != -1 ? ContextCompat.getColor(context, resourceId4) : typedArrayObtainStyledAttributes.getColor(i5, ContextCompat.getColor(context, R.color.ease_conversation_color_item_mention)));
            float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_avatar_size, 0.0f);
            int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseConversationListLayout_ease_con_item_avatar_shape_type, -1);
            float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_avatar_radius, EaseBaseLayout.dip2px(context, 50.0f));
            float dimension3 = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_avatar_border_width, 0.0f);
            int i6 = R.styleable.EaseConversationListLayout_ease_con_item_avatar_border_color;
            int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(i6, -1);
            int color = resourceId5 != -1 ? ContextCompat.getColor(context, resourceId5) : typedArrayObtainStyledAttributes.getColor(i6, 0);
            this.setModel.setAvatarSize(dimension);
            this.setModel.setShapeType(integer);
            this.setModel.setAvatarRadius(dimension2);
            this.setModel.setBorderWidth(dimension3);
            this.setModel.setBorderColor(color);
            float dimension4 = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseConversationListLayout_ease_con_item_height, EaseBaseLayout.dip2px(context, 75.0f));
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseConversationListLayout_ease_con_item_background);
            this.setModel.setItemHeight(dimension4);
            this.setModel.setBgDrawable(drawable);
            this.setModel.setUnreadDotPosition(typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseConversationListLayout_ease_con_item_unread_dot_position, 0) == 0 ? EaseConversationSetStyle.UnreadDotPosition.LEFT : EaseConversationSetStyle.UnreadDotPosition.RIGHT);
            boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EaseConversationListLayout_ease_con_item_show_system_message, true);
            this.setModel.setShowSystemMessage(z2);
            this.presenter.setShowSystemMessage(z2);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void initListener() {
        this.listAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.hyphenate.easeui.modules.conversation.EaseConversationListLayout.1
            @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
            public void onItemClick(View view, int i2) {
                if (EaseConversationListLayout.this.itemListener != null) {
                    EaseConversationListLayout.this.itemListener.onItemClick(view, i2);
                }
            }
        });
        this.listAdapter.setOnItemLongClickListener(new OnItemLongClickListener() { // from class: com.hyphenate.easeui.modules.conversation.EaseConversationListLayout.2
            @Override // com.hyphenate.easeui.interfaces.OnItemLongClickListener
            public boolean onItemLongClick(View view, int i2) throws NoSuchFieldException, SecurityException {
                EaseConversationListLayout.this.listAdapter.getItem(i2).setSelected(true);
                if (EaseConversationListLayout.this.itemLongListener != null) {
                    return EaseConversationListLayout.this.itemLongListener.onItemLongClick(view, i2);
                }
                if (!EaseConversationListLayout.this.showDefaultMenu) {
                    return false;
                }
                EaseConversationListLayout easeConversationListLayout = EaseConversationListLayout.this;
                easeConversationListLayout.showDefaultMenu(view, i2, easeConversationListLayout.listAdapter.getItem(i2));
                return true;
            }
        });
    }

    private void initViews() {
        this.presenter.attachView(this);
        EaseRecyclerView easeRecyclerView = (EaseRecyclerView) findViewById(R.id.rv_conversation_list);
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
    public void showDefaultMenu(View view, final int i2, final EaseConversationInfo easeConversationInfo) throws NoSuchFieldException, SecurityException {
        EasePopupMenuHelper easePopupMenuHelper = this.menuHelper;
        int i3 = R.id.action_con_make_read;
        easePopupMenuHelper.addItemMenu(0, i3, 0, getContext().getString(R.string.ease_conversation_menu_make_read));
        EasePopupMenuHelper easePopupMenuHelper2 = this.menuHelper;
        int i4 = R.id.action_con_make_top;
        easePopupMenuHelper2.addItemMenu(0, i4, 1, getContext().getString(R.string.ease_conversation_menu_make_top));
        EasePopupMenuHelper easePopupMenuHelper3 = this.menuHelper;
        int i5 = R.id.action_con_cancel_top;
        easePopupMenuHelper3.addItemMenu(0, i5, 2, getContext().getString(R.string.ease_conversation_menu_cancel_top));
        this.menuHelper.addItemMenu(0, R.id.action_con_delete, 3, getContext().getString(R.string.ease_conversation_menu_delete));
        this.menuHelper.initMenu(view, getContext());
        this.menuHelper.findItemVisible(i4, !easeConversationInfo.isTop());
        this.menuHelper.findItemVisible(i5, easeConversationInfo.isTop());
        if (easeConversationInfo.getInfo() instanceof EMConversation) {
            this.menuHelper.findItemVisible(i3, ((EMConversation) easeConversationInfo.getInfo()).getUnreadMsgCount() > 0);
        }
        this.menuHelper.findItemVisible(i3, false);
        OnPopupMenuPreShowListener onPopupMenuPreShowListener = this.menuPreShowListener;
        if (onPopupMenuPreShowListener != null) {
            onPopupMenuPreShowListener.onMenuPreShow(this.menuHelper, i2);
        }
        this.menuHelper.setOnPopupMenuItemClickListener(new OnPopupMenuItemClickListener() { // from class: com.hyphenate.easeui.modules.conversation.EaseConversationListLayout.3
            @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem, int i6) {
                if (EaseConversationListLayout.this.popupMenuItemClickListener != null && EaseConversationListLayout.this.popupMenuItemClickListener.onMenuItemClick(menuItem, i2)) {
                    return true;
                }
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_con_make_read) {
                    EaseConversationListLayout.this.presenter.makeConversionRead(i2, easeConversationInfo);
                    return true;
                }
                if (itemId == R.id.action_con_make_top) {
                    EaseConversationListLayout.this.presenter.makeConversationTop(i2, easeConversationInfo);
                    return true;
                }
                if (itemId == R.id.action_con_cancel_top) {
                    EaseConversationListLayout.this.presenter.cancelConversationTop(i2, easeConversationInfo);
                    return true;
                }
                if (itemId != R.id.action_con_delete) {
                    return false;
                }
                EaseConversationListLayout.this.presenter.deleteConversation(i2, easeConversationInfo);
                return true;
            }
        });
        this.menuHelper.setOnPopupMenuDismissListener(new OnPopupMenuDismissListener() { // from class: com.hyphenate.easeui.modules.conversation.EaseConversationListLayout.4
            @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuDismissListener
            public void onDismiss(PopupMenu popupMenu) {
                easeConversationInfo.setSelected(false);
                if (EaseConversationListLayout.this.dismissListener != null) {
                    EaseConversationListLayout.this.dismissListener.onDismiss(popupMenu);
                }
            }
        });
        this.menuHelper.show(getContext(), (int) getTouchX(), 0);
    }

    public void addData(List<EaseConversationInfo> list) {
        if (list != null) {
            List<EaseConversationInfo> data = this.listAdapter.getData();
            data.addAll(list);
            this.presenter.sortData(data);
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
    public void addItemMenu(int i2, int i3, int i4, String str) {
        this.menuHelper.addItemMenu(i2, i3, i4, str);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.rvConversationList.addItemDecoration(itemDecoration);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void cancelConversationTop(int i2, EaseConversationInfo easeConversationInfo) {
        this.presenter.cancelConversationTop(i2, easeConversationInfo);
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
    public void deleteConversation(int i2, EaseConversationInfo easeConversationInfo) {
        this.presenter.deleteConversation(i2, easeConversationInfo);
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void deleteItem(int i2) {
        if (this.listAdapter.getData() == null) {
            return;
        }
        OnConversationChangeListener onConversationChangeListener = this.conversationChangeListener;
        if (onConversationChangeListener != null) {
            onConversationChangeListener.notifyItemRemove(i2);
        }
        try {
            this.listAdapter.getData().remove(i2);
            this.listAdapter.notifyItemRemoved(i2);
            this.listAdapter.notifyItemChanged(i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void deleteItemFail(int i2, String str) {
        Toast.makeText(getContext(), R.string.ease_conversation_delete_item_fail, 0).show();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.touchX = motionEvent.getX();
        this.touchY = motionEvent.getY();
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void findItemVisible(int i2, boolean z2) {
        this.menuHelper.findItemVisible(i2, z2);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public EaseConversationInfo getItem(int i2) {
        if (i2 < this.listAdapter.getData().size()) {
            return this.listAdapter.getItem(i2);
        }
        throw new ArrayIndexOutOfBoundsException(i2);
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
    public void hideUnreadDot(boolean z2) {
        this.setModel.setHideUnreadDot(z2);
        notifyDataSetChanged();
    }

    public void init() {
        this.listAdapter.addDelegate(new EaseSystemMsgDelegate(this.setModel));
        this.listAdapter.addDelegate(new EaseConversationDelegate(this.setModel));
        this.rvConversationList.setAdapter(this.adapter);
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void loadConversationListFail(String str) {
        OnConversationLoadListener onConversationLoadListener = this.loadListener;
        if (onConversationLoadListener != null) {
            onConversationLoadListener.loadDataFail(str);
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
    public void loadConversationListSuccess(List<EaseConversationInfo> list) {
        this.presenter.sortData(list);
    }

    public void loadDefaultData() {
        this.presenter.loadData();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void makeConversationTop(int i2, EaseConversationInfo easeConversationInfo) {
        this.presenter.makeConversationTop(i2, easeConversationInfo);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void makeConversionRead(int i2, EaseConversationInfo easeConversationInfo) {
        this.presenter.makeConversionRead(i2, easeConversationInfo);
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
    public void removeRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.rvConversationList.removeItemDecoration(itemDecoration);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarBorderColor(int i2) {
        this.setModel.setBorderColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarBorderWidth(int i2) {
        this.setModel.setBorderWidth(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public /* synthetic */ void setAvatarDefaultSrc(Drawable drawable) {
        f1.a.a(this, drawable);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarRadius(int i2) {
        this.setModel.setAvatarRadius(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarShapeType(EaseImageView.ShapeType shapeType) {
        this.setModel.setShapeType(shapeType);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarSize(float f2) {
        this.setModel.setAvatarSize(f2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setContentTextColor(int i2) {
        this.setModel.setContentTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setContentTextSize(int i2) {
        this.setModel.setContentTextSize(i2);
        notifyDataSetChanged();
    }

    public void setData(List<EaseConversationInfo> list) {
        this.presenter.sortData(list);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setDateTextColor(int i2) {
        this.setModel.setDateTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setDateTextSize(int i2) {
        this.setModel.setDateTextSize(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void setItemBackGround(Drawable drawable) {
        this.setModel.setBgDrawable(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void setItemHeight(int i2) {
        this.setModel.setItemHeight(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void setOnConversationChangeListener(OnConversationChangeListener onConversationChangeListener) {
        this.conversationChangeListener = onConversationChangeListener;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void setOnConversationLoadListener(OnConversationLoadListener onConversationLoadListener) {
        this.loadListener = onConversationLoadListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemListener = onItemClickListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.itemLongListener = onItemLongClickListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void setOnPopupMenuDismissListener(OnPopupMenuDismissListener onPopupMenuDismissListener) {
        this.dismissListener = onPopupMenuDismissListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void setOnPopupMenuItemClickListener(OnPopupMenuItemClickListener onPopupMenuItemClickListener) {
        this.popupMenuItemClickListener = onPopupMenuItemClickListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void setOnPopupMenuPreShowListener(OnPopupMenuPreShowListener onPopupMenuPreShowListener) {
        this.menuPreShowListener = onPopupMenuPreShowListener;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void setPresenter(EaseConversationPresenter easeConversationPresenter) {
        this.presenter = easeConversationPresenter;
        if (getContext() instanceof AppCompatActivity) {
            ((AppCompatActivity) getContext()).getLifecycle().addObserver(easeConversationPresenter);
        }
        this.presenter.setShowSystemMessage(this.setModel.isShowSystemMessage());
        this.presenter.attachView(this);
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setTitleTextColor(int i2) {
        this.setModel.setTitleTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationTextStyle
    public void setTitleTextSize(int i2) {
        this.setModel.setTitleTextSize(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationListLayout
    public void showItemDefaultMenu(boolean z2) {
        this.showDefaultMenu = z2;
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void showSystemMessage(boolean z2) {
        this.setModel.setShowSystemMessage(z2);
        this.presenter.setShowSystemMessage(z2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.IConversationStyle
    public void showUnreadDotPosition(EaseConversationSetStyle.UnreadDotPosition unreadDotPosition) {
        this.setModel.setUnreadDotPosition(unreadDotPosition);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void sortConversationListSuccess(List<EaseConversationInfo> list) {
        OnConversationLoadListener onConversationLoadListener = this.loadListener;
        if (onConversationLoadListener != null) {
            onConversationLoadListener.loadDataFinish(list);
        }
        this.listAdapter.setData(list);
    }

    public EaseConversationListLayout(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseConversationListLayout(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.showDefaultMenu = true;
        this.setModel = new EaseConversationSetStyle();
        LayoutInflater.from(context).inflate(R.layout.ease_conversation_list, this);
        this.presenter = new EaseConversationPresenterImpl();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this.presenter);
        }
        initAttrs(context, attributeSet);
        initViews();
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.IEaseConversationListView
    public void refreshList(int i2) {
        OnConversationChangeListener onConversationChangeListener = this.conversationChangeListener;
        if (onConversationChangeListener != null) {
            onConversationChangeListener.notifyItemChange(i2);
        }
        this.listAdapter.notifyItemChanged(i2);
    }
}
