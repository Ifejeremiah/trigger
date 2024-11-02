package com.trigger.processor;

import com.trigger.model.Repository;
import com.trigger.model.Trigger;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class TriggerProcessor implements Runnable {
    Trigger trigger;
    Repository repository;

    public TriggerProcessor(Trigger trigger) {
        this.trigger = trigger;
        this.repository = trigger.getRepository();
    }

    @Override
    public void run() {
        try {
            log.info("Repository name: {}", repository.getName());
            executeScript();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeScript() throws IOException {
        try {
            String[] cmdList = {"bash", "sync.sh", repository.getName()};
            Process p = Runtime.getRuntime().exec(cmdList);

            BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = is.readLine()) != null) {
                log.info(line);
            }

            log.info("Completed CI...");
        } catch (Exception error) {
            log.error("Error executing script", error);
            throw error;
        }
    }
}
