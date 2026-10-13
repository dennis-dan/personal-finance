package it.personalfinance.finance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.personalfinance.finance.dto.IncomeRequestDto;
import it.personalfinance.finance.dto.IncomeResponseDto;
import it.personalfinance.finance.service.IncomeOrchestrationService;

@RestController
@RequestMapping("/api/incomes")
public class IncomeApi {
	
	private final IncomeOrchestrationService service;
	
	public IncomeApi(IncomeOrchestrationService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<IncomeResponseDto> findAll(){
		return service.findAll();
	}
	
	@GetMapping(path="/{id}")
	//recupera una spesa specifica
	public IncomeResponseDto findById(@PathVariable String id) {
		return service.findById(id);
	}
	
	@PutMapping(path="/{id}")
	//Modifica una spesa
	public IncomeResponseDto update(@PathVariable String id, @RequestBody IncomeRequestDto income) {
		return service.update(id, income);
	}
	
	@DeleteMapping(path="/{id}")
	public String delete(@PathVariable String id) {
		return service.delete(id);
	}
	
	@PostMapping(path="/save")
	//Salva una nuova spesa e ne ritorna gli attributi aggiunti
	public IncomeResponseDto save(@RequestBody IncomeRequestDto income) {
		return service.save(income);
	}
}
