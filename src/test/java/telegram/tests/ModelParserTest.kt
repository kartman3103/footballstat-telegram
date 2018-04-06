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
        Assert.assertEquals(414309712L, user?.id)
        Assert.assertEquals(false, user?.isBot)
        Assert.assertEquals("Pavel", user?.firstName)
        Assert.assertEquals("Demin", user?.secondName)
        Assert.assertEquals("ru", user?.languageCode)
    }

    @Test
    fun userNullTest() {
        val user = modelParser.parseUser(null)
        Assert.assertNull(user)
    }

    @Test
    fun incorrectUserJsonTest() {
        val user = modelParser.parseUser("{\"das\"")
        Assert.assertNull(user)
    }

    @Test
    fun chatParserTest() {
        val chat = modelParser.parseChat("{\"id\":414309712,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"type\":\"private\"}")

        Assert.assertNotNull(chat)
        Assert.assertEquals(414309712L, chat?.id)
        Assert.assertEquals("private", chat?.type)
        Assert.assertEquals("Pavel", chat?.firstName)
        Assert.assertEquals("Demin", chat?.lastName)
    }

    @Test
    fun chatNullTest() {
        val chat = modelParser.parseChat(null)
        Assert.assertNull(chat)
    }

    @Test
    fun chatIncorrectTest() {
        val chat = modelParser.parseChat("{\"das\"")
        Assert.assertNull(chat)
    }

    @Test
    fun messageParserTest() {
        val message = modelParser.parseMessage("{\"message_id\":5,\"from\":{\"id\":414309712,\"is_bot\":false,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"language_code\":\"ru\"},\"chat\":{\"id\":414309712,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"type\":\"private\"},\"date\":1523021455,\"text\":\"/getUpdates\",\"entities\":[{\"offset\":0,\"length\":11,\"type\":\"bot_command\"}]}},{\"update_id\":540766783,\n" +
                "\"message\":{\"message_id\":6,\"from\":{\"id\":414309712,\"is_bot\":false,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"language_code\":\"ru\"},\"chat\":{\"id\":414309712,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"type\":\"private\"},\"date\":1523026221,\"text\":\"/\"}}")

        Assert.assertNotNull(message)
        Assert.assertEquals(5L, message?.id)
        Assert.assertEquals(1523021455, message?.date)

        Assert.assertNotNull(message?.chat)
    }

    @Test
    fun messageNullTest() {
        val message = modelParser.parseMessage(null)
        Assert.assertNull(message)
    }

    @Test
    fun messageWithoutUserTest() {
        val message = modelParser.parseMessage("{\"message_id\":5,\"chat\":{\"id\":414309712,\"first_name\":\"Pavel\",\"last_name\":\"Demin\",\"type\":\"private\"},\"date\":1523021455,\"text\":\"/getUpdates\"}")

        Assert.assertNotNull(message)
        Assert.assertNotNull(message?.chat)
        Assert.assertEquals(5L, message?.id)
        Assert.assertEquals(1523021455, message?.date)
        Assert.assertEquals("/getUpdates", message?.text)
        Assert.assertNull(message?.from)
    }
}