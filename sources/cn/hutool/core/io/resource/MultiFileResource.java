package cn.hutool.core.io.resource;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MultiFileResource extends MultiResource {
    private static final long serialVersionUID = 1;

    public MultiFileResource(Collection<File> collection) {
        super(new Resource[0]);
        add(collection);
    }

    public MultiFileResource add(File... fileArr) {
        for (File file : fileArr) {
            add((Resource) new FileResource(file));
        }
        return this;
    }

    public MultiFileResource(File... fileArr) {
        super(new Resource[0]);
        add(fileArr);
    }

    public MultiFileResource add(Collection<File> collection) {
        Iterator<File> it = collection.iterator();
        while (it.hasNext()) {
            add((Resource) new FileResource(it.next()));
        }
        return this;
    }

    @Override // cn.hutool.core.io.resource.MultiResource
    public MultiFileResource add(Resource resource) {
        return (MultiFileResource) super.add(resource);
    }
}
