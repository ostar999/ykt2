package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.google.gson.Gson;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.EMSilentModeParam;
import com.hyphenate.chat.EMSilentModeResult;
import com.plv.socket.event.sclass.PLVInLiveAckResult;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.MessageNoticeTypeBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MessageNoticeSetAct extends BaseActivity {
    private Switch commentSwitchs;
    private Switch courseSwitchs;
    private Switch courseUpdateSwitchs;
    private Switch friendSwitchs;
    private Switch likeSwitchs;
    private Switch messageSwitchs;
    private Switch privateLetterSwitchs;
    private Switch serviceSwitchs;
    private boolean isInitSystem = false;
    private boolean isInitComment = false;
    private boolean isInitPraise = false;
    private boolean isInitFriend = false;
    private boolean isInitService = false;
    private boolean isInitLive = false;
    private boolean isInitCourseUpdate = false;
    boolean isUserInteractionEnabled = false;

    /* renamed from: com.psychiatrygarden.activity.setting.MessageNoticeSetAct$1, reason: invalid class name */
    public class AnonymousClass1 implements EMValueCallBack<EMSilentModeResult> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EMPushManager.EMPushRemindType eMPushRemindType) {
            if (MessageNoticeSetAct.this.privateLetterSwitchs != null) {
                MessageNoticeSetAct.this.privateLetterSwitchs.setChecked(eMPushRemindType == EMPushManager.EMPushRemindType.ALL);
            }
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onError(int error, String errorMsg) {
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onSuccess(EMSilentModeResult result) {
            final EMPushManager.EMPushRemindType remindType = result.getRemindType();
            MessageNoticeSetAct.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.setting.y
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13914c.lambda$onSuccess$0(remindType);
                }
            });
        }
    }

    private void getMessageNotice() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getMessageNotice, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.MessageNoticeSetAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MessageNoticeSetAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                MessageNoticeSetAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        MessageNoticeTypeBean messageNoticeTypeBean = (MessageNoticeTypeBean) new Gson().fromJson(s2, MessageNoticeTypeBean.class);
                        if (messageNoticeTypeBean.getData() != null) {
                            boolean z2 = true;
                            MessageNoticeSetAct.this.isInitSystem = !messageNoticeTypeBean.getData().getSystem().equals("1");
                            MessageNoticeSetAct.this.messageSwitchs.setChecked(messageNoticeTypeBean.getData().getSystem().equals("1"));
                            MessageNoticeSetAct.this.isInitComment = !messageNoticeTypeBean.getData().getComment().equals("1");
                            MessageNoticeSetAct.this.commentSwitchs.setChecked(messageNoticeTypeBean.getData().getComment().equals("1"));
                            MessageNoticeSetAct.this.isInitPraise = !messageNoticeTypeBean.getData().getPraise().equals("1");
                            MessageNoticeSetAct.this.likeSwitchs.setChecked(messageNoticeTypeBean.getData().getPraise().equals("1"));
                            MessageNoticeSetAct.this.isInitFriend = !messageNoticeTypeBean.getData().getFriends().equals("1");
                            MessageNoticeSetAct.this.friendSwitchs.setChecked(messageNoticeTypeBean.getData().getFriends().equals("1"));
                            MessageNoticeSetAct.this.courseUpdateSwitchs.setChecked(messageNoticeTypeBean.getData().getCourseUpdate().equals("1"));
                            MessageNoticeSetAct.this.isUserInteractionEnabled = !messageNoticeTypeBean.getData().getChat().equals("1");
                            MessageNoticeSetAct.this.isInitLive = !messageNoticeTypeBean.getData().getLive().equals("1");
                            MessageNoticeSetAct.this.courseSwitchs.setChecked(messageNoticeTypeBean.getData().getLive().equals("1"));
                            MessageNoticeSetAct messageNoticeSetAct = MessageNoticeSetAct.this;
                            if (messageNoticeTypeBean.getData().getService().equals("1")) {
                                z2 = false;
                            }
                            messageNoticeSetAct.isInitService = z2;
                            MessageNoticeSetAct.this.serviceSwitchs.setChecked(messageNoticeTypeBean.getData().getService().equals("1"));
                        }
                    } else {
                        ToastUtil.shortToast(MessageNoticeSetAct.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                MessageNoticeSetAct.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(CompoundButton compoundButton, boolean z2) {
        if (this.isInitSystem) {
            settingMessageNotice("system", z2 ? "1" : "0");
        }
        this.isInitSystem = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(CompoundButton compoundButton, boolean z2) {
        if (this.isInitComment) {
            settingMessageNotice(ClientCookie.COMMENT_ATTR, z2 ? "1" : "0");
        }
        this.isInitComment = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(CompoundButton compoundButton, boolean z2) {
        if (this.isInitPraise) {
            settingMessageNotice("praise", z2 ? "1" : "0");
        }
        this.isInitPraise = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(CompoundButton compoundButton, boolean z2) {
        EMClient.getInstance().pushManager().setSilentModeForAll(new EMSilentModeParam(EMSilentModeParam.EMSilentModeParamType.REMIND_TYPE).setRemindType(z2 ? EMPushManager.EMPushRemindType.ALL : EMPushManager.EMPushRemindType.NONE), new EMValueCallBack<EMSilentModeResult>() { // from class: com.psychiatrygarden.activity.setting.MessageNoticeSetAct.2
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(EMSilentModeResult value) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(CompoundButton compoundButton, boolean z2) {
        if (this.isInitFriend) {
            settingMessageNotice("friends", z2 ? "1" : "0");
        }
        this.isInitFriend = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(CompoundButton compoundButton, boolean z2) {
        if (this.isInitLive) {
            settingMessageNotice(PLVInLiveAckResult.STATUS_LIVE, z2 ? "1" : "0");
        }
        this.isInitLive = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(CompoundButton compoundButton, boolean z2) {
        if (this.isInitCourseUpdate) {
            settingMessageNotice("course_update", z2 ? "1" : "0");
        }
        this.isInitCourseUpdate = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(CompoundButton compoundButton, boolean z2) {
        if (this.isInitService) {
            settingMessageNotice("service", z2 ? "1" : "0");
        }
        this.isInitService = true;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) MessageNoticeSetAct.class);
    }

    private void settingMessageNotice(final String type, final String value) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", type);
        ajaxParams.put("value", value);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("is_vip", UserConfig.getInstance().getUser().getIs_vip());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.setMessageNotice, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.MessageNoticeSetAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MessageNoticeSetAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                MessageNoticeSetAct.this.showProgressDialog();
            }

            /* JADX WARN: Removed duplicated region for block: B:28:0x0063  */
            @Override // net.tsz.afinal.http.AjaxCallBack
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onSuccess(java.lang.String r3) {
                /*
                    Method dump skipped, instructions count: 290
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.setting.MessageNoticeSetAct.AnonymousClass4.onSuccess(java.lang.String):void");
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.messageSwitchs = (Switch) findViewById(R.id.messageSwitchs);
        this.commentSwitchs = (Switch) findViewById(R.id.commentSwitchs);
        this.likeSwitchs = (Switch) findViewById(R.id.likeSwitchs);
        this.friendSwitchs = (Switch) findViewById(R.id.friendSwitchs);
        this.courseUpdateSwitchs = (Switch) findViewById(R.id.courseUpdateSwitchs);
        this.privateLetterSwitchs = (Switch) findViewById(R.id.privateLetterSwitchs);
        this.serviceSwitchs = (Switch) findViewById(R.id.serviceSwitchs);
        this.courseSwitchs = (Switch) findViewById(R.id.courseSwitchs);
        EMClient.getInstance().pushManager().getSilentModeForAll(new AnonymousClass1());
        this.messageSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.q
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13898c.lambda$init$0(compoundButton, z2);
            }
        });
        this.commentSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.r
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13900c.lambda$init$1(compoundButton, z2);
            }
        });
        this.likeSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.s
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13902c.lambda$init$2(compoundButton, z2);
            }
        });
        this.privateLetterSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.t
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13904c.lambda$init$3(compoundButton, z2);
            }
        });
        this.friendSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.u
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13906c.lambda$init$4(compoundButton, z2);
            }
        });
        this.courseSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.v
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13908c.lambda$init$5(compoundButton, z2);
            }
        });
        this.courseUpdateSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.w
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13910c.lambda$init$6(compoundButton, z2);
            }
        });
        this.serviceSwitchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.setting.x
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13912c.lambda$init$7(compoundButton, z2);
            }
        });
        getMessageNotice();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_message_notice_set);
        setTitle("消息通知");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
