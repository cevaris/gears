package gears.template;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

public class Templaton {
	
	private static final Logger LOG = Logger.getLogger(Templaton.class.getClass());
	
	
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
	
	public static VelocityContext getContext(Object obj) {
		Class<?> clazz = obj.getClass();
		VelocityContext context = new VelocityContext();
		
		try {
			for(Field field : clazz.getDeclaredFields()) {
				LOG.info(field.getName()+" "+field.get(clazz));
				context.put(field.getName(), field.get(clazz).toString());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
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
