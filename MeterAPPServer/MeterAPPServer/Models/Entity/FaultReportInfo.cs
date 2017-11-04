using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class FaultReportInfo
    {
        public int FId { get; set; }
        public string UserNo { get; set; }
        public string NoteNo { get; set; }
        public string FaultDescript { get; set; }
        public string AddDate { get; set; }
        public string AddUserID { get; set; }
        public string AddUser { get; set; }
    }
}