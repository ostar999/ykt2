package com.arialyy.aria.core.download.m3u8;

import android.text.TextUtils;
import com.arialyy.aria.core.common.BaseOption;
import com.arialyy.aria.core.download.m3u8.M3U8Option;
import com.arialyy.aria.core.processor.IBandWidthUrlConverter;
import com.arialyy.aria.core.processor.IKeyUrlConverter;
import com.arialyy.aria.core.processor.ITsMergeHandler;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import com.arialyy.aria.util.ComponentUtil;

/* loaded from: classes2.dex */
public class M3U8Option<OP extends M3U8Option> extends BaseOption {
    private int bandWidth;
    private IBandWidthUrlConverter bandWidthUrlConverter;
    private String keyPath;
    private IKeyUrlConverter keyUrlConverter;
    private ITsMergeHandler mergeHandler;
    private boolean generateIndexFile = false;
    private boolean mergeFile = true;
    private boolean ignoreFailureTs = false;
    private boolean useDefConvert = true;

    public M3U8Option() throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(3);
    }

    public OP generateIndexFile() {
        this.generateIndexFile = true;
        return this;
    }

    public OP ignoreFailureTs() {
        this.ignoreFailureTs = true;
        return this;
    }

    public OP merge(boolean z2) {
        this.mergeFile = z2;
        return this;
    }

    public OP setBandWidth(int i2) {
        this.bandWidth = i2;
        return this;
    }

    public OP setBandWidthUrlConverter(IBandWidthUrlConverter iBandWidthUrlConverter) {
        CheckUtil.checkMemberClass(iBandWidthUrlConverter.getClass());
        this.bandWidthUrlConverter = iBandWidthUrlConverter;
        return this;
    }

    public OP setKeyPath(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "密钥文件保存路径为空");
            return this;
        }
        this.keyPath = str;
        return this;
    }

    public OP setKeyUrlConverter(IKeyUrlConverter iKeyUrlConverter) {
        CheckUtil.checkMemberClass(iKeyUrlConverter.getClass());
        this.keyUrlConverter = iKeyUrlConverter;
        return this;
    }

    public OP setMergeHandler(ITsMergeHandler iTsMergeHandler) {
        CheckUtil.checkMemberClass(iTsMergeHandler.getClass());
        this.mergeHandler = iTsMergeHandler;
        return this;
    }

    public OP setUseDefConvert(boolean z2) {
        this.useDefConvert = z2;
        ALog.d(this.TAG, "使用默认的码率转换器和TS转换器，如果无法下载，请参考：https://github.com/AriaLyy/Aria/issues/597 定制转换器");
        return this;
    }
}
