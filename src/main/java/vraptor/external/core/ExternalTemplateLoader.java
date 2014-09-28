package vraptor.external.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import vraptor.external.configuration.Config;
import br.com.caelum.vraptor.controller.ControllerMethod;
/**
 * Loads the view based on controller's method name.
 * @author Leandro Tomaz Andre
 */
@RequestScoped
public class ExternalTemplateLoader {
    private static final String CONTROLLER_SUFFIX = "Controller";

    @Inject private Router router;

    private String resourceExtension = Config.getDefaultViewExtension(); 

    /**
     * @deprecated CDI eyes only
     */
    @Deprecated
    protected ExternalTemplateLoader() {}

    public ExternalTemplateLoader setPath(String path) {
        this.router.setTemplatesExternalPath(path);

        return this;
    }

    public ExternalTemplateLoader usingExtension(String extension) {
        this.resourceExtension = extension;

        return this;
    }

    public String load(ControllerMethod method) throws FileNotFoundException {
        String resourceName = method.getMethod().getName();
        String resourceController = method.getController().getType().getSimpleName();

        return getTemplateContent(resourceController, resourceName);
    }

    private String getTemplateContent(String resourceController, String resourceName) throws FileNotFoundException {
        StringBuilder fileName = new StringBuilder()
        .append(resourceName)
        .append(resourceExtension);

        File resource = new File(router.getRouteFor(getResourceFolder(resourceController), fileName.toString()));
        String resourceContent = null;
        InputStream stream = new FileInputStream(resource);

        try {
            resourceContent = IOUtils.toString(stream, "UTF-8");
            stream.close();
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(stream);
        }

        return resourceContent;
    }

    private String getResourceFolder(String controllerName) {
        return controllerName.replaceAll(CONTROLLER_SUFFIX, "").toLowerCase();
    }
}