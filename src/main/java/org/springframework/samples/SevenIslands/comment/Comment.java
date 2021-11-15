package org.springframework.samples.SevenIslands.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="comments")
public class Comment extends BaseEntity{
    @NotEmpty
    @Column(name="message")
    private String message;

    @Column(name="publication_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
     private LocalDateTime publicationDate = LocalDateTime.now();


    
}
