package com.gmail.trentech.pja.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Texts;

public class CommandManager {

	private CommandSpec cmdCreate = CommandSpec.builder()
		    .description(Texts.of("Create new scheduled command"))
		    .permission("pja.cmd.auto.create")	    
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("name")))
		    		,GenericArguments.optional(GenericArguments.string(Texts.of("time")))
		    		,GenericArguments.optional(GenericArguments.string(Texts.of("command")))
		    		,GenericArguments.optional(GenericArguments.string(Texts.of("repeat"))))
		    .executor(new CMDCreate())
		    .build();
	
	private CommandSpec cmdDelete = CommandSpec.builder()
		    .description(Texts.of("delete scheduled command"))
		    .permission("pja.cmd.auto.delete")
		    .arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Texts.of("name"))))
		    .executor(new CMDDelete())
		    .build();

	private CommandSpec cmdEdit = CommandSpec.builder()
		    .description(Texts.of("Edit a scheduled command"))
		    .permission("pja.cmd.auto.edit")	    
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("name")))
		    		,GenericArguments.optional(GenericArguments.string(Texts.of("arg0")))
		    		,GenericArguments.optional(GenericArguments.string(Texts.of("arg1")))
		    		,GenericArguments.optional(GenericArguments.string(Texts.of("arg2"))))
		    .executor(new CMDEdit())
		    .build();
	
	private CommandSpec cmdReload = CommandSpec.builder()
		    .description(Texts.of("reload schedulers"))
		    .permission("pja.cmd.auto.reload")
		    .executor(new CMDReload())
		    .build();

	private CommandSpec cmdList = CommandSpec.builder()
		    .description(Texts.of("list all tasks"))
		    .permission("pja.cmd.auto.list")
		    .executor(new CMDList())
		    .build();
	
	private CommandSpec cmdHelp = CommandSpec.builder()
		    .description(Texts.of("i need help"))
		    .permission("pja.cmd.help")
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("command"))))
		    .executor(new CMDHelp())
		    .build();
	
	public CommandSpec cmdAuto = CommandSpec.builder()
			.description(Texts.of("Base command"))
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
