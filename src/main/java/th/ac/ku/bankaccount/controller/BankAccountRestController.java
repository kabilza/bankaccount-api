package th.ac.ku.bankaccount.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.bankaccount.data.BankAccountRepository;
import th.ac.ku.bankaccount.model.BankAccount;
import th.ac.ku.bankaccount.model.Money;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountRestController {

    private BankAccountRepository repository;

    public BankAccountRestController(BankAccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BankAccount> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public BankAccount getOne(@PathVariable int id) {
        return repository.findById(id).get();
    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        BankAccount record = repository.save(bankAccount);
        repository.flush();
        return record;
    }

    @PutMapping("/{id}")
    public BankAccount update(@PathVariable int id,
                              @RequestBody BankAccount bankAccount) {
        BankAccount record = repository.findById(id).get();
        record.setBalance(bankAccount.getBalance());
        repository.save(record);
        return record;
    }

    @GetMapping("/customer/{customerId}")
    public List<BankAccount> getAllCustomerId(@PathVariable int customerId) {
        return repository.findByCustomerId(customerId);
    }

    @DeleteMapping("/{id}")
    public BankAccount delete(@PathVariable int id) {
        BankAccount record = repository.findById(id).get();
        repository.deleteById(id);
        return record;
    }

    @PutMapping("/deposit/{id}")
    public BankAccount deposit(@PathVariable int id,
                               @RequestBody Money money) {
        if (money.getMoney() <= 0) {
            throw new Error("Amount to be deposit should be positive");
        } else {
            System.out.println("Deposit");
            BankAccount record = repository.findById(id).get();
            double balance = record.getBalance() + money.getMoney();
            record.setBalance(balance);
            repository.save(record);
            return record;
        }
    }

    @PutMapping("/withdraw/{id}")
    public BankAccount withdraw(@PathVariable int id,
                                @RequestBody Money money) {
        if (money.getMoney() <= 0) {
            throw new Error("Amount to be deposit should be positive");
        } else {
            System.out.println("Withdraw");
            BankAccount record = repository.findById(id).get();
            double balance = record.getBalance() - money.getMoney();
            record.setBalance(balance);
            repository.save(record);
            return record;
        }
    }
}

