package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.QwertyKeyListener;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.KeyboardUtils;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.interfaceclass.BiaoqianCallbackInterface;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.BiaoPupNewWindow;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class BiaoPupNewWindow extends BottomPopupView {
    private BaseQuickAdapter<BiaoqianBeab.DataBean, BaseViewHolder> adapter;
    private Context context;
    private List<BiaoqianBeab.DataBean> dataBiaoBIao;
    private LinearLayout linrel;
    private BiaoqianCallbackInterface mBiaoqianInterface;
    private SparseArray<EditText> tagEditViewMap;

    /* renamed from: com.psychiatrygarden.widget.BiaoPupNewWindow$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<BiaoqianBeab.DataBean, BaseViewHolder> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ CharSequence lambda$convert$1(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
            try {
                if (Long.parseLong(spanned.subSequence(0, i4).toString() + ((Object) charSequence.subSequence(i2, i3)) + ((Object) spanned.subSequence(i5, spanned.length()))) < 2147483647L) {
                    return null;
                }
                ToastUtil.shortToast(getContext(), "请输入正确的数字");
                return "";
            } catch (NumberFormatException unused) {
                ToastUtil.shortToast(getContext(), "请输入正确的数字");
                return "";
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$2(View view) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(view, 1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$3(EditText editText, BiaoqianBeab.DataBean dataBean, final View view) {
            int size = BiaoPupNewWindow.this.tagEditViewMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                EditText editText2 = (EditText) BiaoPupNewWindow.this.tagEditViewMap.get(BiaoPupNewWindow.this.tagEditViewMap.keyAt(i2));
                if (editText2 != view) {
                    editText2.setKeyListener(null);
                    editText2.setFocusable(false);
                    editText2.setCursorVisible(false);
                    editText2.clearFocus();
                    editText.setFocusableInTouchMode(false);
                    InputMethodManager inputMethodManager = (InputMethodManager) editText2.getContext().getSystemService("input_method");
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(editText2.getWindowToken(), 1);
                    }
                }
            }
            editText.setKeyListener(QwertyKeyListener.getInstanceForFullKeyboard());
            editText.requestFocus();
            editText.setFocusable(true);
            editText.setTag(dataBean.getLabel());
            editText.setFocusableInTouchMode(true);
            editText.setSelection(editText.getText().length());
            ((EditText) view).setCursorVisible(true);
            view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.r
                @Override // java.lang.Runnable
                public final void run() {
                    BiaoPupNewWindow.AnonymousClass1.lambda$convert$2(view);
                }
            }, 200L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$4(EditText editText, BiaoqianBeab.DataBean dataBean, BaseViewHolder baseViewHolder, View view, boolean z2) {
            if (editText.getTag() == null) {
                return;
            }
            if (!editText.getTag().equals(dataBean.getLabel())) {
                dataBean.setCount(dataBean.getCount());
                return;
            }
            if (z2) {
                return;
            }
            int size = BiaoPupNewWindow.this.tagEditViewMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                EditText editText2 = (EditText) BiaoPupNewWindow.this.tagEditViewMap.get(BiaoPupNewWindow.this.tagEditViewMap.keyAt(i2));
                if (editText2 == view) {
                    editText2.setKeyListener(null);
                    editText2.setFocusable(false);
                    editText2.setCursorVisible(false);
                    editText.setFocusableInTouchMode(false);
                    KeyboardUtils.hideSoftInput(view);
                }
            }
            Pattern patternCompile = Pattern.compile(RegexPool.NUMBERS);
            Matcher matcher = patternCompile.matcher(((EditText) view).getText().toString());
            String strGroup = null;
            while (matcher.find()) {
                strGroup = matcher.group();
                LogUtils.d("matcher-found", strGroup);
                if (TextUtils.isEmpty(strGroup) || !strGroup.matches(patternCompile.pattern())) {
                    strGroup = "0";
                }
            }
            if (TextUtils.isEmpty(strGroup)) {
                strGroup = "0";
            }
            if ("0".equals(strGroup)) {
                dataBean.setUser_label("0");
                dataBean.setUser_label_Xuni("0");
            }
            try {
                dataBean.setCount(Integer.parseInt(strGroup));
                view.setFocusable(false);
                editText.setKeyListener(null);
                editText.setTag(null);
                ((EditText) view).setCursorVisible(false);
                BiaoPupNewWindow.this.mBiaoqianInterface.mBiaoianLinster(getData(), baseViewHolder.getLayoutPosition(), true);
                view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.s
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f16875c.notifyDataSetChanged();
                    }
                }, 500L);
            } catch (Exception unused) {
                ToastUtil.shortToast(BiaoPupNewWindow.this.context, "请输入正确的数字");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$5(View view) {
            BiaoPupNewWindow.this.userClick(((Integer) view.getTag()).intValue());
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder viewHolder, final BiaoqianBeab.DataBean dataBean) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = (viewHolder.getLayoutPosition() == 0 || viewHolder.getLayoutPosition() % 3 == 0) ? 0 : CommonUtil.dip2px(getContext(), 16.0f);
            viewHolder.itemView.setLayoutParams(layoutParams);
            final EditText editText = (EditText) viewHolder.getView(R.id.biaoqiantv);
            TextView textView = (TextView) viewHolder.getView(R.id.tvTag);
            LinearLayout linearLayout = (LinearLayout) viewHolder.getView(R.id.ll_admin);
            boolean zEquals = dataBean.getUser_label().equals("1");
            Resources.Theme theme = getContext().getTheme();
            int[] iArr = new int[1];
            iArr[0] = zEquals ? R.attr.bg_question_tag_select : R.attr.bg_question_tag_unselect;
            TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(iArr);
            editText.setEnabled(false);
            editText.setTag(dataBean.getLabel());
            textView.setTag(Integer.valueOf(viewHolder.getLayoutPosition()));
            BiaoPupNewWindow.this.tagEditViewMap.put(viewHolder.getLayoutPosition(), editText);
            if ("1".equals(UserConfig.getInstance().getUser().getAdmin())) {
                editText.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.t
                    @Override // java.lang.Runnable
                    public final void run() {
                        editText.setEnabled(true);
                    }
                }, 100L);
                linearLayout.setVisibility(0);
                linearLayout.setBackground(typedArrayObtainStyledAttributes.getDrawable(0));
                textView.setVisibility(8);
                editText.setFilters(new InputFilter[]{new InputFilter() { // from class: com.psychiatrygarden.widget.u
                    @Override // android.text.InputFilter
                    public final CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
                        return this.f16939a.lambda$convert$1(charSequence, i2, i3, spanned, i4, i5);
                    }
                }});
                editText.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f16972c.lambda$convert$3(editText, dataBean, view);
                    }
                });
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.psychiatrygarden.widget.w
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z2) {
                        this.f17012c.lambda$convert$4(editText, dataBean, viewHolder, view, z2);
                    }
                });
            } else {
                linearLayout.setVisibility(8);
                textView.setVisibility(0);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.x
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f17060c.lambda$convert$5(view);
                    }
                });
                textView.setBackground(typedArrayObtainStyledAttributes.getDrawable(0));
            }
            LogUtils.d("convert", zEquals + "");
            BiaoPupNewWindow.this.setContent(dataBean, dataBean.getLabel(), viewHolder.getLayoutPosition(), viewHolder, "1".equals(UserConfig.getInstance().getUser().getAdmin()) ^ true);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public BiaoPupNewWindow(@NonNull Context mContext, List<BiaoqianBeab.DataBean> dataBiaoBIao, final BiaoqianCallbackInterface mBiaoqianInterfaceListenr) {
        super(mContext);
        this.tagEditViewMap = new SparseArray<>();
        this.context = mContext;
        this.dataBiaoBIao = dataBiaoBIao;
        this.mBiaoqianInterface = mBiaoqianInterfaceListenr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        KeyboardUtils.hideSoftInput(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$1(View view, MotionEvent motionEvent) {
        this.linrel.setFocusable(true);
        this.linrel.setFocusableInTouchMode(true);
        this.linrel.findFocus();
        this.linrel.requestFocus();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$2(View view, MotionEvent motionEvent) {
        this.linrel.setFocusable(true);
        this.linrel.setFocusableInTouchMode(true);
        this.linrel.findFocus();
        this.linrel.requestFocus();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$3(View view, MotionEvent motionEvent) {
        this.linrel.setFocusable(true);
        this.linrel.setFocusableInTouchMode(true);
        this.linrel.findFocus();
        this.linrel.requestFocus();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        userClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContent(BiaoqianBeab.DataBean dataBean, String labelText, int position, BaseViewHolder viewHolder, boolean showCount) {
        boolean zEquals = dataBean.getUser_label().equals("1");
        boolean z2 = SkinManager.getCurrentSkinType(getContext()) == 1;
        int color = Color.parseColor(z2 ? position <= 2 ? "#7380A9" : "#575F79" : position <= 2 ? "#303030" : "#909090");
        EditText editText = (EditText) viewHolder.getView(R.id.biaoqiantv);
        TextView textView = (TextView) viewHolder.getView(R.id.tvPreTag);
        TextView textView2 = (TextView) viewHolder.getView(R.id.tvTag);
        if (dataBean.getCount() > 0 && zEquals) {
            color = Color.parseColor(z2 ? "#B2575C" : "#DD594A");
        }
        if (dataBean.getCount() < 0) {
            textView2.setText(dataBean.getLabel());
        } else if (showCount) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append((CharSequence) labelText).setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(getContext(), 12.0f)), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.append((CharSequence) "\n");
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append((CharSequence) String.valueOf(dataBean.getCount())).setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(getContext(), 14.0f)), length, spannableStringBuilder.length(), 34);
            spannableStringBuilder.setSpan(new StyleSpan(1), length, spannableStringBuilder.length(), 34);
            textView2.setText(spannableStringBuilder);
        } else {
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
            spannableStringBuilder2.append((CharSequence) labelText).setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(getContext(), 12.0f)), 0, spannableStringBuilder2.length(), 18);
            textView.setText(spannableStringBuilder2);
            editText.setTextSize(14.0f);
            editText.setTypeface(Typeface.DEFAULT_BOLD);
            editText.setText(String.valueOf(dataBean.getCount()));
            editText.setSelection(editText.getText().length());
        }
        editText.setTextColor(color);
        textView.setTextColor(color);
        textView2.setTextColor(color);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void userClick(int position) {
        List<BiaoqianBeab.DataBean> list;
        if ("1".equals(UserConfig.getInstance().getUser().getAdmin()) || (list = this.dataBiaoBIao) == null || list.isEmpty()) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.dataBiaoBIao.size(); i3++) {
            if (this.dataBiaoBIao.get(i3).getUser_label().equals("1")) {
                i2++;
            }
        }
        if (i2 >= 2 && this.dataBiaoBIao.get(position).getUser_label().equals("0")) {
            ToastUtil.shortToast(this.context, "最多选择两个标签！");
            return;
        }
        if (this.dataBiaoBIao.get(position).getUser_label().equals("1")) {
            this.dataBiaoBIao.get(position).setUser_label("0");
            this.dataBiaoBIao.get(position).setCount(Math.max(this.dataBiaoBIao.get(position).getCount() - 1, 0));
        } else {
            this.dataBiaoBIao.get(position).setUser_label("1");
            this.dataBiaoBIao.get(position).setCount(this.dataBiaoBIao.get(position).getCount() + 1);
        }
        this.mBiaoqianInterface.mBiaoianLinster(this.dataBiaoBIao, position, false);
        this.adapter.notifyDataSetChanged();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_answer_question_select_tag;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        return (getContext().getResources().getDisplayMetrics().heightPixels - CommonUtil.dip2px(this.context, 48.0f)) - StatusBarUtil.getStatusBarHeight(getContext());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.titleTv);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linrel);
        this.linrel = linearLayout;
        Drawable background = linearLayout.getBackground();
        if (background != null && (background instanceof GradientDrawable)) {
            int iDip2px = CommonUtil.dip2px(getContext(), 8.0f);
            GradientDrawable gradientDrawable = (GradientDrawable) background.mutate();
            float f2 = iDip2px;
            gradientDrawable.setCornerRadii(new float[]{f2, f2, f2, f2, 0.0f, 0.0f, 0.0f, 0.0f});
            this.linrel.setBackground(gradientDrawable);
        }
        ((ImageView) findViewById(R.id.chose)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16693c.lambda$onCreate$0(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTags);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_question_tag_new, this.dataBiaoBIao);
        this.adapter = anonymousClass1;
        recyclerView.setAdapter(anonymousClass1);
        if ("1".equals(UserConfig.getInstance().getUser().getAdmin())) {
            textView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.n
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f16720c.lambda$onCreate$1(view, motionEvent);
                }
            });
            this.linrel.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.o
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f16745c.lambda$onCreate$2(view, motionEvent);
                }
            });
            recyclerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.p
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f16772c.lambda$onCreate$3(view, motionEvent);
                }
            });
        }
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.q
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16805c.lambda$onCreate$4(baseQuickAdapter, view, i2);
            }
        });
    }
}
