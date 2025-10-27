package com.umeng.socialize.net.base;

import android.text.TextUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.UResponse;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class SocializeReseponse extends UResponse {
    protected static final String TAG = "SocializeReseponse";
    private int mHttpCode;
    protected JSONObject mJsonData;
    public String mMsg;
    public int mStCode;

    public SocializeReseponse(JSONObject jSONObject) {
        super(jSONObject);
        this.mStCode = -103;
        this.mJsonData = parseStatus(jSONObject);
        parseJsonObject();
    }

    private void parseErrorMsg(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(itKeys.next());
                if (TextUtils.isEmpty(jSONObject2.getString("msg"))) {
                    jSONObject2.getJSONObject("data").getString(SocializeProtocolConstants.PROTOCOL_KEY_PLATFORM_ERROR);
                }
            }
        } catch (Exception e2) {
            SLog.error(e2);
        }
    }

    public JSONObject getJsonData() {
        return this.mJsonData;
    }

    public boolean isHttpOK() {
        return this.mHttpCode == 200;
    }

    public boolean isOk() {
        return this.mStCode == 200;
    }

    public void parseJsonObject() {
    }

    public JSONObject parseStatus(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            int iOptInt = jSONObject.optInt("st", 1998);
            this.mStCode = iOptInt;
            if (iOptInt == 0) {
                return null;
            }
            this.mMsg = jSONObject.optString("msg", "");
            String strOptString = jSONObject.optString("data", null);
            if (TextUtils.isEmpty(strOptString)) {
                return null;
            }
            if (this.mStCode != 200) {
                parseErrorMsg(strOptString);
            }
            return new JSONObject(strOptString);
        } catch (JSONException e2) {
            SLog.error(UmengText.NET.PARSEERROR, e2);
            return null;
        }
    }

    public SocializeReseponse(Integer num, JSONObject jSONObject) {
        this(jSONObject);
        this.mHttpCode = num == null ? -1 : num.intValue();
    }
}
