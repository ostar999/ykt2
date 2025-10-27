package cn.hutool.core.text.csv;

import cn.hutool.core.text.csv.CsvConfig;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CsvConfig<T extends CsvConfig<T>> implements Serializable {
    private static final long serialVersionUID = -8069578249066158459L;
    protected char fieldSeparator = ',';
    protected char textDelimiter = '\"';
    protected Character commentCharacter = '#';
    protected Map<String, String> headerAlias = new LinkedHashMap();

    public T addHeaderAlias(String str, String str2) {
        this.headerAlias.put(str, str2);
        return this;
    }

    public T disableComment() {
        return (T) setCommentCharacter(null);
    }

    public T removeHeaderAlias(String str) {
        this.headerAlias.remove(str);
        return this;
    }

    public T setCommentCharacter(Character ch) {
        this.commentCharacter = ch;
        return this;
    }

    public T setFieldSeparator(char c3) {
        this.fieldSeparator = c3;
        return this;
    }

    public T setHeaderAlias(Map<String, String> map) {
        this.headerAlias = map;
        return this;
    }

    public T setTextDelimiter(char c3) {
        this.textDelimiter = c3;
        return this;
    }
}
