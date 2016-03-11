package com.gmail.trentech.pja.utils;

import java.io.File;
import java.io.IOException;

import com.gmail.trentech.pja.Main;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {

	private File file;
	private CommentedConfigurationNode config;
	private ConfigurationLoader<CommentedConfigurationNode> loader;

	public ConfigManager(String configName) {
		String folder = "config" + File.separator + "projectautomation";
        if (!new File(folder).isDirectory()) {
        	new File(folder).mkdirs();
        }
		file = new File(folder + configName);
		
		create();
		load();
		init();
	}
	
	public ConfigManager() {
		String folder = "config" + File.separator + "projectautomation";
        if (!new File(folder).isDirectory()) {
        	new File(folder).mkdirs();
        }
		file = new File(folder, "config.conf");
		
		create();
		load();
		init();
	}
	
	public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
		return loader;
	}

	public CommentedConfigurationNode getConfig() {
		return config;
	}

	public void save(){
		try {
			loader.save(config);
		} catch (IOException e) {
			Main.getLog().error("Failed to save config");
			e.printStackTrace();
		}
	}
	
	private void init() {	
		if(config.getNode("Delay").getString() == null) {
			config.getNode("Delay").setValue("5");
		}
		if(config.getNode("Commands").getString() == null) {
			config.getNode("Commands", "cmd1", "Interval").setValue("1m");
			config.getNode("Commands", "cmd1", "Repeat").setValue(true);
			config.getNode("Commands", "cmd1", "Command").setValue("say This command will run automatically every 1 minute");
			config.getNode("Commands", "cmd2", "Time").setValue("12/31/2015-2:17:00");
			config.getNode("Commands", "cmd2", "Command").setValue("say This command will run at a specific time");
		}
		save();
	}

	private void create(){
		if(!file.exists()) {
			try {
				Main.getLog().info("Creating new " + file.getName() + " file...");
				file.createNewFile();		
			} catch (IOException e) {				
				Main.getLog().error("Failed to create new config file");
				e.printStackTrace();
			}
		}
	}
	
	private void load(){
		loader = HoconConfigurationLoader.builder().setFile(file).build();
		try {
			config = loader.load();
		} catch (IOException e) {
			Main.getLog().error("Failed to load config");
			e.printStackTrace();
		}
	}
}
