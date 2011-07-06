/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.security.microcontainer.metadata;

import org.jboss.security.auth.login.XMLLoginConfigImpl;
import org.jboss.security.config.ApplicationPolicy;
import org.jboss.security.microcontainer.beans.ApplicationPolicyBean;
import org.jboss.security.microcontainer.beans.IdentityTrustPolicyBean;

/**
 * <p>
 * Extends the {@code BasicApplicationPolicyTestCase} to test the configuration of identity-trust policies along with
 * the basic authentication policies.
 * </p>
 * <p>
 * The first scenario tests the configuration of an identity-trust policy together with an authentication policy:
 * 
 * <pre>
 *  &lt;application-policy xmlns=&quot;urn:jboss:security-beans:1.0&quot; name=&quot;TestPolicy1&quot;&gt;
 *     &lt;authentication&gt;
 *        &lt;login-module code=&quot;org.jboss.security.auth.AuthModule1&quot; flag=&quot;required&quot;&gt;
 *           &lt;module-option name=&quot;authOption1&quot;&gt;value1&lt;/module-option&gt;
 *           &lt;module-option name=&quot;authOption2&quot;&gt;value2&lt;/module-option&gt;
 *        &lt;/login-module&gt;
 *        &lt;login-module code=&quot;org.jboss.security.auth.AuthModule2&quot; flag=&quot;optional&quot;&gt;
 *           &lt;module-option name=&quot;authOption3&quot;&gt;value3&lt;/module-option&gt;
 *           &lt;module-option name=&quot;authOption4&quot;&gt;value4&lt;/module-option&gt;
 *        &lt;/login-module&gt;
 *     &lt;/authentication&gt;
 *     &lt;identity-trust&gt;
 *        &lt;trust-module code=&quot;org.jboss.security.trust.IdentityTrustModule1&quot; flag=&quot;required&quot;&gt;
 *           &lt;module-option name=&quot;trustOption1&quot;&gt;value1&lt;/module-option&gt;
 *           &lt;module-option name=&quot;trustOption2&quot;&gt;value2&lt;/module-option&gt;
 *        &lt;/trust-module&gt;
 *     &lt;/identity-trust&gt;
 *  &lt;/application-policy&gt;
 * </pre>
 * 
 * while the second scenario tests the configuration of the trust policy together with an authentication-jaspi policy:
 * 
 * <pre>
 *  &lt;application-policy xmlns=&quot;urn:jboss:security-beans:1.0&quot; name=&quot;TestPolicy2&quot;&gt;
 *     &lt;authentication-jaspi&gt;
 *        &lt;login-module-stack name=&quot;ModuleStack1&quot;&gt;
 *           &lt;login-module code=&quot;org.jboss.security.auth.AuthModule3&quot; flag=&quot;required&quot;&gt;
 *              &lt;module-option name=&quot;authOption5&quot;&gt;value5&lt;/module-option&gt;
 *           &lt;/login-module&gt;
 *           &lt;login-module code=&quot;org.jboss.security.auth.AuthModule4&quot; flag=&quot;optional&quot;/&gt;
 *        &lt;/login-module-stack&gt;
 *        &lt;login-module-stack name=&quot;ModuleStack2&quot;&gt;
 *           &lt;login-module code=&quot;org.jboss.security.auth.AuthModule5&quot; flag=&quot;required&quot;&gt;
 *              &lt;module-option name=&quot;authOption6&quot;&gt;value6&lt;/module-option&gt;
 *              &lt;module-option name=&quot;authOption7&quot;&gt;value7&lt;/module-option&gt;
 *           &lt;/login-module&gt;
 *        &lt;/login-module-stack&gt;
 *        &lt;auth-module code=&quot;org.jboss.security.auth.AuthModule1&quot; login-module-stack-ref=&quot;ModuleStack2&quot;&gt;
 *           &lt;module-option name=&quot;authOption1&quot;&gt;value1&lt;/module-option&gt;
 *           &lt;module-option name=&quot;authOption2&quot;&gt;value2&lt;/module-option&gt;
 *        &lt;/auth-module&gt;
 *     &lt;/authentication-jaspi&gt;
 *     &lt;identity-trust&gt;
 *        &lt;trust-module code=&quot;org.jboss.security.trust.IdentityTrustModule1&quot; flag=&quot;required&quot;&gt;
 *           &lt;module-option name=&quot;trustOption1&quot;&gt;value1&lt;/module-option&gt;
 *           &lt;module-option name=&quot;trustOption2&quot;&gt;value2&lt;/module-option&gt;
 *        &lt;/trust-module&gt;
 *     &lt;/identity-trust&gt;
 *  &lt;/application-policy&gt;
 * 
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 * 
 */
public class IdentityTrustPolicyTestCase extends BasicApplicationPolicyTestCase
{

   /**
    * <p>
    * Creates an instance of {@code IdentityTrustPolicyTestCase} with the specified name.
    * </p>
    * 
    * @param name a {@code String} representing the name of this test case.
    */
   public IdentityTrustPolicyTestCase(String name)
   {
      super(name);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.test.security.microcontainer.beans.BasicApplicationPolicyTestCase#testApplicationPoliciesCreation()
    */
   @Override
   public void testApplicationPoliciesCreation() throws Exception
   {
      super.testApplicationPoliciesCreation();

      // validate the identity-trust policy beans have been properly created.
      ApplicationPolicyBean policyBean1 = (ApplicationPolicyBean) super.getBean("TestPolicy1");
      assertNotNull("Unexpected null identity-trust policy found", policyBean1.getIdentityTrustPolicy());

      ApplicationPolicyBean policyBean2 = (ApplicationPolicyBean) super.getBean("TestPolicy2");
      assertNotNull("Unexpected null identity-trust policy found", policyBean2.getIdentityTrustPolicy());

      IdentityTrustPolicyBean trustBean = (IdentityTrustPolicyBean) super.getBean("TestPolicy1$IdentityTrustPolicy");
      // assert the bean retrieved from the microcontainer is the same that has been injected into the app policy.
      assertEquals(policyBean1.getIdentityTrustPolicy(), trustBean);
      BeanValidator.validateIdentityTrustBean(trustBean);

      trustBean = (IdentityTrustPolicyBean) super.getBean("TestPolicy2$IdentityTrustPolicy");
      assertEquals(policyBean2.getIdentityTrustPolicy(), trustBean);
      BeanValidator.validateIdentityTrustBean(trustBean);

      // verify the contents of the identity-trust policies.
      ApplicationPolicy policy = XMLLoginConfigImpl.getInstance().getApplicationPolicy("TestPolicy1");
      PolicyValidator.validateIdentityTrustPolicy("TestPolicy1", policy.getIdentityTrustInfo());

      policy = XMLLoginConfigImpl.getInstance().getApplicationPolicy("TestPolicy2");
      PolicyValidator.validateIdentityTrustPolicy("TestPolicy2", policy.getIdentityTrustInfo());

   }
}