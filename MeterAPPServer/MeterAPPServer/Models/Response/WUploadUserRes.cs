using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Response
{
    public class WUploadUserRes:BaseRes
    {
        /// <summary>
        /// 是否已打印过小票
        /// </summary>
        public bool hasPrint { get; set; }
        /// <summary>
        /// 收费ID
        /// </summary>
        public string chargeId { get; set; }
        /// <summary>
        /// 支付类型
        /// </summary>
        public int chargeType { get; set; }
    }
}