package org.yossy.demo.presentation.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {
    @NotBlank(message = "名前は必須入力です。")
    @Length(max = 60, message = "名前は60文字以内で入力してください。")
    private String name;
    @Email(message = "メールアドレス形式で入力してください。")
    @Length(max = 120, message = "メールアドレスは120文字以内で入力してください。")
    private String email;
    @NotBlank(message = "パスワードは必須入力です。")
    @Length(min = 8, message = "パスワードは8文字以上で入力してください。")
    private String password;
}
