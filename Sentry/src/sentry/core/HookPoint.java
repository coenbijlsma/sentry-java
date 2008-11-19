package sentry.core;

import java.util.Collection;
import java.util.HashMap;

import sentry.plugs.IPlugin;
import sentry.plugs.IPluginCommand;

public class HookPoint {

	private String _name;
	private HashMap<String, IPluginCommand> _attachedCommands;
	
	public HookPoint(String name){
		_name = name;
		_attachedCommands = new HashMap<String, IPluginCommand>();
	}
	
	public String getName(){
		return _name;
	}
	
	public boolean attachPluginCommand(IPluginCommand command){
		if(_attachedCommands.get(command) == null){
			_attachedCommands.put(command.getName(), command);
			return true;
		}
		return false;
	}
	
	public Collection<IPluginCommand> getAttachedCommands(){
		return _attachedCommands.values();
	}
}
