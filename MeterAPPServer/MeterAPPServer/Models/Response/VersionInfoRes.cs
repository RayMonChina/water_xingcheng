using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Response
{
    public class VersionInfoRes:BaseRes
    {
        public string fileUrl { get; set; }
        public string updateInfo { get; set; }
        public int versionCode { get; set; }//
    }
}