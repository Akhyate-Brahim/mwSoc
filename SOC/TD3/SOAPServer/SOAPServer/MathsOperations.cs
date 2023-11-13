using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace SOAPServer
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in both code and config file together.
    public class MathsOperations : IMathsOperations
    {
        public int Add(int x, int y)
        {
            return x + y;
        }

        public double divide(double x, double y)
        {
            if (y == 0)
            {
                throw new DivideByZeroException();
            }
            return x / y;
        }

        public int multiply(int x, int y)
        {
            return x * y;
        }

        public int substract(int x, int y)
        {
            return x - y;
        }
    }
}
