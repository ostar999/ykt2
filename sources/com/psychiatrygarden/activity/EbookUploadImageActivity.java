package com.psychiatrygarden.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EbookUploadImageActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        if (ImageFactory.getImageSize(str) > 20.0f) {
            NewToast.showShort(getApplicationContext(), "请选择小于10M的图片上传", 0).show();
        } else {
            uploadImage(str);
        }
    }

    private void uploadImage(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(imgUrl));
            showProgressDialog("正在上传...");
            YJYHttpUtils.postImage(getApplicationContext(), NetworkRequestsURL.uploadForComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.EbookUploadImageActivity.1
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    EbookUploadImageActivity.this.hideProgressDialog();
                    NewToast.showShort(EbookUploadImageActivity.this.getApplicationContext(), "上传失败！", 0).show();
                    EbookUploadImageActivity.this.finish();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass1) s2);
                    EbookUploadImageActivity.this.hideProgressDialog();
                    try {
                        if (new JSONObject(s2).optString("code").equals("200")) {
                            String strOptString = new JSONObject(s2).optJSONObject("data").optString("b_img");
                            if (!TextUtils.isEmpty(strOptString)) {
                                EbookUploadImageActivity.this.setResult(-1, new Intent().putExtra("imageUrl", strOptString));
                                EbookUploadImageActivity.this.finish();
                            }
                        } else {
                            NewToast.showShort(EbookUploadImageActivity.this.getApplicationContext(), new JSONObject(s2).optString("message"), 0).show();
                            AndroidImagePicker.getInstance().clearImageSets();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Uri data = getIntent().getData();
        if (data == null) {
            finish();
        } else if (!"ebook".equals(data.getScheme())) {
            finish();
        } else {
            AndroidImagePicker.getInstance().setSelectLimit(1);
            AndroidImagePicker.getInstance().pickSingle(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.y8
                @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                public final void onImagePickComplete(List list) {
                    this.f14209a.lambda$init$0(list);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setTitle("选择上传图片");
        setContentView(R.layout.act_ebook_upload_image);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
