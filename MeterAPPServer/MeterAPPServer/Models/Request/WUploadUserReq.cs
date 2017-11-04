using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Request
{
    public class WUploadUserReq : BaseRequest
    {
        //public string cbUserName { get; set; }//抄表员
        //public string cbUserId { get; set; }//抄表员ID
        //public int dataYear { get; set; }//数据Year
        //public int dataMonth { get; set; }//数据月份
        //public string noteNo { get; set; }//表本号
        //public List<WUserItem> userItems { get; set; }//
        //{"readMeterRecordId":"20160131041818",
        //"waterMeterNo":"0000020801",
        //"lastNumberYearMonth":"201601",
        //"waterMeterLastNumber":682,
        //"waterMeterEndNumber":683,
        //"totalNumber":1,"
        //avePrice":4.3,"
        //waterTotalCharge":4.3,"
        //extraChargePrice1":0.8,"
        //extraCharge1":0.8,"
        //extraChargePrice2":0,"
        //extraCharge2":0,"
        //extraTotalCharge":0.8,"
        //trapezoidPrice":"0-999999:4.3","
        //extraCharge":"F1:0.8:1","
        //totalCharge":5.1,"
        //OVERDUEMONEY":0,"
        //WATERFIXVALUE":0,"
        //readMeterRecordYear":2016,"
        //readMeterRecordMonth":3,"
        //readMeterRecordDate":"2016/3/3 11:34:59","
        //waterMeterPositionName":"表1","
        //loginId":"0003","
        //USERNAME":"刘亚军","
        //checkState":"1","
        //checkDateTime":"2016/3/25 8:32:31","
        //checker":"系统管理员","
        //chargeState":"3","
        //chargeID":"201603250000SF002430","
        //waterUserId":"00000208","
        //waterUserNO":"00000208","
        //waterUserName":"赵东海","
        //waterPhone":null,"
        //isSummaryMeter":"1","
        //waterMeterParentId":null,"
        //ordernumber":1,"
        //WATERMETERNUMBERCHANGESTATE":"0"}
        public string readMeterRecordId { get; set; }//上传主键
        public int waterMeterEndNumber { get; set; }//本月读数
        public int totalNumber { get; set; }//本月水量；三种情况：1、正常（本月读数-上期表底数）；2、定量用水；3、总表（本月读数-上期表底数-所有分表水量）
        public decimal avePrice { get; set; } //单价
        public decimal waterTotalCharge { get; set; }//水费
        public decimal extraChargePrice1 { get; set; }//污水处理费单价
        public decimal extraCharge1 { get; set; } //污水处理费
        public decimal extraChargePrice2 { get; set; } //附加费单价
        public decimal extraCharge2 { get; set; }//附加费
        public decimal extraTotalCharge { get; set; } //污水处理费和附加费之和
        public decimal totalCharge { get; set; } //总水费
        public decimal OVERDUEMONEY { get; set; }//滞纳金
        public string readMeterRecordDate { get; set; } //抄表时间
        public string checkState { get; set; } //审核状态：为1
        public string checkDateTime { get; set; }  //审核时间
        public string checker { get; set; }//审核人
        public string chargeState { get; set; } //抄表状态 0-未抄，1-已抄，2-预收，3-已收费；
        public string chargeID { get; set; }//收费流水号
        public string Latitude { get; set; }//纬度
        public string Longitude { get; set; }//经度

        public string Phone { get; set; }//电话
        public string Memo1 { get; set; }//备注

    }
}