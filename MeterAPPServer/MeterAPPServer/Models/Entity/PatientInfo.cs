using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class PatientInfo
    {
        public string brID { get; set; }//病人编号
        public string brxm { get; set; }//姓名
        public string rysj { get; set; }//入院时间
        public string zyh { get; set; }//住院号
        public string cwh { get; set; }//床位号
        public int brlb { get; set; }//病人类别
        public string brxb { get; set; }//病人性别
        public string ward { get; set; }//病区
        public string wzjb { get; set; }//危重级别
        public string card_no { get; set; }//卡号
    }
}