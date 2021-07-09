(function () {
    'use strict';

    const recorder = new MicRecorder({
        bitRate: 128
      });
    let isRecording = false;
    const questionInput = document.getElementById("question");
    const tweetButton = document.getElementById("tweet");
    const warningParagraph = document.getElementById("warn");

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
            isRecording = !isRecording;
            recordButton.innerText = isRecording ? "Stop Recording" : "Start Recording";
            if (!isRecording) {
                stop();
            } else {
                start();
            }      
        });
    });

    tweetButton.addEventListener("click", function () {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8080/tweet", false);
        xhttp.setRequestHeader("Content-Type", "application/text; charset=UTF-8");
        warningParagraph.hidden = false;
        try {
            xhttp.send(questionInput.value);
            if (xhttp.status != 200) {
                warningParagraph.innerText = "An error occurred, please try again!";
            } else {
                let response = JSON.parse(xhttp.responseText);
                response = response.map(str => "#" + str);
                response = response.join(" ");
                if (response.length == 0) {
                    warningParagraph.innerText = "No special hashtags were tweeted with your question. Just the usual ones. #aloha #collabothon2021 #coffeetalk";
                } else {
                    warningParagraph.innerText = 'These hashtags were tweeted with your question: ' + response + " #aloha #collabothon2021 #coffeetalk";
                }
            }
        } catch (err) {
            console.log(err);
            warningParagraph.innerText = "An error occurred, please try again!";
        }
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

                var request = new XMLHttpRequest();
                request.open("POST", "https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/6e80aa64-3f40-4fc0-ad31-1aef35f2faa1/v1/recognize", false)
                request.setRequestHeader("authorization", "Basic YXBpa2V5OmVtcFpqSkZ4N2RyMm91UU9YTmx3WUlDSzQ3Q1VvWVNtb1RlcWx2WWJ3emNv");
                request.setRequestHeader("Content-type", "audio/mpeg")
                request.send(file);
                var response = JSON.parse(request.responseText);

                console.log(response);

                var text = response.results[response.result_index].alternatives[0].transcript;
                questionInput.value = text;
                console.log(text);
            
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
