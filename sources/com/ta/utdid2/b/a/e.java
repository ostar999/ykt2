package com.ta.utdid2.b.a;

import android.util.Xml;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes6.dex */
class e {
    public static final void a(Map map, OutputStream outputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        a aVar = new a();
        aVar.setOutput(outputStream, "utf-8");
        aVar.startDocument(null, Boolean.TRUE);
        aVar.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        a(map, (String) null, (XmlSerializer) aVar);
        aVar.endDocument();
    }

    private static Object b(XmlPullParser xmlPullParser, String[] strArr) throws XmlPullParserException, IOException, NumberFormatException {
        int next;
        Object objValueOf = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        String name = xmlPullParser.getName();
        if (!name.equals("null")) {
            if (name.equals(TypedValues.Custom.S_STRING)) {
                String str = "";
                while (true) {
                    int next2 = xmlPullParser.next();
                    if (next2 == 1) {
                        throw new XmlPullParserException("Unexpected end of document in <string>");
                    }
                    if (next2 == 3) {
                        if (xmlPullParser.getName().equals(TypedValues.Custom.S_STRING)) {
                            strArr[0] = attributeValue;
                            return str;
                        }
                        throw new XmlPullParserException("Unexpected end tag in <string>: " + xmlPullParser.getName());
                    }
                    if (next2 == 4) {
                        str = str + xmlPullParser.getText();
                    } else if (next2 == 2) {
                        throw new XmlPullParserException("Unexpected start tag in <string>: " + xmlPullParser.getName());
                    }
                }
            } else if (name.equals("int")) {
                objValueOf = Integer.valueOf(Integer.parseInt(xmlPullParser.getAttributeValue(null, "value")));
            } else if (name.equals("long")) {
                objValueOf = Long.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            } else if (name.equals(TypedValues.Custom.S_FLOAT)) {
                objValueOf = Float.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            } else if (name.equals("double")) {
                objValueOf = Double.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            } else {
                if (!name.equals(TypedValues.Custom.S_BOOLEAN)) {
                    if (name.equals("int-array")) {
                        xmlPullParser.next();
                        int[] iArrM115a = m115a(xmlPullParser, "int-array", strArr);
                        strArr[0] = attributeValue;
                        return iArrM115a;
                    }
                    if (name.equals("map")) {
                        xmlPullParser.next();
                        HashMap mapM114a = m114a(xmlPullParser, "map", strArr);
                        strArr[0] = attributeValue;
                        return mapM114a;
                    }
                    if (name.equals("list")) {
                        xmlPullParser.next();
                        ArrayList arrayListA = a(xmlPullParser, "list", strArr);
                        strArr[0] = attributeValue;
                        return arrayListA;
                    }
                    throw new XmlPullParserException("Unknown tag: " + name);
                }
                objValueOf = Boolean.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            }
        }
        do {
            next = xmlPullParser.next();
            if (next == 1) {
                throw new XmlPullParserException("Unexpected end of document in <" + name + ">");
            }
            if (next == 3) {
                if (xmlPullParser.getName().equals(name)) {
                    strArr[0] = attributeValue;
                    return objValueOf;
                }
                throw new XmlPullParserException("Unexpected end tag in <" + name + ">: " + xmlPullParser.getName());
            }
            if (next == 4) {
                throw new XmlPullParserException("Unexpected text in <" + name + ">: " + xmlPullParser.getName());
            }
        } while (next != 2);
        throw new XmlPullParserException("Unexpected start tag in <" + name + ">: " + xmlPullParser.getName());
    }

    public static final void a(Map map, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        if (map == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "map");
        if (str != null) {
            xmlSerializer.attribute(null, "name", str);
        }
        for (Map.Entry entry : map.entrySet()) {
            a(entry.getValue(), (String) entry.getKey(), xmlSerializer);
        }
        xmlSerializer.endTag(null, "map");
    }

    public static final void a(List list, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        if (list == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "list");
        if (str != null) {
            xmlSerializer.attribute(null, "name", str);
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            a(list.get(i2), (String) null, xmlSerializer);
        }
        xmlSerializer.endTag(null, "list");
    }

