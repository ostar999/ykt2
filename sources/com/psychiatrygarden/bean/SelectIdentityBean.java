package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SelectIdentityBean implements Serializable {
    public String code;
    public List<DataBean> data = new ArrayList();
    public String message;

    public static class DataBean implements Serializable {
        private String activity_id;
        private String am_pm;
        private String app_id;
        private String app_type;
        private String bind_identity_id;
        private String category;
        private String created_at;
        private String default_identity_id;
        private String deleted_at;
        private String describe;
        private String export_func_identity_id;
        private String has_permission;
        private String id;
        private String identity_id;
        private String is_b_hidden;
        private String is_hidden;
        private String is_hidden_correction_error;
        private String is_hidden_course;
        private String is_hidden_question_type;
        private String is_hidden_restore_img;
        private String is_hidden_shop;
        private String is_hidden_statistics_card;
        private String is_hidden_unit;
        private String is_hidden_wrong_option;
        private String is_search;
        private String label;
        private String module_type;
        private String name;
        private String parent_id;
        private String question_bank_id;
        private String question_category_id;
        private boolean select;
        private String sort;
        private String title;
        private String type;
        private String updated_at;
        private String updated_by;
        private String is_hidden_exp = "";
        private String is_last = "0";
        private String is_label = "0";
        private int is_select = 0;
        private String last_update_time = "";
        private String is_hidden_difficulty = "";
        private String is_hidden_stat = "";
        private String is_hidden_label = "";
        private String is_hidden_outlines = "";
        private String is_hidden_restore = "";
        private String is_hidden_analysis = "";
        private String is_hidden_options = "";
        private String is_hidden_index = "0";
        private String is_hidden_options_update = "";
        private String is_hidden_analysis_update = "";
        public List<DataBean> children = new ArrayList();

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getAm_pm() {
            return this.am_pm;
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getApp_type() {
            return this.app_type;
        }

        public String getBind_identity_id() {
            return this.bind_identity_id;
        }

        public String getCategory() {
            return this.category;
        }

        public List<DataBean> getChildren() {
            return this.children;
        }

        public String getCreated_at() {
            return this.created_at;
        }

        public String getDefault_identity_id() {
            return this.default_identity_id;
        }

        public String getDeleted_at() {
            return this.deleted_at;
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getExport_func_identity_id() {
            return this.export_func_identity_id;
        }

        public String getHas_permission() {
            return this.has_permission;
        }

        public String getId() {
            return this.id;
        }

        public String getIdentity_id() {
            return this.identity_id;
        }

        public String getIs_b_hidden() {
            return this.is_b_hidden;
        }

        public String getIs_hidden() {
            return this.is_hidden;
        }

        public String getIs_hidden_analysis() {
            return this.is_hidden_analysis;
        }

        public String getIs_hidden_analysis_update() {
            return this.is_hidden_analysis_update;
        }

        public String getIs_hidden_correction_error() {
            return this.is_hidden_correction_error;
        }

        public String getIs_hidden_course() {
            return this.is_hidden_course;
        }

        public String getIs_hidden_difficulty() {
            return this.is_hidden_difficulty;
        }

        public String getIs_hidden_exp() {
            return this.is_hidden_exp;
        }

        public String getIs_hidden_index() {
            return this.is_hidden_index;
        }

        public String getIs_hidden_label() {
            return this.is_hidden_label;
        }

        public String getIs_hidden_options() {
            return this.is_hidden_options;
        }

        public String getIs_hidden_options_update() {
            return this.is_hidden_options_update;
        }

        public String getIs_hidden_outlines() {
            return this.is_hidden_outlines;
        }

        public String getIs_hidden_question_type() {
            return this.is_hidden_question_type;
        }

        public String getIs_hidden_restore() {
            return this.is_hidden_restore;
        }

        public String getIs_hidden_restore_img() {
            return this.is_hidden_restore_img;
        }

        public String getIs_hidden_shop() {
            return this.is_hidden_shop;
        }

        public String getIs_hidden_stat() {
            return this.is_hidden_stat;
        }

        public String getIs_hidden_statistics_card() {
            return this.is_hidden_statistics_card;
        }

        public String getIs_hidden_unit() {
            return this.is_hidden_unit;
        }

        public String getIs_hidden_wrong_option() {
            return this.is_hidden_wrong_option;
        }

        public String getIs_label() {
            return this.is_label;
        }

        public String getIs_last() {
            return this.is_last;
        }

        public String getIs_search() {
            return this.is_search;
        }

        public int getIs_select() {
            return this.is_select;
        }

        public String getLabel() {
            return this.label;
        }

        public String getLast_update_time() {
            return this.last_update_time;
        }

        public String getModule_type() {
            return this.module_type;
        }

        public String getName() {
            return this.name;
        }

        public String getParent_id() {
            return this.parent_id;
        }

        public String getQuestion_bank_id() {
            return this.question_bank_id;
        }

        public String getQuestion_category_id() {
            return this.question_category_id;
        }

        public String getSort() {
            return this.sort;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getUpdated_at() {
            return this.updated_at;
        }

        public String getUpdated_by() {
            return this.updated_by;
        }

        public boolean isSelect() {
            return this.select;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public void setAm_pm(String am_pm) {
            this.am_pm = am_pm;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public void setBind_identity_id(String bind_identity_id) {
            this.bind_identity_id = bind_identity_id;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setChildren(List<DataBean> children) {
            this.children = children;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setDefault_identity_id(String default_identity_id) {
            this.default_identity_id = default_identity_id;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setExport_func_identity_id(String export_func_identity_id) {
            this.export_func_identity_id = export_func_identity_id;
        }

        public void setHas_permission(String has_permission) {
            this.has_permission = has_permission;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIdentity_id(String identity_id) {
            this.identity_id = identity_id;
        }

        public void setIs_b_hidden(String is_b_hidden) {
            this.is_b_hidden = is_b_hidden;
        }

        public void setIs_hidden(String is_hidden) {
            this.is_hidden = is_hidden;
        }

        public void setIs_hidden_analysis(String is_hidden_analysis) {
            this.is_hidden_analysis = is_hidden_analysis;
        }

        public void setIs_hidden_analysis_update(String is_hidden_analysis_update) {
            this.is_hidden_analysis_update = is_hidden_analysis_update;
        }

        public void setIs_hidden_correction_error(String is_hidden_correction_error) {
            this.is_hidden_correction_error = is_hidden_correction_error;
        }

        public void setIs_hidden_course(String is_hidden_course) {
            this.is_hidden_course = is_hidden_course;
        }

        public void setIs_hidden_difficulty(String is_hidden_difficulty) {
            this.is_hidden_difficulty = is_hidden_difficulty;
        }

        public void setIs_hidden_exp(String is_hidden_exp) {
            this.is_hidden_exp = is_hidden_exp;
        }

        public void setIs_hidden_index(String is_hidden_index) {
            this.is_hidden_index = is_hidden_index;
        }

        public void setIs_hidden_label(String is_hidden_label) {
            this.is_hidden_label = is_hidden_label;
        }

        public void setIs_hidden_options(String is_hidden_options) {
            this.is_hidden_options = is_hidden_options;
        }

        public void setIs_hidden_options_update(String is_hidden_options_update) {
            this.is_hidden_options_update = is_hidden_options_update;
        }

        public void setIs_hidden_outlines(String is_hidden_outlines) {
            this.is_hidden_outlines = is_hidden_outlines;
        }

        public void setIs_hidden_question_type(String is_hidden_question_type) {
            this.is_hidden_question_type = is_hidden_question_type;
        }

        public void setIs_hidden_restore(String is_hidden_restore) {
            this.is_hidden_restore = is_hidden_restore;
        }

        public void setIs_hidden_restore_img(String is_hidden_restore_img) {
            this.is_hidden_restore_img = is_hidden_restore_img;
        }

        public void setIs_hidden_shop(String is_hidden_shop) {
            this.is_hidden_shop = is_hidden_shop;
        }

        public void setIs_hidden_stat(String is_hidden_stat) {
            this.is_hidden_stat = is_hidden_stat;
        }

        public void setIs_hidden_statistics_card(String is_hidden_statistics_card) {
            this.is_hidden_statistics_card = is_hidden_statistics_card;
        }

        public void setIs_hidden_unit(String is_hidden_unit) {
            this.is_hidden_unit = is_hidden_unit;
        }

        public void setIs_hidden_wrong_option(String is_hidden_wrong_option) {
            this.is_hidden_wrong_option = is_hidden_wrong_option;
        }

        public void setIs_label(String is_label) {
            this.is_label = is_label;
        }

        public void setIs_last(String is_last) {
            this.is_last = is_last;
        }

        public void setIs_search(String is_search) {
            this.is_search = is_search;
        }

        public void setIs_select(int is_select) {
            this.is_select = is_select;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setLast_update_time(String last_update_time) {
            this.last_update_time = last_update_time;
        }

        public void setModule_type(String module_type) {
            this.module_type = module_type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public void setQuestion_bank_id(String question_bank_id) {
            this.question_bank_id = question_bank_id;
        }

        public void setQuestion_category_id(String question_category_id) {
            this.question_category_id = question_category_id;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setUpdated_by(String updated_by) {
            this.updated_by = updated_by;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
