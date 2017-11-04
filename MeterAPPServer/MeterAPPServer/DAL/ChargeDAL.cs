using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.DAL;
using TestAndroid.Models.Entity;

namespace MeterAPPServer.DAL
{
    public class ChargeDAL:BaseDAL
    {
        /// <summary>
        /// 获取最大的小票编号
        /// </summary>
        /// <returns></returns>
        public string GetMaxPringNo(int length=8)
        {
            string printNo = "";
            using (var context = WDbContext()) {
                var stringSql = "select max(RECEIPTNO) from WATERFEECHARGE";
                var maxNo = context.Sql(stringSql)
                    .QuerySingle<string>();
                var currYear = DateTime.Now.ToString("yy");
                if (string.IsNullOrWhiteSpace(maxNo) || maxNo.Length < length) {
                    printNo = currYear + "1".PadLeft(length - 2, '0');
                    return printNo;
                }
                var dataYear = maxNo.Substring(0, 2);
                var dataIndex = maxNo.Substring(2);
                if (currYear != dataYear) {
                    printNo = currYear + "1".PadLeft(length - 2, '0');
                    return printNo;
                }
                var tempIndex = 0;
                int.TryParse(dataIndex, out tempIndex);
                tempIndex++;
                printNo = currYear + tempIndex.ToString().PadLeft(length - 2, '0');
            }
            return printNo;
        }

        /// <summary>
        /// 根据收费id获取收费信息
        /// </summary>
        /// <param name="chargeId"></param>
        /// <returns></returns>
        public WaterChageItem GetCharInfoByChargeId(string chargeId) {
            WaterChageItem retData = null;
            if (string.IsNullOrWhiteSpace(chargeId))
                return retData;
            using (var context = WDbContext()) {
                var sql = @"select CHARGEID,CHARGETYPEID,CHARGEDATETIME,RECEIPTPRINTCOUNT,RECEIPTNO from WATERFEECHARGE 
                           where CHARGEID=@chargeId";
                retData = context.Sql(sql)
                    .Parameter("chargeId",chargeId)
                    .QuerySingle<WaterChageItem>();
                return retData;
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="recordId"></param>
        /// <returns></returns>
        public WaterChageItem GetChargeInfoByRecordId(string recordId) {
            WaterChageItem retData = null;
            if (string.IsNullOrWhiteSpace(recordId))
                return retData;
            using (var context = WDbContext())
            {
                var sql = @"select c.CHARGEID,c.CHARGETYPEID,c.CHARGEDATETIME,c.RECEIPTPRINTCOUNT,c.RECEIPTNO 
                           from WATERFEECHARGE c join readMeterRecord r on c.CHARGEID=r.chargeID  
                           where r.readMeterRecordId=@recordId";
                retData = context.Sql(sql)
                    .Parameter("recordId",recordId)
                    .QuerySingle<WaterChageItem>();
                return retData;
            }
        }

        /// <summary>
        /// 更新打印状态
        /// </summary>
        /// <param name="recordId"></param>
        /// <returns></returns>
        public string UpdatePrintNo(string chargeId,int printCount) {
            var retData = "";
            if (string.IsNullOrWhiteSpace(chargeId))
            {
                return retData;
            }
            using (var context = WDbContext()) {
                var printNo = this.GetMaxPringNo();
                context.Update("WATERFEECHARGE")
                    .Column("RECEIPTNO", printNo)
                    .Column("RECEIPTPRINTCOUNT", printCount)
                    .Where("CHARGEID", chargeId)
                    .Execute();
                retData = printNo;
                return retData;
            }
        }

    }
}