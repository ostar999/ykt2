package net.polyv.danmaku.danmaku.renderer.android;

import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.IDisplayer;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes9.dex */
public class DanmakusRetainer {
    private IDanmakusRetainer rldrInstance = null;
    private IDanmakusRetainer lrdrInstance = null;
    private IDanmakusRetainer ftdrInstance = null;
    private IDanmakusRetainer fbdrInstance = null;

    public static class AlignBottomRetainer extends FTDanmakusRetainer {
        protected RetainerConsumer mConsumer;
        protected Danmakus mVisibleDanmakus;

        public class RetainerConsumer extends IDanmakus.Consumer<BaseDanmaku, RetainerState> {
            public IDisplayer disp;
            float topPos;
            int lines = 0;
            public BaseDanmaku removeItem = null;
            public BaseDanmaku firstItem = null;
            public BaseDanmaku drawItem = null;
            boolean willHit = false;

            public RetainerConsumer() {
            }

            @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
            public void before() {
                this.lines = 0;
                this.firstItem = null;
                this.removeItem = null;
                this.willHit = false;
            }

            @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
            public int accept(BaseDanmaku baseDanmaku) {
                if (AlignBottomRetainer.this.mCancelFixingFlag) {
                    return 1;
                }
                this.lines++;
                if (baseDanmaku == this.drawItem) {
                    this.removeItem = null;
                    this.willHit = false;
                    return 1;
                }
                if (this.firstItem == null) {
                    this.firstItem = baseDanmaku;
                    if (baseDanmaku.getBottom() != this.disp.getHeight()) {
                        return 1;
                    }
                }
                if (this.topPos < this.disp.getAllMarginTop()) {
                    this.removeItem = null;
                    return 1;
                }
                IDisplayer iDisplayer = this.disp;
                BaseDanmaku baseDanmaku2 = this.drawItem;
                boolean zWillHitInDuration = DanmakuUtils.willHitInDuration(iDisplayer, baseDanmaku, baseDanmaku2, baseDanmaku2.getDuration(), this.drawItem.getTimer().currMillisecond);
                this.willHit = zWillHitInDuration;
                if (zWillHitInDuration) {
                    this.topPos = (baseDanmaku.getTop() - this.disp.getMargin()) - this.drawItem.paintHeight;
                    return 0;
                }
                this.removeItem = baseDanmaku;
                return 1;
            }

            @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
            public RetainerState result() {
                RetainerState retainerState = new RetainerState();
                retainerState.lines = this.lines;
                retainerState.firstItem = this.firstItem;
                retainerState.removeItem = this.removeItem;
                retainerState.willHit = this.willHit;
                return retainerState;
            }
        }

