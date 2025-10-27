package com.plv.livescenes.model.interact;

import com.plv.foundationsdk.model.PLVFoundationVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVWebviewUpdateAppStatusVO implements PLVFoundationVO {
    private String event;
    private Value value;

    public static class Value {
        private List<Function> dataArray;

        public static class Function {
            private String event;
            private String icon;
            private boolean isShow;
            private String title;

            public String getEvent() {
                return this.event;
            }

            public String getIcon() {
                return this.icon;
            }

            public String getTitle() {
                return this.title;
            }

            public boolean isShow() {
                return this.isShow;
            }

            public void setEvent(String str) {
                this.event = str;
            }

            public void setIcon(String str) {
                this.icon = str;
            }

            public void setShow(boolean z2) {
                this.isShow = z2;
            }

            public void setTitle(String str) {
                this.title = str;
            }
        }

        public List<Function> getDataArray() {
            return this.dataArray;
        }

        public void setDataArray(List<Function> list) {
            this.dataArray = list;
        }
    }

    public String getEvent() {
        return this.event;
    }

    public Value getValue() {
        return this.value;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
