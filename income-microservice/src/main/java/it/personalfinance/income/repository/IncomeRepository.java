package it.personalfinance.income.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import it.personalfinance.income.entity.Income;

public interface IncomeRepository extends MongoRepository<Income,String> {
//	Operazione							Endpoint 
//	Recuperare tutte le entrate			GET /api/incomes
//	Recuperare un'entrata per ID		GET /api/incomes/{id}
//	Registrare una nuova entrata		POST /api/incomes
//	Modificare un'entrata				PUT /api/incomes/{id}
//	Eliminare un'entrata				DELETE /api/incomes/{id}
}
