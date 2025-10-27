package com.plv.livescenes.document.model;

import cn.hutool.core.text.CharPool;
import com.google.gson.annotations.Expose;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVPPTInfo extends PLVPPTBaseModel {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private List<ContentsBean> contents;
        private int endRow;
        private boolean firstPage;
        private boolean lastPage;
        private int limit;
        private int nextPageNumber;
        private int offset;
        private int pageNumber;
        private int pageSize;
        private int prePageNumber;
        private boolean reprehenderite8;
        private int startRow;
        private int totalItems;
        private int totalPages;

        public static class ContentsBean extends PLVPPTBaseModel {
            private int autoId;
            private String channelId;
            private String convertType;
            private String fileId;
            private String fileName;

            @Expose(deserialize = false, serialize = false)
            private String filePath;
            private String fileType;
            private String fileUrl;
            private String previewBigImage;
            private String previewImage;

            @Expose(deserialize = false, serialize = false)
            private int progress;
            private String status;
            private int totalPage;
            private String type;

            @Expose(deserialize = false, serialize = false)
            private int uploadStatus;

            public int getAutoId() {
                return this.autoId;
            }

            public String getChannelId() {
                return this.channelId;
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

            public String getFileType() {
                return this.fileType;
            }

            public String getFileUrl() {
                return this.fileUrl;
            }

            public String getPreviewBigImage() {
                return this.previewBigImage;
            }

            public String getPreviewImage() {
                return this.previewImage;
            }

            public int getProgress() {
                return this.progress;
            }

            public String getStatus() {
                return this.status;
            }

            public int getTotalPage() {
                return this.totalPage;
            }

            public String getType() {
                return this.type;
            }

            public int getUploadStatus() {
                return this.uploadStatus;
            }

            public void setAutoId(int i2) {
                this.autoId = i2;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setConvertType(String str) {
                this.convertType = str;
            }

            public void setFileId(String str) {
                this.fileId = str;
            }

            public void setFileName(String str) {
                this.fileName = str;
            }

            public void setFilePath(String str) {
                this.filePath = str;
            }

            public void setFileType(String str) {
                this.fileType = str;
            }

            public void setFileUrl(String str) {
                this.fileUrl = str;
            }

            public void setPreviewBigImage(String str) {
                this.previewBigImage = str;
            }

            public void setPreviewImage(String str) {
                this.previewImage = str;
            }

            public void setProgress(int i2) {
                this.progress = i2;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public void setTotalPage(int i2) {
                this.totalPage = i2;
            }

            public void setType(String str) {
                this.type = str;
            }

            public void setUploadStatus(int i2) {
                this.uploadStatus = i2;
            }

            public String toString() {
                return "ContentsBean{status='" + this.status + CharPool.SINGLE_QUOTE + ", totalPage=" + this.totalPage + ", autoId=" + this.autoId + ", fileType='" + this.fileType + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", previewBigImage='" + this.previewBigImage + CharPool.SINGLE_QUOTE + ", fileName='" + this.fileName + CharPool.SINGLE_QUOTE + ", convertType='" + this.convertType + CharPool.SINGLE_QUOTE + ", previewImage='" + this.previewImage + CharPool.SINGLE_QUOTE + ", fileUrl='" + this.fileUrl + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", uploadStatus=" + this.uploadStatus + ", progress=" + this.progress + ", filePath='" + this.filePath + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public List<ContentsBean> getContents() {
            return this.contents;
        }

        public int getEndRow() {
            return this.endRow;
        }

        public int getLimit() {
            return this.limit;
        }

        public int getNextPageNumber() {
            return this.nextPageNumber;
        }

        public int getOffset() {
            return this.offset;
        }

        public int getPageNumber() {
            return this.pageNumber;
        }

        public int getPageSize() {
            return this.pageSize;
        }

        public int getPrePageNumber() {
            return this.prePageNumber;
        }

        public int getStartRow() {
            return this.startRow;
        }

        public int getTotalItems() {
            return this.totalItems;
        }

        public int getTotalPages() {
            return this.totalPages;
        }

        public boolean isFirstPage() {
            return this.firstPage;
        }

        public boolean isLastPage() {
            return this.lastPage;
        }

        public boolean isReprehenderite8() {
            return this.reprehenderite8;
        }

        public void setContents(List<ContentsBean> list) {
            this.contents = list;
        }

        public void setEndRow(int i2) {
            this.endRow = i2;
        }

        public void setFirstPage(boolean z2) {
            this.firstPage = z2;
        }

        public void setLastPage(boolean z2) {
            this.lastPage = z2;
        }

        public void setLimit(int i2) {
            this.limit = i2;
        }

        public void setNextPageNumber(int i2) {
            this.nextPageNumber = i2;
        }

        public void setOffset(int i2) {
            this.offset = i2;
        }

        public void setPageNumber(int i2) {
            this.pageNumber = i2;
        }

        public void setPageSize(int i2) {
            this.pageSize = i2;
        }

        public void setPrePageNumber(int i2) {
            this.prePageNumber = i2;
        }

        public void setReprehenderite8(boolean z2) {
            this.reprehenderite8 = z2;
        }

        public void setStartRow(int i2) {
            this.startRow = i2;
        }

        public void setTotalItems(int i2) {
            this.totalItems = i2;
        }

        public void setTotalPages(int i2) {
            this.totalPages = i2;
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return (DataBean) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
