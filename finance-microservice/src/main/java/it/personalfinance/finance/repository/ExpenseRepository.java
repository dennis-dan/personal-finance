package it.personalfinance.finance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.personalfinance.finance.entity.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
//  posso già usare i metodi di mongo, per cui non devo implementare nulla, per ora
//	save(expense) Salva una spesa o aggiorna quella esistente
//	findAll() Recupera tutte le spese
//	findById(id) Cerca una spesa tramite ID
//	deleteById(id) Elimina una spesa tramite ID
//	count() Conta i documenti
}
