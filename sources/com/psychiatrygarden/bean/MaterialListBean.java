package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MaterialListBean {
    private String code;
    private MaterialList data;
    private String message;

    public class MaterialList {
        private List<MaterialListData> list;

        public MaterialList() {
        }

        public List<MaterialListData> getList() {
            return this.list;
        }

        public void setList(List<MaterialListData> list) {
            this.list = list;
        }
    }

    public class MaterialListData {
        private String desc;
        private String download_count;
        private String file_count;
        private String file_id;
        private String file_type;
        private String icon;
        private String id;
        private String img_url;
        private boolean isSelect;
        private String is_collect;
        private String is_prohibit;
        private String is_review;
        private String is_rights;
        private String is_support;
        private String nickname;
        private String original_size;
        private String review_count;
        private String size;
        private String title;
        private String type_name;
        private String url;
        private String user_id;

        public MaterialListData() {
        }

        public String getDesc() {
            return this.desc;
        }

        public String getDownload_count() {
            return this.download_count;
        }

        public String getFile_count() {
            return this.file_count;
        }

        public String getFile_id() {
            return this.file_id;
        }

        public String getFile_type() {
            return this.file_type;
        }

        public String getIcon() {
            return this.icon;
        }

        public String getId() {
            return this.id;
        }

        public String getImg_url() {
            return this.img_url;
        }

        public String getIs_collect() {
            return this.is_collect;
        }

        public String getIs_prohibit() {
            return this.is_prohibit;
        }

        public String getIs_review() {
            return this.is_review;
        }

        public String getIs_rights() {
            return this.is_rights;
        }

        public String getIs_support() {
            return this.is_support;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getOriginal_size() {
            return this.original_size;
        }

        public String getReview_count() {
            return this.review_count;
        }

        public String getSize() {
            return this.size;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType_name() {
            return this.type_name;
        }

        public String getUrl() {
            return this.url;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public boolean isSelect() {
            return this.isSelect;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setDownload_count(String download_count) {
            this.download_count = download_count;
        }

        public void setFile_count(String file_count) {
            this.file_count = file_count;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public void setIs_prohibit(String is_prohibit) {
            this.is_prohibit = is_prohibit;
        }

        public void setIs_review(String is_review) {
            this.is_review = is_review;
        }

        public void setIs_rights(String is_rights) {
            this.is_rights = is_rights;
        }

        public void setIs_support(String is_support) {
            this.is_support = is_support;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setOriginal_size(String original_size) {
            this.original_size = original_size;
        }

        public void setReview_count(String review_count) {
            this.review_count = review_count;
        }

        public void setSelect(boolean select) {
            this.isSelect = select;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }

    public String getCode() {
        return this.code;
    }

    public MaterialList getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(MaterialList data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
