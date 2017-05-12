<!--
   - Resin 3.0 configuration file.
  -->
<resin xmlns="http://caucho.com/ns/resin"
       xmlns:resin="http://caucho.com/ns/resin/core">
  <!--
     - Logging configuration for the JDK logging API.
    -->
  <log name='' level='info' path='stdout:' timestamp='[%H:%M:%S.%s] '/>
  <log name='com.caucho.java' level='config' path='stdout:'
       timestamp='[%H:%M:%S.%s] '/>
  <log name='com.caucho.loader' level='config' path='stdout:'
       timestamp='[%H:%M:%S.%s] '/>

  <!--
     - For production sites, change dependency-check-interval to something
     - like 600s, so it only checks for updates every 10 minutes.
    -->
  <dependency-check-interval>2s</dependency-check-interval>

  <!--
     - You can change the compiler to "javac" or jikes.
     - The default is "internal" only because it's the most
     - likely to be available.
    -->
  <javac compiler="internal" args=""/>

  <!-- Security providers.
     - <security-provider>
     -    com.sun.net.ssl.internal.ssl.Provider
     - </security-provider>
    -->

  <!--
     - If starting bin/resin as root on Unix, specify the user name
     - and group name for the web server user.
     -
     - <user-name>resin</user-name>
     - <group-name>resin</group-name>
    -->

  <!--
     - Configures threads shared among all HTTP and SRUN ports.
    -->
  <thread-pool>
    <!-- Maximum number of threads. -->
    <thread-max>128</thread-max>

    <!-- Minimum number of spare connection threads. -->
    <spare-thread-min>25</spare-thread-min>
  </thread-pool>

  <!--
     - Configures the minimum free memory allowed before Resin
     - will force a restart.
    -->
  <min-free-memory>1M</min-free-memory>

  <server>
    <!-- adds all .jar files under the resin/lib directory -->
    <class-loader>
      <tree-loader path="$resin-home/lib"/>
    </class-loader>

    <character-encoding>utf-8</character-encoding>

    <!-- Configures the keepalive -->
    <keepalive-max>500</keepalive-max>
    <keepalive-timeout>120s</keepalive-timeout>

    <!-- The http port -->
    <http server-id="" host="*" port="8080"/>

    <!--
       - SSL port configuration:
       -
       - <http port="8443">
       -   <openssl>
       -     <certificate-file>keys/gryffindor.crt</certificate-file>
       -     <certificate-key-file>keys/gryffindor.key</certificate-key-file>
       -     <password>test123</password>
       -   </openssl>
       - </http>
      -->

    <!--
       - The local cluster, used for load balancing and distributed
       - backup.
      -->
    <cluster>
      <srun server-id="" host="127.0.0.1" port="6802" index="1"/>
    </cluster>

    <!--
       - Enables/disables exceptions when the browser closes a connection.
      -->
    <ignore-client-disconnect>true</ignore-client-disconnect>

    <!--
       - Enables the cache
      -->
    <cache path="cache" memory-size="10M"/>

    <!--
       - Enables periodic checking of the server status.
       -
       - With JDK 1.5, this will ask the JDK to check for deadlocks.
       - All servers can add <url>s to be checked.
      -->
    <ping>
      <!-- <url>http://localhost:8080/test-ping.jsp</url> -->
    </ping>

    <!--
       - Defaults applied to each web-app.
      -->
    <web-app-default>
      <!--
         - Sets timeout values for cacheable pages, e.g. static pages.
        -->
      <cache-mapping url-pattern="/" expires="5s"/>
      <cache-mapping url-pattern="*.gif" expires="60s"/>
      <cache-mapping url-pattern="*.jpg" expires="60s"/>

      <!--
         - Servlet to use for directory display.
        -->
      <servlet servlet-name="directory"
              servlet-class="com.caucho.servlets.DirectoryServlet"/>
    </web-app-default>

    <!--
       - Sample database pool configuration
       -
       - The JDBC name is java:comp/env/jdbc/test
       -
         <database>
           <jndi-name>jdbc/mysql</jndi-name>
           <driver type="org.gjt.mm.mysql.Driver">
             <url>jdbc:mysql://localhost:3306/test</url>
             <user></user>
             <password></password>
            </driver>
            <prepared-statement-cache-size>8</prepared-statement-cache-size>
            <max-connections>20</max-connections>
            <max-idle-time>30s</max-idle-time>
          </database>
      -->

    <!--
       - Default host configuration applied to all virtual hosts.
      -->
    <host-default>
      <class-loader>
        <compiling-loader path='webapps/WEB-INF/classes'/>
        <library-loader path='webapps/WEB-INF/lib'/>
      </class-loader>

      <!--
         - With another web server, like Apache, this can be commented out
         - because the web server will log this information.
        -->
      <access-log path='/broker/resin/current/logs/access.log' 
            format='%h %l %u %t "%r" %s %b "%{Referer}i" "%{User-Agent}i"'
            rollover-period='1W'/>

      <!-- creates the webapps directory for .war expansion -->
      <web-app-deploy path='webapps'/>

      <!-- creates the deploy directory for .ear expansion -->
      <ear-deploy path='deploy'>
        <ear-default>
          <!-- Configure this for the ejb server
             -
             - <ejb-server>
             -   <config-directory>WEB-INF</config-directory>
             -   <data-source>jdbc/test</data-source>
             - </ejb-server>
            -->
        </ear-default>
      </ear-deploy>

      <!-- creates the deploy directory for .rar expansion -->
      <resource-deploy path='deploy'/>

      <!-- creates a second deploy directory for .war expansion -->
      <web-app-deploy path='deploy'/>
    </host-default>

    <!-- includes the web-app-default for default web-app behavior -->
    <resin:import path="${resinHome}/conf/app-default.xml"/>

    <!-- configures the default host, matching any host name -->
    <host id=''>
      <document-directory>doc</document-directory>

      <!-- configures the root web-app -->
      <web-app id='/ercbroker'>
        <!-- adds xsl to the search path -->
        <class-loader>
          <simple-loader path="$host-root/xsl"/>
          <simple-loader path="/broker/ERCBroker/current/dist/WEB-INF/classes"/>
          <library-loader path="/broker/ERCBroker/current/dist/WEB-INF/lib"/>
        </class-loader>
        <config-file>/broker/ERCBroker/current/dist/WEB-INF/web.xml</config-file>

        <servlet-mapping url-pattern="/servlet/*" servlet-name="invoker"/>
      </web-app>
    </host>
  </server>
</resin>
