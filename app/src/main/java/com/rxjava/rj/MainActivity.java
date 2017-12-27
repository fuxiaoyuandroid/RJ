package com.rxjava.rj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxjava.rj.beans.TranslationAfter;
import com.rxjava.rj.beans.TranslationBefore;
import com.rxjava.rj.interfazes.GetRequest_Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    /*private static final String TAG = "RxJava";

    Observable<TranslationBefore> observableBefore;
    Observable<TranslationAfter> observableAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rxJavaMap();

        //rxJavaFlatMap();

        //rxJavaBuffer();

        //rxJavaDemo();

        //rxJavaConcat();

        //rxJavaMerge();

        //rxJavaConcatDelayError();

        //rxJavaZip();
        rxJavaZipDemo();
    }

    private void rxJavaZipDemo() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        observableBefore = request.getCallBefore().subscribeOn(Schedulers.io()); // 新开线程进行网络请求1
        observableAfter = request.getCallAfter().subscribeOn(Schedulers.io());// 新开线程进行网络请求2
        // 即2个网络请求异步 & 同时发送

        // 步骤4：通过使用Zip（）对两个网络请求进行合并再发送
        Observable.zip(observableBefore, observableAfter,
                new BiFunction<TranslationBefore, TranslationAfter, String>() {
            @Override
            public String apply(@NonNull TranslationBefore translationBefore, @NonNull TranslationAfter translationAfter) throws Exception {
                return translationBefore.show()+" & "+translationAfter.show();
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                // 结合显示2个网络请求的数据结果
                Log.d(TAG, "最终接收到的数据是：" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("登录失败");
            }
        });

    }

    private void rxJavaZip() {
        //创建第一个被观察者
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(1000);
                e.onNext(2);
                Thread.sleep(1000);
                e.onNext(3);
                Thread.sleep(1000);
                //e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                Thread.sleep(1000);
                e.onNext("B");
                Thread.sleep(1000);
                e.onNext("C");
                Thread.sleep(1000);
                e.onNext("D");
                Thread.sleep(1000);
                //e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return integer+s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaConcatDelayError() {

        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new NullPointerException());
                e.onComplete();

            }
        }),Observable.just(4,5,6)).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件"+ value  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });

    }

    private void rxJavaMerge() {
        Observable.merge(Observable.intervalRange(0,3,1,1, TimeUnit.SECONDS),
                Observable.intervalRange(7,3,1,1,TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void rxJavaConcat() {

        Observable.concat(Observable.just(1,2,3,4),Observable.just(5,6,7,8))
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件"+ value  );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    private void rxJavaDemo() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 步骤2：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        observableBefore = request.getCallBefore();

        observableAfter = request.getCallAfter();

        observableBefore.subscribeOn(Schedulers.io())// （初始被观察者）切换到IO线程进行网络请求1
                        .observeOn(AndroidSchedulers.mainThread())// （新观察者）切换到主线程 处理网络请求1的结果
                        .doOnNext(new Consumer<TranslationBefore>() {
                            @Override
                            public void accept(TranslationBefore translationBefore) throws Exception {
                                Log.d(TAG, "第1次网络请求成功");
                                translationBefore.show();
                                // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                            }
                        })
                        .observeOn(Schedulers.io())
                        // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                        // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                        // 但对于初始观察者，它则是新的被观察者
                        .flatMap(new Function<TranslationBefore, ObservableSource<TranslationAfter>>() {
                            @Override
                            public ObservableSource<TranslationAfter> apply(TranslationBefore translationBefore) throws Exception {
                                // 将网络请求1转换成网络请求2，即发送网络请求2
                                return observableAfter;
                            }
                        }).observeOn(AndroidSchedulers.mainThread()) // （初始观察者）切换到主线程 处理网络请求2的结果
                        .subscribe(new Consumer<TranslationAfter>() {
                            @Override
                            public void accept(TranslationAfter translationAfter) throws Exception {
                                Log.d(TAG, "第2次网络请求成功");
                                translationAfter.show();
                                // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d(TAG, "登录失败");
                            }
                        });
    }

    private void rxJavaBuffer() {
        Observable.just(1,2,3,4,5).buffer(3,2).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Integer> value) {
                Log.d(TAG, " 缓存区里的事件数量 = " +  value.size());
                for (Integer v:value) {
                    Log.d(TAG, "onNext: 事件  "+v);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
                emitter.onNext(8);
                emitter.onNext(9);
                emitter.onNext(10);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                }
                // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                // 最终合并，再发送给被观察者
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: "+s);
            }
        });
    }

    private void rxJavaMap() {

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {

            @Override
            public String apply(Integer integer) throws Exception {
                return "使用Map变换操作符将事件"+integer+"的参数从integer变换成String"+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: "+s);
            }
        });

    }*/
}
