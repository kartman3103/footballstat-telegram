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
import telegrambot.parsers.ModelParser

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
@TestPropertySource(locations= arrayOf("classpath:config/json-template.properties"))
class ModelParserTest {
    @Autowired
    private lateinit var modelParser : ModelParser

    @Value("\${correct.user}")
    private val correctUser : String? = null

    @Test
    fun userParserTest() {
        val user = modelParser.parseUser(correctUser)

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

    @Value("\${correct.chat}")
    private val correctChat : String? = null

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

    @Value("\${correct.message}")
    private val correctMessage : String? = null

    @Test
    fun messageParserTest() {
        val message = modelParser.parseMessage(correctMessage)

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

    @Value("\${correct.message.not.user}")
    private val correctMessageNotUser : String? = null

    @Test
    fun messageWithoutUserTest() {
        val message = modelParser.parseMessage(correctMessageNotUser)

        Assert.assertNotNull(message)
        Assert.assertNotNull(message?.chat)
        Assert.assertEquals(5L, message?.id)
        Assert.assertEquals(1523021455, message?.date)
        Assert.assertEquals("/getUpdates", message?.text)
        Assert.assertNull(message?.from)
    }

    @Value("\${correct.update}")
    private val correctUpdate : String? = null

    @Test
    fun updateParseTest() {
        val update = modelParser.parseUpdate(correctUpdate)

        Assert.assertNotNull(update?.message)
        Assert.assertEquals(540766782L, update?.id)

        Assert.assertNotNull(update?.message)
        Assert.assertNotNull(update?.message?.chat)
        Assert.assertEquals(5L, update?.message?.id)
        Assert.assertEquals(1523021455, update?.message?.date)
        Assert.assertEquals("/getUpdates", update?.message?.text)
    }

    @Test
    fun updateNullTest() {
        val update = modelParser.parseUpdate(null)
        Assert.assertNull(update)
    }

    @Test
    fun incorrectUpdateTest() {
        val update = modelParser.parseUpdate("{\"das\"")
        Assert.assertNull(update)
    }

//    @Test
//    fun updateResponseTest() {
//        val updateResponse = modelParser.parseUpdateResponse()
//    }
}