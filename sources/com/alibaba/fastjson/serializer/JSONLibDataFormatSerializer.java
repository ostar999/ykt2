package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/* loaded from: classes2.dex */
public class JSONLibDataFormatSerializer implements ObjectSerializer {
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        if (obj == null) {
            jSONSerializer.out.writeNull();
            return;
        }
        Date date = (Date) obj;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("date", (Object) Integer.valueOf(date.getDate()));
        jSONObject.put("day", (Object) Integer.valueOf(date.getDay()));
        jSONObject.put("hours", (Object) Integer.valueOf(date.getHours()));
        jSONObject.put("minutes", (Object) Integer.valueOf(date.getMinutes()));
        jSONObject.put("month", (Object) Integer.valueOf(date.getMonth()));
        jSONObject.put("seconds", (Object) Integer.valueOf(date.getSeconds()));
        jSONObject.put(CrashHianalyticsData.TIME, (Object) Long.valueOf(date.getTime()));
        jSONObject.put("timezoneOffset", (Object) Integer.valueOf(date.getTimezoneOffset()));
        jSONObject.put("year", (Object) Integer.valueOf(date.getYear()));
        jSONSerializer.write(jSONObject);
    }
}
