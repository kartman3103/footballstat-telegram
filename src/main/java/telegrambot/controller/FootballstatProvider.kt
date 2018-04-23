package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.url.FootballstatUrlDealer
import java.nio.charset.Charset

@Component
class FootballstatProvider {
    @Autowired
    private lateinit var footballstatUrlDealer : FootballstatUrlDealer

    @Autowired
    private lateinit var footballstatHttpController : HttpController

    fun availableLeagues() : String {
        return footballstatHttpController.makeContentPOST(
                footballstatUrlDealer.availableLeaguesUrl, Charset.defaultCharset())
    }
}