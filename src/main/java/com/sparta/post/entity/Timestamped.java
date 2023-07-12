package com.sparta.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 부모 클래스를 지정하여 상속 관계 매핑을 할 때 사용
/*
@MappedSuperclass 어노테이션과 함께 @EntityListeners 를 사용하는 경우,
해당 엔티티 클래스와 그 하위 클래스들에 대한 이벤트 리스너가 등록됩니다.
AuditingEntityListener : 엔티티의 생성일자(created date)와 수정일자(last modified date)를 자동으로 관리하기 위해 사용
*/
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    //updatable 속성은 엔티티의 수정이 이루어질 때 해당 필드가 업데이트 가능한지를 지정
    //updatable = false로 설정하면, 이 필드는 생성 시에만 값을 가지고 수정이 불가능하도록 설정
    @Column(name = "created_data", updatable = false)
    // @Temporal : 날짜 및 시간 타입의 필드를 어떤 형식으로 매핑할 것인지를 지정
    // TemporalType.TIMESTAMP : 필드가 일시 및 시간 정보를 나타내는 타입
    // 이렇게 지정하면 JPA가 필드 값을 데이터베이스의 타임스탬프 형식에 맞게 저장하거나 조회할 수 있다.
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}