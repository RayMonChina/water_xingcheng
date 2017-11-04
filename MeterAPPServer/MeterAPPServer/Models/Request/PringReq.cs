using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Request;

namespace MeterAPPServer.Models.Request
{
    public class PringReq: BaseRequest
    {
        public string recordId { get; set; }
    }
}