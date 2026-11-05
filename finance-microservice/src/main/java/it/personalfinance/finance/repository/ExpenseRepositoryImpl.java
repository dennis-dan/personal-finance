package it.personalfinance.finance.repository;

import java.util.List;

import it.personalfinance.finance.dto.ExpenseSearchRequestDto;
import it.personalfinance.finance.entity.Expense;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom{
	private final MongoTemplate mongoTemplate;
	
	public ExpenseRepositoryImpl(MongoTemplate mongoTemplate) {
	    this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Expense> search(ExpenseSearchRequestDto dto) {
		Query query = new Query();
//		Criteria criteria = new Criteria();
		if (dto.getCategory() != null && !dto.getCategory().isBlank()) {
			query.addCriteria(Criteria.where("category").is(dto.getCategory()));
		}
		
		Criteria amountCriteria = Criteria.where("amount");
		if (dto.getMinAmount() != null) {
			amountCriteria.gte(dto.getMinAmount());
		}
		if (dto.getMaxAmount() != null) {
			amountCriteria.lte(dto.getMaxAmount());
		}
		if (dto.getMinAmount() != null || dto.getMaxAmount() != null) {
		    query.addCriteria(amountCriteria);
		}
		Criteria dateCriteria = Criteria.where("date");
		if (dto.getStartDate() != null) {
			dateCriteria.gte(dto.getStartDate());
		}
		if (dto.getEndDate() != null) {
			dateCriteria.lte(dto.getEndDate());
		}
		if (dto.getStartDate() != null || dto.getEndDate() != null) {
		    query.addCriteria(dateCriteria);
		}
		if (dto.getDescription()!=null && !dto.getDescription().isBlank()) {
			query.addCriteria(Criteria.where("description").regex(dto.getDescription(), "i"));
		}
		return mongoTemplate.find(query, Expense.class);
	}
	
}
