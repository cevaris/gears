package template;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import sun.security.jca.GetInstance;

public class Templaton {
	
	
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
	
	
	public StringWriter render(String source, VelocityContext context) {
		return render(source, context, new StringWriter());
	}
	public StringWriter render(String source, VelocityContext context, StringWriter writer) {
		
		if(writer == null) writer = new StringWriter();
		
		Template template = Velocity.getTemplate(source);
		template.merge(context, writer);
		
		return writer;
	}

	
//	public void init(String templatePath, StringWriter writer){
//		this.context = new VelocityContext();
//		this.template = Velocity.getTemplate(templatePath);
//		this.writer = writer;
//		
//	}
	
//	public Context put(String key, Object value) {
//		this.context.put(key,value);
//		return this.context;
//	}
	
//	public StringWriter render() {
//		
//		if(this.writer == null) this.writer = new StringWriter();
//		
//		this.template.merge(context, writer);
//		this.hasRendered = true;
//		
//		return this.writer;
//	}
	
	

}
