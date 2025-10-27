package com.psychiatrygarden.activity.vip.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class VipTipsBean implements Serializable {
    private String code;
    private DataDTO data;
    private String message;
    private String server_time;

    public static class DataDTO implements Serializable {
        private String is_vip;
        private List<String> shop_images;
        private String show_eshop;
        private VipDTO svip;
        private VipDTO vip;

        public static class SvipDTO implements Serializable {
            private int is_vip;
            private String vip_deadline;
            private int vip_due_soon;

            public int getIs_vip() {
                return this.is_vip;
            }

            public String getVip_deadline() {
                return this.vip_deadline;
            }

            public int getVip_due_soon() {
                return this.vip_due_soon;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public void setVip_deadline(String vip_deadline) {
                this.vip_deadline = vip_deadline;
            }

            public void setVip_due_soon(int vip_due_soon) {
                this.vip_due_soon = vip_due_soon;
            }
        }

        public static class VipDTO implements Serializable {
            private String available;
            private int is_vip;
            private String simple_declaration = "";
            private String vip_deadline;
            private int vip_due_soon;

            public String getAvailable() {
                return this.available;
            }

            public int getIs_vip() {
                return this.is_vip;
            }

            public String getSimple_declaration() {
                return this.simple_declaration;
            }

            public String getVip_deadline() {
                return this.vip_deadline;
            }

            public int getVip_due_soon() {
                return this.vip_due_soon;
            }

            public void setAvailable(String available) {
                this.available = available;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public void setSimple_declaration(String simple_declaration) {
                this.simple_declaration = simple_declaration;
            }

            public void setVip_deadline(String vip_deadline) {
                this.vip_deadline = vip_deadline;
            }

            public void setVip_due_soon(int vip_due_soon) {
                this.vip_due_soon = vip_due_soon;
            }
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public List<String> getShop_images() {
            return this.shop_images;
        }

        public String getShow_eshop() {
            return this.show_eshop;
        }

        public VipDTO getSvip() {
            return this.svip;
        }

        public VipDTO getVip() {
            return this.vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setShop_images(List<String> shop_images) {
            this.shop_images = shop_images;
        }

        public void setShow_eshop(String show_eshop) {
            this.show_eshop = show_eshop;
        }

        public void setSvip(VipDTO svip) {
            this.svip = svip;
        }

        public void setVip(VipDTO vip) {
            this.vip = vip;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataDTO getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
