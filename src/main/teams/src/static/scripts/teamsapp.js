(function () {
    'use strict';

    const recorder = new MicRecorder({
        bitRate: 128
      });
    let isRecording = false;

    // Call the initialize API first
    microsoftTeams.initialize();

    // Check the initial theme user chose and respect it
    microsoftTeams.getContext(function (context) {
        if (context && context.theme) {
            setTheme(context.theme);
        }
    });

    // Handle theme changes
    microsoftTeams.registerOnThemeChangeHandler(function (theme) {
        setTheme(theme);
    });

    // Save configuration changes
    microsoftTeams.settings.registerOnSaveHandler(function (saveEvent) {
        // Let the Microsoft Teams platform know what you want to load based on
        // what the user configured on this page
        microsoftTeams.settings.setSettings({
            contentUrl: createTabUrl(), // Mandatory parameter
            entityId: createTabUrl(), // Mandatory parameter
        });

        // Tells Microsoft Teams platform that we are done saving our settings. Microsoft Teams waits
        // for the app to call this API before it dismisses the dialog. If the wait times out, you will
        // see an error indicating that the configuration settings could not be saved.
        saveEvent.notifySuccess();
    });

    // Logic to let the user configure what they want to see in the tab being loaded
    document.addEventListener('DOMContentLoaded', function () {
        let recordButton = document.getElementById("recordButton");
        recordButton.addEventListener("click", function () {
            if (isRecording) {
                stop();
            } else {
                start();
            }
            isRecording = !isRecording;
            recordButton.innerText = isRecording ? "Stop Recording" : "Start Recording";
        });
    });

    function start() {
        recorder.start().then(() => {
            console.log("started recording");
          }).catch((e) => {
            console.error(e);
          });
    }

    function stop() {
        recorder
            .stop()
            .getMp3().then(([buffer, blob]) => {
                console.log("stopped recording");
                // do what ever you want with buffer and blob
                // Example: Create a mp3 file and play
                const file = new File(buffer, 'me-at-thevoice.mp3', {
                    type: blob.type,
                    lastModified: Date.now()
                });
                console.log(buffer);
                console.log(blob);
                console.log(file);

        
                var oReq = new XMLHttpRequest();
                oReq.open("POST", "http://localhost:8080/speech/to/text", false);
                oReq.send(file);
            
                const player = new Audio(URL.createObjectURL(file));
                player.play();
            
            }).catch((e) => {
            alert('We could not retrieve your message');
            console.log(e);
            });
    }

    // Set the desired theme
    function setTheme(theme) {
        if (theme) {
            // Possible values for theme: 'default', 'light', 'dark' and 'contrast'
            document.body.className =
                'theme-' + (theme === 'default' ? 'light' : theme);
        }
    }

    // Create the URL that Microsoft Teams will load in the tab. You can compose any URL even with query strings.
    function createTabUrl() {
        var tabChoice = document.getElementById('tabChoice');
        var selectedTab = tabChoice[tabChoice.selectedIndex].value;

        return (
            window.location.protocol +
            '//' +
            window.location.host +
            '/' +
            selectedTab
        );
    }
})();
