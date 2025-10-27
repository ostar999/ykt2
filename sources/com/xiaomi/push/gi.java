package com.xiaomi.push;

import com.github.mikephil.charting.BuildConfig;
import com.vivo.push.PushClientConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes6.dex */
public final class gi {

    /* renamed from: a, reason: collision with root package name */
    private static int f24930a = 5000;

    /* renamed from: a, reason: collision with other field name */
    private static Vector<String> f489a = new Vector<>();

    /* renamed from: b, reason: collision with root package name */
    private static int f24931b = 330000;

    /* renamed from: c, reason: collision with root package name */
    private static int f24932c = 600000;

    /* renamed from: d, reason: collision with root package name */
    private static int f24933d = 330000;

    static {
        try {
            for (ClassLoader classLoader : m461a()) {
                Enumeration<URL> resources = classLoader.getResources("META-INF/smack-config.xml");
                while (resources.hasMoreElements()) {
                    InputStream inputStreamOpenStream = null;
                    try {
                        try {
                            inputStreamOpenStream = resources.nextElement().openStream();
                            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                            xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                            xmlPullParserNewPullParser.setInput(inputStreamOpenStream, "UTF-8");
                            int eventType = xmlPullParserNewPullParser.getEventType();
                            do {
                                if (eventType == 2) {
                                    if (xmlPullParserNewPullParser.getName().equals(PushClientConstants.TAG_CLASS_NAME)) {
                                        a(xmlPullParserNewPullParser);
                                    } else if (xmlPullParserNewPullParser.getName().equals("packetReplyTimeout")) {
                                        f24930a = a(xmlPullParserNewPullParser, f24930a);
                                    } else if (xmlPullParserNewPullParser.getName().equals("keepAliveInterval")) {
                                        f24931b = a(xmlPullParserNewPullParser, f24931b);
                                    } else if (xmlPullParserNewPullParser.getName().equals("mechName")) {
                                        f489a.add(xmlPullParserNewPullParser.nextText());
                                    }
                                }
                                eventType = xmlPullParserNewPullParser.next();
                            } while (eventType != 1);
                        } catch (Throwable th) {
                            try {
                                inputStreamOpenStream.close();
                            } catch (Exception unused) {
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        inputStreamOpenStream.close();
                    } catch (Exception unused2) {
                    }
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private gi() {
    }

    public static int a() {
        return f24931b;
    }

    private static int a(XmlPullParser xmlPullParser, int i2) {
        try {
            return Integer.parseInt(xmlPullParser.nextText());
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return i2;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m460a() {
        return BuildConfig.VERSION_NAME;
    }

    private static void a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, ClassNotFoundException {
        String strNextText = xmlPullParser.nextText();
        try {
            Class.forName(strNextText);
        } catch (ClassNotFoundException unused) {
            System.err.println("Error! A startup class specified in smack-config.xml could not be loaded: " + strNextText);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static ClassLoader[] m461a() {
        ClassLoader[] classLoaderArr = {gi.class.getClassLoader(), Thread.currentThread().getContextClassLoader()};
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 2; i2++) {
            ClassLoader classLoader = classLoaderArr[i2];
            if (classLoader != null) {
                arrayList.add(classLoader);
            }
        }
        return (ClassLoader[]) arrayList.toArray(new ClassLoader[arrayList.size()]);
    }

    public static int b() {
        return f24932c;
    }
}
