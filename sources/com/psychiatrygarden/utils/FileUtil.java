package com.psychiatrygarden.utils;

import android.content.Context;
import android.os.Environment;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class FileUtil {
    private static final String FILENAME = "data.txt";
    public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    public static void clearFile(Context context) {
        context.deleteFile(FILENAME);
    }

    public static void copy(Context context, String fileName, String filePath) throws IOException {
        try {
            InputStream inputStreamOpen = context.getResources().getAssets().open(fileName);
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + fileName);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStreamOpen.read(bArr);
                if (i2 <= 0) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStreamOpen.close();
                    return;
                }
                fileOutputStream.write(bArr, 0, i2);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static File createDir(String dirName) {
        File file = new File(SDPATH + dirName + File.separator);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static boolean exists(Context context, String fileName) {
        return new File(context.getFilesDir(), fileName).exists();
    }

    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isExistDir(String dirName) {
        return new File(SDPATH + dirName).exists();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.util.List] */
    public static List<QuestionDetailBean> loadListFromFile(Context context) throws ClassNotFoundException, IOException {
        ArrayList arrayList = new ArrayList();
        try {
            FileInputStream fileInputStreamOpenFileInput = context.openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStreamOpenFileInput);
            Object object = objectInputStream.readObject();
            if (object instanceof List) {
                arrayList = (List) object;
            }
            objectInputStream.close();
            fileInputStreamOpenFileInput.close();
        } catch (IOException | ClassNotFoundException e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.FileInputStream] */
    public static String readFile(Context context, String str) {
        FileInputStream fileInputStreamOpenFileInput;
        String str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        ?? r12 = 0;
        try {
            try {
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (!exists(context, str)) {
                return null;
            }
            try {
                fileInputStreamOpenFileInput = context.openFileInput(str);
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        byte[] bArr = new byte[1024];
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        while (true) {
                            int i2 = fileInputStreamOpenFileInput.read(bArr);
                            if (i2 == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, i2);
                        }
                        fileInputStreamOpenFileInput.close();
                        byteArrayOutputStream.close();
                        str2 = new String(byteArrayOutputStream.toByteArray());
                    } catch (FileNotFoundException e3) {
                        e = e3;
                        e.printStackTrace();
                        if (fileInputStreamOpenFileInput != null) {
                            fileInputStreamOpenFileInput.close();
                        }
                        return str2;
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                        if (fileInputStreamOpenFileInput != null) {
                            fileInputStreamOpenFileInput.close();
                        }
                        return str2;
                    }
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                fileInputStreamOpenFileInput = null;
            } catch (IOException e6) {
                e = e6;
                fileInputStreamOpenFileInput = null;
            } catch (Throwable th) {
                th = th;
                if (r12 != 0) {
                    try {
                        r12.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
            if (fileInputStreamOpenFileInput != null) {
                fileInputStreamOpenFileInput.close();
            }
            return str2;
        } catch (Throwable th2) {
            th = th2;
            r12 = context;
        }
    }

    public static void saveListToFile(Context context, List<QuestionDetailBean> objectList) throws IOException {
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(FILENAME, 0);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStreamOpenFileOutput);
            objectOutputStream.writeObject(objectList);
            objectOutputStream.close();
            fileOutputStreamOpenFileOutput.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x002e -> B:32:0x0031). Please report as a decompilation issue!!! */
    public static boolean writeFile(Context context, String fileName, String content) throws IOException {
        boolean z2 = false;
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            try {
                fileOutputStreamOpenFileOutput = context.openFileOutput(fileName, 0);
                fileOutputStreamOpenFileOutput.write(content.getBytes());
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                z2 = true;
            } catch (FileNotFoundException e4) {
                e4.printStackTrace();
                if (fileOutputStreamOpenFileOutput != null) {
                    fileOutputStreamOpenFileOutput.close();
                }
            } catch (IOException e5) {
                e5.printStackTrace();
                if (fileOutputStreamOpenFileOutput != null) {
                    fileOutputStreamOpenFileOutput.close();
                }
            }
            return z2;
        } catch (Throwable th) {
            if (fileOutputStreamOpenFileOutput != null) {
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }
}
