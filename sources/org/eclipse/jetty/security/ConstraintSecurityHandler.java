package org.eclipse.jetty.security;

import com.just.agentweb.DefaultWebClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.HttpConstraintElement;
import javax.servlet.HttpMethodConstraintElement;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.StringMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes9.dex */
public class ConstraintSecurityHandler extends SecurityHandler implements ConstraintAware {
    private static final String OMISSION_SUFFIX = ".omission";
    private final List<ConstraintMapping> _constraintMappings = new CopyOnWriteArrayList();
    private final Set<String> _roles = new CopyOnWriteArraySet();
    private final PathMap _constraintMap = new PathMap();
    private boolean _strict = true;

    public static Constraint createConstraint() {
        return new Constraint();
    }

    public static List<ConstraintMapping> createConstraintsWithMappingsForPath(String str, String str2, ServletSecurityElement servletSecurityElement) {
        ArrayList arrayList = new ArrayList();
        Constraint constraintCreateConstraint = createConstraint(str, servletSecurityElement);
        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setPathSpec(str2);
        constraintMapping.setConstraint(constraintCreateConstraint);
        arrayList.add(constraintMapping);
        ArrayList arrayList2 = new ArrayList();
        Collection<HttpMethodConstraintElement> httpMethodConstraints = servletSecurityElement.getHttpMethodConstraints();
        if (httpMethodConstraints != null) {
            for (HttpMethodConstraintElement httpMethodConstraintElement : httpMethodConstraints) {
                Constraint constraintCreateConstraint2 = createConstraint(str, httpMethodConstraintElement);
                ConstraintMapping constraintMapping2 = new ConstraintMapping();
                constraintMapping2.setConstraint(constraintCreateConstraint2);
                constraintMapping2.setPathSpec(str2);
                if (httpMethodConstraintElement.getMethodName() != null) {
                    constraintMapping2.setMethod(httpMethodConstraintElement.getMethodName());
                    arrayList2.add(httpMethodConstraintElement.getMethodName());
                }
                arrayList.add(constraintMapping2);
            }
        }
        if (arrayList2.size() > 0) {
            constraintMapping.setMethodOmissions((String[]) arrayList2.toArray(new String[arrayList2.size()]));
        }
        return arrayList;
    }

