package org.springframework.samples.SevenIslands.achievement;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.SevenIslands.model.NamedEntity;
import org.springframework.samples.SevenIslands.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="achievements", uniqueConstraints={ @UniqueConstraint(columnNames={"min_value", "parameter"})})
public class Achievement extends NamedEntity {
    
    @Version
	private Integer version;

    @Column(name="name")
    @NotEmpty
    private String name;
    
    @Column(name="description")
    @NotEmpty
    private String description;
    
    @Column(name="icon")
    @NotEmpty
    private String icon;
    
    @Column(name="min_value")
    @NotNull
    private Integer minValue;
    
    @Column(name="achievement_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ACHIEVEMENT_TYPE achievementType;
    
    @Column(name="parameter")
    @Enumerated(EnumType.STRING)
    @NotNull
    private PARAMETER parameter;

	@ManyToMany(mappedBy = "achievements")
	private List<Player> players;

    
}
