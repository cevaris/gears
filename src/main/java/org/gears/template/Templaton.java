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
	
	
	public static Context getContext(Gear obj) {
		
//		return getContext(obj, Templaton.getContext());
		showHierarchy(obj.getClass());
		return getContext();
		
	}
	
	private static void showHierarchy(Class<?> c) {
        if (c.getSuperclass() == Gear.class || 
        		c.getSuperclass() == Object.class) {
            System.out.println(c.getSimpleName());
            return;
        }
        showHierarchy(c.getSuperclass());
        System.out.println(c.getSimpleName());
    }
	
	private static Context getContext(Object obj, Context context) {
		LOG.info("Adding class: " +obj.getClass());
		context.put(obj.getClass().getSimpleName(), obj);
		
		if(obj.getClass().getSuperclass()  == Object.class){
			return context;
		}
		
		LOG.info("Sending: "+obj.getClass().getSuperclass());
		
		//Work up the hierarchy
		return getContext(obj.getClass().getSuperclass().cast(Gear.class), context);
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
