package it.personalfinance.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.personalfinance.finance.client.IncomeMicroserviceClient;
import it.personalfinance.finance.dto.IncomeRequestDto;
import it.personalfinance.finance.dto.IncomeResponseDto;


@Service
public class IncomeOrchestrationService {
		
		private final IncomeMicroserviceClient client;
		
		public IncomeOrchestrationService(IncomeMicroserviceClient client) {
			this.client=client;
		}
		
		public List<IncomeResponseDto> findAll(){
			return client.findAll();
		}

		public IncomeResponseDto findById(String id) {
			return client.findById(id);
		}

		public IncomeResponseDto update(String id, IncomeRequestDto income) {
			return client.update(id,income);
		}

		public String delete(String id) {
			return client.delete(id);
		}

		public IncomeResponseDto save(IncomeRequestDto income) {
			return client.save(income);
		}
}
