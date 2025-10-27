package org.eclipse.jetty.util.ajax;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class JSONDateConvertor implements JSON.Convertor {
    private static final Logger LOG = Log.getLogger((Class<?>) JSONDateConvertor.class);
    private final DateCache _dateCache;
    private final SimpleDateFormat _format;
    private final boolean _fromJSON;

    public JSONDateConvertor() {
        this(false);
    }

    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    public Object fromJSON(Map map) {
        Object object;
        if (!this._fromJSON) {
            throw new UnsupportedOperationException();
        }
        try {
            synchronized (this._format) {
                object = this._format.parseObject((String) map.get("value"));
            }
            return object;
        } catch (Exception e2) {
            LOG.warn(e2);
            return null;
        }
    }

    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    public void toJSON(Object obj, JSON.Output output) {
        String str = this._dateCache.format((Date) obj);
        if (!this._fromJSON) {
            output.add(str);
        } else {
            output.addClass(obj.getClass());
            output.add("value", str);
        }
    }

    public JSONDateConvertor(boolean z2) {
        this(DateCache.DEFAULT_FORMAT, TimeZone.getTimeZone("GMT"), z2);
    }

    public JSONDateConvertor(String str, TimeZone timeZone, boolean z2) {
        DateCache dateCache = new DateCache(str);
        this._dateCache = dateCache;
        dateCache.setTimeZone(timeZone);
        this._fromJSON = z2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        this._format = simpleDateFormat;
        simpleDateFormat.setTimeZone(timeZone);
    }

    public JSONDateConvertor(String str, TimeZone timeZone, boolean z2, Locale locale) {
        DateCache dateCache = new DateCache(str, locale);
        this._dateCache = dateCache;
        dateCache.setTimeZone(timeZone);
        this._fromJSON = z2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, new DateFormatSymbols(locale));
        this._format = simpleDateFormat;
        simpleDateFormat.setTimeZone(timeZone);
    }
}
