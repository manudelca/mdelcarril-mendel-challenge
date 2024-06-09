package com.mendel.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.challenge.dto.controller.CreateTransactionRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class ChallengeApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testCreateTransactionSuccess() throws Exception {
		// Given
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", null);

		// When - Then
		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
					.content(objectMapper.writeValueAsString(transactionRequest))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.type").value("CARS"))
				.andExpect(jsonPath("$.parent_id").isEmpty())
				.andReturn();

	}

	@Test
	public void testCreateTransactionEqualParentID() throws Exception {
		// Given
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", 1L);

		// When - Then
		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.message").value("parent transaction equals transaction id"))
				.andReturn();

	}

	@Test
	public void testCreateTransactionNonExistentParentID() throws Exception {
		// Given
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", 2L);

		// When - Then
		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.error").value("Not Found"))
				.andExpect(jsonPath("$.message").value("parent transaction not found"))
				.andReturn();

	}

	@Test
	public void testCreateTransactionWithParentID() throws Exception {
		// Given Parent Transaction
		CreateTransactionRequestDTO parentTransactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", null);

		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(parentTransactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.type").value("CARS"))
				.andExpect(jsonPath("$.parent_id").isEmpty())
				.andReturn();

		// Given
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", 1L);

		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/2")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.type").value("CARS"))
				.andExpect(jsonPath("$.parent_id").value(1))
				.andReturn();

	}


}
