using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Text;
namespace TestAndroid.BLL
{
    public class SimpleLog
    {
        static object errLog = new object();
        public static void WriteLog(string content)
        {
            string filePath = HttpContext.Current.Server.MapPath("~/log/" + DateTime.Now.ToString("yyyyMMdd") + "/");
            if (!Directory.Exists(filePath))
            {
                Directory.CreateDirectory(filePath);
            }
            string fileName = Path.Combine(filePath, Guid.NewGuid().ToString() + ".txt");
            using (StreamWriter sw = new StreamWriter(fileName, true, Encoding.UTF8))
            {
                sw.WriteLine(content);
            }
        }

        public static void WriteErrorLog(string content)
        {
            lock (errLog)
            {
                string filePath = HttpContext.Current.Server.MapPath("~/log/err/");
                if (!Directory.Exists(filePath))
                {
                    Directory.CreateDirectory(filePath);
                }
                string fileName = Path.Combine(filePath, DateTime.Now.ToString("MM-dd-HH") + ".txt");
                using (StreamWriter sw = new StreamWriter(fileName, true, Encoding.UTF8))
                {
                    sw.WriteLine(content);
                }
            }
        }
    }
}