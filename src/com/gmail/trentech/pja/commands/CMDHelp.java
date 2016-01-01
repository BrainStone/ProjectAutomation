package com.gmail.trentech.pja.commands;

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

public class CMDHelp implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("command")) {
			Text t1 = Text.of(TextColors.YELLOW, "/auto help ");
			Text t2 = Text.builder().color(TextColors.YELLOW).onHover(TextActions.showText(Text.of("Enter the command you need help with"))).append(Text.of("<command> ")).build();
			src.sendMessage(Text.of(t1,t2));
			return CommandResult.empty();
		}
		String command = args.<String>getOne("command").get().toUpperCase();
		String description = null;
		String syntax = null;
		String example = null;

		switch(command.toLowerCase()) {
			case "create":
				description = " Create new scheduled command.";
				syntax = " /auto create <name> <time> <command> [repeat]\n"
						+ " /a c <name> <time> <command> [repeat]";
				example = " /auto create mycommand 5m \"say recurring message\" true\n"
						+ " /auto create mycommand 1h,30m \"say this command will run one\"\n"
						+ " /auto create mycommand 12/31/2015-1:00:00 \"say this command will only run once at a specific time\"";
				break;
			case "delete":
				description = " Delete an existing scheduled command.";
				syntax = " /auto delete <name>\n"
						+ " /a d <name>";
				example = " /auto delete mycommand";
				break;
			case "edit":
				description = " Edit a scheduled command. Must contain at least one argument";
				syntax = " /auto edit <name> [time] [command] [repeat]\n"
						+ " /a e <name> [time] [command] [repeat]";
				example = " /auto edit mycommand 5m\n"
						+ " /auto edit mycommand 1h,30m \"say this command will run one\"\n"
						+ " /auto edit mycommand 12/31/2015-1:00:00\n"
						+ " /auto edit mycommand \"say this command edited\"";
				break;
			case "reload":
				description = " Reloads plugin.";
				syntax = " /auto reload\n"
						+ " /a r";
				example = " /auto reload";
				break;
			case "list":
				description = " List all tasks.";
				syntax = " /auto list\n"
						+ " /a l";
				example = " /auto list";
				break;
			default:
				src.sendMessage(Text.of(TextColors.DARK_RED, "Not a valid command"));
				return CommandResult.empty();
		}

		help(command, description, syntax, example).sendTo(src);
		return CommandResult.success();
	}
	
	private PaginationBuilder help(String command, String description, String syntax, String example){
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		pages.title(Text.builder().color(TextColors.DARK_GREEN).append(Text.of(TextColors.AQUA, command)).build());
		
		List<Text> list = new ArrayList<>();

		list.add(Text.of(TextColors.AQUA, "Description:"));
		list.add(Text.of(TextColors.GREEN, description));
		list.add(Text.of(TextColors.AQUA, "Syntax:"));
		list.add(Text.of(TextColors.GREEN, syntax));
		list.add(Text.of(TextColors.AQUA, "Example:"));
		list.add(Text.of(TextColors.GREEN,  example, TextColors.DARK_GREEN));
		
		pages.contents(list);
		
		return pages;
	}

}
