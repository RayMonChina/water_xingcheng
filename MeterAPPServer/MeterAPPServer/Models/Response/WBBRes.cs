using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Response
{
    public class WBBRes:BaseRes
    {
        public List<WBBItem> bbItems { get; set; }
    }
}