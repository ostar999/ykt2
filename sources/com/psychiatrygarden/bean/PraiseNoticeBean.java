package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.MyMessageCommentBean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class PraiseNoticeBean implements Serializable {
    private int code;
    private List<PraiseNoticeDataBean> data;
    private String message;
    private int server_time;

    public class PraiseNoticeDataBean {
        private int author_id;
        private int author_looked;
        private String avatar;
        private int comment_id;
        private int comment_type;
        private String content;
        private String is_oppose;
        private String is_praise;
        private String nickname;
        private String obj_id;
        private String oppose_num;
        private String praise_num;
        private int push_type;
        private List<MyMessageCommentBean.ReplayBean> reply;
        private String reply_num;
        private String reply_primary_id;
        private TarGetParamBean target_params;
        private String timestr;
        private String title;
        private String to_from;
        private String to_from_author;
        private String user_id;
        private int module_type = 0;
        private String is_read = "1";

        public PraiseNoticeDataBean() {
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<PraiseNoticeDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<PraiseNoticeDataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
