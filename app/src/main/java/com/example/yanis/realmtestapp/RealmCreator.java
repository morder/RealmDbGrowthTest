package com.example.yanis.realmtestapp;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by yanis on 7/5/16.
 */
public class RealmCreator {

    private final static Logger LOGGER = Logger.getLogger(RealmCreator.class);

    public enum Db {
        Test
    }

    public static synchronized Realm getRealmInstance(Db db) {
        return Realm.getInstance(getRealmConfig(db));
    }

    public static synchronized RealmConfiguration getRealmConfig(Db db) {
        switch (db) {
            case Test:
                return new RealmConfiguration.Builder()
                        .name(db.name() + ".realm")
                        .modules(new TestModule())
                        .schemaVersion(1)
                        .migration(new RealmMigration() {
                            @Override
                            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                            }
                        })
                        .build();
            default:
                throw new RuntimeException("Realm " + db.name() + " not realized");
        }
    }

}
