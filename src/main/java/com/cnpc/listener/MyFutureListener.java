package com.cnpc.listener;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 异步执行结果监听器
 * 可用来获取异步执行结果。
 *
 */
public class MyFutureListener implements GenericFutureListener {
    @Override
    public void operationComplete(Future future) throws Exception {
        //同步阻塞
        future.sync();
        //异步操操作是否完成，正常终止，异常，取消都是完成
        future.isDone();
        //异步操作完成且正常终止
        future.isSuccess();
        //异步操作是否可以取消
        future.isCancellable();
        //异步操作是否取消
        future.isCancelled();
        //取消异步操作。
        future.cancel(true);
        //异步操作失败的原因
        future.cause();
        //非阻塞地返回异步结果，如果尚未完成返回null
        future.getNow();
        //阻塞。直到异步操作取得结果，但最长时间为timeout;
        // uture.get(100,null);
        //阻塞。直到异步操作取得结果。
        future.get();
        //移除监听器
        future.removeListener(null);
        //添加一个监听者，异步操作完成时回调，类比javascript的回调函数
        //future.addListener(null);
    }
}
