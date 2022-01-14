package com.prisma.library.library.entity.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Setter
@MappedSuperclass
public abstract class BaseObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTimestamp;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateTimestamp;

    @PreUpdate
    public void preUpdate() {
        updateTimestamp = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            this.id = UUID.randomUUID();
        }
        if (Objects.isNull(createTimestamp)) {
            createTimestamp = LocalDateTime.now();
        }
        updateTimestamp = LocalDateTime.now();
    }
}
