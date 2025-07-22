package seml.doNotChat

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin
import net.kyori.adventure.text.Component

class DoNotChat : JavaPlugin(), org.bukkit.event.Listener {
    private var isChatEnabled: Boolean = false
    private var enableAnonymous: Boolean = false
    private var isWhisper: Boolean = false

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(this, this)

        saveDefaultConfig()
        isChatEnabled = config.getBoolean("allow-chat", false)
        enableAnonymous = config.getBoolean("anonymous-chat", false)
        isWhisper = config.getBoolean("allow-whisper", false)


    }


    @EventHandler
    fun onCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        if (!isWhisper)  {
            val blocked = listOf("msg", "tell", "w", "me","teammsg")
            val command = event.message.split(" ")[0].removePrefix("/").lowercase()
            if (command in blocked) {
                event.isCancelled = true
                event.player.sendMessage("이 명령어는 사용할 수 없습니다.")
            }
        }
    }

    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {
        if (!isChatEnabled) {
            event.isCancelled = true
            event.player.sendMessage("채팅이 비활성화되어 있습니다.")
            return
        }
        if (enableAnonymous) {
            val anonymousPrefix = Component.text("<익명> ")

            event.renderer { _, _, message, _ ->
                anonymousPrefix.append(message)
            }
        }
    }
}
