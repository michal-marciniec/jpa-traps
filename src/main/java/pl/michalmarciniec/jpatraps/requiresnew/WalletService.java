package pl.michalmarciniec.jpatraps.requiresnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final PersonRepository personRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository, PersonRepository personRepository) {
        this.walletRepository = walletRepository;
        this.personRepository = personRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Wallet createWalletAndAttachToPerson(long personId) {
        Wallet emptyWallet = new Wallet();
        walletRepository.save(emptyWallet);

        Person person = personRepository.findById(personId).orElseThrow();
        person.setWallet(emptyWallet);

        return emptyWallet;
    }

}
