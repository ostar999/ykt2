package com.hyphenate.easeui.modules.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.menu.EasePopupWindow;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EasePopupWindowHelper {
    private static final int SPAN_COUNT = 5;
    private static final int[] icons;
    private static final int[] itemIds = {R.id.action_chat_copy, R.id.action_chat_delete, R.id.action_chat_recall, R.id.action_chat_translate, R.id.action_chat_reTranslate, R.id.action_chat_hide};
    private static final int[] titles = {R.string.action_copy, R.string.action_delete, R.string.action_recall, R.string.action_translate, R.string.action_reTranslate, R.string.action_hide};
    private MenuAdapter adapter;
    private Drawable background;
    private Context context;
    private EasePopupWindow.OnPopupWindowDismissListener dismissListener;
    private EasePopupWindow.OnPopupWindowItemClickListener itemClickListener;
    private View layout;
    private EasePopupWindow pMenu;
    private RecyclerView rvMenuList;
    private boolean touchable;
    private TextView tvTitle;
    private List<MenuItemBean> menuItems = new ArrayList();
    private Map<Integer, MenuItemBean> menuItemMap = new HashMap();

    public class MenuAdapter extends EaseBaseRecyclerViewAdapter<MenuItemBean> {

        public class MenuViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<MenuItemBean> {
            private ImageView ivActionIcon;
            private TextView tvActionName;

            public MenuViewHolder(@NonNull View view) {
                super(view);
            }

            @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
            public void initView(View view) {
                this.ivActionIcon = (ImageView) findViewById(R.id.iv_action_icon);
                this.tvActionName = (TextView) findViewById(R.id.tv_action_name);
                if (DarkModeHelper.isDarkMode(view.getContext())) {
                    this.tvActionName.setTextColor(Color.parseColor("#7380a9"));
                    view.setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
                }
            }

            @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
            public void setData(MenuItemBean menuItemBean, int i2) {
                String title = menuItemBean.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    this.tvActionName.setText(title);
                }
                if (menuItemBean.getResourceId() != 0) {
                    if (DarkModeHelper.isDarkMode(this.itemView.getContext())) {
                        if (menuItemBean.getItemId() == R.id.action_chat_copy) {
                            this.ivActionIcon.setImageResource(R.drawable.ease_chat_item_menu_copy_night);
                            return;
                        } else {
                            DarkModeHelper.setDarkModeDrawable(this.ivActionIcon, menuItemBean.getResourceId());
                            return;
                        }
                    }
                    if (menuItemBean.getItemId() == R.id.action_chat_copy) {
                        this.ivActionIcon.setImageResource(R.drawable.ease_chat_item_menu_copy);
                    } else {
                        this.ivActionIcon.setImageResource(menuItemBean.getResourceId());
                    }
                }
            }
        }

        private MenuAdapter() {
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
        public EaseBaseRecyclerViewAdapter.ViewHolder getViewHolder(ViewGroup viewGroup, int i2) {
            return new MenuViewHolder(LayoutInflater.from(EasePopupWindowHelper.this.context).inflate(R.layout.ease_layout_item_menu_popupwindow, viewGroup, false));
        }
    }

    static {
        int i2 = R.drawable.ease_chat_item_menu_translation;
        icons = new int[]{R.drawable.ease_chat_item_menu_copy, R.drawable.ease_chat_item_menu_delete, R.drawable.ease_chat_item_menu_recall, i2, i2, R.drawable.ease_chat_item_menu_hide};
    }

    public EasePopupWindowHelper() {
        EasePopupWindow easePopupWindow = this.pMenu;
        if (easePopupWindow != null) {
            easePopupWindow.dismiss();
        }
        this.menuItems.clear();
        this.menuItemMap.clear();
    }

    private void checkIfShowItems() {
        if (this.menuItemMap.size() > 0) {
            this.menuItems.clear();
            for (MenuItemBean menuItemBean : this.menuItemMap.values()) {
                if (menuItemBean.isVisible()) {
                    this.menuItems.add(menuItemBean);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMenu$0(View view, int i2) {
        dismiss();
        EasePopupWindow.OnPopupWindowItemClickListener onPopupWindowItemClickListener = this.itemClickListener;
        if (onPopupWindowItemClickListener != null) {
            onPopupWindowItemClickListener.onMenuItemClick(this.adapter.getItem(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortList$1(MenuItemBean menuItemBean, MenuItemBean menuItemBean2) {
        int order = menuItemBean.getOrder();
        int order2 = menuItemBean2.getOrder();
        if (order2 < order) {
            return 1;
        }
        return order == order2 ? 0 : -1;
    }

    private void showPre() {
        this.pMenu.setOutsideTouchable(this.touchable);
        this.pMenu.setBackgroundDrawable(this.background);
        checkIfShowItems();
        sortList(this.menuItems);
        this.adapter.setData(this.menuItems);
    }

    private void sortList(List<MenuItemBean> list) {
        Collections.sort(list, new Comparator() { // from class: com.hyphenate.easeui.modules.menu.b
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return EasePopupWindowHelper.lambda$sortList$1((MenuItemBean) obj, (MenuItemBean) obj2);
            }
        });
    }

    public void addItemMenu(MenuItemBean menuItemBean) {
        if (this.menuItemMap.containsKey(Integer.valueOf(menuItemBean.getItemId()))) {
            return;
        }
        this.menuItemMap.put(Integer.valueOf(menuItemBean.getItemId()), menuItemBean);
    }

    public void clear() {
        this.menuItems.clear();
        this.menuItemMap.clear();
    }

    public void dismiss() {
        EasePopupWindow easePopupWindow = this.pMenu;
        if (easePopupWindow == null) {
            throw new NullPointerException("please must init first!");
        }
        easePopupWindow.dismiss();
        EasePopupWindow.OnPopupWindowDismissListener onPopupWindowDismissListener = this.dismissListener;
        if (onPopupWindowDismissListener != null) {
            onPopupWindowDismissListener.onDismiss(this.pMenu);
        }
    }

    public MenuItemBean findItem(int i2) {
        if (this.menuItemMap.containsKey(Integer.valueOf(i2))) {
            return this.menuItemMap.get(Integer.valueOf(i2));
        }
        return null;
    }

    public void findItemVisible(int i2, boolean z2) {
        if (this.menuItemMap.containsKey(Integer.valueOf(i2))) {
            this.menuItemMap.get(Integer.valueOf(i2)).setVisible(z2);
        }
    }

    public PopupWindow getPopupWindow() {
        return this.pMenu;
    }

    public View getView() {
        return this.layout;
    }

    public void initMenu(@NonNull Context context) {
        this.context = context;
        this.pMenu = new EasePopupWindow(context, true);
        this.layout = LayoutInflater.from(context).inflate(R.layout.ease_layout_menu_popupwindow, (ViewGroup) null);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ease_bg_menu_popupwindow);
        if (DarkModeHelper.isDarkMode(context)) {
            drawable.setColorFilter(Color.parseColor("#1C2134"), PorterDuff.Mode.SRC_IN);
            this.layout.setBackground(drawable);
            icons[0] = R.drawable.ease_chat_item_menu_copy_night;
        }
        this.pMenu.setContentView(this.layout);
        this.tvTitle = (TextView) this.layout.findViewById(R.id.tv_title);
        this.rvMenuList = (RecyclerView) this.layout.findViewById(R.id.rv_menu_list);
        MenuAdapter menuAdapter = new MenuAdapter();
        this.adapter = menuAdapter;
        this.rvMenuList.setAdapter(menuAdapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.hyphenate.easeui.modules.menu.a
            @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                this.f8741c.lambda$initMenu$0(view, i2);
            }
        });
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.background = drawable;
    }

    public void setDefaultMenus() {
        int i2 = 0;
        while (true) {
            int[] iArr = itemIds;
            if (i2 >= iArr.length) {
                return;
            }
            int i3 = i2 + 1;
            MenuItemBean menuItemBean = new MenuItemBean(0, iArr[i2], i3 * 10, this.context.getString(titles[i2]));
            menuItemBean.setResourceId(icons[i2]);
            addItemMenu(menuItemBean);
            i2 = i3;
        }
    }

    public void setOnPopupMenuDismissListener(EasePopupWindow.OnPopupWindowDismissListener onPopupWindowDismissListener) {
        this.dismissListener = onPopupWindowDismissListener;
    }

    public void setOnPopupMenuItemClickListener(EasePopupWindow.OnPopupWindowItemClickListener onPopupWindowItemClickListener) {
        this.itemClickListener = onPopupWindowItemClickListener;
    }

    public void setOutsideTouchable(boolean z2) {
        this.touchable = z2;
    }

    public void show(View view, View view2) {
        showPre();
        if (this.menuItems.size() <= 0) {
            Log.e("EasePopupWindowHelper", "Span count should be at least 1. Provided " + this.menuItems.size());
            return;
        }
        if (this.menuItems.size() < 5) {
            this.rvMenuList.setLayoutManager(new GridLayoutManager(this.context, this.menuItems.size(), 1, false));
        } else {
            this.rvMenuList.setLayoutManager(new GridLayoutManager(this.context, 5, 1, false));
        }
        getView().measure(0, 0);
        int measuredWidth = getView().getMeasuredWidth();
        int measuredHeight = getView().getMeasuredHeight();
        int[] iArr = new int[2];
        view2.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr2);
        int iDip2px = (int) EaseCommonUtils.dip2px(this.context, 5.0f);
        int i2 = iArr[1];
        int height = (i2 - measuredHeight) - iDip2px < iArr2[1] ? i2 + view2.getHeight() + iDip2px : (i2 - measuredHeight) - iDip2px;
        int i3 = measuredWidth / 2;
        int width = ((float) ((iArr[0] + (view2.getWidth() / 2)) + i3)) + EaseCommonUtils.dip2px(this.context, 10.0f) > ((float) view.getWidth()) ? (int) ((view.getWidth() - EaseCommonUtils.dip2px(this.context, 10.0f)) - measuredWidth) : (iArr[0] + (view2.getWidth() / 2)) - i3;
        if (width < EaseCommonUtils.dip2px(this.context, 10.0f)) {
            width = (int) EaseCommonUtils.dip2px(this.context, 10.0f);
        }
        this.pMenu.showAtLocation(view2, 0, width, height);
    }

    public void showTitle(@NonNull String str) {
        if (this.pMenu == null) {
            throw new NullPointerException("please must init first!");
        }
        this.tvTitle.setText(str);
        this.tvTitle.setVisibility(0);
    }

    public void addItemMenu(int i2, int i3, int i4, String str) {
        addItemMenu(new MenuItemBean(i2, i3, i4, str));
    }
}
