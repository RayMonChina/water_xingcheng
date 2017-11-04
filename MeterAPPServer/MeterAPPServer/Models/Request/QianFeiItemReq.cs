using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Request;

namespace MeterAPPServer.Models.Request
{
    public class QianFeiItemReq : BaseRequest
    {
        public string loginId { get; set; }
        public string meterReadingNO { get; set; }
    }
}