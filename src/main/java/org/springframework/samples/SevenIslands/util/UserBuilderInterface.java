package org.springframework.samples.SevenIslands.util;
import org.springframework.samples.SevenIslands.user.User;

public interface UserBuilderInterface {
    UserBuilderInterface setUsername(String username);
    UserBuilderInterface setPassword(String password);
    UserBuilderInterface setEnabled(Boolean enabled);
    User build();

   /* Player player = new Player();
    player.setFirstName("Antonio");
    player.setSurname("García");
    player.setEmail("antoniogar@gmail.com");
    player.setProfilePhoto("www.foto.png");

    Player player1 = new Player();
    player1.setFirstName("Antonio1");
    player1.setSurname("García1");
    player1.setEmail("antonio1gar@gmail.com");
    player1.setProfilePhoto("www.foto.png");

    User user = new User();
    user.setUsername("antoniog11");
    user.setPassword("4G4rc14");
    user.setEnabled(true);

    User user1 = new User();
    user1.setUsername("antoniog111");
    user1.setPassword("4G4rc14");
    user1.setEnabled(true);

    player.setUser(user);
    player1.setUser(user1);*/
    
}
