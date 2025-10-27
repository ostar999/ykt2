package com.plv.livescenes.model;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.plv.livescenes.chatroom.event.PLVEventHelper;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVPlaybackListVO {
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
        private int startRow;
        private int totalItems;
        private int totalPages;

        public static class ContentsBean {
            private String asDefault;
            private Object callbackUrl;
            private String channelId;
            private String channelSessionId;
            private long createdTime;
            private String duration;
            private Object errorCount;
            private String fileId;
            private String fileUrl;
            private String firstImage;
            private Object height;
            private long lastModified;
            private String liveType;
            private Object mergeinfo;
            private String myBr;
            private long ordertime;
            private Object origin;
            private Object qid;
            private int rank;
            private int seed;
            private String startTime;
            private String status;
            private String title;
            private String url;
            private String userId;
            private String videoId;
            private String videoPoolId;
            private String watchUrl;
            private Object width;

            public String getAsDefault() {
                return this.asDefault;
            }

            public Object getCallbackUrl() {
                return this.callbackUrl;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public String getChannelSessionId() {
                return this.channelSessionId;
            }

            public long getCreatedTime() {
                return this.createdTime;
            }

            public String getDuration() {
                return this.duration;
            }

            public Object getErrorCount() {
                return this.errorCount;
            }

            public String getFileId() {
                return this.fileId;
            }

            public String getFileUrl() {
                return this.fileUrl;
            }

            public String getFirstImage() {
                return PLVEventHelper.fixChatPic(this.firstImage);
            }

            public Object getHeight() {
                return this.height;
            }

            public long getLastModified() {
                return this.lastModified;
            }

            public String getLiveType() {
                return this.liveType;
            }

            public Object getMergeinfo() {
                return this.mergeinfo;
            }

            public String getMyBr() {
                return this.myBr;
            }

            public long getOrdertime() {
                return this.ordertime;
            }

            public Object getOrigin() {
                return this.origin;
            }

            public Object getQid() {
                return this.qid;
            }

            public int getRank() {
                return this.rank;
            }

            public int getSeed() {
                return this.seed;
            }

            public String getStartTime() {
                return this.startTime;
            }

            public String getStatus() {
                return this.status;
            }

            public String getTitle() {
                return this.title;
            }

            public String getUrl() {
                return this.url;
            }

            public String getUserId() {
                return this.userId;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public String getVideoPoolId() {
                return this.videoPoolId;
            }

            public String getWatchUrl() {
                return this.watchUrl;
            }

            public Object getWidth() {
                return this.width;
            }

            public boolean isAlone() {
                return !isPPTType();
            }

            public boolean isPPTType() {
                return "ppt".equals(this.liveType) || (TextUtils.isEmpty(this.liveType) && !TextUtils.isEmpty(this.channelSessionId));
            }

            public void setAsDefault(String str) {
                this.asDefault = str;
            }

            public void setCallbackUrl(Object obj) {
                this.callbackUrl = obj;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setChannelSessionId(String str) {
                this.channelSessionId = str;
            }

            public void setCreatedTime(long j2) {
                this.createdTime = j2;
            }

            public void setDuration(String str) {
                this.duration = str;
            }

            public void setErrorCount(Object obj) {
                this.errorCount = obj;
            }

            public void setFileId(String str) {
                this.fileId = str;
            }

            public void setFileUrl(String str) {
                this.fileUrl = str;
            }

            public void setFirstImage(String str) {
                this.firstImage = str;
            }

            public void setHeight(Object obj) {
                this.height = obj;
            }

            public void setLastModified(long j2) {
                this.lastModified = j2;
            }

            public void setLiveType(String str) {
                this.liveType = str;
            }

            public void setMergeinfo(Object obj) {
                this.mergeinfo = obj;
            }

            public void setMyBr(String str) {
                this.myBr = str;
            }

            public void setOrdertime(long j2) {
                this.ordertime = j2;
            }

            public void setOrigin(Object obj) {
                this.origin = obj;
            }

            public void setQid(Object obj) {
                this.qid = obj;
            }

            public void setRank(int i2) {
                this.rank = i2;
            }

            public void setSeed(int i2) {
                this.seed = i2;
            }

            public void setStartTime(String str) {
                this.startTime = str;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public void setUrl(String str) {
                this.url = str;
            }

            public void setUserId(String str) {
                this.userId = str;
            }

            public void setVideoId(String str) {
                this.videoId = str;
            }

            public void setVideoPoolId(String str) {
                this.videoPoolId = str;
            }

            public void setWatchUrl(String str) {
                this.watchUrl = str;
            }

            public void setWidth(Object obj) {
                this.width = obj;
            }

            public String toString() {
                return "ContentsBean{videoId='" + this.videoId + CharPool.SINGLE_QUOTE + ", videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", title='" + this.title + CharPool.SINGLE_QUOTE + ", firstImage='" + this.firstImage + CharPool.SINGLE_QUOTE + ", duration='" + this.duration + CharPool.SINGLE_QUOTE + ", myBr='" + this.myBr + CharPool.SINGLE_QUOTE + ", qid=" + this.qid + ", seed=" + this.seed + ", ordertime=" + this.ordertime + ", createdTime=" + this.createdTime + ", lastModified=" + this.lastModified + ", rank=" + this.rank + ", asDefault='" + this.asDefault + CharPool.SINGLE_QUOTE + ", url='" + this.url + CharPool.SINGLE_QUOTE + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", fileUrl='" + this.fileUrl + CharPool.SINGLE_QUOTE + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", startTime='" + this.startTime + CharPool.SINGLE_QUOTE + ", liveType='" + this.liveType + CharPool.SINGLE_QUOTE + ", width=" + this.width + ", height=" + this.height + ", origin=" + this.origin + ", callbackUrl=" + this.callbackUrl + ", errorCount=" + this.errorCount + ", mergeinfo=" + this.mergeinfo + ", watchUrl='" + this.watchUrl + CharPool.SINGLE_QUOTE + '}';
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

        public void setStartRow(int i2) {
            this.startRow = i2;
        }

        public void setTotalItems(int i2) {
            this.totalItems = i2;
        }

        public void setTotalPages(int i2) {
            this.totalPages = i2;
        }

        public String toString() {
            return "DataBean{pageSize=" + this.pageSize + ", pageNumber=" + this.pageNumber + ", totalItems=" + this.totalItems + ", totalPages=" + this.totalPages + ", endRow=" + this.endRow + ", nextPageNumber=" + this.nextPageNumber + ", lastPage=" + this.lastPage + ", prePageNumber=" + this.prePageNumber + ", startRow=" + this.startRow + ", firstPage=" + this.firstPage + ", limit=" + this.limit + ", offset=" + this.offset + ", contents=" + this.contents + '}';
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

    public String toString() {
        return "PLVPlaybackListVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
