package com.psychiatrygarden.activity.courselist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.timepicker.TimeModel;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.makeramen.roundedimageview.RoundedImageView;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RecdationDetailActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.fragment.CourseDoubleChapterFragment;
import com.psychiatrygarden.activity.courselist.fragment.CourseNoNeChapterFragment;
import com.psychiatrygarden.bean.CourseList2Bean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.SelectModePop;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.glideUtil.transformation.BlurTransformation;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseListChpterActivity extends BaseActivity {
    public CourseList2Bean.DataBean.DataChildBean childBean;
    private String is_collected = "0";
    private boolean iscoment;
    private boolean ispraise;
    private LinearLayout llay_top_one;
    private RelativeLayout reldddd;
    private GlideImageView shoucang;
    private TextView timrs;
    private TextView unreadnum;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$0(View view) {
        Intent intent = new Intent(this, (Class<?>) RecdationDetailActivity.class);
        intent.putExtra("title", "" + this.childBean.getTitle());
        intent.putExtra("description", "" + this.childBean.getDescription());
        intent.putExtra("cover_img", "" + this.childBean.getCover_img());
        intent.putExtra("times", "" + this.timrs.getText().toString());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$1(View view) {
        new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).asCustom(new SelectModePop(this.mContext)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$10(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$2(View view) {
        gotoConfig("collection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$3(View view) {
        gotoConfig(ClientCookie.COMMENT_ATTR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$4(View view) {
        gotoConfig("praise");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$5(View view) {
        gotoConfig("note");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$6(View view) {
        Intent intent = new Intent(this, (Class<?>) CourseDownLoadActivity.class);
        intent.putExtra("series", "" + this.childBean.getSeries());
        intent.putExtra("vidteaching_id", "" + this.childBean.getId());
        intent.putExtra("chapter_id", "0");
        intent.putExtra("parent_id", "0");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$7(View view) {
        if (this.is_collected.equals("1")) {
            putCollectData("0");
            this.shoucang.setImageResource(R.drawable.shoucang);
            this.is_collected = "0";
        } else {
            putCollectData("1");
            this.shoucang.setImageResource(R.drawable.shoucangtwo);
            this.is_collected = "1";
        }
        EventBus.getDefault().post("qiehuan");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$8(View view) {
        Intent intent = new Intent(this, (Class<?>) DiscussPublicActivity.class);
        intent.putExtra("obj_id", "" + this.childBean.getId());
        intent.putExtra("module_type", 14);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", this.iscoment);
        intent.putExtra("isZantongTrue", this.ispraise);
        intent.putExtra("title", "" + this.childBean.getTitle());
        intent.putExtra("commentEnum", DiscussStatus.CommentsOnTheEaluationModelTestQuestionnaire);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$9(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.llay_top_one.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        this.reldddd.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.llay_top_one.setVisibility(8);
            this.reldddd.setVisibility(8);
        } else {
            this.llay_top_one.setVisibility(0);
            this.reldddd.setVisibility(0);
        }
    }

    public void getCommingNum() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("vidteaching_id", "" + this.childBean.getId());
        YJYHttpUtils.get(this, NetworkRequestsURL.infoApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CourseListChpterActivity.1
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
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        int iOptInt = new JSONObject(s2).optJSONObject("data").optInt("comment_count");
                        boolean z2 = true;
                        if (iOptInt == 0) {
                            CourseListChpterActivity.this.unreadnum.setVisibility(8);
                        } else {
                            CourseListChpterActivity.this.unreadnum.setVisibility(0);
                            if (iOptInt > 999) {
                                CourseListChpterActivity.this.unreadnum.setText("999+");
                            } else {
                                CourseListChpterActivity.this.unreadnum.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(iOptInt)));
                            }
                        }
                        int iOptInt2 = new JSONObject(s2).optJSONObject("data").optInt("is_comment");
                        int iOptInt3 = new JSONObject(s2).optJSONObject("data").optInt("is_praise");
                        CourseListChpterActivity.this.is_collected = new JSONObject(s2).optJSONObject("data").optString("is_collected");
                        if (CourseListChpterActivity.this.is_collected.equals("1")) {
                            CourseListChpterActivity.this.shoucang.setImageResource(R.drawable.shoucangtwo);
                        } else {
                            CourseListChpterActivity.this.shoucang.setImageResource(R.drawable.shoucang);
                        }
                        CourseListChpterActivity.this.iscoment = iOptInt2 != 0;
                        CourseListChpterActivity courseListChpterActivity = CourseListChpterActivity.this;
                        if (iOptInt3 == 0) {
                            z2 = false;
                        }
                        courseListChpterActivity.ispraise = z2;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void gotoConfig(String type) {
        Intent intent;
        intent = new Intent(this, (Class<?>) CourseToConfigureActivity.class);
        intent.putExtra("vidteaching_id", "" + this.childBean.getId());
        intent.putExtra("type", type);
        intent.putExtra("series", "" + this.childBean.getSeries());
        type.hashCode();
        switch (type) {
            case "collection":
                intent.putExtra("title", "收藏");
                break;
            case "praise":
                intent.putExtra("title", "点赞");
                break;
            case "note":
                intent.putExtra("title", "笔记");
                break;
            case "comment":
                intent.putExtra("title", "评论");
                break;
        }
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.childBean = (CourseList2Bean.DataBean.DataChildBean) getIntent().getExtras().getSerializable("vidteaching");
        initDataView();
    }

    public void initDataView() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.reldddd = (RelativeLayout) findViewById(R.id.reldddd);
        RoundedImageView roundedImageView = (RoundedImageView) findViewById(R.id.headermin);
        GlideImageView glideImageView = (GlideImageView) findViewById(R.id.headerimg);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pinglunrel);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        GlideImageView glideImageView2 = (GlideImageView) findViewById(R.id.icon_left_back);
        TextView textView2 = (TextView) findViewById(R.id.tv_question_cuoti);
        TextView textView3 = (TextView) findViewById(R.id.tv_question_shoucang);
        TextView textView4 = (TextView) findViewById(R.id.pinglun);
        TextView textView5 = (TextView) findViewById(R.id.dianzan);
        TextView textView6 = (TextView) findViewById(R.id.tv_question_biji);
        GlideImageView glideImageView3 = (GlideImageView) findViewById(R.id.qiehuan);
        this.unreadnum = (TextView) findViewById(R.id.unreadnum);
        this.shoucang = (GlideImageView) findViewById(R.id.shoucang);
        TextView textView7 = (TextView) findViewById(R.id.title);
        TextView textView8 = (TextView) findViewById(R.id.desc);
        this.timrs = (TextView) findViewById(R.id.timrs);
        textView.setText(this.childBean.getTitle());
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.llay_top_one = (LinearLayout) findViewById(R.id.llay_top_one);
        setStatusBarTranslucent();
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(toolbar.getLayoutParams());
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        layoutParams.setCollapseMode(1);
        toolbar.setLayoutParams(layoutParams);
        int iDip2px = CommonUtil.dip2px(this.mContext, 68.0f) + statusBarHeight;
        CollapsingToolbarLayout.LayoutParams layoutParams2 = new CollapsingToolbarLayout.LayoutParams(this.reldddd.getLayoutParams());
        layoutParams2.setMargins(0, iDip2px, 0, 0);
        layoutParams2.setCollapseMode(1);
        this.reldddd.setLayoutParams(layoutParams2);
        int iDip2px2 = CommonUtil.dip2px(this.mContext, 100.0f) + CommonUtil.dip2px(this.mContext, 98.0f) + statusBarHeight;
        CollapsingToolbarLayout.LayoutParams layoutParams3 = new CollapsingToolbarLayout.LayoutParams(this.llay_top_one.getLayoutParams());
        layoutParams3.setMargins(0, iDip2px2, 0, CommonUtil.dip2px(this.mContext, 20.0f));
        layoutParams3.setCollapseMode(1);
        this.llay_top_one.setLayoutParams(layoutParams3);
        this.llay_top_one.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        CoordinatorLayout.LayoutParams layoutParams4 = new CoordinatorLayout.LayoutParams(-1, iDip2px2 + this.llay_top_one.getMeasuredHeight() + CommonUtil.dip2px(this.mContext, 20.0f));
        layoutParams4.setBehavior(new AppBarLayout.Behavior());
        appBarLayout.setLayoutParams(layoutParams4);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.transparent));
        GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(this.childBean.getCover_img())).into(roundedImageView);
        glideImageView.fitCenter().load(this.childBean.getCover_img(), R.drawable.imgplacehodel, new BlurTransformation(this, 25, 10));
        textView7.setText(this.childBean.getTitle());
        textView8.setText(this.childBean.getDescription());
        textView2.setText("下载");
        this.timrs.setVisibility(0);
        if (this.childBean.getUpdate_time() != null && !this.childBean.getUpdate_time().equals("")) {
            this.timrs.setText(String.format("更新时间：%s", this.childBean.getUpdate_time()));
        } else if (this.childBean.getCreate_time() != null && !this.childBean.getCreate_time().equals("")) {
            this.timrs.setText(String.format("创建时间：%s", this.childBean.getCreate_time()));
        }
        textView8.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12150c.lambda$initDataView$0(view);
            }
        });
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.cdown);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView2.setCompoundDrawables(null, drawable, null, null);
        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.ccollent);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        textView3.setCompoundDrawables(null, drawable2, null, null);
        Drawable drawable3 = ContextCompat.getDrawable(this, R.drawable.ccoment);
        drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
        textView4.setCompoundDrawables(null, drawable3, null, null);
        Drawable drawable4 = ContextCompat.getDrawable(this, R.drawable.cparse);
        drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
        textView5.setCompoundDrawables(null, drawable4, null, null);
        Drawable drawable5 = ContextCompat.getDrawable(this, R.drawable.cnot);
        drawable5.setBounds(0, 0, drawable5.getMinimumWidth(), drawable5.getMinimumHeight());
        textView6.setCompoundDrawables(null, drawable5, null, null);
        textView2.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        textView3.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        textView4.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        textView5.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        textView6.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        glideImageView3.setVisibility(8);
        glideImageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12167c.lambda$initDataView$1(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12174c.lambda$initDataView$2(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12186c.lambda$initDataView$3(view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12190c.lambda$initDataView$4(view);
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12204c.lambda$initDataView$5(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12209c.lambda$initDataView$6(view);
            }
        });
        this.shoucang.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12213c.lambda$initDataView$7(view);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11831c.lambda$initDataView$8(view);
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.courselist.r
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                this.f12155a.lambda$initDataView$9(appBarLayout2, i2);
            }
        });
        glideImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12161c.lambda$initDataView$10(view);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("vidteaching_id", "" + this.childBean.getId());
        bundle.putString("type", "all");
        bundle.putString("series", "" + this.childBean.getSeries());
        bundle.putString("chapter_id", "0");
        bundle.putString("parent_id", "0");
        bundle.putString("flag", "1");
        if (this.childBean.getSeries().equals("2")) {
            if (findFragment(CourseDoubleChapterFragment.class) == null) {
                CourseDoubleChapterFragment courseDoubleChapterFragment = new CourseDoubleChapterFragment();
                courseDoubleChapterFragment.setArguments(bundle);
                loadRootFragment(R.id.fg_main, courseDoubleChapterFragment);
                return;
            }
            return;
        }
        if (this.childBean.getSeries().equals("1")) {
            if (findFragment(CourseDoubleChapterFragment.class) == null) {
                CourseDoubleChapterFragment courseDoubleChapterFragment2 = new CourseDoubleChapterFragment();
                courseDoubleChapterFragment2.setArguments(bundle);
                loadRootFragment(R.id.fg_main, courseDoubleChapterFragment2);
                return;
            }
            return;
        }
        if (findFragment(CourseNoNeChapterFragment.class) == null) {
            CourseNoNeChapterFragment courseNoNeChapterFragment = new CourseNoNeChapterFragment();
            courseNoNeChapterFragment.setArguments(bundle);
            loadRootFragment(R.id.fg_main, courseNoNeChapterFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "无法下载，请检查app存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CommonUtil.copyEncryptedFile(this);
        getCommingNum();
    }

    public void putCollectData(String operation) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("vidteaching_id", "" + this.childBean.getId());
        ajaxParams.put("operation", "" + operation);
        YJYHttpUtils.post(this, NetworkRequestsURL.collectApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CourseListChpterActivity.2
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
                    ToastUtil.shortToast(CourseListChpterActivity.this, new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_list_chpter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setStatusBarTranslucent() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StatusBarUtil.setStatusBarTranslucent(this, false);
    }
}
