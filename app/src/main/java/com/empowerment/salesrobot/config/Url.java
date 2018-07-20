package com.empowerment.salesrobot.config;


/**
 * 接口地址
 */
public class Url {


    //服务器地址
    public static final String HTTP = "http://192.168.1.6:8081/robot";
//    public static final String HTTP = "http://192.168.1.6:8080/robot-manager-web";

    //登录
    public static final String LOGIN_ = HTTP + "/app/sale/login?";

    //获取短信
    public static final String SMS = HTTP +"/app/sale/sendCode";

    //短信验证
    public static final String VERIFICATION_SMS = "http://api.sms.ronghub.com/verifyCode/json";

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

    //训练—提交图文
    public static final String ADD_TRAIN_RECORD_ABOUT_VIDEO = HTTP + "/app/sale/addTrainRecordAboutVideo?";


    //维修保养室
    public static final String ROBOT_MAINTAIN = "http://192.168.1.6:8081/app/train/robotMaintain?";

    //保险理赔室
    public static final String ROBOT_INSURANCE = "http://192.168.1.6:8081/app/train/robotInsurance?";

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

    //客户资料**查询
    public static final String CUSTOMER = HTTP + "/app/customer/customerList?";

    //添加今日客户
    public static final String SUBMIT_CUSTOMER = HTTP;

    //客户资料修改
    public static final String EDIT_CUSTOMER = HTTP + "/app/customer/updateCustomer?";

    //首页
    public static final String INDEX = HTTP + "/app/sale/index?";

    //客户 维修 保养 保险
    public static final String MPBLIST = HTTP + "/app/customer/mpbList?";

    //添加VIP客户
    public static final String ADD_CUSTOMER = HTTP + "/app/customer/addCustomer?";

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
    public static final String VIDEO_URL="http://123.125.39.231/play.videocache.lecloud.com/187/28/92/letv-gug/14/ver_00_22-1051581402-avc-1507856-aac-96000-117151-23680505-2e0b3774490e51ac469db4313025b877-1466497857703.mp4?crypt=13aa7f2e25900&b=259&nlh=4096&nlt=60&bf=8000&p2p=1&video_type=mp4&termid=0&tss=no&platid=3&splatid=345&its=0&qos=3&fcheck=0&amltag=5&mltag=5&proxy=3086463625,467476870&uid=3722289275.rp&keyitem=GOw_33YJAAbXYE-cnQwpfLlv_b2zAkYctFVqe5bsXQpaGNn3T1-vhw..&ntm=1529559600&nkey=b16cff671373077cbe213a622c700f91&nkey2=4f312022b4e7da5b5a32ff2a5c220265&auth_key=1529559600-1-0-3-345-8f54840f90bffd363433c96565a50985&geo=CN-1-6-2&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&playid=0&vtype=21&cvid=2026135183914&payff=0&sign=mb&dname=mobile&tag=mobile&xformat=super&uidx=0&errc=424&gn=3334&ndtype=0&vrtmcd=102&buss=5&cips=221.221.160.123";

}
