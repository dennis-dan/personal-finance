package it.personalfinance.finance.client;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import it.personalfinance.finance.dto.ExpenseRequestDto;
import it.personalfinance.finance.dto.ExpenseResponseDto;

@Component
public class FinanceMicroserviceClient {

    private final RestClient restClient;

    public FinanceMicroserviceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<ExpenseResponseDto> findAll() {
        return restClient.get()
                .uri("/api/expenses")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ExpenseResponseDto>>() {});
    }

	public ExpenseResponseDto findById(String id) {
		return restClient.get()
				.uri("/api/expenses/{id}", id)
                .retrieve()
                .body(ExpenseResponseDto.class);
	}

	public ExpenseResponseDto update(String id, ExpenseRequestDto expense) {
		return restClient.put()
				.uri("/api/expenses/{id}", id)
	            .body(expense)
	            .retrieve()
	            .body(ExpenseResponseDto.class);
	}

	public String delete(String id) {
		return restClient.delete()
				.uri("/api/expenses/{id}", id)
                .retrieve()
                .body(String.class);
	}

	public ExpenseResponseDto save(ExpenseRequestDto expense) {
		return restClient.post()
				.uri("/api/expenses/save")
				.body(expense)
                .retrieve()
                .body(ExpenseResponseDto.class);
	}
}