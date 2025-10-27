package com.ykb.ebook.common_interface;

import com.ykb.ebook.R;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Lcom/ykb/ebook/common_interface/AnimAction;", "", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface AnimAction {
    public static final int ANIM_DEFAULT = -1;
    public static final int ANIM_EMPTY = 0;
    public static final int ANIM_TOAST = 16973828;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0006¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/common_interface/AnimAction$Companion;", "", "()V", "ANIM_BOTTOM", "", "getANIM_BOTTOM", "()I", "ANIM_DEFAULT", "ANIM_EMPTY", "ANIM_IOS", "getANIM_IOS", "ANIM_LEFT", "getANIM_LEFT", "ANIM_RIGHT", "getANIM_RIGHT", "ANIM_SCALE", "getANIM_SCALE", "ANIM_TOAST", "ANIM_TOP", "getANIM_TOP", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public static final int ANIM_DEFAULT = -1;
        public static final int ANIM_EMPTY = 0;
        public static final int ANIM_TOAST = 16973828;
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final int ANIM_SCALE = R.style.ScaleAnimStyle;
        private static final int ANIM_IOS = R.style.IOSAnimStyle;
        private static final int ANIM_TOP = R.style.TopAnimStyle;
        private static final int ANIM_BOTTOM = R.style.BottomAnimStyle;
        private static final int ANIM_LEFT = R.style.LeftAnimStyle;
        private static final int ANIM_RIGHT = R.style.RightAnimStyle;

        private Companion() {
        }

        public final int getANIM_BOTTOM() {
            return ANIM_BOTTOM;
        }

        public final int getANIM_IOS() {
            return ANIM_IOS;
        }

        public final int getANIM_LEFT() {
            return ANIM_LEFT;
        }

        public final int getANIM_RIGHT() {
            return ANIM_RIGHT;
        }

        public final int getANIM_SCALE() {
            return ANIM_SCALE;
        }

        public final int getANIM_TOP() {
            return ANIM_TOP;
        }
    }
}
