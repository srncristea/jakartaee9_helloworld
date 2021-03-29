package ro.sxntech.java.pocs.jee9.helloworld.service.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Setter
    protected Long id;

    @Setter
    @Getter
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Setter
    @Getter
    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Version
    private Integer version;
}
