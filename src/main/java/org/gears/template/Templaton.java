package org.gears.template;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
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
//		showHierarchy(obj.getClass());
//		return getContext();

	}

	private static void showHierarchy(Class<?> c) {
		if (c.getSuperclass() == Gear.class
				|| c.getSuperclass() == Object.class) {
			System.out.println(c.getSimpleName());
			return;
		}
		showHierarchy(c.getSuperclass());
		System.out.println(c.getSimpleName());
	}

	private static Context getContext(Class<?> c, Gear obj, Context context) {
		// Return when at root hierarchy
		if (c.getSuperclass() == Object.class) return context;
		
		// Load context for the given gear
		context.put(c.getSimpleName(), obj);
		LOG.info(c.getSimpleName());
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

		Template template = Velocity.getTemplate(source);
		template.merge(context, writer);

		return writer;
	}

}
