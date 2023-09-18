# TD1 - JAVA REMOTE METHOD INVOCATION

## Creation of the remote object
- Created first of all the remote interface.
- In the creation of the implementation of our remote object [`src/ObjetDistante.java`](src/ObjetDistante.java), i included the server as well in the main of this class, it could be a completely different class.
- For our remote object to really become a process listening through an
  open port, and capable to communicate using sockets, the programmer must do something
  Either extends the class UnicastRemoteObject , or use UnicastRemoteObject.exportObject method
  Here are details about these two possibilities,, assuming also that you want to impose
  that the remote object listens on TCP port 100001:
    -
```java
import common.Distante;     
class ObjetDistant extends UnicastRemoteObject implements Distante {
        public ObjetDistant(int numport) throws RemoteException {
            super(numport);
        }
    }
````
and in the main: `Distante d = new ObjetDistant(10001);`
- or just `class ObjetDistant implements Distante { ... }`
  and in the main:
  ```java
  ObjetDistant od=new ObjetDistant();
  Distante d = (Distante)UnicastRemoteObject.exportObject(od,10001);
  ```
**We will go with the first option**

- Usage of the `rmiregistry` to publish a RMI reference (a stub) to the remote object

  To publish `d`, you can use the `rebind` method, which is better than using `bind`. If the `rmiregistry` is not killed at the end of each remote object execution, using `bind` can raise an `AlreadyBoundException` if an object with the same key/name is already registered in the `rmiregistry`. With `rebind`, you can use the same key because it updates the corresponding entry in the `rmiregistry` dictionary.

    - By explicitly using the `rmiregistry` address and storing it in a local variable named `reg`:
      ```java
      reg.rebind("MonOD", d);
      ```

    - By implicitly using the `rmiregistry` with a URL instead of its object reference in the JVM:
      ```java
      Naming.rebind("rmi://localhost:2001/MonOD", d);
      ```

      One can observe that the first part of the URL specifies the use of the RMI-specific communication protocol (`rmi://`) to connect with the `rmiregistry` running on the host `localhost`, listening on port number 2001. The stub object `d` is passed as a parameter, serialized, and it requests the `rmiregistry` to register the deserialized object in its dictionary with the key/name `MonOD`.

**We Go with the second option**

## Creation of Client


At client-side: assume that client is not running on the same host than the remote object.
1) It is necessary to have access to the **rmiregistry** that runs on the server host
   (let it name "IP_server")

    1) in an explicit manner
       `Registry reg = LocateRegistry.getRegistry("IP_server",2001);`
    2) in an implicit manner, at the lookup operation time, refer to the step 2 below

2) Getting the stub of the remote object by requesting the rmiregistry
    1) `Distante d = (Distante)reg.lookup("MonOD");`
       or
    2) `Distante d = (Distante)Naming.lookup("rmi://IP_server:2001/MonOD");`

**2) b) is what I decide to go with**

## Local execution (Exo 3)

- first of all, we compile all the java classes using javac, making sure to adjust according to our project's structure.
- secondly, we run rmi registry on port 1099, using the command `start rmiregistry 1099`, we have to make sure that rmiregistry is set up well in the path variables.
- we then run on seperate terminals:
    - the server ([`src/ObjetDistante.java`](src/ObjetDistante.java))
    - the client ([`src/Client.java`](src/Client.java))

***The message printed by the echo function is in the terminal of the server, this is explained by the fact that after the methods are called by the client, they are executed on the remote JVM, meaning any outputs or print messages will be displayed on the server side. A way to circumvent this problem is to make the echo function return a string, that way the client class receives the string and then prints on its console***

## Synchronicity of RMI (Exo 4)

After the execution, we see that between the two print statements, we wait a period of time equivalent to the sleep introduced in the echo method in the remote class, this means that the client waits for the remote method to finish before it continues thus showcasing the synchronicity of RMI in java.

## Compilation and Classpath

during the compilation of Client, using the javac command, you will get an error because the compiler looks for the classes of Distante and Resultat only in the current directory, so unless you determine in the classpath variable where he should look, you will get an error, that can be done using this command using ':' on Linux or ';' on Windows:

```javac -cp dir1:dir2:...:dirN Client.java```

dir1...dirN represent the directories where the compilers should look for class files.

## Serialization problem (exo 5)

In Java RMI (Remote Method Invocation), the issue I faced was related to how objects are passed between different Java Virtual Machines (JVMs) over the network. When you use RMI to call methods on remote objects, the data you send and receive must be transformed into a format that can be transmitted over the network. This process is called serialization.

Serialization involves converting the complex structure of an object, including its data and relationships, into a stream of bytes that can be sent over the network. On the receiving side, deserialization reconstructs the object from the byte stream.

Now, here's where the problem came up. In RMI, any class that's involved in the remote method call—either as an argument or a return value—needs to be serializable. This requirement ensures that the objects can be correctly transformed into bytes and then back into objects on the other side. It's like packaging something for shipping: the item needs to be properly packed to survive the journey.

In my case, I had a class called Resultat that was used as a return value in one of the remote methods. However, I hadn't marked this class as serializable. When the server tried to return a Resultat object to the client, it encountered a problem. The RMI framework needed to convert the object into bytes, but since Resultat wasn't set up to be serializable, this process failed. That's why I got the error message saying "java.io.NotSerializableException: common.Resultat".

The solution was to implement the Serializable interface in the Resultat class. This interface acts as a marker to tell Java that the class is ready to be serialized. Once I made this change, Java knew how to properly package the Resultat objects for transmission between the client and server over the network. This fixed the issue and allowed my RMI-based application to work smoothly.

## Transient keyword
The **transient** keyword makes certain objects untransmittable, meaning they can't be serialized in order to be transmitted and they can't be deserialized by the client consequently.
For instance, we made the String infoCB variable containing credit card info transient, it has a value in the class, however when we try to access this value from the client, we get the default value for String which is null.