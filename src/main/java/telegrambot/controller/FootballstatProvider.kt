package telegrambot.controller

import model.football.League
import model.football.LeagueInfo
import org.apache.http.client.utils.URIBuilder
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
        val uriBuilder = URIBuilder(footballstatUrlDealer.availableLeaguesUrl)
        val url = uriBuilder.build().toString()

        val content = footballstatHttpController.makeContentPOST(url, Charset.defaultCharset())
        return footballstatModelParser.parseAvailableLeagues(content)
    }

    fun getLeague(leagueId : String, matchDay : Int) : League {
        val uriBuilder = URIBuilder(footballstatUrlDealer.league)
        uriBuilder.addParameter("leagueId", leagueId)
        uriBuilder.addParameter("matchDay", matchDay.toString())
        val url = uriBuilder.build().toString()

        val content = footballstatHttpController.makeContentPOST(url, Charset.defaultCharset())
        return footballstatModelParser.parseLeague(content)
    }
}