package com.base.library.rx;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 作者：王东一
 * 创建时间：2017/5/4.
 */

public class RxBus {
    private static volatile RxBus mInstance;
    private final Subject<Object> subject;
    private Disposable disposable;


    public RxBus() {
        subject = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }


    /**
     * 发送事件
     *
     * @param object
     */
    public void send(Object object) {
        subject.onNext(object);
    }


    /**
     * @param classType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservale(Class<T> classType) {
        return subject.ofType(classType);
    }


    /**
     * 订阅
     *
     * @param bean
     * @param consumer
     */
    public void subscribe(Class bean, Consumer consumer) {
        disposable = toObservale(bean).subscribe(consumer);
    }

    /**
     * 取消订阅
     */
    public void unSubscribe() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
