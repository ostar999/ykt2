package com.psychiatrygarden.bean;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseSurveyBean {
    private String id;

    @JsonAdapter(String2ArrAdapter.class)
    private List<String> option;
    private boolean select;
    private String selectOption;
    private String title;
    private String type;

    public static class String2ArrAdapter implements JsonDeserializer<List<String>> {
        @Override // com.google.gson.JsonDeserializer
        public List<String> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonArray()) {
                return new ArrayList();
            }
            ArrayList arrayList = new ArrayList();
            JsonArray asJsonArray = jsonElement.getAsJsonArray();
            int size = asJsonArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(String.valueOf(asJsonArray.get(i2)).replace("\"", ""));
            }
            return arrayList;
        }
    }

    public String getId() {
        return this.id;
    }

    public List<String> getOption() {
        return this.option;
    }

    public String getSelectOption() {
        return this.selectOption;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setSelectOption(String selectOption) {
        this.selectOption = selectOption;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }
}
