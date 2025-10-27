package me.dkzwm.widget.srl;

import android.content.Context;
import android.util.AttributeSet;
import me.dkzwm.widget.srl.extra.footer.ClassicFooter;
import me.dkzwm.widget.srl.extra.header.ClassicHeader;

/* loaded from: classes9.dex */
public class ClassicSmoothRefreshLayout extends SmoothRefreshLayout {
    private ClassicFooter mClassicFooter;
    private ClassicHeader mClassicHeader;

    public ClassicSmoothRefreshLayout(Context context) {
        super(context);
    }

    @Override // me.dkzwm.widget.srl.SmoothRefreshLayout
    public void init(Context context, AttributeSet attributeSet, int i2, int i3) {
        super.init(context, attributeSet, i2, i3);
        ClassicHeader classicHeader = new ClassicHeader(context);
        this.mClassicHeader = classicHeader;
        setHeaderView(classicHeader);
        ClassicFooter classicFooter = new ClassicFooter(context);
        this.mClassicFooter = classicFooter;
        setFooterView(classicFooter);
    }

    public void setLastUpdateTimeFooterKey(String str) {
        ClassicFooter classicFooter = this.mClassicFooter;
        if (classicFooter != null) {
            classicFooter.setLastUpdateTimeKey(str);
        }
    }

    public void setLastUpdateTimeHeaderKey(String str) {
        ClassicHeader classicHeader = this.mClassicHeader;
        if (classicHeader != null) {
            classicHeader.setLastUpdateTimeKey(str);
        }
    }

    public void setLastUpdateTimeKey(String str) {
        setLastUpdateTimeHeaderKey(str + "_header");
        setLastUpdateTimeFooterKey(str + "_footer");
    }

    public ClassicSmoothRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ClassicSmoothRefreshLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
