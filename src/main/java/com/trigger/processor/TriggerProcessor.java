package com.trigger.processor;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class TriggerProcessor implements Runnable {
    String appName;

    public TriggerProcessor(String appName) {
        this.appName = appName;
    }

    @Override
    public void run() {
//        try {
        log.info("value of repository, {}", appName);
//            executeScript();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void executeScript() throws IOException {
        try {
            String[] cmdList = {"bash", "sync.sh", appName};
            Process p = Runtime.getRuntime().exec(cmdList);

            BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = is.readLine()) != null) {
                log.info(line);
            }

            log.info("Exit value: {}", p.exitValue());
            log.info("Completed CI...");
        } catch (Exception error) {
            log.error("Error executing script", error);
            throw error;
        }
    }
}
