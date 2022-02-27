package org.yossy.demo.presentation.form.forum;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryPostForm {
    @NotBlank
    @Length(max = 60)
    private String entryName;
    @NotBlank
    @Length(max = 140)
    private String comment;
}
