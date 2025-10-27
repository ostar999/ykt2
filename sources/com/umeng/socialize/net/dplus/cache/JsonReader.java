package com.umeng.socialize.net.dplus.cache;

import com.umeng.socialize.utils.SLog;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class JsonReader extends IReader<JSONObject> {
    public JsonReader(String str) {
        super(str);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, org.json.JSONObject] */
    @Override // com.umeng.socialize.net.dplus.cache.IReader
    public void create(String str) {
        try {
            this.result = new JSONObject(str);
        } catch (JSONException e2) {
            SLog.error(e2);
        }
    }
}
