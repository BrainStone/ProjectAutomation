package com.gmail.trentech.pja.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.ConfigManager;
import com.gmail.trentech.pja.Main;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDList implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Texts.builder().color(TextColors.DARK_GREEN).append(Texts.of(TextColors.AQUA, "Tasks")).build());
		
		List<Text> list = new ArrayList<>();
		
		ConfigurationNode config = new ConfigManager().getConfig();
		
		for(Entry<Object, ? extends ConfigurationNode> node : config.getNode("Commands").getChildrenMap().entrySet()){
			String name = node.getKey().toString();
			String time = null;
			String repeat = "false";
			
			if(config.getNode("Commands", name, "Time").getString() != null){
				time = config.getNode("Commands", name, "Time").getString();
			}else if(config.getNode("Commands", name, "Interval").getString() != null){
				time = config.getNode("Commands", name, "Interval").getString();
				if(node.getValue().getNode("Commands", name, "Repeat").getString() != null){
					repeat = config.getNode("Commands", name, "Repeat").getString();
				}
			}

			String command = config.getNode("Commands", name, "Command").getString();

			list.add(getTask(name, time, repeat, command));
		}

		pages.contents(list);
		
		pages.sendTo(src);

		return CommandResult.success();
	}
	
	private Text getTask(String name, String time, String repeat, String command){
		return Texts.of(TextColors.DARK_GREEN, "Name: ", TextColors.GREEN, name, "\n",
				TextColors.AQUA, "  Time: ", TextColors.GREEN, time, "\n",
				TextColors.AQUA, "  Repeat: ", TextColors.GREEN, repeat, "\n",
				TextColors.AQUA, "  Command:\n",
				TextColors.GREEN,  "    ", command);
	}

}
