package org.springframework.samples.petclinic.topic;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.comment.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="topics")
public class Topic extends NamedEntity{
    @NotEmpty
    @Column(name="description")
    private String description;

    @OneToMany
    @JoinTable(name = "topics_comments", joinColumns = @JoinColumn(name = "topic_id"),
			inverseJoinColumns = @JoinColumn(name = "comment_id"))
	private Collection<Comment> comments;
}
