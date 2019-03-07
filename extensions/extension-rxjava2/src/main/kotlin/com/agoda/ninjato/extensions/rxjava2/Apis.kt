package com.agoda.ninjato.extensions.rxjava2

import com.agoda.ninjato.Api
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

inline fun <A : Api, reified T> A.completable(crossinline receiver: A.() -> Unit): Completable = Completable.fromCallable { receiver() }

inline fun <A : Api, reified T> A.single(crossinline receiver: A.() -> T): Single<T> = Single.fromCallable { receiver() }

inline fun <A : Api, reified T> A.observable(crossinline receiver: A.() -> T): Observable<T> = Observable.fromCallable { receiver() }

inline fun <A : Api, reified T> A.flowable(crossinline receiver: A.() -> T): Flowable<T> = Flowable.fromCallable { receiver() }


