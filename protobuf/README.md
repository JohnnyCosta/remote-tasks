Add to infinispan configuration the cache definition:

            <replicated-cache name="PROTOTEST" mode="SYNC" remote-timeout="3600000">
                    <locking striping="false" acquire-timeout="30000" concurrency-level="1000"/>
                    <transaction mode="NONE"/>
                    <compatibility enabled="true"/>
					<indexing index="ALL">
						<property name="default.directory_provider">ram</property>
						<property name="lucene_version">LUCENE_CURRENT</property>
						<property name="default.indexmanager">near-real-time</property>
					</indexing>
                </replicated-cache>