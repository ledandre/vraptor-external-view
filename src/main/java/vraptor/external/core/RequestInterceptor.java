package vraptor.external.core;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vraptor.external.core.annotations.ExternalView;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.view.Results;
/**
 * Intercepts execution of methods with {@link ExternalView} annotation and load the external template 
 * based on the name of the method.
 * 
 * @author Leandro Tomaz Andre
 *
 */
@Intercepts
@AcceptsWithAnnotations(ExternalView.class)
public class RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptor.class);

    @Inject private Result result;
    @Inject ExternalTemplateLoader externalViewLoader;

    @AroundCall
    @SuppressWarnings("unused")
    public void intercepts(SimpleInterceptorStack stack, ControllerMethod method) {
        LOGGER.info("Intercepting method {}", method.getMethod().getName());
        String externalViewContent;
        try {
            externalViewContent = externalViewLoader.load(method.getMethod().getName());
            result.use(Results.http()).body(externalViewContent);
        } catch (FileNotFoundException e) {
            LOGGER.error("Cannot find the required resource", e);
            result.use(Results.http()).sendError(404, "Cannot find the required resource");
        }
    }
}