package online.nasgar.survivalcore.utils.command;

import online.nasgar.survivalcore.Core;

public abstract class BaseCommand {

	public BaseCommand() {
		Core.getInstance().getCommandFramework().registerCommands(this, null);
	}

	public abstract void onCommand(CommandArgs command);
}
