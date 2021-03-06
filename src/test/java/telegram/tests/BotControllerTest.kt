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
import telegrambot.controller.BotController

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
@TestPropertySource(locations= arrayOf("classpath:config/test-data.properties"))
class BotControllerTest {
    @Autowired
    private lateinit var botController : BotController

    @Test
    fun botPingTest() {
        val user = botController.getMe()
        Assert.assertEquals(412390579L, user?.id)
        Assert.assertEquals(true, user?.isBot)
        Assert.assertEquals("fs_bot", user?.firstName)
        Assert.assertEquals("football_stat_bot", user?.username)
    }

    @Test
    fun botGetUpdate() {
        val updates = botController.getUpdates(-1)
        Assert.assertNotNull(updates)
    }

    @Test
    fun botApplyUpdates() {
        var offset = -1
        val updates = botController.getUpdates(offset)

        Assert.assertNotNull(updates)

        if (!updates.isEmpty()) {
            offset = updates.last().id + 1
            val repeatUpdates = botController.getUpdates(offset)

            Assert.assertTrue(repeatUpdates.isEmpty())
        }
    }

    @Value("\${test.chat.id}")
    private var chatId : Long? = null

    @Test
    fun botSendMessage() {
        val message = botController.sendMessage(chatId ?:
                throw IllegalArgumentException("Chat id cannot be null"), "Message")

        Assert.assertNotNull(message)
        Assert.assertEquals("Message", message.text)
    }
}