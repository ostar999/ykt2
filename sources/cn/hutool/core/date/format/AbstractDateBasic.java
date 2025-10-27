package cn.hutool.core.date.format;

import cn.hutool.core.text.StrPool;
import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public abstract class AbstractDateBasic implements DateBasic, Serializable {
    private static final long serialVersionUID = 6333136319870641818L;
    protected final Locale locale;
    protected final String pattern;
    protected final TimeZone timeZone;

    public AbstractDateBasic(String str, TimeZone timeZone, Locale locale) {
        this.pattern = str;
        this.timeZone = timeZone;
        this.locale = locale;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDatePrinter)) {
            return false;
        }
        AbstractDateBasic abstractDateBasic = (AbstractDateBasic) obj;
        return this.pattern.equals(abstractDateBasic.pattern) && this.timeZone.equals(abstractDateBasic.timeZone) && this.locale.equals(abstractDateBasic.locale);
    }

    @Override // cn.hutool.core.date.format.DateBasic
    public Locale getLocale() {
        return this.locale;
    }

    @Override // cn.hutool.core.date.format.DateBasic
    public String getPattern() {
        return this.pattern;
    }

    @Override // cn.hutool.core.date.format.DateBasic
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public int hashCode() {
        return this.pattern.hashCode() + ((this.timeZone.hashCode() + (this.locale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDatePrinter[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + StrPool.BRACKET_END;
    }
}
