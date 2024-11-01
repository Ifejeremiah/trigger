package com.trigger.web;

import com.trigger.model.Response;
import com.trigger.model.Trigger;
import com.trigger.service.TriggerService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/triggers")
public class TriggerController {
    TriggerService triggerService;

    public TriggerController(TriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> trigger(@Validated @RequestBody Trigger trigger) {
        triggerService.trigger(trigger);
        return new Response<>("Trigger successful");
    }
}
