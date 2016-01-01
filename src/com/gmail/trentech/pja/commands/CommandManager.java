package com.gmail.trentech.pja.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {

	private CommandSpec cmdCreate = CommandSpec.builder()
		    .description(Text.of("Create new scheduled command"))
		    .permission("pja.cmd.auto.create")	    
		    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("name")))
		    		,GenericArguments.optional(GenericArguments.string(Text.of("time")))
		    		,GenericArguments.optional(GenericArguments.string(Text.of("command")))
		    		,GenericArguments.optional(GenericArguments.string(Text.of("repeat"))))
		    .executor(new CMDCreate())
		    .build();
	
	private CommandSpec cmdDelete = CommandSpec.builder()
		    .description(Text.of("delete scheduled command"))
		    .permission("pja.cmd.auto.delete")
		    .arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("name"))))
		    .executor(new CMDDelete())
		    .build();

	private CommandSpec cmdEdit = CommandSpec.builder()
		    .description(Text.of("Edit a scheduled command"))
		    .permission("pja.cmd.auto.edit")	    
		    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("name")))
		    		,GenericArguments.optional(GenericArguments.string(Text.of("arg0")))
		    		,GenericArguments.optional(GenericArguments.string(Text.of("arg1")))
		    		,GenericArguments.optional(GenericArguments.string(Text.of("arg2"))))
		    .executor(new CMDEdit())
		    .build();
	
	private CommandSpec cmdReload = CommandSpec.builder()
		    .description(Text.of("reload schedulers"))
		    .permission("pja.cmd.auto.reload")
		    .executor(new CMDReload())
		    .build();

	private CommandSpec cmdList = CommandSpec.builder()
		    .description(Text.of("list all tasks"))
		    .permission("pja.cmd.auto.list")
		    .executor(new CMDList())
		    .build();
	
	private CommandSpec cmdHelp = CommandSpec.builder()
		    .description(Text.of("i need help"))
		    .permission("pja.cmd.help")
		    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("command"))))
		    .executor(new CMDHelp())
		    .build();
	
	public CommandSpec cmdAuto = CommandSpec.builder()
			.description(Text.of("Base command"))
			.permission("pja.cmd.auto")
			.child(cmdCreate, "create", "c")
			.child(cmdDelete, "delete", "d")
			.child(cmdEdit, "edit", "e")
			.child(cmdReload, "reload", "r")
			.child(cmdList, "list", "l")
			.child(cmdHelp, "help")
			.executor(new CMDAuto())
			.build();
}
