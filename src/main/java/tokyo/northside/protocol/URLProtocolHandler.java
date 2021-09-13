package tokyo.northside.protocol;

/**
 * Data URL protocol handler installer.
 * <p>
 *     You can install data protocol hander by calling
 *     <pre>URLProtocolHandler.install();</pre>
 *
 * @author Hiroshi Miura
 */
public final class URLProtocolHandler {
    private static final String PKG =  "tokyo.northside.protocol";
    private static final String CONTENT_PATH_PROP = "java.protocol.handler.pkgs";

    private URLProtocolHandler() {
    }

    public static void install() {
        String handlerPkgs = System.getProperty(CONTENT_PATH_PROP, "");
        if (!handlerPkgs.contains(PKG)) {
            if (handlerPkgs.isEmpty()) {
                handlerPkgs = PKG;
            } else {
                handlerPkgs += "|" + PKG;
            }
            System.setProperty(CONTENT_PATH_PROP, handlerPkgs);
        }
    }
}