    public static List<ConstraintMapping> getConstraintMappingsForPath(String str, List<ConstraintMapping> list) {
        if (str == null || "".equals(str.trim()) || list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (ConstraintMapping constraintMapping : list) {
            if (str.equals(constraintMapping.getPathSpec())) {
                arrayList.add(constraintMapping);
            }
        }
        return arrayList;
    }

    public static List<ConstraintMapping> removeConstraintMappingsForPath(String str, List<ConstraintMapping> list) {
        if (str == null || "".equals(str.trim()) || list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (ConstraintMapping constraintMapping : list) {
            if (!str.equals(constraintMapping.getPathSpec())) {
                arrayList.add(constraintMapping);
            }
        }
        return arrayList;
    }

    @Override // org.eclipse.jetty.security.ConstraintAware
    public void addConstraintMapping(ConstraintMapping constraintMapping) {
        this._constraintMappings.add(constraintMapping);
        if (constraintMapping.getConstraint() != null && constraintMapping.getConstraint().getRoles() != null) {
            for (String str : constraintMapping.getConstraint().getRoles()) {
                addRole(str);
            }
        }
        if (isStarted()) {
            processConstraintMapping(constraintMapping);
        }
    }

    @Override // org.eclipse.jetty.security.ConstraintAware
    public void addRole(String str) {
        boolean zAdd = this._roles.add(str);
        if (isStarted() && zAdd && this._strict) {
            Iterator it = this._constraintMap.values().iterator();
            while (it.hasNext()) {
                for (RoleInfo roleInfo : ((Map) it.next()).values()) {
                    if (roleInfo.isAnyRole()) {
                        roleInfo.addRole(str);
                    }
                }
            }
        }
    }

    @Override // org.eclipse.jetty.security.SecurityHandler
    public boolean checkUserDataPermissions(String str, Request request, Response response, Object obj) throws IOException {
        String str2;
        String str3;
        if (obj == null) {
            return true;
        }
        RoleInfo roleInfo = (RoleInfo) obj;
        if (roleInfo.isForbidden()) {
            return false;
        }
        UserDataConstraint userDataConstraint = roleInfo.getUserDataConstraint();
        if (userDataConstraint == null || userDataConstraint == UserDataConstraint.None) {
            return true;
        }
        Connector connector = AbstractHttpConnection.getCurrentConnection().getConnector();
        if (userDataConstraint == UserDataConstraint.Integral) {
            if (connector.isIntegral(request)) {
                return true;
            }
            if (connector.getIntegralPort() > 0) {
                String integralScheme = connector.getIntegralScheme();
                int integralPort = connector.getIntegralPort();
                if ("https".equalsIgnoreCase(integralScheme) && integralPort == 443) {
                    str3 = DefaultWebClient.HTTPS_SCHEME + request.getServerName() + request.getRequestURI();
                } else {
                    str3 = integralScheme + "://" + request.getServerName() + ":" + integralPort + request.getRequestURI();
                }
                if (request.getQueryString() != null) {
                    str3 = str3 + "?" + request.getQueryString();
                }
                response.setContentLength(0);
                response.sendRedirect(str3);
            } else {
                response.sendError(403, "!Integral");
            }
            request.setHandled(true);
            return false;
        }
        if (userDataConstraint != UserDataConstraint.Confidential) {
            throw new IllegalArgumentException("Invalid dataConstraint value: " + userDataConstraint);
        }
        if (connector.isConfidential(request)) {
            return true;
        }
        if (connector.getConfidentialPort() > 0) {
            String confidentialScheme = connector.getConfidentialScheme();
            int confidentialPort = connector.getConfidentialPort();
            if ("https".equalsIgnoreCase(confidentialScheme) && confidentialPort == 443) {
                str2 = DefaultWebClient.HTTPS_SCHEME + request.getServerName() + request.getRequestURI();
            } else {
                str2 = confidentialScheme + "://" + request.getServerName() + ":" + confidentialPort + request.getRequestURI();
            }
            if (request.getQueryString() != null) {
                str2 = str2 + "?" + request.getQueryString();
            }
            response.setContentLength(0);
            response.sendRedirect(str2);
        } else {
            response.sendError(403, "!Confidential");
        }
        request.setHandled(true);
        return false;
    }

    @Override // org.eclipse.jetty.security.SecurityHandler
    public boolean checkWebResourcePermissions(String str, Request request, Response response, Object obj, UserIdentity userIdentity) throws IOException {
        if (obj == null) {
            return true;
        }
        RoleInfo roleInfo = (RoleInfo) obj;
        if (!roleInfo.isChecked()) {
            return true;
        }
        if (roleInfo.isAnyRole() && request.getAuthType() != null) {
            return true;
        }
        Iterator<String> it = roleInfo.getRoles().iterator();
        while (it.hasNext()) {
            if (userIdentity.isUserInRole(it.next(), null)) {
                return true;
            }
        }
        return false;
    }

    public void configureRoleInfo(RoleInfo roleInfo, ConstraintMapping constraintMapping) {
        roleInfo.setForbidden(constraintMapping.getConstraint().isForbidden());
        roleInfo.setUserDataConstraint(UserDataConstraint.get(constraintMapping.getConstraint().getDataConstraint()));
        if (roleInfo.isForbidden()) {
            return;
        }
        roleInfo.setChecked(constraintMapping.getConstraint().getAuthenticate());
        if (roleInfo.isChecked()) {
            if (constraintMapping.getConstraint().isAnyRole()) {
                if (!this._strict) {
                    roleInfo.setAnyRole(true);
                    return;
                }
                Iterator<String> it = this._roles.iterator();
                while (it.hasNext()) {
                    roleInfo.addRole(it.next());
                }
                return;
            }
            for (String str : constraintMapping.getConstraint().getRoles()) {
                if (this._strict && !this._roles.contains(str)) {
                    throw new IllegalArgumentException("Attempt to use undeclared role: " + str + ", known roles: " + this._roles);
                }
                roleInfo.addRole(str);
            }
        }
    }

    @Override // org.eclipse.jetty.security.SecurityHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        this._constraintMap.clear();
        List<ConstraintMapping> list = this._constraintMappings;
        if (list != null) {
            Iterator<ConstraintMapping> it = list.iterator();
            while (it.hasNext()) {
                processConstraintMapping(it.next());
            }
        }
        super.doStart();
    }

    @Override // org.eclipse.jetty.security.SecurityHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._constraintMap.clear();
        this._constraintMappings.clear();
        this._roles.clear();
        super.doStop();
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        AggregateLifeCycle.dump(appendable, str, Collections.singleton(getLoginService()), Collections.singleton(getIdentityService()), Collections.singleton(getAuthenticator()), Collections.singleton(this._roles), this._constraintMap.entrySet(), getBeans(), TypeUtil.asList(getHandlers()));
    }

