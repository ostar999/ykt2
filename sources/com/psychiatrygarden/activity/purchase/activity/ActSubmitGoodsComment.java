package com.psychiatrygarden.activity.purchase.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.ImageDataBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.NinePicAdp;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.NiceRatingBar;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ActSubmitGoodsComment extends BaseActivity {
    public static final String EXTRA_DATA_COURSE_ID = "course_id";
    public static final String EXTRA_DATA_ORDER_NO = "order_no";
    public static final String EXTRA_DATA_TYPE = "CourseType";
    private String courseId;
    private EditText edit_content;
    private int grade = 5;
    private boolean isNewVersion = false;
    private NinePicAdp mPicAdp;
    private String orderNo;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putCommPublish$4(String str, String str2) {
        try {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optString("code").equals("200")) {
                    AlertToast(jSONObject.optString("message"));
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    goActivity(GanXieActivity.class);
                    finish();
                } else {
                    AlertToast(jSONObject.optString("message"));
                }
            } catch (Exception e2) {
                Log.d("ActSubmitError", "putCommPublish: " + e2.getMessage());
            }
        } finally {
            hideProgressDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        Editable text = this.edit_content.getText();
        if (TextUtils.isEmpty(text) || text.length() < 5) {
            AlertToast("请输入不少于5个字的评价");
            return;
        }
        if (!this.isNewVersion) {
            putCommPublish();
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this));
        ajaxParams.put("star_level", String.valueOf(this.grade));
        ajaxParams.put("content", this.edit_content.getText().toString().trim());
        ajaxParams.put(EXTRA_DATA_ORDER_NO, this.orderNo);
        List<String> data = this.mPicAdp.getData();
        JSONArray jSONArray = new JSONArray();
        for (String str : data) {
            if (!TextUtils.isEmpty(str)) {
                jSONArray.put(str);
            }
        }
        ajaxParams.put("img", jSONArray.toString());
        LogUtils.d("params", ajaxParams.toString());
        YJYHttpUtils.post(this, NetworkRequestsURL.putCourseComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                ActSubmitGoodsComment.this.AlertToast(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        EventBus.getDefault().post(EventBusConstant.COURSE_ORDER_COMMENT);
                        ActSubmitGoodsComment.this.AlertToast("评价成功");
                        ActSubmitGoodsComment.this.finish();
                    } else {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            ActSubmitGoodsComment.this.AlertToast(strOptString);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1(float f2) {
        this.grade = (int) f2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChoosePic$2() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChoosePic$3(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = ((ImageItem) list.get(i2)).path;
            String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            LogUtils.d("BitmapFactoryType", "图片类型1：" + mimeTypeFromFile);
            if (mimeTypeFromFile.equalsIgnoreCase("IMAGE/WEBP")) {
                AlertToast("不支持此文件格式，请选择其它图片上传！");
                return;
            } else {
                if (ImageFactory.getImageSize(str) > 10.0f) {
                    NewToast.showShort(this.mContext, "请选择小于10M的图片上传", 0).show();
                    return;
                }
                uploadImage(str);
            }
        }
    }

    private void putCommPublish() {
        HashMap map = new HashMap();
        map.put("obj_id", this.courseId);
        map.put("grade", String.valueOf(this.grade));
        map.put(EXTRA_DATA_ORDER_NO, this.orderNo);
        map.put("content", this.edit_content.getText().toString().trim());
        List<String> data = this.mPicAdp.getData();
        JSONArray jSONArray = new JSONArray();
        for (String str : data) {
            if (!TextUtils.isEmpty(str)) {
                jSONArray.put(str);
            }
        }
        map.put("img", jSONArray.toString());
        map.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        map.put("comment_type", "1");
        YJYHttpUtils.postgoodsmd5(this.mContext, NetworkRequestsURL.mPutComment, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.purchase.activity.e
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str2) {
                this.f13585c.lambda$putCommPublish$4((String) obj, str2);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment.4
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError arg0, String arg1) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showChoosePic() {
        if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
            new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.purchase.activity.c
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13574a.lambda$showChoosePic$2();
                }
            })).show();
            return;
        }
        int size = this.mPicAdp.getData().size() - 1;
        int i2 = size > 0 ? 3 - size : 3;
        if (i2 <= 0) {
            AlertToast("最多只能上传3张图片");
        } else {
            AndroidImagePicker.getInstance().setSelectLimit(i2);
            AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.purchase.activity.d
                @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                public final void onImagePickComplete(List list) {
                    this.f13581a.lambda$showChoosePic$3(list);
                }
            });
        }
    }

    private void uploadImage(final String imgUrl) {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("image", new File(imgUrl));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.getforumuploadImageApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(ActSubmitGoodsComment.this.mContext, "上传失败！", 0).show();
                ActSubmitGoodsComment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(ActSubmitGoodsComment.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                    } else {
                        if (ActSubmitGoodsComment.this.isFinishing() || Looper.myLooper() != Looper.getMainLooper()) {
                            return;
                        }
                        long jCurrentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("url");
                        ImageDataBean imageDataBean = new ImageDataBean();
                        imageDataBean.setId(jCurrentThreadTimeMillis);
                        imageDataBean.setB_img(strOptString);
                        imageDataBean.setS_img(strOptString);
                        if (ActSubmitGoodsComment.this.mPicAdp.getData().size() == 0) {
                            ActSubmitGoodsComment.this.mPicAdp.addData((NinePicAdp) "");
                        }
                        if (ActSubmitGoodsComment.this.mPicAdp.getData().size() < 4) {
                            ActSubmitGoodsComment.this.mPicAdp.addData(0, (int) strOptString);
                            if (ActSubmitGoodsComment.this.mPicAdp.getData().size() > 3) {
                                Log.e("last_index", "index====>" + ActSubmitGoodsComment.this.mPicAdp.getData().lastIndexOf(""));
                                ActSubmitGoodsComment.this.mPicAdp.removeAt(ActSubmitGoodsComment.this.mPicAdp.getData().size() + (-1));
                            }
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                ActSubmitGoodsComment.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean z2 = false;
        if (requestCode == 1 && grantResults.length == 3 && grantResults[0] == 0 && grantResults[1] == 0 && grantResults[2] == 0) {
            z2 = true;
        }
        if (z2) {
            showChoosePic();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_submit_goods_comment);
        setTitle("评价晒单");
        NiceRatingBar niceRatingBar = (NiceRatingBar) findViewById(R.id.rating_bar);
        this.edit_content = (EditText) findViewById(R.id.et_content);
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13563c.lambda$setContentView$0(view);
            }
        });
        niceRatingBar.setOnRatingChangedListener(new NiceRatingBar.OnRatingChangedListener() { // from class: com.psychiatrygarden.activity.purchase.activity.b
            @Override // com.psychiatrygarden.widget.NiceRatingBar.OnRatingChangedListener
            public final void onRatingChanged(float f2) {
                this.f13568a.lambda$setContentView$1(f2);
            }
        });
        Intent intent = getIntent();
        this.courseId = intent.getStringExtra("course_id");
        this.orderNo = intent.getStringExtra(EXTRA_DATA_ORDER_NO);
        this.isNewVersion = intent.getBooleanExtra(EXTRA_DATA_TYPE, false);
        if (TextUtils.isEmpty(this.courseId)) {
            AlertToast("课程id不能为空");
            finish();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvImg);
        NinePicAdp ninePicAdp = new NinePicAdp(this, false);
        this.mPicAdp = ninePicAdp;
        recyclerView.setAdapter(ninePicAdp);
        this.mPicAdp.setOnPicItemClickListener(new NinePicAdp.OnPicItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment.2
            @Override // com.psychiatrygarden.forum.NinePicAdp.OnPicItemClickListener
            public void addPicClick(int pos, String url) {
                ActSubmitGoodsComment.this.showChoosePic();
            }

            @Override // com.psychiatrygarden.forum.NinePicAdp.OnPicItemClickListener
            public void delPicClick(int pos, String url) {
                ActSubmitGoodsComment.this.mPicAdp.removeAt(pos);
                if (ActSubmitGoodsComment.this.mPicAdp.getData().contains("")) {
                    return;
                }
                ActSubmitGoodsComment.this.mPicAdp.addData((NinePicAdp) "");
            }
        });
        this.mPicAdp.setSpanCount(3);
        this.mPicAdp.addData((NinePicAdp) "");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
