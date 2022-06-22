package com.share.news.service;

import com.share.news.proto.NewsProto;
import com.share.news.proto.NewsServiceGrpc;
import com.share.news.proto.NewsServiceGrpc.NewsServiceImplBase;
import io.grpc.stub.StreamObserver;

import java.util.Date;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

public class NewsService extends NewsServiceImplBase {
    /**
     * 查询当日新闻内容列表
     * @param request
     * @param responseObserver
     */
    @Override
    public void list(NewsProto.NewsRequest request, StreamObserver<NewsProto.NewsResponse> responseObserver) {
        String date = request.getDate();
        NewsProto.NewsResponse newList = null;
        try{
            NewsProto.NewsResponse.Builder newListbuilder = NewsProto.NewsResponse.newBuilder();
            for (int i = 0; i < 100; i++) {
                NewsProto.News news = NewsProto.News.newBuilder().setId(i)
                        .setTitle("新闻标题" + i)
                        .setContent(date + "当日新闻内容" + i)
                        .setCreateTime(new Date().getTime())
                        .build();
                newListbuilder.addNews(news);
            }
            newList = newListbuilder.build();
        }catch (Exception e){
            responseObserver.onError(e);
        }finally {
            responseObserver.onNext(newList);
        }
        responseObserver.onCompleted();
    }

    public void hello(NewsProto.StringRequest request, StreamObserver<NewsProto.StringResponse> responseObserver) {
        String name = request.getName();
        NewsProto.StringResponse response = NewsProto.StringResponse.newBuilder().setResult("Hi,"+name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }






}
