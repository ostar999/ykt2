package com.hyphenate.easeui.model.styles;

import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public class EaseMessageListItemStyle {
    private Drawable myBubbleBg;
    private Drawable otherBubbleBg;
    private boolean showAvatar;
    private boolean showUserNick;

    public static final class Builder {
        private Drawable myBubbleBg;
        private Drawable otherBubbleBg;
        private boolean showAvatar;
        private boolean showUserNick;

        public EaseMessageListItemStyle build() {
            return new EaseMessageListItemStyle(this);
        }

        public Builder myBubbleBg(Drawable drawable) {
            this.myBubbleBg = drawable;
            return this;
        }

        public Builder otherBuddleBg(Drawable drawable) {
            this.otherBubbleBg = drawable;
            return this;
        }

        public Builder showAvatar(boolean z2) {
            this.showAvatar = z2;
            return this;
        }

        public Builder showUserNick(boolean z2) {
            this.showUserNick = z2;
            return this;
        }
    }

    public EaseMessageListItemStyle(Builder builder) {
        this.showUserNick = builder.showUserNick;
        this.showAvatar = builder.showAvatar;
        this.myBubbleBg = builder.myBubbleBg;
        this.otherBubbleBg = builder.otherBubbleBg;
    }

    public Drawable getMyBubbleBg() {
        return this.myBubbleBg;
    }

    public Drawable getOtherBubbleBg() {
        return this.otherBubbleBg;
    }

    public boolean isShowAvatar() {
        return this.showAvatar;
    }

    public boolean isShowUserNick() {
        return this.showUserNick;
    }

    public void setMyBubbleBg(Drawable drawable) {
        this.myBubbleBg = drawable;
    }

    public void setOtherBubbleBg(Drawable drawable) {
        this.otherBubbleBg = drawable;
    }

    public void setShowAvatar(boolean z2) {
        this.showAvatar = z2;
    }

    public void setShowUserNick(boolean z2) {
        this.showUserNick = z2;
    }
}
