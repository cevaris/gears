package org.gears.template;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
		
		LOG.info(clazz.getName());
		LOG.info(clazz.getSuperclass().getCanonicalName());
		LOG.info(String.format("-%s-",clazz.getSuperclass().getSuperclass().getCanonicalName()));
		
		while(!clazz.getCanonicalName().equalsIgnoreCase("java.lang.Object")){
			
			for(Field field : clazz.getDeclaredFields()) {
				
				LOG.info(clazz.getCanonicalName() + " - " +field.getName() + " - " + field.getType() + " - " + field.getGenericType());
				
				try {
					
					Type type = field.getGenericType();
					field.setAccessible(true);
					
					if (type instanceof ParameterizedType) {  
		                ParameterizedType pt = (ParameterizedType) type;  
		                LOG.info("Return type is " + pt.getRawType() + " with the following type arguments: ");
		                for (Type t : pt.getActualTypeArguments()) {  
		                	Class genericParameter0OfThisClass = 
		                		    (Class)
		                		        ((ParameterizedType)
		                		            clazz
		                		                .getGenericSuperclass())
		                		                    .getActualTypeArguments()[0];
		                	LOG.info(genericParameter0OfThisClass);
		                	context.put(field.getName(), field.get(genericParameter0OfThisClass));
		                }  
		                
		            } else {
		            	LOG.info(field.getName()+" "+field.get(clazz));
						context.put(field.getName(), field.get(clazz).toString());
		            }
					
				} catch (IllegalArgumentException e) {
					LOG.error(String.format("Could not reflect field %s.%s ", clazz.getCanonicalName(), field.getName()));
				} catch (IllegalAccessException e) {
					LOG.error(String.format("Could not reflect field %s.%s ", clazz.getCanonicalName(), field.getName()));
				}
			}
			clazz = clazz.getSuperclass();
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
