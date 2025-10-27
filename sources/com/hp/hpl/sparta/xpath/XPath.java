package com.hp.hpl.sparta.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

/* loaded from: classes4.dex */
public class XPath {
    private static final int ASSERTION = 0;
    private static Hashtable cache_ = new Hashtable();
    private boolean absolute_;
    private Stack steps_;
    private String string_;

    private XPath(String str) throws XPathException {
        this(str, new InputStreamReader(new ByteArrayInputStream(str.getBytes())));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004f A[Catch: IOException -> 0x006d, TryCatch #0 {IOException -> 0x006d, blocks: (B:3:0x000a, B:5:0x002d, B:7:0x0035, B:10:0x003d, B:11:0x0044, B:16:0x004f, B:17:0x0058, B:18:0x0059, B:20:0x005f, B:22:0x0065, B:8:0x003a), top: B:27:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0059 A[Catch: IOException -> 0x006d, TryCatch #0 {IOException -> 0x006d, blocks: (B:3:0x000a, B:5:0x002d, B:7:0x0035, B:10:0x003d, B:11:0x0044, B:16:0x004f, B:17:0x0058, B:18:0x0059, B:20:0x005f, B:22:0x0065, B:8:0x003a), top: B:27:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004b A[EDGE_INSN: B:29:0x004b->B:13:0x004b BREAK  A[LOOP:0: B:11:0x0044->B:22:0x0065], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private XPath(java.lang.String r6, java.io.Reader r7) throws com.hp.hpl.sparta.xpath.XPathException {
        /*
            r5 = this;
            r5.<init>()
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            r5.steps_ = r0
            r5.string_ = r6     // Catch: java.io.IOException -> L6d
            com.hp.hpl.sparta.xpath.SimpleStreamTokenizer r6 = new com.hp.hpl.sparta.xpath.SimpleStreamTokenizer     // Catch: java.io.IOException -> L6d
            r6.<init>(r7)     // Catch: java.io.IOException -> L6d
            r7 = 47
            r6.ordinaryChar(r7)     // Catch: java.io.IOException -> L6d
            r0 = 46
            r6.ordinaryChar(r0)     // Catch: java.io.IOException -> L6d
            r0 = 58
            r6.wordChars(r0, r0)     // Catch: java.io.IOException -> L6d
            r0 = 95
            r6.wordChars(r0, r0)     // Catch: java.io.IOException -> L6d
            int r0 = r6.nextToken()     // Catch: java.io.IOException -> L6d
            r1 = 1
            r2 = 0
            if (r0 != r7) goto L3a
            r5.absolute_ = r1     // Catch: java.io.IOException -> L6d
            int r0 = r6.nextToken()     // Catch: java.io.IOException -> L6d
            if (r0 != r7) goto L3c
            r6.nextToken()     // Catch: java.io.IOException -> L6d
            r0 = r1
            goto L3d
        L3a:
            r5.absolute_ = r2     // Catch: java.io.IOException -> L6d
        L3c:
            r0 = r2
        L3d:
            java.util.Stack r3 = r5.steps_     // Catch: java.io.IOException -> L6d
            com.hp.hpl.sparta.xpath.Step r4 = new com.hp.hpl.sparta.xpath.Step     // Catch: java.io.IOException -> L6d
            r4.<init>(r5, r0, r6)     // Catch: java.io.IOException -> L6d
        L44:
            r3.push(r4)     // Catch: java.io.IOException -> L6d
            int r0 = r6.ttype     // Catch: java.io.IOException -> L6d
            if (r0 == r7) goto L59
            r7 = -1
            if (r0 != r7) goto L4f
            return
        L4f:
            com.hp.hpl.sparta.xpath.XPathException r7 = new com.hp.hpl.sparta.xpath.XPathException     // Catch: java.io.IOException -> L6d
            java.lang.String r0 = "at end of XPATH expression"
            java.lang.String r1 = "end of expression"
            r7.<init>(r5, r0, r6, r1)     // Catch: java.io.IOException -> L6d
            throw r7     // Catch: java.io.IOException -> L6d
        L59:
            int r0 = r6.nextToken()     // Catch: java.io.IOException -> L6d
            if (r0 != r7) goto L64
            r6.nextToken()     // Catch: java.io.IOException -> L6d
            r0 = r1
            goto L65
        L64:
            r0 = r2
        L65:
            java.util.Stack r3 = r5.steps_     // Catch: java.io.IOException -> L6d
            com.hp.hpl.sparta.xpath.Step r4 = new com.hp.hpl.sparta.xpath.Step     // Catch: java.io.IOException -> L6d
            r4.<init>(r5, r0, r6)     // Catch: java.io.IOException -> L6d
            goto L44
        L6d:
            r6 = move-exception
            com.hp.hpl.sparta.xpath.XPathException r7 = new com.hp.hpl.sparta.xpath.XPathException
            r7.<init>(r5, r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hp.hpl.sparta.xpath.XPath.<init>(java.lang.String, java.io.Reader):void");
    }

    private XPath(boolean z2, Step[] stepArr) {
        this.steps_ = new Stack();
        for (Step step : stepArr) {
            this.steps_.addElement(step);
        }
        this.absolute_ = z2;
        this.string_ = null;
    }

    private String generateString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumerationElements = this.steps_.elements();
        boolean z2 = true;
        while (enumerationElements.hasMoreElements()) {
            Step step = (Step) enumerationElements.nextElement();
            if (!z2 || this.absolute_) {
                stringBuffer.append('/');
                if (step.isMultiLevel()) {
                    stringBuffer.append('/');
                }
            }
            stringBuffer.append(step.toString());
            z2 = false;
        }
        return stringBuffer.toString();
    }

    public static XPath get(String str) throws XPathException {
        XPath xPath;
        synchronized (cache_) {
            xPath = (XPath) cache_.get(str);
            if (xPath == null) {
                xPath = new XPath(str);
                cache_.put(str, xPath);
            }
        }
        return xPath;
    }

    public static XPath get(boolean z2, Step[] stepArr) {
        XPath xPath = new XPath(z2, stepArr);
        String string = xPath.toString();
        synchronized (cache_) {
            XPath xPath2 = (XPath) cache_.get(string);
            if (xPath2 != null) {
                return xPath2;
            }
            cache_.put(string, xPath);
            return xPath;
        }
    }

    public static boolean isStringValue(String str) throws XPathException, IOException {
        return get(str).isStringValue();
    }

    public Object clone() {
        int size = this.steps_.size();
        Step[] stepArr = new Step[size];
        Enumeration enumerationElements = this.steps_.elements();
        for (int i2 = 0; i2 < size; i2++) {
            stepArr[i2] = (Step) enumerationElements.nextElement();
        }
        return new XPath(this.absolute_, stepArr);
    }

    public String getIndexingAttrName() throws XPathException {
        BooleanExpr predicate = ((Step) this.steps_.peek()).getPredicate();
        if (predicate instanceof AttrExistsExpr) {
            return ((AttrExistsExpr) predicate).getAttrName();
        }
        throw new XPathException(this, "has no indexing attribute name (must end with predicate of the form [@attrName]");
    }

    public String getIndexingAttrNameOfEquals() throws XPathException {
        BooleanExpr predicate = ((Step) this.steps_.peek()).getPredicate();
        if (predicate instanceof AttrEqualsExpr) {
            return ((AttrEqualsExpr) predicate).getAttrName();
        }
        return null;
    }

    public Enumeration getSteps() {
        return this.steps_.elements();
    }

    public boolean isAbsolute() {
        return this.absolute_;
    }

    public boolean isStringValue() {
        return ((Step) this.steps_.peek()).isStringValue();
    }

    public String toString() {
        if (this.string_ == null) {
            this.string_ = generateString();
        }
        return this.string_;
    }
}
