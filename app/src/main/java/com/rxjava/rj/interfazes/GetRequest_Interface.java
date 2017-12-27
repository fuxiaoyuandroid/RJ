package com.rxjava.rj.interfazes;

import com.rxjava.rj.beans.TranslationAfter;
import com.rxjava.rj.beans.TranslationBefore;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<TranslationBefore> getCallBefore();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20china")
    Observable<TranslationAfter> getCallAfter();
}
