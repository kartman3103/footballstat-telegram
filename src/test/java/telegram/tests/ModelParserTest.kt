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
        val user = modelParser.parseUserResponse(correctUser ?: "")

        Assert.assertNotNull(user)
        Assert.assertEquals(414309712L, user.id)
        Assert.assertEquals(false, user.isBot)
        Assert.assertEquals("Pavel", user.firstName)
        Assert.assertEquals("Demin", user.username)
        Assert.assertEquals("ru", user.languageCode)
    }

    @Value("\${correct.chat}")
    private val correctChat : String? = null

    @Value("\${correct.message}")
    private val correctMessage : String? = null

    @Test
    fun messageParserTest() {
        val message = modelParser.parseMessageResponse(correctMessage ?: "")

        Assert.assertNotNull(message)
        Assert.assertEquals(5L, message.id)
        Assert.assertEquals(1523021455, message.date)

        val chat = message.chat
        Assert.assertNotNull(chat)
        Assert.assertEquals(414309712L, chat.id)
        Assert.assertEquals("private", chat.type)
        Assert.assertEquals("Pavel", chat.firstName)
        Assert.assertEquals("Demin", chat.lastName)


    }

    @Value("\${correct.message.not.user}")
    private val correctMessageNotUser : String? = null

    @Test
    fun messageWithoutUserTest() {
        val message = modelParser.parseMessageResponse(correctMessageNotUser ?: "")

        Assert.assertNotNull(message)
        Assert.assertNotNull(message.chat)
        Assert.assertEquals(5L, message.id)
        Assert.assertEquals(1523021455, message.date)
        Assert.assertEquals("/getUpdates", message.text)
        Assert.assertNull(message.from)
    }

    @Value("\${correct.update}")
    private val correctUpdate : String? = null

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

    @Value("\${correct.sended.message.response}")
    private val correctSendedMessageResponse : String? = null

    @Test
    fun sendMessageResponseTest() {
        val message = modelParser.parseMessageResponse(correctSendedMessageResponse ?: "")
    }

    @Test(expected = JsonMappingException::class)
    fun emptyStringUserTest() { modelParser.parseUserResponse("") }

    @Test(expected = JsonParseException::class)
    fun incorrectUserJsonTest() { modelParser.parseUserResponse("{\"das\"") }

    @Test(expected = JsonMappingException::class)
    fun emptyMessageTest() { modelParser.parseMessageResponse("") }

    @Test(expected = JsonMappingException::class)
    fun emptyStringUpdateTest() { modelParser.parseUpdates("") }

    @Test(expected = JsonParseException::class)
    fun incorrectJsonUpdateResponseTest() { modelParser.parseUpdates("{\"das\"") }

    @Value("\${incorrect.user.without.id}")
    private val incorrectUserWithoutId : String? = null

    @Test(expected = ModelInvalidationException::class)
    fun incorrectUserWithouIdTest() { modelParser.parseUserResponse(incorrectUserWithoutId ?: "") }
}