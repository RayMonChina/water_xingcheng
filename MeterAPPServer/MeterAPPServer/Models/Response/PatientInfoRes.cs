using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Response
{
    public class PatientInfoRes:BaseRes
    {
        public List<PatientInfo> brjyxxs { get; set; }

        public string brjzount { get; set; }
    }
}