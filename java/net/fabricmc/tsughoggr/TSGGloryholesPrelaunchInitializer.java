package net.fabricmc.tsughoggr;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import btw.community.tsughoggr.gloryholes.TSGGloryhole;

public class TSGGloryholesPrelaunchInitializer implements PreLaunchEntrypoint {
    /**
     * Runs the PreLaunch entrypoint to register BTW-Addon.
     * Don't initialize anything else here, use
     * the method Initialize() in the Addon.
     */
    @Override
    public void onPreLaunch() {
 	       TSGGloryhole.getInstance();

    }
}
