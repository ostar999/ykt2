package com.psychiatrygarden.activity.purchase.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class FormFile {
    private String contentType;
    private byte[] data;
    private File file;
    private int fileSize;
    private String filname;
    private InputStream inStream;
    private String parameterName;

    public FormFile(String filname, byte[] data, String parameterName, String contentType) {
        this.contentType = "application/octet-stream";
        this.data = data;
        this.filname = filname;
        this.parameterName = parameterName;
        if (contentType != null) {
            this.contentType = contentType;
        }
    }

    public String getContentType() {
        return this.contentType;
    }

    public byte[] getData() {
        return this.data;
    }

    public File getFile() {
        return this.file;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public String getFilname() {
        return this.filname;
    }

    public InputStream getInStream() {
        return this.inStream;
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFilname(String filname) {
        this.filname = filname;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public FormFile(String filname, File file, String parameterName, String contentType) {
        this.contentType = "application/octet-stream";
        this.filname = filname;
        this.parameterName = parameterName;
        this.file = file;
        try {
            this.inStream = new FileInputStream(file);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        if (contentType != null) {
            this.contentType = contentType;
        }
    }

    public FormFile(InputStream inStream, int fileSize, String filname, String parameterName, String contentType) {
        this.inStream = inStream;
        this.fileSize = fileSize;
        this.filname = filname;
        this.parameterName = parameterName;
        this.contentType = contentType;
    }
}
