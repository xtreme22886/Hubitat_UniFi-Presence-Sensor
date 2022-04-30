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

// Version 2022.04.30.1

metadata {
    definition (
        name: "UniFi Presence Sensor",
        namespace: "xtreme22886",
        author: "xtreme",
        importUrl: "https://raw.githubusercontent.com/xtreme22886/Hubitat_UniFi-Presence-Sensor/main/Driver/unifi-presence-sensor.groovy"
    ) {
	capability "PresenceSensor"
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

def setPresence(status) {
    oldStatus = device.latestValue("presence")
    
    if (status == true) {
	currentStatus = "present"
        event = "arrived"
    } else {
	currentStatus = "not present"
        event = "departed"
    }

    if (oldStatus != currentStatus) {
        sendEvent(displayed: true,  isStateChange: true, name: "presence", value: currentStatus, descriptionText: "$device.displayName has $event")
    }
}
