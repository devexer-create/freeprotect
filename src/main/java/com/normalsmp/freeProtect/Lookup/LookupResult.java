package com.normalsmp.freeProtect.Lookup;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.List;

public class LookupResult {
    public final long timestamp;
    public final List<BaseComponent[]> lines;

    public LookupResult(long timestamp, List<BaseComponent[]> lines) {
        this.timestamp = timestamp;
        this.lines = lines;
    }
}
