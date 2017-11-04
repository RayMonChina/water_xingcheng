using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Response
{
    public class LoginRes:BaseRes
    {
        // //LOGINID,LOGINNAME,LOGINPASSWORD,USERNAME
        public string LOGINID { get; set; }
        public string USERNAME { get; set; }
        public int MeterDateTimeBegin { get; set; }
        public int MeterDateTimeEnd { get; set; }


    }
}