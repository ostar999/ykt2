package com.psychiatrygarden.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.UploadImageAdapter;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ChatEditInfoActivity extends BaseActivity implements QuestionDataCallBack<String>, OnItemClickListener, OnItemChildClickListener {
    private EditText edit_content;
    private RecyclerView rv_img;
    private TextView tv_content;
    private String type;
    private UploadImageAdapter uploadImageAdapter = new UploadImageAdapter(new ArrayList());
    private List<String> listImg = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemClick$3() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setListenerForWidget$0(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (motionEvent.getAction() == 2) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (motionEvent.getAction() == 1) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        Intent intent = new Intent();
        intent.putExtra("edit_content", getIntent().getExtras().getString("content", ""));
        intent.putExtra("announcement_image", getIntent().getExtras().getString("announcement_image", ""));
        intent.putExtra("type", getIntent().getExtras().getString("type"));
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (!this.type.equals(CommonParameter.tv_group_chat_notice)) {
            ChatRequest.getIntance(this).updateCommunity(getIntent().getExtras().getString("id", ""), getIntent().getExtras().getString("community_id", ""), this.edit_content.getText().toString(), this);
            return;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= this.listImg.size()) {
                break;
            }
            if (TextUtils.isEmpty(this.listImg.get(i2))) {
                this.listImg.remove(i2);
                break;
            }
            i2++;
        }
        ChatRequest.getIntance(this).chatAnnouncement(getIntent().getExtras().getString("id", ""), getIntent().getExtras().getString("community_id", ""), this.edit_content.getText().toString(), new Gson().toJson(this.listImg), this);
    }

    public void getImageData(final String imgUrl) {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("image", new File(imgUrl));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.getforumuploadImageApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chat.ChatEditInfoActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(ChatEditInfoActivity.this.mContext, "上传失败！", 0).show();
                ChatEditInfoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(ChatEditInfoActivity.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                    } else {
                        if (ChatEditInfoActivity.this.isFinishing() || Looper.myLooper() != Looper.getMainLooper()) {
                            return;
                        }
                        ChatEditInfoActivity.this.listImg.add(ChatEditInfoActivity.this.listImg.size() - 1, new JSONObject(s2).optJSONObject("data").optString("url"));
                        if (ChatEditInfoActivity.this.listImg.size() > 3) {
                            ChatEditInfoActivity.this.listImg.remove(ChatEditInfoActivity.this.listImg.size() - 1);
                        }
                        ChatEditInfoActivity.this.uploadImageAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                ChatEditInfoActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.type = getIntent().getExtras().getString("type");
        this.edit_content = (EditText) findViewById(R.id.edit_content);
        this.tv_content = (TextView) findViewById(R.id.tv_content);
        this.rv_img = (RecyclerView) findViewById(R.id.rv_img);
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("完成");
        if (this.type.equals(CommonParameter.tv_group_chat_notice)) {
            this.listImg = (List) new Gson().fromJson(getIntent().getExtras().getString("announcement_image"), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.activity.chat.ChatEditInfoActivity.1
            }.getType());
            setTitle("群公告");
            this.rv_img.setVisibility(0);
            this.rv_img.setLayoutManager(new GridLayoutManager(this, 3));
            this.uploadImageAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chat.h
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f11161c.onItemClick(baseQuickAdapter, view, i2);
                }
            });
            this.uploadImageAdapter.setUserIdentity(getIntent().getExtras().getString("user_identity"));
            this.uploadImageAdapter.addChildClickViewIds(R.id.iv_group_delete);
            this.uploadImageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.chat.i
                @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
                public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f11163c.onItemChildClick(baseQuickAdapter, view, i2);
                }
            });
            this.rv_img.setAdapter(this.uploadImageAdapter);
            if (getIntent().getExtras().getString("user_identity").equals("0")) {
                this.mBtnActionbarRight.setVisibility(8);
                this.edit_content.setKeyListener(null);
                this.edit_content.setVisibility(8);
                this.tv_content.setVisibility(0);
            } else {
                this.edit_content.setVisibility(0);
                this.tv_content.setVisibility(8);
                if (this.listImg.size() < 3) {
                    this.listImg.add("");
                }
            }
            this.uploadImageAdapter.setNewInstance(this.listImg);
        } else {
            setTitle("群昵称");
            this.rv_img.setVisibility(8);
        }
        if (TextUtils.isEmpty(getIntent().getExtras().getString("content", ""))) {
            return;
        }
        this.edit_content.setText(getIntent().getExtras().getString("content", ""));
        this.tv_content.setText(getIntent().getExtras().getString("content", ""));
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, cn.webdemo.com.supporfragment.ISupportActivity
    public void onBackPressedSupport() {
        Intent intent = new Intent();
        intent.putExtra("edit_content", getIntent().getExtras().getString("content", ""));
        intent.putExtra("announcement_image", getIntent().getExtras().getString("announcement_image", ""));
        intent.putExtra("type", getIntent().getExtras().getString("type"));
        setResult(-1, intent);
        super.onBackPressedSupport();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideProgressDialog();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        this.listImg.remove(position);
        if (!TextUtils.isEmpty(this.listImg.get(r1.size() - 1))) {
            this.listImg.add("");
        }
        this.uploadImageAdapter.notifyDataSetChanged();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (TextUtils.isEmpty(this.listImg.get(position))) {
            if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
                new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.chat.d
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f11154a.lambda$onItemClick$3();
                    }
                })).show();
                return;
            } else {
                AndroidImagePicker.getInstance().setSelectLimit(1);
                AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.chat.ChatEditInfoActivity.2
                    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                    public void onImagePickComplete(List<ImageItem> items) {
                        if (items == null || items.size() <= 0) {
                            return;
                        }
                        String str = items.get(0).path;
                        String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(str, options);
                        LogUtils.d("BitmapFactoryType", "图片类型1：" + mimeTypeFromFile);
                        if (mimeTypeFromFile.toUpperCase().equals("IMAGE/WEBP")) {
                            ChatEditInfoActivity.this.AlertToast("不支持此文件格式，请选择其它图片上传！");
                        } else if (ImageFactory.getImageSize(str) > 10.0f) {
                            NewToast.showShort(ChatEditInfoActivity.this.mContext, "请选择小于10M的图片上传", 0).show();
                        } else {
                            ChatEditInfoActivity.this.getImageData(str);
                        }
                    }
                });
                return;
            }
        }
        int i2 = 0;
        while (true) {
            if (i2 >= this.listImg.size()) {
                break;
            }
            if (TextUtils.isEmpty(this.listImg.get(i2))) {
                this.listImg.remove(i2);
                break;
            }
            i2++;
        }
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(new ArrayList(this.listImg)).setSrcView(null, position).show();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        showProgressDialog();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_chat_edit_info);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.edit_content.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.chat.e
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return ChatEditInfoActivity.lambda$setListenerForWidget$0(view, motionEvent);
            }
        });
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11157c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11159c.lambda$setListenerForWidget$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        hideProgressDialog();
        try {
            JSONObject jSONObject = new JSONObject(s2);
            if (!jSONObject.optString("code").equals("200")) {
                ToastUtil.shortToast(this, jSONObject.optString("message"));
                return;
            }
            Intent intent = new Intent();
            if (requstUrl.equals(NetworkRequestsURL.chatAnnouncementApi)) {
                intent.putExtra("announcement_image", new Gson().toJson(this.listImg));
                EMMessage eMMessageCreateTxtSendMessage = EMMessage.createTxtSendMessage("群公告\n" + this.edit_content.getText().toString(), getIntent().getExtras().getString("community_id", ""));
                eMMessageCreateTxtSendMessage.setChatType(EMMessage.ChatType.GroupChat);
                EMClient.getInstance().chatManager().sendMessage(eMMessageCreateTxtSendMessage);
            } else {
                requstUrl.equals(NetworkRequestsURL.updateCommunityApi);
            }
            intent.putExtra("edit_content", this.edit_content.getText().toString());
            intent.putExtra("type", getIntent().getExtras().getString("type"));
            setResult(-1, intent);
            finish();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
