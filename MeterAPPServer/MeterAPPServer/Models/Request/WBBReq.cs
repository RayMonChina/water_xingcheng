using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Request
{
    public class WBBReq:BaseRequest
    {
        public string LoginID { get; set; }//根据登录人ID获取表本信息
    }
}