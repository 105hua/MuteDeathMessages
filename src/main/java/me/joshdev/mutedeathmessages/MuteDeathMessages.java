package me.joshdev.mutedeathmessages;

import me.joshdev.mutedeathmessages.commands.ToggleDeathMessagesCommand;
import me.joshdev.mutedeathmessages.events.OnPlayerDeath;
import me.joshdev.mutedeathmessages.libs.SerializationUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class MuteDeathMessages extends JavaPlugin {
    public static MuteDeathMessages pluginInstance;
    private final Logger logger = getLogger();
    private final boolean toggleFileIsValid = false;
    public static HashMap<String, Boolean> deathTogglesTable = new HashMap<>();
    public File dataFolder = getDataFolder();

    public static boolean getToggleFromPlayer(Player player){
        String playerID = player.getUniqueId().toString();
        return deathTogglesTable.getOrDefault(playerID, false);
    }

    public static void setToggleForPlayer(Player player, Boolean newValue){
        deathTogglesTable.put(player.getUniqueId().toString(), newValue);
    }

    public static MuteDeathMessages getInstance() {
        return pluginInstance;
    }

    @Override
    public void onEnable() {
        logger.info("Setting up the plugin...");
        // Set the plugin instance to this class.
        pluginInstance = this;
        // Check if data folder exists and if not, create it.
        if(!dataFolder.exists()){
            logger.info("Data Folder doesn't exist. The plugin will create it.");
            boolean hasCreated = dataFolder.mkdirs();
            if(hasCreated){
                logger.info("Data Folder successfully created.");
            }else{
                throw new IllegalStateException("The plugin does not have the correct data. (Data Folder couldn't be created).");
            }
        }
        if(!dataFolder.isDirectory()){
            logger.info("Data Folder is not a directory. The plugin is going to delete whatever it is and create the directory.");
            boolean hasDeleted = dataFolder.delete();
            if(hasDeleted){
                logger.info("Deleted whatever the data folder was.");
            }else{
                throw new IllegalStateException("The plugin doesn't have the correct data. (Couldn't delete invalid Data Folder).");
            }
        }
        // Try to find an existing data file.
        String togglesFilePath = dataFolder.getAbsolutePath() + "/toggles.dat";
        File togglesDataFile = new File(togglesFilePath);
        if(!togglesDataFile.exists()){
            logger.info("The toggles data file does not exist. The plugin will try to create it.");
            try{
                boolean hasCreatedTogglesFile = togglesDataFile.createNewFile();
                if(hasCreatedTogglesFile){
                    logger.info("Successfully created toggles data file.");
                }else{
                    throw new IllegalStateException("The plugin does not have the correct data. (Couldn't create the Toggles File)");
                }
                logger.info("Created the toggles file.");
            }catch (Exception e){
                throw new IllegalStateException("The plugin doesn't have the correct data. (Couldn't create the Toggles File)");
            }
        }else{
            logger.info("Found a Toggles file, the plugin will attempt to load the data from it.");
            try{
                deathTogglesTable = SerializationUtils.DeserializeHashMap(togglesFilePath);
                logger.info("Successfully loaded toggles file.");
            }catch (Exception e){
                throw new IllegalStateException("The plugin doesn't have the correct data. (Couldn't deserialize hashmap.");
            }
        }
        // Register the death event and the toggle command.
        logger.info("Registering player death event and toggle command.");
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
        logger.info("Event registered.");
        try{
            this.getCommand("toggledeathmsgs").setExecutor(new ToggleDeathMessagesCommand()); // Ignore method invocation as it's in a try which catches the exception.
            logger.info("Command registered.");
        }catch(NullPointerException exception){
            logger.info("Command registration threw an error, the toggle command will not be available in this session.");
        }
        // Write to console that plugin is ready.
        logger.info("Plugin is now ready.");
    }

    @Override
    public void onDisable() {
        logger.info("Plugin is now disabling, attempting to save toggles map.");
        boolean hasSerialized = SerializationUtils.SerializeHashMap(deathTogglesTable, dataFolder.getAbsolutePath() + "/toggles.dat");
        if(hasSerialized){
            logger.info("Successfully saved toggles map.");
        }else{
            logger.info("Couldn't save toggles map, data might be reset when server is next started.");
        }
    }
}
