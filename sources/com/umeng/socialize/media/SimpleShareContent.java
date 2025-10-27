package com.umeng.socialize.media;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.b.a.a;
import com.umeng.socialize.interfaces.CompressListener;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.DefaultClass;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import com.yikaobang.yixue.R2;
import java.io.File;

/* loaded from: classes6.dex */
public class SimpleShareContent {

    /* renamed from: a, reason: collision with root package name */
    private UMImage f23687a;

    /* renamed from: b, reason: collision with root package name */
    private UMImage[] f23688b;

    /* renamed from: c, reason: collision with root package name */
    private String f23689c;

    /* renamed from: d, reason: collision with root package name */
    private UMVideo f23690d;

    /* renamed from: e, reason: collision with root package name */
    private UMEmoji f23691e;

    /* renamed from: f, reason: collision with root package name */
    private UMusic f23692f;

    /* renamed from: g, reason: collision with root package name */
    private UMMin f23693g;

    /* renamed from: h, reason: collision with root package name */
    private UMQQMini f23694h;

    /* renamed from: i, reason: collision with root package name */
    private UMWeb f23695i;

    /* renamed from: j, reason: collision with root package name */
    private File f23696j;

    /* renamed from: k, reason: collision with root package name */
    private BaseMediaObject f23697k;

    /* renamed from: l, reason: collision with root package name */
    private int f23698l;

    /* renamed from: m, reason: collision with root package name */
    private String f23699m;

    /* renamed from: n, reason: collision with root package name */
    private String f23700n;

    /* renamed from: o, reason: collision with root package name */
    private CompressListener f23701o;
    public int THUMB_LIMIT = 24576;
    public int WX_THUMB_LIMIT = R2.id.share_num;
    public int WX_MIN_LIMIT = 131072;
    public final int IMAGE_LIMIT = 491520;
    public final String DEFAULT_TITLE = "这里是标题";
    public final String DEFAULT_DESCRIPTION = "这里是描述";

    public SimpleShareContent(ShareContent shareContent) {
        this.f23689c = shareContent.mText;
        UMediaObject uMediaObject = shareContent.mMedia;
        if (uMediaObject != null && (uMediaObject instanceof UMImage)) {
            UMImage uMImage = (UMImage) uMediaObject;
            this.f23687a = uMImage;
            this.f23697k = uMImage;
            UMImage[] uMImageArr = shareContent.mMedias;
            if (uMImageArr != null && uMImageArr.length > 0) {
                this.f23688b = uMImageArr;
            }
        }
        if (uMediaObject != null && (uMediaObject instanceof UMusic)) {
            UMusic uMusic = (UMusic) uMediaObject;
            this.f23692f = uMusic;
            this.f23697k = uMusic;
        }
        if (uMediaObject != null && (uMediaObject instanceof UMVideo)) {
            UMVideo uMVideo = (UMVideo) uMediaObject;
            this.f23690d = uMVideo;
            this.f23697k = uMVideo;
        }
        if (uMediaObject != null && (uMediaObject instanceof UMEmoji)) {
            UMEmoji uMEmoji = (UMEmoji) uMediaObject;
            this.f23691e = uMEmoji;
            this.f23697k = uMEmoji;
        }
        if (uMediaObject != null && (uMediaObject instanceof UMWeb)) {
            UMWeb uMWeb = (UMWeb) uMediaObject;
            this.f23695i = uMWeb;
            this.f23697k = uMWeb;
        }
        if (uMediaObject != null && (uMediaObject instanceof UMMin)) {
            this.f23693g = (UMMin) uMediaObject;
            this.f23697k = this.f23695i;
        }
        if (uMediaObject != null && (uMediaObject instanceof UMQQMini)) {
            this.f23694h = (UMQQMini) uMediaObject;
            this.f23697k = this.f23695i;
        }
        File file = shareContent.file;
        if (file != null) {
            this.f23696j = file;
        }
        this.f23700n = shareContent.subject;
        this.f23698l = shareContent.getShareType();
        this.f23699m = a();
    }

    private String a() {
        int i2 = this.f23698l;
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 8 ? i2 != 16 ? i2 != 32 ? i2 != 64 ? i2 != 128 ? i2 != 256 ? "error" : "qqMini" : "minapp" : "emoji" : "file" : "web" : "video" : "music" : "textandimage" : "image" : "text";
    }

    private byte[] b() {
        byte[] bytes = DefaultClass.getBytes();
        if (ContextUtil.getIcon() != 0 && ((bytes = a.a(new UMImage(ContextUtil.getContext(), ContextUtil.getIcon()), this.WX_THUMB_LIMIT)) == null || bytes.length <= 0)) {
            SLog.E(UmengText.IMAGE.SHARECONTENT_THUMB_ERROR);
        }
        return bytes;
    }

    public boolean canFileValid(UMImage uMImage) {
        return uMImage.asFileImage() != null;
    }

    public String getAssertSubject() {
        return TextUtils.isEmpty(this.f23700n) ? "umengshare" : this.f23700n;
    }

    public BaseMediaObject getBaseMediaObject() {
        return this.f23697k;
    }

    public File getFile() {
        return this.f23696j;
    }

    public UMImage getImage() {
        return this.f23687a;
    }

    public byte[] getImageData(UMImage uMImage) {
        return uMImage.asBinImage();
    }

