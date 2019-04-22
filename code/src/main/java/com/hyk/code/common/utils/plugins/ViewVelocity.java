package com.hyk.code.common.utils.plugins;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.InitializingBean;

import com.hyk.code.common.config.Global;


public class ViewVelocity implements InitializingBean {
	private static final String PAGE_ROOT = "webpage/";
	static final Logger logger;
	private String propertiesFile;
	private Properties prop;

	static {
		logger = Logger.getLogger((Class) ViewVelocity.class);
	}

	public void setPropertiesFile(final String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public void setProperties(final Properties prop) {
		this.prop = prop;
	}

	public void afterPropertiesSet() throws Exception {
		try {
			final Properties p = new Properties();
			if (this.propertiesFile != null) {
				p.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(this.propertiesFile));
			}
			if (this.prop != null) {
				p.putAll(this.prop);
			}
			Velocity.init(p);
			if (ViewVelocity.logger.isInfoEnabled()) {
				ViewVelocity.logger.info((Object) "Loading VelocityEngine....");
				final Enumeration<?> en = p.propertyNames();
				while (en.hasMoreElements()) {
					final String key = en.nextElement().toString();
					ViewVelocity.logger.info((Object) ("\t" + key + "=" + p
							.getProperty(key)));
				}
			}
		} catch (Exception e) {
			throw new Exception("\u542f\u52a8Velocity\u5931\u8d25", e);
		}
	}

	public static void view(final HttpServletResponse response,
			final String template) throws Exception {
		view(response, template, null);
	}

	@Deprecated
	public static void view(final HttpServletResponse response,
			final String template, VelocityContext velocityContext)
			throws Exception {
		if (ViewVelocity.logger.isDebugEnabled()) {
			ViewVelocity.logger
					.debug((Object) ("Velocity loading" + template));
		}
		final HttpServletRequest request = ContextHolderUtils.getRequest();
		if (velocityContext == null) {
			velocityContext = new VelocityContext();
		}
		velocityContext.put("Format", (Object) new SimpleFormat());
		velocityContext.put("dictTool", (Object) new DataDictTool());
		velocityContext.put("dateTool", (Object) new DateTool());
		final String basePath = request.getContextPath();
		ViewVelocity.logger
				.info((Object) ("---------------basePath--------------" + basePath));
		velocityContext.put("basePath", (Object) basePath);
		velocityContext.put("vcPath",(Object) (basePath+"/"+Global.getAdminPath()));
		final StringWriter writer = new StringWriter();
		Velocity.mergeTemplate(PAGE_ROOT + template, "UTF-8",
				(Context) velocityContext, (Writer) writer);
		outputToPage(request, response, writer.toString());
	}

	public static void view(final HttpServletRequest request,
			final HttpServletResponse response, final String template,
			VelocityContext velocityContext) throws Exception {
		if (ViewVelocity.logger.isDebugEnabled()) {
			ViewVelocity.logger.debug((Object) ("Velocity loading" + template));
		}
		if (velocityContext == null) {
			velocityContext = new VelocityContext();
		}
	
		velocityContext.put("Format", (Object) new SimpleFormat());
		velocityContext.put("dictTool", (Object) new DataDictTool());
		velocityContext.put("dateTool", (Object) new DateTool());
		velocityContext.put("session", request.getSession());
		final String basePath = request.getContextPath();
		velocityContext.put("vcPath",(Object) (basePath+"/"+Global.getAdminPath()));
		ViewVelocity.logger
				.info((Object) ("---------------basePath--------------" + basePath));
		velocityContext.put("basePath", (Object) basePath);
		final StringWriter writer = new StringWriter();
		Template template2 = RuntimeSingleton.getTemplate(
				PAGE_ROOT + template, "UTF-8");
		if (template2== null) {
			String msg = "Velocity.mergeTemplate() was unable to load template '"
					+ "webpage/" + template + "'";
			throw new ResourceNotFoundException(msg);
		} else {
			template2.merge((Context) velocityContext, writer);

		}
		// Velocity.mergeTemplate(, (, (Writer)writer);
		outputToPage(request, response, writer.toString());
	}

	private static void outputToPage(final HttpServletRequest request,
			final HttpServletResponse response, final String content)
			throws Exception {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
			}
		}
		try {
			if (writer != null) {
				writer.close();
			}
		} catch (Exception ex2) {
		}
	}
}
