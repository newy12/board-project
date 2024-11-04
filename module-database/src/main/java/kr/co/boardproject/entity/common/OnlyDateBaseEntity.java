package kr.co.boardproject.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class OnlyDateBaseEntity {
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private String createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private String updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = formatDate(LocalDateTime.now());
        updatedAt = formatDate(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = formatDate(LocalDateTime.now());
    }

    // LocalDateTime을 원하는 포맷의 문자열로 변환
    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
