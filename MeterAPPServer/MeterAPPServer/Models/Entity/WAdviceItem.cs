using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class WAdviceItem
    {
        public int AdviceID { get; set; }
        public string UserNo { get; set; }
        public string NoteNo { get; set; }
        public string Advice { get; set; }
        public string AddUserId { get; set; }
        public string AddUser { get; set; }
        public string AddDate { get; set; }
    }
}