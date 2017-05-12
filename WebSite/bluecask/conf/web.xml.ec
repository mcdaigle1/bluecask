<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE web-app
    PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>
    <!-- We are the one and only ERC (G2) broker... -->
    <display-name>ERCBroker</display-name>
    <description>GoToMyPC broker</description>

    <!-- Define ERCBroker/Spine servlets -->
    <!-- Note that Tomcat insists on the following order: filter, servlet, servlet-mapping -->

    <!-- System servlet is loaded at startup, needs config file as parameter -->
    <system-property javax.xml.parsers.DocumentBuilderFactory="org.apache.xerces.jaxp.DocumentBuilderFactoryImpl"/>
    <system-property javax.xml.parsers.SAXParserFactory="org.apache.xerces.jaxp.SAXParserFactoryImpl"/>
    <system-property javax.xml.transform.TransformerFactory="org.apache.xalan.processor.TransformerFactoryImpl"/>

    <!-- System servlet is loaded at startup, needs config file as parameter -->
    <servlet>
      <servlet-name>system</servlet-name>
      <servlet-class>com.ec.spine.servlets.SystemServlet</servlet-class>
      <init-param>
        <param-name>configfile</param-name>
        <param-value>/broker/ERCBroker/current/conf/broker.properties</param-value>
      </init-param>
      <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- Dispatch servlet (handling all pages/flows) -->
    <servlet>
      <servlet-name>dispatch</servlet-name>
      <servlet-class>com.ec.ercbroker.servlets.DispatchServlet</servlet-class>
    </servlet>

    <!-- AdTracker servlet (tracks visits/clicks) -->
    <servlet>
      <servlet-name>adtracker</servlet-name>
      <servlet-class>com.ec.ercbroker.servlets.AdTrackerServlet</servlet-class>
    </servlet>

    <!-- Jedi servlet (handles comm server requests) -->
    <servlet>
      <servlet-name>jedi</servlet-name>
      <servlet-class>com.ec.csmanager.CommunicationServlet</servlet-class>
    </servlet>

    <!-- Regular reqest dispatch. -->
    <servlet-mapping>
        <servlet-name>dispatch</servlet-name>
        <url-pattern>/dispatch/*</url-pattern>
    </servlet-mapping>

    <!-- For tracking of external links. -->
    <servlet-mapping>
        <servlet-name>adtracker</servlet-name>
        <url-pattern>/tr/*</url-pattern>
    </servlet-mapping>

    <!-- For tracking of internal clicks (deprecated). -->
    <servlet-mapping>
        <servlet-name>adtracker</servlet-name>
        <url-pattern>/c/*</url-pattern>
    </servlet-mapping>

    <!-- For portals. -->
    <servlet-mapping>
        <servlet-name>adtracker</servlet-name>
        <url-pattern>/p/*</url-pattern>
    </servlet-mapping>

    <!-- comm server requests. -->
    <servlet-mapping>
        <servlet-name>jedi</servlet-name>
        <url-pattern>/Jedi</url-pattern>
    </servlet-mapping>

    <!-- all other servlet requests (no longer mapped by default in 4.1.12) -->
    <servlet-mapping>
        <servlet-name>invoker</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>

</web-app>
