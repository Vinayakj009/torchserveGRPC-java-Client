package com.example.torchserve.client;

import java.util.HashMap;
import java.util.Map;

import org.pytorch.serve.grpc.inference.InferenceAPIsServiceGrpc;
import org.pytorch.serve.grpc.inference.PredictionResponse;
import org.pytorch.serve.grpc.inference.PredictionsRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class TorchServeClient {
    private final InferenceAPIsServiceGrpc.InferenceAPIsServiceBlockingStub blockingStub;

    public TorchServeClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = InferenceAPIsServiceGrpc.newBlockingStub(channel);
    }

    public byte[] predict(String modelName, Map<String, byte[]> inputData) {
        System.out.println("Sending prediction request to model: " + modelName);
        PredictionsRequest.Builder requestBuilder = PredictionsRequest.newBuilder()
                .setModelName(modelName);

        for (Map.Entry<String, byte[]> entry : inputData.entrySet()) {
            requestBuilder.putInput(entry.getKey(), com.google.protobuf.ByteString.copyFrom(entry.getValue()));
        }

        PredictionsRequest request = requestBuilder.build();
        PredictionResponse response = blockingStub.predictions(request);
        return response.getPrediction().toByteArray();
    }

    public static void main(String[] args) {
        TorchServeClient client = new TorchServeClient("localhost", 7070);
        Map<String, byte[]> inputData = new HashMap<>();
        inputData.put("example_key", "example_input".getBytes());
        byte[] result = client.predict("example_model", inputData);
        System.out.println("Prediction result: " + new String(result));
    }
}