package me.pink_____floyd.FKPlugin;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <b>Plugin Name:</b> FUTURE KINGDOMS PLUGIN
 * <p>
 * <b>File:</b> FKPlugin.java
 * <p>
 * @since: 13 August 2014
 * <p>
 * @version: 1.0
 * <p>
 * @author PINK_FLOYD - Future Kingdoms
 **/

//
// main class of FKPlugin
//

public class FKPlugin extends JavaPlugin implements Listener
{
	//---- Start Pligin Global Variables ------
	
	public final Logger logger = Logger.getLogger("Minecraft");
	public static FKPlugin Plugin;
	public Permission playerPermission = new Permission("playerAbillities.allowed");
	
	//----- End Plugin Global Variables ------
	


	//------ Start OnDisable -------------	
	@Override
	public void onDisable() 
	{
		
		//Displays Plugin Unloading Success on Minecraft Server Stop, Restart or Error
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " -Future Kingdoms Plugin Disabled");
		saveConfig();
		
	}
	//------ End OnDisable -------------

	
	
	//------ Start OnEnable ------------
	@Override
	public void onEnable() 
	{
		initialiseConfig();

		
		// set up player data
	

		// code to return UUID from Player Name
		//UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList("evilmidget38", "mbaxter"));
		
		//Displays Plugin Loading Success on Minecraft Server Start-up
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " - Future Kingdoms Plugin Enabled");

				
		//Main plugin command - shows version and plugin help
		this.getCommand("fkplugin").setExecutor(new FKPluginMainCommandExecutor(this));
		this.getCommand("fkhelp").setExecutor(new FKPluginHelpCommandExecutor(this));
		
		//adds player and block Event listeners
		//new PlayerListener(this);
		//new BlockListener(this);
		
		//adds player permissions to Plugin
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(playerPermission);
		
	
				
	}
	//----- End onEnable -----------

	

	private void initialiseConfig(){
	// adds default config to plugin
	FileConfiguration config = getConfig();
	config.addDefault("Iteration", 5);
	config.addDefault("MOTD", "Welcome to Pinks Server");
	//this.getConfig().addDefault("PlayerName","");
	//this.getConfig().addDefault("zombiekills", 0);
	this.getConfig().options().copyDefaults(true);
	saveDefaultConfig();
	

	
	// adds default config to plugin
	FileConfiguration data = getConfig();
	data.addDefault("Player.Int",0);
	this.getConfig().options().copyDefaults(true);
	saveDefaultConfig();
	}
	
	
	//*******Start Section Commands ******************
	//Sets the command for "/giveitems and /zombiekills"
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if (commandLabel.equalsIgnoreCase("giveitems") && sender instanceof Player)
		{
			Player player = (Player) sender;
		
				if(player.hasPermission("playerAbilities.allowed"))
				{
				player.setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
				}	
		return true;
		}

		if (commandLabel.equalsIgnoreCase("zombiekills") && sender instanceof Player)
		{
			Player player = (Player) sender;
			player.sendMessage(ChatColor.GOLD + " " + this.getConfig().getInt("zombiekills") + " Zombies have been slaughtered by " + this.getConfig().getString("PlayerName"));
		
			return true;
		}
		return false;	
		
	}
	//********End Section Commands*****************
	
	
	
} //final class bracket
