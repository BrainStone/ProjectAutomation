package com.gmail.trentech.pja.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.utils.ConfigManager;
import com.gmail.trentech.pja.utils.Utils;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDEdit implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!args.hasAny("name")) {
			src.sendMessage(Text.of(TextColors.YELLOW, "/auto edit <name> [time] [command] [repeat]"));
			return CommandResult.empty();
		}
		String name = args.<String> getOne("name").get().toLowerCase();

		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();

		if (config.getNode("Schedulers", name).getString() == null) {
			src.sendMessage(Text.of(TextColors.DARK_RED, name, " does not exist"));
			return CommandResult.empty();
		}
		ConfigurationNode node = config.getNode("Schedulers", name);

		if (!args.hasAny("arg0")) {
			src.sendMessage(Text.of(TextColors.YELLOW, "/auto edit <name> [time] [command] [repeat]"));
			return CommandResult.empty();
		}
		process(node, args.<String> getOne("arg0").get());

		if (args.hasAny("arg1")) {
			process(node, args.<String> getOne("arg1").get());
		}

		if (args.hasAny("arg2")) {
			process(node, args.<String> getOne("arg2").get());
		}

		configManager.save();

		src.sendMessage(Text.of(TextColors.DARK_GREEN, name, " edited successfully. Will not take effect until reload or restart"));
		return CommandResult.success();
	}

	private void process(ConfigurationNode node, String command) {
		if (Utils.isTimeValid(command)) {
			node.getNode("Time").setValue(null);
			node.getNode("Interval").setValue(command);
		} else if (isTimeValid(command)) {
			node.getNode("Interval").setValue(null);
			node.getNode("Repeat").setValue(null);
			node.getNode("Time").setValue(command);
		} else if (command.equalsIgnoreCase("true") || command.equalsIgnoreCase("false")) {
			node.getNode("Repeat").setValue(command);
		} else {
			node.getNode("Command").setValue(command);
		}
	}

	private boolean isTimeValid(String command) {
		try {
			new SimpleDateFormat("MM/dd/yyyy-h:mm:ss").parse(command);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
