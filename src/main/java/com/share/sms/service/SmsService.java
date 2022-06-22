package com.share.sms.service;

import com.google.protobuf.ProtocolStringList;
import com.share.sms.proto.SmsProto;
import com.share.sms.proto.SmsServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SmsService extends SmsServiceGrpc.SmsServiceImplBase   {


    public void sendSms(SmsProto.SmsRequest request, StreamObserver<SmsProto.SmsResponse> responseObserver){
        ProtocolStringList phoneNumberList = request.getPhoneNumberList();
        for (String phoneNumber : phoneNumberList) {
            SmsProto.SmsResponse smsResponse = SmsProto.SmsResponse.newBuilder().setResult(request.getContent() + "," + phoneNumber + "发送成功！").build();
            responseObserver.onNext(smsResponse);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }


    public StreamObserver<SmsProto.PhoneNumberRequest> createPhone(StreamObserver<SmsProto.PhoneNumberResponse> responseObserver){
        //异步通信，基于responseObserver回调
        return new StreamObserver<SmsProto.PhoneNumberRequest>() {
            int i=0;
            @Override
            public void onNext(SmsProto.PhoneNumberRequest phoneNumberRequest) {
                System.out.println(phoneNumberRequest.getPhoneNumber() + "手机号已登记");
                i+=1;
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult("您本次导入"+i+"个电话号码").build());
                responseObserver.onCompleted();
            }
        };
    }


    public StreamObserver<SmsProto.PhoneNumberRequest> createAndSendSms(StreamObserver<SmsProto.PhoneNumberResponse> responseObserver){
        //异步通信，基于responseObserver回调
        return new StreamObserver<SmsProto.PhoneNumberRequest>() {
            int i = 0;
            //接到客户端发来的新的电话号码时，出发onNext，再创建多个响应
            @Override
            public void onNext(SmsProto.PhoneNumberRequest phoneNumberRequest) {
                System.out.println(phoneNumberRequest.getPhoneNumber()+"手机号已登记");
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult(phoneNumberRequest.getPhoneNumber()+"手机号已登记，此消息已发送部门经理").build());
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult(phoneNumberRequest.getPhoneNumber()+"手机号已登记，此消息已发送副总经理").build());
                responseObserver.onNext(SmsProto.PhoneNumberResponse.newBuilder().setResult(phoneNumberRequest.getPhoneNumber()+"手机号已登记，此消息已发送总经理").build());
                i+=1;

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

    }
}
