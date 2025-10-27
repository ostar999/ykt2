package com.psychiatrygarden.activity.purchase.http;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.Sha1Utils;
import com.tencent.open.SocialOperation;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PicUpLoadExecutor {
    private static final String TAG = "PicUpLoadHelper";
    public static final int UpLoadFinish = 801;
    public static final StringBuilder sb = new StringBuilder();
    public static String user_id;
    private ExecListenter ExecListenter;
    private SoftReference<ExecutorService> fixedThreadPool;
    private short poolSize = 1;
    private Handler handler = null;
    private String url = null;

    /* renamed from: f, reason: collision with root package name */
    public int f13700f = 1;

    public interface ExecListenter {
        void onBeforeExec();
    }

    public interface ExecRunnableListenter {
        void onRun(int i2);
    }

    public PicUpLoadExecutor(short poolSize, String user_id2) {
        this.fixedThreadPool = null;
        this.fixedThreadPool = new SoftReference<>(Executors.newFixedThreadPool(poolSize));
        user_id = user_id2;
    }

    public static String uploadPic(String uploadUrl, String filename, Bitmap bit) throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uploadUrl).openConnection();
            httpURLConnection.setChunkedStreamingMode(262144);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=******");
            HashMap map = new HashMap();
            map.put("app_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
            map.put("user_id", user_id);
            if (!UserConfig.getInstance().getUser().getToken().equals("")) {
                map.put("token", UserConfig.getInstance().getUser().getToken());
                map.put("secret", UserConfig.getInstance().getUser().getSecret());
            }
            map.put("type", ClientCookie.COMMENT_ATTR);
            TreeMap treeMap = new TreeMap(map);
            String str = "";
            for (Map.Entry entry : treeMap.entrySet()) {
                str = str + ((String) entry.getKey()) + "=" + ((String) entry.getValue());
            }
            httpURLConnection.setRequestProperty("app_id", (String) treeMap.get("app_id"));
            httpURLConnection.setRequestProperty("token", (String) treeMap.get("token"));
            httpURLConnection.setRequestProperty("secret", (String) treeMap.get("secret"));
            String str2 = (System.currentTimeMillis() / 1000) + "";
            String str3 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1") + str2;
            httpURLConnection.setRequestProperty("channel", "10000");
            httpURLConnection.setRequestProperty("timestamp", str2);
            httpURLConnection.setRequestProperty(AliyunLogKey.KEY_UUID, "" + AndroidBaseUtils.getIMEI(ProjectApp.instance()));
            httpURLConnection.setRequestProperty("client-type", "android");
            httpURLConnection.setRequestProperty("mobile-info", AndroidBaseUtils.getDeviceName());
            httpURLConnection.setRequestProperty("app-version", AndroidBaseUtils.getAPPVersionCode(ProjectApp.instance()) + "");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Md5Util.MD5Encode(str + str3));
            sb2.append("bfde83c3208f4bfe97a57765ee824e92");
            httpURLConnection.setRequestProperty(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb2.toString()));
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\r\n");
            for (Map.Entry entry2 : map.entrySet()) {
                stringBuffer.append("--");
                stringBuffer.append("******");
                stringBuffer.append("\r\n");
                stringBuffer.append("Content-Disposition: form-data; name=\"" + ((String) entry2.getKey()) + "\"\r\n");
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Content-Type: text/plain; charset=utf-8");
                sb3.append("\r\n");
                stringBuffer.append(sb3.toString());
                stringBuffer.append("Content-Transfer-Encoding: 8bit\r\n");
                stringBuffer.append("\r\n");
                stringBuffer.append((String) entry2.getValue());
                stringBuffer.append("\r\n");
            }
            stringBuffer.append("--");
            stringBuffer.append("******");
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + filename.substring(filename.lastIndexOf("/") + 1) + "\"\r\n");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Content-Type: application/octet-stream; charset=utf-8");
            sb4.append("\r\n");
            stringBuffer.append(sb4.toString());
            stringBuffer.append("\r\n");
            dataOutputStream.writeBytes(stringBuffer.toString());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 100;
            bit.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            while (true) {
                if (byteArrayOutputStream.toByteArray().length / 1024 <= 3000) {
                    break;
                }
                byteArrayOutputStream.reset();
                i2 -= 10;
                if (i2 == 0) {
                    bit.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
                    break;
                }
                bit.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            byte[] bArr = new byte[10240];
            while (true) {
                int i3 = byteArrayInputStream.read(bArr);
                if (i3 == -1) {
                    byteArrayInputStream.close();
                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.writeBytes("--******--\r\n");
                    dataOutputStream.flush();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String line = new BufferedReader(new InputStreamReader(inputStream, "utf-8")).readLine();
                    dataOutputStream.close();
                    inputStream.close();
                    return line;
                }
                dataOutputStream.write(bArr, 0, i3);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void exec(final Bitmap[] bitmaps, final ExecRunnableListenter ExecRunnableListenter2) {
        if (bitmaps == null || ExecRunnableListenter2 == null) {
            return;
        }
        int length = bitmaps.length;
        for (final int i2 = 0; i2 < length; i2++) {
            this.fixedThreadPool.get().execute(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.http.PicUpLoadExecutor.1
                @Override // java.lang.Runnable
                public void run() {
                    ExecRunnableListenter2.onRun(i2);
                }
            });
        }
    }

    public ExecutorService getFixedThreadPool() {
        return this.fixedThreadPool.get();
    }

    public PicUpLoadExecutor withBeforeExecListenter(ExecListenter ExecListenter2) {
        this.ExecListenter = ExecListenter2;
        return this;
    }

    public PicUpLoadExecutor withHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public PicUpLoadExecutor withUpLoadUrl(String url) {
        this.url = url;
        return this;
    }

    public void exec(final Bitmap[] bitmaps) {
        if (bitmaps == null) {
            return;
        }
        final int length = bitmaps.length;
        for (final int i2 = 0; i2 < length; i2++) {
            this.f13700f++;
            this.fixedThreadPool.get().execute(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.http.PicUpLoadExecutor.2
                @Override // java.lang.Runnable
                public void run() throws JSONException, IOException {
                    String strUploadPic = PicUpLoadExecutor.uploadPic(PicUpLoadExecutor.this.url, "" + i2 + ".jpg", bitmaps[i2]);
                    if (strUploadPic != null) {
                        try {
                            JSONObject jSONObject = new JSONObject(strUploadPic);
                            if (jSONObject.optString("code").equals("200")) {
                                String string = jSONObject.optJSONArray("data").getString(0);
                                Message message = new Message();
                                message.what = 801;
                                message.obj = string.toString();
                                message.arg1 = length;
                                PicUpLoadExecutor.this.handler.sendMessage(message);
                            } else {
                                Message message2 = new Message();
                                message2.what = 801;
                                message2.obj = jSONObject.optString("message");
                                message2.arg1 = 10000;
                                PicUpLoadExecutor.this.handler.sendMessage(message2);
                            }
                        } catch (Exception unused) {
                            Message message3 = new Message();
                            message3.what = 801;
                            message3.obj = "null";
                            message3.arg1 = 10001;
                            PicUpLoadExecutor.this.handler.sendMessage(message3);
                        }
                    }
                }
            });
        }
    }

    public PicUpLoadExecutor(short poolSize, ThreadFactory threadFactory) {
        this.fixedThreadPool = null;
        this.fixedThreadPool = new SoftReference<>(Executors.newFixedThreadPool(poolSize, threadFactory));
    }
}
