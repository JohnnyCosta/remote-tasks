package com.arhs.infinispan.task;

import com.arhs.infinispan.task.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;

@Slf4j
public class RemoteTask2 implements ServerTask<String> {


    public static final String TASK_NAME = "remote-task2";

    private TaskContext taskContext;

    public String call() throws Exception {

        log.info("--------- Starting remote-task2 ---------");
        Cache<String, Customer> cache = taskContext
                .getCache().get()
                .getCacheManager()
                .getCache("MODEL_CUSTOMER", false);

        Customer customer = cache.get("ID_1");
        log.info(customer.getName());


        log.info("--------- Finished remote-task2 ---------");
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
