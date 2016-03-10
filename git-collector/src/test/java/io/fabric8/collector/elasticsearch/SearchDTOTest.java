/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.collector.elasticsearch;

import io.fabric8.collector.git.elasticsearch.Searches;
import io.fabric8.utils.cxf.JsonHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 */
public class SearchDTOTest {
    @Test
    public void testFirstMatchAllWithMaxValue() throws Exception {
        SearchDTO search = new SearchDTO();
        search.matchAll();
        search.setSize(1L);
        search.addSort("run_id", false);

        System.out.println("Have created JSON: " + JsonHelper.toJson(search));
    }

    @Test
    public void testFirstMatchMaxValue() throws Exception {
        String namespace = "default";
        String app = "kubeflix";
        boolean ascending = false;
        SearchDTO search = Searches.createMinMaxGitCommitSearch(namespace, app, ascending);

        String json = JsonHelper.toJson(search);
        System.out.println("Have created JSON: " + json);

        assertEquals("{\n" +
                "  \"filter\" : {\n" +
                "    \"bool\" : {\n" +
                "      \"must\" : [ {\n" +
                "        \"term\" : {\n" +
                "          \"namespace\" : \"default\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"term\" : {\n" +
                "          \"app\" : \"kubeflix\"\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"sort\" : [ {\n" +
                "    \"commit_time\" : {\n" +
                "      \"order\" : \"desc\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"size\" : 1\n" +
                "}", json);
    }

}
