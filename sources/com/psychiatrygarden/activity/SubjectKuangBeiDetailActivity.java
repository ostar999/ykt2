package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.KuangBeiPagerAdapter;
import com.psychiatrygarden.bean.AnsweredQuestionBeanBei;
import com.psychiatrygarden.bean.FavoritesBeanBei;
import com.psychiatrygarden.bean.KuangBeiStaDataBean;
import com.psychiatrygarden.bean.NotesBeanBei;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ViewPagerCompat;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectKuangBeiDetailActivity extends BaseActivity {
    private Button btn_comment;
    private LinearLayout line_viewok;
    private long[] list_questionid;
    private KuangBeiStaDataBean mKKuangBeiStaDataBean;
    private KuangBeiPagerAdapter myviewPagerAdapter;
    private PopupWindow popupWindow_note;
    private LinearLayout questiondetails_bottom_layout;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private ImageView questiondetails_btn_edit;
    private ImageView questiondetails_zantong;
    private ViewPagerCompat viewPager;
    private int indext = 0;
    private boolean is_show = true;
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wm
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SubjectKuangBeiDetailActivity.lambda$new$3(view);
        }
    };

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiDetailActivity.3
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 1) {
                SubjectKuangBeiDetailActivity.this.intPostView();
            }
            if (i2 == 2) {
                SubjectKuangBeiDetailActivity.this.line_viewok.setVisibility(8);
                return;
            }
            if (i2 == 6) {
                SubjectKuangBeiDetailActivity.this.clearCollection();
                return;
            }
            switch (i2) {
                case 10:
                    AnsweredQuestionBeanBei answeredQuestionBeanBeiLoadByRowId = ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().loadByRowId(SubjectKuangBeiDetailActivity.this.list_questionid[SubjectKuangBeiDetailActivity.this.indext]);
                    if (answeredQuestionBeanBeiLoadByRowId != null) {
                        if (TextUtils.isEmpty(answeredQuestionBeanBeiLoadByRowId.getIs_right())) {
                            SubjectKuangBeiDetailActivity.this.mHandler.sendEmptyMessage(1);
                        } else {
                            SubjectKuangBeiDetailActivity.this.mHandler.sendEmptyMessage(2);
                        }
                    }
                    NotesBeanBei notesBeanBeiLoad = ProjectApp.mDaoSession.getNotesBeanBeiDao().load(Long.valueOf(SubjectKuangBeiDetailActivity.this.list_questionid[SubjectKuangBeiDetailActivity.this.indext]));
                    FavoritesBeanBei favoritesBeanBeiLoad = ProjectApp.mDaoSession.getFavoritesBeanBeiDao().load(Long.valueOf(SubjectKuangBeiDetailActivity.this.list_questionid[SubjectKuangBeiDetailActivity.this.indext]));
                    if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                        if (notesBeanBeiLoad == null) {
                            SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edit));
                        } else {
                            SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edited));
                        }
                    } else if (notesBeanBeiLoad == null) {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edit_night));
                    } else {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edited_night));
                    }
                    if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) != 0) {
                        if (favoritesBeanBeiLoad != null) {
                            SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_yes_night));
                            break;
                        } else {
                            SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_no_night));
                            break;
                        }
                    } else if (favoritesBeanBeiLoad != null) {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_yes));
                        break;
                    } else {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_no));
                        break;
                    }
                case 11:
                    SubjectKuangBeiDetailActivity.this.getQuestionData(SubjectKuangBeiDetailActivity.this.list_questionid[SubjectKuangBeiDetailActivity.this.indext] + "");
                    break;
                case 12:
                    SubjectKuangBeiDetailActivity.this.questiondetails_bottom_layout.setVisibility(0);
                    break;
                case 13:
                    SubjectKuangBeiDetailActivity.this.questiondetails_bottom_layout.setVisibility(8);
                    break;
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$5(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$6(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) EditNoteKuangActivity.class);
        intent.putExtra("question_id", this.list_questionid[this.indext]);
        startActivity(intent);
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$7() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$3(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushComment$8(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", this.mContext);
                this.mHandler.sendEmptyMessage(5);
            }
            AlertToast(jSONObject.optString("message"));
            EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
            CommonUtil.showFristDialog(jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushComment$9(VolleyError volleyError, String str) {
        hideProgressDialog();
        if (CommonUtil.isNetworkConnected(this.mContext)) {
            AlertToast(volleyError.getMessage());
        } else {
            AlertToast("网络连接失败，请检查网络");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        finish();
        EventBus.getDefault().post("SubKuangisRefulf1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (this.is_show) {
            this.is_show = false;
            this.iv_actionbar_right.setImageResource(R.drawable.kuangbei_colse);
        } else {
            this.is_show = true;
            this.iv_actionbar_right.setImageResource(R.drawable.kuangbei_open);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(int i2) {
        if (i2 == 1) {
            AlertToast("已为最后一条");
        }
    }

    private void pushComment(Bundle b3) {
        HashMap map = new HashMap();
        map.put("obj_id", this.list_questionid[this.indext] + "");
        map.put("content", b3.getString("content"));
        map.put("module_type", "4");
        map.put("comment_type", "2");
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                map.put("b_img", string);
                map.put("s_img", string2);
            } else {
                map.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            map.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        showProgressDialog("发布中");
        YJYHttpUtils.post(this, NetworkRequestsURL.mPutComment, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.um
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f13995c.lambda$pushComment$8((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.vm
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f14123c.lambda$pushComment$9(volleyError, str);
            }
        });
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("question_id", this.list_questionid[this.indext] + "");
        ajaxParams.put("module_type", "4");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiDetailActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubjectKuangBeiDetailActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                LogUtils.e(SubjectKuangBeiDetailActivity.this.TAG, t2);
            }
        });
    }

    public void dialogNote(String content) {
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.popu_note, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.popu_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.popu_edit);
        ((TextView) viewInflate.findViewById(R.id.tv_note_content)).setText(content);
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ym
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14223c.lambda$dialogNote$4(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14256c.lambda$dialogNote$5(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.an
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11000c.lambda$dialogNote$6(view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_note = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_note.setOutsideTouchable(true);
        this.popupWindow_note.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.bn
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SubjectKuangBeiDetailActivity.lambda$dialogNote$7();
            }
        });
        this.popupWindow_note.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_note.showAtLocation(this.viewPager, 17, 0, 0);
    }

    public void getQuestionData(String question_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id + "");
        ajaxParams.put("module_type", "4");
        YJYHttpUtils.post(ProjectApp.instance, NetworkRequestsURL.mQuestionDataURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiDetailActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                List<KuangBeiStaDataBean.DataBean> data;
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        SubjectKuangBeiDetailActivity.this.mKKuangBeiStaDataBean = (KuangBeiStaDataBean) new Gson().fromJson(t2, KuangBeiStaDataBean.class);
                        try {
                            if (SubjectKuangBeiDetailActivity.this.mKKuangBeiStaDataBean != null && (data = SubjectKuangBeiDetailActivity.this.mKKuangBeiStaDataBean.getData()) != null && data.size() > 0) {
                                for (int i2 = 0; i2 < data.size(); i2++) {
                                    if (data.get(i2).getQuestion_id() == SubjectKuangBeiDetailActivity.this.list_questionid[SubjectKuangBeiDetailActivity.this.indext]) {
                                        if (data.get(i2).getIs_comment().equals("1")) {
                                            if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                                SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youpinglun));
                                            } else {
                                                SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youpinglun_night));
                                            }
                                        } else if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                            SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.question_msg));
                                        } else {
                                            SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.question_msg_night));
                                        }
                                        if (data.get(i2).getIs_praise().equals("1")) {
                                            if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                                SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youdianzan));
                                            } else {
                                                SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youdianzan_night));
                                            }
                                        } else if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                            SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.dianzancourse));
                                        } else {
                                            SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.dianzancourse_night));
                                        }
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                SubjectKuangBeiDetailActivity.this.myviewPagerAdapter.setRefultData(SubjectKuangBeiDetailActivity.this.mKKuangBeiStaDataBean, SubjectKuangBeiDetailActivity.this.indext, SubjectKuangBeiDetailActivity.this.list_questionid[SubjectKuangBeiDetailActivity.this.indext]);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.questiondetails_bottom_layout = (LinearLayout) findViewById(R.id.questiondetails_bottom_layout);
        TextView textView = (TextView) findViewById(R.id.questiondetails_tv_title);
        textView.setVisibility(0);
        this.line_viewok = (LinearLayout) findViewById(R.id.line_viewok);
        this.questiondetails_zantong = (ImageView) findViewById(R.id.questiondetails_zantong);
        TextView textView2 = (TextView) findViewById(R.id.zuoduil);
        TextView textView3 = (TextView) findViewById(R.id.zuocuol);
        textView2.setOnClickListener(this.mOnclick);
        textView3.setOnClickListener(this.mOnclick);
        this.questiondetails_zantong.setOnClickListener(this.mOnclick);
        setTitle(getIntent().getStringExtra("subject_name"));
        this.btn_comment = (Button) findViewById(R.id.btn_comment);
        this.questiondetails_btn_collect = (ImageView) findViewById(R.id.questiondetails_btn_collect);
        ImageView imageView = (ImageView) findViewById(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_edit = imageView;
        imageView.setOnClickListener(this.mOnclick);
        this.viewPager = (ViewPagerCompat) findViewById(R.id.questiondetails_viewPager);
        this.questiondetails_btn_centerMsg = (ImageView) findViewById(R.id.questiondetails_btn_centerMsg);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext)) {
            this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
        }
        this.questiondetails_btn_centerMsg.setOnClickListener(this.mOnclick);
        KuangBeiPagerAdapter kuangBeiPagerAdapter = new KuangBeiPagerAdapter(this.mContext, this.list_questionid, new KuangBeiStaDataBean(), this.mHandler);
        this.myviewPagerAdapter = kuangBeiPagerAdapter;
        this.viewPager.setAdapter(kuangBeiPagerAdapter);
        this.viewPager.setCurrentItem(this.indext);
        this.viewPager.setOffscreenPageLimit(1);
        this.mHandler.sendEmptyMessage(10);
        this.mHandler.sendEmptyMessage(11);
        textView.setText(getIntent().getStringExtra("chapter_name"));
    }

    public void intPostView() {
        this.questiondetails_bottom_layout.setVisibility(8);
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ver_fadint));
        this.line_viewok.setVisibility(0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(CommonParameter.Comment_library_Red_Dot)) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext)) {
                this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg));
            } else {
                this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_night));
            }
        }
        if (str.equals("QuestionDetailActivity_note_delete_Bei")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.btn_edit));
            } else {
                this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.btn_edit_night));
            }
        }
        if (str.equals("QuestionDetailActivity_note_add_Bei")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.btn_edited));
            } else {
                this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.btn_edited_night));
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        finish();
        EventBus.getDefault().post("SubKuangisRefulf1");
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.indext = getIntent().getIntExtra("position", 0);
        this.list_questionid = getIntent().getLongArrayExtra("list");
        setSwipeBackEnable(false);
        setContentView(R.layout.activity_kuangbei_detail);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14193c.lambda$setContentView$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_actionbar_right.setVisibility(8);
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setImageResource(R.drawable.kuangbei_open);
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.sm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13927c.lambda$setListenerForWidget$1(view);
            }
        });
        this.btn_comment.setOnClickListener(this.mOnclick);
        this.questiondetails_btn_collect.setOnClickListener(this.mOnclick);
        this.viewPager.setOnListener(new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.tm
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f13961a.lambda$setListenerForWidget$2(i2);
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiDetailActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == 2) {
                    SubjectKuangBeiDetailActivity.this.mHandler.sendEmptyMessage(11);
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg0) {
                List<KuangBeiStaDataBean.DataBean> data;
                SubjectKuangBeiDetailActivity.this.indext = arg0;
                AnsweredQuestionBeanBei answeredQuestionBeanBeiLoadByRowId = ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().loadByRowId(SubjectKuangBeiDetailActivity.this.list_questionid[arg0]);
                if (answeredQuestionBeanBeiLoadByRowId == null) {
                    SubjectKuangBeiDetailActivity.this.myviewPagerAdapter.setWebViewRefushShow(SubjectKuangBeiDetailActivity.this.indext);
                } else if (TextUtils.isEmpty(answeredQuestionBeanBeiLoadByRowId.getIs_right())) {
                    SubjectKuangBeiDetailActivity.this.mHandler.sendEmptyMessage(1);
                } else {
                    SubjectKuangBeiDetailActivity.this.mHandler.sendEmptyMessage(2);
                }
                NotesBeanBei notesBeanBeiLoad = ProjectApp.mDaoSession.getNotesBeanBeiDao().load(Long.valueOf(SubjectKuangBeiDetailActivity.this.list_questionid[arg0]));
                FavoritesBeanBei favoritesBeanBeiLoad = ProjectApp.mDaoSession.getFavoritesBeanBeiDao().load(Long.valueOf(SubjectKuangBeiDetailActivity.this.list_questionid[arg0]));
                try {
                    if (SubjectKuangBeiDetailActivity.this.mKKuangBeiStaDataBean != null && (data = SubjectKuangBeiDetailActivity.this.mKKuangBeiStaDataBean.getData()) != null && data.size() > 0) {
                        for (int i2 = 0; i2 < data.size(); i2++) {
                            if (data.get(i2).getQuestion_id() == SubjectKuangBeiDetailActivity.this.list_questionid[arg0]) {
                                if (data.get(i2).getIs_comment().equals("1")) {
                                    if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youpinglun));
                                    } else {
                                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youpinglun_night));
                                    }
                                } else if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                    SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.question_msg));
                                } else {
                                    SubjectKuangBeiDetailActivity.this.questiondetails_btn_centerMsg.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.question_msg_night));
                                }
                                if (data.get(i2).getIs_praise().equals("1")) {
                                    if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                        SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youdianzan));
                                    } else {
                                        SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.youdianzan_night));
                                    }
                                } else if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                                    SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.dianzancourse));
                                } else {
                                    SubjectKuangBeiDetailActivity.this.questiondetails_zantong.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.dianzancourse_night));
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                    if (notesBeanBeiLoad == null) {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edit));
                    } else {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edited));
                    }
                } else if (notesBeanBeiLoad == null) {
                    SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edit_night));
                } else {
                    SubjectKuangBeiDetailActivity.this.questiondetails_btn_edit.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.btn_edited_night));
                }
                if (SkinManager.getCurrentSkinType(SubjectKuangBeiDetailActivity.this.mContext) == 0) {
                    if (favoritesBeanBeiLoad == null) {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_no));
                        return;
                    } else {
                        SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_yes));
                        return;
                    }
                }
                if (favoritesBeanBeiLoad == null) {
                    SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_no_night));
                } else {
                    SubjectKuangBeiDetailActivity.this.questiondetails_btn_collect.setBackground(MyNightUtil.getDrawable(SubjectKuangBeiDetailActivity.this.mContext, R.drawable.icon_collect_yes_night));
                }
            }
        });
    }
}
