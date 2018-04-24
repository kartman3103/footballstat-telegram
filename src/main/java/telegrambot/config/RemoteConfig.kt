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

    var proxyIP : String = "198.50.143.31"
        get set

    var proxyPort : Int = 3128
        get set

    var proxyConnectionTimeout : Int = 1000
        get set

    var proxySocketTimeout : Int = 1000
        get set
}