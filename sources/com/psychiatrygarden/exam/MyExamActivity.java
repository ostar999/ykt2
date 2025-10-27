package com.psychiatrygarden.exam;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.catchpig.mvvm.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.ExamDescriptionActivity;
import com.psychiatrygarden.bean.ExamInfoBean;
import com.psychiatrygarden.bean.MyExamDataBean;
import com.psychiatrygarden.bean.MyExamsBean;
import com.psychiatrygarden.exam.RvCountDownHelper;
import com.psychiatrygarden.exam.adapter.MyExamAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.InputCodeExamPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.yikaobang.yixue.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyExamActivity extends BaseActivity {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 1;
    private static final int REQUEST_CODE_SCAN_QR_CODE = 999;
    private InputCodeExamPopup codeExamPopup;
    private MyExamAdapter mAdapter;
    private SmartRefreshLayout smartRefreshLayout;

    private void checkCameraAndJump2ScanPage() {
        if (!(ContextCompat.checkSelfPermission(this, Permission.CAMERA) == 0)) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 1);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 1);
        } else {
            startActivityForResult(new Intent(this, (Class<?>) CaptureActivity.class), 999, null);
        }
    }

    private void checkExamInfo(final String examCode, String id) {
        AjaxParams ajaxParams = new AjaxParams();
        if (examCode != null) {
            ajaxParams.put("code", examCode);
        }
        if (id != null) {
            ajaxParams.put("exam_id", id);
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.GET_EXAM_INFO, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.exam.MyExamActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyExamActivity.this.hideProgressDialog();
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                MyExamActivity.this.AlertToast(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                MyExamActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                MyExamActivity.this.hideProgressDialog();
                try {
                    if (TextUtils.isEmpty(s2)) {
                        return;
                    }
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code", 0) == 200) {
                        ExamInfoBean examInfoBean = (ExamInfoBean) new Gson().fromJson(s2, ExamInfoBean.class);
                        Intent intent = new Intent();
                        intent.setClass(MyExamActivity.this, ExamDescriptionActivity.class).putExtra("code", examCode).putExtra("title", examInfoBean.getData().getTitle()).putExtra("exam_id", examInfoBean.getData().getId() + "");
                        MyExamActivity.this.startActivity(intent);
                    } else {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            MyExamActivity.this.AlertToast(strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$init$0(RecyclerView.ViewHolder viewHolder, int i2) {
        MyExamDataBean myExamDataBean = (MyExamDataBean) this.mAdapter.getItem(i2);
        if (myExamDataBean.getStatus() != 0 || myExamDataBean.getRemainTimeStamp() >= 1000) {
            return;
        }
        this.smartRefreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        checkExamInfo(null, String.valueOf(((MyExamDataBean) this.mAdapter.getItem(i2)).getId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        checkCameraAndJump2ScanPage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(String str) {
        checkExamInfo(str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        if (this.codeExamPopup == null) {
            this.codeExamPopup = (InputCodeExamPopup) new XPopup.Builder(this).asCustom(new InputCodeExamPopup(view.getContext(), new InputCodeExamPopup.ExamCodeConfirmListener() { // from class: com.psychiatrygarden.exam.a
                @Override // com.psychiatrygarden.widget.InputCodeExamPopup.ExamCodeConfirmListener
                public final void onConfirm(String str) {
                    this.f15302a.lambda$init$3(str);
                }
            }));
        }
        this.codeExamPopup.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(RefreshLayout refreshLayout) {
        loadApiData();
    }

    private void loadApiData() {
        YJYHttpUtils.post(this, NetworkRequestsURL.USER_EXAM, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.exam.MyExamActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (t2 != null) {
                    t2.printStackTrace();
                }
                if (!TextUtils.isEmpty(strMsg)) {
                    MyExamActivity.this.AlertToast(strMsg);
                }
                MyExamActivity.this.smartRefreshLayout.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws ParseException {
                Calendar calendar;
                Calendar calendar2;
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (TextUtils.isEmpty(s2)) {
                        return;
                    }
                    MyExamsBean myExamsBean = (MyExamsBean) new Gson().fromJson(s2, MyExamsBean.class);
                    if (myExamsBean != null && myExamsBean.isSuccess()) {
                        Calendar calendar3 = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-M-dd HH:mm", Locale.CHINA);
                        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("M-dd HH:mm", Locale.CHINA);
                        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(DateUtil.TIME_FORMAT_WITH_HM, Locale.CHINA);
                        long j2 = 1000;
                        calendar3.setTimeInMillis(myExamsBean.getPrc_time() * 1000);
                        int i2 = calendar3.get(1);
                        Iterator<MyExamDataBean> it = myExamsBean.getData().iterator();
                        while (it.hasNext()) {
                            MyExamDataBean next = it.next();
                            Date date = simpleDateFormat.parse(next.getStart_time());
                            Date date2 = simpleDateFormat.parse(next.getEnt_time());
                            if (date == null || date2 == null) {
                                calendar = calendar3;
                            } else {
                                if (next.getStatus() == 0) {
                                    calendar2 = calendar3;
                                    next.setStartTimeStamp(date.getTime());
                                    next.setServerTime(myExamsBean.getPrc_time() * j2);
                                    long time = date.getTime();
                                    long prc_time = myExamsBean.getPrc_time();
                                    Long.signum(prc_time);
                                    next.setRemainTimeStamp((time - (prc_time * j2)) + SystemClock.elapsedRealtime());
                                    if (((int) (((date.getTime() - (myExamsBean.getPrc_time() * j2)) / 3600) / j2)) < 24) {
                                        next.setStatus(3);
                                    }
                                } else {
                                    calendar2 = calendar3;
                                }
                                if (date2.getTime() < myExamsBean.getPrc_time() * j2 && next.getStatus() != 2) {
                                    next.setStatus(2);
                                }
                                calendar = calendar2;
                                calendar.setTime(date);
                                int i3 = calendar.get(1);
                                int i4 = calendar.get(5);
                                calendar.setTime(date2);
                                String str = i4 == calendar.get(5) ? simpleDateFormat4.format(date2) : simpleDateFormat3.format(date2);
                                if (i2 != i3) {
                                    next.setStartTimeShow(simpleDateFormat2.format(date) + Constants.WAVE_SEPARATOR + str);
                                } else {
                                    next.setStartTimeShow(simpleDateFormat3.format(date) + Constants.WAVE_SEPARATOR + str);
                                }
                            }
                            calendar3 = calendar;
                            j2 = 1000;
                        }
                        MyExamActivity.this.mAdapter.setList(myExamsBean.getData());
                    } else if (myExamsBean != null) {
                        MyExamActivity.this.AlertToast(myExamsBean.getMessage());
                    }
                    MyExamActivity.this.smartRefreshLayout.finishRefresh(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    MyExamActivity.this.smartRefreshLayout.finishRefresh(true);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        this.mAdapter = new MyExamAdapter(recyclerView, new RvCountDownHelper.OnTimeCollectListener() { // from class: com.psychiatrygarden.exam.b
            @Override // com.psychiatrygarden.exam.RvCountDownHelper.OnTimeCollectListener
            public final void onTimeCollect(RecyclerView.ViewHolder viewHolder, int i2) {
                this.f15303a.lambda$init$0(viewHolder, i2);
            }
        });
        getLifecycle().addObserver(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.exam.c
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15304c.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setEmptyView(R.layout.layout_empty_view);
        findViewById(R.id.tv_scan_qr).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.exam.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15305c.lambda$init$2(view);
            }
        });
        findViewById(R.id.tv_input_exam_code).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.exam.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15306c.lambda$init$4(view);
            }
        });
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh);
        this.smartRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.exam.f
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15307c.lambda$init$5(refreshLayout);
            }
        }).setEnableLoadMore(false).setEnableLoadMoreWhenContentNotFull(false);
        this.smartRefreshLayout.autoRefresh();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bundle extras;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 999 || data == null || (extras = data.getExtras()) == null || extras.getInt(CodeUtils.RESULT_TYPE) != 1) {
            return;
        }
        String string = extras.getString(CodeUtils.RESULT_STRING);
        LogUtils.d(getClass().getSimpleName(), "result=" + string);
        String[] strArrSplit = string.split("=");
        if (strArrSplit == null || strArrSplit.length != 2) {
            return;
        }
        checkExamInfo(null, strArrSplit[1]);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.view.Window.Callback
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length == 1) {
            int i2 = grantResults[0];
            if (i2 == 0) {
                ActivityCompat.startActivityForResult(this, new Intent(this, (Class<?>) CaptureActivity.class), 999, null);
            } else {
                if (i2 != -1 || ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA)) {
                    return;
                }
                ToastUtil.shortToast(this, "请检查app相机权限是否打开！");
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_my_exam);
        setTitle(getString(R.string.my_exam));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
