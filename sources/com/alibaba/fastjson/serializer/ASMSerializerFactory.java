package com.alibaba.fastjson.serializer;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.alipay.sdk.util.h;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.umeng.analytics.pro.am;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.HttpUrl;

/* loaded from: classes2.dex */
public class ASMSerializerFactory implements Opcodes {
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String JavaBeanSerializer;
    static final String JavaBeanSerializer_desc;
    static final String ObjectSerializer;
    static final String ObjectSerializer_desc;
    static final String SerialContext_desc;
    static final String SerializeFilterable_desc;
    static final String SerializeWriter;
    static final String SerializeWriter_desc;
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();

    static {
        String strType = ASMUtils.type(ObjectSerializer.class);
        ObjectSerializer = strType;
        ObjectSerializer_desc = "L" + strType + h.f3376b;
        String strType2 = ASMUtils.type(SerializeWriter.class);
        SerializeWriter = strType2;
        SerializeWriter_desc = "L" + strType2 + h.f3376b;
        JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
        JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + h.f3376b;
        SerialContext_desc = ASMUtils.desc((Class<?>) SerialContext.class);
        SerializeFilterable_desc = ASMUtils.desc((Class<?>) SerializeFilterable.class);
    }

    private void _after(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _apply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _before(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _decimal(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("decimal"));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitJumpInsn(199, label3);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label4);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(167, label4);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitLabel(label);
    }

    private void _double(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(57, context.var("double", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(24, context.var("double", 2));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _enum(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label3);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/lang/Enum");
        methodVisitor.visitVarInsn(58, context.var("enum"));
        _filters(methodVisitor, fieldInfo, context, label3);
        methodVisitor.visitVarInsn(25, context.var("enum"));
        methodVisitor.visitJumpInsn(199, label);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel(label);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitMethodInsn(182, "java/lang/Enum", "name", "()Ljava/lang/String;");
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            String str = SerializeWriter;
            methodVisitor.visitMethodInsn(182, str, "write", "(I)V");
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            methodVisitor.visitMethodInsn(182, str, "writeFieldName", "(Ljava/lang/String;Z)V");
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label3);
    }

    private void _filters(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (fieldInfo.fieldTransient) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(154, label);
        }
        _notWriteDefault(methodVisitor, fieldInfo, context, label);
        if (context.writeDirect) {
            return;
        }
        _apply(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(153, label);
        _processKey(methodVisitor, fieldInfo, context);
        _processValue(methodVisitor, fieldInfo, context, label);
    }

    private void _float(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(56, context.var(TypedValues.Custom.S_FLOAT));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _get(MethodVisitor methodVisitor, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            methodVisitor.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            methodVisitor.visitMethodInsn(declaringClass.isInterface() ? 185 : 182, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (method.getReturnType().equals(fieldInfo.fieldClass)) {
                return;
            }
            methodVisitor.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("entity"));
        Field field = fieldInfo.field;
        methodVisitor.visitFieldInsn(180, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
        if (field.getType().equals(fieldInfo.fieldClass)) {
            return;
        }
        methodVisitor.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
    }

    private void _getFieldSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        String str2 = fieldInfo.name + "_asm_ser_";
        String str3 = ObjectSerializer_desc;
        methodVisitor.visitFieldInsn(180, str, str2, str3);
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + str3);
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_ser_", str3);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", str3);
    }

