package com.mendel.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.challenge.dto.controller.CreateTransactionRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	@DirtiesContext
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
	public void testCreateTransactionInvalidType() throws Exception {
		// Given
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(10.0, "invalid type", null);

		// When - Then
		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.causes").value("type: Invalid value. Allowed values are: [CARS, SHOPPING]"))
				.andReturn();

	}

	@Test
	public void testCreateTransactionInvalidAmount() throws Exception {
		// Given
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(-10.0, "CARS", null);

		// When - Then
		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.causes").value("amount: minimum amount is zero"))
				.andReturn();

	}

	@Test
	@DirtiesContext
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
	@DirtiesContext
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
	@DirtiesContext
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

	@Test
	public void testGetTransactionIDsByTypeNoTransactions() throws Exception {
		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/types/CARS")
						.param("offset", "0")
						.param("limit", "30"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.page.total").value(0))
				.andExpect(jsonPath("$.page.limit").value(30))
				.andExpect(jsonPath("$.page.offset").value(0))
				.andExpect(jsonPath("$.result").value(new ArrayList<>()))
				.andReturn();
	}

	@Test
	@DirtiesContext
	public void testGetTransactionIDsByTypeCarsTransactions() throws Exception {
		// Given cars transaction
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", null);

		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.type").value("CARS"))
				.andExpect(jsonPath("$.parent_id").isEmpty())
				.andReturn();


		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/types/CARS")
						.param("offset", "0")
						.param("limit", "30"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.page.total").value(1))
				.andExpect(jsonPath("$.page.limit").value(30))
				.andExpect(jsonPath("$.page.offset").value(0))
				.andExpect(jsonPath("$.result[0]").value(1))
				.andReturn();
	}

	@Test
	@DirtiesContext
	public void testGetTransactionIDsByTypeShoppingTransactions() throws Exception {
		// Given shopping transaction
		CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "SHOPPING", null);

		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
						.content(objectMapper.writeValueAsString(transactionRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.type").value("SHOPPING"))
				.andExpect(jsonPath("$.parent_id").isEmpty())
				.andReturn();


		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/types/SHOPPING")
						.param("offset", "0")
						.param("limit", "30"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.page.total").value(1))
				.andExpect(jsonPath("$.page.limit").value(30))
				.andExpect(jsonPath("$.page.offset").value(0))
				.andExpect(jsonPath("$.result[0]").value(1))
				.andReturn();
	}

	@Test
	public void testGetTransactionIDsByTypeInvalidLimit() throws Exception {
		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/types/CARS")
						.param("offset", "0")
						.param("limit", "51"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void testGetTransactionIDsByTypeInvalidOffset() throws Exception {
		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/types/CARS")
						.param("offset", "-1")
						.param("limit", "30"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void testGetTransactionIDsByTypeInvalidType() throws Exception {
		// When - Then

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/types/asd")
						.param("offset", "0")
						.param("limit", "30"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

}
