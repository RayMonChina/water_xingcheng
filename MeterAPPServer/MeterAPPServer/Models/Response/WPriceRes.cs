using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Response
{
    public class WPriceRes:BaseRes
    {
        public List<WPriceItem> priceItems { get; set; }
    }
}