package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls) {
        this(parserConfig, cls, cls);
    }

    private Object createFactoryInstance(ParserConfig parserConfig, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, obj);
    }

    public static JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        JSONType jSONType = javaBeanInfo.jsonType;
        if (jSONType == null) {
            return null;
        }
        for (Class<?> cls : jSONType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }

    public static boolean isSetFlag(int i2, int[] iArr) {
        if (iArr == null) {
            return false;
        }
        int i3 = i2 / 32;
        int i4 = i2 % 32;
        if (i3 < iArr.length) {
            if (((1 << i4) & iArr[i3]) != 0) {
                return true;
            }
        }
        return false;
    }

    public static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        int i2 = jSONLexerBase.token();
        if (i2 == 8) {
            jSONLexerBase.nextToken(16);
            jSONLexerBase.token();
            return;
        }
        if (i2 != 14) {
            defaultJSONParser.throwException(i2);
        }
        if (jSONLexerBase.getCurrent() == '[') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(14);
        } else {
            jSONLexerBase.nextToken(14);
        }
        if (jSONLexerBase.token() == 15) {
            jSONLexerBase.nextToken();
            return;
        }
        int i3 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, Integer.valueOf(i3)));
            i3++;
            if (jSONLexerBase.token() != 16) {
                break;
            }
            if (jSONLexerBase.getCurrent() == '[') {
                jSONLexerBase.next();
                jSONLexerBase.setToken(14);
            } else {
                jSONLexerBase.nextToken(14);
            }
        }
        int i4 = jSONLexerBase.token();
        if (i4 != 15) {
            defaultJSONParser.throwException(i4);
        }
        if (jSONLexerBase.getCurrent() != ',') {
            jSONLexerBase.nextToken(16);
        } else {
            jSONLexerBase.next();
            jSONLexerBase.setToken(16);
        }
    }

    public void check(JSONLexer jSONLexer, int i2) {
        if (jSONLexer.token() != i2) {
            throw new JSONException("syntax error");
        }
    }

    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        JavaBeanInfo javaBeanInfo = this.beanInfo;
        Constructor<?> constructor = javaBeanInfo.defaultConstructor;
        Object obj = null;
        if (constructor == null && javaBeanInfo.factoryMethod == null) {
            return null;
        }
        Method method = javaBeanInfo.factoryMethod;
        if (method != null && javaBeanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            if (javaBeanInfo.defaultConstructorParameterSize == 0) {
                objNewInstance = constructor != null ? constructor.newInstance(new Object[0]) : method.invoke(null, new Object[0]);
            } else {
                ParseContext context = defaultJSONParser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (!(type instanceof Class)) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                String name = ((Class) type).getName();
                String strSubstring = name.substring(0, name.lastIndexOf(36));
                Object obj2 = context.object;
                String name2 = obj2.getClass().getName();
                if (!name2.equals(strSubstring)) {
                    ParseContext parseContext = context.parent;
                    if (parseContext == null || parseContext.object == null || !("java.util.ArrayList".equals(name2) || "java.util.List".equals(name2) || "java.util.Collection".equals(name2) || "java.util.Map".equals(name2) || "java.util.HashMap".equals(name2))) {
                        obj = obj2;
                    } else if (parseContext.object.getClass().getName().equals(strSubstring)) {
                        obj = parseContext.object;
                    }
                    obj2 = obj;
                }
                if (obj2 == null || ((obj2 instanceof Collection) && ((Collection) obj2).isEmpty())) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                objNewInstance = constructor.newInstance(obj2);
            }
            if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                for (FieldInfo fieldInfo : this.beanInfo.fields) {
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(objNewInstance, "");
                        } catch (Exception e2) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e2);
                        }
                    }
                }
            }
            return objNewInstance;
        } catch (JSONException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e4);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException("error");
        }
        String strScanTypeName = jSONLexer.scanTypeName(defaultJSONParser.symbolTable);
        if (strScanTypeName != null) {
            ObjectDeserializer seeAlso = getSeeAlso(defaultJSONParser.getConfig(), this.beanInfo, strScanTypeName);
            if (seeAlso == null) {
                seeAlso = defaultJSONParser.getConfig().getDeserializer(defaultJSONParser.getConfig().checkAutoType(strScanTypeName, TypeUtils.getClass(type), jSONLexer.getFeatures()));
            }
            if (seeAlso instanceof JavaBeanDeserializer) {
                return (T) ((JavaBeanDeserializer) seeAlso).deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
            }
        }
        T t2 = (T) createInstance(defaultJSONParser, type);
        int length = this.sortedFieldDeserializers.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char c3 = i2 == length + (-1) ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i2];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t2, jSONLexer.scanInt(c3));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t2, jSONLexer.scanString(c3));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t2, jSONLexer.scanLong(c3));
            } else if (cls.isEnum()) {
                char current = jSONLexer.getCurrent();
                fieldDeserializer.setValue(t2, (current == '\"' || current == 'n') ? jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c3) : (current < '0' || current > '9') ? scanEnum(jSONLexer, c3) : ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c3)));
            } else if (cls == Boolean.TYPE) {
                fieldDeserializer.setValue(t2, jSONLexer.scanBoolean(c3));
            } else if (cls == Float.TYPE) {
                fieldDeserializer.setValue(t2, Float.valueOf(jSONLexer.scanFloat(c3)));
            } else if (cls == Double.TYPE) {
                fieldDeserializer.setValue(t2, Double.valueOf(jSONLexer.scanDouble(c3)));
            } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                fieldDeserializer.setValue(t2, new Date(jSONLexer.scanLong(c3)));
            } else if (cls == BigDecimal.class) {
                fieldDeserializer.setValue(t2, jSONLexer.scanDecimal(c3));
            } else {
                jSONLexer.nextToken(14);
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                fieldDeserializer.setValue(t2, defaultJSONParser.parseObject(fieldInfo.fieldType, fieldInfo.name));
                if (jSONLexer.token() == 15) {
                    break;
                }
                check(jSONLexer, c3 == ']' ? 15 : 16);
            }
            i2++;
        }
        jSONLexer.nextToken(16);
        return t2;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public FieldDeserializer getFieldDeserializer(String str) {
        return getFieldDeserializer(str, null);
    }

    public Type getFieldType(int i2) {
        return this.sortedFieldDeserializers[i2].fieldInfo.fieldType;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        return parseField(defaultJSONParser, str, obj, type, map, null);
    }

    public Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i2) {
        return parseRest(defaultJSONParser, type, obj, obj2, i2, new int[0]);
    }

    public Enum<?> scanEnum(JSONLexer jSONLexer, char c3) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    public FieldDeserializer smartMatch(String str) {
        return smartMatch(str, null);
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, JavaBeanInfo.build(cls, type, parserConfig.propertyNamingStrategy, parserConfig.fieldBased, parserConfig.compatibleWithJavaBean, parserConfig.isJacksonCompatible()));
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i2) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i2, null);
    }

    public FieldDeserializer getFieldDeserializer(String str, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        Map<String, FieldDeserializer> map = this.fieldDeserializerMap;
        if (map != null && (fieldDeserializer = map.get(str)) != null) {
            return fieldDeserializer;
        }
        int length = this.sortedFieldDeserializers.length - 1;
        int i2 = 0;
        while (i2 <= length) {
            int i3 = (i2 + length) >>> 1;
            int iCompareTo = this.sortedFieldDeserializers[i3].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i2 = i3 + 1;
            } else {
                if (iCompareTo <= 0) {
                    if (isSetFlag(i3, iArr)) {
                        return null;
                    }
                    return this.sortedFieldDeserializers[i3];
                }
                length = i3 - 1;
            }
        }
        Map<String, FieldDeserializer> map2 = this.alterNameFieldDeserializers;
        if (map2 != null) {
            return map2.get(str);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0113  */
    /* JADX WARN: Type inference failed for: r17v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Type inference failed for: r17v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean parseField(com.alibaba.fastjson.parser.DefaultJSONParser r22, java.lang.String r23, java.lang.Object r24, java.lang.reflect.Type r25, java.util.Map<java.lang.String, java.lang.Object> r26, int[] r27) throws java.lang.IllegalAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 578
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.String, java.lang.Object, java.lang.reflect.Type, java.util.Map, int[]):boolean");
    }

    public Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i2, int[] iArr) {
        return deserialze(defaultJSONParser, type, obj, obj2, i2, iArr);
    }

    public Enum scanEnum(JSONLexerBase jSONLexerBase, char[] cArr, ObjectDeserializer objectDeserializer) {
        EnumDeserializer enumDeserializer = objectDeserializer instanceof EnumDeserializer ? (EnumDeserializer) objectDeserializer : null;
        if (enumDeserializer == null) {
            jSONLexerBase.matchStat = -1;
            return null;
        }
        long jScanEnumSymbol = jSONLexerBase.scanEnumSymbol(cArr);
        if (jSONLexerBase.matchStat <= 0) {
            return null;
        }
        Enum enumByHashCode = enumDeserializer.getEnumByHashCode(jScanEnumSymbol);
        if (enumByHashCode == null) {
            if (jScanEnumSymbol == -3750763034362895579L) {
                return null;
            }
            if (jSONLexerBase.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return enumByHashCode;
    }

    public FieldDeserializer smartMatch(String str, int[] iArr) {
        boolean zStartsWith;
        if (str == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(str, iArr);
        if (fieldDeserializer == null) {
            long jFnv1a_64_lower = TypeUtils.fnv1a_64_lower(str);
            int i2 = 0;
            if (this.smartMatchHashArray == null) {
                long[] jArr = new long[this.sortedFieldDeserializers.length];
                int i3 = 0;
                while (true) {
                    FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                    if (i3 >= fieldDeserializerArr.length) {
                        break;
                    }
                    jArr[i3] = TypeUtils.fnv1a_64_lower(fieldDeserializerArr[i3].fieldInfo.name);
                    i3++;
                }
                Arrays.sort(jArr);
                this.smartMatchHashArray = jArr;
            }
            int iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, jFnv1a_64_lower);
            if (iBinarySearch < 0) {
                zStartsWith = str.startsWith("is");
                if (zStartsWith) {
                    iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(str.substring(2)));
                }
            } else {
                zStartsWith = false;
            }
            if (iBinarySearch >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    short[] sArr = new short[this.smartMatchHashArray.length];
                    Arrays.fill(sArr, (short) -1);
                    while (true) {
                        FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                        if (i2 >= fieldDeserializerArr2.length) {
                            break;
                        }
                        int iBinarySearch2 = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(fieldDeserializerArr2[i2].fieldInfo.name));
                        if (iBinarySearch2 >= 0) {
                            sArr[iBinarySearch2] = (short) i2;
                        }
                        i2++;
                    }
                    this.smartMatchHashArrayMapping = sArr;
                }
                short s2 = this.smartMatchHashArrayMapping[iBinarySearch];
                if (s2 != -1 && !isSetFlag(s2, iArr)) {
                    fieldDeserializer = this.sortedFieldDeserializers[s2];
                }
            }
            if (fieldDeserializer != null) {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
                    return null;
                }
                Class<?> cls = fieldInfo.fieldClass;
                if (zStartsWith && cls != Boolean.TYPE && cls != Boolean.class) {
                    return null;
                }
            }
        }
        return fieldDeserializer;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(6:137|138|(5:288|(4:290|(1:292)|295|(8:297|(5:691|311|(4:313|694|314|(2:714|316)(5:317|(0)(1:321)|406|693|659))(1:322)|323|(1:(1:409)(2:380|(9:382|(1:717)|401|699|402|(2:718|404)|406|693|659)(3:716|407|408)))(4:715|326|327|(5:329|(1:331)(2:332|(3:334|(2:337|362)|336)(3:338|(4:340|(2:341|(1:343)(1:754))|344|(2:346|336)(1:347))(4:348|(4:350|(4:353|(2:355|756)(1:757)|356|351)|755|357)|358|(1:360)(1:361))|362))|363|364|(4:366|(1:368)|369|370)(2:371|372))(2:373|374)))(1:413)|(1:430)(5:703|416|(3:418|684|419)(1:420)|421|(6:423|424|(3:(2:434|435)(5:436|(1:438)(1:(2:440|(1:450))(1:451))|(1:453)(1:454)|455|(2:719|457)(1:458))|476|(5:478|473|658|693|659)(2:479|(2:722|481)(6:653|654|(1:713)|658|693|659)))(5:459|(1:461)(1:465)|466|467|(2:469|(2:720|471)(5:472|473|658|693|659))(4:474|(3:721|662|663)|476|(0)(0)))|(1:673)|674|675)(1:427))|431|(0)(0)|(0)|674|675)(8:298|(1:300)|309|(0)(0)|(3:430|431|(0)(0))(0)|(0)|674|675))(1:293)|294|295|(0)(0))(5:142|(4:275|(1:280)(1:279)|281|(6:283|248|151|136|(0)(0)|(0)(0))(1:284))(1:(7:148|(0)|150|151|136|(0)(0)|(0)(0))(2:155|(2:164|(2:166|(6:168|150|151|136|(0)(0)|(0)(0))(6:(3:170|153|287)|154|135|136|(0)(0)|(0)(0)))(2:171|(2:173|(6:175|150|151|136|(0)(0)|(0)(0))(6:(3:177|153|287)|154|135|136|(0)(0)|(0)(0)))(2:178|(4:265|(1:267)(1:268)|269|(6:271|248|151|136|(0)(0)|(0)(0))(7:272|(3:274|286|287)|252|135|136|(0)(0)|(0)(0)))(2:183|(4:253|(1:258)(1:257)|259|(6:261|248|151|136|(0)(0)|(0)(0))(7:262|(3:264|286|287)|252|135|136|(0)(0)|(0)(0)))(12:188|(4:193|(2:211|(2:213|(6:215|150|151|136|(0)(0)|(0)(0))(7:216|(1:218)|154|135|136|(0)(0)|(0)(0)))(2:219|(2:221|(6:223|150|151|136|(0)(0)|(0)(0))(7:224|(1:226)|154|135|136|(0)(0)|(0)(0)))(2:227|(2:229|(6:231|150|151|136|(0)(0)|(0)(0))(7:232|(1:234)|154|135|136|(0)(0)|(0)(0)))(2:235|(1:237)(5:238|321|406|693|659)))))(2:200|(6:202|(1:204)(2:205|(1:207)(1:208))|209|136|(0)(0)|(0)(0))(1:210))|153|287)|239|(9:241|(1:243)|246|(7:249|(3:251|286|287)|252|135|136|(0)(0)|(0)(0))|248|151|136|(0)(0)|(0)(0))(1:244)|245|246|(0)|248|151|136|(0)(0)|(0)(0))))))(2:159|(6:161|150|151|136|(0)(0)|(0)(0))(6:(3:163|153|287)|154|135|136|(0)(0)|(0)(0)))))|(0)|674|675)|301|693|659) */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x01fd, code lost:
    
        if (r2 == (-2)) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x0360, code lost:
    
        if (r2 == (-2)) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x0509, code lost:
    
        r7 = r28;
        r1 = getSeeAlso(r7, r32.beanInfo, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x0511, code lost:
    
        if (r1 != null) goto L391;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0513, code lost:
    
        r14 = r7.checkAutoType(r0, com.alibaba.fastjson.util.TypeUtils.getClass(r34), r11.getFeatures());
        r1 = r33.getConfig().getDeserializer(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x0528, code lost:
    
        r14 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x0529, code lost:
    
        r2 = (T) r1.deserialze(r33, r14, r35);
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x052f, code lost:
    
        if ((r1 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L397;
     */
    /* JADX WARN: Code restructure failed: missing block: B:394:0x0531, code lost:
    
        r1 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x0533, code lost:
    
        if (r5 == null) goto L397;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x0535, code lost:
    
        r1.getFieldDeserializer(r5).setValue((java.lang.Object) r2, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x053c, code lost:
    
        if (r3 == null) goto L399;
     */
    /* JADX WARN: Code restructure failed: missing block: B:398:0x053e, code lost:
    
        r3.object = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x0542, code lost:
    
        r33.setContext(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0545, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0555, code lost:
    
        r20 = r6;
        r14 = r17;
        r12 = r29;
        r38 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:661:0x0944, code lost:
    
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r11.token()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:664:0x094d, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:665:0x094e, code lost:
    
        r6 = r20;
     */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x038f  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x03ba  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x059e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:430:0x05db  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x05e5  */
    /* JADX WARN: Removed duplicated region for block: B:459:0x0658  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x06b0  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x06b3 A[Catch: all -> 0x094d, TryCatch #9 {all -> 0x094d, blocks: (B:659:0x0913, B:476:0x06a8, B:479:0x06b3, B:481:0x06b9, B:654:0x08fe, B:656:0x0906, B:660:0x0926, B:661:0x0944, B:467:0x0688, B:469:0x068e, B:471:0x0694, B:474:0x06a0, B:662:0x0945, B:663:0x094c), top: B:693:0x0913 }] */
    /* JADX WARN: Removed duplicated region for block: B:608:0x0839 A[Catch: Exception -> 0x086c, all -> 0x08c9, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x086c, blocks: (B:592:0x0811, B:594:0x0817, B:608:0x0839), top: B:682:0x0811 }] */
    /* JADX WARN: Removed duplicated region for block: B:673:0x095f  */
    /* JADX WARN: Removed duplicated region for block: B:682:0x0811 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:691:0x03c8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r33, java.lang.reflect.Type r34, java.lang.Object r35, java.lang.Object r36, int r37, int[] r38) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) {
        this.clazz = javaBeanInfo.clazz;
        this.beanInfo = javaBeanInfo;
        FieldInfo[] fieldInfoArr = javaBeanInfo.sortedFields;
        this.sortedFieldDeserializers = new FieldDeserializer[fieldInfoArr.length];
        int length = fieldInfoArr.length;
        HashMap map = null;
        for (int i2 = 0; i2 < length; i2++) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i2];
            FieldDeserializer fieldDeserializerCreateFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, javaBeanInfo, fieldInfo);
            this.sortedFieldDeserializers[i2] = fieldDeserializerCreateFieldDeserializer;
            if (length > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializerCreateFieldDeserializer);
            }
            for (String str : fieldInfo.alternateNames) {
                if (map == null) {
                    map = new HashMap();
                }
                map.put(str, fieldDeserializerCreateFieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = map;
        FieldInfo[] fieldInfoArr2 = javaBeanInfo.fields;
        this.fieldDeserializers = new FieldDeserializer[fieldInfoArr2.length];
        int length2 = fieldInfoArr2.length;
        for (int i3 = 0; i3 < length2; i3++) {
            this.fieldDeserializers[i3] = getFieldDeserializer(javaBeanInfo.fields[i3].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(long j2) {
        int i2 = 0;
        if (this.hashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            int i3 = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i3 >= fieldDeserializerArr.length) {
                    break;
                }
                jArr[i3] = TypeUtils.fnv1a_64(fieldDeserializerArr[i3].fieldInfo.name);
                i3++;
            }
            Arrays.sort(jArr);
            this.hashArray = jArr;
        }
        int iBinarySearch = Arrays.binarySearch(this.hashArray, j2);
        if (iBinarySearch < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            while (true) {
                FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                if (i2 >= fieldDeserializerArr2.length) {
                    break;
                }
                int iBinarySearch2 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(fieldDeserializerArr2[i2].fieldInfo.name));
                if (iBinarySearch2 >= 0) {
                    sArr[iBinarySearch2] = (short) i2;
                }
                i2++;
            }
            this.hashArrayMapping = sArr;
        }
        short s2 = this.hashArrayMapping[iBinarySearch];
        if (s2 != -1) {
            return this.sortedFieldDeserializers[s2];
        }
        return null;
    }

    public Object createInstance(Map<String, Object> map, ParserConfig parserConfig) throws IllegalAccessException, InstantiationException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        Constructor<?> constructor;
        FieldInfo[] fieldInfoArr;
        FieldInfo[] fieldInfoArr2;
        Integer num;
        Object objCast;
        float f2;
        double d3;
        JavaBeanInfo javaBeanInfo = this.beanInfo;
        boolean z2 = true;
        if (javaBeanInfo.creatorConstructor == null && javaBeanInfo.factoryMethod == null) {
            Object objCreateInstance = createInstance((DefaultJSONParser) null, this.clazz);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                FieldDeserializer fieldDeserializerSmartMatch = smartMatch(key);
                if (fieldDeserializerSmartMatch != null) {
                    FieldInfo fieldInfo = fieldDeserializerSmartMatch.fieldInfo;
                    Field field = fieldInfo.field;
                    Type type = fieldInfo.fieldType;
                    if (field != null) {
                        Class<?> type2 = field.getType();
                        if (type2 == Boolean.TYPE) {
                            if (value == Boolean.FALSE) {
                                field.setBoolean(objCreateInstance, false);
                            } else if (value == Boolean.TRUE) {
                                field.setBoolean(objCreateInstance, true);
                            }
                        } else if (type2 == Integer.TYPE) {
                            if (value instanceof Number) {
                                field.setInt(objCreateInstance, ((Number) value).intValue());
                            }
                        } else if (type2 == Long.TYPE) {
                            if (value instanceof Number) {
                                field.setLong(objCreateInstance, ((Number) value).longValue());
                            }
                        } else if (type2 == Float.TYPE) {
                            if (value instanceof Number) {
                                field.setFloat(objCreateInstance, ((Number) value).floatValue());
                            } else if (value instanceof String) {
                                String str = (String) value;
                                if (str.length() <= 10) {
                                    f2 = TypeUtils.parseFloat(str);
                                } else {
                                    f2 = Float.parseFloat(str);
                                }
                                field.setFloat(objCreateInstance, f2);
                            }
                        } else if (type2 == Double.TYPE) {
                            if (value instanceof Number) {
                                field.setDouble(objCreateInstance, ((Number) value).doubleValue());
                            } else if (value instanceof String) {
                                String str2 = (String) value;
                                if (str2.length() <= 10) {
                                    d3 = TypeUtils.parseDouble(str2);
                                } else {
                                    d3 = Double.parseDouble(str2);
                                }
                                field.setDouble(objCreateInstance, d3);
                            }
                        } else if (value != null && type == value.getClass()) {
                            field.set(objCreateInstance, value);
                        }
                    }
                    String str3 = fieldInfo.format;
                    if (str3 != null && type == Date.class) {
                        objCast = TypeUtils.castToDate(value, str3);
                    } else if (str3 != null && (type instanceof Class) && ((Class) type).getName().equals("java.time.LocalDateTime")) {
                        objCast = TypeUtils.castToLocalDateTime(value, str3);
                    } else if (type instanceof ParameterizedType) {
                        objCast = TypeUtils.cast(value, (ParameterizedType) type, parserConfig);
                    } else {
                        objCast = TypeUtils.cast(value, type, parserConfig);
                    }
                    fieldDeserializerSmartMatch.setValue(objCreateInstance, objCast);
                }
            }
            Method method = this.beanInfo.buildMethod;
            if (method == null) {
                return objCreateInstance;
            }
            try {
                return method.invoke(objCreateInstance, new Object[0]);
            } catch (Exception e2) {
                throw new JSONException("build object error", e2);
            }
        }
        FieldInfo[] fieldInfoArr3 = javaBeanInfo.fields;
        int length = fieldInfoArr3.length;
        Object[] objArr = new Object[length];
        HashMap map2 = null;
        for (int i2 = 0; i2 < length; i2++) {
            FieldInfo fieldInfo2 = fieldInfoArr3[i2];
            Object objValueOf = map.get(fieldInfo2.name);
            if (objValueOf == null) {
                Class<?> cls = fieldInfo2.fieldClass;
                if (cls == Integer.TYPE) {
                    objValueOf = 0;
                } else if (cls == Long.TYPE) {
                    objValueOf = 0L;
                } else if (cls == Short.TYPE) {
                    objValueOf = (short) 0;
                } else if (cls == Byte.TYPE) {
                    objValueOf = (byte) 0;
                } else if (cls == Float.TYPE) {
                    objValueOf = Float.valueOf(0.0f);
                } else if (cls == Double.TYPE) {
                    objValueOf = Double.valueOf(0.0d);
                } else if (cls == Character.TYPE) {
                    objValueOf = '0';
                } else if (cls == Boolean.TYPE) {
                    objValueOf = Boolean.FALSE;
                }
                if (map2 == null) {
                    map2 = new HashMap();
                }
                map2.put(fieldInfo2.name, Integer.valueOf(i2));
            }
            objArr[i2] = objValueOf;
        }
        if (map2 != null) {
            for (Map.Entry<String, Object> entry2 : map.entrySet()) {
                String key2 = entry2.getKey();
                Object value2 = entry2.getValue();
                FieldDeserializer fieldDeserializerSmartMatch2 = smartMatch(key2);
                if (fieldDeserializerSmartMatch2 != null && (num = (Integer) map2.get(fieldDeserializerSmartMatch2.fieldInfo.name)) != null) {
                    objArr[num.intValue()] = value2;
                }
            }
        }
        JavaBeanInfo javaBeanInfo2 = this.beanInfo;
        if (javaBeanInfo2.creatorConstructor != null) {
            if (javaBeanInfo2.f2628kotlin) {
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    if (objArr[i3] != null || (fieldInfoArr2 = this.beanInfo.fields) == null || i3 >= fieldInfoArr2.length) {
                        i3++;
                    } else if (fieldInfoArr2[i3].fieldClass != String.class) {
                        break;
                    }
                }
                z2 = false;
            } else {
                z2 = false;
            }
            if (z2 && (constructor = this.beanInfo.kotlinDefaultConstructor) != null) {
                try {
                    Object objNewInstance = constructor.newInstance(new Object[0]);
                    for (int i4 = 0; i4 < length; i4++) {
                        Object obj = objArr[i4];
                        if (obj != null && (fieldInfoArr = this.beanInfo.fields) != null && i4 < fieldInfoArr.length) {
                            fieldInfoArr[i4].set(objNewInstance, obj);
                        }
                    }
                    return objNewInstance;
                } catch (Exception e3) {
                    throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e3);
                }
            }
            try {
                return this.beanInfo.creatorConstructor.newInstance(objArr);
            } catch (Exception e4) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e4);
            }
        }
        Method method2 = javaBeanInfo2.factoryMethod;
        if (method2 == null) {
            return null;
        }
        try {
            return method2.invoke(null, objArr);
        } catch (Exception e5) {
            throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e5);
        }
    }
}
