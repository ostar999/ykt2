package cn.webdemo.com.supporfragment.anim;

import android.os.Parcel;
import android.os.Parcelable;
import cn.webdemo.com.supporfragment.R;

/* loaded from: classes.dex */
public class DefaultVerticalAnimator extends FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<DefaultVerticalAnimator> CREATOR = new Parcelable.Creator<DefaultVerticalAnimator>() { // from class: cn.webdemo.com.supporfragment.anim.DefaultVerticalAnimator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultVerticalAnimator createFromParcel(Parcel parcel) {
            return new DefaultVerticalAnimator(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultVerticalAnimator[] newArray(int i2) {
            return new DefaultVerticalAnimator[i2];
        }
    };

    public DefaultVerticalAnimator() {
        this.enter = R.anim.v_fragment_enter;
        this.exit = R.anim.v_fragment_exit;
        this.popEnter = R.anim.v_fragment_pop_enter;
        this.popExit = R.anim.v_fragment_pop_exit;
    }

    @Override // cn.webdemo.com.supporfragment.anim.FragmentAnimator, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // cn.webdemo.com.supporfragment.anim.FragmentAnimator, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
    }

    public DefaultVerticalAnimator(Parcel parcel) {
        super(parcel);
    }
}
