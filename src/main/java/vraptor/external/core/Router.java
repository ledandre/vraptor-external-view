package vraptor.external.core;

import java.io.File;

import javax.enterprise.context.RequestScoped;

import vraptor.external.configuration.Config;

/**
 * Builds the route to external view file.
 * @author Leandro Tomaz Andre
 *
 */
@RequestScoped
public class Router {
    private String defaultExternalTemplatesPath = Config.getDefaultViewPath();

    /**
     * @deprecated CDI eyes only
     */
    @Deprecated
    protected Router() {}

    public String getRouteFor(String resourceFolder, String fileName) {
        return new StringBuilder()
            .append(defaultExternalTemplatesPath)
            .append(File.separator)
            .append(resourceFolder)
            .append(File.separator)
            .append(fileName)
            .toString();
    }

    /**
     * Overrides the default external template's path.
     * @param path The path that must be used instead the default path.
     */
    protected void setTemplatesExternalPath(String path) {
        this.defaultExternalTemplatesPath = path;
    }
}