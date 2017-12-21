package org.posmall.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by USER on 2017-12-21.
 */
@Data
public class TbRvasVo {
    int trSeq;

    String orgBank;

    String orgCd;

    String vacctNo;

    String statCd;

    String custCd;

    String custNm;

    String errorCd;

    String trDate;

    String trTime;

    int trAmt;

    String inBankCd;

    String inBankBranch;

    String inName;

    String entryDate;

    String entryIdno;

    String erpProcYn;

    String erpProcDt;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
