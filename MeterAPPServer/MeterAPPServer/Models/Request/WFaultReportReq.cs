using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Request
{
    public class WFaultReportReq:BaseRequest
    {
        public String ImgData { get; set; }
        public String WaterUserId { get; set; }
        public String Describe { get; set; }
        public String CreateDateTime { get; set; }
        public String LoginId { get; set; }
    }
}