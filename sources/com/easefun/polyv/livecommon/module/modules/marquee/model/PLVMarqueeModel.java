package com.easefun.polyv.livecommon.module.modules.marquee.model;

import android.util.Log;
import com.alipay.sdk.sys.a;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.umeng.analytics.pro.am;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PLVMarqueeModel {
    private static final String TAG = "PLVMarqueeModel";
    private PLVMarqueeTextVO mainTextVO = new PLVMarqueeTextVO();
    private PLVMarqueeTextVO secondTextVO = new PLVMarqueeTextVO();
    private PLVMarqueeAnimationVO animationVO = new PLVMarqueeAnimationVO();

    public PLVMarqueeAnimationVO getAnimationVO() {
        return this.animationVO;
    }

    public PLVMarqueeTextVO getMainTextVO() {
        return this.mainTextVO;
    }

    public PLVMarqueeTextVO getSecondTextVO() {
        return this.secondTextVO;
    }

    public int getSetting() {
        return this.animationVO.getAnimationType();
    }

    public PLVMarqueeModel setAlwaysShowWhenRun(boolean isAlwaysShowWhenRun) {
        this.animationVO.setAlwaysShowWhenRun(isAlwaysShowWhenRun);
        return this;
    }

    public PLVMarqueeModel setFilter(boolean isFilter) {
        this.mainTextVO.setFilter(isFilter);
        return this;
    }

    public PLVMarqueeModel setFilterAlpha(int filterAlpha) {
        this.mainTextVO.setFilterAlpha(filterAlpha);
        return this;
    }

    public PLVMarqueeModel setFilterBlurX(int filterBlurX) {
        this.mainTextVO.setFilterBlurX(filterBlurX);
        return this;
    }

    public PLVMarqueeModel setFilterBlurY(int filterBlurY) {
        this.mainTextVO.setFilterBlurY(filterBlurY);
        return this;
    }

    public PLVMarqueeModel setFilterColor(int color) {
        this.mainTextVO.setFilterColor(color);
        return this;
    }

    public PLVMarqueeModel setFilterStrength(int strength) {
        this.mainTextVO.setFilterStrength(strength);
        return this;
    }

    public PLVMarqueeModel setFontAlpha(int fontAlpha) {
        this.mainTextVO.setFontAlpha(fontAlpha);
        return this;
    }

    public PLVMarqueeModel setFontColor(int color) {
        this.mainTextVO.setFontColor(color);
        return this;
    }

    public PLVMarqueeModel setFontSize(int fontSize) {
        this.mainTextVO.setFontSize(fontSize);
        return this;
    }

    public PLVMarqueeModel setHiddenWhenPause(boolean hiddenWhenPause) {
        this.animationVO.setHiddenWhenPause(hiddenWhenPause);
        return this;
    }

    public PLVMarqueeModel setInterval(int interval) {
        this.animationVO.setInterval(interval);
        return this;
    }

    public PLVMarqueeModel setLifeTime(int lifeTime) {
        this.animationVO.setLifeTime(lifeTime);
        return this;
    }

    public void setParamsByJsonObject(JSONObject jsonObject) {
        String str;
        String str2;
        if (jsonObject != null) {
            try {
                if (jsonObject.isNull("userName")) {
                    str = am.aT;
                    str2 = "speed";
                } else {
                    str = am.aT;
                    StringBuilder sb = new StringBuilder();
                    str2 = "speed";
                    sb.append("setParamsByJsonObject: userName:");
                    sb.append(jsonObject.getString("userName"));
                    Log.i(TAG, sb.toString());
                    setUserName(jsonObject.getString("userName"));
                }
                if (!jsonObject.isNull(TtmlNode.ATTR_TTS_FONT_SIZE)) {
                    Log.i(TAG, "setFontSize: fontSize:" + jsonObject.getInt(TtmlNode.ATTR_TTS_FONT_SIZE));
                    setFontSize(jsonObject.getInt(TtmlNode.ATTR_TTS_FONT_SIZE));
                }
                if (!jsonObject.isNull("fontColor")) {
                    Log.i(TAG, "setFontColor: fontColor:" + jsonObject.getInt("fontColor"));
                    setFontColor(jsonObject.getInt("fontColor"));
                }
                if (!jsonObject.isNull("fontAlpha")) {
                    Log.i(TAG, "setFontAlpha: fontAlpha:" + jsonObject.getInt("fontAlpha"));
                    setFontAlpha(jsonObject.getInt("fontAlpha"));
                }
                if (!jsonObject.isNull("filter")) {
                    Log.i(TAG, "setFilter: filter:" + jsonObject.getBoolean("filter"));
                    setFilter(jsonObject.getBoolean("filter"));
                }
                if (!jsonObject.isNull("filterColor")) {
                    Log.i(TAG, "setFilterColor: filterColor:" + jsonObject.getInt("filterColor"));
                    setFilterColor(jsonObject.getInt("filterColor"));
                }
                if (!jsonObject.isNull("filterBlurX")) {
                    Log.i(TAG, "setFilterBlurX: filterBlurX:" + jsonObject.getInt("filterBlurX"));
                    setFilterBlurX(jsonObject.getInt("filterBlurX"));
                }
                if (!jsonObject.isNull("filterBlurY")) {
                    Log.i(TAG, "setFilterBlurX: filterBlurY:" + jsonObject.getInt("filterBlurY"));
                    setFilterBlurY(jsonObject.getInt("filterBlurY"));
                }
                if (!jsonObject.isNull("filterAlpha")) {
                    Log.i(TAG, "setFilterAlpha: filterAlpha:" + jsonObject.getInt("filterAlpha"));
                    setFilterAlpha(jsonObject.getInt("filterAlpha"));
                }
                if (!jsonObject.isNull("filterStrength")) {
                    Log.i(TAG, "setFilterStrength: filterStrength:" + jsonObject.getInt("filterStrength"));
                    setFilterStrength(jsonObject.getInt("filterStrength"));
                }
                if (!jsonObject.isNull(a.f3325j)) {
                    Log.i(TAG, "setSetting: setting:" + jsonObject.getInt(a.f3325j));
                    setSetting(jsonObject.getInt(a.f3325j));
                }
                String str3 = str2;
                if (!jsonObject.isNull(str3)) {
                    Log.i(TAG, "setSpeed: speed:" + jsonObject.getInt(str3));
                    setSpeed(jsonObject.getInt(str3));
                }
                String str4 = str;
                if (!jsonObject.isNull(str4)) {
                    Log.i(TAG, "setInterval: interval:" + jsonObject.getInt(str4));
                    setInterval(jsonObject.getInt(str4));
                }
                if (!jsonObject.isNull("lifeTime")) {
                    Log.i(TAG, "setLifeTime: lifeTime:" + jsonObject.getInt("lifeTime"));
                    setLifeTime(jsonObject.getInt("lifeTime"));
                }
                if (jsonObject.isNull("tweenTime")) {
                    return;
                }
                Log.i(TAG, "setTweenTime: tweenTime:" + jsonObject.getInt("tweenTime"));
                setTweenTime(jsonObject.getInt("tweenTime"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setSecondDefaultTextVO() {
        int animationType = this.animationVO.getAnimationType();
        if (animationType == 6 || animationType == 7) {
            this.secondTextVO.setContent(this.mainTextVO.getContent());
            this.secondTextVO.setFontColor(this.mainTextVO.getFontColor());
            this.secondTextVO.setFontSize(this.mainTextVO.getFontSize());
            this.secondTextVO.setFontAlpha(2);
            this.secondTextVO.setFilter(this.mainTextVO.isFilter());
            this.secondTextVO.setFilterBlurY(this.mainTextVO.getFilterBlurY());
            this.secondTextVO.setFilterBlurX(this.mainTextVO.getFilterBlurX());
            this.secondTextVO.setFilterStrength(this.mainTextVO.getFilterStrength());
            this.secondTextVO.setFilterAlpha(2.0f);
            this.secondTextVO.setFilterColor(this.mainTextVO.getFilterColor());
        }
    }

    public PLVMarqueeModel setSetting(int animationType) {
        this.animationVO.setAnimationType(animationType);
        return this;
    }

    public PLVMarqueeModel setSpeed(int speed) {
        this.animationVO.setSpeed(speed);
        return this;
    }

    public PLVMarqueeModel setTweenTime(int tweenTime) {
        this.animationVO.setTweenTime(tweenTime);
        return this;
    }

    public PLVMarqueeModel setUserName(String userName) {
        this.mainTextVO.setContent(userName);
        return this;
    }
}
