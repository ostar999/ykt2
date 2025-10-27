package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    private int objectKeyLevel;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    static {
        Class<?>[] clsArr = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class};
        for (int i2 = 0; i2 < 17; i2++) {
            primitiveClasses.add(clsArr[i2]);
        }
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    private void addContext(ParseContext parseContext) {
        int i2 = this.contextArrayIndex;
        this.contextArrayIndex = i2 + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i2 >= parseContextArr.length) {
            ParseContext[] parseContextArr2 = new ParseContext[(parseContextArr.length * 3) / 2];
            System.arraycopy(parseContextArr, 0, parseContextArr2, 0, parseContextArr.length);
            this.contextArray = parseContextArr2;
        }
        this.contextArray[i2] = parseContext;
    }

    public final void accept(int i2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i2) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i2) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public void acceptType(String str) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (!str.equals(jSONLexer.stringVal())) {
            throw new JSONException("type not match error");
        }
        jSONLexer.nextToken();
        if (jSONLexer.token() == 16) {
            jSONLexer.nextToken();
        }
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public void checkListResolve(Collection collection) {
        if (this.resolveStatus == 1) {
            if (!(collection instanceof List)) {
                ResolveTask lastResolveTask = getLastResolveTask();
                lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(collection);
                lastResolveTask.ownerContext = this.context;
                setResolveStatus(0);
                return;
            }
            int size = collection.size() - 1;
            ResolveTask lastResolveTask2 = getLastResolveTask();
            lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, size);
            lastResolveTask2.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map map, Object obj) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        } finally {
            jSONLexer.close();
        }
    }

    public void config(Feature feature, boolean z2) {
        this.lexer.config(feature, z2);
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public ParseContext getContext() {
        return this.context;
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat = simpleDateFormat;
            simpleDateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public String getInput() {
        Object obj = this.input;
        return obj instanceof char[] ? new String((char[]) obj) : obj.toString();
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(r0.size() - 1);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public Object getObject(String str) {
        for (int i2 = 0; i2 < this.contextArrayIndex; i2++) {
            if (str.equals(this.contextArray[i2].toString())) {
                return this.contextArray[i2].object;
            }
        }
        return null;
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public void handleResovleTask(Object obj) {
        Object objEval;
        FieldInfo fieldInfo;
        List<ResolveTask> list = this.resolveTaskList;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            ResolveTask resolveTask = this.resolveTaskList.get(i2);
            String str = resolveTask.referenceValue;
            ParseContext parseContext = resolveTask.ownerContext;
            Object obj2 = parseContext != null ? parseContext.object : null;
            if (str.startsWith("$")) {
                objEval = getObject(str);
                if (objEval == null) {
                    try {
                        JSONPath jSONPathCompile = JSONPath.compile(str);
                        if (jSONPathCompile.isRef()) {
                            objEval = jSONPathCompile.eval(obj);
                        }
                    } catch (JSONPathException unused) {
                    }
                }
            } else {
                objEval = resolveTask.context.object;
            }
            FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
            if (fieldDeserializer != null) {
                if (objEval != null && objEval.getClass() == JSONObject.class && (fieldInfo = fieldDeserializer.fieldInfo) != null && !Map.class.isAssignableFrom(fieldInfo.fieldClass)) {
                    Object obj3 = this.contextArray[0].object;
                    JSONPath jSONPathCompile2 = JSONPath.compile(str);
                    if (jSONPathCompile2.isRef()) {
                        objEval = jSONPathCompile2.eval(obj3);
                    }
                }
                fieldDeserializer.setValue(obj2, objEval);
            }
        }
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public Object parse() {
        return parse(null);
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type " + type);
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class<?>) type2, (Collection) arrayList);
            return arrayList;
        }
        if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (!Object.class.equals(type3)) {
                ArrayList arrayList2 = new ArrayList();
                parseArray((Class<?>) type3, (Collection) arrayList2);
                return arrayList2;
            }
            if (wildcardType.getLowerBounds().length == 0) {
                return parse();
            }
            throw new JSONException("not support type : " + type);
        }
        if (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }
            Type type4 = bounds[0];
            if (type4 instanceof Class) {
                ArrayList arrayList3 = new ArrayList();
                parseArray((Class<?>) type4, (Collection) arrayList3);
                return arrayList3;
            }
        }
        if (type2 instanceof ParameterizedType) {
            ArrayList arrayList4 = new ArrayList();
            parseArray((ParameterizedType) type2, arrayList4);
            return arrayList4;
        }
        throw new JSONException("TODO : " + type);
    }

    public void parseExtra(Object obj, String str) {
        this.lexer.nextTokenWithColon();
        List<ExtraTypeProvider> list = this.extraTypeProviders;
        Type extraType = null;
        if (list != null) {
            Iterator<ExtraTypeProvider> it = list.iterator();
            while (it.hasNext()) {
                extraType = it.next().getExtraType(obj, str);
            }
        }
        Object object = extraType == null ? parse() : parseObject(extraType);
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, object);
            return;
        }
        List<ExtraProcessor> list2 = this.extraProcessors;
        if (list2 != null) {
            Iterator<ExtraProcessor> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().processExtra(obj, str, object);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    public Object parseKey() {
        if (this.lexer.token() != 18) {
            return parse(null);
        }
        String strStringVal = this.lexer.stringVal();
        this.lexer.nextToken(16);
        return strStringVal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:143:0x0292, code lost:
    
        r5.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x029d, code lost:
    
        if (r5.token() != 13) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x029f, code lost:
    
        r5.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02aa, code lost:
    
        if ((r18.config.getDeserializer(r8) instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02ac, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r19, (java.lang.Class<java.lang.Object>) r8, r18.config);
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02b3, code lost:
    
        r0 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02b4, code lost:
    
        if (r0 != null) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02b8, code lost:
    
        if (r8 != java.lang.Cloneable.class) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02ba, code lost:
    
        r0 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02c6, code lost:
    
        if ("java.util.Collections$EmptyMap".equals(r7) == false) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02c8, code lost:
    
        r0 = java.util.Collections.emptyMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02d3, code lost:
    
        if ("java.util.Collections$UnmodifiableMap".equals(r7) == false) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02d5, code lost:
    
        r0 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02df, code lost:
    
        r0 = r8.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02e6, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02e7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x02ef, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x02f0, code lost:
    
        setResolveStatus(2);
        r3 = r18.context;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x02f6, code lost:
    
        if (r3 == null) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x02f8, code lost:
    
        if (r20 == null) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x02fc, code lost:
    
        if ((r20 instanceof java.lang.Integer) != false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0302, code lost:
    
        if ((r3.fieldName instanceof java.lang.Integer) != false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0304, code lost:
    
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x030b, code lost:
    
        if (r19.size() <= 0) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x030d, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r19, (java.lang.Class<java.lang.Object>) r8, r18.config);
        setResolveStatus(0);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x031d, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x031e, code lost:
    
        r0 = r18.config.getDeserializer(r8);
        r3 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x032e, code lost:
    
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r3) == false) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0332, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0336, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0338, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x033f, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0341, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x034c, code lost:
    
        return r0.deserialze(r18, r8, r20);
     */
    /* JADX WARN: Removed duplicated region for block: B:115:0x021b A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0443 A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:266:0x046a A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x04c6 A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:341:0x05c4  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x05c8 A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:348:0x05d4 A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x05e0 A[Catch: all -> 0x067f, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x05f5 A[Catch: all -> 0x067f, TRY_ENTER, TryCatch #2 {all -> 0x067f, blocks: (B:24:0x0074, B:26:0x0078, B:29:0x0082, B:32:0x0095, B:36:0x00aa, B:115:0x021b, B:116:0x0221, B:118:0x022c, B:120:0x0234, B:124:0x024a, B:126:0x0258, B:142:0x028c, B:143:0x0292, B:145:0x029f, B:146:0x02a2, B:148:0x02ac, B:153:0x02ba, B:154:0x02c0, B:156:0x02c8, B:157:0x02cd, B:159:0x02d5, B:160:0x02df, B:164:0x02e8, B:165:0x02ef, B:166:0x02f0, B:169:0x02fa, B:171:0x02fe, B:173:0x0304, B:174:0x0307, B:176:0x030d, B:179:0x031e, B:185:0x0338, B:189:0x0345, B:186:0x033d, B:188:0x0341, B:128:0x025f, B:130:0x0265, B:135:0x0272, B:139:0x027c, B:197:0x0356, B:199:0x035c, B:201:0x0364, B:203:0x036e, B:205:0x037f, B:207:0x038a, B:209:0x0392, B:211:0x0396, B:213:0x039c, B:216:0x03a1, B:218:0x03a5, B:238:0x03f3, B:240:0x03fb, B:243:0x0404, B:244:0x041e, B:220:0x03aa, B:222:0x03b2, B:225:0x03b8, B:226:0x03c5, B:229:0x03ce, B:232:0x03d4, B:235:0x03d9, B:236:0x03e6, B:245:0x041f, B:246:0x043d, B:249:0x0443, B:251:0x0447, B:253:0x044b, B:256:0x0451, B:260:0x045a, B:266:0x046a, B:268:0x0479, B:270:0x0484, B:271:0x048c, B:272:0x048f, B:284:0x04bb, B:286:0x04c6, B:290:0x04d3, B:293:0x04e3, B:294:0x0503, B:279:0x049f, B:281:0x04a9, B:283:0x04b8, B:282:0x04ae, B:297:0x0508, B:299:0x0512, B:301:0x051a, B:302:0x051d, B:304:0x0528, B:305:0x052c, B:307:0x0537, B:310:0x053e, B:313:0x054b, B:314:0x0550, B:317:0x0555, B:319:0x055a, B:323:0x0566, B:325:0x056e, B:327:0x0581, B:331:0x059c, B:333:0x05a2, B:336:0x05a8, B:338:0x05ae, B:340:0x05b6, B:343:0x05c8, B:346:0x05d0, B:348:0x05d4, B:349:0x05db, B:351:0x05e0, B:352:0x05e3, B:354:0x05eb, B:357:0x05f5, B:360:0x05ff, B:361:0x0604, B:362:0x0609, B:363:0x0623, B:328:0x058a, B:329:0x058f, B:364:0x0624, B:366:0x0636, B:369:0x063d, B:372:0x064a, B:373:0x066a, B:39:0x00be, B:40:0x00dc, B:43:0x00e1, B:45:0x00ec, B:47:0x00f0, B:49:0x00f4, B:52:0x00fa, B:59:0x0109, B:61:0x0111, B:64:0x0123, B:65:0x013b, B:66:0x013c, B:67:0x0141, B:78:0x0156, B:79:0x015c, B:81:0x0163, B:83:0x016e, B:90:0x0180, B:93:0x018a, B:94:0x01a2, B:88:0x017b, B:82:0x0168, B:95:0x01a3, B:96:0x01bb, B:102:0x01c5, B:104:0x01cd, B:107:0x01e0, B:108:0x0200, B:109:0x0201, B:110:0x0206, B:111:0x0207, B:113:0x0211, B:374:0x066b, B:375:0x0672, B:376:0x0673, B:377:0x0678, B:378:0x0679, B:379:0x067e), top: B:386:0x0074, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:404:0x04cf A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:407:0x05eb A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object parseObject(java.util.Map r19, java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 1668
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        int i2 = this.contextArrayIndex;
        if (i2 <= 0) {
            return;
        }
        int i3 = i2 - 1;
        this.contextArrayIndex = i3;
        this.contextArray[i3] = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object resolveReference(java.lang.String r5) {
        /*
            r4 = this;
            com.alibaba.fastjson.parser.ParseContext[] r0 = r4.contextArray
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            r0 = 0
        L7:
            com.alibaba.fastjson.parser.ParseContext[] r2 = r4.contextArray
            int r3 = r2.length
            if (r0 >= r3) goto L22
            int r3 = r4.contextArrayIndex
            if (r0 >= r3) goto L22
            r2 = r2[r0]
            java.lang.String r3 = r2.toString()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L1f
            java.lang.Object r5 = r2.object
            return r5
        L1f:
            int r0 = r0 + 1
            goto L7
        L22:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.resolveReference(java.lang.String):java.lang.Object");
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = parseContext;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setResolveStatus(int i2) {
        this.resolveStatus = i2;
    }

    public void throwException(int i2) {
        throw new JSONException("syntax error, expect " + JSONToken.name(i2) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(str, new JSONScanner(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public Object parse(Object obj) {
        JSONLexer jSONLexer = this.lexer;
        int i2 = jSONLexer.token();
        if (i2 == 2) {
            Number numberIntegerValue = jSONLexer.integerValue();
            jSONLexer.nextToken();
            return numberIntegerValue;
        }
        if (i2 == 3) {
            Number numberDecimalValue = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
            jSONLexer.nextToken();
            return numberDecimalValue;
        }
        if (i2 == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                JSONScanner jSONScanner = new JSONScanner(strStringVal);
                try {
                    if (jSONScanner.scanISO8601DateIfMatch()) {
                        return jSONScanner.getCalendar().getTime();
                    }
                } finally {
                    jSONScanner.close();
                }
            }
            return strStringVal;
        }
        if (i2 == 12) {
            return parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), obj);
        }
        if (i2 == 14) {
            JSONArray jSONArray = new JSONArray();
            parseArray(jSONArray, obj);
            return jSONLexer.isEnabled(Feature.UseObjectArray) ? jSONArray.toArray() : jSONArray;
        }
        if (i2 == 18) {
            if ("NaN".equals(jSONLexer.stringVal())) {
                jSONLexer.nextToken();
                return null;
            }
            throw new JSONException("syntax error, " + jSONLexer.info());
        }
        if (i2 == 26) {
            byte[] bArrBytesValue = jSONLexer.bytesValue();
            jSONLexer.nextToken();
            return bArrBytesValue;
        }
        switch (i2) {
            case 6:
                jSONLexer.nextToken();
                return Boolean.TRUE;
            case 7:
                jSONLexer.nextToken();
                return Boolean.FALSE;
            case 8:
                jSONLexer.nextToken();
                return null;
            case 9:
                jSONLexer.nextToken(18);
                if (jSONLexer.token() != 18) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken(10);
                accept(10);
                long jLongValue = jSONLexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(jLongValue);
            default:
                switch (i2) {
                    case 20:
                        if (jSONLexer.isBlankInput()) {
                            return null;
                        }
                        throw new JSONException("unterminated json string, " + jSONLexer.info());
                    case 21:
                        jSONLexer.nextToken();
                        HashSet hashSet = new HashSet();
                        parseArray(hashSet, obj);
                        return hashSet;
                    case 22:
                        jSONLexer.nextToken();
                        TreeSet treeSet = new TreeSet();
                        parseArray(treeSet, obj);
                        return treeSet;
                    case 23:
                        jSONLexer.nextToken();
                        return null;
                    default:
                        throw new JSONException("syntax error, " + jSONLexer.info());
                }
        }
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i2) {
        this(str, new JSONScanner(str, i2), parserConfig);
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public ParseContext setContext(Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, obj, obj2);
    }

    public DefaultJSONParser(char[] cArr, int i2, ParserConfig parserConfig, int i3) {
        this(cArr, new JSONScanner(cArr, i2, i3), parserConfig);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer deserializer;
        int i2 = this.lexer.token();
        if (i2 == 21 || i2 == 22) {
            this.lexer.nextToken();
            i2 = this.lexer.token();
        }
        if (i2 == 14) {
            if (Integer.TYPE == type) {
                deserializer = IntegerCodec.instance;
                this.lexer.nextToken(2);
            } else if (String.class == type) {
                deserializer = StringCodec.instance;
                this.lexer.nextToken(4);
            } else {
                deserializer = this.config.getDeserializer(type);
                this.lexer.nextToken(deserializer.getFastMatchToken());
            }
            ParseContext parseContext = this.context;
            setContext(collection, obj);
            int i3 = 0;
            while (true) {
                try {
                    if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (this.lexer.token() == 16) {
                            this.lexer.nextToken();
                        }
                    }
                    if (this.lexer.token() == 15) {
                        setContext(parseContext);
                        this.lexer.nextToken(16);
                        return;
                    }
                    Object objDeserialze = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            objDeserialze = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj2 = parse();
                            if (obj2 != null) {
                                objDeserialze = obj2.toString();
                            }
                        }
                        collection.add(objDeserialze);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                        } else {
                            objDeserialze = deserializer.deserialze(this, type, Integer.valueOf(i3));
                        }
                        collection.add(objDeserialze);
                        checkListResolve(collection);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i3++;
                } catch (Throwable th) {
                    setContext(parseContext);
                    throw th;
                }
            }
        } else {
            throw new JSONException("expect '[', but " + JSONToken.name(i2) + ", " + this.lexer.info());
        }
    }

    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        ParseContext parseContext2 = new ParseContext(parseContext, obj, obj2);
        this.context = parseContext2;
        addContext(parseContext2);
        return this.context;
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this((Object) null, jSONLexer, parserConfig);
    }

    public final void accept(int i2, int i3) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i2) {
            jSONLexer.nextToken(i3);
        } else {
            throwException(i2);
        }
    }

    public DefaultJSONParser(Object obj, JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.objectKeyLevel = 0;
        this.autoTypeAccept = null;
        this.lexer = jSONLexer;
        this.input = obj;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char current = jSONLexer.getCurrent();
        if (current == '{') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 12;
        } else if (current == '[') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object objCast;
        Class<?> componentType;
        boolean zIsArray;
        Class cls;
        int i2 = 8;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i3 = 14;
        if (this.lexer.token() == 14) {
            Object[] objArr = new Object[typeArr.length];
            if (typeArr.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token() == 15) {
                    this.lexer.nextToken(16);
                    return new Object[0];
                }
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(2);
            int i4 = 0;
            while (i4 < typeArr.length) {
                if (this.lexer.token() == i2) {
                    this.lexer.nextToken(16);
                    objCast = null;
                } else {
                    Type type = typeArr[i4];
                    if (type != Integer.TYPE && type != Integer.class) {
                        if (type == String.class) {
                            if (this.lexer.token() == 4) {
                                objCast = this.lexer.stringVal();
                                this.lexer.nextToken(16);
                            } else {
                                objCast = TypeUtils.cast(parse(), type, this.config);
                            }
                        } else {
                            if (i4 == typeArr.length - 1 && (type instanceof Class) && (((cls = (Class) type) != byte[].class && cls != char[].class) || this.lexer.token() != 4)) {
                                zIsArray = cls.isArray();
                                componentType = cls.getComponentType();
                            } else {
                                componentType = null;
                                zIsArray = false;
                            }
                            if (zIsArray && this.lexer.token() != i3) {
                                ArrayList arrayList = new ArrayList();
                                ObjectDeserializer deserializer = this.config.getDeserializer(componentType);
                                int fastMatchToken = deserializer.getFastMatchToken();
                                if (this.lexer.token() != 15) {
                                    while (true) {
                                        arrayList.add(deserializer.deserialze(this, type, null));
                                        if (this.lexer.token() != 16) {
                                            break;
                                        }
                                        this.lexer.nextToken(fastMatchToken);
                                    }
                                    if (this.lexer.token() != 15) {
                                        throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                                    }
                                }
                                objCast = TypeUtils.cast(arrayList, type, this.config);
                            } else {
                                objCast = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i4));
                            }
                        }
                    } else if (this.lexer.token() == 2) {
                        objCast = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                    } else {
                        objCast = TypeUtils.cast(parse(), type, this.config);
                    }
                }
                objArr[i4] = objCast;
                if (this.lexer.token() == 15) {
                    break;
                }
                if (this.lexer.token() == 16) {
                    if (i4 == typeArr.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                    i4++;
                    i2 = 8;
                    i3 = 14;
                } else {
                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                }
            }
            if (this.lexer.token() == 15) {
                this.lexer.nextToken(16);
                return objArr;
            }
            throw new JSONException("syntax error");
        }
        throw new JSONException("syntax error : " + this.lexer.tokenName());
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x0236, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable r11, java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 613
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable, java.lang.Object):java.lang.Object");
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    public final void parseArray(Collection collection, Object obj) {
        Object object;
        Number numberDecimalValue;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() == 14) {
            jSONLexer.nextToken(4);
            ParseContext parseContext = this.context;
            if (parseContext != null && parseContext.level > 512) {
                throw new JSONException("array level > 512");
            }
            setContext(collection, obj);
            int i2 = 0;
            while (true) {
                try {
                    if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (jSONLexer.token() == 16) {
                            jSONLexer.nextToken();
                        }
                    }
                    int i3 = jSONLexer.token();
                    if (i3 == 2) {
                        Number numberIntegerValue = jSONLexer.integerValue();
                        jSONLexer.nextToken(16);
                        object = numberIntegerValue;
                    } else if (i3 == 3) {
                        if (jSONLexer.isEnabled(Feature.UseBigDecimal)) {
                            numberDecimalValue = jSONLexer.decimalValue(true);
                        } else {
                            numberDecimalValue = jSONLexer.decimalValue(false);
                        }
                        object = numberDecimalValue;
                        jSONLexer.nextToken(16);
                    } else if (i3 == 4) {
                        String strStringVal = jSONLexer.stringVal();
                        jSONLexer.nextToken(16);
                        object = strStringVal;
                        if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                            JSONScanner jSONScanner = new JSONScanner(strStringVal);
                            Object time = strStringVal;
                            if (jSONScanner.scanISO8601DateIfMatch()) {
                                time = jSONScanner.getCalendar().getTime();
                            }
                            jSONScanner.close();
                            object = time;
                        }
                    } else if (i3 == 6) {
                        Boolean bool = Boolean.TRUE;
                        jSONLexer.nextToken(16);
                        object = bool;
                    } else if (i3 != 7) {
                        object = null;
                        object = null;
                        if (i3 == 8) {
                            jSONLexer.nextToken(4);
                        } else if (i3 == 12) {
                            object = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i2));
                        } else {
                            if (i3 == 20) {
                                throw new JSONException("unclosed jsonArray");
                            }
                            if (i3 == 23) {
                                jSONLexer.nextToken(4);
                            } else if (i3 == 14) {
                                JSONArray jSONArray = new JSONArray();
                                parseArray(jSONArray, Integer.valueOf(i2));
                                object = jSONArray;
                                if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                    object = jSONArray.toArray();
                                }
                            } else if (i3 != 15) {
                                object = parse();
                            } else {
                                jSONLexer.nextToken(16);
                                return;
                            }
                        }
                    } else {
                        Boolean bool2 = Boolean.FALSE;
                        jSONLexer.nextToken(16);
                        object = bool2;
                    }
                    collection.add(object);
                    checkListResolve(collection);
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(4);
                    }
                    i2++;
                } finally {
                    setContext(parseContext);
                }
            }
        } else {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i2 = this.lexer.token();
        if (i2 == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (i2 == 4) {
            if (type == byte[].class) {
                T t2 = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t2;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() == JavaBeanDeserializer.class) {
                if (this.lexer.token() != 12 && this.lexer.token() != 14) {
                    throw new JSONException("syntax error,except start with { or [,but actually start with " + this.lexer.tokenName());
                }
                return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
            }
            return (T) deserializer.deserialze(this, type, obj);
        } catch (JSONException e2) {
            throw e2;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public void parseObject(Object obj) {
        Object objDeserialze;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String strScanSymbol = this.lexer.scanSymbol(this.symbolTable);
            if (strScanSymbol == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(strScanSymbol) : null;
            if (fieldDeserializer == null) {
                if (this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    this.lexer.nextTokenWithColon();
                    parse();
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken();
                        return;
                    }
                } else {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + strScanSymbol);
                }
            } else {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                Class<?> cls2 = fieldInfo.fieldClass;
                Type type = fieldInfo.fieldType;
                if (cls2 == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else if (cls2 == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    objDeserialze = StringCodec.deserialze(this);
                } else if (cls2 == Long.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = LongCodec.instance.deserialze(this, type, null);
                } else {
                    ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                    this.lexer.nextTokenWithColon(deserializer2.getFastMatchToken());
                    objDeserialze = deserializer2.deserialze(this, type, null);
                }
                fieldDeserializer.setValue(obj, objDeserialze);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        Object object = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        if (object == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) object);
    }
}
