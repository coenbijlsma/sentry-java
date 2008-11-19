package sentry.plugs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;

public class JarClassLoader extends URLClassLoader  {
	private URL _url;
	
	/**
	 * 
	 * @param url The url of the jar file
	 */
	public JarClassLoader(URL url){
		super(new URL[] { url });
		this._url = url;
	}
	
	public String getMainClassName() throws IOException {
		URL u = new URL("jar", "", _url + "!/");
		JarURLConnection uc = (JarURLConnection)u.openConnection();
		Attributes attr = uc.getMainAttributes();
		return attr != null ? attr.getValue(Attributes.Name.MAIN_CLASS) : null;
	}
	
	public IPlugin[] getPlugins(){
		try{
			String mainClassName = getMainClassName();
			Class c = loadClass(mainClassName);
			Method m = c.getMethod("getPlugins");
			IPlugin[] plugins = (IPlugin[])m.invoke(null, null);
			return plugins;
		}catch(IOException ex){
			ex.printStackTrace(System.err);
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace(System.err);
		}catch(NoSuchMethodException nsme){
			nsme.printStackTrace(System.err);
		}catch(InvocationTargetException ite){
			ite.printStackTrace(System.err);
		}catch(IllegalAccessException iae){
			iae.printStackTrace(System.err);
		}
		
		return null;
	}
}
