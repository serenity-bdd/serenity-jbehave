package net.serenitybdd.jbehave.service;


import net.thucydides.core.statistics.service.FeatureStoryTagProvider;
import net.thucydides.core.statistics.service.TagProvider;
import net.thucydides.core.statistics.service.TagProviderStrategy;
import net.thucydides.core.steps.StepEventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class JBehaveTagProviderStrategy implements TagProviderStrategy {

        @Override
        public boolean canHandleTestSource(String testType) {
            return StepEventBus.TEST_SOURCE_JBEHAVE.equals(testType);
        }

        @Override
        public Iterable<TagProvider> getTagProviders() {
            List<TagProvider> retTagProviders = new ArrayList<TagProvider>();
            ServiceLoader<TagProvider> tagProviders = ServiceLoader.load(TagProvider.class);
            Iterator<TagProvider> iterator = tagProviders.iterator();
            while(iterator.hasNext()) {
                TagProvider currentTagProvider = iterator.next();
                if (!(currentTagProvider instanceof FeatureStoryTagProvider)) {
                    retTagProviders.add(currentTagProvider);
                }
            }
            return retTagProviders;
        }
}

