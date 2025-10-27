package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVHCGroupInfoVO {
    private Integer code;
    private List<DataBean> data;
    private String message;
    private String status;

    public static class DataBean {
        private String groupId;
        private String groupName;

        public String getGroupId() {
            return this.groupId;
        }

        public String getGroupName() {
            return this.groupName;
        }

        public void setGroupId(String str) {
            this.groupId = str;
        }

        public void setGroupName(String str) {
            this.groupName = str;
        }

        public String toString() {
            return "DataBean{groupId='" + this.groupId + CharPool.SINGLE_QUOTE + ", groupName='" + this.groupName + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVHCGroupInfoVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
