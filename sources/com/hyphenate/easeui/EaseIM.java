package com.hyphenate.easeui;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.manager.EaseChatPresenter;
import com.hyphenate.easeui.manager.EaseConfigsManager;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.provider.EaseConversationInfoProvider;
import com.hyphenate.easeui.provider.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.provider.EaseSettingsProvider;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
public class EaseIM {
    private static final String TAG = "EaseIM";
    private static EaseIM instance;
    private EaseAvatarOptions avatarOptions;
    private EaseConfigsManager configsManager;
    private EaseConversationInfoProvider conversationInfoProvider;
    public boolean isVideoCalling;
    public boolean isVoiceCalling;
    private EaseEmojiconInfoProvider mEmojiconInfoProvider;
    private EaseChatPresenter presenter;
    private EaseSettingsProvider settingsProvider;
    private EaseUserProfileProvider userProvider;
    private Context appContext = null;
    private EaseNotifier notifier = null;
    private boolean sdkInited = false;

    private EaseIM() {
    }

    private EaseSettingsProvider getDefaultSettingsProvider() {
        return new EaseSettingsProvider() { // from class: com.hyphenate.easeui.EaseIM.1
            @Override // com.hyphenate.easeui.provider.EaseSettingsProvider
            public boolean isMsgNotifyAllowed(EMMessage eMMessage) {
                return false;
            }

            @Override // com.hyphenate.easeui.provider.EaseSettingsProvider
            public boolean isMsgSoundAllowed(EMMessage eMMessage) {
                return false;
            }

            @Override // com.hyphenate.easeui.provider.EaseSettingsProvider
            public boolean isMsgVibrateAllowed(EMMessage eMMessage) {
                return false;
            }

            @Override // com.hyphenate.easeui.provider.EaseSettingsProvider
            public boolean isSpeakerOpened() {
                return false;
            }
        };
    }

    public static EaseIM getInstance() {
        if (instance == null) {
            synchronized (EaseIM.class) {
                if (instance == null) {
                    instance = new EaseIM();
                }
            }
        }
        return instance;
    }

    private void initNotifier() {
        this.notifier = new EaseNotifier(this.appContext);
    }

    public void addChatPresenter(EaseChatPresenter easeChatPresenter) {
        this.presenter = easeChatPresenter;
        easeChatPresenter.attachApp(this.appContext);
    }

    public EaseAvatarOptions getAvatarOptions() {
        return this.avatarOptions;
    }

    public EaseChatPresenter getChatPresenter() {
        return this.presenter;
    }

    public EaseConfigsManager getConfigsManager() {
        return this.configsManager;
    }

    public Context getContext() {
        return this.appContext;
    }

    public EaseConversationInfoProvider getConversationInfoProvider() {
        return this.conversationInfoProvider;
    }

    public EaseEmojiconInfoProvider getEmojiconInfoProvider() {
        return this.mEmojiconInfoProvider;
    }

    public EaseNotifier getNotifier() {
        return this.notifier;
    }

    public EaseSettingsProvider getSettingsProvider() {
        if (this.settingsProvider == null) {
            this.settingsProvider = getDefaultSettingsProvider();
        }
        return this.settingsProvider;
    }

    public EaseUserProfileProvider getUserProvider() {
        return this.userProvider;
    }

    public synchronized boolean init(Context context, EMOptions eMOptions) {
        if (this.sdkInited) {
            return true;
        }
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext;
        if (!isMainProcess(applicationContext)) {
            Log.e(TAG, "enter the service process!");
            return false;
        }
        if (eMOptions == null) {
            eMOptions = initChatOptions();
        }
        this.configsManager = new EaseConfigsManager(context);
        EMClient.getInstance().init(context, eMOptions);
        initNotifier();
        EaseChatPresenter easeChatPresenter = new EaseChatPresenter();
        this.presenter = easeChatPresenter;
        easeChatPresenter.attachApp(this.appContext);
        this.sdkInited = true;
        return true;
    }

    public EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");
        EMOptions eMOptions = new EMOptions();
        eMOptions.setAcceptInvitationAlways(false);
        eMOptions.setRequireAck(true);
        eMOptions.setRequireDeliveryAck(false);
        return eMOptions;
    }

    public boolean isMainProcess(Context context) {
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return context.getApplicationInfo().packageName.equals(runningAppProcessInfo.processName);
            }
        }
        return false;
    }

    public EaseIM setAvatarOptions(EaseAvatarOptions easeAvatarOptions) {
        this.avatarOptions = easeAvatarOptions;
        return this;
    }

    public EaseIM setConversationInfoProvider(EaseConversationInfoProvider easeConversationInfoProvider) {
        this.conversationInfoProvider = easeConversationInfoProvider;
        return this;
    }

    public EaseIM setEmojiconInfoProvider(EaseEmojiconInfoProvider easeEmojiconInfoProvider) {
        this.mEmojiconInfoProvider = easeEmojiconInfoProvider;
        return this;
    }

    public EaseIM setSettingsProvider(EaseSettingsProvider easeSettingsProvider) {
        this.settingsProvider = easeSettingsProvider;
        return this;
    }

    public EaseIM setUserProvider(EaseUserProfileProvider easeUserProfileProvider) {
        this.userProvider = easeUserProfileProvider;
        return this;
    }
}
