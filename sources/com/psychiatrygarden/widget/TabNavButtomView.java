package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class TabNavButtomView extends LinearLayout {
    private boolean isNotRead;
    private String is_hidden_course;
    private String is_hidden_shop;
    private ImageView iv_message_new;
    private Context mContext;
    private OnHomeNavButtomiml mOnHomeNavButtom;
    private final View.OnClickListener mOnclick;
    private int preOldPosition;
    private TextView tv_chat_message;
    private int type;

    public interface OnHomeNavButtomiml {
        void mHomeTab1Method(int mHomeTagPosition, int oldHomeTagPosition, String isCircle);
    }

    public TabNavButtomView(Context context) {
        super(context);
        this.preOldPosition = 0;
        this.isNotRead = false;
        this.type = 0;
        this.mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.TabNavButtomView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                switch (v2.getId()) {
                    case R.id.home_tab1_nav /* 2131363501 */:
                        if (TabNavButtomView.this.preOldPosition != 0) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(0, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.setSelectView(0);
                            TabNavButtomView.this.preOldPosition = 0;
                            break;
                        }
                        break;
                    case R.id.home_tab2_nav /* 2131363502 */:
                        if (TabNavButtomView.this.preOldPosition != 1) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(1, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.setSelectView(1);
                            TabNavButtomView.this.preOldPosition = 1;
                            break;
                        }
                        break;
                    case R.id.home_tab3_nav /* 2131363503 */:
                        if ("1".equals(TabNavButtomView.this.is_hidden_course) || ("1".equals(TabNavButtomView.this.is_hidden_course) && "1".equals(TabNavButtomView.this.is_hidden_shop))) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(1, TabNavButtomView.this.preOldPosition, "isCircle");
                            TabNavButtomView.this.preOldPosition = 1;
                        } else {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "isCircle");
                            TabNavButtomView.this.preOldPosition = 2;
                        }
                        TabNavButtomView.this.setSelectView(2);
                        break;
                    case R.id.home_tab4_nav /* 2131363504 */:
                        if ("1".equals(TabNavButtomView.this.is_hidden_course)) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.preOldPosition = 2;
                        } else {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.preOldPosition = 3;
                        }
                        TabNavButtomView.this.setSelectView(3);
                        break;
                    case R.id.home_tab5_nav /* 2131363505 */:
                        if (TabNavButtomView.this.preOldPosition != 4) {
                            if ("1".equals(TabNavButtomView.this.is_hidden_course) && "0".equals(TabNavButtomView.this.is_hidden_shop)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 3;
                            } else if ("1".equals(TabNavButtomView.this.is_hidden_shop) && "0".equals(TabNavButtomView.this.is_hidden_course)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 3;
                            } else if ("1".equals(TabNavButtomView.this.is_hidden_course) && "1".equals(TabNavButtomView.this.is_hidden_shop)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 2;
                            } else {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(4, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 4;
                            }
                            TabNavButtomView.this.setSelectView(4);
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        initial();
    }

    public int getPreOldPosition() {
        return this.preOldPosition;
    }

    public int getType() {
        return this.type;
    }

    public OnHomeNavButtomiml getmOnHomeNavButtom() {
        return this.mOnHomeNavButtom;
    }

    public void initial() {
        View.inflate(this.mContext, R.layout.activity_tnb, this);
        TextView textView = (TextView) findViewById(R.id.home_tab1_nav);
        TextView textView2 = (TextView) findViewById(R.id.home_tab2_nav);
        TextView textView3 = (TextView) findViewById(R.id.home_tab3_nav);
        TextView textView4 = (TextView) findViewById(R.id.home_tab4_nav);
        TextView textView5 = (TextView) findViewById(R.id.home_tab5_nav);
        this.tv_chat_message = (TextView) findViewById(R.id.tv_chat_message);
        this.iv_message_new = (ImageView) findViewById(R.id.iv_message_new);
        textView.setOnClickListener(this.mOnclick);
        textView2.setOnClickListener(this.mOnclick);
        textView3.setOnClickListener(this.mOnclick);
        textView4.setOnClickListener(this.mOnclick);
        textView5.setOnClickListener(this.mOnclick);
        this.is_hidden_shop = SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, getContext(), "0");
        this.is_hidden_course = SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, getContext(), "0");
        if ("1".equals(this.is_hidden_shop)) {
            textView4.setVisibility(8);
        }
        if ("1".equals(this.is_hidden_course)) {
            textView2.setVisibility(8);
        }
        setSelectView(this.type);
    }

    public void isChatReadMessage() {
        if (!UserConfig.isLogin()) {
            this.tv_chat_message.setVisibility(4);
            return;
        }
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.SYStem_UnRead_Msg_Count, this.mContext, 0);
        if (intConfig <= 0) {
            this.tv_chat_message.setVisibility(4);
            return;
        }
        this.tv_chat_message.setVisibility(0);
        this.tv_chat_message.getPaint().setFakeBoldText(true);
        if (intConfig > 99) {
            this.tv_chat_message.setTextSize(2, 12.0f);
            this.tv_chat_message.setText("···");
            return;
        }
        this.tv_chat_message.setTextSize(2, 14.0f);
        this.tv_chat_message.setText(intConfig + "");
    }

    public boolean isNotRead() {
        return this.isNotRead;
    }

    public void isReadMessage(boolean isNotRead) {
        if (isNotRead) {
            this.iv_message_new.setVisibility(0);
        } else {
            this.iv_message_new.setVisibility(4);
        }
    }

    public void scaleAnimator(View view, float orignal, float dest) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "scaleX", orignal, dest);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "scaleY", orignal, dest);
        animatorSet.setDuration(500L);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(objectAnimatorOfFloat).with(objectAnimatorOfFloat2);
        animatorSet.start();
    }

    public void setSelectView(int type) {
        if (getChildAt(0) instanceof LinearLayout) {
            for (int i2 = 0; i2 < ((LinearLayout) getChildAt(0)).getChildCount(); i2++) {
                View childAt = ((LinearLayout) getChildAt(0)).getChildAt(i2);
                if (i2 != type) {
                    childAt.setSelected(false);
                } else if (!childAt.isSelected()) {
                    childAt.setSelected(true);
                }
            }
        }
    }

    public void setType(int type) {
        this.type = type;
        setSelectView(type);
        this.preOldPosition = type;
    }

    public void setmOnHomeNavButtom(OnHomeNavButtomiml mOnHomeNavButtom) {
        this.mOnHomeNavButtom = mOnHomeNavButtom;
    }

    public TabNavButtomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.preOldPosition = 0;
        this.isNotRead = false;
        this.type = 0;
        this.mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.TabNavButtomView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                switch (v2.getId()) {
                    case R.id.home_tab1_nav /* 2131363501 */:
                        if (TabNavButtomView.this.preOldPosition != 0) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(0, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.setSelectView(0);
                            TabNavButtomView.this.preOldPosition = 0;
                            break;
                        }
                        break;
                    case R.id.home_tab2_nav /* 2131363502 */:
                        if (TabNavButtomView.this.preOldPosition != 1) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(1, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.setSelectView(1);
                            TabNavButtomView.this.preOldPosition = 1;
                            break;
                        }
                        break;
                    case R.id.home_tab3_nav /* 2131363503 */:
                        if ("1".equals(TabNavButtomView.this.is_hidden_course) || ("1".equals(TabNavButtomView.this.is_hidden_course) && "1".equals(TabNavButtomView.this.is_hidden_shop))) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(1, TabNavButtomView.this.preOldPosition, "isCircle");
                            TabNavButtomView.this.preOldPosition = 1;
                        } else {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "isCircle");
                            TabNavButtomView.this.preOldPosition = 2;
                        }
                        TabNavButtomView.this.setSelectView(2);
                        break;
                    case R.id.home_tab4_nav /* 2131363504 */:
                        if ("1".equals(TabNavButtomView.this.is_hidden_course)) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.preOldPosition = 2;
                        } else {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.preOldPosition = 3;
                        }
                        TabNavButtomView.this.setSelectView(3);
                        break;
                    case R.id.home_tab5_nav /* 2131363505 */:
                        if (TabNavButtomView.this.preOldPosition != 4) {
                            if ("1".equals(TabNavButtomView.this.is_hidden_course) && "0".equals(TabNavButtomView.this.is_hidden_shop)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 3;
                            } else if ("1".equals(TabNavButtomView.this.is_hidden_shop) && "0".equals(TabNavButtomView.this.is_hidden_course)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 3;
                            } else if ("1".equals(TabNavButtomView.this.is_hidden_course) && "1".equals(TabNavButtomView.this.is_hidden_shop)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 2;
                            } else {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(4, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 4;
                            }
                            TabNavButtomView.this.setSelectView(4);
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        initial();
    }

    public TabNavButtomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.preOldPosition = 0;
        this.isNotRead = false;
        this.type = 0;
        this.mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.TabNavButtomView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                switch (v2.getId()) {
                    case R.id.home_tab1_nav /* 2131363501 */:
                        if (TabNavButtomView.this.preOldPosition != 0) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(0, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.setSelectView(0);
                            TabNavButtomView.this.preOldPosition = 0;
                            break;
                        }
                        break;
                    case R.id.home_tab2_nav /* 2131363502 */:
                        if (TabNavButtomView.this.preOldPosition != 1) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(1, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.setSelectView(1);
                            TabNavButtomView.this.preOldPosition = 1;
                            break;
                        }
                        break;
                    case R.id.home_tab3_nav /* 2131363503 */:
                        if ("1".equals(TabNavButtomView.this.is_hidden_course) || ("1".equals(TabNavButtomView.this.is_hidden_course) && "1".equals(TabNavButtomView.this.is_hidden_shop))) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(1, TabNavButtomView.this.preOldPosition, "isCircle");
                            TabNavButtomView.this.preOldPosition = 1;
                        } else {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "isCircle");
                            TabNavButtomView.this.preOldPosition = 2;
                        }
                        TabNavButtomView.this.setSelectView(2);
                        break;
                    case R.id.home_tab4_nav /* 2131363504 */:
                        if ("1".equals(TabNavButtomView.this.is_hidden_course)) {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.preOldPosition = 2;
                        } else {
                            TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                            TabNavButtomView.this.preOldPosition = 3;
                        }
                        TabNavButtomView.this.setSelectView(3);
                        break;
                    case R.id.home_tab5_nav /* 2131363505 */:
                        if (TabNavButtomView.this.preOldPosition != 4) {
                            if ("1".equals(TabNavButtomView.this.is_hidden_course) && "0".equals(TabNavButtomView.this.is_hidden_shop)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 3;
                            } else if ("1".equals(TabNavButtomView.this.is_hidden_shop) && "0".equals(TabNavButtomView.this.is_hidden_course)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(3, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 3;
                            } else if ("1".equals(TabNavButtomView.this.is_hidden_course) && "1".equals(TabNavButtomView.this.is_hidden_shop)) {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(2, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 2;
                            } else {
                                TabNavButtomView.this.mOnHomeNavButtom.mHomeTab1Method(4, TabNavButtomView.this.preOldPosition, "");
                                TabNavButtomView.this.preOldPosition = 4;
                            }
                            TabNavButtomView.this.setSelectView(4);
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        initial();
    }
}
