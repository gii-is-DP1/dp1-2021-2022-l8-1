package org.springframework.samples.SevenIslands.forum;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.model.NamedEntity;
import org.springframework.samples.SevenIslands.topic.Topic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="forums")
public class Forum extends NamedEntity{
    @NotEmpty
    @Column(name = "description")   
    private String description;

    @OneToMany
    @JoinTable(name = "forums_topics", joinColumns = @JoinColumn(name = "forum_id"),
			inverseJoinColumns = @JoinColumn(name = "topic_id"))
	private Collection<Topic> topics;
    
}
