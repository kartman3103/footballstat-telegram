package telegrambot.controller.request

import org.apache.http.HttpHost
import org.apache.http.client.fluent.Executor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import telegrambot.config.RemoteConfig
import telegrambot.controller.HttpController

@Configuration
open class RequestConfiguration {
    @Bean
    open fun proxyHost(remoteConfig : RemoteConfig) : HttpHost {
        return HttpHost(remoteConfig.proxyIP, remoteConfig.proxyPort)
    }

    @Bean
    open fun proxyExecutor(proxyHost : HttpHost) : Executor {
        return Executor.newInstance().authPreemptive(proxyHost)
    }

    @Bean
    open fun telegramHttpController(remoteConfig : RemoteConfig) : HttpController {
        return HttpController(requestSender(remoteConfig))
    }

    @Bean
    open fun footballstatHttpController() : HttpController {
        return HttpController(DirectRequestSender())
    }

    @Bean
    open fun requestSender(remoteConfig : RemoteConfig) : RequestSender {
        if (remoteConfig.useProxy) {
            return ProxyRequestSender()
        }
        return DirectRequestSender()
    }
}