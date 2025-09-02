package com.sidecar.ecms.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caching.sidecar.grpc.CacheGrpc.CacheImplBase;
import com.caching.sidecar.grpc.CacheOuterClass.CacheStatus;
import com.caching.sidecar.grpc.CacheOuterClass.DeleteRequest;
import com.caching.sidecar.grpc.CacheOuterClass.DeleteResponse;
import com.caching.sidecar.grpc.CacheOuterClass.GetRequest;
import com.caching.sidecar.grpc.CacheOuterClass.GetResponse;
import com.caching.sidecar.grpc.CacheOuterClass.SetRequest;
import com.caching.sidecar.grpc.CacheOuterClass.SetResponse;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class CacheServiceImpl extends CacheImplBase {

	private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

	// In a real application, you would replace this with a real cache provider
	// like Redis, Memcached, or a simple in-memory cache.
	// For this example, we'll use a simple in-memory map.
	private static final java.util.concurrent.ConcurrentHashMap<String, byte[]> inMemoryCache = new java.util.concurrent.ConcurrentHashMap<>();

	@Override
	public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
		String key = request.getKey();
		logger.info("Received Get request for key: " + key);

		GetResponse.Builder responseBuilder = GetResponse.newBuilder();

		// Implement your cache retrieval logic here.
		byte[] value = inMemoryCache.get(key);

		if (value != null) {
			logger.info("Key found: " + key);
			responseBuilder.setStatus(CacheStatus.SUCCESS);
			responseBuilder.setValue(com.google.protobuf.ByteString.copyFrom(value));
		} else {
			logger.warn("Key not found: " + key);
			responseBuilder.setStatus(CacheStatus.NOT_FOUND);
			responseBuilder.setMessage("Key was not found in the cache.");
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	@Override
	public void set(SetRequest request, StreamObserver<SetResponse> responseObserver) {
		String key = request.getKey();
		byte[] value = request.getValue().toByteArray();
		int ttl = request.getTtlSeconds();
		logger.info("Received Set request for key: " + key + " with TTL: " + ttl + " seconds.");

		SetResponse.Builder responseBuilder = SetResponse.newBuilder();

		try {
			// Implement your cache insertion logic here.
			// For this example, we'll just put it in our in-memory map.
			inMemoryCache.put(key, value);

			// TODO: In a real-world scenario, you would need to implement the TTL logic
			// using scheduled tasks or a cache provider that supports expiration.

			responseBuilder.setStatus(CacheStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("Failed to set key: " + key + ", error: " + e.getMessage());
			responseBuilder.setStatus(CacheStatus.INTERNAL_ERROR);
			responseBuilder.setMessage("An internal error occurred while setting the value.");
		}

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	@Override
	public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
		String key = request.getKey();
		logger.info("Received Delete request for key: " + key);

		DeleteResponse.Builder responseBuilder = DeleteResponse.newBuilder();

		// Implement your cache deletion logic here.
		inMemoryCache.remove(key);

		// We can always return SUCCESS here since the goal is to ensure the key is
		// gone.
		responseBuilder.setStatus(CacheStatus.SUCCESS);

		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

	/**
	 * Main method to start the gRPC server. This is for demonstration purposes and
	 * shows how to set up and run the service.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 50051;
		Server server = ServerBuilder.forPort(port).addService(new CacheServiceImpl()).build().start();
		logger.info("Server started, listening on port " + port);

		// Add a shutdown hook to gracefully shut down the server.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.err.println("*** shutting down gRPC server since JVM is shutting down");
			server.shutdown();
			System.err.println("*** server shut down");
		}));

		// Keep the server running until the application is terminated.
		server.awaitTermination();
	}
}
