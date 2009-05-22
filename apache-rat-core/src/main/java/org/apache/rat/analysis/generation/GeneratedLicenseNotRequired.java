/*
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 */ 
package org.apache.rat.analysis.generation;

import java.util.regex.Pattern;

import org.apache.rat.analysis.IHeaderMatcher;
import org.apache.rat.analysis.RatHeaderAnalysisException;
import org.apache.rat.api.MetaData;
import org.apache.rat.document.IDocument;
import org.apache.rat.report.claim.IClaimReporter;

public class GeneratedLicenseNotRequired implements IHeaderMatcher {

    private static final Pattern[] DEFAULT_PATTERNS = {Pattern.compile(".*generated by Cayenne.*"),
        Pattern.compile(".*Generated By:JJTree.*"),
        Pattern.compile(".*Generated By:JavaCC.*"),
        Pattern.compile(".*THIS FILE IS AUTOMATICALLY GENERATED.*"),
        Pattern.compile(".*NOTE: this file is autogenerated by XBeans.*"),
        Pattern.compile(".*This file was automatically generated by .*"),
        Pattern.compile(".*# WARNING: DO NOT EDIT OR DELETE THIS WORKSPACE FILE!.*"),
        Pattern.compile(".*# Microsoft Developer Studio Generated NMAKE File.*"),
        Pattern.compile(".*# Microsoft Developer Studio Generated Build File.*"),
        Pattern.compile(".*Generated from configure.ac by autoheader.*"),
        Pattern.compile(".*generated automatically by aclocal.*"),
        Pattern.compile(".*build.xml generated by maven from project.xml.*"),
        Pattern.compile(".*This file was generated by.*"),
        Pattern.compile(".*This file has been automatically generated..*"),
        Pattern.compile(".*Automatically generated - do not modify!.*"),
        Pattern.compile(".*Javadoc style sheet.*"),
        Pattern.compile(".*SOURCE FILE GENERATATED.*"),
        Pattern.compile(".*Generated by the Batik.*"),
        Pattern.compile(".*this file is autogenerated.*"),
        Pattern.compile(".*This class was autogenerated.*"),
        Pattern.compile(".*Generated by Maven.*"),
        Pattern.compile(".*This class was generated by.*")};
    
    
    private final Pattern[] linePatterns;
    private final int numberOfPatterns;
    
    public GeneratedLicenseNotRequired() {
        this(DEFAULT_PATTERNS);
    }
    
    public GeneratedLicenseNotRequired(final Pattern[] linePatterns) {
        this.linePatterns = linePatterns;
        this.numberOfPatterns = linePatterns.length;
    }

    public boolean match(IDocument subject, String line, IClaimReporter reporter) throws RatHeaderAnalysisException {
        boolean result = false;
        for (int i=0;i<numberOfPatterns;i++) {
            if (linePatterns[i].matcher(line).matches()) {
                result = true;
                reportOnLicense(subject, reporter);
                break;
            }
        }
        return result;
    }

    private void reportOnLicense(IDocument subject, IClaimReporter reporter) throws RatHeaderAnalysisException {
        subject.getMetaData().set(MetaData.RAT_LICENSE_FAMILY_CATEGORY_DATUM_GEN);
    }

    public void reset() {
        // stateless
    }

}
