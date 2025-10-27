package com.hyphenate.easeui.modules.contact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
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
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.interfaces.OnItemLongClickListener;
import com.hyphenate.easeui.modules.contact.adapter.EaseContactCustomAdapter;
import com.hyphenate.easeui.modules.contact.adapter.EaseContactListAdapter;
import com.hyphenate.easeui.modules.contact.interfaces.IContactCustomListLayout;
import com.hyphenate.easeui.modules.contact.interfaces.IContactListLayout;
import com.hyphenate.easeui.modules.contact.interfaces.IContactListStyle;
import com.hyphenate.easeui.modules.contact.interfaces.OnContactLoadListener;
import com.hyphenate.easeui.modules.contact.model.EaseContactSetStyle;
import com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenter;
import com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenterImpl;
import com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView;
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
public class EaseContactListLayout extends EaseRecyclerView implements IEaseContactListView, IContactListLayout, IContactCustomListLayout, IContactListStyle, IPopupMenu {
    private ConcatAdapter concatAdapter;
    private EaseContactSetStyle contactSetModel;
    private EaseContactCustomAdapter customAdapter;
    private OnItemClickListener customItemClickListener;
    private OnPopupMenuDismissListener dismissListener;
    private OnItemClickListener itemListener;
    private OnItemLongClickListener itemLongListener;
    private EaseContactListAdapter listAdapter;
    private OnContactLoadListener loadListener;
    private EasePopupMenuHelper menuHelper;
    private OnPopupMenuPreShowListener menuPreShowListener;
    private OnPopupMenuItemClickListener popupMenuItemClickListener;
    private EaseContactPresenter presenter;
    private boolean showDefaultMenu;
    private float touchX;
    private float touchY;

    public EaseContactListLayout(Context context) {
        this(context, null);
    }

    private void addHeader() {
        EaseContactCustomAdapter easeContactCustomAdapter = new EaseContactCustomAdapter();
        this.customAdapter = easeContactCustomAdapter;
        easeContactCustomAdapter.setSettingModel(this.contactSetModel);
        this.customAdapter.setEmptyView(R.layout.ease_layout_empty_list_wrap_content);
        this.concatAdapter.addAdapter(this.customAdapter);
    }

