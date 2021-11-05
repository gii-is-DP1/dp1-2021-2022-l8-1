package org.springframework.samples.petclinic.comment;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Data;

@Data
@Entity
@Table(name="comments")
public class Comment extends BaseEntity{
    @NotEmpty
    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
     private LocalDateTime publicationDate = LocalDateTime.now();


    
}
