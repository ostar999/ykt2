package com.psychiatrygarden.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.UploadShowPicAdapter;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.PushCommentNoteUploadPicItem;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.http.CourseDataRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class DialogVideoNoteInputLandScape extends AlertDialog implements View.OnClickListener, QuestionDataCallBack<String>, DialogInterface.OnDismissListener {
    public int FullSCREEN_Y_N;
    private String b_img;
    private onDialogNoteListener clickNoteListener;
    private String content;
    private Context context;
    private String courseId;
    private EditText et_content;
    private DialogInterface.OnDismissListener mDismissListener;
    private UploadShowPicAdapter mPicAdapter;
    private String module_type;
    private String objId;
    public int postType;
    private TextView pushBtm;
    private QuestionNoteBean questionNoteBean;
    private String s_img;
    private String screenShotFilePath;
    private String screenShotFileUrl_b;
    private String screenShotFileUrl_s;
    private ImageView screenview;
    private String title;
    private String videoId;
    public String videoImage;
    public String videoPath;

    public DialogVideoNoteInputLandScape(Context context, String courseId, String videoId, onDialogNoteListener clickNoteListener) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.context = context;
        this.clickNoteListener = clickNoteListener;
        this.videoId = videoId;
        this.objId = videoId;
        this.courseId = courseId;
        this.content = ProjectApp.noteContent;
        this.b_img = ProjectApp.noteBigImage;
        this.s_img = ProjectApp.noteSmellImage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5() {
        ActivityCompat.requestPermissions((Activity) this.context, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        float imageSize = ImageFactory.getImageSize(str);
        LogUtils.d("imgSize", String.valueOf(imageSize));
        if (imageSize > 20.0f) {
            NewToast.showShort(this.context, "请选择小于10M的图片上传", 0).show();
            return;
        }
        this.postType = 1;
        PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem = new PushCommentNoteUploadPicItem();
        pushCommentNoteUploadPicItem.setType(1);
        pushCommentNoteUploadPicItem.setStatus(1);
        pushCommentNoteUploadPicItem.setPicFilePath(str);
        if (this.mPicAdapter.getData().size() <= 1) {
            this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem);
        }
        getImageData(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.context).setSingleSrcView(null, this.mPicAdapter.getItem(i2).getbImg()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        if (this.FullSCREEN_Y_N == 1) {
            this.FullSCREEN_Y_N = 0;
        } else {
            this.FullSCREEN_Y_N = 1;
        }
        setScreen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3() {
        EditText editText = this.et_content;
        if (editText != null) {
            editText.setFocusable(true);
            this.et_content.setFocusableInTouchMode(true);
            this.et_content.requestFocus();
            ((InputMethodManager) this.context.getSystemService("input_method")).showSoftInput(this.et_content, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ boolean lambda$onCreate$4(android.view.View r5, int[] r6, android.view.View r7, android.view.MotionEvent r8) {
        /*
            r4 = this;
            int r7 = r8.getAction()
            r0 = 0
            r1 = 1
            if (r7 != r1) goto L3c
            r5.getLocationOnScreen(r6)
            float r7 = r8.getRawX()
            float r8 = r8.getRawY()
            r2 = r6[r0]
            float r3 = (float) r2
            int r3 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r3 < 0) goto L36
            int r3 = r5.getWidth()
            int r2 = r2 + r3
            float r2 = (float) r2
            int r7 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r7 > 0) goto L36
            r6 = r6[r1]
            float r7 = (float) r6
            int r7 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r7 < 0) goto L36
            int r5 = r5.getHeight()
            int r6 = r6 + r5
            float r5 = (float) r6
            int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r5 > 0) goto L36
            goto L37
        L36:
            r1 = r0
        L37:
            if (r1 != 0) goto L3c
            r4.dismiss()
        L3c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DialogVideoNoteInputLandScape.lambda$onCreate$4(android.view.View, int[], android.view.View, android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onDismiss$7(String str, Uri uri) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("path = ");
        sb.append(str);
        if (uri != null) {
            str2 = " uri = " + uri.getPath();
        } else {
            str2 = "";
        }
        sb.append(str2);
        LogUtils.d("onScanCompleted", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreen$0() {
        if (getWindow() != null) {
            getWindow().setLayout(-1, -1);
        }
    }

    private void pushNote(String toString, String b_img, String s_img) throws JSONException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_id", this.objId);
        ajaxParams.put("module_type", this.module_type);
        QuestionNoteBean questionNoteBean = this.questionNoteBean;
        if (questionNoteBean != null && !TextUtils.isEmpty(questionNoteBean.getId())) {
            ajaxParams.put("id", this.questionNoteBean.getId());
        }
        ajaxParams.put("content", toString);
        try {
            JSONArray jSONArray = new JSONArray();
            List<PushCommentNoteUploadPicItem> data = this.mPicAdapter.getData();
            if (data.size() > 0) {
                for (PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem : data) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("b_img", pushCommentNoteUploadPicItem.getbImg());
                    jSONObject.put("s_img", pushCommentNoteUploadPicItem.getsImg());
                    jSONArray.put(jSONObject);
                }
            }
            ajaxParams.put("img", jSONArray.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        CourseDataRequest.getIntance(getContext()).addNote(ajaxParams, this);
    }

    private void uploadScreenShotFile() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(this.screenShotFilePath));
            YJYHttpUtils.postImage(this.context, NetworkRequestsURL.noteUploads, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogVideoNoteInputLandScape.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        if (jSONObject.optString("code").equals("200")) {
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                            if (jSONObjectOptJSONObject == null) {
                                return;
                            }
                            DialogVideoNoteInputLandScape.this.screenShotFileUrl_b = jSONObjectOptJSONObject.optString("b_img");
                            DialogVideoNoteInputLandScape.this.screenShotFileUrl_s = jSONObjectOptJSONObject.optString("s_img");
                            DialogVideoNoteInputLandScape.this.pushBtm.setSelected(true);
                            PushCommentNoteUploadPicItem item = DialogVideoNoteInputLandScape.this.mPicAdapter.getItem(0);
                            item.setStatus(2);
                            item.setsImg(DialogVideoNoteInputLandScape.this.screenShotFileUrl_s);
                            item.setbImg(DialogVideoNoteInputLandScape.this.screenShotFileUrl_b);
                            DialogVideoNoteInputLandScape.this.mPicAdapter.notifyItemChanged(0);
                        } else {
                            NewToast.showShort(DialogVideoNoteInputLandScape.this.context, new JSONObject(s2).optString("message"), 0).show();
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public void CommOkData() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        dismiss();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        DialogInterface.OnDismissListener onDismissListener = this.mDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }
        super.dismiss();
    }

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.context, NetworkRequestsURL.noteUploads, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogVideoNoteInputLandScape.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(DialogVideoNoteInputLandScape.this.context, "上传失败！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    int size = 0;
                    if (!jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(DialogVideoNoteInputLandScape.this.context, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (jSONObjectOptJSONObject == null) {
                        return;
                    }
                    DialogVideoNoteInputLandScape.this.b_img = jSONObjectOptJSONObject.optString("b_img");
                    DialogVideoNoteInputLandScape.this.s_img = jSONObjectOptJSONObject.optString("s_img");
                    List<PushCommentNoteUploadPicItem> data = DialogVideoNoteInputLandScape.this.mPicAdapter.getData();
                    UploadShowPicAdapter uploadShowPicAdapter = DialogVideoNoteInputLandScape.this.mPicAdapter;
                    if (!data.isEmpty()) {
                        size = data.size() - 1;
                    }
                    PushCommentNoteUploadPicItem item = uploadShowPicAdapter.getItem(size);
                    item.setbImg(DialogVideoNoteInputLandScape.this.s_img);
                    item.setbImg(DialogVideoNoteInputLandScape.this.b_img);
                    item.setStatus(2);
                    DialogVideoNoteInputLandScape.this.mPicAdapter.notifyDataSetChanged();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws JSONException {
        switch (v2.getId()) {
            case R.id.dialog_input_btn_comment_ok /* 2131362912 */:
            case R.id.pushBtm /* 2131366158 */:
                submitComment();
                break;
            case R.id.iv_dialog_cancel /* 2131364057 */:
                findViewById(R.id.screenview).performClick();
                break;
            case R.id.upimg /* 2131368941 */:
                if (!CommonUtil.hasRequiredPermissions(this.context)) {
                    new XPopup.Builder(this.context).asCustom(new RequestMediaPermissionPop(this.context, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.g8
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f16507a.lambda$onClick$5();
                        }
                    })).show();
                    break;
                } else {
                    AndroidImagePicker.getInstance().setSelectLimit(1);
                    AndroidImagePicker.getInstance().pickMulti((Activity) this.context, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.widget.h8
                        @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                        public final void onImagePickComplete(List list) {
                            this.f16544a.lambda$onClick$6(list);
                        }
                    });
                    break;
                }
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        List<ImagesBean> img;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_video_note_input_landscape);
        CommonUtil.getScreenWidth(this.context);
        getWindow().setWindowAnimations(R.style.popupAnimation);
        this.mPicAdapter = new UploadShowPicAdapter();
        getWindow().setGravity(80);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        QuestionNoteBean questionNoteBean = this.questionNoteBean;
        if (questionNoteBean != null) {
            this.content = questionNoteBean.getContent();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvPic);
        Context context = this.context;
        new GridItemDecoration(context, 3, 0, context.getResources().getDimensionPixelSize(R.dimen.dp_1), this.context.getResources().getDimensionPixelSize(R.dimen.dp_10));
        recyclerView.setAdapter(this.mPicAdapter);
        this.mPicAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.i8
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16583c.lambda$onCreate$1(baseQuickAdapter, view, i2);
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_title);
        this.et_content = (EditText) findViewById(R.id.dialog_input_et_comment);
        this.pushBtm = (TextView) findViewById(R.id.pushBtm);
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.DialogVideoNoteInputLandScape.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                String string = s2.toString();
                TextView textView2 = DialogVideoNoteInputLandScape.this.pushBtm;
                boolean z2 = true;
                if (DialogVideoNoteInputLandScape.this.mPicAdapter.getData().size() <= 0 && ((string.length() < 1 || string.length() > 5000) && TextUtils.isEmpty(DialogVideoNoteInputLandScape.this.screenShotFileUrl_b))) {
                    z2 = false;
                }
                textView2.setSelected(z2);
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.screenview);
        this.screenview = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.j8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16612c.lambda$onCreate$2(view);
            }
        });
        setScreen();
        ((ImageView) findViewById(R.id.upimg)).setOnClickListener(this);
        if (TextUtils.isEmpty(this.screenShotFilePath)) {
            QuestionNoteBean questionNoteBean2 = this.questionNoteBean;
            if (questionNoteBean2 != null && (img = questionNoteBean2.getImg()) != null && img.size() > 0) {
                PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem = new PushCommentNoteUploadPicItem();
                pushCommentNoteUploadPicItem.setbImg(img.get(0).getB_img());
                pushCommentNoteUploadPicItem.setsImg(img.get(0).getS_img());
                pushCommentNoteUploadPicItem.setStatus(2);
                pushCommentNoteUploadPicItem.setType(2);
                this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem);
                if (img.size() > 1) {
                    PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem2 = new PushCommentNoteUploadPicItem();
                    pushCommentNoteUploadPicItem2.setbImg(img.get(1).getB_img());
                    pushCommentNoteUploadPicItem2.setsImg(img.get(1).getS_img());
                    pushCommentNoteUploadPicItem2.setStatus(2);
                    pushCommentNoteUploadPicItem2.setType(1);
                    this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem2);
                }
            }
        } else {
            PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem3 = new PushCommentNoteUploadPicItem();
            pushCommentNoteUploadPicItem3.setbImg(this.screenShotFileUrl_b);
            pushCommentNoteUploadPicItem3.setsImg(this.screenShotFileUrl_s);
            pushCommentNoteUploadPicItem3.setPicFilePath(this.screenShotFilePath);
            pushCommentNoteUploadPicItem3.setStatus(1);
            pushCommentNoteUploadPicItem3.setType(2);
            this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem3);
            uploadScreenShotFile();
        }
        if (!TextUtils.isEmpty(this.title)) {
            textView.setText(this.title);
        }
        if (!TextUtils.isEmpty(this.content)) {
            this.et_content.setText(this.content);
            this.et_content.setSelection(this.content.length());
        }
        this.pushBtm.setOnClickListener(this);
        findViewById(R.id.dialog_input_btn_comment_ok).setOnClickListener(this);
        findViewById(R.id.iv_dialog_cancel).setOnClickListener(this);
        this.et_content.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.k8
            @Override // java.lang.Runnable
            public final void run() {
                this.f16642c.lambda$onCreate$3();
            }
        }, 200L);
        this.et_content.performClick();
        setOnDismissListener(this);
        final View viewFindViewById = findViewById(R.id.commentList_bottom_layout);
        final int[] iArr = new int[2];
        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.l8
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f16672c.lambda$onCreate$4(viewFindViewById, iArr, view, motionEvent);
            }
        });
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        if (this.questionNoteBean == null) {
            ProjectApp.noteContent = this.et_content.getText().toString();
            ProjectApp.noteBigImage = this.b_img;
            ProjectApp.noteSmellImage = this.s_img;
        }
        if (TextUtils.isEmpty(this.screenShotFilePath)) {
            return;
        }
        try {
            File file = new File(this.screenShotFilePath);
            if (file.exists() && file.length() > 0) {
                file.delete();
                if (Build.VERSION.SDK_INT < 29) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.parse("file://" + this.screenShotFilePath));
                    this.context.sendBroadcast(intent);
                } else {
                    MediaScannerConnection.scanFile(this.context, new String[]{file.getParent()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.psychiatrygarden.widget.n8
                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                        public final void onScanCompleted(String str, Uri uri) {
                            DialogVideoNoteInputLandScape.lambda$onDismiss$7(str, uri);
                        }
                    });
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String isDismiss) {
        if (isDismiss.equals(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS)) {
            CommOkData();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        try {
            ((BaseActivity) this.context).hideProgressDialog();
            if (TextUtils.isEmpty(this.screenShotFilePath)) {
                return;
            }
            File file = new File(this.screenShotFilePath);
            if (!file.exists() || file.length() <= 0) {
                return;
            }
            file.delete();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        try {
            ((BaseActivity) this.context).showProgressDialog();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public DialogVideoNoteInputLandScape setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public void setDismissListener(DialogInterface.OnDismissListener l2) {
        this.mDismissListener = l2;
    }

    public void setModuleType(String module_type) {
        this.module_type = module_type;
    }

    public void setScreen() {
        if (this.FullSCREEN_Y_N == 0) {
            this.screenview.setVisibility(0);
            this.pushBtm.setVisibility(0);
            findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(8);
            findViewById(R.id.iv_dialog_cancel).setVisibility(8);
            getWindow().setLayout(-1, ScreenUtil.getPxByDp(this.context, 115));
            return;
        }
        this.screenview.setVisibility(8);
        this.pushBtm.setVisibility(8);
        findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(0);
        findViewById(R.id.iv_dialog_cancel).setVisibility(0);
        getWindow().setLayout(-1, XPopupUtils.getScreenHeight(this.context));
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.m8
            @Override // java.lang.Runnable
            public final void run() {
                this.f16702c.lambda$setScreen$0();
            }
        }, 200L);
    }

    public void setScreenShotFilePath(String filePath) {
        this.screenShotFilePath = filePath;
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    public void submitComment() throws JSONException {
        if (this.postType != 1) {
            if (this.et_content.getText().toString().trim().length() > 5000) {
                NewToast.showShort(this.context, "笔记长度不能超过5000字", 0).show();
                return;
            } else {
                pushNote(this.et_content.getText().toString(), this.b_img, this.s_img);
                return;
            }
        }
        if (TextUtils.isEmpty(this.b_img) || TextUtils.isEmpty(this.s_img)) {
            NewToast.showShort(this.context, "图片未上传成功，请重新选择上传图片！", 0).show();
        } else if (this.et_content.getText().toString().trim().length() > 5000) {
            NewToast.showShort(this.context, "笔记长度不能超过5000字", 0).show();
        } else {
            pushNote(this.et_content.getText().toString(), this.b_img, this.s_img);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requestUrl) {
        try {
            ((BaseActivity) this.context).hideProgressDialog();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (requestUrl.equals(NetworkRequestsURL.mCourseAddNoteURL) || requestUrl.equals(NetworkRequestsURL.courseVideoAddNote)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    ToastUtil.shortToast(this.context, jSONObject.optString("message"));
                    return;
                }
                QuestionNoteBean questionNoteBean = this.questionNoteBean;
                if (questionNoteBean != null) {
                    questionNoteBean.setContent(this.et_content.getText().toString());
                    this.questionNoteBean.setCtime((System.currentTimeMillis() / 1000) + "");
                    List<PushCommentNoteUploadPicItem> data = this.mPicAdapter.getData();
                    ArrayList arrayList = new ArrayList();
                    for (PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem : data) {
                        ImagesBean imagesBean = new ImagesBean();
                        imagesBean.setS_img(pushCommentNoteUploadPicItem.getsImg());
                        imagesBean.setB_img(pushCommentNoteUploadPicItem.getbImg());
                        arrayList.add(imagesBean);
                    }
                    this.questionNoteBean.setImg(arrayList);
                    onDialogNoteListener ondialognotelistener = this.clickNoteListener;
                    if (ondialognotelistener != null) {
                        ondialognotelistener.onclickStringBack(this.questionNoteBean);
                    }
                    ToastUtil.shortToast(this.context, "编辑笔记成功");
                    EventBus.getDefault().post(EventBusConstant.ADD_NOTE);
                } else {
                    QuestionNoteBean questionNoteBean2 = new QuestionNoteBean();
                    this.questionNoteBean = questionNoteBean2;
                    questionNoteBean2.setContent(this.et_content.getText().toString());
                    this.questionNoteBean.setCtime((System.currentTimeMillis() / 1000) + "");
                    if (!TextUtils.isEmpty(this.s_img)) {
                        ArrayList arrayList2 = new ArrayList();
                        ImagesBean imagesBean2 = new ImagesBean();
                        imagesBean2.setS_img(this.s_img);
                        imagesBean2.setB_img(this.b_img);
                        arrayList2.add(imagesBean2);
                        this.questionNoteBean.setImg(arrayList2);
                    }
                    onDialogNoteListener ondialognotelistener2 = this.clickNoteListener;
                    if (ondialognotelistener2 != null) {
                        ondialognotelistener2.onclickStringBack(this.questionNoteBean);
                    }
                    ToastUtil.shortToast(this.context, "添加笔记成功");
                    int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, getContext(), 0);
                    int intConfig2 = SharePreferencesUtils.readIntConfig(CommonParameter.COMMENT_COUNT, getContext(), 0);
                    int i2 = intConfig + 1;
                    SharePreferencesUtils.writeIntConfig(CommonParameter.NOTE_COUNT, i2, getContext());
                    EventBus.getDefault().post(new UpdateVideoCommentNote(i2, intConfig2));
                    if (!TextUtils.isEmpty(this.screenShotFilePath)) {
                        File file = new File(this.screenShotFilePath);
                        if (file.exists() && file.length() > 0) {
                            file.delete();
                        }
                    }
                }
                this.b_img = "";
                this.s_img = "";
                this.et_content.setText("");
                dismiss();
            } catch (Exception e3) {
                this.b_img = "";
                this.s_img = "";
                this.et_content.setText("");
                dismiss();
                e3.printStackTrace();
            }
        }
    }

    public DialogVideoNoteInputLandScape(Context context, String module_type, String screenShotFilePath, String title, onDialogNoteListener clickNoteListener, String objId) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.objId = "";
        this.courseId = "";
        this.context = context;
        this.clickNoteListener = clickNoteListener;
        this.screenShotFilePath = screenShotFilePath;
        this.module_type = module_type;
        this.videoId = objId;
        this.objId = String.valueOf(objId);
        this.title = title;
        this.content = ProjectApp.noteContent;
        this.b_img = ProjectApp.noteBigImage;
        this.s_img = ProjectApp.noteSmellImage;
    }

    public DialogVideoNoteInputLandScape(Context context, String courseId, String videoId, QuestionNoteBean questionNoteBean, onDialogNoteListener clickNoteListener) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.context = context;
        this.clickNoteListener = clickNoteListener;
        this.courseId = courseId;
        this.questionNoteBean = questionNoteBean;
        this.videoId = videoId;
        this.objId = videoId;
    }
}
