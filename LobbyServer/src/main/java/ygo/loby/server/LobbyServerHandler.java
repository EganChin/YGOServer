package ygo.loby.server;

import io.netty.channel.SimpleChannelInboundHandler;
import ygo.comn.constant.MessageType;
import ygo.comn.constant.Secret;
import ygo.comn.controller.redis.RedisClient;
import ygo.comn.controller.redis.RedisFactory;
import ygo.comn.model.*;
import ygo.comn.util.YgoLog;
import ygo.loby.controller.ChiefController;
import ygo.comn.constant.StatusCode;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * 大厅服务器处理器
 *
 * @author Egan
 * @date 2018/5/7 22:54
 **/
public class LobbyServerHandler extends SimpleChannelInboundHandler<DataPacket> {

    private YgoLog log = new YgoLog("IOBound");


    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

        if (Secret.DUEL_HOST.equals(address.getHostString()) && GlobalMap.getDuelChannel() == null){
            GlobalMap.setDuelChannel(ctx.channel());
        }

        log.info(StatusCode.INBOUND, address.getHostString() + ":" + address.getPort());
        //分配redis连接
        RedisFactory.getRedisforLobby(address);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info(StatusCode.OUTBOUND, address.getHostString() + ":" + address.getPort() );


        //检查玩家是否掉线
        RedisClient redis = RedisFactory.getRedisforLobby(address);
        if(redis.removeAndInform(address)){
            log.warn(StatusCode.LOST_CONNECTION, address.getHostString() + ":" + address.getPort());
        }
        RedisFactory.closeRedis(address);
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DataPacket packet){
        new ChiefController(packet, ctx.channel());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.writeAndFlush(new DataPacket(new ResponseStatus(StatusCode.INTERNAL_SERVER_ERROR)));
        log.fatal("Unexpected Error", cause);
        ctx.close();
    }
}
