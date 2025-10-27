package com.arialyy.aria.core.config;

import android.text.TextUtils;
import com.arialyy.aria.util.CommonUtil;
import k.a;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes2.dex */
public class XMLReader extends DefaultHandler {
    private int mType;
    private final String TAG = CommonUtil.getClassName(this);
    private DownloadConfig mDownloadConfig = Configuration.getInstance().downloadCfg;
    private UploadConfig mUploadConfig = Configuration.getInstance().uploadCfg;
    private AppConfig mAppConfig = Configuration.getInstance().appCfg;
    private DGroupConfig mDGroupConfig = Configuration.getInstance().dGroupCfg;

    private boolean checkBoolean(String str) {
        return !TextUtils.isEmpty(str) && (str.equalsIgnoreCase(a.f27523u) || str.equalsIgnoreCase(a.f27524v));
    }

    private boolean checkInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return Integer.parseInt(str) >= 0;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private boolean checkLong(String str) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private void setField(String str, Object obj, int i2) throws IllegalAccessException, IllegalArgumentException {
        if (i2 == 1) {
            setField(DownloadConfig.class, this.mDownloadConfig, str, obj);
            return;
        }
        if (i2 == 2) {
            setField(UploadConfig.class, this.mUploadConfig, str, obj);
        } else if (i2 == 3) {
            setField(AppConfig.class, this.mAppConfig, str, obj);
        } else if (i2 == 4) {
            setField(DGroupConfig.class, this.mDGroupConfig, str, obj);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i2, int i3) throws SAXException {
        super.characters(cArr, i2, i3);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() throws Throwable {
        super.endDocument();
        this.mDownloadConfig.save();
        this.mUploadConfig.save();
        this.mAppConfig.save();
        this.mDGroupConfig.save();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        super.endElement(str, str2, str3);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0016  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0171  */
    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void startElement(java.lang.String r19, java.lang.String r20, java.lang.String r21, org.xml.sax.Attributes r22) throws java.lang.IllegalAccessException, org.xml.sax.SAXException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 1248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.core.config.XMLReader.startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes):void");
    }

    private void setField(Class cls, Object obj, String str, Object obj2) throws IllegalAccessException, IllegalArgumentException {
        try {
            CommonUtil.getField(cls, str).set(obj, obj2);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }
}
