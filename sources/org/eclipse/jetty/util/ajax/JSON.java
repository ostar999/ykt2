package org.eclipse.jetty.util.ajax;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import k.a;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class JSON {
    private Map<String, Convertor> _convertors = new ConcurrentHashMap();
    private int _stringBufferSize = 1024;
    static final Logger LOG = Log.getLogger((Class<?>) JSON.class);
    public static final JSON DEFAULT = new JSON();

    public final class ConvertableOutput implements Output {
        private final Appendable _buffer;

        /* renamed from: c, reason: collision with root package name */
        char f27951c;

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(Object obj) throws IOException {
            if (this.f27951c == 0) {
                throw new IllegalStateException();
            }
            JSON.this.append(this._buffer, obj);
            this.f27951c = (char) 0;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void addClass(Class cls) throws IOException {
            try {
                char c3 = this.f27951c;
                if (c3 == 0) {
                    throw new IllegalStateException();
                }
                this._buffer.append(c3);
                this._buffer.append("\"class\":");
                JSON.this.append(this._buffer, cls.getName());
                this.f27951c = ',';
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }

        public void complete() throws IOException {
            try {
                char c3 = this.f27951c;
                if (c3 == '{') {
                    this._buffer.append(StrPool.EMPTY_JSON);
                } else if (c3 != 0) {
                    this._buffer.append("}");
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }

        private ConvertableOutput(Appendable appendable) {
            this.f27951c = '{';
            this._buffer = appendable;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, Object obj) throws IOException {
            try {
                char c3 = this.f27951c;
                if (c3 != 0) {
                    this._buffer.append(c3);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.append(this._buffer, obj);
                    this.f27951c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, double d3) throws IOException {
            try {
                char c3 = this.f27951c;
                if (c3 != 0) {
                    this._buffer.append(c3);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendNumber(this._buffer, Double.valueOf(d3));
                    this.f27951c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, long j2) throws IOException {
            try {
                char c3 = this.f27951c;
                if (c3 != 0) {
                    this._buffer.append(c3);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendNumber(this._buffer, Long.valueOf(j2));
                    this.f27951c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, boolean z2) throws IOException {
            try {
                char c3 = this.f27951c;
                if (c3 != 0) {
                    this._buffer.append(c3);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendBoolean(this._buffer, z2 ? Boolean.TRUE : Boolean.FALSE);
                    this.f27951c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public interface Convertible {
        void fromJSON(Map map);

        void toJSON(Output output);
    }

    public interface Convertor {
        Object fromJSON(Map map);

        void toJSON(Object obj, Output output);
    }

    public interface Generator {
        void addJSON(Appendable appendable);
    }

    public static class Literal implements Generator {
        private String _json;

        public Literal(String str) {
            if (JSON.LOG.isDebugEnabled()) {
                JSON.parse(str);
            }
            this._json = str;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Generator
        public void addJSON(Appendable appendable) throws IOException {
            try {
                appendable.append(this._json);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }

        public String toString() {
            return this._json;
        }
    }

    public interface Output {
        void add(Object obj);

        void add(String str, double d3);

        void add(String str, long j2);

        void add(String str, Object obj);

        void add(String str, boolean z2);

        void addClass(Class cls);
    }

    public static class ReaderSource implements Source {
        private int _next = -1;
        private Reader _reader;
        private char[] scratch;

        public ReaderSource(Reader reader) {
            this._reader = reader;
        }

        private void getNext() {
            if (this._next < 0) {
                try {
                    this._next = this._reader.read();
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public boolean hasNext() {
            getNext();
            if (this._next >= 0) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char next() {
            getNext();
            char c3 = (char) this._next;
            this._next = -1;
            return c3;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char peek() {
            getNext();
            return (char) this._next;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[1024];
            }
            return this.scratch;
        }

        public void setReader(Reader reader) {
            this._reader = reader;
            this._next = -1;
        }
    }

    public interface Source {
        boolean hasNext();

        char next();

        char peek();

        char[] scratchBuffer();
    }

    public static class StringSource implements Source {
        private int index;
        private char[] scratch;
        private final String string;

        public StringSource(String str) {
            this.string = str;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public boolean hasNext() {
            if (this.index < this.string.length()) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char next() {
            String str = this.string;
            int i2 = this.index;
            this.index = i2 + 1;
            return str.charAt(i2);
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char peek() {
            return this.string.charAt(this.index);
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[this.string.length()];
            }
            return this.scratch;
        }

        public String toString() {
            return this.string.substring(0, this.index) + "|||" + this.string.substring(this.index);
        }
    }

    public static void complete(String str, Source source) {
        int i2 = 0;
        while (source.hasNext() && i2 < str.length()) {
            char next = source.next();
            int i3 = i2 + 1;
            if (next != str.charAt(i2)) {
                throw new IllegalStateException("Unexpected '" + next + " while seeking  \"" + str + "\"");
            }
            i2 = i3;
        }
        if (i2 >= str.length()) {
            return;
        }
        throw new IllegalStateException("Expected \"" + str + "\"");
    }

    public static JSON getDefault() {
        return DEFAULT;
    }

    public static Object parse(String str) {
        return DEFAULT.parse((Source) new StringSource(str), false);
    }

    public static void registerConvertor(Class cls, Convertor convertor) {
        DEFAULT.addConvertor(cls, convertor);
    }

    @Deprecated
    public static void setDefault(JSON json) {
    }

    public static String toString(Object obj) throws IOException {
        JSON json = DEFAULT;
        StringBuilder sb = new StringBuilder(json.getStringBufferSize());
        json.append(sb, obj);
        return sb.toString();
    }

    public void addConvertor(Class cls, Convertor convertor) {
        this._convertors.put(cls.getName(), convertor);
    }

    public void addConvertorFor(String str, Convertor convertor) {
        this._convertors.put(str, convertor);
    }

    @Deprecated
    public void append(StringBuffer stringBuffer, Object obj) throws IOException {
        append((Appendable) stringBuffer, obj);
    }

    @Deprecated
    public void appendArray(StringBuffer stringBuffer, Collection collection) throws IOException {
        appendArray((Appendable) stringBuffer, collection);
    }

    @Deprecated
    public void appendBoolean(StringBuffer stringBuffer, Boolean bool) throws IOException {
        appendBoolean((Appendable) stringBuffer, bool);
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Convertor convertor, Object obj) throws IOException {
        appendJSON((Appendable) stringBuffer, convertor, obj);
    }

    @Deprecated
    public void appendMap(StringBuffer stringBuffer, Map<?, ?> map) throws IOException {
        appendMap((Appendable) stringBuffer, map);
    }

    @Deprecated
    public void appendNull(StringBuffer stringBuffer) throws IOException {
        appendNull((Appendable) stringBuffer);
    }

    @Deprecated
    public void appendNumber(StringBuffer stringBuffer, Number number) throws IOException {
        appendNumber((Appendable) stringBuffer, number);
    }

    @Deprecated
    public void appendString(StringBuffer stringBuffer, String str) throws IOException {
        appendString((Appendable) stringBuffer, str);
    }

    public JSON contextFor(String str) {
        return this;
    }

    public JSON contextForArray() {
        return this;
    }

    public Object convertTo(Class cls, Map map) {
        if (cls == null || !Convertible.class.isAssignableFrom(cls)) {
            Convertor convertor = getConvertor(cls);
            return convertor != null ? convertor.fromJSON(map) : map;
        }
        try {
            Convertible convertible = (Convertible) cls.newInstance();
            convertible.fromJSON(map);
            return convertible;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public Object fromJSON(String str) {
        return parse(new StringSource(str));
    }

    public Convertor getConvertor(Class cls) {
        JSON json;
        Convertor convertor = this._convertors.get(cls.getName());
        if (convertor == null && this != (json = DEFAULT)) {
            convertor = json.getConvertor(cls);
        }
        while (convertor == null && cls != Object.class) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i2 = 0; convertor == null && interfaces != null && i2 < interfaces.length; i2++) {
                convertor = this._convertors.get(interfaces[i2].getName());
            }
            if (convertor == null) {
                cls = cls.getSuperclass();
                convertor = this._convertors.get(cls.getName());
            }
        }
        return convertor;
    }

    public Convertor getConvertorFor(String str) {
        JSON json;
        Convertor convertor = this._convertors.get(str);
        return (convertor != null || this == (json = DEFAULT)) ? convertor : json.getConvertorFor(str);
    }

    public int getStringBufferSize() {
        return this._stringBufferSize;
    }

    public Object handleUnknown(Source source, char c3) {
        throw new IllegalStateException("unknown char '" + c3 + "'(" + ((int) c3) + ") in " + source);
    }

    public Object[] newArray(int i2) {
        return new Object[i2];
    }

    public Map<String, Object> newMap() {
        return new HashMap();
    }

    public Object parseArray(Source source) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (source.next() != '[') {
            throw new IllegalStateException();
        }
        Object obj = null;
        ArrayList arrayList = null;
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            while (source.hasNext()) {
                char cPeek = source.peek();
                if (cPeek != ',') {
                    if (cPeek == ']') {
                        source.next();
                        if (i2 == 0) {
                            return newArray(0);
                        }
                        if (i2 != 1) {
                            return arrayList.toArray(newArray(arrayList.size()));
                        }
                        Object[] objArrNewArray = newArray(1);
                        Array.set(objArrNewArray, 0, obj);
                        return objArrNewArray;
                    }
                    if (Character.isWhitespace(cPeek)) {
                        source.next();
                    } else {
                        int i3 = i2 + 1;
                        if (i2 == 0) {
                            obj = contextForArray().parse(source);
                        } else {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                arrayList.add(obj);
                                arrayList.add(contextForArray().parse(source));
                            } else {
                                arrayList.add(contextForArray().parse(source));
                            }
                            obj = null;
                        }
                        i2 = i3;
                        z2 = false;
                    }
                } else {
                    if (z2) {
                        throw new IllegalStateException();
                    }
                    source.next();
                }
            }
            throw new IllegalStateException("unexpected end of array");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0065 A[LOOP:1: B:29:0x0065->B:39:0x007d, LOOP_START] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Number parseNumber(org.eclipse.jetty.util.ajax.JSON.Source r12) {
        /*
            r11 = this;
            r0 = 0
            r1 = 0
            r3 = r1
        L4:
            boolean r5 = r12.hasNext()
            r6 = 46
            r7 = 101(0x65, float:1.42E-43)
            r8 = 69
            r9 = 43
            r10 = 45
            if (r5 == 0) goto L58
            char r5 = r12.peek()
            if (r5 == r9) goto L47
            if (r5 == r8) goto L31
            if (r5 == r7) goto L31
            if (r5 == r10) goto L47
            if (r5 == r6) goto L31
            switch(r5) {
                case 48: goto L26;
                case 49: goto L26;
                case 50: goto L26;
                case 51: goto L26;
                case 52: goto L26;
                case 53: goto L26;
                case 54: goto L26;
                case 55: goto L26;
                case 56: goto L26;
                case 57: goto L26;
                default: goto L25;
            }
        L25:
            goto L58
        L26:
            r6 = 10
            long r3 = r3 * r6
            int r5 = r5 + (-48)
            long r5 = (long) r5
            long r3 = r3 + r5
            r12.next()
            goto L4
        L31:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = 16
            r1.<init>(r2)
            if (r0 == 0) goto L3d
            r1.append(r10)
        L3d:
            r1.append(r3)
            r1.append(r5)
            r12.next()
            goto L59
        L47:
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 != 0) goto L50
            r12.next()
            r0 = 1
            goto L4
        L50:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "bad number"
            r12.<init>(r0)
            throw r12
        L58:
            r1 = 0
        L59:
            if (r1 != 0) goto L65
            if (r0 == 0) goto L60
            r0 = -1
            long r3 = r3 * r0
        L60:
            java.lang.Long r12 = java.lang.Long.valueOf(r3)
            return r12
        L65:
            boolean r0 = r12.hasNext()
            if (r0 == 0) goto L84
            char r0 = r12.peek()
            if (r0 == r9) goto L7d
            if (r0 == r8) goto L7d
            if (r0 == r7) goto L7d
            if (r0 == r10) goto L7d
            if (r0 == r6) goto L7d
            switch(r0) {
                case 48: goto L7d;
                case 49: goto L7d;
                case 50: goto L7d;
                case 51: goto L7d;
                case 52: goto L7d;
                case 53: goto L7d;
                case 54: goto L7d;
                case 55: goto L7d;
                case 56: goto L7d;
                case 57: goto L7d;
                default: goto L7c;
            }
        L7c:
            goto L84
        L7d:
            r1.append(r0)
            r12.next()
            goto L65
        L84:
            java.lang.Double r12 = new java.lang.Double
            java.lang.String r0 = r1.toString()
            r12.<init>(r0)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parseNumber(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.Number");
    }

    public Object parseObject(Source source) {
        if (source.next() != '{') {
            throw new IllegalStateException();
        }
        Map<String, Object> mapNewMap = newMap();
        char cSeekTo = seekTo("\"}", source);
        while (true) {
            if (!source.hasNext()) {
                break;
            }
            if (cSeekTo == '}') {
                source.next();
                break;
            }
            String string = parseString(source);
            seekTo(':', source);
            source.next();
            mapNewMap.put(string, contextFor(string).parse(source));
            seekTo(",}", source);
            if (source.next() == '}') {
                break;
            }
            cSeekTo = seekTo("\"}", source);
        }
        String str = (String) mapNewMap.get("x-class");
        if (str != null) {
            Convertor convertorFor = getConvertorFor(str);
            if (convertorFor != null) {
                return convertorFor.fromJSON(mapNewMap);
            }
            LOG.warn("No Convertor for x-class '{}'", str);
        }
        String str2 = (String) mapNewMap.get("class");
        if (str2 != null) {
            try {
                return convertTo(Loader.loadClass(JSON.class, str2), mapNewMap);
            } catch (ClassNotFoundException unused) {
                LOG.warn("No Class for '{}'", str2);
            }
        }
        return mapNewMap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d9, code lost:
    
        if (r3 != null) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00df, code lost:
    
        return toString(r1, 0, r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String parseString(org.eclipse.jetty.util.ajax.JSON.Source r21) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parseString(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.String");
    }

    public void seekTo(char c3, Source source) {
        while (source.hasNext()) {
            char cPeek = source.peek();
            if (cPeek == c3) {
                return;
            }
            if (!Character.isWhitespace(cPeek)) {
                throw new IllegalStateException("Unexpected '" + cPeek + " while seeking '" + c3 + "'");
            }
            source.next();
        }
        throw new IllegalStateException("Expected '" + c3 + "'");
    }

    public void setStringBufferSize(int i2) {
        this._stringBufferSize = i2;
    }

    public String toJSON(Object obj) throws IOException {
        StringBuilder sb = new StringBuilder(getStringBufferSize());
        append(sb, obj);
        return sb.toString();
    }

    public static Object parse(String str, boolean z2) {
        return DEFAULT.parse(new StringSource(str), z2);
    }

    public void append(Appendable appendable, Object obj) throws IOException {
        try {
            if (obj == null) {
                appendable.append("null");
            } else if (obj instanceof Map) {
                appendMap(appendable, (Map<?, ?>) obj);
            } else if (obj instanceof String) {
                appendString(appendable, (String) obj);
            } else if (obj instanceof Number) {
                appendNumber(appendable, (Number) obj);
            } else if (obj instanceof Boolean) {
                appendBoolean(appendable, (Boolean) obj);
            } else if (obj.getClass().isArray()) {
                appendArray(appendable, obj);
            } else if (obj instanceof Character) {
                appendString(appendable, obj.toString());
            } else if (obj instanceof Convertible) {
                appendJSON(appendable, (Convertible) obj);
            } else if (obj instanceof Generator) {
                appendJSON(appendable, (Generator) obj);
            } else {
                Convertor convertor = getConvertor(obj.getClass());
                if (convertor != null) {
                    appendJSON(appendable, convertor, obj);
                } else if (obj instanceof Collection) {
                    appendArray(appendable, (Collection) obj);
                } else {
                    appendString(appendable, obj.toString());
                }
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendArray(Appendable appendable, Collection collection) throws IOException {
        try {
            if (collection == null) {
                appendNull(appendable);
                return;
            }
            appendable.append('[');
            Iterator it = collection.iterator();
            boolean z2 = true;
            while (it.hasNext()) {
                if (!z2) {
                    appendable.append(',');
                }
                append(appendable, it.next());
                z2 = false;
            }
            appendable.append(']');
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendBoolean(Appendable appendable, Boolean bool) throws IOException {
        try {
            if (bool == null) {
                appendNull(appendable);
            } else {
                appendable.append(bool.booleanValue() ? a.f27523u : a.f27524v);
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendJSON(Appendable appendable, final Convertor convertor, final Object obj) throws IOException {
        appendJSON(appendable, new Convertible() { // from class: org.eclipse.jetty.util.ajax.JSON.1
            @Override // org.eclipse.jetty.util.ajax.JSON.Convertible
            public void fromJSON(Map map) {
            }

            @Override // org.eclipse.jetty.util.ajax.JSON.Convertible
            public void toJSON(Output output) {
                convertor.toJSON(obj, output);
            }
        });
    }

    public void appendMap(Appendable appendable, Map<?, ?> map) throws IOException {
        try {
            if (map == null) {
                appendNull(appendable);
                return;
            }
            appendable.append('{');
            Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> next = it.next();
                QuotedStringTokenizer.quote(appendable, next.getKey().toString());
                appendable.append(':');
                append(appendable, next.getValue());
                if (it.hasNext()) {
                    appendable.append(',');
                }
            }
            appendable.append('}');
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendNull(Appendable appendable) throws IOException {
        try {
            appendable.append("null");
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendNumber(Appendable appendable, Number number) throws IOException {
        try {
            if (number == null) {
                appendNull(appendable);
            } else {
                appendable.append(String.valueOf(number));
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendString(Appendable appendable, String str) throws IOException {
        if (str == null) {
            appendNull(appendable);
        } else {
            QuotedStringTokenizer.quote(appendable, str);
        }
    }

    public static Object parse(Reader reader) throws IOException {
        return DEFAULT.parse((Source) new ReaderSource(reader), false);
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Convertible convertible) throws IOException {
        appendJSON((Appendable) stringBuffer, convertible);
    }

    public static Object parse(Reader reader, boolean z2) throws IOException {
        return DEFAULT.parse(new ReaderSource(reader), z2);
    }

    public static String toString(Map map) throws IOException {
        JSON json = DEFAULT;
        StringBuilder sb = new StringBuilder(json.getStringBufferSize());
        json.appendMap(sb, (Map<?, ?>) map);
        return sb.toString();
    }

    public void appendJSON(Appendable appendable, Convertible convertible) throws IOException {
        ConvertableOutput convertableOutput = new ConvertableOutput(appendable);
        convertible.toJSON(convertableOutput);
        convertableOutput.complete();
    }

    @Deprecated
    public static Object parse(InputStream inputStream) throws IOException {
        return DEFAULT.parse((Source) new StringSource(IO.toString(inputStream)), false);
    }

    @Deprecated
    public static Object parse(InputStream inputStream, boolean z2) throws IOException {
        return DEFAULT.parse(new StringSource(IO.toString(inputStream)), z2);
    }

    public static String toString(Object[] objArr) throws IOException {
        JSON json = DEFAULT;
        StringBuilder sb = new StringBuilder(json.getStringBufferSize());
        json.appendArray(sb, objArr);
        return sb.toString();
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Generator generator) {
        generator.addJSON(stringBuffer);
    }

    public Object parse(Source source, boolean z2) {
        if (!z2) {
            return parse(source);
        }
        Object obj = null;
        char c3 = 1;
        char c4 = 0;
        while (source.hasNext()) {
            char cPeek = source.peek();
            if (c4 == 1) {
                if (cPeek != '*') {
                    if (cPeek == '/') {
                        c4 = 65535;
                    }
                } else if (c3 == 1) {
                    c4 = 0;
                    c3 = 2;
                } else {
                    c4 = 2;
                }
                source.next();
            } else {
                if (c4 > 1) {
                    if (cPeek != '*') {
                        if (cPeek == '/' && c4 == 3) {
                            if (c3 == 2) {
                                return obj;
                            }
                            c4 = 0;
                        }
                        c4 = 2;
                    }
                    c4 = 3;
                } else if (c4 < 0) {
                    if (cPeek == '\n' || cPeek == '\r') {
                        c4 = 0;
                    }
                } else if (!Character.isWhitespace(cPeek)) {
                    if (cPeek == '/') {
                        c4 = 1;
                    } else if (cPeek == '*') {
                        c4 = 3;
                    } else if (obj == null) {
                        obj = parse(source);
                    }
                }
                source.next();
            }
        }
        return obj;
    }

    public char seekTo(String str, Source source) {
        while (source.hasNext()) {
            char cPeek = source.peek();
            if (str.indexOf(cPeek) >= 0) {
                return cPeek;
            }
            if (Character.isWhitespace(cPeek)) {
                source.next();
            } else {
                throw new IllegalStateException("Unexpected '" + cPeek + "' while seeking one of '" + str + "'");
            }
        }
        throw new IllegalStateException("Expected one of '" + str + "'");
    }

    public void appendJSON(Appendable appendable, Generator generator) {
        generator.addJSON(appendable);
    }

    @Deprecated
    public void appendArray(StringBuffer stringBuffer, Object obj) throws IOException {
        appendArray((Appendable) stringBuffer, obj);
    }

    public String toString(char[] cArr, int i2, int i3) {
        return new String(cArr, i2, i3);
    }

    public void appendArray(Appendable appendable, Object obj) throws IOException {
        try {
            if (obj == null) {
                appendNull(appendable);
                return;
            }
            appendable.append('[');
            int length = Array.getLength(obj);
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 != 0) {
                    appendable.append(',');
                }
                append(appendable, Array.get(obj, i2));
            }
            appendable.append(']');
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object parse(org.eclipse.jetty.util.ajax.JSON.Source r9) {
        /*
            r8 = this;
            r0 = 0
            r1 = r0
        L2:
            boolean r2 = r9.hasNext()
            r3 = 0
            if (r2 == 0) goto Lb5
            char r2 = r9.peek()
            r4 = 42
            r5 = 2
            r6 = 47
            r7 = 1
            if (r1 != r7) goto L1e
            if (r2 == r4) goto L25
            if (r2 == r6) goto L1b
            goto La6
        L1b:
            r1 = -1
            goto La6
        L1e:
            if (r1 <= r7) goto L2e
            r3 = 3
            if (r2 == r4) goto L2b
            if (r2 == r6) goto L28
        L25:
            r1 = r5
            goto La6
        L28:
            if (r1 != r3) goto L25
            goto L3a
        L2b:
            r1 = r3
            goto La6
        L2e:
            if (r1 >= 0) goto L3c
            r3 = 10
            if (r2 == r3) goto L3a
            r3 = 13
            if (r2 == r3) goto L3a
            goto La6
        L3a:
            r1 = r0
            goto La6
        L3c:
            r4 = 34
            if (r2 == r4) goto Lb0
            r4 = 45
            if (r2 == r4) goto Lab
            if (r2 == r6) goto La5
            r4 = 78
            if (r2 == r4) goto L9f
            r4 = 91
            if (r2 == r4) goto L9a
            r4 = 102(0x66, float:1.43E-43)
            if (r2 == r4) goto L92
            r4 = 110(0x6e, float:1.54E-43)
            if (r2 == r4) goto L8c
            r4 = 123(0x7b, float:1.72E-43)
            if (r2 == r4) goto L87
            r4 = 116(0x74, float:1.63E-43)
            if (r2 == r4) goto L7f
            r4 = 117(0x75, float:1.64E-43)
            if (r2 == r4) goto L79
            boolean r3 = java.lang.Character.isDigit(r2)
            if (r3 == 0) goto L6d
            java.lang.Number r9 = r8.parseNumber(r9)
            return r9
        L6d:
            boolean r3 = java.lang.Character.isWhitespace(r2)
            if (r3 == 0) goto L74
            goto La6
        L74:
            java.lang.Object r9 = r8.handleUnknown(r9, r2)
            return r9
        L79:
            java.lang.String r0 = "undefined"
            complete(r0, r9)
            return r3
        L7f:
            java.lang.String r0 = "true"
            complete(r0, r9)
            java.lang.Boolean r9 = java.lang.Boolean.TRUE
            return r9
        L87:
            java.lang.Object r9 = r8.parseObject(r9)
            return r9
        L8c:
            java.lang.String r0 = "null"
            complete(r0, r9)
            return r3
        L92:
            java.lang.String r0 = "false"
            complete(r0, r9)
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            return r9
        L9a:
            java.lang.Object r9 = r8.parseArray(r9)
            return r9
        L9f:
            java.lang.String r0 = "NaN"
            complete(r0, r9)
            return r3
        La5:
            r1 = r7
        La6:
            r9.next()
            goto L2
        Lab:
            java.lang.Number r9 = r8.parseNumber(r9)
            return r9
        Lb0:
            java.lang.String r9 = r8.parseString(r9)
            return r9
        Lb5:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parse(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.Object");
    }
}
