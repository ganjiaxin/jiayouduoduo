/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商城商品管理Entity
 * @author wyy
 * @version 2018-12-21
 */
@ApiModel(value = "商城商品管理")
public class HykMallGoods extends DataEntity<HykMallGoods> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(required=false,value="商品模块 字典 mall_category")
	private String category;		// 商品模块
	@ApiModelProperty(required=false,value="商品类型 字典 mall_goodsType")
	private String goodsType;		// 商品类型 0虚拟商品 1实物商品
	@ApiModelProperty(required=false,value="商品名称")
	private String goodsName;		// 商品名称
	@ApiModelProperty(required=false,value="商品副标题")
	private String goodsTitle;	// 商品副标题
	@ApiModelProperty(required=false,value="商品价格")
	private BigDecimal prices;		// 商品价格
	@ApiModelProperty(required=false,value="商品价值")
	private BigDecimal money;		// 商品价值
	@ApiModelProperty(required=false,value="商品库存")
	private Integer stock;		// 商品库存
	@ApiModelProperty(required=false,value="商品图片")
	private String img;		// 商品图片
	@ApiModelProperty(required=false,value="商品标签 字典mall_labels")
	private String labels;		// 商品标签 0热卖 1限时
	@ApiModelProperty(required=false,value="商品介绍")
	private String introcture;// 商品介绍
	@ApiModelProperty(required=false,value="商品购买说明 ")
	private String mess;		// 商品购买说明
	@ApiModelProperty(required=false,value="商品状态 字典 mall_status")
	private String status;		// 商品状态 0在售 1停售
	@ApiModelProperty(required=false,value="备注")
	private String remark;		// 备注
	@ApiModelProperty(required=false,value="创建日期")
	private Date createDate;//创建日期
	private BigDecimal freight;		// 运费
	private String f2;		// f2
	private String ishot;//是否热卖 字典字段 mallGoods_saleStatus
	private String viewpic;//预览图片



	private String categoryStr;//商品模块字典类型
	private String goodsTypeStr;//商品类型展示
	private String labelsStr;// 商品标签展示字段
	private String createDateStr;
	public String getIshot() {

		return ishot;
	}

	public void setIshot(String ishot) {
		this.ishot = ishot;
	}

	public String getViewpic() {
		return viewpic;
	}

	public void setViewpic(String viewpic) {
		this.viewpic = viewpic;
	}

	public String getCategoryStr() {
		return categoryStr;
	}

	public void setCategoryStr(String categoryStr) {
		this.categoryStr = categoryStr;
	}

	public String getGoodsTypeStr() {
		return goodsTypeStr;
	}

	public void setGoodsTypeStr(String goodsTypeStr) {
		this.goodsTypeStr = goodsTypeStr;
	}

	public String getLabelsStr() {
		return labelsStr;
	}

	public void setLabelsStr(String labelsStr) {
		this.labelsStr = labelsStr;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public HykMallGoods() {
		super();
	}

	public HykMallGoods(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商品模块长度必须介于 1 和 64 之间")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Length(min=1, max=64, message="商品类型长度必须介于 1 和 64 之间")
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	@Length(min=1, max=64, message="商品名称长度必须介于 1 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="商品副标题长度必须介于 0 和 64 之间")
	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	
	@NotNull(message="商品价格不能为空")
	public BigDecimal getPrices() {
		return prices;
	}

	public void setPrices(BigDecimal prices) {
		this.prices = prices;
	}
	
	@NotNull(message="商品价值不能为空")
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@NotNull(message="商品库存不能为空")
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@Length(min=0, max=100, message="商品图片长度必须介于 0 和 100 之间")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=2, message="商品标签长度必须介于 0 和 2 之间")
	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}
	
	public String getIntrocture() {
		return introcture;
	}

	public void setIntrocture(String introcture) {
		this.introcture = introcture;
	}
	
	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}
	
	@Length(min=1, max=2, message="商品状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	@Length(min=0, max=2, message="f2长度必须介于 0 和 2 之间")
	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}
	
}