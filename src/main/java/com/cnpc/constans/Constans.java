package com.cnpc.constans;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

import java.net.InetAddress;
import java.net.NetworkInterface;

import static io.netty.channel.ChannelOption.valueOf;

/**
 *
 * netty参数配置解释。
 *
 */
public class Constans {
    //Netty参数，ByteBuf的分配器，默认值为ByteBufAllocator.DEFAULT，4.0版本为UnpooledByteBufAllocator，
    //4.1版本为PooledByteBufAllocator。该值也可以使用系统参数io.netty.allocator.type配置，使用字符串值："unpooled"，"pooled"。
    public static final ChannelOption<ByteBufAllocator> ALLOCATOR = valueOf("ALLOCATOR");

    //Netty参数，用于Channel分配接受Buffer的分配器，默认值为AdaptiveRecvByteBufAllocator.DEFAULT，是一个自适应的接受缓冲区分配器，
    //能根据接受到的数据自动调节大小。可选值为FixedRecvByteBufAllocator，固定大小的接受缓冲区分配器。
    public static final ChannelOption<RecvByteBufAllocator> RCVBUF_ALLOCATOR = valueOf("RCVBUF_ALLOCATOR");

    //Netty参数，消息大小估算器，默认为DefaultMessageSizeEstimator.DEFAULT。估算ByteBuf、ByteBufHolder和FileRegion的大小，其中ByteBuf和ByteBufHolder为实际大小，
    //FileRegion估算值为0。该值估算的字节数在计算水位时使用，FileRegion为0可知FileRegion不影响高低水位
    public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");

    //Netty参数，连接超时毫秒数，默认值30000毫秒即30秒。
    public static final ChannelOption<Integer> CONNECT_TIMEOUT_MILLIS = valueOf("CONNECT_TIMEOUT_MILLIS");

    /** @deprecated */
    // Netty参数，一次Loop读取的最大消息数，对于ServerChannel或者NioByteChannel，默认值为16，其他Channel默认值为1。
    // 默认值这样设置，是因为：ServerChannel需要接受足够多的连接，保证大吞吐量，NioByteChannel可以减少不必要的系统调用select。
    @Deprecated
    public static final ChannelOption<Integer> MAX_MESSAGES_PER_READ = valueOf("MAX_MESSAGES_PER_READ");

    //Netty参数，一个Loop写操作执行的最大次数，默认值为16。也就是说，对于大数据量的写操作至多进行16次，
    //如果16次仍没有全部写完数据，此时会提交一个新的写任务给EventLoop，任务将在下次调度继续执行。
    //这样，其他的写请求才能被响应不会因为单个大数据量写请求而耽误
    public static final ChannelOption<Integer> WRITE_SPIN_COUNT = valueOf("WRITE_SPIN_COUNT");

    /** @deprecated */
    @Deprecated
    //Netty参数，写高水位标记，默认值64KB。如果Netty的写缓冲区中的字节超过该值，Channel的isWritable()返回False。
    public static final ChannelOption<Integer> WRITE_BUFFER_HIGH_WATER_MARK = valueOf("WRITE_BUFFER_HIGH_WATER_MARK");

    /** @deprecated */
    @Deprecated
    //Netty参数，写低水位标记，默认值32KB。当Netty的写缓冲区中的字节超过高水位之后若下降到低水位，则Channel的isWritable()返回True。
    //写高低水位标记使用户可以控制写入数据速度，从而实现流量控制。
    //推荐做法是：每次调用channl.write(msg)方法首先调用channel.isWritable()判断是否可写
    public static final ChannelOption<Integer> WRITE_BUFFER_LOW_WATER_MARK = valueOf("WRITE_BUFFER_LOW_WATER_MARK");
    public static final ChannelOption<WriteBufferWaterMark> WRITE_BUFFER_WATER_MARK = valueOf("WRITE_BUFFER_WATER_MARK");

    //Netty参数，一个连接的远端关闭时本地端是否关闭，默认值为False。值为False时，连接自动关闭；为True时，
    //触发ChannelInboundHandler的userEventTriggered()方法，事件为ChannelInputShutdownEvent。
    public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE = valueOf("ALLOW_HALF_CLOSURE");

