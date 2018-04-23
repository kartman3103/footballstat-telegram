package telegrambot.controller.request

import org.apache.http.HttpHost
import org.apache.http.client.fluent.Executor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import telegrambot.config.RemoteConfig

@Configuration
open class RequestSenderConfiguration {
    @Bean
    open fun proxyHost(remoteConfig : RemoteConfig) : HttpHost {
        return HttpHost(remoteConfig.proxyIP, remoteConfig.proxyPort)
    }

    @Bean
    open fun proxyExecutor(proxyHost : HttpHost) : Executor {
        return Executor.newInstance().authPreemptive(proxyHost)
    }

    @Bean
    open fun requestSender(remoteConfig : RemoteConfig) : RequestSender {
        if (remoteConfig.useProxy) {
            return ProxyRequestSender()
        }
        return DirectRequestSender()
    }
}