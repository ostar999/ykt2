package com.psychiatrygarden.bean;

import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class WordSentenceBean implements Comparable<WordSentenceBean> {
    private int charEndIndex;
    private int charStartIndex;
    private String detailId;
    private String extendInfo;
    private String interpretation;
    private String originWord;
    private String phonogram;
    private List<RectF> rectFS = new ArrayList();
    private String wordColor;

    public int getCharEndIndex() {
        return this.charEndIndex;
    }

    public int getCharStartIndex() {
        return this.charStartIndex;
    }

    public String getDetailId() {
        return this.detailId;
    }

    public String getExtendInfo() {
        return this.extendInfo;
    }

    public String getInterpretation() {
        return this.interpretation;
    }

    public String getOriginWord() {
        return this.originWord;
    }

    public String getPhonogram() {
        return this.phonogram;
    }

    public List<RectF> getRectFS() {
        return this.rectFS;
    }

    public String getWordColor() {
        return this.wordColor;
    }

    public void setCharEndIndex(int charEndIndex) {
        this.charEndIndex = charEndIndex;
    }

    public void setCharStartIndex(int charStartIndex) {
        this.charStartIndex = charStartIndex;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public void setOriginWord(String originWord) {
        this.originWord = originWord;
    }

    public void setPhonogram(String phonogram) {
        this.phonogram = phonogram;
    }

    public void setRectFS(List<RectF> rectFS) {
        this.rectFS = rectFS;
    }

    public void setWordColor(String wordColor) {
        this.wordColor = wordColor;
    }

    @Override // java.lang.Comparable
    public int compareTo(WordSentenceBean bean) {
        return this.charStartIndex - bean.charStartIndex;
    }
}