    public byte[] getImageThumb(UMImage uMImage) {
        if (uMImage.getThumbImage() == null) {
            return b();
        }
        byte[] bArrA = a.a(uMImage.getThumbImage(), this.WX_THUMB_LIMIT);
        if (bArrA != null && bArrA.length > 0) {
            return bArrA;
        }
        SLog.E(UmengText.IMAGE.SHARECONTENT_THUMB_ERROR);
        return b();
    }

    public UMusic getMusic() {
        return this.f23692f;
    }

    public String getMusicTargetUrl(UMusic uMusic) {
        return TextUtils.isEmpty(uMusic.getmTargetUrl()) ? uMusic.toUrl() : uMusic.getmTargetUrl();
    }

    public UMQQMini getQQMini() {
        return this.f23694h;
    }

    public String getStrStyle() {
        return this.f23699m;
    }

    public byte[] getStrictImageData(UMImage uMImage) {
        if (getUMImageScale(uMImage) <= 491520) {
            return getImageData(uMImage);
        }
        byte[] bArrA = a.a(getImage(), 491520);
        if (bArrA != null && bArrA.length > 0) {
            return bArrA;
        }
        SLog.E(UmengText.IMAGE.SHARECONTENT_THUMB_ERROR);
        return null;
    }

    public String getSubject() {
        return this.f23700n;
    }

    public String getText() {
        return this.f23689c;
    }

    public int getUMImageScale(UMImage uMImage) {
        return a.a(uMImage);
    }

    public UMEmoji getUmEmoji() {
        return this.f23691e;
    }

    public UMMin getUmMin() {
        return this.f23693g;
    }

    public UMWeb getUmWeb() {
        return this.f23695i;
    }

    public UMVideo getVideo() {
        return this.f23690d;
    }

    public UMImage[] getmImages() {
        return this.f23688b;
    }

    public int getmStyle() {
        return this.f23698l;
    }

    public String objectSetDescription(BaseMediaObject baseMediaObject) {
        if (TextUtils.isEmpty(baseMediaObject.getDescription())) {
            return "这里是描述";
        }
        String description = baseMediaObject.getDescription();
        return description.length() > 1024 ? description.substring(0, 1024) : description;
    }

    public byte[] objectSetMInAppThumb(BaseMediaObject baseMediaObject) {
        if (baseMediaObject.getThumbImage() == null) {
            return DefaultClass.getBytes();
        }
        if (this.f23701o != null) {
            UMImage thumbImage = baseMediaObject.getThumbImage();
            if (thumbImage == null) {
                return DefaultClass.getBytes();
            }
            byte[] bArrAsBinImage = thumbImage.asBinImage();
            return (bArrAsBinImage == null || a.a(thumbImage) > this.WX_MIN_LIMIT) ? this.f23701o.compressThumb(bArrAsBinImage) : bArrAsBinImage;
        }
        byte[] bArrA = a.a(baseMediaObject.getThumbImage().asBinImage(), this.WX_MIN_LIMIT, Bitmap.CompressFormat.JPEG);
        if (bArrA != null && bArrA.length > 0) {
            return bArrA;
        }
        SLog.E(UmengText.IMAGE.SHARECONTENT_THUMB_ERROR);
        return bArrA;
    }

    public String objectSetText(String str, int i2) {
        return TextUtils.isEmpty(str) ? "这里是描述" : str.length() > i2 ? str.substring(0, i2) : str;
    }

    public byte[] objectSetThumb(BaseMediaObject baseMediaObject) {
        if (baseMediaObject.getThumbImage() == null) {
            return b();
        }
        if (this.f23701o != null) {
            UMImage thumbImage = baseMediaObject.getThumbImage();
            if (thumbImage == null) {
                return DefaultClass.getBytes();
            }
            byte[] bArrAsBinImage = thumbImage.asBinImage();
            return (bArrAsBinImage == null || a.a(thumbImage) > this.THUMB_LIMIT) ? this.f23701o.compressThumb(bArrAsBinImage) : bArrAsBinImage;
        }
        byte[] bArrA = a.a(baseMediaObject.getThumbImage(), this.THUMB_LIMIT);
        if (bArrA != null && bArrA.length > 0) {
            return bArrA;
        }
        SLog.E(UmengText.IMAGE.SHARECONTENT_THUMB_ERROR);
        return b();
    }

    public String objectSetTitle(BaseMediaObject baseMediaObject) {
        if (TextUtils.isEmpty(baseMediaObject.getTitle())) {
            return "这里是标题";
        }
        String title = baseMediaObject.getTitle();
        return title.length() > 512 ? title.substring(0, 512) : title;
    }

    public void setCompressListener(CompressListener compressListener) {
        this.f23701o = compressListener;
    }

    public void setImage(UMImage uMImage) {
        this.f23687a = uMImage;
    }

    public void setMusic(UMusic uMusic) {
        this.f23692f = uMusic;
    }

    public void setText(String str) {
        this.f23689c = str;
    }

    public void setVideo(UMVideo uMVideo) {
        this.f23690d = uMVideo;
    }

    public String subString(String str, int i2) {
        return (!TextUtils.isEmpty(str) || str.length() <= i2) ? str : str.substring(0, i2);
    }

    public String objectSetText(String str) {
        return objectSetText(str, 10240);
    }
}
