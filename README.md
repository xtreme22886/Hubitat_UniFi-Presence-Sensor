# Hubitat <-> UniFi Presence Sensor

**Integration for Hubitat to use UniFi wireless clients as presence sensors**
This will allow you to select from a list of known UniFi wireless clients to monitor their presence. By selecting device(s) to monitor, a script will run every 5 seconds to check the UniFi controller's current status of the monitored device(s). If the device can't be seen by any UniFi Access Point, then the device is reported offline. A device is "offline" when it is not connected to an UniFi wireless network.

You can also monitor guest connected to a UniFi hotspot by enabling the guest option.

## Getting Started

These instructions will help you get this solution implemented. I'm going to assume a working local UniFi Controller already exist on the network.

### What are we going to be setting up / installing

Here are the things that we will need to install / configure

- UniFi Bridge (REST API server to facilitate communication between Hubitat and the UniFi Controller)
  - This can be installed from [here](https://github.com/xtreme22886/SmartThings_UniFi-Presence-REST)
- UniFi Wireless Presence (Hubitat App)
- UniFi Presence Sensor (Hubitat Driver)

## Installing App and Driver
**First things first. Get the UniFi Bridge up and running before proceeding**

Setup Steps:
1. Install App
   - Copy/Paste the app code in **Advanced > App Code**
   - Make sure to **enable** `OAuth` for this app
2. Install Driver
   - Copy/Paste the driver code in **Advanced > Drivers Code**
3. Add the App
   - **Apps > Add User App**
   - Enter in information for **Bridge Address**, **UniFi Controller Address**, **UniFi Controller Username**, **UniFi Controller Password** and **UniFi Controller Site**
4. Click **Done** to push settings to the UniFi Bridge
   
**NOTE:** The **UniFi Controller Site** is **NOT** the *name* of the site but rather the *id* of the site. Take "https://x.x.x.x:8443/manage/site/default/dashboard" as an example; the *site id* is what is listed directly after **/site/**. In this cause, it would be **default**.

After setup has been completed, we can begin to monitor device(s)
1. Go back into the App
2. Click on **UniFi Client List**
3. Wait 5 seconds for the list of wireless clients to refresh and populate, click **Done** and then click on **UniFi Client List** again
4. Click on `Tap to choose clients to monitor`
5. Select the device(s) you want to monitor
6. Click **Done** all the way out of the App
7. Go to your list of *Devices* and locate the new presence device that was created
   - Feel free to rename this device as needed
   
**Notes:**
- Anytime you add/remove devices to be monitored, the App will add/remove the presence sensor device assocated with that device

### Donate
[PayPal.me](https://www.paypal.com/donate?hosted_button_id=HEZ9EPNJR2UYA&source=url)
