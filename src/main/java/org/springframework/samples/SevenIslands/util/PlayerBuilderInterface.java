package org.springframework.samples.SevenIslands.util;

import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.user.User;

public interface PlayerBuilderInterface {
    PlayerBuilderInterface setFirstName(String firstName);
    PlayerBuilderInterface setSurname(String surnName);
    PlayerBuilderInterface setEmail(String email);
    PlayerBuilderInterface setProfilePhoto(String profilePhoto);
    PlayerBuilderInterface setUser(User user);
    Player build();
}
