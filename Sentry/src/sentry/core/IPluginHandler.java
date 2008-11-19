package sentry.core;

import sentry.core.exceptions.NoSuchHookPointException;
import sentry.plugs.IPlugin;

public interface IPluginHandler {

	/**
	 * 
	 * @param hookpoint
	 *            The name of the <code>HookPoint</code> to attach to.
	 * @param plugin
	 *            The <code>IPlugin</code> to attach to the
	 *            <code>HookPoint</code>.
	 * @return boolean Whether the attaching succeeded.
	 * @throws NoSuchHookPointException
	 *             If the requested <code>HookPoint</code> doesn't exist.
	 * @see HookPoint
	 */
	public boolean attachPluginTo(String hookpoint, IPlugin plugin)
			throws NoSuchHookPointException;
}
