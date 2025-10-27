package com.github.mikephil.charting.utils;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class FileUtils {
    private static final String LOG = "MPChart-FileUtils";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v9 */
    public static List<BarEntry> loadBarEntriesFromAssets(AssetManager assetManager, String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        float f2 = 0;
        bufferedReader = null;
        bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(assetManager.open(str), "UTF-8"));
                    try {
                        for (String line = bufferedReader2.readLine(); line != null; line = bufferedReader2.readLine()) {
                            String[] strArrSplit = line.split(DictionaryFactory.SHARP);
                            f2 = Float.parseFloat(strArrSplit[1]);
                            arrayList.add(new BarEntry(f2, Float.parseFloat(strArrSplit[0])));
                        }
                        bufferedReader2.close();
                        bufferedReader = f2;
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader = bufferedReader2;
                        Log.e(LOG, e.toString());
                        if (bufferedReader != null) {
                            bufferedReader.close();
                            bufferedReader = bufferedReader;
                        }
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                Log.e(LOG, e3.toString());
                            }
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (IOException e5) {
                Log.e(LOG, e5.toString());
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Type inference failed for: r2v10, types: [float[]] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v13, types: [float] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0081 -> B:37:0x0088). Please report as a decompilation issue!!! */
    public static List<Entry> loadEntriesFromAssets(AssetManager assetManager, String str) throws Throwable {
        ?? r2;
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        bufferedReader = null;
        try {
        } catch (IOException e2) {
            Log.e(LOG, e2.toString());
            bufferedReader = bufferedReader;
        }
        try {
            try {
                BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(assetManager.open(str), "UTF-8"));
                try {
                    String line = bufferedReader4.readLine();
                    while (line != null) {
                        String[] strArrSplit = line.split(DictionaryFactory.SHARP);
                        if (strArrSplit.length <= 2) {
                            r2 = Float.parseFloat(strArrSplit[1]);
                            arrayList.add(new Entry(r2, Float.parseFloat(strArrSplit[0])));
                        } else {
                            int length = strArrSplit.length - 1;
                            r2 = new float[length];
                            for (int i2 = 0; i2 < length; i2++) {
                                r2[i2] = Float.parseFloat(strArrSplit[i2]);
                            }
                            arrayList.add(new BarEntry(Integer.parseInt(strArrSplit[strArrSplit.length - 1]), (float[]) r2));
                        }
                        line = bufferedReader4.readLine();
                        bufferedReader2 = r2;
                    }
                    bufferedReader4.close();
                    bufferedReader = bufferedReader2;
                } catch (IOException e3) {
                    e = e3;
                    bufferedReader3 = bufferedReader4;
                    Log.e(LOG, e.toString());
                    bufferedReader = bufferedReader3;
                    if (bufferedReader3 != null) {
                        bufferedReader3.close();
                        bufferedReader = bufferedReader3;
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader4;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4) {
                            Log.e(LOG, e4.toString());
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static List<Entry> loadEntriesFromFile(String str) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), str);
        ArrayList arrayList = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] strArrSplit = line.split(DictionaryFactory.SHARP);
                if (strArrSplit.length <= 2) {
                    arrayList.add(new Entry(Float.parseFloat(strArrSplit[0]), Integer.parseInt(strArrSplit[1])));
                } else {
                    int length = strArrSplit.length - 1;
                    float[] fArr = new float[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        fArr[i2] = Float.parseFloat(strArrSplit[i2]);
                    }
                    arrayList.add(new BarEntry(Integer.parseInt(strArrSplit[strArrSplit.length - 1]), fArr));
                }
            }
        } catch (IOException e2) {
            Log.e(LOG, e2.toString());
        }
        return arrayList;
    }

    public static void saveToSdCard(List<Entry> list, String str) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                Log.e(LOG, e2.toString());
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            for (Entry entry : list) {
                bufferedWriter.append((CharSequence) (entry.getY() + DictionaryFactory.SHARP + entry.getX()));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e3) {
            Log.e(LOG, e3.toString());
        }
    }
}
