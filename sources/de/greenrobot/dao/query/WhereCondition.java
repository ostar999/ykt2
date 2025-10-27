package de.greenrobot.dao.query;

import cn.hutool.core.text.CharPool;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.Property;
import java.util.Date;
import java.util.List;

/* loaded from: classes8.dex */
public interface WhereCondition {
    void appendTo(StringBuilder sb, String str);

    void appendValuesTo(List<Object> list);

    public static class StringCondition extends AbstractCondition {
        protected final String string;

        public StringCondition(String str) {
            this.string = str;
        }

        @Override // de.greenrobot.dao.query.WhereCondition
        public void appendTo(StringBuilder sb, String str) {
            sb.append(this.string);
        }

        public StringCondition(String str, Object obj) {
            super(obj);
            this.string = str;
        }

        public StringCondition(String str, Object... objArr) {
            super(objArr);
            this.string = str;
        }
    }

    public static class PropertyCondition extends AbstractCondition {
        public final String op;
        public final Property property;

        public PropertyCondition(Property property, String str) {
            this.property = property;
            this.op = str;
        }

        private static Object checkValueForType(Property property, Object obj) {
            if (obj != null && obj.getClass().isArray()) {
                throw new DaoException("Illegal value: found array, but simple object required");
            }
            Class<?> cls = property.type;
            if (cls == Date.class) {
                if (obj instanceof Date) {
                    return Long.valueOf(((Date) obj).getTime());
                }
                if (obj instanceof Long) {
                    return obj;
                }
                throw new DaoException("Illegal date value: expected java.util.Date or Long for value " + obj);
            }
            if (cls == Boolean.TYPE || cls == Boolean.class) {
                if (obj instanceof Boolean) {
                    return Integer.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
                }
                if (obj instanceof Number) {
                    int iIntValue = ((Number) obj).intValue();
                    if (iIntValue != 0 && iIntValue != 1) {
                        throw new DaoException("Illegal boolean value: numbers must be 0 or 1, but was " + obj);
                    }
                } else if (obj instanceof String) {
                    String str = (String) obj;
                    if ("TRUE".equalsIgnoreCase(str)) {
                        return 1;
                    }
                    if ("FALSE".equalsIgnoreCase(str)) {
                        return 0;
                    }
                    throw new DaoException("Illegal boolean value: Strings must be \"TRUE\" or \"FALSE\" (case insesnsitive), but was " + obj);
                }
            }
            return obj;
        }

        private static Object[] checkValuesForType(Property property, Object[] objArr) {
            for (int i2 = 0; i2 < objArr.length; i2++) {
                objArr[i2] = checkValueForType(property, objArr[i2]);
            }
            return objArr;
        }

        @Override // de.greenrobot.dao.query.WhereCondition
        public void appendTo(StringBuilder sb, String str) {
            if (str != null) {
                sb.append(str);
                sb.append('.');
            }
            sb.append(CharPool.SINGLE_QUOTE);
            sb.append(this.property.columnName);
            sb.append(CharPool.SINGLE_QUOTE);
            sb.append(this.op);
        }

        public PropertyCondition(Property property, String str, Object obj) {
            super(checkValueForType(property, obj));
            this.property = property;
            this.op = str;
        }

        public PropertyCondition(Property property, String str, Object[] objArr) {
            super(checkValuesForType(property, objArr));
            this.property = property;
            this.op = str;
        }
    }

    public static abstract class AbstractCondition implements WhereCondition {
        protected final boolean hasSingleValue;
        protected final Object value;
        protected final Object[] values;

        public AbstractCondition() {
            this.hasSingleValue = false;
            this.value = null;
            this.values = null;
        }

        @Override // de.greenrobot.dao.query.WhereCondition
        public void appendValuesTo(List<Object> list) {
            if (this.hasSingleValue) {
                list.add(this.value);
            }
            Object[] objArr = this.values;
            if (objArr != null) {
                for (Object obj : objArr) {
                    list.add(obj);
                }
            }
        }

        public AbstractCondition(Object obj) {
            this.value = obj;
            this.hasSingleValue = true;
            this.values = null;
        }

        public AbstractCondition(Object[] objArr) {
            this.value = null;
            this.hasSingleValue = false;
            this.values = objArr;
        }
    }
}
