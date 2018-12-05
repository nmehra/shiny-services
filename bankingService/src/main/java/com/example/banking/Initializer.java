package com.example.banking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.banking.model.Account;
import com.example.banking.model.AccountRepository;
import com.example.banking.model.User;
import com.example.banking.model.UserRepository;
import com.netflix.config.ConfigurationManager;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Initializer(AccountRepository repository, UserRepository userRepository) {
        this.accountRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) {
    		//ConfigurationManager.getConfigInstance().setProperty("hystrix.command.Transfer.execution.isolation.thread.timeoutInMilliseconds", "500");
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(accountId ->
                accountRepository.save(new Account(accountId,1000000))
        );

        Account account = accountRepository.findByAccountId("Denver JUG");
        //User user = User.builder().name("abc").build();
        User user = userRepository.save(new User("nitin"));
        TreeSet<Account> accounts = new TreeSet(); 
        accounts.add(account);
        //user.setAccounts(accounts);
        account.setUser(user);
        //userRepository.save(user);
        accountRepository.save(account);
        
        //Account e = Account.builder().accountId("xyz").build();
        /*Event e = Event.builder().title("Full Stack Reactive")
                .description("Reactive with Spring Boot + React")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();
        djug.setEvents(Collections.singleton(e));
        repository.save(djug);
		*/
        System.out.println("printing all account names:" + account.getAccountId());
        //repository.findAll().forEach(System.out::println);
    }
}