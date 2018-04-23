package telegrambot.controller

import model.football.LeagueInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.parsers.FootballstatModelParser
import telegrambot.url.FootballstatUrlDealer
import java.nio.charset.Charset

@Component
class FootballstatProvider {
    @Autowired
    private lateinit var footballstatUrlDealer : FootballstatUrlDealer

    @Autowired
    private lateinit var footballstatModelParser : FootballstatModelParser

    @Autowired
    private lateinit var footballstatHttpController : HttpController

    fun availableLeagues() : List<LeagueInfo> {
        val content = footballstatHttpController.makeContentPOST(
                footballstatUrlDealer.availableLeaguesUrl, Charset.defaultCharset())

        return footballstatModelParser.parseAvailableLeagues(content)
    }
}