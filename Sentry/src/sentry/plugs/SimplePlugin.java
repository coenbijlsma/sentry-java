package sentry.plugs;

import java.util.ArrayList;
import java.util.Properties;

/**
 * This class represents a simple plug-in for Sentry. Sentry knows two types of
 * plug-ins: regular and simple. A regular plug-in is a jar-file containing java
 * code that can be executed by Sentry, and can be way more complex than a
 * simple plug-in. For more info about regular plug-ins, see the
 * <code>Plugin</code> class. A simple plug-in however is defined in a xml-file
 * and can represent just one command.
 * 
 * @author Coen Bijlsma
 * @since 2008-11-19
 * @version 0.1
 * @see Plugin
 * 
 */
public class SimplePlugin implements IPlugin{

	/*
	 * The name of the plug-in
	 */
	private String _name;

	/*
	 * The place where this plug-in 'hooks' into Sentry
	 */
	private String _hookPoint;

	/*
	 * The protocol this plug-in is meant for, I.E. irc or rfc1459
	 */
	private String _protocol;

	/*
	 * The command (in protocol format) that must be executed whenever the
	 * plug-in is activated. example: MODE __CHANNEL__ +o __USER__
	 */
	private String _command;

	/*
	 * The plug-in names this plug-in depends on.
	 */
	private ArrayList<String> _dependencies;

	/**
	 * Constructor
	 * 
	 * @param properties
	 *            The properties file that contains the settings for this
	 *            plug-in.
	 */
	public SimplePlugin(Properties properties) {
		_dependencies = new ArrayList<String>();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getHookPoint() {
		return _hookPoint;
	}

	@Override
	public void execute() {
		System.out.println("Executing SimplePlugin " + _name);
	}
}
