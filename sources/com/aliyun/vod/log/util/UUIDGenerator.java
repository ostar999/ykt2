package com.aliyun.vod.log.util;

import android.os.Environment;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/* loaded from: classes2.dex */
public class UUIDGenerator {
    public static final File ALIVC_DATA_FILE = new File(Environment.getExternalStorageDirectory(), "AlivcData");
    public static final String DATA_DIRECTORY = "AlivcData";
    private static final int MAX_WRITE_COUNT = 10;
    private static final String UUID_FILE = "alivc_data.txt";
    private static final String UUID_PROP = "UUID";
    private static String sDeviceUUID;
    private static int sWriteUUIDCount;

    public static /* synthetic */ int access$008() {
        int i2 = sWriteUUIDCount;
        sWriteUUIDCount = i2 + 1;
        return i2;
    }

    public static final String generateRequestID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static synchronized String generateUUID() {
        if (!TextUtils.isEmpty(sDeviceUUID)) {
            return sDeviceUUID;
        }
        File file = ALIVC_DATA_FILE;
        File file2 = new File(file, UUID_FILE);
        try {
            if (file.exists() || file.mkdir()) {
                Properties properties = new Properties();
                FileReader fileReader = new FileReader(file2);
                properties.load(fileReader);
                fileReader.close();
                sDeviceUUID = properties.getProperty(UUID_PROP);
            }
        } catch (Throwable unused) {
        }
        if (TextUtils.isEmpty(sDeviceUUID)) {
            sWriteUUIDCount = 0;
            String strReplace = UUID.randomUUID().toString().replace("-", "");
            sDeviceUUID = strReplace;
            writeUUIDToFile(file2, strReplace);
        }
        return sDeviceUUID;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeUUIDToFile(final File file, final String str) {
        if (file == null || TextUtils.isEmpty(str)) {
            return;
        }
        new Timer().schedule(new TimerTask() { // from class: com.aliyun.vod.log.util.UUIDGenerator.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                boolean z2 = false;
                try {
                    boolean z3 = file.exists() || file.createNewFile();
                    Properties properties = new Properties();
                    properties.setProperty(UUIDGenerator.UUID_PROP, str);
                    if (z3) {
                        FileWriter fileWriter = new FileWriter(file);
                        properties.store(fileWriter, (String) null);
                        fileWriter.close();
                        z2 = true;
                    }
                } catch (Throwable unused) {
                }
                UUIDGenerator.access$008();
                if (z2 || UUIDGenerator.sWriteUUIDCount >= 10) {
                    return;
                }
                UUIDGenerator.writeUUIDToFile(file, str);
            }
        }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }
}
