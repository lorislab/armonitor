/*
 * Copyright 2014 lorislab.org.
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

package org.lorislab.armonitor.timer.model;

/**
 * The timer configuration model.
 * 
 * @author Andrej Petras
 */
public class TimerConfig {
   
    /**
     * Start the timer after restart.
     */
    public boolean start = false;
    /**
     * Enable the execution of the timer.
     */
    public boolean enabled = false;
    /**
     * The timeout configuration: second.
     */
    public String second = "*/30";
    /**
     * The timeout configuration: minute.
     */    
    public String minute = "*";
    /**
     * The timeout configuration: hour.
     */    
    public String hour = "*";
}
