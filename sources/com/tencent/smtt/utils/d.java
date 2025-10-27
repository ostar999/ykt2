package com.tencent.smtt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.smtt.sdk.WebView;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: b, reason: collision with root package name */
    private static DexClassLoader f21463b;

    /* renamed from: c, reason: collision with root package name */
    private static Looper f21464c;

    /* renamed from: d, reason: collision with root package name */
    private static d f21465d;

    /* renamed from: a, reason: collision with root package name */
    public String f21466a;

    public interface a {
        void a();

        void a(int i2);

        void a(Throwable th);
    }

    private d(Context context) {
        this.f21466a = "";
        this.f21466a = context.getDir("debugtbs", 0).getAbsolutePath() + File.separator + "plugin";
    }

    public static d a(Context context) {
        if (f21465d == null) {
            f21465d = new d(context);
        }
        return f21465d;
    }

    @SuppressLint({"NewApi"})
    public static void a(final String str, final a aVar) {
        new Thread() { // from class: com.tencent.smtt.utils.d.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                FileOutputStream fileOutputStream;
                InputStream inputStream = null;
                fileOutputStreamD = null;
                FileOutputStream fileOutputStreamD = null;
                inputStream = null;
                try {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://tbs.imtt.qq.com/plugin/DebugPlugin_v2.tbs").openConnection();
                        int contentLength = httpURLConnection.getContentLength();
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.connect();
                        InputStream inputStream2 = httpURLConnection.getInputStream();
                        try {
                            fileOutputStreamD = FileUtil.d(new File(str));
                            byte[] bArr = new byte[8192];
                            int i2 = 0;
                            while (true) {
                                int i3 = inputStream2.read(bArr);
                                if (i3 <= 0) {
                                    break;
                                }
                                i2 += i3;
                                fileOutputStreamD.write(bArr, 0, i3);
                                aVar.a((i2 * 100) / contentLength);
                            }
                            aVar.a();
                            try {
                                inputStream2.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            fileOutputStreamD.close();
                        } catch (Exception e3) {
                            e = e3;
                            fileOutputStream = fileOutputStreamD;
                            inputStream = inputStream2;
                            try {
                                e.printStackTrace();
                                aVar.a(e);
                                try {
                                    inputStream.close();
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                                fileOutputStream.close();
                            } catch (Throwable th) {
                                th = th;
                                try {
                                    inputStream.close();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                    throw th;
                                } catch (Exception e6) {
                                    e6.printStackTrace();
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStreamD;
                            inputStream = inputStream2;
                            inputStream.close();
                            fileOutputStream.close();
                            throw th;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        fileOutputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = null;
                    }
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            }
        }.start();
    }

    public void a(final String str, final WebView webView, final Context context) {
        final RelativeLayout relativeLayout = new RelativeLayout(context);
        final TextView textView = new TextView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        textView.setText("加载中，请稍后...");
        relativeLayout.addView(textView, layoutParams);
        webView.addView(relativeLayout, new FrameLayout.LayoutParams(-1, -1));
        String str2 = this.f21466a + File.separator + "DebugPlugin.tbs";
        FileUtil.b(new File(str2));
        a(str2, new a() { // from class: com.tencent.smtt.utils.d.1
            @Override // com.tencent.smtt.utils.d.a
            public void a() {
                webView.post(new Runnable() { // from class: com.tencent.smtt.utils.d.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(context, "下载成功", 0).show();
                        relativeLayout.setVisibility(4);
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        d.this.a(str, webView, context, d.f21464c);
                    }
                });
            }

            @Override // com.tencent.smtt.utils.d.a
            public void a(final int i2) {
                webView.post(new Runnable() { // from class: com.tencent.smtt.utils.d.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        textView.setText("已下载" + i2 + "%");
                    }
                });
            }

            @Override // com.tencent.smtt.utils.d.a
            public void a(Throwable th) {
                webView.post(new Runnable() { // from class: com.tencent.smtt.utils.d.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(context, "下载失败，请检查网络", 0).show();
                    }
                });
            }
        });
    }

    @SuppressLint({"NewApi"})
    public void a(String str, WebView webView, Context context, Looper looper) {
        TbsLog.i("debugtbs", "showPluginView -- url: " + str + "; webview: " + webView + "; context: " + context);
        StringBuilder sb = new StringBuilder();
        sb.append(this.f21466a);
        String str2 = File.separator;
        sb.append(str2);
        sb.append("DebugPlugin.tbs");
        String string = sb.toString();
        String str3 = this.f21466a + str2 + "DebugPlugin.apk";
        File file = new File(string);
        File file2 = new File(str3);
        f21464c = looper;
        if (file.exists()) {
            file2.delete();
            file.renameTo(file2);
        }
        if (!file2.exists()) {
            TbsLog.i("debugtbs", "showPluginView - going to download plugin...");
            a(str, webView, context);
            return;
        }
        try {
            String strA = b.a(context, true, new File(str3));
            if (!"308202fb308201e3a0030201020204375ef026300d06092a864886f70d01010b0500302e310b3009060355040613023836311f301d0603550403131674656e63656e7462726f7773696e6773657276696365301e170d3232303531383038333632345a170d3437303531323038333632345a302e310b3009060355040613023836311f301d0603550403131674656e63656e7462726f7773696e677365727669636530820122300d06092a864886f70d01010105000382010f003082010a0282010100c85799a78f706304be13eba6b9d824cf61f3bc15bf0536fb7272c0d93dd0e6574249cf13e0120e843a5b3d25c2ca899d175e0689633f4aac7de14011073fd35266d342e7da9a9f7ecd20872a1183556d5ef9a4b0a53f59cec0d877ea9472b974d1a4902d0031d3b6ace2a06a4fc3475cf8c5b759e9b5c1c6c252b6698a940971c81ff25e6e16e998f102128649db8465786dacd1adbadba0e1673099596eefa51ba245106d6e4121d83ef1540e546dbbabda80a1763094bc12abe5b667c7619ba194043c204ccd60a4b23ed9283cbb19d69f5a662b55f855d11048f9c91834b4849e711ae486c3337ae7cfa2dde3cb0e70c5e1ef30db86e3a9ec02ee3be90b690203010001a321301f301d0603551d0e041604148c544df62af09c37c889982a833dd664abc7ab63300d06092a864886f70d01010b050003820101003d67c21bcbc4d5ba11727a85157df542d35de5f0ead38aa3ee65017298ed5e9692f71993a44e7ece954da1314450324e6b93f0cd927b1ff836d1ff237c13e4a226a4d1d66d0f73681ee90b0a71606726799c4cb350d4c97e38076d27039762b5b117eaabc2fb2f8adaa0ca5be5b336dfbc47e3390803fadf3d62f208f5c5b213f113a77b59bf9a0706390e0963e11f9fa622b4091f7189ffa56042ba3d21b5299ecdc533eee59471169b20927288c5331e13c526918487664443cb7e6a40d6d7df3a17386c323ebf3b4066f73aee5c7381f52f0388c48b5a6e45bc6a524ca9f028a7428dc72388857a3acd56e6cf8458463cdb53b1547a272f2613970763ec12".equals(strA)) {
                TbsLog.e("debugtbs", "verifyPlugin apk: " + str3 + " signature failed - sig: " + strA);
                Toast.makeText(context, "插件校验失败，请重试", 0).show();
                file.delete();
                file2.delete();
                return;
            }
            String str4 = this.f21466a + str2 + "opt";
            File file3 = new File(str4);
            if (!file3.exists()) {
                file3.mkdirs();
            }
            if (f21463b == null) {
                f21463b = new DexClassLoader(str3, str4, null, context.getClassLoader());
            }
            HashMap map = new HashMap();
            map.put("url", str);
            map.put("tbs_version", "" + WebView.getTbsSDKVersion(context));
            map.put("tbs_core_version", "" + WebView.getTbsCoreVersion(context));
            if (f21464c != null) {
                map.put("looper", looper);
            }
            Object objNewInstance = f21463b.loadClass("com.tencent.tbs.debug.plugin.DebugView").getConstructor(Context.class, Map.class).newInstance(context, map);
            if (!(objNewInstance instanceof FrameLayout)) {
                TbsLog.e("debugtbs", "get debugview failure: " + objNewInstance);
                return;
            }
            FrameLayout frameLayout = (FrameLayout) objNewInstance;
            webView.addView(frameLayout, new FrameLayout.LayoutParams(-1, -1));
            TbsLog.i("debugtbs", "show " + frameLayout + " successful in " + webView);
        } catch (Exception e2) {
            FileUtil.b(file2);
            e2.printStackTrace();
        }
    }
}
