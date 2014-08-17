package me.pink_____floyd.FKPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

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

public class FKPlugin extends JavaPlugin 
{
	//---- Start Pligin Global Variables ------
	public final Logger logger = Logger.getLogger("Minecraft");
	public static FKPlugin Plugin;
	
	

	
    private static Plugin plugin;
	   
	
	//Instanciate Plugin Permissions
	public Permission playerPermission = new Permission("FKPlugin.Commands.Stats");

	
	//----- End Plugin Global Variables ------
	
	//------ Start OnEnable ------------

	@Override
	public void onEnable() 
	{
		File newConfig = new File(getDataFolder(), "newconfig.yml");
		FileConfiguration newConfigz = YamlConfiguration.loadConfiguration(newConfig);
		
		savenewConfig(newConfigz, newConfig);
		
		plugin = this;
		
		//ConfigManager.setup(plugin);

	    

		//Displays Plugin Loading Success on Minecraft Server Start-up
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " - Future Kingdoms Plugin Enabled");

				
		//Main plugin command registrations
		//registerEvents(this, new Both());
		//registerEvents(this, new JoinListener());
		getCommand("stats").setExecutor(new CommandManager());
		//getCommand("hello").setExecutor(new Both());
		
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new JoinListener(this), this);
	}
	//----- End onEnable -----------

	

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

	//Routine to Save NewConfig.yml file

    public void savenewConfig(FileConfiguration ymlConfig, File ymlFile) {
	    try {
	    ymlConfig.save(ymlFile);
	    } catch (IOException e) {
	    e.printStackTrace();
	    }
	    }
    
    
    
    //Much eaisier then registering events in 10 diffirent methods
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    //To access the plugin variable from other classes
    public static Plugin getPlugin() {
        return plugin;
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
