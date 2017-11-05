using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class WUserItem
    {
        //public string UserNo { get; set; }//用户编号
        //public string NoteNo { get; set; }//表本号
        //public string UserFName { get; set; }//户名
        //public string Phone { get; set; }//电话
        //public string Address { get; set; }//地址
        //public double LastMonthValue { get; set; }//上月止度
        //public double LastMonthWater { get; set; }//上月水量
        //public double LastMonthFee { get; set; }//上月水费
        //public string PriceType { get; set; }//价格类型
        //public string PriceTypeName { get; set; }//价格类型名称
        //public string StealID { get; set; }
        //public string StealNo { get; set; }//表钢号
        //public int ChaoBiaoTag { get; set; }//抄表标志 1未抄 2已抄
        //public double PreMoney { get; set; }//预存金额
        //public double OweMoney { get; set; }//累计欠费
        //public double CurrentMonthValue { get; set; }//本月止度
        //public double CurrMonthWNum { get; set; }//本月水量
        //public double CurrMonthFee { get; set; }//本月金额
        //public double ShouFei { get; set; }//本月收费
        //public string ShouFeiDate { get; set; }//收费日期
        //public string ChaoBiaoDate { get; set; }//抄表日期
        //public double Latitude { get; set; }//纬度
        //public double Longitude { get; set; }//经度
        //public string IsReverse { get; set; }//是否反转
        //public string StepPrice { get; set; }
        //public string ExtraPrice { get; set; }
        //public int IsSummaryMeter { get; set; }
        //public string WaterMeterParentID { get; set; }
        //public int OrderNumber { get; set; }
        //public double WaterFixValue { get; set; }
        //public string NFCTag { get; set; }
        //public string LastChaoBiaoDate { get; set; }
        //public string PianNo { get; set; }
        //public string AreaNo { get; set; }
        //public string DuanNo { get; set; }
        //Memo1备注

        private string _Latitude { get; set; }
        private string _Longitude { get; set; }

        public string readMeterRecordId { get; set; }
        public string NoteNo { get; set; }//表本号
        public string StealNo { get; set; }//水表号
        public string Address { get; set; }//地址
        public string LastChaoBiaoDate { get; set; }//上次抄表时间
        public int LastMonthValue { get; set; }//上期表底数
        public int CurrentMonthValue { get; set; }//本月读数
        public int CurrMonthWNum { get; set; }//本月水量；三种情况：1、正常（本月读数-上期表底数）；2、定量用水；3、总表（本月读数-上期表底数-所有分表水量）
        public decimal avePrice { get; set; }//单价
        public decimal CurrMonthFee { get; set; }//水费=本月水量*单价
        public decimal extraChargePrice1 { get; set; }//污水处理费单价
        public decimal extraCharge1 { get; set; }//污水处理费
        public decimal extraChargePrice2 { get; set; }//附加费单价
        public decimal extraCharge2 { get; set; }//附加费
        public decimal extraTotalCharge { get; set; }//污水处理费和附加费之和
        public string StepPrice { get; set; }//阶梯水价算法
        public string ExtraPrice { get; set; }//污水处理费和附加费算法
        public decimal TotalCharge { get; set; }//总水费=水费+污水处理费+附加费
        public decimal OVERDUEMONEY { get; set; }//滞纳金
        public int WaterFixValue { get; set; }//定量用水标志:默认为0，大于0的数字就是水量
        public int ReadMeterRecordYear { get; set; }//应抄年份
        public int ReadMeterRecordMonth { get; set; }//应抄月份
        public string ChaoBiaoDate { get; set; }//抄表时间
        public string WaterMeterPositionName { get; set; }//水表位置
        public string loginId { get; set; }//抄表员ID
        public string USERNAME { get; set; }//抄表员名字
        public string checkState { get; set; }//审核状态：为1
        public string checkDateTime { get; set; }//审核时间
        public string checker { get; set; }//审核人
        public string ChaoBiaoTag { get; set; }//抄表状态 0-未抄，1-已抄，2-预收，3-已收费；
        public string chargeID { get; set; }//收费流水号
        public string waterUserId { get; set; }//用户ID
        public string UserNo { get; set; }//户号
        public string UserFName { get; set; }//户名
        public string Phone { get; set; }//电话（含联系人）
        public string IsSummaryMeter { get; set; }//总表标志：1-总表，0-分表,(只有分表抄完，再抄总表)
        public string WaterMeterParentID { get; set; }//总表ID（WaterMeterID）
        public int OrderNumber { get; set; }//抄表顺序（显示顺序）
        public string WATERMETERNUMBERCHANGESTATE { get; set; }//换表标志：0-正常，1-换表
        public string waterUserchargeType { get; set; }
        public string ReceiptIO { get; set; }
        /// <summary>
        /// 支付方式
        /// </summary>
        public int ChargeTypeId { get; set; }
        public string Latitude
        {
            get
            {
                var retLa = 0d;
                if (double.TryParse(this._Latitude, out retLa))
                {
                    return this._Latitude;
                }
                return "0";
            }
            set { _Latitude = value; }
        }//纬度
        public string Longitude
        {
            get
            {
                var retLa = 0d;
                if (double.TryParse(this._Longitude, out retLa))
                {
                    return this._Longitude;
                }
                return "0";
            }
            set { _Longitude = value; }
        }//经度
        public string PriceType { get; set; }//用户类型
        public string PriceTypeName { get; set; }//用户类型名称

        public string Memo1 { get; set; }
        public string PreMoney { get; set; }
    }
}