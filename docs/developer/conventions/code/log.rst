Logging
-------

We use the logging package bundled into J2SE 1.4 and above (java.util.logging). An overview is available on line in the Sun's SDK documentation. Another valuable source of inspiration is Logging in NetBeans.

GeoTools typically (but not always) uses one logger per package and is named after the package name. Private classes or implementations sometime use the name of their public counterpart.

Getting a Logger
^^^^^^^^^^^^^^^^

The Java way to get a logger is to invoke Logger.getLogger(String). However in the GeoTools library, this call is replaced by a call to Logging.setLogger(String) where Logging is a class in the org.geotools.util.logging package:

.. code-block:: java
   
   package org.geotools.mypackage;
   
   import java.util.logging.Logger;
   import org.geotools.util.logging.Logging:

   public class Example {
     void method() {
        final Logger logger = Logging.getLogger("org.geotools.mypackage");
        LOGGER.config("There is some configuration information.");
     }
   }

Logger Declaration
^^^^^^^^^^^^^^^^^^

The logger may be declared in the class's static fields or returned by a class's static method. This is not mandatory but suggested if it is going to be used in many places:

.. code-block:: java
   
   package org.geotools.image;
   
   import java.util.logging.Logger;
   import org.geotools.util.logging.Logging:
   
   public class ImageWorker {
   
       /** The logger ImageWorker.class used is "org.geotools.image" */
       private static final Logger LOGGER = Logging.getLogger(ImageWorker.class);
   }

Abstract classes may define a protected logger for their subclasses to use:

Logging Messages
^^^^^^^^^^^^^^^^

Message can be conveniently logged using one of 7 predefined levels. The levels in descending order are:

========== ================================ ====================================================
Level      Displayed on standard output     Comments 
========== ================================ ====================================================
Severe     yes by default                   highest value
Warning    yes by default                   non-fatal warning to bring to user attention
Info       yes by default                   message for end users (not debugging information)
Config     no unless configured             configuration information (services available, etc.)
Fine       no unless configured             information for developers (high level)
Finer      no unless configured             common when entering, returning, or an exception
Finest     no unless configured             most verbose output
========== ================================ ====================================================

A convenience method exists in Logger for each of those levels:

.. code-block:: java
   
   LOGGER.info("There is a message of interest for ordinary user");
   
Do not use the logging info level as a replacement of System.out.println for displaying debug information to the console.
   
The INFO level is for end users. Use the FINE, FINER or FINEST levels for debug information, and setup yours :file:`logging.properties` file accordingly (see Logging Configuration below).

Entering/Existing Logger
^^^^^^^^^^^^^^^^^^^^^^^^

There are three more convenience methods: entering, exiting and throwing when entering and exiting a method:

.. code-block:: java
   
   public Object myMethod(String myArgument) {
       LOGGER.entering("MyClass", "MyMethod", myArgument);
       // ... do some process here
       LOGGER.exiting("MyClass", "MyMethod", myReturnValue);
       return myReturnValue;
   }

When we are about to terminate a method with an exception.:

Minimising Logger output
^^^^^^^^^^^^^^^^^^^^^^^^^

When logging a message, the logger will include detailed information such as date and time, source class and method name, current thread, etc.

In order to minimise  the amount of information logged, it may be useful to merge consecutive logging into a single log statement.

This is especially appropriate if the many logs are actually different parts of a multi-lines message. Using distinct logger calls can result in an output interleaved with the logging from an other thread. Merging the logging is not appropriate if the log messages are conceptually unrelated.

Wasteful use of logging::
   
   LOGGER.finer("Value for A is "+A);
   LOGGER.finer("Value for B is "+B);
   LOGGER.finer("Value for C is "+C);

Good use of logging::
   LOGGER.finer("Computed values: A="+A+"; B="+B+"; C="+C);

Selective Logging
^^^^^^^^^^^^^^^^^^

If the log message is expensive to construct, then consider enclosing it in an if statement.::
   
   if (LOGGER.isLoggable(Level.FINER)) {
      LOGGER.finer("Current state = "+someVeryExpensiveMethodCall());
   }

Java Util Logging Configuration
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The java util logging configuration is defined by a file :file:`logging.properties`:

* ``java.util.logging.config.file`` system property
* ``java.util.logging.config.class`` identifying a class in your application responsible for configuration
* Java 8: :file:`$JAVA_HOME/jre/lib/logging.properties`
* Java 9: :file:`$JAVA_HOME/conf/logging.properties`

And can be detected during application startup if necessary:

