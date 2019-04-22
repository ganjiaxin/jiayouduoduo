/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import org.hibernate.validator.constraints.Length;

import com.hyk.code.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 商品管理Entity
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykGoods extends DataEntity<HykGoods> {
	
	private static final long serialVersionUID = 1L;
	private String goodsName;		// 商品名称
	private BigDecimal discount;		// 商品折扣
	private BigDecimal activityDiscount;		// 商品活动折扣
	private Long cycle;		// 商品周期月
	private Long stock;		// 库存
	private String goodsType;		// 商品类型 0加油套餐 1即时充值
	private String status;		// 商品状态 0停用1启用
	private BigDecimal prices;		// 商品价格
	private BigDecimal val;		// 商品价值
	private BigDecimal discountMoney;		// 折扣价格
	private String remark;		// 备注
	private String f1;		// f1
	private String goodsImg;		//商品图片
	private String label;		// 标签  0限时1热卖2性价比高

	private String goodsTypeStr;
	private String statusStr;

	private BigDecimal adviceMoney1;//建议金额1
	private BigDecimal adviceMoney2;//建议金额2

	private String  istop;//是否首页展示

	public String getIstop() {
		return istop;
	}

	public void setIstop(String istop) {
		this.istop = istop;
	}

	public BigDecimal getAdviceMoney1() {
		return adviceMoney1;
	}

	public void setAdviceMoney1(BigDecimal adviceMoney1) {
		this.adviceMoney1 = adviceMoney1;
	}

	public BigDecimal getAdviceMoney2() {
		return adviceMoney2;
	}

	public void setAdviceMoney2(BigDecimal adviceMoney2) {
		this.adviceMoney2 = adviceMoney2;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getGoodsTypeStr() {
		return goodsTypeStr;
	}

	public void setGoodsTypeStr(String goodsTypeStr) {
		this.goodsTypeStr = goodsTypeStr;
	}

	public HykGoods() {
		super();
	}

	public HykGoods(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商品名称长度必须介于 1 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	public BigDecimal getActivityDiscount() {
		return activityDiscount;
	}

	public void setActivityDiscount(BigDecimal activityDiscount) {
		this.activityDiscount = activityDiscount;
	}
	
	public Long getCycle() {
		return cycle;
	}

	public void setCycle(Long cycle) {
		this.cycle = cycle;
	}
	
	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}
	
	@Length(min=0, max=64, message="商品类型长度必须介于 0 和 64 之间")
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	@Length(min=0, max=64, message="商品状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getPrices() {
		return prices;
	}

	public void setPrices(BigDecimal prices) {
		this.prices = prices;
	}
	
	public BigDecimal getVal() {
		return val;
	}

	public void setVal(BigDecimal val) {
		this.val = val;
	}
	
	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=64, message="f1长度必须介于 0 和 64 之间")
	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	
	public String getGoodsImg() {
		return goodsImg;
	}


	@Length(min=0, max=64, message="label长度必须介于 0 和 64 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}