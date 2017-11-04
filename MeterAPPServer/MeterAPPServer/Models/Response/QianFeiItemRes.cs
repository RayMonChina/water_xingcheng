using MeterAPPServer.Models.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Response;

namespace MeterAPPServer.Models.Response
{
    public class QianFeiItemRes : BaseRes
    {
        public List<QianFeiItem> QianFeiItem { get; set; }
    }
}