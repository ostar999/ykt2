package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.vivo.push.PushClientConstants;
import java.util.Map;

/* loaded from: classes.dex */
public class StackTraceElementConverter extends AbstractConverter<StackTraceElement> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public StackTraceElement convertInternal(Object obj) {
        if (!(obj instanceof Map)) {
            return null;
        }
        Map map = (Map) obj;
        return new StackTraceElement(MapUtil.getStr(map, PushClientConstants.TAG_CLASS_NAME), MapUtil.getStr(map, "methodName"), MapUtil.getStr(map, "fileName"), ((Integer) ObjectUtil.defaultIfNull((int) MapUtil.getInt(map, "lineNumber"), 0)).intValue());
    }
}
