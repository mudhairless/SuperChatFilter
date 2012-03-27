package net.owlbox.sirmud.SuperChatFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			Pattern pat = Pattern.compile(k);
			Matcher match = pat.matcher(thismsg);
			
			if( match.find() ) {
				thismsg = thismsg.replaceAll(k, (String) plugin.filter.get(k));
				Integer thisplw = (Integer) plugin.player_warnings.get(event.getPlayer().getName());
				if(thisplw == null) {
					thisplw = new Integer(0);
				}
				thisplw++;
				plugin.player_warnings.put(event.getPlayer().getName(), thisplw);
				if(thisplw > plugin.getConfig().getInt("config.warnings",5)) {
					plugin.getConfig().set("warnings", plugin.player_warnings);
					event.getPlayer().kickPlayer(plugin.getConfig().getString("config.strings.kick","You have been kicked for inappropriate language."));
					plugin.log.info(event.getPlayer().getName() + " was kicked for inappropriate language.");
					plugin.getServer().broadcast(event.getPlayer().getName() + " was kicked for inappropriate language.","bukkit.command.kick");
				}
				event.setCancelled(false);
				event.setMessage(thismsg);
			}
		}
	}
	
}
