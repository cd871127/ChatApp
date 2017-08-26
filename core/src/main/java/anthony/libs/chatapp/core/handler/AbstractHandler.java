package anthony.libs.chatapp.core.handler;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/8/14.
 */
public abstract class AbstractHandler<V> implements Callable<V> {
    public abstract V handle();

    @Override
    public V call() {
        return handle();
    }

    protected byte[] read(SocketChannel socketChannel, int length) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
        byteBuffer.clear();
        byte[] bytes = new byte[length];
        //这个方法没有超时的设置
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        byteBuffer.get(bytes);
        return bytes;
    }
}
