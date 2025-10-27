package cn.hutool.core.convert.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ArrayConverter extends AbstractConverter<Object> {
    private static final long serialVersionUID = 1;
    private boolean ignoreElementError;
    private final Class<?> targetComponentType;
    private final Class<?> targetType;

    public ArrayConverter(Class<?> cls) {
        this(cls, false);
    }

    private Object convertArrayToArray(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (ArrayUtil.getComponentType(obj) == this.targetComponentType) {
            return obj;
        }
        int length = ArrayUtil.length(obj);
        Object objNewInstance = Array.newInstance(this.targetComponentType, length);
        for (int i2 = 0; i2 < length; i2++) {
            Array.set(objNewInstance, i2, convertComponentType(Array.get(obj, i2)));
        }
        return objNewInstance;
    }

    private Object convertComponentType(Object obj) {
        return Convert.convertWithCheck(this.targetComponentType, obj, null, this.ignoreElementError);
    }

    private Object convertObjectToArray(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (obj instanceof CharSequence) {
            Class<?> cls = this.targetComponentType;
            if (cls == Character.TYPE || cls == Character.class) {
                return convertArrayToArray(obj.toString().toCharArray());
            }
            if (cls != Byte.TYPE) {
                return convertArrayToArray(CharSequenceUtil.splitToArray((CharSequence) obj.toString(), ','));
            }
            String string = obj.toString();
            return Base64.isBase64(string) ? Base64.decode(obj.toString()) : string.getBytes();
        }
        int i2 = 0;
        if (obj instanceof List) {
            List list = (List) obj;
            Object objNewInstance = Array.newInstance(this.targetComponentType, list.size());
            while (i2 < list.size()) {
                Array.set(objNewInstance, i2, convertComponentType(list.get(i2)));
                i2++;
            }
            return objNewInstance;
        }
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            Object objNewInstance2 = Array.newInstance(this.targetComponentType, collection.size());
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Array.set(objNewInstance2, i2, convertComponentType(it.next()));
                i2++;
            }
            return objNewInstance2;
        }
        if (obj instanceof Iterable) {
            List list2 = IterUtil.toList((Iterable) obj);
            Object objNewInstance3 = Array.newInstance(this.targetComponentType, list2.size());
            while (i2 < list2.size()) {
                Array.set(objNewInstance3, i2, convertComponentType(list2.get(i2)));
                i2++;
            }
            return objNewInstance3;
        }
        if (!(obj instanceof Iterator)) {
            return ((obj instanceof Number) && Byte.TYPE == this.targetComponentType) ? ByteUtil.numberToBytes((Number) obj) : ((obj instanceof Serializable) && Byte.TYPE == this.targetComponentType) ? ObjectUtil.serialize(obj) : convertToSingleElementArray(obj);
        }
        List list3 = IterUtil.toList((Iterator) obj);
        Object objNewInstance4 = Array.newInstance(this.targetComponentType, list3.size());
        while (i2 < list3.size()) {
            Array.set(objNewInstance4, i2, convertComponentType(list3.get(i2)));
            i2++;
        }
        return objNewInstance4;
    }

    private Object[] convertToSingleElementArray(Object obj) {
        Object[] objArrNewArray = ArrayUtil.newArray(this.targetComponentType, 1);
        objArrNewArray[0] = convertComponentType(obj);
        return objArrNewArray;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Object convertInternal(Object obj) {
        return obj.getClass().isArray() ? convertArrayToArray(obj) : convertObjectToArray(obj);
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<Object> getTargetType() {
        return this.targetType;
    }

    public void setIgnoreElementError(boolean z2) {
        this.ignoreElementError = z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArrayConverter(Class<?> cls, boolean z2) {
        Class cls2 = cls == null ? Object[].class : cls;
        if (cls2.isArray()) {
            this.targetType = cls2;
            this.targetComponentType = cls2.getComponentType();
        } else {
            this.targetComponentType = cls2;
            this.targetType = ArrayUtil.getArrayType(cls2);
        }
        this.ignoreElementError = z2;
    }
}
