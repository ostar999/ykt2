package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class JSONPath implements JSONAware {
    static final long LENGTH = -1580386065683472715L;
    static final long SIZE = 5614464919154503228L;
    private static ConcurrentMap<String, JSONPath> pathCache = new ConcurrentHashMap(128, 0.75f, 1);
    private boolean hasRefSegment;
    private ParserConfig parserConfig;
    private final String path;
    private Segment[] segments;
    private SerializeConfig serializeConfig;

    /* renamed from: com.alibaba.fastjson.JSONPath$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$fastjson$JSONPath$Operator;

        static {
            int[] iArr = new int[Operator.values().length];
            $SwitchMap$com$alibaba$fastjson$JSONPath$Operator = iArr;
            try {
                iArr[Operator.EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.NE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.GE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.GT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.LE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.LT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static class ArrayAccessSegment implements Segment {
        private final int index;

        public ArrayAccessSegment(int i2) {
            this.index = i2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            return jSONPath.getArrayItem(obj2, this.index);
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            if (((JSONLexerBase) defaultJSONParser.lexer).seekArrayToItem(this.index) && context.eval) {
                context.object = defaultJSONParser.parse();
            }
        }

        public boolean remove(JSONPath jSONPath, Object obj) {
            return jSONPath.removeArrayItem(jSONPath, obj, this.index);
        }

        public boolean setValue(JSONPath jSONPath, Object obj, Object obj2) {
            return jSONPath.setArrayItem(jSONPath, obj, this.index, obj2);
        }
    }

    public static class Context {
        final boolean eval;
        Object object;
        final Context parent;

        public Context(Context context, boolean z2) {
            this.parent = context;
            this.eval = z2;
        }
    }

    public static class DoubleOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final double value;

        public DoubleOpSegement(String str, double d3, Operator operator) {
            this.propertyName = str;
            this.value = d3;
            this.op = operator;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            double dDoubleValue = ((Number) propertyValue).doubleValue();
            switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                case 1:
                    if (dDoubleValue == this.value) {
                        break;
                    }
                    break;
                case 2:
                    if (dDoubleValue != this.value) {
                        break;
                    }
                    break;
                case 3:
                    if (dDoubleValue >= this.value) {
                        break;
                    }
                    break;
                case 4:
                    if (dDoubleValue > this.value) {
                        break;
                    }
                    break;
                case 5:
                    if (dDoubleValue <= this.value) {
                        break;
                    }
                    break;
                case 6:
                    if (dDoubleValue < this.value) {
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    public interface Filter {
        boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3);
    }

    public static class FilterGroup implements Filter {
        private boolean and;
        private List<Filter> fitlers;

        public FilterGroup(Filter filter, Filter filter2, boolean z2) {
            ArrayList arrayList = new ArrayList(2);
            this.fitlers = arrayList;
            arrayList.add(filter);
            this.fitlers.add(filter2);
            this.and = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            if (this.and) {
                Iterator<Filter> it = this.fitlers.iterator();
                while (it.hasNext()) {
                    if (!it.next().apply(jSONPath, obj, obj2, obj3)) {
                        return false;
                    }
                }
                return true;
            }
            Iterator<Filter> it2 = this.fitlers.iterator();
            while (it2.hasNext()) {
                if (it2.next().apply(jSONPath, obj, obj2, obj3)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class FilterSegment implements Segment {
        private final Filter filter;

        public FilterSegment(Filter filter) {
            this.filter = filter;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (obj2 == null) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            if (!(obj2 instanceof Iterable)) {
                if (this.filter.apply(jSONPath, obj, obj2, obj2)) {
                    return obj2;
                }
                return null;
            }
            for (Object obj3 : (Iterable) obj2) {
                if (this.filter.apply(jSONPath, obj, obj2, obj3)) {
                    jSONArray.add(obj3);
                }
            }
            return jSONArray;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(JSONPath jSONPath, Object obj, Object obj2) {
            if (obj2 == null || !(obj2 instanceof Iterable)) {
                return false;
            }
            Iterator it = ((Iterable) obj2).iterator();
            while (it.hasNext()) {
                if (this.filter.apply(jSONPath, obj, obj2, it.next())) {
                    it.remove();
                }
            }
            return true;
        }
    }

    public static class IntBetweenSegement implements Filter {
        private final long endValue;
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final long startValue;

        public IntBetweenSegement(String str, long j2, long j3, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.startValue = j2;
            this.endValue = j3;
            this.not = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long jLongExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                if (jLongExtractValue >= this.startValue && jLongExtractValue <= this.endValue) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    public static class IntInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final long[] values;

        public IntInSegement(String str, long[] jArr, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.values = jArr;
            this.not = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long jLongExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                for (long j2 : this.values) {
                    if (j2 == jLongExtractValue) {
                        return !this.not;
                    }
                }
            }
            return this.not;
        }
    }

    public static class IntObjInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final Long[] values;

        public IntObjInSegement(String str, Long[] lArr, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.values = lArr;
            this.not = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            int i2 = 0;
            if (propertyValue == null) {
                Long[] lArr = this.values;
                int length = lArr.length;
                while (i2 < length) {
                    if (lArr[i2] == null) {
                        return !this.not;
                    }
                    i2++;
                }
                return this.not;
            }
            if (propertyValue instanceof Number) {
                long jLongExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                Long[] lArr2 = this.values;
                int length2 = lArr2.length;
                while (i2 < length2) {
                    Long l2 = lArr2[i2];
                    if (l2 != null && l2.longValue() == jLongExtractValue) {
                        return !this.not;
                    }
                    i2++;
                }
            }
            return this.not;
        }
    }

    public static class IntOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final long value;
        private BigDecimal valueDecimal;
        private Double valueDouble;
        private Float valueFloat;

        public IntOpSegement(String str, long j2, Operator operator) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.value = j2;
            this.op = operator;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            if (propertyValue instanceof BigDecimal) {
                if (this.valueDecimal == null) {
                    this.valueDecimal = BigDecimal.valueOf(this.value);
                }
                int iCompareTo = this.valueDecimal.compareTo((BigDecimal) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (iCompareTo == 0) {
                            break;
                        }
                        break;
                    case 2:
                        if (iCompareTo != 0) {
                            break;
                        }
                        break;
                    case 3:
                        if (iCompareTo <= 0) {
                            break;
                        }
                        break;
                    case 4:
                        if (iCompareTo < 0) {
                            break;
                        }
                        break;
                    case 5:
                        if (iCompareTo >= 0) {
                            break;
                        }
                        break;
                    case 6:
                        if (iCompareTo > 0) {
                            break;
                        }
                        break;
                }
                return false;
            }
            if (propertyValue instanceof Float) {
                if (this.valueFloat == null) {
                    this.valueFloat = Float.valueOf(this.value);
                }
                int iCompareTo2 = this.valueFloat.compareTo((Float) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (iCompareTo2 == 0) {
                            break;
                        }
                        break;
                    case 2:
                        if (iCompareTo2 != 0) {
                            break;
                        }
                        break;
                    case 3:
                        if (iCompareTo2 <= 0) {
                            break;
                        }
                        break;
                    case 4:
                        if (iCompareTo2 < 0) {
                            break;
                        }
                        break;
                    case 5:
                        if (iCompareTo2 >= 0) {
                            break;
                        }
                        break;
                    case 6:
                        if (iCompareTo2 > 0) {
                            break;
                        }
                        break;
                }
                return false;
            }
            if (!(propertyValue instanceof Double)) {
                long jLongExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (jLongExtractValue == this.value) {
                            break;
                        }
                        break;
                    case 2:
                        if (jLongExtractValue != this.value) {
                            break;
                        }
                        break;
                    case 3:
                        if (jLongExtractValue >= this.value) {
                            break;
                        }
                        break;
                    case 4:
                        if (jLongExtractValue > this.value) {
                            break;
                        }
                        break;
                    case 5:
                        if (jLongExtractValue <= this.value) {
                            break;
                        }
                        break;
                    case 6:
                        if (jLongExtractValue < this.value) {
                            break;
                        }
                        break;
                }
                return false;
            }
            if (this.valueDouble == null) {
                this.valueDouble = Double.valueOf(this.value);
            }
            int iCompareTo3 = this.valueDouble.compareTo((Double) propertyValue);
            switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                case 1:
                    if (iCompareTo3 == 0) {
                        break;
                    }
                    break;
                case 2:
                    if (iCompareTo3 != 0) {
                        break;
                    }
                    break;
                case 3:
                    if (iCompareTo3 <= 0) {
                        break;
                    }
                    break;
                case 4:
                    if (iCompareTo3 < 0) {
                        break;
                    }
                    break;
                case 5:
                    if (iCompareTo3 >= 0) {
                        break;
                    }
                    break;
                case 6:
                    if (iCompareTo3 > 0) {
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    public static class JSONPathParser {
        private char ch;
        private boolean hasRefSegment;
        private int level;
        private final String path;
        private int pos;
        private static final String strArrayRegex = "'\\s*,\\s*'";
        private static final Pattern strArrayPatternx = Pattern.compile(strArrayRegex);

        public JSONPathParser(String str) {
            this.path = str;
            next();
        }

        public static boolean isDigitFirst(char c3) {
            return c3 == '-' || c3 == '+' || (c3 >= '0' && c3 <= '9');
        }

        public void accept(char c3) {
            if (this.ch == c3) {
                if (isEOF()) {
                    return;
                }
                next();
            } else {
                throw new JSONPathException("expect '" + c3 + ", but '" + this.ch + "'");
            }
        }

        public Segment buildArraySegement(String str) {
            int length = str.length();
            char cCharAt = str.charAt(0);
            int i2 = length - 1;
            char cCharAt2 = str.charAt(i2);
            int iIndexOf = str.indexOf(44);
            if (str.length() > 2 && cCharAt == '\'' && cCharAt2 == '\'') {
                String strSubstring = str.substring(1, i2);
                return (iIndexOf == -1 || !strArrayPatternx.matcher(str).find()) ? new PropertySegment(strSubstring, false) : new MultiPropertySegment(strSubstring.split(strArrayRegex));
            }
            int iIndexOf2 = str.indexOf(58);
            if (iIndexOf == -1 && iIndexOf2 == -1) {
                if (TypeUtils.isNumber(str)) {
                    try {
                        return new ArrayAccessSegment(Integer.parseInt(str));
                    } catch (NumberFormatException unused) {
                        return new PropertySegment(str, false);
                    }
                }
                if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
                    str = str.substring(1, str.length() - 1);
                }
                return new PropertySegment(str, false);
            }
            if (iIndexOf != -1) {
                String[] strArrSplit = str.split(",");
                int[] iArr = new int[strArrSplit.length];
                for (int i3 = 0; i3 < strArrSplit.length; i3++) {
                    iArr[i3] = Integer.parseInt(strArrSplit[i3]);
                }
                return new MultiIndexSegment(iArr);
            }
            if (iIndexOf2 == -1) {
                throw new UnsupportedOperationException();
            }
            String[] strArrSplit2 = str.split(":");
            int length2 = strArrSplit2.length;
            int[] iArr2 = new int[length2];
            for (int i4 = 0; i4 < strArrSplit2.length; i4++) {
                String str2 = strArrSplit2[i4];
                if (str2.length() != 0) {
                    iArr2[i4] = Integer.parseInt(str2);
                } else {
                    if (i4 != 0) {
                        throw new UnsupportedOperationException();
                    }
                    iArr2[i4] = 0;
                }
            }
            int i5 = iArr2[0];
            int i6 = length2 > 1 ? iArr2[1] : -1;
            int i7 = length2 == 3 ? iArr2[2] : 1;
            if (i6 < 0 || i6 >= i5) {
                if (i7 > 0) {
                    return new RangeSegment(i5, i6, i7);
                }
                throw new UnsupportedOperationException("step must greater than zero : " + i7);
            }
            throw new UnsupportedOperationException("end must greater than or equals start. start " + i5 + ",  end " + i6);
        }

        public Segment[] explain() {
            String str = this.path;
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException();
            }
            Segment[] segmentArr = new Segment[8];
            while (true) {
                Segment segement = readSegement();
                if (segement == null) {
                    break;
                }
                if (segement instanceof PropertySegment) {
                    PropertySegment propertySegment = (PropertySegment) segement;
                    if (propertySegment.deep || !propertySegment.propertyName.equals("*")) {
                    }
                }
                int i2 = this.level;
                if (i2 == segmentArr.length) {
                    Segment[] segmentArr2 = new Segment[(i2 * 3) / 2];
                    System.arraycopy(segmentArr, 0, segmentArr2, 0, i2);
                    segmentArr = segmentArr2;
                }
                int i3 = this.level;
                this.level = i3 + 1;
                segmentArr[i3] = segement;
            }
            int i4 = this.level;
            if (i4 == segmentArr.length) {
                return segmentArr;
            }
            Segment[] segmentArr3 = new Segment[i4];
            System.arraycopy(segmentArr, 0, segmentArr3, 0, i4);
            return segmentArr3;
        }

        public Filter filterRest(Filter filter) {
            char c3 = this.ch;
            boolean z2 = true;
            boolean z3 = c3 == '&';
            if ((c3 != '&' || getNextChar() != '&') && (this.ch != '|' || getNextChar() != '|')) {
                return filter;
            }
            next();
            next();
            if (this.ch == '(') {
                next();
            } else {
                z2 = false;
            }
            while (this.ch == ' ') {
                next();
            }
            FilterGroup filterGroup = new FilterGroup(filter, (Filter) parseArrayAccessFilter(false), z3);
            if (z2 && this.ch == ')') {
                next();
            }
            return filterGroup;
        }

        public char getNextChar() {
            return this.path.charAt(this.pos);
        }

        public boolean isEOF() {
            return this.pos >= this.path.length();
        }

        public void next() {
            String str = this.path;
            int i2 = this.pos;
            this.pos = i2 + 1;
            this.ch = str.charAt(i2);
        }

        public Segment parseArrayAccess(boolean z2) {
            Object arrayAccessFilter = parseArrayAccessFilter(z2);
            return arrayAccessFilter instanceof Segment ? (Segment) arrayAccessFilter : new FilterSegment((Filter) arrayAccessFilter);
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x008e  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x00cc  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Object parseArrayAccessFilter(boolean r24) {
            /*
                Method dump skipped, instructions count: 1601
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.JSONPathParser.parseArrayAccessFilter(boolean):java.lang.Object");
        }

        public double readDoubleValue(long j2) {
            int i2 = this.pos - 1;
            next();
            while (true) {
                char c3 = this.ch;
                if (c3 < '0' || c3 > '9') {
                    break;
                }
                next();
            }
            return Double.parseDouble(this.path.substring(i2, this.pos - 1)) + j2;
        }

        public long readLongValue() {
            int i2 = this.pos - 1;
            char c3 = this.ch;
            if (c3 == '+' || c3 == '-') {
                next();
            }
            while (true) {
                char c4 = this.ch;
                if (c4 < '0' || c4 > '9') {
                    break;
                }
                next();
            }
            return Long.parseLong(this.path.substring(i2, this.pos - 1));
        }

        public String readName() {
            skipWhitespace();
            char c3 = this.ch;
            if (c3 != '\\' && !Character.isJavaIdentifierStart(c3)) {
                throw new JSONPathException("illeal jsonpath syntax. " + this.path);
            }
            StringBuilder sb = new StringBuilder();
            while (!isEOF()) {
                char c4 = this.ch;
                if (c4 == '\\') {
                    next();
                    sb.append(this.ch);
                    if (isEOF()) {
                        return sb.toString();
                    }
                    next();
                } else {
                    if (!Character.isJavaIdentifierPart(c4)) {
                        break;
                    }
                    sb.append(this.ch);
                    next();
                }
            }
            if (isEOF() && Character.isJavaIdentifierPart(this.ch)) {
                sb.append(this.ch);
            }
            return sb.toString();
        }

        public Operator readOp() {
            Operator operator;
            char c3 = this.ch;
            if (c3 == '=') {
                next();
                char c4 = this.ch;
                if (c4 == '~') {
                    next();
                    operator = Operator.REG_MATCH;
                } else if (c4 == '=') {
                    next();
                    operator = Operator.EQ;
                } else {
                    operator = Operator.EQ;
                }
            } else if (c3 == '!') {
                next();
                accept('=');
                operator = Operator.NE;
            } else if (c3 == '<') {
                next();
                if (this.ch == '=') {
                    next();
                    operator = Operator.LE;
                } else {
                    operator = Operator.LT;
                }
            } else if (c3 == '>') {
                next();
                if (this.ch == '=') {
                    next();
                    operator = Operator.GE;
                } else {
                    operator = Operator.GT;
                }
            } else {
                operator = null;
            }
            if (operator != null) {
                return operator;
            }
            String name = readName();
            if ("not".equalsIgnoreCase(name)) {
                skipWhitespace();
                String name2 = readName();
                if ("like".equalsIgnoreCase(name2)) {
                    return Operator.NOT_LIKE;
                }
                if ("rlike".equalsIgnoreCase(name2)) {
                    return Operator.NOT_RLIKE;
                }
                if ("in".equalsIgnoreCase(name2)) {
                    return Operator.NOT_IN;
                }
                if ("between".equalsIgnoreCase(name2)) {
                    return Operator.NOT_BETWEEN;
                }
                throw new UnsupportedOperationException();
            }
            if ("nin".equalsIgnoreCase(name)) {
                return Operator.NOT_IN;
            }
            if ("like".equalsIgnoreCase(name)) {
                return Operator.LIKE;
            }
            if ("rlike".equalsIgnoreCase(name)) {
                return Operator.RLIKE;
            }
            if ("in".equalsIgnoreCase(name)) {
                return Operator.IN;
            }
            if ("between".equalsIgnoreCase(name)) {
                return Operator.BETWEEN;
            }
            throw new UnsupportedOperationException();
        }

        public Segment readSegement() {
            boolean z2 = true;
            if (this.level == 0 && this.path.length() == 1) {
                if (isDigitFirst(this.ch)) {
                    return new ArrayAccessSegment(this.ch - '0');
                }
                char c3 = this.ch;
                if ((c3 >= 'a' && c3 <= 'z') || (c3 >= 'A' && c3 <= 'Z')) {
                    return new PropertySegment(Character.toString(c3), false);
                }
            }
            while (!isEOF()) {
                skipWhitespace();
                char c4 = this.ch;
                if (c4 != '$') {
                    if (c4 != '.' && c4 != '/') {
                        if (c4 == '[') {
                            return parseArrayAccess(true);
                        }
                        if (this.level == 0) {
                            return new PropertySegment(readName(), false);
                        }
                        throw new JSONPathException("not support jsonpath : " + this.path);
                    }
                    next();
                    if (c4 == '.' && this.ch == '.') {
                        next();
                        int length = this.path.length();
                        int i2 = this.pos;
                        if (length > i2 + 3 && this.ch == '[' && this.path.charAt(i2) == '*' && this.path.charAt(this.pos + 1) == ']' && this.path.charAt(this.pos + 2) == '.') {
                            next();
                            next();
                            next();
                            next();
                        }
                    } else {
                        z2 = false;
                    }
                    char c5 = this.ch;
                    if (c5 == '*') {
                        if (!isEOF()) {
                            next();
                        }
                        return z2 ? WildCardSegment.instance_deep : WildCardSegment.instance;
                    }
                    if (isDigitFirst(c5)) {
                        return parseArrayAccess(false);
                    }
                    String name = readName();
                    if (this.ch != '(') {
                        return new PropertySegment(name, z2);
                    }
                    next();
                    if (this.ch != ')') {
                        throw new JSONPathException("not support jsonpath : " + this.path);
                    }
                    if (!isEOF()) {
                        next();
                    }
                    if (DatabaseManager.SIZE.equals(name) || SessionDescription.ATTR_LENGTH.equals(name)) {
                        return SizeSegment.instance;
                    }
                    if ("max".equals(name)) {
                        return MaxSegment.instance;
                    }
                    if ("min".equals(name)) {
                        return MinSegment.instance;
                    }
                    if ("keySet".equals(name)) {
                        return KeySetSegment.instance;
                    }
                    throw new JSONPathException("not support jsonpath : " + this.path);
                }
                next();
            }
            return null;
        }

        public String readString() {
            char c3 = this.ch;
            next();
            int i2 = this.pos - 1;
            while (this.ch != c3 && !isEOF()) {
                next();
            }
            String strSubstring = this.path.substring(i2, isEOF() ? this.pos : this.pos - 1);
            accept(c3);
            return strSubstring;
        }

        public Object readValue() {
            skipWhitespace();
            if (isDigitFirst(this.ch)) {
                return Long.valueOf(readLongValue());
            }
            char c3 = this.ch;
            if (c3 == '\"' || c3 == '\'') {
                return readString();
            }
            if (c3 != 'n') {
                throw new UnsupportedOperationException();
            }
            if ("null".equals(readName())) {
                return null;
            }
            throw new JSONPathException(this.path);
        }

        public final void skipWhitespace() {
            while (true) {
                char c3 = this.ch;
                if (c3 > ' ') {
                    return;
                }
                if (c3 != ' ' && c3 != '\r' && c3 != '\n' && c3 != '\t' && c3 != '\f' && c3 != '\b') {
                    return;
                } else {
                    next();
                }
            }
        }
    }

    public static class KeySetSegment implements Segment {
        public static final KeySetSegment instance = new KeySetSegment();

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            return jSONPath.evalKeySet(obj2);
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class MatchSegement implements Filter {
        private final String[] containsValues;
        private final String endsWithValue;
        private final int minLength;
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final String startsWithValue;

        public MatchSegement(String str, String str2, String str3, String[] strArr, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.startsWithValue = str2;
            this.endsWithValue = str3;
            this.containsValues = strArr;
            this.not = z2;
            int length = str2 != null ? str2.length() + 0 : 0;
            length = str3 != null ? length + str3.length() : length;
            if (strArr != null) {
                for (String str4 : strArr) {
                    length += str4.length();
                }
            }
            this.minLength = length;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            int length;
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            String string = propertyValue.toString();
            if (string.length() < this.minLength) {
                return this.not;
            }
            String str = this.startsWithValue;
            if (str == null) {
                length = 0;
            } else {
                if (!string.startsWith(str)) {
                    return this.not;
                }
                length = this.startsWithValue.length() + 0;
            }
            String[] strArr = this.containsValues;
            if (strArr != null) {
                for (String str2 : strArr) {
                    int iIndexOf = string.indexOf(str2, length);
                    if (iIndexOf == -1) {
                        return this.not;
                    }
                    length = iIndexOf + str2.length();
                }
            }
            String str3 = this.endsWithValue;
            return (str3 == null || string.endsWith(str3)) ? !this.not : this.not;
        }
    }

    public static class MaxSegment implements Segment {
        public static final MaxSegment instance = new MaxSegment();

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (!(obj instanceof Collection)) {
                throw new UnsupportedOperationException();
            }
            Object obj3 = null;
            for (Object obj4 : (Collection) obj) {
                if (obj4 != null && (obj3 == null || JSONPath.compare(obj3, obj4) < 0)) {
                    obj3 = obj4;
                }
            }
            return obj3;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class MinSegment implements Segment {
        public static final MinSegment instance = new MinSegment();

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (!(obj instanceof Collection)) {
                throw new UnsupportedOperationException();
            }
            Object obj3 = null;
            for (Object obj4 : (Collection) obj) {
                if (obj4 != null && (obj3 == null || JSONPath.compare(obj3, obj4) > 0)) {
                    obj3 = obj4;
                }
            }
            return obj3;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class MultiIndexSegment implements Segment {
        private final int[] indexes;

        public MultiIndexSegment(int[] iArr) {
            this.indexes = iArr;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            JSONArray jSONArray = new JSONArray(this.indexes.length);
            int i2 = 0;
            while (true) {
                int[] iArr = this.indexes;
                if (i2 >= iArr.length) {
                    return jSONArray;
                }
                jSONArray.add(jSONPath.getArrayItem(obj2, iArr[i2]));
                i2++;
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            if (context.eval) {
                Object obj = defaultJSONParser.parse();
                if (obj instanceof List) {
                    int[] iArr = this.indexes;
                    int length = iArr.length;
                    int[] iArr2 = new int[length];
                    System.arraycopy(iArr, 0, iArr2, 0, length);
                    List list = (List) obj;
                    if (iArr2[0] >= 0) {
                        for (int size = list.size() - 1; size >= 0; size--) {
                            if (Arrays.binarySearch(iArr2, size) < 0) {
                                list.remove(size);
                            }
                        }
                        context.object = list;
                        return;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    public static class MultiPropertySegment implements Segment {
        private final String[] propertyNames;
        private final long[] propertyNamesHash;

        public MultiPropertySegment(String[] strArr) {
            this.propertyNames = strArr;
            this.propertyNamesHash = new long[strArr.length];
            int i2 = 0;
            while (true) {
                long[] jArr = this.propertyNamesHash;
                if (i2 >= jArr.length) {
                    return;
                }
                jArr[i2] = TypeUtils.fnv1a_64(strArr[i2]);
                i2++;
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            ArrayList arrayList = new ArrayList(this.propertyNames.length);
            int i2 = 0;
            while (true) {
                String[] strArr = this.propertyNames;
                if (i2 >= strArr.length) {
                    return arrayList;
                }
                arrayList.add(jSONPath.getPropertyValue(obj2, strArr[i2], this.propertyNamesHash[i2]));
                i2++;
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) throws NumberFormatException {
            JSONArray jSONArray;
            Object objIntegerValue;
            JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
            Object obj = context.object;
            if (obj == null) {
                jSONArray = new JSONArray();
                context.object = jSONArray;
            } else {
                jSONArray = (JSONArray) obj;
            }
            for (int size = jSONArray.size(); size < this.propertyNamesHash.length; size++) {
                jSONArray.add(null);
            }
            do {
                int iSeekObjectToField = jSONLexerBase.seekObjectToField(this.propertyNamesHash);
                if (jSONLexerBase.matchStat != 3) {
                    return;
                }
                int i2 = jSONLexerBase.token();
                if (i2 == 2) {
                    objIntegerValue = jSONLexerBase.integerValue();
                    jSONLexerBase.nextToken(16);
                } else if (i2 == 3) {
                    objIntegerValue = jSONLexerBase.decimalValue();
                    jSONLexerBase.nextToken(16);
                } else if (i2 != 4) {
                    objIntegerValue = defaultJSONParser.parse();
                } else {
                    objIntegerValue = jSONLexerBase.stringVal();
                    jSONLexerBase.nextToken(16);
                }
                jSONArray.set(iSeekObjectToField, objIntegerValue);
            } while (jSONLexerBase.token() == 16);
        }
    }

    public static class NotNullSegement implements Filter {
        private final String propertyName;
        private final long propertyNameHash;

        public NotNullSegement(String str) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            return jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash) != null;
        }
    }

    public static class NullSegement implements Filter {
        private final String propertyName;
        private final long propertyNameHash;

        public NullSegement(String str) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            return jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash) == null;
        }
    }

    public enum Operator {
        EQ,
        NE,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        NOT_LIKE,
        RLIKE,
        NOT_RLIKE,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_BETWEEN,
        And,
        Or,
        REG_MATCH
    }

    public static class PropertySegment implements Segment {
        private final boolean deep;
        private final String propertyName;
        private final long propertyNameHash;

        public PropertySegment(String str, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.deep = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (!this.deep) {
                return jSONPath.getPropertyValue(obj2, this.propertyName, this.propertyNameHash);
            }
            ArrayList arrayList = new ArrayList();
            jSONPath.deepScan(obj2, this.propertyName, arrayList);
            return arrayList;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) throws NumberFormatException {
            Object objIntegerValue;
            Object objIntegerValue2;
            Object objIntegerValue3;
            JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
            if (this.deep && context.object == null) {
                context.object = new JSONArray();
            }
            if (jSONLexerBase.token() != 14) {
                boolean z2 = this.deep;
                if (!z2) {
                    if (jSONLexerBase.seekObjectToField(this.propertyNameHash, z2) == 3 && context.eval) {
                        int i2 = jSONLexerBase.token();
                        if (i2 == 2) {
                            objIntegerValue2 = jSONLexerBase.integerValue();
                            jSONLexerBase.nextToken(16);
                        } else if (i2 == 3) {
                            objIntegerValue2 = jSONLexerBase.decimalValue();
                            jSONLexerBase.nextToken(16);
                        } else if (i2 != 4) {
                            objIntegerValue2 = defaultJSONParser.parse();
                        } else {
                            objIntegerValue2 = jSONLexerBase.stringVal();
                            jSONLexerBase.nextToken(16);
                        }
                        if (context.eval) {
                            context.object = objIntegerValue2;
                            return;
                        }
                        return;
                    }
                    return;
                }
                while (true) {
                    int iSeekObjectToField = jSONLexerBase.seekObjectToField(this.propertyNameHash, this.deep);
                    if (iSeekObjectToField == -1) {
                        return;
                    }
                    if (iSeekObjectToField == 3) {
                        if (context.eval) {
                            int i3 = jSONLexerBase.token();
                            if (i3 == 2) {
                                objIntegerValue = jSONLexerBase.integerValue();
                                jSONLexerBase.nextToken(16);
                            } else if (i3 == 3) {
                                objIntegerValue = jSONLexerBase.decimalValue();
                                jSONLexerBase.nextToken(16);
                            } else if (i3 != 4) {
                                objIntegerValue = defaultJSONParser.parse();
                            } else {
                                objIntegerValue = jSONLexerBase.stringVal();
                                jSONLexerBase.nextToken(16);
                            }
                            if (context.eval) {
                                Object obj = context.object;
                                if (obj instanceof List) {
                                    List list = (List) obj;
                                    if (list.size() == 0 && (objIntegerValue instanceof List)) {
                                        context.object = objIntegerValue;
                                    } else {
                                        list.add(objIntegerValue);
                                    }
                                } else {
                                    context.object = objIntegerValue;
                                }
                            }
                        }
                    } else if (iSeekObjectToField == 1 || iSeekObjectToField == 2) {
                        extract(jSONPath, defaultJSONParser, context);
                    }
                }
            } else {
                if ("*".equals(this.propertyName)) {
                    return;
                }
                jSONLexerBase.nextToken();
                JSONArray jSONArray = this.deep ? (JSONArray) context.object : new JSONArray();
                while (true) {
                    int i4 = jSONLexerBase.token();
                    if (i4 == 12) {
                        boolean z3 = this.deep;
                        if (z3) {
                            extract(jSONPath, defaultJSONParser, context);
                        } else {
                            int iSeekObjectToField2 = jSONLexerBase.seekObjectToField(this.propertyNameHash, z3);
                            if (iSeekObjectToField2 == 3) {
                                int i5 = jSONLexerBase.token();
                                if (i5 == 2) {
                                    objIntegerValue3 = jSONLexerBase.integerValue();
                                    jSONLexerBase.nextToken();
                                } else if (i5 != 4) {
                                    objIntegerValue3 = defaultJSONParser.parse();
                                } else {
                                    objIntegerValue3 = jSONLexerBase.stringVal();
                                    jSONLexerBase.nextToken();
                                }
                                jSONArray.add(objIntegerValue3);
                                if (jSONLexerBase.token() == 13) {
                                    jSONLexerBase.nextToken();
                                } else {
                                    jSONLexerBase.skipObject(false);
                                }
                            } else if (iSeekObjectToField2 == -1) {
                                continue;
                            } else {
                                if (this.deep) {
                                    throw new UnsupportedOperationException(jSONLexerBase.info());
                                }
                                jSONLexerBase.skipObject(false);
                            }
                        }
                    } else if (i4 != 14) {
                        switch (i4) {
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                jSONLexerBase.nextToken();
                                break;
                        }
                    } else if (this.deep) {
                        extract(jSONPath, defaultJSONParser, context);
                    } else {
                        jSONLexerBase.skipObject(false);
                    }
                    if (jSONLexerBase.token() == 15) {
                        jSONLexerBase.nextToken();
                        if (this.deep || jSONArray.size() <= 0) {
                            return;
                        }
                        context.object = jSONArray;
                        return;
                    }
                    if (jSONLexerBase.token() != 16) {
                        throw new JSONException("illegal json : " + jSONLexerBase.info());
                    }
                    jSONLexerBase.nextToken();
                }
            }
        }

        public boolean remove(JSONPath jSONPath, Object obj) {
            return jSONPath.removePropertyValue(obj, this.propertyName, this.deep);
        }

        public void setValue(JSONPath jSONPath, Object obj, Object obj2) {
            if (this.deep) {
                jSONPath.deepSet(obj, this.propertyName, this.propertyNameHash, obj2);
            } else {
                jSONPath.setPropertyValue(obj, this.propertyName, this.propertyNameHash, obj2);
            }
        }
    }

    public static class RangeSegment implements Segment {
        private final int end;
        private final int start;
        private final int step;

        public RangeSegment(int i2, int i3, int i4) {
            this.start = i2;
            this.end = i3;
            this.step = i4;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            int iIntValue = SizeSegment.instance.eval(jSONPath, obj, obj2).intValue();
            int i2 = this.start;
            if (i2 < 0) {
                i2 += iIntValue;
            }
            int i3 = this.end;
            if (i3 < 0) {
                i3 += iIntValue;
            }
            int i4 = ((i3 - i2) / this.step) + 1;
            if (i4 == -1) {
                return null;
            }
            ArrayList arrayList = new ArrayList(i4);
            while (i2 <= i3 && i2 < iIntValue) {
                arrayList.add(jSONPath.getArrayItem(obj2, i2));
                i2 += this.step;
            }
            return arrayList;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class RefOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final Segment refSgement;

        public RefOpSegement(String str, Segment segment, Operator operator) {
            this.propertyName = str;
            this.refSgement = segment;
            this.op = operator;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            Object objEval = this.refSgement.eval(jSONPath, obj, obj);
            if ((objEval instanceof Integer) || (objEval instanceof Long) || (objEval instanceof Short) || (objEval instanceof Byte)) {
                long jLongExtractValue = TypeUtils.longExtractValue((Number) objEval);
                if ((propertyValue instanceof Integer) || (propertyValue instanceof Long) || (propertyValue instanceof Short) || (propertyValue instanceof Byte)) {
                    long jLongExtractValue2 = TypeUtils.longExtractValue((Number) propertyValue);
                    switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                        case 1:
                            return jLongExtractValue2 == jLongExtractValue;
                        case 2:
                            return jLongExtractValue2 != jLongExtractValue;
                        case 3:
                            return jLongExtractValue2 >= jLongExtractValue;
                        case 4:
                            return jLongExtractValue2 > jLongExtractValue;
                        case 5:
                            return jLongExtractValue2 <= jLongExtractValue;
                        case 6:
                            return jLongExtractValue2 < jLongExtractValue;
                    }
                }
                if (propertyValue instanceof BigDecimal) {
                    int iCompareTo = BigDecimal.valueOf(jLongExtractValue).compareTo((BigDecimal) propertyValue);
                    switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                        case 1:
                            return iCompareTo == 0;
                        case 2:
                            return iCompareTo != 0;
                        case 3:
                            return iCompareTo <= 0;
                        case 4:
                            return iCompareTo < 0;
                        case 5:
                            return iCompareTo >= 0;
                        case 6:
                            return iCompareTo > 0;
                        default:
                            return false;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    public static class RegMatchSegement implements Filter {
        private final Operator op;
        private final Pattern pattern;
        private final String propertyName;
        private final long propertyNameHash;

        public RegMatchSegement(String str, Pattern pattern, Operator operator) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.pattern = pattern;
            this.op = operator;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            return this.pattern.matcher(propertyValue.toString()).matches();
        }
    }

    public static class RlikeSegement implements Filter {
        private final boolean not;
        private final Pattern pattern;
        private final String propertyName;
        private final long propertyNameHash;

        public RlikeSegement(String str, String str2, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.pattern = Pattern.compile(str2);
            this.not = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            boolean zMatches = this.pattern.matcher(propertyValue.toString()).matches();
            return this.not ? !zMatches : zMatches;
        }
    }

    public interface Segment {
        Object eval(JSONPath jSONPath, Object obj, Object obj2);

        void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context);
    }

    public static class SizeSegment implements Segment {
        public static final SizeSegment instance = new SizeSegment();

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Integer eval(JSONPath jSONPath, Object obj, Object obj2) {
            return Integer.valueOf(jSONPath.evalSize(obj2));
        }
    }

    public static class StringInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final String[] values;

        public StringInSegement(String str, String[] strArr, boolean z2) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.values = strArr;
            this.not = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            for (String str : this.values) {
                if (str == propertyValue) {
                    return !this.not;
                }
                if (str != null && str.equals(propertyValue)) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    public static class StringOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final String value;

        public StringOpSegement(String str, String str2, Operator operator) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.value = str2;
            this.op = operator;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            Operator operator = this.op;
            if (operator == Operator.EQ) {
                return this.value.equals(propertyValue);
            }
            if (operator == Operator.NE) {
                return !this.value.equals(propertyValue);
            }
            if (propertyValue == null) {
                return false;
            }
            int iCompareTo = this.value.compareTo(propertyValue.toString());
            Operator operator2 = this.op;
            return operator2 == Operator.GE ? iCompareTo <= 0 : operator2 == Operator.GT ? iCompareTo < 0 : operator2 == Operator.LE ? iCompareTo >= 0 : operator2 == Operator.LT && iCompareTo > 0;
        }
    }

    public static class ValueSegment implements Filter {
        private boolean eq;
        private final String propertyName;
        private final long propertyNameHash;
        private final Object value;

        public ValueSegment(String str, Object obj, boolean z2) {
            this.eq = true;
            if (obj == null) {
                throw new IllegalArgumentException("value is null");
            }
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.value = obj;
            this.eq = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            boolean zEquals = this.value.equals(jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash));
            return !this.eq ? !zEquals : zEquals;
        }
    }

    public static class WildCardSegment implements Segment {
        public static final WildCardSegment instance = new WildCardSegment(false);
        public static final WildCardSegment instance_deep = new WildCardSegment(true);
        private boolean deep;

        private WildCardSegment(boolean z2) {
            this.deep = z2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (!this.deep) {
                return jSONPath.getPropertyValues(obj2);
            }
            ArrayList arrayList = new ArrayList();
            jSONPath.deepGetPropertyValues(obj2, arrayList);
            return arrayList;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            if (context.eval) {
                Object obj = defaultJSONParser.parse();
                if (this.deep) {
                    ArrayList arrayList = new ArrayList();
                    jSONPath.deepGetPropertyValues(obj, arrayList);
                    context.object = arrayList;
                    return;
                } else {
                    if (obj instanceof JSONObject) {
                        Collection<Object> collectionValues = ((JSONObject) obj).values();
                        JSONArray jSONArray = new JSONArray(collectionValues.size());
                        Iterator<Object> it = collectionValues.iterator();
                        while (it.hasNext()) {
                            jSONArray.add(it.next());
                        }
                        context.object = jSONArray;
                        return;
                    }
                    if (obj instanceof JSONArray) {
                        context.object = obj;
                        return;
                    }
                }
            }
            throw new JSONException("TODO");
        }
    }

    public JSONPath(String str) {
        this(str, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance());
    }

    public static int compare(Object obj, Object obj2) {
        Object d3;
        Object f2;
        if (obj.getClass() == obj2.getClass()) {
            return ((Comparable) obj).compareTo(obj2);
        }
        Class<?> cls = obj.getClass();
        Class<?> cls2 = obj2.getClass();
        if (cls == BigDecimal.class) {
            if (cls2 == Integer.class) {
                f2 = new BigDecimal(((Integer) obj2).intValue());
            } else if (cls2 == Long.class) {
                f2 = new BigDecimal(((Long) obj2).longValue());
            } else if (cls2 == Float.class) {
                f2 = new BigDecimal(((Float) obj2).floatValue());
            } else if (cls2 == Double.class) {
                f2 = new BigDecimal(((Double) obj2).doubleValue());
            }
            obj2 = f2;
        } else if (cls == Long.class) {
            if (cls2 == Integer.class) {
                f2 = new Long(((Integer) obj2).intValue());
                obj2 = f2;
            } else {
                if (cls2 == BigDecimal.class) {
                    d3 = new BigDecimal(((Long) obj).longValue());
                } else if (cls2 == Float.class) {
                    d3 = new Float(((Long) obj).longValue());
                } else if (cls2 == Double.class) {
                    d3 = new Double(((Long) obj).longValue());
                }
                obj = d3;
            }
        } else if (cls == Integer.class) {
            if (cls2 == Long.class) {
                d3 = new Long(((Integer) obj).intValue());
            } else if (cls2 == BigDecimal.class) {
                d3 = new BigDecimal(((Integer) obj).intValue());
            } else if (cls2 == Float.class) {
                d3 = new Float(((Integer) obj).intValue());
            } else if (cls2 == Double.class) {
                d3 = new Double(((Integer) obj).intValue());
            }
            obj = d3;
        } else if (cls == Double.class) {
            if (cls2 == Integer.class) {
                f2 = new Double(((Integer) obj2).intValue());
            } else if (cls2 == Long.class) {
                f2 = new Double(((Long) obj2).longValue());
            } else if (cls2 == Float.class) {
                f2 = new Double(((Float) obj2).floatValue());
            }
            obj2 = f2;
        } else if (cls == Float.class) {
            if (cls2 == Integer.class) {
                f2 = new Float(((Integer) obj2).intValue());
            } else if (cls2 == Long.class) {
                f2 = new Float(((Long) obj2).longValue());
            } else if (cls2 == Double.class) {
                d3 = new Double(((Float) obj).floatValue());
                obj = d3;
            }
            obj2 = f2;
        }
        return ((Comparable) obj).compareTo(obj2);
    }

    public static JSONPath compile(String str) {
        if (str == null) {
            throw new JSONPathException("jsonpath can not be null");
        }
        JSONPath jSONPath = pathCache.get(str);
        if (jSONPath != null) {
            return jSONPath;
        }
        JSONPath jSONPath2 = new JSONPath(str);
        if (pathCache.size() >= 1024) {
            return jSONPath2;
        }
        pathCache.putIfAbsent(str, jSONPath2);
        return pathCache.get(str);
    }

    public static boolean eq(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        if (obj.getClass() == obj2.getClass()) {
            return obj.equals(obj2);
        }
        if (!(obj instanceof Number)) {
            return obj.equals(obj2);
        }
        if (obj2 instanceof Number) {
            return eqNotNull((Number) obj, (Number) obj2);
        }
        return false;
    }

    public static boolean eqNotNull(Number number, Number number2) {
        Class<?> cls = number.getClass();
        boolean zIsInt = isInt(cls);
        Class<?> cls2 = number2.getClass();
        boolean zIsInt2 = isInt(cls2);
        if (number instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) number;
            if (zIsInt2) {
                return bigDecimal.equals(BigDecimal.valueOf(TypeUtils.longExtractValue(number2)));
            }
        }
        if (zIsInt) {
            if (zIsInt2) {
                return number.longValue() == number2.longValue();
            }
            if (number2 instanceof BigInteger) {
                return BigInteger.valueOf(number.longValue()).equals((BigInteger) number);
            }
        }
        if (zIsInt2 && (number instanceof BigInteger)) {
            return ((BigInteger) number).equals(BigInteger.valueOf(TypeUtils.longExtractValue(number2)));
        }
        boolean zIsDouble = isDouble(cls);
        boolean zIsDouble2 = isDouble(cls2);
        return ((zIsDouble && zIsDouble2) || ((zIsDouble && zIsInt2) || (zIsDouble2 && zIsInt))) && number.doubleValue() == number2.doubleValue();
    }

    public static boolean isDouble(Class<?> cls) {
        return cls == Float.class || cls == Double.class;
    }

    public static boolean isInt(Class<?> cls) {
        return cls == Byte.class || cls == Short.class || cls == Integer.class || cls == Long.class;
    }

    public static Map<String, Object> paths(Object obj) {
        return paths(obj, SerializeConfig.globalInstance);
    }

    public static Object read(String str, String str2) {
        return compile(str2).eval(JSON.parse(str));
    }

    public static Object reserveToArray(Object obj, String... strArr) {
        JSONArray jSONArray = new JSONArray();
        if (strArr != null && strArr.length != 0) {
            for (String str : strArr) {
                JSONPath jSONPathCompile = compile(str);
                jSONPathCompile.init();
                jSONArray.add(jSONPathCompile.eval(obj));
            }
        }
        return jSONArray;
    }

    public static Object reserveToObject(Object obj, String... strArr) {
        Object objEval;
        if (strArr == null || strArr.length == 0) {
            return obj;
        }
        JSONObject jSONObject = new JSONObject(true);
        for (String str : strArr) {
            JSONPath jSONPathCompile = compile(str);
            jSONPathCompile.init();
            Segment[] segmentArr = jSONPathCompile.segments;
            if ((segmentArr[segmentArr.length - 1] instanceof PropertySegment) && (objEval = jSONPathCompile.eval(obj)) != null) {
                jSONPathCompile.set(jSONObject, objEval);
            }
        }
        return jSONObject;
    }

    public void arrayAdd(Object obj, Object... objArr) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (objArr == null || objArr.length == 0 || obj == null) {
            return;
        }
        init();
        int i2 = 0;
        Object obj2 = null;
        Object objEval = obj;
        int i3 = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i3 >= segmentArr.length) {
                break;
            }
            if (i3 == segmentArr.length - 1) {
                obj2 = objEval;
            }
            objEval = segmentArr[i3].eval(this, obj, objEval);
            i3++;
        }
        if (objEval == null) {
            throw new JSONPathException("value not found in path " + this.path);
        }
        if (objEval instanceof Collection) {
            Collection collection = (Collection) objEval;
            int length = objArr.length;
            while (i2 < length) {
                collection.add(objArr[i2]);
                i2++;
            }
            return;
        }
        Class<?> cls = objEval.getClass();
        if (!cls.isArray()) {
            throw new JSONException("unsupported array put operation. " + cls);
        }
        int length2 = Array.getLength(objEval);
        Object objNewInstance = Array.newInstance(cls.getComponentType(), objArr.length + length2);
        System.arraycopy(objEval, 0, objNewInstance, 0, length2);
        while (i2 < objArr.length) {
            Array.set(objNewInstance, length2 + i2, objArr[i2]);
            i2++;
        }
        Segment segment = this.segments[r8.length - 1];
        if (segment instanceof PropertySegment) {
            ((PropertySegment) segment).setValue(this, obj2, objNewInstance);
        } else {
            if (!(segment instanceof ArrayAccessSegment)) {
                throw new UnsupportedOperationException();
            }
            ((ArrayAccessSegment) segment).setValue(this, obj2, objNewInstance);
        }
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        init();
        Object obj2 = obj;
        int i2 = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return true;
            }
            Object objEval = segmentArr[i2].eval(this, obj, obj2);
            if (objEval == null) {
                return false;
            }
            if (objEval == Collections.EMPTY_LIST && (obj2 instanceof List)) {
                return ((List) obj2).contains(objEval);
            }
            i2++;
            obj2 = objEval;
        }
    }

    public boolean containsValue(Object obj, Object obj2) {
        Object objEval = eval(obj);
        if (objEval == obj2) {
            return true;
        }
        if (objEval == null) {
            return false;
        }
        if (!(objEval instanceof Iterable)) {
            return eq(objEval, obj2);
        }
        Iterator it = ((Iterable) objEval).iterator();
        while (it.hasNext()) {
            if (eq(it.next(), obj2)) {
                return true;
            }
        }
        return false;
    }

    public void deepGetPropertyValues(Object obj, List<Object> list) {
        Collection fieldValues;
        Class<?> cls = obj.getClass();
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(cls);
        if (javaBeanSerializer != null) {
            try {
                fieldValues = javaBeanSerializer.getFieldValues(obj);
            } catch (Exception e2) {
                throw new JSONPathException("jsonpath error, path " + this.path, e2);
            }
        } else {
            fieldValues = obj instanceof Map ? ((Map) obj).values() : obj instanceof Collection ? (Collection) obj : null;
        }
        if (fieldValues == null) {
            throw new UnsupportedOperationException(cls.getName());
        }
        for (Object obj2 : fieldValues) {
            if (obj2 == null || ParserConfig.isPrimitive2(obj2.getClass())) {
                list.add(obj2);
            } else {
                deepGetPropertyValues(obj2, list);
            }
        }
    }

    public void deepScan(Object obj, String str, List<Object> list) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Map) {
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object value = entry.getValue();
                if (str.equals(entry.getKey())) {
                    if (value instanceof Collection) {
                        list.addAll((Collection) value);
                    } else {
                        list.add(value);
                    }
                } else if (value != null && !ParserConfig.isPrimitive2(value.getClass())) {
                    deepScan(value, str, list);
                }
            }
            return;
        }
        if (obj instanceof Collection) {
            for (Object obj2 : (Collection) obj) {
                if (!ParserConfig.isPrimitive2(obj2.getClass())) {
                    deepScan(obj2, str, list);
                }
            }
            return;
        }
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj.getClass());
        if (javaBeanSerializer == null) {
            if (obj instanceof List) {
                List list2 = (List) obj;
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    deepScan(list2.get(i2), str, list);
                }
                return;
            }
            return;
        }
        try {
            FieldSerializer fieldSerializer = javaBeanSerializer.getFieldSerializer(str);
            if (fieldSerializer == null) {
                Iterator<Object> it = javaBeanSerializer.getFieldValues(obj).iterator();
                while (it.hasNext()) {
                    deepScan(it.next(), str, list);
                }
                return;
            }
            try {
                list.add(fieldSerializer.getPropertyValueDirect(obj));
            } catch (IllegalAccessException e2) {
                throw new JSONException("getFieldValue error." + str, e2);
            } catch (InvocationTargetException e3) {
                throw new JSONException("getFieldValue error." + str, e3);
            }
        } catch (Exception e4) {
            throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + str, e4);
        }
    }

    public void deepSet(Object obj, String str, long j2, Object obj2) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.containsKey(str)) {
                map.get(str);
                map.put(str, obj2);
                return;
            } else {
                Iterator it = map.values().iterator();
                while (it.hasNext()) {
                    deepSet(it.next(), str, j2, obj2);
                }
                return;
            }
        }
        Class<?> cls = obj.getClass();
        JavaBeanDeserializer javaBeanDeserializer = getJavaBeanDeserializer(cls);
        if (javaBeanDeserializer == null) {
            if (obj instanceof List) {
                List list = (List) obj;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    deepSet(list.get(i2), str, j2, obj2);
                }
                return;
            }
            return;
        }
        try {
            FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(str);
            if (fieldDeserializer != null) {
                fieldDeserializer.setValue(obj, obj2);
                return;
            }
            Iterator<Object> it2 = getJavaBeanSerializer(cls).getObjectFieldValues(obj).iterator();
            while (it2.hasNext()) {
                deepSet(it2.next(), str, j2, obj2);
            }
        } catch (Exception e2) {
            throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + str, e2);
        }
    }

    public Object eval(Object obj) {
        if (obj == null) {
            return null;
        }
        init();
        int i2 = 0;
        Object objEval = obj;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return objEval;
            }
            objEval = segmentArr[i2].eval(this, obj, objEval);
            i2++;
        }
    }

    public Set<?> evalKeySet(Object obj) {
        JavaBeanSerializer javaBeanSerializer;
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            return ((Map) obj).keySet();
        }
        if ((obj instanceof Collection) || (obj instanceof Object[]) || obj.getClass().isArray() || (javaBeanSerializer = getJavaBeanSerializer(obj.getClass())) == null) {
            return null;
        }
        try {
            return javaBeanSerializer.getFieldNames(obj);
        } catch (Exception e2) {
            throw new JSONPathException("evalKeySet error : " + this.path, e2);
        }
    }

    public int evalSize(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        if (obj instanceof Map) {
            Iterator it = ((Map) obj).values().iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (it.next() != null) {
                    i2++;
                }
            }
            return i2;
        }
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj.getClass());
        if (javaBeanSerializer == null) {
            return -1;
        }
        try {
            return javaBeanSerializer.getSize(obj);
        } catch (Exception e2) {
            throw new JSONPathException("evalSize error : " + this.path, e2);
        }
    }

    public Object extract(DefaultJSONParser defaultJSONParser) {
        Object obj;
        if (defaultJSONParser == null) {
            return null;
        }
        init();
        if (this.hasRefSegment) {
            return eval(defaultJSONParser.parse());
        }
        if (this.segments.length == 0) {
            return defaultJSONParser.parse();
        }
        Context context = null;
        int i2 = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return context.object;
            }
            Segment segment = segmentArr[i2];
            boolean z2 = true;
            boolean z3 = i2 == segmentArr.length - 1;
            if (context == null || (obj = context.object) == null) {
                if (!z3) {
                    Segment segment2 = segmentArr[i2 + 1];
                    if ((!(segment instanceof PropertySegment) || !((PropertySegment) segment).deep || (!(segment2 instanceof ArrayAccessSegment) && !(segment2 instanceof MultiIndexSegment) && !(segment2 instanceof MultiPropertySegment) && !(segment2 instanceof SizeSegment) && !(segment2 instanceof PropertySegment) && !(segment2 instanceof FilterSegment))) && ((!(segment2 instanceof ArrayAccessSegment) || ((ArrayAccessSegment) segment2).index >= 0) && !(segment2 instanceof FilterSegment) && !(segment instanceof WildCardSegment))) {
                        z2 = false;
                    }
                }
                Context context2 = new Context(context, z2);
                segment.extract(this, defaultJSONParser, context2);
                context = context2;
            } else {
                context.object = segment.eval(this, null, obj);
            }
            i2++;
        }
    }

    public Object getArrayItem(Object obj, int i2) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            if (i2 >= 0) {
                if (i2 < list.size()) {
                    return list.get(i2);
                }
                return null;
            }
            if (Math.abs(i2) <= list.size()) {
                return list.get(list.size() + i2);
            }
            return null;
        }
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            if (i2 >= 0) {
                if (i2 < length) {
                    return Array.get(obj, i2);
                }
                return null;
            }
            if (Math.abs(i2) <= length) {
                return Array.get(obj, length + i2);
            }
            return null;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            Object obj2 = map.get(Integer.valueOf(i2));
            return obj2 == null ? map.get(Integer.toString(i2)) : obj2;
        }
        if (!(obj instanceof Collection)) {
            throw new UnsupportedOperationException();
        }
        int i3 = 0;
        for (Object obj3 : (Collection) obj) {
            if (i3 == i2) {
                return obj3;
            }
            i3++;
        }
        return null;
    }

    public JavaBeanDeserializer getJavaBeanDeserializer(Class<?> cls) {
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(cls);
        if (deserializer instanceof JavaBeanDeserializer) {
            return (JavaBeanDeserializer) deserializer;
        }
        return null;
    }

    public JavaBeanSerializer getJavaBeanSerializer(Class<?> cls) {
        ObjectSerializer objectWriter = this.serializeConfig.getObjectWriter(cls);
        if (objectWriter instanceof JavaBeanSerializer) {
            return (JavaBeanSerializer) objectWriter;
        }
        return null;
    }

    public String getPath() {
        return this.path;
    }

    public Object getPropertyValue(Object obj, String str, long j2) {
        JSONArray jSONArray = null;
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            try {
                obj = JSON.parseObject((String) obj);
            } catch (Exception unused) {
            }
        }
        Object obj2 = obj;
        if (obj2 instanceof Map) {
            Map map = (Map) obj2;
            Object obj3 = map.get(str);
            return obj3 == null ? (SIZE == j2 || LENGTH == j2) ? Integer.valueOf(map.size()) : obj3 : obj3;
        }
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj2.getClass());
        if (javaBeanSerializer != null) {
            try {
                return javaBeanSerializer.getFieldValue(obj2, str, j2, false);
            } catch (Exception e2) {
                throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + str, e2);
            }
        }
        int i2 = 0;
        if (obj2 instanceof List) {
            List list = (List) obj2;
            if (SIZE == j2 || LENGTH == j2) {
                return Integer.valueOf(list.size());
            }
            while (i2 < list.size()) {
                Object obj4 = list.get(i2);
                if (obj4 == list) {
                    if (jSONArray == null) {
                        jSONArray = new JSONArray(list.size());
                    }
                    jSONArray.add(obj4);
                } else {
                    Object propertyValue = getPropertyValue(obj4, str, j2);
                    if (propertyValue instanceof Collection) {
                        Collection collection = (Collection) propertyValue;
                        if (jSONArray == null) {
                            jSONArray = new JSONArray(list.size());
                        }
                        jSONArray.addAll(collection);
                    } else if (propertyValue != null) {
                        if (jSONArray == null) {
                            jSONArray = new JSONArray(list.size());
                        }
                        jSONArray.add(propertyValue);
                    }
                }
                i2++;
            }
            return jSONArray == null ? Collections.emptyList() : jSONArray;
        }
        if (obj2 instanceof Object[]) {
            Object[] objArr = (Object[]) obj2;
            if (SIZE == j2 || LENGTH == j2) {
                return Integer.valueOf(objArr.length);
            }
            JSONArray jSONArray2 = new JSONArray(objArr.length);
            while (i2 < objArr.length) {
                Object[] objArr2 = objArr[i2];
                if (objArr2 == objArr) {
                    jSONArray2.add(objArr2);
                } else {
                    Object propertyValue2 = getPropertyValue(objArr2, str, j2);
                    if (propertyValue2 instanceof Collection) {
                        jSONArray2.addAll((Collection) propertyValue2);
                    } else if (propertyValue2 != null) {
                        jSONArray2.add(propertyValue2);
                    }
                }
                i2++;
            }
            return jSONArray2;
        }
        if (obj2 instanceof Enum) {
            Enum r8 = (Enum) obj2;
            if (-4270347329889690746L == j2) {
                return r8.name();
            }
            if (-1014497654951707614L == j2) {
                return Integer.valueOf(r8.ordinal());
            }
        }
        if (obj2 instanceof Calendar) {
            Calendar calendar = (Calendar) obj2;
            if (8963398325558730460L == j2) {
                return Integer.valueOf(calendar.get(1));
            }
            if (-811277319855450459L == j2) {
                return Integer.valueOf(calendar.get(2));
            }
            if (-3851359326990528739L == j2) {
                return Integer.valueOf(calendar.get(5));
            }
            if (4647432019745535567L == j2) {
                return Integer.valueOf(calendar.get(11));
            }
            if (6607618197526598121L == j2) {
                return Integer.valueOf(calendar.get(12));
            }
            if (-6586085717218287427L == j2) {
                return Integer.valueOf(calendar.get(13));
            }
        }
        return null;
    }

    public Collection<Object> getPropertyValues(Object obj) {
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj.getClass());
        if (javaBeanSerializer == null) {
            if (obj instanceof Map) {
                return ((Map) obj).values();
            }
            if (obj instanceof Collection) {
                return (Collection) obj;
            }
            throw new UnsupportedOperationException();
        }
        try {
            return javaBeanSerializer.getFieldValues(obj);
        } catch (Exception e2) {
            throw new JSONPathException("jsonpath error, path " + this.path, e2);
        }
    }

    public void init() {
        if (this.segments != null) {
            return;
        }
        if ("*".equals(this.path)) {
            this.segments = new Segment[]{WildCardSegment.instance};
            return;
        }
        JSONPathParser jSONPathParser = new JSONPathParser(this.path);
        this.segments = jSONPathParser.explain();
        this.hasRefSegment = jSONPathParser.hasRefSegment;
    }

    public boolean isRef() {
        init();
        int i2 = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return true;
            }
            Class<?> cls = segmentArr[i2].getClass();
            if (cls != ArrayAccessSegment.class && cls != PropertySegment.class) {
                return false;
            }
            i2++;
        }
    }

    public Set<?> keySet(Object obj) {
        if (obj == null) {
            return null;
        }
        init();
        int i2 = 0;
        Object objEval = obj;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return evalKeySet(objEval);
            }
            objEval = segmentArr[i2].eval(this, obj, objEval);
            i2++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x008d, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008e, code lost:
    
        if (r4 != null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0090, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0093, code lost:
    
        if ((r1 instanceof com.alibaba.fastjson.JSONPath.PropertySegment) == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0095, code lost:
    
        r1 = (com.alibaba.fastjson.JSONPath.PropertySegment) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0099, code lost:
    
        if ((r4 instanceof java.util.Collection) == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009b, code lost:
    
        r11 = r10.segments;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x009e, code lost:
    
        if (r11.length <= 1) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00a0, code lost:
    
        r11 = r11[r11.length - 2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00a7, code lost:
    
        if ((r11 instanceof com.alibaba.fastjson.JSONPath.RangeSegment) != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ab, code lost:
    
        if ((r11 instanceof com.alibaba.fastjson.JSONPath.MultiIndexSegment) == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ad, code lost:
    
        r11 = ((java.util.Collection) r4).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b7, code lost:
    
        if (r11.hasNext() == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c1, code lost:
    
        if (r1.remove(r10, r11.next()) == false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c3, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c5, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ca, code lost:
    
        return r1.remove(r10, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00cd, code lost:
    
        if ((r1 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00d5, code lost:
    
        return ((com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r1).remove(r10, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00d8, code lost:
    
        if ((r1 instanceof com.alibaba.fastjson.JSONPath.FilterSegment) == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00e0, code lost:
    
        return ((com.alibaba.fastjson.JSONPath.FilterSegment) r1).remove(r10, r11, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00e6, code lost:
    
        throw new java.lang.UnsupportedOperationException();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean remove(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.remove(java.lang.Object):boolean");
    }

    public boolean removeArrayItem(JSONPath jSONPath, Object obj, int i2) {
        if (!(obj instanceof List)) {
            throw new JSONPathException("unsupported set operation." + obj.getClass());
        }
        List list = (List) obj;
        if (i2 >= 0) {
            if (i2 >= list.size()) {
                return false;
            }
            list.remove(i2);
            return true;
        }
        int size = list.size() + i2;
        if (size < 0) {
            return false;
        }
        list.remove(size);
        return true;
    }

    public boolean removePropertyValue(Object obj, String str, boolean z2) {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            z = map.remove(str) != null;
            if (z2) {
                Iterator it = map.values().iterator();
                while (it.hasNext()) {
                    removePropertyValue(it.next(), str, z2);
                }
            }
            return z;
        }
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(obj.getClass());
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (javaBeanDeserializer == null) {
            if (z2) {
                return false;
            }
            throw new UnsupportedOperationException();
        }
        FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(str);
        if (fieldDeserializer != null) {
            fieldDeserializer.setValue(obj, (String) null);
        } else {
            z = false;
        }
        if (z2) {
            for (Object obj2 : getPropertyValues(obj)) {
                if (obj2 != null) {
                    removePropertyValue(obj2, str, z2);
                }
            }
        }
        return z;
    }

    public boolean set(Object obj, Object obj2) {
        return set(obj, obj2, true);
    }

    public boolean setArrayItem(JSONPath jSONPath, Object obj, int i2, Object obj2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (obj instanceof List) {
            List list = (List) obj;
            if (i2 >= 0) {
                list.set(i2, obj2);
            } else {
                list.set(list.size() + i2, obj2);
            }
            return true;
        }
        Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            throw new JSONPathException("unsupported set operation." + cls);
        }
        int length = Array.getLength(obj);
        if (i2 >= 0) {
            if (i2 < length) {
                Array.set(obj, i2, obj2);
            }
        } else if (Math.abs(i2) <= length) {
            Array.set(obj, length + i2, obj2);
        }
        return true;
    }

    public boolean setPropertyValue(Object obj, String str, long j2, Object obj2) {
        if (obj instanceof Map) {
            ((Map) obj).put(str, obj2);
            return true;
        }
        if (obj instanceof List) {
            for (Object obj3 : (List) obj) {
                if (obj3 != null) {
                    setPropertyValue(obj3, str, j2, obj2);
                }
            }
            return true;
        }
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(obj.getClass());
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (javaBeanDeserializer == null) {
            throw new UnsupportedOperationException();
        }
        FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(j2);
        if (fieldDeserializer == null) {
            return false;
        }
        fieldDeserializer.setValue(obj, obj2);
        return true;
    }

    public int size(Object obj) {
        if (obj == null) {
            return -1;
        }
        init();
        int i2 = 0;
        Object objEval = obj;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return evalSize(objEval);
            }
            objEval = segmentArr[i2].eval(this, obj, objEval);
            i2++;
        }
    }

    @Override // com.alibaba.fastjson.JSONAware
    public String toJSONString() {
        return JSON.toJSONString(this.path);
    }

    public JSONPath(String str, SerializeConfig serializeConfig, ParserConfig parserConfig) {
        if (str == null || str.length() == 0) {
            throw new JSONPathException("json-path can not be null or empty");
        }
        this.path = str;
        this.serializeConfig = serializeConfig;
        this.parserConfig = parserConfig;
    }

    public static Map<String, Object> paths(Object obj, SerializeConfig serializeConfig) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        HashMap map = new HashMap();
        paths(identityHashMap, map, "/", obj, serializeConfig);
        return map;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean set(java.lang.Object r9, java.lang.Object r10, boolean r11) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r8 = this;
            r11 = 0
            if (r9 != 0) goto L4
            return r11
        L4:
            r8.init()
            r0 = 0
            r2 = r9
            r1 = r11
            r3 = r0
        Lb:
            com.alibaba.fastjson.JSONPath$Segment[] r4 = r8.segments
            int r5 = r4.length
            r6 = 1
            if (r1 >= r5) goto L86
            r3 = r4[r1]
            java.lang.Object r4 = r3.eval(r8, r9, r2)
            if (r4 != 0) goto L81
            com.alibaba.fastjson.JSONPath$Segment[] r4 = r8.segments
            int r5 = r4.length
            int r5 = r5 - r6
            if (r1 >= r5) goto L24
            int r5 = r1 + 1
            r4 = r4[r5]
            goto L25
        L24:
            r4 = r0
        L25:
            boolean r5 = r4 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r5 == 0) goto L61
            boolean r4 = r3 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r4 == 0) goto L4b
            r4 = r3
            com.alibaba.fastjson.JSONPath$PropertySegment r4 = (com.alibaba.fastjson.JSONPath.PropertySegment) r4
            java.lang.String r4 = com.alibaba.fastjson.JSONPath.PropertySegment.access$400(r4)
            java.lang.Class r5 = r2.getClass()
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r5 = r8.getJavaBeanDeserializer(r5)
            if (r5 == 0) goto L4b
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r4 = r5.getFieldDeserializer(r4)
            com.alibaba.fastjson.util.FieldInfo r4 = r4.fieldInfo
            java.lang.Class<?> r4 = r4.fieldClass
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r5 = r8.getJavaBeanDeserializer(r4)
            goto L4d
        L4b:
            r4 = r0
            r5 = r4
        L4d:
            if (r5 == 0) goto L5b
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r5.beanInfo
            java.lang.reflect.Constructor<?> r7 = r7.defaultConstructor
            if (r7 == 0) goto L5a
            java.lang.Object r4 = r5.createInstance(r0, r4)
            goto L6c
        L5a:
            return r11
        L5b:
            com.alibaba.fastjson.JSONObject r4 = new com.alibaba.fastjson.JSONObject
            r4.<init>()
            goto L6c
        L61:
            boolean r4 = r4 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment
            if (r4 == 0) goto L6b
            com.alibaba.fastjson.JSONArray r4 = new com.alibaba.fastjson.JSONArray
            r4.<init>()
            goto L6c
        L6b:
            r4 = r0
        L6c:
            if (r4 == 0) goto L87
            boolean r5 = r3 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r5 == 0) goto L78
            com.alibaba.fastjson.JSONPath$PropertySegment r3 = (com.alibaba.fastjson.JSONPath.PropertySegment) r3
            r3.setValue(r8, r2, r4)
            goto L81
        L78:
            boolean r5 = r3 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment
            if (r5 == 0) goto L87
            com.alibaba.fastjson.JSONPath$ArrayAccessSegment r3 = (com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r3
            r3.setValue(r8, r2, r4)
        L81:
            int r1 = r1 + 1
            r3 = r2
            r2 = r4
            goto Lb
        L86:
            r2 = r3
        L87:
            if (r2 != 0) goto L8a
            return r11
        L8a:
            com.alibaba.fastjson.JSONPath$Segment[] r9 = r8.segments
            int r11 = r9.length
            int r11 = r11 - r6
            r9 = r9[r11]
            boolean r11 = r9 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r11 == 0) goto L9a
            com.alibaba.fastjson.JSONPath$PropertySegment r9 = (com.alibaba.fastjson.JSONPath.PropertySegment) r9
            r9.setValue(r8, r2, r10)
            return r6
        L9a:
            boolean r11 = r9 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment
            if (r11 == 0) goto La5
            com.alibaba.fastjson.JSONPath$ArrayAccessSegment r9 = (com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r9
            boolean r9 = r9.setValue(r8, r2, r10)
            return r9
        La5:
            java.lang.UnsupportedOperationException r9 = new java.lang.UnsupportedOperationException
            r9.<init>()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.set(java.lang.Object, java.lang.Object, boolean):boolean");
    }

    public static Object eval(Object obj, String str) {
        return compile(str).eval(obj);
    }

    public static Set<?> keySet(Object obj, String str) {
        JSONPath jSONPathCompile = compile(str);
        return jSONPathCompile.evalKeySet(jSONPathCompile.eval(obj));
    }

    private static void paths(Map<Object, String> map, Map<String, Object> map2, String str, Object obj, SerializeConfig serializeConfig) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        if (obj == null) {
            return;
        }
        int i2 = 0;
        if (map.put(obj, str) != null) {
            if (!((obj instanceof String) || (obj instanceof Number) || (obj instanceof Date) || (obj instanceof UUID))) {
                return;
            }
        }
        map2.put(str, obj);
        if (obj instanceof Map) {
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                if (key instanceof String) {
                    if (str.equals("/")) {
                        sb4 = new StringBuilder();
                    } else {
                        sb4 = new StringBuilder();
                        sb4.append(str);
                    }
                    sb4.append("/");
                    sb4.append(key);
                    paths(map, map2, sb4.toString(), entry.getValue(), serializeConfig);
                }
            }
            return;
        }
        if (obj instanceof Collection) {
            for (Object obj2 : (Collection) obj) {
                if (str.equals("/")) {
                    sb3 = new StringBuilder();
                } else {
                    sb3 = new StringBuilder();
                    sb3.append(str);
                }
                sb3.append("/");
                sb3.append(i2);
                paths(map, map2, sb3.toString(), obj2, serializeConfig);
                i2++;
            }
            return;
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            int length = Array.getLength(obj);
            while (i2 < length) {
                Object obj3 = Array.get(obj, i2);
                if (str.equals("/")) {
                    sb2 = new StringBuilder();
                } else {
                    sb2 = new StringBuilder();
                    sb2.append(str);
                }
                sb2.append("/");
                sb2.append(i2);
                paths(map, map2, sb2.toString(), obj3, serializeConfig);
                i2++;
            }
            return;
        }
        if (ParserConfig.isPrimitive2(cls) || cls.isEnum()) {
            return;
        }
        ObjectSerializer objectWriter = serializeConfig.getObjectWriter(cls);
        if (objectWriter instanceof JavaBeanSerializer) {
            try {
                for (Map.Entry<String, Object> entry2 : ((JavaBeanSerializer) objectWriter).getFieldValuesMap(obj).entrySet()) {
                    String key2 = entry2.getKey();
                    if (key2 instanceof String) {
                        if (str.equals("/")) {
                            sb = new StringBuilder();
                            sb.append("/");
                            sb.append(key2);
                        } else {
                            sb = new StringBuilder();
                            sb.append(str);
                            sb.append("/");
                            sb.append(key2);
                        }
                        paths(map, map2, sb.toString(), entry2.getValue(), serializeConfig);
                    }
                }
            } catch (Exception e2) {
                throw new JSONException("toJSON error", e2);
            }
        }
    }

    public static int size(Object obj, String str) {
        JSONPath jSONPathCompile = compile(str);
        return jSONPathCompile.evalSize(jSONPathCompile.eval(obj));
    }

    public static boolean contains(Object obj, String str) {
        if (obj == null) {
            return false;
        }
        return compile(str).contains(obj);
    }

    public static boolean containsValue(Object obj, String str, Object obj2) {
        return compile(str).containsValue(obj, obj2);
    }

    public static Object extract(String str, String str2, ParserConfig parserConfig, int i2, Feature... featureArr) {
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser(str, parserConfig, i2 | Feature.OrderedField.mask);
        Object objExtract = compile(str2).extract(defaultJSONParser);
        defaultJSONParser.lexer.close();
        return objExtract;
    }

    public static void arrayAdd(Object obj, String str, Object... objArr) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        compile(str).arrayAdd(obj, objArr);
    }

    public static Object extract(String str, String str2) {
        return extract(str, str2, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
    }

    public static boolean set(Object obj, String str, Object obj2) {
        return compile(str).set(obj, obj2);
    }

    public static boolean remove(Object obj, String str) {
        return compile(str).remove(obj);
    }
}
