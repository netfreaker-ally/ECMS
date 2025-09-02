//package com.sidecar.ecms.service;
//
//import com.caching.sidecar.grpc.helloworld.Cache.HelloReply;
//import com.caching.sidecar.grpc.helloworld.Cache.HelloRequest;
//import com.caching.sidecar.grpc.helloworld.GreeterGrpc;
//
//import io.grpc.stub.StreamObserver;
//import net.devh.boot.grpc.server.service.GrpcService;
//
//@GrpcService
//public class GreeterService extends GreeterGrpc.GreeterImplBase {
//
//	@Override
//	public void sayHello(HelloRequest request, StreamObserver<HelloReply>  responseObserver) {
//		System.out.println("-------------------*--------------");
//		String message = "Heldlo, " + request.getName();
//		HelloReply reply = HelloReply.newBuilder().setMessage(message).build();
//		
//		responseObserver.onNext(reply);
//		//responseObserver.onNext(HelloReply.newBuilder().setMessage("Hanuma").build());
//		responseObserver.onCompleted();
//	}
//}