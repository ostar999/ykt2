package com.psychiatrygarden.http;

import android.content.Context;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class CourseDataRequest {
    private static WeakReference<CourseDataRequest> mWeakReference;
    private Context mContext;

    private CourseDataRequest(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static CourseDataRequest getIntance(Context mContext) {
        WeakReference<CourseDataRequest> weakReference = mWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            mWeakReference = new WeakReference<>(new CourseDataRequest(mContext.getApplicationContext()));
        }
        return mWeakReference.get();
    }

    public <T> void addNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.courseVideoAddNote;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.CourseDataRequest.2
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
                    if (new JSONObject((String) s2).optString("code").equals("200")) {
                        callBack.onSuccess(s2, str);
                        EventBus.getDefault().post(EventBusConstant.ADD_NOTE);
                    } else {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void cleanNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.mCourseClearNoteURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.CourseDataRequest.5
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
                    if (new JSONObject((String) s2).optString("code").equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void deleteNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.deleteVideoNote;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.CourseDataRequest.6
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
                    if (new JSONObject((String) s2).optString("code").equals("200")) {
                        callBack.onSuccess(s2, str);
                        EventBus.getDefault().post(EventBusConstant.DEL_NOTE);
                    } else {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void getNote(AjaxParams params, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.mCourseNoteListURL;
        YJYHttpUtils.get(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.CourseDataRequest.3
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
                    if (new JSONObject((String) s2).optString("code").equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else {
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
        final String str = NetworkRequestsURL.mCourseAddNoteURL;
        YJYHttpUtils.post(this.mContext, str, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.CourseDataRequest.1
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
                    if (new JSONObject((String) s2).optString("code").equals("200")) {
                        callBack.onSuccess(s2, str);
                    } else {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", str);
                }
            }
        });
    }

    public <T> void getNote(AjaxParams params, final QuestionDataCallBack<T> callBack, final String url) {
        YJYHttpUtils.get(this.mContext, url, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.CourseDataRequest.4
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
                    if (new JSONObject((String) s2).optString("code").equals("200")) {
                        callBack.onSuccess(s2, url);
                    } else {
                        callBack.onFailure(null, 0, new JSONObject((String) s2).optString("message"), url);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    callBack.onFailure(null, 0, "数据解析失败！", url);
                }
            }
        });
    }
}
