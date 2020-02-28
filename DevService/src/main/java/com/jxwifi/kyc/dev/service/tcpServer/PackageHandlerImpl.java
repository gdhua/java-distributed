package com.jxwifi.kyc.dev.service.tcpServer;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
public  class PackageHandlerImpl implements PackageHandler {

    public static List<PackageHandler> packageHandlers= new ArrayList<PackageHandler>();
    static PackageHandlerImpl packageHandler;
    protected PackageHandlerImpl(){
        super();
        System.out.println("init PackageHandlerImpl");
    }

    public static PackageHandlerImpl getInstance(){
        if(packageHandler==null){
            packageHandler=new PackageHandlerImpl();
        }
        return packageHandler;
    }

    @Override
    public void doHandle(String hex) {
        for(PackageHandler f : packageHandlers){
            f.doHandle(hex);
        }

    }
    public PackageHandlerImpl addHandle(PackageHandler f){
        packageHandlers.add(f);
        return this;
    }


}
