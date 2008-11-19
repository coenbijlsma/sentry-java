package sentry.core;

import java.util.Collection;
import java.util.HashMap;

import sentry.plugs.IPlugin;

public class HookPoint {

	private String _name;
	private HashMap<String, IPlugin> _attachedPlugins;
	
	public HookPoint(String name){
		_name = name;
		_attachedPlugins = new HashMap<String, IPlugin>();
	}
	
	public String getName(){
		return _name;
	}
	
	public boolean attachPlugin(IPlugin plugin){
		if(_attachedPlugins.get(plugin) == null){
			_attachedPlugins.put(plugin.getName(), plugin);
			return true;
		}
		return false;
	}
	
	public Collection<IPlugin> getAttachedPlugins(){
		return _attachedPlugins.values();
	}
}
