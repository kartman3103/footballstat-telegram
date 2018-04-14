package telegram.tests

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
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

    @Test
    fun parseAvailableLeaguesTest() {

    }
}