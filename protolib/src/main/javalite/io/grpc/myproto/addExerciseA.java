// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: myproto.proto

package io.grpc.myproto;

/**
 * Protobuf type {@code myproto.addExerciseA}
 */
public  final class addExerciseA extends
    com.google.protobuf.GeneratedMessageLite<
        addExerciseA, addExerciseA.Builder> implements
    // @@protoc_insertion_point(message_implements:myproto.addExerciseA)
    addExerciseAOrBuilder {
  private addExerciseA() {
    ok_ = "";
  }
  public static final int OK_FIELD_NUMBER = 1;
  private java.lang.String ok_;
  /**
   * <code>optional string ok = 1;</code>
   */
  public java.lang.String getOk() {
    return ok_;
  }
  /**
   * <code>optional string ok = 1;</code>
   */
  public com.google.protobuf.ByteString
      getOkBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(ok_);
  }
  /**
   * <code>optional string ok = 1;</code>
   */
  private void setOk(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    ok_ = value;
  }
  /**
   * <code>optional string ok = 1;</code>
   */
  private void clearOk() {
    
    ok_ = getDefaultInstance().getOk();
  }
  /**
   * <code>optional string ok = 1;</code>
   */
  private void setOkBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    ok_ = value.toStringUtf8();
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!ok_.isEmpty()) {
      output.writeString(1, getOk());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!ok_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getOk());
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static io.grpc.myproto.addExerciseA parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.grpc.myproto.addExerciseA parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static io.grpc.myproto.addExerciseA parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.grpc.myproto.addExerciseA parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.grpc.myproto.addExerciseA prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * Protobuf type {@code myproto.addExerciseA}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        io.grpc.myproto.addExerciseA, Builder> implements
      // @@protoc_insertion_point(builder_implements:myproto.addExerciseA)
      io.grpc.myproto.addExerciseAOrBuilder {
    // Construct using io.grpc.myproto.addExerciseA.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>optional string ok = 1;</code>
     */
    public java.lang.String getOk() {
      return instance.getOk();
    }
    /**
     * <code>optional string ok = 1;</code>
     */
    public com.google.protobuf.ByteString
        getOkBytes() {
      return instance.getOkBytes();
    }
    /**
     * <code>optional string ok = 1;</code>
     */
    public Builder setOk(
        java.lang.String value) {
      copyOnWrite();
      instance.setOk(value);
      return this;
    }
    /**
     * <code>optional string ok = 1;</code>
     */
    public Builder clearOk() {
      copyOnWrite();
      instance.clearOk();
      return this;
    }
    /**
     * <code>optional string ok = 1;</code>
     */
    public Builder setOkBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setOkBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:myproto.addExerciseA)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new io.grpc.myproto.addExerciseA();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        io.grpc.myproto.addExerciseA other = (io.grpc.myproto.addExerciseA) arg1;
        ok_ = visitor.visitString(!ok_.isEmpty(), ok_,
            !other.ok_.isEmpty(), other.ok_);
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
        }
        return this;
      }
      case MERGE_FROM_STREAM: {
        com.google.protobuf.CodedInputStream input =
            (com.google.protobuf.CodedInputStream) arg0;
        com.google.protobuf.ExtensionRegistryLite extensionRegistry =
            (com.google.protobuf.ExtensionRegistryLite) arg1;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!input.skipField(tag)) {
                  done = true;
                }
                break;
              }
              case 10: {
                String s = input.readStringRequireUtf8();

                ok_ = s;
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw new RuntimeException(e.setUnfinishedMessage(this));
        } catch (java.io.IOException e) {
          throw new RuntimeException(
              new com.google.protobuf.InvalidProtocolBufferException(
                  e.getMessage()).setUnfinishedMessage(this));
        } finally {
        }
      }
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        if (PARSER == null) {    synchronized (io.grpc.myproto.addExerciseA.class) {
            if (PARSER == null) {
              PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
            }
          }
        }
        return PARSER;
      }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:myproto.addExerciseA)
  private static final io.grpc.myproto.addExerciseA DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new addExerciseA();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static io.grpc.myproto.addExerciseA getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<addExerciseA> PARSER;

  public static com.google.protobuf.Parser<addExerciseA> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

