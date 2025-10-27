package io.socket.hasbinary;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class HasBinary {
    private static final Logger logger = Logger.getLogger(HasBinary.class.getName());

    private HasBinary() {
    }

    private static boolean _hasBinary(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof byte[]) {
            return true;
        }
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                try {
                    if (_hasBinary(jSONArray.isNull(i2) ? null : jSONArray.get(i2))) {
                        return true;
                    }
                } catch (JSONException e2) {
                    logger.log(Level.WARNING, "An error occured while retrieving data from JSONArray", (Throwable) e2);
                    return false;
                }
            }
        } else if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                try {
                    if (_hasBinary(jSONObject.get(itKeys.next()))) {
                        return true;
                    }
                } catch (JSONException e3) {
                    logger.log(Level.WARNING, "An error occured while retrieving data from JSONObject", (Throwable) e3);
                }
            }
        }
        return false;
    }

    public static boolean hasBinary(Object obj) {
        return _hasBinary(obj);
    }
}
