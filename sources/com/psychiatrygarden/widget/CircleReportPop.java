package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.adapter.UploadPicAdapter;
import com.psychiatrygarden.bean.ReportParams;
import com.psychiatrygarden.bean.ReportReason;
import com.psychiatrygarden.bean.SelectPicItem;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ReportReasonPop;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class CircleReportPop extends BottomPopupView {
    private UploadPicAdapter mPicAdapter;
    private List<SelectPicItem> mPicItems;
    private ReportParams mReportParams;
    private List<ReportReason> mReportReasonList;

    public CircleReportPop(Context context, ReportParams params) {
        super(context);
        this.mReportParams = params;
    }

    private void getReportReason() {
        YJYHttpUtils.get(getContext(), NetworkRequestsURL.reportReason, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.CircleReportPop.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code", ""))) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data", ""), new TypeToken<List<ReportReason>>() { // from class: com.psychiatrygarden.widget.CircleReportPop.2.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            return;
                        }
                        CircleReportPop.this.mReportReasonList = new ArrayList(list);
                        if (CircleReportPop.this.mReportParams != null) {
                            String reason_id = CircleReportPop.this.mReportParams.getReason_id();
                            if (TextUtils.isEmpty(reason_id)) {
                                return;
                            }
                            for (int i2 = 0; i2 < CircleReportPop.this.mReportReasonList.size(); i2++) {
                                if (TextUtils.equals(((ReportReason) CircleReportPop.this.mReportReasonList.get(i2)).getId(), reason_id)) {
                                    ((TextView) CircleReportPop.this.findViewById(R.id.tv_show_msg)).setText(((ReportReason) CircleReportPop.this.mReportReasonList.get(i2)).getTitle());
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1() {
        findViewById(R.id.ll_type).requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(TextView textView, String str, String str2) {
        if (this.mReportParams != null) {
            if (TextUtils.isEmpty(str)) {
                textView.setText("请选择举报类型");
                this.mReportParams.setReason_id(null);
            } else {
                this.mReportParams.setReason_id(str);
                textView.setText(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(final TextView textView, View view) {
        List<ReportReason> list = this.mReportReasonList;
        if (list == null || list.size() <= 0) {
            return;
        }
        new XPopup.Builder(getContext()).asCustom(new ReportReasonPop(getContext(), this.mReportReasonList, new ReportReasonPop.OnReasonSelectListener() { // from class: com.psychiatrygarden.widget.d3
            @Override // com.psychiatrygarden.widget.ReportReasonPop.OnReasonSelectListener
            public final void onSelect(String str, String str2) {
                this.f16393a.lambda$onCreate$2(textView, str, str2);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(EditText editText, View view) {
        ReportParams reportParams = this.mReportParams;
        if (reportParams == null) {
            return;
        }
        if (TextUtils.isEmpty(reportParams.getReason_id())) {
            ToastUtil.shortToast(getContext(), "请选择举报类型");
        } else {
            report(editText);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$5(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.mPicItems.size() < 2 && ((SelectPicItem) this.mPicAdapter.getItem(i2)).getItemType() == 1) {
            selectPic();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$6(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        SelectPicItem selectPicItem = (SelectPicItem) this.mPicAdapter.getItem(i2);
        if (selectPicItem.getItemType() == 2 && view.getId() == R.id.iv_delete) {
            ReportParams reportParams = this.mReportParams;
            if (reportParams != null && !TextUtils.isEmpty(reportParams.getReport_img())) {
                this.mReportParams.setReport_img(null);
            }
            selectPicItem.setType(1);
            selectPicItem.setLocalPath(null);
            selectPicItem.setUploadUrl(null);
            this.mPicAdapter.notifyItemChanged(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectPic$8() {
        ActivityCompat.requestPermissions((Activity) getContext(), Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectPic$9(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        LogUtils.d("imgSize", String.valueOf(ImageFactory.getImageSize(str)));
        SelectPicItem selectPicItem = new SelectPicItem(2);
        selectPicItem.setLocalPath(str);
        selectPicItem.setUploading(true);
        this.mPicItems.set(0, selectPicItem);
        this.mPicAdapter.notifyDataSetChanged();
        uploadImage(str);
    }

    private void report(final EditText content) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", this.mReportParams.getArticle_id());
        ajaxParams.put("reason_id", this.mReportParams.getReason_id());
        if (!TextUtils.isEmpty(this.mReportParams.getReport_description())) {
            ajaxParams.put("report_description", this.mReportParams.getReport_description());
        }
        if (!TextUtils.isEmpty(this.mReportParams.getReport_img())) {
            ajaxParams.put("report_img", this.mReportParams.getReport_img());
        }
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.circleReport, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.CircleReportPop.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code", ""))) {
                        content.setText("");
                        ToastUtil.shortToast(CircleReportPop.this.getContext(), "举报成功");
                        CircleReportPop.this.dismiss();
                    } else {
                        String strOptString = jSONObject.optString("message", "");
                        if (!TextUtils.isEmpty(strOptString)) {
                            ToastUtil.shortToast(CircleReportPop.this.getContext(), strOptString);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void selectPic() {
        if (!CommonUtil.hasRequiredPermissions(getContext())) {
            new XPopup.Builder(getContext()).asCustom(new RequestMediaPermissionPop(getContext(), new OnConfirmListener() { // from class: com.psychiatrygarden.widget.b3
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f16332a.lambda$selectPic$8();
                }
            })).show();
        } else {
            AndroidImagePicker.getInstance().setSelectLimit(1);
            AndroidImagePicker.getInstance().pickSingle((Activity) getContext(), true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.widget.c3
                @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                public final void onImagePickComplete(List list) {
                    this.f16358a.lambda$selectPic$9(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputLen(SpannableStringBuilder stringBuilder, String inputCount) {
        int color = Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#B2575C" : "#F95843");
        int color2 = Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#454C64" : "#C2C6CB");
        stringBuilder.clear();
        stringBuilder.clearSpans();
        if (TextUtils.isEmpty(inputCount)) {
            inputCount = "0";
        }
        stringBuilder.append((CharSequence) inputCount);
        stringBuilder.setSpan(new ForegroundColorSpan(color), 0, stringBuilder.length(), 18);
        int length = stringBuilder.length();
        stringBuilder.append((CharSequence) "/200");
        stringBuilder.setSpan(new ForegroundColorSpan(color2), length, stringBuilder.length(), 34);
        ((TextView) findViewById(R.id.tv_text_len)).setText(stringBuilder);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_circle_report;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        getHostWindow().setLayout(-1, -1);
        getHostWindow().setSoftInputMode(48);
        final EditText editText = (EditText) findViewById(R.id.et_input_content);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.u2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16944c.lambda$onCreate$0(view);
            }
        });
        findViewById(R.id.ll_type).post(new Runnable() { // from class: com.psychiatrygarden.widget.v2
            @Override // java.lang.Runnable
            public final void run() {
                this.f16980c.lambda$onCreate$1();
            }
        });
        final TextView textView = (TextView) findViewById(R.id.tv_show_msg);
        findViewById(R.id.ll_type).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17027c.lambda$onCreate$3(textView, view);
            }
        });
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.x2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17072c.lambda$onCreate$4(editText, view);
            }
        });
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ContextCompat.getDrawable(getContext(), R.drawable.ic_down_arrow_night), (Drawable) null);
        }
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        updateInputLen(spannableStringBuilder, "0");
        ReportParams reportParams = this.mReportParams;
        if (reportParams != null) {
            String report_description = reportParams.getReport_description();
            if (!TextUtils.isEmpty(report_description)) {
                editText.setText(report_description);
                updateInputLen(spannableStringBuilder, String.valueOf(report_description.length()));
            }
        }
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        editText.addTextChangedListener(new SimpleTextWatcher() { // from class: com.psychiatrygarden.widget.CircleReportPop.1
            @Override // com.psychiatrygarden.widget.SimpleTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                super.afterTextChanged(s2);
                CircleReportPop.this.updateInputLen(spannableStringBuilder, TextUtils.isEmpty(s2) ? "0" : String.valueOf(s2.length()));
                if (CircleReportPop.this.mReportParams != null) {
                    CircleReportPop.this.mReportParams.setReport_description(TextUtils.isEmpty(s2) ? "" : s2.toString());
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_pic);
        ArrayList arrayList = new ArrayList();
        this.mPicItems = arrayList;
        arrayList.add(new SelectPicItem(1));
        UploadPicAdapter uploadPicAdapter = new UploadPicAdapter(this.mPicItems);
        this.mPicAdapter = uploadPicAdapter;
        uploadPicAdapter.setList(this.mPicItems);
        this.mPicAdapter.addChildClickViewIds(R.id.iv_delete);
        recyclerView.setAdapter(this.mPicAdapter);
        this.mPicAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.y2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f17112c.lambda$onCreate$5(baseQuickAdapter, view, i2);
            }
        });
        this.mPicAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.widget.z2
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f17140c.lambda$onCreate$6(baseQuickAdapter, view, i2);
            }
        });
        editText.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.a3
            @Override // java.lang.Runnable
            public final void run() {
                editText.setEnabled(true);
            }
        }, 1500L);
        getReportReason();
        ReportParams reportParams2 = this.mReportParams;
        if (reportParams2 != null) {
            String report_img = reportParams2.getReport_img();
            if (TextUtils.isEmpty(report_img)) {
                return;
            }
            SelectPicItem selectPicItem = new SelectPicItem(2);
            selectPicItem.setUploadUrl(report_img);
            selectPicItem.setUploading(false);
            selectPicItem.setLocalPath(report_img);
            this.mPicItems.set(0, selectPicItem);
            this.mPicAdapter.notifyDataSetChanged();
        }
    }

    public void uploadImage(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
            YJYHttpUtils.postImage(getContext(), NetworkRequestsURL.uploadForComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.CircleReportPop.3
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    NewToast.showShort(CircleReportPop.this.getContext(), "上传失败！", 0).show();
                    AndroidImagePicker.getInstance().clearImageSets();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass3) s2);
                    try {
                        if (new JSONObject(s2).optString("code").equals("200")) {
                            String strOptString = new JSONObject(s2).optJSONObject("data").optString("b_img");
                            SelectPicItem selectPicItem = (SelectPicItem) CircleReportPop.this.mPicItems.get(0);
                            selectPicItem.setUploading(false);
                            selectPicItem.setUploadUrl(strOptString);
                            CircleReportPop.this.mPicAdapter.notifyDataSetChanged();
                            if (CircleReportPop.this.mReportParams != null) {
                                CircleReportPop.this.mReportParams.setReport_img(strOptString);
                            }
                        } else {
                            NewToast.showShort(CircleReportPop.this.getContext(), new JSONObject(s2).optString("message"), 0).show();
                            AndroidImagePicker.getInstance().clearImageSets();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        AndroidImagePicker.getInstance().clearImageSets();
                    }
                }
            });
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public CircleReportPop(@NonNull Context context) {
        super(context);
    }
}
