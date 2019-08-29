package es.upm.miw.apaw_ep_themes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemResource.SYSTEM)
public class SystemResource {

    public static final String SYSTEM = "/system";
    public static final String VERSION = "/version";
    public static final String BADGE = "/badge";


    private static final String BADGE_IMAGE =
            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"122\" height=\"20\">"
                    + "<linearGradient id=\"b\" x2=\"0\" y2=\"100%%\">"
                    + "<stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\"/>"
                    + "<stop offset=\"1\" stop-opacity=\".1\"/></linearGradient>"
                    + "<clipPath id=\"a\"><rect width=\"122\" height=\"20\" rx=\"3\" fill=\"#fff\"/></clipPath>"
                    + "<g clip-path=\"url(#a)\"><path fill=\"#555\" d=\"M0 0h49v20H0z\"/>"
                    + "<path fill=\"#4c1\" d=\"M49 0h73v20H49z\"/><path fill=\"url(#b)\" d=\"M0 0h122v20H0z\"/></g>"
                    + "<g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\">"
                    + "<text x=\"255\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"390\">"
                    + "heroku</text>"
                    + "<text x=\"255\" y=\"140\" transform=\"scale(.1)\" textLength=\"390\">heroku</text>"
                    + "<text x=\"845\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"630\">"
                    + "%s</text>"
                    + "<text x=\"845\" y=\"140\" transform=\"scale(.1)\" textLength=\"630\">%s</text></g> </svg>";


    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.timestamp}")
    private String buildTimestamp;

    @GetMapping(value = BADGE, produces = {"image/svg+xml"})
    public byte[] generateBadge() { // http://localhost:8080/system/badge
        String version = ("v" + buildVersion + "-R           ").substring(0, 11);
        return String.format(BADGE_IMAGE, version, version).getBytes();
    }

    @GetMapping(SystemResource.VERSION)
    public VersionDto readVersion() { // http://localhost:8080/system/version
        return new VersionDto(this.applicationName + "::" + this.buildVersion + "::" + this.buildTimestamp);
    }
}