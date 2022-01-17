package org.springframework.samples.SevenIslands.user;

import java.util.Set;

import org.aspectj.weaver.EnumAnnotationValue;
import org.springframework.samples.SevenIslands.util.UserBuilderInterface;

public class UserBuilder implements UserBuilderInterface{
    private String username;
    private String password;
    private Boolean enabled;

    @Override
    public UserBuilder setUsername(String username){
        this.username = username;
        return this;
    }

    @Override
    public UserBuilder setPassword(String password){
        this.password=password;
        return this;
    }

    @Override
    public UserBuilder setEnabled(Boolean enabled){
        this.enabled=enabled;
        return this;
    }

    @Override
    public User build(){
        return new User(username, password, enabled);
    }

    



   
    


    

    
    
}
