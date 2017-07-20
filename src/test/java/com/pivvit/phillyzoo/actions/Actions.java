package com.pivvit.phillyzoo.actions;

import pivvit.base.ObjectsCollection;

public class Actions {
    private static ThreadLocal<ObjectsCollection<Object>> actions = ThreadLocal.withInitial(ObjectsCollection::new);

    public static void clear() {
        actions.get().clear();
    }

    public static NavigationActions navigationActions(){return actions.get().getInstance(NavigationActions.class);}
}
