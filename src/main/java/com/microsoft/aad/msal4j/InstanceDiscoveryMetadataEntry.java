package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

class InstanceDiscoveryMetadataEntry {
    @JsonProperty("preferred_network")
    String preferredNetwork;
    @JsonProperty("preferred_cache")
    String preferredCache;
    @JsonProperty("aliases")
    Set<String> aliases;

    public static InstanceDiscoveryMetadataEntry.InstanceDiscoveryMetadataEntryBuilder builder() {
        return new InstanceDiscoveryMetadataEntry.InstanceDiscoveryMetadataEntryBuilder();
    }

    String preferredNetwork() {
        return this.preferredNetwork;
    }

    String preferredCache() {
        return this.preferredCache;
    }

    Set<String> aliases() {
        return this.aliases;
    }

    public InstanceDiscoveryMetadataEntry() {
    }

    public InstanceDiscoveryMetadataEntry(String preferredNetwork, String preferredCache, Set<String> aliases) {
        this.preferredNetwork = preferredNetwork;
        this.preferredCache = preferredCache;
        this.aliases = aliases;
    }

    public static class InstanceDiscoveryMetadataEntryBuilder {
        private String preferredNetwork;
        private String preferredCache;
        private Set<String> aliases;

        InstanceDiscoveryMetadataEntryBuilder() {
        }

        @JsonProperty("preferred_network")
        public InstanceDiscoveryMetadataEntry.InstanceDiscoveryMetadataEntryBuilder preferredNetwork(String preferredNetwork) {
            this.preferredNetwork = preferredNetwork;
            return this;
        }

        @JsonProperty("preferred_cache")
        public InstanceDiscoveryMetadataEntry.InstanceDiscoveryMetadataEntryBuilder preferredCache(String preferredCache) {
            this.preferredCache = preferredCache;
            return this;
        }

        @JsonProperty("aliases")
        public InstanceDiscoveryMetadataEntry.InstanceDiscoveryMetadataEntryBuilder aliases(Set<String> aliases) {
            this.aliases = aliases;
            return this;
        }

        public InstanceDiscoveryMetadataEntry build() {
            return new InstanceDiscoveryMetadataEntry(this.preferredNetwork, this.preferredCache, this.aliases);
        }

        public String toString() {
            return "InstanceDiscoveryMetadataEntry.InstanceDiscoveryMetadataEntryBuilder(preferredNetwork=" + this.preferredNetwork + ", preferredCache=" + this.preferredCache + ", aliases=" + this.aliases + ")";
        }
    }
}
