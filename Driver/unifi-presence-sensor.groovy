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
        command "checkDriverStatus"
    }
}

def arrived() {
    setPresence(true)
}

def departed() {
    setPresence(false)
}

def setPresence(status) {
    def oldStatus = device.latestValue("presence")
    def currentStatus
    def event
    
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

def checkDriverStatus() {
    state."Driver Name" = "UniFi Presence Sensor"
    state."Driver Version" = "2022.05.03"
    state."Driver Status" = "unknown"
    
    try {
        httpGet(uri: "https://xtreme22886.github.io/Hubitat_UniFi-Presence-Sensor/Driver/version.json", contentType: "application/json") { resp ->
            switch (resp.status) {
                case 200:
                    if (resp.data."${state."Driver Name"}") {
                        if (state."Driver Version" == resp.data."${state."Driver Name"}".version) {
                            state."Driver Status" = "Up To Date"
                        }
                        if (state."Driver Version" < resp.data."${state."Driver Name"}".version) {
                            state."Driver Status" = "Update Available"
                        }
                    } else {
                        state."Driver Status" = "unknown"
                        log.error("'${state."Driver Name"}' not listed on versions page")
                    }
                    break
                default:
                    state."Driver Status" = "unknown"
                    log.error("Unable to check for '${state."Driver Name"}' driver updates")
                    break
            }
        }
    } catch (Exception e) {
        def error = e as String
        if (error.contains("Not Found")) {
            state."Driver Status" = "unknown"
            log.error("'${state."Driver Name"}' version page not found")
        } else {
            state."Driver Status" = "unknown"
            log.error("Unknown error trying to check updates for '${state."Driver Name"}'")
        }
    }
}
