package sentry.plugs;

import java.util.Collection;

public interface IPlugin {

	public String getName();
	
	public String[] getDependencies();
	
	public Collection<IPluginCommand> getCommands();
	
	public Collection<IPluginCommand> getCommandsByHookPoint(String hook);
}
