package cn.hutool.core.net.multipart;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.multi.ListValueMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class MultipartFormData {
    private boolean loaded;
    private final ListValueMap<String, UploadFile> requestFiles;
    private final ListValueMap<String, String> requestParameters;
    private final UploadSetting setting;

    public MultipartFormData() {
        this(null);
    }

    private void putFile(String str, UploadFile uploadFile) {
        this.requestFiles.lambda$null$0(str, uploadFile);
    }

    private void putParameter(String str, String str2) {
        this.requestParameters.lambda$null$0(str, str2);
    }

    private void setLoaded() throws IOException {
        if (this.loaded) {
            throw new IOException("Multi-part request already parsed.");
        }
        this.loaded = true;
    }

    public String[] getArrayParam(String str) {
        List<String> listParam = getListParam(str);
        if (listParam != null) {
            return (String[]) listParam.toArray(new String[0]);
        }
        return null;
    }

    public UploadFile getFile(String str) {
        UploadFile[] files = getFiles(str);
        if (files == null || files.length <= 0) {
            return null;
        }
        return files[0];
    }

    public List<UploadFile> getFileList(String str) {
        return (List) this.requestFiles.get(str);
    }

    public ListValueMap<String, UploadFile> getFileListValueMap() {
        return this.requestFiles;
    }

    public Map<String, UploadFile[]> getFileMap() {
        return Convert.toMap(String.class, UploadFile[].class, getFileListValueMap());
    }

    public Set<String> getFileParamNames() {
        return this.requestFiles.keySet();
    }

    public UploadFile[] getFiles(String str) {
        List<UploadFile> fileList = getFileList(str);
        if (fileList != null) {
            return (UploadFile[]) fileList.toArray(new UploadFile[0]);
        }
        return null;
    }

    public List<String> getListParam(String str) {
        return (List) this.requestParameters.get(str);
    }

    public String getParam(String str) {
        List<String> listParam = getListParam(str);
        if (CollUtil.isNotEmpty((Collection<?>) listParam)) {
            return listParam.get(0);
        }
        return null;
    }

    public ListValueMap<String, String> getParamListMap() {
        return this.requestParameters;
    }

    public Map<String, String[]> getParamMap() {
        return Convert.toMap(String.class, String[].class, getParamListMap());
    }

    public Set<String> getParamNames() {
        return this.requestParameters.keySet();
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void parseRequestStream(InputStream inputStream, Charset charset) throws IOException {
        setLoaded();
        MultipartRequestInputStream multipartRequestInputStream = new MultipartRequestInputStream(inputStream);
        multipartRequestInputStream.readBoundary();
        while (true) {
            UploadFileHeader dataHeader = multipartRequestInputStream.readDataHeader(charset);
            if (dataHeader == null) {
                return;
            }
            if (dataHeader.isFile) {
                if (dataHeader.fileName.length() > 0 && dataHeader.contentType.contains("application/x-macbinary")) {
                    multipartRequestInputStream.skipBytes(128L);
                }
                UploadFile uploadFile = new UploadFile(dataHeader, this.setting);
                if (uploadFile.processStream(multipartRequestInputStream)) {
                    putFile(dataHeader.formFieldName, uploadFile);
                }
            } else {
                putParameter(dataHeader.formFieldName, multipartRequestInputStream.readString(charset));
            }
            multipartRequestInputStream.skipBytes(1L);
            multipartRequestInputStream.mark(1);
            int i2 = multipartRequestInputStream.read();
            if (i2 == -1 || i2 == 45) {
                break;
            } else {
                multipartRequestInputStream.reset();
            }
        }
        multipartRequestInputStream.reset();
    }

    public MultipartFormData(UploadSetting uploadSetting) {
        this.requestParameters = new ListValueMap<>();
        this.requestFiles = new ListValueMap<>();
        this.setting = uploadSetting == null ? new UploadSetting() : uploadSetting;
    }
}
