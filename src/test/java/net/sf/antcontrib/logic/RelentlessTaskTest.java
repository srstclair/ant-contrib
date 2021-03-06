/*
 * Copyright (c) 2005 Ant-Contrib project.  All rights reserved.
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
package net.sf.antcontrib.logic;

import static org.junit.Assert.assertTrue;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildFileRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Testcase for &lt;relentless&gt;.
 *
 * @author <a href="mailto:clheiny@users.sf.net">Christopher Heiny</a>
 */
public class RelentlessTaskTest {
    @Rule
    public BuildFileRule buildRule = new BuildFileRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Method setUp.
     */
    @Before
    public void setUp() {
        buildRule.configureProject("src/test/resources/logic/relentless.xml");
    }

    /**
     * Method tearDown.
     */
    @After
    public void tearDown() {
        buildRule.executeTarget("teardown");
    }

    /**
     * Method testSimpleTasks.
     */
    @Test
    public void testSimpleTasks() {
        simpleTest("simpleTasks");
    }

    /**
     * Method testFailTask.
     */
    @Test
    public void testFailTask() {
        thrown.expect(BuildException.class);
        thrown.expectMessage("Relentless execution: 2 of 4 tasks failed.");
        buildRule.executeTarget("failTask");
    }

    /**
     * Method testNoTasks.
     */
    @Test
    public void testNoTasks() {
        thrown.expect(BuildException.class);
        thrown.expectMessage("No tasks specified for <relentless>.");
        buildRule.executeTarget("noTasks");
    }

    /**
     * Method simpleTest.
     *
     * @param target String
     */
    private void simpleTest(String target) {
        buildRule.executeTarget(target);
        int last = -1;
        for (int i = 1; i < 4; i++) {
            int thisIdx = buildRule.getLog().indexOf("Called with param: " + i);
            assertTrue(thisIdx > last);
            last = thisIdx;
        }
    }
}
