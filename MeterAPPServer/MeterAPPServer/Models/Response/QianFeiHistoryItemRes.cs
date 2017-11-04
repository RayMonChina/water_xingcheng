using MeterAPPServer.Models.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TestAndroid.Models.Response;

namespace MeterAPPServer.Models.Response
{
    public class QianFeiHistoryItemRes : BaseRes
    {
      //  [waterUserId]
      //,[waterUserNO]
      //,[waterUserName]
      //,[waterPhone]
      //,[waterUserAddress]
      //,[waterMeterLastNumber]
      //,[waterMeterEndNumber]
      //,[totalNumber]
      //,[  ]
      //,[readMeterRecordYear]
      //,[readMeterRecordMonth]
      //,[meterReadingNO]
      //,[meterReadingID]
      //,[ordernumber]
      //,[WATERUSERQQYE]
      //,[WATERUSERJSYE]
      //,[INFORMPRINTSIGN]
        public List<QianFeiHistoryItem> QianFeiHistoryItem { get; set; }

    }
}