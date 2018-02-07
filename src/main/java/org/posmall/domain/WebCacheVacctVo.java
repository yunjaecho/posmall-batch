package org.posmall.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by USER on 2017-11-30.
 */
@Data
public class WebCacheVacctVo {
    private String orgBank;

    private String orgCd;

    private String vacctNo;

    private String trDate;

    private String trSeq;

    private String inGb;

    private String statCd;

    private String detStatCd;

    private double  payAmt;

    private String payFromDate;

    private String payToDate;

    private String payToTime;

    private String custCd;

    private String custNm;

    private String errorCd;

    private String trTime;

    private double trAmt;

    private String inBankCd;

    private String inBankBranch;

    private String inName;

    private String entryDate;

    private String entryIdno;

    private String erpProcYn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
