package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.FullyGridLayoutManager;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ChoosePicDialog;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private GridImageAdapter adapter;
    private EditText et_feedback;
    private EditText et_wx;
    private ImageView imgshown;
    private boolean isShowCamera;
    private RelativeLayout relimg;
    private RelativeLayout uploadprogrss;
    private final List<String> urlList = new ArrayList();
    private final List<String> imageStr = new ArrayList();
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new AnonymousClass4();

    /* renamed from: com.psychiatrygarden.activity.FeedbackActivity$4, reason: invalid class name */
    public class AnonymousClass4 implements GridImageAdapter.onAddPicClickListener {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAddPicClick$0() {
            ActivityCompat.requestPermissions((Activity) FeedbackActivity.this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAddPicClick$1(boolean z2, ChoosePicDialog choosePicDialog) {
            FeedbackActivity.this.isShowCamera = z2;
            if (!CommonUtil.hasRequiredPermissions(FeedbackActivity.this.mContext)) {
                new XPopup.Builder(FeedbackActivity.this.mContext).asCustom(new RequestMediaPermissionPop(FeedbackActivity.this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.pa
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f13536a.lambda$onAddPicClick$0();
                    }
                })).show();
            } else {
                choosePicDialog.dismiss();
                FeedbackActivity.this.selectImg(z2);
            }
        }

        @Override // com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter.onAddPicClickListener
        public void deleteData(int position) {
            if (FeedbackActivity.this.imageStr.size() > position) {
                FeedbackActivity.this.imageStr.remove(position);
            }
            FeedbackActivity.this.adapter.setList((List<String>) new ArrayList());
            FeedbackActivity.this.adapter.notifyDataSetChanged();
        }

        @Override // com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter.onAddPicClickListener
        public void onAddPicClick() {
            new XPopup.Builder(FeedbackActivity.this).asCustom(new ChoosePicDialog(FeedbackActivity.this, new ChoosePicDialog.OnBtnClickLisenter() { // from class: com.psychiatrygarden.activity.oa
                @Override // com.psychiatrygarden.widget.ChoosePicDialog.OnBtnClickLisenter
                public final void onClickLisenter(boolean z2, ChoosePicDialog choosePicDialog) {
                    this.f13075a.lambda$onAddPicClick$1(z2, choosePicDialog);
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(List list) {
        if (list == null || list.size() <= 0) {
            this.relimg.setVisibility(8);
            this.uploadprogrss.setVisibility(8);
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        float imageSize = ImageFactory.getImageSize(str);
        LogUtils.d("imgSize", "" + imageSize);
        if (imageSize > 20.0f) {
            NewToast.showShort(this, "请选择小于10M的图片上传", 0).show();
            return;
        }
        getImageData(str);
        this.urlList.clear();
        this.urlList.add(((ImageItem) list.get(0)).path);
        this.uploadprogrss.setVisibility(0);
        this.relimg.setVisibility(0);
        Glide.with((FragmentActivity) this).load((Object) GlideUtils.generateUrl(((ImageItem) list.get(0)).path)).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into(this.imgshown);
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
    public void selectImg(boolean isCamera) {
        AndroidImagePicker.getInstance().setSelectLimit(1);
        AndroidImagePicker.getInstance().pickMulti(this, isCamera, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.na
            @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
            public final void onImagePickComplete(List list) {
                this.f13041a.lambda$selectImg$0(list);
            }
        });
    }

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this, NetworkRequestsURL.uploadForComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.FeedbackActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(FeedbackActivity.this, "上传失败！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        FeedbackActivity.this.imageStr.clear();
                        FeedbackActivity.this.imageStr.add(new JSONObject(s2).optJSONObject("data").optString("b_img"));
                        FeedbackActivity.this.adapter.setList((List<String>) new ArrayList(FeedbackActivity.this.imageStr));
                        FeedbackActivity.this.adapter.notifyDataSetChanged();
                    } else {
                        NewToast.showShort(FeedbackActivity.this, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ImageView imageView = (ImageView) findViewById(R.id.upimg);
        this.relimg = (RelativeLayout) findViewById(R.id.relimg);
        this.imgshown = (ImageView) findViewById(R.id.imgshown);
        ImageView imageView2 = (ImageView) findViewById(R.id.deleteimg);
        TextView textView = (TextView) findViewById(R.id.pushBtm);
        this.uploadprogrss = (RelativeLayout) findViewById(R.id.uploadprogrss);
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        this.imgshown.setOnClickListener(this);
        this.et_wx = (EditText) findViewById(R.id.et_wx);
        EditText editText = (EditText) findViewById(R.id.et_feedback);
        this.et_feedback = editText;
        editText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.FeedbackActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() > 500) {
                    FeedbackActivity.this.AlertToast("您的字数超出字数限制,请做出修改。");
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        new Timer().schedule(new TimerTask() { // from class: com.psychiatrygarden.activity.FeedbackActivity.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ((InputMethodManager) FeedbackActivity.this.et_feedback.getContext().getSystemService("input_method")).showSoftInput(FeedbackActivity.this.et_feedback, 0);
            }
        }, 500L);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3, 1, false));
        GridImageAdapter gridImageAdapter = new GridImageAdapter(this.imageStr, 1, this.onAddPicClickListener);
        this.adapter = gridImageAdapter;
        recyclerView.setAdapter(gridImageAdapter);
        this.adapter.setList((List<String>) new ArrayList());
        this.adapter.notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.deleteimg /* 2131362880 */:
                AndroidImagePicker.getInstance().clearImageSets();
                this.relimg.setVisibility(8);
                this.uploadprogrss.setVisibility(8);
                this.et_feedback.getText().length();
                this.urlList.clear();
                break;
            case R.id.imgshown /* 2131363814 */:
                if (this.uploadprogrss.getVisibility() == 0) {
                    NewToast.showShort(this, "上传中", 0).show();
                    break;
                } else {
                    try {
                        List<String> list = this.urlList;
                        if (list != null && list.size() > 0) {
                            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                            xPopupImageLoader.popupInfo = new PopupInfo();
                            xPopupImageLoader.setImageUrls(new ArrayList(this.urlList)).setSrcView(null, 0).show();
                            break;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                break;
            case R.id.pushBtm /* 2131366158 */:
                if (!CommonUtil.isFastClick()) {
                    if (this.et_feedback.getText().toString().length() <= 500) {
                        if (!this.et_feedback.getText().toString().equals("")) {
                            subFeedback();
                            break;
                        } else {
                            AlertToast("内容不能为空");
                            break;
                        }
                    } else {
                        AlertToast("您的字数超出限制,请做出修改。");
                        break;
                    }
                }
                break;
            case R.id.upimg /* 2131368941 */:
                if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
                    new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.la
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f12659a.lambda$onClick$1();
                        }
                    })).show();
                    break;
                } else {
                    AndroidImagePicker.getInstance().setSelectLimit(1);
                    AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.ma
                        @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                        public final void onImagePickComplete(List list2) {
                            this.f12693a.lambda$onClick$2(list2);
                        }
                    });
                    break;
                }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            int i2 = grantResults[0];
            if (i2 != -1) {
                if (i2 == 0) {
                    selectImg(this.isShowCamera);
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("意见反馈");
        setContentView(R.layout.activity_feedback);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void subFeedback() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", this.et_feedback.getText().toString());
        if (!TextUtils.isEmpty(this.et_wx.getText().toString())) {
            ajaxParams.put("wechat", this.et_wx.getText().toString());
        }
        List<String> list = this.imageStr;
        if (list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.imageStr.size(); i2++) {
                if (!"none".equals(this.imageStr.get(i2))) {
                    arrayList.add(this.imageStr.get(i2));
                }
            }
            if (!arrayList.isEmpty()) {
                ajaxParams.put("image", (String) arrayList.get(0));
            }
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mFeedUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.FeedbackActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                FeedbackActivity.this.hideProgressDialog();
                FeedbackActivity.this.AlertToast("提交失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                FeedbackActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        FeedbackActivity.this.AlertToast("提交成功");
                        FeedbackActivity.this.et_feedback.setText("");
                        FeedbackActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                FeedbackActivity.this.hideProgressDialog();
            }
        });
    }
}
