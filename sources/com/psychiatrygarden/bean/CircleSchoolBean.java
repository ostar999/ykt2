package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CircleSchoolBean {
    private Long id;
    private String name;
    private String province_id;
    private String status;
    private String type;
    private String updated;

    public CircleSchoolBean() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getProvince_id() {
        return this.province_id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public CircleSchoolBean(Long id) {
        this.id = id;
    }

    public CircleSchoolBean(Long id, String name, String province_id, String type, String status, String updated) {
        this.id = id;
        this.name = name;
        this.province_id = province_id;
        this.type = type;
        this.status = status;
        this.updated = updated;
    }
}
