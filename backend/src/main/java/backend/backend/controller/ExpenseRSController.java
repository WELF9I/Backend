package backend.backend.controller;

import backend.backend.entity.ExpenseEntity;
import backend.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/expenses")
@RestController
public class ExpenseRSController {

    @Autowired
    public ExpenseRepository expenseRepository;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ExpenseEntity> getAll() {
        return expenseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseEntity> getExpenseById(@PathVariable Long id) {
        Optional<ExpenseEntity> expenseOptional = expenseRepository.findById(id);
        return expenseOptional.map(expense -> new ResponseEntity<>(expense, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable Long id, @RequestBody ExpenseEntity updatedExpense) {
        Optional<ExpenseEntity> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            ExpenseEntity existingExpense = optionalExpense.get();
            existingExpense.setIdExp(id);
            existingExpense.setAmount(updatedExpense.getAmount());
            existingExpense.setDateExpense(updatedExpense.getDateExpense());
            existingExpense.setPdfFile(updatedExpense.getPdfFile());
            // Set other fields here
            expenseRepository.save(existingExpense);
            return new ResponseEntity<>("Expense updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Expense not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseEntity newExpense) {
        expenseRepository.save(newExpense);
        return new ResponseEntity<>("Expense added successfully", HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return new ResponseEntity<>("Expense with ID " + id + " deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Expense with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
