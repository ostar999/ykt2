package com.plv.livescenes.download;

import androidx.annotation.IntRange;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVProgressManager {
    public static final int NODE_TOTAL_PERCENT = 100;
    public static final int TOTAL_PERCENT = 100;
    private List<ProgressNode> list;

    public static class ProgressNode {
        int curPercent = 0;
        private String name;

        @IntRange(from = 0, to = 100)
        int nodePercentInAll;

        public ProgressNode(String str, int i2) {
            this.nodePercentInAll = i2;
            this.name = str;
        }
    }

    public static class ProgressNodeBuilder {
        private List<ProgressNode> list = new ArrayList();

        public ProgressNodeBuilder add(String str, @IntRange(from = 0, to = 100) int i2) {
            Iterator<ProgressNode> it = this.list.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next().name)) {
                    throw new RuntimeException("不可以加入同名的任务");
                }
            }
            this.list.add(new ProgressNode(str, i2));
            return this;
        }

        public PLVProgressManager build() {
            Iterator<ProgressNode> it = this.list.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                i2 += it.next().nodePercentInAll;
            }
            if (i2 == 100) {
                return new PLVProgressManager(this);
            }
            throw new RuntimeException("任务的总占比必须为100");
        }
    }

    @IntRange(from = 0, to = 100)
    public int updateAndGetProgressInTotal(String str, @IntRange(from = 0, to = 100) int i2) {
        int i3 = 0;
        for (ProgressNode progressNode : this.list) {
            if (str.equals(progressNode.name)) {
                progressNode.curPercent = (progressNode.nodePercentInAll * i2) / 100;
            }
            i3 += progressNode.curPercent;
        }
        return i3;
    }

    private PLVProgressManager(ProgressNodeBuilder progressNodeBuilder) {
        this.list = progressNodeBuilder.list;
    }
}
