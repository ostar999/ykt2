package cn.hutool.core.text.split;

import cn.hutool.core.collection.ComputeIter;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.finder.TextFinder;
import cn.hutool.core.text.split.SplitIter;
import cn.hutool.core.util.StrUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes.dex */
public class SplitIter extends ComputeIter<String> implements Serializable {
    private static final long serialVersionUID = 1;
    private int count;
    private final TextFinder finder;
    private final boolean ignoreEmpty;
    private final int limit;
    private int offset;
    private final String text;

    public SplitIter(CharSequence charSequence, TextFinder textFinder, int i2, boolean z2) throws IllegalArgumentException {
        Assert.notNull(charSequence, "Text must be not null!", new Object[0]);
        this.text = charSequence.toString();
        this.finder = textFinder.setText(charSequence);
        this.limit = i2 <= 0 ? Integer.MAX_VALUE : i2;
        this.ignoreEmpty = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$toList$0(boolean z2, String str) {
        return z2 ? CharSequenceUtil.trim(str) : str;
    }

    public void reset() {
        this.finder.reset();
        this.offset = 0;
        this.count = 0;
    }

    public String[] toArray(boolean z2) {
        return (String[]) toList(z2).toArray(new String[0]);
    }

    public List<String> toList(final boolean z2) {
        return toList(new Function() { // from class: l0.a
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SplitIter.lambda$toList$0(z2, (String) obj);
            }
        });
    }

    @Override // cn.hutool.core.collection.ComputeIter
    public String computeNext() {
        if (this.count >= this.limit || this.offset > this.text.length()) {
            return null;
        }
        if (this.count == this.limit - 1) {
            if (this.ignoreEmpty && this.offset == this.text.length()) {
                return null;
            }
            this.count++;
            return this.text.substring(this.offset);
        }
        int iStart = this.finder.start(this.offset);
        if (iStart < 0) {
            if (this.offset <= this.text.length()) {
                String strSubstring = this.text.substring(this.offset);
                if (!this.ignoreEmpty || !strSubstring.isEmpty()) {
                    this.offset = Integer.MAX_VALUE;
                    return strSubstring;
                }
            }
            return null;
        }
        String strSubstring2 = this.text.substring(this.offset, iStart);
        this.offset = this.finder.end(iStart);
        if (this.ignoreEmpty && strSubstring2.isEmpty()) {
            return computeNext();
        }
        this.count++;
        return strSubstring2;
    }

    public <T> List<T> toList(Function<String, T> function) {
        ArrayList arrayList = new ArrayList();
        while (hasNext()) {
            Object objApply = function.apply(next());
            if (!this.ignoreEmpty || !StrUtil.isEmptyIfStr(objApply)) {
                arrayList.add(objApply);
            }
        }
        return arrayList.isEmpty() ? new ArrayList(0) : arrayList;
    }
}
