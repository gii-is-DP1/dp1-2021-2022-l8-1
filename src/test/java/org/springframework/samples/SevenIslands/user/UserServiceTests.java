package org.springframework.samples.SevenIslands.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTests {
    
    @Autowired	
	UserService userService;
    
    @Autowired
    PlayerService playerService;

    @Autowired
    AuthoritiesService auService;


}