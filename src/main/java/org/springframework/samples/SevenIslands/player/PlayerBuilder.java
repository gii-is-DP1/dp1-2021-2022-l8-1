package org.springframework.samples.SevenIslands.player;

import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.util.PlayerBuilderInterface;

public class PlayerBuilder implements PlayerBuilderInterface{
    private String firstName;
    private String surname;
    private String email;
    private String profilePhoto;
    private User user;

    @Override
    public PlayerBuilderInterface setFirstName(String firstName) {
        this.firstName=firstName;
        return this;
    }
    @Override
    public PlayerBuilderInterface setSurname(String surname) {
        this.surname=surname;
        return this;
    }
    @Override
    public PlayerBuilderInterface setEmail(String email) {
        this.email=email;
        return this;
    }
    @Override
    public PlayerBuilderInterface setProfilePhoto(String profilePhoto) {
        this.profilePhoto=profilePhoto;
        return this;
    }
    @Override
    public PlayerBuilderInterface setUser(User user) {
        this.user = user;
        return this;
    }
    @Override
    public Player build() {
        return new Player(firstName, surname, email, profilePhoto, user);
    }

    
    
    
}
