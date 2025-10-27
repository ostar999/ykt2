package cn.webdemo.com.supporfragment.anim;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.AnimRes;

/* loaded from: classes.dex */
public class FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<FragmentAnimator> CREATOR = new Parcelable.Creator<FragmentAnimator>() { // from class: cn.webdemo.com.supporfragment.anim.FragmentAnimator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentAnimator createFromParcel(Parcel parcel) {
            return new FragmentAnimator(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentAnimator[] newArray(int i2) {
            return new FragmentAnimator[i2];
        }
    };

    @AnimRes
    protected int enter;

    @AnimRes
    protected int exit;

    @AnimRes
    protected int popEnter;

    @AnimRes
    protected int popExit;

    public FragmentAnimator() {
    }

    public FragmentAnimator copy() {
        return new FragmentAnimator(getEnter(), getExit(), getPopEnter(), getPopExit());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getEnter() {
        return this.enter;
    }

    public int getExit() {
        return this.exit;
    }

    public int getPopEnter() {
        return this.popEnter;
    }

    public int getPopExit() {
        return this.popExit;
    }

    public FragmentAnimator setEnter(int i2) {
        this.enter = i2;
        return this;
    }

    public FragmentAnimator setExit(int i2) {
        this.exit = i2;
        return this;
    }

    public FragmentAnimator setPopEnter(int i2) {
        this.popEnter = i2;
        return this;
    }

    public FragmentAnimator setPopExit(int i2) {
        this.popExit = i2;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.enter);
        parcel.writeInt(this.exit);
        parcel.writeInt(this.popEnter);
        parcel.writeInt(this.popExit);
    }

    public FragmentAnimator(int i2, int i3) {
        this.enter = i2;
        this.exit = i3;
    }

    public FragmentAnimator(int i2, int i3, int i4, int i5) {
        this.enter = i2;
        this.exit = i3;
        this.popEnter = i4;
        this.popExit = i5;
    }

    public FragmentAnimator(Parcel parcel) {
        this.enter = parcel.readInt();
        this.exit = parcel.readInt();
        this.popEnter = parcel.readInt();
        this.popExit = parcel.readInt();
    }
}