    public static float dip2px(Context context, float f2) {
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseContactListLayout);
            this.contactSetModel.setTitleTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseContactListLayout_ease_contact_item_title_text_size, sp2px(context, 16.0f)));
            int i2 = R.styleable.EaseContactListLayout_ease_contact_item_title_text_color;
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(i2, -1);
            this.contactSetModel.setTitleTextColor(resourceId != -1 ? ContextCompat.getColor(context, resourceId) : typedArrayObtainStyledAttributes.getColor(i2, ContextCompat.getColor(context, R.color.ease_contact_color_item_title)));
            this.contactSetModel.setHeaderTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseContactListLayout_ease_contact_item_header_text_size, sp2px(context, 16.0f)));
            int i3 = R.styleable.EaseContactListLayout_ease_contact_item_header_text_color;
            this.contactSetModel.setHeaderTextColor(typedArrayObtainStyledAttributes.getResourceId(i3, -1) != -1 ? ContextCompat.getColor(context, resourceId) : typedArrayObtainStyledAttributes.getColor(i3, ContextCompat.getColor(context, R.color.ease_contact_color_item_header)));
            this.contactSetModel.setHeaderBgDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseContactListLayout_ease_contact_item_header_background));
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseContactListLayout_ease_contact_item_avatar_default_src);
            float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseContactListLayout_ease_contact_item_avatar_size, 0.0f);
            int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseContactListLayout_ease_contact_item_avatar_shape_type, -1);
            float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseContactListLayout_ease_contact_item_avatar_radius, dip2px(context, 50.0f));
            float dimension3 = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseContactListLayout_ease_contact_item_avatar_border_width, 0.0f);
            int i4 = R.styleable.EaseContactListLayout_ease_contact_item_avatar_border_color;
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(i4, -1);
            int color = resourceId2 != -1 ? ContextCompat.getColor(context, resourceId2) : typedArrayObtainStyledAttributes.getColor(i4, 0);
            this.contactSetModel.setAvatarDefaultSrc(drawable);
            this.contactSetModel.setAvatarSize(dimension);
            this.contactSetModel.setShapeType(integer);
            this.contactSetModel.setAvatarRadius(dimension2);
            this.contactSetModel.setBorderWidth(dimension3);
            this.contactSetModel.setBorderColor(color);
            float dimension4 = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseContactListLayout_ease_contact_item_height, dip2px(context, 75.0f));
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseContactListLayout_ease_contact_item_background);
            this.contactSetModel.setItemHeight(dimension4);
            this.contactSetModel.setBgDrawable(drawable2);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void initListener() {
        this.listAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.hyphenate.easeui.modules.contact.EaseContactListLayout.1
            @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
            public void onItemClick(View view, int i2) {
                if (EaseContactListLayout.this.itemListener != null) {
                    EaseContactListLayout.this.itemListener.onItemClick(view, i2);
                }
            }
        });
        this.listAdapter.setOnItemLongClickListener(new OnItemLongClickListener() { // from class: com.hyphenate.easeui.modules.contact.EaseContactListLayout.2
            @Override // com.hyphenate.easeui.interfaces.OnItemLongClickListener
            public boolean onItemLongClick(View view, int i2) throws NoSuchFieldException, SecurityException {
                if (EaseContactListLayout.this.itemLongListener != null) {
                    return EaseContactListLayout.this.itemLongListener.onItemLongClick(view, i2);
                }
                if (!EaseContactListLayout.this.showDefaultMenu) {
                    return false;
                }
                EaseContactListLayout easeContactListLayout = EaseContactListLayout.this;
                easeContactListLayout.showDefaultMenu(view, i2, easeContactListLayout.listAdapter.getItem(i2));
                return true;
            }
        });
        this.customAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.hyphenate.easeui.modules.contact.EaseContactListLayout.3
            @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
            public void onItemClick(View view, int i2) {
                if (EaseContactListLayout.this.customItemClickListener != null) {
                    EaseContactListLayout.this.customItemClickListener.onItemClick(view, i2);
                }
            }
        });
    }

    private void initViews() {
        this.presenter.attachView(this);
        setLayoutManager(new LinearLayoutManager(getContext()));
        this.concatAdapter = new ConcatAdapter((RecyclerView.Adapter<? extends RecyclerView.ViewHolder>[]) new RecyclerView.Adapter[0]);
        EaseContactListAdapter easeContactListAdapter = new EaseContactListAdapter();
        this.listAdapter = easeContactListAdapter;
        easeContactListAdapter.setSettingModel(this.contactSetModel);
        addHeader();
        this.concatAdapter.addAdapter(this.listAdapter);
        setAdapter(this.concatAdapter);
        this.menuHelper = new EasePopupMenuHelper();
        initListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDefaultMenu(View view, final int i2, final EaseUser easeUser) throws NoSuchFieldException, SecurityException {
        EasePopupMenuHelper easePopupMenuHelper = this.menuHelper;
        int i3 = R.id.action_add_note;
        easePopupMenuHelper.addItemMenu(0, i3, 0, getContext().getString(R.string.ease_contact_menu_add_note));
        this.menuHelper.initMenu(view, getContext());
        this.menuHelper.findItemVisible(i3, false);
        OnPopupMenuPreShowListener onPopupMenuPreShowListener = this.menuPreShowListener;
        if (onPopupMenuPreShowListener != null) {
            onPopupMenuPreShowListener.onMenuPreShow(this.menuHelper, i2);
        }
        this.menuHelper.setOnPopupMenuItemClickListener(new OnPopupMenuItemClickListener() { // from class: com.hyphenate.easeui.modules.contact.EaseContactListLayout.4
            @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem, int i4) {
                if (EaseContactListLayout.this.popupMenuItemClickListener != null && EaseContactListLayout.this.popupMenuItemClickListener.onMenuItemClick(menuItem, i2)) {
                    return true;
                }
                if (menuItem.getItemId() != R.id.action_add_note) {
                    return false;
                }
                EaseContactListLayout.this.presenter.addNote(i2, easeUser);
                return true;
            }
        });
        this.menuHelper.setOnPopupMenuDismissListener(new OnPopupMenuDismissListener() { // from class: com.hyphenate.easeui.modules.contact.EaseContactListLayout.5
            @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuDismissListener
            public void onDismiss(PopupMenu popupMenu) {
                if (EaseContactListLayout.this.dismissListener != null) {
                    EaseContactListLayout.this.dismissListener.onDismiss(popupMenu);
                }
            }
        });
        this.menuHelper.show(getContext(), (int) getTouchX(), 0);
    }

    public static float sp2px(Context context, float f2) {
        return TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactCustomListLayout
    public void addCustomItem(int i2, int i3, String str) {
        EaseContactCustomAdapter easeContactCustomAdapter = this.customAdapter;
        if (easeContactCustomAdapter != null) {
            easeContactCustomAdapter.addItem(i2, i3, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addFooterAdapter(RecyclerView.Adapter adapter) {
        this.concatAdapter.addAdapter(adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addHeaderAdapter(RecyclerView.Adapter adapter) {
        this.concatAdapter.addAdapter(0, adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void addItemMenu(int i2, int i3, int i4, String str) {
        this.menuHelper.addItemMenu(i2, i3, i4, str);
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void addNote(int i2) {
        this.listAdapter.notifyItemChanged(i2);
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void addNoteFail(int i2, String str) {
        Toast.makeText(getContext(), str, 0).show();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        addItemDecoration(itemDecoration);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupMenu
    public void clearMenu() {
        this.menuHelper.clear();
    }

    @Override // com.hyphenate.easeui.modules.ILoadDataView
    public Context context() {
        return getContext();
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

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactCustomListLayout
    public EaseContactCustomAdapter getCustomAdapter() {
        return this.customAdapter;
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListLayout
    public EaseUser getItem(int i2) {
        if (i2 < this.listAdapter.getData().size()) {
            return this.listAdapter.getItem(i2);
        }
        throw new ArrayIndexOutOfBoundsException(i2);
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListLayout
    public EaseContactListAdapter getListAdapter() {
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

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void loadContactListFail(String str) {
        OnContactLoadListener onContactLoadListener = this.loadListener;
        if (onContactLoadListener != null) {
            onContactLoadListener.loadDataFail(str);
        }
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void loadContactListNoData() {
        OnContactLoadListener onContactLoadListener = this.loadListener;
        if (onContactLoadListener != null) {
            onContactLoadListener.loadDataFinish(new ArrayList());
        }
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void loadContactListSuccess(List<EaseUser> list) {
        this.presenter.sortData(list);
    }

    public void loadDefaultData() {
        this.presenter.loadData();
    }

    public void notifyDataSetChanged() {
        this.listAdapter.setSettingModel(this.contactSetModel);
        this.listAdapter.notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void refreshList() {
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void removeAdapter(RecyclerView.Adapter adapter) {
        this.concatAdapter.removeAdapter(adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void removeRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        removeItemDecoration(itemDecoration);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarBorderColor(int i2) {
        this.contactSetModel.setBorderColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarBorderWidth(int i2) {
        this.contactSetModel.setBorderWidth(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarDefaultSrc(Drawable drawable) {
        this.contactSetModel.setAvatarDefaultSrc(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarRadius(int i2) {
        this.contactSetModel.setAvatarRadius(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarShapeType(EaseImageView.ShapeType shapeType) {
        this.contactSetModel.setShapeType(shapeType);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IAvatarSet
    public void setAvatarSize(float f2) {
        this.contactSetModel.setAvatarSize(f2);
        notifyDataSetChanged();
    }

    public void setData(List<EaseUser> list) {
        this.presenter.sortData(list);
    }

    public void setDataNotSort(List<EaseUser> list) {
        this.listAdapter.setData(list);
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListStyle
    public void setHeaderBackGround(Drawable drawable) {
        this.contactSetModel.setHeaderBgDrawable(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactTextStyle
    public void setHeaderTextColor(int i2) {
        this.contactSetModel.setHeaderTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactTextStyle
    public void setHeaderTextSize(int i2) {
        this.contactSetModel.setHeaderTextSize(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListStyle
    public void setItemBackGround(Drawable drawable) {
        this.contactSetModel.setBgDrawable(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListStyle
    public void setItemHeight(int i2) {
        this.contactSetModel.setItemHeight(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactCustomListLayout
    public void setOnContactLoadListener(OnContactLoadListener onContactLoadListener) {
        this.loadListener = onContactLoadListener;
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactCustomListLayout
    public void setOnCustomItemClickListener(OnItemClickListener onItemClickListener) {
        this.customItemClickListener = onItemClickListener;
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

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListLayout
    public void setPresenter(EaseContactPresenter easeContactPresenter) {
        this.presenter = easeContactPresenter;
        if (getContext() instanceof AppCompatActivity) {
            ((AppCompatActivity) getContext()).getLifecycle().addObserver(easeContactPresenter);
        }
        this.presenter.attachView(this);
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactTextStyle
    public void setTitleTextColor(int i2) {
        this.contactSetModel.setTitleTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactTextStyle
    public void setTitleTextSize(int i2) {
        this.contactSetModel.setTitleTextSize(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListLayout
    public void showItemDefaultMenu(boolean z2) {
        this.showDefaultMenu = z2;
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactListLayout
    public void showItemHeader(boolean z2) {
        this.contactSetModel.setShowItemHeader(z2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void sortContactListSuccess(List<EaseUser> list) {
        OnContactLoadListener onContactLoadListener = this.loadListener;
        if (onContactLoadListener != null) {
            onContactLoadListener.loadDataFinish(list);
        }
        this.listAdapter.setData(list);
    }

    public EaseContactListLayout(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.IEaseContactListView
    public void refreshList(int i2) {
        this.listAdapter.notifyItemChanged(i2);
    }

    public EaseContactListLayout(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.showDefaultMenu = true;
        EaseContactSetStyle easeContactSetStyle = new EaseContactSetStyle();
        this.contactSetModel = easeContactSetStyle;
        easeContactSetStyle.setShowItemHeader(true);
        this.presenter = new EaseContactPresenterImpl();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this.presenter);
        }
        initAttrs(context, attributeSet);
        initViews();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactCustomListLayout
    public void addCustomItem(int i2, String str, String str2) {
        EaseContactCustomAdapter easeContactCustomAdapter = this.customAdapter;
        if (easeContactCustomAdapter != null) {
            easeContactCustomAdapter.addItem(i2, str, str2);
        }
    }
}
