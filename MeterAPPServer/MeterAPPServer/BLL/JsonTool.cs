using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.IO;
using System.Text;
namespace TestAndroid.BLL
{
    public static class JsonTool
    {
        public static List<T> JSONStringToList<T>(this string JsonStr)
        {
            JavaScriptSerializer Serializer = new JavaScriptSerializer();
            List<T> objs = Serializer.Deserialize<List<T>>(JsonStr);
            return objs;
        }

        public static T Deserialize<T>(string json)
        {
            JavaScriptSerializer Serializer = new JavaScriptSerializer();
            T objs = Serializer.Deserialize<T>(json);
            return objs;
        }

        public static string Serialize<T>(T Input)
        {
            JavaScriptSerializer Serializer = new JavaScriptSerializer();
            Serializer.MaxJsonLength = int.MaxValue;
            string retVal=Serializer.Serialize(Input);
            return retVal;
        }

        //public static T Deserialize<T>(string json)
        //{
        //    T obj = Activator.CreateInstance<T>();
        //    using (MemoryStream ms = new MemoryStream(Encoding.UTF8.GetBytes(json)))
        //    {
        //        DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
        //        return (T)serializer.ReadObject(ms);
        //    }
        //}  
    }
}