package it.personalfinance.finance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.personalfinance.finance.entity.Expense;
import it.personalfinance.finance.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")


public class ExpenseController {
	
	private final ExpenseService expenseService;
	
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@GetMapping
	//Ritorna tutte le spese
	public List<Expense> findAll(){
		return expenseService.findAll();
	}
	
	@GetMapping(path="/health")
	//Mini run solo per verificare che il microservizio parta
	public String run() {
		return "Finance_Microservice_is_running!";
	}
	
	@GetMapping(path="/{id}")
	//recupera una spesa specifica
	public Optional<Expense> findById(@PathVariable String id) {
		return expenseService.findById(id);
	}
	
	@PutMapping(path="/{id}")
	//Modifica una spesa
	public Expense update(@PathVariable String id, @RequestBody Expense expense) {
		return expenseService.update(id, expense);
	}
	
	@DeleteMapping(path="/{id}")
	public String delete(@PathVariable String id) {
		return expenseService.delete(id);
	}
	
	@PostMapping(path="/save")
	//Salva una nuova spesa e ne ritorna gli attributi aggiunti
	public Expense save(@RequestBody Expense expense) {
		return expenseService.save(expense);
	}
	
}
