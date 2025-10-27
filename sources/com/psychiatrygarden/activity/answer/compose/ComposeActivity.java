package com.psychiatrygarden.activity.answer.compose;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.answer.bean.AnalysisBean;
import com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.FullyGridLayoutManager;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ComposeActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private GridImageAdapter adapter;
    private CheckBox checkid;
    private EditText editup;
    private String identity_id;
    private AnalysisBean.DataBean mAnalysisBean;
    private long question_id;
    private final List<String> imageStr = new ArrayList();
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new AnonymousClass1();

    /* renamed from: com.psychiatrygarden.activity.answer.compose.ComposeActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements GridImageAdapter.onAddPicClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAddPicClick$0() {
            ActivityCompat.requestPermissions((Activity) ComposeActivity.this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
        }

        @Override // com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter.onAddPicClickListener
        public void deleteData(int position) {
            if (ComposeActivity.this.imageStr.size() <= position || TextUtils.equals("none", (String) ComposeActivity.this.imageStr.get(position))) {
                return;
            }
            ComposeActivity.this.imageStr.remove(position);
        }

        @Override // com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter.onAddPicClickListener
        public void onAddPicClick() {
            if (CommonUtil.hasRequiredPermissions(ComposeActivity.this.mContext)) {
                ComposeActivity.this.selectImg();
            } else {
                new XPopup.Builder(ComposeActivity.this.mContext).asCustom(new RequestMediaPermissionPop(ComposeActivity.this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.answer.compose.c
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f11017a.lambda$onAddPicClick$0();
                    }
                })).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectImg$0(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        if (mimeTypeFromFile.equalsIgnoreCase("IMAGE/WEBP")) {
            AlertToast("不支持此文件格式，请选择其它图片上传！");
        } else if (ImageFactory.getImageSize(str) > 10.0f) {
            NewToast.showShort(this.mContext, "请选择小于10M的图片上传", 0).show();
        } else {
            getImageData(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (TextUtils.isEmpty(this.editup.getText().toString())) {
            ToastUtil.shortToast(this.mContext, "请输入内容");
        } else {
            commitData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectImg() {
        AndroidImagePicker.getInstance().setSelectLimit(1);
        AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.answer.compose.b
            @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
            public final void onImagePickComplete(List list) {
                this.f11016a.lambda$selectImg$0(list);
            }
        });
    }

    public void commitData() {
        AjaxParams ajaxParams = new AjaxParams();
        List<String> list = this.imageStr;
        if (list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.imageStr.size(); i2++) {
                if (!"none".equals(this.imageStr.get(i2))) {
                    arrayList.add(this.imageStr.get(i2));
                }
            }
            ajaxParams.put("images", new Gson().toJson(arrayList));
        }
        AnalysisBean.DataBean dataBean = this.mAnalysisBean;
        if (dataBean != null && !"".equals(dataBean.getId())) {
            ajaxParams.put("id", this.mAnalysisBean.getId());
        }
        ajaxParams.put("is_hidden", this.checkid.isChecked() ? "1" : "0");
        ajaxParams.put("question_id", this.question_id + "");
        if (!TextUtils.isEmpty(this.identity_id)) {
            ajaxParams.put("identity_id", this.identity_id);
        }
        ajaxParams.put("analysis", "" + this.editup.getText().toString());
        QuestionDataRequest.getIntance(this).questionSARAnalysisData(ajaxParams, this);
    }

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        QuestionDataRequest.getIntance(this).questionPutImgData(ajaxParams, this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.identity_id = getIntent().getExtras().getString("identity_id", "");
        this.question_id = getIntent().getExtras().getLong("question_id");
        this.mAnalysisBean = (AnalysisBean.DataBean) getIntent().getSerializableExtra("mAnalysisBean");
        this.checkid = (CheckBox) findViewById(R.id.checkid);
        this.editup = (EditText) findViewById(R.id.editup);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        int i2 = 0;
        recyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3, 1, false));
        GridImageAdapter gridImageAdapter = new GridImageAdapter(this.imageStr, 9, this.onAddPicClickListener);
        this.adapter = gridImageAdapter;
        recyclerView.setAdapter(gridImageAdapter);
        this.checkid.setVisibility(8);
        if (!TextUtils.isEmpty(ProjectApp.analysisContent) || ProjectApp.analysisImageStr.size() > 0) {
            this.editup.setText(ProjectApp.analysisContent);
            this.checkid.setChecked(ProjectApp.analysisIsHidde);
            List<String> list = ProjectApp.analysisImageStr;
            if (list != null && list.size() > 0) {
                while (i2 < ProjectApp.analysisImageStr.size()) {
                    ProjectApp.analysisImageStr.size();
                    this.imageStr.add(ProjectApp.analysisImageStr.get(i2));
                    i2++;
                }
                this.adapter.setList(ProjectApp.analysisImageStr);
                this.adapter.notifyDataSetChanged();
            }
        } else if (!TextUtils.equals(this.mAnalysisBean.getId(), "")) {
            this.editup.setText(this.mAnalysisBean.getAnalysis());
            this.checkid.setChecked("1".equals(this.mAnalysisBean.getIs_hidden()));
            if (this.mAnalysisBean.getImages() != null && this.mAnalysisBean.getImages().size() > 0) {
                while (i2 < this.mAnalysisBean.getImages().size()) {
                    this.mAnalysisBean.getImages().size();
                    this.imageStr.add(this.mAnalysisBean.getImages().get(i2));
                    i2++;
                }
                this.adapter.setList(this.mAnalysisBean.getImages());
                this.adapter.notifyDataSetChanged();
            }
        }
        if (this.adapter.getData().isEmpty()) {
            this.adapter.setList((List<String>) new ArrayList());
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, cn.webdemo.com.supporfragment.ISupportActivity
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        AndroidImagePicker.getInstance().clearImageSets();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        ToastUtil.shortToast(this, strMsg);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            int i2 = grantResults[0];
            if (i2 != -1) {
                if (i2 == 0) {
                    selectImg();
                }
            } else {
                boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA);
                boolean zShouldShowRequestPermissionRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE);
                if (zShouldShowRequestPermissionRationale || zShouldShowRequestPermissionRationale2) {
                    return;
                }
                ToastUtil.shortToast(this, "请检查app相机及存储权限是否打开！");
            }
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        ProjectApp.analysisContent = this.editup.getText().toString();
        ProjectApp.analysisImageStr = this.imageStr;
        ProjectApp.analysisIsHidde = this.checkid.isChecked();
        super.onStop();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.fragment_compose);
        setTitle("撰写解析");
        this.mBtnActionbarRight.setText("提交");
        this.mBtnActionbarRight.setVisibility(0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.compose.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11015c.lambda$setListenerForWidget$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            if (requstUrl.equals(NetworkRequestsURL.getsaveAnalysisApi)) {
                this.editup.setText("");
                this.imageStr.clear();
                ProjectApp.analysisContent = "";
                ProjectApp.analysisImageStr = new ArrayList();
                EventBus.getDefault().post((AnalysisBean.DataBean) new Gson().fromJson(new JSONObject(s2).optString("data"), AnalysisBean.DataBean.class));
                finish();
            } else if (requstUrl.equals(NetworkRequestsURL.stockApi)) {
                this.imageStr.add(0, new JSONObject(s2).optJSONObject("data").optString("url"));
                this.adapter.setList((List<String>) new ArrayList(this.imageStr));
                this.adapter.notifyDataSetChanged();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
