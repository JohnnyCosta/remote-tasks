package com.arhs.infinispan.task.model;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

/**
 * Created by costajo on 25/05/2016.
 */
@Listener
public class PrintWhenAdded {
    @CacheEntryCreated
    public void print(CacheEntryCreatedEvent event) {
        System.out.println("||||||||||||||||||||||||||||||||||||||||||New entry " + event.getKey() + " created in the cache");
    }
}
