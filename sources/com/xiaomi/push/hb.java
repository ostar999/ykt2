package com.xiaomi.push;

import android.text.TextUtils;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.tencent.connect.common.Constants;
import com.xiaomi.push.gr;
import com.xiaomi.push.gv;
import com.xiaomi.push.gx;
import com.xiaomi.push.service.at;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes6.dex */
public class hb {

    /* renamed from: a, reason: collision with root package name */
    private static XmlPullParser f25048a;

    public static gq a(String str, String str2, XmlPullParser xmlPullParser) {
        Object objM475a = ha.a().m475a("all", "xm:chat");
        if (objM475a == null || !(objM475a instanceof com.xiaomi.push.service.e)) {
            return null;
        }
        return ((com.xiaomi.push.service.e) objM475a).b(xmlPullParser);
    }

    public static gr a(XmlPullParser xmlPullParser, gc gcVar) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue("", "id");
        String attributeValue2 = xmlPullParser.getAttributeValue("", "to");
        String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
        String attributeValue4 = xmlPullParser.getAttributeValue("", "chid");
        gr.a aVarA = gr.a.a(xmlPullParser.getAttributeValue("", "type"));
        HashMap map = new HashMap();
        boolean z2 = false;
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            map.put(attributeName, xmlPullParser.getAttributeValue("", attributeName));
        }
        gr hdVar = null;
        gx gxVarM479a = null;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("error")) {
                    gxVarM479a = m479a(xmlPullParser);
                } else {
                    hdVar = new gr();
                    hdVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("iq")) {
                z2 = true;
            }
        }
        if (hdVar == null) {
            if (gr.a.f24950a == aVarA || gr.a.f24951b == aVarA) {
                hc hcVar = new hc();
                hcVar.k(attributeValue);
                hcVar.m(attributeValue3);
                hcVar.n(attributeValue2);
                hcVar.a(gr.a.f24953d);
                hcVar.l(attributeValue4);
                hcVar.a(new gx(gx.a.f25000e));
                gcVar.a(hcVar);
                com.xiaomi.channel.commonutils.logger.b.d("iq usage error. send packet in packet parser.");
                return null;
            }
            hdVar = new hd();
        }
        hdVar.k(attributeValue);
        hdVar.m(attributeValue2);
        hdVar.l(attributeValue4);
        hdVar.n(attributeValue3);
        hdVar.a(aVarA);
        hdVar.a(gxVarM479a);
        hdVar.a(map);
        return hdVar;
    }

    public static gt a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, gn {
        String attributeValue;
        boolean z2 = false;
        String strNextText = null;
        if ("1".equals(xmlPullParser.getAttributeValue("", "s"))) {
            String attributeValue2 = xmlPullParser.getAttributeValue("", "chid");
            String attributeValue3 = xmlPullParser.getAttributeValue("", "id");
            String attributeValue4 = xmlPullParser.getAttributeValue("", "from");
            String attributeValue5 = xmlPullParser.getAttributeValue("", "to");
            String attributeValue6 = xmlPullParser.getAttributeValue("", "type");
            at.b bVarA = com.xiaomi.push.service.at.a().a(attributeValue2, attributeValue5);
            if (bVarA == null) {
                bVarA = com.xiaomi.push.service.at.a().a(attributeValue2, attributeValue4);
            }
            if (bVarA == null) {
                throw new gn("the channel id is wrong while receiving a encrypted message");
            }
            gt gtVarA = null;
            while (!z2) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    if (!"s".equals(xmlPullParser.getName())) {
                        throw new gn("error while receiving a encrypted message with wrong format");
                    }
                    if (xmlPullParser.next() != 4) {
                        throw new gn("error while receiving a encrypted message with wrong format");
                    }
                    String text = xmlPullParser.getText();
                    if ("5".equals(attributeValue2) || Constants.VIA_SHARE_TYPE_INFO.equals(attributeValue2)) {
                        gs gsVar = new gs();
                        gsVar.l(attributeValue2);
                        gsVar.b(true);
                        gsVar.n(attributeValue4);
                        gsVar.m(attributeValue5);
                        gsVar.k(attributeValue3);
                        gsVar.f(attributeValue6);
                        gq gqVar = new gq("s", null, null, null);
                        gqVar.m466a(text);
                        gsVar.a(gqVar);
                        return gsVar;
                    }
                    a(com.xiaomi.push.service.bc.a(com.xiaomi.push.service.bc.a(bVarA.f25598h, attributeValue3), text));
                    f25048a.next();
                    gtVarA = a(f25048a);
                } else if (next == 3 && xmlPullParser.getName().equals("message")) {
                    z2 = true;
                }
            }
            if (gtVarA != null) {
                return gtVarA;
            }
            throw new gn("error while receiving a encrypted message with wrong format");
        }
        gs gsVar2 = new gs();
        String attributeValue7 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue7 == null) {
            attributeValue7 = "ID_NOT_AVAILABLE";
        }
        gsVar2.k(attributeValue7);
        gsVar2.m(xmlPullParser.getAttributeValue("", "to"));
        gsVar2.n(xmlPullParser.getAttributeValue("", "from"));
        gsVar2.l(xmlPullParser.getAttributeValue("", "chid"));
        gsVar2.a(xmlPullParser.getAttributeValue("", "appid"));
        try {
            attributeValue = xmlPullParser.getAttributeValue("", "transient");
        } catch (Exception unused) {
            attributeValue = null;
        }
        try {
            String attributeValue8 = xmlPullParser.getAttributeValue("", "seq");
            if (!TextUtils.isEmpty(attributeValue8)) {
                gsVar2.b(attributeValue8);
            }
        } catch (Exception unused2) {
        }
        try {
            String attributeValue9 = xmlPullParser.getAttributeValue("", "mseq");
            if (!TextUtils.isEmpty(attributeValue9)) {
                gsVar2.c(attributeValue9);
            }
        } catch (Exception unused3) {
        }
        try {
            String attributeValue10 = xmlPullParser.getAttributeValue("", "fseq");
            if (!TextUtils.isEmpty(attributeValue10)) {
                gsVar2.d(attributeValue10);
            }
        } catch (Exception unused4) {
        }
        try {
            String attributeValue11 = xmlPullParser.getAttributeValue("", "status");
            if (!TextUtils.isEmpty(attributeValue11)) {
                gsVar2.e(attributeValue11);
            }
        } catch (Exception unused5) {
        }
        gsVar2.a(!TextUtils.isEmpty(attributeValue) && attributeValue.equalsIgnoreCase(k.a.f27523u));
        gsVar2.f(xmlPullParser.getAttributeValue("", "type"));
        String strB = b(xmlPullParser);
        if (strB == null || "".equals(strB.trim())) {
            gt.q();
        } else {
            gsVar2.j(strB);
        }
        while (!z2) {
            int next2 = xmlPullParser.next();
            if (next2 == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (TextUtils.isEmpty(namespace)) {
                    namespace = "xm";
                }
                if (name.equals("subject")) {
                    b(xmlPullParser);
                    gsVar2.g(m480a(xmlPullParser));
                } else if (name.equals(TtmlNode.TAG_BODY)) {
                    String attributeValue12 = xmlPullParser.getAttributeValue("", "encode");
                    String strM480a = m480a(xmlPullParser);
                    if (TextUtils.isEmpty(attributeValue12)) {
                        gsVar2.h(strM480a);
                    } else {
                        gsVar2.a(strM480a, attributeValue12);
                    }
                } else if (name.equals("thread")) {
                    if (strNextText == null) {
                        strNextText = xmlPullParser.nextText();
                    }
                } else if (name.equals("error")) {
                    gsVar2.a(m479a(xmlPullParser));
                } else {
                    gsVar2.a(a(name, namespace, xmlPullParser));
                }
            } else if (next2 == 3 && xmlPullParser.getName().equals("message")) {
                z2 = true;
            }
        }
        gsVar2.i(strNextText);
        return gsVar2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static gv m477a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        gv.b bVarValueOf = gv.b.available;
        String attributeValue = xmlPullParser.getAttributeValue("", "type");
        if (attributeValue != null && !attributeValue.equals("")) {
            try {
                bVarValueOf = gv.b.valueOf(attributeValue);
            } catch (IllegalArgumentException unused) {
                System.err.println("Found invalid presence type " + attributeValue);
            }
        }
        gv gvVar = new gv(bVarValueOf);
        gvVar.m(xmlPullParser.getAttributeValue("", "to"));
        gvVar.n(xmlPullParser.getAttributeValue("", "from"));
        gvVar.l(xmlPullParser.getAttributeValue("", "chid"));
        String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue2 == null) {
            attributeValue2 = "ID_NOT_AVAILABLE";
        }
        gvVar.k(attributeValue2);
        boolean z2 = false;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("status")) {
                    gvVar.a(xmlPullParser.nextText());
                } else if (name.equals(RemoteMessageConst.Notification.PRIORITY)) {
                    try {
                        gvVar.a(Integer.parseInt(xmlPullParser.nextText()));
                    } catch (NumberFormatException unused2) {
                    } catch (IllegalArgumentException unused3) {
                        gvVar.a(0);
                    }
                } else if (name.equals("show")) {
                    String strNextText = xmlPullParser.nextText();
                    try {
                        gvVar.a(gv.a.valueOf(strNextText));
                    } catch (IllegalArgumentException unused4) {
                        System.err.println("Found invalid presence mode " + strNextText);
                    }
                } else if (name.equals("error")) {
                    gvVar.a(m479a(xmlPullParser));
                } else {
                    gvVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("presence")) {
                z2 = true;
            }
        }
        return gvVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static gw m478a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        gw gwVar = null;
        boolean z2 = false;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                gwVar = new gw(xmlPullParser.getName());
            } else if (next == 3 && xmlPullParser.getName().equals("error")) {
                z2 = true;
            }
        }
        return gwVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static gx m479a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        String attributeValue = "-1";
        boolean z2 = false;
        String attributeValue2 = null;
        String attributeValue3 = null;
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            if (xmlPullParser.getAttributeName(i2).equals("code")) {
                attributeValue = xmlPullParser.getAttributeValue("", "code");
            }
            if (xmlPullParser.getAttributeName(i2).equals("type")) {
                attributeValue3 = xmlPullParser.getAttributeValue("", "type");
            }
            if (xmlPullParser.getAttributeName(i2).equals("reason")) {
                attributeValue2 = xmlPullParser.getAttributeValue("", "reason");
            }
        }
        String str = null;
        String strNextText = null;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (xmlPullParser.getName().equals("text")) {
                    strNextText = xmlPullParser.nextText();
                } else {
                    String name = xmlPullParser.getName();
                    String namespace = xmlPullParser.getNamespace();
                    if ("urn:ietf:params:xml:ns:xmpp-stanzas".equals(namespace)) {
                        str = name;
                    } else {
                        arrayList.add(a(name, namespace, xmlPullParser));
                    }
                }
            } else if (next == 3) {
                if (xmlPullParser.getName().equals("error")) {
                    z2 = true;
                }
            } else if (next == 4) {
                strNextText = xmlPullParser.getText();
            }
        }
        return new gx(Integer.parseInt(attributeValue), attributeValue3 == null ? "cancel" : attributeValue3, attributeValue2, str, strNextText, arrayList);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static String m480a(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        String str = "";
        while (true) {
            if (xmlPullParser.next() == 3 && xmlPullParser.getDepth() == depth) {
                return str;
            }
            str = str + xmlPullParser.getText();
        }
    }

    private static void a(byte[] bArr) throws XmlPullParserException {
        if (f25048a == null) {
            try {
                XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                f25048a = xmlPullParserNewPullParser;
                xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            } catch (XmlPullParserException e2) {
                e2.printStackTrace();
            }
        }
        f25048a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
    }

    private static String b(XmlPullParser xmlPullParser) {
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            if ("xml:lang".equals(attributeName) || ("lang".equals(attributeName) && AliyunVodHttpCommon.Format.FORMAT_XML.equals(xmlPullParser.getAttributePrefix(i2)))) {
                return xmlPullParser.getAttributeValue(i2);
            }
        }
        return null;
    }
}
