package com.umeng.socialize;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMQQMini;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoard;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ShareAction {

    /* renamed from: e, reason: collision with root package name */
    private Activity f23567e;

    /* renamed from: l, reason: collision with root package name */
    private ShareBoard f23574l;

    /* renamed from: a, reason: collision with root package name */
    private ShareContent f23563a = new ShareContent();

    /* renamed from: b, reason: collision with root package name */
    private SHARE_MEDIA f23564b = null;

    /* renamed from: c, reason: collision with root package name */
    private UMShareListener f23565c = null;

    /* renamed from: d, reason: collision with root package name */
    private ShareBoardlistener f23566d = null;

    /* renamed from: f, reason: collision with root package name */
    private List<SHARE_MEDIA> f23568f = null;

    /* renamed from: g, reason: collision with root package name */
    private List<SnsPlatform> f23569g = new ArrayList();

    /* renamed from: h, reason: collision with root package name */
    private List<ShareContent> f23570h = new ArrayList();

    /* renamed from: i, reason: collision with root package name */
    private List<UMShareListener> f23571i = new ArrayList();

    /* renamed from: j, reason: collision with root package name */
    private int f23572j = 80;

    /* renamed from: k, reason: collision with root package name */
    private View f23573k = null;

    /* renamed from: m, reason: collision with root package name */
    private ShareBoardlistener f23575m = new ShareBoardlistener() { // from class: com.umeng.socialize.ShareAction.1
        @Override // com.umeng.socialize.utils.ShareBoardlistener
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            ShareAction.this.setPlatform(share_media);
            ShareAction.this.share();
        }
    };

    /* renamed from: n, reason: collision with root package name */
    private ShareBoardlistener f23576n = new ShareBoardlistener() { // from class: com.umeng.socialize.ShareAction.2
        @Override // com.umeng.socialize.utils.ShareBoardlistener
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            int iIndexOf = ShareAction.this.f23568f.indexOf(share_media);
            int size = ShareAction.this.f23570h.size();
            if (size != 0) {
                ShareAction.this.f23563a = iIndexOf < size ? (ShareContent) ShareAction.this.f23570h.get(iIndexOf) : (ShareContent) ShareAction.this.f23570h.get(size - 1);
            }
            int size2 = ShareAction.this.f23571i.size();
            if (size2 != 0) {
                if (iIndexOf < size2) {
                    ShareAction shareAction = ShareAction.this;
                    shareAction.f23565c = (UMShareListener) shareAction.f23571i.get(iIndexOf);
                } else {
                    ShareAction shareAction2 = ShareAction.this;
                    shareAction2.f23565c = (UMShareListener) shareAction2.f23571i.get(size2 - 1);
                }
            }
            ShareAction.this.setPlatform(share_media);
            ShareAction.this.share();
        }
    };

    public ShareAction(Activity activity) {
        if (activity != null) {
            this.f23567e = (Activity) new WeakReference(activity).get();
        }
    }

    public static Rect locateView(View view) {
        int[] iArr = new int[2];
        if (view == null) {
            return null;
        }
        try {
            view.getLocationOnScreen(iArr);
            Rect rect = new Rect();
            int i2 = iArr[0];
            rect.left = i2;
            rect.top = iArr[1];
            rect.right = i2 + view.getWidth();
            rect.bottom = rect.top + view.getHeight();
            return rect;
        } catch (NullPointerException e2) {
            SLog.error(e2);
            return null;
        }
    }

    public ShareAction addButton(String str, String str2, String str3, String str4) {
        this.f23569g.add(SHARE_MEDIA.createSnsPlatform(str, str2, str3, str4, 0));
        return this;
    }

    public void close() {
        ShareBoard shareBoard = this.f23574l;
        if (shareBoard != null) {
            shareBoard.dismiss();
            this.f23574l = null;
        }
    }

    public SHARE_MEDIA getPlatform() {
        return this.f23564b;
    }

    public ShareContent getShareContent() {
        return this.f23563a;
    }

    public boolean getUrlValid() {
        UMediaObject uMediaObject;
        ShareContent shareContent = this.f23563a;
        return shareContent == null || (uMediaObject = shareContent.mMedia) == null || !(uMediaObject instanceof UMWeb) || uMediaObject.toUrl() == null || this.f23563a.mMedia.toUrl().startsWith("http");
    }

    public void open(ShareBoardConfig shareBoardConfig) {
        if (this.f23569g.size() != 0) {
            HashMap map = new HashMap();
            map.put(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.f23565c);
            map.put("content", this.f23563a);
            try {
                ShareBoard shareBoard = new ShareBoard(this.f23567e, this.f23569g, shareBoardConfig);
                this.f23574l = shareBoard;
                ShareBoardlistener shareBoardlistener = this.f23566d;
                if (shareBoardlistener == null) {
                    shareBoard.setShareBoardlistener(this.f23576n);
                } else {
                    shareBoard.setShareBoardlistener(shareBoardlistener);
                }
                this.f23574l.setFocusable(true);
                this.f23574l.setBackgroundDrawable(new BitmapDrawable());
                if (this.f23573k == null) {
                    this.f23573k = this.f23567e.getWindow().getDecorView();
                }
                this.f23574l.showAtLocation(this.f23573k, this.f23572j, 0, 0);
                return;
            } catch (Exception e2) {
                SLog.error(e2);
                return;
            }
        }
        this.f23569g.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        this.f23569g.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        this.f23569g.add(SHARE_MEDIA.SINA.toSnsPlatform());
        this.f23569g.add(SHARE_MEDIA.QQ.toSnsPlatform());
        this.f23569g.add(SHARE_MEDIA.DINGTALK.toSnsPlatform());
        HashMap map2 = new HashMap();
        map2.put(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.f23565c);
        map2.put("content", this.f23563a);
        ShareBoard shareBoard2 = new ShareBoard(this.f23567e, this.f23569g, shareBoardConfig);
        this.f23574l = shareBoard2;
        ShareBoardlistener shareBoardlistener2 = this.f23566d;
        if (shareBoardlistener2 == null) {
            shareBoard2.setShareBoardlistener(this.f23575m);
        } else {
            shareBoard2.setShareBoardlistener(shareBoardlistener2);
        }
        this.f23574l.setFocusable(true);
        this.f23574l.setBackgroundDrawable(new BitmapDrawable());
        if (this.f23573k == null) {
            this.f23573k = this.f23567e.getWindow().getDecorView();
        }
        this.f23574l.showAtLocation(this.f23573k, 80, 0, 0);
    }

    public ShareAction setCallback(UMShareListener uMShareListener) {
        this.f23565c = uMShareListener;
        return this;
    }

    @Deprecated
    public ShareAction setContentList(ShareContent... shareContentArr) {
        if (shareContentArr == null || Arrays.asList(shareContentArr).size() == 0) {
            ShareContent shareContent = new ShareContent();
            shareContent.mText = "empty";
            this.f23570h.add(shareContent);
        } else {
            this.f23570h = Arrays.asList(shareContentArr);
        }
        return this;
    }

    public ShareAction setDisplayList(SHARE_MEDIA... share_mediaArr) {
        this.f23568f = Arrays.asList(share_mediaArr);
        this.f23569g.clear();
        Iterator<SHARE_MEDIA> it = this.f23568f.iterator();
        while (it.hasNext()) {
            this.f23569g.add(it.next().toSnsPlatform());
        }
        return this;
    }

    @Deprecated
    public ShareAction setListenerList(UMShareListener... uMShareListenerArr) {
        this.f23571i = Arrays.asList(uMShareListenerArr);
        return this;
    }

    public ShareAction setPlatform(SHARE_MEDIA share_media) {
        this.f23564b = share_media;
        return this;
    }

    public ShareAction setShareContent(ShareContent shareContent) {
        this.f23563a = shareContent;
        return this;
    }

    public ShareAction setShareboardclickCallback(ShareBoardlistener shareBoardlistener) {
        this.f23566d = shareBoardlistener;
        return this;
    }

    public void share() {
        UMShareAPI.get(this.f23567e).doShare(this.f23567e, this, this.f23565c);
    }

    public ShareAction withApp(File file) {
        this.f23563a.app = file;
        return this;
    }

    public ShareAction withExtra(UMImage uMImage) {
        this.f23563a.mExtra = uMImage;
        return this;
    }

    public ShareAction withFile(File file) {
        this.f23563a.file = file;
        return this;
    }

    public ShareAction withFollow(String str) {
        this.f23563a.mFollow = str;
        return this;
    }

    public ShareAction withMedia(UMImage uMImage) {
        this.f23563a.mMedia = uMImage;
        return this;
    }

    public ShareAction withMedias(UMImage... uMImageArr) {
        if (uMImageArr != null && uMImageArr.length > 0) {
            this.f23563a.mMedia = uMImageArr[0];
        }
        this.f23563a.mMedias = uMImageArr;
        return this;
    }

    public ShareAction withShareBoardDirection(View view, int i2) {
        this.f23572j = i2;
        this.f23573k = view;
        return this;
    }

    public ShareAction withSubject(String str) {
        this.f23563a.subject = str;
        return this;
    }

    public ShareAction withText(String str) {
        this.f23563a.mText = str;
        return this;
    }

    public ShareAction withMedia(UMMin uMMin) {
        this.f23563a.mMedia = uMMin;
        return this;
    }

    public ShareAction withMedia(UMQQMini uMQQMini) {
        this.f23563a.mMedia = uMQQMini;
        return this;
    }

    public ShareAction withMedia(UMEmoji uMEmoji) {
        this.f23563a.mMedia = uMEmoji;
        return this;
    }

    public ShareAction withMedia(UMWeb uMWeb) {
        this.f23563a.mMedia = uMWeb;
        return this;
    }

    public ShareAction withMedia(UMusic uMusic) {
        this.f23563a.mMedia = uMusic;
        return this;
    }

    public ShareAction withMedia(UMVideo uMVideo) {
        this.f23563a.mMedia = uMVideo;
        return this;
    }

    public void open() {
        open(null);
    }
}
