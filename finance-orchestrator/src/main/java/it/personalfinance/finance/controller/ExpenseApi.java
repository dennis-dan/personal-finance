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

import it.personalfinance.finance.dto.ExpenseRequestDto;
import it.personalfinance.finance.dto.ExpenseResponseDto;
import it.personalfinance.finance.service.ExpenseOrchestrationService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseApi {
	
	private final ExpenseOrchestrationService service;
	
	public ExpenseApi(ExpenseOrchestrationService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<ExpenseResponseDto> findAll(){
		return service.findAll();
	}
	
	@GetMapping(path="/health")
	//Mini run solo per verificare che il microservizio parta
	public String run() {
		return "Finance_Microservice_is_running!";
	}
	
	@GetMapping(path="/{id}")
	//recupera una spesa specifica
	public ExpenseResponseDto findById(@PathVariable String id) {
		return service.findById(id);
	}
	
	@PutMapping(path="/{id}")
	//Modifica una spesa
	public ExpenseResponseDto update(@PathVariable String id, @RequestBody ExpenseRequestDto expense) {
		return service.update(id, expense);
	}
	
	@DeleteMapping(path="/{id}")
	public String delete(@PathVariable String id) {
		return service.delete(id);
	}
	
	@PostMapping(path="/save")
	//Salva una nuova spesa e ne ritorna gli attributi aggiunti
	public ExpenseResponseDto save(@RequestBody ExpenseRequestDto expense) {
		return service.save(expense);
	}
}
