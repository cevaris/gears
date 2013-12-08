package gears.template;

import java.io.StringWriter;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

public class Templaton {
	
	private static final Logger LOG = Logger.getLogger(Templaton.class);
	
	
//	VelocityContext context = null;
//	StringWriter writer = null;
//	Template template = null;
	private static Templaton instance = null;
	
	
	public static Templaton getInstance() {
		if(instance == null) instance = new Templaton();
		return instance;
	}
	
	public static VelocityContext getContext(){
		return new VelocityContext();
	}
	
	public static Context getContext(Object obj) {
		Class<?> clazz = obj.getClass();
		Context context = new VelocityContext();
		
//		LOG.info(clazz.getName());
		
		for(Field field : clazz.getDeclaredFields()) {
			
			try {
				field.setAccessible(true);
//				LOG.info(field.getName()+" "+field.get(clazz));
				context.put(field.getName(), field.get(clazz).toString());
			} catch (IllegalArgumentException e) {
				// Skip
			} catch (IllegalAccessException e) {
				// Skip
			}
		}
		
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
