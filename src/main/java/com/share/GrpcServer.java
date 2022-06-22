package com.share;

import com.share.news.service.NewsService;
import com.share.sms.service.SmsService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    /**
     * 端口号
     */
    private static final int port = 9000;

    /**
     * 启动器
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port)
                .addService(new NewsService())
                .addService(new SmsService())
                .build()
                .start();
        System.out.println(String.format("GRPC 服务端启动成功，端口号：%d.",port));
        server.awaitTermination();
    }

}
