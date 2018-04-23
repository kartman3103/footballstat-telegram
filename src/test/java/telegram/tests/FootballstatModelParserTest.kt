package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.parsers.FootballstatModelParser

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
@TestPropertySource(locations= arrayOf("classpath:config/test-data.properties"))
class FootballstatModelParserTest {
    @Autowired
    private lateinit var footballstatModelParser : FootballstatModelParser

    @Value("\${available.leagues}")
    var availableLeagues : String? = null

    @Test
    fun parseAvailableLeaguesTest() {
        val availableLeagues = footballstatModelParser.parseAvailableLeagues(availableLeagues ?: "")

        Assert.assertNotNull(availableLeagues)
        Assert.assertTrue(availableLeagues.isNotEmpty())
    }

    @Value("\${league}")
    var league : String? = null

    @Test
    fun parseLeagueTest() {
        val league = footballstatModelParser.parseLeague(league ?: "")

        Assert.assertNotNull(league)
        Assert.assertEquals("426", league.id)
    }
}