package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.controller.FootballstatProvider

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class FootballstatProviderTest {
    @Autowired
    private lateinit var footballstatProvider : FootballstatProvider

    @Test
    fun getAvailableLeaguesTest() {
        val leagues = footballstatProvider.availableLeagues()

        Assert.assertNotNull(leagues)
        Assert.assertTrue(!leagues.isEmpty())
    }
}