package com.hyphenate.chat;

import android.os.AsyncTask;
import com.hyphenate.cloud.EMHttpClient;
import com.hyphenate.cloud.HttpClientManager;
import com.hyphenate.cloud.HttpResponse;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes4.dex */
class EMTranslator {
    static final String mGetLanguagePath = "/languages?api-version=3.0";
    private static EMTranslateParams mParams = null;
    static final String mTranslatePath = "/translate?api-version=3.0";

    public interface TranslationCallback {
        void onResult(String str, String str2);
    }

    public EMTranslator(EMTranslateParams eMTranslateParams) {
        mParams = eMTranslateParams;
        eMTranslateParams.EndPoint = eMTranslateParams.EndPoint.replaceAll("/$", "");
    }

    private List<EMLanguage> getLanguageListFromResponse(String str) throws JSONException {
        ArrayList arrayList = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("translation");
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject2 = (JSONObject) jSONObject.get(next);
                if (jSONObject2 instanceof JSONObject) {
                    EMLanguage eMLanguage = new EMLanguage();
                    eMLanguage.LanguageCode = next;
                    eMLanguage.LanguageName = jSONObject2.getString("name");
                    eMLanguage.LanguageLocalName = jSONObject2.getString("nativeName");
                    arrayList.add(eMLanguage);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTranslationFromResponse(String str) {
        try {
            return new JSONArray(str).get(0).toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public List<EMLanguage> getSupportedLanguages() {
        String str = mParams.EndPoint + mGetLanguagePath;
        ArrayList arrayList = new ArrayList();
        try {
            HttpResponse httpResponseHttpExecute = HttpClientManager.httpExecute(str, new HashMap(), "", EMHttpClient.GET);
            return httpResponseHttpExecute.code == 200 ? getLanguageListFromResponse(httpResponseHttpExecute.content) : arrayList;
        } catch (Exception e2) {
            e2.printStackTrace();
            return arrayList;
        }
    }

    public String getTranslatedText(String str) throws JSONException {
        try {
            Object obj = new JSONObject(str).getJSONArray("translations").get(0);
            return obj instanceof JSONObject ? ((JSONObject) obj).getString("text") : "";
        } catch (JSONException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public void translate(final String str, final String str2, final TranslationCallback translationCallback) {
        new AsyncTask<String, Void, HttpResponse>() { // from class: com.hyphenate.chat.EMTranslator.1
            @Override // android.os.AsyncTask
            public HttpResponse doInBackground(String... strArr) throws JSONException {
                try {
                    String str3 = EMTranslator.mParams.EndPoint + EMTranslator.mTranslatePath + "&to=" + str2;
                    JSONArray jSONArray = new JSONArray();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("Text", str);
                    jSONArray.put(jSONObject);
                    HashMap map = new HashMap();
                    map.put("Ocp-Apim-Subscription-Key", EMTranslator.mParams.SubscriptionKey);
                    map.put("Content-Type", "application/json; charset=UTF-8");
                    return HttpClientManager.httpExecute(str3, map, jSONArray.toString(), EMHttpClient.POST);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(HttpResponse httpResponse) {
                if (httpResponse != null) {
                    try {
                        int i2 = httpResponse.code;
                        if (i2 == 200) {
                            String str3 = httpResponse.content;
                            if (str3 != null && str3.length() > 0) {
                                translationCallback.onResult(EMTranslator.this.getTranslatedText(EMTranslator.this.getTranslationFromResponse(str3)), "");
                                return;
                            }
                        } else {
                            EMLog.d("EMTranslator", i2 + ":" + httpResponse.content);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                translationCallback.onResult("", "Fail to translation");
            }
        }.execute(new String[0]);
    }
}
