using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TestAndroid.Models.Response
{
    public class BaseRes
    {
        //是否出错
        private bool isErrmsg;
        public bool isErrMsg{
            get {
                if (!string.IsNullOrEmpty(this.errMsg))
                {
                    return true;
                }
                return false;
            }
            set {
                isErrmsg = value;
            }
        }
        //错误信息
        private string _errMsg;
        public string errMsg {
            get {
                return _errMsg;
            }
            set {
                _errMsg = value;
            }
        }
        //错误编号
        private int _errMsgNo;
        public int errMsgNo {
            get {
                return _errMsgNo;
            }
            set {
                _errMsgNo = value;
            }
        }
    }
}