package com.easefun.polyv.livecommon.module.modules.document.model.vo;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.utils.PLVGsonUtil;

/* loaded from: classes3.dex */
public class PLVPptUploadLocalCacheVO {
    private String convertType;
    private String fileId;
    private String fileName;
    private String filePath;
    private Integer status;

    public static class Serializer {
        public static PLVPptUploadLocalCacheVO fromJson(String json) {
            return (PLVPptUploadLocalCacheVO) PLVGsonUtil.fromJson(PLVPptUploadLocalCacheVO.class, json);
        }

        public static String toJson(PLVPptUploadLocalCacheVO vo) {
            return PLVGsonUtil.toJson(vo);
        }
    }

    public String getConvertType() {
        return this.convertType;
    }

    public String getFileId() {
        return this.fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setConvertType(String convertType) {
        this.convertType = convertType;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        return "PLVPptUploadLocalCacheVO{fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", filePath='" + this.filePath + CharPool.SINGLE_QUOTE + ", fileName='" + this.fileName + CharPool.SINGLE_QUOTE + ", convertType='" + this.convertType + CharPool.SINGLE_QUOTE + ", status=" + this.status + '}';
    }
}