        private AlignBottomRetainer() {
            super();
            this.mConsumer = new RetainerConsumer();
            this.mVisibleDanmakus = new Danmakus(2);
        }

        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer, net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void clear() {
            this.mCancelFixingFlag = true;
            this.mVisibleDanmakus.clear();
        }

        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer, net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier) {
            boolean z2;
            boolean z3;
            BaseDanmaku baseDanmaku2;
            BaseDanmaku baseDanmaku3;
            int i2;
            if (baseDanmaku.isOutside()) {
                return;
            }
            boolean zIsShown = baseDanmaku.isShown();
            float top2 = zIsShown ? baseDanmaku.getTop() : -1.0f;
            int i3 = 1;
            boolean z4 = false;
            boolean z5 = (zIsShown || this.mVisibleDanmakus.isEmpty()) ? false : true;
            if (top2 < iDisplayer.getAllMarginTop()) {
                top2 = iDisplayer.getHeight() - baseDanmaku.paintHeight;
            }
            BaseDanmaku baseDanmaku4 = null;
            if (zIsShown) {
                i3 = 0;
            } else {
                this.mCancelFixingFlag = false;
                RetainerConsumer retainerConsumer = this.mConsumer;
                retainerConsumer.topPos = top2;
                retainerConsumer.disp = iDisplayer;
                retainerConsumer.drawItem = baseDanmaku;
                this.mVisibleDanmakus.forEachSync(retainerConsumer);
                RetainerState retainerStateResult = this.mConsumer.result();
                float f2 = this.mConsumer.topPos;
                if (retainerStateResult != null) {
                    int i4 = retainerStateResult.lines;
                    BaseDanmaku baseDanmaku5 = retainerStateResult.firstItem;
                    BaseDanmaku baseDanmaku6 = retainerStateResult.removeItem;
                    boolean z6 = retainerStateResult.shown;
                    i2 = i4;
                    z3 = retainerStateResult.willHit;
                    baseDanmaku2 = baseDanmaku5;
                    baseDanmaku3 = baseDanmaku6;
                    z2 = z6;
                } else {
                    z2 = zIsShown;
                    z3 = z5;
                    baseDanmaku2 = null;
                    baseDanmaku3 = null;
                    i2 = 0;
                }
                boolean zIsOutVerticalEdge = isOutVerticalEdge(false, baseDanmaku, iDisplayer, f2, baseDanmaku2, null);
                if (zIsOutVerticalEdge) {
                    top2 = iDisplayer.getHeight() - baseDanmaku.paintHeight;
                    z4 = zIsOutVerticalEdge;
                    z5 = true;
                } else {
                    boolean z7 = f2 >= ((float) iDisplayer.getAllMarginTop()) ? false : z3;
                    if (baseDanmaku3 != null) {
                        z4 = zIsOutVerticalEdge;
                        z5 = z7;
                        zIsShown = z2;
                        baseDanmaku4 = baseDanmaku3;
                        i3 = i2 - 1;
                        top2 = f2;
                    } else {
                        z4 = zIsOutVerticalEdge;
                        z5 = z7;
                        top2 = f2;
                        i3 = i2;
                    }
                }
                zIsShown = z2;
                baseDanmaku4 = baseDanmaku3;
            }
            if (verifier == null || !verifier.skipLayout(baseDanmaku, top2, i3, z5)) {
                if (z4) {
                    clear();
                }
                baseDanmaku.layout(iDisplayer, baseDanmaku.getLeft(), top2);
                if (zIsShown) {
                    return;
                }
                this.mVisibleDanmakus.removeItem(baseDanmaku4);
                this.mVisibleDanmakus.addItem(baseDanmaku);
            }
        }

        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.FTDanmakusRetainer, net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer
        public boolean isOutVerticalEdge(boolean z2, BaseDanmaku baseDanmaku, IDisplayer iDisplayer, float f2, BaseDanmaku baseDanmaku2, BaseDanmaku baseDanmaku3) {
            if (f2 >= iDisplayer.getAllMarginTop()) {
                return (baseDanmaku2 == null || baseDanmaku2.getBottom() == ((float) iDisplayer.getHeight())) ? false : true;
            }
            return true;
        }
    }

    public static class AlignTopRetainer implements IDanmakusRetainer {
        protected boolean mCancelFixingFlag;
        protected RetainerConsumer mConsumer;
        protected Danmakus mVisibleDanmakus;

        public class RetainerConsumer extends IDanmakus.Consumer<BaseDanmaku, RetainerState> {
            public IDisplayer disp;
            int lines = 0;
            public BaseDanmaku insertItem = null;
            public BaseDanmaku firstItem = null;
            public BaseDanmaku lastItem = null;
            public BaseDanmaku minRightRow = null;
            public BaseDanmaku drawItem = null;
            boolean overwriteInsert = false;
            boolean shown = false;
            boolean willHit = false;

            public RetainerConsumer() {
            }

            @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
            public void before() {
                this.lines = 0;
                this.minRightRow = null;
                this.lastItem = null;
                this.firstItem = null;
                this.insertItem = null;
                this.willHit = false;
                this.shown = false;
                this.overwriteInsert = false;
            }

            @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
            public int accept(BaseDanmaku baseDanmaku) {
                if (AlignTopRetainer.this.mCancelFixingFlag) {
                    return 1;
                }
                this.lines++;
                BaseDanmaku baseDanmaku2 = this.drawItem;
                if (baseDanmaku == baseDanmaku2) {
                    this.insertItem = baseDanmaku;
                    this.lastItem = null;
                    this.shown = true;
                    this.willHit = false;
                    return 1;
                }
                if (this.firstItem == null) {
                    this.firstItem = baseDanmaku;
                }
                if (baseDanmaku2.paintHeight + baseDanmaku.getTop() > this.disp.getHeight()) {
                    this.overwriteInsert = true;
                    return 1;
                }
                BaseDanmaku baseDanmaku3 = this.minRightRow;
                if (baseDanmaku3 == null || baseDanmaku3.getRight() >= baseDanmaku.getRight()) {
                    this.minRightRow = baseDanmaku;
                }
                IDisplayer iDisplayer = this.disp;
                BaseDanmaku baseDanmaku4 = this.drawItem;
                boolean zWillHitInDuration = DanmakuUtils.willHitInDuration(iDisplayer, baseDanmaku, baseDanmaku4, baseDanmaku4.getDuration(), this.drawItem.getTimer().currMillisecond);
                this.willHit = zWillHitInDuration;
                if (zWillHitInDuration) {
                    this.lastItem = baseDanmaku;
                    return 0;
                }
                this.insertItem = baseDanmaku;
                return 1;
            }

            @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
            public RetainerState result() {
                RetainerState retainerState = new RetainerState();
                retainerState.lines = this.lines;
                retainerState.firstItem = this.firstItem;
                retainerState.insertItem = this.insertItem;
                retainerState.lastItem = this.lastItem;
                retainerState.minRightRow = this.minRightRow;
                retainerState.overwriteInsert = this.overwriteInsert;
                retainerState.shown = this.shown;
                retainerState.willHit = this.willHit;
                return retainerState;
            }
        }

        private AlignTopRetainer() {
            this.mVisibleDanmakus = new Danmakus(1);
            this.mCancelFixingFlag = false;
            this.mConsumer = new RetainerConsumer();
        }

        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void clear() {
            this.mCancelFixingFlag = true;
            this.mVisibleDanmakus.clear();
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x00af  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00bc  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00bf  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00c8  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00d8  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00e0  */
        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void fix(net.polyv.danmaku.danmaku.model.BaseDanmaku r20, net.polyv.danmaku.danmaku.model.IDisplayer r21, net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.Verifier r22) {
            /*
                Method dump skipped, instructions count: 267
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer.fix(net.polyv.danmaku.danmaku.model.BaseDanmaku, net.polyv.danmaku.danmaku.model.IDisplayer, net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer$Verifier):void");
        }

        public boolean isOutVerticalEdge(boolean z2, BaseDanmaku baseDanmaku, IDisplayer iDisplayer, float f2, BaseDanmaku baseDanmaku2, BaseDanmaku baseDanmaku3) {
            if (f2 >= iDisplayer.getAllMarginTop()) {
                return (baseDanmaku2 != null && baseDanmaku2.getTop() > 0.0f) || f2 + baseDanmaku.paintHeight > ((float) iDisplayer.getHeight());
            }
            return true;
        }
    }

    public static class FTDanmakusRetainer extends AlignTopRetainer {
        private FTDanmakusRetainer() {
            super();
        }

        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer
        public boolean isOutVerticalEdge(boolean z2, BaseDanmaku baseDanmaku, IDisplayer iDisplayer, float f2, BaseDanmaku baseDanmaku2, BaseDanmaku baseDanmaku3) {
            return f2 + baseDanmaku.paintHeight > ((float) iDisplayer.getHeight());
        }
    }

    public interface IDanmakusRetainer {
        void clear();

        void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier);
    }

    public static class RetainerState {
        public BaseDanmaku firstItem;
        public BaseDanmaku insertItem;
        public BaseDanmaku lastItem;
        public int lines;
        public BaseDanmaku minRightRow;
        public boolean overwriteInsert;
        public BaseDanmaku removeItem;
        public boolean shown;
        public boolean willHit;

        private RetainerState() {
            this.lines = 0;
            this.insertItem = null;
            this.firstItem = null;
            this.lastItem = null;
            this.minRightRow = null;
            this.removeItem = null;
            this.overwriteInsert = false;
            this.shown = false;
            this.willHit = false;
        }
    }

    public interface Verifier {
        boolean skipLayout(BaseDanmaku baseDanmaku, float f2, int i2, boolean z2);
    }

    public DanmakusRetainer(boolean z2) {
        alignBottom(z2);
    }

    public void alignBottom(boolean z2) {
        this.rldrInstance = z2 ? new AlignBottomRetainer() : new AlignTopRetainer();
        this.lrdrInstance = z2 ? new AlignBottomRetainer() : new AlignTopRetainer();
        if (this.ftdrInstance == null) {
            this.ftdrInstance = new FTDanmakusRetainer();
        }
        if (this.fbdrInstance == null) {
            this.fbdrInstance = new AlignBottomRetainer();
        }
    }

    public void clear() {
        IDanmakusRetainer iDanmakusRetainer = this.rldrInstance;
        if (iDanmakusRetainer != null) {
            iDanmakusRetainer.clear();
        }
        IDanmakusRetainer iDanmakusRetainer2 = this.lrdrInstance;
        if (iDanmakusRetainer2 != null) {
            iDanmakusRetainer2.clear();
        }
        IDanmakusRetainer iDanmakusRetainer3 = this.ftdrInstance;
        if (iDanmakusRetainer3 != null) {
            iDanmakusRetainer3.clear();
        }
        IDanmakusRetainer iDanmakusRetainer4 = this.fbdrInstance;
        if (iDanmakusRetainer4 != null) {
            iDanmakusRetainer4.clear();
        }
    }

    public void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier) {
        int type = baseDanmaku.getType();
        if (type == 1) {
            this.rldrInstance.fix(baseDanmaku, iDisplayer, verifier);
            return;
        }
        if (type == 4) {
            this.fbdrInstance.fix(baseDanmaku, iDisplayer, verifier);
            return;
        }
        if (type == 5) {
            this.ftdrInstance.fix(baseDanmaku, iDisplayer, verifier);
        } else if (type == 6) {
            this.lrdrInstance.fix(baseDanmaku, iDisplayer, verifier);
        } else {
            if (type != 7) {
                return;
            }
            baseDanmaku.layout(iDisplayer, 0.0f, 0.0f);
        }
    }

    public void release() {
        clear();
    }
}
