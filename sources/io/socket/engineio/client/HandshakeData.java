package io.socket.engineio.client;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class HandshakeData {
    public long pingInterval;
    public long pingTimeout;
    public String sid;
    public String[] upgrades;

    public HandshakeData(String str) throws JSONException {
        this(new JSONObject(str));
    }

    public HandshakeData(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("upgrades");
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = jSONArray.getString(i2);
        }
        this.sid = jSONObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_SID);
        this.upgrades = strArr;
        this.pingInterval = jSONObject.getLong("pingInterval");
        this.pingTimeout = jSONObject.getLong("pingTimeout");
    }
}
