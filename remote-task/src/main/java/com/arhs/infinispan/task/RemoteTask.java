package com.arhs.infinispan.task;

import com.arhs.infinispan.task.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;

import java.math.BigInteger;

@Slf4j
public class RemoteTask implements ServerTask<String> {


    public static final String TASK_NAME = "remote-task";

    private TaskContext taskContext;

    public String call() throws Exception {

        log.info("--------- Starting remote-task ---------");
        Cache<String, Customer> cache = taskContext
                .getCache().get()
                .getCacheManager()
                .getCache("MODEL_CUSTOMER", false);
        cache.clear();

        Customer customer = new Customer();
        customer.setId("ID_1");
        customer.setName("NAME_1");
        cache.put(customer.getId(), customer);

        customer = new Customer();
        customer.setId("ID_2");
        customer.setName("NAME_2");
        cache.put(customer.getId(), customer);

        log.info("--------- Finished remote-task ---------");
        return "Success";
    }

    public void setTaskContext(TaskContext taskContext) {
        this.taskContext = taskContext;
    }

    public String getName() {
        return TASK_NAME;
    }

    public TaskExecutionMode getExecutionMode() {
        return TaskExecutionMode.ONE_NODE;
    }

}
