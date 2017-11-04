using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Request
{
    public class PicReq:BaseRequest
    {
        public string UserId { get; set; }
        public string UserNo { get; set; }
        public string picType { get; set; }
        public string picName { get; set; }
        public string picData { get; set; }
        public string addDate { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
    }
}