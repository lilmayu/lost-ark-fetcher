package dev.mayuna.lostarkfetcher.objects.api;

import dev.mayuna.lostarkfetcher.objects.api.other.LostArkRegion;
import dev.mayuna.lostarkfetcher.objects.api.other.LostArkServerStatus;
import lombok.Getter;

public class LostArkServer {

    private final @Getter String name;
    private final @Getter LostArkRegion region;
    private final @Getter LostArkServerStatus status;

    public LostArkServer(String name, LostArkRegion region, LostArkServerStatus status) {
        this.name = name;
        this.region = region;
        this.status = status;
    }

    public boolean is(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public boolean isFromRegion(LostArkRegion region) {
        return this.region == region;
    }

    public boolean hasStatus(LostArkServerStatus status) {
        return this.status == status;
    }
}
