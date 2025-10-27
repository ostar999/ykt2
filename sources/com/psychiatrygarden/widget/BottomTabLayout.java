package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.TabMenuItem;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Triple;

/* loaded from: classes6.dex */
public class BottomTabLayout extends LinearLayout {
    private int[] circleLocation;
    private int currentPosition;
    private boolean isNotice;
    private RectF mBounds;
    private OnTabMenuClickListener mMenuClickListener;
    private Path mOuterCurvePath;
    private int mPrePosition;
    private Path mScreenEdgePath;
    private TextView mTvChatMessage;
    private Paint paint;
    private Path path;
    private final Point pointA;
    private final Point pointA2;
    private final Point pointA3;
    private final Point pointB;
    private final Point pointB2;
    private final Point pointB3;
    private final Point pointC;
    private final Point pointC2;
    private final Point pointC3;
    private boolean showCourse;
    private boolean showShop;
    private final List<TabMenuItem> tabMenuItems;
    private float width;

    public interface OnTabMenuClickListener {
        void jump2PushCircle();

        void onMenuItemClick(int currentPosition, int prePosition, String circle);
    }

    public BottomTabLayout(Context context) {
        super(context);
        this.pointA = new Point();
        this.pointA2 = new Point();
        this.pointA3 = new Point();
        this.pointB = new Point();
        this.pointB2 = new Point();
        this.pointB3 = new Point();
        this.pointC = new Point();
        this.pointC2 = new Point();
        this.pointC3 = new Point();
        this.showCourse = true;
        this.showShop = false;
        this.currentPosition = 0;
        this.tabMenuItems = new ArrayList();
        this.circleLocation = new int[2];
        this.isNotice = false;
        init();
    }

