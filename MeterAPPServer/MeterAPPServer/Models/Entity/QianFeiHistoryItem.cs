using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MeterAPPServer.Models.Entity
{
    public class QianFeiHistoryItem
    {
        public string waterUserId { get; set; }
        public string waterUserNO { get; set; }
        public string waterUserName { get; set; }
        public string waterPhone { get; set; }
        public string waterUserAddress { get; set; }
        public string waterMeterLastNumber { get; set; }
        public string waterMeterEndNumber { get; set; }
        public string totalNumber { get; set; }
        public string totalCharge { get; set; }
        public string readMeterRecordYear { get; set; }
        public string readMeterRecordMonth { get; set; }
        public string meterReadingNO { get; set; }
        public string meterReadingID { get; set; }
        public string ordernumber { get; set; }
        public string WATERUSERQQYE { get; set; }
        public string WATERUSERJSYE { get; set; }
        public string INFORMPRINTSIGN { get; set; }
    }
}