package com.psychiatrygarden.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.PopupInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.adapter.MockTestReviewAdapter;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.QuestionIndexBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class SubQueDaAdapter extends PagerAdapter {
    private Context context;
    private int curPos;
    private int currentShareStemPosition;
    private Drawable drawable;
    private boolean fromMyExam;
    private int lineHeight;
    private int maxTopHeight;
    private int moudle_type;
    float offsetDiff;
    List<ExesQuestionBean> questBeans;
    private ArrayMap<String, List<ExesQuestionBean>> shareStemMap;
    float startRawY;
    private Map<Integer, View> map = new HashMap();
    private final SparseArray<Boolean> expandStatusArray = new SparseArray<>();
    private final SparseArray<Boolean> scrollStateArr = new SparseArray<>();
    private final SparseArray<Integer> titleHeightArr = new SparseArray<>();
    private final SparseArray<Integer> topHeightArr = new SparseArray<>();
    private final SparseArray<Integer> dragViewHeightArr = new SparseArray<>();
    private final SparseArray<Integer> stemViewHeightArr = new SparseArray<>();
    private final SparseArray<Boolean> singleLineTitleArr = new SparseArray<>();
    SparseArray<Integer> pageStateArr = new SparseArray<>();
    private long lastTime = 0;

    public SubQueDaAdapter(Context context, List<ExesQuestionBean> questBeans, int moudle_type, boolean fromMyExam) {
        this.context = context;
        this.questBeans = questBeans;
        this.fromMyExam = fromMyExam;
        this.moudle_type = moudle_type;
        WeakReference weakReference = new WeakReference(this.drawable);
        this.drawable = weakReference.get() != null ? (Drawable) weakReference.get() : ContextCompat.getDrawable(context, R.drawable.huanyuan);
        this.maxTopHeight = 0;
        int statusBarHeight = 0 + StatusBarUtil.getStatusBarHeight(context);
        this.maxTopHeight = statusBarHeight;
        this.maxTopHeight = statusBarHeight + CommonUtil.dip2px(context, 494.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execTopMarinAnimation(final boolean showTitle, final View view, int... values) {
        int i2 = values[values.length - 1];
        if (view == null || view.findViewById(R.id.ll_child_question).getTop() == i2) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(values);
        final View viewFindViewById = view.findViewById(R.id.nsl);
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        Boolean bool = this.singleLineTitleArr.get(((Integer) view.getTag()).intValue());
        if (bool == null) {
            bool = Boolean.TRUE;
        }
        if (bool.booleanValue()) {
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.adapter.SubQueDaAdapter.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (((Boolean) SubQueDaAdapter.this.scrollStateArr.get(((Integer) view.getTag()).intValue())) == null) {
                        SubQueDaAdapter.this.scrollStateArr.put(((Integer) view.getTag()).intValue(), Boolean.TRUE);
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
                    layoutParams.height = showTitle ? view.findViewById(R.id.ll_child_question).getTop() - SubQueDaAdapter.this.lineHeight : 0;
                    viewFindViewById.setLayoutParams(layoutParams);
                }
            });
        }
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.adapter.ef
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SubQueDaAdapter.lambda$execTopMarinAnimation$11(view, valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
    }

    private String getTypeStr(String type) throws Resources.NotFoundException {
        int i2;
        String[] stringArray = this.context.getResources().getStringArray(R.array.exam_type);
        if (type == null) {
            return null;
        }
        if (type.equals(Constants.VIA_REPORT_TYPE_DATALINE)) {
            return "判断题";
        }
        if (!type.matches("-?\\d+.?\\d*") || (i2 = Integer.parseInt(type)) < 1) {
            return null;
        }
        int i3 = i2 - 1;
        if (i3 < stringArray.length) {
            return stringArray[i3];
        }
        if (i3 == stringArray.length) {
            return stringArray[stringArray.length - 1];
        }
        return null;
    }

    private void initRestoreData(TextView tv_restore, ExesQuestionBean bean, RoundedImageView img_original, LinearLayout mLyRestore) {
        String restore = bean.getRestore();
        tv_restore.setVisibility(TextUtils.isEmpty(restore) ? 8 : 0);
        boolean z2 = SkinManager.getCurrentSkinType(tv_restore.getContext()) == 1;
        if (TextUtils.isEmpty(restore) && TextUtils.isEmpty(bean.getRestore_img())) {
            mLyRestore.setVisibility(8);
            return;
        }
        if (!TextUtils.isEmpty(restore)) {
            tv_restore.setTextColor(Color.parseColor(z2 ? "#7380A9" : "#141516"));
            tv_restore.setGravity(GravityCompat.START);
            tv_restore.setTextSize(16.0f);
            String restore2 = bean.getRestore();
            Matcher matcher = Pattern.compile("[(（]([\\w*]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(restore2);
            Context context = this.context;
            ContextCompat.getColorStateList(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.app_theme_red : R.color.jiucuo_night);
            Context context2 = this.context;
            ColorStateList colorStateList = ContextCompat.getColorStateList(context2, SkinManager.getCurrentSkinType(context2) == 0 ? R.color.black : R.color.question_color_night);
            int i2 = 34;
            if (SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_restore_img, this.context).equals("1")) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(restore2);
                while (matcher.find()) {
                    spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, ContextCompat.getColorStateList(this.context, R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
                }
                Matcher matcher2 = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(restore2);
                while (matcher2.find()) {
                    int i3 = i2;
                    spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList, null), matcher2.start(0), matcher2.end(0), i3);
                    i2 = i3;
                }
                tv_restore.setText(spannableStringBuilder);
            } else {
                final ArrayList arrayList = new ArrayList();
                int i4 = 0;
                while (matcher.find()) {
                    String strGroup = matcher.group();
                    restore2 = restore2.substring(0, matcher.end(0) + i4) + "&" + restore2.substring(matcher.end(0) + i4);
                    String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.context);
                    StringBuilder sb = new StringBuilder();
                    sb.append("https://ykb-app-files.yikaobang.com.cn/exam/restore/");
                    sb.append(strConfig);
                    sb.append("/");
                    sb.append(bean.getQuestion_id());
                    sb.append(strGroup.substring(1, strGroup.length() - 1));
                    sb.append("-");
                    i4++;
                    sb.append(i4);
                    sb.append(".jpg?x-oss-process=style/water_mark");
                    arrayList.add(sb.toString());
                }
                Matcher matcher3 = Pattern.compile("[(（]([\\w*]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(restore2);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(restore2);
                Paint.FontMetrics fontMetrics = tv_restore.getPaint().getFontMetrics();
                int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
                this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
                final int i5 = 0;
                while (matcher3.find()) {
                    spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, 1), matcher3.end(0), matcher3.end(0) + 1, 33);
                    spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.adapter.lf
                        @Override // com.psychiatrygarden.interfaceclass.DomoIml
                        public final void clickToast() {
                            this.f14734a.lambda$initRestoreData$12(arrayList, i5);
                        }
                    }), matcher3.start(0), matcher3.end(0), 33);
                    tv_restore.setMovementMethod(LinkMovementMethod.getInstance());
                    i5++;
                }
                Matcher matcher4 = Pattern.compile("[(（]([\\w*]+)?[A-Z][对|错]+[)）]").matcher(restore2);
                while (matcher4.find()) {
                    spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList, null), matcher4.start(0), matcher4.end(0), 34);
                }
                Matcher matcher5 = Pattern.compile("[(（]([\\w*]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(restore2);
                while (matcher5.find()) {
                    spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.app_theme_red)), null), matcher5.start(0), matcher5.end(0), 34);
                }
                tv_restore.setText(spannableStringBuilder2);
            }
        }
        if (TextUtils.isEmpty(bean.getRestore_img())) {
            img_original.setVisibility(8);
        } else {
            img_original.setVisibility(0);
            Glide.with(this.context).load((Object) GlideUtils.generateUrl(bean.getRestore_img())).placeholder(new ColorDrawable(ContextCompat.getColor(img_original.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(img_original);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$execTopMarinAnimation$11(View view, ValueAnimator valueAnimator) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.findViewById(R.id.ll_child_question).getLayoutParams();
        layoutParams.topMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        view.findViewById(R.id.ll_child_question).setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$13(List list, View view) {
        CommonUtil.doPicture(this.context, list, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$14(List list, View view) {
        CommonUtil.doPicture(this.context, list, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$12(ArrayList arrayList, int i2) {
        CommonUtil.doPicture(this.context, arrayList, i2, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNormalContent$0(ImageView imageView, int i2, View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.context).setSingleSrcView(imageView, this.questBeans.get(i2).getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadNormalContent$1(TextView textView, View view) {
        int measuredHeight = textView.getMeasuredHeight();
        int measuredWidth = textView.getMeasuredWidth();
        if (measuredHeight <= 0 || measuredWidth <= 0) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_comment_bg);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = measuredWidth;
        layoutParams.height = measuredHeight;
        imageView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNormalContent$2(int i2, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) DiscussPublicActivity.class);
        intent.putExtra("obj_id", this.questBeans.get(i2).getQuestion_id() + "");
        intent.putExtra("module_type", this.moudle_type);
        intent.putExtra("comment_type", "2");
        intent.putExtra("title", "评论");
        intent.putExtra("commentEnum", DiscussStatus.CommentsOnTheEaluationModelTest);
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNormalContent$3(CharSequence charSequence) {
        ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNormalContent$4(CharSequence charSequence) {
        ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNormalContent$5(CharSequence charSequence) {
        ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadStemContent$10(View view, LinearLayout linearLayout, ViewPager viewPager, boolean z2, boolean z3, int i2) {
        if (this.lastTime == 0) {
            this.lastTime = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.lastTime < 300) {
            return;
        }
        int iIntValue = this.dragViewHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        if (z2) {
            int top2 = linearLayout.getTop();
            boolean zBooleanValue = this.singleLineTitleArr.get(((Integer) view.getTag()).intValue(), Boolean.FALSE).booleanValue();
            int iIntValue2 = this.topHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
            int iIntValue3 = this.titleHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
            if (i2 > 5) {
                if (top2 < (zBooleanValue ? iIntValue2 : iIntValue3) && !z3) {
                    view.findViewById(R.id.lineviewtype).setVisibility(0);
                    int[] iArr = new int[4];
                    iArr[0] = -iIntValue;
                    iArr[1] = 0;
                    iArr[2] = (!zBooleanValue ? iIntValue3 : iIntValue2) / 2;
                    if (!zBooleanValue) {
                        iIntValue2 = iIntValue3;
                    }
                    iArr[3] = iIntValue2;
                    execTopMarinAnimation(true, view, iArr);
                    this.expandStatusArray.put(viewPager.getCurrentItem(), Boolean.TRUE);
                }
            }
        } else {
            int i3 = -iIntValue;
            if (linearLayout.getTop() != i3 && i2 > 0) {
                execTopMarinAnimation(false, view, linearLayout.getTop(), 0, i3);
                view.findViewById(R.id.lineviewtype).setVisibility(4);
                this.expandStatusArray.put(viewPager.getCurrentItem(), Boolean.FALSE);
            }
        }
        this.lastTime = System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadStemContent$6(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
        int iIntValue = ((Integer) nestedScrollView.getTag()).intValue();
        Boolean bool = this.scrollStateArr.get(iIntValue);
        if (bool == null || !bool.booleanValue()) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nestedScrollView.getLayoutParams();
            layoutParams.height -= this.lineHeight;
            nestedScrollView.setLayoutParams(layoutParams);
            this.scrollStateArr.put(iIntValue, Boolean.TRUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadStemContent$7(View view) {
        this.dragViewHeightArr.put(((Integer) view.getTag()).intValue(), Integer.valueOf(view.findViewById(R.id.rl_drag_area).getMeasuredHeight()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadStemContent$8(View view, TextView textView, View view2, LinearLayout linearLayout) {
        int measuredHeight = view.getMeasuredHeight();
        this.lineHeight = textView.getLineHeight();
        this.topHeightArr.put(((Integer) view2.getTag()).intValue(), Integer.valueOf(measuredHeight));
        int measuredHeight2 = (view2.findViewById(R.id.rl_top_title).getVisibility() == 0 ? view2.findViewById(R.id.rl_top_title).getMeasuredHeight() : 0) + view2.findViewById(R.id.lineviewtype).getMeasuredHeight();
        if (textView.getLineCount() > 3) {
            int lineHeight = measuredHeight2 + (textView.getLineHeight() * 3) + CommonUtil.dip2px(this.context, 10.0f);
            this.titleHeightArr.put(((Integer) view2.getTag()).intValue(), Integer.valueOf(lineHeight));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view2.findViewById(R.id.nsl).getLayoutParams();
            layoutParams.height = lineHeight;
            view2.findViewById(R.id.nsl).setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.topMargin = lineHeight;
            linearLayout.setLayoutParams(layoutParams2);
        } else {
            this.titleHeightArr.put(((Integer) view2.getTag()).intValue(), Integer.valueOf(measuredHeight2));
            int iDip2px = measuredHeight + CommonUtil.dip2px(this.context, 10.0f);
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams3.topMargin = iDip2px;
            linearLayout.setLayoutParams(layoutParams3);
        }
        int measuredHeight3 = view2.findViewById(R.id.ll_stem_content).getMeasuredHeight();
        boolean z2 = textView.getLineCount() <= 3;
        this.stemViewHeightArr.put(((Integer) view2.getTag()).intValue(), Integer.valueOf(measuredHeight3));
        this.singleLineTitleArr.put(((Integer) view2.getTag()).intValue(), Boolean.valueOf(z2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$loadStemContent$9(View view, NestedScrollView nestedScrollView, View view2, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startRawY = motionEvent.getRawY();
            return true;
        }
        if (action != 2 || this.singleLineTitleArr.get(((Integer) view.getTag()).intValue()).booleanValue()) {
            return false;
        }
        this.offsetDiff = motionEvent.getRawY() - this.startRawY;
        int iIntValue = this.topHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        int iIntValue2 = this.stemViewHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        int iIntValue3 = this.titleHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        if (motionEvent.getRawY() - this.startRawY <= 0.0f) {
            if (motionEvent.getRawY() - this.startRawY < 0.0f) {
                if (view.findViewById(R.id.nsl).getHeight() > iIntValue3 - this.lineHeight) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.findViewById(R.id.nsl).getLayoutParams();
                    layoutParams.height = (int) (layoutParams.height - Math.abs(this.offsetDiff));
                    view.findViewById(R.id.nsl).setLayoutParams(layoutParams);
                } else {
                    nestedScrollView.setScrollbarFadingEnabled(false);
                }
                if (view.findViewById(R.id.ll_child_question).getTop() > iIntValue3) {
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.findViewById(R.id.ll_child_question).getLayoutParams();
                    layoutParams2.topMargin = (int) (layoutParams2.topMargin - Math.abs(this.offsetDiff));
                    view.findViewById(R.id.ll_child_question).setLayoutParams(layoutParams2);
                }
                this.startRawY = motionEvent.getRawY();
                return true;
            }
            return false;
        }
        if (view.findViewById(R.id.ll_child_question).getTop() > this.maxTopHeight) {
            return false;
        }
        if (view.findViewById(R.id.nsl).getHeight() < iIntValue2 + this.lineHeight) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view.findViewById(R.id.nsl).getLayoutParams();
            layoutParams3.height = (int) (layoutParams3.height + Math.abs(this.offsetDiff));
            view.findViewById(R.id.nsl).setLayoutParams(layoutParams3);
        }
        if (view.findViewById(R.id.ll_child_question).getTop() >= iIntValue + this.lineHeight) {
            view.findViewById(R.id.nsl).setScrollbarFadingEnabled(true);
            return false;
        }
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) view.findViewById(R.id.ll_child_question).getLayoutParams();
        layoutParams4.topMargin = (int) (layoutParams4.topMargin + this.offsetDiff);
        view.findViewById(R.id.ll_child_question).setLayoutParams(layoutParams4);
        this.startRawY = motionEvent.getRawY();
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(70:248|7|(3:9|(1:11)(1:12)|13)(3:14|(1:16)(1:17)|18)|19|20|(1:25)(1:24)|26|27|(1:29)(1:30)|31|(1:33)(1:34)|35|36|(1:38)(1:39)|40|(1:42)|43|44|(2:46|(3:48|(4:51|(3:253|53|256)(1:255)|254|49)|252)(3:54|(4:57|(3:273|59|(3:269|61|(3:267|63|290)(3:266|64|289))(3:265|65|(3:270|67|288)(3:268|68|287)))(3:264|69|(3:276|71|(3:274|73|286)(3:272|74|(3:275|76|285)(1:284)))(3:271|77|(3:278|79|283)(3:277|80|(3:279|82|282)(1:281))))|280|55)|263))(2:84|(2:86|(3:88|(1:90)|291)(3:91|(4:93|(3:95|(2:97|295)(2:98|(2:100|294)(1:296))|101)|293|102)|292))(52:103|(1:105)(2:106|(1:108))|109|(1:111)(1:112)|113|(1:115)(1:116)|117|(1:119)|120|(1:122)(1:123)|124|(1:126)(1:127)|128|(1:130)(2:131|(1:133)(2:134|(1:136)(2:137|(1:139)(1:140))))|141|(3:143|(2:145|(2:147|259)(2:148|260))(2:149|(2:151|261)(2:152|258))|153)|257|154|(1:156)(1:157)|158|(1:160)(1:161)|162|(5:164|(1:166)(1:167)|168|(3:170|(1:172)(1:173)|174)(1:175)|176)(1:177)|178|(3:180|(3:182|(1:184)(1:185)|186)(3:187|(1:189)(1:190)|191)|192)(1:193)|194|195|244|196|201|(2:204|202)|262|205|246|206|211|(1:213)(1:214)|215|250|216|221|(1:223)(1:224)|225|(1:227)|228|(1:230)|231|(1:233)|234|(1:236)|237|(2:239|298)(1:297)))|83|109|(0)(0)|113|(0)(0)|117|(0)|120|(0)(0)|124|(0)(0)|128|(0)(0)|141|(0)|257|154|(0)(0)|158|(0)(0)|162|(0)(0)|178|(0)(0)|194|195|244|196|201|(1:202)|262|205|246|206|211|(0)(0)|215|250|216|221|(0)(0)|225|(0)|228|(0)|231|(0)|234|(0)|237|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x085d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x085f, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x08e9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x08eb, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0942, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0944, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x04c7  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x04ca  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x04f1 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0520 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0571  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x05a6  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x05a8  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x05c4 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0669 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0685 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x06a7  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x06aa  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x06c3 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0733 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0742 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x07d0 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0894 A[Catch: Exception -> 0x0999, LOOP:2: B:202:0x088e->B:204:0x0894, LOOP_END, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0900  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0903  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x094d A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0958 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0966 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0970 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:233:0x097c A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0988 A[Catch: Exception -> 0x0999, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0993 A[Catch: Exception -> 0x0999, TRY_LEAVE, TryCatch #2 {Exception -> 0x0999, blocks: (B:7:0x0117, B:9:0x011b, B:13:0x0135, B:19:0x0183, B:22:0x0199, B:26:0x01a2, B:29:0x01c9, B:31:0x01ec, B:33:0x0217, B:35:0x025c, B:38:0x0272, B:40:0x02a5, B:42:0x02c7, B:43:0x02db, B:46:0x02f7, B:48:0x02fd, B:49:0x0301, B:51:0x0307, B:53:0x0317, B:109:0x04a9, B:113:0x04b9, B:117:0x04cb, B:119:0x04f1, B:120:0x04f9, B:122:0x0520, B:124:0x0554, B:128:0x0578, B:143:0x05c4, B:145:0x05cd, B:147:0x05d5, B:153:0x0610, B:148:0x05e2, B:149:0x05ef, B:151:0x05f7, B:152:0x0604, B:154:0x0616, B:156:0x0669, B:158:0x06a1, B:162:0x06ab, B:164:0x06c3, B:166:0x06dd, B:168:0x06e9, B:170:0x06ec, B:172:0x06f0, B:174:0x06ff, B:178:0x073c, B:180:0x0742, B:182:0x0784, B:186:0x0793, B:192:0x07ab, B:194:0x07f2, B:201:0x0862, B:202:0x088e, B:204:0x0894, B:205:0x08ba, B:211:0x08ee, B:215:0x0904, B:221:0x0947, B:223:0x094d, B:225:0x095f, B:227:0x0966, B:228:0x0969, B:230:0x0970, B:231:0x0975, B:233:0x097c, B:234:0x0981, B:236:0x0988, B:237:0x098d, B:239:0x0993, B:224:0x0958, B:220:0x0944, B:210:0x08eb, B:200:0x085f, B:187:0x0798, B:191:0x07a7, B:193:0x07d0, B:167:0x06e4, B:177:0x0733, B:157:0x0685, B:54:0x031b, B:55:0x031f, B:57:0x0325, B:59:0x033d, B:61:0x034f, B:63:0x0359, B:64:0x035d, B:65:0x0361, B:67:0x036b, B:68:0x036f, B:69:0x0373, B:71:0x0385, B:73:0x038f, B:74:0x0393, B:76:0x0399, B:77:0x039d, B:79:0x03a7, B:80:0x03ac, B:82:0x03b2, B:84:0x03bb, B:86:0x03c2, B:88:0x03c8, B:90:0x03d0, B:91:0x03e0, B:93:0x03ec, B:95:0x0405, B:97:0x0431, B:101:0x0454, B:98:0x043b, B:100:0x044b, B:102:0x045d, B:103:0x0468, B:105:0x0474, B:106:0x0483, B:108:0x0497, B:39:0x0299, B:34:0x0257, B:14:0x013c, B:18:0x017d, B:196:0x0833, B:206:0x08bf, B:216:0x0918), top: B:248:0x0117, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:297:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadNormalContent(final int r54, final android.view.View r55) {
        /*
            Method dump skipped, instructions count: 2463
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.adapter.SubQueDaAdapter.loadNormalContent(int, android.view.View):void");
    }

    private void loadStemContent(final int position, final View view) throws Resources.NotFoundException {
        final TextView textView = (TextView) view.findViewById(R.id.typeStr);
        view.findViewById(R.id.pagenumtv).setVisibility(8);
        view.setTag(Integer.valueOf(position));
        this.curPos = position;
        ExesQuestionBean exesQuestionBean = this.questBeans.get(position);
        final List<ExesQuestionBean> stemQuestionList = getStemQuestionList(this.questBeans.get(position).getPublic_number());
        textView.setText(stemQuestionList.get(0).getType_str());
        if (stemQuestionList.size() >= 5) {
            view.findViewById(R.id.iv_shadow_left).setVisibility(0);
            view.findViewById(R.id.iv_shadow_right).setVisibility(0);
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < stemQuestionList.size()) {
            StringBuilder sb = new StringBuilder();
            sb.append("第");
            int i3 = i2 + 1;
            sb.append(i3);
            sb.append("问");
            arrayList.add(new QuestionIndexBean(sb.toString(), String.valueOf(i3), i2 == 0));
            i2 = i3;
        }
        final TextView textView2 = (TextView) view.findViewById(R.id.titletv);
        final NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nsl);
        nestedScrollView.setTag(Integer.valueOf(position));
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.adapter.ze
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView2, int i4, int i5, int i6, int i7) {
                this.f15249a.lambda$loadStemContent$6(nestedScrollView2, i4, i5, i6, i7);
            }
        });
        textView2.setText(exesQuestionBean.getPublic_title());
        TextView textView3 = (TextView) view.findViewById(R.id.questiondetails_tv_title);
        textView3.setText(exesQuestionBean.getQuestion_type());
        textView3.setGravity(8388627);
        textView3.setTextSize(12.0f);
        textView3.setPadding(CommonUtil.dip2px(view.getContext(), 20.0f), CommonUtil.dip2px(view.getContext(), 7.0f), CommonUtil.dip2px(view.getContext(), 20.0f), CommonUtil.dip2px(view.getContext(), 7.0f));
        final MagicIndicator magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(view.getContext());
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new ShareStemQuestionAdapter(arrayList, viewPager));
        magicIndicator.setNavigator(commonNavigator);
        MockTestReviewAdapter mockTestReviewAdapter = new MockTestReviewAdapter(this.context, stemQuestionList, this.moudle_type, this.fromMyExam);
        viewPager.setAdapter(mockTestReviewAdapter);
        int jumpPosition = exesQuestionBean.getJumpPosition();
        if (jumpPosition != -1) {
            int i4 = 0;
            while (true) {
                if (i4 >= stemQuestionList.size()) {
                    break;
                }
                if (stemQuestionList.get(i4).getActualStemIndex() == jumpPosition) {
                    viewPager.setCurrentItem(i4, false);
                    magicIndicator.onPageSelected(i4);
                    this.currentShareStemPosition = i4;
                    break;
                }
                i4++;
            }
        }
        this.pageStateArr.put(position, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.adapter.SubQueDaAdapter.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position2, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position2, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int pos) {
                magicIndicator.onPageSelected(pos);
                SubQueDaAdapter.this.currentShareStemPosition = pos;
                Boolean bool = (Boolean) SubQueDaAdapter.this.expandStatusArray.get(pos);
                Integer num = SubQueDaAdapter.this.pageStateArr.get(position);
                if (bool == null) {
                    bool = Boolean.TRUE;
                }
                if (num != null) {
                    if (pos > num.intValue()) {
                        Boolean bool2 = (Boolean) SubQueDaAdapter.this.expandStatusArray.get(pos - 1);
                        if (bool2 == null) {
                            bool2 = Boolean.TRUE;
                        }
                        if (bool.booleanValue() == bool2.booleanValue()) {
                            return;
                        }
                    } else if (pos < num.intValue()) {
                        Boolean bool3 = (Boolean) SubQueDaAdapter.this.expandStatusArray.get(pos + 1);
                        if (bool3 == null) {
                            bool3 = Boolean.TRUE;
                        }
                        if (bool.booleanValue() == bool3.booleanValue()) {
                            return;
                        }
                    }
                }
                boolean zBooleanValue = ((Boolean) SubQueDaAdapter.this.singleLineTitleArr.get(((Integer) view.getTag()).intValue())).booleanValue();
                int iIntValue = ((Integer) SubQueDaAdapter.this.topHeightArr.get(((Integer) view.getTag()).intValue())).intValue();
                int iIntValue2 = ((Integer) SubQueDaAdapter.this.dragViewHeightArr.get(((Integer) view.getTag()).intValue())).intValue();
                int iIntValue3 = ((Integer) SubQueDaAdapter.this.titleHeightArr.get(((Integer) view.getTag()).intValue())).intValue();
                if (!zBooleanValue) {
                    iIntValue = iIntValue3;
                }
                SubQueDaAdapter subQueDaAdapter = SubQueDaAdapter.this;
                boolean zBooleanValue2 = bool.booleanValue();
                View view2 = view;
                int[] iArr = new int[2];
                iArr[0] = !bool.booleanValue() ? iIntValue : -iIntValue2;
                if (!bool.booleanValue()) {
                    iIntValue = -iIntValue2;
                }
                iArr[1] = iIntValue;
                subQueDaAdapter.execTopMarinAnimation(zBooleanValue2, view2, iArr);
                SubQueDaAdapter.this.expandStatusArray.put(position, bool);
                SubQueDaAdapter.this.pageStateArr.put(position, Integer.valueOf(pos));
                textView.setText(((ExesQuestionBean) stemQuestionList.get(pos)).getType_str());
            }
        });
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_child_question);
        final View viewFindViewById = view.findViewById(R.id.ll_top_content);
        this.expandStatusArray.put(viewPager.getCurrentItem(), Boolean.TRUE);
        linearLayout.post(new Runnable() { // from class: com.psychiatrygarden.adapter.ff
            @Override // java.lang.Runnable
            public final void run() {
                this.f14492c.lambda$loadStemContent$7(view);
            }
        });
        viewFindViewById.post(new Runnable() { // from class: com.psychiatrygarden.adapter.gf
            @Override // java.lang.Runnable
            public final void run() {
                this.f14533c.lambda$loadStemContent$8(viewFindViewById, textView2, view, linearLayout);
            }
        });
        view.findViewById(R.id.rl_drag_area).setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.adapter.hf
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f14573c.lambda$loadStemContent$9(view, nestedScrollView, view2, motionEvent);
            }
        });
        mockTestReviewAdapter.setScrollStateListener(new MockTestReviewAdapter.ScrollStateChangeListener() { // from class: com.psychiatrygarden.adapter.if
            @Override // com.psychiatrygarden.adapter.MockTestReviewAdapter.ScrollStateChangeListener
            public final void onScrollStop(boolean z2, boolean z3, int i5) {
                this.f14618a.lambda$loadStemContent$10(view, linearLayout, viewPager, z2, z3, i5);
            }
        });
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        this.map.remove(Integer.valueOf(position));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(@NonNull View arg0) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.questBeans.size();
    }

    public int getCurrentShareStemQuestionPosition() {
        return this.currentShareStemPosition;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object object) {
        return -2;
    }

    public View getPageView(int pos) {
        return this.map.get(Integer.valueOf(pos));
    }

    public List<ExesQuestionBean> getStemQuestionList(String publicNumber) {
        return this.shareStemMap.get(publicNumber);
    }

    public void initAnsData(ExesQuestionBean bean, RoundedImageView mImgExplain, TextView questiondetails_tv_contents, TextView tv_encyclopedia_contents, RoundedImageView mImgEncyclopedia, LinearLayout mLyExpline, LinearLayout mLyOption, LinearLayout mLyRestore, View mLineExpline, View mLineRestore) {
        int i2;
        int i3;
        String explain = bean.getExplain();
        String explain_img = bean.getExplain_img();
        boolean z2 = SkinManager.getCurrentSkinType(mImgEncyclopedia.getContext()) == 1;
        if (TextUtils.isEmpty(explain) && TextUtils.isEmpty(explain_img)) {
            mLyExpline.setVisibility(8);
            i2 = 8;
        } else {
            if (TextUtils.isEmpty(explain)) {
                questiondetails_tv_contents.setVisibility(8);
            } else {
                questiondetails_tv_contents.setTextColor(z2 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
                questiondetails_tv_contents.setGravity(GravityCompat.START);
                questiondetails_tv_contents.setTextSize(16.0f);
                questiondetails_tv_contents.setVisibility(0);
                if (TextUtils.isEmpty(explain_img)) {
                    mImgExplain.setVisibility(8);
                } else {
                    mImgExplain.setVisibility(0);
                    Glide.with(this.context).load((Object) GlideUtils.generateUrl(explain_img)).placeholder(new ColorDrawable(ContextCompat.getColor(mImgExplain.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(mImgExplain);
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(explain);
                Matcher matcher = Pattern.compile("[(（]([\\w*]+)?[A-Z][对|错]+[)）]").matcher(explain);
                Context context = this.context;
                ColorStateList colorStateList = ContextCompat.getColorStateList(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.black : R.color.question_color_night);
                while (matcher.find()) {
                    spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList, null), matcher.start(0), matcher.end(0), 34);
                    colorStateList = colorStateList;
                }
                questiondetails_tv_contents.setText(spannableStringBuilder);
            }
            if (TextUtils.isEmpty(explain_img)) {
                i2 = 8;
                mImgExplain.setVisibility(8);
            } else {
                mImgExplain.setVisibility(0);
                Glide.with(this.context).load((Object) GlideUtils.generateUrl(explain_img)).placeholder(new ColorDrawable(ContextCompat.getColor(mImgExplain.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(mImgExplain);
                i2 = 8;
            }
        }
        if (TextUtils.isEmpty(bean.getOption_analysis()) && TextUtils.isEmpty(bean.getOption_analysis_img())) {
            mLyOption.setVisibility(i2);
            mLineExpline.setVisibility(i2);
        } else {
            mLineExpline.setVisibility(0);
            if (TextUtils.isEmpty(bean.getOption_analysis())) {
                i3 = 0;
                tv_encyclopedia_contents.setVisibility(8);
            } else {
                tv_encyclopedia_contents.setVisibility(0);
                tv_encyclopedia_contents.setTextColor(z2 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
                tv_encyclopedia_contents.setGravity(GravityCompat.START);
                tv_encyclopedia_contents.setTextSize(16.0f);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(bean.getOption_analysis());
                Matcher matcher2 = Pattern.compile("[(（]([\\w*]+)?[A-Z][对|错]+[)）]").matcher(bean.getOption_analysis());
                Context context2 = this.context;
                ColorStateList colorStateList2 = ContextCompat.getColorStateList(context2, SkinManager.getCurrentSkinType(context2) == 0 ? R.color.black : R.color.question_color_night);
                while (matcher2.find()) {
                    spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList2, null), matcher2.start(0), matcher2.end(0), 34);
                }
                i3 = 0;
                tv_encyclopedia_contents.setText(spannableStringBuilder2);
            }
            if (TextUtils.isEmpty(bean.getOption_analysis_img())) {
                i2 = 8;
                mImgEncyclopedia.setVisibility(8);
            } else {
                mImgEncyclopedia.setVisibility(i3);
                Glide.with(this.context).load((Object) GlideUtils.generateUrl(bean.getOption_analysis_img())).placeholder(new ColorDrawable(ContextCompat.getColor(mImgEncyclopedia.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(mImgEncyclopedia);
                i2 = 8;
            }
            if (mLyRestore.getVisibility() == 0 && mLyExpline.getVisibility() == i2) {
                mLineRestore.setVisibility(0);
            }
        }
        if (mLyExpline.getVisibility() == i2 && mLyOption.getVisibility() == i2 && mLyRestore.getVisibility() == 0) {
            mLineRestore.setVisibility(i2);
        }
        final ArrayList arrayList = new ArrayList();
        arrayList.add(explain_img);
        mImgExplain.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.jf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14660c.lambda$initAnsData$13(arrayList, view);
            }
        });
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add(bean.getOption_analysis_img());
        mImgEncyclopedia.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.kf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14690c.lambda$initAnsData$14(arrayList2, view);
            }
        });
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    @SuppressLint({"NewApi"})
    public Object instantiateItem(@NonNull ViewGroup container, final int position) throws Resources.NotFoundException {
        ExesQuestionBean exesQuestionBean = this.questBeans.get(position);
        this.curPos = position;
        View viewInflate = LayoutInflater.from(this.context).inflate(TextUtils.isEmpty(exesQuestionBean.getPublic_number()) ? R.layout.item_mock_test_review_no_share_stem : R.layout.item_share_stem_question, (ViewGroup) null);
        viewInflate.setTag(Integer.valueOf(position));
        if (TextUtils.isEmpty(exesQuestionBean.getPublic_number())) {
            loadNormalContent(position, viewInflate);
        } else {
            loadStemContent(position, viewInflate);
        }
        container.addView(viewInflate);
        this.map.put(Integer.valueOf(position), viewInflate);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View v2, @NonNull Object arg1) {
        return v2 == arg1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    public void setShareStemMap(ArrayMap<String, List<ExesQuestionBean>> data) {
        this.shareStemMap = data;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(@NonNull View arg0) {
    }

    public void updateData(List<ExesQuestionBean> questBeanss) {
        this.questBeans.clear();
        this.questBeans.addAll(questBeanss);
        notifyDataSetChanged();
    }
}
