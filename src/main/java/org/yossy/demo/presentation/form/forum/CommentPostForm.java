package org.yossy.demo.presentation.form.forum;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostForm {
    @NotBlank
    @Length(max = 140)
    private String comment;
    private Long entryId;
}
