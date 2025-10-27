package cn.hutool.core.text.replacer;

import cn.hutool.core.lang.Chain;
import cn.hutool.core.text.StrBuilder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class ReplacerChain extends StrReplacer implements Chain<StrReplacer, ReplacerChain> {
    private static final long serialVersionUID = 1;
    private final List<StrReplacer> replacers = new LinkedList();

    public ReplacerChain(StrReplacer... strReplacerArr) {
        for (StrReplacer strReplacer : strReplacerArr) {
            addChain(strReplacer);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<StrReplacer> iterator() {
        return this.replacers.iterator();
    }

    @Override // cn.hutool.core.text.replacer.StrReplacer
    public int replace(CharSequence charSequence, int i2, StrBuilder strBuilder) {
        Iterator<StrReplacer> it = this.replacers.iterator();
        int iReplace = 0;
        while (it.hasNext() && (iReplace = it.next().replace(charSequence, i2, strBuilder)) == 0) {
        }
        return iReplace;
    }

    @Override // cn.hutool.core.lang.Chain
    public ReplacerChain addChain(StrReplacer strReplacer) {
        this.replacers.add(strReplacer);
        return this;
    }
}
