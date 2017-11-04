using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Request
{
    public class LoginReq : BaseRequest
    {
        public string LOGINNAME { get; set; }
        public string LOGINPASSWORD { get; set; }
    }
}