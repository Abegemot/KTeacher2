package io.grpc.myproto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.0)",
    comments = "Source: myproto.proto")
public final class addLessonerGrpc {

  private addLessonerGrpc() {}

  public static final String SERVICE_NAME = "myproto.addLessoner";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getAddLessonMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.myproto.addLessonR,
      io.grpc.myproto.addLessonA> METHOD_ADD_LESSON = getAddLessonMethod();

  private static volatile io.grpc.MethodDescriptor<io.grpc.myproto.addLessonR,
      io.grpc.myproto.addLessonA> getAddLessonMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.myproto.addLessonR,
      io.grpc.myproto.addLessonA> getAddLessonMethod() {
    io.grpc.MethodDescriptor<io.grpc.myproto.addLessonR, io.grpc.myproto.addLessonA> getAddLessonMethod;
    if ((getAddLessonMethod = addLessonerGrpc.getAddLessonMethod) == null) {
      synchronized (addLessonerGrpc.class) {
        if ((getAddLessonMethod = addLessonerGrpc.getAddLessonMethod) == null) {
          addLessonerGrpc.getAddLessonMethod = getAddLessonMethod = 
              io.grpc.MethodDescriptor.<io.grpc.myproto.addLessonR, io.grpc.myproto.addLessonA>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "myproto.addLessoner", "addLesson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  io.grpc.myproto.addLessonR.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  io.grpc.myproto.addLessonA.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getAddLessonMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getAddExerciseMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.myproto.addExerciseR,
      io.grpc.myproto.addExerciseA> METHOD_ADD_EXERCISE = getAddExerciseMethod();

  private static volatile io.grpc.MethodDescriptor<io.grpc.myproto.addExerciseR,
      io.grpc.myproto.addExerciseA> getAddExerciseMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.myproto.addExerciseR,
      io.grpc.myproto.addExerciseA> getAddExerciseMethod() {
    io.grpc.MethodDescriptor<io.grpc.myproto.addExerciseR, io.grpc.myproto.addExerciseA> getAddExerciseMethod;
    if ((getAddExerciseMethod = addLessonerGrpc.getAddExerciseMethod) == null) {
      synchronized (addLessonerGrpc.class) {
        if ((getAddExerciseMethod = addLessonerGrpc.getAddExerciseMethod) == null) {
          addLessonerGrpc.getAddExerciseMethod = getAddExerciseMethod = 
              io.grpc.MethodDescriptor.<io.grpc.myproto.addExerciseR, io.grpc.myproto.addExerciseA>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "myproto.addLessoner", "addExercise"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  io.grpc.myproto.addExerciseR.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  io.grpc.myproto.addExerciseA.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getAddExerciseMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetExerciseMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.myproto.getExerciseR,
      io.grpc.myproto.addExerciseR> METHOD_GET_EXERCISE = getGetExerciseMethod();

  private static volatile io.grpc.MethodDescriptor<io.grpc.myproto.getExerciseR,
      io.grpc.myproto.addExerciseR> getGetExerciseMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.myproto.getExerciseR,
      io.grpc.myproto.addExerciseR> getGetExerciseMethod() {
    io.grpc.MethodDescriptor<io.grpc.myproto.getExerciseR, io.grpc.myproto.addExerciseR> getGetExerciseMethod;
    if ((getGetExerciseMethod = addLessonerGrpc.getGetExerciseMethod) == null) {
      synchronized (addLessonerGrpc.class) {
        if ((getGetExerciseMethod = addLessonerGrpc.getGetExerciseMethod) == null) {
          addLessonerGrpc.getGetExerciseMethod = getGetExerciseMethod = 
              io.grpc.MethodDescriptor.<io.grpc.myproto.getExerciseR, io.grpc.myproto.addExerciseR>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "myproto.addLessoner", "getExercise"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  io.grpc.myproto.getExerciseR.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  io.grpc.myproto.addExerciseR.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getGetExerciseMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static addLessonerStub newStub(io.grpc.Channel channel) {
    return new addLessonerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static addLessonerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new addLessonerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static addLessonerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new addLessonerFutureStub(channel);
  }

  /**
   */
  public static abstract class addLessonerImplBase implements io.grpc.BindableService {

    /**
     */
    public void addLesson(io.grpc.myproto.addLessonR request,
        io.grpc.stub.StreamObserver<io.grpc.myproto.addLessonA> responseObserver) {
      asyncUnimplementedUnaryCall(getAddLessonMethod(), responseObserver);
    }

    /**
     */
    public void addExercise(io.grpc.myproto.addExerciseR request,
        io.grpc.stub.StreamObserver<io.grpc.myproto.addExerciseA> responseObserver) {
      asyncUnimplementedUnaryCall(getAddExerciseMethod(), responseObserver);
    }

    /**
     */
    public void getExercise(io.grpc.myproto.getExerciseR request,
        io.grpc.stub.StreamObserver<io.grpc.myproto.addExerciseR> responseObserver) {
      asyncUnimplementedUnaryCall(getGetExerciseMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddLessonMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.myproto.addLessonR,
                io.grpc.myproto.addLessonA>(
                  this, METHODID_ADD_LESSON)))
          .addMethod(
            getAddExerciseMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.myproto.addExerciseR,
                io.grpc.myproto.addExerciseA>(
                  this, METHODID_ADD_EXERCISE)))
          .addMethod(
            getGetExerciseMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.myproto.getExerciseR,
                io.grpc.myproto.addExerciseR>(
                  this, METHODID_GET_EXERCISE)))
          .build();
    }
  }

  /**
   */
  public static final class addLessonerStub extends io.grpc.stub.AbstractStub<addLessonerStub> {
    private addLessonerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private addLessonerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected addLessonerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new addLessonerStub(channel, callOptions);
    }

    /**
     */
    public void addLesson(io.grpc.myproto.addLessonR request,
        io.grpc.stub.StreamObserver<io.grpc.myproto.addLessonA> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddLessonMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addExercise(io.grpc.myproto.addExerciseR request,
        io.grpc.stub.StreamObserver<io.grpc.myproto.addExerciseA> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddExerciseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getExercise(io.grpc.myproto.getExerciseR request,
        io.grpc.stub.StreamObserver<io.grpc.myproto.addExerciseR> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetExerciseMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class addLessonerBlockingStub extends io.grpc.stub.AbstractStub<addLessonerBlockingStub> {
    private addLessonerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private addLessonerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected addLessonerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new addLessonerBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.myproto.addLessonA addLesson(io.grpc.myproto.addLessonR request) {
      return blockingUnaryCall(
          getChannel(), getAddLessonMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.myproto.addExerciseA addExercise(io.grpc.myproto.addExerciseR request) {
      return blockingUnaryCall(
          getChannel(), getAddExerciseMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.myproto.addExerciseR getExercise(io.grpc.myproto.getExerciseR request) {
      return blockingUnaryCall(
          getChannel(), getGetExerciseMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class addLessonerFutureStub extends io.grpc.stub.AbstractStub<addLessonerFutureStub> {
    private addLessonerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private addLessonerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected addLessonerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new addLessonerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.myproto.addLessonA> addLesson(
        io.grpc.myproto.addLessonR request) {
      return futureUnaryCall(
          getChannel().newCall(getAddLessonMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.myproto.addExerciseA> addExercise(
        io.grpc.myproto.addExerciseR request) {
      return futureUnaryCall(
          getChannel().newCall(getAddExerciseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.myproto.addExerciseR> getExercise(
        io.grpc.myproto.getExerciseR request) {
      return futureUnaryCall(
          getChannel().newCall(getGetExerciseMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_LESSON = 0;
  private static final int METHODID_ADD_EXERCISE = 1;
  private static final int METHODID_GET_EXERCISE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final addLessonerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(addLessonerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_LESSON:
          serviceImpl.addLesson((io.grpc.myproto.addLessonR) request,
              (io.grpc.stub.StreamObserver<io.grpc.myproto.addLessonA>) responseObserver);
          break;
        case METHODID_ADD_EXERCISE:
          serviceImpl.addExercise((io.grpc.myproto.addExerciseR) request,
              (io.grpc.stub.StreamObserver<io.grpc.myproto.addExerciseA>) responseObserver);
          break;
        case METHODID_GET_EXERCISE:
          serviceImpl.getExercise((io.grpc.myproto.getExerciseR) request,
              (io.grpc.stub.StreamObserver<io.grpc.myproto.addExerciseR>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (addLessonerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getAddLessonMethod())
              .addMethod(getAddExerciseMethod())
              .addMethod(getGetExerciseMethod())
              .build();
        }
      }
    }
    return result;
  }
}
