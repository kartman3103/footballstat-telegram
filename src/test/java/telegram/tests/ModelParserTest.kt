package telegram.tests

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.parsers.ModelInvalidationException
import telegrambot.parsers.ModelParser

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
@TestPropertySource(locations= arrayOf("classpath:config/test-data.properties"))
class ModelParserTest {
    @Autowired
    private lateinit var modelParser : ModelParser

    @Value("\${correct.user}")
    private val correctUser : String? = null

    @Test
    fun userParserTest() {
        val user = modelParser.parseUser(correctUser ?: "")

        Assert.assertNotNull(user)
        Assert.assertEquals(414309712L, user.id)
        Assert.assertEquals(false, user.isBot)
        Assert.assertEquals("Pavel", user.firstName)
        Assert.assertEquals("Demin", user.username)
        Assert.assertEquals("ru", user.languageCode)
    }

    @Value("\${correct.chat}")
    private val correctChat : String? = null

    @Test
    fun chatParserTest() {
        val chat = modelParser.parseChat(correctChat ?: "")

        Assert.assertNotNull(chat)
        Assert.assertEquals(414309712L, chat.id)
        Assert.assertEquals("private", chat.type)
        Assert.assertEquals("Pavel", chat.firstName)
        Assert.assertEquals("Demin", chat.lastName)
    }

    @Value("\${correct.message}")
    private val correctMessage : String? = null

    @Test
    fun messageParserTest() {
        val message = modelParser.parseMessage(correctMessage ?: "")

        Assert.assertNotNull(message)
        Assert.assertEquals(5L, message.id)
        Assert.assertEquals(1523021455, message.date)

        Assert.assertNotNull(message.chat)
    }

    @Value("\${correct.message.not.user}")
    private val correctMessageNotUser : String? = null

    @Test
    fun messageWithoutUserTest() {
        val message = modelParser.parseMessage(correctMessageNotUser ?: "")

        Assert.assertNotNull(message)
        Assert.assertNotNull(message.chat)
        Assert.assertEquals(5L, message.id)
        Assert.assertEquals(1523021455, message.date)
        Assert.assertEquals("/getUpdates", message.text)
        Assert.assertNull(message.from)
    }

    @Value("\${correct.update}")
    private val correctUpdate : String? = null

    @Test
    fun updateParseTest() {
        val update = modelParser.parseUpdate(correctUpdate ?: "")

        Assert.assertNotNull(update.message)
        Assert.assertEquals(540766782L, update.id)

        Assert.assertNotNull(update.message)
        Assert.assertNotNull(update.message?.chat)
        Assert.assertEquals(5L, update.message?.id)
        Assert.assertEquals(1523021455, update.message?.date)
        Assert.assertEquals("/getUpdates", update.message?.text)
    }

    @Value("\${correct.update.response}")
    private val correctUpdateResponse : String? = null

    @Test
    fun updateResponseTest() {
        val updates = modelParser.parseUpdates(correctUpdateResponse ?: "")

        Assert.assertNotNull(updates)
        Assert.assertTrue(updates.size == 1)

        val firstUpdate = updates[0]
        Assert.assertNotNull(firstUpdate)
        Assert.assertEquals(540766782L, firstUpdate.id)

        val updateMessage = firstUpdate.message
        Assert.assertNotNull(updateMessage)
        Assert.assertEquals(5L, updateMessage ?.id)
        Assert.assertEquals(1523021455, updateMessage?.date)

        Assert.assertNotNull(updateMessage?.chat)
    }

    @Value("\${correct.update.response.empty}")
    private val emptyUpdatesResponse : String? = null

    @Test
    fun emptyUpdatesResponseTest() {
        val updates = modelParser.parseUpdates(emptyUpdatesResponse ?: "")

        Assert.assertNotNull(updates)
        Assert.assertNotNull(updates)
        Assert.assertTrue(updates.size == 0)
    }

    @Test(expected = JsonMappingException::class)
    fun emptyStringUserTest() { modelParser.parseUser("") }

    @Test(expected = JsonParseException::class)
    fun incorrectUserJsonTest() { modelParser.parseUser("{\"das\"") }

    @Test(expected = JsonMappingException::class)
    fun emptyStringChatTest() { modelParser.parseChat("") }

    @Test(expected = JsonParseException::class)
    fun chatIncorrectTest() { modelParser.parseChat("{\"das\"") }

    @Test(expected = JsonMappingException::class)
    fun emptyMessageTest() { modelParser.parseMessage("") }

    @Test(expected = JsonMappingException::class)
    fun updateEmptyStringTest() { modelParser.parseUpdate("") }

    @Test(expected = JsonParseException::class)
    fun incorrectUpdateTest() { modelParser.parseUpdate("{\"das\"") }

    @Test(expected = JsonMappingException::class)
    fun emptyStringUpdateTest() { modelParser.parseUpdates("") }

    @Test(expected = JsonParseException::class)
    fun incorrectJsonUpdateResponseTest() { modelParser.parseUpdates("{\"das\"") }

    @Value("\${incorrect.user.without.id}")
    private val incorrectUserWithoutId : String? = null

    @Test(expected = ModelInvalidationException::class)
    fun incorrectUserWithouIdTest() { modelParser.parseUser(incorrectUserWithoutId ?: "") }
}