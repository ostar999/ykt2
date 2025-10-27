package com.psychiatrygarden.fragmenthome;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommMentList2Activity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteNewActivity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.activity.SubQuestionMainActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.QuestionDataStaticSetListBean;
import com.psychiatrygarden.bean.RecdQuestionBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.BiaoqianInterface;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.AutoHeightViewPager;
import com.psychiatrygarden.widget.BiaoPupWindow;
import com.psychiatrygarden.widget.CirclePoint;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.MyListView;
import com.psychiatrygarden.widget.OnSelectListener;
import com.psychiatrygarden.widget.SelectableTextHelper;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubQuestionFragment extends BaseFragment {
    private TextView biaotxt;
    ColorStateList blackColors;
    private CirclePoint circlepoint;
    private Drawable drawable;
    ColorStateList grayColors;
    private ImageView imgtitle;
    private LinearLayout lineout;
    private LinearLayout ll_more_columns;
    private LinearLayout ly_questiondetails_btn_centerMsg;
    private LinearLayout ly_questiondetails_btn_collect;
    private LinearLayout ly_questiondetails_btn_edit;
    private LinearLayout ly_questiondetails_btn_zantong;
    private CommAdapter<RecdQuestionBean.DataBean.OptionBean> mAdapter;
    private LinearLayout mRadioGroup_content;
    private SelectableTextHelper mSelectableTextHelper;
    private RecdQuestionBean.DataBean mUpdateBean;
    private PopupWindow popupWindow_note;
    private LinearLayout questiondetails_bottom_layout;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private Button questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private Button questiondetails_btn_pushAnswer;
    private ImageView questiondetails_btn_zantong;
    private LinearLayout questiondetails_layout_diff;
    private MyListView questiondetails_listView;
    private TextView questiondetails_tv_content_ques;
    private TextView questiondetails_tv_content_ques1;
    private TextView questiondetails_tv_contents;
    private TextView questiondetails_tv_statistics;
    ColorStateList redColors;
    int redSelected;
    int redTransSelected;
    private RelativeLayout rl_question_video;
    private String show_restore_img = "1";
    private AutoHeightViewPager viewpager_question_video;

    /* renamed from: com.psychiatrygarden.fragmenthome.SubQuestionFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends CommAdapter<RecdQuestionBean.DataBean.OptionBean> {
        public AnonymousClass3(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ImageView imageView, RecdQuestionBean.DataBean.OptionBean optionBean, View view) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(SubQuestionFragment.this.getActivity()).setSingleSrcView(imageView, optionBean.getImg()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, final RecdQuestionBean.DataBean.OptionBean optionBean, int position) {
            TextView textView = (TextView) vHolder.getView(R.id.QuestionOptions_item_tv_content);
            ImageView imageView = (ImageView) vHolder.getView(R.id.QuestionOptions_item_img_select);
            final ImageView imageView2 = (ImageView) vHolder.getView(R.id.optionimg);
            if (optionBean.getImg() == null || optionBean.getImg().equals("")) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(optionBean.getImg())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image)).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.3.2
                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView2.setImageResource(R.drawable.imgplacehodel_image);
                        return true;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageView2.setImageDrawable(resource);
                        return true;
                    }
                }).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.3.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                        onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
                    }

                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource != null) {
                            float intrinsicWidth = resource.getIntrinsicWidth();
                            float intrinsicHeight = resource.getIntrinsicHeight();
                            float width = imageView2.getWidth();
                            if (width == 0.0f) {
                                width = imageView2.getResources().getDisplayMetrics().widthPixels;
                            }
                            int i2 = (int) ((intrinsicHeight / intrinsicWidth) * width);
                            ViewGroup.LayoutParams layoutParams = imageView2.getLayoutParams();
                            if (i2 >= 4096) {
                                layoutParams.height = R2.color.N_stretchTextColor;
                                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                layoutParams.height = -1;
                            }
                            imageView2.setLayoutParams(layoutParams);
                        }
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.pg
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15922c.lambda$convert$0(imageView2, optionBean, view);
                    }
                });
            }
            textView.setText(String.format("%s.%s", optionBean.getKey(), optionBean.getTitle()));
            String type = optionBean.getType();
            if (type.equals("2")) {
                if (SkinManager.getCurrentSkinType(SubQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.green_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_ok));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.green_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_ok_night));
                    return;
                }
            }
            if (type.equals("3")) {
                if (SkinManager.getCurrentSkinType(SubQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.red_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_error));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.red_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_error_night));
                    return;
                }
            }
            if (type.equals("4")) {
                if (SkinManager.getCurrentSkinType(SubQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.red_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_ok_lack));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.red_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_ok_lack_night));
                    return;
                }
            }
            if (type.equals("1")) {
                if (SkinManager.getCurrentSkinType(SubQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.black));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_yes));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.question_color_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_yes_night));
                    return;
                }
            }
            if (SkinManager.getCurrentSkinType(SubQuestionFragment.this.getActivity()) == 0) {
                textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.black));
                imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_no));
            } else {
                textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubQuestionFragment.this).mContext, R.color.question_color_night));
                imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubQuestionFragment.this).mContext, R.drawable.icon_options_select_no_night));
            }
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.SubQuestionFragment$7, reason: invalid class name */
    public class AnonymousClass7 extends AjaxCallBack<String> {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list, View view) {
            SubQuestionFragment.this.lambda$initBiaoQianData$14(list, view);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            try {
                SubQuestionFragment.this.biaotxt.setText("标签：？");
                SubQuestionFragment.this.mRadioGroup_content.removeAllViews();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass7) s2);
            try {
                BiaoqianBeab biaoqianBeab = (BiaoqianBeab) new Gson().fromJson(s2, BiaoqianBeab.class);
                if (biaoqianBeab.getCode().equals("200")) {
                    final List<BiaoqianBeab.DataBean> data = biaoqianBeab.getData();
                    SubQuestionFragment.this.initBiaoQianData(data);
                    SubQuestionFragment.this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.qg
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f15948c.lambda$onSuccess$0(data, view);
                        }
                    });
                } else {
                    SubQuestionFragment.this.biaotxt.setText("标签：？");
                    SubQuestionFragment.this.mRadioGroup_content.removeAllViews();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ObServerData$20(ObservableEmitter observableEmitter) throws Exception {
        ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().insertOrReplace(new SubmitAnsweredQuestionBean(Long.valueOf(Long.parseLong(this.mUpdateBean.getQuestion_id())), this.mUpdateBean.getOwnerAns(), this.mUpdateBean.getIsRight().trim(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.mUpdateBean.getQuestion_id() + "mengdepeng" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerData$21(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ObServerDataCollect$18(int i2, ObservableEmitter observableEmitter) throws Exception {
        if (i2 == 1) {
            submitFavorites();
        } else {
            clearCollection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerDataCollect$19(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$23(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$24(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$25(String str, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        intent.putExtra("notestr", str);
        intent.putExtra("noteStatus", this.mUpdateBean.getmStaticsData().getIs_note() + "");
        startActivity(intent);
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$26() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$17(ArrayList arrayList, int i2) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(new ArrayList(arrayList)).setSrcView(null, i2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$10(CharSequence charSequence) {
        try {
            ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$15(CharSequence charSequence) {
        ((ClipboardManager) getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$16(CharSequence charSequence) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSelectData$11(AdapterView adapterView, View view, int i2, long j2) {
        if (this.mUpdateBean.getIsNotAll() == 1 || !SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, getActivity())) {
            if (this.mUpdateBean.getOwnerAns().equals("")) {
                doSelectOption(i2);
                this.mAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (this.mUpdateBean.getIsdoType() != 1) {
            doSelectOption(i2);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$12(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(getActivity()).setSingleSrcView(this.imgtitle, this.mUpdateBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CommMentList2Activity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", this.mUpdateBean.getmStaticsData().getIs_comment() != 0);
        intent.putExtra("isZantongTrue", this.mUpdateBean.getmStaticsData().getIs_praise() != 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionCorrectionActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id() + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewCom", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewComzantong", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(View view) {
        if (isLogin()) {
            new DialogInput(getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.fragmenthome.gg
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f15630a.lambda$initViews$4(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(CharSequence charSequence) {
        try {
            ((ClipboardManager) getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(View view) {
        int i2 = 0;
        if (this.mUpdateBean.getOption() != null && this.mUpdateBean.getOption().size() > 0) {
            int i3 = 0;
            for (int i4 = 0; i4 < this.mUpdateBean.getOption().size(); i4++) {
                if (this.mUpdateBean.getOption().get(i4).getType().equals("1")) {
                    i3++;
                }
            }
            if (i3 == 0) {
                AlertToast("请选择答案");
                return;
            } else if (this.mUpdateBean.getAnswer().replaceAll(",", "").toString().length() > 1 && i3 < 2) {
                AlertToast("此题为多选题");
                return;
            }
        }
        if (this.mUpdateBean.getIsNotAll() == 1 || !SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, getActivity())) {
            doPushAnsData();
            initSubViewData();
            CommAdapter<RecdQuestionBean.DataBean.OptionBean> commAdapter = this.mAdapter;
            if (commAdapter != null) {
                commAdapter.notifyDataSetChanged();
            }
            ObServerData();
            return;
        }
        if (this.mUpdateBean.getOption() != null && this.mUpdateBean.getOption().size() > 0) {
            int i5 = 0;
            while (i2 < this.mUpdateBean.getOption().size()) {
                if (!this.mUpdateBean.getOption().get(i2).getType().equals("0") && !TextUtils.isEmpty(this.mUpdateBean.getOption().get(i2).getType())) {
                    i5++;
                }
                i2++;
            }
            i2 = i5;
        }
        if (i2 > 0) {
            EventBus.getDefault().post("mIndex");
        } else {
            AlertToast("请选择选项！");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$8(View view) {
        if (this.mUpdateBean.getmStaticsData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        if (this.mUpdateBean.getmStaticsData().getIs_collection() == 0) {
            ObServerDataCollect(1);
            havaCollectimg();
            AlertToast("收藏成功！");
            this.mUpdateBean.getmStaticsData().setIs_collection(1);
            this.mUpdateBean.getmStaticsData().setCollection_count(this.mUpdateBean.getmStaticsData().getCollection_count() + 1);
        } else {
            ObServerDataCollect(2);
            noCollectimg();
            AlertToast("取消收藏成功！");
            this.mUpdateBean.getmStaticsData().setIs_collection(0);
            if (this.mUpdateBean.getmStaticsData().getCollection_count() > 0) {
                this.mUpdateBean.getmStaticsData().setCollection_count(this.mUpdateBean.getmStaticsData().getCollection_count() - 1);
            }
        }
        initStaticData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$9(View view) {
        if (this.mUpdateBean.getmStaticsData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        if (this.mUpdateBean.getmStaticsData().getIs_note() != 0) {
            getNoteData(view);
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        intent.putExtra("notestr", "");
        intent.putExtra("noteStatus", this.mUpdateBean.getmStaticsData().getIs_note() + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$22(List list, boolean z2) {
        initBiaoQianData(list);
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (((BiaoqianBeab.DataBean) list.get(i2)).getUser_label().equals("1")) {
                arrayList.add(((BiaoqianBeab.DataBean) list.get(i2)).getId());
            }
        }
        if (arrayList.size() > 0) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                sb.append((String) arrayList.get(i3));
                if (i3 < arrayList.size() - 1) {
                    sb.append(",");
                }
            }
        }
        postBiaoqianData(this.mUpdateBean.getQuestion_id() + "", sb.toString());
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.mUpdateBean.getQuestion_id() + "");
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", "1");
        ajaxParams.put("comment_type", "2");
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                ajaxParams.put("b_img", string);
                ajaxParams.put("s_img", string2);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        showProgressDialog("发布中");
        YJYHttpUtils.post(getActivity().getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubQuestionFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SubQuestionFragment.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(SubQuestionFragment.this.mUpdateBean.getmStaticsData().getComment_count() + 1)));
                        SubQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(SubQuestionFragment.this.getActivity()).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SubQuestionFragment.this.hideProgressDialog();
            }
        });
    }

    private void submitFavorites() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("question_id", this.mUpdateBean.getQuestion_id());
            jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()));
            jSONArray.put(0, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("collection", jSONArray.toString());
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mPostCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
            }
        });
    }

    public void ObServerData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.fragmenthome.kg
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f15726a.lambda$ObServerData$20(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.lg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubQuestionFragment.lambda$ObServerData$21(obj);
            }
        });
    }

    public void ObServerDataCollect(final int type) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.fragmenthome.ig
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f15674a.lambda$ObServerDataCollect$18(type, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.jg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubQuestionFragment.lambda$ObServerDataCollect$19(obj);
            }
        });
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id() + "");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
            }
        });
    }

    public void dialogNote(View v2, final String content) {
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.popu_note, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.popu_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.popu_edit);
        ((TextView) viewInflate.findViewById(R.id.tv_note_content)).setText(content);
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.qf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15947c.lambda$dialogNote$23(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.rf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15973c.lambda$dialogNote$24(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.sf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15994c.lambda$dialogNote$25(content, view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_note = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_note.setOutsideTouchable(true);
        this.popupWindow_note.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.fragmenthome.tf
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SubQuestionFragment.lambda$dialogNote$26();
            }
        });
        this.popupWindow_note.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_note.showAtLocation(v2, 80, 0, 0);
    }

    public void doPushAnsData() {
        List<RecdQuestionBean.DataBean.OptionBean> option;
        if (CommonUtil.isFastClick() || (option = this.mUpdateBean.getOption()) == null || option.size() <= 0) {
            return;
        }
        int i2 = 0;
        String str = "";
        for (int i3 = 0; i3 < option.size(); i3++) {
            if (option.get(i3).getType() != null && option.get(i3).getType().equals("1")) {
                if (!TextUtils.isEmpty(str)) {
                    str = str + ",";
                }
                str = str + option.get(i3).getKey().trim();
                i2++;
            }
        }
        if (i2 == 0) {
            NewToast.showShort(getActivity(), "请选择答案", 0).show();
            return;
        }
        this.mUpdateBean.setOwnerAns(str);
        if (this.mUpdateBean.getAnswer().replace(",", "").trim().toString().equals(str.replace(",", "").trim().toString())) {
            this.mUpdateBean.setIsRight("1");
            this.mUpdateBean.getmStaticsData().getAnswer().setRight_count(this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + 1);
        } else {
            this.mUpdateBean.setIsRight("0");
            this.mUpdateBean.getmStaticsData().getAnswer().setWrong_count(this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count() + 1);
        }
        initStaticData();
    }

    public void doSelectOption(int position) {
        int i2 = 0;
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, getActivity()) || this.mUpdateBean.getIsNotAll() == 1) {
            if (this.mUpdateBean.getAnswer().replaceAll(",", "").length() > 1) {
                this.mUpdateBean.getOption().get(position).setType(this.mUpdateBean.getOption().get(position).getType() != "1" ? "1" : "0");
                return;
            }
            while (i2 < this.mUpdateBean.getOption().size()) {
                this.mUpdateBean.getOption().get(i2).setType("0");
                i2++;
            }
            this.mUpdateBean.getOption().get(position).setType("1");
            return;
        }
        if (this.mUpdateBean.getAnswer().replaceAll(",", "").length() > 1) {
            this.mUpdateBean.getOption().get(position).setType(this.mUpdateBean.getOption().get(position).getType() == "1" ? "0" : "1");
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < this.mUpdateBean.getOption().size(); i3++) {
                if (this.mUpdateBean.getOption().get(i3).getType().equals("1")) {
                    sb.append(this.mUpdateBean.getOption().get(i3).getKey());
                    sb.append(",");
                }
            }
            if (TextUtils.isEmpty(sb.toString())) {
                this.mUpdateBean.setOwnerAns("");
                this.mUpdateBean.setIsRight("0");
                return;
            }
            this.mUpdateBean.setOwnerAns(sb.substring(0, sb.length() - 1));
            if (this.mUpdateBean.getOwnerAns().replaceAll(",", "").equals(this.mUpdateBean.getAnswer().replaceAll(",", ""))) {
                this.mUpdateBean.setIsRight("1");
            } else {
                this.mUpdateBean.setIsRight("0");
            }
            ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().insertOrReplace(new SubmitAnsweredQuestionBean(Long.valueOf(Long.parseLong(this.mUpdateBean.getQuestion_id())), this.mUpdateBean.getOwnerAns(), this.mUpdateBean.getIsRight(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.mUpdateBean.getQuestion_id() + "mengdepeng" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
            return;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < this.mUpdateBean.getOption().size(); i5++) {
            if (this.mUpdateBean.getOption().get(i5).getType().equals("1")) {
                i4++;
            }
        }
        if (i4 <= 0) {
            this.mUpdateBean.getOption().get(position).setType("1");
            RecdQuestionBean.DataBean dataBean = this.mUpdateBean;
            dataBean.setOwnerAns(dataBean.getOption().get(position).getKey());
            if (this.mUpdateBean.getOption().get(position).getKey().equals(this.mUpdateBean.getAnswer())) {
                this.mUpdateBean.setIsRight("1");
            } else {
                this.mUpdateBean.setIsRight("0");
            }
            ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().insertOrReplace(new SubmitAnsweredQuestionBean(Long.valueOf(Long.parseLong(this.mUpdateBean.getQuestion_id())), this.mUpdateBean.getOwnerAns(), this.mUpdateBean.getIsRight(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.mUpdateBean.getQuestion_id() + "mengdepeng" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
            EventBus.getDefault().post("mIndex");
            return;
        }
        if (this.mUpdateBean.getOption().get(position).getType().equals("1")) {
            this.mUpdateBean.getOption().get(position).setType("0");
            this.mUpdateBean.setOwnerAns("");
            this.mUpdateBean.setIsRight("0");
        } else {
            while (i2 < this.mUpdateBean.getOption().size()) {
                this.mUpdateBean.getOption().get(i2).setType("0");
                i2++;
            }
            this.mUpdateBean.getOption().get(position).setType("1");
            RecdQuestionBean.DataBean dataBean2 = this.mUpdateBean;
            dataBean2.setOwnerAns(dataBean2.getOption().get(position).getKey());
            if (this.mUpdateBean.getOption().get(position).getKey().equals(this.mUpdateBean.getAnswer())) {
                this.mUpdateBean.setIsRight("1");
            } else {
                this.mUpdateBean.setIsRight("0");
            }
        }
        ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().insertOrReplace(new SubmitAnsweredQuestionBean(Long.valueOf(Long.parseLong(this.mUpdateBean.getQuestion_id())), this.mUpdateBean.getOwnerAns(), this.mUpdateBean.getIsRight(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.mUpdateBean.getQuestion_id() + "mengdepeng" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
    }

    public void getBiaoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id());
        YJYHttpUtils.get(this.mContext.getApplicationContext(), NetworkRequestsURL.mlabelUrl, ajaxParams, new AnonymousClass7());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_subquestion;
    }

    public void getNoteData(final View v2) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.mGetNoteUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        SubQuestionFragment.this.dialogNote(v2, new JSONObject(s2).optJSONObject("data").optString("content"));
                    } else {
                        SubQuestionFragment.this.AlertToast("获取笔记失败！");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getRestoreStr(String str, TextView mTextV, String number, int type) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        String str2;
        String str3 = str;
        int i2 = type;
        Matcher matcher = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(str3);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.black);
        } else {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.jiucuo_night);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        ColorStateList colorStateList3 = colorStateList2;
        if (this.show_restore_img.equals("0")) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str3);
            int i3 = 0;
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, 6, 34);
            while (matcher.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher.start(0), matcher.end(0), 34);
            }
            Matcher matcher2 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str3);
            while (matcher2.find()) {
                int i4 = i3;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher2.start(i4), matcher2.end(i4), 34);
                i3 = i4;
            }
            int i5 = i3;
            Matcher matcher3 = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?错.*?[)）]").matcher(str3);
            while (matcher3.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher3.start(i5), matcher3.end(i5), 34);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        int i6 = 0;
        int i7 = 1;
        String str4 = i2 != 1 ? i2 != 2 ? "" : NetworkRequestsURL.CommentIameUrl2 : NetworkRequestsURL.CommentIameUrl;
        final ArrayList arrayList = new ArrayList();
        int i8 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            String str5 = str3.substring(i6, matcher.end(i6) + i8) + "&" + str3.substring(matcher.end(i6) + i8, str3.length());
            if (i2 != i7) {
                if (i2 != 2) {
                    str2 = str5;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    str2 = str5;
                    sb.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()));
                    sb.append("/");
                    sb.append(number);
                    sb.append(strGroup.substring(1, strGroup.length() - 1));
                    sb.append("-");
                    sb.append(i8 + 1);
                    sb.append(".jpg?x-oss-process=style/water_mark");
                    arrayList.add(sb.toString());
                }
                i7 = 1;
            } else {
                str2 = str5;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str4);
                sb2.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()));
                sb2.append("/");
                sb2.append(number);
                i7 = 1;
                sb2.append(strGroup.substring(1, strGroup.length() - 1));
                sb2.append("-");
                sb2.append(i8 + 1);
                sb2.append(".jpg?x-oss-process=style/water_mark");
                arrayList.add(sb2.toString());
            }
            i8++;
            str3 = str2;
            i2 = type;
            i6 = 0;
        }
        Matcher matcher4 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(str3);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str3);
        int i9 = i7;
        int i10 = 34;
        spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, 6, 34);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i11 = 0;
        while (matcher4.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, i9), matcher4.end(0), matcher4.end(0) + i9, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.fragmenthome.uf
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f16051a.lambda$getRestoreStr$17(arrayList, i11);
                }
            }), matcher4.start(0), matcher4.end(0), 33);
            mTextV.setHighlightColor(Color.parseColor("#00ffffff"));
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i11 += i9;
        }
        Matcher matcher5 = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?对.*?[)）]").matcher(str3);
        while (matcher5.find()) {
            int i12 = i10;
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher5.start(0), matcher5.end(0), i12);
            i10 = i12;
        }
        int i13 = i10;
        Matcher matcher6 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str3);
        while (matcher6.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher6.start(0), matcher6.end(0), i13);
        }
        mTextV.setText(spannableStringBuilder2);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mQuestionDataURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    QuestionDataStaticSetListBean questionDataStaticSetListBean = (QuestionDataStaticSetListBean) new Gson().fromJson(t2, QuestionDataStaticSetListBean.class);
                    if (!questionDataStaticSetListBean.getCode().equals("200") || questionDataStaticSetListBean.getData() == null || questionDataStaticSetListBean.getData().size() <= 0) {
                        return;
                    }
                    for (int i2 = 0; i2 < questionDataStaticSetListBean.getData().size(); i2++) {
                        if (questionDataStaticSetListBean.getData().get(i2).getQuestion_id().equals(SubQuestionFragment.this.mUpdateBean.getQuestion_id())) {
                            SubQuestionFragment.this.mUpdateBean.setmStaticsData(questionDataStaticSetListBean.getData().get(i2));
                            SubQuestionFragment.this.initStaticData();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    public void havaCommingimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    public void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    public void haveParise() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    public void initAnsData() {
        String str = "[答案解析] " + this.mUpdateBean.getExplain();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.redColors, null), 0, 6, 34);
        Matcher matcher = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher.start(0), matcher.end(0), 34);
        }
        Matcher matcher2 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str);
        while (matcher2.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher2.start(0), matcher2.end(0), 34);
        }
        this.questiondetails_tv_contents.setText(spannableStringBuilder);
        SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(this.questiondetails_tv_contents).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
        this.mSelectableTextHelper = selectableTextHelperBuild;
        selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.of
            @Override // com.psychiatrygarden.widget.OnSelectListener
            public final void onTextSelected(CharSequence charSequence) {
                this.f15898a.lambda$initAnsData$10(charSequence);
            }
        });
    }

    public void initBiaoQianData(final List<BiaoqianBeab.DataBean> dataBiao) {
        if (dataBiao == null || dataBiao.size() <= 0) {
            this.mRadioGroup_content.removeAllViews();
            this.biaotxt.setText("标签：？");
            return;
        }
        this.biaotxt.setText("标签：");
        this.mRadioGroup_content.removeAllViews();
        if (dataBiao.get(0).getCount() < 3) {
            TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color));
            } else {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
            }
            textView.setText("点击为本题添加标签");
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.zf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16171c.lambda$initBiaoQianData$13(dataBiao, view);
                }
            });
            this.mRadioGroup_content.addView(textView);
            return;
        }
        for (int i2 = 0; i2 < 3; i2++) {
            if (dataBiao.get(i2).getCount() >= 3) {
                TextView textView2 = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.rightMargin = 20;
                textView2.setText(String.format(Locale.CHINA, "%s %d", dataBiao.get(i2).getLabel(), Integer.valueOf(dataBiao.get(i2).getCount())));
                try {
                    if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                        textView2.setTextColor(Color.parseColor(dataBiao.get(i2).getColor()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                textView2.setLayoutParams(layoutParams);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.hg
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15651c.lambda$initBiaoQianData$14(dataBiao, view);
                    }
                });
                this.mRadioGroup_content.addView(textView2);
            }
        }
    }

    public void initRestoreData() {
        try {
            if (TextUtils.isEmpty(this.mUpdateBean.getRestore())) {
                return;
            }
            SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(this.questiondetails_tv_content_ques).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
            this.mSelectableTextHelper = selectableTextHelperBuild;
            selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.mg
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    this.f15853a.lambda$initRestoreData$15(charSequence);
                }
            });
            SelectableTextHelper selectableTextHelperBuild2 = new SelectableTextHelper.Builder(this.questiondetails_tv_content_ques1).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
            this.mSelectableTextHelper = selectableTextHelperBuild2;
            selectableTextHelperBuild2.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.ng
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    this.f15879a.lambda$initRestoreData$16(charSequence);
                }
            });
            if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
                String[] strArrSplit = this.mUpdateBean.getRestore().split("\\[\\$delimiter\\$\\]");
                if (strArrSplit.length < 2) {
                    this.questiondetails_tv_content_ques1.setVisibility(8);
                    this.questiondetails_tv_content_ques.setVisibility(0);
                    getRestoreStr("[考点还原] " + strArrSplit[0], this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 1);
                    return;
                }
                if (TextUtils.isEmpty(strArrSplit[1])) {
                    this.questiondetails_tv_content_ques.setVisibility(8);
                } else {
                    this.questiondetails_tv_content_ques.setVisibility(0);
                    getRestoreStr("[考点还原] " + strArrSplit[1], this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 1);
                }
                if (TextUtils.isEmpty(strArrSplit[0])) {
                    this.questiondetails_tv_content_ques1.setVisibility(8);
                    return;
                }
                this.questiondetails_tv_content_ques1.setVisibility(0);
                getRestoreStr("[考点还原] " + strArrSplit[0], this.questiondetails_tv_content_ques1, this.mUpdateBean.getNumber(), 2);
                return;
            }
            String[] strArrSplit2 = this.mUpdateBean.getRestore().split("\\[\\$delimiter\\$]");
            if (strArrSplit2.length < 2) {
                this.questiondetails_tv_content_ques1.setVisibility(8);
                this.questiondetails_tv_content_ques.setVisibility(0);
                getRestoreStr("[九版还原] " + strArrSplit2[0], this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 2);
                return;
            }
            if (TextUtils.isEmpty(strArrSplit2[1])) {
                this.questiondetails_tv_content_ques.setVisibility(8);
            } else {
                this.questiondetails_tv_content_ques.setVisibility(0);
                getRestoreStr("[八版还原] " + strArrSplit2[1], this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 1);
            }
            if (TextUtils.isEmpty(strArrSplit2[0])) {
                this.questiondetails_tv_content_ques1.setVisibility(8);
                return;
            }
            this.questiondetails_tv_content_ques1.setVisibility(0);
            getRestoreStr("[九版还原] " + strArrSplit2[0], this.questiondetails_tv_content_ques1, this.mUpdateBean.getNumber(), 2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initSelectData() {
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.mUpdateBean.getOption(), getActivity(), R.layout.item_questionoptions_listview);
        this.mAdapter = anonymousClass3;
        this.questiondetails_listView.setAdapter((ListAdapter) anonymousClass3);
        this.questiondetails_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.pf
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f15921c.lambda$initSelectData$11(adapterView, view, i2, j2);
            }
        });
    }

    public void initSelectedAnsed() {
        String strTrim = this.mUpdateBean.getAnswer().replace(",", "").trim();
        String strTrim2 = this.mUpdateBean.getOwnerAns().replace(",", "").trim();
        if (TextUtils.equals(strTrim2, "0")) {
            return;
        }
        if (strTrim.length() <= 1) {
            char[] charArray = strTrim.toCharArray();
            if (charArray[0] - 'A' >= this.mUpdateBean.getOption().size() || charArray[0] - 'A' < 0) {
                return;
            }
            if (TextUtils.isEmpty(strTrim2)) {
                this.mUpdateBean.getOption().get(charArray[0] - 'A').setType("4");
                return;
            }
            this.mUpdateBean.getOption().get(charArray[0] - 'A').setType("2");
            if (TextUtils.equals(strTrim, strTrim2)) {
                return;
            }
            char[] charArray2 = strTrim2.toCharArray();
            if (charArray2[0] - 'A' >= this.mUpdateBean.getOption().size() || charArray2[0] - 'A' < 0) {
                return;
            }
            this.mUpdateBean.getOption().get(charArray2[0] - 'A').setType("3");
            return;
        }
        if (TextUtils.isEmpty(strTrim2)) {
            for (char c3 : strTrim.toCharArray()) {
                int i2 = c3 - 'A';
                if (i2 >= this.mUpdateBean.getOption().size() || i2 < 0) {
                    return;
                }
                this.mUpdateBean.getOption().get(i2).setType("4");
            }
            return;
        }
        char[] charArray3 = strTrim.toCharArray();
        for (char c4 : strTrim2.toCharArray()) {
            int i3 = c4 - 'A';
            if (i3 >= this.mUpdateBean.getOption().size() || i3 < 0) {
                return;
            }
            this.mUpdateBean.getOption().get(i3).setType("3");
            for (char c5 : charArray3) {
                int i4 = c5 - 'A';
                if (i4 >= this.mUpdateBean.getOption().size() || i4 < 0) {
                    return;
                }
                if (this.mUpdateBean.getOption().get(i4).getKey().trim().equals(this.mUpdateBean.getOption().get(i3).getKey().trim())) {
                    this.mUpdateBean.getOption().get(i4).setType("2");
                } else if (!this.mUpdateBean.getOption().get(i4).getType().equals("2")) {
                    this.mUpdateBean.getOption().get(i4).setType("4");
                }
            }
        }
    }

    public void initStaticData() {
        String number;
        if (this.mUpdateBean.getmStaticsData() == null) {
            this.questiondetails_btn_commentNum.setText("?评论");
            this.questiondetails_tv_statistics.setText("统计：本题?人收藏，全部考生作答?次，对?次，正确率?%，本人作答?次，对?次，正确率?%");
            this.questiondetails_layout_diff.removeAllViews();
            TextView textView = new TextView(getActivity());
            textView.setTextSize(12.0f);
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.black));
            } else {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
            }
            textView.setText("难度：?");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = 10;
            textView.setLayoutParams(layoutParams);
            this.questiondetails_layout_diff.addView(textView);
            return;
        }
        Double dValueOf = Double.valueOf(0.0d);
        if (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count() > 0) {
            dValueOf = Double.valueOf((this.mUpdateBean.getmStaticsData().getRight_count() * 100) / (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count()));
            number = CommonUtil.getNumber(dValueOf.doubleValue());
        } else {
            number = "0";
        }
        this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.mUpdateBean.getmStaticsData().getComment_count())));
        if (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count() > 0) {
            String str = "统计：本题" + this.mUpdateBean.getmStaticsData().getCollection_count() + "人收藏，全部考生作答" + (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count()) + "次，对" + this.mUpdateBean.getmStaticsData().getRight_count() + "次，正确率" + number + "%，本人作答{" + (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count()) + "}次，对{" + this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() * 100) / (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count())) + "%}";
            if (dValueOf.doubleValue() > (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() * 100) / (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count())) {
                this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
            } else {
                this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
            }
        } else {
            String str2 = "统计：本题" + this.mUpdateBean.getmStaticsData().getCollection_count() + "人收藏，全部考生作答" + (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count()) + "次，对" + this.mUpdateBean.getmStaticsData().getRight_count() + "次，正确率" + number + "%，本人作答{?}次，对{?}次，正确率{?%}";
            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
        }
        this.questiondetails_layout_diff.removeAllViews();
        TextView textView2 = new TextView(getActivity());
        textView2.setTextSize(12.0f);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.black));
        } else {
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
        }
        textView2.setText("难度：");
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.rightMargin = 10;
        textView2.setLayoutParams(layoutParams2);
        this.questiondetails_layout_diff.addView(textView2);
        int i2 = dValueOf.doubleValue() > 95.0d ? 1 : dValueOf.doubleValue() > 80.0d ? 2 : dValueOf.doubleValue() > 60.0d ? 3 : dValueOf.doubleValue() > 30.0d ? 4 : 5;
        for (int i3 = 0; i3 < 5; i3++) {
            ImageView imageView = new ImageView(getActivity());
            if (i3 < i2) {
                if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                    imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_yellow));
                } else {
                    imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_yellow_night));
                }
            } else if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_gary));
            } else {
                imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_gary_night));
            }
            this.questiondetails_layout_diff.addView(imageView);
        }
        if (this.mUpdateBean.getmStaticsData().getIs_note() == 0) {
            noNoteimg();
        } else {
            haveNoteimg();
        }
        if (this.mUpdateBean.getmStaticsData().getIs_collection() == 0) {
            noCollectimg();
        } else {
            havaCollectimg();
        }
        if (this.mUpdateBean.getmStaticsData().getIs_comment() == 0) {
            noCommingimg();
        } else {
            havaCommingimg();
        }
        if (this.mUpdateBean.getmStaticsData().getIs_praise() == 0) {
            noParise();
        } else {
            haveParise();
        }
    }

    public void initSubViewData() {
        if (this.mUpdateBean.getIsNotAll() != 1 && this.mUpdateBean.getIsdoType() != 1 && SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, getActivity())) {
            this.questiondetails_bottom_layout.setVisibility(8);
            this.lineout.setVisibility(8);
            this.questiondetails_btn_commentNum.setVisibility(8);
            this.questiondetails_layout_diff.setVisibility(8);
            if (this.mUpdateBean.getAnswer().replace(",", "").length() > 1) {
                this.questiondetails_btn_pushAnswer.setVisibility(0);
                return;
            } else {
                this.questiondetails_btn_pushAnswer.setVisibility(8);
                return;
            }
        }
        this.questiondetails_bottom_layout.setVisibility(0);
        this.questiondetails_btn_commentNum.setVisibility(0);
        if (this.mUpdateBean.getOwnerAns().equals("")) {
            this.lineout.setVisibility(8);
            this.questiondetails_layout_diff.setVisibility(8);
            this.questiondetails_btn_pushAnswer.setVisibility(0);
        } else {
            this.lineout.setVisibility(0);
            this.questiondetails_layout_diff.setVisibility(0);
            this.questiondetails_btn_pushAnswer.setVisibility(8);
            initSelectedAnsed();
        }
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.mUpdateBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(this.mUpdateBean.getTitle_img())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image)).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.5
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                SubQuestionFragment.this.imgtitle.setImageResource(R.drawable.imgplacehodel_image);
                return true;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                SubQuestionFragment.this.imgtitle.setImageDrawable(resource);
                return true;
            }
        }).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.4
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (resource != null) {
                    float intrinsicWidth = resource.getIntrinsicWidth();
                    float intrinsicHeight = resource.getIntrinsicHeight();
                    float width = SubQuestionFragment.this.imgtitle.getWidth();
                    if (width == 0.0f) {
                        width = SubQuestionFragment.this.imgtitle.getResources().getDisplayMetrics().widthPixels;
                    }
                    int i2 = (int) ((intrinsicHeight / intrinsicWidth) * width);
                    ViewGroup.LayoutParams layoutParams = SubQuestionFragment.this.imgtitle.getLayoutParams();
                    if (i2 >= 4096) {
                        layoutParams.height = R2.color.N_stretchTextColor;
                        SubQuestionFragment.this.imgtitle.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        layoutParams.height = -1;
                    }
                    SubQuestionFragment.this.imgtitle.setLayoutParams(layoutParams);
                }
            }
        });
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.og
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15899c.lambda$initTitleImg$12(view);
            }
        });
    }

    public void initVideoData() {
        String[] strArrSplit;
        if (this.mUpdateBean.getMedia_img().equals("")) {
            this.rl_question_video.setVisibility(8);
            return;
        }
        this.rl_question_video.setVisibility(0);
        try {
            this.circlepoint.removeAllViews();
            String media_img = this.mUpdateBean.getMedia_img();
            if (TextUtils.equals(media_img, "") || (strArrSplit = media_img.split(",")) == null || strArrSplit.length <= 0) {
                return;
            }
            if (strArrSplit.length > 1) {
                this.circlepoint.setCount(strArrSplit.length);
                this.circlepoint.initViewData();
                this.circlepoint.invalidate();
            }
            this.viewpager_question_video.setAdapter(new QuestionVideoViewpagerAdapter(getActivity(), strArrSplit, Long.parseLong(this.mUpdateBean.getQuestion_id()), 10));
            final int length = strArrSplit.length;
            this.viewpager_question_video.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.6
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (length > 1) {
                        SubQuestionFragment.this.circlepoint.setonPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        String str;
        String str2;
        SpannableStringBuilder spannableStringBuilder;
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        this.mUpdateBean = ((SubQuestionMainActivity) getActivity()).mDataList.get(arguments != null ? arguments.getInt("positionT") : 0);
        try {
            this.show_restore_img = SharePreferencesUtils.readStrConfig(CommonParameter.show_restore_img, getActivity(), "1");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.questiondetails_bottom_layout = (LinearLayout) holder.get(R.id.questiondetails_bottom_layout);
        TextView textView = (TextView) holder.get(R.id.questiondetails_tv_childTitle);
        this.imgtitle = (ImageView) holder.get(R.id.imgtitle);
        this.questiondetails_listView = (MyListView) holder.get(R.id.questiondetails_listView);
        this.questiondetails_btn_commentNum = (Button) holder.get(R.id.questiondetails_btn_commentNum);
        this.questiondetails_layout_diff = (LinearLayout) holder.get(R.id.questiondetails_layout_diff);
        this.questiondetails_tv_statistics = (TextView) holder.get(R.id.questiondetails_tv_statistics);
        this.redSelected = ContextCompat.getColor(this.mContext, R.color.app_theme_red);
        this.redTransSelected = ContextCompat.getColor(this.mContext, R.color.trans_app_theme_red);
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.wf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16102c.lambda$initViews$0(view);
            }
        });
        this.lineout = (LinearLayout) holder.get(R.id.lineout);
        this.biaotxt = (TextView) holder.get(R.id.biaotxt);
        this.mRadioGroup_content = (LinearLayout) holder.get(R.id.mRadioGroup_content);
        this.ll_more_columns = (LinearLayout) holder.get(R.id.ll_more_columns);
        WeakReference weakReference = new WeakReference(this.drawable);
        if (weakReference.get() != null) {
            this.drawable = (Drawable) weakReference.get();
        } else if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan);
        } else {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan_night);
        }
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.redColors = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.black);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.back_font_gray);
        } else {
            this.redColors = ContextCompat.getColorStateList(this.mContext, R.color.jiucuo_night);
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        this.rl_question_video = (RelativeLayout) holder.get(R.id.rl_question_video);
        this.viewpager_question_video = (AutoHeightViewPager) holder.get(R.id.viewpager_question_video);
        this.circlepoint = (CirclePoint) holder.get(R.id.circlepoint);
        ((TextView) holder.get(R.id.tv_correction)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.yf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16148c.lambda$initViews$1(view);
            }
        });
        TextView textView2 = (TextView) holder.get(R.id.questiondetails_tv_Answer);
        TextView textView3 = (TextView) holder.get(R.id.questiondetails_tv_outline);
        this.questiondetails_tv_content_ques1 = (TextView) holder.get(R.id.questiondetails_tv_content_ques1);
        this.questiondetails_tv_content_ques = (TextView) holder.get(R.id.questiondetails_tv_content_ques);
        this.questiondetails_tv_contents = (TextView) holder.get(R.id.questiondetails_tv_contents);
        this.questiondetails_btn_pushAnswer = (Button) holder.get(R.id.questiondetails_btn_pushAnswer);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.ly_questiondetails_btn_zantong = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_zantong);
        this.ly_questiondetails_btn_centerMsg = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_centerMsg);
        this.ly_questiondetails_btn_collect = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_collect);
        this.ly_questiondetails_btn_edit = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit);
        Button button = (Button) holder.get(R.id.btn_comment);
        this.ly_questiondetails_btn_centerMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ag
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15447c.lambda$initViews$2(view);
            }
        });
        this.ly_questiondetails_btn_zantong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.bg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15483c.lambda$initViews$3(view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.cg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15514c.lambda$initViews$5(view);
            }
        });
        initSubViewData();
        try {
            ColorStateList colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.black);
            if (this.mUpdateBean.getIs_real_question().equals("1")) {
                spannableStringBuilder = new SpannableStringBuilder(this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getNumber() + "] " + this.mUpdateBean.getTitle());
                TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, CommonUtil.dip2px(getActivity(), 12.0f), colorStateList, null);
                StringBuilder sb = new StringBuilder();
                sb.append(this.mUpdateBean.getS_num_pm());
                sb.append(StrPool.DOT);
                spannableStringBuilder.setSpan(textAppearanceSpan, sb.toString().length(), (this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getNumber() + StrPool.BRACKET_END).length(), 34);
            } else {
                spannableStringBuilder = new SpannableStringBuilder(this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getType_str() + "] " + this.mUpdateBean.getTitle());
                TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(null, 0, CommonUtil.dip2px(getActivity(), 12.0f), colorStateList, null);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mUpdateBean.getS_num_pm());
                sb2.append(StrPool.DOT);
                spannableStringBuilder.setSpan(textAppearanceSpan2, sb2.toString().length(), (this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getType_str() + StrPool.BRACKET_END).length(), 34);
            }
            textView.setText(spannableStringBuilder);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            str = "<font color='#dd594a'>[正确答案]  </font>" + this.mUpdateBean.getAnswer().replaceAll(",", "");
        } else {
            str = "<font color='#B2575C'>[正确答案]  </font>" + this.mUpdateBean.getAnswer().replaceAll(",", "");
        }
        textView2.setText(Html.fromHtml(str, 0));
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            str2 = "<font color='#dd594a'>[大纲考点]  </font>" + this.mUpdateBean.getOutlines_pm();
        } else {
            str2 = "<font color='#B2575C'>[大纲考点]  </font>" + this.mUpdateBean.getOutlines_pm();
        }
        textView3.setText(Html.fromHtml(str2, 0));
        if ("".equals(this.mUpdateBean.getOutlines_pm())) {
            textView3.setVisibility(8);
        } else {
            textView3.setVisibility(0);
        }
        SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(textView3).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
        this.mSelectableTextHelper = selectableTextHelperBuild;
        selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.dg
            @Override // com.psychiatrygarden.widget.OnSelectListener
            public final void onTextSelected(CharSequence charSequence) {
                this.f15552a.lambda$initViews$6(charSequence);
            }
        });
        initRestoreData();
        initAnsData();
        initSelectData();
        initTitleImg();
        initStaticData();
        initVideoData();
        getBiaoData();
        getStaticsData();
        this.questiondetails_btn_pushAnswer.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.eg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15576c.lambda$initViews$7(view);
            }
        });
        this.ly_questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.fg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15603c.lambda$initViews$8(view);
            }
        });
        this.ly_questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.xf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16123c.lambda$initViews$9(view);
            }
        });
    }

    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void noCommingimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
        }
    }

    public void noNoteimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    public void noParise() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse_night);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("notesave" + this.mUpdateBean.getQuestion_id())) {
            this.mUpdateBean.getmStaticsData().setIs_note(1);
            haveNoteimg();
        }
        if (str.equals("noteclear" + this.mUpdateBean.getQuestion_id())) {
            this.mUpdateBean.getmStaticsData().setIs_note(0);
            noNoteimg();
        }
    }

    public void postBiaoqianData(String question_id, String label_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("label_id", label_id);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.muserLabelUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubQuestionFragment.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        return;
                    }
                    NewToast.showShort(SubQuestionFragment.this.getActivity(), jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* renamed from: showlog, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$initBiaoQianData$14(List<BiaoqianBeab.DataBean> dataBiao, View v2) {
        new XPopup.Builder(getActivity()).asCustom(new BiaoPupWindow(this.mContext, dataBiao, new BiaoqianInterface() { // from class: com.psychiatrygarden.fragmenthome.vf
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianInterface
            public final void mBiaoianLinster(List list, boolean z2) {
                this.f16081a.lambda$showlog$22(list, z2);
            }
        })).toggle();
    }
}
