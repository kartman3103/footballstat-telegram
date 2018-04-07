package telegrambot.model

data class Update(val id : Long, val message: Message? = null)