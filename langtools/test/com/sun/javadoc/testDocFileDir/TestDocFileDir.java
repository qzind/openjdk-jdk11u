/*
 * Copyright (c) 2002, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.io.File;

/*
 * @test
 * @bug 4258405 4973606 8024096
 * @summary This test verifies that the doc-file directory does not
 *          get overwritten when the sourcepath is equal to the destination
 *          directory.
 *          Also test that -docfilessubdirs and -excludedocfilessubdir both work.
 * @author jamieh
 * @library ../lib/
 * @build JavadocTester
 * @build TestDocFileDir
 * @run main TestDocFileDir
 */

public class TestDocFileDir extends JavadocTester {

    private static final String[][] TEST1 = {
        { "pkg/doc-files/testfile.txt",
            "This doc file did not get trashed."}
        };

    private static final String[] FILE_TEST2 = {
        "pkg/doc-files/subdir-used1/testfile.txt",
        "pkg/doc-files/subdir-used2/testfile.txt"
    };
    private static final String[] FILE_NEGATED_TEST2 = {
        "pkg/doc-files/subdir-excluded1/testfile.txt",
        "pkg/doc-files/subdir-excluded2/testfile.txt"
    };

    private static final String[][] TEST0 = {
        {"pkg/doc-files/testfile.txt",
            "This doc file did not get trashed."}
        };

    //Output dir = Input Dir
    private static final String[] ARGS1 =
        new String[] {
            "-d", OUTPUT_DIR + "-1",
            "-sourcepath",
            "blah" + File.pathSeparator + OUTPUT_DIR + "-1" +
            File.pathSeparator + "blah", "pkg"};

    //Exercising -docfilessubdirs and -excludedocfilessubdir
    private static final String[] ARGS2 =
        new String[] {
            "-d", OUTPUT_DIR + "-2",
            "-sourcepath", SRC_DIR,
            "-docfilessubdirs",
            "-excludedocfilessubdir", "subdir-excluded1:subdir-excluded2",
            "pkg"};

    //Output dir = "", Input dir = ""
    private static final String[] ARGS0 =
        new String[] {"pkg/C.java"};


    /**
     * The entry point of the test.
     * @param args the array of command line arguments.
     */
    public static void main(String[] args) {
        TestDocFileDir tester = new TestDocFileDir();
        copyDir(SRC_DIR + "/pkg", ".");
        tester.run(ARGS0, TEST0, NO_TEST);
        copyDir(SRC_DIR + "/pkg", OUTPUT_DIR + "-1");
        tester.run(ARGS1, TEST1, NO_TEST);
        tester.run(ARGS2, NO_TEST, NO_TEST, FILE_TEST2, FILE_NEGATED_TEST2);
        tester.printSummary();
    }
}
