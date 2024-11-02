package com.trigger.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * GitHub push webhook event <a href="https://gist.github.com/walkingtospace/0dcfe43116ca6481f129cdaa0e112dc4">payload</a>
 */
@Getter
@Setter
public class Trigger {
    @NotBlank(message = "Ref is required")
    @Length(min = 3, max = 30)
    private String ref;
    private Repository repository;
}
