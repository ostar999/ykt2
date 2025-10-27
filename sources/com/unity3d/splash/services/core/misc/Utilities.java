package com.unity3d.splash.services.core.misc;

import android.os.Handler;
import android.os.Looper;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Utilities {
    public static String Sha256(InputStream inputStream) throws IllegalAccessException, NoSuchMethodException, NoSuchAlgorithmException, IOException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (inputStream == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bArr = new byte[4096];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    return toHexString(messageDigest.digest());
                }
                messageDigest.update(bArr, 0, i2);
            }
        } catch (NoSuchAlgorithmException e2) {
            DeviceLog.exception("SHA-256 algorithm not found", e2);
            return null;
        }
    }

    public static String Sha256(String str) {
        return Sha256(str.getBytes());
    }

    public static String Sha256(byte[] bArr) throws IllegalAccessException, NoSuchMethodException, NoSuchAlgorithmException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr, 0, bArr.length);
            return toHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e2) {
            DeviceLog.exception("SHA-256 algorithm not found", e2);
            return null;
        }
    }

    public static JSONObject mergeJsonObjects(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        if (jSONObject == null) {
            return jSONObject2;
        }
        if (jSONObject2 == null) {
            return jSONObject;
        }
        JSONObject jSONObject3 = new JSONObject();
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            jSONObject3.put(next, jSONObject2.get(next));
        }
        Iterator<String> itKeys2 = jSONObject.keys();
        while (itKeys2.hasNext()) {
            String next2 = itKeys2.next();
            jSONObject3.put(next2, (jSONObject3.has(next2) && (jSONObject3.get(next2) instanceof JSONObject) && (jSONObject.get(next2) instanceof JSONObject)) ? mergeJsonObjects(jSONObject.getJSONObject(next2), jSONObject3.getJSONObject(next2)) : jSONObject.get(next2));
        }
        return jSONObject3;
    }

    public static byte[] readFileBytes(File file) throws IOException {
        if (file == null) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[(int) file.length()];
        int i2 = 0;
        int length = file.length() < 4096 ? (int) file.length() : 4096;
        while (true) {
            int i3 = fileInputStream.read(bArr, i2, length);
            if (i3 <= 0) {
                fileInputStream.close();
                return bArr;
            }
            i2 += i3;
            if (file.length() - i2 < 4096) {
                length = ((int) file.length()) - i2;
            }
        }
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0L);
    }

    public static void runOnUiThread(Runnable runnable, long j2) {
        Handler handler = new Handler(Looper.getMainLooper());
        if (j2 > 0) {
            handler.postDelayed(runnable, j2);
        } else {
            handler.post(runnable);
        }
    }

    public static String toHexString(byte[] bArr) {
        String str = "";
        for (byte b3 : bArr) {
            int i2 = b3 & 255;
            if (i2 <= 15) {
                str = str + "0";
            }
            str = str + Integer.toHexString(i2);
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean writeFile(java.io.File r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Error closing FileOutputStream"
            r1 = 0
            if (r4 != 0) goto L6
            return r1
        L6:
            r2 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L28
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L28
            byte[] r5 = r5.getBytes()     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L23
            r3.write(r5)     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L23
            r3.flush()     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L23
            r3.close()     // Catch: java.lang.Exception -> L1a
            goto L1e
        L1a:
            r5 = move-exception
            com.unity3d.splash.services.core.log.DeviceLog.exception(r0, r5)
        L1e:
            r1 = 1
            goto L38
        L20:
            r4 = move-exception
            r2 = r3
            goto L50
        L23:
            r5 = move-exception
            r2 = r3
            goto L29
        L26:
            r4 = move-exception
            goto L50
        L28:
            r5 = move-exception
        L29:
            java.lang.String r3 = "Could not write file"
            com.unity3d.splash.services.core.log.DeviceLog.exception(r3, r5)     // Catch: java.lang.Throwable -> L26
            if (r2 == 0) goto L38
            r2.close()     // Catch: java.lang.Exception -> L34
            goto L38
        L34:
            r5 = move-exception
            com.unity3d.splash.services.core.log.DeviceLog.exception(r0, r5)
        L38:
            if (r1 == 0) goto L4f
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "Wrote file: "
            r5.<init>(r0)
            java.lang.String r4 = r4.getAbsolutePath()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.unity3d.splash.services.core.log.DeviceLog.debug(r4)
        L4f:
            return r1
        L50:
            if (r2 == 0) goto L5a
            r2.close()     // Catch: java.lang.Exception -> L56
            goto L5a
        L56:
            r5 = move-exception
            com.unity3d.splash.services.core.log.DeviceLog.exception(r0, r5)
        L5a:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.splash.services.core.misc.Utilities.writeFile(java.io.File, java.lang.String):boolean");
    }
}
