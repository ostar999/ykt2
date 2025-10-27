package com.aliyun.player.alivcplayerexpand.view.sectionlist;

import androidx.annotation.LayoutRes;

/* loaded from: classes2.dex */
public final class SectionParameters {

    @LayoutRes
    public final Integer emptyResourceId;
    public final boolean emptyViewWillBeProvided;

    @LayoutRes
    public final Integer failedResourceId;
    public final boolean failedViewWillBeProvided;

    @LayoutRes
    public final Integer footerResourceId;
    public final boolean footerViewWillBeProvided;

    @LayoutRes
    public final Integer headerResourceId;
    public final boolean headerViewWillBeProvided;

    @LayoutRes
    public final Integer itemResourceId;
    public final boolean itemViewWillBeProvided;

    @LayoutRes
    public final Integer loadingResourceId;
    public final boolean loadingViewWillBeProvided;

    public static class Builder {

        @LayoutRes
        private Integer emptyResourceId;
        private boolean emptyViewWillBeProvided;

        @LayoutRes
        private Integer failedResourceId;
        private boolean failedViewWillBeProvided;

        @LayoutRes
        private Integer footerResourceId;
        private boolean footerViewWillBeProvided;

        @LayoutRes
        private Integer headerResourceId;
        private boolean headerViewWillBeProvided;

        @LayoutRes
        private Integer itemResourceId;
        private boolean itemViewWillBeProvided;

        @LayoutRes
        private Integer loadingResourceId;
        private boolean loadingViewWillBeProvided;

        public SectionParameters build() {
            return new SectionParameters(this);
        }

        public Builder emptyResourceId(@LayoutRes int i2) {
            this.emptyResourceId = Integer.valueOf(i2);
            return this;
        }

        public Builder emptyViewWillBeProvided() {
            this.emptyViewWillBeProvided = true;
            return this;
        }

        public Builder failedResourceId(@LayoutRes int i2) {
            this.failedResourceId = Integer.valueOf(i2);
            return this;
        }

        public Builder failedViewWillBeProvided() {
            this.failedViewWillBeProvided = true;
            return this;
        }

        public Builder footerResourceId(@LayoutRes int i2) {
            this.footerResourceId = Integer.valueOf(i2);
            return this;
        }

        public Builder footerViewWillBeProvided() {
            this.footerViewWillBeProvided = true;
            return this;
        }

        public Builder headerResourceId(@LayoutRes int i2) {
            this.headerResourceId = Integer.valueOf(i2);
            return this;
        }

        public Builder headerViewWillBeProvided() {
            this.headerViewWillBeProvided = true;
            return this;
        }

        public Builder itemResourceId(@LayoutRes int i2) {
            this.itemResourceId = Integer.valueOf(i2);
            return this;
        }

        public Builder itemViewWillBeProvided() {
            this.itemViewWillBeProvided = true;
            return this;
        }

        public Builder loadingResourceId(@LayoutRes int i2) {
            this.loadingResourceId = Integer.valueOf(i2);
            return this;
        }

        public Builder loadingViewWillBeProvided() {
            this.loadingViewWillBeProvided = true;
            return this;
        }

        private Builder() {
        }

        @Deprecated
        public Builder(@LayoutRes int i2) {
            this.itemResourceId = Integer.valueOf(i2);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private SectionParameters(Builder builder) {
        Integer num = builder.itemResourceId;
        this.itemResourceId = num;
        Integer num2 = builder.headerResourceId;
        this.headerResourceId = num2;
        Integer num3 = builder.footerResourceId;
        this.footerResourceId = num3;
        Integer num4 = builder.loadingResourceId;
        this.loadingResourceId = num4;
        Integer num5 = builder.failedResourceId;
        this.failedResourceId = num5;
        Integer num6 = builder.emptyResourceId;
        this.emptyResourceId = num6;
        boolean z2 = builder.itemViewWillBeProvided;
        this.itemViewWillBeProvided = z2;
        boolean z3 = builder.headerViewWillBeProvided;
        this.headerViewWillBeProvided = z3;
        boolean z4 = builder.footerViewWillBeProvided;
        this.footerViewWillBeProvided = z4;
        boolean z5 = builder.loadingViewWillBeProvided;
        this.loadingViewWillBeProvided = z5;
        boolean z6 = builder.failedViewWillBeProvided;
        this.failedViewWillBeProvided = z6;
        boolean z7 = builder.emptyViewWillBeProvided;
        this.emptyViewWillBeProvided = z7;
        if (num != null && z2) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided cannot both be set");
        }
        if (num == null && !z2) {
            throw new IllegalArgumentException("Either itemResourceId or itemViewWillBeProvided must be set");
        }
        if (num2 != null && z3) {
            throw new IllegalArgumentException("headerResourceId and headerViewWillBeProvided cannot both be set");
        }
        if (num3 != null && z4) {
            throw new IllegalArgumentException("footerResourceId and footerViewWillBeProvided cannot both be set");
        }
        if (num4 != null && z5) {
            throw new IllegalArgumentException("loadingResourceId and loadingViewWillBeProvided cannot both be set");
        }
        if (num5 != null && z6) {
            throw new IllegalArgumentException("failedResourceId and failedViewWillBeProvided cannot both be set");
        }
        if (num6 != null && z7) {
            throw new IllegalArgumentException("emptyResourceId and emptyViewWillBeProvided cannot both be set");
        }
    }
}
