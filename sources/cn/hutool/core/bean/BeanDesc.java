package cn.hutool.core.bean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ModifierUtil;
import cn.hutool.core.util.ReflectUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BeanDesc implements Serializable {
    private static final long serialVersionUID = 1;
    private final Class<?> beanClass;
    private final Map<String, PropDesc> propMap = new LinkedHashMap();

    public BeanDesc(Class<?> cls) throws SecurityException, IllegalArgumentException {
        Assert.notNull(cls);
        this.beanClass = cls;
        init();
    }

    private PropDesc createProp(Field field, Method[] methodArr) {
        PropDesc propDescFindProp = findProp(field, methodArr, false);
        if (propDescFindProp.getter == null || propDescFindProp.setter == null) {
            PropDesc propDescFindProp2 = findProp(field, methodArr, true);
            if (propDescFindProp.getter == null) {
                propDescFindProp.getter = propDescFindProp2.getter;
            }
            if (propDescFindProp.setter == null) {
                propDescFindProp.setter = propDescFindProp2.setter;
            }
        }
        return propDescFindProp;
    }

    private PropDesc findProp(Field field, Method[] methodArr, boolean z2) {
        String name = field.getName();
        Class<?> type = field.getType();
        boolean zIsBoolean = BooleanUtil.isBoolean(type);
        Method method = null;
        Method method2 = null;
        for (Method method3 : methodArr) {
            String name2 = method3.getName();
            if (method3.getParameterCount() == 0) {
                if (isMatchGetter(name2, name, zIsBoolean, z2)) {
                    method = method3;
                }
            } else if (isMatchSetter(name2, name, zIsBoolean, z2) && type.isAssignableFrom(method3.getParameterTypes()[0])) {
                method2 = method3;
            }
            if (method != null && method2 != null) {
                break;
            }
        }
        return new PropDesc(field, method, method2);
    }

    private BeanDesc init() throws SecurityException {
        Method[] methods = ReflectUtil.getMethods(this.beanClass, new Filter() { // from class: cn.hutool.core.bean.b
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ReflectUtil.isGetterOrSetterIgnoreCase((Method) obj);
            }
        });
        for (Field field : ReflectUtil.getFields(this.beanClass)) {
            if (!ModifierUtil.isStatic(field) && !ReflectUtil.isOuterClassField(field)) {
                PropDesc propDescCreateProp = createProp(field, methods);
                this.propMap.putIfAbsent(propDescCreateProp.getFieldName(), propDescCreateProp);
            }
        }
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
    
        if (("is" + r7).equals(r4) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isMatchGetter(java.lang.String r4, java.lang.String r5, boolean r6, boolean r7) {
        /*
            r3 = this;
            if (r7 == 0) goto Lc
            java.lang.String r4 = r4.toLowerCase()
            java.lang.String r5 = r5.toLowerCase()
            r7 = r5
            goto L10
        Lc:
            java.lang.String r7 = cn.hutool.core.text.CharSequenceUtil.upperFirst(r5)
        L10:
            java.lang.String r0 = "get"
            if (r6 == 0) goto L64
            java.lang.String r6 = "is"
            boolean r1 = r5.startsWith(r6)
            r2 = 1
            if (r1 == 0) goto L4e
            boolean r5 = r4.equals(r5)
            if (r5 != 0) goto L4d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r0)
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            boolean r5 = r5.equals(r4)
            if (r5 != 0) goto L4d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r6)
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            boolean r5 = r5.equals(r4)
            if (r5 == 0) goto L64
        L4d:
            return r2
        L4e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r6)
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            boolean r5 = r5.equals(r4)
            if (r5 == 0) goto L64
            return r2
        L64:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r0)
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            boolean r4 = r5.equals(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.bean.BeanDesc.isMatchGetter(java.lang.String, java.lang.String, boolean, boolean):boolean");
    }

    private boolean isMatchSetter(String str, String str2, boolean z2, boolean z3) {
        String strUpperFirst;
        if (z3) {
            str = str.toLowerCase();
            str2 = str2.toLowerCase();
            strUpperFirst = str2;
        } else {
            strUpperFirst = CharSequenceUtil.upperFirst(str2);
        }
        if (!str.startsWith("set")) {
            return false;
        }
        if (z2 && str2.startsWith("is")) {
            if (("set" + CharSequenceUtil.removePrefix(str2, "is")).equals(str)) {
                return true;
            }
            if (("set" + strUpperFirst).equals(str)) {
                return true;
            }
        }
        return ("set" + strUpperFirst).equals(str);
    }

    public Field getField(String str) {
        PropDesc propDesc = this.propMap.get(str);
        if (propDesc == null) {
            return null;
        }
        return propDesc.getField();
    }

    public Method getGetter(String str) {
        PropDesc propDesc = this.propMap.get(str);
        if (propDesc == null) {
            return null;
        }
        return propDesc.getGetter();
    }

    public String getName() {
        return this.beanClass.getName();
    }

    public PropDesc getProp(String str) {
        return this.propMap.get(str);
    }

    public Map<String, PropDesc> getPropMap(boolean z2) {
        return z2 ? new CaseInsensitiveMap(1.0f, this.propMap) : this.propMap;
    }

    public Collection<PropDesc> getProps() {
        return this.propMap.values();
    }

    public Method getSetter(String str) {
        PropDesc propDesc = this.propMap.get(str);
        if (propDesc == null) {
            return null;
        }
        return propDesc.getSetter();
    }

    public String getSimpleName() {
        return this.beanClass.getSimpleName();
    }
}
