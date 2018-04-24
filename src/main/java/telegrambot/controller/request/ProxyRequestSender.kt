package telegrambot.controller.request

import org.apache.http.HttpHost
import org.apache.http.client.fluent.Executor
import org.apache.http.client.fluent.Request
import org.apache.http.client.fluent.Response
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import telegrambot.config.RemoteConfig

open class ProxyRequestSender : RequestSender {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var proxyExecutor : Executor

    @Autowired
    private lateinit var proxyHost : HttpHost

    @Autowired
    private lateinit var remoteConfig : RemoteConfig

    override fun sendRequest(request : Request) : Response {
        try {
            request.connectTimeout(remoteConfig.proxyConnectionTimeout)
            request.socketTimeout(remoteConfig.proxySocketTimeout)
            return proxyExecutor.execute(request.viaProxy(proxyHost))
        }
        catch (ex : Exception) {
            logger.error("Unsuccessfull request: ", ex)
            throw ex
        }
    }
}