using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Request
{
    public class PatientInfoReq : BaseRequest
    {
        public int brlb { get; set; }//病人类别
        public int cxms { get; set; }//查询名称
        public string cxz { get; set; }//查询码
        public int zyzt { get; set; }//住院状态
    }
}