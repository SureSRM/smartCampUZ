package es.unizar.smartcampuz.model.user;

import es.unizar.smartcampuz.application.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, org.hibernate.cfg.Configuration.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Before
    public void before() {
        User u = new User("777@unizar.es", "Seven", "pass");
        repo.save(u);
    }

    @Test @Transactional
    public void findByEmail() throws Exception {
        User u;

        u = repo.findByEmail("");
        assertEquals(null, u);

        u = repo.findByEmail("wrong");
        assertEquals(null, u);

        u = repo.findByEmail("test");
        assertEquals("test@unizar.es", u.getEmail());

    }

    @Test
    public void findByName() throws Exception {

    }

}