.. code-block:: java

   public void static main(String ...){
      File logging = new File("logging.properties");
      if( logging.exists() && !System.getProperties().hasKey("java.util.logging.config.file")){
        System.setProperty("java.util.logging.config.file", path);
      }
      else {
        System.setProperty("java.util.logging.config.class", "ApplicationDefaultLogging");
      }
   }

Falling back to ApplicationDefaultLogging (reading :file:`logging.properties` from `src/main/resources/logging.properties` resource included in jar:

.. code-block:: java

   class ApplicationDefaultLogging {
      public ApplicationDefaultLogging(){
          try( Inputstream stream : ApplicationDefaultLogging.class.getResourceAsStream("/logging.properties")){
             LogManager.readConfiguration(stream);
          }
      }
   }

To define a default configuration level provide a the **.level** property to the minimal level of interest for you::
   
   .level= FINER

You can specify a different level to be shown to the console (than is saved out to xml). To define the java.util.logging.ConsoleHandler.level property to the minimal level you want to see on the console::
   
   # Limit the message that are printed on the console to FINE and above.
   java.util.logging.ConsoleHandler.level = FINE
   java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
   java.util.logging.ConsoleHandler.encoding = Cp850

Note the **encoding** property. For Windows user, it should be set to the value displayed by :command:`chcp` on the command line. Linux and Unix users may ignore this line since Unix systems do a more intelligent work with page codes.

To list detailed messages for a specific module you can define a different logging level may be specified for each module.::
   
   org.geotools.gml.level = FINE
   org.geotools.referencing.level = INFO

Provides fairly detailed logging message from the GML module, but not from the referencing module.

GeoTools can produces a console output similar to the Log4J one (single-line instead of multi-line log message) if the following code is invoked once at application starting time:

.. code-block:: java

   Logging.ALL.forceMonolineConsoleOutput();

Alternatively, this formatter can also be configured in the :file:`logging.properties` without the need for the above-cited method call:

.. code-block:: ini

   java.util.logging.ConsoleHandler.formatter = org.geotools.util.logging.MonolineFormatter
   java.util.logging.ConsoleHandler.encoding = Cp850
   java.util.logging.ConsoleHandler.level = FINE

   # Optional
   # org.geotools.util.logging.MonolineFormatter.time = HH:mm:ss.SSS
   # org.geotools.util.logging.MonolineFormatter.source = class:short



See the **MonolineFormatter** javadoc for details.

Reload4J Interoperability
^^^^^^^^^^^^^^^^^^^^^^^^^

The logging output can also be redirected Log4J 1 API framework if the following code is invoked once at application starting time:

.. code-block:: java

   Logging.ALL.setLoggerFactory("org.geotools.util.logging.Log4JLoggerFactory");

With the Log4J 1 library reaching end of life the the API is provided by Reload4J.

Logback interoperability
^^^^^^^^^^^^^^^^^^^^^^^^

The logging output can also be redirected to Logback (via SL4J API):

.. code-block:: java

   Logging.ALL.setLoggerFactory("org.geotools.util.logging.LogbackLoggerFactory");

Log4J 2 Interoperability
^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: java

   Logging.ALL.setLoggerFactory("org.geotools.util.logging.Log4J2LoggerFactory");

Commons Logging Interoperability
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The logging output can also be redirected to commons-logging:

.. code-block:: java

   Logging.ALL.setLoggerFactory("org.geotools.util.logging.CommonsLoggerFactory");

Why not common-logging directly?
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

GeoTools provides its own system to bridge to different logging libraries rather than use commons-logging, Log4J, or SLF4J directly.

The common-logging API is little more than a set of println functions with name (info, trace, debug, etc.). Java logging API provides the same convenience methods, but is also richer. We use some of its extra capabilities in GeoTools code base:

* ResourceBundle support for localization.
* Logging of stack traces.
* Information on source class and method names.
* Information about which thread produced the logging.
* Can be used through Java Monitoring and Management system.

Log4J 1 offered similar functionalities with a wider range of handler implementations. On the other hand, Java logging is more closely tied to the JVM, which avoid some ClassLoader problems that prevent usage of Log4J in some environments.

We are not claiming that Java logging in superior to Log4J, neither we are forcing peoples to use Java logging. We push for usage of Java logging API, which may very well redirect to Log4J under the hood through java.util.logging.Log4JLoggerFactory implementations.

Commons-logging is widely used in server containers, but other communities like scientists face a different picture. For example the NetCDF library developed by the University Corporation for Atmospheric Research (UCAR) uses SLF4J, yet another logging framework that aims to be a replacement for commons-logging.
