package net.serenity_bdd.jbehave;

import net.thucydides.core.webdriver.Configuration;

public class BehaviorsForUatAndTestEnvironments extends SerenityStories {
    public BehaviorsForUatAndTestEnvironments() {
        super();
    }

    public BehaviorsForUatAndTestEnvironments(Configuration configuration) {
//        super(configuration);
        findStoriesCalled("behavior_uat_tagged.story");
    }
}
