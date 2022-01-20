package org.springframework.samples.SevenIslands.util;
import org.springframework.samples.SevenIslands.user.User;

public interface UserBuilderInterface {
    UserBuilderInterface setUsername(String username);
    UserBuilderInterface setPassword(String password);
    UserBuilderInterface setEnabled(Boolean enabled);
    User build();
    
}
