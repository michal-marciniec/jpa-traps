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
public class WalletServiceTest {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    @Test
    public void shouldCreateAndAddEmptyWalletToPerson() {
        // when
        long walletId = walletService.createWallet().getId();

        // then
        Optional<Wallet> dbWallet = walletRepository.findById(walletId);
        assertThat(dbWallet).isPresent();
        assertThat(dbWallet.get().getId()).isNotNull();
        assertThat(dbWallet.get().getAmount()).isZero();
    }

}