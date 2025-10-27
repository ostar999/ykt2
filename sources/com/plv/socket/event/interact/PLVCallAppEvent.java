package com.plv.socket.event.interact;

import cn.hutool.core.text.CharPool;
import com.google.android.exoplayer2.text.ttml.TtmlNode;

/* loaded from: classes5.dex */
public class PLVCallAppEvent {
    private String event;
    private ValueBean value;

    public static class ValueBean {
        private String type;
        private String url;

        public String toString() {
            return "ValueBean{type='" + this.type + CharPool.SINGLE_QUOTE + ", url='" + this.url + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public String getUrl() {
        ValueBean valueBean = this.value;
        if (valueBean != null) {
            return valueBean.url;
        }
        return null;
    }

    public boolean isInsideOpen() {
        ValueBean valueBean = this.value;
        return valueBean != null && "inside".equals(valueBean.type);
    }

    public boolean isOpenLinkEvent() {
        return "openLink".equals(this.event);
    }

    public boolean isOutsideOpen() {
        ValueBean valueBean = this.value;
        return valueBean != null && TtmlNode.ANNOTATION_POSITION_OUTSIDE.equals(valueBean.type);
    }

    public String toString() {
        return "PLVCallAppEvent{event='" + this.event + CharPool.SINGLE_QUOTE + ", value=" + this.value + '}';
    }
}
