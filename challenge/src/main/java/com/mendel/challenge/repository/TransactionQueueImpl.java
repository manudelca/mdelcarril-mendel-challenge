package com.mendel.challenge.repository;

import com.mendel.challenge.dto.controller.UpdateTransactionSumRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class TransactionQueueImpl implements TransactionQueue {

    private final RestTemplate restTemplate;
    private final Environment environment;


    public TransactionQueueImpl(Environment environment) {
        this.restTemplate = new RestTemplate();
        this.environment = environment;
    }

    @Override
    public void PublishUpdateTransaction(UpdateTransactionSumRequestDTO update) {
        // Esto viene a simula el publish en una queue tipo RabbitMQ o Kafka o similar. Para simplificar la solucion
        // se hace una llamada sincronica al controller. Pero viene a representar un posteo a una queue la cual
        // el consumer escucha.
        String url = "http://localhost:" + environment.getProperty("local.server.port") + "/consumer/transactions/updates/sum";
        restTemplate.postForObject(url, update, String.class);
    }
}
