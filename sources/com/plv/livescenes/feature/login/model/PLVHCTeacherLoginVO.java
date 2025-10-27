package com.plv.livescenes.feature.login.model;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginResultVO;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVHCTeacherLoginVO {
    public static final int STATUS_LOGIN_FAIL = -1;
    public static final int STATUS_LOGIN_SELECT_COMPANY = 1;
    public static final int STATUS_LOGIN_SUCCESS = 0;
    private List<PLVHCTeacherLoginResultVO.CompanyVO> companyList;
    private Integer serverResponseErrorCode;
    private String serverResponseErrorDesc;
    private Integer status;
    private PLVHCTeacherLoginResultVO.DataVO successData;

    public static int getStatusLoginFail() {
        return -1;
    }

    public static int getStatusLoginSelectCompany() {
        return 1;
    }

    public static int getStatusLoginSuccess() {
        return 0;
    }

    public List<PLVHCTeacherLoginResultVO.CompanyVO> getCompanyList() {
        return this.companyList;
    }

    public Integer getServerResponseErrorCode() {
        return this.serverResponseErrorCode;
    }

    public String getServerResponseErrorDesc() {
        return this.serverResponseErrorDesc;
    }

    public Integer getStatus() {
        return this.status;
    }

    public PLVHCTeacherLoginResultVO.DataVO getSuccessData() {
        return this.successData;
    }

    public void setCompanyList(List<PLVHCTeacherLoginResultVO.CompanyVO> list) {
        this.companyList = list;
    }

    public void setServerResponseErrorCode(Integer num) {
        this.serverResponseErrorCode = num;
    }

    public void setServerResponseErrorDesc(String str) {
        this.serverResponseErrorDesc = str;
    }

    public void setStatus(Integer num) {
        this.status = num;
    }

    public void setSuccessData(PLVHCTeacherLoginResultVO.DataVO dataVO) {
        this.successData = dataVO;
    }

    public String toString() {
        return "PLVHCTeacherLoginVO{status=" + this.status + ", successData=" + this.successData + ", companyList=" + this.companyList + ", serverResponseErrorCode=" + this.serverResponseErrorCode + ", serverResponseErrorDesc='" + this.serverResponseErrorDesc + CharPool.SINGLE_QUOTE + '}';
    }
}
