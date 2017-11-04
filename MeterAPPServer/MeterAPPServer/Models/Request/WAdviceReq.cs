using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Request
{
    public class WAdviceReq:BaseRequest
    {
        public string noteNo { get; set; }
        public List<WAdviceItem> adviceItems { get; set; }
    }
}