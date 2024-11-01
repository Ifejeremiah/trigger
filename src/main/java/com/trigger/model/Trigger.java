package com.trigger.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Trigger {
    @NotBlank(message = "Ref is required")
    @Length(min = 10, max = 30)
    private String ref;
}
