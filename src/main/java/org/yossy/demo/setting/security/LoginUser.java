package org.yossy.demo.setting.security;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.yossy.demo.db.entity.User;

public class LoginUser extends org.springframework.security.core.userdetails.User {
  // DBより検索したUserエンティティ
  // アプリケーションから利用されるのでフィールドに定義
  private User user;

  /**
   * データベースより検索したuserエンティティよりSpring Securityで使用するユーザー認証情報の
   * インスタンスを生成
   *
   * @param user userエンティティ
   */
  public LoginUser(User user) {
    super(user.getEmail(), user.getPassword(), user.getEnable(), true, true,
        true, convertGrantedAuthorities(user.getRoles()));
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public String getUserName() {
    return this.user.getName();
  }

  public String getUserEmail() {
    return this.user.getEmail();
  }

  /**
   * カンマ区切りのロールをSimpleGrantedAuthorityのコレクションへ変換
   *
   * @param strings カンマ区切りのロール
   * @return SimpleGrantedAuthorityのコレクション
   */
  static Set<GrantedAuthority> convertGrantedAuthorities(String[] strings) {
    if (strings == null || strings.length == 0) {
      return Collections.emptySet();
    }
    Set<GrantedAuthority> authorities = Stream.of(strings)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
    return authorities;
  }
}
