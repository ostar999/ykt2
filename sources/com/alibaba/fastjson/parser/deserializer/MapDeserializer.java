package com.alibaba.fastjson.parser.deserializer;

import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class MapDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    /* JADX WARN: Code restructure failed: missing block: B:88:0x01f2, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Map parseMap(com.alibaba.fastjson.parser.DefaultJSONParser r10, java.util.Map<java.lang.String, java.lang.Object> r11, java.lang.reflect.Type r12, java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 543
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.MapDeserializer.parseMap(com.alibaba.fastjson.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.Object):java.util.Map");
    }

    public Map<Object, Object> createMap(Type type) {
        return createMap(type, JSON.DEFAULT_GENERATE_FEATURE);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == JSONObject.class && defaultJSONParser.getFieldTypeResolver() == null) {
            return (T) defaultJSONParser.parseObject();
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        boolean z2 = (type instanceof Class) && "java.util.Collections$UnmodifiableMap".equals(((Class) type).getName());
        Map<Object, Object> mapCreateMap = (jSONLexer.getFeatures() & Feature.OrderedField.mask) != 0 ? createMap(type, jSONLexer.getFeatures()) : createMap(type);
        ParseContext context = defaultJSONParser.getContext();
        try {
            defaultJSONParser.setContext(context, mapCreateMap, obj);
            Map map = (T) deserialze(defaultJSONParser, type, obj, mapCreateMap);
            if (z2) {
                map = (T) Collections.unmodifiableMap(map);
            }
            return (T) map;
        } finally {
            defaultJSONParser.setContext(context);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public Map<Object, Object> createMap(Type type, int i2) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class) {
            return (Feature.OrderedField.mask & i2) != 0 ? new LinkedHashMap() : new HashMap();
        }
        if (type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return EnumMap.class.equals(rawType) ? new EnumMap((Class) parameterizedType.getActualTypeArguments()[0]) : createMap(rawType, i2);
        }
        Class cls = (Class) type;
        if (cls.isInterface()) {
            throw new JSONException("unsupport type " + type);
        }
        if ("java.util.Collections$UnmodifiableMap".equals(cls.getName())) {
            return new HashMap();
        }
        try {
            return (Map) cls.newInstance();
        } catch (Exception e2) {
            throw new JSONException("unsupport type " + type, e2);
        }
    }

    public Object deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, Map map) {
        Type type2;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type type3 = parameterizedType.getActualTypeArguments()[0];
            if (map.getClass().getName().equals("org.springframework.util.LinkedMultiValueMap")) {
                type2 = List.class;
            } else {
                type2 = parameterizedType.getActualTypeArguments()[1];
            }
            if (String.class == type3) {
                return parseMap(defaultJSONParser, map, type2, obj);
            }
            return parseMap(defaultJSONParser, map, type3, type2, obj);
        }
        return defaultJSONParser.parseObject(map, obj);
    }

    public static Object parseMap(DefaultJSONParser defaultJSONParser, Map<Object, Object> map, Type type, Type type2, Object obj) {
        Object objDeserialze;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + jSONLexer.tokenName());
        }
        ObjectDeserializer deserializer = defaultJSONParser.getConfig().getDeserializer(type);
        ObjectDeserializer deserializer2 = defaultJSONParser.getConfig().getDeserializer(type2);
        jSONLexer.nextToken(deserializer.getFastMatchToken());
        ParseContext context = defaultJSONParser.getContext();
        while (jSONLexer.token() != 13) {
            try {
                Object obj2 = null;
                if (jSONLexer.token() == 4 && jSONLexer.isRef() && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    jSONLexer.nextTokenWithColon(4);
                    if (jSONLexer.token() == 4) {
                        String strStringVal = jSONLexer.stringVal();
                        if (StrPool.DOUBLE_DOT.equals(strStringVal)) {
                            obj2 = context.parent.object;
                        } else if ("$".equals(strStringVal)) {
                            ParseContext parseContext = context;
                            while (true) {
                                ParseContext parseContext2 = parseContext.parent;
                                if (parseContext2 == null) {
                                    break;
                                }
                                parseContext = parseContext2;
                            }
                            obj2 = parseContext.object;
                        } else {
                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(context, strStringVal));
                            defaultJSONParser.setResolveStatus(1);
                        }
                        jSONLexer.nextToken(13);
                        if (jSONLexer.token() == 13) {
                            jSONLexer.nextToken(16);
                            return obj2;
                        }
                        throw new JSONException("illegal ref");
                    }
                    throw new JSONException("illegal ref, " + JSONToken.name(jSONLexer.token()));
                }
                if (map.size() == 0 && jSONLexer.token() == 4 && JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal()) && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    jSONLexer.nextTokenWithColon(4);
                    jSONLexer.nextToken(16);
                    if (jSONLexer.token() == 13) {
                        jSONLexer.nextToken();
                        return map;
                    }
                    jSONLexer.nextToken(deserializer.getFastMatchToken());
                }
                if (jSONLexer.token() == 4 && (deserializer instanceof JavaBeanDeserializer)) {
                    String strStringVal2 = jSONLexer.stringVal();
                    jSONLexer.nextToken();
                    DefaultJSONParser defaultJSONParser2 = new DefaultJSONParser(strStringVal2, defaultJSONParser.getConfig(), defaultJSONParser.getLexer().getFeatures());
                    defaultJSONParser2.setDateFormat(defaultJSONParser.getDateFomartPattern());
                    objDeserialze = deserializer.deserialze(defaultJSONParser2, type, null);
                } else {
                    objDeserialze = deserializer.deserialze(defaultJSONParser, type, null);
                }
                if (jSONLexer.token() == 17) {
                    jSONLexer.nextToken(deserializer2.getFastMatchToken());
                    Object objDeserialze2 = deserializer2.deserialze(defaultJSONParser, type2, objDeserialze);
                    defaultJSONParser.checkMapResolve(map, objDeserialze);
                    map.put(objDeserialze, objDeserialze2);
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(deserializer.getFastMatchToken());
                    }
                } else {
                    throw new JSONException("syntax error, expect :, actual " + jSONLexer.token());
                }
            } finally {
                defaultJSONParser.setContext(context);
            }
        }
        jSONLexer.nextToken(16);
        return map;
    }
}
