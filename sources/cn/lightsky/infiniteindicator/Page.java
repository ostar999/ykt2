package cn.lightsky.infiniteindicator;

/* loaded from: classes.dex */
public class Page {
    public String data;
    public Object res;
    private String videoUrl;

    public Page(Object obj) {
        this.data = "";
        this.res = obj;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String str) {
        this.videoUrl = str;
    }

    public Page(String str, Object obj) {
        this.data = str;
        this.res = obj;
    }

    public Page(String str, Object obj, OnPageClickListener onPageClickListener) {
        this.data = str;
        this.res = obj;
    }
}
