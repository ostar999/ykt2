package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
import okhttp3.HttpUrl;

/* loaded from: classes9.dex */
public class ResourceCollection extends Resource {
    private Resource[] _resources;

    public ResourceCollection() {
        this._resources = new Resource[0];
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public Resource addPath(String str) throws IOException {
        if (this._resources == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        if (str == null) {
            throw new MalformedURLException();
        }
        if (str.length() == 0 || "/".equals(str)) {
            return this;
        }
        int i2 = 0;
        Resource resourceAddPath = null;
        while (true) {
            Resource[] resourceArr = this._resources;
            if (i2 >= resourceArr.length) {
                break;
            }
            resourceAddPath = resourceArr[i2].addPath(str);
            if (!resourceAddPath.exists()) {
                i2++;
            } else if (!resourceAddPath.isDirectory()) {
                return resourceAddPath;
            }
        }
        int i3 = i2 + 1;
        ArrayList arrayList = null;
        while (true) {
            Resource[] resourceArr2 = this._resources;
            if (i3 >= resourceArr2.length) {
                break;
            }
            Resource resourceAddPath2 = resourceArr2[i3].addPath(str);
            if (resourceAddPath2.exists() && resourceAddPath2.isDirectory()) {
                if (resourceAddPath != null) {
                    arrayList = new ArrayList();
                    arrayList.add(resourceAddPath);
                    resourceAddPath = null;
                }
                arrayList.add(resourceAddPath2);
            }
            i3++;
        }
        if (resourceAddPath != null) {
            return resourceAddPath;
        }
        if (arrayList != null) {
            return new ResourceCollection((Resource[]) arrayList.toArray(new Resource[arrayList.size()]));
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public void copyTo(File file) throws IOException {
        int length = this._resources.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return;
            }
            this._resources[i2].copyTo(file);
            length = i2;
        }
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean delete() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean exists() {
        if (this._resources != null) {
            return true;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public Object findResource(String str) throws IOException {
        int i2 = 0;
        Resource resourceAddPath = null;
        while (true) {
            Resource[] resourceArr = this._resources;
            if (i2 >= resourceArr.length) {
                break;
            }
            resourceAddPath = resourceArr[i2].addPath(str);
            if (!resourceAddPath.exists()) {
                i2++;
            } else if (!resourceAddPath.isDirectory()) {
                return resourceAddPath;
            }
        }
        int i3 = i2 + 1;
        ArrayList arrayList = null;
        while (true) {
            Resource[] resourceArr2 = this._resources;
            if (i3 >= resourceArr2.length) {
                break;
            }
            Resource resourceAddPath2 = resourceArr2[i3].addPath(str);
            if (resourceAddPath2.exists() && resourceAddPath2.isDirectory()) {
                if (resourceAddPath != null) {
                    arrayList = new ArrayList();
                    arrayList.add(resourceAddPath);
                }
                arrayList.add(resourceAddPath2);
            }
            i3++;
        }
        if (resourceAddPath != null) {
            return resourceAddPath;
        }
        if (arrayList != null) {
            return arrayList;
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public File getFile() throws IOException {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            File file = resource.getFile();
            if (file != null) {
                return file;
            }
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public InputStream getInputStream() throws IOException {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            InputStream inputStream = resource.getInputStream();
            if (inputStream != null) {
                return inputStream;
            }
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public String getName() {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            String name = resource.getName();
            if (name != null) {
                return name;
            }
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public OutputStream getOutputStream() throws IOException, SecurityException {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            OutputStream outputStream = resource.getOutputStream();
            if (outputStream != null) {
                return outputStream;
            }
        }
        return null;
    }

    public Resource[] getResources() {
        return this._resources;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public URL getURL() {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            URL url = resource.getURL();
            if (url != null) {
                return url;
            }
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean isContainedIn(Resource resource) throws MalformedURLException {
        return false;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean isDirectory() {
        if (this._resources != null) {
            return true;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public long lastModified() {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            long jLastModified = resource.lastModified();
            if (jLastModified != -1) {
                return jLastModified;
            }
        }
        return -1L;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public long length() {
        return -1L;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public String[] list() {
        if (this._resources == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        HashSet hashSet = new HashSet();
        for (Resource resource : this._resources) {
            for (String str : resource.list()) {
                hashSet.add(str);
            }
        }
        String[] strArr = (String[]) hashSet.toArray(new String[hashSet.size()]);
        Arrays.sort(strArr);
        return strArr;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public void release() {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            throw new IllegalStateException("*resources* not set.");
        }
        for (Resource resource : resourceArr) {
            resource.release();
        }
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean renameTo(Resource resource) throws SecurityException {
        throw new UnsupportedOperationException();
    }

    public void setResources(Resource[] resourceArr) {
        if (resourceArr == null) {
            resourceArr = new Resource[0];
        }
        this._resources = resourceArr;
    }

    public void setResourcesAsCSV(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",;");
        int iCountTokens = stringTokenizer.countTokens();
        if (iCountTokens == 0) {
            throw new IllegalArgumentException("ResourceCollection@setResourcesAsCSV(String)  argument must be a string containing one or more comma-separated resource strings.");
        }
        this._resources = new Resource[iCountTokens];
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            try {
                this._resources[i2] = Resource.newResource(stringTokenizer.nextToken().trim());
                if (!this._resources[i2].exists() || !this._resources[i2].isDirectory()) {
                    throw new IllegalArgumentException(this._resources[i2] + " is not an existing directory.");
                }
                i2++;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public String toString() {
        Resource[] resourceArr = this._resources;
        return resourceArr == null ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : String.valueOf(Arrays.asList(resourceArr));
    }

    public ResourceCollection(Resource... resourceArr) {
        ArrayList arrayList = new ArrayList();
        for (Resource resource : resourceArr) {
            if (resource != null) {
                if (resource instanceof ResourceCollection) {
                    for (Resource resource2 : ((ResourceCollection) resource).getResources()) {
                        arrayList.add(resource2);
                    }
                } else {
                    arrayList.add(resource);
                }
            }
        }
        Resource[] resourceArr2 = (Resource[]) arrayList.toArray(new Resource[arrayList.size()]);
        this._resources = resourceArr2;
        for (Resource resource3 : resourceArr2) {
            if (!resource3.exists() || !resource3.isDirectory()) {
                throw new IllegalArgumentException(resource3 + " is not an existing directory.");
            }
        }
    }

    public ResourceCollection(String[] strArr) {
        this._resources = new Resource[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            try {
                this._resources[i2] = Resource.newResource(strArr[i2]);
                if (!this._resources[i2].exists() || !this._resources[i2].isDirectory()) {
                    throw new IllegalArgumentException(this._resources[i2] + " is not an existing directory.");
                }
            } catch (IllegalArgumentException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new RuntimeException(e3);
            }
        }
    }

    public ResourceCollection(String str) {
        setResourcesAsCSV(str);
    }
}
