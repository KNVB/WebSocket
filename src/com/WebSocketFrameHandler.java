package com;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;





import javax.crypto.Cipher;

import com.util.MessageCoder;
import com.util.RSA;
import com.util.Utility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import org.apache.logging.log4j.*;
import org.bouncycastle.util.encoders.Base64;


public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame>
{
	private RSA keyCoder;
	private MessageCoder messageCoder;
	private boolean isFirstConnect=true;
	private String returnCoder=new String();
	private String responseString,request;
	private static final Logger logger = LogManager.getLogger(WebSocketFrameHandler.class);
	@Override
	public void channelActive(ChannelHandlerContext ctx)
            throws java.lang.Exception
    {
		/*keyCoder = new RSA(1024);
		messageCoder=new MessageCoder();
		String messageKey=Utility.byteArrayToHexString(keyCoder.encode(messageCoder.key.getBytes()));
		String ivText=Utility.byteArrayToHexString(keyCoder.encode(messageCoder.ivText.getBytes()));
		logger.debug("messageKey={}",messageCoder.key);
		logger.debug("ivText={}",messageCoder.ivText);
		returnCoder="keyCoder=new KeyCoder(\""+keyCoder.getPublicExponent()+"\",\"\",\""+keyCoder.getPublicModulus()+"\");";
		//returnCoder="keyDecoder=new KeyCoder(\"\",\""+keyCoder.getPrivateExponent()+"\",\""+keyCoder.getPublicModulus()+"\");";
		//returnCoder="coder=new Coder(\""+messageKey+"\",\""+ivText+"\",\""+keyCoder.getPrivateExponent()+"\",\""+keyCoder.getPublicModulus()+"\");";*/
    }
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame instanceof TextWebSocketFrame) {
        	handleRequest(ctx, frame);
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
	private void handleRequest(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception
	{
		request = ((TextWebSocketFrame) frame).text();
        logger.debug("{} received {}|", ctx.channel(),request);
        if (isFirstConnect)
        {
        	messageCoder=new MessageCoder();
        	String messageKey=messageCoder.key;
    		String ivText=messageCoder.ivText;
    		logger.debug("messageKey={}",messageKey);
    		logger.debug("ivText={}",ivText);
    		Cipher rsaCipher;
    		rsaCipher = Cipher.getInstance("RSA");
    		byte[] publicBytes = Base64.decode(request);
    		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
    		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    		rsaCipher.init(Cipher.ENCRYPT_MODE,keyFactory.generatePublic(keySpec));
    		//byte[] ciphertext = rsaCipher.doFinal(data);
    		
        }
        /*if (request.equals("Hello"))
        {
			if (isFirstConnect)
			{	
				ctx.channel().writeAndFlush(new TextWebSocketFrame(returnCoder));
				isFirstConnect=false;
			}
			else
			{
				throw new UnsupportedOperationException("Invalid action specified!");
			}
        }
        else
        {
        	responseString=new String(keyCoder.decode(request.getBytes("UTF-8")),"UTF-8");
        	logger.debug("Decoded text:"+responseString);
        	/*responseString=messageCoder.encode(responseString);
        	ctx.channel().writeAndFlush(new TextWebSocketFrame(responseString));
        }*/
	}
	/**
	 * Calls ChannelHandlerContext.fireExceptionCaught(Throwable) to forward to the next ChannelHandler in the ChannelPipeline. Sub-classes may override this method to change behavior.
	 * @param ctx the channel that user input command
	 * @param cause the exception cause  
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) 
    {
	    cause.printStackTrace();
    }
}