    private void _getListFieldItemSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        String str2 = fieldInfo.name + "_asm_list_item_ser_";
        String str3 = ObjectSerializer_desc;
        methodVisitor.visitFieldInsn(180, str, str2, str3);
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + str3);
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_list_item_ser_", str3);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", str3);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void _if_write_null(com.alibaba.fastjson.asm.MethodVisitor r18, com.alibaba.fastjson.util.FieldInfo r19, com.alibaba.fastjson.serializer.ASMSerializerFactory.Context r20) {
        /*
            Method dump skipped, instructions count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ASMSerializerFactory._if_write_null(com.alibaba.fastjson.asm.MethodVisitor, com.alibaba.fastjson.util.FieldInfo, com.alibaba.fastjson.serializer.ASMSerializerFactory$Context):void");
    }

    private void _int(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, int i2, char c3) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(54, i2);
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(21, i2);
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;" + c3 + ")V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _labelApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(fieldInfo.label);
        methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        methodVisitor.visitJumpInsn(153, label);
    }

    private void _list(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label;
        Label label2;
        Label label3;
        String str;
        Label label4;
        Label label5;
        String str2;
        Label label6;
        FieldInfo fieldInfo2;
        int i2;
        int i3;
        int i4;
        Label label7;
        String str3;
        String str4;
        java.lang.reflect.Type collectionItemType = TypeUtils.getCollectionItemType(fieldInfo.fieldType);
        Class<?> cls2 = null;
        Class<?> cls3 = collectionItemType instanceof Class ? (Class) collectionItemType : null;
        if (cls3 != Object.class && cls3 != Serializable.class) {
            cls2 = cls3;
        }
        Label label8 = new Label();
        Label label9 = new Label();
        Label label10 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label8);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/util/List");
        methodVisitor.visitVarInsn(58, context.var("list"));
        _filters(methodVisitor, fieldInfo, context, label8);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitJumpInsn(199, label9);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label10);
        methodVisitor.visitLabel(label9);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        String str5 = SerializeWriter;
        methodVisitor.visitMethodInsn(182, str5, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitMethodInsn(185, "java/util/List", DatabaseManager.SIZE, "()I");
        methodVisitor.visitVarInsn(54, context.var(DatabaseManager.SIZE));
        Label label11 = new Label();
        Label label12 = new Label();
        methodVisitor.visitVarInsn(21, context.var(DatabaseManager.SIZE));
        methodVisitor.visitInsn(3);
        methodVisitor.visitJumpInsn(160, label11);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        methodVisitor.visitMethodInsn(182, str5, "write", "(Ljava/lang/String;)V");
        methodVisitor.visitJumpInsn(167, label12);
        methodVisitor.visitLabel(label11);
        if (context.nonContext) {
            label = label12;
        } else {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            label = label12;
            methodVisitor.visitMethodInsn(182, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (collectionItemType == String.class && context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitMethodInsn(182, str5, "write", "(Ljava/util/List;)V");
            i3 = 25;
            i4 = 182;
            i2 = 1;
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 91);
            methodVisitor.visitMethodInsn(182, str5, "write", "(I)V");
            Label label13 = new Label();
            Label label14 = new Label();
            Label label15 = new Label();
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, context.var(am.aC));
            methodVisitor.visitLabel(label13);
            methodVisitor.visitVarInsn(21, context.var(am.aC));
            methodVisitor.visitVarInsn(21, context.var(DatabaseManager.SIZE));
            methodVisitor.visitJumpInsn(162, label15);
            methodVisitor.visitVarInsn(21, context.var(am.aC));
            methodVisitor.visitJumpInsn(153, label14);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitMethodInsn(182, str5, "write", "(I)V");
            methodVisitor.visitLabel(label14);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(21, context.var(am.aC));
            methodVisitor.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;");
            methodVisitor.visitVarInsn(58, context.var("list_item"));
            Label label16 = new Label();
            Label label17 = new Label();
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            methodVisitor.visitJumpInsn(199, label17);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(182, str5, "writeNull", "()V");
            methodVisitor.visitJumpInsn(167, label16);
            methodVisitor.visitLabel(label17);
            Label label18 = new Label();
            Label label19 = new Label();
            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                label2 = label13;
                label3 = label16;
                str = "out";
                label4 = label18;
                label5 = label19;
                str2 = "write";
                label6 = label15;
                fieldInfo2 = fieldInfo;
            } else {
                str = "out";
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                label6 = label15;
                label2 = label13;
                methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitJumpInsn(166, label19);
                fieldInfo2 = fieldInfo;
                _getListFieldItemSer(context, methodVisitor, fieldInfo2, cls2);
                methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                Label label20 = new Label();
                Label label21 = new Label();
                if (context.writeDirect) {
                    if (context.nonContext && context.writeDirect) {
                        label3 = label16;
                        str4 = "writeDirectNonContext";
                        label5 = label19;
                    } else {
                        label3 = label16;
                        label5 = label19;
                        str4 = "write";
                    }
                    label7 = label18;
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    String str6 = JavaBeanSerializer;
                    methodVisitor.visitTypeInsn(193, str6);
                    methodVisitor.visitJumpInsn(153, label20);
                    str3 = "write";
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    methodVisitor.visitTypeInsn(192, str6);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                    } else {
                        methodVisitor.visitVarInsn(21, context.var(am.aC));
                        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                    methodVisitor.visitMethodInsn(182, str6, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(167, label21);
                    methodVisitor.visitLabel(label20);
                } else {
                    label3 = label16;
                    label7 = label18;
                    label5 = label19;
                    str3 = "write";
                }
                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    methodVisitor.visitInsn(1);
                } else {
                    methodVisitor.visitVarInsn(21, context.var(am.aC));
                    methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                str2 = str3;
                methodVisitor.visitMethodInsn(185, ObjectSerializer, str2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label21);
                label4 = label7;
                methodVisitor.visitJumpInsn(167, label4);
            }
            methodVisitor.visitLabel(label5);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                methodVisitor.visitInsn(1);
            } else {
                methodVisitor.visitVarInsn(21, context.var(am.aC));
                methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) collectionItemType)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLabel(label3);
            i2 = 1;
            methodVisitor.visitIincInsn(context.var(am.aC), 1);
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label6);
            i3 = 25;
            methodVisitor.visitVarInsn(25, context.var(str));
            methodVisitor.visitVarInsn(16, 93);
            i4 = 182;
            methodVisitor.visitMethodInsn(182, str5, str2, "(I)V");
        }
        methodVisitor.visitVarInsn(i3, i2);
        methodVisitor.visitMethodInsn(i4, JSONSerializer, "popContext", "()V");
        methodVisitor.visitLabel(label);
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label10);
        methodVisitor.visitLabel(label8);
    }

    private void _long(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(55, context.var("long", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(22, context.var("long", 2));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _nameApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            methodVisitor.visitJumpInsn(153, label);
            _labelApply(methodVisitor, fieldInfo, context, label);
        }
        if (fieldInfo.field == null) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(154, label);
        }
    }

    private void _notWriteDefault(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (context.writeDirect) {
            return;
        }
        Label label2 = new Label();
        methodVisitor.visitVarInsn(21, context.var("notWriteDefaultValue"));
        methodVisitor.visitJumpInsn(153, label2);
        Class<?> cls = fieldInfo.fieldClass;
        if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitJumpInsn(153, label);
        } else if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long"));
            methodVisitor.visitInsn(9);
            methodVisitor.visitInsn(148);
            methodVisitor.visitJumpInsn(153, label);
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitInsn(11);
            methodVisitor.visitInsn(149);
            methodVisitor.visitJumpInsn(153, label);
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double"));
            methodVisitor.visitInsn(14);
            methodVisitor.visitInsn(151);
            methodVisitor.visitJumpInsn(153, label);
        }
        methodVisitor.visitLabel(label2);
    }

    private void _object(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("object"));
        _filters(methodVisitor, fieldInfo, context, label);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitLabel(label);
    }

    private void _processKey(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        methodVisitor.visitVarInsn(21, context.var("hasNameFilters"));
        methodVisitor.visitJumpInsn(153, label);
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        methodVisitor.visitVarInsn(58, Context.fieldName);
        methodVisitor.visitLabel(label);
    }

    private void _processValue(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        Label label2 = new Label();
        Class<?> cls = fieldInfo.fieldClass;
        if (cls.isPrimitive()) {
            Label label3 = new Label();
            methodVisitor.visitVarInsn(21, context.var("checkValue"));
            methodVisitor.visitJumpInsn(154, label3);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(58, Context.processValue);
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label3);
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(Integer.valueOf(context.getFieldOrinal(fieldInfo.name)));
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(182, str, "getBeanContext", "(I)" + ASMUtils.desc((Class<?>) BeanContext.class));
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        }
        methodVisitor.visitMethodInsn(182, str, "processValue", "(L" + JSONSerializer + h.f3376b + ASMUtils.desc((Class<?>) BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, Context.processValue);
        methodVisitor.visitVarInsn(25, Context.original);
        methodVisitor.visitVarInsn(25, Context.processValue);
        methodVisitor.visitJumpInsn(165, label2);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitJumpInsn(167, label);
        methodVisitor.visitLabel(label2);
    }

    private void _seperator(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _string(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        if (fieldInfo.name.equals(context.beanInfo.typeKey)) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            methodVisitor.visitJumpInsn(154, label);
        }
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var(TypedValues.Custom.S_STRING));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        methodVisitor.visitJumpInsn(199, label2);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label2);
        if ("trim".equals(fieldInfo.format)) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitMethodInsn(182, "java/lang/String", "trim", "()Ljava/lang/String;");
            methodVisitor.visitVarInsn(58, context.var(TypedValues.Custom.S_STRING));
        }
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitLabel(label);
    }

    private void _writeFieldName(MethodVisitor methodVisitor, Context context) {
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
        }
    }

    private void _writeObject(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        String str;
        Label label2;
        Label label3;
        String format = fieldInfo.getFormat();
        Class<?> cls = fieldInfo.fieldClass;
        Label label4 = new Label();
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, context.var("object"));
        methodVisitor.visitJumpInsn(199, label4);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        Label label5 = new Label();
        Label label6 = new Label();
        if (!Modifier.isPublic(cls.getModifiers()) || ParserConfig.isPrimitive2(cls)) {
            str = format;
            label2 = label5;
            label3 = label6;
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
            methodVisitor.visitJumpInsn(166, label6);
            _getFieldSer(context, methodVisitor, fieldInfo);
            methodVisitor.visitVarInsn(58, context.var("fied_ser"));
            Label label7 = new Label();
            Label label8 = new Label();
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            String str2 = JavaBeanSerializer;
            methodVisitor.visitTypeInsn(193, str2);
            methodVisitor.visitJumpInsn(153, label7);
            int i2 = fieldInfo.serialzeFeatures;
            str = format;
            boolean z2 = (SerializerFeature.DisableCircularReferenceDetect.mask & i2) != 0;
            boolean z3 = (SerializerFeature.BeanToArray.mask & i2) != 0;
            String str3 = (z2 || (context.nonContext && context.writeDirect)) ? z3 ? "writeAsArrayNonContext" : "writeDirectNonContext" : z3 ? "writeAsArray" : "write";
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(192, str2);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            StringBuilder sb = new StringBuilder();
            sb.append("(L");
            String str4 = JSONSerializer;
            sb.append(str4);
            sb.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitMethodInsn(182, str2, str3, sb.toString());
            methodVisitor.visitJumpInsn(167, label8);
            methodVisitor.visitLabel(label7);
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(185, ObjectSerializer, "write", "(L" + str4 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(label8);
            label2 = label5;
            methodVisitor.visitJumpInsn(167, label2);
            label3 = label6;
        }
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, 1);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        if (str != null) {
            methodVisitor.visitLdcInsn(str);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, Context.fieldName);
            java.lang.reflect.Type type = fieldInfo.fieldType;
            if ((type instanceof Class) && ((Class) type).isPrimitive()) {
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                if (fieldInfo.fieldClass == String.class) {
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) String.class)));
                } else {
                    methodVisitor.visitVarInsn(25, 0);
                    methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
        }
        methodVisitor.visitLabel(label2);
        _seperator(methodVisitor, context);
    }

    private void generateWriteAsArray(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        String str;
        int i2;
        int i3;
        String str2;
        String str3;
        char c3;
        boolean z2;
        int i4;
        ASMSerializerFactory aSMSerializerFactory;
        char c4;
        int i5;
        Label label;
        String str4;
        Class<?> cls2;
        int i6;
        Label label2;
        Label label3;
        Label label4;
        String str5;
        Label label5;
        int i7;
        int i8;
        String str6;
        int i9;
        Label label6;
        int i10;
        ASMSerializerFactory aSMSerializerFactory2 = this;
        FieldInfo[] fieldInfoArr2 = fieldInfoArr;
        Label label7 = new Label();
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        String str7 = JSONSerializer;
        methodVisitor.visitMethodInsn(182, str7, "hasPropertyFilters", "(" + SerializeFilterable_desc + ")Z");
        methodVisitor.visitJumpInsn(154, label7);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, 3);
        methodVisitor.visitVarInsn(25, 4);
        methodVisitor.visitVarInsn(21, 5);
        String str8 = JavaBeanSerializer;
        StringBuilder sb = new StringBuilder();
        String str9 = "(L";
        sb.append("(L");
        sb.append(str7);
        String str10 = ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
        sb.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        methodVisitor.visitMethodInsn(183, str8, "writeNoneASM", sb.toString());
        methodVisitor.visitInsn(177);
        methodVisitor.visitLabel(label7);
        String str11 = "out";
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(16, 91);
        String str12 = SerializeWriter;
        String str13 = "(I)V";
        methodVisitor.visitMethodInsn(182, str12, "write", "(I)V");
        int length = fieldInfoArr2.length;
        if (length == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            methodVisitor.visitMethodInsn(182, str12, "write", "(I)V");
            return;
        }
        int i11 = 0;
        while (i11 < length) {
            int i12 = i11 == length + (-1) ? 93 : 44;
            FieldInfo fieldInfo = fieldInfoArr2[i11];
            Class<?> cls3 = fieldInfo.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                str = str11;
                i2 = length;
                i3 = i11;
                int i13 = i12;
                str2 = str9;
                str3 = str13;
                c3 = 25;
                z2 = false;
                i4 = 182;
                methodVisitor.visitVarInsn(25, context.var(str));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory = this;
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                String str14 = SerializeWriter;
                methodVisitor.visitMethodInsn(182, str14, "writeInt", str3);
                c4 = 16;
                methodVisitor.visitVarInsn(16, i13);
                methodVisitor.visitMethodInsn(182, str14, "write", str3);
            } else {
                if (cls3 == Long.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str11));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    String str15 = SerializeWriter;
                    i10 = 182;
                    methodVisitor.visitMethodInsn(182, str15, "writeLong", "(J)V");
                    methodVisitor.visitVarInsn(16, i12);
                    methodVisitor.visitMethodInsn(182, str15, "write", str13);
                } else if (cls3 == Float.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str11));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(4);
                    String str16 = SerializeWriter;
                    i10 = 182;
                    methodVisitor.visitMethodInsn(182, str16, "writeFloat", "(FZ)V");
                    methodVisitor.visitVarInsn(16, i12);
                    methodVisitor.visitMethodInsn(182, str16, "write", str13);
                } else if (cls3 == Double.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str11));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(4);
                    String str17 = SerializeWriter;
                    i10 = 182;
                    methodVisitor.visitMethodInsn(182, str17, "writeDouble", "(DZ)V");
                    methodVisitor.visitVarInsn(16, i12);
                    methodVisitor.visitMethodInsn(182, str17, "write", str13);
                } else {
                    if (cls3 == Boolean.TYPE) {
                        methodVisitor.visitVarInsn(25, context.var(str11));
                        methodVisitor.visitInsn(89);
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                        String str18 = SerializeWriter;
                        i5 = 182;
                        methodVisitor.visitMethodInsn(182, str18, "write", "(Z)V");
                        methodVisitor.visitVarInsn(16, i12);
                        methodVisitor.visitMethodInsn(182, str18, "write", str13);
                    } else if (cls3 == Character.TYPE) {
                        methodVisitor.visitVarInsn(25, context.var(str11));
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                        methodVisitor.visitMethodInsn(184, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                        methodVisitor.visitVarInsn(16, i12);
                        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                        i2 = length;
                        i3 = i11;
                        i4 = 182;
                        str2 = str9;
                        str3 = str13;
                        z2 = false;
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str11;
                        c4 = 16;
                        c3 = 25;
                    } else if (cls3 == String.class) {
                        methodVisitor.visitVarInsn(25, context.var(str11));
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                        methodVisitor.visitVarInsn(16, i12);
                        i4 = 182;
                        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                        i2 = length;
                        i3 = i11;
                        str2 = str9;
                        str3 = str13;
                        z2 = false;
                        c4 = 16;
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str11;
                        c3 = 25;
                    } else {
                        i5 = 182;
                        if (cls3.isEnum()) {
                            methodVisitor.visitVarInsn(25, context.var(str11));
                            methodVisitor.visitInsn(89);
                            aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                            String str19 = SerializeWriter;
                            methodVisitor.visitMethodInsn(182, str19, "writeEnum", "(Ljava/lang/Enum;)V");
                            methodVisitor.visitVarInsn(16, i12);
                            methodVisitor.visitMethodInsn(182, str19, "write", str13);
                        } else if (List.class.isAssignableFrom(cls3)) {
                            java.lang.reflect.Type type = fieldInfo.fieldType;
                            java.lang.reflect.Type type2 = type instanceof Class ? Object.class : ((ParameterizedType) type).getActualTypeArguments()[0];
                            if (!(type2 instanceof Class) || (cls2 = (Class) type2) == Object.class) {
                                cls2 = null;
                            }
                            aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                            i2 = length;
                            methodVisitor.visitTypeInsn(192, "java/util/List");
                            i3 = i11;
                            methodVisitor.visitVarInsn(58, context.var("list"));
                            if (cls2 == String.class && context.writeDirect) {
                                methodVisitor.visitVarInsn(25, context.var(str11));
                                methodVisitor.visitVarInsn(25, context.var("list"));
                                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/util/List;)V");
                                str = str11;
                                i9 = 182;
                                i6 = i12;
                                str2 = str9;
                                str6 = str13;
                                i7 = 25;
                                i8 = 16;
                            } else {
                                Label label8 = new Label();
                                Label label9 = new Label();
                                i6 = i12;
                                methodVisitor.visitVarInsn(25, context.var("list"));
                                methodVisitor.visitJumpInsn(199, label9);
                                methodVisitor.visitVarInsn(25, context.var(str11));
                                String str20 = SerializeWriter;
                                java.lang.reflect.Type type3 = type2;
                                String str21 = str10;
                                methodVisitor.visitMethodInsn(182, str20, "writeNull", "()V");
                                methodVisitor.visitJumpInsn(167, label8);
                                methodVisitor.visitLabel(label9);
                                methodVisitor.visitVarInsn(25, context.var("list"));
                                methodVisitor.visitMethodInsn(185, "java/util/List", DatabaseManager.SIZE, "()I");
                                methodVisitor.visitVarInsn(54, context.var(DatabaseManager.SIZE));
                                methodVisitor.visitVarInsn(25, context.var(str11));
                                methodVisitor.visitVarInsn(16, 91);
                                methodVisitor.visitMethodInsn(182, str20, "write", str13);
                                Label label10 = new Label();
                                Label label11 = new Label();
                                Label label12 = new Label();
                                methodVisitor.visitInsn(3);
                                String str22 = str9;
                                methodVisitor.visitVarInsn(54, context.var(am.aC));
                                methodVisitor.visitLabel(label10);
                                methodVisitor.visitVarInsn(21, context.var(am.aC));
                                methodVisitor.visitVarInsn(21, context.var(DatabaseManager.SIZE));
                                methodVisitor.visitJumpInsn(162, label12);
                                methodVisitor.visitVarInsn(21, context.var(am.aC));
                                methodVisitor.visitJumpInsn(153, label11);
                                methodVisitor.visitVarInsn(25, context.var(str11));
                                methodVisitor.visitVarInsn(16, 44);
                                methodVisitor.visitMethodInsn(182, str20, "write", str13);
                                methodVisitor.visitLabel(label11);
                                methodVisitor.visitVarInsn(25, context.var("list"));
                                methodVisitor.visitVarInsn(21, context.var(am.aC));
                                methodVisitor.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;");
                                methodVisitor.visitVarInsn(58, context.var("list_item"));
                                Label label13 = new Label();
                                Label label14 = new Label();
                                String str23 = str13;
                                methodVisitor.visitVarInsn(25, context.var("list_item"));
                                methodVisitor.visitJumpInsn(199, label14);
                                methodVisitor.visitVarInsn(25, context.var(str11));
                                String str24 = str11;
                                methodVisitor.visitMethodInsn(182, str20, "writeNull", "()V");
                                methodVisitor.visitJumpInsn(167, label13);
                                methodVisitor.visitLabel(label14);
                                Label label15 = new Label();
                                Label label16 = new Label();
                                if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                                    label2 = label10;
                                    label3 = label13;
                                    label4 = label12;
                                    str5 = str20;
                                    str10 = str21;
                                    str2 = str22;
                                    label5 = label16;
                                } else {
                                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                                    str5 = str20;
                                    label4 = label12;
                                    methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                                    methodVisitor.visitJumpInsn(166, label16);
                                    aSMSerializerFactory2._getListFieldItemSer(context, methodVisitor, fieldInfo, cls2);
                                    methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                                    Label label17 = new Label();
                                    Label label18 = new Label();
                                    if (context.writeDirect) {
                                        methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                        String str25 = JavaBeanSerializer;
                                        methodVisitor.visitTypeInsn(193, str25);
                                        methodVisitor.visitJumpInsn(153, label17);
                                        methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                        methodVisitor.visitTypeInsn(192, str25);
                                        methodVisitor.visitVarInsn(25, 1);
                                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                                        if (context.nonContext) {
                                            methodVisitor.visitInsn(1);
                                            label2 = label10;
                                            label3 = label13;
                                        } else {
                                            methodVisitor.visitVarInsn(21, context.var(am.aC));
                                            label2 = label10;
                                            label3 = label13;
                                            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                        }
                                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                                        methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                        StringBuilder sb2 = new StringBuilder();
                                        str2 = str22;
                                        sb2.append(str2);
                                        sb2.append(JSONSerializer);
                                        str10 = str21;
                                        sb2.append(str10);
                                        label6 = label16;
                                        methodVisitor.visitMethodInsn(182, str25, "writeAsArrayNonContext", sb2.toString());
                                        methodVisitor.visitJumpInsn(167, label18);
                                        methodVisitor.visitLabel(label17);
                                    } else {
                                        label2 = label10;
                                        label3 = label13;
                                        str10 = str21;
                                        str2 = str22;
                                        label6 = label16;
                                    }
                                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                    methodVisitor.visitVarInsn(25, 1);
                                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                                    if (context.nonContext) {
                                        methodVisitor.visitInsn(1);
                                    } else {
                                        methodVisitor.visitVarInsn(21, context.var(am.aC));
                                        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                    }
                                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                    methodVisitor.visitMethodInsn(185, ObjectSerializer, "write", str2 + JSONSerializer + str10);
                                    methodVisitor.visitLabel(label18);
                                    methodVisitor.visitJumpInsn(167, label15);
                                    label5 = label6;
                                }
                                methodVisitor.visitLabel(label5);
                                methodVisitor.visitVarInsn(25, 1);
                                methodVisitor.visitVarInsn(25, context.var("list_item"));
                                if (context.nonContext) {
                                    methodVisitor.visitInsn(1);
                                } else {
                                    methodVisitor.visitVarInsn(21, context.var(am.aC));
                                    methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                }
                                if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                                    methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                                } else {
                                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) type3)));
                                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                    methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                                }
                                methodVisitor.visitLabel(label15);
                                methodVisitor.visitLabel(label3);
                                methodVisitor.visitIincInsn(context.var(am.aC), 1);
                                methodVisitor.visitJumpInsn(167, label2);
                                methodVisitor.visitLabel(label4);
                                str = str24;
                                i7 = 25;
                                methodVisitor.visitVarInsn(25, context.var(str));
                                i8 = 16;
                                methodVisitor.visitVarInsn(16, 93);
                                str6 = str23;
                                i9 = 182;
                                methodVisitor.visitMethodInsn(182, str5, "write", str6);
                                methodVisitor.visitLabel(label8);
                            }
                            methodVisitor.visitVarInsn(i7, context.var(str));
                            methodVisitor.visitVarInsn(i8, i6);
                            methodVisitor.visitMethodInsn(i9, SerializeWriter, "write", str6);
                            aSMSerializerFactory = this;
                            i4 = i9;
                            str3 = str6;
                            c3 = 25;
                            z2 = false;
                            c4 = 16;
                        } else {
                            String str26 = str11;
                            i2 = length;
                            i3 = i11;
                            str2 = str9;
                            String str27 = str13;
                            Label label19 = new Label();
                            Label label20 = new Label();
                            _get(methodVisitor, context, fieldInfo);
                            methodVisitor.visitInsn(89);
                            methodVisitor.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                            methodVisitor.visitJumpInsn(199, label20);
                            methodVisitor.visitVarInsn(25, context.var(str26));
                            String str28 = SerializeWriter;
                            int i14 = i12;
                            methodVisitor.visitMethodInsn(182, str28, "writeNull", "()V");
                            methodVisitor.visitJumpInsn(167, label19);
                            methodVisitor.visitLabel(label20);
                            Label label21 = new Label();
                            Label label22 = new Label();
                            methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                            methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                            methodVisitor.visitJumpInsn(166, label22);
                            _getFieldSer(context, methodVisitor, fieldInfo);
                            methodVisitor.visitVarInsn(58, context.var("fied_ser"));
                            Label label23 = new Label();
                            Label label24 = new Label();
                            if (context.writeDirect && Modifier.isPublic(cls3.getModifiers())) {
                                methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                                String str29 = JavaBeanSerializer;
                                methodVisitor.visitTypeInsn(193, str29);
                                methodVisitor.visitJumpInsn(153, label23);
                                str4 = "writeWithFieldName";
                                methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                                methodVisitor.visitTypeInsn(192, str29);
                                methodVisitor.visitVarInsn(25, 1);
                                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                                methodVisitor.visitVarInsn(25, Context.fieldName);
                                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                label = label22;
                                methodVisitor.visitMethodInsn(182, str29, "writeAsArrayNonContext", str2 + JSONSerializer + str10);
                                methodVisitor.visitJumpInsn(167, label24);
                                methodVisitor.visitLabel(label23);
                            } else {
                                label = label22;
                                str4 = "writeWithFieldName";
                            }
                            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                            methodVisitor.visitVarInsn(25, Context.fieldName);
                            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            String str30 = ObjectSerializer;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str2);
                            String str31 = JSONSerializer;
                            sb3.append(str31);
                            sb3.append(str10);
                            methodVisitor.visitMethodInsn(185, str30, "write", sb3.toString());
                            methodVisitor.visitLabel(label24);
                            methodVisitor.visitJumpInsn(167, label21);
                            methodVisitor.visitLabel(label);
                            String format = fieldInfo.getFormat();
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                            if (format != null) {
                                methodVisitor.visitLdcInsn(format);
                                i4 = 182;
                                methodVisitor.visitMethodInsn(182, str31, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                            } else {
                                i4 = 182;
                                methodVisitor.visitVarInsn(25, Context.fieldName);
                                java.lang.reflect.Type type4 = fieldInfo.fieldType;
                                if ((type4 instanceof Class) && ((Class) type4).isPrimitive()) {
                                    methodVisitor.visitMethodInsn(182, str31, str4, "(Ljava/lang/Object;Ljava/lang/Object;)V");
                                } else {
                                    z2 = false;
                                    methodVisitor.visitVarInsn(25, 0);
                                    methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                    i4 = 182;
                                    methodVisitor.visitMethodInsn(182, str31, str4, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                                    methodVisitor.visitLabel(label21);
                                    methodVisitor.visitLabel(label19);
                                    str = str26;
                                    c3 = 25;
                                    methodVisitor.visitVarInsn(25, context.var(str));
                                    methodVisitor.visitVarInsn(16, i14);
                                    str3 = str27;
                                    methodVisitor.visitMethodInsn(i4, str28, "write", str3);
                                    aSMSerializerFactory = this;
                                    c4 = 16;
                                }
                            }
                            z2 = false;
                            methodVisitor.visitLabel(label21);
                            methodVisitor.visitLabel(label19);
                            str = str26;
                            c3 = 25;
                            methodVisitor.visitVarInsn(25, context.var(str));
                            methodVisitor.visitVarInsn(16, i14);
                            str3 = str27;
                            methodVisitor.visitMethodInsn(i4, str28, "write", str3);
                            aSMSerializerFactory = this;
                            c4 = 16;
                        }
                    }
                    aSMSerializerFactory = aSMSerializerFactory2;
                    str = str11;
                    i2 = length;
                    i3 = i11;
                    i4 = i5;
                    str2 = str9;
                    str3 = str13;
                    c3 = 25;
                    z2 = false;
                    c4 = 16;
                }
                i2 = length;
                i3 = i11;
                i4 = i10;
                str2 = str9;
                str3 = str13;
                z2 = false;
                c4 = 16;
                aSMSerializerFactory = aSMSerializerFactory2;
                str = str11;
                c3 = 25;
            }
            str13 = str3;
            length = i2;
            i11 = i3 + 1;
            fieldInfoArr2 = fieldInfoArr;
            str11 = str;
            aSMSerializerFactory2 = aSMSerializerFactory;
            str9 = str2;
        }
    }

    private void generateWriteMethod(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        Label label;
        String str;
        int i2;
        String str2;
        int i3;
        Class<?> cls2;
        Label label2 = new Label();
        int length = fieldInfoArr.length;
        String str3 = "out";
        if (context.writeDirect) {
            label = label2;
        } else {
            Label label3 = new Label();
            Label label4 = new Label();
            label = label2;
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(154, label4);
            int length2 = fieldInfoArr.length;
            int i4 = 0;
            boolean z2 = false;
            while (i4 < length2) {
                int i5 = length2;
                if (fieldInfoArr[i4].method != null) {
                    z2 = true;
                }
                i4++;
                length2 = i5;
            }
            if (z2) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                methodVisitor.visitJumpInsn(153, label3);
            } else {
                methodVisitor.visitJumpInsn(167, label3);
            }
            methodVisitor.visitLabel(label4);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(183, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(177);
            methodVisitor.visitLabel(label3);
        }
        if (!context.nonContext) {
            Label label5 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            methodVisitor.visitJumpInsn(153, label5);
            methodVisitor.visitInsn(177);
            methodVisitor.visitLabel(label5);
        }
        String str4 = context.writeDirect ? context.nonContext ? "writeAsArrayNonContext" : "writeAsArray" : "writeAsArrayNormal";
        int i6 = context.beanInfo.features;
        SerializerFeature serializerFeature = SerializerFeature.BeanToArray;
        if ((i6 & serializerFeature.mask) == 0) {
            Label label6 = new Label();
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(serializerFeature.mask));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(153, label6);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(182, context.className, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(177);
            methodVisitor.visitLabel(label6);
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(182, context.className, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(177);
        }
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            String str5 = JSONSerializer;
            StringBuilder sb = new StringBuilder();
            sb.append("()");
            String str6 = SerialContext_desc;
            sb.append(str6);
            methodVisitor.visitMethodInsn(182, str5, "getContext", sb.toString());
            methodVisitor.visitVarInsn(58, context.var("parent"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("parent"));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            methodVisitor.visitMethodInsn(182, str5, "setContext", "(" + str6 + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean z3 = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        if (z3 || !context.writeDirect) {
            Label label7 = new Label();
            Label label8 = new Label();
            Label label9 = new Label();
            if (z3) {
                str = "parent";
                i2 = 182;
            } else {
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, 4);
                methodVisitor.visitVarInsn(25, 2);
                str = "parent";
                i2 = 182;
                methodVisitor.visitMethodInsn(182, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                methodVisitor.visitJumpInsn(153, label8);
            }
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(i2, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitJumpInsn(165, label8);
            methodVisitor.visitLabel(label9);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitMethodInsn(i2, SerializeWriter, "write", "(I)V");
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                methodVisitor.visitLdcInsn(context.beanInfo.typeKey);
            } else {
                methodVisitor.visitInsn(1);
            }
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitJumpInsn(167, label7);
            methodVisitor.visitLabel(label8);
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitLabel(label7);
        } else {
            methodVisitor.visitVarInsn(16, 123);
            str = "parent";
        }
        methodVisitor.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            _before(methodVisitor, context);
        }
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            methodVisitor.visitVarInsn(54, context.var("notWriteDefaultValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            String str7 = JSONSerializer;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(");
            String str8 = SerializeFilterable_desc;
            sb2.append(str8);
            sb2.append(")Z");
            methodVisitor.visitMethodInsn(182, str7, "checkValue", sb2.toString());
            methodVisitor.visitVarInsn(54, context.var("checkValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, str7, "hasNameFilters", "(" + str8 + ")Z");
            methodVisitor.visitVarInsn(54, context.var("hasNameFilters"));
        }
        int i7 = 0;
        while (i7 < length) {
            FieldInfo fieldInfo = fieldInfoArr[i7];
            Class<?> cls3 = fieldInfo.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                str2 = str3;
                i3 = i7;
                _int(cls, methodVisitor, fieldInfo, context, context.var(cls3.getName()), 'I');
            } else {
                if (cls3 == Long.TYPE) {
                    cls2 = cls;
                    _long(cls2, methodVisitor, fieldInfo, context);
                } else {
                    cls2 = cls;
                    if (cls3 == Float.TYPE) {
                        _float(cls2, methodVisitor, fieldInfo, context);
                    } else if (cls3 == Double.TYPE) {
                        _double(cls2, methodVisitor, fieldInfo, context);
                    } else if (cls3 == Boolean.TYPE) {
                        str2 = str3;
                        i3 = i7;
                        _int(cls, methodVisitor, fieldInfo, context, context.var(TypedValues.Custom.S_BOOLEAN), 'Z');
                    } else {
                        str2 = str3;
                        i3 = i7;
                        if (cls3 == Character.TYPE) {
                            _int(cls, methodVisitor, fieldInfo, context, context.var("char"), 'C');
                        } else if (cls3 == String.class) {
                            _string(cls2, methodVisitor, fieldInfo, context);
                        } else if (cls3 == BigDecimal.class) {
                            _decimal(cls2, methodVisitor, fieldInfo, context);
                        } else if (List.class.isAssignableFrom(cls3)) {
                            _list(cls2, methodVisitor, fieldInfo, context);
                        } else if (cls3.isEnum()) {
                            _enum(cls2, methodVisitor, fieldInfo, context);
                        } else {
                            _object(cls2, methodVisitor, fieldInfo, context);
                        }
                    }
                }
                str2 = str3;
                i3 = i7;
            }
            i7 = i3 + 1;
            str3 = str2;
        }
        String str9 = str3;
        if (!context.writeDirect) {
            _after(methodVisitor, context);
        }
        Label label10 = new Label();
        Label label11 = new Label();
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitIntInsn(16, 123);
        methodVisitor.visitJumpInsn(160, label10);
        methodVisitor.visitVarInsn(25, context.var(str9));
        methodVisitor.visitVarInsn(16, 123);
        String str10 = SerializeWriter;
        methodVisitor.visitMethodInsn(182, str10, "write", "(I)V");
        methodVisitor.visitLabel(label10);
        methodVisitor.visitVarInsn(25, context.var(str9));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitMethodInsn(182, str10, "write", "(I)V");
        methodVisitor.visitLabel(label11);
        methodVisitor.visitLabel(label);
        if (context.nonContext) {
            return;
        }
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(str));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "setContext", "(" + SerialContext_desc + ")V");
    }

    public JavaBeanSerializer createJavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) throws Exception {
        String str;
        String str2;
        boolean z2;
        Class<SerializeBeanInfo> cls;
        FieldInfo[] fieldInfoArr;
        int i2;
        String str3;
        boolean z3;
        boolean z4;
        String str4;
        boolean z5;
        boolean z6;
        String str5;
        JSONType jSONType;
        boolean z7;
        int i3;
        char c3;
        ClassWriter classWriter;
        String str6;
        int i4;
        Method method;
        Class<?> cls2 = serializeBeanInfo.beanType;
        if (cls2.isPrimitive()) {
            throw new JSONException("unsupportd class " + cls2.getName());
        }
        JSONType jSONType2 = (JSONType) TypeUtils.getAnnotation(cls2, JSONType.class);
        FieldInfo[] fieldInfoArr2 = serializeBeanInfo.fields;
        for (FieldInfo fieldInfo : fieldInfoArr2) {
            if (fieldInfo.field == null && (method = fieldInfo.method) != null && method.getDeclaringClass().isInterface()) {
                return new JavaBeanSerializer(serializeBeanInfo);
            }
        }
        FieldInfo[] fieldInfoArr3 = serializeBeanInfo.sortedFields;
        boolean z8 = fieldInfoArr3 == serializeBeanInfo.fields;
        if (fieldInfoArr3.length > 256) {
            return new JavaBeanSerializer(serializeBeanInfo);
        }
        for (FieldInfo fieldInfo2 : fieldInfoArr3) {
            if (!ASMUtils.checkName(fieldInfo2.getMember().getName())) {
                return new JavaBeanSerializer(serializeBeanInfo);
            }
        }
        String str7 = "ASMSerializer_" + this.seed.incrementAndGet() + StrPool.UNDERLINE + cls2.getSimpleName();
        Package r3 = ASMSerializerFactory.class.getPackage();
        if (r3 != null) {
            String name = r3.getName();
            String str8 = name.replace('.', '/') + "/" + str7;
            str = name + StrPool.DOT + str7;
            str2 = str8;
        } else {
            str = str7;
            str2 = str;
        }
        ASMSerializerFactory.class.getPackage().getName();
        ClassWriter classWriter2 = new ClassWriter();
        classWriter2.visit(49, 33, str2, JavaBeanSerializer, new String[]{ObjectSerializer});
        int length = fieldInfoArr3.length;
        int i5 = 0;
        while (i5 < length) {
            FieldInfo fieldInfo3 = fieldInfoArr3[i5];
            if (fieldInfo3.fieldClass.isPrimitive() || fieldInfo3.fieldClass == String.class) {
                i4 = length;
            } else {
                i4 = length;
                new FieldWriter(classWriter2, 1, fieldInfo3.name + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
                if (List.class.isAssignableFrom(fieldInfo3.fieldClass)) {
                    new FieldWriter(classWriter2, 1, fieldInfo3.name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
                }
                new FieldWriter(classWriter2, 1, fieldInfo3.name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
            }
            i5++;
            length = i4;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Class<SerializeBeanInfo> cls3 = SerializeBeanInfo.class;
        sb.append(ASMUtils.desc(cls3));
        sb.append(")V");
        MethodWriter methodWriter = new MethodWriter(classWriter2, 1, "<init>", sb.toString(), null, null);
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(183, JavaBeanSerializer, "<init>", "(" + ASMUtils.desc(cls3) + ")V");
        int i6 = 0;
        while (i6 < fieldInfoArr3.length) {
            FieldInfo fieldInfo4 = fieldInfoArr3[i6];
            if (fieldInfo4.fieldClass.isPrimitive() || fieldInfo4.fieldClass == String.class) {
                classWriter = classWriter2;
                str6 = str;
            } else {
                methodWriter.visitVarInsn(25, 0);
                if (fieldInfo4.method != null) {
                    methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo4.declaringClass)));
                    methodWriter.visitLdcInsn(fieldInfo4.method.getName());
                    classWriter = classWriter2;
                    str6 = str;
                    methodWriter.visitMethodInsn(184, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
                } else {
                    classWriter = classWriter2;
                    str6 = str;
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitLdcInsn(Integer.valueOf(i6));
                    methodWriter.visitMethodInsn(183, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
                }
                methodWriter.visitFieldInsn(181, str2, fieldInfo4.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            }
            i6++;
            classWriter2 = classWriter;
            str = str6;
        }
        ClassWriter classWriter3 = classWriter2;
        String str9 = str;
        methodWriter.visitInsn(177);
        char c4 = 4;
        methodWriter.visitMaxs(4, 4);
        methodWriter.visitEnd();
        if (jSONType2 != null) {
            for (SerializerFeature serializerFeature : jSONType2.serialzeFeatures()) {
                if (serializerFeature == SerializerFeature.DisableCircularReferenceDetect) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        } else {
            z2 = false;
        }
        int i7 = 0;
        while (true) {
            cls = cls3;
            fieldInfoArr = fieldInfoArr2;
            if (i7 >= 3) {
                break;
            }
            if (i7 == 0) {
                str4 = "write";
                z6 = z2;
                z5 = true;
            } else if (i7 == 1) {
                str4 = "writeNormal";
                z6 = z2;
                z5 = false;
            } else {
                str4 = "writeDirectNonContext";
                z5 = true;
                z6 = true;
            }
            ClassWriter classWriter4 = classWriter3;
            int i8 = i7;
            String str10 = str9;
            String str11 = str2;
            Context context = new Context(fieldInfoArr3, serializeBeanInfo, str2, z5, z6);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(L");
            String str12 = JSONSerializer;
            sb2.append(str12);
            sb2.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            MethodWriter methodWriter2 = new MethodWriter(classWriter3, 1, str4, sb2.toString(), null, new String[]{"java/io/IOException"});
            Label label = new Label();
            methodWriter2.visitVarInsn(25, 2);
            methodWriter2.visitJumpInsn(199, label);
            methodWriter2.visitVarInsn(25, 1);
            methodWriter2.visitMethodInsn(182, str12, "writeNull", "()V");
            methodWriter2.visitInsn(177);
            methodWriter2.visitLabel(label);
            methodWriter2.visitVarInsn(25, 1);
            methodWriter2.visitFieldInsn(180, str12, "out", SerializeWriter_desc);
            methodWriter2.visitVarInsn(58, context.var("out"));
            if (z8 || context.writeDirect || !(jSONType2 == null || jSONType2.alphabetic())) {
                str5 = str11;
            } else {
                Label label2 = new Label();
                methodWriter2.visitVarInsn(25, context.var("out"));
                methodWriter2.visitMethodInsn(182, SerializeWriter, "isSortField", "()Z");
                methodWriter2.visitJumpInsn(154, label2);
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitVarInsn(25, 3);
                methodWriter2.visitVarInsn(25, 4);
                methodWriter2.visitVarInsn(21, 5);
                str5 = str11;
                methodWriter2.visitMethodInsn(182, str5, "writeUnsorted", "(L" + str12 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodWriter2.visitInsn(177);
                methodWriter2.visitLabel(label2);
            }
            if (!context.writeDirect || z6) {
                jSONType = jSONType2;
                z7 = z8;
                i3 = 177;
                c3 = 4;
            } else {
                Label label3 = new Label();
                Label label4 = new Label();
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                String str13 = JavaBeanSerializer;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("(L");
                sb3.append(str12);
                jSONType = jSONType2;
                sb3.append(";)Z");
                z7 = z8;
                methodWriter2.visitMethodInsn(182, str13, "writeDirect", sb3.toString());
                methodWriter2.visitJumpInsn(154, label4);
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitVarInsn(25, 3);
                methodWriter2.visitVarInsn(25, 4);
                methodWriter2.visitVarInsn(21, 5);
                methodWriter2.visitMethodInsn(182, str5, "writeNormal", "(L" + str12 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodWriter2.visitInsn(177);
                methodWriter2.visitLabel(label4);
                methodWriter2.visitVarInsn(25, context.var("out"));
                methodWriter2.visitLdcInsn(Integer.valueOf(SerializerFeature.DisableCircularReferenceDetect.mask));
                methodWriter2.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                methodWriter2.visitJumpInsn(153, label3);
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitVarInsn(25, 3);
                c3 = 4;
                methodWriter2.visitVarInsn(25, 4);
                methodWriter2.visitVarInsn(21, 5);
                methodWriter2.visitMethodInsn(182, str5, "writeDirectNonContext", "(L" + str12 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                i3 = 177;
                methodWriter2.visitInsn(177);
                methodWriter2.visitLabel(label3);
            }
            methodWriter2.visitVarInsn(25, 2);
            methodWriter2.visitTypeInsn(192, ASMUtils.type(cls2));
            methodWriter2.visitVarInsn(58, context.var("entity"));
            generateWriteMethod(cls2, methodWriter2, fieldInfoArr3, context);
            methodWriter2.visitInsn(i3);
            methodWriter2.visitMaxs(7, context.variantIndex + 2);
            methodWriter2.visitEnd();
            str2 = str5;
            jSONType2 = jSONType;
            z8 = z7;
            cls3 = cls;
            fieldInfoArr2 = fieldInfoArr;
            classWriter3 = classWriter4;
            str9 = str10;
            c4 = c3;
            i7 = i8 + 1;
        }
        String str14 = str2;
        ClassWriter classWriter5 = classWriter3;
        String str15 = str9;
        if (z8) {
            i2 = 180;
        } else {
            Context context2 = new Context(fieldInfoArr3, serializeBeanInfo, str14, false, z2);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("(L");
            String str16 = JSONSerializer;
            sb4.append(str16);
            sb4.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            i2 = 180;
            MethodWriter methodWriter3 = new MethodWriter(classWriter5, 1, "writeUnsorted", sb4.toString(), null, new String[]{"java/io/IOException"});
            methodWriter3.visitVarInsn(25, 1);
            methodWriter3.visitFieldInsn(180, str16, "out", SerializeWriter_desc);
            methodWriter3.visitVarInsn(58, context2.var("out"));
            methodWriter3.visitVarInsn(25, 2);
            methodWriter3.visitTypeInsn(192, ASMUtils.type(cls2));
            methodWriter3.visitVarInsn(58, context2.var("entity"));
            generateWriteMethod(cls2, methodWriter3, fieldInfoArr, context2);
            methodWriter3.visitInsn(177);
            methodWriter3.visitMaxs(7, context2.variantIndex + 2);
            methodWriter3.visitEnd();
        }
        int i9 = 0;
        int i10 = 3;
        while (i9 < i10) {
            if (i9 == 0) {
                str3 = "writeAsArray";
                z4 = z2;
                z3 = true;
            } else if (i9 == 1) {
                str3 = "writeAsArrayNormal";
                z4 = z2;
                z3 = false;
            } else {
                str3 = "writeAsArrayNonContext";
                z3 = true;
                z4 = true;
            }
            int i11 = i9;
            int i12 = i2;
            Context context3 = new Context(fieldInfoArr3, serializeBeanInfo, str14, z3, z4);
            StringBuilder sb5 = new StringBuilder();
            sb5.append("(L");
            String str17 = JSONSerializer;
            sb5.append(str17);
            sb5.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            MethodWriter methodWriter4 = new MethodWriter(classWriter5, 1, str3, sb5.toString(), null, new String[]{"java/io/IOException"});
            methodWriter4.visitVarInsn(25, 1);
            methodWriter4.visitFieldInsn(i12, str17, "out", SerializeWriter_desc);
            methodWriter4.visitVarInsn(58, context3.var("out"));
            methodWriter4.visitVarInsn(25, 2);
            methodWriter4.visitTypeInsn(192, ASMUtils.type(cls2));
            methodWriter4.visitVarInsn(58, context3.var("entity"));
            generateWriteAsArray(cls2, methodWriter4, fieldInfoArr3, context3);
            methodWriter4.visitInsn(177);
            methodWriter4.visitMaxs(7, context3.variantIndex + 2);
            methodWriter4.visitEnd();
            i2 = i12;
            i10 = 3;
            i9 = i11 + 1;
        }
        byte[] byteArray = classWriter5.toByteArray();
        return (JavaBeanSerializer) this.classLoader.defineClassPublic(str15, byteArray, 0, byteArray.length).getConstructor(cls).newInstance(serializeBeanInfo);
    }

    public static class Context {
        static final int features = 5;
        static int fieldName = 6;
        static final int obj = 2;
        static int original = 7;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static int processValue = 8;
        static final int serializer = 1;
        private final SerializeBeanInfo beanInfo;
        private final String className;
        private final FieldInfo[] getters;
        private final boolean nonContext;
        private final boolean writeDirect;
        private Map<String, Integer> variants = new HashMap();
        private int variantIndex = 9;

        public Context(FieldInfo[] fieldInfoArr, SerializeBeanInfo serializeBeanInfo, String str, boolean z2, boolean z3) {
            this.getters = fieldInfoArr;
            this.className = str;
            this.beanInfo = serializeBeanInfo;
            this.writeDirect = z2;
            this.nonContext = z3 || serializeBeanInfo.beanType.isEnum();
        }

        public int getFieldOrinal(String str) {
            int length = this.getters.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (this.getters[i2].name.equals(str)) {
                    return i2;
                }
            }
            return -1;
        }

        public int var(String str) {
            if (this.variants.get(str) == null) {
                Map<String, Integer> map = this.variants;
                int i2 = this.variantIndex;
                this.variantIndex = i2 + 1;
                map.put(str, Integer.valueOf(i2));
            }
            return this.variants.get(str).intValue();
        }

        public int var(String str, int i2) {
            if (this.variants.get(str) == null) {
                this.variants.put(str, Integer.valueOf(this.variantIndex));
                this.variantIndex += i2;
            }
            return this.variants.get(str).intValue();
        }
    }
}
