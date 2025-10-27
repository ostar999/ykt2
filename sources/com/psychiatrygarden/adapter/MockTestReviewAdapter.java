package com.psychiatrygarden.adapter;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.PopupInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.SelectableTextHelper;
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
public class MockTestReviewAdapter extends PagerAdapter {
    private Context context;
    private Drawable drawable;
    private boolean fromMyExam;
    private ScrollStateChangeListener mListener;
    private SelectableTextHelper mSelectableTextHelper;
    private Map<Integer, View> map = new HashMap();
    private int moudle_type;
    List<ExesQuestionBean> questBeans;

    public interface ScrollStateChangeListener {
        void onScrollStop(boolean upScroll, boolean scroll2Bottom, int distance);
    }

    public MockTestReviewAdapter(Context context, List<ExesQuestionBean> questBeans, int moudle_type, boolean fromMyExam) {
        this.context = context;
        this.questBeans = questBeans;
        this.fromMyExam = fromMyExam;
        this.moudle_type = moudle_type;
        WeakReference weakReference = new WeakReference(this.drawable);
        this.drawable = weakReference.get() != null ? (Drawable) weakReference.get() : ContextCompat.getDrawable(context, R.drawable.huanyuan);
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
        boolean z2 = SkinManager.getCurrentSkinType(tv_restore.getContext()) == 1;
        tv_restore.setVisibility(TextUtils.isEmpty(restore) ? 8 : 0);
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
                    spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.adapter.ya
                        @Override // com.psychiatrygarden.interfaceclass.DomoIml
                        public final void clickToast() {
                            this.f15206a.lambda$initRestoreData$6(arrayList, i5);
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
    public /* synthetic */ void lambda$initAnsData$7(List list, View view) {
        CommonUtil.doPicture(this.context, list, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$8(List list, View view) {
        CommonUtil.doPicture(this.context, list, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$6(ArrayList arrayList, int i2) {
        CommonUtil.doPicture(this.context, arrayList, i2, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$0(ImageView imageView, int i2, View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.context).setSingleSrcView(imageView, this.questBeans.get(i2).getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$instantiateItem$1(TextView textView, View view) {
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
    public /* synthetic */ void lambda$instantiateItem$2(int i2, View view) {
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
    public /* synthetic */ void lambda$instantiateItem$3(CharSequence charSequence) {
        ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$4(CharSequence charSequence) {
        ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$5(CharSequence charSequence) {
        ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
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

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object object) {
        return -2;
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
        mImgExplain.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.wa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15140c.lambda$initAnsData$7(arrayList, view);
            }
        });
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add(bean.getOption_analysis_img());
        mImgEncyclopedia.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.xa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15172c.lambda$initAnsData$8(arrayList2, view);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:274:0x09eb  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x09f4  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x09fd  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0a08  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0a13  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0a1f  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0a30  */
    /* JADX WARN: Type inference failed for: r11v32, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v38 */
    /* JADX WARN: Type inference failed for: r11v41 */
    /* JADX WARN: Type inference failed for: r11v42 */
    /* JADX WARN: Type inference failed for: r11v55 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v7, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v2, types: [androidx.recyclerview.widget.RecyclerView, com.psychiatrygarden.widget.MaxRecyclerView] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    @Override // androidx.viewpager.widget.PagerAdapter
    @androidx.annotation.NonNull
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object instantiateItem(@androidx.annotation.NonNull android.view.ViewGroup r60, final int r61) {
        /*
            Method dump skipped, instructions count: 2614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.adapter.MockTestReviewAdapter.instantiateItem(android.view.ViewGroup, int):java.lang.Object");
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

    public void setScrollStateListener(ScrollStateChangeListener listener) {
        this.mListener = listener;
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
