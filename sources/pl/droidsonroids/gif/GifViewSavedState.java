package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes9.dex */
class GifViewSavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<GifViewSavedState> CREATOR = new Parcelable.Creator<GifViewSavedState>() { // from class: pl.droidsonroids.gif.GifViewSavedState.1
        @Override // android.os.Parcelable.Creator
        public GifViewSavedState createFromParcel(Parcel parcel) {
            return new GifViewSavedState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GifViewSavedState[] newArray(int i2) {
            return new GifViewSavedState[i2];
        }
    };
    final long[][] mStates;

    public void restoreState(Drawable drawable, int i2) {
        if (this.mStates[i2] == null || !(drawable instanceof GifDrawable)) {
            return;
        }
        ((GifDrawable) drawable).startAnimation(r3.mNativeInfoHandle.restoreSavedState(r4, r3.mBuffer));
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeInt(this.mStates.length);
        for (long[] jArr : this.mStates) {
            parcel.writeLongArray(jArr);
        }
    }

    public GifViewSavedState(Parcelable parcelable, Drawable... drawableArr) {
        super(parcelable);
        this.mStates = new long[drawableArr.length][];
        for (int i2 = 0; i2 < drawableArr.length; i2++) {
            Drawable drawable = drawableArr[i2];
            if (drawable instanceof GifDrawable) {
                this.mStates[i2] = ((GifDrawable) drawable).mNativeInfoHandle.getSavedState();
            } else {
                this.mStates[i2] = null;
            }
        }
    }

    private GifViewSavedState(Parcel parcel) {
        super(parcel);
        this.mStates = new long[parcel.readInt()][];
        int i2 = 0;
        while (true) {
            long[][] jArr = this.mStates;
            if (i2 >= jArr.length) {
                return;
            }
            jArr[i2] = parcel.createLongArray();
            i2++;
        }
    }

    public GifViewSavedState(Parcelable parcelable, long[] jArr) {
        super(parcelable);
        this.mStates = new long[][]{jArr};
    }
}
