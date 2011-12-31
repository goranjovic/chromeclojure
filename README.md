# Description

A Clojure plugin for Google Chrome which adds an option to eval selected Clojure code to the browser's context menu. This project contains the plugin source (in directory chrome) and backend service source (everything else).

Service which evals the code is heavily based on [Raynes' tryclojure application](https://github.com/Raynes/tryclojure).

# Installation and usage 

1. Visit the [Clojure plugin page](https://chrome.google.com/webstore/detail/dkhaobmljgohccicjemmbacpooaacgeo) in Chrome Web Store.
2. Add the extension.
3. Select some Clojure code, right click and choose *Eval as Clojure*.


# Running from source

To install the server side application from source follow these steps:

1. Clone this repo.
2. `lein deps, run`
3. Open http://localhost:8081/

And to install the plugin from source:

1. Open directory `chromeclojure/chrome` and edit `manifest.json` and `chromeclojure.js` to replace chromeclojure.com with your own host name, like localhost.
2. Open Chrome browser
3. Wrench > Tools > Extensions
4. Click `Load unpacked extension`, select directory `chromeclojure/chrome` and click Open.
5. The extension should be loaded now. Try selecting some text, preferably valid Clojure code, right click it and choose *Eval as Clojure*.

# License

Copyright (C) 2011 Goran Jovic

Distributed under the Eclipse Public License, the same as Clojure.


