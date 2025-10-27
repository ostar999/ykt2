package com.psychiatrygarden.http;

import android.content.Context;
import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.Constants;
import java.lang.ref.WeakReference;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ChatRequest {
    private static WeakReference<ChatRequest> mWeakReference;
    private Context mContext;

    private ChatRequest(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static ChatRequest getIntance(Context mContext) {
        WeakReference<ChatRequest> weakReference = mWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            mWeakReference = new WeakReference<>(new ChatRequest(mContext.getApplicationContext()));
        }
        return mWeakReference.get();
    }

    public <T> void addBlackList(String to_user_uuid, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.addBlackListApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("to_user_uuid", to_user_uuid);
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void addManager(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.addManagerApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.18
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void bannedSingleMember(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.bannedSingleMemberApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.21
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatAnnouncement(String id, String community_id, String announcement, String announcement_image, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatAnnouncementApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("announcement", announcement);
        ajaxParams.put("announcement_image", announcement_image);
        ajaxParams.put("community_id", community_id);
        ajaxParams.put("id", id);
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatCategory(final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatCategoryApi;
        YJYHttpUtils.get(this.mContext, str, new AjaxParams(), new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatDetail(String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatDetailApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("community_id", community_id);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatExit(String id, String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatSignOutApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("community_id", community_id);
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatGroupList(String category_id, String keyword, int page, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatListApi;
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(category_id)) {
            ajaxParams.put("category_id", category_id);
        }
        if (!TextUtils.isEmpty(keyword)) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, keyword);
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatJoin(String id, final String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatJoinApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("community_id", community_id);
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
                try {
                    JSONObject jSONObject = new JSONObject((String) s2);
                    if (!jSONObject.optString("code").equals("200") || jSONObject.optString("message").equals("已加入过该群组")) {
                        return;
                    }
                    EMMessage eMMessageCreateSendMessage = EMMessage.createSendMessage(EMMessage.Type.CUSTOM);
                    eMMessageCreateSendMessage.addBody(new EMCustomMessageBody("你已加入本群，开始发言吧"));
                    eMMessageCreateSendMessage.setTo(community_id);
                    eMMessageCreateSendMessage.setChatType(EMMessage.ChatType.GroupChat);
                    eMMessageCreateSendMessage.setUnread(false);
                    EMClient.getInstance().chatManager().getConversation(community_id, EMConversation.EMConversationType.GroupChat, true).insertMessage(eMMessageCreateSendMessage);
                    EMClient.getInstance().chatManager().saveMessage(eMMessageCreateSendMessage);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public <T> void chatMember(String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatMemberApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("community_id", community_id);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatRecordingTime(String community_id, String id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatRecordingTimeApi;
        String str2 = (System.currentTimeMillis() / 1000) + "";
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("community_id", community_id);
        ajaxParams.put("entry_time", str2);
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.15
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void chatSignOut(String id, String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.chatSignOutApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("community_id", community_id);
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void communityCategory(final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.communityCategoryApi;
        YJYHttpUtils.get(this.mContext, str, new AjaxParams(), new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.23
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionDataCallBack questionDataCallBack = callBack;
                if (questionDataCallBack != null) {
                    questionDataCallBack.onFailure(t2, errorNo, strMsg, str);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                QuestionDataCallBack questionDataCallBack = callBack;
                if (questionDataCallBack != null) {
                    questionDataCallBack.onStart(str);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                QuestionDataCallBack questionDataCallBack = callBack;
                if (questionDataCallBack != null) {
                    questionDataCallBack.onSuccess(s2, str);
                }
            }
        });
    }

    public <T> void communitySee(String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.communitySeeApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("community_id", community_id);
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.17
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void getAnnouncement(String community_id, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getAnnouncementApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("community_id", community_id);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.16
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void onBlackList(String to_user_uuid, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.onBlackListApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("to_user_uuid", to_user_uuid);
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void removeBatchMember(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.removeBatchMemberApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.14
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void removeBlackList(String to_user_uuid, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.removeBlackListApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("to_user_uuid", to_user_uuid);
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void removeManager(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.removeManagerApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.19
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void removeSingleMember(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.removeSingleMemberApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void unBannedMember(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.unBannedMemberApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.20
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void updateCommunity(String id, String community_id, String name, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.updateCommunityApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("community_id", community_id);
        ajaxParams.put("id", id);
        ajaxParams.put("name", name);
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void updateUserToEasemob(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.updateUserToEasemobApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.ChatRequest.22
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionDataCallBack questionDataCallBack = callBack;
                if (questionDataCallBack != null) {
                    questionDataCallBack.onFailure(t2, errorNo, strMsg, str);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                QuestionDataCallBack questionDataCallBack = callBack;
                if (questionDataCallBack != null) {
                    questionDataCallBack.onStart(str);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                QuestionDataCallBack questionDataCallBack = callBack;
                if (questionDataCallBack != null) {
                    questionDataCallBack.onSuccess(s2, str);
                }
            }
        });
    }
}
