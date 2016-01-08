package com.gmail.trentech.pja.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.gmail.trentech.pja.Main;

import ninja.leaping.configurate.ConfigurationNode;

public class Tasks {

	public void start() {
		ConfigurationNode config = new ConfigManager().getConfig();
		Main.getGame().getScheduler().createTaskBuilder().async().delay(config.getNode("Delay").getLong(), TimeUnit.SECONDS).name(Resource.NAME + ":" + UUID.randomUUID().toString()).execute(new Runnable() {
			@Override
			public void run() {
				Main.getLog().info("Starting command scheduler");
				start(config);
			}
		}).submit(Main.getPlugin());
	}
    
	private void start(ConfigurationNode config) {
		for(Entry<Object, ? extends ConfigurationNode> entry : config.getNode("Schedulers").getChildrenMap().entrySet()){
			String uuid = entry.getKey().toString();
			ConfigurationNode node = config.getNode("Schedulers", uuid);
			if(node.getNode("Time").getString() != null){
				timeCommand(node);	
			}else if(node.getNode("Repeat").getBoolean()){
				repeatCommand(node);
			}else{
				runOnceCommand(node);
			}
		}
	}

	private void repeatCommand(ConfigurationNode node) {
		long interval = Utils.getTimeInMilliSeconds(node.getNode("Interval").getString());
		Main.getGame().getScheduler().createTaskBuilder().async().delay(interval, TimeUnit.MILLISECONDS).interval(interval, TimeUnit.MILLISECONDS).name(Resource.NAME + ":" + node.getKey().toString().toLowerCase()).execute(new Runnable() {
			@Override
            public void run() {			
				Main.getGame().getCommandManager().process(Main.getGame().getServer().getConsole(), node.getNode("Command").getString());
            }
        }).submit(Main.getPlugin());
	}
	
	private void runOnceCommand(ConfigurationNode node) {
		Main.getGame().getScheduler().createTaskBuilder().async().delay(Utils.getTimeInMilliSeconds(node.getNode("Interval").getString()), TimeUnit.MILLISECONDS).name(Resource.NAME + ":" + node.getKey().toString().toLowerCase()).execute(new Runnable() {
			@Override
            public void run() {			
				Main.getGame().getCommandManager().process(Main.getGame().getServer().getConsole(), node.getNode("Command").getString());
            }
        }).submit(Main.getPlugin());
	}
	
	private void timeCommand(ConfigurationNode node) {
		Date current = new Date();
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy-h:mm:ss").parse(node.getNode("Time").getString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(current.after(date)){
			return;
		}

		Main.getGame().getScheduler().createTaskBuilder().async().delay(date.getTime() - current.getTime(), TimeUnit.MILLISECONDS).name(Resource.NAME + ":" + node.getKey().toString().toLowerCase()).execute(new Runnable() {
			@Override
            public void run() {
				Main.getGame().getCommandManager().process(Main.getGame().getServer().getConsole(), node.getNode("Command").getString());
            }
        }).submit(Main.getPlugin());
	}
}
