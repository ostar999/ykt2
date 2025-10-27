package com.plv.business.sub.danmaku.auxiliary;

import java.io.IOException;
import java.util.Locale;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.IDisplayer;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;
import net.polyv.danmaku.danmaku.parser.IDataSource;
import net.polyv.danmaku.danmaku.parser.android.AndroidFileSource;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/* loaded from: classes4.dex */
public class BiliDanmukuParser extends BaseDanmakuParser {
    private float mDispScaleX;
    private float mDispScaleY;

    public class XmlContentHandler extends DefaultHandler {
        private static final String TRUE_STRING = "true";
        private Danmakus result = null;
        private BaseDanmaku item = null;
        private boolean completed = false;
        private int index = 0;

        public XmlContentHandler() {
        }

        private String decodeXmlString(String str) {
            if (str.contains("&amp;")) {
                str = str.replace("&amp;", "&");
            }
            if (str.contains("&quot;")) {
                str = str.replace("&quot;", "\"");
            }
            if (str.contains("&gt;")) {
                str = str.replace("&gt;", ">");
            }
            return str.contains("&lt;") ? str.replace("&lt;", "<") : str;
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x0121  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x012e  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x0139  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0144  */
        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void characters(char[] r32, int r33, int r34) throws java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 533
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.plv.business.sub.danmaku.auxiliary.BiliDanmukuParser.XmlContentHandler.characters(char[], int, int):void");
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endDocument() throws SAXException {
            this.completed = true;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            BaseDanmaku baseDanmaku = this.item;
            if (baseDanmaku != null) {
                if (baseDanmaku.duration != null) {
                    if (str2.length() == 0) {
                        str2 = str3;
                    }
                    if (str2.equalsIgnoreCase("d")) {
                        this.item.setTimer(((BaseDanmakuParser) BiliDanmukuParser.this).mTimer);
                        this.result.addItem(this.item);
                    }
                }
                this.item = null;
            }
        }

        public Danmakus getResult() {
            return this.result;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startDocument() throws SAXException {
            this.result = new Danmakus();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException, NumberFormatException {
            if (str2.length() == 0) {
                str2 = str3;
            }
            if (str2.toLowerCase(Locale.getDefault()).trim().equals("d")) {
                String[] strArrSplit = attributes.getValue("p").split(",");
                if (strArrSplit.length > 0) {
                    long j2 = (long) (Float.parseFloat(strArrSplit[0]) * 1000.0f);
                    int i2 = Integer.parseInt(strArrSplit[1]);
                    float f2 = Float.parseFloat(strArrSplit[2]);
                    int i3 = (int) ((Long.parseLong(strArrSplit[3]) | (-16777216)) & (-1));
                    BaseDanmaku baseDanmakuCreateDanmaku = ((BaseDanmakuParser) BiliDanmukuParser.this).mContext.mDanmakuFactory.createDanmaku(i2, ((BaseDanmakuParser) BiliDanmukuParser.this).mContext);
                    this.item = baseDanmakuCreateDanmaku;
                    if (baseDanmakuCreateDanmaku != null) {
                        baseDanmakuCreateDanmaku.setTime(j2);
                        this.item.textSize = f2 * (((BaseDanmakuParser) BiliDanmukuParser.this).mDispDensity - 0.6f);
                        BaseDanmaku baseDanmaku = this.item;
                        baseDanmaku.textColor = i3;
                        baseDanmaku.textShadowColor = i3 <= -16777216 ? -1 : -16777216;
                    }
                }
            }
        }
    }

    static {
        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPercentageNumber(float f2) {
        return f2 >= 0.0f && f2 <= 1.0f;
    }

    @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
    public BaseDanmakuParser setDisplayer(IDisplayer iDisplayer) {
        super.setDisplayer(iDisplayer);
        this.mDispScaleX = this.mDispWidth / 682.0f;
        this.mDispScaleY = this.mDispHeight / 438.0f;
        return this;
    }

    @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
    public Danmakus parse() throws SAXException, IOException {
        IDataSource<?> iDataSource = this.mDataSource;
        if (iDataSource == null) {
            return null;
        }
        AndroidFileSource androidFileSource = (AndroidFileSource) iDataSource;
        try {
            XMLReader xMLReaderCreateXMLReader = XMLReaderFactory.createXMLReader();
            XmlContentHandler xmlContentHandler = new XmlContentHandler();
            xMLReaderCreateXMLReader.setContentHandler(xmlContentHandler);
            xMLReaderCreateXMLReader.parse(new InputSource(androidFileSource.data()));
            return xmlContentHandler.getResult();
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } catch (SAXException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
