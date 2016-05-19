TASKS API POC with INFINISPAN DEPLOYMENT
--------------------------------
Compile using maven:
mvn clean package

Copy deployment "remote-task.jar" to Data grid or Infinispan server deployment folder "%JBOSS_HOME%/standalone/deployments". Optionally, can run install target, but need to change pom.xml variable infinispan.path.````

To install the lib. Copy module folder to %JBOSS_HOME%, that contains the definition to be loaded on infinispan commons. And copy target/module to %JBOSS_HOME%, that contains the new module.

Add the following to the cluster cache configuration (for example standalone.xml on "%JBOSS_HOME%/standalone/configuration):
                <distributed-cache name="MODEL_CUSTOMER" owners="1" segments="20" mode="SYNC" remote-timeout="30000" start="EAGER">
                    <locking striping="false" acquire-timeout="30000" concurrency-level="1000"/>
                    <transaction mode="NONE"/>
                    <compatibility enabled="true"/>
                </distributed-cache>
