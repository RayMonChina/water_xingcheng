using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Entity;
namespace TestAndroid.Models.Response
{
    public class WUserItemRes:BaseRes
    {
        public List<WUserItem> userItems { get; set; }
    }
}