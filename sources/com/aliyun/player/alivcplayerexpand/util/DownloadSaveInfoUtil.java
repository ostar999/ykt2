package com.aliyun.player.alivcplayerexpand.util;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DownloadSaveInfoUtil {
    private String mSaveDir;

    public DownloadSaveInfoUtil(String str) {
        this.mSaveDir = str;
    }

    private static String getInfoFileName(String str) {
        return StrPool.DOT + str + ".info";
    }

    private String readStringFromFile(File file) throws Throwable {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    } catch (IOException unused) {
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        return sb.toString();
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader2.close();
                bufferedReader2.close();
            } catch (IOException unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
        }
        return sb.toString();
    }

    private void writeStringToFile(File file, String str) throws Throwable {
        PrintStream printStream = null;
        try {
            PrintStream printStream2 = new PrintStream(new FileOutputStream(file));
            try {
                printStream2.println(str);
                printStream2.flush();
                printStream2.close();
            } catch (FileNotFoundException unused) {
                printStream = printStream2;
                if (printStream != null) {
                    printStream.close();
                }
            } catch (Throwable th) {
                th = th;
                printStream = printStream2;
                if (printStream != null) {
                    printStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void deleteAllInfo(ArrayList<AliyunDownloadMediaInfo> arrayList) {
        Iterator<AliyunDownloadMediaInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            deleteInfo(it.next(), this.mSaveDir);
        }
    }

    public void deleteInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        deleteInfo(aliyunDownloadMediaInfo, this.mSaveDir);
    }

    public void fillDownloadInfoFromCache(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        String savePath = aliyunDownloadMediaInfo.getSavePath();
        if (TextUtils.isEmpty(savePath)) {
            return;
        }
        List<AliyunDownloadMediaInfo> infosFromJson = AliyunDownloadMediaInfo.getInfosFromJson(readStringFromFile(new File(this.mSaveDir, getInfoFileName(new File(savePath).getName()))));
        if (infosFromJson == null || infosFromJson.isEmpty()) {
            return;
        }
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 : infosFromJson) {
            aliyunDownloadMediaInfo.setDownloadIndex(aliyunDownloadMediaInfo2.getDownloadIndex());
            aliyunDownloadMediaInfo.setProgress(aliyunDownloadMediaInfo2.getProgress());
            AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo2.getStatus();
            if (status == AliyunDownloadMediaInfo.Status.Complete) {
                aliyunDownloadMediaInfo.setStatus(status);
            }
            aliyunDownloadMediaInfo.setSavePath(aliyunDownloadMediaInfo2.getSavePath());
            aliyunDownloadMediaInfo.setSize(Math.max(aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo2.getSize()));
        }
    }

    public List<AliyunDownloadMediaInfo> getAlivcDownloadeds() {
        List<AliyunDownloadMediaInfo> infosFromJson;
        ArrayList arrayList = null;
        if (this.mSaveDir != null) {
            File file = new File(this.mSaveDir);
            if (file.exists() && file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles == null) {
                    return null;
                }
                arrayList = new ArrayList();
                for (File file2 : fileArrListFiles) {
                    if (file2.getAbsolutePath().endsWith(".info") && (infosFromJson = AliyunDownloadMediaInfo.getInfosFromJson(readStringFromFile(file2))) != null && !infosFromJson.isEmpty()) {
                        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : infosFromJson) {
                            AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo.getStatus();
                            AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Complete;
                            if (status == status2) {
                                aliyunDownloadMediaInfo.setStatus(status2);
                            }
                            arrayList.add(aliyunDownloadMediaInfo);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public void writeDownloadingInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) throws Throwable {
        if (TextUtils.isEmpty(aliyunDownloadMediaInfo.getSavePath())) {
            return;
        }
        File file = new File(this.mSaveDir, getInfoFileName(new File(aliyunDownloadMediaInfo.getSavePath()).getName()));
        if (!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException unused) {
            }
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(aliyunDownloadMediaInfo);
        if (file.exists()) {
            writeStringToFile(file, AliyunDownloadMediaInfo.getJsonFromInfos(arrayList));
        }
    }

    public static void deleteInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, String str) {
        if (aliyunDownloadMediaInfo != null) {
            String savePath = aliyunDownloadMediaInfo.getSavePath();
            if (TextUtils.isEmpty(savePath)) {
                return;
            }
            File file = new File(str, getInfoFileName(new File(savePath).getName()));
            if (!file.exists() || file.isDirectory()) {
                return;
            }
            file.delete();
        }
    }
}
