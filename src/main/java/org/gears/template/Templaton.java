package org.gears.template;

import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.gears.Gear;

public class Templaton {

	private static final Logger LOG = Logger.getLogger(Templaton.class);

	private static Templaton instance = null;

	public static Templaton getInstance() {
		if (instance == null)
			instance = new Templaton();
		return instance;
	}

	public static VelocityContext getContext() {
		return new VelocityContext();
	}

	public static Context getContext(Gear obj) {
		return getContext(obj.getClass(), obj, Templaton.getContext());
	}

	private static Context getContext(Class<?> c, Gear obj, Context context) {
		// Return when at root hierarchy
		if (c.getSuperclass() == Object.class)
			return context;

		// Load context for the given gear
		context.put(c.getSimpleName(), obj);
//		LOG.info(c.getSimpleName());
		// Keep walking up the hierarchy
		return getContext(c.getSuperclass(), obj, context);

	}

	public StringWriter render(String source, Context context) {
		return render(source, context, new StringWriter());
	}

	public StringWriter render(String source, Context context,
			StringWriter writer) {

		if (writer == null)
			writer = new StringWriter();
		
		VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template template = ve.getTemplate(source);
//		Template template = Velocity.getTemplate(source);
		template.merge(context, writer);

		return writer;
	}

}
