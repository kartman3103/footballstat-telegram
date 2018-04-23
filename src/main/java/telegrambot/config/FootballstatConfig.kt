package telegrambot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties
@PropertySource("classpath:config/footballstat.yml")
open class FootballstatConfig {
    var serverUrl : String = "http://localhost:8080"

    var requestAvailableLeagues : String = "availableLeagues"

    var requestLeague : String = "league"
}