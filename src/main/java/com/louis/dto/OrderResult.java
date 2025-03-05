package com.louis.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class OrderResult {
	private String CheckMacValue;
	private String MerchantID;
	private String MerchantTradeNo;
	private String PaymentDate;
	private String PaymentType;
	private Number PaymentTypeChargeFee;
	private Integer RtnCode;
	private String RtnMsg;
	private Integer SimulatePaid;
	private Integer TradeAmt;
	private String TradeDate;
	private String TradeNo;
	
	public String getCheckMacValue() {
		return CheckMacValue;
	}
	public void setCheckMacValue(String checkMacValue) {
		CheckMacValue = checkMacValue;
	}
	public String getMerchantID() {
		return MerchantID;
	}
	public void setMerchantID(String merchantID) {
		MerchantID = merchantID;
	}
	public String getMerchantTradeNo() {
		return MerchantTradeNo;
	}
	public void setMerchantTradeNo(String merchantTradeNo) {
		MerchantTradeNo = merchantTradeNo;
	}
	public String getPaymentDate() {
		return PaymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		PaymentDate = paymentDate;
	}
	public String getPaymentType() {
		return PaymentType;
	}
	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}
	public Number getPaymentTypeChargeFee() {
		return PaymentTypeChargeFee;
	}
	public void setPaymentTypeChargeFee(Number paymentTypeChargeFee) {
		PaymentTypeChargeFee = paymentTypeChargeFee;
	}
	public Integer getRtnCode() {
		return RtnCode;
	}
	public void setRtnCode(Integer rtnCode) {
		RtnCode = rtnCode;
	}
	public String getRtnMsg() {
		return RtnMsg;
	}
	public void setRtnMsg(String rtnMsg) {
		RtnMsg = rtnMsg;
	}
	public Integer getSimulatePaid() {
		return SimulatePaid;
	}
	public void setSimulatePaid(Integer simulatePaid) {
		SimulatePaid = simulatePaid;
	}
	public Integer getTradeAmt() {
		return TradeAmt;
	}
	public void setTradeAmt(Integer tradeAmt) {
		TradeAmt = tradeAmt;
	}
	public String getTradeDate() {
		return TradeDate;
	}
	public void setTradeDate(String tradeDate) {
		TradeDate = tradeDate;
	}
	public String getTradeNo() {
		return TradeNo;
	}
	public void setTradeNo(String tradeNo) {
		TradeNo = tradeNo;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CheckMacValue : " + this.CheckMacValue + "\n");
		sb.append("MerchantID : " + this.MerchantID + "\n");
		sb.append("MerchantTradeNo : " + this.MerchantTradeNo + "\n");
		sb.append("PaymentDate : " + this.PaymentDate + "\n");
		sb.append("PaymentType : " + this.PaymentType + "\n");
		sb.append("PaymentTypeChargeFee : " + this.PaymentTypeChargeFee + "\n");
		sb.append("RtnCode : " + this.RtnCode + "\n");
		sb.append("RtnMsg : " + this.RtnMsg + "\n");
		sb.append("SimulatePaid : " + this.SimulatePaid + "\n");
		sb.append("TradeAmt : " + this.TradeAmt + "\n");
		sb.append("TradeDate : " + this.TradeDate + "\n");
		sb.append("TradeNo : " + this.TradeNo + "\n");
		return sb.toString();
	}

	
	
}
