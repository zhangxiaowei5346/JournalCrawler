package ai.zhuanzhi.contentextractor;

import org.bson.Document;

import java.util.List;

public class NSFCFund {
    private String fundName, fundNo, fundSubject, fundType,
            fundInCharge, fundSupportingUnit, fundApprovedYear, fundStartEndTime,
            fundApprovedAmount, fundAbstract;
    private List<Document> fundRelated;

    @Override
    public String toString() {
        return "NSFCFund{" +
                "fundName='" + fundName + '\'' +
                ", fundNo='" + fundNo + '\'' +
                ", fundSubject='" + fundSubject + '\'' +
                ", fundType='" + fundType + '\'' +
                ", fundInCharge='" + fundInCharge + '\'' +
                ", fundSupportingUnit='" + fundSupportingUnit + '\'' +
                ", fundApprovedYear='" + fundApprovedYear + '\'' +
                ", fundStartEndTime='" + fundStartEndTime + '\'' +
                ", fundApprovedAmount='" + fundApprovedAmount + '\'' +
                ", fundAbstract='" + fundAbstract + '\'' +
                ", fundRelated=" + fundRelated +
                '}';
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundNo() {
        return fundNo;
    }

    public void setFundNo(String fundNo) {
        this.fundNo = fundNo;
    }

    public String getFundSubject() {
        return fundSubject;
    }

    public void setFundSubject(String fundSubject) {
        this.fundSubject = fundSubject;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getFundInCharge() {
        return fundInCharge;
    }

    public void setFundInCharge(String fundInCharge) {
        this.fundInCharge = fundInCharge;
    }

    public String getFundSupportingUnit() {
        return fundSupportingUnit;
    }

    public void setFundSupportingUnit(String fundSupportingUnit) {
        this.fundSupportingUnit = fundSupportingUnit;
    }

    public String getfundApprovedYear() {
        return fundApprovedYear;
    }

    public void setfundApprovedYear(String fundApprovedYear) {
        this.fundApprovedYear = fundApprovedYear;
    }

    public String getFundStartEndTime() {
        return fundStartEndTime;
    }

    public void setFundStartEndTime(String fundStartEndTime) {
        this.fundStartEndTime = fundStartEndTime;
    }

    public String getFundApprovedAmount() {
        return fundApprovedAmount;
    }

    public void setFundApprovedAmount(String fundApprovedAmount) {
        this.fundApprovedAmount = fundApprovedAmount;
    }

    public String getFundAbstract() {
        return fundAbstract;
    }

    public void setFundAbstract(String fundAbstract) {
        this.fundAbstract = fundAbstract;
    }

    public List<Document> getFundRelated() {
        return fundRelated;
    }

    public void setFundRelated(List<Document> fundRelated) {
        this.fundRelated = fundRelated;
    }

    public NSFCFund(String fundName, String fundNo, String fundSubject, String fundType, String fundInCharge, String fundSupportingUnit, String fundApprovedYear, String fundStartEndTime, String fundApprovedAmount, String fundAbstract, List<Document> fundRelated) {
        this.fundName = fundName;
        this.fundNo = fundNo;
        this.fundSubject = fundSubject;
        this.fundType = fundType;
        this.fundInCharge = fundInCharge;
        this.fundSupportingUnit = fundSupportingUnit;
        this.fundApprovedYear = fundApprovedYear;
        this.fundStartEndTime = fundStartEndTime;
        this.fundApprovedAmount = fundApprovedAmount;
        this.fundAbstract = fundAbstract;
        this.fundRelated = fundRelated;
    }
}
