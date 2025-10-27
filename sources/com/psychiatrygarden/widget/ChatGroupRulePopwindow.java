package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.constants.EaseConstant;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.activity.chat.GroupChatHomeActivity;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class ChatGroupRulePopwindow extends CenterPopupView {
    private String community_id;
    private Context context;
    private String showUrl;
    private String ykb_community_id;

    public ChatGroupRulePopwindow(@NonNull Context context, String community_id, String ykb_community_id, String popup_img) {
        super(context);
        this.context = context;
        this.showUrl = popup_img;
        this.community_id = community_id;
        this.ykb_community_id = ykb_community_id;
    }

    private void chatGroupJoin() {
        ChatRequest.getIntance(this.context).chatJoin(this.ykb_community_id, this.community_id, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.widget.ChatGroupRulePopwindow.1
            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
                ChatGroupRulePopwindow.this.dismiss();
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onStart(String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onSuccess(String s2, String requstUrl) {
                try {
                    ChatGroupRulePopwindow.this.dismiss();
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_CHAT_GROUP_ADD);
                        Intent intent = new Intent(ChatGroupRulePopwindow.this.context, (Class<?>) ChatActivity.class);
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
                        intent.putExtra("id", ChatGroupRulePopwindow.this.ykb_community_id);
                        intent.putExtra("activity_join", "jon_us");
                        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, ChatGroupRulePopwindow.this.community_id);
                        ChatGroupRulePopwindow.this.context.startActivity(intent);
                    } else {
                        ToastUtil.shortToast(ChatGroupRulePopwindow.this.context, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (!TextUtils.isEmpty(this.showUrl)) {
            chatGroupJoin();
            return;
        }
        this.context.startActivity(new Intent(this.context, (Class<?>) GroupChatHomeActivity.class));
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_ad_chat_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.close);
        ImageView imageView2 = (ImageView) findViewById(R.id.imgurl);
        if (TextUtils.isEmpty(this.showUrl)) {
            imageView.setVisibility(8);
            GlideApp.with(getContext()).load(Integer.valueOf(R.mipmap.chat_group_goto)).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into(imageView2);
        } else {
            imageView.setVisibility(0);
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(this.showUrl)).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into(imageView2);
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.c1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16356c.lambda$onCreate$0(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16389c.lambda$onCreate$1(view);
            }
        });
    }

    public ChatGroupRulePopwindow(@NonNull Context context) {
        super(context);
        this.context = context;
    }
}
