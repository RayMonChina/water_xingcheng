using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TestAndroid.Models.Request
{
    public class WSingleUserItemReq : BaseRequest
    {
        public string readMeterRecordId { get; set; }
        /// <summary>
        /// 支付方式 6 微信
        /// </summary>
        public int chargeTypeID { get; set; }
    }
}
