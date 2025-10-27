package io.socket.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class Binary {
    private static final String KEY_NUM = "num";
    private static final String KEY_PLACEHOLDER = "_placeholder";
    private static final Logger logger = Logger.getLogger(Binary.class.getName());

    public static class DeconstructedPacket {
        public byte[][] buffers;
        public Packet packet;
    }

    private static Object _deconstructPacket(Object obj, List<byte[]> list) throws JSONException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(KEY_PLACEHOLDER, true);
                jSONObject.put(KEY_NUM, list.size());
                list.add((byte[]) obj);
                return jSONObject;
            } catch (JSONException e2) {
                logger.log(Level.WARNING, "An error occured while putting data to JSONObject", (Throwable) e2);
                return null;
            }
        }
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = new JSONArray();
            JSONArray jSONArray2 = (JSONArray) obj;
            int length = jSONArray2.length();
            for (int i2 = 0; i2 < length; i2++) {
                try {
                    jSONArray.put(i2, _deconstructPacket(jSONArray2.get(i2), list));
                } catch (JSONException e3) {
                    logger.log(Level.WARNING, "An error occured while putting packet data to JSONObject", (Throwable) e3);
                    return null;
                }
            }
            return jSONArray;
        }
        if (!(obj instanceof JSONObject)) {
            return obj;
        }
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = (JSONObject) obj;
        Iterator<String> itKeys = jSONObject3.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                jSONObject2.put(next, _deconstructPacket(jSONObject3.get(next), list));
            } catch (JSONException e4) {
                logger.log(Level.WARNING, "An error occured while putting data to JSONObject", (Throwable) e4);
                return null;
            }
        }
        return jSONObject2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [org.json.JSONObject] */
    private static Object _reconstructPacket(Object obj, byte[][] bArr) throws JSONException {
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                try {
                    jSONArray.put(i2, _reconstructPacket(jSONArray.get(i2), bArr));
                } catch (JSONException e2) {
                    logger.log(Level.WARNING, "An error occured while putting packet data to JSONObject", (Throwable) e2);
                    return null;
                }
            }
            return jSONArray;
        }
        if (obj instanceof JSONObject) {
            obj = (JSONObject) obj;
            if (obj.optBoolean(KEY_PLACEHOLDER)) {
                int iOptInt = obj.optInt(KEY_NUM, -1);
                if (iOptInt < 0 || iOptInt >= bArr.length) {
                    return null;
                }
                return bArr[iOptInt];
            }
            Iterator<String> itKeys = obj.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    obj.put(next, _reconstructPacket(obj.get(next), bArr));
                } catch (JSONException e3) {
                    logger.log(Level.WARNING, "An error occured while putting data to JSONObject", (Throwable) e3);
                    return null;
                }
            }
        }
        return obj;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
    public static DeconstructedPacket deconstructPacket(Packet packet) {
        ArrayList arrayList = new ArrayList();
        packet.data = _deconstructPacket(packet.data, arrayList);
        packet.attachments = arrayList.size();
        DeconstructedPacket deconstructedPacket = new DeconstructedPacket();
        deconstructedPacket.packet = packet;
        deconstructedPacket.buffers = (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
        return deconstructedPacket;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.Object] */
    public static Packet reconstructPacket(Packet packet, byte[][] bArr) {
        packet.data = _reconstructPacket(packet.data, bArr);
        packet.attachments = -1;
        return packet;
    }
}