    public static final void a(byte[] bArr, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        if (bArr == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "byte-array");
        if (str != null) {
            xmlSerializer.attribute(null, "name", str);
        }
        xmlSerializer.attribute(null, "num", Integer.toString(bArr.length));
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b3 : bArr) {
            int i2 = b3 >> 4;
            sb.append(i2 >= 10 ? (i2 + 97) - 10 : i2 + 48);
            int i3 = b3 & 255;
            sb.append(i3 >= 10 ? (i3 + 97) - 10 : i3 + 48);
        }
        xmlSerializer.text(sb.toString());
        xmlSerializer.endTag(null, "byte-array");
    }

    public static final void a(int[] iArr, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        if (iArr == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "int-array");
        if (str != null) {
            xmlSerializer.attribute(null, "name", str);
        }
        xmlSerializer.attribute(null, "num", Integer.toString(iArr.length));
        for (int i2 : iArr) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Integer.toString(i2));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "int-array");
    }

    public static final void a(Object obj, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        String str2;
        if (obj == null) {
            xmlSerializer.startTag(null, "null");
            if (str != null) {
                xmlSerializer.attribute(null, "name", str);
            }
            xmlSerializer.endTag(null, "null");
            return;
        }
        if (obj instanceof String) {
            xmlSerializer.startTag(null, TypedValues.Custom.S_STRING);
            if (str != null) {
                xmlSerializer.attribute(null, "name", str);
            }
            xmlSerializer.text(obj.toString());
            xmlSerializer.endTag(null, TypedValues.Custom.S_STRING);
            return;
        }
        if (obj instanceof Integer) {
            str2 = "int";
        } else if (obj instanceof Long) {
            str2 = "long";
        } else if (obj instanceof Float) {
            str2 = TypedValues.Custom.S_FLOAT;
        } else if (obj instanceof Double) {
            str2 = "double";
        } else {
            if (!(obj instanceof Boolean)) {
                if (obj instanceof byte[]) {
                    a((byte[]) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof int[]) {
                    a((int[]) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof Map) {
                    a((Map) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof List) {
                    a((List) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof CharSequence) {
                    xmlSerializer.startTag(null, TypedValues.Custom.S_STRING);
                    if (str != null) {
                        xmlSerializer.attribute(null, "name", str);
                    }
                    xmlSerializer.text(obj.toString());
                    xmlSerializer.endTag(null, TypedValues.Custom.S_STRING);
                    return;
                }
                throw new RuntimeException("writeValueXml: unable to write value " + obj);
            }
            str2 = TypedValues.Custom.S_BOOLEAN;
        }
        xmlSerializer.startTag(null, str2);
        if (str != null) {
            xmlSerializer.attribute(null, "name", str);
        }
        xmlSerializer.attribute(null, "value", obj.toString());
        xmlSerializer.endTag(null, str2);
    }

    public static final HashMap a(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, null);
        return (HashMap) a(xmlPullParserNewPullParser, new String[1]);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static final HashMap m114a(XmlPullParser xmlPullParser, String str, String[] strArr) throws XmlPullParserException, IOException, NumberFormatException {
        HashMap map = new HashMap();
        int eventType = xmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                Object objB = b(xmlPullParser, strArr);
                String str2 = strArr[0];
                if (str2 != null) {
                    map.put(str2, objB);
                } else {
                    throw new XmlPullParserException("Map value without name attribute: " + xmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return map;
                }
                throw new XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName());
            }
            eventType = xmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final ArrayList a(XmlPullParser xmlPullParser, String str, String[] strArr) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        int eventType = xmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                arrayList.add(b(xmlPullParser, strArr));
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return arrayList;
                }
                throw new XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName());
            }
            eventType = xmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException("Document ended before " + str + " end tag");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static final int[] m115a(XmlPullParser xmlPullParser, String str, String[] strArr) throws XmlPullParserException, IOException {
        try {
            int[] iArr = new int[Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"))];
            int eventType = xmlPullParser.getEventType();
            int i2 = 0;
            do {
                if (eventType == 2) {
                    if (xmlPullParser.getName().equals("item")) {
                        try {
                            iArr[i2] = Integer.parseInt(xmlPullParser.getAttributeValue(null, "value"));
                        } catch (NullPointerException unused) {
                            throw new XmlPullParserException("Need value attribute in item");
                        } catch (NumberFormatException unused2) {
                            throw new XmlPullParserException("Not a number in value attribute in item");
                        }
                    } else {
                        throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                    }
                } else if (eventType == 3) {
                    if (xmlPullParser.getName().equals(str)) {
                        return iArr;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName());
                    }
                    i2++;
                }
                eventType = xmlPullParser.next();
            } while (eventType != 1);
            throw new XmlPullParserException("Document ended before " + str + " end tag");
        } catch (NullPointerException unused3) {
            throw new XmlPullParserException("Need num attribute in byte-array");
        } catch (NumberFormatException unused4) {
            throw new XmlPullParserException("Not a number in num attribute in byte-array");
        }
    }

    public static final Object a(XmlPullParser xmlPullParser, String[] strArr) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        while (eventType != 2) {
            if (eventType == 3) {
                throw new XmlPullParserException("Unexpected end tag at: " + xmlPullParser.getName());
            }
            if (eventType != 4) {
                try {
                    eventType = xmlPullParser.next();
                    if (eventType == 1) {
                        throw new XmlPullParserException("Unexpected end of document");
                    }
                } catch (Exception unused) {
                    throw new XmlPullParserException("Unexpected call next(): " + xmlPullParser.getName());
                }
            } else {
                throw new XmlPullParserException("Unexpected text: " + xmlPullParser.getText());
            }
        }
        return b(xmlPullParser, strArr);
    }
}
