package telegrambot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties
@PropertySource("classpath:config/remote.yml")
open class RemoteConfig {
    var telegramUrl : String = "https://api.telegram.org"
        get set

    var botId : String = "bot412390579"
        get set

    var token : String = "AAGMICSbEDmvQGVndGJuzdPwq3Cfzo0qjNo"
        get set

    var useProxy : Boolean = false
        get set

    var proxyIP : String = "199.247.23.227"
        get set

    var proxyPort : Int = 10080
        get set
}