package com.louis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer.FromDecimalArguments;
import com.louis.dto.ItemsDto;
import com.louis.dto.OrderResult;
import com.louis.service.OrderService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Controller
public class PaymentController {
	
	private final String ngrokURL = "https://4598-114-137-167-116.ngrok-free.app";
	
	private int size = 3;
	
	@Autowired
	private OrderService orderService;
	
	public String payment(List<ItemsDto> itemList) {
		
		AllInOne allInOne = new AllInOne("");
		
		AioCheckOutALL aioCheckOutALL = new AioCheckOutALL();
		String itemName = "";
		String totalAmount = "";
		
		int sum = 0;
		for(int i = 0; i < itemList.size(); i++) {
			itemName += itemList.get(i).getLaptopName();
			itemName += "#";
			
			sum += itemList.get(i).getPrice();
			
			if(i == itemList.size() - 1) {
				itemName = itemName.substring(0, itemName.length() - 1);
			}
			if(itemList.size() == 1) {
				itemName = itemName.substring(0,itemName.length());
			}
			totalAmount = String.valueOf(sum);
		}
		
		// 訂單項目
		aioCheckOutALL.setItemName(itemName);
		// 總金額
		aioCheckOutALL.setTotalAmount(totalAmount);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String now = sf.format(new Date());
		// 訂單日期
		aioCheckOutALL.setMerchantTradeDate(now);
		
		String orderNo = orderService.getLastOrderId();
		if(orderNo == null) {
			orderNo = "order001";
		}
		
		String tradeNo = "order" + padZero(String.valueOf(orderNo), this.size);
		//訂單編號
		aioCheckOutALL.setMerchantTradeNo(tradeNo);
		// 額外付款資訊
		aioCheckOutALL.setNeedExtraPaidInfo("N");
		// 交易資訊
		aioCheckOutALL.setTradeDesc("test desc");
		
		aioCheckOutALL.setReturnURL(ngrokURL + "/results");
		
		aioCheckOutALL.setOrderResultURL(ngrokURL + "/back_to_home");
		
		String form = allInOne.aioCheckOut(aioCheckOutALL, null);
		
		return form;
	}
	
	@ResponseBody
	@PostMapping("/results")
	public String getOrderResult(OrderResult result) {
		System.out.println("綠界回傳資料: ");
		System.out.println(result);
		// 返回綠界科技訊息
		return "1|OK";
	}
	
	@PostMapping("/back_to_home")
	public String toHome(OrderResult result,Model model) {
		
		model.addAttribute("CheckMacValue", result.getCheckMacValue());
		model.addAttribute("MerchantID", result.getMerchantID());
		model.addAttribute("MerchantTradeNo", result.getMerchantTradeNo());
		model.addAttribute("PaymentDate", result.getPaymentDate());
		model.addAttribute("PaymentType", result.getPaymentType());
		model.addAttribute("PaymentTypeChargeFee", result.getPaymentTypeChargeFee());
		model.addAttribute("RtnCode", result.getRtnCode());
		model.addAttribute("RtnMsg", result.getRtnMsg());
		model.addAttribute("SimulatePaid", result.getSimulatePaid());
		model.addAttribute("TradeAmt", result.getTradeAmt());
		model.addAttribute("TradeDate", result.getTradeDate());
		model.addAttribute("TradeNo", result.getTradeNo());
		// 待測試
		return "redirect:/home";
	}

	private static String padZero(String str, int length) {
		
		StringBuilder sb = new StringBuilder();
		
		if(str.length() >= length) {
			return str;
		}else {
			for(int i = 0; i < length - str.length(); i++) {
				sb.append("0");
			}
			sb.append(str);
			return sb.toString();
		}
	}
}
