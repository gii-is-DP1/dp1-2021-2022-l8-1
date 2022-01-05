package org.springframework.samples.SevenIslands.auditing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AuditingTests {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;


    @Disabled
    @Test
	public void shouldSaveUserChanges(){
 
		Player player = new Player();
        player.setFirstName("Antonio");
        player.setSurname("García");
        player.setEmail("antoniogar@gmail.com");
        player.setProfilePhoto("www.foto.png");

        Player player2 = new Player();
        player2.setFirstName("Antonio2");
        player2.setSurname("García2");
        player2.setEmail("antonio2gar@gmail.com");
        player2.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("usuario1");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        User user2 = new User();
        user2.setUsername("antoniog111");
        user2.setPassword("4G4rc14!1234");
        user2.setEnabled(true);

        player.setUser(user);
        player2.setUser(user2);
    
        
        playerService.save(player);
        playerService.savePlayer(player2);

        entityManager.persist(player);
        entityManager.flush();

        Player p  = playerService.findPlayerById(player.getId()).get();

        p.setEmail("newemail@email.com");

        playerService.save(p);

        entityManager.merge(p);

        //entityManager.getTransaction().commit();

        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List revisions = auditReader.createQuery()
                .forRevisionsOfEntity(Player.class, true)
                .getResultList();


        // Player p2  = playerService.findPlayerById(13).get();
        
        

        int actions = playerService.getAuditPlayers2().size();
        
        assertEquals(3, revisions.size());
 
	}

}
