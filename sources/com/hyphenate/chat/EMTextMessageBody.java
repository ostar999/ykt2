package com.hyphenate.chat;

import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.adapter.message.EMATextMessageBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMTextMessageBody extends EMMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMTextMessageBody> CREATOR = new Parcelable.Creator<EMTextMessageBody>() { // from class: com.hyphenate.chat.EMTextMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMTextMessageBody createFromParcel(Parcel parcel) {
            return new EMTextMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMTextMessageBody[] newArray(int i2) {
            return new EMTextMessageBody[i2];
        }
    };

    public static class EMTranslationInfo {
        public String languageCode;
        public String translationText;

        public EMTranslationInfo(String str, String str2) {
            this.languageCode = str;
            this.translationText = str2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.message.EMATextMessageBody] */
    private EMTextMessageBody(Parcel parcel) {
        this.emaObject = new EMATextMessageBody(parcel.readString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMTextMessageBody(EMATextMessageBody eMATextMessageBody) {
        this.emaObject = eMATextMessageBody;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.message.EMATextMessageBody] */
    public EMTextMessageBody(String str) {
        this.emaObject = new EMATextMessageBody(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getMessage() {
        return ((EMATextMessageBody) this.emaObject).text();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getTargetLanguages() {
        return ((EMATextMessageBody) this.emaObject).getTargetLanguages();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMTranslationInfo> getTranslations() {
        Map<String, String> translations = ((EMATextMessageBody) this.emaObject).getTranslations();
        ArrayList arrayList = new ArrayList();
        for (String str : translations.keySet()) {
            arrayList.add(new EMTranslationInfo(str, translations.get(str)));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setMessage(String str) {
        ((EMATextMessageBody) this.emaObject).setText(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTargetLanguages(List<String> list) {
        ((EMATextMessageBody) this.emaObject).setTargetLanguages(list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "txt:\"" + ((EMATextMessageBody) this.emaObject).text() + "\"";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMATextMessageBody) this.emaObject).text());
    }
}
