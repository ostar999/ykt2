package com.aliyun.vod.jasonparse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class JSONSupportImpl extends JSONSupport {
    private final Gson mGson = new Gson();

    public static String fileToStr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                return byteArrayOutputStream.toString();
            }
            byteArrayOutputStream.write(bArr, 0, i2);
        }
    }

    private byte[] getByteFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[16384];
        while (true) {
            try {
                int i2 = inputStream.read(bArr, 0, 16384);
                if (i2 == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        byteArrayOutputStream.flush();
        return bArr;
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> T readListValue(String str, Type type) throws Exception {
        return (T) this.mGson.fromJson(str, type);
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> T readValue(InputStream inputStream, Class<? extends T> cls) throws Exception {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        jsonReader.setLenient(true);
        T t2 = (T) this.mGson.fromJson(jsonReader, cls);
        jsonReader.close();
        return t2;
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> void writeValue(OutputStream outputStream, T t2) throws Exception {
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        this.mGson.toJson(t2, t2.getClass(), jsonWriter);
        jsonWriter.flush();
        jsonWriter.close();
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> T readValue(File file, Class<? extends T> cls) throws Exception {
        return (T) readValue(new FileInputStream(file), cls);
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> void writeValue(File file, T t2) throws Exception {
        writeValue((OutputStream) new FileOutputStream(file), (FileOutputStream) t2);
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> T readValue(String str, Class<? extends T> cls) throws Exception {
        return (T) this.mGson.fromJson(str, (Class) cls);
    }

    @Override // com.aliyun.vod.jasonparse.JSONSupport
    public <T> String writeValue(T t2) throws Exception {
        return this.mGson.toJson(t2);
    }
}
