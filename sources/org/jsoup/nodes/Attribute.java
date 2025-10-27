package org.jsoup.nodes;

import com.psychiatrygarden.db.SQLHelper;
import java.util.Arrays;
import java.util.Map;
import org.eclipse.jetty.servlet.ServletHandler;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/* loaded from: classes9.dex */
public class Attribute implements Map.Entry<String, String>, Cloneable {
    private static final String[] booleanAttributes = {"allowfullscreen", "async", "autofocus", "checked", "compact", "declare", ServletHandler.__DEFAULT_SERVLET, "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", SQLHelper.SELECTED, "sortable", "truespeed", "typemustmatch"};
    private String key;
    private String value;

    public Attribute(String str, String str2) {
        Validate.notEmpty(str);
        Validate.notNull(str2);
        this.key = str.trim().toLowerCase();
        this.value = str2;
    }

    public static Attribute createFromEncoded(String str, String str2) {
        return new Attribute(str, Entities.unescape(str2, true));
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Attribute)) {
            return false;
        }
        Attribute attribute = (Attribute) obj;
        String str = this.key;
        if (str == null ? attribute.key != null : !str.equals(attribute.key)) {
            return false;
        }
        String str2 = this.value;
        String str3 = attribute.value;
        return str2 == null ? str3 == null : str2.equals(str3);
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        String str = this.key;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.value;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        html(sb, new Document("").outputSettings());
        return sb.toString();
    }

    public boolean isDataAttribute() {
        return this.key.startsWith("data-") && this.key.length() > 5;
    }

    public void setKey(String str) {
        Validate.notEmpty(str);
        this.key = str.trim().toLowerCase();
    }

    public final boolean shouldCollapseAttribute(Document.OutputSettings outputSettings) {
        return ("".equals(this.value) || this.value.equalsIgnoreCase(this.key)) && outputSettings.syntax() == Document.OutputSettings.Syntax.html && Arrays.binarySearch(booleanAttributes, this.key) >= 0;
    }

    public String toString() {
        return html();
    }

    public Attribute clone() {
        try {
            return (Attribute) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // java.util.Map.Entry
    public String getKey() {
        return this.key;
    }

    @Override // java.util.Map.Entry
    public String getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public String setValue(String str) {
        Validate.notNull(str);
        String str2 = this.value;
        this.value = str;
        return str2;
    }

    public void html(StringBuilder sb, Document.OutputSettings outputSettings) {
        sb.append(this.key);
        if (shouldCollapseAttribute(outputSettings)) {
            return;
        }
        sb.append("=\"");
        Entities.escape(sb, this.value, outputSettings, true, false, false);
        sb.append('\"');
    }
}
