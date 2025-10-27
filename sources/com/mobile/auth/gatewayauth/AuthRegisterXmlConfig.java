package com.mobile.auth.gatewayauth;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.LayoutRes;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.ui.AbstractPnsViewDelegate;

@AuthNumber
/* loaded from: classes4.dex */
public class AuthRegisterXmlConfig implements Parcelable {
    public static final Parcelable.Creator<AuthRegisterXmlConfig> CREATOR = new Parcelable.Creator<AuthRegisterXmlConfig>() { // from class: com.mobile.auth.gatewayauth.AuthRegisterXmlConfig.1
        public AuthRegisterXmlConfig a(Parcel parcel) {
            try {
                return new AuthRegisterXmlConfig(parcel);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public AuthRegisterXmlConfig[] a(int i2) {
            try {
                return new AuthRegisterXmlConfig[i2];
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        @Override // android.os.Parcelable.Creator
        public /* synthetic */ AuthRegisterXmlConfig createFromParcel(Parcel parcel) {
            try {
                return a(parcel);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        @Override // android.os.Parcelable.Creator
        public /* synthetic */ AuthRegisterXmlConfig[] newArray(int i2) {
            try {
                return a(i2);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
    };

    @LayoutRes
    private int layoutResId;
    private AbstractPnsViewDelegate viewDelegate;

    @AuthNumber
    public static class Builder {

        @LayoutRes
        private int layoutResId;
        private AbstractPnsViewDelegate viewDelegate;

        public static /* synthetic */ int access$000(Builder builder) {
            try {
                return builder.layoutResId;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return -1;
            }
        }

        public static /* synthetic */ AbstractPnsViewDelegate access$100(Builder builder) {
            try {
                return builder.viewDelegate;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public AuthRegisterXmlConfig build() {
            try {
                return new AuthRegisterXmlConfig(this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public Builder setLayout(@LayoutRes int i2, AbstractPnsViewDelegate abstractPnsViewDelegate) {
            try {
                this.layoutResId = i2;
                this.viewDelegate = abstractPnsViewDelegate;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
    }

    public AuthRegisterXmlConfig(Parcel parcel) {
        this.layoutResId = parcel.readInt();
    }

    private AuthRegisterXmlConfig(Builder builder) {
        this.layoutResId = Builder.access$000(builder);
        this.viewDelegate = Builder.access$100(builder);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getLayoutResId() {
        try {
            return this.layoutResId;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public AbstractPnsViewDelegate getViewDelegate() {
        try {
            return this.viewDelegate;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        try {
            parcel.writeInt(this.layoutResId);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
