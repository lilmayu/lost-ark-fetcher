package dev.mayuna.lostarkfetcher.objects.api;

import dev.mayuna.lostarkfetcher.objects.api.other.LostArkRegion;
import dev.mayuna.lostarkfetcher.objects.api.other.LostArkServerStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LostArkServers {

    private List<LostArkServer> servers;

    public LostArkServers() {
    }

    public LostArkServers(List<LostArkServer> servers) {
        this.servers = servers;
    }

    public List<LostArkServer> get() {
        return servers;
    }

    public Optional<LostArkServer> getServerByName(String name) {
        return servers.stream()
                .filter(lostArkServer -> lostArkServer.is(name))
                .findFirst();
    }

    public List<LostArkServer> getServersByRegion(LostArkRegion region) {
        return servers.stream()
                .filter(lostArkServer -> lostArkServer.isFromRegion(region))
                .collect(Collectors.toList());
    }

    public List<LostArkServer> getServeryByStatus(LostArkServerStatus status) {
        return servers.stream()
                .filter(lostArkServer -> lostArkServer.hasStatus(status))
                .collect(Collectors.toList());
    }
}
