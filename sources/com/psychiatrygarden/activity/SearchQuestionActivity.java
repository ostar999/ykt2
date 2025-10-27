package com.psychiatrygarden.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.adapter.SearchQuestionAdapter;
import com.psychiatrygarden.bean.CourseChapterBean;
import com.psychiatrygarden.bean.QuestionSearchBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SearchQuestionActivity extends BaseActivity implements SearchQuestionAdapter.WebClickOnIm, QuestionDataCallBack<String> {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 1;
    private static final int REQUEST_CODE_SCAN_QR_CODE = 999;
    public TextView allSections;
    public TextView allTypes;
    private LinearLayout banners;
    public TextView comprehensiveSorting;
    private EditText ed_search;
    private SearchQuestionAdapter mAdapter;
    private QuestionDetailBean questionDetailBean;
    private final List<QuestionSearchBean.DataDTO> list = new ArrayList();
    private int page = 1;
    private String qsBank = "";
    private String sortType = "";
    private String questionType = "";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$3(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.ed_search.getText().toString().equals("")) {
            this.mAdapter.setSearchContent(this.ed_search.getText().toString().trim());
            this.page = 1;
            QuestionDataRequest.getIntance(this).questionSearch(this.page, this.ed_search.getText().toString().trim(), "", "", "", this);
            String str = "[\"" + this.ed_search.getText().toString().trim() + "\"]";
            AliyunEvent aliyunEvent = AliyunEvent.SearchQuestion;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", str, "", "2");
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        if (isLogin()) {
            if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
                new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.yi
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f14219a.lambda$init$4();
                    }
                })).show();
                return;
            }
            if (!(ContextCompat.checkSelfPermission(this.mContext, Permission.CAMERA) == 0)) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 1);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 1);
            } else {
                startActivityForResult(new Intent(this.mContext, (Class<?>) CaptureActivity.class), 999, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0() {
        this.page++;
        QuestionDataRequest.getIntance(this).questionSearch(this.page, this.ed_search.getText().toString().trim(), "", "", "", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        QuestionDataRequest.getIntance(this).questionInfo(this.list.get(i2).getId(), "1", this);
    }

    private void loadVideoInfo(String videoId, String questionId) {
        QuestionDataRequest.getIntance(this.mContext).questionVideo(questionId, videoId, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.activity.SearchQuestionActivity.2
            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onStart(String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onSuccess(String s2, String requstUrl) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        CourseChapterBean courseChapterBean = (CourseChapterBean) new Gson().fromJson(jSONObject.optString("data"), CourseChapterBean.class);
                        if (courseChapterBean != null) {
                            Intent intent = new Intent();
                            intent.setClass(SearchQuestionActivity.this, AliperCommentActivity.class);
                            intent.putExtra("obj_id", courseChapterBean.getId());
                            intent.putExtra("free_watch_time", courseChapterBean.getFree_watch_time());
                            intent.putExtra("watch_permission", "1");
                            intent.putExtra("expire_str", courseChapterBean.getExpire_str());
                            intent.putExtra("realVideo", true);
                            intent.putExtra("module_type", 10);
                            intent.putExtra("vid", courseChapterBean.getVid());
                            intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "real_question_video");
                            intent.putExtra("commentEnum", DiscussStatus.QuestionBankVideo);
                            SearchQuestionActivity.this.startActivity(intent);
                        } else {
                            NewToast.showShort(SearchQuestionActivity.this, "获取视频源失败", 0).show();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.banners = (LinearLayout) findViewById(R.id.banners);
        TextView textView = (TextView) findViewById(R.id.searchview);
        final ImageView imageView = (ImageView) findViewById(R.id.btn_scan);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ti
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13955c.lambda$init$2(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0, CommonUtil.dip2px(this, 10.0f), ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 0 ? R.color.gray_line_126 : R.color.app_theme_night)));
        SearchQuestionAdapter searchQuestionAdapter = new SearchQuestionAdapter(this.list, this);
        this.mAdapter = searchQuestionAdapter;
        searchQuestionAdapter.setHeaderWithEmptyEnable(true);
        recyclerView.setAdapter(this.mAdapter);
        EditText editText = (EditText) findViewById(R.id.ed_search);
        this.ed_search = editText;
        editText.setText("");
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.ui
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return this.f13991c.lambda$init$3(textView2, i2, keyEvent);
            }
        });
        this.ed_search.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.SearchQuestionActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (SearchQuestionActivity.this.ed_search.getText().toString().trim().length() > 0) {
                    imageView.setVisibility(8);
                } else {
                    imageView.setVisibility(0);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14031c.lambda$init$5(view);
            }
        });
    }

    public void isSelected(boolean comprehensiveSortingBoolean, boolean allSectionsBoolean, boolean allTypesBoolean) {
        this.comprehensiveSorting.setSelected(comprehensiveSortingBoolean);
        this.allSections.setSelected(allSectionsBoolean);
        this.allTypes.setSelected(allTypesBoolean);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bundle extras;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 999 || data == null || (extras = data.getExtras()) == null || extras.getInt(CodeUtils.RESULT_TYPE) != 1) {
            return;
        }
        String string = extras.getString(CodeUtils.RESULT_STRING);
        LogUtils.e("scan_code_res", "result=" + string);
        String[] strArrSplit = string.split("=");
        if (strArrSplit == null || strArrSplit.length != 3) {
            return;
        }
        String str = strArrSplit[1].split("&")[0];
        String str2 = strArrSplit[2];
        LogUtils.e("scan_code_res", "id=" + str + ";questionid=" + str2);
        loadVideoInfo(str, str2);
    }

    @Override // com.psychiatrygarden.adapter.SearchQuestionAdapter.WebClickOnIm
    public void onClick(int position) {
        QuestionDataRequest.getIntance(this).questionInfo(this.list.get(position).getId(), "1", this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        ProjectApp.instance().hideDialogWindow();
        if (!requstUrl.equals(NetworkRequestsURL.searchQuestionApi)) {
            ToastUtil.shortToast(this, "请求失败");
        } else if (this.page == 1) {
            ToastUtil.shortToast(this, "请求失败");
        } else {
            this.mAdapter.getLoadMoreModule().loadMoreFail();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length == 1) {
            int i2 = grantResults[0];
            if (i2 == 0) {
                ActivityCompat.startActivityForResult(this, new Intent(this.mContext, (Class<?>) CaptureActivity.class), 999, null);
            } else {
                if (i2 != -1 || ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA)) {
                    return;
                }
                ToastUtil.shortToast(this.mContext, "请检查app相机权限是否打开！");
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        ProjectApp.instance().showDialogWindow();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("搜索");
        setContentView(R.layout.activity_search_question);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.wi
            @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
            public final void onLoadMore() {
                this.f14155c.lambda$setListenerForWidget$0();
            }
        });
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.xi
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f14187c.lambda$setListenerForWidget$1(baseQuickAdapter, view, i2);
            }
        });
    }

    public void showBanner() {
        SearchQuestionAdapter searchQuestionAdapter = this.mAdapter;
        if (searchQuestionAdapter != null) {
            if (searchQuestionAdapter.getData().size() > 0) {
                this.banners.setVisibility(8);
            } else {
                this.banners.setVisibility(8);
            }
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String str, String requstUrl) {
        ProjectApp.instance().hideDialogWindow();
        try {
            if (requstUrl.equals(NetworkRequestsURL.searchQuestionApi)) {
                QuestionSearchBean questionSearchBean = (QuestionSearchBean) new Gson().fromJson(str, QuestionSearchBean.class);
                if (questionSearchBean.getCode().equals("200")) {
                    this.mAdapter.setEmptyView(R.layout.layout_empty_view);
                    if (this.page == 1) {
                        this.list.clear();
                        this.list.addAll(questionSearchBean.getData());
                        this.mAdapter.notifyDataSetChanged();
                    } else {
                        this.list.addAll(questionSearchBean.getData());
                        this.mAdapter.addData((Collection) questionSearchBean.getData());
                        if (questionSearchBean.getData().size() > 0) {
                            this.mAdapter.getLoadMoreModule().loadMoreComplete();
                        } else {
                            this.mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                } else {
                    if (this.page == 1) {
                        this.list.clear();
                        this.list.addAll(questionSearchBean.getData());
                        this.mAdapter.notifyDataSetChanged();
                    }
                    ToastUtil.shortToast(this, questionSearchBean.getMessage());
                }
                showBanner();
                return;
            }
            if (!requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
                if (requstUrl.equals(NetworkRequestsURL.questionInfoApi)) {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.optInt("code") != 200) {
                        ToastUtil.shortToast(this, jSONObject.optString("message"));
                        return;
                    }
                    this.questionDetailBean = (QuestionDetailBean) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data")), QuestionDetailBean.class);
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("question_id", this.questionDetailBean.getId());
                    ajaxParams.put("module_type", "1");
                    QuestionDataRequest.getIntance(this).questionUserAnswer(ajaxParams, this);
                    return;
                }
                return;
            }
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2.optInt("code") != 200) {
                    ArrayList arrayList = new ArrayList();
                    if (TextUtils.isEmpty(this.questionDetailBean.getSort())) {
                        this.questionDetailBean.setSort("1");
                    }
                    arrayList.add(this.questionDetailBean);
                    ProjectApp.showQuestionList = new Gson().toJson(arrayList);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    bundle.putString("module_type", "1");
                    bundle.putString("externalsources", "");
                    bundle.putString("answerMode", Constants.ANSWER_MODE.PRACTICE_MODE);
                    bundle.putString("subject_title", this.questionDetailBean.getChapter_parent_title());
                    bundle.putString("chapter_title", this.questionDetailBean.getChapter_title());
                    bundle.putBoolean("fromQuestionCombine", true);
                    bundle.putBoolean("fromSearch", true);
                    AnswerQuestionActivity.gotoActivity(this, bundle);
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                if (jSONObject2.getJSONArray("data").length() > 0) {
                    this.questionDetailBean.setUser_answer(jSONObject2.getJSONArray("data").getJSONObject(0).optString("answer"));
                }
                if (TextUtils.isEmpty(this.questionDetailBean.getSort())) {
                    this.questionDetailBean.setSort("1");
                }
                arrayList2.add(this.questionDetailBean);
                ProjectApp.showQuestionList = new Gson().toJson(arrayList2);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("position", 0);
                bundle2.putString("module_type", "1");
                bundle2.putString("externalsources", "");
                bundle2.putString("subject_title", this.questionDetailBean.getChapter_parent_title());
                bundle2.putString("chapter_title", this.questionDetailBean.getChapter_title());
                if (TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
                    bundle2.putString("answerMode", Constants.ANSWER_MODE.PRACTICE_MODE);
                } else {
                    bundle2.putString("answerMode", Constants.ANSWER_MODE.RECITE_MODE);
                }
                bundle2.putBoolean("fromQuestionCombine", true);
                bundle2.putBoolean("fromSearch", true);
                AnswerQuestionActivity.gotoActivity(this, bundle2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