    private void addTabView() {
        removeAllViews();
        float currentScreenWidth = getCurrentScreenWidth();
        this.width = currentScreenWidth;
        int size = (int) (currentScreenWidth / this.tabMenuItems.size());
        for (TabMenuItem tabMenuItem : this.tabMenuItems) {
            View viewInflate = View.inflate(getContext(), tabMenuItem.getType() == 5 ? R.layout.item_tab_my : R.layout.item_tab_menu, null);
            viewInflate.setTag(tabMenuItem);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_icon);
            imageView.setImageDrawable(tabMenuItem.getIcon());
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_menu_name);
            textView.setText(tabMenuItem.getTabTitle());
            if (tabMenuItem.getType() == 1) {
                imageView.setSelected(true);
                textView.setSelected(true);
            } else {
                imageView.setSelected(false);
                textView.setSelected(false);
            }
            if (tabMenuItem.getType() == 5) {
                this.mTvChatMessage = (TextView) viewInflate.findViewById(R.id.tv_chat_message);
            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, -2);
            if (tabMenuItem.getType() == 3) {
                layoutParams.height = dip2px(83.0f);
                textView.setPadding(0, dip2px(5.0f), 0, 0);
            }
            addView(viewInflate, layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewInflate.getLayoutParams();
            if (tabMenuItem.getType() == 3) {
                imageView.setPadding(dip2px(10.0f), dip2px(10.0f), dip2px(10.0f), dip2px(10.0f));
                imageView.setBackground(ContextCompat.getDrawable(getContext(), SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.bg_tab_circle_day : R.drawable.bg_tab_circle_night));
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams3.width = dip2px(45.0f);
                layoutParams3.height = dip2px(45.0f);
                layoutParams3.topMargin = dip2px(10.0f);
                imageView.setLayoutParams(layoutParams3);
                layoutParams2.topMargin = dip2px(8.0f);
            } else {
                layoutParams2.bottomMargin = dip2px(4.0f);
            }
            viewInflate.setLayoutParams(layoutParams2);
            viewInflate.setTag(tabMenuItem);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16302c.clickTab(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickTab(View v2) {
        TabMenuItem tabMenuItem = (TabMenuItem) v2.getTag();
        if (tabMenuItem.getSelect() && tabMenuItem.getType() == 3 && this.mMenuClickListener != null && !isNotice()) {
            this.mMenuClickListener.jump2PushCircle();
        }
        int i2 = this.mPrePosition;
        int i3 = this.currentPosition;
        if (i2 != i3) {
            this.mPrePosition = i3;
        }
        int childCount = getChildCount();
        int i4 = 0;
        while (true) {
            if (i4 >= childCount) {
                break;
            }
            if (v2 == getChildAt(i4)) {
                tabMenuItem.setSelect(true);
                this.currentPosition = i4;
                break;
            }
            i4++;
        }
        int i5 = 0;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            TabMenuItem tabMenuItem2 = (TabMenuItem) childAt.getTag();
            tabMenuItem2.setSelect(this.currentPosition == i5);
            ((ImageView) childAt.findViewById(R.id.iv_icon)).setSelected(this.currentPosition == i5);
            TextView textView = (TextView) childAt.findViewById(R.id.tv_menu_name);
            childAt.findViewById(R.id.tv_menu_name).setSelected(this.currentPosition == i5);
            if (this.currentPosition == i5) {
                if (tabMenuItem.getType() == 3) {
                    textView.setText("发帖");
                }
            } else if (tabMenuItem2.getType() == 3) {
                textView.setText("论坛");
            }
            i5++;
        }
        if (this.mMenuClickListener != null) {
            this.mMenuClickListener.onMenuItemClick(this.currentPosition, this.mPrePosition, tabMenuItem.getType() == 3 ? "isCircle" : tabMenuItem.getType() == 5 ? "personal" : "");
        }
    }

    private int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, getResources().getDisplayMetrics());
    }

    private void drawPath(Canvas canvas) {
        this.width = getCurrentScreenWidth();
        int circlePosition = getCirclePosition();
        if (circlePosition <= 0) {
            return;
        }
        int iDip2px = (int) ((this.width / 3.0f) + dip2px(15.0f));
        int iDip2px2 = (int) ((this.width / 2.8f) + dip2px(15.0f));
        int iDip2px3 = (int) ((this.width / 2.6f) + dip2px(15.0f));
        int i2 = (int) (this.width / 8.0f);
        if (ProjectApp.newHomeStyle || this.showCourse || circlePosition == 1) {
            i2 *= -1;
        }
        if (this.tabMenuItems.size() == 3 || this.tabMenuItems.size() == 5) {
            i2 = 0;
        }
        Point point = this.pointA;
        point.x = iDip2px - i2;
        float f2 = 30;
        point.y = dip2px(f2);
        Point point2 = this.pointB;
        point2.x = iDip2px2 - i2;
        point2.y = dip2px(f2);
        Point point3 = this.pointC;
        point3.x = iDip2px3 - i2;
        float f3 = 22;
        point3.y = dip2px(f3);
        Point point4 = this.pointA2;
        Point point5 = this.pointC;
        point4.x = point5.x;
        point4.y = point5.y;
        Point point6 = this.pointB2;
        point6.x = ((int) (this.width / 2.0f)) - i2;
        point6.y = dip2px(-5.0f);
        Point point7 = this.pointC2;
        point7.x = ((int) (this.width - iDip2px3)) - i2;
        point7.y = dip2px(f3);
        Point point8 = this.pointA3;
        Point point9 = this.pointC2;
        point8.x = point9.x;
        point8.y = point9.y;
        Point point10 = this.pointB3;
        point10.x = ((int) (this.width - iDip2px2)) - i2;
        point10.y = dip2px(f2);
        this.pointC3.y = dip2px(f2);
        this.pointC3.x = ((int) (this.width - iDip2px)) - i2;
        this.path.moveTo(0.0f, dip2px(f2));
        Path path = this.path;
        Point point11 = this.pointA;
        path.lineTo(point11.x, point11.y);
        Path path2 = this.path;
        Point point12 = this.pointB;
        float f4 = point12.x;
        float f5 = point12.y;
        Point point13 = this.pointC;
        path2.quadTo(f4, f5, point13.x, point13.y);
        Path path3 = this.path;
        Point point14 = this.pointB2;
        float f6 = point14.x;
        float f7 = point14.y;
        Point point15 = this.pointC2;
        path3.quadTo(f6, f7, point15.x, point15.y);
        Path path4 = this.path;
        Point point16 = this.pointB3;
        float f8 = point16.x;
        float f9 = point16.y;
        Point point17 = this.pointC3;
        path4.quadTo(f8, f9, point17.x, point17.y);
        this.path.lineTo(this.width, dip2px(f2));
        this.path.lineTo(this.width, getHeight());
        this.path.lineTo(0.0f, getHeight());
        this.path.close();
        canvas.drawPath(this.path, this.paint);
        if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            if (this.mBounds == null) {
                this.mBounds = new RectF();
            }
            this.path.computeBounds(this.mBounds, true);
            if (this.mScreenEdgePath == null) {
                this.mScreenEdgePath = new Path();
            }
            this.mScreenEdgePath.reset();
            this.mScreenEdgePath.addRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), Path.Direction.CW);
            if (this.mOuterCurvePath == null) {
                this.mOuterCurvePath = new Path();
            }
            this.mOuterCurvePath.reset();
            this.mOuterCurvePath.op(this.path, this.mScreenEdgePath, Path.Op.INTERSECT);
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#0a909399"));
            paint.setShadowLayer(20.0f, 0.0f, 20.0f, Color.parseColor("#0a909399"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dip2px(1.5f));
            canvas.drawPath(this.mOuterCurvePath, paint);
        }
    }

    private int getCirclePosition() {
        for (int i2 = 0; i2 < this.tabMenuItems.size(); i2++) {
            if (this.tabMenuItems.get(i2).getType() == 3) {
                return i2;
            }
        }
        return -1;
    }

    private void init() {
        setOrientation(0);
        setGravity(80);
        this.paint = new Paint(1);
        this.path = new Path();
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setColor(-1);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.paint.setColor(Color.parseColor("#1C2134"));
        }
        this.showCourse = !"1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, getContext(), "0"));
        this.showShop = (ProjectApp.newHomeStyle || "1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, getContext(), "0"))) ? false : true;
        this.width = getCurrentScreenWidth();
        setClipChildren(false);
        initTabMenuData();
        addTabView();
        setWillNotDraw(false);
    }

    private void initTabMenuData() {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.home_tab1, R.attr.home_tab2, R.attr.home_tab3, R.attr.home_tab4, R.attr.home_tab5, R.attr.home_tab_index});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.homepage_bottom_question_database_default);
        }
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(2);
        if (drawable2 == null) {
            drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.homepage_bottom_circle_default_day);
        }
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(4);
        if (drawable3 == null) {
            drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.homepage_bottom_personal_center_default);
        }
        Drawable drawable4 = typedArrayObtainStyledAttributes.getDrawable(3);
        if (drawable4 == null) {
            drawable4 = ContextCompat.getDrawable(getContext(), R.drawable.home_tab_shop_normal_day);
        }
        Drawable drawable5 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (drawable5 == null) {
            drawable5 = ContextCompat.getDrawable(getContext(), R.drawable.tab_course_day_normal);
        }
        if (ProjectApp.newHomeStyle) {
            Drawable drawable6 = typedArrayObtainStyledAttributes.getDrawable(5);
            if (drawable6 == null) {
                drawable6 = ContextCompat.getDrawable(getContext(), R.drawable.tab_home_index_day);
            }
            if (drawable6 != null) {
                this.tabMenuItems.add(new TabMenuItem(drawable6, "首页", 0, true));
            }
            List<TabMenuItem> list = this.tabMenuItems;
            Objects.requireNonNull(drawable);
            list.add(new TabMenuItem(drawable, getResources().getString(R.string.tab_menu_question_database), 1, true));
            List<TabMenuItem> list2 = this.tabMenuItems;
            Objects.requireNonNull(drawable2);
            list2.add(new TabMenuItem(drawable2, getResources().getString(R.string.tab_menu_circle), 3, false));
            if (this.showCourse) {
                List<TabMenuItem> list3 = this.tabMenuItems;
                Objects.requireNonNull(drawable5);
                list3.add(new TabMenuItem(drawable5, getResources().getString(R.string.tab_menu_question_course), 2, false));
            } else if (this.showShop) {
                List<TabMenuItem> list4 = this.tabMenuItems;
                Objects.requireNonNull(drawable4);
                list4.add(new TabMenuItem(drawable4, getResources().getString(R.string.tab_menu_shop), 4, false));
            }
        } else {
            List<TabMenuItem> list5 = this.tabMenuItems;
            Objects.requireNonNull(drawable);
            list5.add(new TabMenuItem(drawable, getResources().getString(R.string.tab_menu_question_database), 1, true));
            if (this.showCourse) {
                List<TabMenuItem> list6 = this.tabMenuItems;
                Objects.requireNonNull(drawable5);
                list6.add(new TabMenuItem(drawable5, getResources().getString(R.string.tab_menu_question_course), 2, false));
            }
            List<TabMenuItem> list7 = this.tabMenuItems;
            Objects.requireNonNull(drawable2);
            list7.add(new TabMenuItem(drawable2, getResources().getString(R.string.tab_menu_circle), 3, false));
            if (this.showShop) {
                List<TabMenuItem> list8 = this.tabMenuItems;
                Objects.requireNonNull(drawable4);
                list8.add(new TabMenuItem(drawable4, getResources().getString(R.string.tab_menu_shop), 4, false));
            }
        }
        List<TabMenuItem> list9 = this.tabMenuItems;
        Objects.requireNonNull(drawable3);
        list9.add(new TabMenuItem(drawable3, getResources().getString(R.string.tab_menu_my), 5, false));
        typedArrayObtainStyledAttributes.recycle();
        for (TabMenuItem tabMenuItem : this.tabMenuItems) {
            if (tabMenuItem.getType() == 4) {
                this.showShop = true;
            }
            if (tabMenuItem.getType() == 2) {
                this.showCourse = true;
            }
        }
        if (ProjectApp.newHomeStyle && this.showCourse) {
            this.showShop = false;
        }
    }

    public Triple<View, Integer, Integer> getCircleView() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (((TabMenuItem) childAt.getTag()).getType() == 3) {
                int[] iArr = new int[2];
                childAt.findViewById(R.id.iv_icon).getLocationOnScreen(iArr);
                boolean z2 = this.showShop;
                if ((z2 && this.showCourse) || (!z2 && !this.showCourse)) {
                    iArr[0] = 0;
                }
                return new Triple<>(childAt, Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1] - CommonUtil.dip2px(getContext(), 63.0f)));
            }
        }
        return null;
    }

    public float getCurrentScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    public int getPreOldPosition() {
        return this.mPrePosition;
    }

    public void isChatReadMessage() {
        if (!UserConfig.isLogin()) {
            this.mTvChatMessage.setVisibility(4);
            return;
        }
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.SYStem_UnRead_Msg_Count, getContext(), 0) + ProjectApp.instance().getUnreadMessageCount();
        this.mTvChatMessage.setVisibility(intConfig > 0 ? 0 : 4);
        if (intConfig > 0) {
            this.mTvChatMessage.getPaint().setFakeBoldText(true);
            this.mTvChatMessage.setText(intConfig > 99 ? "···" : String.valueOf(intConfig));
        }
    }

    public boolean isNotice() {
        return this.isNotice;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        this.path.reset();
        drawPath(canvas);
        super.onDraw(canvas);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getContext().getResources().getDisplayMetrics().widthPixels, dip2px(83.0f));
    }

    public void setNotice(boolean notice) {
        this.isNotice = notice;
    }

    public void setOnTabMenuClickListener(OnTabMenuClickListener listener) {
        this.mMenuClickListener = listener;
    }

    public void setType(int type) {
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = getChildAt(i2);
            childAt.findViewById(R.id.iv_icon).setSelected(type == i2);
            childAt.findViewById(R.id.tv_menu_name).setSelected(type == i2);
            if (type == i2) {
                ((TabMenuItem) childAt.getTag()).setSelect(true);
                childAt.performClick();
            }
            i2++;
        }
        this.currentPosition = type;
        this.mPrePosition = type;
    }

    public void updateWidth() {
        this.tabMenuItems.clear();
        initTabMenuData();
        addTabView();
        setType(this.currentPosition, false);
        invalidate();
        requestLayout();
    }

    public void setType(int type, boolean pushCircle) {
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = getChildAt(i2);
            childAt.findViewById(R.id.iv_icon).setSelected(type == i2);
            childAt.findViewById(R.id.tv_menu_name).setSelected(type == i2);
            if (type == i2) {
                TabMenuItem tabMenuItem = (TabMenuItem) childAt.getTag();
                if (!tabMenuItem.getSelect()) {
                    childAt.performClick();
                }
                tabMenuItem.setSelect(true);
            }
            i2++;
        }
        this.currentPosition = type;
        this.mPrePosition = type;
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.pointA = new Point();
        this.pointA2 = new Point();
        this.pointA3 = new Point();
        this.pointB = new Point();
        this.pointB2 = new Point();
        this.pointB3 = new Point();
        this.pointC = new Point();
        this.pointC2 = new Point();
        this.pointC3 = new Point();
        this.showCourse = true;
        this.showShop = false;
        this.currentPosition = 0;
        this.tabMenuItems = new ArrayList();
        this.circleLocation = new int[2];
        this.isNotice = false;
        init();
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.pointA = new Point();
        this.pointA2 = new Point();
        this.pointA3 = new Point();
        this.pointB = new Point();
        this.pointB2 = new Point();
        this.pointB3 = new Point();
        this.pointC = new Point();
        this.pointC2 = new Point();
        this.pointC3 = new Point();
        this.showCourse = true;
        this.showShop = false;
        this.currentPosition = 0;
        this.tabMenuItems = new ArrayList();
        this.circleLocation = new int[2];
        this.isNotice = false;
        init();
    }
}
