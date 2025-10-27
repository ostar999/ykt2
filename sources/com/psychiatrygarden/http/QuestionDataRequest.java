package com.psychiatrygarden.http;

import android.content.Context;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import java.lang.ref.WeakReference;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class QuestionDataRequest {
    private static WeakReference<QuestionDataRequest> mWeakReference;
    private Context mContext;

    private QuestionDataRequest(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static QuestionDataRequest getIntance(Context mContext) {
        WeakReference<QuestionDataRequest> weakReference = mWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            mWeakReference = new WeakReference<>(new QuestionDataRequest(mContext.getApplicationContext()));
        }
        return mWeakReference.get();
    }

    public <T> void cleanNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.cleanNoteURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.31
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void cleancollection(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.cleancollectionApi;
        params.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void clearAllAnswer(final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.clearAllAnswerApi;
        YJYHttpUtils.post(this.mContext, str, new AjaxParams(), new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.28
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void clearAllAnswerNotice(final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.clearAllAnswerNoticeApi;
        YJYHttpUtils.post(this.mContext, str, new AjaxParams(), new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.27
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void clearAnswer(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.clearAnswerApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.23
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void errorCorrectionGettype(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.errorCorrectionGettypeURL;
        YJYHttpUtils.get(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.32
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void errorCorrectionList(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.errorCorrectionListURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.36
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void errorCorrectionPraise(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.errorCorrectionPraiseURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.35
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void errorCorrectionReplyList(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.errorCorrectionReplyListURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.34
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void errorCorrectionSave(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.errorCorrectionSaveURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.33
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void getCircleAccess(String circleId, final QuestionDataCallBack<T> callBack) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", circleId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCircleAccess, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.getCircleAccess);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.getCircleAccess);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.getCircleAccess);
            }
        });
    }

    public <T> void getCorrectionRules(final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getCorrectionRulesURL;
        YJYHttpUtils.get(this.mContext, str, new AjaxParams(), new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.37
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void getMeidaSourceById(AjaxParams params, final String type, final QuestionDataCallBack<T> callBack, String appId) {
        final String str = NetworkRequestsURL.getSourceInfoById;
        params.put("app_id", appId);
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.38
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + type);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        }.progress(false, 1));
    }

    public <T> void getNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getNoteURL;
        YJYHttpUtils.get(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.30
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void getWordInfo(String word, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getWordInfoApi;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("word", word);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void pushComment(AjaxParams map, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.mPutComment;
        YJYHttpUtils.post(this.mContext, str, map, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.15
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (strOptString.equals("401")) {
                        callBack.onFailure(null, 401, new JSONObject((String) s2).optString("message"), str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void pushNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.pushNoteURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.29
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionAllAnalysisData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getallAnalysisApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.20
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionClearNoteData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.clearNoteApi;
        params.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.14
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionCombineUserAnswer(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.paperUserAnswer, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.paperUserAnswer);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.paperUserAnswer);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.paperUserAnswer);
            }
        });
    }

    public <T> void questionDoCollectData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getcollectionApi;
        params.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionInfo(String question_id, String module_type, final QuestionDataCallBack<T> callBack) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("module_type", module_type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.questionInfoApi, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.questionInfoApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.questionInfoApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.questionInfoApi);
            }
        });
    }

    public <T> void questionList(AjaxParams params, String category, boolean isCutQuestion, final QuestionDataCallBack<T> callBack) {
        final String str = isCutQuestion ? NetworkRequestsURL.sheetCutList : "unit".equals(category) ? NetworkRequestsURL.questionSetsList : NetworkRequestsURL.questionListApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.4
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

    public <T> void questionMyAnalysisData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getmyAnalysisApi;
        YJYHttpUtils.get(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.19
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionNoteData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getnoteApi;
        params.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.get(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionPutAnswerData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getanswerApi;
        params.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "上传答题记录失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionPutImgData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.stockApi;
        YJYHttpUtils.postImage(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.22
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionSACNoteData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getpostNoteApi;
        params.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionSARAnalysisData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getsaveAnalysisApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.21
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void questionSearch(int page, String key_word, String sortType, String questionType, String qsBank, final QuestionDataCallBack<T> callBack) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, key_word);
        ajaxParams.put("qsBank", "" + qsBank);
        ajaxParams.put("sortType", "" + sortType);
        ajaxParams.put("questionType", "" + questionType);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, page + "");
        ajaxParams.put(DatabaseManager.SIZE, com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.searchQuestionApi, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.searchQuestionApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.searchQuestionApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.searchQuestionApi);
            }
        });
    }

    public <T> void questionStatData(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getstatApi, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.getstatApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.getstatApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                try {
                    callBack.onSuccess(s2, NetworkRequestsURL.getstatApi);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public <T> void questionSubmitLabel(String question_id, String label_id, String count, boolean isAdmin, final QuestionDataCallBack<T> callBack) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("label_id", label_id);
        final String str = NetworkRequestsURL.questionUserLabelApi;
        if (isAdmin) {
            ajaxParams.put("count", count);
            str = NetworkRequestsURL.adminUpdateLabelCountApi;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.18
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

    public <T> void questionUserAnswer(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionUserAnswerApi, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.questionUserAnswerApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.questionUserAnswerApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.questionUserAnswerApi);
            }
        });
    }

    public <T> void questionVideo(String question_id, String video_id, final QuestionDataCallBack<T> callBack) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("video_id", video_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.questionVideoApi, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.16
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.questionVideoApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.questionVideoApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.questionVideoApi);
            }
        });
    }

    public <T> void questionlabel(String question_id, final QuestionDataCallBack<T> callBack) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionlabelApi, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.17
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.questionlabelApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(NetworkRequestsURL.questionlabelApi);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, NetworkRequestsURL.questionlabelApi);
            }
        });
    }

    public <T> void redoAnswer(AjaxParams params, final String url, final QuestionDataCallBack<T> callBack) {
        YJYHttpUtils.post(this.mContext, url, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.24
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", url);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(url);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, url);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), url);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", url);
                }
            }
        });
    }

    public <T> void submitStatistical(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.submitStatisticalApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.25
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void submitStatisticalExam(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.submitStatisticalExamApi;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.26
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void getMeidaSourceById(AjaxParams params, final String type, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.getSourceInfoById;
        params.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.QuestionDataRequest.39
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, "操作失败！", str);
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
                try {
                    String strOptString = new JSONObject((String) s2).optString("code");
                    if (strOptString.equals("200")) {
                        callBack.onSuccess(s2, str + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + type);
                    } else if (!strOptString.equals("500") || !strOptString.equals("502")) {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        }.progress(false, 1));
    }
}
