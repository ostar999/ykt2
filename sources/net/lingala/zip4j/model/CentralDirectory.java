package net.lingala.zip4j.model;

import java.util.ArrayList;

/* loaded from: classes9.dex */
public class CentralDirectory {
    private DigitalSignature digitalSignature;
    private ArrayList fileHeaders;

    public DigitalSignature getDigitalSignature() {
        return this.digitalSignature;
    }

    public ArrayList getFileHeaders() {
        return this.fileHeaders;
    }

    public void setDigitalSignature(DigitalSignature digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public void setFileHeaders(ArrayList arrayList) {
        this.fileHeaders = arrayList;
    }
}
