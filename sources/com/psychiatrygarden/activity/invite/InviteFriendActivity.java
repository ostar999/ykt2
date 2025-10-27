package com.psychiatrygarden.activity.invite;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.bean.InvitePageBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.QrCodeUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u000fH\u0016J\b\u0010\u0016\u001a\u00020\u000fH\u0016J\b\u0010\u0017\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/activity/invite/InviteFriendActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "ivQrCode", "Landroid/widget/ImageView;", "mLyView", "Landroid/widget/LinearLayout;", "rvTips", "Landroidx/recyclerview/widget/RecyclerView;", "tvCopy", "Landroid/widget/Button;", "tvInviteCode", "Landroid/widget/TextView;", "tvInviteInfo", "getInviteInfo", "", "init", "initStatusBar", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "setQrCode", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class InviteFriendActivity extends BaseActivity {
    private ImageView ivQrCode;
    private LinearLayout mLyView;
    private RecyclerView rvTips;
    private Button tvCopy;
    private TextView tvInviteCode;
    private TextView tvInviteInfo;

    private final void getInviteInfo() {
        YJYHttpUtils.get(this, NetworkRequestsURL.getInvitePageURL, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.invite.InviteFriendActivity.getInviteInfo.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable error, int errorNo, @Nullable String strMsg) {
                super.onFailure(error, errorNo, strMsg);
                if (error != null) {
                    error.printStackTrace();
                }
                if (strMsg != null) {
                    InviteFriendActivity.this.AlertToast(strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                InviteFriendActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String result) {
                super.onSuccess((AnonymousClass1) result);
                InviteFriendActivity.this.hideProgressDialog();
                if (result != null) {
                    InviteFriendActivity inviteFriendActivity = InviteFriendActivity.this;
                    try {
                        InvitePageBean invitePageBean = (InvitePageBean) new Gson().fromJson(result, InvitePageBean.class);
                        if (!invitePageBean.isSuccess()) {
                            inviteFriendActivity.AlertToast(invitePageBean.getMessage());
                            Unit unit = Unit.INSTANCE;
                            return;
                        }
                        String join_status = invitePageBean.getData().getJoin_status();
                        if (Intrinsics.areEqual(join_status, "0")) {
                            String download_h5 = invitePageBean.getData().getDownload_h5();
                            if (download_h5 != null) {
                                Intrinsics.checkNotNullExpressionValue(download_h5, "download_h5");
                                Intent intent = new Intent(inviteFriendActivity, (Class<?>) WebLongSaveActivity.class);
                                intent.putExtra("title", inviteFriendActivity.getString(R.string.app_name));
                                intent.putExtra("web_url", download_h5);
                                intent.setFlags(268435456);
                                ProjectApp.instance().startActivity(intent);
                                Unit unit2 = Unit.INSTANCE;
                                return;
                            }
                            return;
                        }
                        if (!Intrinsics.areEqual(join_status, "1")) {
                            inviteFriendActivity.AlertToast("状态码返回错误");
                            Unit unit3 = Unit.INSTANCE;
                            return;
                        }
                        TextView textView = inviteFriendActivity.tvInviteCode;
                        RecyclerView recyclerView = null;
                        if (textView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvInviteCode");
                            textView = null;
                        }
                        textView.setText(invitePageBean.getData().getInvite_code());
                        String message = invitePageBean.getData().getInvite_info().getMessage();
                        Matcher matcher = Pattern.compile("[0-9]\\d*").matcher(message);
                        SpannableString spannableString = new SpannableString(message);
                        String str = "#909090";
                        String str2 = "#DD594A";
                        if (SkinManager.getCurrentSkinType(inviteFriendActivity) == 1) {
                            str = "#575F79";
                            str2 = "#B2575C";
                        }
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(str)), 0, spannableString.length(), 18);
                        while (matcher.find()) {
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(str2)), matcher.start(), matcher.end(), 18);
                        }
                        TextView textView2 = inviteFriendActivity.tvInviteInfo;
                        if (textView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvInviteInfo");
                            textView2 = null;
                        }
                        textView2.setText(spannableString);
                        final List<String> desc = invitePageBean.getData().getDesc();
                        if (desc == null) {
                            desc = CollectionsKt__CollectionsKt.emptyList();
                        } else {
                            Intrinsics.checkNotNullExpressionValue(desc, "data.data.desc ?: emptyList()");
                        }
                        RecyclerView recyclerView2 = inviteFriendActivity.rvTips;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvTips");
                        } else {
                            recyclerView = recyclerView2;
                        }
                        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(desc) { // from class: com.psychiatrygarden.activity.invite.InviteFriendActivity$getInviteInfo$1$onSuccess$1$2
                            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
                            public void convert(@Nullable BaseViewHolder helper, @NotNull String item) {
                                Intrinsics.checkNotNullParameter(item, "item");
                                View view = helper != null ? helper.itemView : null;
                                Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
                                ((TextView) view).setText(item);
                            }
                        });
                        Unit unit4 = Unit.INSTANCE;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Unit unit5 = Unit.INSTANCE;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(InviteFriendActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) InviteListActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(InviteFriendActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextView textView = this$0.tvInviteCode;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvInviteCode");
            textView = null;
        }
        String string = textView.getText().toString();
        if (string.length() == 0) {
            this$0.AlertToast("没有可复制的邀请码");
            return;
        }
        Object systemService = this$0.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, string));
        this$0.AlertToast("复制成功");
    }

    private final void setQrCode() {
        ImageView imageView = this.ivQrCode;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivQrCode");
            imageView = null;
        }
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.invite.d
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                InviteFriendActivity.setQrCode$lambda$2(this.f12527c);
            }
        });
        final Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        final int iDp2px = SizeUtil.dp2px(this, 132);
        final String str = "https://sj.qq.com/appdetail/com.yikaobang.yixue";
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.invite.e
            @Override // java.lang.Runnable
            public final void run() {
                InviteFriendActivity.setQrCode$lambda$4(iDp2px, str, bitmapDecodeResource, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setQrCode$lambda$2(InviteFriendActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        StringBuilder sb = new StringBuilder();
        sb.append("w=");
        ImageView imageView = this$0.ivQrCode;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivQrCode");
            imageView = null;
        }
        sb.append(imageView.getWidth());
        sb.append(",h=");
        ImageView imageView3 = this$0.ivQrCode;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivQrCode");
        } else {
            imageView2 = imageView3;
        }
        sb.append(imageView2.getHeight());
        LogUtils.d("Size", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setQrCode$lambda$4(int i2, String yybUrl, Bitmap bitmap, final InviteFriendActivity this$0) {
        Intrinsics.checkNotNullParameter(yybUrl, "$yybUrl");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final Bitmap bitmapCreateQRcodeImage = QrCodeUtils.createQRcodeImage(i2, i2, yybUrl, bitmap);
        this$0.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.invite.c
            @Override // java.lang.Runnable
            public final void run() {
                InviteFriendActivity.setQrCode$lambda$4$lambda$3(this.f12525c, bitmapCreateQRcodeImage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setQrCode$lambda$4$lambda$3(InviteFriendActivity this$0, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ImageView imageView = this$0.ivQrCode;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivQrCode");
            imageView = null;
        }
        imageView.setImageBitmap(bitmap);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("邀请好友");
        this.mTvActionbarRight.setVisibility(0);
        this.mTvActionbarRight.setText("受邀人");
        this.mBtnActionbarRight.setVisibility(8);
        View viewFindViewById = findViewById(R.id.tv_invite_code);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_invite_code)");
        this.tvInviteCode = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tv_invite_info);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_invite_info)");
        this.tvInviteInfo = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tv_copy);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_copy)");
        this.tvCopy = (Button) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.img_qr_code);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.img_qr_code)");
        this.ivQrCode = (ImageView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.rvTips);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.rvTips)");
        this.rvTips = (RecyclerView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.ly_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.ly_view)");
        this.mLyView = (LinearLayout) viewFindViewById6;
        LinearLayout linearLayout = null;
        if (SkinManager.getCurrentSkinType(this) == 1) {
            LinearLayout linearLayout2 = this.mLyView;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mLyView");
            } else {
                linearLayout = linearLayout2;
            }
            linearLayout.setBackgroundResource(R.drawable.invite_friend_night_bg);
        } else {
            LinearLayout linearLayout3 = this.mLyView;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mLyView");
            } else {
                linearLayout = linearLayout3;
            }
            linearLayout.setBackgroundResource(R.mipmap.bg_invite_friend);
        }
        this.mTvActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.invite.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteFriendActivity.init$lambda$0(this.f12523c, view);
            }
        });
        getInviteInfo();
        setQrCode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 1) {
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_invite_friend);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        Button button = this.tvCopy;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCopy");
            button = null;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.invite.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteFriendActivity.setListenerForWidget$lambda$1(this.f12524c, view);
            }
        });
    }
}
