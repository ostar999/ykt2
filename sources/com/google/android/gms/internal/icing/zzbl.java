package com.google.android.gms.internal.icing;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzbl {
    private static zzbm zza(File file) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
                HashMap map = new HashMap();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        String strValueOf = String.valueOf(file);
                        StringBuilder sb = new StringBuilder(strValueOf.length() + 7);
                        sb.append("Parsed ");
                        sb.append(strValueOf);
                        Log.i("HermeticFileOverrides", sb.toString());
                        zzbm zzbmVar = new zzbm(map);
                        bufferedReader.close();
                        return zzbmVar;
                    }
                    String[] strArrSplit = line.split(" ", 3);
                    if (strArrSplit.length != 3) {
                        Log.e("HermeticFileOverrides", line.length() != 0 ? "Invalid: ".concat(line) : new String("Invalid: "));
                    } else {
                        String str = strArrSplit[0];
                        String strDecode = Uri.decode(strArrSplit[1]);
                        String strDecode2 = Uri.decode(strArrSplit[2]);
                        if (!map.containsKey(str)) {
                            map.put(str, new HashMap());
                        }
                        ((Map) map.get(str)).put(strDecode, strDecode2);
                    }
                }
            } finally {
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static zzbx<zzbm> zzd(Context context) {
        String str = Build.TYPE;
        String str2 = Build.TAGS;
        String str3 = Build.HARDWARE;
        boolean z2 = false;
        if ((str.equals("eng") || str.equals("userdebug")) && ((str3.equals("goldfish") || str3.equals("ranchu") || str3.equals("robolectric")) && (str2.contains("dev-keys") || str2.contains("test-keys")))) {
            z2 = true;
        }
        if (!z2) {
            return zzbx.zzw();
        }
        if (zzaz.zzk() && !context.isDeviceProtectedStorage()) {
            context = context.createDeviceProtectedStorageContext();
        }
        zzbx<File> zzbxVarZze = zze(context);
        return zzbxVarZze.isPresent() ? zzbx.zzb(zza(zzbxVarZze.get())) : zzbx.zzw();
    }

    private static zzbx<File> zze(Context context) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            StrictMode.allowThreadDiskWrites();
            File file = new File(context.getDir("phenotype_hermetic", 0), "overrides.txt");
            return file.exists() ? zzbx.zzb(file) : zzbx.zzw();
        } catch (RuntimeException e2) {
            Log.e("HermeticFileOverrides", "no data dir", e2);
            return zzbx.zzw();
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }
}
