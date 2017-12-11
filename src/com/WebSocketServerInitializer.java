package com;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> 
{
	private static final String WEBSOCKET_PATH = "/websocket";

    private final SslContext sslCtx;
    private Logger logger = LogManager.getLogger(WebSocketServerInitializer.class);
    public WebSocketServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        String remoteIp=(((InetSocketAddress) ch.remoteAddress()).getAddress().getHostAddress());
        ch.closeFuture().addListener(new AdminChannelClosureListener(logger,remoteIp));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        pipeline.addLast(new WebSocketIndexPageHandler(WEBSOCKET_PATH));
        pipeline.addLast(new WebSocketFrameHandler());
    }
    /**
	 * Calls ChannelHandlerContext.fireExceptionCaught(Throwable) to forward to the next ChannelHandler in the ChannelPipeline. Sub-classes may override this method to change behavior.
	 * @param ctx the channel that user input command
	 * @param cause the exception cause  
	 */
    @Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) 
    {
    	
    	logger.debug(cause.getMessage());
    }
}
