using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Response;

namespace MeterAPPServer.Models.Response
{
    public class ServerTimeRes : BaseRes
    {
        public string ServerTime { get; set; }
    }
}