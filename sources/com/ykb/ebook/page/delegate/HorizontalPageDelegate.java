package com.ykb.ebook.page.delegate;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorder;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorderFactory;
import com.ykb.ebook.weight.ReadView;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000eH\u0016J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000eH\u0016J\b\u0010\u001b\u001a\u00020\u0012H\u0016J\u0010\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lcom/ykb/ebook/page/delegate/HorizontalPageDelegate;", "Lcom/ykb/ebook/page/delegate/PageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "curRecorder", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "getCurRecorder", "()Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "nextRecorder", "getNextRecorder", "prevRecorder", "getPrevRecorder", "slopSquare", "", "getSlopSquare", "()I", "abortAnim", "", "nextPageByAnim", "animationSpeed", "onDestroy", "onScroll", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "onTouch", "prevPageByAnim", "setBitmap", "setDirection", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lcom/ykb/ebook/page/PageDirection;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHorizontalPageDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HorizontalPageDelegate.kt\ncom/ykb/ebook/page/delegate/HorizontalPageDelegate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,163:1\n1#2:164\n*E\n"})
/* loaded from: classes7.dex */
public abstract class HorizontalPageDelegate extends PageDelegate {

    @NotNull
    private final CanvasRecorder curRecorder;

    @NotNull
    private final CanvasRecorder nextRecorder;

    @NotNull
    private final CanvasRecorder prevRecorder;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.PREV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PageDirection.NEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HorizontalPageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
        CanvasRecorderFactory canvasRecorderFactory = CanvasRecorderFactory.INSTANCE;
        this.curRecorder = CanvasRecorderFactory.create$default(canvasRecorderFactory, false, 1, null);
        this.prevRecorder = CanvasRecorderFactory.create$default(canvasRecorderFactory, false, 1, null);
        this.nextRecorder = CanvasRecorderFactory.create$default(canvasRecorderFactory, false, 1, null);
    }

    private final int getSlopSquare() {
        return getReadView().getPageSlopSquare2();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void onScroll(MotionEvent event) {
        List<Chapter> chapterList;
        List<Chapter> chapterList2;
        boolean z2 = false;
        boolean z3 = (event.getAction() & 255) == 6;
        int actionIndex = z3 ? event.getActionIndex() : -1;
        int pointerCount = event.getPointerCount();
        float x2 = 0.0f;
        float y2 = 0.0f;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (actionIndex != i2) {
                x2 += event.getX(i2);
                y2 += event.getY(i2);
            }
        }
        if (z3) {
            pointerCount--;
        }
        float f2 = pointerCount;
        float f3 = x2 / f2;
        float f4 = y2 / f2;
        if (!getIsMoved()) {
            int startX = (int) (f3 - getStartX());
            int startY = (int) (f4 - getStartY());
            setMoved((startX * startX) + (startY * startY) > getSlopSquare());
            if (getIsMoved()) {
                if (x2 - getStartX() <= 0.0f) {
                    ReadBook readBook = ReadBook.INSTANCE;
                    BookInfo book = readBook.getBook();
                    if (readBook.getDurChapterIndex() == ((book == null || (chapterList2 = book.getChapterList()) == null) ? 0 : chapterList2.size()) - 1) {
                        BookInfo book2 = readBook.getBook();
                        Chapter chapter = null;
                        if (book2 != null && (chapterList = book2.getChapterList()) != null) {
                            Iterator<T> it = chapterList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Object next = it.next();
                                if (((Chapter) next).getIndex() == ReadBook.INSTANCE.getDurChapterIndex()) {
                                    chapter = next;
                                    break;
                                }
                            }
                            chapter = chapter;
                        }
                        if (chapter != null && ReadConfig.INSTANCE.getLastPage() && !chapter.isPay()) {
                            return;
                        }
                    }
                    if (!hasNext() && ReadConfig.INSTANCE.getLastPage()) {
                        setNoNext(true);
                        return;
                    }
                    setDirection(PageDirection.NEXT);
                } else {
                    if (!hasPrev() && ReadConfig.INSTANCE.getFirstPage()) {
                        setNoNext(true);
                        return;
                    }
                    setDirection(PageDirection.PREV);
                }
                getReadView().setStartPoint(event.getX(), event.getY(), false);
            }
        }
        if (getIsMoved()) {
            if (getMDirection() != PageDirection.NEXT ? x2 < getLastX() : x2 > getLastX()) {
                z2 = true;
            }
            setCancel(z2);
            setRunning(true);
            ReadView.setTouchPoint$default(getReadView(), x2, y2, false, 4, null);
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void abortAnim() throws SecurityException, NumberFormatException {
        setStarted(false);
        setMoved(false);
        setRunning(false);
        if (getScroller().isFinished()) {
            getReadView().setAbortAnim(false);
            return;
        }
        getReadView().setAbortAnim(true);
        getScroller().abortAnimation();
        if (getIsCancel()) {
            return;
        }
        getReadView().fillPage(getMDirection());
        getReadView().invalidate();
    }

    @NotNull
    public final CanvasRecorder getCurRecorder() {
        return this.curRecorder;
    }

    @NotNull
    public final CanvasRecorder getNextRecorder() {
        return this.nextRecorder;
    }

    @NotNull
    public final CanvasRecorder getPrevRecorder() {
        return this.prevRecorder;
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void nextPageByAnim(int animationSpeed) throws SecurityException, NumberFormatException {
        abortAnim();
        if (hasNext() || !ReadConfig.INSTANCE.getLastPage()) {
            setDirection(PageDirection.NEXT);
            getReadView().setStartPoint(getViewWidth() * 0.9f, getStartY() > ((float) (getViewHeight() / 2)) ? getViewHeight() * 0.9f : 1.0f, false);
            onAnimStart(animationSpeed);
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onDestroy() {
        super.onDestroy();
        this.prevRecorder.recycle();
        this.curRecorder.recycle();
        this.nextRecorder.recycle();
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onTouch(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int action = event.getAction();
        if (action == 0) {
            abortAnim();
            return;
        }
        if (action != 1) {
            if (action == 2) {
                onScroll(event);
                return;
            } else if (action != 3) {
                return;
            }
        }
        onAnimStart(getReadView().getDefaultAnimationSpeed());
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void prevPageByAnim(int animationSpeed) throws SecurityException, NumberFormatException {
        abortAnim();
        if (hasPrev() || !ReadConfig.INSTANCE.getFirstPage()) {
            setDirection(PageDirection.PREV);
            getReadView().setStartPoint(0.0f, getViewHeight(), false);
            onAnimStart(animationSpeed);
        }
    }

    public void setBitmap() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()];
        if (i2 == 1) {
            ViewExtensionsKt.screenshot(getPrevPage(), this.prevRecorder);
            ViewExtensionsKt.screenshot(getCurPage(), this.curRecorder);
        } else {
            if (i2 != 2) {
                return;
            }
            ViewExtensionsKt.screenshot(getNextPage(), this.nextRecorder);
            ViewExtensionsKt.screenshot(getCurPage(), this.curRecorder);
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void setDirection(@NotNull PageDirection direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        super.setDirection(direction);
        setBitmap();
    }
}
