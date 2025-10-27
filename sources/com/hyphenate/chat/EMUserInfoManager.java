package com.hyphenate.chat;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMAUserInfoManager;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMUserInfoManager {
    static final String TAG = "EMUserInfoManager";
    EMAUserInfoManager emaObject;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public EMUserInfoManager(EMAUserInfoManager eMAUserInfoManager) {
        this.emaObject = eMAUserInfoManager;
    }

    public void fetchUserInfoByAttribute(final String[] strArr, final EMUserInfo.EMUserInfoType[] eMUserInfoTypeArr, final EMValueCallBack<Map<String, EMUserInfo>> eMValueCallBack) {
        EMLog.d(TAG, "start fetchUserInfoByAttribute");
        if (strArr == null || strArr.length == 0) {
            EMLog.d(TAG, "fetchUserInfoByUserId userIds is empty");
            eMValueCallBack.onError(205, "userIds is empty");
        } else if (eMUserInfoTypeArr != null && eMUserInfoTypeArr.length != 0) {
            this.executorService.execute(new Runnable() { // from class: com.hyphenate.chat.EMUserInfoManager.4
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    EMAError eMAError = new EMAError();
                    ArrayList arrayList = new ArrayList();
                    Collections.addAll(arrayList, strArr);
                    ArrayList arrayList2 = new ArrayList();
                    for (EMUserInfo.EMUserInfoType eMUserInfoType : eMUserInfoTypeArr) {
                        arrayList2.add(eMUserInfoType.getDesc());
                    }
                    EMLog.d(EMUserInfoManager.TAG, "fetchUserInfoByAttribute param: " + arrayList.toString() + "  " + arrayList2.toString());
                    String strFetchUserInfoByAttribute = EMUserInfoManager.this.emaObject.fetchUserInfoByAttribute(arrayList, arrayList2, eMAError);
                    StringBuilder sb = new StringBuilder();
                    sb.append("fetchUserInfoByAttribute response: ");
                    sb.append(strFetchUserInfoByAttribute);
                    EMLog.d(EMUserInfoManager.TAG, sb.toString());
                    if (eMValueCallBack == null) {
                        return;
                    }
                    if (eMAError.errCode() != 0) {
                        eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                        EMLog.e(EMUserInfoManager.TAG, "fetchUserInfoByAttribute failed error:" + eMAError.errCode() + "  errorMessage:" + eMAError.errMsg());
                        return;
                    }
                    EMLog.d(EMUserInfoManager.TAG, "fetchUserInfoByAttribute success");
                    HashMap map = new HashMap();
                    if (strFetchUserInfoByAttribute == null || strFetchUserInfoByAttribute.length() <= 0) {
                        EMLog.d(EMUserInfoManager.TAG, "fetchUserInfoByAttribute response is null ");
                        eMValueCallBack.onSuccess(null);
                        return;
                    }
                    try {
                        JSONObject jSONObject = new JSONObject(strFetchUserInfoByAttribute);
                        for (String str : strArr) {
                            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                            if (jSONObject2 != null) {
                                EMUserInfo eMUserInfo = new EMUserInfo();
                                eMUserInfo.setNickName(jSONObject2.optString(EMUserInfo.EMUserInfoType.NICKNAME.getDesc()));
                                String strOptString = jSONObject2.optString(EMUserInfo.EMUserInfoType.GENDER.getDesc());
                                if (strOptString != null && strOptString.length() > 0) {
                                    eMUserInfo.setGender(Integer.valueOf(strOptString).intValue());
                                }
                                eMUserInfo.setEmail(jSONObject2.optString(EMUserInfo.EMUserInfoType.EMAIL.getDesc()));
                                eMUserInfo.setPhoneNumber(jSONObject2.optString(EMUserInfo.EMUserInfoType.PHONE.getDesc()));
                                eMUserInfo.setSignature(jSONObject2.optString(EMUserInfo.EMUserInfoType.SIGN.getDesc()));
                                eMUserInfo.setAvatarUrl(jSONObject2.optString(EMUserInfo.EMUserInfoType.AVATAR_URL.getDesc()));
                                eMUserInfo.setExt(jSONObject2.optString(EMUserInfo.EMUserInfoType.EXT.getDesc()));
                                eMUserInfo.setBirth(jSONObject2.optString(EMUserInfo.EMUserInfoType.BIRTH.getDesc()));
                                eMUserInfo.setUserId(str);
                                map.put(str, eMUserInfo);
                            }
                        }
                    } catch (JSONException unused) {
                        eMAError.errMsg();
                    }
                    eMValueCallBack.onSuccess(map);
                }
            });
        } else {
            EMLog.d(TAG, "fetchUserInfoByUserId attributes is empty");
            eMValueCallBack.onError(205, "attributes is empty");
        }
    }

    public void fetchUserInfoByUserId(final String[] strArr, final EMValueCallBack<Map<String, EMUserInfo>> eMValueCallBack) {
        EMLog.d(TAG, "start fetchUserInfoByUserId");
        if (strArr != null && strArr.length != 0) {
            this.executorService.execute(new Runnable() { // from class: com.hyphenate.chat.EMUserInfoManager.3
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    EMAError eMAError = new EMAError();
                    ArrayList arrayList = new ArrayList();
                    Collections.addAll(arrayList, strArr);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(EMUserInfo.EMUserInfoType.NICKNAME.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.EMAIL.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.PHONE.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.GENDER.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.AVATAR_URL.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.SIGN.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.BIRTH.getDesc());
                    arrayList2.add(EMUserInfo.EMUserInfoType.EXT.getDesc());
                    EMLog.d(EMUserInfoManager.TAG, "fetchUserInfoByUserId param: " + arrayList.toString() + "  " + arrayList2.toString());
                    String strFetchUserInfoByAttribute = EMUserInfoManager.this.emaObject.fetchUserInfoByAttribute(arrayList, arrayList2, eMAError);
                    if (eMValueCallBack == null) {
                        return;
                    }
                    EMLog.d(EMUserInfoManager.TAG, "fetchUserInfoByUserId response: " + strFetchUserInfoByAttribute);
                    if (eMAError.errCode() != 0) {
                        eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                        EMLog.e(EMUserInfoManager.TAG, "fetchUserInfoByUserId failed error:" + eMAError.errCode() + "  errorMessage:" + eMAError.errMsg());
                        return;
                    }
                    EMLog.d(EMUserInfoManager.TAG, "fetchUserInfoByUserId success");
                    HashMap map = new HashMap();
                    if (strFetchUserInfoByAttribute == null || strFetchUserInfoByAttribute.length() <= 0) {
                        EMLog.e(EMUserInfoManager.TAG, "fetchUserInfoByUserId response is null");
                        eMValueCallBack.onSuccess(null);
                        return;
                    }
                    try {
                        JSONObject jSONObject = new JSONObject(strFetchUserInfoByAttribute);
                        for (String str : strArr) {
                            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                            if (jSONObject2 != null) {
                                EMUserInfo eMUserInfo = new EMUserInfo();
                                eMUserInfo.setNickName(jSONObject2.optString(EMUserInfo.EMUserInfoType.NICKNAME.getDesc()));
                                String strOptString = jSONObject2.optString(EMUserInfo.EMUserInfoType.GENDER.getDesc());
                                if (strOptString != null && strOptString.length() > 0) {
                                    try {
                                        eMUserInfo.setGender(Integer.parseInt(strOptString));
                                    } catch (Exception unused) {
                                        eMUserInfo.setGender(0);
                                    }
                                }
                                eMUserInfo.setEmail(jSONObject2.optString(EMUserInfo.EMUserInfoType.EMAIL.getDesc()));
                                eMUserInfo.setPhoneNumber(jSONObject2.optString(EMUserInfo.EMUserInfoType.PHONE.getDesc()));
                                eMUserInfo.setSignature(jSONObject2.optString(EMUserInfo.EMUserInfoType.SIGN.getDesc()));
                                eMUserInfo.setAvatarUrl(jSONObject2.optString(EMUserInfo.EMUserInfoType.AVATAR_URL.getDesc()));
                                eMUserInfo.setExt(jSONObject2.optString(EMUserInfo.EMUserInfoType.EXT.getDesc()));
                                eMUserInfo.setBirth(jSONObject2.optString(EMUserInfo.EMUserInfoType.BIRTH.getDesc()));
                                eMUserInfo.setUserId(str);
                                map.put(str, eMUserInfo);
                            }
                        }
                    } catch (JSONException unused2) {
                        eMAError.errMsg();
                    }
                    eMValueCallBack.onSuccess(map);
                }
            });
        } else {
            EMLog.d(TAG, "fetchUserInfoByUserId userIds is empty");
            eMValueCallBack.onError(205, "userIds is empty");
        }
    }

    public void updateOwnInfo(final EMUserInfo eMUserInfo, final EMValueCallBack<String> eMValueCallBack) {
        EMLog.d(TAG, "start updateOwnInfo");
        if (eMUserInfo != null) {
            this.executorService.execute(new Runnable() { // from class: com.hyphenate.chat.EMUserInfoManager.1
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    EMAError eMAError = new EMAError();
                    JSONObject jSONObject = new JSONObject();
                    try {
                        if (eMUserInfo != null) {
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.NICKNAME.getDesc(), eMUserInfo.getNickName());
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.AVATAR_URL.getDesc(), eMUserInfo.getAvatarUrl());
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.EMAIL.getDesc(), eMUserInfo.getEmail());
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.GENDER.getDesc(), Integer.valueOf(eMUserInfo.getGender()));
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.BIRTH.getDesc(), eMUserInfo.getBirth());
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.PHONE.getDesc(), eMUserInfo.getPhoneNumber());
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.SIGN.getDesc(), eMUserInfo.getSignature());
                            jSONObject.putOpt(EMUserInfo.EMUserInfoType.EXT.getDesc(), eMUserInfo.getExt());
                        }
                        EMLog.d(EMUserInfoManager.TAG, "updateOwnInfo param: " + jSONObject.toString());
                        EMUserInfoManager.this.emaObject.updateOwnInfo(jSONObject.toString(), eMAError);
                        if (eMValueCallBack == null) {
                            return;
                        }
                        if (eMAError.errCode() == 0) {
                            EMLog.d(EMUserInfoManager.TAG, "updateOwnInfo success");
                            eMValueCallBack.onSuccess(eMAError.toString());
                            return;
                        }
                        EMLog.e(EMUserInfoManager.TAG, "updateOwnInfo failed error:" + eMAError.errCode() + "  errorMessage:" + eMAError.errMsg());
                        eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                    } catch (Exception e2) {
                        e2.getStackTrace();
                    }
                }
            });
        } else {
            EMLog.d(TAG, "fetchUserInfoByUserId userInfo is empty");
            eMValueCallBack.onError(205, "userInfo is empty");
        }
    }

    public void updateOwnInfoByAttribute(final EMUserInfo.EMUserInfoType eMUserInfoType, final String str, final EMValueCallBack<String> eMValueCallBack) {
        EMLog.d(TAG, "start updateOwnInfoByAttribute");
        if (eMUserInfoType != null) {
            this.executorService.execute(new Runnable() { // from class: com.hyphenate.chat.EMUserInfoManager.2
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    EMAError eMAError = new EMAError();
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.putOpt(eMUserInfoType.getDesc(), str);
                        EMLog.d(EMUserInfoManager.TAG, "updateOwnInfoByAttribute param: " + jSONObject.toString());
                        String strUpdateOwnInfo = EMUserInfoManager.this.emaObject.updateOwnInfo(jSONObject.toString(), eMAError);
                        EMLog.d(EMUserInfoManager.TAG, "updateOwnInfoByAttribute response: " + strUpdateOwnInfo);
                        if (eMValueCallBack == null) {
                            return;
                        }
                        if (eMAError.errCode() == 0) {
                            EMLog.d(EMUserInfoManager.TAG, "updateOwnInfoByAttribute success");
                            eMValueCallBack.onSuccess(strUpdateOwnInfo);
                        } else {
                            EMLog.e(EMUserInfoManager.TAG, "updateOwnInfoByAttribute failed error:" + eMAError.errCode() + "  errorMessage:" + eMAError.errMsg());
                            eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                        }
                    } catch (Exception e2) {
                        e2.getStackTrace();
                    }
                }
            });
        } else {
            EMLog.d(TAG, "fetchUserInfoByUserId attribute is null");
            eMValueCallBack.onError(205, "attribute is null");
        }
    }
}
