package net.tsz.afinal.http;

import com.psychiatrygarden.utils.MimeTypes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

/* loaded from: classes9.dex */
public class AjaxParams {
    private static String ENCODING = "UTF-8";
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected ConcurrentHashMap<String, String> urlParams;

    public static class FileWrapper {
        public String contentType;
        public String fileName;
        public InputStream inputStream;

        public FileWrapper(InputStream inputStream, String str, String str2) {
            this.inputStream = inputStream;
            this.fileName = str;
            this.contentType = str2;
        }

        public String getFileName() {
            String str = this.fileName;
            return str != null ? str : "nofilename";
        }
    }

    public AjaxParams() {
        init();
    }

    private void init() {
        this.urlParams = new ConcurrentHashMap<>();
        this.fileParams = new ConcurrentHashMap<>();
    }

    public HttpEntity getEntity() throws IOException {
        UrlEncodedFormEntity urlEncodedFormEntity;
        UnsupportedEncodingException e2;
        if (this.fileParams.isEmpty()) {
            try {
                urlEncodedFormEntity = new UrlEncodedFormEntity(getParamsList(), ENCODING);
                try {
                    urlEncodedFormEntity.setContentType(new BasicHeader("Content-Type", MimeTypes.APP_JSON));
                } catch (UnsupportedEncodingException e3) {
                    e2 = e3;
                    e2.printStackTrace();
                    return urlEncodedFormEntity;
                }
            } catch (UnsupportedEncodingException e4) {
                urlEncodedFormEntity = null;
                e2 = e4;
            }
            return urlEncodedFormEntity;
        }
        MultipartEntity multipartEntity = new MultipartEntity();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            multipartEntity.addPart(entry.getKey(), entry.getValue());
        }
        int size = this.fileParams.entrySet().size() - 1;
        int i2 = 0;
        for (Map.Entry<String, FileWrapper> entry2 : this.fileParams.entrySet()) {
            FileWrapper value = entry2.getValue();
            if (value.inputStream != null) {
                boolean z2 = i2 == size;
                if (value.contentType != null) {
                    multipartEntity.addPart(entry2.getKey(), value.getFileName(), value.inputStream, value.contentType, z2);
                } else {
                    multipartEntity.addPart(entry2.getKey(), value.getFileName(), value.inputStream, z2);
                }
            }
            i2++;
        }
        return multipartEntity;
    }

    public ConcurrentHashMap<String, String> getParam() {
        return this.urlParams;
    }

    public String getParamString() {
        return URLEncodedUtils.format(getParamsList(), ENCODING);
    }

    public List<BasicNameValuePair> getParamsList() {
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            linkedList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return linkedList;
    }

    public void put(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.urlParams.put(str, str2);
    }

    public void remove(String str) {
        this.urlParams.remove(str);
        this.fileParams.remove(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        for (Map.Entry<String, FileWrapper> entry2 : this.fileParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry2.getKey());
            sb.append("=");
            sb.append("FILE");
        }
        return sb.toString();
    }

    public void put(String str, File file) throws FileNotFoundException {
        put(str, new FileInputStream(file), file.getName());
    }

    public AjaxParams(Map<String, String> map) {
        init();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void put(String str, InputStream inputStream) {
        put(str, inputStream, null);
    }

    public void put(String str, InputStream inputStream, String str2) {
        put(str, inputStream, str2, null);
    }

    public void put(String str, InputStream inputStream, String str2, String str3) {
        if (str == null || inputStream == null) {
            return;
        }
        this.fileParams.put(str, new FileWrapper(inputStream, str2, str3));
    }

    public AjaxParams(String str, String str2) {
        init();
        put(str, str2);
    }

    public AjaxParams(Object... objArr) {
        init();
        int length = objArr.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i2 = 0; i2 < length; i2 += 2) {
            put(String.valueOf(objArr[i2]), String.valueOf(objArr[i2 + 1]));
        }
    }
}
