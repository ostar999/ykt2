package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (Map<String, String>) null);
    }

    public static Map<String, String> createAliasMap(String... strArr) {
        HashMap map = new HashMap();
        for (String str : strArr) {
            map.put(str, str);
        }
        return map;
    }

    public boolean applyLabel(JSONSerializer jSONSerializer, String str) {
        List<LabelFilter> list = jSONSerializer.labelFilters;
        if (list != null) {
            Iterator<LabelFilter> it = list.iterator();
            while (it.hasNext()) {
                if (!it.next().apply(str)) {
                    return false;
                }
            }
        }
        List<LabelFilter> list2 = this.labelFilters;
        if (list2 == null) {
            return true;
        }
        Iterator<LabelFilter> it2 = list2.iterator();
        while (it2.hasNext()) {
            if (!it2.next().apply(str)) {
                return false;
            }
        }
        return true;
    }

    public BeanContext getBeanContext(int i2) {
        return this.sortedGetters[i2].fieldContext;
    }

    public Set<String> getFieldNames(Object obj) throws Exception {
        HashSet hashSet = new HashSet();
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                hashSet.add(fieldSerializer.fieldInfo.name);
            }
        }
        return hashSet;
    }

    public FieldSerializer getFieldSerializer(String str) {
        if (str == null) {
            return null;
        }
        int length = this.sortedGetters.length - 1;
        int i2 = 0;
        while (i2 <= length) {
            int i3 = (i2 + length) >>> 1;
            int iCompareTo = this.sortedGetters[i3].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i2 = i3 + 1;
            } else {
                if (iCompareTo <= 0) {
                    return this.sortedGetters[i3];
                }
                length = i3 - 1;
            }
        }
        return null;
    }

    public Type getFieldType(int i2) {
        return this.sortedGetters[i2].fieldInfo.fieldType;
    }

    public Object getFieldValue(Object obj, String str) {
        FieldSerializer fieldSerializer = getFieldSerializer(str);
        if (fieldSerializer == null) {
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        } catch (InvocationTargetException e3) {
            throw new JSONException("getFieldValue error." + str, e3);
        }
    }

    public List<Object> getFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            arrayList.add(fieldSerializer.getPropertyValue(obj));
        }
        return arrayList;
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            boolean zIsEnabled = SerializerFeature.isEnabled(fieldSerializer.features, SerializerFeature.SkipTransientField);
            FieldInfo fieldInfo = fieldSerializer.fieldInfo;
            if (!zIsEnabled || fieldInfo == null || !fieldInfo.fieldTransient) {
                if (fieldInfo.unwrapped) {
                    Object json = JSON.toJSON(fieldSerializer.getPropertyValue(obj));
                    if (json instanceof Map) {
                        linkedHashMap.putAll((Map) json);
                    } else {
                        linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
                    }
                } else {
                    linkedHashMap.put(fieldInfo.name, fieldSerializer.getPropertyValue(obj));
                }
            }
        }
        return linkedHashMap;
    }

    public List<Object> getObjectFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            Class<?> cls = fieldSerializer.fieldInfo.fieldClass;
            if (!cls.isPrimitive() && !cls.getName().startsWith("java.lang.")) {
                arrayList.add(fieldSerializer.getPropertyValue(obj));
            }
        }
        return arrayList;
    }

    public int getSize(Object obj) throws Exception {
        int i2 = 0;
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                i2++;
            }
        }
        return i2;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    public boolean isWriteAsArray(JSONSerializer jSONSerializer) {
        return isWriteAsArray(jSONSerializer, 0);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2, false);
    }

    public char writeAfter(JSONSerializer jSONSerializer, Object obj, char c3) {
        List<AfterFilter> list = jSONSerializer.afterFilters;
        if (list != null) {
            Iterator<AfterFilter> it = list.iterator();
            while (it.hasNext()) {
                c3 = it.next().writeAfter(jSONSerializer, obj, c3);
            }
        }
        List<AfterFilter> list2 = this.afterFilters;
        if (list2 != null) {
            Iterator<AfterFilter> it2 = list2.iterator();
            while (it2.hasNext()) {
                c3 = it2.next().writeAfter(jSONSerializer, obj, c3);
            }
        }
        return c3;
    }

    public void writeAsArray(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2);
    }

    public void writeAsArrayNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2);
    }

    public char writeBefore(JSONSerializer jSONSerializer, Object obj, char c3) {
        List<BeforeFilter> list = jSONSerializer.beforeFilters;
        if (list != null) {
            Iterator<BeforeFilter> it = list.iterator();
            while (it.hasNext()) {
                c3 = it.next().writeBefore(jSONSerializer, obj, c3);
            }
        }
        List<BeforeFilter> list2 = this.beforeFilters;
        if (list2 != null) {
            Iterator<BeforeFilter> it2 = list2.iterator();
            while (it2.hasNext()) {
                c3 = it2.next().writeBefore(jSONSerializer, obj, c3);
            }
        }
        return c3;
    }

    public void writeClassName(JSONSerializer jSONSerializer, String str, Object obj) {
        if (str == null) {
            str = jSONSerializer.config.typeKey;
        }
        jSONSerializer.out.writeFieldName(str, false);
        String name = this.beanInfo.typeName;
        if (name == null) {
            Class<?> superclass = obj.getClass();
            if (TypeUtils.isProxy(superclass)) {
                superclass = superclass.getSuperclass();
            }
            name = superclass.getName();
        }
        jSONSerializer.write(name);
    }

    public void writeDirectNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2);
    }

    public void writeNoneASM(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2, false);
    }

    public boolean writeReference(JSONSerializer jSONSerializer, Object obj, int i2) {
        IdentityHashMap<Object, SerialContext> identityHashMap;
        SerialContext serialContext = jSONSerializer.context;
        int i3 = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (serialContext == null || (serialContext.features & i3) != 0 || (i2 & i3) != 0 || (identityHashMap = jSONSerializer.references) == null || !identityHashMap.containsKey(obj)) {
            return false;
        }
        jSONSerializer.writeReference(obj);
        return true;
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, createAliasMap(strArr));
    }

    public boolean isWriteAsArray(JSONSerializer jSONSerializer, int i2) {
        int i3 = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & i3) == 0 && !jSONSerializer.out.beanToArray && (i2 & i3) == 0) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:369:0x04af, code lost:
    
        r30 = r6;
        r31 = r7;
        r22 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x04b8, code lost:
    
        if (r21 == 0) goto L372;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x04ba, code lost:
    
        r4 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x04bc, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x04bd, code lost:
    
        writeAfter(r33, r34, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x04c3, code lost:
    
        if (r22.length <= 0) goto L378;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x04cb, code lost:
    
        if (r14.isEnabled(com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat) == false) goto L378;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x04cd, code lost:
    
        r33.decrementIdent();
        r33.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x04d3, code lost:
    
        if (r38 != false) goto L380;
     */
    /* JADX WARN: Code restructure failed: missing block: B:379:0x04d5, code lost:
    
        r14.append(r30);
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x04da, code lost:
    
        r33.context = r31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x04de, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x04e4, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x029a A[Catch: Exception -> 0x046f, all -> 0x04df, TryCatch #1 {Exception -> 0x046f, blocks: (B:113:0x0190, B:116:0x0198, B:118:0x01a4, B:120:0x01af, B:122:0x01b9, B:125:0x01c3, B:127:0x01cf, B:129:0x01d3, B:132:0x01da, B:134:0x01de, B:135:0x01e2, B:137:0x01e7, B:139:0x01ea, B:141:0x01f0, B:143:0x01fc, B:145:0x0200, B:148:0x0207, B:151:0x020e, B:153:0x0213, B:156:0x0217, B:158:0x021f, B:160:0x022b, B:162:0x022f, B:165:0x0236, B:167:0x023a, B:168:0x023f, B:170:0x0244, B:172:0x0247, B:173:0x024c, B:175:0x0254, B:177:0x0260, B:179:0x0264, B:182:0x026b, B:184:0x026f, B:185:0x0274, B:187:0x0279, B:189:0x027c, B:191:0x0283, B:193:0x0287, B:195:0x0291, B:199:0x029a, B:201:0x029e, B:203:0x02a7, B:205:0x02ae, B:207:0x02b4, B:209:0x02b8, B:212:0x02c3, B:214:0x02c7, B:216:0x02cb, B:219:0x02d6, B:221:0x02da, B:223:0x02de, B:226:0x02e9, B:228:0x02ed, B:230:0x02f1, B:233:0x02ff, B:235:0x0303, B:237:0x0307, B:240:0x0314, B:242:0x0318, B:244:0x031c, B:247:0x032a, B:249:0x032e, B:251:0x0332, B:255:0x033e, B:257:0x0342, B:259:0x0346, B:262:0x0356, B:264:0x0363, B:269:0x036f, B:271:0x0375, B:328:0x0433, B:330:0x0437, B:332:0x043b, B:335:0x0445, B:337:0x044d, B:338:0x0455, B:340:0x045b, B:276:0x0381, B:277:0x0384, B:279:0x038a, B:281:0x0396, B:288:0x03ac, B:293:0x03b6, B:297:0x03c9, B:300:0x03d1, B:303:0x03db, B:305:0x03e3, B:306:0x03ec, B:308:0x03f5, B:310:0x03fc, B:311:0x0400, B:313:0x0403, B:314:0x0407, B:315:0x040b, B:317:0x0410, B:318:0x0414, B:319:0x0418, B:321:0x041c, B:323:0x0420, B:326:0x042c, B:327:0x0430, B:294:0x03bf), top: B:419:0x0190 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x02ae A[Catch: Exception -> 0x046f, all -> 0x04df, TryCatch #1 {Exception -> 0x046f, blocks: (B:113:0x0190, B:116:0x0198, B:118:0x01a4, B:120:0x01af, B:122:0x01b9, B:125:0x01c3, B:127:0x01cf, B:129:0x01d3, B:132:0x01da, B:134:0x01de, B:135:0x01e2, B:137:0x01e7, B:139:0x01ea, B:141:0x01f0, B:143:0x01fc, B:145:0x0200, B:148:0x0207, B:151:0x020e, B:153:0x0213, B:156:0x0217, B:158:0x021f, B:160:0x022b, B:162:0x022f, B:165:0x0236, B:167:0x023a, B:168:0x023f, B:170:0x0244, B:172:0x0247, B:173:0x024c, B:175:0x0254, B:177:0x0260, B:179:0x0264, B:182:0x026b, B:184:0x026f, B:185:0x0274, B:187:0x0279, B:189:0x027c, B:191:0x0283, B:193:0x0287, B:195:0x0291, B:199:0x029a, B:201:0x029e, B:203:0x02a7, B:205:0x02ae, B:207:0x02b4, B:209:0x02b8, B:212:0x02c3, B:214:0x02c7, B:216:0x02cb, B:219:0x02d6, B:221:0x02da, B:223:0x02de, B:226:0x02e9, B:228:0x02ed, B:230:0x02f1, B:233:0x02ff, B:235:0x0303, B:237:0x0307, B:240:0x0314, B:242:0x0318, B:244:0x031c, B:247:0x032a, B:249:0x032e, B:251:0x0332, B:255:0x033e, B:257:0x0342, B:259:0x0346, B:262:0x0356, B:264:0x0363, B:269:0x036f, B:271:0x0375, B:328:0x0433, B:330:0x0437, B:332:0x043b, B:335:0x0445, B:337:0x044d, B:338:0x0455, B:340:0x045b, B:276:0x0381, B:277:0x0384, B:279:0x038a, B:281:0x0396, B:288:0x03ac, B:293:0x03b6, B:297:0x03c9, B:300:0x03d1, B:303:0x03db, B:305:0x03e3, B:306:0x03ec, B:308:0x03f5, B:310:0x03fc, B:311:0x0400, B:313:0x0403, B:314:0x0407, B:315:0x040b, B:317:0x0410, B:318:0x0414, B:319:0x0418, B:321:0x041c, B:323:0x0420, B:326:0x042c, B:327:0x0430, B:294:0x03bf), top: B:419:0x0190 }] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x033c  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0519 A[Catch: all -> 0x0598, TRY_ENTER, TryCatch #9 {all -> 0x0598, blocks: (B:392:0x04f5, B:395:0x0519, B:403:0x0567, B:405:0x056d, B:406:0x0585, B:408:0x0589, B:412:0x0592, B:413:0x0597, B:397:0x052e, B:399:0x0532, B:401:0x0536, B:402:0x0551), top: B:434:0x04f5 }] */
    /* JADX WARN: Removed duplicated region for block: B:396:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x056d A[Catch: all -> 0x0598, TryCatch #9 {all -> 0x0598, blocks: (B:392:0x04f5, B:395:0x0519, B:403:0x0567, B:405:0x056d, B:406:0x0585, B:408:0x0589, B:412:0x0592, B:413:0x0597, B:397:0x052e, B:399:0x0532, B:401:0x0536, B:402:0x0551), top: B:434:0x04f5 }] */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0589 A[Catch: all -> 0x0598, TryCatch #9 {all -> 0x0598, blocks: (B:392:0x04f5, B:395:0x0519, B:403:0x0567, B:405:0x056d, B:406:0x0585, B:408:0x0589, B:412:0x0592, B:413:0x0597, B:397:0x052e, B:399:0x0532, B:401:0x0536, B:402:0x0551), top: B:434:0x04f5 }] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0590  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r33, java.lang.Object r34, java.lang.Object r35, java.lang.reflect.Type r36, int r37, boolean r38) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1436
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }

    public JavaBeanSerializer(Class<?> cls, Map<String, String> map) {
        this(TypeUtils.buildBeanInfo(cls, map, null));
    }

    public JavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) {
        FieldSerializer[] fieldSerializerArr;
        boolean z2;
        this.beanInfo = serializeBeanInfo;
        this.sortedGetters = new FieldSerializer[serializeBeanInfo.sortedFields.length];
        int i2 = 0;
        while (true) {
            fieldSerializerArr = this.sortedGetters;
            if (i2 >= fieldSerializerArr.length) {
                break;
            }
            fieldSerializerArr[i2] = new FieldSerializer(serializeBeanInfo.beanType, serializeBeanInfo.sortedFields[i2]);
            i2++;
        }
        FieldInfo[] fieldInfoArr = serializeBeanInfo.fields;
        if (fieldInfoArr == serializeBeanInfo.sortedFields) {
            this.getters = fieldSerializerArr;
        } else {
            this.getters = new FieldSerializer[fieldInfoArr.length];
            int i3 = 0;
            while (true) {
                if (i3 >= this.getters.length) {
                    z2 = false;
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(serializeBeanInfo.fields[i3].name);
                if (fieldSerializer == null) {
                    z2 = true;
                    break;
                } else {
                    this.getters[i3] = fieldSerializer;
                    i3++;
                }
            }
            if (z2) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                System.arraycopy(fieldSerializerArr2, 0, this.getters, 0, fieldSerializerArr2.length);
            }
        }
        JSONType jSONType = serializeBeanInfo.jsonType;
        if (jSONType != null) {
            for (Class<? extends SerializeFilter> cls : jSONType.serialzeFilters()) {
                try {
                    addFilter(cls.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (Exception unused) {
                }
            }
        }
    }

    public FieldSerializer getFieldSerializer(long j2) {
        PropertyNamingStrategy[] propertyNamingStrategyArrValues;
        int iBinarySearch;
        if (this.hashArray == null) {
            propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            long[] jArr = new long[this.sortedGetters.length * propertyNamingStrategyArrValues.length];
            int i2 = 0;
            int i3 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr = this.sortedGetters;
                if (i2 >= fieldSerializerArr.length) {
                    break;
                }
                String str = fieldSerializerArr[i2].fieldInfo.name;
                jArr[i3] = TypeUtils.fnv1a_64(str);
                i3++;
                for (PropertyNamingStrategy propertyNamingStrategy : propertyNamingStrategyArrValues) {
                    String strTranslate = propertyNamingStrategy.translate(str);
                    if (!str.equals(strTranslate)) {
                        jArr[i3] = TypeUtils.fnv1a_64(strTranslate);
                        i3++;
                    }
                }
                i2++;
            }
            Arrays.sort(jArr, 0, i3);
            this.hashArray = new long[i3];
            System.arraycopy(jArr, 0, this.hashArray, 0, i3);
        } else {
            propertyNamingStrategyArrValues = null;
        }
        int iBinarySearch2 = Arrays.binarySearch(this.hashArray, j2);
        if (iBinarySearch2 < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (propertyNamingStrategyArrValues == null) {
                propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            }
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            int i4 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                if (i4 >= fieldSerializerArr2.length) {
                    break;
                }
                String str2 = fieldSerializerArr2[i4].fieldInfo.name;
                int iBinarySearch3 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(str2));
                if (iBinarySearch3 >= 0) {
                    sArr[iBinarySearch3] = (short) i4;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : propertyNamingStrategyArrValues) {
                    String strTranslate2 = propertyNamingStrategy2.translate(str2);
                    if (!str2.equals(strTranslate2) && (iBinarySearch = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(strTranslate2))) >= 0) {
                        sArr[iBinarySearch] = (short) i4;
                    }
                }
                i4++;
            }
            this.hashArrayMapping = sArr;
        }
        short s2 = this.hashArrayMapping[iBinarySearch2];
        if (s2 != -1) {
            return this.sortedGetters[s2];
        }
        return null;
    }

    public Object getFieldValue(Object obj, String str, long j2, boolean z2) {
        FieldSerializer fieldSerializer = getFieldSerializer(j2);
        if (fieldSerializer == null) {
            if (!z2) {
                return null;
            }
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        } catch (InvocationTargetException e3) {
            throw new JSONException("getFieldValue error." + str, e3);
        }
    }
}
