package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.parsers.ModelParser

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class ModelParserTest {

    @Autowired
    private lateinit var modelParser : ModelParser

    @Test
    fun userParserTest() {
        val user = modelParser.parseUser("{\"id\":414309712,\"is_bot\":false,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"language_code\":\"ru\"}")

        Assert.assertNotNull(user)
        Assert.assertEquals(414309712, user.id)
        Assert.assertEquals(false, user.isBot)
        Assert.assertEquals("Pavel", user.firstName)
        Assert.assertEquals("Demin", user.secondName)
        Assert.assertEquals("ru", user.languageCode)
    }

    @Test
    fun chatParserTest() {
        val chat = modelParser.parseChat("{\"id\":414309712,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"type\":\"private\"}")

        Assert.assertNotNull(chat)
        Assert.assertEquals(414309712, chat.id)
        Assert.assertEquals("private", chat.type)
        Assert.assertEquals("Pavel", chat.firstName)
        Assert.assertEquals("Demin", chat.lastName)
    }

}