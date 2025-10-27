package kotlin.reflect.jvm.internal.impl.incremental.components;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class Position implements Serializable {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final Position NO_POSITION = new Position(-1, -1);
    private final int column;
    private final int line;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Position getNO_POSITION() {
            return Position.NO_POSITION;
        }
    }

    public Position(int i2, int i3) {
        this.line = i2;
        this.column = i3;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position position = (Position) obj;
        return this.line == position.line && this.column == position.column;
    }

    public int hashCode() {
        return (this.line * 31) + this.column;
    }

    @NotNull
    public String toString() {
        return "Position(line=" + this.line + ", column=" + this.column + ')';
    }
}
