package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class MessageMapperTest {
    @Test
    fun leagueParseTest() {
        val testString = "leagueName"
        val parts = testString.split("_")

        Assert.assertNotNull(parts)
    }
}