    //Netty参数，自动读取，默认值为True。Netty只在必要的时候才设置关心相应的I/O事件。对于读操作，需要调用channel.read()设置关心的I/O事件为OP_READ，
    //这样若有数据到达才能读取以供用户处理。该值为True时，每次读操作完毕后会自动调用channel.read()，
    //从而有数据到达便能读取；否则，需要用户手动调用channel.read()。需要注意的是：当调用config.setAutoRead(boolean)方法时，
    //如果状态由false变为true，将会调用channel.read()方法读取数据；由true变为false，将调用config.autoReadCleared()方法终止数据读取。
    public static final ChannelOption<Boolean> AUTO_READ = valueOf("AUTO_READ");

    //自动关闭，默认为false;
    public static final ChannelOption<Boolean> AUTO_CLOSE = valueOf("AUTO_CLOSE");

    //Socket参数，设置广播模式
    public static final ChannelOption<Boolean> SO_BROADCAST = valueOf("SO_BROADCAST");

    //Channeloption.SO_KEEPALIVE参数对应于套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接，
    //当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的
    //连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
    public static final ChannelOption<Boolean> SO_KEEPALIVE = valueOf("SO_KEEPALIVE");

    //　ChannelOption.SO_SNDBUF参数对应于套接字选项中的SO_SNDBUF，
    // ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区
    //　的大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。
    public static final ChannelOption<Integer> SO_SNDBUF = valueOf("SO_SNDBUF");
    public static final ChannelOption<Integer> SO_RCVBUF = valueOf("SO_RCVBUF");

    //ChanneOption.SO_REUSEADDR对应于套接字选项中的SO_REUSEADDR，这个参数表示允许重复使用本地地址和端口，
    public static final ChannelOption<Boolean> SO_REUSEADDR = valueOf("SO_REUSEADDR");

    //hannelOption.SO_LINGER参数对应于套接字选项中的SO_LINGER,Linux内核默认的处理方式是当用户调用close（）方法的时候，
    //函数返回，在可能的情况下，尽量发送数据，不一定保证
    //会发生剩余的数据，造成了数据的不确定性，使用SO_LINGER可以阻塞close()的调用时间，直到数据完全发送
    public static final ChannelOption<Integer> SO_LINGER = valueOf("SO_LINGER");

    //BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
    //如果未设置或所设置的值小于1，Java将使用默认值50。
    public static final ChannelOption<Integer> SO_BACKLOG = valueOf("SO_BACKLOG");

    //设置socket调用InputStream读数据的超时时间，以毫秒为单位，如果超过这个时候，会抛出java.net.SocketTimeoutException。
    public static final ChannelOption<Integer> SO_TIMEOUT = valueOf("SO_TIMEOUT");

    //IP参数，设置IP头部的Type-of-Service字段，用于描述IP包的优先级和QoS选项。
    public static final ChannelOption<Integer> IP_TOS = valueOf("IP_TOS");

    //对应IP参数IP_MULTICAST_IF，设置对应地址的网卡为多播模式。
    public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");

    //对应IP参数IP_MULTICAST_IF2，同上但支持IPV6。
    public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");

    //IP参数，多播数据报的time-to-live即存活跳数。
    public static final ChannelOption<Integer> IP_MULTICAST_TTL = valueOf("IP_MULTICAST_TTL");

    //对应IP参数IP_MULTICAST_LOOP，设置本地回环接口的多播功能。由于IP_MULTICAST_LOOP返回True表示关闭，所以Netty加上后缀_DISABLED防止歧义。
    public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED = valueOf("IP_MULTICAST_LOOP_DISABLED");

    //在TCP/IP协议中，无论发送多少数据，总是要在数据前面加上协议头，同时，对方接收到数据，也需要发送ACK表示确认。
    // 为了尽可能的利用网络带宽，TCP总是希望尽可能的发送足够大的数据。这里就涉及到一个名为Nagle的算法，
    // 该算法的目的就是为了尽可能发送大块数据，避免网络中充斥着许多小数据块。
    //TCP_NODELAY就是用于启用或关于Nagle算法。如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；
    // 如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false。
    public static final ChannelOption<Boolean> TCP_NODELAY = valueOf("TCP_NODELAY");

    /** @deprecated */
    @Deprecated
    public static final ChannelOption<Boolean> DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION = valueOf("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");
    public static final ChannelOption<Boolean> SINGLE_EVENTEXECUTOR_PER_GROUP = valueOf("SINGLE_EVENTEXECUTOR_PER_GROUP");
}
