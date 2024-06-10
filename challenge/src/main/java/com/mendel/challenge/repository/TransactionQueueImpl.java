package com.mendel.challenge.repository;

import com.mendel.challenge.dto.controller.UpdateTransactionSumRequestDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class TransactionQueueImpl implements TransactionQueue {

    private final RestTemplate restTemplate;

    public TransactionQueueImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void PublishUpdateTransaction(UpdateTransactionSumRequestDTO update) {
        // Esto viene a simula el publish en una queue tipo RabbitMQ o Kafka o similar. Para simplificar la solucion
        // se hace una llamada sincronica al controller. Pero viene a representar un posteo a una queue la cual
        // el consumer escucha.
        restTemplate.postForObject("http://localhost:8080/consumer/transactions/updates/sum", update, String.class);
    }
}
