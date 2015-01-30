/**
 * Copyright 2015 DuraSpace, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fcrepo.auth.roles.basic.integration;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.fcrepo.auth.roles.common.integration.RolesFadTestObjectBean;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

/**
 * Verifies that role for readers is properly enforced.
 *
 * @author Scott Prater
 * @author Gregory Jansen
 */
public class BasicRolesReaderIT extends AbstractBasicRolesIT {

    private final static String TESTDS = "readertestds";

    private final static String TESTCHILD = "readertestchild";

    @Override
    protected List<RolesFadTestObjectBean> getTestObjs() {
        return test_objs;
    }

    /* Public object, one open datastream */
    @Test
    public void testReaderCanReadOpenObj()
            throws ClientProtocolException, IOException {
        assertEquals("Reader cannot read testparent1!", OK.getStatusCode(),
                canRead("examplereader", testParent1, true));
    }

    @Test
    public void testReaderCannotWriteDatastreamOnOpenObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to write datastream to testparent1!",
                FORBIDDEN
                .getStatusCode(), canAddDS("examplereader", testParent1,
                        TESTDS, true));
    }

    @Test
    public void testReaderCannotAddChildToOpenObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add child to testparent1!",
                FORBIDDEN
                .getStatusCode(), canAddChild("examplereader", testParent1,
                        TESTCHILD, true));
    }

    @Test
    public void testReaderCannotAddACLToOpenObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to testparent1!",
                FORBIDDEN
                .getStatusCode(), canAddACL("examplereader", testParent1,
                        "everyone", "admin", true));
    }

    /* Public object, one open datastream, one restricted datastream */
    /* object */
    @Test
    public void
    testReaderCanReadOpenObjWithRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals("Reader cannot read testparent2!", OK.getStatusCode(),
                canRead("examplereader", testParent2, true));
    }

    /* open datastream */
    @Test
    public void testReaderCanReadOpenObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read datastream testparent2/tsp1_data!", OK
                .getStatusCode(), canRead("examplereader",
                        testParent2 + "/" + tsp1Data,
                        true));
    }

    @Test
    public void
    testReaderCannotUpdateOpenObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent2/tsp1_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent2,
                        tsp1Data, true));
    }

    @Test
    public void testReaderCannotAddACLToOpenObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent2/tsp1_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent2 + "/" + tsp1Data, "everyone", "admin", true));
    }

    /* restricted datastream */
    @Test
    public void testReaderCanReadOpenObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read restricted datastream testparent2/tsp2_data!",
                OK.getStatusCode(), canRead("examplereader",
                        testParent2 + "/" + tsp2Data, true));
    }

    @Test
    public void testReaderCannotUpdateOpenObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update restricted datastream testparent2/tsp2_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent2,
                        tsp2Data, true));
    }

    @Test
    public void testReaderCannotAddACLToOpenObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to restricted datastream testparent2/tsp2_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent2 + "/" + tsp2Data, "everyone", "admin", true));
    }

    /* Child object (inherits ACL), one open datastream */
    @Test
    public void testReaderCanReadInheritedACLChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read testparent1/testchild1NoACL!", OK
                .getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild1NoACL,
                        true));
    }

    @Test
    public void testReaderCannotWriteDatastreamOnInheritedACLChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to write datastream to testparent1/testchild1NoACL!",
                FORBIDDEN.getStatusCode(), canAddDS("examplereader",
                        testParent1 + "/" + testChild1NoACL, TESTDS, true));
    }

    @Test
    public void testReaderCannotAddChildToInheritedACLChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add a child to testparent1/testchild1NoACL!",
                FORBIDDEN.getStatusCode(), canAddChild("examplereader",
                        testParent1 + "/" + testChild1NoACL, TESTCHILD, true));
    }

    @Test
    public void testReaderCannotAddACLToInheritedACLChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to testparent1/testchild1NoACL!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild1NoACL, "everyone", "admin",
                        true));
    }

    @Test
    public void testReaderCanReadInheritedACLChildObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read datastream testparent1/testchild1NoACL/tsc1_data!",
                OK.getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild1NoACL + "/" + tsc1Data, true));
    }

    @Test
    public void testReaderCannotUpdateInheritedACLChildObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent1/testchild1NoACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent1 + "/" + testChild1NoACL, tsc1Data, true));
    }

    @Test
    public
    void testReaderCannotAddACLToInheritedACLChildObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent1/testchild1NoACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild1NoACL + "/" + tsc1Data, "everyone",
                        "admin", true));
    }

    /* Restricted child object with own ACL, two restricted datastreams */
    @Test
    public void testReaderCanReadRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read testparent1/testchild2WithACL!", OK
                .getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild2WithACL, true));
    }

    @Test
    public void testReaderCannotWriteDatastreamOnRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to write a datastream to testparent1/testchild2WithACL!",
                FORBIDDEN.getStatusCode(), canAddDS("examplereader",
                        testParent1 + "/" + testChild2WithACL, TESTDS, true));
    }

    @Test
    public void testReaderCannotAddChildToRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add a child to testparent1/testchild2WithACL!",
                FORBIDDEN.getStatusCode(), canAddChild("examplereader",
                        testParent1 + "/" + testChild2WithACL, TESTCHILD, true));
    }

    @Test
    public void testReaderCannotAddACLToRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to testparent1/testchild2WithACL!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild2WithACL, "everyone", "admin",
                        true));
    }

    @Test
    public void testReaderCanReadRestrictedChildObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read datastream testparent1/testchild2WithACL/tsc1_data!",
                OK.getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild2WithACL + "/" + tsc1Data, true));
    }

    @Test
    public void testReaderCannotUpdateRestrictedChildObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent1/testchild2WithACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent1 + "/" + testChild2WithACL, tsc1Data, true));
    }

    @Test
    public void testReaderCannotAddACLToRestrictedChildObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent1/testchild2WithACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild2WithACL + "/" + tsc1Data, "everyone",
                        "admin", true));
    }

    /* Even more restricted datastream */
    @Test
    public void testReaderCannotReadRestrictedChildObjReallyRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to read datastream testparent1/testchild2WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild2WithACL + "/" + tsc2Data, true));
    }

    @Test
    public void testReaderCannotUpdateRestrictedChildObjReallyRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent1/testchild2WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent1 + "/" + testChild2WithACL, tsc2Data, true));
    }

    @Test
    public void testReaderCannotAddACLToRestrictedChildObjReallyRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent1/testchild2WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild2WithACL + "/" + tsc2Data, "everyone",
                        "admin", true));
    }

    /* Writer/Admin child object with own ACL, two restricted datastreams */
    @Test
    public void testReaderCannotReadWriterRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to read testparent1/testchild4WithACL!",
                FORBIDDEN.getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild4WithACL, true));
    }

    @Test
    public void testReaderCannotWriteDatastreamOnWriterRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to write a datastream to testparent1/testchild4WithACL!",
                FORBIDDEN.getStatusCode(), canAddDS("examplereader",
                        testParent1 + "/" + testChild4WithACL, TESTDS, true));
    }

    @Test
    public void testReaderCannotAddChildToWriterRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add a child to testparent1/testchild4WithACL!",
                FORBIDDEN.getStatusCode(), canAddChild("examplereader",
                        testParent1 + "/" + testChild4WithACL, TESTCHILD, true));
    }

    @Test
    public void testReaderCannotAddACLToWriterRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to testparent1/testchild4WithACL!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild4WithACL, "everyone", "admin",
                        true));
    }

    @Test
    public void testReaderCannotReadWriterRestrictedChildObjWriterRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to read datastream testparent1/testchild4WithACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild4WithACL + "/" + tsc1Data, true));
    }

    @Test
    public void testReaderCannotUpdateWriterRestrictedChildObjWriterRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent1/testchild4WithACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent1 + "/" + testChild4WithACL, tsc1Data, true));
    }

    @Test
    public void testReaderCannotAddACLToWriterRestrictedChildObjWriterRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent1/testchild4WithACL/tsc1_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild4WithACL + "/" + tsc1Data, "everyone",
                        "admin", true));
    }

    /* Even more restricted datastream */
    @Test
    public void testReaderCannotReadWriterRestrictedChildObjReallyWriterRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to read datastream testparent1/testchild4WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canRead("examplereader",
                        testParent1 + "/" + testChild4WithACL + "/" + tsc2Data, true));
    }

    @Test
    public void testReaderCannotUpdateWriterRestrictedChildObjReallyWriterRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent1/testchild4WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent1 + "/" + testChild4WithACL, tsc2Data, true));
    }

    @Test
    public void testReaderCannotAddACLToWriterRestrictedChildObjReallyWriterRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent1/testchild4WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent1 + "/" + testChild4WithACL + "/" + tsc2Data, "everyone",
                        "admin", true));
    }

    /* Admin object with public datastream */
    @Test
    public void testReaderCannotReadAdminObj() throws ClientProtocolException,
    IOException {
        assertEquals(
                "Reader should not be allowed to read testparent2/testchild5WithACL!",
                FORBIDDEN.getStatusCode(), canRead("examplereader",
                        testParent2 + "/" + testChild5WithACL, true));
    }

    @Test
    public void testReaderCannotWriteDatastreamOnAdminObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to write a datastream to testparent2/testchild5WithACL!",
                FORBIDDEN.getStatusCode(), canAddDS("examplereader",
                        testParent2 + "/" + testChild5WithACL, TESTDS, true));
    }

    @Test
    public void testReaderCannotAddChildToAdminObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add a child to testparent2/testchild5WithACL!",
                FORBIDDEN.getStatusCode(), canAddChild("examplereader",
                        testParent2 + "/" + testChild5WithACL, TESTCHILD, true));
    }

    @Test
    public void testReaderCannotAddACLToAdminObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to testparent2/testchild5WithACL!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent2 + "/" + testChild5WithACL, "everyone", "admin",
                        true));
    }

    @Test
    public void testReaderCanReadAdminObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader cannot read datastream testparent2/testchild5WithACL/tsc2_data!",
                OK.getStatusCode(), canRead("examplereader",
                        testParent2 + "/" + tsp1Data, true));
    }

    @Test
    public void testReaderCannotUpdateAdminObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to update datastream testparent2/testchild5WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canUpdateDS("examplereader",
                        testParent2 + "/" + testChild5WithACL, tsc2Data, true));
    }

    @Test
    public void testReaderCannotAddACLToAdminObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to datastream testparent2/testchild5WithACL/tsc2_data!",
                FORBIDDEN.getStatusCode(), canAddACL("examplereader",
                        testParent2 + "/" + testChild5WithACL + "/" + tsc2Data, "everyone",
                        "admin", true));
    }

    /* Deletions */
    @Test
    public void testReaderCannotDeleteOpenObj() throws ClientProtocolException,
    IOException {
        assertEquals(
                "Reader should not be allowed to delete object testparent3!",
                FORBIDDEN
                .getStatusCode(), canDelete("examplereader", testParent3,
                        true));
    }

    @Test
    public void testReaderCannotDeleteOpenObjPublicDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to delete datastream testparent3/tsp1_data!",
                FORBIDDEN.getStatusCode(), canDelete("examplereader",
                        testParent3 + "/" + tsp1Data, true));
    }

    @Test
    public void testReaderCannotDeleteOpenObjRestrictedDatastream()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to delete datastream testparent3/tsp2_data!",
                FORBIDDEN.getStatusCode(), canDelete("examplereader",
                        testParent3 + "/" + tsp2Data, true));
    }

    @Test
    public void testReaderCannotDeleteRestrictedChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to delete object testparent3/testchild3a!",
                FORBIDDEN.getStatusCode(), canDelete("examplereader",
                        testParent3 + "/" + testChild3A, true));
    }

    @Test
    public void testReaderCannotDeleteInheritedACLChildObj()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to delete object testparent3/testchild3b!",
                FORBIDDEN.getStatusCode(), canDelete("examplereader",
                        testParent3 + "/" + testChild3B, true));
    }

    /* root node */
    @Test
    public void testReaderCannotReadRootNode()
            throws ClientProtocolException, IOException {
        assertEquals("Reader should not be allowed to read root node!",
                FORBIDDEN
                .getStatusCode(),
                canRead("examplereader", "/", true));
    }

    @Test
    public void testReaderCannotWriteDatastreamOnRootNode()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to write a datastream to root node!",
                FORBIDDEN
                .getStatusCode(), canAddDS("examplereader", "/", TESTDS, true));
    }

    @Test
    public void testReaderCannotAddChildToRootNode()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add a child to root node!",
                FORBIDDEN
                .getStatusCode(), canAddChild("examplereader", "/", TESTCHILD, true));
    }

    @Test
    public void testReaderCannotAddACLToRootNode()
            throws ClientProtocolException, IOException {
        assertEquals(
                "Reader should not be allowed to add an ACL to root node!",
                FORBIDDEN
                .getStatusCode(), canAddACL("examplereader", "/", "everyone",
                        "admin", true));
    }
}
