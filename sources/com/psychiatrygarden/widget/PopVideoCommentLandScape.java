package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.DrawerPopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshVideoCommentEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.VideoCommentLayout;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopVideoCommentLandScape extends DrawerPopupView implements View.OnClickListener {
    private final DiscussStatus commentEnum;
    private final String courseId;
    private View lineselect2;
    private VideoCommentLayout lyComment;
    private XPopupCallback mCallback;
    private final String moduleType;
    private final String objId;
    private TextView tvReMen;
    private TextView tvTitle;
    private TextView tvZuiXin;

    public PopVideoCommentLandScape(@NonNull Context context, String objId, String courseId, String moduleType, DiscussStatus commentEnum, XPopupCallback callback) {
        super(context);
        this.mCallback = callback;
        this.commentEnum = commentEnum;
        this.courseId = courseId;
        this.moduleType = moduleType;
        this.objId = objId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(int i2) {
        this.tvTitle.setText(String.format("%d条评论", Integer.valueOf(i2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(bundle);
            return;
        }
        Intent intent = new Intent(getContext(), (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        ((BaseActivity) getContext()).startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(DialogInterface dialogInterface) {
        this.mCallback.onShow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(DialogInterface dialogInterface) {
        this.mCallback.onDismiss(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$5(View view) {
        DialogInput dialogInput = new DialogInput(true, getContext(), new onDialogClickListener() { // from class: com.psychiatrygarden.widget.fd
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f16488a.lambda$onCreate$2(str, str2, str3);
            }
        }, "", "", false);
        dialogInput.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.gd
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16517c.lambda$onCreate$3(dialogInterface);
            }
        });
        dialogInput.setFromVideo(true, true);
        dialogInput.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.widget.hd
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f16551c.lambda$onCreate$4(dialogInterface);
            }
        });
        dialogInput.show();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_layout_video_comment_landscape;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.tvReMen) {
            this.lyComment.doSectionClick(0);
        } else if (v2.getId() == R.id.tvZuiXin) {
            this.lyComment.doSectionClick(1);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.cd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16373c.lambda$onCreate$0(view);
            }
        });
        this.lineselect2 = findViewById(R.id.lineselect2);
        this.tvReMen = (TextView) findViewById(R.id.tvReMen);
        this.tvZuiXin = (TextView) findViewById(R.id.tvZuiXin);
        this.tvReMen.setOnClickListener(this);
        this.tvZuiXin.setOnClickListener(this);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        VideoCommentLayout videoCommentLayout = (VideoCommentLayout) findViewById(R.id.ll_comment);
        this.lyComment = videoCommentLayout;
        videoCommentLayout.setAnchorView(getPopupContentView());
        this.lyComment.setCourseId(this.courseId);
        this.lyComment.initParams(this.objId, this.moduleType, this.commentEnum, false);
        this.lyComment.setLandScape(true);
        this.lyComment.initView();
        this.lyComment.setOnGetCommentListListener(new VideoCommentLayout.OnGetCommentListListener() { // from class: com.psychiatrygarden.widget.dd
            @Override // com.psychiatrygarden.widget.VideoCommentLayout.OnGetCommentListListener
            public final void onFinish(int i2) {
                this.f16407a.lambda$onCreate$1(i2);
            }
        });
        this.lyComment.setHotNewChangeListener(new VideoCommentLayout.HotNewChangeListener() { // from class: com.psychiatrygarden.widget.PopVideoCommentLandScape.2
            @Override // com.psychiatrygarden.widget.VideoCommentLayout.HotNewChangeListener
            public void onChange(boolean latestTrue, boolean hotTrue) {
                if (hotTrue) {
                    PopVideoCommentLandScape.this.tvReMen.setTextColor(-1);
                    PopVideoCommentLandScape.this.tvZuiXin.setTextColor(PopVideoCommentLandScape.this.getContext().getColor(R.color.color_909499));
                    PopVideoCommentLandScape.this.tvReMen.setBackground(PopVideoCommentLandScape.this.getContext().getDrawable(R.drawable.shape_select_tag_bg));
                    PopVideoCommentLandScape.this.tvZuiXin.setBackground(new ColorDrawable(0));
                    return;
                }
                PopVideoCommentLandScape.this.tvZuiXin.setTextColor(-1);
                PopVideoCommentLandScape.this.tvReMen.setTextColor(PopVideoCommentLandScape.this.getContext().getColor(R.color.color_909499));
                PopVideoCommentLandScape.this.tvZuiXin.setBackground(PopVideoCommentLandScape.this.getContext().getDrawable(R.drawable.shape_select_tag_bg));
                PopVideoCommentLandScape.this.tvReMen.setBackground(new ColorDrawable(0));
            }

            @Override // com.psychiatrygarden.widget.VideoCommentLayout.HotNewChangeListener
            public void showHotNew(boolean show) {
                PopVideoCommentLandScape.this.lineselect2.setVisibility(0);
            }
        });
        findViewById(R.id.ll_add_note).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ed
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16440c.lambda$onCreate$5(view);
            }
        });
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", String.valueOf(b3.getInt("module_type", 8)));
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("video_type", b3.getString("video_type", "1"));
        ajaxParams.put("obj_id", this.objId);
        ajaxParams.put("course_id", this.courseId);
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (string != null) {
            if (string.contains("http")) {
                ajaxParams.put("b_img", string);
                ajaxParams.put("s_img", string2);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopVideoCommentLandScape.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        ProjectApp.comment_content = "";
                        ProjectApp.comment_b_img = "";
                        ProjectApp.comment_s_img = "";
                        ProjectApp.commentvideoPath = "";
                        ProjectApp.commentvideoImage = "";
                        ProjectApp.commentvideoId = "";
                        ProjectApp.commentvideoImagePath = "";
                        SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", PopVideoCommentLandScape.this.getContext());
                        NewToast.showShort(ProjectApp.instance(), "评论成功");
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        EventBus.getDefault().post("autoData");
                        EventBus.getDefault().post(new RefreshVideoCommentEvent());
                        PopVideoCommentLandScape.this.lyComment.refresh();
                    } else {
                        ToastUtil.shortToast(PopVideoCommentLandScape.this.getContext(), jSONObject.optString("message", ""));
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
