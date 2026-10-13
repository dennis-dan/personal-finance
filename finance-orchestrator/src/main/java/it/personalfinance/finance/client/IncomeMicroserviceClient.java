package it.personalfinance.finance.client;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import it.personalfinance.finance.dto.IncomeRequestDto;
import it.personalfinance.finance.dto.IncomeResponseDto;

@Component
public class IncomeMicroserviceClient {
	    private final RestClient restClient;

	    public IncomeMicroserviceClient(@Qualifier("incomeRestClient") RestClient restClient) {
	        this.restClient = restClient;
	    }

	    public List<IncomeResponseDto> findAll() {
	        return restClient.get()
	                .uri("/api/incomes")
	                .retrieve()
	                .body(new ParameterizedTypeReference<List<IncomeResponseDto>>() {});
	    }

		public IncomeResponseDto findById(String id) {
			return restClient.get()
					.uri("/api/incomes/{id}", id)
	                .retrieve()
	                .body(IncomeResponseDto.class);
		}

		public IncomeResponseDto update(String id, IncomeRequestDto income) {
			return restClient.put()
					.uri("/api/incomes/{id}", id)
		            .body(income)
		            .retrieve()
		            .body(IncomeResponseDto.class);
		}

		public String delete(String id) {
			return restClient.delete()
					.uri("/api/incomes/{id}", id)
	                .retrieve()
	                .body(String.class);
		}

		public IncomeResponseDto save(IncomeRequestDto income) {
			return restClient.post()
					.uri("/api/incomes/save")
					.body(income)
	                .retrieve()
	                .body(IncomeResponseDto.class);
		}
}