    @Override // org.eclipse.jetty.security.ConstraintAware
    public List<ConstraintMapping> getConstraintMappings() {
        return this._constraintMappings;
    }

    @Override // org.eclipse.jetty.security.ConstraintAware
    public Set<String> getRoles() {
        return this._roles;
    }

    @Override // org.eclipse.jetty.security.SecurityHandler
    public boolean isAuthMandatory(Request request, Response response, Object obj) {
        if (obj == null) {
            return false;
        }
        return ((RoleInfo) obj).isChecked();
    }

    public boolean isStrict() {
        return this._strict;
    }

    @Override // org.eclipse.jetty.security.SecurityHandler
    public Object prepareConstraintInfo(String str, Request request) {
        Map map = (Map) this._constraintMap.match(str);
        if (map == null) {
            return null;
        }
        String method = request.getMethod();
        RoleInfo roleInfo = (RoleInfo) map.get(method);
        if (roleInfo != null) {
            return roleInfo;
        }
        ArrayList arrayList = new ArrayList();
        RoleInfo roleInfo2 = (RoleInfo) map.get(null);
        if (roleInfo2 != null) {
            arrayList.add(roleInfo2);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey() != null && ((String) entry.getKey()).contains(OMISSION_SUFFIX)) {
                if (!(method + OMISSION_SUFFIX).equals(entry.getKey())) {
                    arrayList.add(entry.getValue());
                }
            }
        }
        if (arrayList.size() == 1) {
            return (RoleInfo) arrayList.get(0);
        }
        RoleInfo roleInfo3 = new RoleInfo();
        roleInfo3.setUserDataConstraint(UserDataConstraint.None);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            roleInfo3.combine((RoleInfo) it.next());
        }
        return roleInfo3;
    }

    public void processConstraintMapping(ConstraintMapping constraintMapping) {
        Map<String, RoleInfo> stringMap = (Map) this._constraintMap.get(constraintMapping.getPathSpec());
        if (stringMap == null) {
            stringMap = new StringMap();
            this._constraintMap.put(constraintMapping.getPathSpec(), stringMap);
        }
        RoleInfo roleInfo = stringMap.get(null);
        if (roleInfo == null || !roleInfo.isForbidden()) {
            if (constraintMapping.getMethodOmissions() != null && constraintMapping.getMethodOmissions().length > 0) {
                processConstraintMappingWithMethodOmissions(constraintMapping, stringMap);
                return;
            }
            String method = constraintMapping.getMethod();
            RoleInfo roleInfo2 = stringMap.get(method);
            if (roleInfo2 == null) {
                roleInfo2 = new RoleInfo();
                stringMap.put(method, roleInfo2);
                if (roleInfo != null) {
                    roleInfo2.combine(roleInfo);
                }
            }
            if (roleInfo2.isForbidden()) {
                return;
            }
            configureRoleInfo(roleInfo2, constraintMapping);
            if (roleInfo2.isForbidden()) {
                if (method == null) {
                    stringMap.clear();
                    stringMap.put(null, roleInfo2);
                    return;
                }
                return;
            }
            if (method == null) {
                for (Map.Entry<String, RoleInfo> entry : stringMap.entrySet()) {
                    if (entry.getKey() != null) {
                        entry.getValue().combine(roleInfo2);
                    }
                }
            }
        }
    }

    public void processConstraintMappingWithMethodOmissions(ConstraintMapping constraintMapping, Map<String, RoleInfo> map) {
        for (String str : constraintMapping.getMethodOmissions()) {
            RoleInfo roleInfo = map.get(str + OMISSION_SUFFIX);
            if (roleInfo == null) {
                roleInfo = new RoleInfo();
                map.put(str + OMISSION_SUFFIX, roleInfo);
            }
            configureRoleInfo(roleInfo, constraintMapping);
        }
    }

    public void setConstraintMappings(List<ConstraintMapping> list) {
        setConstraintMappings(list, null);
    }

    public void setRoles(Set<String> set) {
        this._roles.clear();
        this._roles.addAll(set);
    }

    public void setStrict(boolean z2) {
        this._strict = z2;
    }

    public static Constraint createConstraint(Constraint constraint) {
        try {
            return (Constraint) constraint.clone();
        } catch (CloneNotSupportedException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void setConstraintMappings(ConstraintMapping[] constraintMappingArr) {
        setConstraintMappings(Arrays.asList(constraintMappingArr), null);
    }

    @Override // org.eclipse.jetty.security.ConstraintAware
    public void setConstraintMappings(List<ConstraintMapping> list, Set<String> set) {
        this._constraintMappings.clear();
        this._constraintMappings.addAll(list);
        if (set == null) {
            set = new HashSet<>();
            Iterator<ConstraintMapping> it = list.iterator();
            while (it.hasNext()) {
                String[] roles = it.next().getConstraint().getRoles();
                if (roles != null) {
                    for (String str : roles) {
                        if (!"*".equals(str)) {
                            set.add(str);
                        }
                    }
                }
            }
        }
        setRoles(set);
        if (isStarted()) {
            Iterator<ConstraintMapping> it2 = this._constraintMappings.iterator();
            while (it2.hasNext()) {
                processConstraintMapping(it2.next());
            }
        }
    }

    public static Constraint createConstraint(String str, boolean z2, String[] strArr, int i2) {
        Constraint constraintCreateConstraint = createConstraint();
        if (str != null) {
            constraintCreateConstraint.setName(str);
        }
        constraintCreateConstraint.setAuthenticate(z2);
        constraintCreateConstraint.setRoles(strArr);
        constraintCreateConstraint.setDataConstraint(i2);
        return constraintCreateConstraint;
    }

    public static Constraint createConstraint(String str, HttpConstraintElement httpConstraintElement) {
        return createConstraint(str, httpConstraintElement.getRolesAllowed(), httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee());
    }

    public static Constraint createConstraint(String str, String[] strArr, ServletSecurity.EmptyRoleSemantic emptyRoleSemantic, ServletSecurity.TransportGuarantee transportGuarantee) {
        Constraint constraintCreateConstraint = createConstraint();
        if (strArr != null && strArr.length != 0) {
            constraintCreateConstraint.setAuthenticate(true);
            constraintCreateConstraint.setRoles(strArr);
            constraintCreateConstraint.setName(str + "-RolesAllowed");
        } else if (emptyRoleSemantic.equals(ServletSecurity.EmptyRoleSemantic.DENY)) {
            constraintCreateConstraint.setName(str + "-Deny");
            constraintCreateConstraint.setAuthenticate(true);
        } else {
            constraintCreateConstraint.setName(str + "-Permit");
            constraintCreateConstraint.setAuthenticate(false);
        }
        constraintCreateConstraint.setDataConstraint(transportGuarantee.equals(ServletSecurity.TransportGuarantee.CONFIDENTIAL) ? 2 : 0);
        return constraintCreateConstraint;
    }
}
