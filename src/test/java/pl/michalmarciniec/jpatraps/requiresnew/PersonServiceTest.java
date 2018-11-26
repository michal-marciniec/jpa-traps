package pl.michalmarciniec.jpatraps.requiresnew;

import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Before
    public void setUp() {
        personRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void shouldCreatePersonWithInitialAmountOfMoney() {
        // when
        long jeremyId = personService.createPerson("Jeremy", BigDecimal.TEN);

        // then
        Optional<Person> jeremy = personRepository.findById(jeremyId);
        assertThat(jeremy).isPresent();
        Wallet jeremyWallet = jeremy.get().getWallet();
        assertThat(jeremyWallet).isNotNull();
        assertThat(jeremyWallet.getId()).isNotNull();
        assertThat(jeremyWallet.getAmount()).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(0.1D));
    }

    @Test
    public void shouldNotCreateAnythingWhenTryingToCreatePersonWithNegativeAmountOfMoney() {
        // when
        assertThatThrownBy(() -> personService.createPerson("Vince", BigDecimal.valueOf(-100.0D)))
                .isInstanceOf(RuntimeException.class);

        // then
        assertThat(personRepository.findAll()).isEmpty();
        assertThat(walletRepository.findAll()).isEmpty();
    }

}