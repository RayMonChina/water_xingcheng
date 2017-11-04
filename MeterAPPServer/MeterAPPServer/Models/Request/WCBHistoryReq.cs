using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
namespace TestAndroid.Models.Request
{
    public class WCBHistoryReq:BaseRequest
    {
        public string StealNo { get; set; }
        public int CbMonth { get; set; }
        public int CbYear { get; set; }
    }
}