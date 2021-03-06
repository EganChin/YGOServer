package ygo.comn.util;

import ygo.comn.constant.StatusCode;
import ygo.comn.constant.YGOP;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import ygo.comn.model.DataPacket;

import java.net.InetSocketAddress;

/**
 * 自定义协议YGOP的编码器
 *
 * @author Egan
 * @date 2018/5/19 20:24
 **/
public class YGOPEncoder extends MessageToByteEncoder<DataPacket>{

    private YgoLog log = new YgoLog("Encoder");

    /**
     * 将YGOP数据包出站时将其编码并写入响应中
     *
     * @date 2018/5/19 20:28
     * @param channelHandlerContext 通道处理器的上下文
	 * @param dataPacket YGOP数据包
	 * @param byteBuf 传输的数据容器
     * @return void
     **/
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          DataPacket dataPacket, ByteBuf byteBuf) throws Exception {
        if(dataPacket != null){

            InetSocketAddress address = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();

            log.info(StatusCode.SEND, ": (" + dataPacket.getType() + "){" + dataPacket.getBody() + "} to "
                    + address.getHostString() + ":" + address.getPort());

            byteBuf.writeFloat(dataPacket.getVersion());
            byteBuf.writeInt(dataPacket.getType().getCode());
            byteBuf.writeInt(dataPacket.getMagic());
            byteBuf.writeInt(dataPacket.getLen());
            byteBuf.writeBytes(dataPacket.getBody().getBytes(YGOP.CHARSET));

        }
    }
}
