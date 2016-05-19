package com.arhs.infinispan.client;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.marshall.core.JBossMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by costajo on 11/05/2016.
 */

@Slf4j
public class ClientApp {

    public static void main(final String[] args) {
        ClientApp client = null;
        client = new ClientApp();
        client.run();

    }


    public void run() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.marshaller(new JBossMarshaller());

        builder.addServer().host("127.0.0.1")
                .port(new Integer(11222));

        RemoteCacheManager rcm = new RemoteCacheManager();
        RemoteCache remoteCache = rcm.getCache();

        Map<String, String> params = new HashMap<String, String>();

        String result = (String) remoteCache.execute("remote-task", params);

        log.info("Finished to call remote task ");

        result = (String) remoteCache.execute("remote-task2", params);

        log.info("Finished to call remote task 2 ");

        rcm.stop();
    }


}
