package com.catchpig.mvvm.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* loaded from: classes2.dex */
public class RequestFileUtil {
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> list, String str) {
        ArrayList arrayList = new ArrayList(list.size());
        for (File file : list) {
            arrayList.add(MultipartBody.Part.createFormData(str, file.getName(), getRequestBodyFile(file)));
        }
        return arrayList;
    }

    public static RequestBody getRequestBody(File file) {
        return RequestBody.INSTANCE.create(file, MediaType.INSTANCE.parse("text/x-markdown; charset=utf-8; charset=utf-8"));
    }

    public static RequestBody getRequestBodyFile(File file) {
        return RequestBody.INSTANCE.create(file, MediaType.INSTANCE.parse("multipart/form-data; charset=utf-8"));
    }

    public static RequestBody getRequestBodyImg(File file) {
        return RequestBody.INSTANCE.create(file, MediaType.INSTANCE.parse("multipart/form-data"));
    }

    public static RequestBody toRequestBody(String str) {
        return RequestBody.INSTANCE.create(str, MediaType.INSTANCE.parse("application/json;charset=utf-8"));
    }

    public static MultipartBody.Part uploadFile(String str, File file) {
        return MultipartBody.Part.createFormData(str, file.getName(), getRequestBody(file));
    }

    public static MultipartBody.Part uploadFileImg(String str, File file) {
        return MultipartBody.Part.createFormData(str, file.getName(), getRequestBodyImg(file));
    }
}
