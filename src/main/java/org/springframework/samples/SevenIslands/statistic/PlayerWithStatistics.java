package org.springframework.samples.SevenIslands.statistic;

import org.springframework.samples.SevenIslands.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerWithStatistics {
    
    public Integer id = 0;
    public String username = "";
    public String profilePhoto = "";
    public Integer parameter = 0;

    public PlayerWithStatistics() {
    }

    public PlayerWithStatistics(Player player, Long parameter) {
        this.id = player.getId();
        this.username = player.getUser().getUsername();
        this.profilePhoto = player.getProfilePhoto();
        this.parameter = parameter.intValue();
    }

}
