package pl.michalmarciniec.jpatraps.requiresnew;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void shouldCreatePersonWithEmptyWallet() {
        // when
        long jeremyId = personService.createPerson("Jeremy");

        // then
        Optional<Person> jeremy = personRepository.findById(jeremyId);
        assertThat(jeremy).isPresent();
        Wallet jeremyWallet = jeremy.get().getWallet();
        assertThat(jeremyWallet.getId()).isNotNull();
        assertThat(jeremyWallet.getAmount()).isZero();
    }

}