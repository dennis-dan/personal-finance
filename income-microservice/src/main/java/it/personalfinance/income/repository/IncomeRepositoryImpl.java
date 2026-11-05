package it.personalfinance.income.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.personalfinance.income.dto.IncomeSearchRequestDto;
import it.personalfinance.income.entity.Income;

public class IncomeRepositoryImpl implements IncomeRepositoryCustom {
	private final MongoTemplate mongoTemplate;
	
	public IncomeRepositoryImpl(MongoTemplate mongoTemplate) {
	    this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Income> search(IncomeSearchRequestDto dto) {
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
		return mongoTemplate.find(query, Income.class);
	}
}
