package com.nirvana.tools.cache;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class CacheHandler {
    private static final String KEY_CONTENT = "content";
    private static final String KEY_VERSION = "version";
    private CacheRepository mRepository;

    public CacheHandler(CacheRepository cacheRepository) {
        this.mRepository = cacheRepository;
    }

    public String load() {
        CacheRepository cacheRepository = this.mRepository;
        if (cacheRepository != null) {
            String str = cacheRepository.read();
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (this.mRepository.getTemplate().getCacheVersion() == jSONObject.optInt("version", -1)) {
                        return jSONObject.optString("content");
                    }
                    return null;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean save(String str) throws JSONException {
        if (this.mRepository != null) {
            if (TextUtils.isEmpty(str)) {
                this.mRepository.clear();
                return true;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("content", str);
                jSONObject.put("version", this.mRepository.getTemplate().getCacheVersion());
                this.mRepository.write(jSONObject.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
