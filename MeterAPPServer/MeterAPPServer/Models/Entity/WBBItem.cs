using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class WBBItem
    {
        public string NoteNo { get; set; }//表本号
        public string PianNo { get; set; }
        public string AreaNo { get; set; }
        public string DuanNo { get; set; }
        public int CBMonth { get; set; }//抄表月
        public int CBYear { get; set; }
        public string CBUser { get; set; }//抄表员
        public string CBUserID { get; set; }//抄表员ID
        public int CustomerCount { get; set; }
    }
}