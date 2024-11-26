BlackjackGameController 4328



In JavaFXapp

we have assemblerFxDoc and show.

scene not initialized in show, specifically down at BrandedApp.init, which calls setDarkTheme, which we can put later.

from BrandedApp:

        Platform.runLater { setDarkTheme(button) } 
        Platform.runLater { updateVersionMessage(fxDoc) }



In StageManager, scene is initialized.


* Put the defaults at the end in PanComponentFactory methods.



    var strategy = Strategy(config)
    var countingSystems = CountingSystems(config)


TabConfigCounting


bge:class     BlackjackGameEngine
    class     Strategy
    class     CountingSystems
bc: interface BlackjackConfiguration
fe: class     BlackjackFxControls
    class     Blackjack
    class     BrandedApp
    class     StageManager
    class     BlackjackGameController


ctls.taCountingSystems.text = defaultCountingSystems = 
ctls.cbCountingSystems = ctls.countingSystems.keyNames


        val countingSystems = ctls.bge.countingSystems


        setChoiceBoxChoices(ctls.chCountingSystems, countingSystems.keyNames)
        ctls.chCountingSystems.selectionModel.selectedIndexProperty()
            .addListener { _, _, newIndex ->
                thread {
                    val selectedIndex = newIndex.toInt()
                    countingSystems.setSystemByKeyNamePosition(selectedIndex)
                    Logz.info(String.format("Counting systems updated to %s.", countingSystems.findSelected().name))
                }
            }

        ctls.chCountingSystems.selectionModel.select(3)


Canvas@467856e0
Canvas@4cd341a7

BlackjackFxControls.felt      Canvas@467856e0
BlackjackGameController.felt  aCanvas@45e8837b

* Move BlackjackConfigDefault up to the interface.
* Blackjack BLACKJACK_STAKE_KEY
* Maybe we don't need Configurable anymore, with the decoupling of CountingSystems.
* FxDoc pmf/path united.
* Anchor bolts ?
* 
* Template source preload.

* Check functionality of error.html.
* Redirect (proxy?) panopset.com/flywheel to panopset.com/flywheel.html
* Duplicate version warnings.
* registerMonitorLizardProcessor 
* Log messages should only go to one window.
* auto complete
* logop update
* show url with env variables translated, output only text, blue font. 
* flywheel list substitution abstraction.

* nj can't be deployed to p, because p is obsolete.
-- deploy nj to p.


* load balancer?
  * merge in panopset.net nojank.com. 

* deployStatic reference obsolete in setup.md?


perhaps panopset/global.properties in config?


* remove @JvmStatic, companion Object from JavaFXapp.kt, once the FXML is gone.
* drag to file input doesn't work anymore.
* Flywheel global options tab, return character policy section.
* Skyscraper format button
* dark theme selection, global.
* NLS bundle.

Low priority:

* line remover should have option for single file - maybe integrated into flywheel.
*        - Use old BlackjackFxControls as an example.


* menu message shouldn't be global, only apply to active window.
* long response crashes, memory limitations.

* Flywheel filter button does nothing.




* mirror project, module-info.java, link, see how that works with kotlin source folder.



* Clean up/verify <pre> in https://panopset.net/dox/site/flywheel/flywheel/com.panopset.flywheel/-flywheel/main.html

* dokka can't find modules error.
* dokka cannot be found in the module graph error messages.

* for some new faq section: 
  * if your env menu is stuck, check to see if it is selected in another open window.

* Skyscraper url variables workaround not the greatest, find TODO.
* clear button underneath send, protocol dropdown.
* option to apply fonts to single window or globally
  * global preferences  

* investigate deprecated gradle build warnings in beam.
* PersistentMapFile warnings.
* Hyperlinks in about box, may have to convert from Alert: https://docs.oracle.com/javafx/2/ui_controls/hyperlink.htm.
* a.js only needed for scrambler and flywheel web versions.
* Rewrite LayoutDealer
* platform specific launchers, to get the ico or png.
* fix privacy policy broken link.
* Try JavaFX mobile options. 
* verify all documents are in ${user.home}/Documents/panopset base path.
* Denebola