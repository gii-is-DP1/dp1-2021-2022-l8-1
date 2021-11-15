package org.springframework.samples.SevenIslands.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="generals")
public class General extends BaseEntity {

    @NotEmpty
    @Column(name = "total_games")
    private String totalGames;
    
    @NotEmpty
    @Column(name = "total_duration_all_games")
    private Integer totalDurationAllGames; //In seconds
}
