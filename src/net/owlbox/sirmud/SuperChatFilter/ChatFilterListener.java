package net.owlbox.sirmud.SuperChatFilter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatFilterListener implements Listener {

	private SuperChatFilter plugin;
	
	public ChatFilterListener ( SuperChatFilter p ) {
		this.plugin = p;
	}
	
	@EventHandler
	public void onChatEvent( PlayerChatEvent event ) {
		
		String thismsg = event.getMessage();
		
		for ( String k : plugin.filter.keySet()) {
			if( thismsg.matches(k)) {
				thismsg = thismsg.replaceAll(k, plugin.filter.get(k));
				Integer thisplw = plugin.player_warnings.get(event.getPlayer().getName());
				thisplw++;
				if(thisplw > plugin.getConfig().getInt("config.warnings",5)) {
					event.getPlayer().kickPlayer(plugin.getConfig().getString("config.strings.kick","You have been kicked for inapropriate language."));
					plugin.log.info(event.getPlayer().getName() + " was kicked for innapropriate language.");
					plugin.getServer().broadcast(event.getPlayer().getName() + " was kicked for inapropriate language.","bukkit.command.kick");
				}
				
				event.setMessage(thismsg);
			}
		}
	}
	
}
