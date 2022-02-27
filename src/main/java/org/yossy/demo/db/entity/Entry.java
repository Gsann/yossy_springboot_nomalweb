package org.yossy.demo.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "entry")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry implements Serializable {
    public Entry(String name, String userName, String[] roles, Boolean enable) {
        this.name = name;
        this.userName = userName;
        this.roles = roles;
        this.enable = enable;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long id;
    @Column(name = "name", length = 60, nullable = false)
    private String name;
    @Column(name = "user_name", length = 60, nullable = false)
    private String userName;
    @Column(name = "roles", length = 120)
    private String[] roles;
    @Column(name = "enable_flag", nullable = false)
    private Boolean enable;
    @Column(name = "create_dt", nullable = false)
    private LocalDateTime createDt;
    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @PrePersist
    public void onPrePersist() {
        setCreateDt(LocalDateTime.now());
        setUpdateDt(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdateDt(LocalDateTime.now());
    }

}
