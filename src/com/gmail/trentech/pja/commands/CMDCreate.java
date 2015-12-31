package com.gmail.trentech.pja.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.ConfigManager;
import com.gmail.trentech.pja.Utils;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDCreate implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("name")) {
			src.sendMessage(Texts.of(TextColors.YELLOW, "/auto create <name> <time> <command> [repeat]"));
			return CommandResult.empty();
		}
		String name = args.<String>getOne("name").get().toLowerCase();

		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();
		
		if(config.getNode("Commands", name).getString() != null){
			src.sendMessage(Texts.of(TextColors.DARK_RED, name, " already exists"));
			return CommandResult.empty();
		}
		ConfigurationNode node = config.getNode("Commands", name);
		
		if(!args.hasAny("time")) {
			src.sendMessage(Texts.of(TextColors.YELLOW, "/auto create <name> <time> <command> [repeat]"));
			return CommandResult.empty();
		}
		String time = args.<String>getOne("time").get();
		
		if(!args.hasAny("command")) {
			src.sendMessage(Texts.of(TextColors.YELLOW, "/auto create <name> <time> <command> [repeat]"));
			return CommandResult.empty();
		}
		String command = args.<String>getOne("command").get();
		
		if(Utils.isTimeValid(time)){
			node.getNode("Interval").setValue(time);
			if(args.hasAny("repeat")){
				node.getNode("Repeat").setValue(true);
			}
		}else{
			try {
				new SimpleDateFormat("MM/dd/yyyy-h:mm:ss").parse(time);
				node.getNode("Time").setValue(time);				
			} catch (ParseException e) {
				src.sendMessage(Texts.of(TextColors.YELLOW, "/auto create <name> <time> <command> [repeat]"));
				return CommandResult.empty();
			}
		}
		
		node.getNode("Command").setValue(command);
		
		configManager.save();

		src.sendMessage(Texts.of(TextColors.DARK_GREEN, name, " created successfully. Will not take effect until reload or restart"));
		return CommandResult.success();
	}

}
