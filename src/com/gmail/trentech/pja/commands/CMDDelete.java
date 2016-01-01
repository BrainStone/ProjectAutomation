package com.gmail.trentech.pja.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.ConfigManager;
import com.gmail.trentech.pja.Main;
import com.gmail.trentech.pja.Resource;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDDelete implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("name")) {
			src.sendMessage(Text.of(TextColors.YELLOW, "/auto delete <name>"));
			return CommandResult.empty();
		}
		String name = args.<String>getOne("name").get().toLowerCase();
		
		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();

		if(config.getNode("Commands", name).getString() == null){
			src.sendMessage(Text.of(TextColors.DARK_RED, name, " does not exist"));
			return CommandResult.empty();
		}

		if(!config.getNode("Commands").removeChild(name)){
			src.sendMessage(Text.of(TextColors.DARK_RED, "Something went wrong"));
			return CommandResult.empty();
		}
		
		configManager.save();
		
		for(Task t : Main.getGame().getScheduler().getScheduledTasks()){
			System.out.println(t.getName());
			if(t.getName().equals(Resource.NAME + ":" + name)){
				t.cancel();
			}
		}
		
		src.sendMessage(Text.of(TextColors.DARK_GREEN, name, " removed"));
		
		return CommandResult.empty();
	}

}
