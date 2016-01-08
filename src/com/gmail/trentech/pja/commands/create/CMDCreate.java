package com.gmail.trentech.pja.commands.create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.Main;
import com.gmail.trentech.pja.utils.ConfigManager;
import com.gmail.trentech.pja.utils.Utils;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDCreate implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("name")) {
			invalidArgs(src);
			return CommandResult.empty();
		}
		String name = args.<String>getOne("name").get().toLowerCase();

		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();
		
		if(config.getNode("Schedulers", name).getString() != null){
			src.sendMessage(Text.of(TextColors.DARK_RED, name, " already exists"));
			return CommandResult.empty();
		}
		ConfigurationNode node = config.getNode("Schedulers", name);
		
		if(!args.hasAny("command")) {
			src.sendMessage(Text.of(TextColors.YELLOW, "/auto create <name> <command> <time> [repeat]"));
			return CommandResult.empty();
		}
		String command = args.<String>getOne("command").get();
		
		if(!args.hasAny("time")) {
			src.sendMessage(Text.of(TextColors.YELLOW, "/auto create <name> <command> <time> [repeat]"));
			return CommandResult.empty();
		}
		String time = args.<String>getOne("time").get();
		
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
				src.sendMessage(Text.of(TextColors.YELLOW, "/auto create <name> <command> <time> [repeat]"));
				return CommandResult.empty();
			}
		}
		
		node.getNode("Command").setValue(command);
		
		configManager.save();

		src.sendMessage(Text.of(TextColors.DARK_GREEN, name, " created successfully. Will not take effect until reload or restart"));
		return CommandResult.success();
	}
	
	private void invalidArgs(CommandSource src){
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Text.builder().color(TextColors.DARK_GREEN).append(Text.of(TextColors.AQUA, "Command List")).build());
		
		List<Text> list = new ArrayList<>();
		
		list.add(Text.builder().color(TextColors.YELLOW).onHover(TextActions.showText(Text.of("Click command for more information ")))
				.append(Text.of(" /auto create <name> <command> <time> [repeat]\n")).build());
		
		if(src.hasPermission("pja.cmd.auto.create.button")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help button")).append(Text.of(" /auto create button")).build());
		}
		if(src.hasPermission("pja.cmd.auto.create.door")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help door")).append(Text.of(" /auto create door")).build());
		}
		if(src.hasPermission("pja.cmd.auto.create.lever")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help lever")).append(Text.of(" /auto create lever")).build());
		}
		if(src.hasPermission("pja.cmd.auto.create.plate")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help plate")).append(Text.of(" /auto create plate")).build());
		}
		if(src.hasPermission("pja.cmd.auto.create.sign")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help sign")).append(Text.of(" /auto create sign")).build());
		}

		pages.contents(list);
		
		pages.sendTo(src);
	}

}
