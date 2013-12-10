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
		if(instance == null) instance = new Templaton();
		return instance;
	}
	
	public static VelocityContext getContext(){
		return new VelocityContext();
	}
	
	public static Context getContext(String key, Gear obj) {
		Context context = Templaton.getContext();
		context.put(key, obj);
		return context;
	}
	
	
	public StringWriter render(String source, Context context) {
		return render(source, context, new StringWriter());
	}
	
	public StringWriter render(String source, Context context, StringWriter writer) {
		
		if(writer == null) writer = new StringWriter();
		
		Template template = Velocity.getTemplate(source);
		template.merge(context, writer);
		
		return writer;
	}

	

}
