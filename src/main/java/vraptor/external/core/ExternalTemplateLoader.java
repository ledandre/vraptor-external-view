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
/**
 * Loads the view based on controller's method name.
 * @author Leandro Tomaz Andre
 *
 */
@RequestScoped
public class ExternalTemplateLoader {
    private String resourceExtension = Config.getDefaultViewExtension();
    @Inject private Router router;

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

    public String load(String callerMethod) throws FileNotFoundException {
        return getTemplateContent(callerMethod);
    }

    private String getTemplateContent(String fileName) throws FileNotFoundException {
        StringBuilder resourceName = new StringBuilder()
        .append(fileName)
        .append(resourceExtension);

        File resource = new File(router.getRouteFor(resourceName.toString()));
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
}