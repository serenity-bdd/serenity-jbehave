## Serenity BDD JBehave change Log

### upcoming (2016/03/02 17:30 +00:00)
 
**Pull requests:**
 
- [#55](https://github.com/serenity-bdd/serenity-jbehave/pull/55) Changed loading of test classes. Now test classes can be loaded from directories and subdirectories based on root package ([@YamStranger](https://github.com/YamStranger))
- [#52](https://github.com/serenity-bdd/serenity-jbehave/pull/52) Updated serenty core to 1.1.27 ([@YamStranger](https://github.com/YamStranger))
- [#51](https://github.com/serenity-bdd/serenity-jbehave/pull/51) Updated loader of testOutcomes to use serenity core utilites ([@YamStranger](https://github.com/YamStranger))
 
**Commits:**
 
- [16d3b4e](https://github.com/serenity-bdd/serenity-jbehave/commit/16d3b4e8c9fe61a99e7e6a4f52ad9cdf97e45502) chore: updating serenty core to 1.1.27 ([@YamStranger](https://github.com/YamStranger))
- [dee66fb](https://github.com/serenity-bdd/serenity-jbehave/commit/dee66fb6ccb649a1fae7b8cbd19409c3527f9212) feat: added samples for using in tests ([@YamStranger](https://github.com/YamStranger))
- [fb7f0c9](https://github.com/serenity-bdd/serenity-jbehave/commit/fb7f0c94b83bc6cd912736d5378a7fb0a499fa0a) feat: updating loader of stories to use serenity core utilites ([@YamStranger](https://github.com/YamStranger))
- [fe2b010](https://github.com/serenity-bdd/serenity-jbehave/commit/fe2b010fb2542a70173ddc5c5f6b174be10eecac) feat: changed loading of test classes. Now classes can be loaded from more complex directory structure ([@YamStranger](https://github.com/YamStranger))
 
### v1.7.0 (2016/02/24 08:54 +00:00)
 
**Pull requests:**
 
- [#49](https://github.com/serenity-bdd/serenity-jbehave/pull/49) Tests are reorganised  to enable as more parallel tests as it possible ([@YamStranger](https://github.com/YamStranger))
- [#48](https://github.com/serenity-bdd/serenity-jbehave/pull/48) Updating logging of tests ([@YamStranger](https://github.com/YamStranger))
- [#47](https://github.com/serenity-bdd/serenity-jbehave/pull/47) Updated jbehave to 4.0.5 ([@YamStranger](https://github.com/YamStranger))
- [#46](https://github.com/serenity-bdd/serenity-jbehave/pull/46) Updated @managed tag processing, added tests ([@YamStranger](https://github.com/YamStranger))
- [#44](https://github.com/serenity-bdd/serenity-jbehave/pull/44) Updated serenity-core to 1.1.26 ([@YamStranger](https://github.com/YamStranger))
- [#41](https://github.com/serenity-bdd/serenity-jbehave/pull/41) Updated contiributing guide - explaned multiline commits and pull requests name convention ([@YamStranger](https://github.com/YamStranger))
- [#38](https://github.com/serenity-bdd/serenity-jbehave/pull/38) Updating serenity core to1.1.26-rc.1 ([@YamStranger](https://github.com/YamStranger))
- [#37](https://github.com/serenity-bdd/serenity-jbehave/pull/37) Updating serenity core to1.25 ([@YamStranger](https://github.com/YamStranger))
 
**Commits:**
 
- [0551108](https://github.com/serenity-bdd/serenity-jbehave/commit/05511080046f0817486384bb6ab62eb21034a8f5) feat: updated managed tag processing ([@YamStranger](https://github.com/YamStranger))
 
 > Now it is possible to mark as manages all scenarious in story file
 > ```
 > Narrative:
 > ...
 > Meta:
 > [@manua](https://github.com/manua)l
 > Scenario: A scenario that works and should me manual
 > Given I have an implemented JBehave scenario
 > ...
 > Scenario: A scenario that works and should me manual too
 > Given I have an implemented JBehave scenario
 > ...
- [2bc5efa](https://github.com/serenity-bdd/serenity-jbehave/commit/2bc5efa8094a10df6c79e08c95537bec57ac00b5) chore: updating logging of tests ([@YamStranger](https://github.com/YamStranger))
- [5a398de](https://github.com/serenity-bdd/serenity-jbehave/commit/5a398dea1239fbe90fbdc4bfa5c1f62937ec3bb3) feat: updated jbehave to 4.0.5 ([@YamStranger](https://github.com/YamStranger))
- [685490b](https://github.com/serenity-bdd/serenity-jbehave/commit/685490b48b194d8374064411ccf03859cf7e0303) chore: reorganisation of tests to enable as more parallel tests as it possible ([@YamStranger](https://github.com/YamStranger))
- [7092c91](https://github.com/serenity-bdd/serenity-jbehave/commit/7092c91f111a9134eacde7d3810e384e6ba507da) docs: updating contributing docs ([@YamStranger](https://github.com/YamStranger))
- [cab04ac](https://github.com/serenity-bdd/serenity-jbehave/commit/cab04ace2d742524cf04d2608513e15933a7e6e9) chore: updating serenty-core to 1.1.26 ([@YamStranger](https://github.com/YamStranger))
- [d2c30c4](https://github.com/serenity-bdd/serenity-jbehave/commit/d2c30c4ee622e24f7e7d7287013683ec1108a56c) fix: core update ([@YamStranger](https://github.com/YamStranger))
- [f061dac](https://github.com/serenity-bdd/serenity-jbehave/commit/f061dac7614aff3022bb4252ec9e2d7a7536e184) chore: serenity core updated ([@YamStranger](https://github.com/YamStranger))
 
### v1.6.0 (2016/02/05 13:11 +00:00)
 
**Pull requests:**
 
- [#36](https://github.com/serenity-bdd/serenity-jbehave/pull/36) Updating dependency for using last serenity core, fixing issue with Java 8 support ([@YamStranger](https://github.com/YamStranger))
- [#34](https://github.com/serenity-bdd/serenity-jbehave/pull/34) Updating code to close streams during reading for files ([@YamStranger](https://github.com/YamStranger))
- [#33](https://github.com/serenity-bdd/serenity-jbehave/pull/33) Build script updating ([@YamStranger](https://github.com/YamStranger))
- [#28](https://github.com/serenity-bdd/serenity-jbehave/pull/28)  upgrading groovy from 2.* to 2.4.4 ([@YamStranger](https://github.com/YamStranger))
- [#27](https://github.com/serenity-bdd/serenity-jbehave/pull/27) upgrading serenity-core from 1.1.22-rc.15 to 1.1.24 ([@YamStranger](https://github.com/YamStranger))
- [#26](https://github.com/serenity-bdd/serenity-jbehave/pull/26) Adding instructions for contributors ([@YamStranger](https://github.com/YamStranger))
- [#25](https://github.com/serenity-bdd/serenity-jbehave/pull/25) Upgrading gradle and bintray plugin version ([@YamStranger](https://github.com/YamStranger))
- [#24](https://github.com/serenity-bdd/serenity-jbehave/pull/24) Updating of serenity core version ([@YamStranger](https://github.com/YamStranger))
 
**Commits:**
 
- [1e47244](https://github.com/serenity-bdd/serenity-jbehave/commit/1e47244b7ec6bff64e81079b165e492ca4b67c74) chore: updating gitignore ([@YamStranger](https://github.com/YamStranger))
- [290e137](https://github.com/serenity-bdd/serenity-jbehave/commit/290e13751702dd56a1d5c5605e36b90d9012d30d) fix: update serenity core to rc.5, updating name of function ([@YamStranger](https://github.com/YamStranger))
- [2a3b0d9](https://github.com/serenity-bdd/serenity-jbehave/commit/2a3b0d9eddfc3a0f9fb6cf157ca9c6b4cf437527) Removed .gitattributes to enable build pipeline on SnapCI ([@wakaleo](https://github.com/wakaleo))
- [435e524](https://github.com/serenity-bdd/serenity-jbehave/commit/435e524598b2549b9693629dba612d39e196e696) chore: updated wrapper, and build publishing libs ([@YamStranger](https://github.com/YamStranger))
- [4743a4b](https://github.com/serenity-bdd/serenity-jbehave/commit/4743a4bf121dd108ec6136f15f010dd5f8b1ad04) chore: update of gradle version from 2.5 to 2.10 ([@YamStranger](https://github.com/YamStranger))
- [609960b](https://github.com/serenity-bdd/serenity-jbehave/commit/609960bbbf3b2bc1c0bae0bc499ba883736f44dc) Updated gitignore file ([@wakaleo](https://github.com/wakaleo))
- [6631b89](https://github.com/serenity-bdd/serenity-jbehave/commit/6631b898dfa5716174a409b893b5caf868bf7a7f) fix: updating class finder to close opened streams ([@YamStranger](https://github.com/YamStranger))
- [74abee4](https://github.com/serenity-bdd/serenity-jbehave/commit/74abee4540e9d87186df7af6d29f6c6d549c8668) docs: adding instructions for contributors ([@YamStranger](https://github.com/YamStranger))
- [7c6377c](https://github.com/serenity-bdd/serenity-jbehave/commit/7c6377c9827b862a6ed16c60935787d52c14705c) chore: updated wrapper, and build publishing libs ([@YamStranger](https://github.com/YamStranger))
- [8c05583](https://github.com/serenity-bdd/serenity-jbehave/commit/8c055831100b0fe5f66a2d6120890d4c2a84140d) fix: issue with java 8 support, commons-collection for selenium 2.50, serenity-core to rc.4 ([@YamStranger](https://github.com/YamStranger))
- [99c20dc](https://github.com/serenity-bdd/serenity-jbehave/commit/99c20dc2f4863dc05d0bf10d5d6c4bc3329c3407) chore: updated gradle version from 2.1 to 2.10 and bintray plugin from 0.6 to 1.5 ([@YamStranger](https://github.com/YamStranger))
- [c345684](https://github.com/serenity-bdd/serenity-jbehave/commit/c34568471156c04fa20baa116209759c3d0b2fb6) chore: upgrade serenty-core from 1.1.22-rc.15 to 1.1.24 ([@YamStranger](https://github.com/YamStranger))
- [c9972bc](https://github.com/serenity-bdd/serenity-jbehave/commit/c9972bc01bf4e57f76b0da74903da359e65fe0e8) chore: update of gradle version from 2.5 to 2.10 ([@YamStranger](https://github.com/YamStranger))
- [d83ef3a](https://github.com/serenity-bdd/serenity-jbehave/commit/d83ef3a036aa23ed7c973c31cbc2973fee7d890f) chore: updated version of serenityCore from rc.11 to rc.15 ([@YamStranger](https://github.com/YamStranger))
- [e72435a](https://github.com/serenity-bdd/serenity-jbehave/commit/e72435a45937bc82348e53ca8c7b349ab53cc82f) chore: groovy upgrade from 2.* to 2.4.4 ([@YamStranger](https://github.com/YamStranger))
- [f452013](https://github.com/serenity-bdd/serenity-jbehave/commit/f4520133e128edaa8de9ff8765e3a1f798365b09) Updated core version ([@wakaleo](https://github.com/wakaleo))
 
### v1.5.0 (2015/12/09 07:54 +00:00)
 
**Pull requests:**
 
- [#21](https://github.com/serenity-bdd/serenity-jbehave/pull/21) Pull request for updating version of serenity-core to 22-rc.10 ([@YamStranger](https://github.com/YamStranger))
 
**Commits:**
 
- [490d9ca](https://github.com/serenity-bdd/serenity-jbehave/commit/490d9ca1599486d601a3d31ee206fe1c48a404f1) 18_issue and 19_issue: updated version of libs ([@YamStranger](https://github.com/YamStranger))
- [5ea2cfd](https://github.com/serenity-bdd/serenity-jbehave/commit/5ea2cfdb673612d8d7c83332d12019963c3f1a32) Updated dependencies ([@wakaleo](https://github.com/wakaleo))
- [c89fd90](https://github.com/serenity-bdd/serenity-jbehave/commit/c89fd9093fda86dd02f7ed97cbd0b0545c0396bb) 19_issue updated gradle version ([@YamStranger](https://github.com/YamStranger))
- [d8ea2c1](https://github.com/serenity-bdd/serenity-jbehave/commit/d8ea2c1dac177f4499ce58bf4dd9069b7431fffb) 19_issue: updated dependecies, updated stories to use chrome instead html_unit ([@YamStranger](https://github.com/YamStranger))
 
### v1.2.0 (2015/08/02 17:06 +00:00)
 
 
**Commits:**
 
- [4d33134](https://github.com/serenity-bdd/serenity-jbehave/commit/4d33134b0d2cd5bdeda6bba6d972ebc6115790f5) Updated to core 1.2.3, and refactored the logic that closes the browsers at the end of a story ([@wakaleo](https://github.com/wakaleo))
 
### v1.1.0 (2015/07/26 02:14 +00:00)
 
 
**Commits:**
 
- [c247784](https://github.com/serenity-bdd/serenity-jbehave/commit/c247784402e8250c3c6828f4fbdb16933a5e4320) Added support for manual tests ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.24 (2015/07/25 05:15 +00:00)
 
 
**Commits:**
 
- [2822299](https://github.com/serenity-bdd/serenity-jbehave/commit/28222990f9375ce7d0939c77d32f7ae44a8aea82) Fixed issue that resulted in the browser not closing after tests. ([@wakaleo](https://github.com/wakaleo))
- [b06e3a7](https://github.com/serenity-bdd/serenity-jbehave/commit/b06e3a7c22920be6e75d926ad42b7c1417f17126) Update core version ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.23 (2015/06/02 01:57 +00:00)
 
 
**Commits:**
 
- [28a4e9b](https://github.com/serenity-bdd/serenity-jbehave/commit/28a4e9b482bffa2258b10501cadedc5ce3851096) Updated serenity core and JBehave dependencies ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.22 (2015/06/01 08:02 +00:00)
 
 
**Commits:**
 
- [396ca40](https://github.com/serenity-bdd/serenity-jbehave/commit/396ca404cc013c9f040581c1b2aa472b1d228eb9) Updated dependencies to serenity-core 1.0.48 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.21 (2015/05/01 23:45 +00:00)
 
 
**Commits:**
 
- [22ded62](https://github.com/serenity-bdd/serenity-jbehave/commit/22ded620cdab80906fe6e441c8c9f59912d1d650) Use the default embedded story timeout rather than defaulting to 300. ([@wakaleo](https://github.com/wakaleo))
- [9386b71](https://github.com/serenity-bdd/serenity-jbehave/commit/9386b7194c83c9bf540d4579d72d7e1b252e8a80) Updated core version to 1.0.47 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.20 (2015/03/26 11:18 +00:00)
 
 
**Commits:**
 
- [a204a0c](https://github.com/serenity-bdd/serenity-jbehave/commit/a204a0c18253550f5d3f78a6e8f240014d69ba4d) fix: Fixed issue #10: Unable to run serenity from Junit ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.19 (2015/03/03 04:42 +00:00)
 
 
**Commits:**
 
- [6988e73](https://github.com/serenity-bdd/serenity-jbehave/commit/6988e73f39608994cf98c8790713295bb11c49d5) Updated to core 1.0.37 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.18 (2015/03/02 23:32 +00:00)
 
**Pull requests:**
 
- [#9](https://github.com/serenity-bdd/serenity-jbehave/pull/9) Fix data provided to the example ([@vaha](https://github.com/vaha))
 
**Commits:**
 
- [527abaf](https://github.com/serenity-bdd/serenity-jbehave/commit/527abafcf8aa5e5aa2d8316efc89b7ec6c3ba585) deps: Updated core dependency to 1.0.35 ([@wakaleo](https://github.com/wakaleo))
- [8e3b011](https://github.com/serenity-bdd/serenity-jbehave/commit/8e3b01159d53c9ee0fb7ef742f14af76811c4d15) Updated to core 1.0.36 ([@wakaleo](https://github.com/wakaleo))
- [ebaa789](https://github.com/serenity-bdd/serenity-jbehave/commit/ebaa78934c553992ef9a63f1541d3b1cdd14b49f) Fix data provided to the example ([@vaha](https://github.com/vaha))
 
### v1.0.17 (2015/02/20 06:09 +00:00)
 
 
**Commits:**
 
- [1d76cef](https://github.com/serenity-bdd/serenity-jbehave/commit/1d76cef46142314c51111b4175e2fb1da958fc19) feat: Updated core dependency to 1.0.34 ([@wakaleo](https://github.com/wakaleo))
- [a485107](https://github.com/serenity-bdd/serenity-jbehave/commit/a48510741aa4ebb31bd871c2d42998b1e1b040f3) feat: Updated core dependency to 1.0.34 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.16 (2015/02/18 23:59 +00:00)
 
 
**Commits:**
 
- [4bdebee](https://github.com/serenity-bdd/serenity-jbehave/commit/4bdebee528aa83be93e10f904a513cd96d7dc011) fix: Handle reporting on scenario and story level metatags correctly. ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.15 (2015/02/14 00:05 +00:00)
 
 
**Commits:**
 
- [699a0c4](https://github.com/serenity-bdd/serenity-jbehave/commit/699a0c42a6ecfe05119d0a08542d22b1a638a04b) Serenity steps that fail will now trigger a failing step in JBehave immediately, which speeds up the tests and provides better reporting in the IDE. ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.13 (2015/02/12 11:07 +00:00)
 
 
**Commits:**
 
- [0f41ea2](https://github.com/serenity-bdd/serenity-jbehave/commit/0f41ea25218d06f8e71b1cd3046c6b692a88156c) Updated core dependencies to 1.0.30 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.12 (2015/01/30 12:15 +00:00)
 
 
**Commits:**
 
- [7098135](https://github.com/serenity-bdd/serenity-jbehave/commit/7098135a96aca4e0f131fac44afa27d4e7378d54) Updated core dependencies ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.11 (2015/01/18 08:35 +00:00)
 
 
**Commits:**
 
- [8782905](https://github.com/serenity-bdd/serenity-jbehave/commit/87829058c8854fa5a8dd4c4ab067efbe39a81cee) Updated dependencies to 1.0.24 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.10 (2014/12/22 22:52 +00:00)
 
**Pull requests:**
 
- [#8](https://github.com/serenity-bdd/serenity-jbehave/pull/8) Update to 1.0.23 for core and replace serenity_bdd with serenitybdd pack... ([@mikezx6r](https://github.com/mikezx6r))
- [#7](https://github.com/serenity-bdd/serenity-jbehave/pull/7) Rename serenity_bdd to serenitybdd ([@mikezx6r](https://github.com/mikezx6r))
- [#6](https://github.com/serenity-bdd/serenity-jbehave/pull/6) Final classes moved to serenity_bdd and Serenity prefix names ([@mikezx6r](https://github.com/mikezx6r))
- [#5](https://github.com/serenity-bdd/serenity-jbehave/pull/5) Move all classes to serenity_bdd namespace ([@mikezx6r](https://github.com/mikezx6r))
- [#4](https://github.com/serenity-bdd/serenity-jbehave/pull/4) More migration to Serenity namespace ([@mikezx6r](https://github.com/mikezx6r))
- [#3](https://github.com/serenity-bdd/serenity-jbehave/pull/3) Fix issue with running reports and old version of guava being version us... ([@mikezx6r](https://github.com/mikezx6r))
 
**Commits:**
 
- [1fe1132](https://github.com/serenity-bdd/serenity-jbehave/commit/1fe11323b56ed57e08bf5a86e1aa7ef9f647548c) Move almost everything into serenity_bdd namespace and rename helper classes ([@mikezx6r](https://github.com/mikezx6r))
- [33e3d87](https://github.com/serenity-bdd/serenity-jbehave/commit/33e3d87d764316af2a1c26b8b2746f0098456a85) Update to 1.0.23 for core and replace serenity_bdd with serenitybdd package ([@mikezx6r](https://github.com/mikezx6r))
- [4a52480](https://github.com/serenity-bdd/serenity-jbehave/commit/4a52480cde7bb8fe944316438dabd203b8240509) Rename Step related classes to Serenity prefix ([@mikezx6r](https://github.com/mikezx6r))
- [5388cfc](https://github.com/serenity-bdd/serenity-jbehave/commit/5388cfc9ce2032aa1c3e7bbd7a3a0cf4c5bd611a) Move and rename ThucydidesReportingRunner to new namespace ([@mikezx6r](https://github.com/mikezx6r))
- [6df9184](https://github.com/serenity-bdd/serenity-jbehave/commit/6df9184d513752a91d161a371aa478554485b3a5) Move Extract class to serenity_bdd package ([@mikezx6r](https://github.com/mikezx6r))
- [787097c](https://github.com/serenity-bdd/serenity-jbehave/commit/787097cff4394b9a6419597ea01504a8bb5563c2) Move Metafilter to serenity_bdd package while keeping deprecated variation in thucydides package ([@mikezx6r](https://github.com/mikezx6r))
 
 > serenity_bdd annotation takes precedence over thucydides if both are defined
- [80a2181](https://github.com/serenity-bdd/serenity-jbehave/commit/80a2181c141234c1bb3c73148cbc5a98cde52945) Fix issue with running reports and old version of guava being version used ([@mikezx6r](https://github.com/mikezx6r))
 
 > If using Maven, and only define a dependency on serenity:serenity-jbehave, Maven
 > appears to resolve guava to the old version contained in reflections (0.11.0).
 > This version of Guava does not contain many of the methods required to complete reporting.
 > Adding this exclusion has all the tests pass, AND my FT suite completes successfully pointing at a
 > local SNAPSHOT version of this project.
- [88231c0](https://github.com/serenity-bdd/serenity-jbehave/commit/88231c007829e6f507ef53010d2f99af7f0093a6) Rename additional items to Serenity ([@mikezx6r](https://github.com/mikezx6r))
- [b91449d](https://github.com/serenity-bdd/serenity-jbehave/commit/b91449deaa0a99e5b0c4aee1dd7deb32cba10a15) Move converters to new serenity_bdd namespace ([@mikezx6r](https://github.com/mikezx6r))
- [becc449](https://github.com/serenity-bdd/serenity-jbehave/commit/becc449909bf67bb396fa4b29d571df02ca17238) Rename serenity_bdd to serenitybdd ([@mikezx6r](https://github.com/mikezx6r))
- [f403139](https://github.com/serenity-bdd/serenity-jbehave/commit/f403139e7ac67d38a6257a5f6514dde48797bac2) Upgrade to Serenity-core 1.0.21 ([@mikezx6r](https://github.com/mikezx6r))
 
### v1.0.9 (2014/12/09 02:16 +00:00)
 
**Pull requests:**
 
- [#2](https://github.com/serenity-bdd/serenity-jbehave/pull/2) Continue effort to migrate to new namespace ([@mikezx6r](https://github.com/mikezx6r))
- [#1](https://github.com/serenity-bdd/serenity-jbehave/pull/1) [build] Use gradle-git for controlling version and tagging ([@mikezx6r](https://github.com/mikezx6r))
 
**Commits:**
 
- [0ace75f](https://github.com/serenity-bdd/serenity-jbehave/commit/0ace75f5b790728696c3a2b1f9ea393e0792b888) Enable better logging on command-line when running tests ([@mikezx6r](https://github.com/mikezx6r))
- [0cc560a](https://github.com/serenity-bdd/serenity-jbehave/commit/0cc560a65849051b18109f1dd4dcf98b0b4c6806) [build] Use gradle-git for controlling version and tagging ([@mikezx6r](https://github.com/mikezx6r))
- [159c324](https://github.com/serenity-bdd/serenity-jbehave/commit/159c324f87c9d4f116a81826c03fc2eb944166db) Updated to Serenity 1.0.16 ([@wakaleo](https://github.com/wakaleo))
- [8ea0651](https://github.com/serenity-bdd/serenity-jbehave/commit/8ea0651db2fd232a8dfedd6e938a05fb9b95cc3b) Upgrade core dependency to 1.0.10 ([@mikezx6r](https://github.com/mikezx6r))
- [97cd16c](https://github.com/serenity-bdd/serenity-jbehave/commit/97cd16ca75dc5707bc86e22b6ddb6f880fd1df8d) Rename method and provide method under original name for backwards compatibility ([@mikezx6r](https://github.com/mikezx6r))
- [b8359eb](https://github.com/serenity-bdd/serenity-jbehave/commit/b8359eb70536750a9fb33d5216c5e401074c680e) Move SerenityStories to serenity_bdd namespace ([@mikezx6r](https://github.com/mikezx6r))
- [c4fc00b](https://github.com/serenity-bdd/serenity-jbehave/commit/c4fc00b09c15377dabb2b2d91923df44cd596276) Move SerenityStory to serenity_bdd namespace ([@mikezx6r](https://github.com/mikezx6r))
- [eace7b9](https://github.com/serenity-bdd/serenity-jbehave/commit/eace7b9395692c6680929a05a2beb6b57b139b1c) Upgrade to latest serenity-core ([@mikezx6r](https://github.com/mikezx6r))
 
### v1.0.8 (2014/11/23 11:47 +00:00)
 
 
**Commits:**
 
- [2516671](https://github.com/serenity-bdd/serenity-jbehave/commit/2516671432f62e03d7f2e964fe08cc49dd7496f0) Updated core dependencies ([@wakaleo](https://github.com/wakaleo))
- [3220cef](https://github.com/serenity-bdd/serenity-jbehave/commit/3220cef63d44bc806070a130888d742c1684b77c) Added support for using JUnit Assumes inside Serenity steps to force a test to be reported as "ignored" if the assumption fails. ([@wakaleo](https://github.com/wakaleo))
- [fd03ac7](https://github.com/serenity-bdd/serenity-jbehave/commit/fd03ac70f9fb855b28b209d2d6033c865994c50d) Added support for using JUnit Assumes inside Serenity steps to force a test to be reported as "ignored" if the assumption fails. ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.7 (2014/11/14 07:31 +00:00)
 
 
**Commits:**
 
- [0818254](https://github.com/serenity-bdd/serenity-jbehave/commit/0818254b3e87bbd2ab212a99329e165d49c45539) Fixing pom for maven release ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.6 (2014/11/14 07:11 +00:00)
 
 
**Commits:**
 
- [bf29f34](https://github.com/serenity-bdd/serenity-jbehave/commit/bf29f342efa63649dc5f7a4fe78fbb5ca49ce690) Upgraded to core 1.0.6 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.5 (2014/11/13 17:28 +00:00)
 
 
**Commits:**
 
- [dd49a29](https://github.com/serenity-bdd/serenity-jbehave/commit/dd49a292d9d71ea6d21c898d22f0f1b9c19d5677) Added SerenityStory and SerenityStories ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.3 (2014/11/12 02:41 +00:00)
 
 
**Commits:**
 
- [1b64bba](https://github.com/serenity-bdd/serenity-jbehave/commit/1b64bba3fbb51ca4ce00d57522cb11d2c89ab925) Fixed dependencies ([@wakaleo](https://github.com/wakaleo))
- [524619d](https://github.com/serenity-bdd/serenity-jbehave/commit/524619d03e0d7c0ad88eea744d308de5b0ba85b8) Streamlined git tagging ([@wakaleo](https://github.com/wakaleo))
- [5e6163c](https://github.com/serenity-bdd/serenity-jbehave/commit/5e6163c5316619e84a0309098b843fd98c482a77) Updated to serenity-core 1.0.4 ([@wakaleo](https://github.com/wakaleo))
- [7868c72](https://github.com/serenity-bdd/serenity-jbehave/commit/7868c72980f92f4b618606119011af6c30c9a727) Tag version in separate step to uploading to bintray ([@wakaleo](https://github.com/wakaleo))
- [8c1073f](https://github.com/serenity-bdd/serenity-jbehave/commit/8c1073f9bb4db04febc846ba7c056c247f619e9c) Getting the release process working ([@wakaleo](https://github.com/wakaleo))
- [efaffcb](https://github.com/serenity-bdd/serenity-jbehave/commit/efaffcbca97c71724a73787f5d7bd7c64b3263e7) Updating to core 1.0.4 ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.2-SNAPSHOT (2014/11/06 11:24 +00:00)
 
 
**Commits:**
 
- [6300d1c](https://github.com/serenity-bdd/serenity-jbehave/commit/6300d1cc0a060a924a0e49a261f4a85dd9511deb) tweaking build number process ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.1 (2014/11/06 11:12 +00:00)
 
 
**Commits:**
 
- [1871981](https://github.com/serenity-bdd/serenity-jbehave/commit/187198165ad92bb8bd96d9ac532b38b0c0136fcc) Trying to fix a CI/CD issue ([@wakaleo](https://github.com/wakaleo))
- [643e83b](https://github.com/serenity-bdd/serenity-jbehave/commit/643e83b2a5dc0f1492641f5738271039e871890a) Trying to fix a CI/CD issue ([@wakaleo](https://github.com/wakaleo))
- [6f2b391](https://github.com/serenity-bdd/serenity-jbehave/commit/6f2b3910af781f26adfd49175642bcee059ded46) Fixed a bug in the version numbers ([@wakaleo](https://github.com/wakaleo))
- [cfac5e0](https://github.com/serenity-bdd/serenity-jbehave/commit/cfac5e063a2ec5bb91294b213580ff6b38e01168) Push tag automatically when a new release is tagged ([@wakaleo](https://github.com/wakaleo))
 
### v1.0.0 (2014/11/06 10:26 +00:00)
 
 
**Commits:**
 
- [2081cb8](https://github.com/serenity-bdd/serenity-jbehave/commit/2081cb874d5ea02d8aea294b5afa58c452dc5f55) New version numbering system ([@wakaleo](https://github.com/wakaleo))
 
### 1.0.15 (2014/11/06 04:26 +00:00)
 
 
**Commits:**
 
- [07c5c79](https://github.com/serenity-bdd/serenity-jbehave/commit/07c5c790dba97775cd222ff2bcfc5fe9e9ff02be) Refactoring tests and adding a gradle build ([@wakaleo](https://github.com/wakaleo))
- [0c5d2c1](https://github.com/serenity-bdd/serenity-jbehave/commit/0c5d2c16e7a16f142f6177f8eb6941111606b972) Look for artifacts in maven local as well as jcenter ([@wakaleo](https://github.com/wakaleo))
- [2706a33](https://github.com/serenity-bdd/serenity-jbehave/commit/2706a33028a618c9c0d77751fa0540b6db2da490) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [474c1b7](https://github.com/serenity-bdd/serenity-jbehave/commit/474c1b71a38a0a75822db03d53ebb1c419be56f1) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [51a7b48](https://github.com/serenity-bdd/serenity-jbehave/commit/51a7b48e997e75624c83272684af9a55efa7db67) Fine-tuning the gradle wrapper ([@wakaleo](https://github.com/wakaleo))
- [610c978](https://github.com/serenity-bdd/serenity-jbehave/commit/610c978e6ef253670fda454368aadc0b959620a9) Fine-tuning release process ([@wakaleo](https://github.com/wakaleo))
- [64b3cf0](https://github.com/serenity-bdd/serenity-jbehave/commit/64b3cf05ae930041cde59f280c3a1eda44406a3a) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [771816f](https://github.com/serenity-bdd/serenity-jbehave/commit/771816f5202803b1503b7eceedf43fce98e12e44) Fixing BinTray ([@wakaleo](https://github.com/wakaleo))
- [8572f8a](https://github.com/serenity-bdd/serenity-jbehave/commit/8572f8a4fa6898f79fbbfc9b8d14a64d99bd4f56) Update build process to Serenity ([@wakaleo](https://github.com/wakaleo))
- [9346253](https://github.com/serenity-bdd/serenity-jbehave/commit/93462535be2b1d6d03462fbf3f54f93af6ca2d46) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [a2e8856](https://github.com/serenity-bdd/serenity-jbehave/commit/a2e8856e80b3488b4f2e3fa0af05cda5c88e92eb) Added CodeShip logo ([@wakaleo](https://github.com/wakaleo))
- [a9b6ee8](https://github.com/serenity-bdd/serenity-jbehave/commit/a9b6ee89b0d12a6d2db46958b25a58b9f4442673) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [b72506e](https://github.com/serenity-bdd/serenity-jbehave/commit/b72506ef77a37ecaaeba78257ec8ed2dac6384e2) Let the bintray keys be defined by the build server ([@wakaleo](https://github.com/wakaleo))
- [bb601f3](https://github.com/serenity-bdd/serenity-jbehave/commit/bb601f3e3de0803ef7b6afc291ddd8a1b7438495) Fixing Bintray integration ([@wakaleo](https://github.com/wakaleo))
- [bee1167](https://github.com/serenity-bdd/serenity-jbehave/commit/bee1167d0eccfbca2895029bcf3da40691cf5e82) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [c1b337e](https://github.com/serenity-bdd/serenity-jbehave/commit/c1b337eda5cebc3ee4ce93eaed5b9fa1c0696768) Switched to Serenity ([@wakaleo](https://github.com/wakaleo))
- [c1bacfa](https://github.com/serenity-bdd/serenity-jbehave/commit/c1bacfa5e44219aa9baedf0f3d4c875fc8dc59b4) simplified the Gradle build ([@wakaleo](https://github.com/wakaleo))
- [cffd36d](https://github.com/serenity-bdd/serenity-jbehave/commit/cffd36d95babfc4066dca46375748fa68484b16f) Fixing BinTray integration ([@wakaleo](https://github.com/wakaleo))
- [e5a5049](https://github.com/serenity-bdd/serenity-jbehave/commit/e5a5049e38a490085d18116fa6d73d8ea953a2a2) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [e7e9950](https://github.com/serenity-bdd/serenity-jbehave/commit/e7e995022416bf75b343280703d2049ec3eff0c6) Fixing Bintray integration ([@wakaleo](https://github.com/wakaleo))
- [ee65b89](https://github.com/serenity-bdd/serenity-jbehave/commit/ee65b898039860df6f277e7dd44013084ae8a13c) Got the Bintray integration working properly ([@wakaleo](https://github.com/wakaleo))
- [ffd796b](https://github.com/serenity-bdd/serenity-jbehave/commit/ffd796bcb4378b5cfe05035ef9bbbc37845b2e57) Added initial BinTray deployment ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.275 (2014/10/22 11:43 +00:00)
 
 
**Commits:**
 
- [04406de](https://github.com/serenity-bdd/serenity-jbehave/commit/04406de931664c752c97442a217cf8ca9fd3d761) Added some tests illustrating the configuration of variable sharing in step definition classes ([@wakaleo](https://github.com/wakaleo))
- [22bc843](https://github.com/serenity-bdd/serenity-jbehave/commit/22bc843098d15606ea7cfd45db9adf646b09b879) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [48e0c7a](https://github.com/serenity-bdd/serenity-jbehave/commit/48e0c7a70929cfe155091ae52edebea51fe54518) Experimental Codeship integration ([@wakaleo](https://github.com/wakaleo))
- [8809c2f](https://github.com/serenity-bdd/serenity-jbehave/commit/8809c2f11cf84932be56ffe8d95fe1bdc86969bb) Version updates ([@wakaleo](https://github.com/wakaleo))
- [e179db5](https://github.com/serenity-bdd/serenity-jbehave/commit/e179db524e8d735723c7f4fd10b1c0bb4f8e2542) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.275 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.273 (2014/09/29 16:53 +00:00)
 
 
**Commits:**
 
- [2c5bb9c](https://github.com/serenity-bdd/serenity-jbehave/commit/2c5bb9c7ffc5fb3a00e76c1c7a8fcd25b4c21918) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [9e61cae](https://github.com/serenity-bdd/serenity-jbehave/commit/9e61cae7040c52b18a4f3f763ad158b84729d61e) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.273 ([@wakaleo](https://github.com/wakaleo))
- [d5b9f10](https://github.com/serenity-bdd/serenity-jbehave/commit/d5b9f106c0068f523b4ed9d7ecb47943d810a6f3) Fixed an issue with the narrative reporting ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.272 (2014/09/28 01:05 +00:00)
 
 
**Commits:**
 
- [25fdc4e](https://github.com/serenity-bdd/serenity-jbehave/commit/25fdc4e9d0c6c9b621774af202b543e21b6c44ad) fixed a bug in the story narrative reporting ([@wakaleo](https://github.com/wakaleo))
- [4b88534](https://github.com/serenity-bdd/serenity-jbehave/commit/4b88534caf2e73df1ec814c91bb06b2f2fea28bf) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.272 ([@wakaleo](https://github.com/wakaleo))
- [6b20302](https://github.com/serenity-bdd/serenity-jbehave/commit/6b203022546ead9e737c050e67a7b82c6f9d161d) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.271 (2014/09/22 00:43 +00:00)
 
 
**Commits:**
 
- [50ee274](https://github.com/serenity-bdd/serenity-jbehave/commit/50ee2742956ef363e542d76fde50c382ad938031) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [aad179c](https://github.com/serenity-bdd/serenity-jbehave/commit/aad179c3efffd217cbb83e65d4aea598ba3d959c) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.271 ([@wakaleo](https://github.com/wakaleo))
- [f69ca01](https://github.com/serenity-bdd/serenity-jbehave/commit/f69ca015e18e16f70fdb0a800a31bf0521cc2147) Minor change to support Gradle builds. ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.268 (2014/08/20 08:28 +00:00)
 
 
**Commits:**
 
- [21263c1](https://github.com/serenity-bdd/serenity-jbehave/commit/21263c182a687fd41747261ab1af3ff06a200e25) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [46bccc1](https://github.com/serenity-bdd/serenity-jbehave/commit/46bccc109a015f3cdcece636f9979717b1d98a1e) Better support for @skip and @wip tags ([@wakaleo](https://github.com/wakaleo))
- [6fc8c5b](https://github.com/serenity-bdd/serenity-jbehave/commit/6fc8c5b35ed18b1a033856af7cdd2d4ff07156ea) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.268 ([@wakaleo](https://github.com/wakaleo))
- [765a694](https://github.com/serenity-bdd/serenity-jbehave/commit/765a694e6e316c808a336b79059b9fe3e711ad93) Prepare release ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.264 (2014/07/24 05:52 +00:00)
 
 
**Commits:**
 
- [1d26065](https://github.com/serenity-bdd/serenity-jbehave/commit/1d26065504fdce8857801dfcb7d24397dd0aba1b) Fixed THUCYDIDES-160 ([@wakaleo](https://github.com/wakaleo))
- [3dd8f69](https://github.com/serenity-bdd/serenity-jbehave/commit/3dd8f6970f40d1ab3bedfe99ff6a147aee272c24) Fixed issue with scenarios that were skipped due to a metafilter setting appearing as passing ([@wakaleo](https://github.com/wakaleo))
- [511166a](https://github.com/serenity-bdd/serenity-jbehave/commit/511166a57f8333378782ebb2f774052dd123fce7) THUCYDIDES-160: add ClassFinder tests for classes located in JAR files ([@cvmocanu](https://github.com/cvmocanu))
 
 > When they are run from an IDE like IntelliJ, the tests that use a maven
 > dependency do not test finding classes that are packaged in a JAR file.
 > This is because IntelliJ will setup the dependencies so that one module
 > depends on another module&#39;s .class files directly. This way, the IDE
 > does not have to waste time to package classes to jar files, and the
 > development is faster.
- [55c0479](https://github.com/serenity-bdd/serenity-jbehave/commit/55c0479e5df7d767f148dd5e8dfc580dbad1887b) THUCYDIDES-160: fix broken test ([@cvmocanu](https://github.com/cvmocanu))
 
 > The class SomeBoilerplateSteps was not annotated with [@Given, so a tes](https://github.com/Given, so a tes)t
 > was failing. To make sure the test is fixed, please change the pom to
 > depend on the latest version of thucydides-sample-alternative-resources.
- [7325839](https://github.com/serenity-bdd/serenity-jbehave/commit/7325839524da73ce6148a94d48c052fd005c1a21) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.264 ([@wakaleo](https://github.com/wakaleo))
- [9f3663d](https://github.com/serenity-bdd/serenity-jbehave/commit/9f3663dd4a851e1bc3203ed2702cce245640827c) THUCYDIDES-160: fix issue ([@cvmocanu](https://github.com/cvmocanu))
 
 > This commit fixes the loading of classes from jar files or file-system
 > directories, when the path contains characters which need to be
 > URL-encoded (e.g. the space character).
- [b626344](https://github.com/serenity-bdd/serenity-jbehave/commit/b6263441fb34f503ee87f35d709c827bcf4ec7ca) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [d366a56](https://github.com/serenity-bdd/serenity-jbehave/commit/d366a56a3a1bdcb97a89e48b8acbc3cc7d1cebe8) Updated dependencies ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.262 (2014/07/13 23:15 +00:00)
 
 
**Commits:**
 
- [0f2e1df](https://github.com/serenity-bdd/serenity-jbehave/commit/0f2e1dfa2c67dce19dd0365387f86d6f9c20d61b) Updated dependencies ([@wakaleo](https://github.com/wakaleo))
- [2552a8c](https://github.com/serenity-bdd/serenity-jbehave/commit/2552a8c973ff930770a89aed4706db9f8cb7bac8) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.262 ([@wakaleo](https://github.com/wakaleo))
- [750020f](https://github.com/serenity-bdd/serenity-jbehave/commit/750020f714cb1d50a53c8587fbbc240a829bb15e) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.260 (2014/07/10 03:58 +00:00)
 
 
**Commits:**
 
- [001ff49](https://github.com/serenity-bdd/serenity-jbehave/commit/001ff49d22f0c607691976f0c55de8a1a188295a) Preparing release ([@wakaleo](https://github.com/wakaleo))
- [13d34f7](https://github.com/serenity-bdd/serenity-jbehave/commit/13d34f740bbed5be7cf24d756bd1523ebfcd4dc1) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [3c469b8](https://github.com/serenity-bdd/serenity-jbehave/commit/3c469b8f284121726765026706aed5d0fece111c) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.260 ([@wakaleo](https://github.com/wakaleo))
- [42b2dc1](https://github.com/serenity-bdd/serenity-jbehave/commit/42b2dc162022cba3ae3ad6668aaffdb29b7d9997) Minor refactoring. ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.256 (2014/07/07 01:56 +00:00)
 
 
**Commits:**
 
- [19c6411](https://github.com/serenity-bdd/serenity-jbehave/commit/19c64113bb0ab38c6052c2644d7f333e264133d2) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.256 ([@wakaleo](https://github.com/wakaleo))
- [5672a5c](https://github.com/serenity-bdd/serenity-jbehave/commit/5672a5cffa546232baf1f33cbaad8c27acb59089) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [7cb20f2](https://github.com/serenity-bdd/serenity-jbehave/commit/7cb20f2da05263729caf9bd1985115c4e86d9c5a) Updated versions ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.255 (2014/06/30 13:27 +00:00)
 
 
**Commits:**
 
- [1dacbee](https://github.com/serenity-bdd/serenity-jbehave/commit/1dacbeec3357595f9eec565c1512372d330191bc) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [773cdd9](https://github.com/serenity-bdd/serenity-jbehave/commit/773cdd920c0be489d1cd36892c025269cfd4a275) fix THUCYDIDES-160 (no candidate steps found when path contains spaces)
 
 > The expression &quot;new File(url.getFile())&quot; is buggy because the result of
 > &quot;url.getFile()&quot; contains url-encoded characters (like &quot;%20&quot; instead of
 > space).
 > The fix is to use &quot;new File(url.toURI())&quot; instead, since that takes care
 > of decoding url-encoded characters.
 > See also: http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4466485
- [a8c6a94](https://github.com/serenity-bdd/serenity-jbehave/commit/a8c6a946351fc3907058cd859e084e29a4c3c344) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.255 ([@wakaleo](https://github.com/wakaleo))
- [fa36401](https://github.com/serenity-bdd/serenity-jbehave/commit/fa364017ef5fc263f9066585151ca9e7fd876b8d) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.250 (2014/06/18 01:47 +00:00)
 
 
**Commits:**
 
- [06c981b](https://github.com/serenity-bdd/serenity-jbehave/commit/06c981be39863a046a113bff35790fd9a552c5eb) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [2fce299](https://github.com/serenity-bdd/serenity-jbehave/commit/2fce29903d7b7cd563982bffda9470c1d7718f8d) Use phantomjs ([@wakaleo](https://github.com/wakaleo))
- [53d53a3](https://github.com/serenity-bdd/serenity-jbehave/commit/53d53a3fe78955b7a77eff26abbcb9ee381c15a9) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.250 ([@wakaleo](https://github.com/wakaleo))
- [8a3b85a](https://github.com/serenity-bdd/serenity-jbehave/commit/8a3b85acdb18274d8a7d41b84379b8ffe56684eb) Updating to version 0.9.250 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.244 (2014/05/07 07:40 +00:00)
 
 
**Commits:**
 
- [4c286fd](https://github.com/serenity-bdd/serenity-jbehave/commit/4c286fd6911c3572f5d83f1da952a19e7abd23e9) version updates ([@wakaleo](https://github.com/wakaleo))
- [8bb07d9](https://github.com/serenity-bdd/serenity-jbehave/commit/8bb07d9914f0afc25ae95ff5a62c9a692379b092) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.244 ([@wakaleo](https://github.com/wakaleo))
- [c72ed8e](https://github.com/serenity-bdd/serenity-jbehave/commit/c72ed8ea265718b0d7ee3feec2b60c9ef142c867) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [deeab26](https://github.com/serenity-bdd/serenity-jbehave/commit/deeab2673e7be61ae9d7926e3ac3c50dd976dc32) version updates ([@wakaleo](https://github.com/wakaleo))
- [e396663](https://github.com/serenity-bdd/serenity-jbehave/commit/e3966635087c1b98e2718df0e889d0a15fb4beb0) Minor refactoring, and fixes for THUCYDIDES-234 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.236 (2014/04/25 22:25 +00:00)
 
 
**Commits:**
 
- [20a8a2e](https://github.com/serenity-bdd/serenity-jbehave/commit/20a8a2ea854facb5dca33590aea07c369cba2156) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.236 ([@wakaleo](https://github.com/wakaleo))
- [5679bd7](https://github.com/serenity-bdd/serenity-jbehave/commit/5679bd7e72798f5174ef9d0694b723c22869140f) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [604cfc3](https://github.com/serenity-bdd/serenity-jbehave/commit/604cfc3812b25225e2fc371ab6af26529dad26c2) Updated JBehave to 3.9.2 ([@wakaleo](https://github.com/wakaleo))
- [854396f](https://github.com/serenity-bdd/serenity-jbehave/commit/854396ff5f394c6531d9842a9ed9128e74b2752b) Upgrading to JBehave 3.9 ([@wakaleo](https://github.com/wakaleo))
- [bb61b0c](https://github.com/serenity-bdd/serenity-jbehave/commit/bb61b0c269f9534b33c34c93a70ddd6ae1842cc9) Extra tests around Spring integration. ([@wakaleo](https://github.com/wakaleo))
- [d3ae1f8](https://github.com/serenity-bdd/serenity-jbehave/commit/d3ae1f83133e7716eedae663e3f5bf666476317a) Updating dependencies ([@wakaleo](https://github.com/wakaleo))
- [f5c87de](https://github.com/serenity-bdd/serenity-jbehave/commit/f5c87dea10e22e1faff35f79fbd077e8b5c1668f) Minor refactoring and additional tests. ([@wakaleo](https://github.com/wakaleo))
- [f94313c](https://github.com/serenity-bdd/serenity-jbehave/commit/f94313ce1b5e676f1de17ee700f23ff0fed118af) Thucydides now supports the JBehave "groovy:" prefix for metafilters (see http://jbehave.org/reference/stable/meta-filtering.html) ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.235 (2014/03/07 03:47 +00:00)
 
 
**Commits:**
 
- [05ede47](https://github.com/serenity-bdd/serenity-jbehave/commit/05ede477411f46c44f60686e39df7bc27c97c834) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [b0f49f0](https://github.com/serenity-bdd/serenity-jbehave/commit/b0f49f074209c6a1fd7d86a299caca7ef24e2f06) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [fb7413d](https://github.com/serenity-bdd/serenity-jbehave/commit/fb7413d42415a30d2852d29376e62c8ca116bb7a) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.235 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.233 (2014/02/25 00:00 +00:00)
 
 
**Commits:**
 
- [39a7d9a](https://github.com/serenity-bdd/serenity-jbehave/commit/39a7d9a406edcacee484e00a42f3bb80ff9307cb) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [3fb2bd0](https://github.com/serenity-bdd/serenity-jbehave/commit/3fb2bd01057556eea39e7a6beaa2731aafc0dd86) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.233 ([@wakaleo](https://github.com/wakaleo))
- [5727efd](https://github.com/serenity-bdd/serenity-jbehave/commit/5727efdceff388856734368895512e4e1432f852) Fixed a bug where @Step libraries where incorrectly persisted between scenarios. This behavior is now deactivated by default, but can be activated by setting the 'reset.steps.each.scenario' property to 'false' ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.230 (2014/02/17 11:03 +00:00)
 
 
**Commits:**
 
- [4128a8d](https://github.com/serenity-bdd/serenity-jbehave/commit/4128a8d57358c0483bbadd97838f3b13cbc0c4b6) Fixed an issue when using multiple tags in a filter. ([@wakaleo](https://github.com/wakaleo))
- [5ba2e00](https://github.com/serenity-bdd/serenity-jbehave/commit/5ba2e0068f26c4a9a172737bd6dc0016f92f2786) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.230 ([@wakaleo](https://github.com/wakaleo))
- [632739f](https://github.com/serenity-bdd/serenity-jbehave/commit/632739fa54c68e2a0f678d0369d713188710c70c) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [6a7207d](https://github.com/serenity-bdd/serenity-jbehave/commit/6a7207d46f451aeb0b0bc81759fd56915533e1b4) Add support for MM-YYYY and MM/YYYY year-month formats ([@wakaleo](https://github.com/wakaleo))
- [99b6bd4](https://github.com/serenity-bdd/serenity-jbehave/commit/99b6bd43b66b33fbf76c1fff839700698058b3f0) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [d9e5043](https://github.com/serenity-bdd/serenity-jbehave/commit/d9e5043b943ac77d0b62a99a6749f6516d23a3b2) Refactored imports ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.229 (2013/12/27 08:01 +00:00)
 
 
**Commits:**
 
- [44799ce](https://github.com/serenity-bdd/serenity-jbehave/commit/44799ce261321858b8431995f2fc39aa535cbceb) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [71b2e2b](https://github.com/serenity-bdd/serenity-jbehave/commit/71b2e2bdd0fd38493e817936f0e150de3424169f) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.229 ([@wakaleo](https://github.com/wakaleo))
- [a968ce4](https://github.com/serenity-bdd/serenity-jbehave/commit/a968ce4bf89063666818bbb6701bbebe5ac3447e) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.228 (2013/12/04 20:37 +00:00)
 
 
**Commits:**
 
- [1e42205](https://github.com/serenity-bdd/serenity-jbehave/commit/1e422058cb7d6b37fb9542df21783aa76c5d7419) Added support for YearMonth types ([@wakaleo](https://github.com/wakaleo))
- [3278ff7](https://github.com/serenity-bdd/serenity-jbehave/commit/3278ff751904ab25e3b94d3884ef4e6ee0ed4f18) Refactored some of the tests ([@wakaleo](https://github.com/wakaleo))
- [4a5bc05](https://github.com/serenity-bdd/serenity-jbehave/commit/4a5bc057fda3bb9a32094361d28e9e2fddca387c) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [a75c899](https://github.com/serenity-bdd/serenity-jbehave/commit/a75c899780fa2b750a700082ddf371a07af5b01f) Removing unfinished changes from release version ([@wakaleo](https://github.com/wakaleo))
- [ec28722](https://github.com/serenity-bdd/serenity-jbehave/commit/ec2872270f2df4343535336adb67ea5ef46c5aee) More date conversions ([@wakaleo](https://github.com/wakaleo))
- [f3601b3](https://github.com/serenity-bdd/serenity-jbehave/commit/f3601b33ca1adbdac3ffa87201c4890c5b7d9338) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.228 ([@wakaleo](https://github.com/wakaleo))
- [f7b77aa](https://github.com/serenity-bdd/serenity-jbehave/commit/f7b77aab5dbbba9d3e322b0b23afdef3227f8066) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.222 (2013/11/05 23:10 +00:00)
 
 
**Commits:**
 
- [8d5999f](https://github.com/serenity-bdd/serenity-jbehave/commit/8d5999fd6de0be75e671986ff6d5335f2ee3e51e) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [a0e4fbd](https://github.com/serenity-bdd/serenity-jbehave/commit/a0e4fbd698730196b93c563659756cdfee417a7e) Added the 'jbehave.threads' property to configure the number of threads JBehave uses to run stories ([@wakaleo](https://github.com/wakaleo))
- [ec2c47c](https://github.com/serenity-bdd/serenity-jbehave/commit/ec2c47c5667da4b837118d9b96d0426f0e846f33) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.222 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.220 (2013/11/02 11:09 +00:00)
 
 
**Commits:**
 
- [388121c](https://github.com/serenity-bdd/serenity-jbehave/commit/388121c112cbc55a746604b1df3c1da32c4135f5) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [9e2cf42](https://github.com/serenity-bdd/serenity-jbehave/commit/9e2cf42cbccd54fbb0dda5e7abb42e2a08b5444a) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.220 ([@wakaleo](https://github.com/wakaleo))
- [fd5a8b6](https://github.com/serenity-bdd/serenity-jbehave/commit/fd5a8b68e4b2e7b6df59ac4ad60fe08253f99125) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.220-RC1 (2013/10/20 12:19 +00:00)
 
 
**Commits:**
 
- [032fe39](https://github.com/serenity-bdd/serenity-jbehave/commit/032fe395bea96d4498a94bb0e8ab540b009152a7) Updated version ([@wakaleo](https://github.com/wakaleo))
- [8fa062b](https://github.com/serenity-bdd/serenity-jbehave/commit/8fa062b0f9060f859aa444f54c5cec721d2e22a6) THUCYDIDES-168 show give stories as part of scenario ([@arussel](https://github.com/arussel))
- [9811515](https://github.com/serenity-bdd/serenity-jbehave/commit/9811515932b882fd6210ccfeabc8162e532e5171) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [9ec88d9](https://github.com/serenity-bdd/serenity-jbehave/commit/9ec88d9fe6f1d87b2b02866cfd758914f6249922) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.220-RC1 ([@wakaleo](https://github.com/wakaleo))
- [e93129c](https://github.com/serenity-bdd/serenity-jbehave/commit/e93129c8a727999bb07f9c8fd0c8ac02fd37a3bb) Updated version ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.205 (2013/08/25 23:52 +00:00)
 
 
**Commits:**
 
- [101863d](https://github.com/serenity-bdd/serenity-jbehave/commit/101863d90580dbedd85d641bdae09db4143dd73b) Updated version of thucydides ([@wakaleo](https://github.com/wakaleo))
- [2df9855](https://github.com/serenity-bdd/serenity-jbehave/commit/2df98559c4eb4fdb69b47875c53aced8c2384c9b) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.205 ([@wakaleo](https://github.com/wakaleo))
- [7bf33b5](https://github.com/serenity-bdd/serenity-jbehave/commit/7bf33b54e4afce12edd3727a49626be9515b5b0b) Added a new sample test ([@wakaleo](https://github.com/wakaleo))
- [f452aca](https://github.com/serenity-bdd/serenity-jbehave/commit/f452acaf6e16b05a24fae7b061138dce66181dc4) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.204 (2013/08/11 01:17 +00:00)
 
 
**Commits:**
 
- [721914b](https://github.com/serenity-bdd/serenity-jbehave/commit/721914b7341390fa1818a67f879d8b7844684f31) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.204 ([@wakaleo](https://github.com/wakaleo))
- [8faca42](https://github.com/serenity-bdd/serenity-jbehave/commit/8faca4279d3eca2dc81dea1fcaa85e828408659c) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [e3fcd1c](https://github.com/serenity-bdd/serenity-jbehave/commit/e3fcd1c12d2a589d5c98daa2b18b672683f1e250) Updated version ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.203 (2013/07/24 05:13 +00:00)
 
 
**Commits:**
 
- [000cad7](https://github.com/serenity-bdd/serenity-jbehave/commit/000cad7f9e6f8bf76b8a3d283dabc641395c3056) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [2f4ae9d](https://github.com/serenity-bdd/serenity-jbehave/commit/2f4ae9de38a0ee90f72f46783a78639552c1d634) Updated version numbers ([@wakaleo](https://github.com/wakaleo))
- [77cb248](https://github.com/serenity-bdd/serenity-jbehave/commit/77cb2489e570f0c8c112741ffa2ebf496b04aa9d) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.203 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.201 (2013/07/22 03:24 +00:00)
 
 
**Commits:**
 
- [10abaf2](https://github.com/serenity-bdd/serenity-jbehave/commit/10abaf2f0da30eb808f5f6d4cc79f9e5b48ba1c9) Out-of-the-box support for @skip and @ignore tags ([@wakaleo](https://github.com/wakaleo))
- [181f47e](https://github.com/serenity-bdd/serenity-jbehave/commit/181f47ecd2c1b2b75b521776fb41782358dc4701) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.201 ([@wakaleo](https://github.com/wakaleo))
- [9dbc71a](https://github.com/serenity-bdd/serenity-jbehave/commit/9dbc71a6d9777902111ea48feadcc2fba9d0a3b6) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.200 (2013/07/17 07:14 +00:00)
 
 
**Commits:**
 
- [307183d](https://github.com/serenity-bdd/serenity-jbehave/commit/307183dac9a664dc42025da9655921acf1a8eaa5) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.200 ([@wakaleo](https://github.com/wakaleo))
- [526d171](https://github.com/serenity-bdd/serenity-jbehave/commit/526d1715a6588d58e3547dfbe4d260e42cb0e676) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
- [6652cbd](https://github.com/serenity-bdd/serenity-jbehave/commit/6652cbd4b633523d0dd4995b2d7a80658ef4a604) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [8d41dbb](https://github.com/serenity-bdd/serenity-jbehave/commit/8d41dbbebe8553a206e60f7c730dd64d121adf71) Added built-in support for Dates and Lists of Dates, Joda dates and Joda times. ([@wakaleo](https://github.com/wakaleo))
- [b414973](https://github.com/serenity-bdd/serenity-jbehave/commit/b414973ca69caa47edf94bb9fd70b43817fb4548) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
- [ca84a2d](https://github.com/serenity-bdd/serenity-jbehave/commit/ca84a2dad772f44994de0b7ce8a9711d4cadd0e3) Fixed bug related to JBehave composed steps ([@wakaleo](https://github.com/wakaleo))
- [e7b71c6](https://github.com/serenity-bdd/serenity-jbehave/commit/e7b71c67693aff334bce29d2f4664ca24f04d1fb) Updated versions ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.131-NS (2013/07/03 10:51 +00:00)
 
 
**Commits:**
 
- [2442fb0](https://github.com/serenity-bdd/serenity-jbehave/commit/2442fb0d6a9073e468dcd79c69c30a71aed86e05) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.131-NS ([@wakaleo](https://github.com/wakaleo))
- [39ba1fb](https://github.com/serenity-bdd/serenity-jbehave/commit/39ba1fb7210cf865b563b63d36ece697317bad73) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [4ce9ac9](https://github.com/serenity-bdd/serenity-jbehave/commit/4ce9ac940d7151f876ea44b2a68ab4746058faaf) Now places all annotations in the .story file into the metadata in the Thucydides session object ([@wakaleo](https://github.com/wakaleo))
- [6f02b02](https://github.com/serenity-bdd/serenity-jbehave/commit/6f02b029c566a388b6f4d02f75e25864ef37760b) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [c587957](https://github.com/serenity-bdd/serenity-jbehave/commit/c58795728a836d5d0549d46b136fc70dd90ebf81) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [f0ea0f2](https://github.com/serenity-bdd/serenity-jbehave/commit/f0ea0f24227730b581b73134d911344d6c269084) Minor bug fix ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.125 (2013/05/06 07:31 +00:00)
 
 
**Commits:**
 
- [1c3c796](https://github.com/serenity-bdd/serenity-jbehave/commit/1c3c796c736132cd72d6764c99051c741f862d6b) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.125 ([@wakaleo](https://github.com/wakaleo))
- [21e8de9](https://github.com/serenity-bdd/serenity-jbehave/commit/21e8de9d2978e80dee971e64bfbbaa7bfa96f717) updated versions ([@wakaleo](https://github.com/wakaleo))
- [681a1b2](https://github.com/serenity-bdd/serenity-jbehave/commit/681a1b2527a5200390025d28caa7ef14b5692319) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [a6532cd](https://github.com/serenity-bdd/serenity-jbehave/commit/a6532cdd8e0695c814d4c40f82407c06fbce532f) Removed dependency on Hamcrest; if the wrong version of Hamcrest was used in the project, no stories would be found with no error message. ([@wakaleo](https://github.com/wakaleo))
- [b6678f9](https://github.com/serenity-bdd/serenity-jbehave/commit/b6678f97a6765e360a5523187ee246a38a4820c8) Updated version ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.120 (2013/04/23 03:27 +00:00)
 
 
**Commits:**
 
- [2ed7fc4](https://github.com/serenity-bdd/serenity-jbehave/commit/2ed7fc400bf4255177a68d28c789a1820a5d38dd) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [8579fa2](https://github.com/serenity-bdd/serenity-jbehave/commit/8579fa2abfd904f335cc299c24acd6045324c1e2) Updated versions and integrated UTF-8 support ([@wakaleo](https://github.com/wakaleo))
- [f33d414](https://github.com/serenity-bdd/serenity-jbehave/commit/f33d41489d7bbddb9b15303a5daf93f74fb92f31) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.120 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.116 (2013/04/20 07:39 +00:00)
 
 
**Commits:**
 
- [0649f28](https://github.com/serenity-bdd/serenity-jbehave/commit/0649f28d89f92b328ff100ffbca674d0d357cebd) Patch for steps in dependcies ([@mdolinin](https://github.com/mdolinin))
- [11e0b42](https://github.com/serenity-bdd/serenity-jbehave/commit/11e0b42cdb3b2139140963f9dc99175b7de0a49d) Add ability to find classes in jar files and tests ([@mdolinin](https://github.com/mdolinin))
- [2e62852](https://github.com/serenity-bdd/serenity-jbehave/commit/2e6285250423cad7ce8c2307faf26da8f6984f85) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [4f2b4fa](https://github.com/serenity-bdd/serenity-jbehave/commit/4f2b4fa9865480ddab08a03a5b14757d07fba640) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.116 ([@wakaleo](https://github.com/wakaleo))
- [5f1c825](https://github.com/serenity-bdd/serenity-jbehave/commit/5f1c82541e0a06812c8d051e8f96d01a9ce3984b) Fix issue with scan subpackages ([@mdolinin](https://github.com/mdolinin))
- [6a7a1eb](https://github.com/serenity-bdd/serenity-jbehave/commit/6a7a1ebf6f3efa2ca628a5cd0468541508d70faa) Updated version ([@wakaleo](https://github.com/wakaleo))
- [6dcd20d](https://github.com/serenity-bdd/serenity-jbehave/commit/6dcd20d974fa3ad2b9cb3767093261532b190328) Added support for Assumptions ([@wakaleo](https://github.com/wakaleo))
- [ab503c9](https://github.com/serenity-bdd/serenity-jbehave/commit/ab503c9600189fa6cf48dc086d07fe4b3aa69c1e) Refactoring ([@wakaleo](https://github.com/wakaleo))
- [e94ccb9](https://github.com/serenity-bdd/serenity-jbehave/commit/e94ccb9aa3c6b983dbeacc00cf758ba1f3b87941) Fix issue with scan subpackages ([@mdolinin](https://github.com/mdolinin))
- [fcb1d97](https://github.com/serenity-bdd/serenity-jbehave/commit/fcb1d97dda438e3901c04710158dfe50d8788e98) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.110 (2013/04/04 05:27 +00:00)
 
 
**Commits:**
 
- [7754100](https://github.com/serenity-bdd/serenity-jbehave/commit/7754100aeb77440d2c40255476ad1f8fb3b31e70) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.110 ([@wakaleo](https://github.com/wakaleo))
- [8d7f1c1](https://github.com/serenity-bdd/serenity-jbehave/commit/8d7f1c13fbbd75d04ee462e8321ffaa4297438a1) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [9092b3b](https://github.com/serenity-bdd/serenity-jbehave/commit/9092b3b68be02366803949133b4d5da72e734721) Updated to 0.9.110 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.108 (2013/04/04 01:08 +00:00)
 
 
**Commits:**
 
- [21f5b3e](https://github.com/serenity-bdd/serenity-jbehave/commit/21f5b3e4bb46e19135e0390a676c7acffea0891f) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [29b29a3](https://github.com/serenity-bdd/serenity-jbehave/commit/29b29a3d30db55758654ee4e7e7ad9a2e4bd8db8) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [8b25a91](https://github.com/serenity-bdd/serenity-jbehave/commit/8b25a910e0f7c2af961ed9ba799129625c3acf22) Added a better example for meta-tags ([@wakaleo](https://github.com/wakaleo))
- [ee9f9cf](https://github.com/serenity-bdd/serenity-jbehave/commit/ee9f9cf6704a210710c1aa9968fbe199acb14af6) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
- [fb2f3cc](https://github.com/serenity-bdd/serenity-jbehave/commit/fb2f3cc2de3d87a78e891f93ed3da7c4186365b9) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.108 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.105 (2013/03/20 23:20 +00:00)
 
 
**Commits:**
 
- [5afa2a6](https://github.com/serenity-bdd/serenity-jbehave/commit/5afa2a612356e733249068d63a88f450bd6f8930) Updated version ([@wakaleo](https://github.com/wakaleo))
- [5e54862](https://github.com/serenity-bdd/serenity-jbehave/commit/5e5486220a9b913f9a5dbb479a090d72846eed06) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.105 ([@wakaleo](https://github.com/wakaleo))
- [d927258](https://github.com/serenity-bdd/serenity-jbehave/commit/d927258d2763548a93498e9702488829b3f8c0cf) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [e3074ca](https://github.com/serenity-bdd/serenity-jbehave/commit/e3074caa7a53aa520fc7e93f69c537e85b0e8653) Added support for GivenStories. Given story files should start with the word 'Given' or 'precondition', or be off the normal story path ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.104 (2013/03/12 05:25 +00:00)
 
 
**Commits:**
 
- [77b4973](https://github.com/serenity-bdd/serenity-jbehave/commit/77b497330d6c1c9c6f2a7595bb9ccca31ae93e62) Fixed support for JBehave fixture methods ([@wakaleo](https://github.com/wakaleo))
- [a518227](https://github.com/serenity-bdd/serenity-jbehave/commit/a5182270b3dcb31db9a1f8bdc0e9282d156e4c64) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [aec9f42](https://github.com/serenity-bdd/serenity-jbehave/commit/aec9f42d10ca157f9ddab77966f2b5c7d9ec4f22) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.104 ([@wakaleo](https://github.com/wakaleo))
- [e287c58](https://github.com/serenity-bdd/serenity-jbehave/commit/e287c58f51d212630744827821295831000c0853) Added tests to reproduce the fixture method issue ([@wakaleo](https://github.com/wakaleo))
- [f4f19a5](https://github.com/serenity-bdd/serenity-jbehave/commit/f4f19a5dfc131d4696c4e16c06e3bd23ca9f81b1) Updated version ([@wakaleo](https://github.com/wakaleo))
- [ff6f823](https://github.com/serenity-bdd/serenity-jbehave/commit/ff6f8235496b9183949547bf84e9bb98fa7498e1) Refined the story lookup logic to avoid duplicates ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.103 (2013/03/11 04:11 +00:00)
 
 
**Commits:**
 
- [0f03df0](https://github.com/serenity-bdd/serenity-jbehave/commit/0f03df07f61efb70653322a887ba5750c4e04893) Updated test logic ([@wakaleo](https://github.com/wakaleo))
- [25e2fbb](https://github.com/serenity-bdd/serenity-jbehave/commit/25e2fbb21deb6014a549711139d3d1b0ebfcbdab) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [a11760c](https://github.com/serenity-bdd/serenity-jbehave/commit/a11760cb753e2216040b4d452ec310df2c445e1c) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [b524632](https://github.com/serenity-bdd/serenity-jbehave/commit/b52463248d593f667609a3d2976e9b855f012129) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.103 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.100 (2013/02/27 23:38 +00:00)
 
 
**Commits:**
 
- [2861694](https://github.com/serenity-bdd/serenity-jbehave/commit/286169486ae93cfaa65ebaf862313592ad092705) Modify tests ([@mdolinin](https://github.com/mdolinin))
- [6cfda8f](https://github.com/serenity-bdd/serenity-jbehave/commit/6cfda8f73312da3ba6ecb65ceac34c5ec73d8fe2) Add verify given story at beforestory method ([@mdolinin](https://github.com/mdolinin))
- [bfe0eed](https://github.com/serenity-bdd/serenity-jbehave/commit/bfe0eed44a10df4f43e55333fd8d1d86bff6f902) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.100 ([@wakaleo](https://github.com/wakaleo))
- [c99851a](https://github.com/serenity-bdd/serenity-jbehave/commit/c99851a614f3be483a03aa8c22e4fc7432447530) Add given ([@mdolinin](https://github.com/mdolinin))
- [d4252f3](https://github.com/serenity-bdd/serenity-jbehave/commit/d4252f34e4e626351c7dce5549509cdca94826b3) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [de487df](https://github.com/serenity-bdd/serenity-jbehave/commit/de487df0204bdd69e2745eb080df6550424d89f5) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [e3440a3](https://github.com/serenity-bdd/serenity-jbehave/commit/e3440a37b202c550e7327bb40897d951bd861318) Add tests ([@mdolinin](https://github.com/mdolinin))
 
### thucydides-jbehave-plugin-0.9.98 (2013/02/19 23:27 +00:00)
 
 
**Commits:**
 
- [6e4396c](https://github.com/serenity-bdd/serenity-jbehave/commit/6e4396c20ad4b3c368cbd2a5113c39eabc1dc9ed) Updating versions ([@wakaleo](https://github.com/wakaleo))
- [75de6ef](https://github.com/serenity-bdd/serenity-jbehave/commit/75de6ef1bea6ba8fc5ac009659a48e0a11c7ef17) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.98 ([@wakaleo](https://github.com/wakaleo))
- [7e8c76a](https://github.com/serenity-bdd/serenity-jbehave/commit/7e8c76ac695bda5f4c34afee41c79ef91ae055ed) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.97 (2013/02/14 05:18 +00:00)
 
 
**Commits:**
 
- [18ce471](https://github.com/serenity-bdd/serenity-jbehave/commit/18ce47116be008d08bbb71e1bcaf40a660818d81) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [2c97a89](https://github.com/serenity-bdd/serenity-jbehave/commit/2c97a8935e01d2af46898483eed5b6a711b6d915) Updating versions ([@wakaleo](https://github.com/wakaleo))
- [cb761cb](https://github.com/serenity-bdd/serenity-jbehave/commit/cb761cb10ecc5a7b7f488d97e169e25fa2549f70) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.97 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.95 (2013/02/01 11:48 +00:00)
 
 
**Commits:**
 
- [7d3ea1b](https://github.com/serenity-bdd/serenity-jbehave/commit/7d3ea1bad496ac143be205643727bc629dc3cae2) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [8890487](https://github.com/serenity-bdd/serenity-jbehave/commit/8890487f413499d6a8d9c0fc513cc63ad08fe166) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.95 ([@wakaleo](https://github.com/wakaleo))
- [a7af9fb](https://github.com/serenity-bdd/serenity-jbehave/commit/a7af9fbbc3c687807235a1ab4507674cb95aa57b) Added support for the 'restart.browser.each.scenario' property, which forces a new browser instance to be used for each scenario and each example in data-driven scenarios ([@wakaleo](https://github.com/wakaleo))
- [ba76c00](https://github.com/serenity-bdd/serenity-jbehave/commit/ba76c004dcb3c2dee6acb6eadc3292f9023c3580) Updated versions ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.94 (2013/01/25 04:48 +00:00)
 
 
**Commits:**
 
- [2ee8528](https://github.com/serenity-bdd/serenity-jbehave/commit/2ee8528e4dbcd53ba2306ea97b577d32617911bb) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [4d9af25](https://github.com/serenity-bdd/serenity-jbehave/commit/4d9af257a7d0f0753fb9f690e5755137de5db2ee) Updated to Thucydides 0.9.94 ([@wakaleo](https://github.com/wakaleo))
- [61662ec](https://github.com/serenity-bdd/serenity-jbehave/commit/61662ec7b541691e19bacd29ee9248fa3c191b74) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.94 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.93 (2013/01/18 20:24 +00:00)
 
 
**Commits:**
 
- [9d454bb](https://github.com/serenity-bdd/serenity-jbehave/commit/9d454bbd87551b80f8ad66864f26fdb8dd8a9090) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [a27fa69](https://github.com/serenity-bdd/serenity-jbehave/commit/a27fa69cfbea6511ce0b3554bb29c86bd90258fb) Fixed THUCYDIDES-126 ([@wakaleo](https://github.com/wakaleo))
- [db37b14](https://github.com/serenity-bdd/serenity-jbehave/commit/db37b145926b3e40d10b4940f69cf2c84893c81c) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.93 ([@wakaleo](https://github.com/wakaleo))
- [f0f8fd9](https://github.com/serenity-bdd/serenity-jbehave/commit/f0f8fd9f7764b5e2dcc569010ea067cae6837d77) Refactoring attempt to fix THUCYDIDES-121 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.92 (2013/01/14 08:34 +00:00)
 
 
**Commits:**
 
- [18d3675](https://github.com/serenity-bdd/serenity-jbehave/commit/18d3675b0fb10d42493d28c9a95743839ee36081) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.92 ([@wakaleo](https://github.com/wakaleo))
- [84cd46d](https://github.com/serenity-bdd/serenity-jbehave/commit/84cd46dbc76a1caa6d57174c40c819843bc7ac8c) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
- [ffcbb04](https://github.com/serenity-bdd/serenity-jbehave/commit/ffcbb04d60ff7ab9f8a2268979f39b18094527b7) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.91 (2013/01/14 06:47 +00:00)
 
 
**Commits:**
 
- [0a21125](https://github.com/serenity-bdd/serenity-jbehave/commit/0a21125c5c10e3ac032a08172db03741db6a09bd) Updated version ([@wakaleo](https://github.com/wakaleo))
- [85027e8](https://github.com/serenity-bdd/serenity-jbehave/commit/85027e80c02bc6379fcff0129623d6779f8a61d8) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [b9504a8](https://github.com/serenity-bdd/serenity-jbehave/commit/b9504a8c8beefc0e193cfb59ae4ddba04e9e727b) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.91 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.90 (2012/12/27 14:02 +00:00)
 
 
**Commits:**
 
- [0927884](https://github.com/serenity-bdd/serenity-jbehave/commit/0927884febe3b90a6d9c19c125732ab8b4b7e9af) Completed support for data-driven tests, currently working for JBehave ([@wakaleo](https://github.com/wakaleo))
- [1c54ce6](https://github.com/serenity-bdd/serenity-jbehave/commit/1c54ce6f7b4b13804a32dad23ba824b306e0ba55) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [5003eb5](https://github.com/serenity-bdd/serenity-jbehave/commit/5003eb544f16de13d7a3c3ff57cfcbacaa4f6e5e) Basic support for example reporting ([@wakaleo](https://github.com/wakaleo))
- [bb69493](https://github.com/serenity-bdd/serenity-jbehave/commit/bb69493f88a97351806fa890855a42cf483ce458) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [f7da6d6](https://github.com/serenity-bdd/serenity-jbehave/commit/f7da6d662b055be164ef17d92217581ba1a8a053) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.90 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.87 (2012/12/16 21:20 +00:00)
 
 
**Commits:**
 
- [27c774d](https://github.com/serenity-bdd/serenity-jbehave/commit/27c774d62b48705b80b2924ebf3754534217a451) Fix for THUCYDIDES-115 ([@wakaleo](https://github.com/wakaleo))
- [285e3a3](https://github.com/serenity-bdd/serenity-jbehave/commit/285e3a3d82b3b96670db302d9369a4a249d2509a) Update Thucydides dependency ([@wakaleo](https://github.com/wakaleo))
- [6f90037](https://github.com/serenity-bdd/serenity-jbehave/commit/6f90037326bb3026894d23528dc5825829e4ebd5) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.87 ([@wakaleo](https://github.com/wakaleo))
- [6fbd947](https://github.com/serenity-bdd/serenity-jbehave/commit/6fbd9470265bb16f4e0d5fb551afe1522eec2f3f) Added support for running web tests in a single browser using either the thucydides.use.unique.browser system property or programmatically using runThucydides().inASingleSession() ([@wakaleo](https://github.com/wakaleo))
- [70167ac](https://github.com/serenity-bdd/serenity-jbehave/commit/70167aca71591a7bf147a6776da72f49a385f7fb) Updated versions ([@wakaleo](https://github.com/wakaleo))
- [a6f2c5d](https://github.com/serenity-bdd/serenity-jbehave/commit/a6f2c5d14b4d0d325d53dbb4a4e79c8cd1a34d66) Added support for the thucydides.restart.browser.frequency system property, which restarts the browser periodically for data-driven tests. Can help as firefox may hang after too many data-driven tests ([@wakaleo](https://github.com/wakaleo))
- [f84a3e1](https://github.com/serenity-bdd/serenity-jbehave/commit/f84a3e164a97231ffd4bdd787ae69dd6190da19b) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.74 (2012/11/13 02:28 +00:00)
 
 
**Commits:**
 
- [004cb61](https://github.com/serenity-bdd/serenity-jbehave/commit/004cb61fd771f623cda72920e8b364961d86c8b5) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.74 ([@wakaleo](https://github.com/wakaleo))
- [26c1140](https://github.com/serenity-bdd/serenity-jbehave/commit/26c1140a1b732ea589d42773b4fa422e37c04c8e) Local compiler config ([@wakaleo](https://github.com/wakaleo))
- [3d94988](https://github.com/serenity-bdd/serenity-jbehave/commit/3d94988f867fa7159ddd7a4365a936399c9b79b1) Upgraded Thucydides version ([@wakaleo](https://github.com/wakaleo))
- [7174d81](https://github.com/serenity-bdd/serenity-jbehave/commit/7174d816f2152a603f46025ab7447866e16ec5f7) Updated Thucydides version and added better support for failing JUnit tests ([@wakaleo](https://github.com/wakaleo))
- [bc3fca8](https://github.com/serenity-bdd/serenity-jbehave/commit/bc3fca853ece7bd15b9424d9f918a467b72faaca) Upgraded Thucydides version ([@wakaleo](https://github.com/wakaleo))
- [ee88248](https://github.com/serenity-bdd/serenity-jbehave/commit/ee88248d24eb8913bde945f5afcca104f11c8f03) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.71 (2012/11/07 02:56 +00:00)
 
 
**Commits:**
 
- [1a206ff](https://github.com/serenity-bdd/serenity-jbehave/commit/1a206ff8dd339e80e9f89fcdb152905a440e38c3) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [8502947](https://github.com/serenity-bdd/serenity-jbehave/commit/85029478bb33be9a6566133c6a6f166435fa59ed) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
- [d9b9bc7](https://github.com/serenity-bdd/serenity-jbehave/commit/d9b9bc79a6274b65540275ffaf20fb9be54cb05f) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.71 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.28 (2012/10/13 00:05 +00:00)
 
 
**Commits:**
 
- [32f3386](https://github.com/serenity-bdd/serenity-jbehave/commit/32f33861b09dc6d8523f91ec32d87620f3efadc3) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [8b8a897](https://github.com/serenity-bdd/serenity-jbehave/commit/8b8a897313622a835cecf9cfbeb58fcb55e32980) Updated version numbers ([@wakaleo](https://github.com/wakaleo))
- [bdf86dc](https://github.com/serenity-bdd/serenity-jbehave/commit/bdf86dca1b70f613294dc8db00aaa118dbdfb23c) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.28 ([@wakaleo](https://github.com/wakaleo))
- [e5f35d1](https://github.com/serenity-bdd/serenity-jbehave/commit/e5f35d134bce63557646ab3298a2c62de39d0535) Added built-in support for enum conversions ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.20 (2012/09/11 04:54 +00:00)
 
 
**Commits:**
 
- [412f0ce](https://github.com/serenity-bdd/serenity-jbehave/commit/412f0ce99c5f7b8ddef86dd3e5a69297fa1aac84) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [8520f49](https://github.com/serenity-bdd/serenity-jbehave/commit/8520f499d218f149c39be937aca2613665811a85) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.20 ([@wakaleo](https://github.com/wakaleo))
- [ab0df39](https://github.com/serenity-bdd/serenity-jbehave/commit/ab0df392eb546da669701bf4f66b5b102c5153c3) Added the story.timeout.in.secs system property for the JBehave stories. ([@wakaleo](https://github.com/wakaleo))
- [b71b190](https://github.com/serenity-bdd/serenity-jbehave/commit/b71b190993acb8eb075a8520df53dd3272f20efe) Suspending a test with timeout issues for investigation later on ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.19 (2012/09/01 12:11 +00:00)
 
 
**Commits:**
 
- [5f4dae0](https://github.com/serenity-bdd/serenity-jbehave/commit/5f4dae073a994971b41b1a16b5ace3515258c86c) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [a1d35b6](https://github.com/serenity-bdd/serenity-jbehave/commit/a1d35b6c536dcef5e7688b9ad448995e7a338411) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.19 ([@wakaleo](https://github.com/wakaleo))
- [ea03998](https://github.com/serenity-bdd/serenity-jbehave/commit/ea03998b937ec62344dfc509fb54b5bc81f6e290) Updated and expanded tests for data-driven tests ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.18 (2012/08/24 00:05 +00:00)
 
 
**Commits:**
 
- [05e3de3](https://github.com/serenity-bdd/serenity-jbehave/commit/05e3de309d1f7503886f7863b2b35a86ff6b654f) Added support for the metafilter environment variable, that you can use to filter the stories that JBehave executes using the JBehave meta annotations. Also added the ignore.failures.in.stories system property, which will prevent the build from failing if a JBehave tests fails. ([@wakaleo](https://github.com/wakaleo))
- [5d820dc](https://github.com/serenity-bdd/serenity-jbehave/commit/5d820dc3f817849ff34f72bf8a62ef6b98bfb18e) Fine-tuning the JBehave reporting, with limited success ([@wakaleo](https://github.com/wakaleo))
- [79a637e](https://github.com/serenity-bdd/serenity-jbehave/commit/79a637e720f94382df801d3f571829c2a673d2f5) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.18 ([@wakaleo](https://github.com/wakaleo))
- [82962cc](https://github.com/serenity-bdd/serenity-jbehave/commit/82962ccbbdd9c4d04c1758f8e19eb19a0680dadd) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.17 (2012/08/17 04:02 +00:00)
 
 
**Commits:**
 
- [b94abbf](https://github.com/serenity-bdd/serenity-jbehave/commit/b94abbf18d45b80b175fe80c63896b930927b06d) Added better support for classloaders, in particular to support running Thucydides tests in Grails ([@wakaleo](https://github.com/wakaleo))
- [df9295e](https://github.com/serenity-bdd/serenity-jbehave/commit/df9295ec56a4bf0bd2c289d7051d6037cc9cfa4c) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [e2bba1f](https://github.com/serenity-bdd/serenity-jbehave/commit/e2bba1fc7f638096b80ece30359eb59377d7becc) Added better support for classloaders, in particular to support running Thucydides tests in Grails ([@wakaleo](https://github.com/wakaleo))
- [e4c6a0a](https://github.com/serenity-bdd/serenity-jbehave/commit/e4c6a0a58e0e4e4788b6c9ddcf5fa7b3109848fe) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.17 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.12 (2012/08/08 23:08 +00:00)
 
 
**Commits:**
 
- [0a211c9](https://github.com/serenity-bdd/serenity-jbehave/commit/0a211c9291803df5ca8aaf5066ad349e192fb965) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [7cbc9e8](https://github.com/serenity-bdd/serenity-jbehave/commit/7cbc9e8b738ca821433bf55ccbf43b0c4afa2e3d) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.12 ([@wakaleo](https://github.com/wakaleo))
- [a598f94](https://github.com/serenity-bdd/serenity-jbehave/commit/a598f94434697455b4962c6a42a6213ae7813f39) Updated thucydides dependency to 0.9.12 ([@wakaleo](https://github.com/wakaleo))
- [cd18269](https://github.com/serenity-bdd/serenity-jbehave/commit/cd1826956e619a7e835be5cd332da7ac746ec2a8) Improved reporting ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0 (2012/07/27 06:49 +00:00)
 
 
**Commits:**
 
- [1f98fcf](https://github.com/serenity-bdd/serenity-jbehave/commit/1f98fcf1f50f9176a2f39fa6afa763f644ca5d7d) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0 ([@wakaleo](https://github.com/wakaleo))
- [b5f179b](https://github.com/serenity-bdd/serenity-jbehave/commit/b5f179b593143d45b9fdde5665a1b3fccfe51d7c) Updating to 0.9.0 ([@wakaleo](https://github.com/wakaleo))
- [b93ada4](https://github.com/serenity-bdd/serenity-jbehave/commit/b93ada4f12094b242259bf1c14015d60246b5b4f) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [c354b6b](https://github.com/serenity-bdd/serenity-jbehave/commit/c354b6b9f41cef3137684198e4e83dfbc568ffb8) Updating to 0.9.0 ([@wakaleo](https://github.com/wakaleo))
- [d2de071](https://github.com/serenity-bdd/serenity-jbehave/commit/d2de07161d0689979d3c20d966f8a494be7c8069) Removed an error in the tests and modified the default step loader to not load inner classes (with names containing a '$'). When using Groovy, trying to load the inner classes that represent closures caused the tests to fail. ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-RC4 (2012/07/18 03:33 +00:00)
 
 
**Commits:**
 
- [64ef9bd](https://github.com/serenity-bdd/serenity-jbehave/commit/64ef9bda0bfc8646e197a06858d89e7986a4baaa) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [b3e137b](https://github.com/serenity-bdd/serenity-jbehave/commit/b3e137b7c9d98930e1621457c8c5a511f402e146) Integrated a better JUnit runner for nicer IDE integration ([@wakaleo](https://github.com/wakaleo))
- [d446806](https://github.com/serenity-bdd/serenity-jbehave/commit/d446806b7dba89ecb56828a3e9fc6b7de10fb098) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-RC4 ([@wakaleo](https://github.com/wakaleo))
- [e2b174c](https://github.com/serenity-bdd/serenity-jbehave/commit/e2b174cd7611e15087799227305840384164c263) Fixed issue with closing the browser after a test failure ([@wakaleo](https://github.com/wakaleo))
- [e432d12](https://github.com/serenity-bdd/serenity-jbehave/commit/e432d121b0486dbb21fa9147332df55717ddde8a) Fixing issue which leaves a browser open after a failing web test ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-RC3 (2012/07/12 13:41 +00:00)
 
 
**Commits:**
 
- [61e331a](https://github.com/serenity-bdd/serenity-jbehave/commit/61e331ae2fa6d749010d94a0da0740fd6b9debf7) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [7c03144](https://github.com/serenity-bdd/serenity-jbehave/commit/7c031445b2345cb36fd9b10197e298e92545f73c) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-RC3 ([@wakaleo](https://github.com/wakaleo))
- [9e10df3](https://github.com/serenity-bdd/serenity-jbehave/commit/9e10df3f13258e9fb5aacc74afb010f7cdcdf454) Fixed an issue managing state between steps ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-RC2 (2012/07/12 02:34 +00:00)
 
 
**Commits:**
 
- [5227ad8](https://github.com/serenity-bdd/serenity-jbehave/commit/5227ad86692cea92393f11150bfd7825e920f9dc) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [671dda7](https://github.com/serenity-bdd/serenity-jbehave/commit/671dda755daa3194c5b7ef5ebac90120fa4dca18) Fixed an issue with taking screenshots in nested steps ([@wakaleo](https://github.com/wakaleo))
- [affeaa2](https://github.com/serenity-bdd/serenity-jbehave/commit/affeaa2bdfccd2ddda81ea5f757fc540bd84fb77) Preparing release ([@wakaleo](https://github.com/wakaleo))
- [cf6168b](https://github.com/serenity-bdd/serenity-jbehave/commit/cf6168b7abfcc2cc3d8e25404478ee3df0bc0c98) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-RC2 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-RC1 (2012/07/10 11:39 +00:00)
 
 
**Commits:**
 
- [13eb79c](https://github.com/serenity-bdd/serenity-jbehave/commit/13eb79c95a2f8f78c0613990948666919d657f21) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-RC1 ([@wakaleo](https://github.com/wakaleo))
- [369fe28](https://github.com/serenity-bdd/serenity-jbehave/commit/369fe28f3ee6b905e6db70b0395be5c4600c2356) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [39468fa](https://github.com/serenity-bdd/serenity-jbehave/commit/39468fa548f5cede2bae37cde9f44e8e0326ce91) Updated versions ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-beta-6 (2012/07/10 10:42 +00:00)
 
 
**Commits:**
 
- [2718e0a](https://github.com/serenity-bdd/serenity-jbehave/commit/2718e0acafe8cb4a206649803f6e02a437e31cdf) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [28261da](https://github.com/serenity-bdd/serenity-jbehave/commit/28261da8199b7c6d92acbcd9bb66f9f6573a388f) Fixed a bug that incorrectly reported a failing test if another test had previously failed ([@wakaleo](https://github.com/wakaleo))
- [2effd60](https://github.com/serenity-bdd/serenity-jbehave/commit/2effd60452fb5873d47229daae9b5f4d7bd16175) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-beta-6 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-beta-5 (2012/07/08 10:45 +00:00)
 
 
**Commits:**
 
- [03d3d47](https://github.com/serenity-bdd/serenity-jbehave/commit/03d3d47b3d903f7dd4465ad96767787cfbd141ff) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [12149d5](https://github.com/serenity-bdd/serenity-jbehave/commit/12149d5027f177d2e16e7152f03cf60338b6a162) Added support for injecting the Pages page factory into JBehave scenario libraries via the constructor ([@wakaleo](https://github.com/wakaleo))
- [56f4041](https://github.com/serenity-bdd/serenity-jbehave/commit/56f404187a50fc00105cd5981c0a39f110e04300) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-beta-5 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-beta-4 (2012/07/07 23:47 +00:00)
 
 
**Commits:**
 
- [0f9df19](https://github.com/serenity-bdd/serenity-jbehave/commit/0f9df1962431f889ed164cb64de02c5b69036583) Mainly bug fixes. ([@wakaleo](https://github.com/wakaleo))
- [2653b13](https://github.com/serenity-bdd/serenity-jbehave/commit/2653b13497656c83537c6f859757de3625d91f0b) Added support for configuring thucydides properties more easily from the JUnit JBehave test ([@wakaleo](https://github.com/wakaleo))
- [5890a60](https://github.com/serenity-bdd/serenity-jbehave/commit/5890a6010e2f446158f63bcc0af5a5c95b6a2062) WIP ([@wakaleo](https://github.com/wakaleo))
- [9394a9a](https://github.com/serenity-bdd/serenity-jbehave/commit/9394a9a1871904ef1b4560631b07c21af06df472) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-beta-4 ([@wakaleo](https://github.com/wakaleo))
- [c29df96](https://github.com/serenity-bdd/serenity-jbehave/commit/c29df967de677a5bd0a988a111d7a0a4c60d5b48) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-beta-3 (2012/07/04 07:41 +00:00)
 
 
**Commits:**
 
- [059b48c](https://github.com/serenity-bdd/serenity-jbehave/commit/059b48c712a4fee3f613e59e2638a1c7403c26f7) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-beta-3 ([@wakaleo](https://github.com/wakaleo))
- [2046a07](https://github.com/serenity-bdd/serenity-jbehave/commit/2046a07111e2ea8004c10eacc3723bce76b7a9b1) Added the ThucydidesJUnitStory class, that makes it easy to run individual JBehavior stories as JUnit tests ([@wakaleo](https://github.com/wakaleo))
- [a0e115f](https://github.com/serenity-bdd/serenity-jbehave/commit/a0e115fa3c9a7c210f194f95549b004aeee54fee) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-beta-3 ([@wakaleo](https://github.com/wakaleo))
- [a1b3c9a](https://github.com/serenity-bdd/serenity-jbehave/commit/a1b3c9a6a1f1072974842f4a2fe65cd0cecbac2b) Refactored packages ([@wakaleo](https://github.com/wakaleo))
- [ea7a355](https://github.com/serenity-bdd/serenity-jbehave/commit/ea7a355a971491d1989842d81021beafcd6228cc) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [f7cd0c3](https://github.com/serenity-bdd/serenity-jbehave/commit/f7cd0c36ecfdc9ba2e5e9d14baefcce6d7ddd1ca) [maven-release-plugin] rollback the release of thucydides-jbehave-plugin-0.9.0-beta-3 ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-beta-2 (2012/06/29 02:27 +00:00)
 
 
**Commits:**
 
- [2a5cfab](https://github.com/serenity-bdd/serenity-jbehave/commit/2a5cfab671509b1634379ef5542dbaad376cf3ce) Update version numbers ([@wakaleo](https://github.com/wakaleo))
- [2ae667e](https://github.com/serenity-bdd/serenity-jbehave/commit/2ae667e9e5361de009722118a933a2ba91ca61c4) Initial working webdriver integration ([@wakaleo](https://github.com/wakaleo))
- [47431b3](https://github.com/serenity-bdd/serenity-jbehave/commit/47431b380ee71baead9cec7b95448649110bf191) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
- [724bf92](https://github.com/serenity-bdd/serenity-jbehave/commit/724bf92e3ee8fa6ddf9b62e7db27170ada5ac501) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-beta-2 ([@wakaleo](https://github.com/wakaleo))
- [7eaf189](https://github.com/serenity-bdd/serenity-jbehave/commit/7eaf1892e9d2642c975400278009f58fcc8dcc82) Update version numbers ([@wakaleo](https://github.com/wakaleo))
- [85896b7](https://github.com/serenity-bdd/serenity-jbehave/commit/85896b7af5dddc93b66baee4e05d6d769b11c90a) Update version numbers ([@wakaleo](https://github.com/wakaleo))
- [afbdd20](https://github.com/serenity-bdd/serenity-jbehave/commit/afbdd207cc480dc89d34fd1fc2ce5cd09a4800b1) Basic webdriver support with screenshots ([@wakaleo](https://github.com/wakaleo))
- [b429d40](https://github.com/serenity-bdd/serenity-jbehave/commit/b429d40e97ac24134d4e18d201d770bed5331d21) Update version numbers ([@wakaleo](https://github.com/wakaleo))
- [f3a8f9e](https://github.com/serenity-bdd/serenity-jbehave/commit/f3a8f9e30d1a6e5e4bba108057d929f7376fd6e9) Update version numbers ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-alpha-6 (2012/06/21 12:47 +00:00)
 
 
**Commits:**
 
- [179fec5](https://github.com/serenity-bdd/serenity-jbehave/commit/179fec59706e2de1ce1776f1300675778ff37c88) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [2857c5b](https://github.com/serenity-bdd/serenity-jbehave/commit/2857c5b2610a22541c11ebb90e027baf2f729d2b) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-alpha-6 ([@wakaleo](https://github.com/wakaleo))
- [ae23692](https://github.com/serenity-bdd/serenity-jbehave/commit/ae236926f18f27a63da67fcf665d9673dca40d97) Updated thucydides version ([@wakaleo](https://github.com/wakaleo))
- [c2851f3](https://github.com/serenity-bdd/serenity-jbehave/commit/c2851f373da03559b0cca246497649a01086df0b) [maven-release-plugin] prepare for next development iteration ([@wakaleo](https://github.com/wakaleo))
 
### thucydides-jbehave-plugin-0.9.0-alpha-4 (2012/06/20 01:47 +00:00)
 
 
**Commits:**
 
- [054f780](https://github.com/serenity-bdd/serenity-jbehave/commit/054f7804b4e98f8d9c6fbc8ef6be37fe02cca1ea) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [084e66d](https://github.com/serenity-bdd/serenity-jbehave/commit/084e66d1596b57256fabcc4d1608c54d1ffcc232) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [16af335](https://github.com/serenity-bdd/serenity-jbehave/commit/16af3355c5b28029751a8c6a8722b0ed752be41f) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [230f471](https://github.com/serenity-bdd/serenity-jbehave/commit/230f47182dfff68a88f9ae743b26bce199b47aff) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-alpha-4 ([@wakaleo](https://github.com/wakaleo))
- [4b6f977](https://github.com/serenity-bdd/serenity-jbehave/commit/4b6f97761f5cc3d5e009ed97922e115e5f3ae5d5) Initial commit ([@wakaleo](https://github.com/wakaleo))
- [818d279](https://github.com/serenity-bdd/serenity-jbehave/commit/818d279740baba4e433c95d024a58df282f8dee5) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-alpha-3 ([@wakaleo](https://github.com/wakaleo))
- [8291b27](https://github.com/serenity-bdd/serenity-jbehave/commit/8291b27fc5f4db627eac05aed7884d44b42df389) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [83533a3](https://github.com/serenity-bdd/serenity-jbehave/commit/83533a382d3920391eda1d37de928bca1e66f3ad) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [a3178d3](https://github.com/serenity-bdd/serenity-jbehave/commit/a3178d334e3ad7bec3daf5e33d7c7d6cac0ae850) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-alpha-3 ([@wakaleo](https://github.com/wakaleo))
- [a3ae499](https://github.com/serenity-bdd/serenity-jbehave/commit/a3ae499343a6e5cf09297646960c8226c7c4acf5) Finetuning version ([@wakaleo](https://github.com/wakaleo))
- [b737173](https://github.com/serenity-bdd/serenity-jbehave/commit/b7371734ca1f7d7bc46707cfdb4136d3aa1c0c7f) [maven-release-plugin] prepare release thucydides-jbehave-plugin-0.9.0-alpha-4 ([@wakaleo](https://github.com/wakaleo))
- [d5cd4be](https://github.com/serenity-bdd/serenity-jbehave/commit/d5cd4bed5c802d7e8c8e9c95c7620dd15489ac6a) Initial commit ([@wakaleo](https://github.com/wakaleo))
 
