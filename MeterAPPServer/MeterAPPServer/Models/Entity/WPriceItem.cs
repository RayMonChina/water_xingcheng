using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Entity
{
    public class WPriceItem
    {
        public int SIndex { get; set; }
        public string priceType { get; set; }
        public string priceTypeName { get; set; }
        public double WFrom { get; set; }
        public double WTo { get; set; }
        public double WPrice { get; set; }
    }
}