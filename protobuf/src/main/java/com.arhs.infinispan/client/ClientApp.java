package com.arhs.infinispan.client;

import com.arhs.infinispan.model.CustomerFinal;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import javax.management.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by costajo on 11/05/2016.
 */

@Slf4j
public class ClientApp {

    public static void main(final String[] args) {
        ClientApp client = null;
        client = new ClientApp();
        try {
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run() throws IOException {

        String host = "localhost";
        Integer port = 11222;

        org.infinispan.client.hotrod.configuration.ConfigurationBuilder cb
                = new org.infinispan.client.hotrod.configuration.ConfigurationBuilder();
        cb
                .tcpNoDelay(false)
                .tcpKeepAlive(true)
                .connectionPool()
                .numTestsPerEvictionRun(3)
                .timeBetweenEvictionRuns(3600000) // 1 hour time out
                .testOnBorrow(false)
                .testOnReturn(false)
                .testWhileIdle(true)
                .connectionTimeout(3600000) // 1 hour time out
                .socketTimeout(3600000) // 1 hour time out
                .maxRetries(0)
                .addServer()
                .host(host)
                .port(port)
                .marshaller(new ProtoStreamMarshaller());
        RemoteCacheManager remoteCacheManager = new RemoteCacheManager(cb.build());

        SerializationContext srcCtx =
                ProtoStreamMarshaller.getSerializationContext(remoteCacheManager);

        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String schema = protoSchemaBuilder
                .fileName("customer.proto")
                .packageName("proximus")
                .addClass(CustomerFinal.class)
                .build(srcCtx);

        RemoteCache<String, String> metadataCache = remoteCacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
        metadataCache.put("customer.proto", schema);

        log.info("Can marshall '{}'",srcCtx.canMarshall(CustomerFinal.class));

        log.info(schema);

        remoteCacheManager.getCache("PROTOTEST").clear();

        CustomerFinal customerFinal1 = new CustomerFinal();
        customerFinal1.setCustomerGroupId("g1");
        customerFinal1.setCustomerId("id1");
        remoteCacheManager.getCache("PROTOTEST").put(customerFinal1.getCustomerId(), customerFinal1);

        CustomerFinal customerFinal2 = new CustomerFinal();
        customerFinal2.setCustomerGroupId("g2");
        customerFinal2.setCustomerId("id2");
        remoteCacheManager.getCache("PROTOTEST").put(customerFinal2.getCustomerId(), customerFinal2);

        CustomerFinal customerFinal3 = new CustomerFinal();
        customerFinal3.setCustomerGroupId("g2");
        customerFinal3.setCustomerId("id3");
        remoteCacheManager.getCache("PROTOTEST").put(customerFinal3.getCustomerId(), customerFinal3);

        QueryFactory queryFactory = Search.getQueryFactory(remoteCacheManager.getCache("PROTOTEST"));
        org.infinispan.query.dsl.Query build = queryFactory.from(CustomerFinal.class)
                .having("customerGroupId").like("g2%").toBuilder()
                .build();

        List<CustomerFinal> results = build.list();

        log.info("search returned {}",results.size());


        for (CustomerFinal c : results) {
            log.info("Found customer {}", c.getCustomerId());
        }

        remoteCacheManager.stop();


    }


}
