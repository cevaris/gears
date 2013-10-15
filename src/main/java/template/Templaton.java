package template;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

public class Templaton {
	
	
	VelocityContext context = null;
	StringWriter writer = null;
	Template template = null;
	boolean hasRendered = false;
	
	public Templaton(String templatePath) {
		init(templatePath, null);
	}
	
	public Templaton(String templateName, StringWriter writer) {
		init(templateName, writer);
	}
	
	public void init(String templatePath, StringWriter writer){
		this.context = new VelocityContext();
		this.template = Velocity.getTemplate(templatePath);
		this.writer = writer;
		
		this.hasRendered = false;
	}
	
	public Context put(String key, Object value) {
		this.context.put(key,value);
		return this.context;
	}
	
	public StringWriter render() {
		if(this.hasRendered) return this.writer;
		if(this.writer == null) this.writer = new StringWriter();
		
		this.template.merge(context, writer);
		this.hasRendered = true;
		
		return this.writer;
	}

}
