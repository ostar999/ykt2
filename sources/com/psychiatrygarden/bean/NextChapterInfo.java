package com.psychiatrygarden.bean;

import android.util.ArrayMap;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class NextChapterInfo {
    private boolean hasNextChapter;
    private boolean nextChapterIsUnlock;
    private ArrayList<String> remainChapterIds;
    private ArrayMap<String, ChapterTitleInfo> titleMap;
    private boolean[] unlockArr;

    public NextChapterInfo(boolean hasNextChapter, boolean nextChapterIsUnlock, ArrayList<String> remainChapterIds, boolean[] unlockArr, ArrayMap<String, ChapterTitleInfo> map) {
        new ArrayList();
        this.hasNextChapter = hasNextChapter;
        this.remainChapterIds = remainChapterIds;
        this.nextChapterIsUnlock = nextChapterIsUnlock;
        this.unlockArr = unlockArr;
        this.titleMap = map;
    }

    public ArrayList<String> getRemainChapterIds() {
        ArrayList<String> arrayList = this.remainChapterIds;
        return arrayList == null ? new ArrayList<>() : arrayList;
    }

    public ArrayMap<String, ChapterTitleInfo> getTitleMap() {
        return this.titleMap;
    }

    public boolean[] getUnlockArr() {
        boolean[] zArr = this.unlockArr;
        return zArr == null ? new boolean[0] : zArr;
    }

    public boolean isHasNextChapter() {
        return this.hasNextChapter;
    }

    public boolean isNextChapterIsUnlock() {
        return this.nextChapterIsUnlock;
    }

    public void setHasNextChapter(boolean hasNextChapter) {
        this.hasNextChapter = hasNextChapter;
    }

    public void setNextChapterIsUnlock(boolean nextChapterIsUnlock) {
        this.nextChapterIsUnlock = nextChapterIsUnlock;
    }

    public void setRemainChapterIds(ArrayList<String> remainChapterIds) {
        this.remainChapterIds = remainChapterIds;
    }

    public void setTitleMap(ArrayMap<String, ChapterTitleInfo> titleMap) {
        this.titleMap = titleMap;
    }

    public void setUnlockArr(boolean[] unlockArr) {
        this.unlockArr = unlockArr;
    }

    public NextChapterInfo(ArrayList<String> remainChapterIds) {
        new ArrayList();
        this.unlockArr = new boolean[0];
        this.remainChapterIds = remainChapterIds;
    }
}
