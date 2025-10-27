package com.ykb.ebook.common_interface;

import com.aliyun.vod.log.struct.AliyunLogKey;
import com.psychiatrygarden.utils.Constants;
import com.ykb.ebook.model.TextPage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/common_interface/LayoutProgressListener;", "", "onLayoutCompleted", "", "onLayoutException", AliyunLogKey.KEY_EVENT, "", "onLayoutPageCompleted", "index", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "Lcom/ykb/ebook/model/TextPage;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface LayoutProgressListener {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void onLayoutCompleted(@NotNull LayoutProgressListener layoutProgressListener) {
        }

        public static void onLayoutException(@NotNull LayoutProgressListener layoutProgressListener, @NotNull Throwable e2) {
            Intrinsics.checkNotNullParameter(e2, "e");
        }

        public static void onLayoutPageCompleted(@NotNull LayoutProgressListener layoutProgressListener, int i2, @NotNull TextPage page) {
            Intrinsics.checkNotNullParameter(page, "page");
        }
    }

    void onLayoutCompleted();

    void onLayoutException(@NotNull Throwable e2);

    void onLayoutPageCompleted(int index, @NotNull TextPage page);
}
