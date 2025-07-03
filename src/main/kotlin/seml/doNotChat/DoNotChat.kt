package seml.doNotChat

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin

class DoNotChat : JavaPlugin(), org.bukkit.event.Listener {

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(this, this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        val blocked = listOf("msg", "tell", "w", "me","teammsg")
        val command = event.message.split(" ")[0].removePrefix("/").lowercase()
        if (command in blocked) {
            event.isCancelled = true
            event.player.sendMessage("이 명령어는 사용할 수 없습니다.")
        }
    }

    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {
        event.isCancelled = true
        event.player.sendMessage("채팅이 비활성화되어 있습니다.")
    }
}
