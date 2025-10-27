package com.luck.picture.lib.style;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.AnimRes;
import com.luck.picture.lib.R;

/* loaded from: classes4.dex */
public class PictureWindowAnimationStyle implements Parcelable {
    public static final Parcelable.Creator<PictureWindowAnimationStyle> CREATOR = new Parcelable.Creator<PictureWindowAnimationStyle>() { // from class: com.luck.picture.lib.style.PictureWindowAnimationStyle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureWindowAnimationStyle createFromParcel(Parcel parcel) {
            return new PictureWindowAnimationStyle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureWindowAnimationStyle[] newArray(int i2) {
            return new PictureWindowAnimationStyle[i2];
        }
    };

    @AnimRes
    public int activityEnterAnimation;

    @AnimRes
    public int activityExitAnimation;

    @AnimRes
    public int activityPreviewEnterAnimation;

    @AnimRes
    public int activityPreviewExitAnimation;

    public PictureWindowAnimationStyle() {
    }

    public static PictureWindowAnimationStyle ofDefaultWindowAnimationStyle() {
        return new PictureWindowAnimationStyle(R.anim.ps_anim_enter, R.anim.ps_anim_exit);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getActivityEnterAnimation() {
        return this.activityEnterAnimation;
    }

    public int getActivityExitAnimation() {
        return this.activityExitAnimation;
    }

    public int getActivityPreviewEnterAnimation() {
        return this.activityPreviewEnterAnimation;
    }

    public int getActivityPreviewExitAnimation() {
        return this.activityPreviewExitAnimation;
    }

    public void setActivityEnterAnimation(int i2) {
        this.activityEnterAnimation = i2;
    }

    public void setActivityExitAnimation(int i2) {
        this.activityExitAnimation = i2;
    }

    public void setActivityPreviewEnterAnimation(int i2) {
        this.activityPreviewEnterAnimation = i2;
    }

    public void setActivityPreviewExitAnimation(int i2) {
        this.activityPreviewExitAnimation = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.activityEnterAnimation);
        parcel.writeInt(this.activityExitAnimation);
        parcel.writeInt(this.activityPreviewEnterAnimation);
        parcel.writeInt(this.activityPreviewExitAnimation);
    }

    public PictureWindowAnimationStyle(@AnimRes int i2, @AnimRes int i3) {
        this.activityEnterAnimation = i2;
        this.activityExitAnimation = i3;
        this.activityPreviewEnterAnimation = i2;
        this.activityPreviewExitAnimation = i3;
    }

    public PictureWindowAnimationStyle(Parcel parcel) {
        this.activityEnterAnimation = parcel.readInt();
        this.activityExitAnimation = parcel.readInt();
        this.activityPreviewEnterAnimation = parcel.readInt();
        this.activityPreviewExitAnimation = parcel.readInt();
    }
}
