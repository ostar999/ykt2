package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class ComputerRuleBean {
    private String code;
    private RuleInfoBean data;
    private String message;

    public class RuleInfoBean {
        private String instruction;
        private String promise;
        private String rules;

        public RuleInfoBean() {
        }

        public String getInstruction() {
            return this.instruction;
        }

        public String getPromise() {
            return this.promise;
        }

        public String getRules() {
            return this.rules;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public void setPromise(String promise) {
            this.promise = promise;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }
    }

    public String getCode() {
        return this.code;
    }

    public RuleInfoBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(RuleInfoBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
