package com.empowerment.salesrobot.config;


/**
 * 接口地址
 */
public class Url {


    //服务器地址
    public static final String HTTP = "http://101.200.60.12:8080/robot-manager-web";//网络

//    public static final String HTTP = "http://192.168.1.6:8080/robot-manager-web";//本地

    //登录
    public static final String LOGIN = HTTP + "/app/sale/login?";

    //获取短信
    public static final String SMS = HTTP +"/app/sale/sendCode";

    //完善资料
    public static final String SALE = HTTP + "/app/sale/prefectOrUpdateSale?";

    //现场记录列表
    public static final String RECORD_LIST = HTTP + "/app/sale/recordList?";

    //现场记录详情
    public static final String RECORD_DETAIL = HTTP + "/app/sale/recordDetail?";

    //训练—提问
    public static final String TRAIN_ROBOT = HTTP + "/app/sale/trainRobot?";

    //训练—结束训练
    public static final String FINISH_TRAIN = HTTP + "/app/sale/finishTrain?";

    //训练—提交图文
    public static final String ADD_TRAIN_RECORD_ABOUT_PIC = HTTP + "/app/sale/addTrainRecordAboutPic?";

    //训练—提交视频
    public static final String ADD_TRAIN_RECORD_ABOUT_VIDEO = HTTP + "/app/sale/addTrainRecordAboutVideo?";

    //训练—替换提交的图片
    public static final String MODIFY_PIC = HTTP + "/app/sale/modifyPic?";

    //维修保养室
    public static final String ROBOT_MAINTAIN = HTTP + "/app/train/robotMaintain?";

    //保险理赔室
    public static final String ROBOT_INSURANCE = HTTP + "/app/train/robotInsurance?";

    //销售员心得
    public static final String SALES_MANS = HTTP + "/app/sale/experience?";

    //删除心得
    public static final String DELET_SALE_MANS = HTTP + "/app/sale/delExperienceList?";

    //添加心得
    public static final String ADD_EXPERLIST = HTTP + "/app/sale/addExperienceList?";

    //修改头像
    public static final String FILE_IMG = HTTP + "/app/sale/changeImage?";

    //待办事宜
    public static final String AFFAIRS = HTTP +"/app/sale/agentList?";

    //待办事宜阅读或者完结
    public static final String READ_OR_FINISH = HTTP +"/app/sale/readOrFinish?";

    //添加个人待办事宜
    public static final String ADD_PERSONAL_AGENCY = HTTP +"/app/sale/addPersonalAgent?";

    //客户数量
    public static final String CUSTOMER_COUNT = HTTP +"/app/customer/customerCount?";

    //客户资料**查询
    public static final String CUSTOMER = HTTP + "/app/customer/customerList?";

    //添加客户(今日客户和VIP客和预成交客户)
    public static final String SUBMIT_CUSTOMER = HTTP + "/app/customer/addCustomer?";

    //客户资料修改
    public static final String EDIT_CUSTOMER = HTTP + "/app/customer/updateCustomer?";

    //首页
    public static final String INDEX = HTTP + "/app/sale/index?";

    //客户 维修 保养 保险
    public static final String MPBLIST = HTTP + "/app/customer/mpbList?";

    //获取VIP*有意向客户
    public static final String VIP_YCJ = HTTP + "/app/customer/vipOrYuChengJiaoCustomer?";

    //所有销售员心得
    public static final String EXPERIENCELIST = HTTP + "/app/sale/experienceList?";

    //产品一览
    public static final String CARLIST = HTTP + "/app/brand/carList?";

    //产品销售
    public static final String PRODUCTSALE = HTTP + "/app/sale/productSale?";

    //视频列表
    public static final String TRAINVIDEOLIST = HTTP + "/app/train/tvideoList?";

    //培训视频
    public static final String TRAINVIDEO = HTTP + "/app/train/trainVideo?";

    //文档列表
    public static final String TRAINDOCLIST = HTTP + "/app/train/tdocList?";

    //培训文档
    public static final String TRAINDOC = HTTP + "/app/train/trainDoc?";

    //新品买点
    public static final String BUYPOINT = HTTP + "/app/brand/buyPoint?";

    //新品详情
    public static final String BUYPOINTDETAIL = HTTP + "/app/brand/buyPointDetail?";

    //视频测试地址  非解析
    public static final String THE_SERVER_URL = HTTP +"/pai/car/version";
}
