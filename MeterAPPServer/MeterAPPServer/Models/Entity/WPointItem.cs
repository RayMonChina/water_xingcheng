using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class WPointItem
    {
        public string UserId { get; set; }
        public string UserName { get; set; }
        public string AddDate { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
    }
}