package com.elepy.hibernate.fast;

import com.elepy.Elepy;
import com.elepy.annotations.Tag;
import com.elepy.hibernate.DatabaseConfigurations;
import com.elepy.tests.basic.BasicFunctionalityTest;

@Tag("slow")
public class HSQLBasicFunctionalityTest extends BasicFunctionalityTest {

    @Override
    public void configureElepy(Elepy elepy) {
        elepy.addConfiguration(DatabaseConfigurations.HSQL);
    }
}
