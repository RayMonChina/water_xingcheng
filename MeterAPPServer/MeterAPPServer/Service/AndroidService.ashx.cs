using System;
using System.Configuration;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using TestAndroid.BLL;
using TestAndroid.Models.Request;
using TestAndroid.Models.Response;
using TestAndroid.Models.Entity;
using MeterAPPServer.Models.Response;
using MeterAPPServer.Models.Request;
namespace TestAndroid.Service
{
    /// <summary>
    /// AndroidService 的摘要说明
    /// </summary>
    public class AndroidService : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            var res = "";
            AndroidOperateService AService = new AndroidOperateService();
            if (!string.IsNullOrEmpty(context.Request["type"]))
            {
                var reqType = context.Request["type"];
                var gson = context.Request["gson"];
                if (string.IsNullOrEmpty(gson))
                {
                    return;
                }
                if ("true".Equals(ConfigurationManager.AppSettings["showlog"]))
                {
                    SimpleLog.WriteLog(gson);
                }

               
                switch (reqType)
                {
                    case "0"://注册
                        var RegReq = JsonTool.Deserialize<EquipmentReg>(gson);
                        res = JsonTool.Serialize<EquipmentRes>(AService.Reg(RegReq));
                        break;
                    case "1"://登录
                        var loginReq = JsonTool.Deserialize<LoginReq>(gson);
                        res = JsonTool.Serialize<LoginRes>(AService.Login(loginReq));
                        break;
                    case "2"://获取表本信息
                        var bbreq = JsonTool.Deserialize<WBBReq>(gson);
                        res = JsonTool.Serialize<WBBRes>(AService.GetBBInfo(bbreq));
                        break;
                    case "3"://用户数据下载
                        var downUserReq = JsonTool.Deserialize<WUserItemsReq>(gson);
                        res = JsonTool.Serialize<WUserItemRes>(AService.GetUserItemRes(downUserReq));
                        break;
                    case "4"://版本更新
                        var versionReq = JsonTool.Deserialize<VersionInfoReq>(gson);
                        res = JsonTool.Serialize<VersionInfoRes>(AService.GetVersionInfo(versionReq));
                        break;
                    case "5"://上传用户数据
                        var userUploadReq = JsonTool.Deserialize<WUploadUserReq>(gson);
                        res = JsonTool.Serialize<WUploadUserRes>(AService.UploadUserInfo(userUploadReq));
                        break;
                    case "6"://上传故障报修
                        var faultReq = JsonTool.Deserialize<WFaultReportReq>(gson);
                        res = JsonTool.Serialize<WFaultReportRes>(AService.UploadFalut(faultReq));
                        break;
                    case "7"://上传客户建议
                        var adviceReq = JsonTool.Deserialize<WAdviceReq>(gson);
                        res = JsonTool.Serialize<WAdviceRes>(AService.UploadAdvice(adviceReq));
                        break;
                    case "8"://上传图片
                        var picReq = JsonTool.Deserialize<PicReq>(gson);
                        res = JsonTool.Serialize<PicRes>(AService.UploadPicData(picReq));
                        break;
                    case "9"://获取水价信息
                        var priceReq = JsonTool.Deserialize<WPriceReq>(gson);
                        res = JsonTool.Serialize<WPriceRes>(AService.GetPriceInfo(priceReq));
                        break;
                    case "10"://行走信息
                        var pointReq = JsonTool.Deserialize<WPointReq>(gson);
                        res = JsonTool.Serialize<WPointRes>(AService.UploadPoints(pointReq));
                        break;
                    case "11"://收费信息
                        var chargeReq = JsonTool.Deserialize<CharegeReq>(gson);
                        res = JsonTool.Serialize<WaterChargeRes>(AService.AddChargeItem(chargeReq));
                        break;
                    case "12"://历史记录
                        var hisReq = JsonTool.Deserialize<WCBHistoryReq>(gson);
                        res = JsonTool.Serialize<WCBHistoryRes>(AService.GetUserHistory(hisReq));
                        break;
                    case "13"://获取最后一条收费编号
                        var chargeNOReq = JsonTool.Deserialize<WUserItemsReq>(gson);
                        res = JsonTool.Serialize<ChargeItemsres>(AService.GetChargeItemByLoginID(chargeNOReq));
                        break;
                    case "14"://获取单条数据
                        var SingleUserItem = JsonTool.Deserialize<WSingleUserItemReq>(gson);
                        res = JsonTool.Serialize<WUserItemRes>(AService.GetSingleUserItemRes(SingleUserItem));
                        break;
                    case "15"://获取欠费信息
                        var QianFeiItem=JsonTool.Deserialize<QianFeiItemReq>(gson);
                        res = JsonTool.Serialize<QianFeiItemRes>(AService.getQainFeiItemRes(QianFeiItem));
                        break;
                    case "16"://获取服务器时间
                        res = JsonTool.Serialize<ServerTimeRes>(AService.getServerTimes());
                        break;
                    case "17"://历史欠费信息
                          var QianFeiIHistorytem=JsonTool.Deserialize<QianFeiItemReq>(gson);
                          res = JsonTool.Serialize<QianFeiHistoryItemRes>(AService.getQainFeiHistoryItemRes(QianFeiIHistorytem));
                        break;
                    case "18"://是否允许抄表WBBReq
                        var AllowUpdateDate = JsonTool.Deserialize<WBBReq>(gson);
                        res = JsonTool.Serialize<LoginRes>(AService.getAllowUpdateDate(AllowUpdateDate));
                        break;
                    case "19"://收费
                         var SingleUserFeeItem = JsonTool.Deserialize<WSingleUserItemReq>(gson);
                         res = JsonTool.Serialize<WUploadUserRes>(AService.GetSingleFeeItemRes(SingleUserFeeItem));
                        break;
                    case "20"://打印
                        var printReq = JsonTool.Deserialize<PringReq>(gson);
                        res = JsonTool.Serialize<PringRes>(AService.PrintTicks(printReq));
                        break;
                    default:
                        break;
                }
                if ("true".Equals(ConfigurationManager.AppSettings["showlog"]))
                {
                    // SimpleLog.WriteLog(res)
                    try
                    {
                        AService.LogWrite(res, reqType);
                    }
                    catch (Exception)
                    {

                    }
                }
            }
           
            context.Response.Write(res);

        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}