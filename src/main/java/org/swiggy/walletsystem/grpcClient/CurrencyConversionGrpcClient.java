package org.swiggy.walletsystem.grpcClient;

import currencyconversion.ConvertRequest;
import currencyconversion.ConvertResponse;
import currencyconversion.ConverterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyConversionGrpcClient {
    private final static Logger log = LoggerFactory.getLogger(CurrencyConversionGrpcClient.class);

    public static ConvertResponse convertCurrency(String baseCurrency, String targetCurrency, double amount) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        ConverterGrpc.ConverterBlockingStub stub = ConverterGrpc.newBlockingStub(channel);
        ConvertRequest request = ConvertRequest.newBuilder()
                .setBaseCurrency(baseCurrency)
                .setTargetCurrency(targetCurrency)
                .setAmount(amount)
                .build();

        ConvertResponse response = stub.convertCurrency(request);
        log.info("Server message: {}",response);
        channel.shutdown();
        return response;
    }
}
