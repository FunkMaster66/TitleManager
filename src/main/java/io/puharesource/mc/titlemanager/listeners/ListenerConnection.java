package io.puharesource.mc.titlemanager.listeners;

import io.puharesource.mc.titlemanager.Config;
import io.puharesource.mc.titlemanager.api.TabTitleCache;
import io.puharesource.mc.titlemanager.api.TabTitleObject;
import io.puharesource.mc.titlemanager.api.TextConverter;
import io.puharesource.mc.titlemanager.api.TitleObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerConnection implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Config.isUsingConfig()) {
            TitleObject titleObject = Config.getWelcomeObject();
            TabTitleObject tabTitleObject = Config.getTabTitleObject();

            if(titleObject.getTitle() != null)
                titleObject.setTitle(TextConverter.setPlayerName(player, titleObject.getTitle()));
            if(titleObject.getSubtitle() != null)
                titleObject.setSubtitle(TextConverter.setPlayerName(player, titleObject.getSubtitle()));

            if(tabTitleObject.getHeader() != null)
                tabTitleObject.setHeader(TextConverter.setPlayerName(player, tabTitleObject.getHeader()));
            if(tabTitleObject.getFooter() != null)
                tabTitleObject.setFooter(TextConverter.setPlayerName(player, tabTitleObject.getFooter()));

            titleObject.send(event.getPlayer());
            tabTitleObject.send(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TabTitleCache.removeTabTitle(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onKick(PlayerKickEvent event) {
        TabTitleCache.removeTabTitle(event.getPlayer().getUniqueId());
    }
}
