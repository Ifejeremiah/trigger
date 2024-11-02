package com.trigger.service;

import com.trigger.model.Trigger;
import com.trigger.processor.TriggerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TriggerService {
    TaskExecutor executor;
    @Value("${DEFAULT.BRANCH}")
    private String branch;

    public TriggerService(TaskExecutor executor) {
        this.executor = executor;
    }

    public void trigger(Trigger trigger) {
        log.info("Trigger listened...");
        try {
            if (trigger.getRef().equalsIgnoreCase(branch)) {
                log.info("Target branch: {}", trigger.getRef());
                log.info("Proceed to execute CI...");

                TriggerProcessor processor = new TriggerProcessor(trigger);
                executor.execute(processor);
            }
        } catch (Exception error) {
            log.error("Error running trigger", error);
            throw error;
        }
    }
}
