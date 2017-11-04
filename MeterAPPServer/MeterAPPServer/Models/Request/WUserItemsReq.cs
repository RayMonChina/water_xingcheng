using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Request
{
    public class WUserItemsReq:BaseRequest
    {
        //public string noteNo { get; set; }//表本号
        //public string PianNo { get; set; }
        //public string AreaNo { get; set; }
        //public string DuanNo { get; set; }
        public string loginid { get; set; }
        public string cbYear { get; set; }
        public string cbMonth { get; set; }
        public string meterReadingNO { get; set; }
    }
}