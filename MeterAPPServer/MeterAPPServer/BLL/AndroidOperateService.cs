using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Text;
using TestAndroid.Models.Entity;
using TestAndroid.Models.Request;
using TestAndroid.Models.Response;
using TestAndroid.DAL;
using MeterAPPServer.Models.Response;
using MeterAPPServer.Models.Request;
using MeterAPPServer.DAL;

namespace TestAndroid.BLL
{
    public class AndroidOperateService
    {
        CbSystemDAL cbDal = new CbSystemDAL();
        ChargeDAL chargeDal = new ChargeDAL();
        //注册手机
        public EquipmentRes Reg(EquipmentReg req)
        {
            var logRes = cbDal.Reg(req);
            return logRes;
        }
        //登录信息
        public LoginRes Login(LoginReq req)
        {
            var logRes = cbDal.Login(req);
            return logRes;
        }
        //表本信息
        public WBBRes GetBBInfo(WBBReq bbreq)
        {
            WBBRes res = cbDal.GetBBInfo(bbreq);
            if (res == null)
            {
                res = new WBBRes();
                res.isErrMsg = true;
                res.errMsgNo = 2;
                res.errMsg = "没有查到数据";
            }
            return res;
        }

        //下载数据
        public WUserItemRes GetUserItemRes(WUserItemsReq req)
        {
            WUserItemRes res = new WUserItemRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "传入参数错误";
                return res;
            }
            res = cbDal.GetUserItemRes(req);
            return res;
        }

        //获取单条抄表记录
        public WUserItemRes GetSingleUserItemRes(WSingleUserItemReq req)
        {
            WUserItemRes res = new WUserItemRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "传入参数错误";
                return res;
            }
            res = cbDal.GetSingleUserItemRes(req);
            return res;
        }

        public WCBHistoryRes GetUserHistory(WCBHistoryReq req)
        {
            WCBHistoryRes res = new WCBHistoryRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "传入参数错误";
                return res;
            }
            res = cbDal.GetUserHistory(req);
            return res;
        }

        //获取版本信息
        public VersionInfoRes GetVersionInfo(VersionInfoReq req)
        {
            var verInfo = cbDal.GetVersionInfo(req);
            return verInfo;
        }
        
        /// <summary>
        /// 上传用户数据 RONG 2016-4-7
        /// </summary>
        /// <param name="req"></param>
        /// <returns></returns>
        public WUploadUserRes UploadUserInfo(WUploadUserReq req)
        {
            var userRes = new WUploadUserRes();
            if (req == null)
            {
                userRes.isErrMsg = true;
                userRes.errMsg = "参数错误";
            }
            else
            {
                userRes = cbDal.UpdataMeterData(req);
            }
            return userRes;
        }
        //上传用户建议
        public WAdviceRes UploadAdvice(WAdviceReq req)
        {
            var adRes = new WAdviceRes();
            if (req == null)
            {
                adRes.isErrMsg = true;
                adRes.errMsg = "参数传递错误";
            }
            else
            {
                adRes = cbDal.AddUserAdvice(req);
            }
            return adRes;
        }

        //获取价格类型
        public WPriceRes GetPriceInfo(WPriceReq req)
        {
            var retRes = new WPriceRes();
            if (req == null)
            {
                retRes.isErrMsg = true;
                retRes.errMsg = "参数错误";
                return retRes;
            }
            retRes = cbDal.GetPriceInfo(req);
            return retRes;
        }
        public WPointRes UploadPoints(WPointReq req)
        {
            var retRes = new WPointRes();
            if (req == null)
            {
                retRes.isErrMsg = true;
                retRes.errMsg = "参数传递错误";
            }
            else
            {
                retRes = cbDal.AddTrack(req);
            }
            return retRes;
        }
        public WFaultReportRes UploadFalut(WFaultReportReq req)
        {
            var res = new WFaultReportRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "参数传递错误";
                return res;
            }
            res = cbDal.AddFaultReport(req);
            return res;
        }

        public WaterChargeRes AddChargeItem(CharegeReq req)
        {
            var res = new WaterChargeRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "参数传递错误";
            }
            res = cbDal.AddChargeItem(req);
            return res;
        }

        public PicRes UploadPicData(PicReq req)
        {
            PicRes res = new PicRes();
            res.isErrMsg = false;
            res.errMsgNo = 0;
            var picPath = HttpContext.Current.Server.MapPath("~/temp/"+DateTime.Now.ToString("yyyyMMdd"));
            if (!Directory.Exists(picPath))
            {
                Directory.CreateDirectory(picPath);
            }
            if (req.picData != null)
            {
                string fileName = Path.Combine(picPath, req.picName);
                using (FileStream fs = new FileStream(fileName, FileMode.CreateNew))
                {
                    var bytes = Convert.FromBase64String(req.picData);
                    fs.Write(bytes, 0, bytes.Length);
                }
            }
            else
            {
                res.errMsgNo = 1;
                res.errMsg = "未接收到相应数据";
            }
            return res;
        }

         public ChargeItemsres GetChargeItemByLoginID(WUserItemsReq req)
        {
              var res= new ChargeItemsres();
              if (req == null)
              {
                  res.isErrMsg = true;
                  res.errMsg = "参数传递错误";
                  return res;
              }
              res = cbDal.GetChargeItemByLoginID(req);
              return res;
         }
        //获取欠费记录
         public QianFeiItemRes getQainFeiItemRes(QianFeiItemReq req)
        {
            var res = new QianFeiItemRes();
            if (req==null)
            {
                 res.isErrMsg = true;
                  res.errMsg = "参数传递错误";
                  return res;
            }
            res = cbDal.getQainFeiItemRes(req);
                 return res;
        }
        //获取服务器时间
        public ServerTimeRes getServerTimes()
         {
             var res = new ServerTimeRes();
             res = cbDal.getServerTimes();
             return res;
         }
        //历史欠费
        public QianFeiHistoryItemRes getQainFeiHistoryItemRes(QianFeiItemReq req)
        {
            var res = new QianFeiHistoryItemRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "参数传递错误";
                return res;
            }
            res = cbDal.getQainFeiHistoryItemRes(req);
            return res;
        }
        //允许上传时间
        public LoginRes getAllowUpdateDate(WBBReq req)
        {
            var res = new LoginRes();
            if (req==null)
            {
                res.isErrMsg = true;
                res.errMsg = "参数传递错误";
                return res;
            }
            res = cbDal.getAllowUpdateDate(req);
            return res;
        }

        public WUploadUserRes GetSingleFeeItemRes(WSingleUserItemReq req)
        {
            var res = new WUploadUserRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "参数传递错误";
                return res;
            }
            res = cbDal.GetSingleFeeItemRes(req);
            return res;
        }

        public PringRes PrintTicks(PringReq req)
        {
            var res = new PringRes();
            if (req == null)
            {
                res.isErrMsg = true;
                res.errMsg = "打印异常";
                return res;
            }
            var chargeInfo = chargeDal.GetChargeInfoByRecordId(req.recordId);
            if (chargeInfo == null)
            {
                res.isErrMsg = true;
                res.errMsg = "请先进行收费";
                return res;
            }
            if (!string.IsNullOrWhiteSpace(chargeInfo.RECEIPTNO))
            {
                res.isErrMsg = true;
                res.errMsg = "已打印过小票";
                return res;
            }
            res.printNo = chargeDal.UpdatePrintNo(chargeInfo.CHARGEID, chargeInfo.RECEIPTPRINTCOUNT + 1);
            return res;
        }

        public void LogWrite(string res, string type)
        {
            cbDal.LogWrite(res, type);
        }

    }
}