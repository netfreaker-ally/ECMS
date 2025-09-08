package com.sidecar.ecms.service;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caching.sidecar.grpc.BatchDeleteRequest;
import com.caching.sidecar.grpc.BatchGetRequest;
import com.caching.sidecar.grpc.BatchReply;
import com.caching.sidecar.grpc.BatchSetRequest;
import com.caching.sidecar.grpc.CacheServiceGrpc.CacheServiceImplBase;
import com.caching.sidecar.grpc.DeleteRequest;
import com.caching.sidecar.grpc.GetReply;
import com.caching.sidecar.grpc.GetRequest;
import com.caching.sidecar.grpc.InvalidationEvent;
import com.caching.sidecar.grpc.OperationMetadata;
import com.caching.sidecar.grpc.RegisterConnectionReply;
import com.caching.sidecar.grpc.RegisterConnectionRequest;
import com.caching.sidecar.grpc.SetRequest;
import com.caching.sidecar.grpc.StatusReply;
import com.caching.sidecar.grpc.WatchRequest;
import com.google.protobuf.ByteString;
import com.sidecar.ecms.cache.CacheConnectionManager;
import com.sidecar.ecms.core.connection.CacheConnection;
import com.sidecar.ecms.core.exception.cache.CacheConnectionException;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl extends CacheServiceImplBase {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final CacheConnectionManager cacheConnectionManager;

	@Override
	public void set(SetRequest request, StreamObserver<StatusReply> responseObserver) {
		log.info("Entered set method in CacheServiceImpl");
		try {
			log.debug("Starting making connection with request: " + request);
			CacheConnection connection;

			if (request.hasConnectionId()) {
				// Use existing connection
				log.info("Request Has Connection Id in Request: {}", request.getConnectionId());
				connection = cacheConnectionManager.getConnectionById(request.getConnectionId())
						.orElseThrow(() -> new CacheConnectionException("Invalid connection id"));
			} else {
				// Create new connection from config
				log.info("Request Has No Connection Id  entering into getOrCreateConnection in cacheConnectionManager");

				connection = cacheConnectionManager.getOrCreateConnection(request.getConfig(), null);
			}

			Optional<Instant> absoluteExpiration = request.hasAbsoluteExpiration()
					? Optional.of(Instant.ofEpochSecond(request.getAbsoluteExpiration().getSeconds(),
							request.getAbsoluteExpiration().getNanos()))
					: Optional.empty();
			log.info("Setting key {} value {} ttl{} absoluteExpiration {}", request.getKey(), request.getValue(),
					request.getTtlSeconds(), absoluteExpiration);
			connection.set(request.getKey(), request.getValue(), request.getTtlSeconds(), absoluteExpiration);

			// Build response
			StatusReply reply = StatusReply.newBuilder().setStatus(StatusReply.Status.SUCCESS)
					.setMessage("Key set successfully")
					.setMetaData(OperationMetadata.newBuilder().setNodeUsed("DemoCtnId:111") // store
																								// connectionId
																								// in
																								// metadata
							.build())
					.build();
			log.info("Status Reply Build SuccessFully and attaching to responseObserver: {}", reply);
			responseObserver.onNext(reply);
			responseObserver.onCompleted();

		} // | CacheOperationException haveToAdd when accessing connectionId
		catch (CacheConnectionException e) {
			log.error("Exception Occcured in CacheServiceImpl set method with error code {} and error message: {}"
					+ e.getErrorCode(), e.getMessage());
			StatusReply reply = StatusReply.newBuilder().setStatus(StatusReply.Status.FAILURE)
					.setMessage(e.getMessage()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}

	@Override
	public void get(GetRequest request, StreamObserver<GetReply> responseObserver) {
		System.out.println("Received a GetRequest for key: " + request.getKey());

		boolean keyFound = false;

		if (keyFound) {
			GetReply reply = GetReply.newBuilder().setStatus(StatusReply.Status.SUCCESS)
					.setValue(ByteString.copyFromUtf8("Hello from the cache!")).build();
			responseObserver.onNext(reply);
		} else {
			GetReply reply = GetReply.newBuilder().setStatus(StatusReply.Status.KEY_NOT_FOUND).build();
			responseObserver.onNext(reply);
		}

		responseObserver.onCompleted();
	}

	/**
	 */
	@Override
	public void delete(DeleteRequest request, StreamObserver<StatusReply> responseObserver) {

	}

	/**
	 */
	@Override
	public void batchSet(BatchSetRequest request, StreamObserver<BatchReply> responseObserver) {

	}

	/**
	 */
	public void batchGet(BatchGetRequest request, StreamObserver<BatchReply> responseObserver) {

	}

	/**
	 */
	@Override
	public void batchDelete(BatchDeleteRequest request, StreamObserver<BatchReply> responseObserver) {

	}

	/**
	 */
	@Override
	public void watchKeys(WatchRequest request, StreamObserver<InvalidationEvent> responseObserver) {

	}

	/**
	 */
	@Override
	public void registerConnection(RegisterConnectionRequest request,
			StreamObserver<RegisterConnectionReply> responseObserver) {

	}
}
