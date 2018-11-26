package pl.michalmarciniec.jpatraps.requiresnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final WalletService walletService;

    @Autowired
    public PersonService(PersonRepository personRepository, WalletService walletService) {
        this.personRepository = personRepository;
        this.walletService = walletService;
    }

    @Transactional
    public long createPerson(String name, BigDecimal money) {
        Person person = new Person(name);
        personRepository.save(person);

        Wallet wallet = walletService.createWallet(money);
        if (wallet.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Initial amount of money cannot be less than zero");
        }

        person.setWallet(wallet);
        return person.getId();
    }

}
