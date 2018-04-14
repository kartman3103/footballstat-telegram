package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.config.FootballstatConfig
import java.nio.charset.Charset

@Component
class FootballstatProvider {
    @Autowired
    private lateinit var footballstatConfig : FootballstatConfig

    @Autowired
    private lateinit var httpController : HttpController

    fun availableLeagues() : String {
        return httpController.makeContentGET(
                "${footballstatConfig.serverUrl}/${footballstatConfig.requestAvailableLeagues}",
                Charset.defaultCharset())
    }
}