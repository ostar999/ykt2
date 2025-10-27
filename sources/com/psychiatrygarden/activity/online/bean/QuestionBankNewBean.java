package com.psychiatrygarden.activity.online.bean;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionBankNewBean implements Serializable {
    public String code;
    public List<DataBean> data;
    private String have_year;
    public String message;

    public static class DataBean extends BaseExpandNode implements Serializable {
        public String activity_id;
        public String am_pm;
        private String chapter_id;
        public List<ChildrenBean> children;

        @JsonAdapter(String2IntAdapter.class)
        public int count;

        @JsonAdapter(String2IntAdapter.class)
        public int error_count;
        public String have;
        public String pass;
        public String primary_id;
        public String question_bank_id;

        @JsonAdapter(String2IntAdapter.class)
        public int right_count;
        public String title;
        public String type;
        private boolean isShowContinue = false;
        public int viewType = 0;
        public int isSelected = 0;
        public List<String> childIds = new ArrayList();
        public int childSelectCount = 0;

        public static class ChildrenBean extends BaseExpandNode implements Serializable {
            public String am_pm;
            public String category;
            public String chapter_id;
            public int count;
            public int error_count;
            public String have;
            public String parent_title;
            public String primary_id;
            public String primary_p_id;
            public String question_bank_id;
            public int right_count;
            public String title;
            public String type = "";
            public int isSelected = 0;
            public int groupPosition = 0;
            private boolean isShowContinue = false;

            public String getAm_pm() {
                return this.am_pm;
            }

            public String getCategory() {
                return this.category;
            }

            public String getChapter_id() {
                return this.chapter_id;
            }

            @Override // com.chad.library.adapter.base.entity.node.BaseNode
            @Nullable
            public List<BaseNode> getChildNode() {
                return null;
            }

            public int getCount() {
                return this.count;
            }

            public int getError_count() {
                return this.error_count;
            }

            public int getGroupPosition() {
                return this.groupPosition;
            }

            public String getHave() {
                return this.have;
            }

            public int getIsSelected() {
                return this.isSelected;
            }

            public String getParent_title() {
                return this.parent_title;
            }

            public String getPrimary_id() {
                return this.primary_id;
            }

            public String getPrimary_p_id() {
                return this.primary_p_id;
            }

            public int getRight_count() {
                return this.right_count;
            }

            public String getTitle() {
                return this.title;
            }

            public String getType() {
                return this.type;
            }

            public boolean isShowContinue() {
                return this.isShowContinue;
            }

            public void setAm_pm(String am_pm) {
                this.am_pm = am_pm;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public void setChapter_id(String chapter_id) {
                this.chapter_id = chapter_id;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public void setError_count(int error_count) {
                this.error_count = error_count;
            }

            public void setGroupPosition(int groupPosition) {
                this.groupPosition = groupPosition;
            }

            public void setHave(String have) {
                this.have = have;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }

            public void setParent_title(String parent_title) {
                this.parent_title = parent_title;
            }

            public void setPrimary_id(String primary_id) {
                this.primary_id = primary_id;
            }

            public void setPrimary_p_id(String primary_p_id) {
                this.primary_p_id = primary_p_id;
            }

            public void setRight_count(int right_count) {
                this.right_count = right_count;
            }

            public void setShowContinue(boolean showContinue) {
                this.isShowContinue = showContinue;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getAm_pm() {
            return this.am_pm;
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public List<String> getChildIds() {
            return this.childIds;
        }

        @Override // com.chad.library.adapter.base.entity.node.BaseNode
        @Nullable
        public List<BaseNode> getChildNode() {
            List<ChildrenBean> list = this.children;
            if (list == null || list.isEmpty()) {
                return null;
            }
            return new ArrayList(this.children);
        }

        public int getChildSelectCount() {
            return this.childSelectCount;
        }

        public List<ChildrenBean> getChildren() {
            return this.children;
        }

        public int getCount() {
            return this.count;
        }

        public int getError_count() {
            return this.error_count;
        }

        public String getHave() {
            return this.have;
        }

        public int getIsSelected() {
            return this.isSelected;
        }

        public String getPass() {
            return this.pass;
        }

        public String getPrimary_id() {
            return this.primary_id;
        }

        public int getRight_count() {
            return this.right_count;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public int getViewType() {
            return this.viewType;
        }

        public boolean isShowContinue() {
            return this.isShowContinue;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public void setAm_pm(String am_pm) {
            this.am_pm = am_pm;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setChildIds(List<String> childIds) {
            this.childIds = childIds;
        }

        public void setChildSelectCount(int childSelectCount) {
            this.childSelectCount = childSelectCount;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setError_count(int error_count) {
            this.error_count = error_count;
        }

        public void setHave(String have) {
            this.have = have;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setPrimary_id(String primary_id) {
            this.primary_id = primary_id;
        }

        public void setRight_count(int right_count) {
            this.right_count = right_count;
        }

        public void setShowContinue(boolean showContinue) {
            this.isShowContinue = showContinue;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }
    }

    public static class String2IntAdapter implements JsonDeserializer<Integer> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.JsonDeserializer
        public Integer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String asString = jsonElement.getAsString();
            if (TextUtils.isEmpty(asString) || !asString.matches("^[0-9]+$")) {
                return 0;
            }
            return Integer.valueOf(Integer.parseInt(asString));
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getHave_year() {
        return this.have_year;
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

    public void setHave_year(String have_year) {
        this.have_year = have_year;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
