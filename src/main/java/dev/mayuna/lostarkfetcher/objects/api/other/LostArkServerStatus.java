package dev.mayuna.lostarkfetcher.objects.api.other;

import lombok.Getter;

public enum LostArkServerStatus {
    ONLINE("ags-ServerStatus-content-responses-response-server-status--good"),
    BUSY("ags-ServerStatus-content-responses-response-server-status--busy"),
    FULL("ags-ServerStatus-content-responses-response-server-status--full"),
    MAINTENANCE("ags-ServerStatus-content-responses-response-server-status--maintenance"),
    OFFLINE("ags-ServerStatus-content-responses-response-server-status--offline"); // Does not exist but may be existing in future

    private @Getter String CSS;

    LostArkServerStatus(String CSS) {
        this.CSS = CSS;
    }
}
