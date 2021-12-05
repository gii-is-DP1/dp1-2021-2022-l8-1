package org.springframework.samples.SevenIslands.statistic;

import org.springframework.samples.SevenIslands.player.Player;

public class PlayerWithStatistics {
    public Player player;
    public Integer parameter = 0;

    public PlayerWithStatistics() {
        this.player = new Player();
    }

    public PlayerWithStatistics(Player player, Long parameter) {
        this.player = player;
        this.parameter = parameter.intValue();
    }

}
