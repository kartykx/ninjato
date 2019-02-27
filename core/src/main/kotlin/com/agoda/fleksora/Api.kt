package com.agoda.fleksora

import com.agoda.fleksora.dsl.Commons
import com.agoda.fleksora.http.Headers
import com.agoda.fleksora.http.HttpClient
import com.agoda.fleksora.http.Method
import com.agoda.fleksora.http.Request
import com.agoda.fleksora.intercept.Interceptors
import com.agoda.fleksora.log.Logger
import com.agoda.fleksora.policy.FallbackPolicy
import com.agoda.fleksora.policy.RetryPolicy
import com.agoda.fleksora.serial.ConverterFactories

abstract class Api : Commons {
    override val headers = Headers()
    override val interceptors = Interceptors()
    override val converterFactories = ConverterFactories()

    override var retryPolicy: RetryPolicy? = null
    override var fallbackPolicy: FallbackPolicy? = null

    protected lateinit var client: HttpClient
    protected var logger: Logger? = null

    abstract val baseUrl: String

    inline fun <reified T> get(configurator: Request.Configurator.() -> Unit): T = call(Method.Get, Request.Configurator().apply(configurator))
    inline fun <reified T> head(configurator: Request.Configurator.() -> Unit): T = call(Method.Head, Request.Configurator().apply(configurator))
    inline fun <reified T> post(configurator: Request.Configurator.WithBody.() -> Unit): T = call(Method.Post, Request.Configurator.WithBody().apply(configurator))
    inline fun <reified T> put(configurator: Request.Configurator.WithBody.() -> Unit): T = call(Method.Put, Request.Configurator.WithBody().apply(configurator))
    inline fun <reified T> delete(configurator: Request.Configurator.WithBody.() -> Unit): T = call(Method.Delete, Request.Configurator.WithBody().apply(configurator))
    inline fun <reified T> options(configurator: Request.Configurator.WithBody.() -> Unit): T = call(Method.Options, Request.Configurator.WithBody().apply(configurator))
    inline fun <reified T> patch(configurator: Request.Configurator.WithBody.() -> Unit): T = call(Method.Patch, Request.Configurator.WithBody().apply(configurator))

    @PublishedApi
    internal inline fun <reified T> call(method: Method, configurator: Request.Configurator): T {
        TODO()
    }

    open class Configurator {
        var logger: Logger? = null
        var httpClient: HttpClient? = null

        open fun configure(instance: Api) = instance.also {
            it.client = httpClient!!
            it.logger = logger
        }
    }

    companion object {
        fun configure(instance: Api, builder: Api.Configurator.() -> Unit)
                = Configurator().apply(builder).configure(instance)
    }
}