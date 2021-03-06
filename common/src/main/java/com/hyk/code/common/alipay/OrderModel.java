package com.hyk.code.common.alipay;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/3 11:01
 * @Description:
 */
public class OrderModel {

   private String  body;//	String	否	128	对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。	Iphone6 16G
    private String  subject;//String	是	256	商品的标题/交易标题/订单标题/订单关键字等。	大乐透
    private String out_trade_no;//	String	是	64	商户网站唯一订单号	70501111111S001111119
    private String timeout_express;//	String	否	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。	90m
    private String total_amount;//	String	是	9	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	9.00
    private String product_code	;//String	是	64	销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY	QUICK_MSECURITY_PAY
    private String goods_type;//	String	否	2	商品主类型：0—虚拟类商品，1—实物类商品 注：虚拟类商品不支持使用花呗渠道	0
    private String passback_params;//	String	否	512	公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝	merchantBizType%3d3C%26merchantBizNo%3d2016010101111
    private String promo_params;//	String	否	512	优惠参数 注：仅与支付宝协商后可用	{"storeIdType":"1"}
    private String extend_params;//	String	否		业务扩展参数，详见下面的“业务扩展参数说明”	{"sys_service_provider_id":"2088511833207846"}
    private String enable_pay_channels;//	String	否	128	可用渠道，用户只能在指定渠道范围内支付    当有多个渠道时用“,”分隔     注：与disable_pay_channels互斥	pcredit,moneyFund,debitCardExpress
    private String  disable_pay_channels;//	String	否	128	禁用渠道，用户不可用指定渠道支付    当有多个渠道时用“,”分隔     注：与enable_pay_channels互斥	pcredit,moneyFund,debitCardExpress
    private String  store_id;//	String	否	32	商户门店编号。该参数用于请求参数中以区分各门店，非必传项。	NJ_001
    private String ext_user_info;//	ExtUserInfo 否		外部指定买家，详见外部用户ExtUserInfo参数说明

    //业务扩展参数说明

    private String  sys_service_provider_id;//	String	否	64	系统商编号，该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID	2088511833207846
    private String  needBuyerRealnamed;//	String	否	1	是否发起实名校验     T：发起     F：不发起	T
    private String TRANS_MEMO;//	String	否	128	账务备注     注：该字段显示在离线账单的账务备注中	促销
    private String hb_fq_num;//	String	否	5	花呗分期数（目前仅支持3、6、12）    注：使用该参数需要仔细阅读“花呗分期接入文档”	3
    private String hb_fq_seller_percent;//	String	否	3	卖家承担收费比例，商家承担手续费传入100，用户承担手续费传入0，仅支持传入100、0两种，其他比例暂不支持     注：使用该参数需要仔细阅读“花呗分期接入文档”	100

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getPassback_params() {
        return passback_params;
    }

    public void setPassback_params(String passback_params) {
        this.passback_params = passback_params;
    }

    public String getPromo_params() {
        return promo_params;
    }

    public void setPromo_params(String promo_params) {
        this.promo_params = promo_params;
    }

    public String getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(String extend_params) {
        this.extend_params = extend_params;
    }

    public String getEnable_pay_channels() {
        return enable_pay_channels;
    }

    public void setEnable_pay_channels(String enable_pay_channels) {
        this.enable_pay_channels = enable_pay_channels;
    }

    public String getDisable_pay_channels() {
        return disable_pay_channels;
    }

    public void setDisable_pay_channels(String disable_pay_channels) {
        this.disable_pay_channels = disable_pay_channels;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getExt_user_info() {
        return ext_user_info;
    }

    public void setExt_user_info(String ext_user_info) {
        this.ext_user_info = ext_user_info;
    }

    public String getSys_service_provider_id() {
        return sys_service_provider_id;
    }

    public void setSys_service_provider_id(String sys_service_provider_id) {
        this.sys_service_provider_id = sys_service_provider_id;
    }

    public String getNeedBuyerRealnamed() {
        return needBuyerRealnamed;
    }

    public void setNeedBuyerRealnamed(String needBuyerRealnamed) {
        this.needBuyerRealnamed = needBuyerRealnamed;
    }

    public String getTRANS_MEMO() {
        return TRANS_MEMO;
    }

    public void setTRANS_MEMO(String TRANS_MEMO) {
        this.TRANS_MEMO = TRANS_MEMO;
    }

    public String getHb_fq_num() {
        return hb_fq_num;
    }

    public void setHb_fq_num(String hb_fq_num) {
        this.hb_fq_num = hb_fq_num;
    }

    public String getHb_fq_seller_percent() {
        return hb_fq_seller_percent;
    }

    public void setHb_fq_seller_percent(String hb_fq_seller_percent) {
        this.hb_fq_seller_percent = hb_fq_seller_percent;
    }

    @Override
    public String toString() {
        return "{" +
                "body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", timeout_express='" + timeout_express + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", product_code='" + product_code + '\'' +
                ", goods_type='" + goods_type + '\'' +
                ", passback_params='" + passback_params + '\'' +
                ", promo_params='" + promo_params + '\'' +
                ", extend_params='" + extend_params + '\'' +
                ", enable_pay_channels='" + enable_pay_channels + '\'' +
                ", disable_pay_channels='" + disable_pay_channels + '\'' +
                ", store_id='" + store_id + '\'' +
                ", ext_user_info='" + ext_user_info + '\'' +
                ", sys_service_provider_id='" + sys_service_provider_id + '\'' +
                ", needBuyerRealnamed='" + needBuyerRealnamed + '\'' +
                ", TRANS_MEMO='" + TRANS_MEMO + '\'' +
                ", hb_fq_num='" + hb_fq_num + '\'' +
                ", hb_fq_seller_percent='" + hb_fq_seller_percent + '\'' +
                '}';
    }
}
