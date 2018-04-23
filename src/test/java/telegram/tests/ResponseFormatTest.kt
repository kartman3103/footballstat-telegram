package telegram.tests

import model.football.LeagueInfo
import org.apache.http.client.utils.URIBuilder
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class ResponseFormatTest {
    @Test
    fun testUriBuilder() {
        val leagues = listOf("APL 2016", "RFPL 2015")

        val builder = URIBuilder()
        builder.addParameter("leagues", leagues.toString())
        val uri = builder.build()

        Assert.assertNotNull(uri)
    }
}