using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace MathsLibraryREST
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    [ServiceContract]
    public interface IMathsOperationsRest
    {
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "Add?x={x}&y={y}",BodyStyle = WebMessageBodyStyle.Wrapped,ResponseFormat = WebMessageFormat.Json)]
        double Add(double x, double y);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "Substract?x={x}&y={y}", BodyStyle = WebMessageBodyStyle.Wrapped, ResponseFormat = WebMessageFormat.Json)]
        double Subtract(double x, double y);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "Multiply?x={x}&y={y}", BodyStyle = WebMessageBodyStyle.Wrapped, ResponseFormat = WebMessageFormat.Json)]
        double Multiply(double x, double y);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "Divide?x={x}&y={y}", BodyStyle = WebMessageBodyStyle.Wrapped, ResponseFormat = WebMessageFormat.Json)]
        double Divide(double x, double y);
    }
}
