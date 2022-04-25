/**
 *  UniFi Presence Sensor
 *
 *  Copyright 2022 xtreme
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

def setVersion(){
    state.name = "UniFi Presence Sensor"
	state.version = "2022.04.23.1"
}

metadata {
	definition (
                name: "UniFi Presence Sensor",
                namespace: "xtreme22886",
                author: "xtreme",
        		importUrl: "https://raw.githubusercontent.com/xtreme22886/Hubitat_UniFi-Presence-Sensor/main/Driver/unifi-presence-sensor.groovy") {
        		capability "PresenceSensor"
        		capability "Sensor"
    
    command "arrived"
    command "departed"
    }
}

def arrived() {
    setPresence(true)
}

def departed() {
    setPresence(false)
}

def installed() {
    setVersion()
}    
def setPresence(status) {	
	if (status == false) {
		status = "not present"
	} else {
		status = "present"
	}
    
def old = device.latestValue("presence")
    
// Do nothing if already in that state
	if ( old != status ) {
        sendEvent(displayed: true,  isStateChange: true, name: "presence", value: status, descriptionText: "$device.displayName is $status")
	}
}
