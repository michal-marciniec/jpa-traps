package pl.michalmarciniec.jpatraps.requiresnew;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WalletServiceTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    @Test
    public void shouldCreateAndAddEmptyWalletToPerson() {
        // given
        Person margaret = personRepository.save(new Person("Margaret"));

        // when
        long walletId = walletService.createWalletAndAttachToPerson(margaret).getId();

        // then
        Optional<Wallet> dbWallet = walletRepository.findById(walletId);
        Assertions.assertThat(dbWallet).isPresent();

        Optional<Person> dbMargaret = personRepository.findById(margaret.getId());
        Assertions.assertThat(dbMargaret).isPresent();

        Wallet margaretWallet = dbMargaret.get().getWallet();
        assertThat(margaretWallet).isNotNull();
        assertThat(margaretWallet.getId()).isNotNull();
        assertThat(margaretWallet.getAmount()).isZero();
    }

}