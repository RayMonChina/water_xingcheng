using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Request
{
    public class WPointReq:BaseRequest
    {
        public List<WPointItem> pointItems { get; set; }
    }
}