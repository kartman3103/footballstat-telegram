package telegrambot.url

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.config.FootballstatConfig
import javax.annotation.PostConstruct

@Component
class FootballstatUrlDealer {
    @Autowired
    private lateinit var footballstatConfig : FootballstatConfig

    @PostConstruct
    fun init() {
        serverUrl = footballstatConfig.serverUrl
        availableLeaguesUrl = "${serverUrl}/${footballstatConfig.requestAvailableLeagues}"
    }

    lateinit var serverUrl : String

    lateinit var availableLeaguesUrl: String
}