package com.xiaomi.push;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes6.dex */
public class fy {

    /* renamed from: a, reason: collision with root package name */
    private XmlPullParser f24912a;

    public fy() throws XmlPullParserException {
        try {
            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
            this.f24912a = xmlPullParserNewPullParser;
            xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        } catch (XmlPullParserException unused) {
        }
    }

    public gt a(byte[] bArr, gc gcVar) throws XmlPullParserException, IOException, gn {
        String name;
        String str;
        this.f24912a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
        this.f24912a.next();
        int eventType = this.f24912a.getEventType();
        String name2 = this.f24912a.getName();
        if (eventType != 2) {
            return null;
        }
        if (name2.equals("message")) {
            return hb.a(this.f24912a);
        }
        if (name2.equals("iq")) {
            return hb.a(this.f24912a, gcVar);
        }
        if (name2.equals("presence")) {
            return hb.m477a(this.f24912a);
        }
        if (this.f24912a.getName().equals("stream")) {
            return null;
        }
        if (this.f24912a.getName().equals("error")) {
            throw new gn(hb.m478a(this.f24912a));
        }
        if (this.f24912a.getName().equals("warning")) {
            this.f24912a.next();
            name = this.f24912a.getName();
            str = "multi-login";
        } else {
            name = this.f24912a.getName();
            str = "bind";
        }
        name.equals(str);
        return null;
    }
}
