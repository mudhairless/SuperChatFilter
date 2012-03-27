package net.owlbox.sirmud.SuperChatFilter;

import java.util.HashMap;
import java.util.logging.Logger;

import net.owlbox.sirmud.SuperChatFilter.ChatFilterListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperChatFilter extends JavaPlugin {

	public HashMap<String,Integer> player_warnings;
	public HashMap<String,String> filter;
	public Logger log;
	
	@Override
	public void onEnable() {
		
		log = getLogger();
		ChatFilterListener ChatListen = new ChatFilterListener(this);
		getServer().getPluginManager().registerEvents(ChatListen,this);
		
		filter = (HashMap<String, String>) getConfig().get("config.filters");
		player_warnings = (HashMap<String, Integer>) getConfig().get("warnings");
		
		if (!getConfig().contains("config")) {
			getConfig().options().copyDefaults(true);
			this.saveDefaultConfig();
			log.info("Saving default configuration.");
		}
		
		log.info("Filtering " + Integer.toString(filter.size()) + " patterns.");
	}
	
	@Override
	public void onDisable() {
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args ) {
		if(cmd.getName().equalsIgnoreCase("addfilter")) {
			String filtered = "";
			if(filter.containsKey(args[0])) {
				//replace
				if(args.length == 1)
					filtered = getConfig().getString("config.strings.default_filtered","!@$&%");
				else
					filtered = args[1];
				filter.put(args[0], filtered);
				((Player)sender).sendMessage("Replaced filter for: " + args[0]);
				log.info("Replaced filter for: " + args[0]);
			} else {
				//new
				if(args.length == 1)
					filtered = getConfig().getString("config.strings.default_filtered","#!@$&");
				else
					filtered = args[1];
				filter.put(args[0], filtered);
				((Player)sender).sendMessage("Created new filter for: " + args[0]);
				log.info("Created new filter for: " + args[0]);
			}
			
			getConfig().set("config.filters", filter);
			getConfig().set("warnings", player_warnings);
			saveConfig();
			return true;
			
		}
		
		return false;
	}
	
}
