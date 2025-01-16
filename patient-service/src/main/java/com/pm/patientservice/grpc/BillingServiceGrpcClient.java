package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.pm.patientservice.dtos.billing.PatientBillingRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:billing-service}") String address,
            @Value("${billing.service.grpc.port:9001}") Integer port) {

        log.info("Initializing gRPC client for billing service at {}:{}", address, port);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();

        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(PatientBillingRequest patientBillingRequest) {
        log.info("creating billing account");
        log.info("creating billing account request - patientId : {}, name : {}, email : {}", patientBillingRequest.getId(), patientBillingRequest.getName(), patientBillingRequest.getEmail());

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientBillingRequest.getId())
                .setName(patientBillingRequest.getName())
                .setEmail(patientBillingRequest.getEmail())
                .build();

        BillingResponse response = this.blockingStub.createBillingAccount(request);

        log.info("response received from billing-service using gRPC is: {}", response);

        return response;
    }
}