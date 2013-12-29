/*
 * Copyright 2013 lorislab.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lorislab.armonitor.web.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class TestController implements Serializable {
    
    private static final long serialVersionUID = -4883568423337397574L;
    
    private int count;
    
    @PostConstruct
    public void create() {
        System.out.println("POST CONSTRUCT");
        count = 100;
    }
    
    public void addCount() {
        count++;
    }
    
    public int getCount() {
        return count;
    }
}
