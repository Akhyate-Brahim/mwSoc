﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace MathsLibraryClient.MathsLibraryReference {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="MathsLibraryReference.IMathsOperations")]
    public interface IMathsOperations {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Add", ReplyAction="http://tempuri.org/IMathsOperations/AddResponse")]
        double Add(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Add", ReplyAction="http://tempuri.org/IMathsOperations/AddResponse")]
        System.Threading.Tasks.Task<double> AddAsync(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Subtract", ReplyAction="http://tempuri.org/IMathsOperations/SubtractResponse")]
        double Subtract(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Subtract", ReplyAction="http://tempuri.org/IMathsOperations/SubtractResponse")]
        System.Threading.Tasks.Task<double> SubtractAsync(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Multiply", ReplyAction="http://tempuri.org/IMathsOperations/MultiplyResponse")]
        double Multiply(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Multiply", ReplyAction="http://tempuri.org/IMathsOperations/MultiplyResponse")]
        System.Threading.Tasks.Task<double> MultiplyAsync(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Divide", ReplyAction="http://tempuri.org/IMathsOperations/DivideResponse")]
        double Divide(double a, double b);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IMathsOperations/Divide", ReplyAction="http://tempuri.org/IMathsOperations/DivideResponse")]
        System.Threading.Tasks.Task<double> DivideAsync(double a, double b);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface IMathsOperationsChannel : global::MathsLibraryClient.MathsLibraryReference.IMathsOperations, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class MathsOperationsClient : System.ServiceModel.ClientBase<global::MathsLibraryClient.MathsLibraryReference.IMathsOperations>, global::MathsLibraryClient.MathsLibraryReference.IMathsOperations {
        
        public MathsOperationsClient() {
        }
        
        public MathsOperationsClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public MathsOperationsClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public MathsOperationsClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public MathsOperationsClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public double Add(double a, double b) {
            return base.Channel.Add(a, b);
        }
        
        public System.Threading.Tasks.Task<double> AddAsync(double a, double b) {
            return base.Channel.AddAsync(a, b);
        }
        
        public double Subtract(double a, double b) {
            return base.Channel.Subtract(a, b);
        }
        
        public System.Threading.Tasks.Task<double> SubtractAsync(double a, double b) {
            return base.Channel.SubtractAsync(a, b);
        }
        
        public double Multiply(double a, double b) {
            return base.Channel.Multiply(a, b);
        }
        
        public System.Threading.Tasks.Task<double> MultiplyAsync(double a, double b) {
            return base.Channel.MultiplyAsync(a, b);
        }
        
        public double Divide(double a, double b) {
            return base.Channel.Divide(a, b);
        }
        
        public System.Threading.Tasks.Task<double> DivideAsync(double a, double b) {
            return base.Channel.DivideAsync(a, b);
        }
    }
}