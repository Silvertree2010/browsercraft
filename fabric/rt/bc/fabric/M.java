package bc.fabric;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.*;

public final class M implements ModContainer, ModMetadata {
    final String id, ver, name, desc;
    final List<String> authors;
    final Path root;
    Version v;

    public M(String id, String ver, String name, String desc, String[] authors) {
        this.id = id; this.ver = ver; this.name = name; this.desc = desc; this.authors = List.of(authors);
        root = Path.of("/mc/mods/" + id);
    }

    public ModMetadata getMetadata() { return this; }
    public List<Path> getRootPaths() { return List.of(root); }
    public Path getRootPath() { return root; }
    public Optional<Path> findPath(String f) { var p = root.resolve(f); return Files.exists(p) ? Optional.of(p) : Optional.empty(); }
    public Path getPath(String f) { return root.resolve(f); }
    public ModOrigin getOrigin() {
        return new ModOrigin() {
            public Kind getKind() { return Kind.PATH; }
            public List<Path> getPaths() { return List.of(root); }
            public String getParentModId() { throw new UnsupportedOperationException(); }
            public String getParentSubLocation() { throw new UnsupportedOperationException(); }
            public String toString() { return root.toString(); }
        };
    }
    public Optional<ModContainer> getContainingMod() { return Optional.empty(); }
    public Collection<ModContainer> getContainedMods() { return List.of(); }

    public String getType() { return "fabric"; }
    public String getId() { return id; }
    public Collection<String> getProvides() { return List.of(); }
    public Version getVersion() {
        if (v == null) {
            try { v = Version.parse(ver); } catch (Exception e) {
                v = new Version() {
                    public String getFriendlyString() { return ver; }
                    public int compareTo(Version o) { return ver.compareTo(o.getFriendlyString()); }
                };
            }
        }
        return v;
    }
    public ModEnvironment getEnvironment() { return ModEnvironment.UNIVERSAL; }
    public Collection<ModDependency> getDependencies() { return List.of(); }
    public String getName() { return name; }
    public String getDescription() { return desc; }
    public Collection<Person> getAuthors() {
        var r = new ArrayList<Person>();
        for (var a : authors) r.add(new Person() { public String getName() { return a; } public ContactInformation getContact() { return ContactInformation.EMPTY; } });
        return r;
    }
    public Collection<Person> getContributors() { return List.of(); }
    public ContactInformation getContact() { return ContactInformation.EMPTY; }
    public Collection<String> getLicense() { return List.of(); }
    public Optional<String> getIconPath(int s) { return Optional.empty(); }
    public boolean containsCustomValue(String k) { return false; }
    public CustomValue getCustomValue(String k) { return null; }
    public Map<String, CustomValue> getCustomValues() { return Map.of(); }
    public boolean containsCustomElement(String k) { return false; }
}
